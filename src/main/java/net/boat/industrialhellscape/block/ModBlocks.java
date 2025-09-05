package net.boat.industrialhellscape.block;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.special_blocks.*;
import net.boat.industrialhellscape.block.special_blocks.ContainerBlock.ContainerBlock;
import net.boat.industrialhellscape.block.special_blocks.PipeBlock;
import net.boat.industrialhellscape.block.special_blocks.SimpleFacingBlock;
import net.boat.industrialhellscape.block.special_blocks.StorageBlock.*;
import net.boat.industrialhellscape.block.special_blocks_properties.HitboxGeometryCollection;
import net.boat.industrialhellscape.sound.ModSounds;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED; //For configuring light-emitting blocks

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, IndustrialHellscape.MOD_ID);

    //EXPERIMENTAL BLOCKS
    public static final RegistryObject<Block> LARGE_LOCKER = registerBlockAndBlockItem("large_locker",
            () -> new Vertical2BlockStorageMultiBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> LOCKER_BOX = registerBlockAndBlockItem("locker_box",
            () -> new ContainerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    , 9
                    , false
            )
    );
    public static final RegistryObject<Block> FUEL_DRUM = registerBlockAndBlockItem("fuel_drum",
            () -> new ContainerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    , 18
                    ,false
            )
    );

    public static final RegistryObject<Block> HANDRAIL = registerBlockAndBlockItem("handrail",
            () -> new ModelledWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    HitboxGeometryCollection.HANDRAIL()
            )
    );
    //    public static final RegistryObject<Block> SEETHROUGH_GRATE_PANE = registerBlock("see-through_grate_pane",
