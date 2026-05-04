package net.boat.industrialhellscape;

import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.modded_block_entities.ModBlockEntities;
import net.boat.industrialhellscape.block.modded_block_entities.ModEntities;
import net.boat.industrialhellscape.block.modded_block_entities.SittableEntity.SittableEntityRenderer;
import net.boat.industrialhellscape.screen.ModMenuTypes;
import net.boat.industrialhellscape.screen.RecyclerScreen;
import net.boat.industrialhellscape.sound.ModSounds;
import net.boat.industrialhellscape.item.ModCreativeTab;
import net.boat.industrialhellscape.item.ModItems;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
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

        ModCreativeTab.register(modEventBus); //Creative Mode tab for InHell
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus); //Not Block Entities (Sittable Entities)
        ModBlockEntities.register(modEventBus); //Block Entities (Inventories)
        ModMenuTypes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
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
            MenuScreens.register(ModMenuTypes.RECYCLER_MENU.get(), RecyclerScreen::new); //Development Content
        }
    }

    //DEPRECATED CONTENT SAFE REMOVAL
    @Mod.EventBusSubscriber(modid = IndustrialHellscape.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class MissingRegistryResolver {
        @SubscribeEvent
        public static void OnMissingRegistryEvent(MissingMappingsEvent event) {

            //Mappings list of Blocks and Items in mod
            List<MissingMappingsEvent.Mapping<Block>> ModBlockMappings = event.getMappings(Registries.BLOCK, IndustrialHellscape.MOD_ID);
            List<MissingMappingsEvent.Mapping<Item>> ModItemMappings = event.getMappings(Registries.ITEM, IndustrialHellscape.MOD_ID);

            //Locate the block to replace (both the block and the item objects)
            //This will only happen when the registration of the block is removed.
            removeAndReplace(ModBlockMappings, ModItemMappings,"rusty_catwalk_strut", ModBlocks.GRAY_HORIZONTAL_CUTOUT_GRATE.get());
        }

        private static void removeAndReplace( List<MissingMappingsEvent.Mapping<Block>> ModBlockMappings, List<MissingMappingsEvent.Mapping<Item>> ModItemMappings,String removedRegistryName, Block blockReplacement) {
            ModBlockMappings.stream() //For blocks in-world
                    .filter(ModBlockMapping -> ModBlockMapping.getKey().getPath().equals(removedRegistryName)) //To remove
                    .forEach(ModBlockMapping -> ModBlockMapping.remap( blockReplacement )); //To replace
            ModItemMappings.stream() //For corresponding block items anywhere
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals(removedRegistryName)) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( blockReplacement.asItem() ));
        }
    }
}
