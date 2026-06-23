package net.boat.industrialhellscape.block;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.modded_block_classes.ConnectedBlocks.AxialPillarBlock;
import net.boat.industrialhellscape.block.modded_block_classes.ConnectedBlocks.ConnectedFurnitureBlock;
import net.boat.industrialhellscape.block.modded_block_classes.PlacedFacingBlocks.*;
import net.boat.industrialhellscape.block.modded_block_classes.RailingBlocks.ParapetBlock;
import net.boat.industrialhellscape.block.modded_block_classes.RailingBlocks.RailingBlock;
import net.boat.industrialhellscape.block.modded_block_classes.StorageBlocks.*;
import net.boat.industrialhellscape.block.modded_block_classes.FallableBlocks.FacingFallableBlock;
import net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks.*;
import net.boat.industrialhellscape.block.modded_block_classes.RailingBlocks.StairRailingBlock;
import net.boat.industrialhellscape.block.modded_block_classes.SurfaceMountBlocks.*;
import net.boat.industrialhellscape.block.modded_block_classes.TextureToggleBlocks.SimpleTextureToggleBlock;
import net.boat.industrialhellscape.block.modded_block_classes.TextureToggleBlocks.TrussBlock;
import net.boat.industrialhellscape.block.modded_interfaces.HitboxGeometryCollection;
import net.boat.industrialhellscape.block.modded_logic_enums.MultiBlockPlacementDirection;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.sound.ModSounds;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

import static net.boat.industrialhellscape.item.ModItems.ITEMS;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(IndustrialHellscape.MOD_ID);

    public static void removeAndReplaceBlocks() { //Replaces both Block and Block Item with replacements.
        specificBlockReplacement("gray_rockrete", "smooth_gray_rockrete");
        specificBlockReplacement("red_rockrete", "smooth_red_rockrete");
        specificBlockReplacement("yellow_rockrete", "smooth_yellow_rockrete");
        specificBlockReplacement("blue_rockrete", "smooth_blue_rockrete");
        specificBlockReplacement("green_rockrete", "smooth_green_rockrete");

        specificBlockReplacement("gray_rockrete_slab", "smooth_gray_rockrete_slab");
        specificBlockReplacement("red_rockrete_slab", "smooth_red_rockrete_slab");
        specificBlockReplacement("yellow_rockrete_slab", "smooth_yellow_rockrete_slab");
        specificBlockReplacement("blue_rockrete_slab", "smooth_blue_rockrete_slab");
        specificBlockReplacement("green_rockrete_slab", "smooth_green_rockrete_slab");

        specificBlockReplacement("gray_rockrete_stairs", "smooth_gray_rockrete_stairs");
        specificBlockReplacement("red_rockrete_stairs", "smooth_red_rockrete_stairs");
        specificBlockReplacement("yellow_rockrete_stairs", "smooth_yellow_rockrete_stairs");
        specificBlockReplacement("blue_rockrete_stairs", "smooth_blue_rockrete_stairs");
        specificBlockReplacement("green_rockrete_stairs", "smooth_green_rockrete_stairs");
    }

    protected static ResourceLocation blockID(String blockID) {
        return ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, blockID);
    }

    protected static void specificBlockReplacement(String oldID, String newID) {
        BLOCKS.addAlias(
                blockID(oldID),
                blockID(newID)
        );
        ITEMS.addAlias(
                blockID(oldID),
                blockID(newID)
        );
    }

//    public static final DeferredBlock<Block> PROTOTYPE_MACHINE = registerBlockAndBlockItem("prototype_machine",
//            () -> new Block(BlockBehaviour
//                    .Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    //JOKE BLOCKS
    public static final DeferredBlock<Block> BODY_PILLOW = registerBlockAndBlockItem("body_pillow",
            () -> new ModdedBedBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.WHITE_WOOL)
                    .sound(SoundType.SLIME_BLOCK)
                    .noOcclusion(),
                    HitboxGeometryCollection.PILLOW_POSITIVE(),
                    HitboxGeometryCollection.PILLOW_NEGATIVE()
            )
    );

    //BASE BLOCKS
    public static final DeferredBlock<Block> IHEA_FURNITURE_KIT = registerBlockAndBlockItem("ihea_furniture_kit",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.OAK_PLANKS))
            {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.ihea_furniture_kit"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            }

    );
    public static final DeferredBlock<Block> METALWORKS = registerBlockAndBlockItem("metalworks",
            () -> new FacingFallableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), () -> SoundEvents.ANVIL_PLACE){
                @Override
                public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                    if (Screen.hasShiftDown()) {
                        tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.blockworks"));
                    } else {
                        tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.shift_down"));
                    }
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredBlock<Block> PIPEWORKS = registerBlockAndBlockItem("pipeworks",
            () -> new FacingFallableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), () -> ModSounds.METALPIPEFALLINGSOUNDEFFECT.get()) {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                    if (Screen.hasShiftDown()) {
                        tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.blockworks"));
                    } else {

                        tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.shift_down"));
                    }
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }

            });


    public static final DeferredBlock<Block> SAFETY_FURNISHINGS = registerBlockAndBlockItem("safety_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE))
            {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.ihea_furniture_kit"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            }
    );
    public static final DeferredBlock<Block> HYGIENE_FURNISHINGS = registerBlockAndBlockItem("hygiene_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE))
            {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.ihea_furniture_kit"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredBlock<Block> INDUSTRIAL_FURNISHINGS = registerBlockAndBlockItem("industrial_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE))
            {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.ihea_furniture_kit"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredBlock<Block> TECHNOLOGY_FURNISHINGS = registerBlockAndBlockItem("technology_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE))
            {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.ihea_furniture_kit"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredBlock<Block> AMENITY_FURNISHINGS = registerBlockAndBlockItem("amenity_furnishings",
            () -> new SimpleFacingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE))
            {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.ihea_furniture_kit"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });


    //DUCT BLOCKS
    public static final DeferredBlock<Block> DUCT = registerBlockAndBlockItem("duct",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK))
            {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.advise_stonecut"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            }
    );

    public static final DeferredBlock<Block> RUSTY_DUCT = registerBlockAndBlockItem("rusty_duct",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));

    //DUCT VENT BLOCKS
    public static final DeferredBlock<Block> DUCT_VENT = registerBlockAndBlockItem("duct_vent",
            () -> new ModelledSurfaceMountBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    HitboxGeometryCollection.PANEL_FLOOR()
            )
    );
    public static final DeferredBlock<Block> RUSTY_DUCT_VENT = registerBlockAndBlockItem("rusty_duct_vent",
            () -> new ModelledSurfaceMountBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    HitboxGeometryCollection.PANEL_FLOOR()
            )
    );

    //GRATE BLOCKS
    public static final DeferredBlock<Block> HORIZONTAL_GRATE = registerBlockAndBlockItem("horizontal_grate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS)
            )
    );
    public static final DeferredBlock<Block> VERTICAL_GRATE = registerBlockAndBlockItem("vertical_grate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS)
            )
            {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.advise_craft_vertical"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            }
    );
    public static final DeferredBlock<Block> HORIZONTAL_CUTOUT_GRATE = registerBlockAndBlockItem("horizontal_cutout_grate",
            () -> new TrussBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.GLASS)
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS),
                    false
            )
    );
    public static final DeferredBlock<Block> VERTICAL_CUTOUT_GRATE = registerBlockAndBlockItem("vertical_cutout_grate",
            () -> new TrussBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.GLASS)
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS),
                    false
            )
            {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.advise_craft_vertical"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            }
    );
