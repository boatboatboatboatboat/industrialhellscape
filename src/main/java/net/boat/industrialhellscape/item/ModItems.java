package net.boat.industrialhellscape.item;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.item.modded_items.InHellTool;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IndustrialHellscape.MOD_ID);

    public static final DeferredItem<Item> JOB_APPLICATION = ITEMS.register("job_application",
            () -> new Item(new Item.Properties()) {
//                @Override
//                public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
//                    tooltipComponents.add(Component.translatable("GET A JOB"));
//                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
//                }
    });

    public static final DeferredItem<Item> INHELL_HAVEN_DEVICE = ITEMS.register("inhell_haven_device",
            () -> new InHellTool(new Item.Properties())
    );

//    public static final DeferredItem<Item> VAPORWAVE_CASSETTE = ITEMS.register("vaporwave_cassette",
//            () -> new RecordItemWithTooltip("vaporwave_cassette", 6, ModSounds.VULTA_SHATTERED, new Item.Properties().stacksTo(1), 4620));
//
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
