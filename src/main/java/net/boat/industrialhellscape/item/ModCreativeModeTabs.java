package net.boat.industrialhellscape.item;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IndustrialHellscape.MOD_ID);

    public static final Supplier<CreativeModeTab> INDUSTRIAL_HELLSCAPE_ITEMS_TAB = CREATIVE_MODE_TAB.register("industrial_hellscape_items_tab",
            () -> CreativeModeTab.builder()
                    .icon( () -> new ItemStack(ModItems.INHELL_HAVEN_DEVICE.get()))
                    .title(Component.translatable("gui.industrialhellscape.creative_tab"))
                    .displayItems(((itemDisplayParameters, output) -> {

                        //ITEMS
                        output.accept(ModItems.INHELL_HAVEN_DEVICE.get());
                        //output.accept(ModItems.MARQUEE_DISC.get());

                        //BASE BLOCKS
                        output.accept(ModBlocks.METALWORKS.get());
                        output.accept(ModBlocks.PIPEWORKS.get());

                        output.accept(ModBlocks.IHEA_FURNITURE_KIT.get());
                        output.accept(ModBlocks.SAFETY_FURNISHINGS.get());
                        output.accept(ModBlocks.HYGIENE_FURNISHINGS.get());
                        output.accept(ModBlocks.INDUSTRIAL_FURNISHINGS.get());
                        output.accept(ModBlocks.TECHNOLOGY_FURNISHINGS.get());
                        output.accept(ModBlocks.AMENITY_FURNISHINGS.get());

                        //DUCT BLOCKS
                        output.accept(ModBlocks.DUCT.get());
                        output.accept(ModBlocks.RUSTY_DUCT.get());
                        output.accept(ModBlocks.DUCT_VENT.get());
                        output.accept(ModBlocks.RUSTY_DUCT_VENT.get());

                        //GRATE BLOCKS
                        output.accept(ModBlocks.HORIZONTAL_GRATE.get());
                        output.accept(ModBlocks.HORIZONTAL_CUTOUT_GRATE.get());
//                        output.accept(ModBlocks.GRAY_HORIZONTAL_GRATE.get());
//                        output.accept(ModBlocks.GRAY_HORIZONTAL_CUTOUT_GRATE.get());

                        //RAILING BLOCKS
                        output.accept(ModBlocks.YELLOW_RAILING.get());
                        output.accept(ModBlocks.YELLOW_STAIR_RAILING.get());
                        output.accept(ModBlocks.GRAY_RAILING.get());
                        output.accept(ModBlocks.GRAY_STAIR_RAILING.get());
                        output.accept(ModBlocks.BLACK_RAILING.get());
                        output.accept(ModBlocks.BLACK_STAIR_RAILING.get());
                        output.accept(ModBlocks.RUSTY_RAILING.get());
                        output.accept(ModBlocks.RUSTY_STAIR_RAILING.get());

                        //VESSELPLATE
                        output.accept(ModBlocks.RIVETED_VESSELPLATE.get());
                        output.accept(ModBlocks.HORIZONTAL_VESSELPLATE.get());
                        output.accept(ModBlocks.SMOOTH_VESSELPLATE.get());
                        output.accept(ModBlocks.VESSELPLATE_PILLAR.get());
                        output.accept(ModBlocks.RIVETED_VESSELPLATE_SLAB.get());
                        output.accept(ModBlocks.RIVETED_VESSELPLATE_STAIRS.get());
                        output.accept(ModBlocks.SMOOTH_VESSELPLATE_STAIRS.get());
                        output.accept(ModBlocks.SMOOTH_VESSELPLATE_SLAB.get());
                        output.accept(ModBlocks.HORIZONTAL_REINFORCED_VESSELPLATE.get());

                        output.accept(ModBlocks.GRAY_RIVETED_VESSELPLATE.get());
                        output.accept(ModBlocks.GRAY_HORIZONTAL_REINFORCED_VESSELPLATE.get());
                        output.accept(ModBlocks.SMOOTH_GRAY_VESSELPLATE.get());
                        output.accept(ModBlocks.GRAY_VESSELPLATE_PILLAR.get());
                        output.accept(ModBlocks.GRAY_RIVETED_VESSELPLATE_SLAB.get());
                        output.accept(ModBlocks.GRAY_RIVETED_VESSELPLATE_STAIRS.get());
                        output.accept(ModBlocks.SMOOTH_GRAY_VESSELPLATE_STAIRS.get());
                        output.accept(ModBlocks.SMOOTH_GRAY_VESSELPLATE_SLAB.get());
                        output.accept(ModBlocks.GRAY_HORIZONTAL_VESSELPLATE.get());

                        //TRUSS BLOCKS
                        output.accept(ModBlocks.TRUSS.get());
                        output.accept(ModBlocks.CATWALK_TRUSS.get());
                        output.accept(ModBlocks.CATWALK_TRUSS_STAIRS.get());
                        output.accept(ModBlocks.CATWALK_TRUSS_SLAB.get());
                        output.accept(ModBlocks.TRUSS_STAIRS.get());
                        output.accept(ModBlocks.TRUSS_SLAB.get());
                        output.accept(ModBlocks.GRAY_TRUSS.get());
                        output.accept(ModBlocks.GRAY_CATWALK_TRUSS.get());
                        output.accept(ModBlocks.GRAY_CATWALK_TRUSS_STAIRS.get());
                        output.accept(ModBlocks.GRAY_CATWALK_TRUSS_SLAB.get());
                        output.accept(ModBlocks.GRAY_TRUSS_STAIRS.get());
                        output.accept(ModBlocks.GRAY_TRUSS_SLAB.get());

                        //VESSELGLASS
                        output.accept(ModBlocks.REINFORCED_VESSELGLASS.get());
                        output.accept(ModBlocks.VESSELGLASS.get());
                        output.accept(ModBlocks.GRAY_REINFORCED_VESSELGLASS.get());
                        output.accept(ModBlocks.GRAY_VESSELGLASS.get());

                        //ROCKRETE
                        output.accept(ModBlocks.GRAY_ROCKRETE.get());
                        output.accept(ModBlocks.ROUGH_GRAY_ROCKRETE.get());
                        output.accept(ModBlocks.GRAY_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.GRAY_ROCKRETE_SLAB.get());
                        output.accept(ModBlocks.ROUGH_GRAY_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.ROUGH_GRAY_ROCKRETE_SLAB.get());
                        output.accept(ModBlocks.GRAY_ROCKRETE_PILLAR.get());

                        output.accept(ModBlocks.GREEN_ROCKRETE.get());
                        output.accept(ModBlocks.ROUGH_GREEN_ROCKRETE.get());
                        output.accept(ModBlocks.GREEN_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.GREEN_ROCKRETE_SLAB.get());
                        output.accept(ModBlocks.ROUGH_GREEN_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.ROUGH_GREEN_ROCKRETE_SLAB.get());
                        output.accept(ModBlocks.GREEN_ROCKRETE_PILLAR.get());

                        output.accept(ModBlocks.YELLOW_ROCKRETE.get());
                        output.accept(ModBlocks.ROUGH_YELLOW_ROCKRETE.get());
                        output.accept(ModBlocks.YELLOW_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.YELLOW_ROCKRETE_SLAB.get());
                        output.accept(ModBlocks.ROUGH_YELLOW_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.ROUGH_YELLOW_ROCKRETE_SLAB.get());
                        output.accept(ModBlocks.YELLOW_ROCKRETE_PILLAR.get());

                        output.accept(ModBlocks.BLUE_ROCKRETE.get());
                        output.accept(ModBlocks.ROUGH_BLUE_ROCKRETE.get());
                        output.accept(ModBlocks.BLUE_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.BLUE_ROCKRETE_SLAB.get());
                        output.accept(ModBlocks.ROUGH_BLUE_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.ROUGH_BLUE_ROCKRETE_SLAB.get());
                        output.accept(ModBlocks.BLUE_ROCKRETE_PILLAR.get());

                        output.accept(ModBlocks.RED_ROCKRETE.get());
                        output.accept(ModBlocks.ROUGH_RED_ROCKRETE.get());
                        output.accept(ModBlocks.RED_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.RED_ROCKRETE_SLAB.get());
                        output.accept(ModBlocks.ROUGH_RED_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.ROUGH_RED_ROCKRETE_SLAB.get());
                        output.accept(ModBlocks.RED_ROCKRETE_PILLAR.get());

                        output.accept(ModBlocks.GRIMY_RESTROOM_TILE.get());

                        //BRACKETS
                        output.accept(ModBlocks.GRAY_BOLTED_BRACKET.get());
                        output.accept(ModBlocks.SMALL_GRAY_BOLTED_BRACKET.get());
                        output.accept(ModBlocks.BLACK_BOLTED_BRACKET.get());
                        output.accept(ModBlocks.SMALL_BLACK_BOLTED_BRACKET.get());
                        output.accept(ModBlocks.RUSTY_BOLTED_BRACKET.get());
                        output.accept(ModBlocks.SMALL_RUSTY_BOLTED_BRACKET.get());

                        //DOOR, TRAPDOORS
                        //output.accept(ModBlocks.VESSELPLATE_TRAPDOOR.get());
                        output.accept(ModBlocks.VENT_TRAPDOOR.get());
                        output.accept(ModBlocks.ARMORED_DOOR.get());
                        output.accept(ModBlocks.STAMPED_METAL_DOOR.get());
                        output.accept(ModBlocks.BULKHEAD_DOOR.get());

                        //PIPEWORKS BLOCKS
                        output.accept(ModBlocks.PIPEWORKS.get());

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

                        //Furniture
                        output.accept(ModBlocks.WHITE_WALL_MEDKIT.get());
                        output.accept(ModBlocks.RED_WALL_MEDKIT.get());
                        output.accept(ModBlocks.FIRE_EXTINGUISHER.get());
                        output.accept(ModBlocks.SMOKE_ALARM.get());
                        output.accept(ModBlocks.OPERATING_TABLE.get());
                        output.accept(ModBlocks.IV_DRIPSTAND.get());
                        output.accept(ModBlocks.VITALS_MONITOR.get());
                        output.accept(ModBlocks.MEDICAL_BED.get());

                        output.accept(ModBlocks.TOILET.get());
                        output.accept(ModBlocks.SINK.get());
                        output.accept(ModBlocks.URINAL.get());

                        output.accept(ModBlocks.RETRO_COMPUTER.get());
                        output.accept(ModBlocks.RETRO_COMPUTER_2.get());
                        output.accept(ModBlocks.MONITOR_AND_KEYBOARD.get());
                        output.accept(ModBlocks.DESKTOP_TOWER.get());
                        output.accept(ModBlocks.CASSETTE_PLAYER.get());

                        output.accept(ModBlocks.WORK_LIGHT_STAND.get());
                        output.accept(ModBlocks.FLOOR_WORK_LIGHT.get());
                        output.accept(ModBlocks.LOCKER_BOX.get());
                        output.accept(ModBlocks.LARGE_LOCKER.get());
                        output.accept(ModBlocks.FUEL_DRUM.get());
                        output.accept(ModBlocks.CCTV_CAMERA.get());
                        output.accept(ModBlocks.CAGE_LAMP.get());

                        output.accept(ModBlocks.DESK.get());
                        output.accept(ModBlocks.DESK_DRAWER.get());
                        output.accept(ModBlocks.METAL_DESK.get());
                        output.accept(ModBlocks.METAL_DESK_DRAWER.get());
                        output.accept(ModBlocks.METAL_DESK_DRAWER_2.get());
                        output.accept(ModBlocks.OFFICE_DESK.get());
                        output.accept(ModBlocks.OFFICE_DESK_DRAWER.get());
                        output.accept(ModBlocks.OFFICE_CHAIR.get());
                        output.accept(ModBlocks.BLACK_OFFICE_CHAIR.get());
                        output.accept(ModBlocks.FOLDING_CHAIR.get());
                    }))

                    //.withSearchBar()
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
