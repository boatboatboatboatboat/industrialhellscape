package net.boat.industrialhellscape.block.block_classes.PlacedFacingBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
//INFO:
//-----
// This block supports rotation of custom models. It also supports directional placement based on player.
// It supports waterlogging.
// It supports interactions for model changes with the boolean property "POWERED"
// Will currently be used for blocks that emit light when "powered". The "LIT" property is omitted from usage for simplicity.
// It extends the modded block class, ModelledFacingBlock. Rotation of hitboxes for all cardinal directions is already handled in that class.
//-----

public class InteractableModelledFacingBlock extends ModelledFacingBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED; //For binary states (on, off, or lit, unlit)

    public final SoundEvent ON_SOUND;
    public final SoundEvent OFF_SOUND;

    public InteractableModelledFacingBlock(Properties pProperties, VoxelShape soloShape, SoundEvent onSound, SoundEvent offSound) {
        super(pProperties, soloShape);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false)
                .setValue(WATERLOGGED, false));
        this.ON_SOUND = onSound;
        this.OFF_SOUND = offSound;
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
            boolean wasOn = state.getValue(POWERED);
            state = state.cycle(POWERED);
            level.setBlock(pos, state, 2);
            SoundEvent onOffSound = wasOn ? OFF_SOUND : ON_SOUND;

            level.playSound(player, pos, onOffSound, SoundSource.BLOCKS, 1f, 1f);
            return InteractionResult.sidedSuccess(level.isClientSide);

    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED, POWERED); //Block's blockstates; its NSEW orientation, its connection type defined
    }
}


