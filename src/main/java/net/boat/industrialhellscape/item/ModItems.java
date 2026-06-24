package net.boat.industrialhellscape.item;
import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.item.modded_items.GasStationPillItem;
import net.boat.industrialhellscape.item.modded_items.InHellToolItem;
import net.boat.industrialhellscape.item.modded_items.JobApplicationItem;
import net.boat.industrialhellscape.sound.ModSounds;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

//THIS JAVA CLASS HANDLES REGISTRATION OF ITEMS AND IN SPECIAL CASES, BLOCK ITEMS

public class ModItems {

    //---------- NORMAL ITEMS ----------
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, IndustrialHellscape.MOD_ID);

    public static final RegistryObject<Item> JOB_APPLICATION = ITEMS.register("job_application",
            () -> new JobApplicationItem(new Item.Properties())
            {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.job_application"));
                    super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
                }
            }
    );

    public static final RegistryObject<Item> MARQUEE_DISC = ITEMS.register("disc_1",
            () -> new RecordItem(1, ModSounds.SONG_1, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), 2360)
//            {
//                @Override
//                public void appendHoverText(@NotNull ItemStack stack, Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
//                    tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.disc_1"));
//                    super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
//                }
//            }
    );



    public static final RegistryObject<Item> INHELL_HAVEN_DEVICE = ITEMS.register("inhell_haven_device",
            () -> new InHellToolItem(new Item.Properties())
            {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    if (Screen.hasShiftDown()) {
                        //Expand tooltip if shift-key is down while hovering over item in a GUI.
                        tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.haventool"));
                    } else {
                        //Minimize tooltip by default.
                        tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.shift_down"));
                    }
                    super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
                }
            }
    );

    public static final RegistryObject<Item> GAS_STATION_PILL = ITEMS.register("gas_station_pill",
            () -> new GasStationPillItem(new Item.Properties().rarity(Rarity.RARE).food((new FoodProperties.Builder()).nutrition(0).saturationMod(0.0F).alwaysEat().build()))

            {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.horsepill"));
                    super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
                }
            }

    );

    //---------- END OF NORMAL ITEMS ----------

    //---------- BLOCK ITEMS ----------
        //For flavor text purposes. Ignore the unuse warning.
//    public static final RegistryObject<Item> RIVETED_VESSELPLATE_ITEM = ITEMS.register("riveted_vesselplate",
//            () -> new BlockItemWithTooltip(ModBlocks.RIVETED_VESSELPLATE.get(), new Item.Properties()));
    //----------- END OF BLOCK ITEMS ----------

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}