//    public static final DeferredBlock<Block> GRAY_HORIZONTAL_GRATE = registerBlockAndBlockItem("gray_horizontal_grate",
//            () -> new TrussBlock(BlockBehaviour
//                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
//                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS),
//                    false
//            )
//    );
//    public static final DeferredBlock<Block> GRAY_VERTICAL_GRATE = registerBlockAndBlockItem("gray_vertical_grate",
//            () -> new TrussBlock(BlockBehaviour
//                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
//                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS),
//                    false
//            )
//    );
//    public static final DeferredBlock<Block> GRAY_HORIZONTAL_CUTOUT_GRATE = registerBlockAndBlockItem("gray_horizontal_cutout_grate",
//            () -> new TrussBlock(BlockBehaviour
//                    .Properties.ofFullCopy(Blocks.GLASS)
//                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS),
//                    false
//            )
//    );
//    public static final DeferredBlock<Block> GRAY_VERTICAL_CUTOUT_GRATE = registerBlockAndBlockItem("gray_vertical_cutout_grate",
//            () -> new TrussBlock(BlockBehaviour
//                    .Properties.ofFullCopy(Blocks.GLASS)
//                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS),
//                    false
//            )
//    );

    //RAILINGS
    public static final DeferredBlock<Block> YELLOW_RAILING = registerBlockAndBlockItem("yellow_railing",
            () -> new RailingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    2,
                    15
            )
    );
    public static final DeferredBlock<Block> YELLOW_STAIR_RAILING = registerBlockAndBlockItem("yellow_stair_railing",
            () -> new StairRailingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion()
            )
    );
    public static final DeferredBlock<Block> GRAY_RAILING = registerBlockAndBlockItem("gray_railing",
            () -> new RailingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    2,
                    15
            )
    );
    public static final DeferredBlock<Block> GRAY_STAIR_RAILING = registerBlockAndBlockItem("gray_stair_railing",
            () -> new StairRailingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion()
            )
    );
    public static final DeferredBlock<Block> BLACK_RAILING = registerBlockAndBlockItem("black_railing",
            () -> new RailingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    2,
                    15
            )
    );
    public static final DeferredBlock<Block> BLACK_STAIR_RAILING = registerBlockAndBlockItem("black_stair_railing",
            () -> new StairRailingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion()
            )
    );
    public static final DeferredBlock<Block> RUSTY_RAILING = registerBlockAndBlockItem("rusty_railing",
            () -> new RailingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    2,
                    15
            )
    );
    public static final DeferredBlock<Block> RUSTY_STAIR_RAILING = registerBlockAndBlockItem("rusty_stair_railing",
            () -> new StairRailingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion()
            )
    );
    public static final DeferredBlock<Block> GRAY_ROCKRETE_PARAPET = registerBlockAndBlockItem("gray_rockrete_parapet",
            () -> new ParapetBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    4,
                    0
            )
    );
    public static final DeferredBlock<Block> RED_ROCKRETE_PARAPET = registerBlockAndBlockItem("red_rockrete_parapet",
            () -> new ParapetBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    4,
                    0
            )
    );
    public static final DeferredBlock<Block> YELLOW_ROCKRETE_PARAPET = registerBlockAndBlockItem("yellow_rockrete_parapet",
            () -> new ParapetBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    4,
                    0
            )
    );
    public static final DeferredBlock<Block> BLUE_ROCKRETE_PARAPET = registerBlockAndBlockItem("blue_rockrete_parapet",
            () -> new ParapetBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    4,
                    0
            )
    );
    public static final DeferredBlock<Block> GREEN_ROCKRETE_PARAPET = registerBlockAndBlockItem("green_rockrete_parapet",
            () -> new ParapetBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    4,
                    0
            )
    );
    public static final DeferredBlock<WallBlock> GRAY_ROCKRETE_WALL = registerBlockAndBlockItem("gray_rockrete_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<WallBlock> RED_ROCKRETE_WALL = registerBlockAndBlockItem("red_rockrete_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<WallBlock> YELLOW_ROCKRETE_WALL = registerBlockAndBlockItem("yellow_rockrete_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<WallBlock> BLUE_ROCKRETE_WALL = registerBlockAndBlockItem("blue_rockrete_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));
    public static final DeferredBlock<WallBlock> GREEN_ROCKRETE_WALL = registerBlockAndBlockItem("green_rockrete_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of().strength(2f).requiresCorrectToolForDrops()));

    //VESSELPLATE BLOCKS
    public static final DeferredBlock<Block> RIVETED_VESSELPLATE = registerBlockAndBlockItem("riveted_vesselplate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
    );
    public static final DeferredBlock<Block> SMOOTH_VESSELPLATE = registerBlockAndBlockItem("smooth_vesselplate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
    );
    public static final DeferredBlock<Block> HORIZONTAL_VESSELPLATE = registerBlockAndBlockItem("horizontal_vesselplate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
    );
    public static final DeferredBlock<Block> VERTICAL_VESSELPLATE = registerBlockAndBlockItem("vertical_vesselplate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
            {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.advise_craft_vertical"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            }
    );
    public static final DeferredBlock<Block> HORIZONTAL_REINFORCED_VESSELPLATE = registerBlockAndBlockItem("horizontal_reinforced_vesselplate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
    );
    public static final DeferredBlock<Block> VERTICAL_REINFORCED_VESSELPLATE = registerBlockAndBlockItem("vertical_reinforced_vesselplate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
            {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.advise_craft_vertical"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            }
    );
    public static final DeferredBlock<Block> RIVETED_VESSELPLATE_STAIRS = registerBlockAndBlockItem("riveted_vesselplate_stairs",
            () -> new StairBlock(ModBlocks.RIVETED_VESSELPLATE.get().defaultBlockState(), //USING A SIMPLETEXTURETOGGLEBLOCK WILL CAUSE TOOLS TO CHANGE THE STAIR BLOCK TO THAT BLOCK
                    BlockBehaviour
                            .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                            .noOcclusion()
            )
    );
    public static final DeferredBlock<Block> RIVETED_VESSELPLATE_SLAB = registerBlockAndBlockItem("riveted_vesselplate_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion()
            )
    );
    public static final DeferredBlock<Block> SMOOTH_VESSELPLATE_STAIRS = registerBlockAndBlockItem("smooth_vesselplate_stairs",
            () -> new StairBlock(ModBlocks.SMOOTH_VESSELPLATE.get().defaultBlockState(),
                    BlockBehaviour
                            .Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
    );
    public static final DeferredBlock<Block> SMOOTH_VESSELPLATE_SLAB = registerBlockAndBlockItem("smooth_vesselplate_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
    );
    public static final DeferredBlock<Block> VESSELPLATE_PILLAR = registerBlockAndBlockItem("vesselplate_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
    );

    public static final DeferredBlock<Block> GRAY_RIVETED_VESSELPLATE = registerBlockAndBlockItem("gray_riveted_vesselplate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
    );
    public static final DeferredBlock<Block> SMOOTH_GRAY_VESSELPLATE = registerBlockAndBlockItem("smooth_gray_vesselplate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
    );
    public static final DeferredBlock<Block> GRAY_HORIZONTAL_VESSELPLATE = registerBlockAndBlockItem("gray_horizontal_vesselplate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
    );
    public static final DeferredBlock<Block> GRAY_VERTICAL_VESSELPLATE = registerBlockAndBlockItem("gray_vertical_vesselplate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
            {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.advise_craft_vertical"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            }
    );
    public static final DeferredBlock<Block> GRAY_HORIZONTAL_REINFORCED_VESSELPLATE = registerBlockAndBlockItem("gray_horizontal_reinforced_vesselplate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
    );
    public static final DeferredBlock<Block> GRAY_VERTICAL_REINFORCED_VESSELPLATE = registerBlockAndBlockItem("gray_vertical_reinforced_vesselplate",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
            {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.advise_craft_vertical"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            }
    );
    public static final DeferredBlock<Block> GRAY_RIVETED_VESSELPLATE_STAIRS = registerBlockAndBlockItem("gray_riveted_vesselplate_stairs",
            () -> new StairBlock(ModBlocks.GRAY_RIVETED_VESSELPLATE.get().defaultBlockState(),
                    BlockBehaviour
                            .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                            .noOcclusion()
            )
    );
    public static final DeferredBlock<Block> GRAY_RIVETED_VESSELPLATE_SLAB = registerBlockAndBlockItem("gray_riveted_vesselplate_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
    );
    public static final DeferredBlock<Block> SMOOTH_GRAY_VESSELPLATE_STAIRS = registerBlockAndBlockItem("smooth_gray_vesselplate_stairs",
            () -> new StairBlock(ModBlocks.SMOOTH_VESSELPLATE.get().defaultBlockState(),
                    BlockBehaviour
                            .Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
    );
    public static final DeferredBlock<Block> SMOOTH_GRAY_VESSELPLATE_SLAB = registerBlockAndBlockItem("smooth_gray_vesselplate_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
    );
    public static final DeferredBlock<Block> GRAY_VESSELPLATE_PILLAR = registerBlockAndBlockItem("gray_vesselplate_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
            )
    );
    
    //TRUSS BLOCKS
    public static final DeferredBlock<Block> TRUSS = registerBlockAndBlockItem("truss",
            () -> new TrussBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS),
                    true)
            {
                @Override
                public void appendHoverText(@NotNull ItemStack stack, Item.@NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.advise_stonecut"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            }
    );
    public static final DeferredBlock<Block> CATWALK_TRUSS = registerBlockAndBlockItem("catwalk_truss",
            () -> new TrussBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS),
                    true
            )
    );
    public static final DeferredBlock<Block> CATWALK_TRUSS_STAIRS = registerBlockAndBlockItem("catwalk_truss_stairs",
            () -> new StairBlock(ModBlocks.CATWALK_TRUSS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion().sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );

    public static final DeferredBlock<Block> CATWALK_TRUSS_SLAB = registerBlockAndBlockItem("catwalk_truss_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );
    public static final DeferredBlock<Block> TRUSS_STAIRS = registerBlockAndBlockItem("truss_stairs",
            () -> new StairBlock( ModBlocks.TRUSS.get().defaultBlockState(),
                    BlockBehaviour
                            .Properties.ofFullCopy(Blocks.STONE)
                            .noOcclusion()
                            .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );
    public static final DeferredBlock<Block> TRUSS_SLAB = registerBlockAndBlockItem("truss_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );

    public static final DeferredBlock<Block> GRAY_TRUSS = registerBlockAndBlockItem("gray_truss",
            () -> new TrussBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS),
                    true
            )
    );
    public static final DeferredBlock<Block> GRAY_CATWALK_TRUSS = registerBlockAndBlockItem("gray_catwalk_truss",
            () -> new TrussBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS),
                    true
            )
    );
    public static final DeferredBlock<Block> GRAY_CATWALK_TRUSS_STAIRS = registerBlockAndBlockItem("gray_catwalk_truss_stairs",
            () -> new StairBlock( ModBlocks.CATWALK_TRUSS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion().sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );
    public static final DeferredBlock<Block> GRAY_CATWALK_TRUSS_SLAB = registerBlockAndBlockItem("gray_catwalk_truss_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion().sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );
    public static final DeferredBlock<Block> GRAY_TRUSS_STAIRS = registerBlockAndBlockItem("gray_truss_stairs",
            () -> new StairBlock( ModBlocks.CATWALK_TRUSS.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noOcclusion().sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );
    public static final DeferredBlock<Block> GRAY_TRUSS_SLAB = registerBlockAndBlockItem("gray_truss_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .sound(ModSounds.HOLLOW_METAL_BLOCK_SOUNDS))
    );

    //VESSELGLASS BLOCKS
    public static final DeferredBlock<Block> REINFORCED_VESSELGLASS = registerBlockAndBlockItem("reinforced_vesselglass",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties
                    .ofFullCopy(Blocks.GLASS)
            )
    );
    public static final DeferredBlock<Block> VESSELGLASS = registerBlockAndBlockItem("vesselglass",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.GLASS)
            )
    );
    public static final DeferredBlock<Block> GRAY_REINFORCED_VESSELGLASS = registerBlockAndBlockItem("gray_reinforced_vesselglass",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties
                    .ofFullCopy(Blocks.GLASS)
            )
    );
    public static final DeferredBlock<Block> GRAY_VESSELGLASS = registerBlockAndBlockItem("gray_vesselglass",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.GLASS)
            )
    );

    
    //ROCKRETE BLOCKS
    //GRAY ROCKRETE
    public static final DeferredBlock<Block> GRAY_ROCKRETE = registerBlockAndBlockItem("gray_rockrete",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> ROUGH_GRAY_ROCKRETE = registerBlockAndBlockItem("rough_gray_rockrete",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> GRAY_ROCKRETE_STAIRS = registerBlockAndBlockItem("gray_rockrete_stairs",
            () -> new StairBlock(ModBlocks.GRAY_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
    );
    public static final DeferredBlock<Block> GRAY_ROCKRETE_SLAB = registerBlockAndBlockItem("gray_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> GRAY_ROCKRETE_PILLAR = registerBlockAndBlockItem("gray_rockrete_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> ROUGH_GRAY_ROCKRETE_STAIRS = registerBlockAndBlockItem("rough_gray_rockrete_stairs",
            () -> new StairBlock(ModBlocks.ROUGH_GRAY_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
    );
    public static final DeferredBlock<Block> ROUGH_GRAY_ROCKRETE_SLAB = registerBlockAndBlockItem("rough_gray_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    //GREEN ROCKRETE
    public static final DeferredBlock<Block> GREEN_ROCKRETE = registerBlockAndBlockItem("green_rockrete",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> ROUGH_GREEN_ROCKRETE = registerBlockAndBlockItem("rough_green_rockrete",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> GREEN_ROCKRETE_STAIRS = registerBlockAndBlockItem("green_rockrete_stairs",
            () -> new StairBlock(ModBlocks.GREEN_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
    );
    public static final DeferredBlock<Block> GREEN_ROCKRETE_SLAB = registerBlockAndBlockItem("green_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> ROUGH_GREEN_ROCKRETE_STAIRS = registerBlockAndBlockItem("rough_green_rockrete_stairs",
            () -> new StairBlock(ModBlocks.ROUGH_GREEN_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
    );
    public static final DeferredBlock<Block> ROUGH_GREEN_ROCKRETE_SLAB = registerBlockAndBlockItem("rough_green_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> GREEN_ROCKRETE_PILLAR = registerBlockAndBlockItem("green_rockrete_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    //YELLOW ROCKRETE
    public static final DeferredBlock<Block> YELLOW_ROCKRETE = registerBlockAndBlockItem("yellow_rockrete",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> ROUGH_YELLOW_ROCKRETE = registerBlockAndBlockItem("rough_yellow_rockrete",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> YELLOW_ROCKRETE_STAIRS = registerBlockAndBlockItem("yellow_rockrete_stairs",
            () -> new StairBlock(ModBlocks.YELLOW_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
    );
    public static final DeferredBlock<Block> YELLOW_ROCKRETE_SLAB = registerBlockAndBlockItem("yellow_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> ROUGH_YELLOW_ROCKRETE_STAIRS = registerBlockAndBlockItem("rough_yellow_rockrete_stairs",
            () -> new StairBlock(ModBlocks.ROUGH_GRAY_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
    );
    public static final DeferredBlock<Block> ROUGH_YELLOW_ROCKRETE_SLAB = registerBlockAndBlockItem("rough_yellow_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> YELLOW_ROCKRETE_PILLAR = registerBlockAndBlockItem("yellow_rockrete_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    //BLUE ROCKRETE
    public static final DeferredBlock<Block> BLUE_ROCKRETE = registerBlockAndBlockItem("blue_rockrete",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> ROUGH_BLUE_ROCKRETE = registerBlockAndBlockItem("rough_blue_rockrete",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> BLUE_ROCKRETE_STAIRS = registerBlockAndBlockItem("blue_rockrete_stairs",
            () -> new StairBlock(ModBlocks.BLUE_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
    );
    public static final DeferredBlock<Block> BLUE_ROCKRETE_SLAB = registerBlockAndBlockItem("blue_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> ROUGH_BLUE_ROCKRETE_STAIRS = registerBlockAndBlockItem("rough_blue_rockrete_stairs",
            () -> new StairBlock(ModBlocks.ROUGH_GRAY_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
    );
    public static final DeferredBlock<Block> ROUGH_BLUE_ROCKRETE_SLAB = registerBlockAndBlockItem("rough_blue_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> BLUE_ROCKRETE_PILLAR = registerBlockAndBlockItem("blue_rockrete_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    //RED ROCKRETE
    public static final DeferredBlock<Block> RED_ROCKRETE = registerBlockAndBlockItem("red_rockrete",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> ROUGH_RED_ROCKRETE = registerBlockAndBlockItem("rough_red_rockrete",
            () -> new SimpleTextureToggleBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> RED_ROCKRETE_STAIRS = registerBlockAndBlockItem("red_rockrete_stairs",
            () -> new StairBlock(ModBlocks.RED_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
    );
    public static final DeferredBlock<Block> RED_ROCKRETE_SLAB = registerBlockAndBlockItem("red_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> ROUGH_RED_ROCKRETE_STAIRS = registerBlockAndBlockItem("rough_red_rockrete_stairs",
            () -> new StairBlock(ModBlocks.ROUGH_GRAY_ROCKRETE.get().defaultBlockState(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))
    );
    public static final DeferredBlock<Block> ROUGH_RED_ROCKRETE_SLAB = registerBlockAndBlockItem("rough_red_rockrete_slab",
            () -> new SlabBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> RED_ROCKRETE_PILLAR = registerBlockAndBlockItem("red_rockrete_pillar",
            () -> new AxialPillarBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );
    public static final DeferredBlock<Block> GRIMY_RESTROOM_TILE = registerBlockAndBlockItem("grimy_restroom_tile",
            () -> new Block(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
            )
    );

    //PIPE BLOCKS
    public static final DeferredBlock<Block> COPPER_PIPE_CONDUIT = registerBlockAndBlockItem("copper_pipe_conduit",
            () -> new WallPIpeBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)

                    .noOcclusion())
    );
    public static final DeferredBlock<Block> COPPER_PIPE_CONDUIT_PLANAR_CORNER = registerBlockAndBlockItem("copper_pipe_conduit_planar_corner",
            () -> new SurfaceMountRotatableBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)

                    .noOcclusion())
    );
    public static final DeferredBlock<Block> COPPER_PIPE_CONDUIT_INNER_CORNER = registerBlockAndBlockItem("copper_pipe_conduit_inner_corner",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.SHAPE_PIPE_CONDUIT_INNER()
            )
    );
    public static final DeferredBlock<Block> COPPER_PIPE_CONDUIT_OUTER_CORNER = registerBlockAndBlockItem("copper_pipe_conduit_outer_corner",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.SHAPE_PIPE_CONDUIT_OUTER()
            )
    );
    public static final DeferredBlock<Block> BRASS_PIPE_CONDUIT = registerBlockAndBlockItem("brass_pipe_conduit",
            () -> new WallPIpeBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)

                    .noOcclusion())
    );
    public static final DeferredBlock<Block> BRASS_PIPE_CONDUIT_PLANAR_CORNER = registerBlockAndBlockItem("brass_pipe_conduit_planar_corner",
            () -> new SurfaceMountRotatableBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)

                    .noOcclusion())
    );
    public static final DeferredBlock<Block> BRASS_PIPE_CONDUIT_INNER_CORNER = registerBlockAndBlockItem("brass_pipe_conduit_inner_corner",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.SHAPE_PIPE_CONDUIT_INNER()
            )
    );
    public static final DeferredBlock<Block> BRASS_PIPE_CONDUIT_OUTER_CORNER = registerBlockAndBlockItem("brass_pipe_conduit_outer_corner",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.SHAPE_PIPE_CONDUIT_OUTER()
            )
    );
    public static final DeferredBlock<Block> GRAY_PIPE_CONDUIT = registerBlockAndBlockItem("gray_pipe_conduit",
            () -> new WallPIpeBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)

                    .noOcclusion())
    );
    public static final DeferredBlock<Block> GRAY_PIPE_CONDUIT_PLANAR_CORNER = registerBlockAndBlockItem("gray_pipe_conduit_planar_corner",
            () -> new SurfaceMountRotatableBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)

                    .noOcclusion())
    );
    public static final DeferredBlock<Block> GRAY_PIPE_CONDUIT_INNER_CORNER = registerBlockAndBlockItem("gray_pipe_conduit_inner_corner",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.SHAPE_PIPE_CONDUIT_INNER()
            )
    );
    public static final DeferredBlock<Block> GRAY_PIPE_CONDUIT_OUTER_CORNER = registerBlockAndBlockItem("gray_pipe_conduit_outer_corner",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)

                    .noOcclusion(),
                    HitboxGeometryCollection.SHAPE_PIPE_CONDUIT_OUTER()
            )
    );
    
    //BRACKETS
    public static final DeferredBlock<Block> GRAY_BOLTED_BRACKET = registerBlockAndBlockItem("gray_bolted_bracket",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK),
                    HitboxGeometryCollection.SHAPE_BOLTED_BRACKET()
            )
    );
    public static final DeferredBlock<Block> SMALL_GRAY_BOLTED_BRACKET = registerBlockAndBlockItem("small_gray_bolted_bracket",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK),
                    HitboxGeometryCollection.SHAPE_SMALL_BOLTED_BRACKET()
            )
    );
    public static final DeferredBlock<Block> BLACK_BOLTED_BRACKET = registerBlockAndBlockItem("black_bolted_bracket",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK),
                    HitboxGeometryCollection.SHAPE_BOLTED_BRACKET()
            )
    );
    public static final DeferredBlock<Block> SMALL_BLACK_BOLTED_BRACKET = registerBlockAndBlockItem("small_black_bolted_bracket",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK),
                    HitboxGeometryCollection.SHAPE_SMALL_BOLTED_BRACKET()
            )
    );
    public static final DeferredBlock<Block> RUSTY_BOLTED_BRACKET = registerBlockAndBlockItem("rusty_bolted_bracket",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK),
                    HitboxGeometryCollection.SHAPE_BOLTED_BRACKET()
            )
    );
    public static final DeferredBlock<Block> SMALL_RUSTY_BOLTED_BRACKET = registerBlockAndBlockItem("small_rusty_bolted_bracket",
            () -> new CornerBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK),
                    HitboxGeometryCollection.SHAPE_SMALL_BOLTED_BRACKET()
            )
    );

    //DOORS, TRAPDOORS, ETC:
    public static final DeferredBlock<Block> ARMORED_DOOR = registerBlockAndBlockItem("armored_door",
            () -> new DoorBlock(BlockSetType.STONE, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.METAL).noOcclusion()));
