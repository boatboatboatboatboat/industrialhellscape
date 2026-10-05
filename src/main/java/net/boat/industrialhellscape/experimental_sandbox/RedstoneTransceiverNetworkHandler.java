package net.boat.industrialhellscape.experimental_sandbox;

import net.minecraft.world.level.LevelAccessor;

import java.util.IdentityHashMap;

public class RedstoneTransceiverNetworkHandler {

    static final Map<LevelAccessor, Map<String, Set<IRedstoneLinkable>>> connections =
            new IdentityHashMap<>();

}
