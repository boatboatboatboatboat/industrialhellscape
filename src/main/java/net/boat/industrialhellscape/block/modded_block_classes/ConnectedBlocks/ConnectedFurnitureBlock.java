package net.boat.industrialhellscape.block.modded_block_classes.ConnectedBlocks;

import com.mojang.serialization.MapCodec;
import net.boat.industrialhellscape.block.modded_block_state_properties.DynamicConnectionState;
import net.boat.industrialhellscape.block.modded_interfaces.ConnectedModelCapability;
import net.boat.industrialhellscape.block.modded_interfaces.HitboxRotationInterface;
import net.boat.industrialhellscape.block.modded_logic_enums.MultiBlockPlacementDirection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

//INFO:
//-----
// This block, when placed, connects with similarly aligned neighbors. Custom models allow the resulting connection to look like a seamless model (E.G. a multi-block table or desk).
// Waterlogging and cardinal directional placement is supported.
// The operating methods for block state detection and updating are present in this mod's ConnectedModelCapability interface.
// Waterlogging is handled by the vanilla SimpleWaterloggedBlock interface.
// Can connect to other block classes. This ability is determined by the block tag passed as a parameter during block registration (TagKey<Block> inputCompatibleBlockSet)

// Block-state notation:
//     Solo - Unconnected block-state. When placed for the first time by itself with no eligible adjacent connections.
//     Left - an "end" connection that should be the left-end portion of a connected block group (a multi-block desk/table) relative to the facing player.
//     Middle - an interior connection that may repeat based on the length of the blocks connected.
//     Right - an "end" connection that should be the right-end portion of a connected block group (a multi-block desk/table) relative to the facing player.

// Block class is adapted from Hearth and Home mod's Stone Pillar block class code.

public class ConnectedFurnitureBlock extends Block implements SimpleWaterloggedBlock, ConnectedModelCapability {

    public static final EnumProperty<DynamicConnectionState> TYPE = EnumProperty.create("type", DynamicConnectionState.class); //"TYPE" is used to store enum value of "solo, left, right, middle" for block connected variants
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING; //"FACING" is used to store DirectionProperty value of "north, south, east, west"
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public final TagKey<Block> BlockSetFamily; //To determine other blocks aside from its own can this block connect to
    private final MultiBlockPlacementDirection placementDirection;

    private final VoxelShape SOLO_SHAPE_NORTH;
    private final VoxelShape SOLO_SHAPE_SOUTH;
    private final VoxelShape SOLO_SHAPE_EAST;
    private final VoxelShape SOLO_SHAPE_WEST;

    private final VoxelShape LEFT_SHAPE_NORTH;
    private final VoxelShape LEFT_SHAPE_SOUTH;
    private final VoxelShape LEFT_SHAPE_EAST;
    private final VoxelShape LEFT_SHAPE_WEST;

    private final VoxelShape MIDDLE_SHAPE_NORTH;
    private final VoxelShape MIDDLE_SHAPE_SOUTH;
    private final VoxelShape MIDDLE_SHAPE_EAST;
    private final VoxelShape MIDDLE_SHAPE_WEST;

    private final VoxelShape RIGHT_SHAPE_NORTH;
    private final VoxelShape RIGHT_SHAPE_SOUTH;
    private final VoxelShape RIGHT_SHAPE_EAST;
    private final VoxelShape RIGHT_SHAPE_WEST;

    public ConnectedFurnitureBlock(Properties pProperties, TagKey<Block> inputCompatibleBlockSet, VoxelShape soloShape, VoxelShape leftShape, VoxelShape middleShape, VoxelShape rightShape, MultiBlockPlacementDirection placementDirection) {
        //When registering this block, pass in:
        // Properties,
        // Block Tag for related blocks that this block can physically connect to
        // Unconnected state hitbox VoxelShape
        // Left connection state hitbox VoxelShape
        // Middle connection state hitbox VoxelShape
        // Right connection state hitbox VoxelShape
        // Whether or not blocks should be placed horizontally, vertically, or longitudinally to check for connections

        super(pProperties);

        //Define the Voxelshape hitboxes for each state
        SOLO_SHAPE_NORTH = soloShape;
        SOLO_SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, soloShape);
        SOLO_SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, soloShape);
        SOLO_SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, soloShape);

        LEFT_SHAPE_NORTH = leftShape;
        LEFT_SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, leftShape);
        LEFT_SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, leftShape);
        LEFT_SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, leftShape);

        MIDDLE_SHAPE_NORTH = middleShape;
        MIDDLE_SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, middleShape);
        MIDDLE_SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, middleShape);
        MIDDLE_SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, middleShape);

        RIGHT_SHAPE_NORTH = rightShape;
        RIGHT_SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, rightShape);
        RIGHT_SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, rightShape);
        RIGHT_SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, rightShape);

        //To determine other blocks aside from its own can this block connect to
        this.BlockSetFamily = inputCompatibleBlockSet;

        //To determine which direction placed blocks will connect to.
        this.placementDirection = placementDirection;

        //Default state is Solo/unconnected, facing North, no waterlogging
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TYPE, DynamicConnectionState.SOLO)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public EnumProperty<DynamicConnectionState> getTypeProperty() {
        return TYPE;
    }

    @Override
    public DirectionProperty getFacingProperty() {
        return FACING;
    }

    @Override
    public BooleanProperty getWaterloggedProperty() {
        return WATERLOGGED;
    }

    //---------- USE INTERACT, HITBOXES, PLACEMENT, AND BLOCK UPDATE METHODS HANDLED BY INTERFACES ---------

    @Override
    public @Nonnull VoxelShape getShape(@NotNull BlockState pState, @Nonnull  BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
        //See this mod's ConnectedModelCapability interface to view the following method.
        return makeConnectedHitboxes(
                pState,
                LEFT_SHAPE_NORTH,LEFT_SHAPE_SOUTH,LEFT_SHAPE_EAST,LEFT_SHAPE_WEST,
                MIDDLE_SHAPE_NORTH,MIDDLE_SHAPE_SOUTH,MIDDLE_SHAPE_EAST,MIDDLE_SHAPE_WEST,
                RIGHT_SHAPE_NORTH,RIGHT_SHAPE_SOUTH,RIGHT_SHAPE_EAST,RIGHT_SHAPE_WEST,
                SOLO_SHAPE_NORTH,SOLO_SHAPE_SOUTH,SOLO_SHAPE_EAST,SOLO_SHAPE_WEST);
    }
    @Override
    public @Nullable BlockState getStateForPlacement(@NotNull BlockPlaceContext pContext) {
        //See this mod's ConnectedModelCapability interface to view the following method.
        return placeTheConnectableBlock(this, pContext, BlockSetFamily, placementDirection);
    }
    public void neighborChanged(@Nonnull BlockState state, @NotNull Level level, @Nonnull BlockPos positionClicked, @Nonnull Block block, @Nonnull BlockPos fromPos, boolean isMoving) {
        //See this mod's ConnectedModelCapability interface to view the following method.
        whenConnectedNeighborUpdated(state,level,positionClicked,block,fromPos,BlockSetFamily, placementDirection);
    }
    //---------- END OF METHODS HANDLED BY INTERFACE ----------

    @Override
    public @Nonnull RenderShape getRenderShape(@Nonnull BlockState state) {
        return RenderShape.MODEL;
    }
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    protected BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, TYPE); //Block's blockstates; its NSEW orientation, its connection type defined
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return null;
    }
}