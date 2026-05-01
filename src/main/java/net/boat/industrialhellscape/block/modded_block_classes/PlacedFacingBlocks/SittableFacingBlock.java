package net.boat.industrialhellscape.block.modded_block_classes.PlacedFacingBlocks;

import net.boat.industrialhellscape.block.modded_block_entities.ModEntities;
import net.boat.industrialhellscape.block.modded_block_entities.SittableEntity.SittableEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import java.util.List;

//INFO:
//-----

public class SittableFacingBlock extends ModelledFacingBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public SittableFacingBlock(Properties pProperties, VoxelShape soloShape) {
        super(pProperties, soloShape);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
        );
    }

    public @Nonnull InteractionResult use(@Nonnull BlockState pState, @Nonnull Level pLevel, @Nonnull BlockPos pPos, Player pPlayer, @Nonnull InteractionHand pHand, @Nonnull BlockHitResult pHit) {
        if(!pLevel.isClientSide()) {
            Entity entity;
            List<SittableEntity> entities = pLevel.getEntities(ModEntities.CHAIR.get(), new AABB(pPos), chair -> true);
            if(entities.isEmpty()) {
                entity = ModEntities.CHAIR.get().spawn((ServerLevel) pLevel, pPos, MobSpawnType.TRIGGERED);
            } else {
                entity = entities.get(0);
            }

            pPlayer.startRiding(entity);
        }

        return InteractionResult.SUCCESS;
    }

}
