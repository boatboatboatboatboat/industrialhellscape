package net.boat.industrialhellscape;

import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.special_blocks.StorageBlock.Storage18SlotMenuScreen;
import net.boat.industrialhellscape.block.special_blocks.StorageBlock.Storage27SlotMenuScreen;
import net.boat.industrialhellscape.block.special_blocks.StorageBlock.Storage54SlotMenuScreen;
import net.boat.industrialhellscape.block.special_blocks.StorageBlock.Storage9SlotMenuScreen;
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
import net.minecraft.resources.ResourceLocation;
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
        ModEntities.register(modEventBus); //Not Block Entities (Sitting Entities)
        ModBlockEntities.register(modEventBus); //Block Entities (Inventories)
        ModMenuTypes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
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
                MenuScreens.register(ModMenuTypes.STORAGE_9SLOT_MENU.get(), Storage9SlotMenuScreen::new);
                MenuScreens.register(ModMenuTypes.STORAGE_18SLOT_MENU.get(), Storage18SlotMenuScreen::new);
                MenuScreens.register(ModMenuTypes.STORAGE_27SLOT_MENU.get(), Storage27SlotMenuScreen::new);
                MenuScreens.register(ModMenuTypes.STORAGE_54SLOT_MENU.get(), Storage54SlotMenuScreen::new);
            });
        }
    }



    //DEPRECIATED CONTENT SAFE REMOVAL
    @Mod.EventBusSubscriber(modid = IndustrialHellscape.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class MissingRegistryResolver {
        @SubscribeEvent
        public static void OnMissingRegistryEvent(MissingMappingsEvent event) {

            //Mappings list of Blocks and Items in mod
            List<MissingMappingsEvent.Mapping<Block>> ModBlockMappings = event.getMappings(Registries.BLOCK, IndustrialHellscape.MOD_ID);
            List<MissingMappingsEvent.Mapping<Item>> ModItemMappings = event.getMappings(Registries.ITEM, IndustrialHellscape.MOD_ID);

            //Locate the block to replace (both the block and the item objects)
            //This will only happen when the registration of the block is removed.

            //Depreciate the supposed-to-be-inaccessible Gray, White, and Black Rockrete of version 0.05
            ModBlockMappings.stream()
                    .filter(ModBlockMapping -> ModBlockMapping.getKey().getPath().equals("gray_rockrete")) //To remove
                    .forEach(ModBlockMapping -> ModBlockMapping.remap( ModBlocks.GRAY_ROCKRETE.get() )); //To replace
            ModItemMappings.stream()
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals("gray_rockrete")) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( ModBlocks.GRAY_ROCKRETE.get().asItem() )); //To replace
            ModBlockMappings.stream()
                    .filter(ModBlockMapping -> ModBlockMapping.getKey().getPath().equals("white_rockrete")) //To remove
                    .forEach(ModBlockMapping -> ModBlockMapping.remap( ModBlocks.GRAY_ROCKRETE.get() )); //To replace
            ModItemMappings.stream()
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals("white_rockrete")) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( ModBlocks.GRAY_ROCKRETE.get().asItem() )); //To replace
            ModBlockMappings.stream()
                    .filter(ModBlockMapping -> ModBlockMapping.getKey().getPath().equals("black_rockrete")) //To remove
                    .forEach(ModBlockMapping -> ModBlockMapping.remap( ModBlocks.GRAY_ROCKRETE.get() )); //To replace
            ModItemMappings.stream()
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals("black_rockrete")) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( ModBlocks.GRAY_ROCKRETE.get().asItem() )); //To replace

            //Depreciate the Bunker Wall
            ModBlockMappings.stream()
                    .filter(ModBlockMapping -> ModBlockMapping.getKey().getPath().equals("bunker_wall")) //To remove
                    .forEach(ModBlockMapping -> ModBlockMapping.remap( ModBlocks.GREEN_ROCKRETE_PILLAR.get() )); //To replace
            ModItemMappings.stream()
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals("bunker_wall")) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( ModBlocks.GREEN_ROCKRETE_PILLAR.get().asItem() )); //To replace

            //Depreciate LG Rockrete
            ModBlockMappings.stream()
                    .filter(ModBlockMapping -> ModBlockMapping.getKey().getPath().equals("light_gray_rockrete")) //To remove
                    .forEach(ModBlockMapping -> ModBlockMapping.remap( ModBlocks.GREEN_ROCKRETE.get() )); //To replace
            ModItemMappings.stream()
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals("light_gray_rockrete")) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( ModBlocks.GREEN_ROCKRETE.get().asItem() ));

            //Depreciate LG Rockrete Rebar
            ModBlockMappings.stream()
                    .filter(ModBlockMapping -> ModBlockMapping.getKey().getPath().equals("light_gray_rockrete_rebar")) //To remove
                    .forEach(ModBlockMapping -> ModBlockMapping.remap( ModBlocks.GREEN_ROCKRETE_REBAR.get() )); //To replace
            ModItemMappings.stream()
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals("light_gray_rockrete_rebar")) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( ModBlocks.GREEN_ROCKRETE_REBAR.get().asItem() ));

            //Depreciate LG Rockrete Stairs
            ModBlockMappings.stream()
                    .filter(ModBlockMapping -> ModBlockMapping.getKey().getPath().equals("light_gray_rockrete_stairs")) //To remove
                    .forEach(ModBlockMapping -> ModBlockMapping.remap( ModBlocks.GREEN_ROCKRETE_STAIRS.get() )); //To replace
            ModItemMappings.stream()
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals("light_gray_rockrete_stairs")) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( ModBlocks.GREEN_ROCKRETE_STAIRS.get().asItem() ));

            //Depreciate LG Rockrete Slab
            ModBlockMappings.stream()
                    .filter(ModBlockMapping -> ModBlockMapping.getKey().getPath().equals("light_gray_rockrete_slab")) //To remove
                    .forEach(ModBlockMapping -> ModBlockMapping.remap( ModBlocks.GREEN_ROCKRETE_SLAB.get() )); //To replace
            ModItemMappings.stream()
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals("light_gray_rockrete_slab")) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( ModBlocks.GREEN_ROCKRETE_SLAB.get().asItem() ));

            //Depreciate Vesselplate Grate Block
            ModBlockMappings.stream()
                    .filter(ModBlockMapping -> ModBlockMapping.getKey().getPath().equals("vesselplate_grate_block")) //To remove
                    .forEach(ModBlockMapping -> ModBlockMapping.remap( ModBlocks.GRATE.get() )); //To replace
            ModItemMappings.stream()
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals("vesselplate_grate_block")) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( ModBlocks.GRATE.get().asItem() ));

            //Depreciate "Vesselplate Grates" to just Grates
            ModBlockMappings.stream()
                    .filter(ModBlockMapping -> ModBlockMapping.getKey().getPath().equals("vesselplate_grate")) //To remove
                    .forEach(ModBlockMapping -> ModBlockMapping.remap( ModBlocks.GRATE.get() )); //To replace
            ModItemMappings.stream()
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals("vesselplate_grate")) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( ModBlocks.GRATE.get().asItem() ));
            ModBlockMappings.stream()
                    .filter(ModBlockMapping -> ModBlockMapping.getKey().getPath().equals("gray_vesselplate_grate")) //To remove
                    .forEach(ModBlockMapping -> ModBlockMapping.remap( ModBlocks.GRAY_GRATE.get() )); //To replace
            ModItemMappings.stream()
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals("gray_vesselplate_grate")) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( ModBlocks.GRAY_GRATE.get().asItem() ));
            ModBlockMappings.stream()
                    .filter(ModBlockMapping -> ModBlockMapping.getKey().getPath().equals("rusty_vesselplate_grate")) //To remove
                    .forEach(ModBlockMapping -> ModBlockMapping.remap( ModBlocks.RUSTY_GRATE.get() )); //To replace
            ModItemMappings.stream()
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals("rusty_vesselplate_grate")) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( ModBlocks.RUSTY_GRATE.get().asItem() ));
        }
    }
}
