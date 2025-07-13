package net.boat.industrialhellscape.block;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.special_blocks.*;
import net.boat.industrialhellscape.block.special_blocks.PlacedFacingBlock;
import net.boat.industrialhellscape.block.special_blocks.StorageBlock.ConnectedFurnitureStorageBlock;
import net.boat.industrialhellscape.block.special_blocks.StorageBlock.ExampleMenuBlock;
import net.boat.industrialhellscape.sound.ModSounds;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, IndustrialHellscape.MOD_ID);

    //Custom sound, add this anywhere: .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)

    //IRONLIKE BLOCKS HERE VVV
    public static final RegistryObject<Block> VESSELPLATE_GRATE_BLOCK = registerBlock("vesselplate_grate_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> VESSELPLATE = registerBlock("vesselplate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops())
    );

    //STONELIKE BLOCKS  HERE vvv

    public static final RegistryObject<Block> LIGHT_GRAY_ROCKRETE = registerBlock("light_gray_rockrete",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .strength(1f).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> LIGHT_GRAY_ROCKRETE_REBAR = registerBlock("light_gray_rockrete_rebar",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .strength(1f).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> LIGHT_GRAY_ROCKRETE_STAIRS = registerBlock("light_gray_rockrete_stairs",
            () -> new StairBlock(() -> ModBlocks.LIGHT_GRAY_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> LIGHT_GRAY_ROCKRETE_SLAB = registerBlock("light_gray_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops())
    );


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

    //IN-HOUSE CONNECTED BLOCKS HERE
    public static final RegistryObject<Block> BUNKER_WALL = registerBlock("bunker_wall",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .strength(1f).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> VESSELPLATE_PILLAR = registerBlock("vesselplate_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .strength(1f).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> DESK = registerBlock("desk",
            () -> new ConnectedFurnitureBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), ModTags.Blocks.CLASSIC_DESK) {
            }
    );
    public static final RegistryObject<Block> DESK_DRAWER = registerBlock("desk_drawer",
            () -> new ConnectedFurnitureStorageBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), ModTags.Blocks.CLASSIC_DESK) {
            }
    );
    public static final RegistryObject<Block> METAL_DESK = registerBlock("metal_desk",
            () -> new ConnectedFurnitureBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion(), ModTags.Blocks.METAL_DESK) {
            }
    );
    public static final RegistryObject<Block> METAL_DESK_DRAWER = registerBlock("metal_desk_drawer",
            () -> new ConnectedFurnitureStorageBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion(), ModTags.Blocks.METAL_DESK) {
            }
    );
    public static final RegistryObject<Block> METAL_DESK_DRAWER_2 = registerBlock("metal_desk_drawer_2",
            () -> new ConnectedFurnitureStorageBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion(), ModTags.Blocks.METAL_DESK) {
            }
    );



    //CTM BLOCKS HERE (3RD PARTY TEXTURE DEPENDENCIES) VVV
    public static final RegistryObject<Block> HORIZONTAL_ENCASED_CABLES = registerBlock("vertical_encased_cables",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> VERTICAL_ENCASED_CABLES = registerBlock("horizontal_encased_cables",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> VESSELPLATE_GRATE = registerBlock("vesselplate_grate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> SMOOTH_VESSELPLATE = registerBlock("smooth_vesselplate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> RUSTY_VESSELPLATE_GRATE = registerBlock("rusty_vesselplate_grate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> VERTICAL_RIVETED_VESSELPLATE = registerBlock("vertical_riveted_vesselplate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops()) {
            }
    );
    public static final RegistryObject<Block> HORIZONTAL_RIVETED_VESSELPLATE = registerBlock("horizontal_riveted_vesselplate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops()) {
            }
    );
    public static final RegistryObject<Block> REINFORCED_VESSELGLASS = registerBlock("reinforced_vesselglass",
            () -> new GlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops()) {
            }
    );
    public static final RegistryObject<Block> VESSELGLASS = registerBlock("vesselglass",
            () -> new GlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).requiresCorrectToolForDrops()) {
            }
    );
    public static final RegistryObject<Block> SMOOTH_VESSELPLATE_TILE = registerBlock("smooth_vesselplate_tile",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops()) {
            }
    );
    public static final RegistryObject<Block> STRUT = registerBlock("strut",
            () -> new StrutBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)) {
            }
    );
    public static final RegistryObject<Block> CATWALK_STRUT = registerBlock("catwalk_strut",
            () -> new StrutBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)) {
            }
    );
    public static final RegistryObject<Block> CATWALK_STRUT_STAIRS = registerBlock("catwalk_strut_stairs",
            () -> new StairBlock(() -> ModBlocks.CATWALK_STRUT.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> CATWALK_STRUT_SLAB = registerBlock("catwalk_strut_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> STRUT_STAIRS = registerBlock("strut_stairs",
            () -> new StairBlock(() -> ModBlocks.STRUT.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> STRUT_SLAB = registerBlock("strut_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );

    public static final RegistryObject<Block> GRAY_STRUT = registerBlock("gray_strut",
            () -> new StrutBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)) {
            }
    );
    public static final RegistryObject<Block> GRAY_CATWALK_STRUT_STAIRS = registerBlock("gray_catwalk_strut_stairs",
            () -> new StairBlock(() -> ModBlocks.CATWALK_STRUT.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> GRAY_STRUT_SLAB = registerBlock("gray_strut_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)) {
            }
    );
    public static final RegistryObject<Block> GRAY_CATWALK_STRUT = registerBlock("gray_catwalk_strut",
            () -> new StrutBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)) {
            }
    );

    public static final RegistryObject<Block> GRAY_CATWALK_STRUT_SLAB = registerBlock("gray_catwalk_strut_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)) {
            }
    );
    public static final RegistryObject<Block> GRAY_STRUT_STAIRS = registerBlock("gray_strut_stairs",
            () -> new StairBlock(() -> ModBlocks.CATWALK_STRUT.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );

    //SPECIAL BLOCKS HERE VVV
    public static final RegistryObject<Block> SOUND_BLOCK = registerBlock("sound_block",
            () -> new SoundBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .strength(1f)
            )
    );
    public static final RegistryObject<Block> IHEA_FURNITURE_KIT = registerBlock("ihea_furniture_kit",
            () -> new PlacedFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .strength(1f)
                    .noOcclusion()
            ) {
            }
    );
    public static final RegistryObject<Block> SAFETY_FURNISHINGS = registerBlock("safety_furnishings",
            () -> new PlacedFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .strength(1f)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> HYGIENE_FURNISHINGS = registerBlock("hygiene_furnishings",
            () -> new PlacedFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .strength(1f)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> INDUSTRIAL_FURNISHINGS = registerBlock("industrial_furnishings",
            () -> new PlacedFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .strength(1f)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> TECHNOLOGY_FURNISHINGS = registerBlock("technology_furnishings",
            () -> new PlacedFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .strength(1f)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> AMENITY_FURNISHINGS = registerBlock("amenity_furnishings",
            () -> new PlacedFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .strength(1f)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> SINK = registerBlock("sink",
            () -> new ExampleMenuBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .strength(1f).noOcclusion()
            )
    );
    public static final RegistryObject<Block> TOILET = registerBlock("toilet",
            () -> new SittableInteractableBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).requiresCorrectToolForDrops().noOcclusion())
    );
    public static final RegistryObject<Block> RED_WALL_MEDKIT = registerBlock("red_wall_medkit",
            () -> new ExampleMenuBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .strength(1f).noOcclusion()
                    .requiresCorrectToolForDrops()
            )
    );
    public static final RegistryObject<Block> WHITE_WALL_MEDKIT = registerBlock("white_wall_medkit",
            () -> new ExampleMenuBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .strength(1f).noOcclusion()
                    .requiresCorrectToolForDrops()
            )
    );


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<Item> simpleRegister(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}


