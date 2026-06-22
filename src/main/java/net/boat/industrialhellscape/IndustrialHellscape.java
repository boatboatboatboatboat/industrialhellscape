package net.boat.industrialhellscape;

import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.modded_block_entities.DebugBE.DebugBERenderer;
import net.boat.industrialhellscape.block.modded_block_entities.ModBlockEntities;
import net.boat.industrialhellscape.entity.ModEntities;
import net.boat.industrialhellscape.entity.SittableEntity.SittableEntityRenderer;
import net.boat.industrialhellscape.item.ModCreativeModeTabs;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.screen.ModMenuTypes;
import net.boat.industrialhellscape.screen.custom.DebugBEScreen;
import net.boat.industrialhellscape.sound.ModSounds;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.client.Minecraft;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

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
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModSounds.register(modEventBus);

        ModEntities.register(modEventBus); //Sittable Entity (in-world)
        ModBlockEntities.register(modEventBus); //Block Entities handling GUIs and inventory

        ModMenuTypes.register(modEventBus);

        //Registers config options for this mod. The config screen registration is handled in IndustrialHellscapeClient
        modContainer.registerConfig(ModConfig.Type.COMMON, CommonModConfig.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

//    // Add the example block item to the building blocks tab
//    private void addCreative(BuildCreativeModeTabContentsEvent event) {
//        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
//            event.accept(ModItems.INHELL_HAVEN_DEVICE);
//        }
//    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = IndustrialHellscape.MOD_ID, value = Dist.CLIENT)
    static class ClientModEvents {
        @SubscribeEvent
        static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            EntityRenderers.register(ModEntities.CHAIR.get(), SittableEntityRenderer::new);
        }


//        @SubscribeEvent
//        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
//            event.registerBlockEntityRenderer(ModBlockEntities.DEBUG_BE.get(), DebugBERenderer::new);
//        }
//        @SubscribeEvent
//        public static void registerScreens(RegisterMenuScreensEvent event) {
//            event.register(ModMenuTypes.DEBUG_BE_MENU.get(), DebugBEScreen::new);
//        }
    }
}
