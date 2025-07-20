package net.boat.industrialhellscape.block.special_blocks_properties;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.special_blocks.StorageBlock.NineSlotMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, IndustrialHellscape.MOD_ID);



    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENU_TYPES.register(name, () -> IForgeMenuType.create(factory));
    }

    public static final RegistryObject<MenuType<NineSlotMenu>> STORAGE_9SLOT_MENU = MENU_TYPES.register("storage_9slot_menu",
            () -> IForgeMenuType.create(NineSlotMenu::new));


    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
