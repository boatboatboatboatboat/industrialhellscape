package net.boat.industrialhellscape.block.special_blocks;

import net.boat.industrialhellscape.block.special_blocks_properties.HorizontalConnectedModelCapability;
import net.boat.industrialhellscape.block.special_blocks_properties.FurnitureConnectionState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
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
import org.jetbrains.annotations.Nullable;

public class ConnectedFurnitureBlock extends HorizontalDirectionalBlock implements HorizontalConnectedModelCapability, SimpleWaterloggedBlock {

    public static final EnumProperty<FurnitureConnectionState> TYPE = EnumProperty.create("type", FurnitureConnectionState.class); //"TYPE" is used to store enum value of "solo, left, right, middle" for block connected variants
    public static DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING; //"FACING" is used to store DirectionProperty value of "north, south, east, west" //KJ
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public TagKey<Block> BlockSetFamily;
    private static final VoxelShape SHAPE = Block.box(0.1, 0.1, 0.1, 15.9, 15.9, 15.9);

    protected ConnectedFurnitureBlock(Properties pProperties, TagKey<Block> inputCompatibleBlockSet) {
        super(pProperties);
        this.BlockSetFamily = inputCompatibleBlockSet;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TYPE, FurnitureConnectionState.SOLO) //Default state if placed with no player present
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    //Placement Faces the player
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel(); //Reads the "level" or world
        BlockPos positionClicked = pContext.getClickedPos(); //Get the position when player places new block
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        Direction directionClicked = pContext.getHorizontalDirection(); //Gets the cardinal direction when player places new block
        BlockState state = this.defaultBlockState().setValue(FACING, directionClicked); //First, defines facing direction of the block
        state = state.setValue(TYPE, getType(state, getRelativeLeft(level, positionClicked, directionClicked), getRelativeRight(level, positionClicked, directionClicked), BlockSetFamily)); //Second, defines connection type of the block
        state = state.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER)); //check for waterlogging status
        return state;
    }

    @Override //THIS TELLS THE NEIGHBORS TO UPDATE
    public void neighborChanged(BlockState state, Level level, BlockPos positionClicked, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            if (state.getValue(WATERLOGGED)) {
                level.scheduleTick(fromPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }
        }

        if (level.isClientSide) return;

        Direction directionClicked = state.getValue(FACING); //<<<<<< Possibly here
        FurnitureConnectionState type = getType(state, getRelativeLeft(level, positionClicked, directionClicked), getRelativeRight(level, positionClicked, directionClicked), BlockSetFamily);
        if (state.getValue(TYPE) == type) return;

        state = state.setValue(TYPE, type);
        level.setBlock(positionClicked, state, 3); //3
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED, TYPE); //Block's blockstates; its NSEW orientation, its connection type defined
    }
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
}
