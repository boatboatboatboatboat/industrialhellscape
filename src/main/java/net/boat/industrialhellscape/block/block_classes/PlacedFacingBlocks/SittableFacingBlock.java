package net.boat.industrialhellscape.block.block_classes.PlacedFacingBlocks;

import net.boat.industrialhellscape.entity.ModEntities;
import net.boat.industrialhellscape.entity.SittableEntity.SittableEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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
import org.jetbrains.annotations.NotNull;

import java.util.List;

//INFO:
//-----

public class SittableFacingBlock extends ModelledFacingBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    //public final EntityType<SittableEntity> sittableEntityType = ModEntities.FOLDING_CHAIR.get(); FAILS TO LOAD
    //private final EntityType<?> sittableEntityType;
    public double seatHeight;

    public SittableFacingBlock(Properties pProperties, VoxelShape soloShape) {
        super(pProperties, soloShape);
        this.seatHeight = getSeatHeight(soloShape);
    }

    public double getSeatHeight(VoxelShape voxelShape) {
        return voxelShape.max(Direction.Axis.Y);
    }

//    private EntityType<SittableEntity> getSittableEntity(VoxelShape voxelShape) { //FAILS TO LOAD
//        double seatHeight = voxelShape.max(Direction.Axis.Y);
//
//        return (seatHeight == 0.5) ? ModEntities.FOLDING_CHAIR.get() : ModEntities.OFFICE_CHAIR.get();
//    }


    @Override
    public @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        //EntityType<SittableEntity> chairEntity = (seatHeight == 0.5) ? ModEntities.FOLDING_CHAIR.get() : ModEntities.OFFICE_CHAIR.get();

        EntityType<SittableEntity> chairEntity = ModEntities.CHAIR.get();

        if(!level.isClientSide()) {
            Entity entity;
            List<SittableEntity> entities = level.getEntities(chairEntity, new AABB(pos), chair -> true);
            if(entities.isEmpty()) {
                entity = chairEntity.spawn((ServerLevel) level, pos, MobSpawnType.TRIGGERED);
            } else {
                entity = entities.getFirst();
            }

            if(entity != null) {
                player.startRiding(entity);
            }
        }

        return InteractionResult.SUCCESS;
    }

}
