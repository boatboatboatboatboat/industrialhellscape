package net.boat.industrialhellscape.item;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
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
                        output.accept(ModItems.INHELL_HAVEN_DEVICE);
                        output.accept(ModItems.JOB_APPLICATION);

                        output.accept(ModBlocks.DUCT);
                        output.accept(ModBlocks.RUSTY_DUCT);
                    }))
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