//    public static final DeferredBlock<Block> VESSELPLATE_TRAPDOOR = registerBlockAndBlockItem("vesselplate_trapdoor",
//            () -> new TrapDoorBlock(BlockSetType.STONE, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.METAL).noOcclusion()));
    public static final DeferredBlock<Block> STAMPED_METAL_DOOR = registerBlockAndBlockItem("stamped_metal_door",
            () -> new DoorBlock(BlockSetType.STONE, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.METAL).noOcclusion()));
    public static final DeferredBlock<Block> BULKHEAD_DOOR = registerBlockAndBlockItem("bulkhead_door",
            () -> new DoorBlock(BlockSetType.STONE, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.METAL).noOcclusion()));
    public static final DeferredBlock<Block> VENT_TRAPDOOR = registerBlockAndBlockItem("vent_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.STONE, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.METAL).noOcclusion()));
    
//    //FURNITURE
    public static final DeferredBlock<Block> RED_WALL_MEDKIT = registerBlockAndBlockItem("red_wall_medkit",
            () -> new ModelledFacingStorageBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
                    .noOcclusion(),
                    9,
                    ()-> ModSounds.METAL_BOX_OPEN.get(),
                    ()-> ModSounds.METAL_BOX_CLOSE.get(),
                    HitboxGeometryCollection.RED_MEDKIT_NORTH()
            )
    );

    public static final DeferredBlock<Block> WHITE_WALL_MEDKIT = registerBlockAndBlockItem("white_wall_medkit",
            () -> new ModelledFacingStorageBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
                    .noOcclusion(),
                    9,
                    ()-> ModSounds.METAL_BOX_OPEN.get(),
                    ()-> ModSounds.METAL_BOX_CLOSE.get(),
                    HitboxGeometryCollection.WHITE_MEDKIT_NORTH()
            )
    );
    public static final DeferredBlock<Block> FIRE_EXTINGUISHER = registerBlockAndBlockItem("fire_extinguisher",
            () -> new ModelledFacingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    HitboxGeometryCollection.FIRE_EXTINGUISHER()
            )
    );
    public static final DeferredBlock<Block> SMOKE_ALARM = registerBlockAndBlockItem("smoke_alarm",
            () -> new RandomTickSoundBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    HitboxGeometryCollection.SMOKE_DETECTOR_FLOOR(),
                    () -> ModSounds.SMOKE_ALARM.get()
            )
    );
    public static final DeferredBlock<Block> OPERATING_TABLE = registerBlockAndBlockItem("operating_table",
            () -> new Modelled2BMBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .sound(SoundType.METAL)
                    .noOcclusion(),
                    MultiBlockPlacementDirection.FORWARD,
                    HitboxGeometryCollection.OPERATING_TABLE_POSITIVE(),
                    HitboxGeometryCollection.OPERATING_TABLE_NEGATIVE()
            )
    );
    public static final DeferredBlock<Block> MEDICAL_BED = registerBlockAndBlockItem("medical_bed",
            () -> new ModdedBedBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .sound(SoundType.METAL)
                    .noOcclusion(),
                    HitboxGeometryCollection.MEDICAL_BED_POSITIVE(),
                    HitboxGeometryCollection.MEDICAL_BED_NEGATIVE()
            )
    );
    public static final DeferredBlock<Block> VITALS_MONITOR = registerBlockAndBlockItem("vitals_monitor",
            () -> new InteractableMultiBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .lightLevel(state -> state.getValue(InteractableMultiBlock.POWERED) ? 15 : 0),
                    MultiBlockPlacementDirection.VERTICAL,
                    HitboxGeometryCollection.VITALS_MONITOR_TOP(),
                    HitboxGeometryCollection.VITALS_MONITOR_BASE()
            )
    );
    public static final DeferredBlock<Block> IV_DRIPSTAND = registerBlockAndBlockItem("iv_dripstand",
            () -> new Modelled2BMBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .noOcclusion(),
                    MultiBlockPlacementDirection.VERTICAL,
                    HitboxGeometryCollection.THIN_VERTICAL_ROD_SHAPE(),
                    HitboxGeometryCollection.THIN_VERTICAL_ROD_SHAPE()
            )
    );
