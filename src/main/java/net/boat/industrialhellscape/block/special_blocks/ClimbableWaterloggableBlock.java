package net.boat.industrialhellscape.block.special_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

//INFO:
//-----
// This block supports basic ladder-like climability via enabling ladder logic and setting a slightly smaller block hitbox to facilitate entity climbing.
// It supports waterlogging but otherwise has no other block-states.

// Block class is adapted from Immersive Engineering's Scaffolding block class code.

public class ClimbableWaterloggableBlock extends ModelledWaterloggableBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public ClimbableWaterloggableBlock(Properties pProperties, VoxelShape hitBoxShape) {
        super(pProperties, hitBoxShape);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
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