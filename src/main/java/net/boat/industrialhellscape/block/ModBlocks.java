package net.boat.industrialhellscape.block;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.special_blocks.*;
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
    public static final RegistryObject<Block> LARGE_LOCKER = registerBlock("large_locker",
            () -> new Vertical2BlockStorageMultiBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> LOCKER_BOX = registerBlock("locker_box",
            () -> new Furniture27SlotStorageBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    ,HitboxGeometryCollection.FULL_BLOCK()
            )
    );
    public static final RegistryObject<Block> HANDRAIL = registerBlock("handrail",
            () -> new ModelledWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    HitboxGeometryCollection.HANDRAIL()
            )
    );


    //VESSELPLATE BLOCKS
//    public static final RegistryObject<Block> VESSELPLATE_GRATE_BLOCK = registerBlock("vesselplate_grate_block",
//            () -> new Block(BlockBehaviour
//                    .Properties.copy(Blocks.IRON_BLOCK)
//                    )
//    );
    public static final RegistryObject<Block> VESSELPLATE = registerBlock("vesselplate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    )
    );
    public static final RegistryObject<Block> RIVETED_VESSELPLATE = registerBlock("riveted_vesselplate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    )
    );
    public static final RegistryObject<Block> GRAY_VESSELPLATE = registerBlock("gray_vesselplate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> GRAY_RIVETED_VESSELPLATE = registerBlock("gray_riveted_vesselplate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> GRAY_VESSELPLATE_PILLAR = registerBlock("gray_vesselplate_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> VESSELPLATE_PILLAR = registerBlock("vesselplate_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );

    //STONELIKE BLOCKS  HERE vvv

    //GRAY ROCKRETE
    public static final RegistryObject<Block> GRAY_ROCKRETE = registerBlock("gray_rockrete",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    )
    );
    public static final RegistryObject<Block> GRAY_ROCKRETE_REBAR = registerBlock("gray_rockrete_rebar",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    )
    );
    public static final RegistryObject<Block> GRAY_ROCKRETE_STAIRS = registerBlock("gray_rockrete_stairs",
            () -> new StairBlock(() -> ModBlocks.GRAY_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE))
    );
    public static final RegistryObject<Block> GRAY_ROCKRETE_SLAB = registerBlock("gray_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    )
    );
    public static final RegistryObject<Block> GRAY_ROCKRETE_PILLAR = registerBlock("gray_rockrete_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
//GREEN ROCKRETE
    public static final RegistryObject<Block> GREEN_ROCKRETE = registerBlock("green_rockrete",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> GREEN_ROCKRETE_REBAR = registerBlock("green_rockrete_rebar",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> GREEN_ROCKRETE_STAIRS = registerBlock("green_rockrete_stairs",
            () -> new StairBlock(() -> ModBlocks.GREEN_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE))
    );
    public static final RegistryObject<Block> GREEN_ROCKRETE_SLAB = registerBlock("green_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> GREEN_ROCKRETE_PILLAR = registerBlock("green_rockrete_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
//YELLOW ROCKRETE
    public static final RegistryObject<Block> YELLOW_ROCKRETE = registerBlock("yellow_rockrete",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> YELLOW_ROCKRETE_REBAR = registerBlock("yellow_rockrete_rebar",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> YELLOW_ROCKRETE_STAIRS = registerBlock("yellow_rockrete_stairs",
            () -> new StairBlock(() -> ModBlocks.YELLOW_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE))
    );
    public static final RegistryObject<Block> YELLOW_ROCKRETE_SLAB = registerBlock("yellow_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> YELLOW_ROCKRETE_PILLAR = registerBlock("yellow_rockrete_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    //BLUE ROCKRETE
    public static final RegistryObject<Block> BLUE_ROCKRETE = registerBlock("blue_rockrete",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> BLUE_ROCKRETE_REBAR = registerBlock("blue_rockrete_rebar",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> BLUE_ROCKRETE_STAIRS = registerBlock("blue_rockrete_stairs",
            () -> new StairBlock(() -> ModBlocks.BLUE_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE))
    );
    public static final RegistryObject<Block> BLUE_ROCKRETE_SLAB = registerBlock("blue_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> BLUE_ROCKRETE_PILLAR = registerBlock("blue_rockrete_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );

    public static final RegistryObject<Block> HAZARD_STRIPE_YELLOW = registerBlock("hazard_stripe_yellow",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    )
    );
    public static final RegistryObject<Block> HAZARD_STRIPE_RED = registerBlock("hazard_stripe_red",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    )
    );
    public static final RegistryObject<Block> GRIMY_RESTROOM_TILE = registerBlock("grimy_restroom_tile",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    )
    );


    //PIPE BLOCKS HERE
    public static final RegistryObject<Block> PIPEWORKS = registerBlock("pipeworks",
            () -> new FacingFallableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> COPPER_PIPE_CONDUIT = registerBlock("copper_pipe_conduit",
            () -> new PipeBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    
                    .noOcclusion())
    );
    public static final RegistryObject<Block> COPPER_PIPE_CONDUIT_PLANAR_CORNER = registerBlock("copper_pipe_conduit_planar_corner",
            () -> new PipePlanarCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    
                    .noOcclusion())
    );
    public static final RegistryObject<Block> COPPER_PIPE_CONDUIT_INNER_CORNER = registerBlock("copper_pipe_conduit_inner_corner",
            () -> new InnerCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    
                    .noOcclusion(),
                    HitboxGeometryCollection.INNER_CORNER_UP(),
                    HitboxGeometryCollection.INNER_CORNER_DOWN(),
                    HitboxGeometryCollection.INNER_CORNER_SIDE())
    );
    public static final RegistryObject<Block> COPPER_PIPE_CONDUIT_OUTER_CORNER = registerBlock("copper_pipe_conduit_outer_corner",
            () -> new InnerCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    
                    .noOcclusion(),
                    HitboxGeometryCollection.OUTER_CORNER_UP(),
                    HitboxGeometryCollection.OUTER_CORNER_DOWN(),
                    HitboxGeometryCollection.OUTER_CORNER_SIDE())
    );
    public static final RegistryObject<Block> BRASS_PIPE_CONDUIT = registerBlock("brass_pipe_conduit",
            () -> new PipeBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion())
    );
    public static final RegistryObject<Block> BRASS_PIPE_CONDUIT_PLANAR_CORNER = registerBlock("brass_pipe_conduit_planar_corner",
            () -> new PipePlanarCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion())
    );
    public static final RegistryObject<Block> BRASS_PIPE_CONDUIT_INNER_CORNER = registerBlock("brass_pipe_conduit_inner_corner",
            () -> new InnerCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.INNER_CORNER_UP(),
                    HitboxGeometryCollection.INNER_CORNER_DOWN(),
                    HitboxGeometryCollection.INNER_CORNER_SIDE())
    );
    public static final RegistryObject<Block> BRASS_PIPE_CONDUIT_OUTER_CORNER = registerBlock("brass_pipe_conduit_outer_corner",
            () -> new InnerCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.OUTER_CORNER_UP(),
                    HitboxGeometryCollection.OUTER_CORNER_DOWN(),
                    HitboxGeometryCollection.OUTER_CORNER_SIDE())
    );
    public static final RegistryObject<Block> GRAY_PIPE_CONDUIT = registerBlock("gray_pipe_conduit",
            () -> new PipeBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion())
    );
    public static final RegistryObject<Block> GRAY_PIPE_CONDUIT_PLANAR_CORNER = registerBlock("gray_pipe_conduit_planar_corner",
            () -> new PipePlanarCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion())
    );
    public static final RegistryObject<Block> GRAY_PIPE_CONDUIT_INNER_CORNER = registerBlock("gray_pipe_conduit_inner_corner",
            () -> new InnerCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.INNER_CORNER_UP(),
                    HitboxGeometryCollection.INNER_CORNER_DOWN(),
                    HitboxGeometryCollection.INNER_CORNER_SIDE())
    );
    public static final RegistryObject<Block> GRAY_PIPE_CONDUIT_OUTER_CORNER = registerBlock("gray_pipe_conduit_outer_corner",
            () -> new InnerCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.OUTER_CORNER_UP(),
                    HitboxGeometryCollection.OUTER_CORNER_DOWN(),
                    HitboxGeometryCollection.OUTER_CORNER_SIDE())
    );

    //FURNITURE BLOCKS HERE
    public static final RegistryObject<Block> GRAY_BOLTED_BRACKET = registerBlock("gray_bolted_bracket",
            () -> new InnerCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.INNER_CORNER_UP(),
                    HitboxGeometryCollection.INNER_CORNER_DOWN(),
                    HitboxGeometryCollection.INNER_CORNER_SIDE())
    );
    public static final RegistryObject<Block> BLACK_BOLTED_BRACKET = registerBlock("black_bolted_bracket",
            () -> new InnerCornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.INNER_CORNER_UP(),
                    HitboxGeometryCollection.INNER_CORNER_DOWN(),
                    HitboxGeometryCollection.INNER_CORNER_SIDE())
    );
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
            () -> new ConnectedFurniture18SlotStorageBlock(BlockBehaviour
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
            () -> new ConnectedFurniture18SlotStorageBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    ModTags.Blocks.METAL_DESK,
                    HitboxGeometryCollection.METAL_DESK_DRAWER_SOLO_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_LEFT_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_RIGHT_SHAPE())

    );
    public static final RegistryObject<Block> METAL_DESK_DRAWER_2 = registerBlock("metal_desk_drawer_2",
            () -> new ConnectedFurniture18SlotStorageBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    ModTags.Blocks.METAL_DESK,
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_SOLO_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_LEFT_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_RIGHT_SHAPE())
    );
    public static final RegistryObject<Block> TOILET = registerBlock("toilet",
            () -> new ToiletBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.TOILET_NORTH())
    );

    public static final RegistryObject<Block> SINK = registerBlock("sink",
            () -> new ModelledFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.UPPER_SLAB())
    );

    public static final RegistryObject<Block> RED_WALL_MEDKIT = registerBlock("red_wall_medkit",
            () -> new Furniture9SlotStorageBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.RED_MEDKIT_NORTH()
            )
    );
    public static final RegistryObject<Block> WHITE_WALL_MEDKIT = registerBlock("white_wall_medkit",
            () -> new Furniture9SlotStorageBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.WHITE_MEDKIT_NORTH()
            )
    );
    public static final RegistryObject<Block> RETRO_COMPUTER = registerBlock("retro_computer",
            () -> new InteractableModelledFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion()
                    .lightLevel(state -> state.getValue(POWERED) ? 7 : 0),
                    HitboxGeometryCollection.RETRO_COMPUTER()
            )
    );
    public static final RegistryObject<Block> CASSETTE_PLAYER = registerBlock("cassette_player",
            () -> new InteractableModelledFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.CASSETTE_PLAYER()
            )
    );
    public static final RegistryObject<Block> YELLOW_TRIPOD = registerBlock("yellow_tripod",
            () -> new ModelledWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    HitboxGeometryCollection.THIN_VERTICAL_ROD_SHAPE()
            )
    );
    public static final RegistryObject<Block> WORK_LIGHT_MOUNT = registerBlock("work_light_mount",
            () -> new InteractableModelledFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .lightLevel(state -> state.getValue(POWERED) ? 15 : 0),
                    HitboxGeometryCollection.WORK_LIGHT_MOUNT_SHAPE()
            )
    );
    public static final RegistryObject<Block> FLOOR_WORK_LIGHT = registerBlock("floor_work_light",
            () -> new InteractableModelledFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .lightLevel(state -> state.getValue(POWERED) ? 15 : 0),
                    HitboxGeometryCollection.FLOOR_WORK_LIGHT_SHAPE()
            )
    );

    //CTM BLOCKS HERE (3RD PARTY TEXTURE DEPENDENCIES) VVV
    public static final RegistryObject<Block> ENCASED_CABLES = registerBlock("encased_cables",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> GRATE = registerBlock("grate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> GRAY_GRATE = registerBlock("gray_grate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> SEETHROUGH_GRATE = registerBlock("see-through_grate",
            () -> new GlassBlock(BlockBehaviour
                    .Properties.copy(Blocks.GLASS)
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)
            )
    );
//    public static final RegistryObject<Block> SEETHROUGH_GRATE_PANE = registerBlock("see-through_grate_pane",
//            () -> new IronBarsBlock(BlockBehaviour
//                    .Properties.copy(Blocks.GLASS)
//                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)
//            )
//    );

    public static final RegistryObject<Block> GRAY_SEETHROUGH_GRATE = registerBlock("gray_see-through_grate",
            () -> new GlassBlock(BlockBehaviour
                    .Properties.copy(Blocks.GLASS)
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> GRAY_SEETHROUGH_GRATE_PANE = registerBlock("gray_see-through_grate_pane",
            () -> new IronBarsBlock(BlockBehaviour
                    .Properties.copy(Blocks.GLASS_PANE)
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> RUSTY_GRATE = registerBlock("rusty_grate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> VERTICAL_RIVETED_VESSELPLATE = registerBlock("vertical_riveted_vesselplate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> GRAY_VERTICAL_RIVETED_VESSELPLATE = registerBlock("gray_vertical_riveted_vesselplate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> HORIZONTAL_RIVETED_VESSELPLATE = registerBlock("horizontal_riveted_vesselplate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> GRAY_HORIZONTAL_RIVETED_VESSELPLATE = registerBlock("gray_horizontal_riveted_vesselplate",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> REINFORCED_VESSELGLASS = registerBlock("reinforced_vesselglass",
            () -> new GlassBlock(BlockBehaviour
                    .Properties
                    .copy(Blocks.GLASS)
            )
    );

    public static final RegistryObject<Block> VESSELGLASS = registerBlock("vesselglass",
            () -> new GlassBlock(BlockBehaviour
                    .Properties.copy(Blocks.GLASS)
            )
    );

    public static final RegistryObject<Block> GRAY_REINFORCED_VESSELGLASS = registerBlock("gray_reinforced_vesselglass",
            () -> new GlassBlock(BlockBehaviour
                    .Properties
                    .copy(Blocks.GLASS)
            )
    );
    public static final RegistryObject<Block> GRAY_VESSELGLASS = registerBlock("gray_vesselglass",
            () -> new GlassBlock(BlockBehaviour
                    .Properties.copy(Blocks.GLASS)
            )
    );
    public static final RegistryObject<Block> SMOOTH_VESSELPLATE_TILE = registerBlock("smooth_vesselplate_tile",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> SMOOTH_GRAY_VESSELPLATE_TILE = registerBlock("smooth_gray_vesselplate_tile",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> STRUT = registerBlock("strut",
            () -> new SimpleWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)
                    )
    );
    public static final RegistryObject<Block> CATWALK_STRUT = registerBlock("catwalk_strut",
            () -> new SimpleWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> CATWALK_STRUT_STAIRS = registerBlock("catwalk_strut_stairs",
            () -> new StairBlock(() -> ModBlocks.CATWALK_STRUT.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> CATWALK_STRUT_SLAB = registerBlock("catwalk_strut_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> STRUT_STAIRS = registerBlock("strut_stairs",
            () -> new StairBlock(() -> ModBlocks.STRUT.get().defaultBlockState(),
                    BlockBehaviour
                            .Properties.copy(Blocks.STONE)
                            .noOcclusion()
                            .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> STRUT_SLAB = registerBlock("strut_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );

    public static final RegistryObject<Block> GRAY_STRUT = registerBlock("gray_strut",
            () -> new SimpleWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> GRAY_CATWALK_STRUT_STAIRS = registerBlock("gray_catwalk_strut_stairs",
            () -> new StairBlock(() -> ModBlocks.CATWALK_STRUT.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> GRAY_STRUT_SLAB = registerBlock("gray_strut_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> GRAY_CATWALK_STRUT = registerBlock("gray_catwalk_strut",
            () -> new SimpleWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    
                    .noOcclusion()
                    .sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS)
            )
    );

    public static final RegistryObject<Block> GRAY_CATWALK_STRUT_SLAB = registerBlock("gray_catwalk_strut_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> GRAY_STRUT_STAIRS = registerBlock("gray_strut_stairs",
            () -> new StairBlock(() -> ModBlocks.CATWALK_STRUT.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().sound(ModSounds.VESSELPLATE_BLOCK_SOUNDS))
    );

    //SPECIAL BLOCKS HERE VVV

    public static final RegistryObject<Block> IHEA_FURNITURE_KIT = registerBlock("ihea_furniture_kit",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion()
            )
    );

    public static final RegistryObject<Block> SAFETY_FURNISHINGS = registerBlock("safety_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> HYGIENE_FURNISHINGS = registerBlock("hygiene_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> INDUSTRIAL_FURNISHINGS = registerBlock("industrial_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> TECHNOLOGY_FURNISHINGS = registerBlock("technology_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> AMENITY_FURNISHINGS = registerBlock("amenity_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
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


