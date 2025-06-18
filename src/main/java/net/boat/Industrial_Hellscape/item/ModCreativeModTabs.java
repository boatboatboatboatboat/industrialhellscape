package net.boat.Industrial_Hellscape.item;

import net.boat.Industrial_Hellscape.IndustrialHellscape;
import net.boat.Industrial_Hellscape.block.ModBlocks;
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
                        output.accept(ModItems.INHELL_DIAGNOSTIC_TOOL.get());

                        //IRONLIKE BLOCKS FOR CREATIVE TAB
                        output.accept(ModBlocks.VESSELPLATE.get());
                        output.accept(ModBlocks.VESSELPLATE_GRATE_BLOCK.get());

                        //STONELIKE BLOCKS FOR CREATIVE TAB
                        output.accept(ModBlocks.GRAY_ROCKRETE.get());
                        output.accept(ModBlocks.LIGHT_GRAY_ROCKRETE.get());
                        output.accept(ModBlocks.WHITE_ROCKRETE.get());
                        output.accept(ModBlocks.BLACK_ROCKRETE.get());
                        output.accept(ModBlocks.HAZARD_STRIPE_YELLOW.get());
                        output.accept(ModBlocks.HAZARD_STRIPE_RED.get());

                        //SPECIAL BLOCKS FOR CREATIVE TAB
                        output.accept(ModBlocks.SOUND_BLOCK.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
