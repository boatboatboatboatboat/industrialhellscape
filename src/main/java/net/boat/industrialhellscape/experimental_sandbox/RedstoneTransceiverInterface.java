package net.boat.industrialhellscape.experimental_sandbox;

import net.minecraft.core.BlockPos;

public interface RedstoneTransceiverInterface {
    public int getTransmittedStrength();

    public void setReceivedStrength(int power);

    public boolean isListening();

    public boolean isAlive();

    public String getNetworkKey();

    public BlockPos getLocation();
}
