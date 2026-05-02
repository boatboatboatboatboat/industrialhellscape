package net.boat.industrialhellscape.item;
import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.item.modded_items.BlockItemWithTooltip;
import net.boat.industrialhellscape.item.modded_items.InHellTool;
import net.boat.industrialhellscape.item.modded_items.RecordItemWithTooltip;
import net.boat.industrialhellscape.sound.ModSounds;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

//THIS JAVA CLASS HANDLES REGISTRATION OF ITEMS AND IN SPECIAL CASES, BLOCK ITEMS

public class ModItems {

    //---------- NORMAL ITEMS ----------
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, IndustrialHellscape.MOD_ID);

    public static final RegistryObject<Item> RETRO_CASSETTE = ITEMS.register("retro_cassette",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FLOPPY_DISK = ITEMS.register("floppy_disk",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FLOPPY_DISKETTE = ITEMS.register("floppy_diskette",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> JOB_APPLICATION = ITEMS.register("job_application",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INHELL_HAVEN_DEVICE = ITEMS.register("inhell_haven_device",
            () -> new InHellTool(new Item.Properties()));

    public static final RegistryObject<Item> VAPORWAVE_CASSETTE = ITEMS.register("vaporwave_cassette",
            () -> new RecordItemWithTooltip("vaporwave_cassette", 6, ModSounds.VULTA_SHATTERED, new Item.Properties().stacksTo(1), 4620));

    //---------- END OF NORMAL ITEMS ----------

    //---------- FOOD ITEMS ----------
    public static final RegistryObject<Item> ASPIC = ITEMS.register("aspic",
    () -> new Item(new Item.Properties().food(ModFoods.ASPIC)));
    //---------- END OF FOOD ITEMS ----------

    //---------- BLOCK ITEMS ----------
        //For flavor text purposes. Ignore the unuse warning.
    public static final RegistryObject<Item> RIVETED_VESSELPLATE_ITEM = ITEMS.register("riveted_vesselplate",
            () -> new BlockItemWithTooltip(ModBlocks.RIVETED_VESSELPLATE.get(), new Item.Properties()));

    public static final RegistryObject<Item> GRAY_ROCKRETE_ITEM = ITEMS.register("gray_rockrete",
            () -> new BlockItemWithTooltip(ModBlocks.GRAY_ROCKRETE.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_ROCKRETE_ITEM = ITEMS.register("red_rockrete",
            () -> new BlockItemWithTooltip(ModBlocks.RED_ROCKRETE.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_ROCKRETE_ITEM = ITEMS.register("blue_rockrete",
            () -> new BlockItemWithTooltip(ModBlocks.BLUE_ROCKRETE.get(), new Item.Properties()));
    public static final RegistryObject<Item> YELLOW_ROCKRETE_ITEM = ITEMS.register("yellow_rockrete",
            () -> new BlockItemWithTooltip(ModBlocks.YELLOW_ROCKRETE.get(), new Item.Properties()));
    public static final RegistryObject<Item> GREEN_ROCKRETE_ITEM = ITEMS.register("green_rockrete",
            () -> new BlockItemWithTooltip(ModBlocks.GREEN_ROCKRETE.get(), new Item.Properties()));

    public static final RegistryObject<Item> IHEA_FURNITURE_KIT_ITEM = ITEMS.register("ihea_furniture_kit",
            () -> new BlockItemWithTooltip(ModBlocks.IHEA_FURNITURE_KIT.get(), new Item.Properties()));
    public static final RegistryObject<Item> PIPEWORKS_ITEM = ITEMS.register("pipeworks",
            () -> new BlockItemWithTooltip(ModBlocks.PIPEWORKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> METALWORKS_ITEM = ITEMS.register("metalworks",
            () -> new BlockItemWithTooltip(ModBlocks.METALWORKS.get(), new Item.Properties()));

    //----------- END OF BLOCK ITEMS ----------

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}