package net.boat.industrialhellscape.experimental_sandbox;

import net.boat.industrialhellscape.block.block_classes.SurfaceMountBlocks.ModelledSurfaceMountBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RedstoneTransceiverBlock extends ModelledSurfaceMountBlock implements EntityBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty RECEIVER = BooleanProperty.create("receiver");

    public int newNetworkFrequency;
    public BlockPos instanceBlockPos;
    public int receivedPower;
    public int transmittedPower;

    public RedstoneTransceiverBlock(Properties pProperties, VoxelShape floorHitBox) {
        super(pProperties, floorHitBox);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(FACE, AttachFace.WALL)
                .setValue(WATERLOGGED, false)
                .setValue(POWERED, false)
                .setValue(RECEIVER, false)
        );
    }

    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        newNetworkFrequency = (int)(Math.random() * 100);

        this.instanceBlockPos = blockPos;
        this.receivedPower = 0;
        this.transmittedPower = 0;

        return new RedstoneTransceiverBE(blockPos, blockState);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos position, Player player, BlockHitResult hitResult) {
//        if (!level.isClientSide() && level.getBlockEntity(position) instanceof RedstoneTransceiverBE blockEntity) {
//            blockEntity.toggle(); //toggle is_powered field for BE
//        }

        //toggle block's transmission behavior
        if(player.isShiftKeyDown()) {
            state.cycle(RECEIVER);
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        receivedPower = level.getBestNeighborSignal(pos);
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return state.getValue(POWERED) && state.getValue(RECEIVER);
    }

    @Override
    public int getDirectSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        if (side != blockState.getValue(FACING))
            return 0;
        return getSignal(blockState, blockAccess, pos, side);
    }

    //To determine output signal power
    @Override
    public int getSignal(BlockState state, BlockGetter blockAccess, BlockPos pos, Direction side) {
        if (!state.getValue(RECEIVER)) { //if is transmitter
            return 0;
        }
        //If a receiver, will output signal power
        if(blockAccess.getBlockEntity(pos) instanceof RedstoneTransceiverBE redstoneTransceiverBE) {
            return redstoneTransceiverBE.getReceivedSignal();
        } else {
            return 0;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED, RECEIVER);
    }

}
