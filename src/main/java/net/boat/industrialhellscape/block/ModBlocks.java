package net.boat.industrialhellscape.block;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.modded_block_classes.ConnectedBlocks.AxialPillarBlock;
import net.boat.industrialhellscape.block.modded_block_classes.ConnectedBlocks.ConnectedFurnitureBlock;
import net.boat.industrialhellscape.block.modded_block_classes.ContainerBlocks.*;
import net.boat.industrialhellscape.block.modded_block_classes.Experimental.RecyclingBlock;
import net.boat.industrialhellscape.block.modded_block_classes.FallableBlocks.FacingFallableBlock;
import net.boat.industrialhellscape.block.modded_block_classes.FallableBlocks.FallableBlock;
import net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks.ToiletBlock;
import net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks.Modelled2BMBlock;
import net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks.SoundModelled2BMBlock;
import net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks.ModdedBedBlock;
import net.boat.industrialhellscape.block.modded_block_classes.PlacedFacingBlocks.SittableFacingBlock;
import net.boat.industrialhellscape.block.modded_block_classes.RailingBlocks.RailingBlock;
import net.boat.industrialhellscape.block.modded_block_classes.RailingBlocks.StairRailingBlock;
import net.boat.industrialhellscape.block.modded_block_classes.SurfaceMountBlocks.*;
import net.boat.industrialhellscape.block.modded_block_classes.PlacedFacingBlocks.InteractableModelledFacingBlock;
import net.boat.industrialhellscape.block.modded_block_classes.PlacedFacingBlocks.ModelledFacingBlock;
import net.boat.industrialhellscape.block.modded_block_classes.PlacedFacingBlocks.SimpleFacingBlock;
import net.boat.industrialhellscape.block.modded_block_classes.SimpleWaterloggableBlocks.ModelledWaterloggableBlock;
import net.boat.industrialhellscape.block.modded_block_classes.SimpleWaterloggableBlocks.SimpleWaterloggableBlock;
import net.boat.industrialhellscape.block.modded_block_classes.TextureToggleBlocks.DirectionalTextureToggleBlock;
import net.boat.industrialhellscape.block.modded_block_classes.TextureToggleBlocks.SimpleTextureToggleBlock;
import net.boat.industrialhellscape.block.modded_interfaces.HitboxGeometryCollection;
import net.boat.industrialhellscape.block.modded_logic_enums.MultiBlockPlacementDirection;
import net.boat.industrialhellscape.sound.ModSounds;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.sounds.SoundEvents;
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

//THIS JAVA CLASS HANDLES BLOCK REGISTRATION AND THEIR CORRESPONDING BLOCK ITEM REGISTRATION UNLESS OTHERWISE SPECIFIED.
// Using the methods:
// - registerBlockAndBlockItem
// - registerBlockOnly

