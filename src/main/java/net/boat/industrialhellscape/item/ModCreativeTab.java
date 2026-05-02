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

//THIS JAVA CLASS HANDLES THE DISPLAY OF ITEMS IN THE IN-GAME CREATIVE MENU TAB

//IF YOU GET AN ERROR/CRASH THAT POINTS TO THIS CLASS. IT IS BECAUSE YOU HAD A BLOCK AND ONLY REGISTERED THE BLOCK ITEM, NOT BOTH THE BLOCK AND THE ITEM

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IndustrialHellscape.MOD_ID);

    public static final RegistryObject<CreativeModeTab> INDUSTRIALHELLSCAPE_CREATIVE_TAB = CREATIVE_MODE_TABS.register("industrialhellscape_creative_tab", //Defines Creative Tab name
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.INHELL_HAVEN_DEVICE.get())) //Defines Tab Icon model
                    .title(Component.translatable("gui.industrialhellscape.creative_tab"))
                    .displayItems((itemDisplayParameters, output) -> {

                        //DEBUG ITEMS
                        //output.accept(ModBlocks.POSTER_1.get());
                        output.accept(ModBlocks.OFFICE_CHAIR.get());
                        output.accept(ModBlocks.BLACK_OFFICE_CHAIR.get());
                        output.accept(ModBlocks.FOLDING_CHAIR.get());
                        output.accept(ModBlocks.OPERATING_TABLE.get());
                        output.accept(ModBlocks.IV_DRIPSTAND.get());
                        output.accept(ModBlocks.VITALS_MONITOR.get());
                        output.accept(ModBlocks.VESSELPLATE_DOOR.get());
                        output.accept(ModBlocks.VESSELPLATE_TRAPDOOR.get());
                        output.accept(ModBlocks.MEDICAL_BED.get());
                        output.accept(ModBlocks.GRAY_VESSELPLATE_DOOR.get());
                        output.accept(ModBlocks.GRAY_VESSELPLATE_TRAPDOOR.get());

                        //JOKE ITEMS
                        //output.accept(ModItems.JOB_APPLICATION.get());
                        //output.accept(ModItems.ASPIC.get());
                        //output.accept(ModBlocks.BODY_PILLOW.get());

                        //ITEMS FOR CREATIVE TAB
                        //output.accept(ModItems.FLOPPY_DISK.get());
                        //output.accept(ModItems.FLOPPY_DISKETTE.get());
                        output.accept(ModItems.INHELL_HAVEN_DEVICE.get());
                        output.accept(ModItems.VAPORWAVE_CASSETTE.get());
                        //output.accept(ModItems.RETRO_CASSETTE.get());

                        //IRONLIKE BLOCKS FOR CREATIVE TAB
                        output.accept(ModBlocks.RIVETED_VESSELPLATE.get());
                        output.accept(ModBlocks.HORIZONTAL_GRATE.get());
                        output.accept(ModBlocks.HORIZONTAL_CUTOUT_GRATE.get());
                        output.accept(ModBlocks.RUSTY_GRATE.get());
                        output.accept(ModBlocks.RUSTY_SEETHROUGH_GRATE.get());
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

                        output.accept(ModBlocks.GRAY_HORIZONTAL_GRATE.get());
                        output.accept(ModBlocks.GRAY_HORIZONTAL_CUTOUT_GRATE.get());

                        output.accept(ModBlocks.SMOOTH_GRAY_VESSELPLATE.get());
                        output.accept(ModBlocks.GRAY_VESSELPLATE_PILLAR.get());
                        output.accept(ModBlocks.GRAY_RIVETED_VESSELPLATE_SLAB.get());
                        output.accept(ModBlocks.GRAY_RIVETED_VESSELPLATE_STAIRS.get());
                        output.accept(ModBlocks.SMOOTH_GRAY_VESSELPLATE_STAIRS.get());
                        output.accept(ModBlocks.SMOOTH_GRAY_VESSELPLATE_SLAB.get());
                        output.accept(ModBlocks.GRAY_HORIZONTAL_VESSELPLATE.get());

                        output.accept(ModBlocks.RUSTY_RIVETED_VESSELPLATE_PANEL.get());
                        output.accept(ModBlocks.RUSTY_RIVETED_VESSELPLATE_STAIRS.get());
                        output.accept(ModBlocks.RUSTY_RIVETED_VESSELPLATE_SLAB.get());
                        output.accept(ModBlocks.RUSTY_VESSELPLATE_SHEETING.get());
                        output.accept(ModBlocks.RUSTY_VESSELPLATE_SHEETING_STAIRS.get());
                        output.accept(ModBlocks.RUSTY_VESSELPLATE_SHEETING_SLAB.get());
                        output.accept(ModBlocks.RUSTY_VESSELPLATE_PILLAR.get());
                        output.accept(ModBlocks.SMOOTH_RUSTY_VESSELPLATE_TILE.get());
                        output.accept(ModBlocks.SMOOTH_RUSTY_VESSELPLATE_STAIRS.get());
                        output.accept(ModBlocks.SMOOTH_RUSTY_VESSELPLATE_SLAB.get());

                        output.accept(ModBlocks.RUSTY_DIRECTIONAL_RIVETED_VESSELPLATE.get());

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
                        output.accept(ModBlocks.RUSTY_TRUSS.get());
                        output.accept(ModBlocks.RUSTY_TRUSS_STAIRS.get());
                        output.accept(ModBlocks.RUSTY_TRUSS_SLAB.get());
                        output.accept(ModBlocks.RUSTY_CATWALK_TRUSS.get());
                        output.accept(ModBlocks.RUSTY_CATWALK_TRUSS_STAIRS.get());
                        output.accept(ModBlocks.RUSTY_CATWALK_TRUSS_SLAB.get());

                        output.accept(ModBlocks.ENCASED_CABLES.get());

                        output.accept(ModBlocks.REINFORCED_VESSELGLASS.get());
                        output.accept(ModBlocks.VESSELGLASS.get());
                        output.accept(ModBlocks.GRAY_REINFORCED_VESSELGLASS.get());
                        output.accept(ModBlocks.GRAY_VESSELGLASS.get());

                        //STONELIKE BLOCKS FOR CREATIVE TAB
                        output.accept(ModBlocks.GRAY_ROCKRETE.get());
                        output.accept(ModBlocks.ROUGH_GRAY_ROCKRETE.get());
                        output.accept(ModBlocks.GRAY_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.GRAY_ROCKRETE_SLAB.get());
                        output.accept(ModBlocks.GRAY_ROCKRETE_PILLAR.get());

                        output.accept(ModBlocks.GREEN_ROCKRETE.get());
                        output.accept(ModBlocks.ROUGH_GREEN_ROCKRETE.get());
                        output.accept(ModBlocks.GREEN_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.GREEN_ROCKRETE_SLAB.get());
                        output.accept(ModBlocks.GREEN_ROCKRETE_PILLAR.get());

                        output.accept(ModBlocks.YELLOW_ROCKRETE.get());
                        output.accept(ModBlocks.ROUGH_YELLOW_ROCKRETE.get());
                        output.accept(ModBlocks.YELLOW_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.YELLOW_ROCKRETE_SLAB.get());
                        output.accept(ModBlocks.YELLOW_ROCKRETE_PILLAR.get());

                        output.accept(ModBlocks.BLUE_ROCKRETE.get());
                        output.accept(ModBlocks.ROUGH_BLUE_ROCKRETE.get());
                        output.accept(ModBlocks.BLUE_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.BLUE_ROCKRETE_SLAB.get());
                        output.accept(ModBlocks.BLUE_ROCKRETE_PILLAR.get());

                        output.accept(ModBlocks.RED_ROCKRETE.get());
                        output.accept(ModBlocks.ROUGH_RED_ROCKRETE.get());
                        output.accept(ModBlocks.RED_ROCKRETE_STAIRS.get());
                        output.accept(ModBlocks.RED_ROCKRETE_SLAB.get());
                        output.accept(ModBlocks.RED_ROCKRETE_PILLAR.get());
                        
                        output.accept(ModBlocks.HAZARD_STRIPE_YELLOW.get());
                        output.accept(ModBlocks.HAZARD_STRIPE_RED.get());
                        output.accept(ModBlocks.GRIMY_RESTROOM_TILE.get());

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

                        //METALWORKS BLOCKS
                        output.accept(ModBlocks.METALWORKS.get());
                        output.accept(ModBlocks.DUCT.get());
                        output.accept(ModBlocks.RUSTY_DUCT.get());
                        output.accept(ModBlocks.GRAY_BOLTED_BRACKET.get());
                        output.accept(ModBlocks.SMALL_GRAY_BOLTED_BRACKET.get());
                        output.accept(ModBlocks.BLACK_BOLTED_BRACKET.get());
                        output.accept(ModBlocks.SMALL_BLACK_BOLTED_BRACKET.get());
                        output.accept(ModBlocks.RUSTY_BOLTED_BRACKET.get());
                        output.accept(ModBlocks.SMALL_RUSTY_BOLTED_BRACKET.get());
                        output.accept(ModBlocks.YELLOW_RAILING.get());
                        output.accept(ModBlocks.YELLOW_STAIR_RAILING.get());
                        output.accept(ModBlocks.GRAY_RAILING.get());
                        output.accept(ModBlocks.GRAY_STAIR_RAILING.get());
                        output.accept(ModBlocks.BLACK_RAILING.get());
                        output.accept(ModBlocks.BLACK_STAIR_RAILING.get());
                        output.accept(ModBlocks.RUSTY_RAILING.get());
                        output.accept(ModBlocks.RUSTY_STAIR_RAILING.get());

                        //DOORS AND TRAPDOORS
                        output.accept(ModBlocks.DUCT_VENT.get());
                        output.accept(ModBlocks.RUSTY_DUCT_VENT.get());

                        //FURNITURE BLOCKS

                        output.accept(ModBlocks.WHITE_WALL_MEDKIT.get());
                        output.accept(ModBlocks.RED_WALL_MEDKIT.get());
                        output.accept(ModBlocks.FIRE_EXTINGUISHER.get());
                        output.accept(ModBlocks.SMOKE_ALARM.get());

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
                        output.accept(ModBlocks.WALL_SPEAKER.get());

                        output.accept(ModBlocks.IHEA_FURNITURE_KIT.get());
                        output.accept(ModBlocks.SAFETY_FURNISHINGS.get());
                        output.accept(ModBlocks.HYGIENE_FURNISHINGS.get());
                        output.accept(ModBlocks.INDUSTRIAL_FURNISHINGS.get());
                        output.accept(ModBlocks.TECHNOLOGY_FURNISHINGS.get());
                        output.accept(ModBlocks.AMENITY_FURNISHINGS.get());

                        output.accept(ModBlocks.DESK.get());
                        output.accept(ModBlocks.DESK_DRAWER.get());
                        output.accept(ModBlocks.METAL_DESK.get());
                        output.accept(ModBlocks.METAL_DESK_DRAWER.get());
                        output.accept(ModBlocks.METAL_DESK_DRAWER_2.get());
                        output.accept(ModBlocks.OFFICE_DESK.get());
                        output.accept(ModBlocks.OFFICE_DESK_DRAWER.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
