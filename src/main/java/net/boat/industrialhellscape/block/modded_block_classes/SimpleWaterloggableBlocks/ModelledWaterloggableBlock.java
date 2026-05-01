package net.boat.industrialhellscape.block.modded_block_classes.SimpleWaterloggableBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;

//Non-directional modelled block (radially symmetrical) that permits waterlogging.

public class ModelledWaterloggableBlock extends SimpleWaterloggableBlock implements SimpleWaterloggedBlock {

    private final VoxelShape HITBOX_SHAPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public ModelledWaterloggableBlock(Properties pProperties, VoxelShape hitboxShape) {
        super(pProperties);
        HITBOX_SHAPE = hitboxShape;
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    public @Nonnull VoxelShape getShape(@Nonnull BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
        return HITBOX_SHAPE;
    }
}