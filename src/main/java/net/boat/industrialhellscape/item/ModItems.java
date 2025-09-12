package net.boat.industrialhellscape.item;
import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.item.special_items.BlockItemWithTooltip;
import net.boat.industrialhellscape.item.special_items.InHellTool;
import net.boat.industrialhellscape.item.special_items.RecordItemWithTooltip;
import net.boat.industrialhellscape.sound.ModSounds;
import net.minecraft.world.item.Item;
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

    public static final RegistryObject<Item> JOB_APPLICATION = ITEMS.register("job_application",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INHELL_HAVEN_DEVICE = ITEMS.register("inhell_haven_device",
            () -> new InHellTool(new Item.Properties().durability(1024)));

    public static final RegistryObject<Item> VAPORWAVE_CASSETTE = ITEMS.register("vaporwave_cassette",
            () -> new RecordItemWithTooltip("vaporwave_cassette", 6, ModSounds.VULTA_SHATTERED, new Item.Properties().stacksTo(1), 4620));

    //---------- BLOCK ITEMS ----------
        //For flavor text purposes. Ignore the unuse warning.
    public static final RegistryObject<Item> VESSELPLATE_ITEM = ITEMS.register("vesselplate",
            () -> new BlockItemWithTooltip("vesselplate", ModBlocks.VESSELPLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRAY_ROCKRETE_ITEM = ITEMS.register("gray_rockrete",
            () -> new BlockItemWithTooltip("gray_rockrete", ModBlocks.GRAY_ROCKRETE.get(), new Item.Properties()));
    public static final RegistryObject<Item> IHEA_FURNITURE_KIT_ITEM = ITEMS.register("ihea_furniture_kit",
            () -> new BlockItemWithTooltip("ihea_furniture_kit", ModBlocks.IHEA_FURNITURE_KIT.get(), new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}