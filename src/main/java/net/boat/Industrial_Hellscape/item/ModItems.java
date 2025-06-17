package net.boat.Industrial_Hellscape.item;
import net.boat.Industrial_Hellscape.IndustrialHellscape;
import net.boat.Industrial_Hellscape.item.special_items.InHellDiagnosticTool;
import net.boat.Industrial_Hellscape.item.special_items.Malevolent_Multitool;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, IndustrialHellscape.MOD_ID);

    public static final RegistryObject<Item> MALEVOLENT_MULTITOOL = ITEMS.register("malevolent_multitool",
            () -> new Malevolent_Multitool(new Item.Properties().fireResistant().stacksTo(1))); //Malevolent_Multitool is a subclass of superclass Item, having more unique characteristics than a normal item

    public static final RegistryObject<Item> FLOPPY_DISK = ITEMS.register("floppy_disk",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FLOPPY_DISKETTE = ITEMS.register("floppy_diskette",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INHELL_DIAGNOSTIC_TOOL = ITEMS.register("inhell_diagnostic_tool",
            () -> new InHellDiagnosticTool(new Item.Properties().durability(1024))); //MetalDetectorItem is a subclass of superclass Item, having more unique characteristics than a normal item

    public static void register(IEventBus eventbus) {
        ITEMS.register(eventbus);
    }

}