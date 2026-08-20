package net.boat.industrialhellscape.block.block_classes.StorageBlocks;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import org.jetbrains.annotations.Nullable;

/*
INFO:
-----
Block will be placed facing the player (cardinal direction) if placed on floor or ceiling. If placed on wall, it will face away from wall.
Block has a block entity within for block storage.

getSlotCount() used by StorageBlockInterface to detect desired Block Entity item slot amount to create.
getOpenSound() and getClosedSound() used by StorageBlockInterface to detect desired sounds for opening and closing Block Entity menu.
*/

public class BarrelStorageBlock extends FacingStorageBlock implements EntityBlock {
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public final int SLOTS;
    public final SoundEvent OPEN_SOUND;
    public final SoundEvent CLOSE_SOUND;

    private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;

    public BarrelStorageBlock(Properties properties, int slotsAmount, SoundEvent openSound, SoundEvent closeSound) {
        super(properties, slotsAmount, openSound, closeSound);
        this.SLOTS = slotsAmount;
        this.OPEN_SOUND = openSound;
        this.CLOSE_SOUND = closeSound;

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(FACE, AttachFace.FLOOR)
                .setValue(OPEN, false)
        );
    }

    @Override
    public int getSlotCount() {
        return SLOTS;
    }

    @Override
    public SoundEvent getOpenSound() {
        return OPEN_SOUND;
    }

    @Override
    public SoundEvent getCloseSound() {
        return CLOSE_SOUND;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState state = defaultBlockState();
        Direction directionClicked = pContext.getHorizontalDirection().getOpposite(); //Gets the cardinal direction when player places new block
        Direction direction = pContext.getClickedFace();
        Direction facing = pContext.getHorizontalDirection().getOpposite();

        if (direction.getAxis() == Direction.Axis.Y) { //On Floor or Ceiling
            state = state.setValue(FACE, direction == Direction.UP ? AttachFace.FLOOR : AttachFace.CEILING).setValue(FACING, facing);
        } else { //On walls
            state = state.setValue(FACE, AttachFace.WALL).setValue(FACING, facing);
        }

        state = state.setValue(FACING, directionClicked);

        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, FACE, OPEN);
    }
}
