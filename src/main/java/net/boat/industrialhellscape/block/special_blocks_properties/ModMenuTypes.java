package net.boat.industrialhellscape.block.special_blocks_properties;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.special_blocks.DeprecatedStorageBlocks.Storage18SlotMenu;
import net.boat.industrialhellscape.block.special_blocks.DeprecatedStorageBlocks.Storage9SlotMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

//DEPRECATED

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, IndustrialHellscape.MOD_ID);

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENU_TYPES.register(name, () -> IForgeMenuType.create(factory));
    }

    public static final RegistryObject<MenuType<Storage9SlotMenu>> STORAGE_9SLOT_MENU = MENU_TYPES.register("storage_9slot_menu",
            () -> IForgeMenuType.create(Storage9SlotMenu::new));
    public static final RegistryObject<MenuType<Storage18SlotMenu>> STORAGE_18SLOT_MENU = MENU_TYPES.register("storage_18slot_menu",
            () -> IForgeMenuType.create(Storage18SlotMenu::new));

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