public class ModBlocks {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED; //For configuring light-emitting blocks
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, IndustrialHellscape.MOD_ID);

    //DEBUG BLOCKS:
    public static final RegistryObject<Block> PROTOTYPE_MACHINE = registerBlockAndBlockItem("prototype_machine",
            () -> new RecyclingBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );

    //JOKE BLOCKS
    public static final RegistryObject<Block> BODY_PILLOW = registerBlockAndBlockItem("body_pillow",
            () -> new ModdedBedBlock(BlockBehaviour
                    .Properties.copy(Blocks.WHITE_WOOL)
                    .sound(SoundType.SLIME_BLOCK)
                    .noOcclusion(),
                    HitboxGeometryCollection.PILLOW_POSITIVE(),
                    HitboxGeometryCollection.PILLOW_NEGATIVE()
            )
    );

    //HVAC
    public static final RegistryObject<Block> DUCT = registerBlockAndBlockItem("duct",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> RUSTY_DUCT = registerBlockAndBlockItem("rusty_duct",
            () -> new Block(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );

    //METALWORKS
    public static final RegistryObject<Block> METALWORKS = registerBlockOnly("metalworks",
            () -> new FallableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );

    public static final RegistryObject<Block> YELLOW_RAILING = registerBlockAndBlockItem("yellow_railing",
            () -> new RailingBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> YELLOW_STAIR_RAILING = registerBlockAndBlockItem("yellow_stair_railing",
            () -> new StairRailingBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> GRAY_RAILING = registerBlockAndBlockItem("gray_railing",
            () -> new RailingBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> GRAY_STAIR_RAILING = registerBlockAndBlockItem("gray_stair_railing",
            () -> new StairRailingBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> BLACK_RAILING = registerBlockAndBlockItem("black_railing",
            () -> new RailingBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> BLACK_STAIR_RAILING = registerBlockAndBlockItem("black_stair_railing",
            () -> new StairRailingBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> RUSTY_RAILING = registerBlockAndBlockItem("rusty_railing",
            () -> new RailingBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> RUSTY_STAIR_RAILING = registerBlockAndBlockItem("rusty_stair_railing",
            () -> new StairRailingBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
            )
    );

    public static final RegistryObject<Block> GRAY_BOLTED_BRACKET = registerBlockAndBlockItem("gray_bolted_bracket",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.SHAPE_BOLTED_BRACKET()
            )
    );
    public static final RegistryObject<Block> BLACK_BOLTED_BRACKET = registerBlockAndBlockItem("black_bolted_bracket",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.SHAPE_BOLTED_BRACKET()
            )
    );
    public static final RegistryObject<Block> RUSTY_BOLTED_BRACKET = registerBlockAndBlockItem("rusty_bolted_bracket",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.SHAPE_BOLTED_BRACKET()
                    )
    );


    //VESSELPLATE BLOCKS
    public static final RegistryObject<Block> RIVETED_VESSELPLATE_PANEL = registerBlockOnly("riveted_vesselplate_panel",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    )
    );
    public static final RegistryObject<Block> VESSELPLATE_PILLAR = registerBlockAndBlockItem("vesselplate_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> DIRECTIONAL_RIVETED_VESSELPLATE = registerBlockAndBlockItem("directional_riveted_vesselplate",
            () -> new DirectionalTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> SMOOTH_VESSELPLATE_TILE = registerBlockAndBlockItem("smooth_vesselplate_tile",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> VESSELPLATE_SHEETING = registerBlockAndBlockItem("vesselplate_sheeting",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
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
    public static final RegistryObject<Block> GRAY_DIRECTIONAL_RIVETED_VESSELPLATE = registerBlockAndBlockItem("gray_directional_riveted_vesselplate",
            () -> new DirectionalTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> SMOOTH_GRAY_VESSELPLATE_TILE = registerBlockAndBlockItem("smooth_gray_vesselplate_tile",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> SMOOTH_GRAY_VESSELPLATE_STAIRS = registerBlockAndBlockItem("smooth_gray_vesselplate_stairs",
            () -> new StairBlock(Blocks.IRON_BLOCK::defaultBlockState,
                    BlockBehaviour
                            .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> SMOOTH_GRAY_VESSELPLATE_SLAB = registerBlockAndBlockItem("smooth_gray_vesselplate_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> GRAY_VESSELPLATE_SHEETING = registerBlockAndBlockItem("gray_vesselplate_sheeting",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> RUSTY_VESSELPLATE_SHEETING = registerBlockAndBlockItem("rusty_vesselplate_sheeting",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> RUSTY_VESSELPLATE_SHEETING_STAIRS = registerBlockAndBlockItem("rusty_vesselplate_sheeting_stairs",
            () -> new StairBlock(Blocks.IRON_BLOCK::defaultBlockState,
                    BlockBehaviour
                            .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> RUSTY_VESSELPLATE_SHEETING_SLAB = registerBlockAndBlockItem("rusty_vesselplate_sheeting_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> RUSTY_RIVETED_VESSELPLATE_PANEL = registerBlockAndBlockItem("rusty_riveted_vesselplate_panel",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> RUSTY_RIVETED_VESSELPLATE_STAIRS = registerBlockAndBlockItem("rusty_riveted_vesselplate_stairs",
            () -> new StairBlock(Blocks.IRON_BLOCK::defaultBlockState, //USING A SIMPLETEXTURETOGGLEBLOCK WILL CAUSE TOOLS TO CHANGE THE STAIR BLOCK TO THAT BLOCK
                    BlockBehaviour
                            .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> RUSTY_RIVETED_VESSELPLATE_SLAB = registerBlockAndBlockItem("rusty_riveted_vesselplate_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> RUSTY_VESSELPLATE_PILLAR = registerBlockAndBlockItem("rusty_vesselplate_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> RUSTY_DIRECTIONAL_RIVETED_VESSELPLATE = registerBlockAndBlockItem("rusty_directional_riveted_vesselplate",
            () -> new DirectionalTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> SMOOTH_RUSTY_VESSELPLATE_TILE = registerBlockAndBlockItem("smooth_rusty_vesselplate_tile",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> SMOOTH_RUSTY_VESSELPLATE_STAIRS = registerBlockAndBlockItem("smooth_rusty_vesselplate_stairs",
            () -> new StairBlock(Blocks.IRON_BLOCK::defaultBlockState,
                    BlockBehaviour
                            .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> SMOOTH_RUSTY_VESSELPLATE_SLAB = registerBlockAndBlockItem("smooth_rusty_vesselplate_slab",
            () -> new SlabBlock(BlockBehaviour
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
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> GRAY_GRATE = registerBlockAndBlockItem("gray_grate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> SEETHROUGH_GRATE = registerBlockAndBlockItem("see-through_grate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.GLASS)
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> GRAY_SEETHROUGH_GRATE = registerBlockAndBlockItem("gray_see-through_grate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.GLASS)
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> RUSTY_GRATE = registerBlockAndBlockItem("rusty_grate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> RUSTY_SEETHROUGH_GRATE = registerBlockAndBlockItem("rusty_see-through_grate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.GLASS)
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS)
            )
    );

    //STRUT BLOCKS
    public static final RegistryObject<Block> STRUT = registerBlockAndBlockItem("strut",
            () -> new SimpleWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> CATWALK_STRUT = registerBlockAndBlockItem("catwalk_strut",
            () -> new SimpleWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> CATWALK_STRUT_STAIRS = registerBlockAndBlockItem("catwalk_strut_stairs",
            () -> new StairBlock(() -> ModBlocks.CATWALK_STRUT.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );

    public static final RegistryObject<Block> CATWALK_STRUT_SLAB = registerBlockAndBlockItem("catwalk_strut_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> STRUT_STAIRS = registerBlockAndBlockItem("strut_stairs",
            () -> new StairBlock(() -> ModBlocks.STRUT.get().defaultBlockState(),
                    BlockBehaviour
                            .Properties.copy(Blocks.STONE)
                            .noOcclusion()
                            .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> STRUT_SLAB = registerBlockAndBlockItem("strut_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );

    public static final RegistryObject<Block> GRAY_STRUT = registerBlockAndBlockItem("gray_strut",
            () -> new SimpleWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> GRAY_CATWALK_STRUT_STAIRS = registerBlockAndBlockItem("gray_catwalk_strut_stairs",
            () -> new StairBlock(() -> ModBlocks.CATWALK_STRUT.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> GRAY_CATWALK_STRUT_SLAB = registerBlockAndBlockItem("gray_catwalk_strut_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> GRAY_STRUT_STAIRS = registerBlockAndBlockItem("gray_strut_stairs",
            () -> new StairBlock(() -> ModBlocks.CATWALK_STRUT.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> GRAY_STRUT_SLAB = registerBlockAndBlockItem("gray_strut_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> GRAY_CATWALK_STRUT = registerBlockAndBlockItem("gray_catwalk_strut",
            () -> new SimpleWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> RUSTY_STRUT = registerBlockAndBlockItem("rusty_strut",
            () -> new SimpleWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> RUSTY_STRUT_SLAB = registerBlockAndBlockItem("rusty_strut_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> RUSTY_STRUT_STAIRS = registerBlockAndBlockItem("rusty_strut_stairs",
            () -> new StairBlock(() -> ModBlocks.CATWALK_STRUT.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );

    public static final RegistryObject<Block> RUSTY_CATWALK_STRUT = registerBlockAndBlockItem("rusty_catwalk_strut",
            () -> new SimpleWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS)
            )
    );
    public static final RegistryObject<Block> RUSTY_CATWALK_STRUT_STAIRS = registerBlockAndBlockItem("rusty_catwalk_strut_stairs",
            () -> new StairBlock(() -> ModBlocks.CATWALK_STRUT.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );
    public static final RegistryObject<Block> RUSTY_CATWALK_STRUT_SLAB = registerBlockAndBlockItem("rusty_catwalk_strut_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );
    //VESSELPLATE
    public static final RegistryObject<Block> RIVETED_VESSELPLATE_STAIRS = registerBlockAndBlockItem("riveted_vesselplate_stairs",
            () -> new StairBlock(Blocks.IRON_BLOCK::defaultBlockState, //USING A SIMPLETEXTURETOGGLEBLOCK WILL CAUSE TOOLS TO CHANGE THE STAIR BLOCK TO THAT BLOCK
                    BlockBehaviour
                            .Properties.copy(Blocks.IRON_BLOCK)
                            .noOcclusion()
            )
    );
    public static final RegistryObject<Block> RIVETED_VESSELPLATE_SLAB = registerBlockAndBlockItem("riveted_vesselplate_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
            )
    );
    public static final RegistryObject<Block> GRAY_RIVETED_VESSELPLATE_STAIRS = registerBlockAndBlockItem("gray_riveted_vesselplate_stairs",
            () -> new StairBlock(Blocks.IRON_BLOCK::defaultBlockState,
                    BlockBehaviour
                            .Properties.copy(Blocks.IRON_BLOCK)
                            .noOcclusion()
            )
    );
    public static final RegistryObject<Block> GRAY_RIVETED_VESSELPLATE_SLAB = registerBlockAndBlockItem("gray_riveted_vesselplate_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> SMOOTH_VESSELPLATE_STAIRS = registerBlockAndBlockItem("smooth_vesselplate_stairs",
            () -> new StairBlock(Blocks.IRON_BLOCK::defaultBlockState,
                    BlockBehaviour
                            .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> SMOOTH_VESSELPLATE_SLAB = registerBlockAndBlockItem("smooth_vesselplate_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> VESSELPLATE_SHEETING_STAIRS = registerBlockAndBlockItem("vesselplate_sheeting_stairs",
            () -> new StairBlock(Blocks.IRON_BLOCK::defaultBlockState,
                    BlockBehaviour
                            .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> VESSELPLATE_SHEETING_SLAB = registerBlockAndBlockItem("vesselplate_sheeting_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );

    public static final RegistryObject<Block> GRAY_VESSELPLATE_SHEETING_STAIRS = registerBlockAndBlockItem("gray_vesselplate_sheeting_stairs",
            () -> new StairBlock(Blocks.IRON_BLOCK::defaultBlockState,
                    BlockBehaviour
                            .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> GRAY_VESSELPLATE_SHEETING_SLAB = registerBlockAndBlockItem("gray_vesselplate_sheeting_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
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
    public static final RegistryObject<Block> GREEN_ROCKRETE = registerBlockOnly("green_rockrete",
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
    public static final RegistryObject<Block> YELLOW_ROCKRETE = registerBlockOnly("yellow_rockrete",
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
    public static final RegistryObject<Block> BLUE_ROCKRETE = registerBlockOnly("blue_rockrete",
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
    //RED ROCKRETE
    public static final RegistryObject<Block> RED_ROCKRETE = registerBlockOnly("red_rockrete",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> RED_ROCKRETE_STAIRS = registerBlockAndBlockItem("red_rockrete_stairs",
            () -> new StairBlock(() -> ModBlocks.RED_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.STONE))
    );
    public static final RegistryObject<Block> RED_ROCKRETE_SLAB = registerBlockAndBlockItem("red_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> RED_ROCKRETE_PILLAR = registerBlockAndBlockItem("red_rockrete_pillar",
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
    public static final RegistryObject<Block> PIPEWORKS = registerBlockOnly("pipeworks",
            () -> new FacingFallableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
            )
    );
    public static final RegistryObject<Block> COPPER_PIPE_CONDUIT = registerBlockAndBlockItem("copper_pipe_conduit",
            () -> new SurfaceMountAxisRotatableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    
                    .noOcclusion())
    );
    public static final RegistryObject<Block> COPPER_PIPE_CONDUIT_PLANAR_CORNER = registerBlockAndBlockItem("copper_pipe_conduit_planar_corner",
            () -> new SurfaceMountRotatableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    
                    .noOcclusion())
    );
    public static final RegistryObject<Block> COPPER_PIPE_CONDUIT_INNER_CORNER = registerBlockAndBlockItem("copper_pipe_conduit_inner_corner",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    
                    .noOcclusion(),
                    HitboxGeometryCollection.SHAPE_PIPE_CONDUIT_INNER()
            )
    );
    public static final RegistryObject<Block> COPPER_PIPE_CONDUIT_OUTER_CORNER = registerBlockAndBlockItem("copper_pipe_conduit_outer_corner",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    
                    .noOcclusion(),
                    HitboxGeometryCollection.SHAPE_PIPE_CONDUIT_OUTER()
                    )
    );
    public static final RegistryObject<Block> BRASS_PIPE_CONDUIT = registerBlockAndBlockItem("brass_pipe_conduit",
            () -> new SurfaceMountAxisRotatableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion())
    );
    public static final RegistryObject<Block> BRASS_PIPE_CONDUIT_PLANAR_CORNER = registerBlockAndBlockItem("brass_pipe_conduit_planar_corner",
            () -> new SurfaceMountRotatableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion())
    );
    public static final RegistryObject<Block> BRASS_PIPE_CONDUIT_INNER_CORNER = registerBlockAndBlockItem("brass_pipe_conduit_inner_corner",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.SHAPE_PIPE_CONDUIT_INNER()
            )
    );
    public static final RegistryObject<Block> BRASS_PIPE_CONDUIT_OUTER_CORNER = registerBlockAndBlockItem("brass_pipe_conduit_outer_corner",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.SHAPE_PIPE_CONDUIT_OUTER()
                    )
    );
    public static final RegistryObject<Block> GRAY_PIPE_CONDUIT = registerBlockAndBlockItem("gray_pipe_conduit",
            () -> new SurfaceMountAxisRotatableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion())
    );
    public static final RegistryObject<Block> GRAY_PIPE_CONDUIT_PLANAR_CORNER = registerBlockAndBlockItem("gray_pipe_conduit_planar_corner",
            () -> new SurfaceMountRotatableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion())
    );
    public static final RegistryObject<Block> GRAY_PIPE_CONDUIT_INNER_CORNER = registerBlockAndBlockItem("gray_pipe_conduit_inner_corner",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.SHAPE_PIPE_CONDUIT_INNER()
            )
    );
    public static final RegistryObject<Block> GRAY_PIPE_CONDUIT_OUTER_CORNER = registerBlockAndBlockItem("gray_pipe_conduit_outer_corner",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.SHAPE_PIPE_CONDUIT_OUTER()
                    )
    );

    //DOORS, TRAPDOORS, ETC:
    public static final RegistryObject<Block> DUCT_VENT = registerBlockAndBlockItem("duct_vent",
            () -> new ModelledSurfaceMountBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    HitboxGeometryCollection.PANEL_FLOOR(),
                    false
            )
    );
    public static final RegistryObject<Block> RUSTY_DUCT_VENT = registerBlockAndBlockItem("rusty_duct_vent",
            () -> new ModelledSurfaceMountBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    HitboxGeometryCollection.PANEL_FLOOR(),
                    false
            )
    );

    //FURNITURE BLOCKS HERE
    public static final RegistryObject<Block> LARGE_LOCKER = registerBlockAndBlockItem("large_locker",
            () -> new TwoBlockContainerMultiBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    , MultiBlockPlacementDirection.VERTICAL
                    , 54
                    , ModSounds.METAL_BOX_OPEN.get()
                    , ModSounds.METAL_BOX_CLOSE.get()
            )
    );
    public static final RegistryObject<Block> LOCKER_BOX = registerBlockAndBlockItem("locker_box",
            () -> new FacingContainerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK),
                    27,
                    ModSounds.METAL_BOX_OPEN.get(),
                    ModSounds.METAL_BOX_CLOSE.get()
            )
    );
    public static final RegistryObject<Block> FUEL_DRUM = registerBlockAndBlockItem("fuel_drum",
            () -> new SurfaceMountContainerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK), 27,
                    ModSounds.METAL_BOX_OPEN.get(),
                    ModSounds.METAL_BOX_CLOSE.get()
            )
    );
    public static final RegistryObject<Block> CCTV_CAMERA = registerBlockAndBlockItem("cctv_camera",
            () -> new ModelledSurfaceMountBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .lightLevel(state -> state.getValue(WATERLOGGED) ? 0 : 3),
                    HitboxGeometryCollection.DECAL_FLOOR(),
                    true
            )
    );
    public static final RegistryObject<Block> YELLOW_TRIPOD = registerBlockAndBlockItem("yellow_tripod",
            () -> new ModelledWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK),
                    HitboxGeometryCollection.THIN_VERTICAL_ROD_SHAPE()
            )
    );
    public static final RegistryObject<Block> YELLOW_STAND = registerBlockAndBlockItem("yellow_stand",
            () -> new ModelledWaterloggableBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK),
                    HitboxGeometryCollection.THIN_VERTICAL_ROD_SHAPE()
            )
    );
    public static final RegistryObject<Block> WORK_LIGHT_MOUNT = registerBlockAndBlockItem("work_light_mount",
            () -> new InteractableModelledFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
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

    public static final RegistryObject<Block> DESK = registerBlockAndBlockItem("desk",
            () -> new ConnectedFurnitureBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion(),
                    ModTags.Blocks.CLASSIC_DESK,
                    HitboxGeometryCollection.DESK_SOLO_SHAPE(),
                    HitboxGeometryCollection.DESK_LEFT_SHAPE(),
                    HitboxGeometryCollection.DESK_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.DESK_RIGHT_SHAPE(),
                    MultiBlockPlacementDirection.HORIZONTAL)

    );
    public static final RegistryObject<Block> DESK_DRAWER = registerBlockAndBlockItem("desk_drawer",
            () -> new ConnectedContainerBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion(),
                   18,
                    ModTags.Blocks.CLASSIC_DESK,
                    HitboxGeometryCollection.DESK_DRAWER_SOLO_SHAPE(),
                    HitboxGeometryCollection.DESK_DRAWER_LEFT_SHAPE(),
                    HitboxGeometryCollection.DESK_DRAWER_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.DESK_DRAWER_RIGHT_SHAPE(),
                    SoundEvents.BARREL_OPEN,
                    SoundEvents.BARREL_CLOSE,
                    MultiBlockPlacementDirection.HORIZONTAL
            )
    );
    public static final RegistryObject<Block> METAL_DESK = registerBlockAndBlockItem("metal_desk",
            () -> new ConnectedFurnitureBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    ModTags.Blocks.METAL_DESK,
                    HitboxGeometryCollection.METAL_DESK_SOLO_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_LEFT_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_RIGHT_SHAPE(),
                    MultiBlockPlacementDirection.HORIZONTAL
            )
    );
    public static final RegistryObject<Block> METAL_DESK_DRAWER = registerBlockAndBlockItem("metal_desk_drawer",
            () -> new ConnectedContainerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    18,
                    ModTags.Blocks.METAL_DESK,
                    HitboxGeometryCollection.METAL_DESK_DRAWER_SOLO_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_LEFT_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_RIGHT_SHAPE(),
                    ModSounds.METAL_BOX_OPEN.get(),
                    ModSounds.METAL_BOX_CLOSE.get(),
                    MultiBlockPlacementDirection.HORIZONTAL
                    )

    );
    public static final RegistryObject<Block> OFFICE_DESK_DRAWER = registerBlockAndBlockItem("office_desk_drawer",
            () -> new ConnectedContainerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    18,
                    ModTags.Blocks.OFFICE_DESK,
                    HitboxGeometryCollection.OFFICE_DESK_DRAWER_SHAPE(),
                    HitboxGeometryCollection.OFFICE_DESK_DRAWER_SHAPE(),
                    HitboxGeometryCollection.OFFICE_DESK_DRAWER_SHAPE(),
                    HitboxGeometryCollection.OFFICE_DESK_DRAWER_SHAPE(),
                    ModSounds.METAL_BOX_OPEN.get(),
                    ModSounds.METAL_BOX_CLOSE.get(),
                    MultiBlockPlacementDirection.HORIZONTAL
            )

    );
    public static final RegistryObject<Block> OFFICE_DESK = registerBlockAndBlockItem("office_desk",
            () -> new ConnectedFurnitureBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    ModTags.Blocks.OFFICE_DESK,
                    HitboxGeometryCollection.OFFICE_DESK_SOLO_SHAPE(),
                    HitboxGeometryCollection.OFFICE_DESK_LEFT_SHAPE(),
                    HitboxGeometryCollection.OFFICE_DESK_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.OFFICE_DESK_RIGHT_SHAPE(),

                    MultiBlockPlacementDirection.HORIZONTAL
            )

    );

    public static final RegistryObject<Block> METAL_DESK_DRAWER_2 = registerBlockAndBlockItem("metal_desk_drawer_2",
            () -> new ConnectedContainerBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    18,
                    ModTags.Blocks.METAL_DESK,
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_SOLO_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_LEFT_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_RIGHT_SHAPE(),
                    ModSounds.METAL_BOX_OPEN.get(),
                    ModSounds.METAL_BOX_CLOSE.get(),
                    MultiBlockPlacementDirection.HORIZONTAL
            )
    );
    public static final RegistryObject<Block> OFFICE_CHAIR = registerBlockAndBlockItem("office_chair",
            () -> new SittableFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion(),
                    HitboxGeometryCollection.OFFICE_CHAIR_SHAPE()
            )
    );
    public static final RegistryObject<Block> BLACK_OFFICE_CHAIR = registerBlockAndBlockItem("black_office_chair",
            () -> new SittableFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion(),
                    HitboxGeometryCollection.OFFICE_CHAIR_SHAPE()
            )
    );
    public static final RegistryObject<Block> TOILET = registerBlockAndBlockItem("toilet",
            () -> new ToiletBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    MultiBlockPlacementDirection.VERTICAL,
                    HitboxGeometryCollection.TOILET_POSITIVE(),
                    HitboxGeometryCollection.TOILET_NEGATIVE())
    );

    public static final RegistryObject<Block> SINK = registerBlockAndBlockItem("sink",
            () -> new ModelledFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.UPPER_SLAB())
    );
    public static final RegistryObject<Block> URINAL = registerBlockAndBlockItem("urinal",
            () -> new SoundModelled2BMBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    MultiBlockPlacementDirection.VERTICAL,
                    HitboxGeometryCollection.URINAL_POSITIVE(),
                    HitboxGeometryCollection.URINAL_NEGATIVE()
            )
    );

    public static final RegistryObject<Block> RED_WALL_MEDKIT = registerBlockAndBlockItem("red_wall_medkit",
            () -> new ModelledFacingContainerBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    9,
                    HitboxGeometryCollection.RED_MEDKIT_NORTH(),
                    ModSounds.METAL_BOX_OPEN.get(),
                    ModSounds.METAL_BOX_CLOSE.get()
            )
    );

    public static final RegistryObject<Block> WHITE_WALL_MEDKIT = registerBlockAndBlockItem("white_wall_medkit",
            () -> new ModelledFacingContainerBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    9,
                    HitboxGeometryCollection.WHITE_MEDKIT_NORTH(),
                    ModSounds.METAL_BOX_OPEN.get(),
                    ModSounds.METAL_BOX_CLOSE.get()
            )
    );
    public static final RegistryObject<Block> FIRE_EXTINGUISHER = registerBlockAndBlockItem("fire_extinguisher",
            () -> new ModelledFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    HitboxGeometryCollection.FIRE_EXTINGUISHER()
            )
    );
    public static final RegistryObject<Block> SMOKE_ALARM = registerBlockAndBlockItem("smoke_alarm",
            () -> new SmokeDetectorBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    HitboxGeometryCollection.SMOKE_DETECTOR_FLOOR(),
                    false
            )
    );
    public static final RegistryObject<Block> OPERATING_TABLE = registerBlockAndBlockItem("operating_table",
            () -> new ModdedBedBlock(BlockBehaviour
                    .Properties.copy(Blocks.IRON_BLOCK)
                    .sound(SoundType.METAL)
                    .noOcclusion(),
                    HitboxGeometryCollection.OPERATING_TABLE_POSITIVE(),
                    HitboxGeometryCollection.OPERATING_TABLE_NEGATIVE()
            )
    );
    public static final RegistryObject<Block> IV_DRIPSTAND = registerBlockAndBlockItem("iv_dripstand",
            () -> new Modelled2BMBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS)
                    .noOcclusion(),
                    MultiBlockPlacementDirection.VERTICAL,
                    HitboxGeometryCollection.THIN_VERTICAL_ROD_SHAPE(),
                    HitboxGeometryCollection.THIN_VERTICAL_ROD_SHAPE()
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
    public static final RegistryObject<Block> RETRO_COMPUTER_2 = registerBlockAndBlockItem("retro_computer_2",
            () -> new InteractableModelledFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion()
                    .lightLevel(state -> state.getValue(POWERED) ? 7 : 0),
                    HitboxGeometryCollection.RETRO_COMPUTER_2()
            )
    );
    public static final RegistryObject<Block> CASSETTE_PLAYER = registerBlockAndBlockItem("cassette_player",
            () -> new InteractableModelledFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.CASSETTE_PLAYER()
            )
    );

    //FURNITURE CATEGORY BLOCKS
    public static final RegistryObject<Block> IHEA_FURNITURE_KIT = registerBlockOnly("ihea_furniture_kit",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );

    public static final RegistryObject<Block> SAFETY_FURNISHINGS = registerBlockAndBlockItem("safety_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> HYGIENE_FURNISHINGS = registerBlockAndBlockItem("hygiene_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> INDUSTRIAL_FURNISHINGS = registerBlockAndBlockItem("industrial_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> TECHNOLOGY_FURNISHINGS = registerBlockAndBlockItem("technology_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> AMENITY_FURNISHINGS = registerBlockAndBlockItem("amenity_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.copy(Blocks.STONE)
            )
    );
    public static final RegistryObject<Block> POSTER_1 = registerBlockAndBlockItem("poster_1",
            () -> new PosterBlock(BlockBehaviour
                    .Properties.copy(Blocks.OAK_PLANKS).instabreak(),
                    HitboxGeometryCollection.DECAL_FLOOR(),
                    false,
                    "poster1.png",
                    128,
                    256
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


