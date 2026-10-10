package net.boat.industrialhellscape.block.block_classes.FenceBlocks;

import com.google.common.primitives.Booleans;
import net.boat.industrialhellscape.block.block_interfaces.HitboxRotationInterface;
import net.boat.industrialhellscape.block.block_interfaces.ToolUseInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;


/*
Source: https://github.com/talrey/CreateDeco/blob/1.21-neo/src/main/java/com/github/talrey/createdeco/blocks/MeshFenceBlock.java
Source License: CC0 1.0 Universal

Custom fence implementation:
    - Tool use toggles the appearance of the fence post.

Caveats observed from CDeco's fence implementation:
    - The visible fence post is not correlated with the boolean UP property. It may render even when "UP" is false.
    - The multipart blockstate handles the combinations in which the visible fence post should render (such as at corners or triple connections),
        independent of whether "UP" is true or not
    - In these cases, using a tool to toggle "UP" blockstate will render the fencepost twice, overlapping identical models
    - Visible change only occurs when a tool toggles "UP" at a straight section of fence (where either NORTH and SOUTH states, or EAST and WEST states are true)

    - Handling fencepost appearance through block state model rendering could be more performant than manually writing
        code to check whether appropriate to toggle UP-block state.
 */

public class FencePostBlock extends FenceBlock implements ToolUseInterface {
    public static final BooleanProperty UP = BlockStateProperties.UP; //fencePost block state that optionally renders post
    public final VoxelShape POST_SHAPE;

    public final VoxelShape NORTH_SHAPE;
    public final VoxelShape SOUTH_SHAPE;
    public final VoxelShape EAST_SHAPE;
    public final VoxelShape WEST_SHAPE;


    public FencePostBlock(Properties pProperties, VoxelShape postShape, VoxelShape fenceShape) {
        super(pProperties);
        this.POST_SHAPE = postShape;
        this.NORTH_SHAPE = fenceShape;
        this.SOUTH_SHAPE = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH,fenceShape);
        this.EAST_SHAPE = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST,fenceShape);
        this.WEST_SHAPE = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST,fenceShape);

        /*
        copy/pasted Create deco (if post-placement is handled by multi-part blockstate rendering,
        set default "UP" value to false
         */

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(UP, false)

                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override //necessary for wallBlocks to create columns to support fence posts based on hitbox reading
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = Shapes.empty();
        if(state.getValue(UP)) shape = Shapes.join(shape,POST_SHAPE, BooleanOp.OR);
        if (state.getValue(NORTH)) shape = Shapes.join(shape, NORTH_SHAPE, BooleanOp.OR);
        if (state.getValue(SOUTH)) shape = Shapes.join(shape, SOUTH_SHAPE, BooleanOp.OR);
        if (state.getValue(EAST))  shape = Shapes.join(shape, EAST_SHAPE,  BooleanOp.OR);
        if (state.getValue(WEST))  shape = Shapes.join(shape, WEST_SHAPE,  BooleanOp.OR);
        return shape;
    }

    protected static boolean directionToConnectionStateEval(Direction face, boolean north, boolean south, boolean east, boolean west) {
        //direction to BooleanProperty
        return switch (face) {
            case SOUTH -> south;
            case EAST -> east;
            case WEST -> west;
            default -> north;
        };
    }

    //comment out onPlace() and onRemove() if using CDeco's fencepost method
    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        /*
            Check all four directions around the placed block.
            If a neighboring block is of this block class,
            check if its connected straight along NS or EW
            if so, set its block state to disable the fence post.

            Handles DISabling of FencePost ONLY by performing setBlock on neighbors
         */

        if( (state.getBlock().equals(oldState.getBlock()))  ) {
            return; //don't correct neighbors if block itself is unchanged.
        }

        for(Direction dir: BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            BlockPos neighborPos = pos.relative(dir);
            BlockState neighborState = level.getBlockState(neighborPos);

            //Remove posts from neighbors
            if(neighborState.getBlock() instanceof FencePostBlock) {
                //If neighbor state is instance of this block, safely read off boolean connection values
                boolean north = neighborState.getValue(NORTH);
                boolean south = neighborState.getValue(SOUTH);
                boolean east = neighborState.getValue(EAST);
                boolean west = neighborState.getValue(WEST);

                switch(dir) {
                         /*
                        If neighboring fenceBlock DOES NOT have ANY perpendicular connection AND has at least one in-line connection
                        remove neighbor's fence post

                        if statement allows logic to pass through if conditions aren't met. This
                        prevents needless block update chains.
                         */
                    case EAST, WEST -> {
                        if( (!(north || south)) && (directionToConnectionStateEval(dir, north,south,east,west)) ) {
                            level.setBlock(neighborPos, neighborState.setValue(UP, false), 3);
                        }
                    }
                    case NORTH, SOUTH -> {
                        if( (!(east || west)) && (directionToConnectionStateEval(dir, north,south,east,west)) ) {
                            level.setBlock(neighborPos, neighborState.setValue(UP, false), 3);
                        }
                    }
                }
            }
        }

        //add post to self
