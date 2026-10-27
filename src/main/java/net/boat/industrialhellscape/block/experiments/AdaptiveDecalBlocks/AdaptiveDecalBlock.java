package net.boat.industrialhellscape.block.experiments.AdaptiveDecalBlocks;

import net.boat.industrialhellscape.block.block_classes.HorizontalDirectionBlocks.ModelledHorizontalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AdaptiveDecalBlock extends ModelledHorizontalBlock {

    private static final int maxOffsets = 15;
    public static final IntegerProperty OFFSET_PIXELS = IntegerProperty.create("offset",0, maxOffsets); //no offset up to 15 pixel offset

    protected AdaptiveDecalBlock(Properties properties, VoxelShape hitbox) {
        super(properties, hitbox);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(OFFSET_PIXELS,0)
        );

        // Upon block registration, cache all 64 hitboxes here??? or calculate offsets every time hitbox is collided/looked at

        // datagen 16x4=64 hitboxes???
        // datagen 8x4=32 hitboxes??? //8
        // datagen 4x4=16 hitboxes???
    }

//    @Override
//    public @Nonnull VoxelShape getShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
//        //North is the default orientation assumed if no other cases met
//
//        return switch (pState.getValue(FACING)) {
//            case SOUTH -> SOLO_SHAPE_SOUTH.move(0,0,pState.getValue(OFFSET_PIXELS)*0.0625);
//            case EAST -> SOLO_SHAPE_EAST.move(pState.getValue(OFFSET_PIXELS)*0.0625,0,0);
//            case WEST -> SOLO_SHAPE_WEST.move(pState.getValue(OFFSET_PIXELS)*-0.0625,0,0); //has negative
//            default -> SOLO_SHAPE_NORTH.move(0,0,pState.getValue(OFFSET_PIXELS)*-0.0625); //has negative
//        };
//    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection().getOpposite(); //Gets the cardinal direction when player places new block
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());

        BlockState state = defaultBlockState().setValue(WATERLOGGED,fluidstate.getType() == Fluids.WATER); //check for waterlogging status

        state = state.setValue(FACING, facing); //Defines facing direction of the block
        return state;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        //Define offset by reading hitbox of neighbor block this block is attached to
        /*
        //VoxelShape.move()

        //VoxelShape.forAllBoxes()

        //VoxelShape.findIndex() //returns int
         */

        Direction directionTowardsNeighborPos = state.getValue(FACING).getOpposite();
        BlockPos neighborPos = pos.relative(directionTowardsNeighborPos);
        BlockState neighborBlockState = level.getBlockState(neighborPos);

        VoxelShape stateHitBox = state.getCollisionShape(level, pos); //0 by default
        VoxelShape neighborHitbox = neighborBlockState.getCollisionShape(level,neighborPos);

        //Preallocate for-loop variable
        BlockState candidateState = state;
        VoxelShape candidateHitBox = candidateState.getCollisionShape(level,pos);


        for(int i = 1; i < maxOffsets+1; i++) {
           candidateState = candidateState.setValue(OFFSET_PIXELS, i);
           candidateHitBox = candidateState.getCollisionShape(level,pos);

           if(Shapes.joinIsNotEmpty(neighborHitbox, candidateHitBox, BooleanOp.AND)) { //If there is an intersection???
                break;
           } else { //no intersection, candidate passes
               state = candidateState;
           }
        }

        level.setBlock(pos,state,3); //set the state with appropriate offset value

        super.onPlace(state, level, pos, oldState, movedByPiston);
    }
}
