package net.boat.industrialhellscape;

import com.mojang.logging.LogUtils;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.modded_block_entities.ModBlockEntities;
import net.boat.industrialhellscape.entity.ModEntities;
import net.boat.industrialhellscape.item.ModCreativeModeTabs;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.sound.ModSounds;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(IndustrialHellscape.MOD_ID)
public class IndustrialHellscape {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "industrialhellscape";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.

    public IndustrialHellscape(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (IndustrialHellscape) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);
        IndustrialHellscape.LOGGER.info("InHell: Creative Tab Registration Complete");
        ModItems.register(modEventBus);
        IndustrialHellscape.LOGGER.info("InHell: Item Registration Complete");
        ModBlocks.register(modEventBus);
        IndustrialHellscape.LOGGER.info("InHell: Block Registration Complete");
        ModSounds.register(modEventBus);
        IndustrialHellscape.LOGGER.info("InHell: Sound Registration Complete");
        ModEntities.register(modEventBus); //Sittable Entity (in-world)
        IndustrialHellscape.LOGGER.info("InHell: Entity Registration Complete");
        ModBlockEntities.register(modEventBus); //Block Entities handling GUIs and inventory
        IndustrialHellscape.LOGGER.info("InHell: Block Entity Registration Complete");

        //Deprecated Content Safe Removal/Replacement for in-game
        ModBlocks.removeAndReplaceBlocks();
        ModItems.removeAndReplaceItems();

        //Registers config options for this mod. The config screen registration is handled in IndustrialHellscapeClient
        modContainer.registerConfig(ModConfig.Type.COMMON, ModCommonConfig.SPEC);
        IndustrialHellscape.LOGGER.info("InHell: Common Config Registration Complete");
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        // LOGGER.info("HELLO FROM COMMON SETUP");
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        //LOGGER.info("HELLO from server starting");
    }
}