//        boolean north = state.getValue(NORTH);
//        boolean south = state.getValue(SOUTH);
//        boolean east = state.getValue(EAST);
//        boolean west = state.getValue(WEST);
//        int amountOfConnections = amountOfConnections(north, south, east, west);
//        switch(amountOfConnections) {
//            case 0,1,3,4 -> {
//                state = state.setValue(UP, true);
//                level.setBlock(pos, state, 3);
//            }
//        }
        level.setBlock(pos, setFencePostBasedOnConnectionAmount(state),3);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {

        state = super.updateShape(state, facing, facingState, level, currentPos, facingPos);

        if(facingState.getBlock() instanceof WallBlock) { //weirdly buggy when placed on wallBlocks
            return state;
        }

        //Handles ENabling of FencePost ONLY
//        boolean north = state.getValue(NORTH);
//        boolean south = state.getValue(SOUTH);
//        boolean east = state.getValue(EAST);
//        boolean west = state.getValue(WEST);
//
//        int amountOfConnections = amountOfConnections(north, south, east, west);
//
//        switch(amountOfConnections) {
//            case 0,1,3,4 -> {
//                state = state.setValue(UP, true);
//            }
//        }
        state = setFencePostBasedOnConnectionAmount(state);

        return state;
    }

    protected BlockState setFencePostBasedOnConnectionAmount(BlockState state) {
        boolean[] boolArray = { state.getValue(NORTH), state.getValue(SOUTH), state.getValue(EAST), state.getValue(WEST) };
        //https://www.geeksforgeeks.org/java/java-guava-booleans-counttrue-method-with-examples/
        switch (Booleans.countTrue(boolArray)) {
            case 0,1,3,4 -> {
                return state.setValue(UP, true);
            }
        }
        return state; //return what it already has
    }

//    protected int amountOfConnections(boolean north, boolean south, boolean east, boolean west) {
//        boolean[] boolArray = { north, south, east, west };
//        //https://www.geeksforgeeks.org/java/java-guava-booleans-counttrue-method-with-examples/
//        return Booleans.countTrue(boolArray);
//    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(fenceExists(state)) { //prevent removal of single post if fence is unconnected, to prevent invisible ghost blocks
            if(ToolUseInterface.checkForEnabledTool(stack)) {
                state = state.cycle(UP);
                level.setBlock(pos, state, 3);
                level.playSound(player, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 0.25f, 1f);

                BlockState aboveBlockState;

                for(int i = 0; i<16; i++) {
                    pos = pos.above();
                    aboveBlockState = level.getBlockState(pos);
                    if(aboveBlockState.getBlock() instanceof FencePostBlock) {
                        level.setBlock(pos, aboveBlockState.setValue(UP, state.getValue(UP)),3);
                    } else {
                        break;
                    }
                }

                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    //Checks if ANY railings exist at that block position
    private static boolean fenceExists(BlockState pState) {
        boolean occupied = false;

        /* For all possible cardinal values
           "occupied" becomes true ONLY IF any of the block state boolean properties are TRUE
           Meaning that a RailingBlock is present here.
         */

        //first, check the four directions
        for (Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            occupied |= pState.getValue(fromDirection(dir));
        }

        return occupied;
    }

    protected static BooleanProperty fromDirection(Direction face) {
        return switch (face) {
            case SOUTH -> SOUTH;
            case EAST -> EAST;
            case WEST -> WEST;
            default -> NORTH;
        };
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP, NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }
}
