package net.boat.industrialhellscape;

import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.special_blocks.StorageBlock.NineSlotMenuScreen;
import net.boat.industrialhellscape.block.special_blocks_properties.ModBlockEntities;
import net.boat.industrialhellscape.entity.ModEntities;
import net.boat.industrialhellscape.block.special_blocks_properties.SittableEntityRenderer;
import net.boat.industrialhellscape.block.special_blocks_properties.ModMenuTypes;
import net.boat.industrialhellscape.sound.ModSounds;
import net.boat.industrialhellscape.item.ModCreativeModTabs;
import net.boat.industrialhellscape.item.ModItems;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.MissingMappingsEvent;

import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(IndustrialHellscape.MOD_ID)
public class IndustrialHellscape {
    public static final String MOD_ID = "industrialhellscape";

    public IndustrialHellscape() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus); //Not Block Entities
        ModBlockEntities.register(modEventBus); //Block Entities
        ModMenuTypes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.MALEVOLENT_MULTITOOL);
            event.accept(ModItems.FLOPPY_DISK);
            event.accept(ModItems.FLOPPY_DISKETTE);
            event.accept(ModItems.INHELL_HAVEN_DEVICE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.CHAIR.get(), SittableEntityRenderer::new);
        }
    }

    @Mod.EventBusSubscriber(modid = IndustrialHellscape.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public class ClientModHandler {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                MenuScreens.register(ModMenuTypes.STORAGE_9SLOT_MENU.get(), NineSlotMenuScreen::new);
            });
        }
    }

    @Mod.EventBusSubscriber(modid = IndustrialHellscape.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class MissingRegistryResolver {
        @SubscribeEvent
        public static void OnMissingRegistryEvent(MissingMappingsEvent event) {

            //Mappings list of Blocks and Items in mod
            List<MissingMappingsEvent.Mapping<Block>> ModBlockMappings = event.getMappings(Registries.BLOCK, IndustrialHellscape.MOD_ID);
            List<MissingMappingsEvent.Mapping<Item>> ModItemMappings = event.getMappings(Registries.ITEM, IndustrialHellscape.MOD_ID);

            //Locate the block to replace

            //Depreciate the Bunker Wall
            ModBlockMappings.stream()
                    .filter(ModBlockMapping -> ModBlockMapping.getKey().getPath().equals("bunker_wall")) //To remove
                    .forEach(ModBlockMapping -> ModBlockMapping.remap( ModBlocks.GRAY_ROCKRETE_PILLAR.get() )); //To replace
            ModItemMappings.stream()
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals("bunker_wall")) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( ModBlocks.GRAY_ROCKRETE_PILLAR.get().asItem() )); //To replace

            //Depreciate LG Rockrete
            ModBlockMappings.stream()
                    .filter(ModBlockMapping -> ModBlockMapping.getKey().getPath().equals("light_gray_rockrete")) //To remove
                    .forEach(ModBlockMapping -> ModBlockMapping.remap( ModBlocks.GRAY_ROCKRETE.get() )); //To replace
            ModItemMappings.stream()
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals("light_gray_rockrete")) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( ModBlocks.GRAY_ROCKRETE.get().asItem() ));

            //Depreciate LG Rockrete Rebar
            ModBlockMappings.stream()
                    .filter(ModBlockMapping -> ModBlockMapping.getKey().getPath().equals("light_gray_rockrete_rebar")) //To remove
                    .forEach(ModBlockMapping -> ModBlockMapping.remap( ModBlocks.GRAY_ROCKRETE_REBAR.get() )); //To replace
            ModItemMappings.stream()
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals("light_gray_rockrete_rebar")) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( ModBlocks.GRAY_ROCKRETE_REBAR.get().asItem() ));

            //Depreciate LG Rockrete Stairs
            ModBlockMappings.stream()
                    .filter(ModBlockMapping -> ModBlockMapping.getKey().getPath().equals("light_gray_rockrete_stairs")) //To remove
                    .forEach(ModBlockMapping -> ModBlockMapping.remap( ModBlocks.GRAY_ROCKRETE_STAIRS.get() )); //To replace
            ModItemMappings.stream()
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals("light_gray_rockrete_stairs")) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( ModBlocks.GRAY_ROCKRETE_STAIRS.get().asItem() ));

            //Depreciate LG Rockrete Slab
            ModBlockMappings.stream()
                    .filter(ModBlockMapping -> ModBlockMapping.getKey().getPath().equals("light_gray_rockrete_slab")) //To remove
                    .forEach(ModBlockMapping -> ModBlockMapping.remap( ModBlocks.GRAY_ROCKRETE_SLAB.get() )); //To replace
            ModItemMappings.stream()
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals("light_gray_rockrete_slab")) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( ModBlocks.GRAY_ROCKRETE_SLAB.get().asItem() ));
        }
    }
}
