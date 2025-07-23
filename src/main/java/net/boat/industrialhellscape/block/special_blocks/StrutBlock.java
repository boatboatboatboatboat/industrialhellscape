package net.boat.industrialhellscape.block.special_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class StrutBlock extends Block implements SimpleWaterloggedBlock {

    //Smaller than full-size collision hitbox to allow player to climb block
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D);

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public StrutBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return super.getStateForPlacement(pContext).setValue(WATERLOGGED, Boolean.valueOf(flag));
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WATERLOGGED);
    }

    //CODE BELOW: CLIMBING FUNCTIONALITY on sides of full blocks
    @Override
    public boolean isLadder(BlockState state, LevelReader world, BlockPos pos, @Nullable LivingEntity entity)
    {
        return true;
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn)
    {
        super.entityInside(state, worldIn, pos, entityIn);
        if(entityIn instanceof LivingEntity&&isLadder(state, worldIn, pos, (LivingEntity)entityIn))
            applyLadderLogic(entityIn);
    }

    public static void applyLadderLogic(Entity entityIn)
    {
        if(entityIn instanceof LivingEntity&&!((LivingEntity)entityIn).onClimbable())
        {
            Vec3 motion = entityIn.getDeltaMovement();
            float maxMotion = 0.15F;
            motion = new Vec3(
                    Mth.clamp(motion.x, -maxMotion, maxMotion),
                    Math.max(motion.y, -maxMotion),
                    Mth.clamp(motion.z, -maxMotion, maxMotion)
            );

            entityIn.fallDistance = 0.0F;

            if(motion.y < 0&&entityIn instanceof Player&&entityIn.isShiftKeyDown())
                motion = new Vec3(motion.x, 0, motion.z);
            else if(entityIn.horizontalCollision)
                motion = new Vec3(motion.x, 0.2, motion.z);
            entityIn.setDeltaMovement(motion);
        }
    }
}