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
    private static final ExplosionDamageCalculator USED_PORTAL_DAMAGE_CALCULATOR;

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
            int chargeDistance = 10;
            Direction chargeDirection = state.getValue(FACING);

            Vec3 vec3 = pos.getCenter();
            level.explode(null, level.damageSources().explosion(null), null, vec3, 3, true, Level.ExplosionInteraction.BLOCK);

            for(int i = 0; i < chargeDistance; i++) {
                pos = pos.relative(chargeDirection);
                Vec3 vec3loop = pos.getCenter();
                level.explode(null, level.damageSources().explosion(null), USED_PORTAL_DAMAGE_CALCULATOR, vec3loop, 1, true, Level.ExplosionInteraction.TNT);
            }
            level.playSound(player, pos, SoundEvents.DONKEY_DEATH, SoundSource.BLOCKS, 0.25f, 1f);
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    static {
        USED_PORTAL_DAMAGE_CALCULATOR = new ExplosionDamageCalculator() {
            public boolean shouldBlockExplode(Explosion p_353087_, BlockGetter p_353096_, BlockPos pos, BlockState state, float p_353094_) {
                return state.is(Blocks.DIRT) ? false : super.shouldBlockExplode(p_353087_, p_353096_, pos, state, p_353094_);
            }

            public float getKnockbackMultiplier(Entity entity) {
                return 100.0F;
            }

            public Optional<Float> getBlockExplosionResistance(Explosion p_353090_, BlockGetter p_353088_, BlockPos p_353091_, BlockState p_353093_, FluidState p_353095_) {
                return p_353093_.is(Blocks.DIRT) ? Optional.empty() : super.getBlockExplosionResistance(p_353090_, p_353088_, p_353091_, p_353093_, p_353095_);
            }
        };
    }
}
