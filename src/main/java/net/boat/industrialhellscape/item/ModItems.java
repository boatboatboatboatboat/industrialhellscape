package net.boat.industrialhellscape.item;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.item.modded_items.GasStationPillItem;
import net.boat.industrialhellscape.item.modded_items.JobApplicationItem;
import net.boat.industrialhellscape.item.modded_items.InHellToolItem;
import net.boat.industrialhellscape.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IndustrialHellscape.MOD_ID);

    public static void removeAndReplaceItems() {
    }

    protected static ResourceLocation itemID(String blockID) {
        return ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, blockID);
    }

    protected static void specificItemReplacement(String oldID, String newID) {
        ITEMS.addAlias(
                itemID(oldID),
                itemID(newID)
        );
    }

    public static final DeferredItem<Item> INHELL_HAVEN_DEVICE = ITEMS.register("inhell_haven_device",
            () -> new InHellToolItem(new Item.Properties())
    );

    public static final DeferredItem<Item> JOB_APPLICATION = ITEMS.register("job_application",
            () -> new JobApplicationItem(new Item.Properties())
    );

    public static final DeferredItem<Item> GAS_STATION_PILL = ITEMS.register("gas_station_pill",
            () -> new GasStationPillItem(new Item.Properties().rarity(Rarity.UNCOMMON).food((new FoodProperties.Builder()).nutrition(0).saturationModifier(0.0F).alwaysEdible().build()))

            {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.horsepill"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            }

    );

    public static final DeferredItem<Item> MARQUEE_DISC = ITEMS.register("disc_1",
            () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.SONG_1_KEY).stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
