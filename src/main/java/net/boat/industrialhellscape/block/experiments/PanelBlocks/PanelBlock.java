package net.boat.industrialhellscape.block.experiments.PanelBlocks;

import com.mojang.serialization.MapCodec;
import net.boat.industrialhellscape.block.block_interfaces.HitboxRotationInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

//Consider making this a subclass of railing blocks with the extra up and down direction properties

public class PanelBlock extends Block {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    //hitboxes
    protected final VoxelShape SHAPE_NORTH;
    protected final VoxelShape SHAPE_SOUTH;
    protected final VoxelShape SHAPE_EAST;
    protected final VoxelShape SHAPE_WEST;
    protected final VoxelShape SHAPE_UP;
    protected final VoxelShape SHAPE_DOWN;

    public static final BooleanProperty NORTH_PANEL = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH_PANEL = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST_PANEL = BlockStateProperties.EAST;
    public static final BooleanProperty WEST_PANEL = BlockStateProperties.WEST;
    public static final BooleanProperty UP_PANEL = BlockStateProperties.UP;
    public static final BooleanProperty DOWN_PANEL = BlockStateProperties.DOWN;

    public PanelBlock(Properties pProperties, VoxelShape floorHitbox) {
        super(pProperties);

        this.SHAPE_DOWN = floorHitbox;
        this.SHAPE_UP = HitboxRotationInterface.rotateVoxelXAxisIntTimes(2, floorHitbox); //rotates floor hitbox to ceiling surface position

        this.SHAPE_NORTH = HitboxRotationInterface.rotateVoxelXAxisIntTimes(3, floorHitbox); //Rotates floor hitbox to the north surface position (Right Hand Rule)
        this.SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, SHAPE_NORTH);
        this.SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, SHAPE_NORTH);
        this.SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, SHAPE_NORTH);



        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH_PANEL, false)
                .setValue(SOUTH_PANEL, false)
                .setValue(EAST_PANEL,  false)
                .setValue(WEST_PANEL,  false)
                .setValue(UP_PANEL,  false)
                .setValue(DOWN_PANEL,  false)

                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public boolean canBeReplaced(BlockState pState, @Nonnull BlockPlaceContext pUseContext) {
        Player player = pUseContext.getPlayer();
        Direction facing = pUseContext.getClickedFace().getOpposite(); //different from railing blocks
        boolean itemInHandIsThis = (player != null) && pUseContext.getItemInHand().is(this.asItem());

        /*
        If Player exists with an instance of PanelBlock in their hand, and is NOT facing an existing PanelBlock, return true.
        This allows additional PanelBlock to be placed in the same block
         */
        return (itemInHandIsThis && !stateExistsAtDirection(facing, pState));
    }

    protected static boolean stateExistsAtDirection(Direction facing, BlockState pState) {
        return switch(facing) {
            case SOUTH -> pState.getValue(SOUTH_PANEL);
            case WEST -> pState.getValue(WEST_PANEL);
            case EAST -> pState.getValue(EAST_PANEL);
            case NORTH -> pState.getValue(NORTH_PANEL);
            case UP -> pState.getValue(UP_PANEL);
            case DOWN -> pState.getValue(DOWN_PANEL);
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement (BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockPos position = pContext.getClickedPos();
        Direction facing = pContext.getClickedFace().getOpposite();
        FluidState fluid = level.getFluidState(position);
        Block clickedBlock = level.getBlockState(position).getBlock();
        BlockState state = level.getBlockState(position); //Get the current block-state
        boolean isThisBlock = clickedBlock instanceof PanelBlock;

        if(isThisBlock) { //If there is a RailingBlock at the location of placement
            switch(facing) { //Assign true to the property corresponding with the direction player is facing to place a new railing in that direction next to existing ones
                case NORTH -> state = state.setValue(NORTH_PANEL, true);
                case SOUTH -> state = state.setValue(SOUTH_PANEL, true);
                case EAST -> state = state.setValue(EAST_PANEL, true);
                case WEST -> state = state.setValue(WEST_PANEL, true);

                case UP -> state = state.setValue(UP_PANEL, true);
                case DOWN -> state = state.setValue(DOWN_PANEL, true);
            }
            state.setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
            return state;

        } else { //Fallback. If there is NOT a detected RailingBlock at this location.
            return defaultBlockState() //get a new "blank slate" (the default block slate with no fences defined earlier)
                    .setValue(NORTH_PANEL, (facing == Direction.NORTH)) //Set value based on where player is currently looking
                    .setValue(SOUTH_PANEL, (facing == Direction.SOUTH))
                    .setValue(EAST_PANEL, (facing == Direction.EAST))
                    .setValue(WEST_PANEL, (facing == Direction.WEST))

                    .setValue(UP_PANEL, (facing == Direction.UP))
                    .setValue(DOWN_PANEL, (facing == Direction.DOWN))

                    .setValue(WATERLOGGED, fluid.getType() == Fluids.WATER); //Set waterlogged status also

        }
    }

    @Override
    public @Nonnull VoxelShape getShape(@Nonnull BlockState pState, @Nonnull BlockGetter reader, @Nonnull BlockPos pos, @Nonnull CollisionContext ctx) {
        return getInteractionShape(pState, reader, pos);
    }

    @Override
    public @Nonnull VoxelShape getInteractionShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos) {
        VoxelShape shape = Shapes.empty();
        if (pState.getValue(NORTH_PANEL)) shape = Shapes.join(shape, SHAPE_NORTH, BooleanOp.OR);
        if (pState.getValue(SOUTH_PANEL)) shape = Shapes.join(shape, SHAPE_SOUTH, BooleanOp.OR);
        if (pState.getValue(EAST_PANEL))  shape = Shapes.join(shape, SHAPE_EAST,  BooleanOp.OR);
        if (pState.getValue(WEST_PANEL))  shape = Shapes.join(shape, SHAPE_WEST,  BooleanOp.OR);
        if (pState.getValue(UP_PANEL))  shape = Shapes.join(shape, SHAPE_UP,  BooleanOp.OR);
        if (pState.getValue(DOWN_PANEL))  shape = Shapes.join(shape, SHAPE_DOWN,  BooleanOp.OR);
        return shape;
    }

    @Override
    public void neighborChanged (@Nonnull BlockState pState, @Nonnull Level pLevel, @Nonnull BlockPos pPos, @Nonnull Block neighborBlock, @Nonnull BlockPos neighborPos, boolean movedByPiston) {
        // Used to ensure the block doesn't leave a ghost behind if all 4 sides are gone
        //If there is NO railings present at the blockstate of this location (All FENCE properties were set to false)
        //Set the block at that location to be an air block, erasing the block.

        if (!blockExistsAtDirection(pState, BlockStateProperties.FACING)) pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 0);
        super.neighborChanged(pState, pLevel, pPos, neighborBlock, neighborPos, movedByPiston);
    }

    //Corresponds a Direction input to the block-state boolean property for railing blocks.
    protected static BooleanProperty fromDirection (Direction face) {
        return switch (face) {
            case NORTH -> NORTH_PANEL;
            case SOUTH -> SOUTH_PANEL;
            case EAST  -> EAST_PANEL;
            case WEST  -> WEST_PANEL;

            case UP -> UP_PANEL;
            case DOWN -> DOWN_PANEL;
        };
    }

    //Checks if ANY panels exist at that block position
    private static boolean blockExistsAtDirection(BlockState pState, DirectionProperty facingProperty) {
        boolean occupied = false;

        /* For all possible direction values
           "occupied" becomes true ONLY IF any of the block state boolean properties are TRUE
           Meaning that a panelBlock is present here.
         */

        for (Direction dir : facingProperty.getPossibleValues()) {
            occupied |= pState.getValue(fromDirection(dir));
        }
        return occupied;
    }


    @Override
    protected MapCodec<? extends DirectionalBlock> codec() {
        return null;
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        //identical to railingBlocks. up/down does not apply here
        boolean north = state.getValue(NORTH_PANEL);
        boolean south = state.getValue(SOUTH_PANEL);
        boolean east = state.getValue(EAST_PANEL);
        boolean west = state.getValue(WEST_PANEL);

        switch (rotation){
            case CLOCKWISE_90 -> state = state.setValue(NORTH_PANEL, west)
                    .setValue(WEST_PANEL, south)
                    .setValue(SOUTH_PANEL, east)
                    .setValue(EAST_PANEL, north);
            case CLOCKWISE_180 -> state = state.setValue(NORTH_PANEL, south)
                    .setValue(WEST_PANEL, east)
                    .setValue(SOUTH_PANEL, north)
                    .setValue(EAST_PANEL, west);
            case COUNTERCLOCKWISE_90 -> state = state.setValue(NORTH_PANEL, east)
                    .setValue(EAST_PANEL, south)
                    .setValue(SOUTH_PANEL, west)
                    .setValue(WEST_PANEL, north);
            default -> {
                //Assumed to be case "NONE", therefore block is unchanged
            }
        }
        return state;
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        //identical to railingBlocks. up/down does not apply here
        boolean north = state.getValue(NORTH_PANEL);
        boolean south = state.getValue(SOUTH_PANEL);
        boolean east = state.getValue(EAST_PANEL);
        boolean west = state.getValue(WEST_PANEL);

        switch (mirror) {
            case LEFT_RIGHT -> state = state.setValue(NORTH_PANEL, south)
                    .setValue(SOUTH_PANEL, north);
            case FRONT_BACK -> state = state.setValue(EAST_PANEL, west)
                    .setValue(WEST_PANEL, east);
            default -> {
                //Assumed to be case "NONE", therefore block is unchanged
            }
        }
        return state;
    }

    @Override
    public @Nonnull FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    protected void createBlockStateDefinition (StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH_PANEL, SOUTH_PANEL, EAST_PANEL, WEST_PANEL, UP_PANEL, DOWN_PANEL, WATERLOGGED);
    }
}
