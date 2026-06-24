package net.boat.industrialhellscape;

import com.mojang.logging.LogUtils;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.modded_block_entities.ModBlockEntities;
import net.boat.industrialhellscape.entity.ModEntities;
import net.boat.industrialhellscape.item.ModCreativeModeTabs;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;
import org.slf4j.Logger;

import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(IndustrialHellscape.MOD_ID)
public class IndustrialHellscape
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "industrialhellscape";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

//    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
//    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
//    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
//    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));
//
//    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
//    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
//            .alwaysEat().nutrition(1).saturationMod(2f).build())));

//    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
//    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
//            .withTabsBefore(CreativeModeTabs.COMBAT)
//            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
//            .displayItems((parameters, output) -> {
//                output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
//            }).build());

    public IndustrialHellscape()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
//        modEventBus.addListener(this::commonSetup);

        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModSounds.register(modEventBus);

        ModEntities.register(modEventBus); //Sittable Entity (in-world)
        ModBlockEntities.register(modEventBus); //Block Entities handling GUIs and inventory

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

//        // Register the item to a creative tab
//        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonModConfig.SPEC);
    }

//    private void commonSetup(final FMLCommonSetupEvent event)
//    {
//
//    }

//    // Add the example block item to the building blocks tab
//    private void addCreative(BuildCreativeModeTabContentsEvent event)
//    {
//        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
//            event.accept(EXAMPLE_BLOCK_ITEM);
//    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
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
            removeAndReplace(ModBlockMappings, ModItemMappings,"hazard_stripe_yellow", ModBlocks.SMOOTH_GRAY_ROCKRETE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"hazard_stripe_red", ModBlocks.SMOOTH_GRAY_ROCKRETE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"encased_cables", ModBlocks.HORIZONTAL_REINFORCED_VESSELPLATE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"rusty_vesselplate_pillar", ModBlocks.VESSELPLATE_PILLAR.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_horizontal_grate", ModBlocks.HORIZONTAL_GRATE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_horizontal_cutout_grate", ModBlocks.HORIZONTAL_CUTOUT_GRATE.get());

            removeAndReplace(ModBlockMappings, ModItemMappings,"vesselplate_door", ModBlocks.ARMORED_DOOR.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"vesselplate_trapdoor", ModBlocks.VENT_TRAPDOOR.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_vesselplate_door", ModBlocks.BULKHEAD_DOOR.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_vesselplate_trapdoor", ModBlocks.VENT_TRAPDOOR.get());

            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_rockrete", ModBlocks.SMOOTH_GRAY_ROCKRETE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"red_rockrete", ModBlocks.SMOOTH_GRAY_ROCKRETE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"yellow_rockrete", ModBlocks.SMOOTH_GRAY_ROCKRETE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"blue_rockrete", ModBlocks.SMOOTH_GRAY_ROCKRETE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"green_rockrete", ModBlocks.SMOOTH_GRAY_ROCKRETE.get());

            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_rockrete_stairs", ModBlocks.SMOOTH_GRAY_ROCKRETE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"red_rockrete_stairs", ModBlocks.SMOOTH_GRAY_ROCKRETE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"yellow_rockrete_stairs", ModBlocks.SMOOTH_GRAY_ROCKRETE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"blue_rockrete_stairs", ModBlocks.SMOOTH_GRAY_ROCKRETE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"green_rockrete_stairs", ModBlocks.SMOOTH_GRAY_ROCKRETE.get());

            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_rockrete_slab", ModBlocks.SMOOTH_GRAY_ROCKRETE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"red_rockrete_slab", ModBlocks.SMOOTH_GRAY_ROCKRETE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"yellow_rockrete_slab", ModBlocks.SMOOTH_GRAY_ROCKRETE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"blue_rockrete_slab", ModBlocks.SMOOTH_GRAY_ROCKRETE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"green_rockrete_slab", ModBlocks.SMOOTH_GRAY_ROCKRETE.get());

            removeAndReplace(ModBlockMappings, ModItemMappings,"strut", ModBlocks.TRUSS.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"strut_stairs", ModBlocks.TRUSS_STAIRS.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"strut_slab", ModBlocks.TRUSS_SLAB.get());

            removeAndReplace(ModBlockMappings, ModItemMappings,"catwalk_strut", ModBlocks.CATWALK_TRUSS.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"catwalk_strut_stairs", ModBlocks.CATWALK_TRUSS_STAIRS.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"catwalk_strut_slab", ModBlocks.CATWALK_TRUSS_SLAB.get());

            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_strut", ModBlocks.GRAY_TRUSS.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_strut_stairs", ModBlocks.GRAY_TRUSS_STAIRS.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_strut_slab", ModBlocks.GRAY_TRUSS_SLAB.get());

            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_catwalk_strut", ModBlocks.GRAY_CATWALK_TRUSS.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_catwalk_strut_stairs", ModBlocks.GRAY_CATWALK_TRUSS_STAIRS.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_catwalk_strut_slab", ModBlocks.GRAY_CATWALK_TRUSS_SLAB.get());

            removeAndReplace(ModBlockMappings, ModItemMappings,"rusty_strut", ModBlocks.TRUSS.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"rusty_strut_stairs", ModBlocks.TRUSS_STAIRS.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"rusty_strut_slab", ModBlocks.TRUSS_SLAB.get());

            removeAndReplace(ModBlockMappings, ModItemMappings,"rusty_catwalk_strut", ModBlocks.CATWALK_TRUSS.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"rusty_catwalk_strut_stairs", ModBlocks.CATWALK_TRUSS_STAIRS.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"rusty_catwalk_strut_slab", ModBlocks.CATWALK_TRUSS_SLAB.get());

            removeAndReplace(ModBlockMappings, ModItemMappings,"riveted_vesselplate_panel", ModBlocks.RIVETED_VESSELPLATE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"directional_riveted_vesselplate", ModBlocks.HORIZONTAL_REINFORCED_VESSELPLATE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"smooth_vesselplate_tile", ModBlocks.SMOOTH_VESSELPLATE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"vesselplate_sheeting", ModBlocks.SMOOTH_VESSELPLATE.get());

            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_riveted_vesselplate_panel", ModBlocks.GRAY_RIVETED_VESSELPLATE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_directional_riveted_vesselplate", ModBlocks.GRAY_HORIZONTAL_REINFORCED_VESSELPLATE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"smooth_gray_vesselplate_tile", ModBlocks.SMOOTH_GRAY_VESSELPLATE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_vesselplate_sheeting", ModBlocks.SMOOTH_GRAY_VESSELPLATE.get());

            removeAndReplace(ModBlockMappings, ModItemMappings,"rusty_riveted_vesselplate_panel", ModBlocks.RIVETED_VESSELPLATE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"rusty_directional_riveted_vesselplate", ModBlocks.HORIZONTAL_REINFORCED_VESSELPLATE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"smooth_rusty_vesselplate_tile", ModBlocks.SMOOTH_VESSELPLATE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"rusty_vesselplate_sheeting", ModBlocks.SMOOTH_VESSELPLATE.get());

            removeAndReplace(ModBlockMappings, ModItemMappings,"rusty_vesselplate_sheeting_stairs", ModBlocks.SMOOTH_VESSELPLATE_STAIRS.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"rusty_vesselplate_sheeting_slab", ModBlocks.SMOOTH_VESSELPLATE_SLAB.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_vesselplate_sheeting_stairs", ModBlocks.SMOOTH_GRAY_VESSELPLATE_STAIRS.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_vesselplate_sheeting_slab", ModBlocks.SMOOTH_GRAY_VESSELPLATE_SLAB.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"vesselplate_sheeting_stairs", ModBlocks.SMOOTH_VESSELPLATE_STAIRS.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"vesselplate_sheeting_slab", ModBlocks.SMOOTH_VESSELPLATE_SLAB.get());

            removeAndReplace(ModBlockMappings, ModItemMappings,"smooth_rusty_vesselplate_stairs", ModBlocks.SMOOTH_GRAY_VESSELPLATE_STAIRS.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"smooth_rusty_vesselplate_slab", ModBlocks.SMOOTH_GRAY_VESSELPLATE_SLAB.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"rusty_riveted_vesselplate_stairs", ModBlocks.SMOOTH_VESSELPLATE_STAIRS.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"rusty_riveted_vesselplate_slab", ModBlocks.SMOOTH_VESSELPLATE_SLAB.get());

            removeAndReplace(ModBlockMappings, ModItemMappings,"grate", ModBlocks.HORIZONTAL_GRATE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_grate", ModBlocks.HORIZONTAL_GRATE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"see-through_grate", ModBlocks.HORIZONTAL_CUTOUT_GRATE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"gray_see-through_grate", ModBlocks.HORIZONTAL_CUTOUT_GRATE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"rusty_grate", ModBlocks.HORIZONTAL_GRATE.get());
            removeAndReplace(ModBlockMappings, ModItemMappings,"rusty_see-through_grate", ModBlocks.HORIZONTAL_CUTOUT_GRATE.get());

            removeAndReplaceItemOnly(ModBlockMappings, ModItemMappings,"retro_cassette", ModItems.MARQUEE_DISC.get());
        }

        private static void removeAndReplace( List<MissingMappingsEvent.Mapping<Block>> ModBlockMappings, List<MissingMappingsEvent.Mapping<Item>> ModItemMappings,String removedRegistryName, Block blockReplacement) {
            ModBlockMappings.stream() //For blocks in-world
                    .filter(ModBlockMapping -> ModBlockMapping.getKey().getPath().equals(removedRegistryName)) //To remove
                    .forEach(ModBlockMapping -> ModBlockMapping.remap( blockReplacement )); //To replace
            ModItemMappings.stream() //For corresponding block items anywhere
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals(removedRegistryName)) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( blockReplacement.asItem() ));
        }
        private static void removeAndReplaceItemOnly( List<MissingMappingsEvent.Mapping<Block>> ModBlockMappings, List<MissingMappingsEvent.Mapping<Item>> ModItemMappings,String removedRegistryName, Item blockReplacement) {
            ModItemMappings.stream() //For corresponding block items anywhere
                    .filter(ModItemMapping -> ModItemMapping.getKey().getPath().equals(removedRegistryName)) //To remove
                    .forEach(ModItemMapping -> ModItemMapping.remap( blockReplacement.asItem() ));
        }
    }
}
