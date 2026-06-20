package net.boat.industrialhellscape.item;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
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
                ModItems.MARQUEE_DISC.asItem(),
                ModItems.JOB_APPLICATION.asItem(),

                //BLOCK ITEMS
                //Secret / Dev Blocks
                ModBlocks.PROTOTYPE_MACHINE.get().asItem(),
                ModBlocks.BODY_PILLOW.get().asItem(),

                //Vesselplate Vertical Variants (Hidden to prevent JEI clutter)
                ModBlocks.VERTICAL_VESSELPLATE.get().asItem(),
                ModBlocks.VERTICAL_REINFORCED_VESSELPLATE.get().asItem(),
                ModBlocks.GRAY_VERTICAL_VESSELPLATE.get().asItem(),
                ModBlocks.GRAY_VERTICAL_REINFORCED_VESSELPLATE.get().asItem(),

                //Grate Vertical Variants (Hidden to prevent JEI clutter)
                ModBlocks.VERTICAL_GRATE.get().asItem(),
                ModBlocks.VERTICAL_CUTOUT_GRATE.get().asItem()
        };

        //https://stackoverflow.com/questions/1128723/how-do-i-determine-whether-an-array-contains-a-particular-value-in-java
        //checks "item" against array of Items to see if it matches any entries
        return Arrays.asList(itemContentToHide).contains(item);
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