//
    public static final DeferredBlock<Block> TOILET = registerBlockAndBlockItem("toilet",
            () -> new ToiletBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.TOILET(),
                    () -> SoundEvents.WOODEN_TRAPDOOR_CLOSE,
                    () -> ModSounds.TOILET_FLUSH.get()
                    )
    );
//
    public static final DeferredBlock<Block> SINK = registerBlockAndBlockItem("sink",
            () -> new ModelledFacingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.UPPER_SLAB())
    );
    public static final DeferredBlock<Block> URINAL = registerBlockAndBlockItem("urinal",
            () -> new SoundModelledFacingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.URINAL(),
                    ()-> ModSounds.TOILET_FLUSH.get()
            )
    );
//
    public static final DeferredBlock<Block> LARGE_LOCKER = registerBlockAndBlockItem("large_locker",
            () -> new TwoBlockStorageMultiBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    , MultiBlockPlacementDirection.VERTICAL
                    , 54
                    , () -> ModSounds.METAL_BOX_OPEN.get()
                    , () -> ModSounds.METAL_BOX_CLOSE.get()
            )
    );
    public static final DeferredBlock<Block> LOCKER_BOX = registerBlockAndBlockItem("locker_box",
            () -> new FacingStorageBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK),
                    27,
                    () -> ModSounds.METAL_BOX_OPEN.get(),
                    () -> ModSounds.METAL_BOX_CLOSE.get()
            )
    );
    public static final DeferredBlock<Block> FUEL_DRUM = registerBlockAndBlockItem("fuel_drum",
            () -> new BarrelStorageBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK),
                    27,
                    () -> ModSounds.METAL_BOX_OPEN.get(),
                    () -> ModSounds.METAL_BOX_CLOSE.get()
            )
    );
    public static final DeferredBlock<Block> CCTV_CAMERA = registerBlockAndBlockItem("cctv_camera",
            () -> new ModelledSurfaceMountBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .lightLevel(state -> state.getValue(ModelledSurfaceMountBlock.WATERLOGGED) ? 0 : 3),
                    HitboxGeometryCollection.DECAL_FLOOR()
            )
    );
    public static final DeferredBlock<Block> WORK_LIGHT_STAND = registerBlockAndBlockItem("work_light_stand",
            () -> new InteractableMultiBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .lightLevel(state -> state.getValue(InteractableMultiBlock.POWERED) ? 15 : 0),
                    MultiBlockPlacementDirection.VERTICAL,
                    HitboxGeometryCollection.WORK_LIGHT_MOUNT_SHAPE(),
                    HitboxGeometryCollection.THIN_VERTICAL_ROD_SHAPE()
            )
    );
    public static final DeferredBlock<Block> FLOOR_WORK_LIGHT = registerBlockAndBlockItem("floor_work_light",
            () -> new InteractableModelledFacingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion()
                    .lightLevel(state -> state.getValue(InteractableModelledFacingBlock.POWERED) ? 15 : 0),
                    HitboxGeometryCollection.FLOOR_WORK_LIGHT_SHAPE(),
                    () -> SoundEvents.STONE_BUTTON_CLICK_ON,
                    () -> SoundEvents.STONE_BUTTON_CLICK_OFF
            )
    );
