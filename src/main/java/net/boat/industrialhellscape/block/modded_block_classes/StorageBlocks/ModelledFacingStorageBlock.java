package net.boat.industrialhellscape.block.modded_block_classes.StorageBlocks;

import net.boat.industrialhellscape.block.modded_interfaces.StorageBlockInterface;
import net.boat.industrialhellscape.block.modded_interfaces.HitboxRotationInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

//INFO:
//-----
//This block supports cardinal directional placement, and an inventory with GUI. The inventory size is determined upon block registration.
//It extends the vanilla block class, Horizontal DirectionalBlock, and implements the vanilla interface, EntityBlock
//
//-----

public class ModelledFacingStorageBlock extends FacingStorageBlock implements EntityBlock, StorageBlockInterface, SimpleWaterloggedBlock {
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public final int SLOTS;
    public final Supplier<SoundEvent> OPEN_SOUND;
    public final Supplier<SoundEvent> CLOSE_SOUND;
    public final VoxelShape SHAPE;

    private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final VoxelShape SOLO_SHAPE_NORTH;
    private final VoxelShape SOLO_SHAPE_SOUTH;
    private final VoxelShape SOLO_SHAPE_EAST;
    private final VoxelShape SOLO_SHAPE_WEST;

    public ModelledFacingStorageBlock(Properties properties, int slotsAmount, Supplier<SoundEvent> openSound, Supplier<SoundEvent> closeSound, VoxelShape hitbox) {
        // When registering this block, pass in
        // Properties
        // Integer amount of slots the block entity inventory will have (multiple of 9)
        // Sound to play when player opens block
        // Sound to play when player closes block

        super(properties, slotsAmount, openSound, closeSound);
        this.SLOTS = slotsAmount;
        this.OPEN_SOUND = openSound;
        this.CLOSE_SOUND = closeSound;
        this.SHAPE = hitbox;
        this.SOLO_SHAPE_NORTH = hitbox;
        this.SOLO_SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, hitbox);
        this.SOLO_SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, hitbox);
        this.SOLO_SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, hitbox);

        this.registerDefaultState(this.stateDefinition.any().setValue(OPEN, false));

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(OPEN, false)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public int getSlotCount() {
        return SLOTS;
    }

    @Override
    public Supplier<SoundEvent> getOpenSound() {
        return OPEN_SOUND;
    }

    @Override
    public Supplier<SoundEvent> getCloseSound() {
        return CLOSE_SOUND;
    }

    @Override
    public @Nonnull VoxelShape getShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {

        //When placed, if the block's "TYPE" property is one of these cases, find its horizontal orientation and give it the correct hitbox
        //North is the default orientation assumed if no other cases met

        return switch (pState.getValue(FACING)) {
            case SOUTH -> SOLO_SHAPE_SOUTH;
            case EAST -> SOLO_SHAPE_EAST;
            case WEST -> SOLO_SHAPE_WEST;
            default -> SOLO_SHAPE_NORTH;
        };
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        BlockState state = defaultBlockState();
        Direction directionClicked = pContext.getHorizontalDirection().getOpposite(); //Gets the cardinal direction when player places new block
        //The autogenerated block item models show the "front" of the block as the right side.
        //In-world, this side faces away from the player when placed, which is not intuitive.
        //getOpposite() is used to rectify in-world placement while keeping any auto-generated asymmetric textured full-block models consistent.
        state = state.setValue(WATERLOGGED,fluidstate.getType() == Fluids.WATER); //check for waterlogging status
        state = state.setValue(FACING, directionClicked); //Defines facing direction of the block
        return state;
    }

    public @Nonnull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    protected @NotNull BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, WATERLOGGED);
    }

    //---------- Block Entity Handling Methods below ----------
    //---------- End of Block Entity Handling Methods ----------
}