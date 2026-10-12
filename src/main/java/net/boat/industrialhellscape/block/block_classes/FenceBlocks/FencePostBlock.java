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
    - Tool use toggles the appearance of the fence post, which vertically chains up to 16 block height.
    - If placed on WallBlocks, wall columns will line up below any produced fence posts
    - Multipart blockState datagen handles 90-degree model rotation of fence-post for blocks
      such as Barbed Wire Fence Top in ModBlockStateProvider

Caveats observed from CDeco's fence implementation:
    - The visible fence post is not correlated with the boolean UP property. It may render even when "UP" is false.
    - The multipart blockState handles the combinations in which the visible fence post should render (such as at corners or triple connections),
        independent of whether "UP" is true or not
    - In these cases, using a tool to toggle "UP" blockstate will render the fencepost twice, overlapping identical models
    - Visible change only occurs when a tool toggles "UP" at a straight section of fence (where either NORTH and SOUTH states, or EAST and WEST states are true)

    - Handling fencepost appearance through block state model rendering could be more performant than manually writing
        code to check whether appropriate to toggle UP-block state.
 */

public class FencePostBlock extends FenceBlock implements ToolUseInterface {
    //fence-post block state
    public static final BooleanProperty UP = BlockStateProperties.UP;

    //hitboxes
    public final VoxelShape POST_SHAPE;
    public final VoxelShape NORTH_SHAPE;
    public final VoxelShape SOUTH_SHAPE;
    public final VoxelShape EAST_SHAPE;
    public final VoxelShape WEST_SHAPE;