//    public static final DeferredBlock<Block> CAGE_LAMP = registerBlockAndBlockItem("cage_lamp",
//            () -> new LightBulbBlock(BlockBehaviour
//                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
//                    .noOcclusion()
//                    .lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 15 : 0),
//                    HitboxGeometryCollection.CAGE_LAMP(),
//                    () -> ModSounds.SWITCH_ON.get(),
//                    () -> ModSounds.SWITCH_OFF.get()
//            )
//    );

    public static final DeferredBlock<Block> RETRO_COMPUTER = registerBlockAndBlockItem("retro_computer",
            () -> new InteractableModelledFacingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
                    .lightLevel(state -> state.getValue(InteractableModelledFacingBlock.POWERED) ? 7 : 0),
                    HitboxGeometryCollection.RETRO_COMPUTER(),
                    () -> ModSounds.COMPUTER_ON.get(),
                    () -> ModSounds.SWITCH_OFF.get()
            )
    );
    public static final DeferredBlock<Block> RETRO_COMPUTER_2 = registerBlockAndBlockItem("retro_computer_2",
            () -> new InteractableModelledFacingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
                    .lightLevel(state -> state.getValue(InteractableModelledFacingBlock.POWERED) ? 7 : 0),
                    HitboxGeometryCollection.RETRO_COMPUTER_2(),
                    () -> ModSounds.COMPUTER_ON.get(),
                    () ->  ModSounds.SWITCH_OFF.get()
            )
    );
    public static final DeferredBlock<Block> MONITOR_AND_KEYBOARD = registerBlockAndBlockItem("monitor_and_keyboard",
            () -> new InteractableModelledFacingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
                    .lightLevel(state -> state.getValue(InteractableModelledFacingBlock.POWERED) ? 7 : 0),
                    HitboxGeometryCollection.MONITOR_AND_KEYBOARD(),
                    () -> SoundEvents.STONE_BUTTON_CLICK_ON,
                    () -> SoundEvents.STONE_BUTTON_CLICK_OFF
            )
    );
    public static final DeferredBlock<Block> DESKTOP_TOWER = registerBlockAndBlockItem("desktop_tower",
            () -> new ModelledFacingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE),
                    HitboxGeometryCollection.DESKTOP_TOWER()
            ) {
            }
    );
    public static final DeferredBlock<Block> CASSETTE_PLAYER = registerBlockAndBlockItem("cassette_player",
            () -> new InteractableModelledFacingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.STONE)
                    .noOcclusion(),
                    HitboxGeometryCollection.CASSETTE_PLAYER(),
                    () -> SoundEvents.STONE_BUTTON_CLICK_ON,
                    () -> SoundEvents.STONE_BUTTON_CLICK_OFF
            )
    );