//            () -> new IronBarsBlock(BlockBehaviour
//                    .Properties.copy(Blocks.GLASS)
//                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)
//            )
//    );
    public static final RegistryObject<Block> GRAY_SEETHROUGH_GRATE_PANE = registerBlockAndBlockItem("gray_see-through_grate_pane",
            () -> new IronBarsBlock(BlockBehaviour
                    .Properties.copy(Blocks.GLASS_PANE)
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)
            )
    );


    //VESSELPLATE BLOCKS
    public static final RegistryObject<Block> VESSELPLATE = registerBlockOnly("vesselplate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    )
    );
    public static final RegistryObject<Block> RIVETED_VESSELPLATE_PANEL = registerBlockAndBlockItem("riveted_vesselplate_panel",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    )
    );
    public static final RegistryObject<Block> VESSELPLATE_PILLAR = registerBlockAndBlockItem("vesselplate_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> VERTICAL_RIVETED_VESSELPLATE = registerBlockAndBlockItem("vertical_riveted_vesselplate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> HORIZONTAL_RIVETED_VESSELPLATE = registerBlockAndBlockItem("horizontal_riveted_vesselplate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> SMOOTH_VESSELPLATE_TILE = registerBlockAndBlockItem("smooth_vesselplate_tile",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );

    public static final RegistryObject<Block> GRAY_VESSELPLATE = registerBlockAndBlockItem("gray_vesselplate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> GRAY_RIVETED_VESSELPLATE_PANEL = registerBlockAndBlockItem("gray_riveted_vesselplate_panel",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> GRAY_VESSELPLATE_PILLAR = registerBlockAndBlockItem("gray_vesselplate_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> GRAY_HORIZONTAL_RIVETED_VESSELPLATE = registerBlockAndBlockItem("gray_horizontal_riveted_vesselplate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> GRAY_VERTICAL_RIVETED_VESSELPLATE = registerBlockAndBlockItem("gray_vertical_riveted_vesselplate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> SMOOTH_GRAY_VESSELPLATE_TILE = registerBlockAndBlockItem("smooth_gray_vesselplate_tile",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );

    //ENCASED CABLES
    public static final RegistryObject<Block> ENCASED_CABLES = registerBlockAndBlockItem("encased_cables",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );

    //GRATE BLOCKS
    public static final RegistryObject<Block> GRATE = registerBlockAndBlockItem("grate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> GRAY_GRATE = registerBlockAndBlockItem("gray_grate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> SEETHROUGH_GRATE = registerBlockAndBlockItem("see-through_grate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.GLASS)
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> GRAY_SEETHROUGH_GRATE = registerBlockAndBlockItem("gray_see-through_grate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.GLASS)
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> RUSTY_GRATE = registerBlockAndBlockItem("rusty_grate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)
            )
    );

    //STRUT BLOCKS
    public static final RegistryObject<Block> STRUT = registerBlockAndBlockItem("strut",
            () -> new SimpleWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> CATWALK_STRUT = registerBlockAndBlockItem("catwalk_strut",
            () -> new SimpleWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> CATWALK_STRUT_STAIRS = registerBlockAndBlockItem("catwalk_strut_stairs",
            () -> new StairBlock(() -> ModBlocks.CATWALK_STRUT.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> CATWALK_STRUT_SLAB = registerBlockAndBlockItem("catwalk_strut_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> STRUT_STAIRS = registerBlockAndBlockItem("strut_stairs",
            () -> new StairBlock(() -> ModBlocks.STRUT.get().defaultBlockState(),
                    BlockBehaviour
                            .Properties.copy(Blocks.STONE)
                            .noOcclusion()
                            .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> STRUT_SLAB = registerBlockAndBlockItem("strut_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );

    public static final RegistryObject<Block> GRAY_STRUT = registerBlockAndBlockItem("gray_strut",
            () -> new SimpleWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> GRAY_CATWALK_STRUT_STAIRS = registerBlockAndBlockItem("gray_catwalk_strut_stairs",
            () -> new StairBlock(() -> ModBlocks.CATWALK_STRUT.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> GRAY_STRUT_SLAB = registerBlockAndBlockItem("gray_strut_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> GRAY_CATWALK_STRUT = registerBlockAndBlockItem("gray_catwalk_strut",
            () -> new SimpleWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)
            )
    );

    public static final RegistryObject<Block> GRAY_CATWALK_STRUT_SLAB = registerBlockAndBlockItem("gray_catwalk_strut_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> GRAY_STRUT_STAIRS = registerBlockAndBlockItem("gray_strut_stairs",
            () -> new StairBlock(() -> ModBlocks.CATWALK_STRUT.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );

    //VESSELGLASS BLOCKS
    public static final RegistryObject<Block> REINFORCED_VESSELGLASS = registerBlockAndBlockItem("reinforced_vesselglass",
            () -> new GlassBlock(BlockBehaviour
                    .Properties
                    .copy(Blocks.GLASS)
            )
    );

    public static final RegistryObject<Block> VESSELGLASS = registerBlockAndBlockItem("vesselglass",
            () -> new GlassBlock(BlockBehaviour
                    .Properties.copy(Blocks.GLASS)
            )
    );

    public static final RegistryObject<Block> GRAY_REINFORCED_VESSELGLASS = registerBlockAndBlockItem("gray_reinforced_vesselglass",
            () -> new GlassBlock(BlockBehaviour
                    .Properties
                    .copy(Blocks.GLASS)
            )
    );
    public static final RegistryObject<Block> GRAY_VESSELGLASS = registerBlockAndBlockItem("gray_vesselglass",
            () -> new GlassBlock(BlockBehaviour
                    .Properties.copy(Blocks.GLASS)
            )
    );

    //GRAY ROCKRETE
    public static final RegistryObject<Block> GRAY_ROCKRETE = registerBlockOnly("gray_rockrete",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    )
    );
    public static final RegistryObject<Block> GRAY_ROCKRETE_STAIRS = registerBlockAndBlockItem("gray_rockrete_stairs",
            () -> new StairBlock(() -> ModBlocks.GRAY_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE))
    );
    public static final RegistryObject<Block> GRAY_ROCKRETE_SLAB = registerBlockAndBlockItem("gray_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    )
    );
    public static final RegistryObject<Block> GRAY_ROCKRETE_PILLAR = registerBlockAndBlockItem("gray_rockrete_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    //GREEN ROCKRETE
    public static final RegistryObject<Block> GREEN_ROCKRETE = registerBlockAndBlockItem("green_rockrete",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> GREEN_ROCKRETE_STAIRS = registerBlockAndBlockItem("green_rockrete_stairs",
            () -> new StairBlock(() -> ModBlocks.GREEN_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE))
    );
    public static final RegistryObject<Block> GREEN_ROCKRETE_SLAB = registerBlockAndBlockItem("green_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> GREEN_ROCKRETE_PILLAR = registerBlockAndBlockItem("green_rockrete_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    //YELLOW ROCKRETE
    public static final RegistryObject<Block> YELLOW_ROCKRETE = registerBlockAndBlockItem("yellow_rockrete",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> YELLOW_ROCKRETE_STAIRS = registerBlockAndBlockItem("yellow_rockrete_stairs",
            () -> new StairBlock(() -> ModBlocks.YELLOW_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE))
    );
    public static final RegistryObject<Block> YELLOW_ROCKRETE_SLAB = registerBlockAndBlockItem("yellow_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> YELLOW_ROCKRETE_PILLAR = registerBlockAndBlockItem("yellow_rockrete_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    //BLUE ROCKRETE
    public static final RegistryObject<Block> BLUE_ROCKRETE = registerBlockAndBlockItem("blue_rockrete",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> BLUE_ROCKRETE_STAIRS = registerBlockAndBlockItem("blue_rockrete_stairs",
            () -> new StairBlock(() -> ModBlocks.BLUE_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE))
    );
    public static final RegistryObject<Block> BLUE_ROCKRETE_SLAB = registerBlockAndBlockItem("blue_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> BLUE_ROCKRETE_PILLAR = registerBlockAndBlockItem("blue_rockrete_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );

    public static final RegistryObject<Block> HAZARD_STRIPE_YELLOW = registerBlockAndBlockItem("hazard_stripe_yellow",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    )
    );
    public static final RegistryObject<Block> HAZARD_STRIPE_RED = registerBlockAndBlockItem("hazard_stripe_red",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    )
    );
    public static final RegistryObject<Block> GRIMY_RESTROOM_TILE = registerBlockAndBlockItem("grimy_restroom_tile",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    )
    );


    //PIPE BLOCKS
    public static final RegistryObject<Block> PIPEWORKS = registerBlockAndBlockItem("pipeworks",
            () -> new FacingFallableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> COPPER_PIPE_CONDUIT = registerBlockAndBlockItem("copper_pipe_conduit",
            () -> new PipeBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    
                    .noOcclusion())
    );
    public static final RegistryObject<Block> COPPER_PIPE_CONDUIT_PLANAR_CORNER = registerBlockAndBlockItem("copper_pipe_conduit_planar_corner",
            () -> new PipePlanarCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    
                    .noOcclusion())
    );
    public static final RegistryObject<Block> COPPER_PIPE_CONDUIT_INNER_CORNER = registerBlockAndBlockItem("copper_pipe_conduit_inner_corner",
            () -> new InnerCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    
                    .noOcclusion(),
                    HitboxGeometryCollection.INNER_CORNER_UP(),
                    HitboxGeometryCollection.INNER_CORNER_DOWN(),
                    HitboxGeometryCollection.INNER_CORNER_SIDE())
    );
    public static final RegistryObject<Block> COPPER_PIPE_CONDUIT_OUTER_CORNER = registerBlockAndBlockItem("copper_pipe_conduit_outer_corner",
            () -> new InnerCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    
                    .noOcclusion(),
                    HitboxGeometryCollection.OUTER_CORNER_UP(),
                    HitboxGeometryCollection.OUTER_CORNER_DOWN(),
                    HitboxGeometryCollection.OUTER_CORNER_SIDE())
    );
    public static final RegistryObject<Block> BRASS_PIPE_CONDUIT = registerBlockAndBlockItem("brass_pipe_conduit",
            () -> new PipeBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion())
    );
    public static final RegistryObject<Block> BRASS_PIPE_CONDUIT_PLANAR_CORNER = registerBlockAndBlockItem("brass_pipe_conduit_planar_corner",
            () -> new PipePlanarCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion())
    );
    public static final RegistryObject<Block> BRASS_PIPE_CONDUIT_INNER_CORNER = registerBlockAndBlockItem("brass_pipe_conduit_inner_corner",
            () -> new InnerCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.INNER_CORNER_UP(),
                    HitboxGeometryCollection.INNER_CORNER_DOWN(),
                    HitboxGeometryCollection.INNER_CORNER_SIDE())
    );
    public static final RegistryObject<Block> BRASS_PIPE_CONDUIT_OUTER_CORNER = registerBlockAndBlockItem("brass_pipe_conduit_outer_corner",
            () -> new InnerCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.OUTER_CORNER_UP(),
                    HitboxGeometryCollection.OUTER_CORNER_DOWN(),
                    HitboxGeometryCollection.OUTER_CORNER_SIDE())
    );
    public static final RegistryObject<Block> GRAY_PIPE_CONDUIT = registerBlockAndBlockItem("gray_pipe_conduit",
            () -> new PipeBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion())
    );
    public static final RegistryObject<Block> GRAY_PIPE_CONDUIT_PLANAR_CORNER = registerBlockAndBlockItem("gray_pipe_conduit_planar_corner",
            () -> new PipePlanarCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion())
    );
    public static final RegistryObject<Block> GRAY_PIPE_CONDUIT_INNER_CORNER = registerBlockAndBlockItem("gray_pipe_conduit_inner_corner",
            () -> new InnerCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.INNER_CORNER_UP(),
                    HitboxGeometryCollection.INNER_CORNER_DOWN(),
                    HitboxGeometryCollection.INNER_CORNER_SIDE())
    );
    public static final RegistryObject<Block> GRAY_PIPE_CONDUIT_OUTER_CORNER = registerBlockAndBlockItem("gray_pipe_conduit_outer_corner",
            () -> new InnerCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.OUTER_CORNER_UP(),
                    HitboxGeometryCollection.OUTER_CORNER_DOWN(),
                    HitboxGeometryCollection.OUTER_CORNER_SIDE())
    );

    //FURNITURE BLOCKS HERE
    public static final RegistryObject<Block> GRAY_BOLTED_BRACKET = registerBlockAndBlockItem("gray_bolted_bracket",
            () -> new InnerCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.INNER_CORNER_UP(),
                    HitboxGeometryCollection.INNER_CORNER_DOWN(),
                    HitboxGeometryCollection.INNER_CORNER_SIDE())
    );
    public static final RegistryObject<Block> BLACK_BOLTED_BRACKET = registerBlockAndBlockItem("black_bolted_bracket",
            () -> new InnerCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.INNER_CORNER_UP(),
                    HitboxGeometryCollection.INNER_CORNER_DOWN(),
                    HitboxGeometryCollection.INNER_CORNER_SIDE())
    );
    public static final RegistryObject<Block> DESK = registerBlockAndBlockItem("desk",
            () -> new ConnectedFurnitureBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion(),
                    ModTags.Blocks.CLASSIC_DESK,
                    HitboxGeometryCollection.DESK_SOLO_SHAPE(),
                    HitboxGeometryCollection.DESK_LEFT_SHAPE(),
                    HitboxGeometryCollection.DESK_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.DESK_RIGHT_SHAPE())
    );
    public static final RegistryObject<Block> DESK_DRAWER = registerBlockAndBlockItem("desk_drawer",
            () -> new ConnectedFurniture18SlotStorageBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion(),
                    ModTags.Blocks.CLASSIC_DESK,
                    HitboxGeometryCollection.DESK_DRAWER_SOLO_SHAPE(),
                    HitboxGeometryCollection.DESK_DRAWER_LEFT_SHAPE(),
                    HitboxGeometryCollection.DESK_DRAWER_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.DESK_DRAWER_RIGHT_SHAPE())
    );
    public static final RegistryObject<Block> METAL_DESK = registerBlockAndBlockItem("metal_desk",
            () -> new ConnectedFurnitureBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    ModTags.Blocks.METAL_DESK,
                    HitboxGeometryCollection.METAL_DESK_SOLO_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_LEFT_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_RIGHT_SHAPE())
    );
    public static final RegistryObject<Block> METAL_DESK_DRAWER = registerBlockAndBlockItem("metal_desk_drawer",
            () -> new ConnectedFurniture18SlotStorageBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    ModTags.Blocks.METAL_DESK,
                    HitboxGeometryCollection.METAL_DESK_DRAWER_SOLO_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_LEFT_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_RIGHT_SHAPE())

    );
    public static final RegistryObject<Block> METAL_DESK_DRAWER_2 = registerBlockAndBlockItem("metal_desk_drawer_2",
            () -> new ConnectedFurniture18SlotStorageBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    ModTags.Blocks.METAL_DESK,
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_SOLO_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_LEFT_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_RIGHT_SHAPE())
    );
    public static final RegistryObject<Block> TOILET = registerBlockAndBlockItem("toilet",
            () -> new ToiletBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.TOILET_NORTH())
    );

    public static final RegistryObject<Block> SINK = registerBlockAndBlockItem("sink",
            () -> new ModelledFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.UPPER_SLAB())
    );

    public static final RegistryObject<Block> RED_WALL_MEDKIT = registerBlockAndBlockItem("red_wall_medkit",
            () -> new Furniture9SlotStorageBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.RED_MEDKIT_NORTH()
            )
    );
    public static final RegistryObject<Block> WHITE_WALL_MEDKIT = registerBlockAndBlockItem("white_wall_medkit",
            () -> new Furniture9SlotStorageBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.WHITE_MEDKIT_NORTH()
            )
    );
    public static final RegistryObject<Block> RETRO_COMPUTER = registerBlockAndBlockItem("retro_computer",
            () -> new InteractableModelledFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion()
                    .lightLevel(state -> state.getValue(POWERED) ? 7 : 0),
                    HitboxGeometryCollection.RETRO_COMPUTER()
            )
    );
    public static final RegistryObject<Block> CASSETTE_PLAYER = registerBlockAndBlockItem("cassette_player",
            () -> new InteractableModelledFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.CASSETTE_PLAYER()
            )
    );
    public static final RegistryObject<Block> YELLOW_TRIPOD = registerBlockAndBlockItem("yellow_tripod",
            () -> new ModelledWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    HitboxGeometryCollection.THIN_VERTICAL_ROD_SHAPE()
            )
    );
    public static final RegistryObject<Block> WORK_LIGHT_MOUNT = registerBlockAndBlockItem("work_light_mount",
            () -> new InteractableModelledFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .lightLevel(state -> state.getValue(POWERED) ? 15 : 0),
                    HitboxGeometryCollection.WORK_LIGHT_MOUNT_SHAPE()
            )
    );
    public static final RegistryObject<Block> FLOOR_WORK_LIGHT = registerBlockAndBlockItem("floor_work_light",
            () -> new InteractableModelledFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .lightLevel(state -> state.getValue(POWERED) ? 15 : 0),
                    HitboxGeometryCollection.FLOOR_WORK_LIGHT_SHAPE()
            )
    );

    //FURNITURE CATEGORY BLOCKS
    public static final RegistryObject<Block> IHEA_FURNITURE_KIT = registerBlockOnly("ihea_furniture_kit",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion()
            )
    );

    public static final RegistryObject<Block> SAFETY_FURNISHINGS = registerBlockAndBlockItem("safety_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> HYGIENE_FURNISHINGS = registerBlockAndBlockItem("hygiene_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> INDUSTRIAL_FURNISHINGS = registerBlockAndBlockItem("industrial_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> TECHNOLOGY_FURNISHINGS = registerBlockAndBlockItem("technology_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> AMENITY_FURNISHINGS = registerBlockAndBlockItem("amenity_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion()
            )
    );

    private static <T extends Block> RegistryObject<T> registerBlockAndBlockItem(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlockOnly(String name, Supplier<T> block) {
        return BLOCKS.register(name,block);
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}


