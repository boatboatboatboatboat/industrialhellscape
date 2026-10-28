package net.boat.industrialhellscape.block.block_classes.FaceAttachedHorizontalDirectionBlocks;

import net.boat.industrialhellscape.block.block_interfaces.SoundPlayInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

// Code obtained from Create Deco mod (CC0 1.0 license)

public class LightBulbBlock extends ModelledFAHDBlock implements SoundPlayInterface {
    public final SoundEvent ON_SOUND;
    public final SoundEvent OFF_SOUND;

    public LightBulbBlock(Properties pProperties, VoxelShape floorHitBox, SoundEvent onSound, SoundEvent offSound) {
        super(pProperties, floorHitBox);

        this.ON_SOUND = onSound;
        this.OFF_SOUND = offSound;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BlockStateProperties.LIT, false)
                .setValue(BlockStateProperties.INVERTED, false)
                .setValue(FACING, Direction.NORTH)
                .setValue(FACE, AttachFace.WALL)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public @NotNull BlockState rotate (BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public static boolean shouldBeLit (BlockState state, Level level, BlockPos pos) {
        Direction attach = directionTowardsLightAttachmentPoint(state);
        return state.getValue(BlockStateProperties.INVERTED) ^ level.hasSignal(pos.relative(attach), attach);
    }

    @Override
    public void neighborChanged (@NotNull BlockState state, @NotNull Level level, BlockPos pos, @NotNull Block neighbor, @NotNull BlockPos neighborPos, boolean bool) {
        Direction attach = directionTowardsLightAttachmentPoint(state);

        if (pos.relative(attach).equals(neighborPos)) {
            toggle(state, level, pos);
        }
    }

    public static Direction directionTowardsLightAttachmentPoint(BlockState state) {
        return switch(state.getValue(FACE)) {
            case FLOOR -> Direction.DOWN;
            case CEILING -> Direction.UP;
            default ->  state.getValue(FACING).getOpposite();
        };
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem (BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player entity, @NotNull BlockHitResult hitResult) {
        BlockState next = this.toggle(state.cycle(BlockStateProperties.INVERTED), level, pos);
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            SoundPlayInterface.onOffSoundWithUpdateCheck(state,next,level,pos,BlockStateProperties.LIT,ON_SOUND,OFF_SOUND,1f,1f,0.5f);
            return InteractionResult.CONSUME;
        }
    }

    private BlockState toggle (BlockState state, Level level, BlockPos pos) {
        BlockState next = state.setValue(BlockStateProperties.LIT, shouldBeLit(state, level, pos));
        level.setBlock(pos, next, 3);

        return next;
    }

    @Override
    protected void createBlockStateDefinition (StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(
                BlockStateProperties.LIT,
                BlockStateProperties.INVERTED,
                FACING,
                FACE,
                WATERLOGGED
        );
    }
}