//
    public static final DeferredBlock<Block> DESK = registerBlockAndBlockItem("desk",
            () -> new ConnectedFurnitureBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .noOcclusion(),
                    ModTags.Blocks.CLASSIC_DESK,
                    HitboxGeometryCollection.DESK_SOLO_SHAPE(),
                    HitboxGeometryCollection.DESK_LEFT_SHAPE(),
                    HitboxGeometryCollection.DESK_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.DESK_RIGHT_SHAPE(),
                    MultiBlockPlacementDirection.HORIZONTAL)

    );
    public static final DeferredBlock<Block> DESK_DRAWER = registerBlockAndBlockItem("desk_drawer",
            () -> new ConnectedStorageBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .noOcclusion(),
                    18,
                    ModTags.Blocks.CLASSIC_DESK,
                    HitboxGeometryCollection.DESK_DRAWER_SOLO_SHAPE(),
                    HitboxGeometryCollection.DESK_DRAWER_LEFT_SHAPE(),
                    HitboxGeometryCollection.DESK_DRAWER_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.DESK_DRAWER_RIGHT_SHAPE(),
                    MultiBlockPlacementDirection.HORIZONTAL,
                    () -> ModSounds.METAL_BOX_OPEN.get(),
                    () -> ModSounds.METAL_BOX_CLOSE.get()
            )
    );
    public static final DeferredBlock<Block> METAL_DESK = registerBlockAndBlockItem("metal_desk",
            () -> new ConnectedFurnitureBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    ModTags.Blocks.METAL_DESK,
                    HitboxGeometryCollection.METAL_DESK_SOLO_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_LEFT_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_RIGHT_SHAPE(),
                    MultiBlockPlacementDirection.HORIZONTAL
            )
    );
    public static final DeferredBlock<Block> METAL_DESK_DRAWER = registerBlockAndBlockItem("metal_desk_drawer",
            () -> new ConnectedStorageBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    18,
                    ModTags.Blocks.METAL_DESK,
                    HitboxGeometryCollection.METAL_DESK_DRAWER_SOLO_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_LEFT_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_RIGHT_SHAPE(),
                    MultiBlockPlacementDirection.HORIZONTAL,
                    () -> ModSounds.METAL_BOX_OPEN.get(),
                    () -> ModSounds.METAL_BOX_CLOSE.get()
            )

    );
    public static final DeferredBlock<Block> OFFICE_DESK_DRAWER = registerBlockAndBlockItem("office_desk_drawer",
            () -> new ConnectedStorageBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    18,
                    ModTags.Blocks.OFFICE_DESK,
                    HitboxGeometryCollection.OFFICE_DESK_DRAWER_SHAPE(),
                    HitboxGeometryCollection.OFFICE_DESK_DRAWER_SHAPE(),
                    HitboxGeometryCollection.OFFICE_DESK_DRAWER_SHAPE(),
                    HitboxGeometryCollection.OFFICE_DESK_DRAWER_SHAPE(),
                    MultiBlockPlacementDirection.HORIZONTAL,
                    () -> ModSounds.METAL_BOX_OPEN.get(),
                    () -> ModSounds.METAL_BOX_CLOSE.get()
            )

    );
    public static final DeferredBlock<Block> OFFICE_DESK = registerBlockAndBlockItem("office_desk",
            () -> new ConnectedFurnitureBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    ModTags.Blocks.OFFICE_DESK,
                    HitboxGeometryCollection.OFFICE_DESK_SOLO_SHAPE(),
                    HitboxGeometryCollection.OFFICE_DESK_LEFT_SHAPE(),
                    HitboxGeometryCollection.OFFICE_DESK_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.OFFICE_DESK_RIGHT_SHAPE(),

                    MultiBlockPlacementDirection.HORIZONTAL
            )

    );

    public static final DeferredBlock<Block> METAL_DESK_DRAWER_2 = registerBlockAndBlockItem("metal_desk_drawer_2",
            () -> new ConnectedStorageBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .noOcclusion(),
                    18,
                    ModTags.Blocks.METAL_DESK,
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_SOLO_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_LEFT_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_MIDDLE_SHAPE(),
                    HitboxGeometryCollection.METAL_DESK_DRAWER_2_RIGHT_SHAPE(),
                    MultiBlockPlacementDirection.HORIZONTAL,
                    () -> ModSounds.METAL_BOX_OPEN.get(),
                    () -> ModSounds.METAL_BOX_CLOSE.get()
            )
    );
    public static final DeferredBlock<Block> OFFICE_CHAIR = registerBlockAndBlockItem("office_chair",
            () -> new SittableFacingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .noOcclusion(),
                    HitboxGeometryCollection.OFFICE_CHAIR_SHAPE()
            )
    );
    public static final DeferredBlock<Block> BLACK_OFFICE_CHAIR = registerBlockAndBlockItem("black_office_chair",
            () -> new SittableFacingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .noOcclusion(),
                    HitboxGeometryCollection.OFFICE_CHAIR_SHAPE()
            )
    );

    public static final DeferredBlock<Block> FOLDING_CHAIR = registerBlockAndBlockItem("folding_chair",
            () -> new SittableFacingBlock(BlockBehaviour
                    .Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .noOcclusion(),
                    HitboxGeometryCollection.FOLDING_CHAIR()
            )
    );

    //Registration Methods
    private static <T extends Block> DeferredBlock<T> registerBlockAndBlockItem(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredBlock<T> registerBlockOnly(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}