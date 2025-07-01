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
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MALEVOLENT_MULTITOOL.get())) //Defines Tab Icon texture
                    .title(Component.translatable("creativetab.inhell_tab"))
                    .displayItems((itemDisplayParameters, output) -> {

                        //ITEMS FOR CREATIVE TAB
                        output.accept(ModItems.MALEVOLENT_MULTITOOL.get());
                        output.accept(ModItems.FLOPPY_DISK.get());
                        output.accept(ModItems.FLOPPY_DISKETTE.get());
                        output.accept(ModItems.INHELL_HAVEN_DEVICE.get());
                        output.accept(ModItems.METAL_DETECTOR.get());

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

                        //SPECIAL BLOCKS FOR CREATIVE TAB
                        output.accept(ModBlocks.SOUND_BLOCK.get());
                        output.accept(ModBlocks.DESK.get());
                        output.accept(ModBlocks.DESK_DRAWER.get());

                        //CTM BLOCKS FOR CREATIVE TAB
                        output.accept(ModBlocks.BUNKER_WALL.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
