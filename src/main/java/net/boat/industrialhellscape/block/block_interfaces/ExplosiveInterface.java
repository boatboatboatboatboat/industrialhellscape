package net.boat.industrialhellscape.block.block_interfaces;

import net.boat.industrialhellscape.block.block_entities.BrushableBE;
import net.boat.industrialhellscape.item.ModDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public interface ExplosiveInterface {

    ExplosionDamageCalculator LANDMINE_DAMAGE_CALCULATOR = new ExplosionDamageCalculator() {

        //custom values
        final int blockBlastResistanceBoost = 10; //Default behavior is 1; High values - weaker effect on block destruction
        final int entityLethalityCoefficient = 3; //TNT default is 2; High values - stronger damage over distance

        @Override
        public Optional<Float> getBlockExplosionResistance(Explosion explosion, BlockGetter reader, BlockPos pos, BlockState state, FluidState fluid) {
            return state.is(Blocks.NETHER_PORTAL) ?

                    Optional.empty()
                    :
                    Optional.of(Math.max( (float)
                                state.getExplosionResistance(reader, pos, explosion) * blockBlastResistanceBoost,
                                fluid.getExplosionResistance(reader, pos, explosion) * blockBlastResistanceBoost
                            )
                    );
        }

        @Override
        public float getEntityDamageAmount(Explosion explosion, Entity entity) {
            float lethality = explosion.radius() * entityLethalityCoefficient;
            Vec3 vec3 = explosion.center();
            double d0 = Math.sqrt(entity.distanceToSqr(vec3)) / (double)lethality;
            double d1 = ((double)1.0F - d0) * (double)Explosion.getSeenPercent(vec3, entity);
            return (float)((d1 * d1 + d1) / (double)2.0F * (double)7.0F * (double)lethality + (double)1.0F);
        }
    };

    static void armExplosive(BlockState state, Level level, BlockPos pos, Player player) {
        if (level.getBlockEntity(pos) instanceof BrushableBE be) {
            //Play Sound
            if (!level.isClientSide) {
                level.playSound(null, pos, SoundEvents.TRIPWIRE_CLICK_ON, SoundSource.BLOCKS, 2, 1);
            } else {
                player.displayClientMessage(Component.literal("Landmine is now armed"), true);
            }

            if(!be.initiatedArmingProcedure) {
                //ensures players can't spam-queue multiple scheduled ticks
                level.scheduleTick(pos, state.getBlock(), 60);
                //tells block entity arming procedure is complete
                be.initiatedArmingProcedure = true;
            }
        }
    }

    static void disarmExplosive(BlockState state, Level level, BlockPos pos, Player player) {
        //Play Sound
        if (!level.isClientSide) {
            level.playSound(null, pos, SoundEvents.TRIPWIRE_CLICK_OFF, SoundSource.BLOCKS, 1, 1);
        } else {
            player.displayClientMessage(Component.literal("Landmine is disarmed and SAFE"), true);
        }

        //Disarm Explosive instantly
        if (level.getBlockEntity(pos) instanceof BrushableBE be) {
            be.initiatedArmingProcedure = false;
        }
        state = state.setValue(BlockStateProperties.DISARMED, true);
        level.setBlock(pos, state, 3);
    }

    static void explodeLandMine(Level level, BlockPos pos, Entity entityStepping) {
        if(!level.isClientSide() ) {
            Vec3 vec3 = pos.getCenter().add(0,1,0); //negligable damage if explosion raycasts are in center of block. offset vertically
            level.explode(null, level.damageSources().source(ModDamageTypes.LANDMINE_DAMAGE), LANDMINE_DAMAGE_CALCULATOR, vec3, 5.0F, true, Level.ExplosionInteraction.BLOCK);
        }
    }

}
