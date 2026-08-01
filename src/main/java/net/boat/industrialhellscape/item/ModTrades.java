package net.boat.industrialhellscape.item;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import java.util.List;

@EventBusSubscriber(modid = IndustrialHellscape.MOD_ID)

//Despite being grayed out, this class and event is functional and active.
//Reference: MCreator
public class ModTrades {
    @SubscribeEvent
    public static void registerWanderingTrades(WandererTradesEvent event) {

        //old Method (MCreator)
//                event.getGenericTrades()
//                .add(
//
//                        new BasicItemListing(new ItemStack(Items.EMERALD, 12), new ItemStack(ModItems.GAS_STATION_PILL.get()), 4, 1, .05f)
//
//                );

        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

//        genericTrades.add((level,entity, randomSource) -> new MerchantOffer(
//                new ItemCost(Items.EMERALD, 8),
//                new ItemStack(ModItems.KAUPEN_SMITHING_TEMPLATE.get(), 1), 1, 10, 0.2f));

        rareTrades.add((level,entity) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 8),
                new ItemStack(ModItems.GAS_STATION_PILL.get(), 1), 4, 0, 0.5f));

        rareTrades.add((level,entity) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 16),
                new ItemStack(ModBlocks.BODY_PILLOW.get(), 1), 1, 0, 0.5f));
    }
}
