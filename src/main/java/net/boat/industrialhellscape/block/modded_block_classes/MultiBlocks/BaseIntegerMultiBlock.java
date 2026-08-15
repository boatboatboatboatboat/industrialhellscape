package net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks;

import net.boat.industrialhellscape.block.modded_block_classes.PlacedFacingBlocks.SimpleFacingBlock;
import net.boat.industrialhellscape.block.modded_interfaces.MultiBlockPlacementInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import org.jetbrains.annotations.NotNull;

public abstract class BaseIntegerMultiBlock extends SimpleFacingBlock implements MultiBlockPlacementInterface, IBlockExtension {
    public int[][] multiBlockPlacementMatrix; //not static, the placement matrix is unique to many kinds of blocks
    public final IntegerProperty partProperty;

    public BaseIntegerMultiBlock(Properties pProperties, int[][] multiBlockPlacementMatrix, IntegerProperty partProperty) {
        super(pProperties);
        //maximumBlockStates = registerMaxBlockStates-1;
        this.multiBlockPlacementMatrix = multiBlockPlacementMatrix;
        this.partProperty = partProperty;

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
        );
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockPos originPos = pContext.getClickedPos();
        BlockState pState = this.defaultBlockState();
        Direction facing = pContext.getHorizontalDirection().getOpposite(); //Standard orientation for Minecraft blocks with a "front" side (faces opposite, towards player)

        return MultiBlockPlacementInterface.placeOriginBlock(level, originPos, pState, FACING, facing, multiBlockPlacementMatrix);
    }

    public PushReaction getPistonPushReaction(@NotNull BlockState pState) {
        return PushReaction.NORMAL;
    }
}
