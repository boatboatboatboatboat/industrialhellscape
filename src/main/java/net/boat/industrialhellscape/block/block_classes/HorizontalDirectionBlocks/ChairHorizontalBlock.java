package net.boat.industrialhellscape.block.block_classes.HorizontalDirectionBlocks;

import net.boat.industrialhellscape.entity.ModEntities;
import net.boat.industrialhellscape.entity.SittableEntity.SittableEntity;
import net.minecraft.core.BlockPos;
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

public class ChairHorizontalBlock extends ModelledHorizontalBlock implements SimpleWaterloggedBlock {

    // Additional BlockState Properties
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public ChairHorizontalBlock(Properties pProperties, VoxelShape soloShape) {
        super(pProperties, soloShape);
    }


    @Override
    public @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
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
