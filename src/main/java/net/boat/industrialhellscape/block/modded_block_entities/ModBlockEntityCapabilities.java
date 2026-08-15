package net.boat.industrialhellscape.block.modded_block_entities;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = IndustrialHellscape.MOD_ID)
public class ModBlockEntityCapabilities {
    @SubscribeEvent
    public static void registerBlockEntityCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.STORAGE_BE.get(),
                (be, c) -> be.getItemCapability()
        );
    }
}