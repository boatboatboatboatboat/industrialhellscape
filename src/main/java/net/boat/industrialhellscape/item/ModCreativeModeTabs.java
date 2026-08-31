package net.boat.industrialhellscape.item;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Arrays;
import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IndustrialHellscape.MOD_ID);

    public static final Supplier<CreativeModeTab> INDUSTRIAL_HELLSCAPE_ITEMS_TAB = CREATIVE_MODE_TAB.register("industrial_hellscape_items_tab",
            () -> CreativeModeTab.builder()
                    .icon( () -> new ItemStack(ModItems.INHELL_HAVEN_DEVICE.get()))
                    .title(Component.translatable("gui.industrialhellscape.creative_tab"))
                    //https://github.com/Tfarcenim/Abyssal-Decor/blob/1.20.1-multiloader/common/src/main/java/net/starrysock/abyssaldecor/registry/AbyssalCreativeTabs.java
                    //For each registered item, if it is NOT a secret item, display it in the Creative mode tab
                    .displayItems(((itemDisplayParameters, output) -> {
                        ModItems.ITEMS.getEntries().forEach(modItem -> {
                            if(!isHiddenItem(modItem.get())) {
                                output.accept(modItem.get());
                            }
                        });
                    }))
                    .withSearchBar()
                    .build()
    );

    static boolean isHiddenItem(Item item) {
        Item[] itemContentToHide = new Item[]{
                //ITEMS
                //Secret / Dev Items
                ModItems.MARQUEE_DISC.asItem(),
                ModItems.GAS_STATION_PILL.asItem(),

                //BLOCK ITEMS
                //Secret / Dev Blocks
                //ModBlocks.PROTOTYPE_MACHINE.asItem(),
                ModBlocks.BODY_PILLOW_OZY.asItem(),
                ModBlocks.BODY_PILLOW_FANG.asItem(),
                ModBlocks.BODY_PILLOW_PROV.asItem(),
                ModBlocks.DEBUG_BLOCK.asItem(),

                //Unreleased
                ModBlocks.TRUSS_SUPPORT.get().asItem(),
                ModBlocks.CHAINLINK_FENCE.get().asItem(),

                //Vesselplate Vertical Variants (Hidden to prevent JEI clutter)
                ModBlocks.VERTICAL_VESSELPLATE.asItem(),
                ModBlocks.VERTICAL_REINFORCED_VESSELPLATE.asItem(),
                ModBlocks.GRAY_VERTICAL_VESSELPLATE.asItem(),
                ModBlocks.GRAY_VERTICAL_REINFORCED_VESSELPLATE.asItem(),
                ModBlocks.RUSTY_VERTICAL_VESSELPLATE.asItem(),
                ModBlocks.RUSTY_VERTICAL_REINFORCED_VESSELPLATE.asItem(),

                //Grate Vertical Variants (Hidden to prevent JEI clutter)
                ModBlocks.VERTICAL_VENT.asItem(),
                ModBlocks.VERTICAL_CUTOUT_VENT.asItem(),
                ModBlocks.RUSTY_VERTICAL_VENT.asItem(),
                ModBlocks.RUSTY_VERTICAL_CUTOUT_VENT.asItem()
        };

        //https://stackoverflow.com/questions/1128723/how-do-i-determine-whether-an-array-contains-a-particular-value-in-java
        //checks "item" against array of Items to see if it matches any entries
        return Arrays.asList(itemContentToHide).contains(item);
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
