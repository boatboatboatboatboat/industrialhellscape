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

                        //DEV
                        output.accept(ModBlocks.LOCKER_BOX.get());
                        output.accept(ModBlocks.FUEL_DRUM.get());

                        //ITEMS FOR CREATIVE TAB
                        output.accept(ModItems.FLOPPY_DISK.get());
                        output.accept(ModItems.FLOPPY_DISKETTE.get());
                        output.accept(ModItems.INHELL_HAVEN_DEVICE.get());
                        output.accept(ModItems.VAPORWAVE_CASSETTE.get());
                        output.accept(ModItems.JOB_APPLICATION.get());

                        //EXPERIMENTAL BLOCKS FOR CREATIVE TAB
                        output.accept(ModBlocks.HANDRAIL.get());

                        //IRONLIKE BLOCKS FOR CREATIVE TAB
                        output.accept(ModBlocks.VESSELPLATE.get());
                        output.accept(ModBlocks.RIVETED_VESSELPLATE_PANEL.get());
                        //output.accept(ModBlocks.VESSELPLATE_GRATE_BLOCK.get()); //Depreciated
                        output.accept(ModBlocks.GRATE.get());
                        output.accept(ModBlocks.SEETHROUGH_GRATE.get());
                        //output.accept(ModBlocks.SEETHROUGH_GRATE_PANE.get()); //Experimental
                        output.accept(ModBlocks.RUSTY_GRATE.get());
                        output.accept(ModBlocks.VERTICAL_RIVETED_VESSELPLATE.get());
                        output.accept(ModBlocks.HORIZONTAL_RIVETED_VESSELPLATE.get());
                        output.accept(ModBlocks.SMOOTH_VESSELPLATE_TILE.get());
                        output.accept(ModBlocks.VESSELPLATE_PILLAR.get());

                        output.accept(ModBlocks.REINFORCED_VESSELGLASS.get());
                        output.accept(ModBlocks.VESSELGLASS.get());
                        output.accept(ModBlocks.GRAY_REINFORCED_VESSELGLASS.get());
                        output.accept(ModBlocks.GRAY_VESSELGLASS.get());

                        output.accept(ModBlocks.GRAY_VESSELPLATE.get());
                        output.accept(ModBlocks.GRAY_RIVETED_VESSELPLATE_PANEL.get());
                        output.accept(ModBlocks.GRAY_VERTICAL_RIVETED_VESSELPLATE.get());
                        output.accept(ModBlocks.GRAY_HORIZONTAL_RIVETED_VESSELPLATE.get());
                        output.accept(ModBlocks.GRAY_GRATE.get());
                        output.accept(ModBlocks.GRAY_SEETHROUGH_GRATE.get());
                        output.accept(ModBlocks.GRAY_SEETHROUGH_GRATE_PANE.get()); //Experimental
                        output.accept(ModBlocks.SMOOTH_GRAY_VESSELPLATE_TILE.get());
                        output.accept(ModBlocks.GRAY_VESSELPLATE_PILLAR.get());

                        //STONELIKE BLOCKS FOR CREATIVE TAB
                        output.accept(ModBlocks.GRAY_ROCKRETE.get());
                        output.accept(ModBlocks.GRAY_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.GRAY_ROCKRETE_SLAB.get());
                        output.accept(ModBlocks.GRAY_ROCKRETE_PILLAR.get());

                        output.accept(ModBlocks.GREEN_ROCKRETE.get());
                        output.accept(ModBlocks.GREEN_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.GREEN_ROCKRETE_SLAB.get());
                        output.accept(ModBlocks.GREEN_ROCKRETE_PILLAR.get());

                        output.accept(ModBlocks.YELLOW_ROCKRETE.get());
                        output.accept(ModBlocks.YELLOW_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.YELLOW_ROCKRETE_SLAB.get());
                        output.accept(ModBlocks.YELLOW_ROCKRETE_PILLAR.get());

                        output.accept(ModBlocks.BLUE_ROCKRETE.get());
                        output.accept(ModBlocks.BLUE_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.BLUE_ROCKRETE_SLAB.get());
                        output.accept(ModBlocks.BLUE_ROCKRETE_PILLAR.get());
                        
                        output.accept(ModBlocks.HAZARD_STRIPE_YELLOW.get());
                        output.accept(ModBlocks.HAZARD_STRIPE_RED.get());
                        output.accept(ModBlocks.GRIMY_RESTROOM_TILE.get());

                        //SPECIAL BLOCKS FOR CREATIVE TAB
                        output.accept(ModBlocks.COPPER_PIPE_CONDUIT.get());
                        output.accept(ModBlocks.COPPER_PIPE_CONDUIT_PLANAR_CORNER.get());
                        output.accept(ModBlocks.COPPER_PIPE_CONDUIT_INNER_CORNER.get());
                        output.accept(ModBlocks.COPPER_PIPE_CONDUIT_OUTER_CORNER.get());

                        output.accept(ModBlocks.BRASS_PIPE_CONDUIT.get());
                        output.accept(ModBlocks.BRASS_PIPE_CONDUIT_PLANAR_CORNER.get());
                        output.accept(ModBlocks.BRASS_PIPE_CONDUIT_INNER_CORNER.get());
                        output.accept(ModBlocks.BRASS_PIPE_CONDUIT_OUTER_CORNER.get());

                        output.accept(ModBlocks.GRAY_PIPE_CONDUIT.get());
                        output.accept(ModBlocks.GRAY_PIPE_CONDUIT_PLANAR_CORNER.get());
                        output.accept(ModBlocks.GRAY_PIPE_CONDUIT_INNER_CORNER.get());
                        output.accept(ModBlocks.GRAY_PIPE_CONDUIT_OUTER_CORNER.get());

                        output.accept(ModBlocks.GRAY_BOLTED_BRACKET.get());
                        output.accept(ModBlocks.BLACK_BOLTED_BRACKET.get());

                        output.accept(ModBlocks.DESK.get());
                        output.accept(ModBlocks.DESK_DRAWER.get());
                        output.accept(ModBlocks.METAL_DESK.get());
                        output.accept(ModBlocks.METAL_DESK_DRAWER.get());
                        output.accept(ModBlocks.METAL_DESK_DRAWER_2.get());

                        output.accept(ModBlocks.TOILET.get());
                        output.accept(ModBlocks.SINK.get());

                        output.accept(ModBlocks.RETRO_COMPUTER.get());
                        output.accept(ModBlocks.CASSETTE_PLAYER.get());

                        output.accept(ModBlocks.YELLOW_TRIPOD.get());
                        output.accept(ModBlocks.WORK_LIGHT_MOUNT.get());
                        output.accept(ModBlocks.FLOOR_WORK_LIGHT.get());
                        //output.accept(ModBlocks.LOCKER_BOX.get()); //Experimental
                        //output.accept(ModBlocks.LARGE_LOCKER.get()); //Experimental

                        output.accept(ModBlocks.PIPEWORKS.get());

                        output.accept(ModBlocks.WHITE_WALL_MEDKIT.get());
                        output.accept(ModBlocks.RED_WALL_MEDKIT.get());
                        output.accept(ModBlocks.IHEA_FURNITURE_KIT.get());
                        output.accept(ModBlocks.SAFETY_FURNISHINGS.get());
                        output.accept(ModBlocks.HYGIENE_FURNISHINGS.get());
                        output.accept(ModBlocks.INDUSTRIAL_FURNISHINGS.get());
                        output.accept(ModBlocks.TECHNOLOGY_FURNISHINGS.get());
                        output.accept(ModBlocks.AMENITY_FURNISHINGS.get());

                        //CTM BLOCKS FOR CREATIVE TAB

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

                        output.accept(ModBlocks.ENCASED_CABLES.get());

                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
