package net.boat.industrialhellscape.item;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.item.modded_items.InHellTool;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IndustrialHellscape.MOD_ID);

    public static final DeferredItem<Item> INHELL_HAVEN_DEVICE = ITEMS.register("inhell_haven_device",
            () -> new InHellTool(new Item.Properties())
    );

    public static final DeferredItem<Item> JOB_APPLICATION = ITEMS.register("job_application",
            () -> new Item(new Item.Properties()) {
    });

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
