package net.boat.industrialhellscape.block;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.special_blocks.*;
import net.boat.industrialhellscape.block.special_blocks.PipeBlock;
import net.boat.industrialhellscape.block.special_blocks.SimplePlacedFacingBlock;
import net.boat.industrialhellscape.block.special_blocks.StorageBlock.ConnectedFurniture9SlotStorageBlock;
import net.boat.industrialhellscape.block.special_blocks.StorageBlock.NineSlotMenuBlock;
import net.boat.industrialhellscape.block.special_blocks_properties.HitboxGeometryCollection;
import net.boat.industrialhellscape.sound.ModSounds;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
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
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> VESSELPLATE = registerBlock("vesselplate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> RIVETED_VESSELPLATE = registerBlock("riveted_vesselplate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops())
    );

    //STONELIKE BLOCKS  HERE vvv
    public static final RegistryObject<Block> GRAY_ROCKRETE = registerBlock("gray_rockrete",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> GRAY_ROCKRETE_REBAR = registerBlock("gray_rockrete_rebar",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> GRAY_ROCKRETE_STAIRS = registerBlock("gray_rockrete_stairs",
            () -> new StairBlock(() -> ModBlocks.GRAY_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> GRAY_ROCKRETE_SLAB = registerBlock("gray_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops())
    );

    public static final RegistryObject<Block> HAZARD_STRIPE_YELLOW = registerBlock("hazard_stripe_yellow",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> HAZARD_STRIPE_RED = registerBlock("hazard_stripe_red",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> GRIMY_RESTROOM_TILE = registerBlock("grimy_restroom_tile",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops())
    );

    public static final RegistryObject<Block> GRAY_ROCKRETE_PILLAR = registerBlock("gray_rockrete_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .requiresCorrectToolForDrops())
    );

    public static final RegistryObject<Block> VESSELPLATE_PILLAR = registerBlock("vesselplate_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops())
    );

    public static final RegistryObject<Block> COPPER_PIPE_CONDUIT = registerBlock("copper_pipe_conduit",
            () -> new PipeBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops()
                    .noOcclusion())
    );

    public static final RegistryObject<Block> COPPER_PIPE_CONDUIT_PLANAR_CORNER = registerBlock("copper_pipe_conduit_planar_corner",
            () -> new PipePlanarCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops()
                    .noOcclusion())
    );

    public static final RegistryObject<Block> COPPER_PIPE_CONDUIT_INNER_CORNER = registerBlock("copper_pipe_conduit_inner_corner",
            () -> new InnerCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops()
                    .noOcclusion(),
                    HitboxGeometryCollection.INNER_CORNER_UP(),
                    HitboxGeometryCollection.INNER_CORNER_DOWN(),
                    HitboxGeometryCollection.INNER_CORNER_SIDE())
    );

    public static final RegistryObject<Block> COPPER_PIPE_CONDUIT_OUTER_CORNER = registerBlock("copper_pipe_conduit_outer_corner",
            () -> new InnerCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops()
                    .noOcclusion(),
                    HitboxGeometryCollection.OUTER_CORNER_UP(),
                    HitboxGeometryCollection.OUTER_CORNER_DOWN(),
                    HitboxGeometryCollection.OUTER_CORNER_SIDE())
    );

    //Furniture Blocks Here
    public static final RegistryObject<Block> DESK = registerBlock("desk",
            () -> new ConnectedFurnitureBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion(),
                    ModTags.Blocks.CLASSIC_DESK,
                    HitboxGeometryCollection.DESK_SOLO_SHAPE(),
                    HitboxGeometryCollection.DESK_LEFT_SHAPE(),
                    HitboxGeometryCollection.DESK_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.DESK_RIGHT_SHAPE())
    );
    public static final RegistryObject<Block> DESK_DRAWER = registerBlock("desk_drawer",
            () -> new ConnectedFurniture9SlotStorageBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion(),
                    ModTags.Blocks.CLASSIC_DESK,
                    HitboxGeometryCollection.DESK_DRAWER_SOLO_SHAPE(),
                    HitboxGeometryCollection.DESK_DRAWER_LEFT_SHAPE(),
                    HitboxGeometryCollection.DESK_DRAWER_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.DESK_DRAWER_RIGHT_SHAPE())
    );
    public static final RegistryObject<Block> METAL_DESK = registerBlock("metal_desk",
            () -> new ConnectedFurnitureBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    ModTags.Blocks.METAL_DESK,
                    HitboxGeometryCollection.METAL_DESK_SOLO_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_LEFT_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_RIGHT_SHAPE())
    );
    public static final RegistryObject<Block> METAL_DESK_DRAWER = registerBlock("metal_desk_drawer",
            () -> new ConnectedFurniture9SlotStorageBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    ModTags.Blocks.METAL_DESK,
                    HitboxGeometryCollection.METAL_DESK_DRAWER_SOLO_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_LEFT_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_RIGHT_SHAPE())

    );
    public static final RegistryObject<Block> METAL_DESK_DRAWER_2 = registerBlock("metal_desk_drawer_2",
            () -> new ConnectedFurniture9SlotStorageBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    ModTags.Blocks.METAL_DESK,
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_SOLO_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_LEFT_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_RIGHT_SHAPE())
    );
    public static final RegistryObject<Block> SINK = registerBlock("sink",
            () -> new NineSlotMenuBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK),
                    HitboxGeometryCollection.SLIGHTLY_SMALLER_FULL_BLOCK()
            )
    );
    public static final RegistryObject<Block> TOILET = registerBlock("toilet",
            () -> new ToiletBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.TOILET_NORTH())
    );
    public static final RegistryObject<Block> RED_WALL_MEDKIT = registerBlock("red_wall_medkit",
            () -> new NineSlotMenuBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.RED_MEDKIT_NORTH()
            )
    );
    public static final RegistryObject<Block> WHITE_WALL_MEDKIT = registerBlock("white_wall_medkit",
            () -> new NineSlotMenuBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.WHITE_MEDKIT_NORTH()
            )
    );
    public static final RegistryObject<Block> RETRO_COMPUTER = registerBlock("retro_computer",
            () -> new ModelledPlacedFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.SLIGHTLY_SMALLER_FULL_BLOCK()
            )
    );

    public static final RegistryObject<Block> PIPEWORKS = registerBlock("pipeworks",
            () -> new PlacedFacingFallableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.PIPEWORKS_SOUNDS)
            ) {
                @Override
                public BlockState mirror(BlockState pState, Mirror pMirror) {
                    return super.mirror(pState, pMirror);
                }

                @Override
                public BlockState rotate(BlockState pState, Rotation pRot) {
                    return super.rotate(pState, pRot);
                }
            }
    );


    //CTM BLOCKS HERE (3RD PARTY TEXTURE DEPENDENCIES) VVV
    public static final RegistryObject<Block> HORIZONTAL_ENCASED_CABLES = registerBlock("vertical_encased_cables",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> VERTICAL_ENCASED_CABLES = registerBlock("horizontal_encased_cables",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> VESSELPLATE_GRATE = registerBlock("vesselplate_grate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> SMOOTH_VESSELPLATE = registerBlock("smooth_vesselplate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> RUSTY_VESSELPLATE_GRATE = registerBlock("rusty_vesselplate_grate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops())
    );
    public static final RegistryObject<Block> VERTICAL_RIVETED_VESSELPLATE = registerBlock("vertical_riveted_vesselplate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops()) {
            }
    );
    public static final RegistryObject<Block> HORIZONTAL_RIVETED_VESSELPLATE = registerBlock("horizontal_riveted_vesselplate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops()) {
            }
    );
    public static final RegistryObject<Block> REINFORCED_VESSELGLASS = registerBlock("reinforced_vesselglass",
            () -> new GlassBlock(BlockBehaviour
                    .Properties
                    .copy(Blocks.GLASS)
                    .requiresCorrectToolForDrops()) {
            }
    );
    public static final RegistryObject<Block> VESSELGLASS = registerBlock("vesselglass",
            () -> new GlassBlock(BlockBehaviour
                    .Properties.copy(Blocks.GLASS)
                    .requiresCorrectToolForDrops()) {
            }
    );
    public static final RegistryObject<Block> SMOOTH_VESSELPLATE_TILE = registerBlock("smooth_vesselplate_tile",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops()) {
            }
    );
    public static final RegistryObject<Block> STRUT = registerBlock("strut",
            () -> new StrutBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)) {
            }
    );
    public static final RegistryObject<Block> CATWALK_STRUT = registerBlock("catwalk_strut",
            () -> new StrutBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)) {
            }
    );
    public static final RegistryObject<Block> CATWALK_STRUT_STAIRS = registerBlock("catwalk_strut_stairs",
            () -> new StairBlock(() -> ModBlocks.CATWALK_STRUT.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> CATWALK_STRUT_SLAB = registerBlock("catwalk_strut_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> STRUT_STAIRS = registerBlock("strut_stairs",
            () -> new StairBlock(() -> ModBlocks.STRUT.get().defaultBlockState(),
                    BlockBehaviour
                            .Properties.copy(Blocks.STONE)
                            .requiresCorrectToolForDrops()
                            .noOcclusion()
                            .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> STRUT_SLAB = registerBlock("strut_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );

    public static final RegistryObject<Block> GRAY_STRUT = registerBlock("gray_strut",
            () -> new StrutBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)) {
            }
    );
    public static final RegistryObject<Block> GRAY_CATWALK_STRUT_STAIRS = registerBlock("gray_catwalk_strut_stairs",
            () -> new StairBlock(() -> ModBlocks.CATWALK_STRUT.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).requiresCorrectToolForDrops().noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> GRAY_STRUT_SLAB = registerBlock("gray_strut_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)) {
            }
    );
    public static final RegistryObject<Block> GRAY_CATWALK_STRUT = registerBlock("gray_catwalk_strut",
            () -> new StrutBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)) {
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

    public static final RegistryObject<Block> IHEA_FURNITURE_KIT = registerBlock("ihea_furniture_kit",
            () -> new SimplePlacedFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> SAFETY_FURNISHINGS = registerBlock("safety_furnishings",
            () -> new SimplePlacedFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> HYGIENE_FURNISHINGS = registerBlock("hygiene_furnishings",
            () -> new SimplePlacedFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> INDUSTRIAL_FURNISHINGS = registerBlock("industrial_furnishings",
            () -> new SimplePlacedFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> TECHNOLOGY_FURNISHINGS = registerBlock("technology_furnishings",
            () -> new SimplePlacedFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> AMENITY_FURNISHINGS = registerBlock("amenity_furnishings",
            () -> new SimplePlacedFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion()
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


