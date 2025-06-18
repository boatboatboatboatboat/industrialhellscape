package net.boat.Industrial_Hellscape.block;

import net.boat.Industrial_Hellscape.IndustrialHellscape;
import net.boat.Industrial_Hellscape.block.special_blocks.SoundBlock;
import net.boat.Industrial_Hellscape.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, IndustrialHellscape.MOD_ID);

    //IRONLIKE BLOCKS HERE VVV
    public static final RegistryObject<Block> VESSELPLATE_GRATE_BLOCK = registerBlock("vesselplate_grate_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> VESSELPLATE = registerBlock("vesselplate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL).requiresCorrectToolForDrops())
    );

    //STONELIKE BLOCKS  HERE vvv
    public static final RegistryObject<Block> GRAY_ROCKRETE = registerBlock("gray_rockrete",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .strength(1f).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> BLACK_ROCKRETE = registerBlock("black_rockrete",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .strength(1f).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> LIGHT_GRAY_ROCKRETE = registerBlock("light_gray_rockrete",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .strength(1f).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> WHITE_ROCKRETE = registerBlock("white_rockrete",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .strength(1f).requiresCorrectToolForDrops())
    );

    public static final RegistryObject<Block> HAZARD_STRIPE_YELLOW = registerBlock("hazard_stripe_yellow",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .strength(1f).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> HAZARD_STRIPE_RED = registerBlock("hazard_stripe_red",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .strength(1f).requiresCorrectToolForDrops())
    );

    //SPECIAL BLOCKS HERE VVV
    public static final RegistryObject<Block> SOUND_BLOCK = registerBlock("sound_block",
            () -> new SoundBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .strength(1f)
                    .requiresCorrectToolForDrops()
            )
    );

    //PREREQUISITE METHODS VVV
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
