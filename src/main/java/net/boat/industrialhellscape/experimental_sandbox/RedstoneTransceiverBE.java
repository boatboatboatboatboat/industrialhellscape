package net.boat.industrialhellscape.experimental_sandbox;

import net.boat.industrialhellscape.block.block_entities.ModBlockEntities;
import net.boat.industrialhellscape.datagen.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RedstoneTransceiverBE extends BlockEntity implements RedstoneTransceiverInterface{

    //tag ids (string)
    private static final String IS_RECEIVER_TAG = "is_receiver";
    private static final String FREQUENCY_TAG = "frequency_string";

    private boolean isReceiver;
    private String frequencyString;
    private int receivedSignal;

    private BlockPos position;

    public RedstoneTransceiverBE(BlockPos position, BlockState state) {
        super(ModBlockEntities.REDSTONE_TRANSCEIVER_BE.get(), position, state);
        isReceiver = state.getValue(RedstoneTransceiverBlock.RECEIVER);
        frequencyString = "Apple";
        receivedSignal = 0;

        this.position = position;
    }

    public boolean isReceiver() {
        return isReceiver;
    }

    public int getReceivedSignal() {
        return receivedSignal;
    }

    //When used, updates the BE instance's receivedSignal field and updates the network
    public void transmit(int strength) {
        receivedSignal = strength;
//        if (link != null)
//            link.notifySignalChange();
    }

    public void setReceiver(boolean isReceiver) {
        if (this.isReceiver == isReceiver) {
            return;
        }

        this.isReceiver = isReceiver;
        setChanged(); //maintains data persistence whenever world saves

        //destroys and sets down updated block state cycling the transmission behavior property
        if (level != null && getBlockState().getValue(RedstoneTransceiverBlock.RECEIVER) != isReceiver) {
            level.setBlock(
                    worldPosition,
                    getBlockState().setValue(RedstoneTransceiverBlock.RECEIVER, isReceiver),
                    Block.UPDATE_CLIENTS
            );
        }
    }

    @Override
    //loads receiver boolean and frequency string
    protected void loadAdditional(
            CompoundTag tag,
            HolderLookup.Provider lookupProvider
    ) {
        super.loadAdditional(tag, lookupProvider);
        isReceiver = tag.getBoolean(IS_RECEIVER_TAG);
        frequencyString = tag.getString(FREQUENCY_TAG);
    }

    @Override
    //Saves receiver boolean and frequency string
    protected void saveAdditional(
            CompoundTag tag,
            HolderLookup.Provider lookupProvider
    ) {
        super.saveAdditional(tag, lookupProvider);
        tag.putBoolean(IS_RECEIVER_TAG, isReceiver);
        tag.putString(FREQUENCY_TAG, frequencyString);
    }

    @Override
    //Applies correct and unique data to ItemStack when block is mined
    protected void collectImplicitComponents(
            DataComponentMap.Builder components
    ) {
        super.collectImplicitComponents(components);
        components.set(ModDataComponents.IS_RECEIVER.get(), isReceiver);
        components.set(ModDataComponents.FREQUENCY_STRING.get(), frequencyString);
    }

    @Override
    //Applies correct block state upon placement in world
    protected void applyImplicitComponents(DataComponentInput components) {
        super.applyImplicitComponents(components);

        setReceiver(components.getOrDefault(
                ModDataComponents.IS_RECEIVER.get(),
                false));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void removeComponentsFromTag(CompoundTag tag) {
        super.removeComponentsFromTag(tag);
        tag.remove(FREQUENCY_TAG);
        tag.remove(IS_RECEIVER_TAG);
    }

    @Override
    public int getTransmittedStrength() {
        return 0;
    }

    @Override
    public void setReceivedStrength(int power) {

    }

    @Override
    public boolean isListening() {
        return false;
    }

    @Override
    public boolean isAlive() {
//        Level level = this.getLevel();
//        BlockPos pos = this.getBlockPos();
//        if (blockEntity.isChunkUnloaded())
//            return false;
//        if (blockEntity.isRemoved())
//            return false;
//        if (!level.isLoaded(pos))
//            return false;
//        return level.getBlockEntity(pos) == blockEntity;
        return false;
    }

    @Override
    public String getNetworkKey() {
        return frequencyString;
    }

    @Override
    public BlockPos getLocation() {
        return position;
    }
}
