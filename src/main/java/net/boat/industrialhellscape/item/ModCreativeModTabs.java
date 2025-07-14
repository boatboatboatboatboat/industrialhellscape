package net.boat.industrialhellscape.item;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IndustrialHellscape.MOD_ID);

    public static final RegistryObject<CreativeModeTab> INHELL_TAB = CREATIVE_MODE_TABS.register("inhell_tab", //Defines Creative Tab name
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.INHELL_HAVEN_DEVICE.get())) //Defines Tab Icon texture
                    .title(Component.translatable("creativetab.inhell_tab"))
                    .displayItems((itemDisplayParameters, output) -> {

                        //ITEMS FOR CREATIVE TAB
                        output.accept(ModItems.MALEVOLENT_MULTITOOL.get());
                        output.accept(ModItems.FLOPPY_DISK.get());
                        output.accept(ModItems.FLOPPY_DISKETTE.get());
                        output.accept(ModItems.INHELL_HAVEN_DEVICE.get());
                        //output.accept(ModItems.METAL_DETECTOR.get());
                        output.accept(ModItems.VAPORWAVE_CASSETTE.get());

                        //IRONLIKE BLOCKS FOR CREATIVE TAB
                        output.accept(ModBlocks.VESSELPLATE.get());
                        output.accept(ModBlocks.VESSELPLATE_GRATE_BLOCK.get());
                        output.accept(ModBlocks.VESSELPLATE_GRATE.get());
                        output.accept(ModBlocks.RUSTY_VESSELPLATE_GRATE.get());
                        output.accept(ModBlocks.VERTICAL_RIVETED_VESSELPLATE.get());
                        output.accept(ModBlocks.HORIZONTAL_RIVETED_VESSELPLATE.get());
                        output.accept(ModBlocks.REINFORCED_VESSELGLASS.get());
                        output.accept(ModBlocks.VESSELGLASS.get());
                        output.accept(ModBlocks.SMOOTH_VESSELPLATE.get());
                        output.accept(ModBlocks.SMOOTH_VESSELPLATE_TILE.get());
                        output.accept(ModBlocks.VESSELPLATE_PILLAR.get());

                        //STONELIKE BLOCKS FOR CREATIVE TAB
                        output.accept(ModBlocks.LIGHT_GRAY_ROCKRETE.get());
                        output.accept(ModBlocks.LIGHT_GRAY_ROCKRETE_REBAR.get());
                        output.accept(ModBlocks.LIGHT_GRAY_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.LIGHT_GRAY_ROCKRETE_SLAB.get());

                        output.accept(ModBlocks.GRAY_ROCKRETE.get());
                        output.accept(ModBlocks.WHITE_ROCKRETE.get());
                        output.accept(ModBlocks.BLACK_ROCKRETE.get());
                        output.accept(ModBlocks.HAZARD_STRIPE_YELLOW.get());
                        output.accept(ModBlocks.HAZARD_STRIPE_RED.get());
                        output.accept(ModBlocks.GRIMY_RESTROOM_TILE.get());

                        //SPECIAL BLOCKS FOR CREATIVE TAB
                        //output.accept(ModBlocks.SOUND_BLOCK.get());
                        output.accept(ModBlocks.DESK.get());
                        output.accept(ModBlocks.DESK_DRAWER.get());
                        output.accept(ModBlocks.METAL_DESK.get());
                        output.accept(ModBlocks.METAL_DESK_DRAWER.get());
                        output.accept(ModBlocks.METAL_DESK_DRAWER_2.get());
                        output.accept(ModBlocks.TOILET.get());
                        //output.accept(ModBlocks.SINK.get());
                        output.accept(ModBlocks.WHITE_WALL_MEDKIT.get());
                        output.accept(ModBlocks.RED_WALL_MEDKIT.get());
                        output.accept(ModBlocks.IHEA_FURNITURE_KIT.get());
                        output.accept(ModBlocks.SAFETY_FURNISHINGS.get());
                        output.accept(ModBlocks.HYGIENE_FURNISHINGS.get());
                        output.accept(ModBlocks.INDUSTRIAL_FURNISHINGS.get());
                        output.accept(ModBlocks.TECHNOLOGY_FURNISHINGS.get());
                        output.accept(ModBlocks.AMENITY_FURNISHINGS.get());

                        //CTM BLOCKS FOR CREATIVE TAB
                        output.accept(ModBlocks.BUNKER_WALL.get());

                        output.accept(ModBlocks.STRUT.get());
                        output.accept(ModBlocks.CATWALK_STRUT.get());
                        output.accept(ModBlocks.CATWALK_STRUT_STAIRS.get());
                        output.accept(ModBlocks.CATWALK_STRUT_SLAB.get());
                        output.accept(ModBlocks.STRUT_STAIRS.get());
                        output.accept(ModBlocks.STRUT_SLAB.get());
                        output.accept(ModBlocks.GRAY_STRUT.get());
                        output.accept(ModBlocks.GRAY_CATWALK_STRUT.get());
                        output.accept(ModBlocks.GRAY_CATWALK_STRUT_STAIRS.get());
                        output.accept(ModBlocks.GRAY_CATWALK_STRUT_SLAB.get());
                        output.accept(ModBlocks.GRAY_STRUT_STAIRS.get());
                        output.accept(ModBlocks.GRAY_STRUT_SLAB.get());

                        output.accept(ModBlocks.HORIZONTAL_ENCASED_CABLES.get());
                        output.accept(ModBlocks.VERTICAL_ENCASED_CABLES.get());

                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
