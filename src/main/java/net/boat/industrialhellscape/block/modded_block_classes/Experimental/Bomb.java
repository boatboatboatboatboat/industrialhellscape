package net.boat.industrialhellscape.block.modded_block_classes.Experimental;

import net.boat.industrialhellscape.block.modded_block_classes.PlacedFacingBlocks.SimpleFacingBlock;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Bomb extends SimpleFacingBlock {
    public Bomb(Properties pProperties) {
        super(pProperties);
    }

    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction directionClicked = pContext.getHorizontalDirection();
        BlockState state = this.defaultBlockState().setValue(FACING, directionClicked); //First, defines facing direction of the block
        return state;
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if(stack.is(ModTags.Items.IH_COMPATIBLE_TOOLS)) {
            int chargeDistance = 5;
            int overPenetration = 3;

            Explosion corn = new Explosion(level, null, 1, 1, 1, 1, true, Explosion.BlockInteraction.DESTROY);

            float blockBlastResistance;
            BlockState loopingState;
            BlockPos loopingPos = pos;

            Direction chargeDirection = state.getValue(FACING);

            //Initial Blast
            Vec3 vec3 = pos.getCenter();
            level.explode(null, level.damageSources().explosion(null), null, vec3, 5, true, Level.ExplosionInteraction.BLOCK);

            //Directional Blasts
            for(int i = 0; i < chargeDistance; i++) {

                loopingPos = loopingPos.relative(chargeDirection);
                Vec3 vec3loop = loopingPos.getCenter();
                blockBlastResistance = state.getExplosionResistance(level, loopingPos, corn);

                //Destroy block if under certain blast resistance
                if(blockBlastResistance <= 6 + overPenetration) {
                    loopingState = level.getBlockState(loopingPos);
                    level.getBlockState(loopingPos).getBlock().destroy(level, loopingPos, loopingState);
                }
                level.explode(null, level.damageSources().explosion(null), null, vec3loop, 1, true, Level.ExplosionInteraction.TNT);
            }

            level.playSound(player, pos, SoundEvents.DONKEY_DEATH, SoundSource.BLOCKS, 0.25f, 1f);
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
