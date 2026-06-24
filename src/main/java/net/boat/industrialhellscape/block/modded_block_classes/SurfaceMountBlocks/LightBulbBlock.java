package net.boat.industrialhellscape.block.modded_block_classes.SurfaceMountBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

// Code obtained from Create Deco mod (CC0 1.0 license)

public class LightBulbBlock extends ModelledSurfaceMountBlock {

//    public static final BooleanProperty LIT = BlockStateProperties.LIT;
//    public static final BooleanProperty INVERTED = BlockStateProperties.INVERTED;

    public final Supplier<SoundEvent> ON_SOUND;
    public final Supplier<SoundEvent> OFF_SOUND;

    public LightBulbBlock(Properties pProperties, VoxelShape floorHitBox, Supplier<SoundEvent> onSound, Supplier<SoundEvent> offSound) {
        super(pProperties, floorHitBox);

        this.ON_SOUND = onSound;
        this.OFF_SOUND = offSound;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BlockStateProperties.LIT, false)
                .setValue(BlockStateProperties.INVERTED, false));
    }

    public static boolean shouldBeLit (BlockState state, Level level, BlockPos pos) {
        Direction attach = state.getValue(FACING).getOpposite();
        return state.getValue(BlockStateProperties.INVERTED) ^ level.hasSignal(pos.relative(attach), attach);
    }

    @Override
    public void neighborChanged (BlockState state, @NotNull Level level, BlockPos pos, @NotNull Block neighbor, @NotNull BlockPos neighborPos, boolean bool) {
        Direction face = state.getValue(FACING);
        if (pos.relative(face.getOpposite()).equals(neighborPos)) {
            BlockState next = toggle(state, level, pos);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        boolean wasOn = state.getValue(BlockStateProperties.LIT);
        SoundEvent onOffSound = wasOn ? OFF_SOUND.get() : ON_SOUND.get();

        BlockState next = this.toggle(state.cycle(BlockStateProperties.INVERTED), level, pos);
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            float pitch = next.getValue(BlockStateProperties.INVERTED) ? 0.6f : 0.5f;
            level.playSound((Player)null, pos, onOffSound, SoundSource.BLOCKS, 0.3f, pitch);
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
        builder.add(BlockStateProperties.LIT, BlockStateProperties.INVERTED, FACING, WATERLOGGED, ATTACH_FACE);
    }

}


