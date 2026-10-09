package net.boat.industrialhellscape.block.block_classes.FenceBlocks;

import net.boat.industrialhellscape.block.block_interfaces.HitboxRotationInterface;
import net.boat.industrialhellscape.block.block_interfaces.ToolUseInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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

import javax.annotation.Nonnull;


/*
Source: https://github.com/talrey/CreateDeco/blob/1.21-neo/src/main/java/com/github/talrey/createdeco/blocks/MeshFenceBlock.java
Source License: CC0 1.0 Universal

Custom fence implementation:
    Custom hitboxes applied during registration.
    Tool use toggles the appearance of the fence post.

 */

public class optionalFencePostFenceBlock extends FenceBlock implements ToolUseInterface {
    public static final BooleanProperty UP = BlockStateProperties.UP; //fencePost block state that optionally renders
    private final VoxelShape POST_SHAPE;

    private final VoxelShape NORTH_FENCE_SHAPE;
    private final VoxelShape SOUTH_FENCE_SHAPE;
    private final VoxelShape EAST_FENCE_SHAPE;
    private final VoxelShape WEST_FENCE_SHAPE;


    public optionalFencePostFenceBlock(Properties pProperties, VoxelShape postHitBox, VoxelShape northFenceHitBox) {
        super(pProperties);
        this.POST_SHAPE = postHitBox; //radially symmetrical regardless of direction
        this.NORTH_FENCE_SHAPE = northFenceHitBox; //north by default
        this.SOUTH_FENCE_SHAPE = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, NORTH_FENCE_SHAPE);
        this.EAST_FENCE_SHAPE = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, NORTH_FENCE_SHAPE);
        this.WEST_FENCE_SHAPE = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, NORTH_FENCE_SHAPE);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(UP, true)
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    //same as railing block. consider class/interface reorganization in future
    @Override
    public @Nonnull VoxelShape getShape(@Nonnull BlockState pState, @Nonnull BlockGetter reader, @Nonnull BlockPos pos, @Nonnull CollisionContext ctx) {
        return getInteractionShape(pState, reader, pos);
    }

    //same as railing block. consider class/interface reorganization in future
    @Override
    public @Nonnull VoxelShape getInteractionShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos) {
        VoxelShape shape = Shapes.empty();

        if(pState.getValue(UP)) shape = Shapes.join(shape, POST_SHAPE, BooleanOp.OR);

        if (pState.getValue(NORTH)) shape = Shapes.join(shape, NORTH_FENCE_SHAPE, BooleanOp.OR);
        if (pState.getValue(SOUTH)) shape = Shapes.join(shape, SOUTH_FENCE_SHAPE, BooleanOp.OR);
        if (pState.getValue(EAST))  shape = Shapes.join(shape, EAST_FENCE_SHAPE,  BooleanOp.OR);
        if (pState.getValue(WEST))  shape = Shapes.join(shape, WEST_FENCE_SHAPE,  BooleanOp.OR);
        return shape;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
//        boolean north = oldState.getValue(NORTH);
//        boolean south = oldState.getValue(SOUTH);
//        boolean east = oldState.getValue(EAST);
//        boolean west = oldState.getValue(WEST);
//
//        if((west && east) && (north && south)) {
//            state.setValue(UP, false);
//        }

        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return ToolUseInterface.simpleToolUse(stack,state,level,pos,player,UP, 3);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(UP, NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }
}