    public FencePostBlock(Properties pProperties, VoxelShape postShape, VoxelShape fenceShape) {
        super(pProperties);

        //hitbox definitions
        this.POST_SHAPE = postShape;
        this.NORTH_SHAPE = fenceShape;
        this.SOUTH_SHAPE = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH,fenceShape);
        this.EAST_SHAPE = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST,fenceShape);
        this.WEST_SHAPE = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST,fenceShape);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(UP, false)
                /*
                keep at false. If enclosing an area with fences, a "true" default "UP" value
                results in the very last fence segment before the corner having an unnecessary fencepost
                */
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

    /*
        Necessary for wallBlocks to create columns to support fence posts based on hitbox reading
        (isCovered() method in WallBlocks reads collisionShape values)
    */
    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = Shapes.empty();
        if(state.getValue(UP)) shape = Shapes.join(shape,POST_SHAPE, BooleanOp.OR);
        if (state.getValue(NORTH)) shape = Shapes.join(shape, NORTH_SHAPE, BooleanOp.OR);
        if (state.getValue(SOUTH)) shape = Shapes.join(shape, SOUTH_SHAPE, BooleanOp.OR);
        if (state.getValue(EAST))  shape = Shapes.join(shape, EAST_SHAPE,  BooleanOp.OR);
        if (state.getValue(WEST))  shape = Shapes.join(shape, WEST_SHAPE,  BooleanOp.OR);
        return shape;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        /*
            Check all four directions around the placed block.
            If a neighboring block is of this block class,
            check if its connected straight along NS or EW
            if so, set its block state to disable the fence post.

            Handles DISabling of FencePost by performing setBlock on neighbors
         */

        if( (state.getBlock().equals(oldState.getBlock()))  ) {
            /*
                Don't correct neighbors if block itself is unchanged.
                Allows players to create fence posts next to each other without
                fence-post removal logic undoing the actions.
            */
            return;
        }

        //Check 4 directions, remove eligible fence-posts from neighbors
        for(Direction dir: BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            BlockPos neighborPos = pos.relative(dir);
            BlockState neighborState = level.getBlockState(neighborPos);

            if((neighborState.getBlock() instanceof FencePostBlock)) {

                boolean neighborHasCornerConnection = hasCornerConnection(neighborState,dir); //pass in neighbor's state, not current state
                boolean neighborIsSupported = fenceHasSupportedPost(level,neighborState,neighborPos); //pass in neighbor's state/pos, not current state/pos
                /*
                    If neighbor FencePostBlock
                    - does not have perpendicular connections
                    - and has a connection extending away from current block
                    - and is not a fencePost already supported above and below by other Posts
                    Remove that neighbor FencePostBlock's fence-post
                */
                if( (!(neighborHasCornerConnection) && (neighborState.getValue(fromDirection(dir))) && !neighborIsSupported) ) {
                    level.setBlock(neighborPos, neighborState.setValue(UP, false), 3); //fencepost removal
                }
            }
        }

        //Only enables fence-post for current block if applicable
        if(fencePostShouldBePlaced(state)) {
            level.setBlock(pos,state.setValue(UP, true), 3);
        } else {
             /*
                Has only two connections
                if there are no collinear connections,
                then block must be a corner connection,
                assign fence-post = true.
             */
            boolean hasCollinearConnection = hasCollinearConnection(state);

            if(!hasCollinearConnection) {
                level.setBlock(pos,state.setValue(UP, true), 3);  //fencepost enabling
            }
        }
        /*
            Current fence-post state remains false by default if no appropriate conditions met.
            Allows FencePostBlock to be seamlessly placed in between others without creating a
            fencePost in between.
         */
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        /*
        fence connection logic from FenceBlock class must happen FIRST before
        fence-post eligibility can be accurately determined
         */
        state = super.updateShape(state, facing, facingState, level, currentPos, facingPos);

        /*
            Ignore block updates from blocks above/below fence
            This avoids weird behavior with WallBlocks
        */
        if(facing.getAxis().isVertical()) {
            return state;
        }

        //Only enable fence-post if applicable
        if(fencePostShouldBePlaced(state)) {
            state = state.setValue(UP, true);
        } else {  //has only two connections
            boolean currentStateHasSupportedPost = fenceHasSupportedPost(level, state, currentPos);
            boolean hasCollinearConnection = hasCollinearConnection(state);
            //if 2 connections are collinear, disable fence-post;
            if(hasCollinearConnection && !currentStateHasSupportedPost) {
                state = state.setValue(UP, false);
            }
        }

        return state;
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(fenceConnectionsExist(state)) { //prevent removal of single post if fence is unconnected, to prevent invisible ghost blocks
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
                        //stop loop early if no more fencePostBlocks detected
                        break;
                    }
                }

                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    protected static boolean fencePostShouldBePlaced(BlockState fencePostBlockState) {
        /*
            Determines fence-post eligibility based on number of connections.
            Having two connections does not guarantee a fence-post should be present
            Corners should have a post, but straight-fence connections should not by default.
         */

        //https://www.geeksforgeeks.org/java/java-guava-booleans-counttrue-method-with-examples/
        boolean[] boolArray = { fencePostBlockState.getValue(NORTH), fencePostBlockState.getValue(SOUTH), fencePostBlockState.getValue(EAST), fencePostBlockState.getValue(WEST) };
        switch (Booleans.countTrue(boolArray)) {
            case 0,1,3,4 -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    protected static boolean hasCornerConnection(BlockState fencePostBlockState, Direction dir) {
        Direction perpendicularDirCW = dir.getClockWise(); //perpendicular to passed direction
        Direction perpendicularDirCCW = dir.getCounterClockWise(); //perpendicular to passed direction

        boolean connectsCW = fencePostBlockState.getValue(fromDirection(perpendicularDirCW));
        boolean connectsCCW = fencePostBlockState.getValue(fromDirection(perpendicularDirCCW));

        return (connectsCW || connectsCCW);
    }

    protected static boolean hasCollinearConnection(BlockState fencePostBlockState) {
        //FencePostBlock has at a straight connection (NS or EW)
        boolean north = fencePostBlockState.getValue(NORTH);
        boolean south = fencePostBlockState.getValue(SOUTH);
        boolean east = fencePostBlockState.getValue(EAST);
        boolean west = fencePostBlockState.getValue(WEST);
        return ((north && south) || (east && west));
    }

    protected static boolean fenceHasSupportedPost(LevelAccessor level, BlockState fencePostBlockState, BlockPos pos) {
        //FencePostBlock's fence-post is supported by another fence-post above or below it
        boolean supportedPost = false;
        BlockPos aboveNeighbor = pos.above();
        BlockPos belowNeighbor = pos.below();
        BlockState aboveState = level.getBlockState(aboveNeighbor);
        BlockState belowState = level.getBlockState(belowNeighbor);

        if(fencePostBlockState.getValue(UP)) {
            if(aboveState.getBlock() instanceof FencePostBlock) {
                supportedPost |= aboveState.getValue(UP);
            }
            if(belowState.getBlock() instanceof FencePostBlock) {
                supportedPost |= belowState.getValue(UP);
            }
        }
        return supportedPost;
    }


    private static boolean fenceConnectionsExist(BlockState pState) {
        //Checks if ANY fence connections exist at that block position
        boolean occupied = false;

        for (Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            occupied |= pState.getValue(fromDirection(dir));
        }

        return occupied;
    }

    protected static BooleanProperty fromDirection(Direction face) {
        //maps Direction to respective BooleanProperty
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
