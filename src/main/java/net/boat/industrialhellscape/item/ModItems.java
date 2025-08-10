package net.boat.industrialhellscape.item;
import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.item.special_items.InHellTool;
import net.boat.industrialhellscape.item.special_items.Malevolent_Multitool;
import net.boat.industrialhellscape.item.special_items.MetalDetector;
import net.boat.industrialhellscape.item.special_items.RecordItemWithTooltip;
import net.boat.industrialhellscape.sound.ModSounds;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, IndustrialHellscape.MOD_ID);

    public static final RegistryObject<Item> FLOPPY_DISK = ITEMS.register("floppy_disk",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FLOPPY_DISKETTE = ITEMS.register("floppy_diskette",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INHELL_HAVEN_DEVICE = ITEMS.register("inhell_haven_device",
            () -> new InHellTool(new Item.Properties().durability(1024)));

    public static final RegistryObject<Item> VAPORWAVE_CASSETTE = ITEMS.register("vaporwave_cassette",
            () -> new RecordItemWithTooltip("vaporwave_cassette", 6, ModSounds.VULTA_SHATTERED, new Item.Properties().stacksTo(1), 4620));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}