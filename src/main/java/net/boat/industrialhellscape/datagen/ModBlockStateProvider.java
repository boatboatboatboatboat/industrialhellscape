package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.block_classes.AxisPillarBlocks.TrussBeamBlock;
import net.boat.industrialhellscape.block.block_classes.MultiBlocks.Integer11Blocks.Integer11Block;
import net.boat.industrialhellscape.block.block_classes.MultiBlocks.Integer2Blocks.Integer2Block;
import net.boat.industrialhellscape.block.block_classes.MultiBlocks.Integer2Blocks.PeanutStatueBlock;
import net.boat.industrialhellscape.block.block_classes.PanelBlocks.PanelBlock;
import net.boat.industrialhellscape.block.block_classes.RailingBlocks.ParapetBlock;
import net.boat.industrialhellscape.block.block_classes.RailingBlocks.RailingBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Arrays;

/*
    Notes:
    - Within this class, modLoc() specifies that a filepath string ( "block/example_folder/example_block_model" ) is from inside this mod, not vanilla minecraft

 */

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, IndustrialHellscape.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //Debug Blocks
        genFullBlockIntegerMultiBlockSI(ModBlocks.DEBUG_BLOCK.get(), "debug_textures", 11, Integer11Block.PART);

        //In-Development Block
        genBrushableBlockSI(ModBlocks.VERY_SUSPICIOUS_SAND.get(), "suspicious_sand");
        genHorizontalModelTogglingSI(ModBlocks.LAND_MINE.get(), "mine", "disarmed", BlockStateProperties.DISARMED, true);

        //Hidden Joke Blocks
        genPeanutS((PeanutStatueBlock) ModBlocks.PEANUT_STATUE.get(), "peanut_statue");

        genMultiBlockHorizontalS(ModBlocks.BODY_PILLOW_OZY.get(), "body_pillow");
        genVariantMultiBlock(ModBlocks.BODY_PILLOW_FANG.get(), ModBlocks.BODY_PILLOW_OZY.get(),"body_pillow","body_pillow","body_pillow_front_graphic_fang", "1", new Integer[]{1} );
        genVariantMultiBlock(ModBlocks.BODY_PILLOW_PROV.get(), ModBlocks.BODY_PILLOW_OZY.get(),"body_pillow","body_pillow","body_pillow_front_graphic_prov", "1", new Integer[]{1} );

        //Base Building Blocks
        gen6FacedCubeHorizontalSBI(ModBlocks.IHEA_FURNITURE_KIT.get(), "furniture_category_block", "ihea_furniture_kit", "front", "back", "left", "right", "top", "bottom");
        gen6FacedCubeHorizontalSBI(ModBlocks.METALWORKS.get(), "metalworks", "metalworks", "side", "side", "side", "side", "top", "bottom");
        gen3FacedCubeHorizontalSBI(ModBlocks.PIPEWORKS.get(),"pipeworks", "pipeworks");

        //Furnishing Category Blocks (generated block state json files. generated block models from vanilla parent, generated item models)
        gen3FacedCubeHorizontalSBI(ModBlocks.SAFETY_FURNISHINGS.get(), "furniture_category_block", "safety_furnishings");
        gen3FacedCubeHorizontalSBI(ModBlocks.HYGIENE_FURNISHINGS.get(), "furniture_category_block", "hygiene_furnishings");
        gen3FacedCubeHorizontalSBI(ModBlocks.INDUSTRIAL_FURNISHINGS.get(), "furniture_category_block", "industrial_furnishings");
        gen3FacedCubeHorizontalSBI(ModBlocks.TECHNOLOGY_FURNISHINGS.get(), "furniture_category_block", "technology_furnishings");
        gen3FacedCubeHorizontalSBI(ModBlocks.AMENITY_FURNISHINGS.get(), "furniture_category_block", "amenity_furnishings");

        //Railing Blocks (generated block state json files. generated block models from parent, generated item models)
        genRailingBlockSI((RailingBlock) ModBlocks.YELLOW_RAILING.get(),"railing","yellow_railing","railing","yellow_railing");
        genRailingBlockSI((RailingBlock) ModBlocks.BLACK_RAILING.get(),"railing","yellow_railing","railing","black_railing");
        genRailingBlockSI((RailingBlock) ModBlocks.GRAY_RAILING.get(),"railing","yellow_railing","railing","gray_railing");
        genRailingBlockSI((RailingBlock) ModBlocks.RUSTY_RAILING.get(),"railing","yellow_railing","railing","rusty_railing");

        //Stair Railing Blocks (Make item model only.) TODO: Datagen this
        genCustomI(ModBlocks.YELLOW_STAIR_RAILING.get(),"railing","yellow_stair_rail_left");
        genCustomI(ModBlocks.GRAY_STAIR_RAILING.get(),"railing","gray_stair_rail_left");
        genCustomI(ModBlocks.BLACK_STAIR_RAILING.get(),"railing","black_stair_rail_left");
        genCustomI(ModBlocks.RUSTY_STAIR_RAILING.get(),"railing","rusty_stair_rail_left");

        //Parapet Blocks (generated block state json files. generated block models from parent, generated item models)
        genParapetBlockS((ParapetBlock) ModBlocks.GRAY_ROCKRETE_PARAPET.get(), "parapet","rockrete_parapet", "rockrete_parapets","gray_rockrete_parapet");
        genParapetBlockS((ParapetBlock) ModBlocks.RED_ROCKRETE_PARAPET.get(), "parapet","rockrete_parapet", "rockrete_parapets","red_rockrete_parapet");
        genParapetBlockS((ParapetBlock) ModBlocks.GREEN_ROCKRETE_PARAPET.get(), "parapet","rockrete_parapet", "rockrete_parapets","green_rockrete_parapet");
        genParapetBlockS((ParapetBlock) ModBlocks.BLUE_ROCKRETE_PARAPET.get(), "parapet","rockrete_parapet", "rockrete_parapets","blue_rockrete_parapet");
        genParapetBlockS((ParapetBlock) ModBlocks.YELLOW_ROCKRETE_PARAPET.get(), "parapet","rockrete_parapet", "rockrete_parapets","yellow_rockrete_parapet");

        //Duct Blocks
        genFolderedSBI(ModBlocks.DUCT.get(), "duct");
        genFolderedSBI(ModBlocks.RUSTY_DUCT.get(), "duct");

        //Vent Blocks
        genSimpleTextureToggleSBI(ModBlocks.HORIZONTAL_VENT.get(),"vent", "solid");
        genSimpleTextureToggleSBI(ModBlocks.HORIZONTAL_CUTOUT_VENT.get(),"vent","cutout");

        genSimpleTextureToggleSBI(ModBlocks.VERTICAL_VENT.get(),"vent", "solid");
        genSimpleTextureToggleSBI(ModBlocks.VERTICAL_CUTOUT_VENT.get(),"vent","cutout");

        genSimpleTextureToggleSBI(ModBlocks.RUSTY_HORIZONTAL_VENT.get(),"vent", "solid");
        genSimpleTextureToggleSBI(ModBlocks.RUSTY_HORIZONTAL_CUTOUT_VENT.get(),"vent","cutout");

        genSimpleTextureToggleSBI(ModBlocks.RUSTY_VERTICAL_VENT.get(),"vent", "solid");
        genSimpleTextureToggleSBI(ModBlocks.RUSTY_VERTICAL_CUTOUT_VENT.get(),"vent","cutout");

        //Panel Blocks (generated block state json files. generated block models from parent, generated item models)
        genMultiStatePanelSBI((PanelBlock) ModBlocks.BLACK_SHEETING_PANEL.get(),"", "panel2pixel","vesselplate","vesselplate_pillar","vesselplate_sheeting","vesselplate_pillar_top");
        genMultiStatePanelSBI((PanelBlock) ModBlocks.GRAY_SHEETING_PANEL.get(),"", "panel2pixel","vesselplate","gray_vesselplate_pillar","gray_vesselplate_sheeting","gray_vesselplate_pillar_top");
        genMultiStatePanelSBI((PanelBlock) ModBlocks.RUSTY_SHEETING_PANEL.get(),"", "panel2pixel","vesselplate","rusty_vesselplate_pillar","rusty_vesselplate_sheeting","rusty_vesselplate_pillar_top");

        //Fence Blocks (generated block state json files. generated block models from parent. NO ITEM MODEL GENERATION. Performed in ModItemModelProvider with manually drawn texture file)
        genFenceBlockSB( (FenceBlock) ModBlocks.BARBED_WIRE_FENCE.get(), "fence","4x4_fence_post", "2d_fence", "fence", "barbed_wire_fencepost", "barbed_wire_fence");
        genFenceBlockSB( (FenceBlock) ModBlocks.CHAINLINK_FENCE.get(), "fence","2x2_fence_post", "2d_fence", "fence", "chainlink_fencepost", "chainlink_fence");

        //Fence Top Blocks ( genOrthoRotFencePostS() does not generate models, uses existing models )
        genOrthoRotFencePostS( (FenceBlock) ModBlocks.ANGLED_BARBED_WIRE_ARM.get(), "fence","angled_barbed_wire_arm", "angled_barbed_wire");
        genOrthoRotFencePostS( (FenceBlock) ModBlocks.COILED_BARBED_WIRE_ARM.get(), "fence","coiled_barbed_wire_fencepost", "coiled_barbed_wire");
        genFenceBlockSB( (FenceBlock) ModBlocks.VERTICAL_BARBED_WIRE_ARM.get(), "fence","vertical_barbed_wire_arm", "2d_fence", "fence", "vertical_barbed_wire_arm", "barbed_wire_fence");

        //Vesselplate Blocks
        genFolderedSBI(ModBlocks.VESSELPLATE_SHEETING.get(), "vesselplate");
        genStairsWithRenderTypeSBI(ModBlocks.VESSELPLATE_SHEETING_STAIRS.get(),"vesselplate","vesselplate_sheeting","vesselplate_sheeting","vesselplate_sheeting","solid");
        genSlabsWithCustomDoubleSBI(ModBlocks.VESSELPLATE_SHEETING_SLAB.get(), ModBlocks.VESSELPLATE_SHEETING.get(),"vesselplate","vesselplate_sheeting","vesselplate_sheeting", "vesselplate_sheeting","","");

        genSimpleTextureToggleSBI(ModBlocks.RIVETED_VESSELPLATE.get(),"vesselplate",  "solid");
        genSimpleTextureToggleSBI(ModBlocks.HORIZONTAL_REINFORCED_VESSELPLATE.get(),"vesselplate", "solid");
        genSimpleTextureToggleSBI(ModBlocks.VERTICAL_REINFORCED_VESSELPLATE.get(),"vesselplate",  "solid");
        genSimpleTextureToggleSBI(ModBlocks.HORIZONTAL_VESSELPLATE.get(),"vesselplate","solid");
        genSimpleTextureToggleSBI(ModBlocks.VERTICAL_VESSELPLATE.get(),"vesselplate", "solid");
        genSimpleTextureToggleSBI(ModBlocks.SMOOTH_VESSELPLATE.get(),"vesselplate",   "solid");

        genStairsWithRenderTypeSBI(ModBlocks.RIVETED_VESSELPLATE_STAIRS.get(),"vesselplate","riveted_vesselplate_stairs","riveted_vesselplate_stairs","riveted_vesselplate_stairs","solid");
        genStairsWithRenderTypeSBI(ModBlocks.SMOOTH_VESSELPLATE_STAIRS.get(),"vesselplate","smooth_vesselplate","smooth_vesselplate","smooth_vesselplate","solid");
        genSlabsWithCustomDoubleSBI(ModBlocks.RIVETED_VESSELPLATE_SLAB.get(), ModBlocks.RIVETED_VESSELPLATE.get(),"vesselplate","riveted_vesselplate_double_slab", "riveted_vesselplate","riveted_vesselplate","","");
        genSlabsWithCustomDoubleSBI(ModBlocks.SMOOTH_VESSELPLATE_SLAB.get(), ModBlocks.SMOOTH_VESSELPLATE.get(),"vesselplate","smooth_vesselplate_double_slab","smooth_vesselplate", "smooth_vesselplate","","");

        //Gray Vesselplate Blocks
        genFolderedSBI(ModBlocks.GRAY_VESSELPLATE_SHEETING.get(), "vesselplate");
        genStairsWithRenderTypeSBI(ModBlocks.GRAY_VESSELPLATE_SHEETING_STAIRS.get(),"vesselplate","gray_vesselplate_sheeting","gray_vesselplate_sheeting","gray_vesselplate_sheeting","solid");
        genSlabsWithCustomDoubleSBI(ModBlocks.GRAY_VESSELPLATE_SHEETING_SLAB.get(), ModBlocks.GRAY_VESSELPLATE_SHEETING.get(),"vesselplate","gray_vesselplate_sheeting","gray_vesselplate_sheeting", "gray_vesselplate_sheeting","","");

        genSimpleTextureToggleSBI(ModBlocks.GRAY_RIVETED_VESSELPLATE.get(),"vesselplate","solid");
        genSimpleTextureToggleSBI(ModBlocks.GRAY_VERTICAL_REINFORCED_VESSELPLATE.get(),"vesselplate", "solid");
        genSimpleTextureToggleSBI(ModBlocks.GRAY_HORIZONTAL_REINFORCED_VESSELPLATE.get(),"vesselplate", "solid");
        genSimpleTextureToggleSBI(ModBlocks.GRAY_HORIZONTAL_VESSELPLATE.get(),"vesselplate",  "solid");
        genSimpleTextureToggleSBI(ModBlocks.GRAY_VERTICAL_VESSELPLATE.get(),"vesselplate","solid");
        genSimpleTextureToggleSBI(ModBlocks.SMOOTH_GRAY_VESSELPLATE.get(),"vesselplate","solid");

        genStairsWithRenderTypeSBI(ModBlocks.GRAY_RIVETED_VESSELPLATE_STAIRS.get(),"vesselplate","gray_riveted_vesselplate_stairs","gray_riveted_vesselplate_stairs","gray_riveted_vesselplate_stairs","solid");
        genStairsWithRenderTypeSBI(ModBlocks.SMOOTH_GRAY_VESSELPLATE_STAIRS.get(),"vesselplate","smooth_gray_vesselplate","smooth_gray_vesselplate","smooth_gray_vesselplate","solid");
        genSlabsWithCustomDoubleSBI(ModBlocks.GRAY_RIVETED_VESSELPLATE_SLAB.get(), ModBlocks.GRAY_RIVETED_VESSELPLATE.get(),"vesselplate","gray_riveted_vesselplate_double_slab","gray_riveted_vesselplate","gray_riveted_vesselplate","","");
        genSlabsWithCustomDoubleSBI(ModBlocks.SMOOTH_GRAY_VESSELPLATE_SLAB.get(), ModBlocks.SMOOTH_GRAY_VESSELPLATE.get(),"vesselplate","smooth_gray_vesselplate_double_slab","smooth_gray_vesselplate", "smooth_gray_vesselplate","","");

        //Rusty Vesselplate Blocks
        genFolderedSBI(ModBlocks.RUSTY_VESSELPLATE_SHEETING.get(), "vesselplate");
        genStairsWithRenderTypeSBI(ModBlocks.RUSTY_VESSELPLATE_SHEETING_STAIRS.get(),"vesselplate","rusty_vesselplate_sheeting","rusty_vesselplate_sheeting","rusty_vesselplate_sheeting","solid");
        genSlabsWithCustomDoubleSBI(ModBlocks.RUSTY_VESSELPLATE_SHEETING_SLAB.get(), ModBlocks.RUSTY_VESSELPLATE_SHEETING.get(),"vesselplate","rusty_vesselplate_sheeting","rusty_vesselplate_sheeting", "rusty_vesselplate_sheeting","","");

        genSimpleTextureToggleSBI(ModBlocks.RUSTY_HORIZONTAL_VESSELPLATE.get(),"vesselplate", "solid");
        genSimpleTextureToggleSBI(ModBlocks.RUSTY_VERTICAL_VESSELPLATE.get(),"vesselplate",  "solid");
        genSimpleTextureToggleSBI(ModBlocks.RUSTY_HORIZONTAL_REINFORCED_VESSELPLATE.get(),"vesselplate", "solid");
        genSimpleTextureToggleSBI(ModBlocks.RUSTY_VERTICAL_REINFORCED_VESSELPLATE.get(),"vesselplate",  "solid");
        genSimpleTextureToggleSBI(ModBlocks.RUSTY_RIVETED_VESSELPLATE.get(),"vesselplate","solid");
        genSimpleTextureToggleSBI(ModBlocks.SMOOTH_RUSTY_VESSELPLATE.get(),"vesselplate","solid");

        genStairsWithRenderTypeSBI(ModBlocks.RUSTY_RIVETED_VESSELPLATE_STAIRS.get(),"vesselplate","rusty_riveted_vesselplate","rusty_riveted_vesselplate","rusty_riveted_vesselplate","solid");
        genStairsWithRenderTypeSBI(ModBlocks.SMOOTH_RUSTY_VESSELPLATE_STAIRS.get(),"vesselplate","smooth_rusty_vesselplate","smooth_rusty_vesselplate","smooth_rusty_vesselplate","solid");
        genSlabsWithCustomDoubleSBI(ModBlocks.RUSTY_RIVETED_VESSELPLATE_SLAB.get(), ModBlocks.RUSTY_RIVETED_VESSELPLATE.get(),"vesselplate","rusty_riveted_vesselplate_double_slab","rusty_riveted_vesselplate", "rusty_riveted_vesselplate","","");
        genSlabsWithCustomDoubleSBI(ModBlocks.SMOOTH_RUSTY_VESSELPLATE_SLAB.get(), ModBlocks.SMOOTH_RUSTY_VESSELPLATE.get(),"vesselplate","smooth_rusty_vesselplate_double_slab","smooth_rusty_vesselplate", "smooth_rusty_vesselplate","","");

        //Truss Blocks
        genTrussSupportSBI(ModBlocks.TRUSS_BEAM.get(), "truss", RotatedPillarBlock.AXIS, TrussBeamBlock.ALT_STATE);
        genTextureToggleAntiCullSBI(ModBlocks.TRUSS.get(),"truss", "truss","truss", "cutout");
        genStairsWithRenderTypeSBI(ModBlocks.TRUSS_STAIRS.get(),"truss","reinforced_truss","truss","truss","cutout");
        genAntiCullSlabBlockSI(ModBlocks.TRUSS_SLAB.get(), "truss", "truss", "truss","truss","cutout");

        genTextureToggleAntiCullSBI(ModBlocks.CATWALK_TRUSS.get(),"truss", "truss","floorgrate_catwalk", "cutout");
        genStairsWithRenderTypeSBI(ModBlocks.CATWALK_TRUSS_STAIRS.get(),"truss","reinforced_truss","truss","floorgrate_catwalk","cutout");
        genAntiCullSlabBlockSI(ModBlocks.CATWALK_TRUSS_SLAB.get(), "truss", "truss", "floorgrate_catwalk","truss","cutout");

        //Gray Truss Blocks
        genTrussSupportSBI(ModBlocks.GRAY_TRUSS_BEAM.get(), "truss", RotatedPillarBlock.AXIS, TrussBeamBlock.ALT_STATE);
        genTextureToggleAntiCullSBI(ModBlocks.GRAY_TRUSS.get(),"truss", "gray_truss","gray_truss", "cutout");
        genStairsWithRenderTypeSBI(ModBlocks.GRAY_TRUSS_STAIRS.get(),"truss","gray_reinforced_truss","gray_truss","gray_truss","cutout");
        genAntiCullSlabBlockSI(ModBlocks.GRAY_TRUSS_SLAB.get(), "truss", "gray_truss", "gray_truss","gray_truss","cutout");

        genTextureToggleAntiCullSBI(ModBlocks.GRAY_CATWALK_TRUSS.get(),"truss", "gray_truss","gray_floorgrate_catwalk", "cutout");
        genStairsWithRenderTypeSBI(ModBlocks.GRAY_CATWALK_TRUSS_STAIRS.get(),"truss","gray_reinforced_truss","gray_truss","gray_floorgrate_catwalk","cutout");
        genAntiCullSlabBlockSI(ModBlocks.GRAY_CATWALK_TRUSS_SLAB.get(), "truss", "gray_truss", "gray_floorgrate_catwalk","gray_truss", "cutout");

        //Rusty Truss Blocks
        genTrussSupportSBI(ModBlocks.RUSTY_TRUSS_BEAM.get(), "truss", RotatedPillarBlock.AXIS, TrussBeamBlock.ALT_STATE);
        genTextureToggleAntiCullSBI(ModBlocks.RUSTY_TRUSS.get(),"truss", "rusty_truss","rusty_truss", "cutout");
        genStairsWithRenderTypeSBI(ModBlocks.RUSTY_TRUSS_STAIRS.get(),"truss","rusty_reinforced_truss","rusty_truss","rusty_truss","cutout");
        genAntiCullSlabBlockSI(ModBlocks.RUSTY_TRUSS_SLAB.get(), "truss", "rusty_truss", "rusty_truss","rusty_truss","cutout");

        genTextureToggleAntiCullSBI(ModBlocks.RUSTY_CATWALK_TRUSS.get(),"truss", "rusty_truss","rusty_floorgrate_catwalk", "cutout");
        genStairsWithRenderTypeSBI(ModBlocks.RUSTY_CATWALK_TRUSS_STAIRS.get(),"truss","rusty_reinforced_truss","rusty_truss","rusty_floorgrate_catwalk","cutout");
        genAntiCullSlabBlockSI(ModBlocks.RUSTY_CATWALK_TRUSS_SLAB.get(), "truss", "rusty_truss", "rusty_floorgrate_catwalk","rusty_truss","cutout");

        //Vesselglass Blocks
        genSimpleTextureToggleSBI(ModBlocks.VESSELGLASS.get(),"vesselglass","translucent");
        genSimpleTextureToggleSBI(ModBlocks.REINFORCED_VESSELGLASS.get(),"vesselglass", "translucent");

        //Gray Vesselglass Blocks
        genSimpleTextureToggleSBI(ModBlocks.GRAY_VESSELGLASS.get(),"vesselglass",  "translucent");
        genSimpleTextureToggleSBI(ModBlocks.GRAY_REINFORCED_VESSELGLASS.get(),"vesselglass","translucent");

        //Rusty Vesselglass Blocks
        genSimpleTextureToggleSBI(ModBlocks.RUSTY_VESSELGLASS.get(),"vesselglass",  "translucent");
        genSimpleTextureToggleSBI(ModBlocks.RUSTY_REINFORCED_VESSELGLASS.get(),"vesselglass","translucent");

        //Rockrete Blocks
        genFolderedSBI(ModBlocks.GRAY_ROCKRETE.get(), "rockrete");
        genStairsWithRenderTypeSBI(ModBlocks.GRAY_ROCKRETE_STAIRS.get(),"rockrete","gray_rockrete","gray_rockrete","gray_rockrete","solid");
        genSimpleSlabsSBI(ModBlocks.GRAY_ROCKRETE_SLAB.get(), ModBlocks.GRAY_ROCKRETE.get(), "rockrete");
        genFolderedSBI(ModBlocks.GREEN_ROCKRETE.get(),"rockrete");
        genStairsWithRenderTypeSBI(ModBlocks.GREEN_ROCKRETE_STAIRS.get(),"rockrete","green_rockrete","green_rockrete","green_rockrete","solid");
        genSimpleSlabsSBI(ModBlocks.GREEN_ROCKRETE_SLAB.get(), ModBlocks.GREEN_ROCKRETE.get(), "rockrete");
        genFolderedSBI(ModBlocks.YELLOW_ROCKRETE.get(),"rockrete");
        genStairsWithRenderTypeSBI(ModBlocks.YELLOW_ROCKRETE_STAIRS.get(),"rockrete","yellow_rockrete","yellow_rockrete","yellow_rockrete","solid");
        genSimpleSlabsSBI(ModBlocks.YELLOW_ROCKRETE_SLAB.get(),ModBlocks.YELLOW_ROCKRETE.get(), "rockrete");
        genFolderedSBI(ModBlocks.BLUE_ROCKRETE.get(),"rockrete");
        genStairsWithRenderTypeSBI(ModBlocks.BLUE_ROCKRETE_STAIRS.get(),"rockrete","blue_rockrete","blue_rockrete","blue_rockrete","solid");
        genSimpleSlabsSBI(ModBlocks.BLUE_ROCKRETE_SLAB.get(), ModBlocks.BLUE_ROCKRETE.get(),"rockrete");
        genFolderedSBI(ModBlocks.RED_ROCKRETE.get(),"rockrete");
        genStairsWithRenderTypeSBI(ModBlocks.RED_ROCKRETE_STAIRS.get(),"rockrete","red_rockrete","red_rockrete","red_rockrete","solid");
        genSimpleSlabsSBI(ModBlocks.RED_ROCKRETE_SLAB.get(), ModBlocks.RED_ROCKRETE.get(), "rockrete");

        //Misc Rockrete Category Blocks
        genFolderedSBI(ModBlocks.GRIMY_RESTROOM_TILE.get(), "");

        //Rough Rockrete Blocks
        genSimpleTextureToggleSBI(ModBlocks.ROUGH_GRAY_ROCKRETE.get(),"rough_rockrete","solid");
        genStairsWithRenderTypeSBI(ModBlocks.ROUGH_GRAY_ROCKRETE_STAIRS.get(),"rough_rockrete","rough_gray_rockrete","rough_gray_rockrete","rough_gray_rockrete","solid");
        genSimpleSlabsSBI(ModBlocks.ROUGH_GRAY_ROCKRETE_SLAB.get(), ModBlocks.ROUGH_GRAY_ROCKRETE.get(),"rough_rockrete");
        genSimpleTextureToggleSBI(ModBlocks.ROUGH_GREEN_ROCKRETE.get(),"rough_rockrete","solid");
        genStairsWithRenderTypeSBI(ModBlocks.ROUGH_GREEN_ROCKRETE_STAIRS.get(),"rough_rockrete","rough_green_rockrete","rough_green_rockrete","rough_green_rockrete","solid");
        genSimpleSlabsSBI(ModBlocks.ROUGH_GREEN_ROCKRETE_SLAB.get(), ModBlocks.ROUGH_GREEN_ROCKRETE.get(),"rough_rockrete");
        genSimpleTextureToggleSBI(ModBlocks.ROUGH_YELLOW_ROCKRETE.get(),"rough_rockrete","solid");
        genStairsWithRenderTypeSBI(ModBlocks.ROUGH_YELLOW_ROCKRETE_STAIRS.get(),"rough_rockrete","rough_yellow_rockrete","rough_yellow_rockrete","rough_yellow_rockrete","solid");
        genSimpleSlabsSBI(ModBlocks.ROUGH_YELLOW_ROCKRETE_SLAB.get(),ModBlocks.ROUGH_YELLOW_ROCKRETE.get(), "rough_rockrete");
        genSimpleTextureToggleSBI(ModBlocks.ROUGH_BLUE_ROCKRETE.get(),"rough_rockrete","solid");
        genStairsWithRenderTypeSBI(ModBlocks.ROUGH_BLUE_ROCKRETE_STAIRS.get(),"rough_rockrete","rough_blue_rockrete","rough_blue_rockrete","rough_blue_rockrete","solid");
        genSimpleSlabsSBI(ModBlocks.ROUGH_BLUE_ROCKRETE_SLAB.get(), ModBlocks.ROUGH_BLUE_ROCKRETE.get(),"rough_rockrete");
        genSimpleTextureToggleSBI(ModBlocks.ROUGH_RED_ROCKRETE.get(),"rough_rockrete","solid");
        genStairsWithRenderTypeSBI(ModBlocks.ROUGH_RED_ROCKRETE_STAIRS.get(),"rough_rockrete","rough_red_rockrete","rough_red_rockrete","rough_red_rockrete","solid");
        genSimpleSlabsSBI(ModBlocks.ROUGH_RED_ROCKRETE_SLAB.get(), ModBlocks.ROUGH_RED_ROCKRETE.get(),"rough_rockrete");

        //Rockrete Walls ( They use the "rough" texture" but not that prefix word )
        genSimpleWallsSB(ModBlocks.GRAY_ROCKRETE_WALL.get(), ModBlocks.ROUGH_GRAY_ROCKRETE.get(), "rough_rockrete");
        genSimpleWallsSB(ModBlocks.RED_ROCKRETE_WALL.get(), ModBlocks.ROUGH_RED_ROCKRETE.get(), "rough_rockrete");
        genSimpleWallsSB(ModBlocks.YELLOW_ROCKRETE_WALL.get(), ModBlocks.ROUGH_YELLOW_ROCKRETE.get(), "rough_rockrete");
        genSimpleWallsSB(ModBlocks.BLUE_ROCKRETE_WALL.get(), ModBlocks.ROUGH_BLUE_ROCKRETE.get(), "rough_rockrete");
        genSimpleWallsSB(ModBlocks.GREEN_ROCKRETE_WALL.get(), ModBlocks.ROUGH_GREEN_ROCKRETE.get(), "rough_rockrete");

        //Bolted Brackets
        genCornerShapedBlocksSI(ModBlocks.RUSTY_BOLTED_BRACKET.get(),"bolted_bracket","bolted_bracket","bolted_bracket","rusty_bolted_bracket");
        genCornerShapedBlocksSI(ModBlocks.BLACK_BOLTED_BRACKET.get(),"bolted_bracket","bolted_bracket","bolted_bracket","black_bolted_bracket");
        genCornerShapedBlocksSI(ModBlocks.GRAY_BOLTED_BRACKET.get(),"bolted_bracket","bolted_bracket","bolted_bracket","gray_bolted_bracket");

        //Small Bolted Brackets
        genCornerShapedBlocksSI(ModBlocks.SMALL_GRAY_BOLTED_BRACKET.get(),"bolted_bracket","small_bolted_bracket","bolted_bracket","small_rusty_bolted_bracket");
        genCornerShapedBlocksSI(ModBlocks.SMALL_BLACK_BOLTED_BRACKET.get(),"bolted_bracket","small_bolted_bracket","bolted_bracket","small_black_bolted_bracket");
        genCornerShapedBlocksSI(ModBlocks.SMALL_RUSTY_BOLTED_BRACKET.get(),"bolted_bracket","small_bolted_bracket","bolted_bracket","small_gray_bolted_bracket");

        //Outer Pipe Conduits
        genCornerShapedBlocksSI(ModBlocks.COPPER_PIPE_CONDUIT_OUTER_CORNER.get(),"pipe_conduit_corner","pipe_conduit_outer_corner","pipe_conduit","copper_pipe_conduit");
        genCornerShapedBlocksSI(ModBlocks.BRASS_PIPE_CONDUIT_OUTER_CORNER.get(),"pipe_conduit_corner","pipe_conduit_outer_corner","pipe_conduit","brass_pipe_conduit");
        genCornerShapedBlocksSI(ModBlocks.GRAY_PIPE_CONDUIT_OUTER_CORNER.get(),"pipe_conduit_corner","pipe_conduit_outer_corner","pipe_conduit","gray_pipe_conduit");

        //Inner Pipe Conduits
        genCornerShapedBlocksSI(ModBlocks.COPPER_PIPE_CONDUIT_INNER_CORNER.get(),"pipe_conduit_corner","pipe_conduit_inner_corner","pipe_conduit","copper_pipe_conduit");
        genCornerShapedBlocksSI(ModBlocks.BRASS_PIPE_CONDUIT_INNER_CORNER.get(),"pipe_conduit_corner","pipe_conduit_inner_corner","pipe_conduit","brass_pipe_conduit");
        genCornerShapedBlocksSI(ModBlocks.GRAY_PIPE_CONDUIT_INNER_CORNER.get(),"pipe_conduit_corner","pipe_conduit_inner_corner","pipe_conduit","gray_pipe_conduit");

        //Doors (Generated block states. Generated block models from vanilla parent model. NO ITEM MODEL GENERATION. Performed in ModItemModelProvider with manually drawn texture file)
        genDoorSB(ModBlocks.ARMORED_DOOR.get() , "cutout");
        genDoorSB(ModBlocks.STAMPED_METAL_DOOR.get() , "solid");
        genDoorSB(ModBlocks.BULKHEAD_DOOR.get() , "cutout");

        //Trapdoors (Generated block states. Generated block models from vanilla parent model. Generated item models)
        genTrapdoorSBI(ModBlocks.VENT_TRAPDOOR.get(),"solid");
        genTrapdoorSBI(ModBlocks.RUSTY_VENT_TRAPDOOR.get(),"solid");

        //Standard Furniture Blocks (Non-generated models)
        genHorizontalModelledSI(ModBlocks.SINK.get(),"");
        genHorizontalModelledSI(ModBlocks.OFFICE_CHAIR.get(),"office_chair");
        genHorizontalModelledSI(ModBlocks.BLACK_OFFICE_CHAIR.get(), "office_chair");
        genHorizontalModelledSI(ModBlocks.FOLDING_CHAIR.get(), "");
        genHorizontalModelledSI(ModBlocks.WHITE_WALL_MEDKIT.get(),"medkit_containers");
        genHorizontalModelledSI(ModBlocks.RED_WALL_MEDKIT.get(),"medkit_containers");
        genHorizontalModelledSI(ModBlocks.FIRE_EXTINGUISHER.get(), "");
        genHorizontalModelledSI(ModBlocks.URINAL.get(), "urinal");
        genHorizontalModelledSI(ModBlocks.DESKTOP_TOWER.get(), "modern_computer");
        gen3FacedCubeHorizontalSBI(ModBlocks.LOCKER_BOX.get(), "locker", "locker_box"); //bottom face texture same as top face texture
        genFaceAttachedSI(ModBlocks.FUEL_DRUM.get(), "fuel_drum","fuel_drum", "front_labelled", "side_labelled", "side_labelled", "side_labelled", "top", "bottom");
        genFaceAttachedModelledSI(ModBlocks.SMOKE_ALARM.get(), "");

        //Direction-Dependant Model Furniture Blocks ( Model changed based on block state orientation/direction value )
        genFaceAttachedModelledFloorCeilingVariantSI(ModBlocks.CCTV_CAMERA.get(),"cctv_camera","ceiling", "ceiling","wall");

        //Lightable Furniture Blocks ( Models that change texture upon interaction. Light emission handled in ModBlocks block registration )
        genFaceAttachedTextureTogglingSI(ModBlocks.OBLONG_CAGE_LAMP.get(), "lighting");
        genHorizontalTextureTogglingSI(ModBlocks.FLOOR_WORK_LIGHT.get(), "work_light", "work_light_lamp");
        genHorizontalTextureTogglingSI(ModBlocks.RETRO_COMPUTER.get(), "retro_computer", "retro_computer_screen");
        genHorizontalTextureTogglingSI(ModBlocks.RETRO_COMPUTER_2.get(), "retro_computer", "retro_computer_2_screen");
        genHorizontalTextureTogglingSI(ModBlocks.MONITOR_AND_KEYBOARD.get(), "modern_computer", "monitor_screen");

        //Model Toggling Furniture Blocks ( Models that change entirely upon interaction )
        genFaceAttachedModelTogglingSI(ModBlocks.INDUSTRIAL_LEVER.get(), "lever", "on", BlockStateProperties.POWERED);
        genHorizontalModelTogglingSI(ModBlocks.CASSETTE_PLAYER.get(), "cassette_player", "on", BlockStateProperties.POWERED, false);
        genHorizontalModelTogglingSI(ModBlocks.TOILET.get(), "toilet", "on", BlockStateProperties.POWERED, false);

        //Furniture Blocks ( Multi-Block models. Block State generation. No block model generation. NO ITEM MODEL GENERATION. Item .json models must be manually created ) TODO - Make 2D item sprites to save a few kilobytes
        genMultiBlockHorizontalS(ModBlocks.IV_DRIPSTAND.get(), "iv_dripstand");
        genMultiBlockHorizontalS(ModBlocks.OPERATING_TABLE.get(), "operating_table");
        genMultiBlockHorizontalS(ModBlocks.MEDICAL_BED.get(), "medical_bed");
        genMultiBlockHorizontalS(ModBlocks.LARGE_LOCKER.get(), "locker");
        genMultiBlockHorizontalTextureTogglingSI(ModBlocks.WORK_LIGHT_STAND.get(),"work_light", "lit_unlit_textures","1","work_light_lamp_on", new Integer[]{1});
        genMultiBlockHorizontalTextureTogglingSI(ModBlocks.VITALS_MONITOR.get(),"vitals_monitor", "lit_unlit_textures","1","vitals_monitor_screen_on", new Integer[]{1});
    }

    //String Helper Method for optional folders. Will add an extra backslash if a folder is defined.
    private String optionalFolder(String optionalFolderName) {
        return (optionalFolderName+(optionalFolderName.isEmpty() ? "":"/"));
    }

    //---------- CUSTOM BLOCK MODEL GENERATORS ----------

    //Builds a textured model that uses six texture .pngs for all 6 faces.
    private ModelFile build6FaceCubeModel(String blockName, String folderName, String front, String back, String left, String right, String top, String bottom) {
        return models().cube(
                blockName,
                modLoc( "block/" + folderName+ "/" + bottom), //bottom
                modLoc("block/" + folderName+ "/" + top), //top
                modLoc("block/" + folderName+ "/" + front), //front
                modLoc("block/" + folderName+ "/" + back), //back
                modLoc("block/"  + folderName+ "/" + left), //left
                modLoc("block/"  + folderName+ "/" + right) //right
        ).texture("particle", modLoc("block/" + folderName +"/" + top));
    }

    private ModelFile buildDoubleSlabModel(String modelName, String textureSubFolder, String sideTexture, String endTexture) {
        String basePath = "block/";

        return models()
                .withExistingParent(basePath + modelName, mcLoc("block/cube_column"))
                .texture("side", IndustrialHellscape.MOD_ID + ":" + basePath + optionalFolder(textureSubFolder) + sideTexture)
                .texture("end", IndustrialHellscape.MOD_ID + ":" + basePath + optionalFolder(textureSubFolder) + endTexture);
    }

    //Takes existing model from this mod, generate new child model with different texture used for key "#0"
    private ModelFile buildSingleTextureKeyBlockModel(String newBlockID, String modelName, String blockModelSubFolder, String textureSubFolder, String blockTexture, String renderType) {
        return models()
                .withExistingParent(newBlockID, modLoc("block/" + optionalFolder(blockModelSubFolder) + modelName))
                .texture("0", modLoc("block/" + textureSubFolder + "/" + blockTexture))
                .renderType(renderType);
    }

    //---------- END OF CUSTOM BLOCK MODEL GENERATOR METHODS ----------

    //---------- SBI METHODS (STATE, BLOCK MODEL, and/or ITEM MODEL GENERATED) ----------

    //Only generate the item model for the block using already-written block model. Block states and block models are already written.
    private void genCustomI(Block block, String modelFolderName, String stringName) {
        String modelPath = "block/"+optionalFolder(modelFolderName)+stringName;
        simpleBlockItem(block, models().getExistingFile(modLoc(modelPath)));
    }

    //Generate Block Model from vanilla cube model with defined textures for 6 faces. Generate Block States for horizontal-placement block. Generate item model from block.
    private void gen6FacedCubeHorizontalSBI(Block block, String textureSubFolder, String textureName, String frontTextureSuffix, String backTextureSuffix, String leftTextureSuffix, String rightTextureSuffix, String topTextureSuffix, String bottomTextureSuffix) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String generatedModeLPath = "block/"+stringName;

        String texturePrecedingName = textureName+"_";
        String frontTexturePath = texturePrecedingName + frontTextureSuffix;
        String backTexturePath = texturePrecedingName + backTextureSuffix;
        String leftTexturePath = texturePrecedingName + leftTextureSuffix;
        String rightTexturePath = texturePrecedingName + rightTextureSuffix;
        String topTexturePath = texturePrecedingName + topTextureSuffix;
        String bottomTexturePath = texturePrecedingName + bottomTextureSuffix;

        //Generate Model
        ModelFile generatedModel = build6FaceCubeModel(
                stringName,
                textureSubFolder,
                frontTexturePath,
                backTexturePath,
                leftTexturePath,
                rightTexturePath,
                topTexturePath,
                bottomTexturePath);

        //Generate Block States for block that can face four directions
        horizontalBlock(
                block,
                generatedModel
        );

        //Generate Item Model
        simpleBlockItem(block, models().getExistingFile(modLoc(generatedModeLPath)));
    }

    //Generate Block Model from vanilla cube model with 3 defined textures for the 6 faces. Generate Block States for horizontal-placement block. Generate item model from block.
    private void gen3FacedCubeHorizontalSBI(Block block, String textureSubFolder, String textureName) {
        gen6FacedCubeHorizontalSBI(block, textureSubFolder, textureName, "front", "front", "side", "side", "top", "top");
    }

    //Generate Block Model from vanilla cube model with 1 defined texture for all 6 faces. Simple block state generation with no dynamic properties. Generate item model from block.
    private void genFolderedSBI(Block block, String textureSubFolder) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();

        simpleBlockWithItem(block,
                models().withExistingParent(stringName, mcLoc("block/cube_all"))
                        .texture("all", modLoc("block/" + optionalFolder(textureSubFolder) + stringName)));
    }

    //Generate Block model from vanilla cube model with 1 defined texture for all 6 faces. Block State generation for all states combinations, barring waterlogging property. Generate item model from block.
    private void genSimpleTextureToggleSBI(Block block, String textureSubFolder, String renderType) {

        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();

        String baseModelPath = "block/"+stringName;
        String pathToTexture = "block/" + optionalFolder(textureSubFolder);
        String texturePath =  pathToTexture + stringName;

       //GENERATE BASE MODEL
        models().withExistingParent(stringName, mcLoc("block/cube_all"))
                .texture("all", modLoc(texturePath)).renderType(renderType);

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    ResourceLocation model = modLoc(baseModelPath);
                    return ConfiguredModel.builder()
                            .modelFile(models().getBuilder(model.toString()))
                            .build();
                },
                        BlockStateProperties.WATERLOGGED
        );

        //Generate Item Model
        simpleBlockItem(block, models().getExistingFile(modLoc(baseModelPath)));
    }

    //Generate Slab and Double Slab Models with texture located in optional subfolder. Generate Block States. Generate item model from block.
    private void genSimpleSlabsSBI(Block block, Block baseBlock, String textureSubFolder) {
        //For Rockrete slabs which have a homogenous texture and solid full-block model already data-generated
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String generatedModelPath = "block/"+stringName;
        String baseStringName = BuiltInRegistries.BLOCK.getKey(baseBlock).getPath();
        String blockPath = "block/" + optionalFolder(textureSubFolder);
        ResourceLocation slabTexturePath = ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID,blockPath+baseStringName);
        ResourceLocation generatedBaseBlock = ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID,"block/" +baseStringName);

        //Generate slab bottom, top, double models and block state file
        slabBlock(((SlabBlock) block), generatedBaseBlock, slabTexturePath);

        //Generate Item Model
        simpleBlockItem(block, models().getExistingFile(modLoc(generatedModelPath)));

    }

    /*
        Generate WallBlock models. Generate block states. NO ITEM MODEL GENERATION.
        - Item Model Generation performed in ModItemModelProvider
        - Wall Item model is a 3D model distinct from other wall block models and is unused in-world. Only used in the inventory.
        - Item model generation needs to occur in ModItemModelProvider so that the model only generates in the generated item/ folder
     */
    private void genSimpleWallsSB(Block block, Block baseBlock, String textureSubFolder) {
        String textureName = BuiltInRegistries.BLOCK.getKey(baseBlock).getPath();
        String precedingTexturePath = "block/" + optionalFolder(textureSubFolder);
        String texturePath = precedingTexturePath+textureName;

        ResourceLocation wallTexturePath = ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID,texturePath);

        //Generate Block Model and Block States
        wallBlock(((WallBlock) block), wallTexturePath);
    }

    /*
        Vesselplate Slabs - rivets at edges halfway, get cut off. Fix someday by modifying this method
        TODO: Make separate side-only texture for riveted slabs with middle horizontal row(s) of rivets removed.
                - Assume that mods which add vertical slabs have the foresight to rotate the vanilla slab model
                    Instead of generating new ones which might mess up texture orientation

        TODO: Currently, double-slab models for Sheeting, Smooth, and Riveted VP are identical to full blocks
            - Generation of double-slab model is therefore redundant currently
            - Make separate texture for double-slab model someday so slab variants look better and justify double-slab model's existence
     */
    private void genSlabsWithCustomDoubleSBI(Block block, Block parentBlock, String folderName, String doubleSlabName, String sideTexture, String endTexture, String nameStringToReplace, String nameStringReplacement ) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String parentStringName = BuiltInRegistries.BLOCK.getKey(parentBlock).getPath();
        String blockPath = "block/" + folderName+(folderName.isEmpty() ? "":"/");
        String generatedBlockPath = "block/";

        //build custom double-slab model
        buildDoubleSlabModel(doubleSlabName, folderName, sideTexture, endTexture);

        ResourceLocation doubleSlabLoc = ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID,generatedBlockPath + doubleSlabName );
        ResourceLocation textureLoc = ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID,blockPath + parentStringName.replace(nameStringToReplace,nameStringReplacement) );

        slabBlock(((SlabBlock) block), doubleSlabLoc, textureLoc);

        String existingModelPath = "block/"+stringName;
        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));
    }

    //Generate Block Models with defined textures for bottom, top, and sides of stairs, Block States and Item
    private void genStairsWithRenderTypeSBI(Block block, String folderName, String sideTexture, String bottomTexture, String topTexture, String renderType) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String blockPath = "block/" + folderName+(folderName.isEmpty() ? "":"/");

        ResourceLocation bottomTextureLoc = ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID,blockPath+bottomTexture );
        ResourceLocation sideTextureLoc = ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID,blockPath+sideTexture );
        ResourceLocation topTextureLoc = ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID,blockPath+topTexture );

        stairsBlockWithRenderType((StairBlock) block, sideTextureLoc,bottomTextureLoc,topTextureLoc,renderType);

        String existingModelPath = "block/"+stringName;
        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));
    }

    /*
        Generates a Full Block model using custom parent model from this mod with chosen render type ("solid" would be pointless)
        - Anticull block has an inverted cube of offset size, inside the normal full-block cube.

        Block States and Item Models generated.
     */
    private void genTextureToggleAntiCullSBI(Block block, String textureSubFolder, String side, String top, String renderType) {
        //For truss blocks. A side and top texture are required to accomodate Catwalk truss

        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String existingModelPath = "block/"+stringName;

        models()
                .withExistingParent(stringName, modLoc("block/truss/anticull_template"))
                .texture("side", modLoc("block/" + textureSubFolder + "/" + side)) //side
                .texture("top", modLoc("block/" + textureSubFolder + "/" + top)) //top
                .texture("bottom", modLoc("block/" + textureSubFolder + "/" + side)) //bottom
                .renderType(renderType);

        getVariantBuilder(block)
                .forAllStates(state -> {
                    ResourceLocation model = modLoc(existingModelPath);

                    return ConfiguredModel.builder()
                            .modelFile(models().getBuilder(model.toString()))
                            .build();
                });

        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));
    }

        /*
        Generates Slab Block model using custom parent model from this mod with chosen render type ("solid" would be pointless)
        - Anticull block has an inverted cube of offset size, inside the normal slab-block cube.

        Block States and Item Models generated.
        - Double-Slab variant (the base full-block) located for use by omitting "_slab" from block name
        - Assumes models are in "truss" subfolder under block models
        - Models have defined textures for bottom, top, and sides of slab
     */
    private void genAntiCullSlabBlockSI(Block block, String textureSubFolder, String sideTextureName, String topTextureName, String bottomTextureName, String renderType) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();

        ResourceLocation side = modLoc("block/" + textureSubFolder + "/" + sideTextureName);
        ResourceLocation top = modLoc("block/" + textureSubFolder + "/" + topTextureName);
        ResourceLocation bottom =  modLoc("block/" + textureSubFolder + "/" + bottomTextureName);

        ModelFile slabBottom = models()
                .withExistingParent(stringName, modLoc("block/truss/anticull_slab"))
                .texture("side", side) //side
                .texture("top", top) //top
                .texture("bottom", bottom) //bottom
                .renderType(renderType);

        ModelFile slabTop = models()
                .withExistingParent(stringName+"_top", modLoc("block/truss/anticull_slab_top"))
                .texture("side", side) //side
                .texture("top", top) //top
                .texture("bottom", bottom) //bottom
                .renderType(renderType);

        ModelFile slabDouble = models().getExistingFile(modLoc("block/"+stringName.replace("_slab","")));

        getVariantBuilder(block).forAllStates(state -> {
            SlabType type = state.getValue(SlabBlock.TYPE);
            return ConfiguredModel.builder()
                    .modelFile(
                            switch (type) {
                                case TOP -> slabTop;
                                case BOTTOM -> slabBottom;
                                default -> slabDouble;
                            }
                    ).build();
        });

        //Generate Item Model
        itemModels().getBuilder(stringName)
                .parent(slabBottom)
                .renderType(renderType);
    }

    //Generate Block Model. Generate Block States. Generate Item Model.
    private void genTrapdoorSBI(Block block, String renderType) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String existingModelPath = "block/"+stringName;
        trapdoorBlockWithRenderType(((TrapDoorBlock) block), modLoc("block/door/" + stringName), true, renderType);

        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath+"_bottom")));
    }

    //Generate Block Model. Generate Block States. NO ITEM MODEL GENERATION. Performed in ModItemModelProvider with manually drawn texture file
    private void genDoorSB(Block block, String renderType) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        doorBlockWithRenderType(((DoorBlock) block), modLoc("block/door/"+stringName+"_bottom"), modLoc("block/door/"+stringName+"_top"), renderType);
    }

    private void genCornerShapedBlocksSI(Block block, String modelSubFolder, String parentModelName, String textureSubFolder, String textureName) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String blockFolder = "block/"; //generated/resources/assets/models/blocks but also used for mod's non-generated resource folder
        String texturePath = blockFolder+optionalFolder(textureSubFolder)+textureName;
        String generatedModelDestination = blockFolder+stringName;
        String existingParentModelLocation = blockFolder+optionalFolder(modelSubFolder)+parentModelName;

        ModelFile cornerBlockModel = models().withExistingParent(generatedModelDestination, modLoc(existingParentModelLocation))
                .texture("0", texturePath)
                .texture("particle", texturePath);

        getVariantBuilder(block).forAllStatesExcept(state -> {
            Direction facing = state.getValue(FaceAttachedHorizontalDirectionalBlock.FACING);
            AttachFace face = state.getValue(FaceAttachedHorizontalDirectionalBlock.FACE);

            int yRot = switch (facing) {
                case SOUTH -> 270;
                case WEST  -> 0;
                case EAST  -> 180;
                default -> 90; //NORTH
            };

            return ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc(generatedModelDestination)))
                    .rotationX(face == AttachFace.FLOOR ? 0 : (face == AttachFace.WALL ? 90 : 180) )
                    //If the current blockstate is "FLOOR", don't rotate in X direction.
                    //If not,
                    //If the current blockstate is "WALL", rotate in the X direction 90 degrees so the model is on its side.
                    //If not,
                    //Then rotate in the X direction 180 degrees so model is upside down for the last block state, "ceiling"

                    .rotationY(yRot)
                    .build();

        }, BlockStateProperties.WATERLOGGED);

        simpleBlockItem(block, models().getExistingFile(modLoc(generatedModelDestination)));
    }

    private void genFaceAttachedTextureTogglingSI(Block block, String modelSubFolder) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String onModelStringName = stringName + "_on";
        String unpoweredModelPath = "block/"+optionalFolder(modelSubFolder)+stringName;
        String poweredModelPath = "block/"+stringName+"_on"; //generated/resources
        String poweredTexturePath = "block/lit_unlit_textures/"+stringName+"_on";

        //On Model
        ModelFile generatedOnModel = models()
                .withExistingParent(onModelStringName, modLoc(unpoweredModelPath))
                .texture("1", poweredTexturePath);

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    AttachFace face = state.getValue(BlockStateProperties.ATTACH_FACE);
                    Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                    Boolean lit = state.getValue(BlockStateProperties.LIT);
                    String modelToUse = lit? poweredModelPath : unpoweredModelPath;

                    int yRot = switch (facing   ) {
                        case SOUTH -> 180;
                        case WEST  -> 270;
                        case EAST  -> 90;
                        default -> 0; //NORTH
                    };

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc(modelToUse)))
                            .rotationX(face == AttachFace.FLOOR ? 0 : (face == AttachFace.WALL ? 90 : 180) )
                            .rotationY(yRot)
                            .build();
                }, BlockStateProperties.WATERLOGGED);

        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc(unpoweredModelPath)));
    }

    private void genFaceAttachedModelTogglingSI(Block block, String modelSubFolder, String suffixForModelTogglePath, BooleanProperty toggleProperty) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String unpoweredModelPath = "block/"+optionalFolder(modelSubFolder)+stringName;
        String poweredModelPath = unpoweredModelPath+"_"+suffixForModelTogglePath; //NOT GENERATED, EXISTING MODEL

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    AttachFace face = state.getValue(BlockStateProperties.ATTACH_FACE);
                    Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                    Boolean lit = state.getValue(toggleProperty);
                    String modelToUse = lit? poweredModelPath : unpoweredModelPath;

                    int yRot = switch (facing) {
                        case SOUTH -> 180;
                        case WEST  -> 270;
                        case EAST  -> 90;
                        default -> 0; //NORTH
                    };

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc(modelToUse)))
                            .rotationX(face == AttachFace.FLOOR ? 0 : (face == AttachFace.WALL ? 90 : 180) )
                            .rotationY(yRot)
                            .build();
                }, BlockStateProperties.WATERLOGGED);

        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc(unpoweredModelPath)));
    }

    private void genHorizontalModelledSI(Block block, String folderName) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String modelPath = "block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName;

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction horizontalFacing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

                    int yRot = switch (horizontalFacing) {
                        case SOUTH -> 180;
                        case WEST  -> 270;
                        case EAST  -> 90;
                        default -> 0; //NORTH
                    };

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc("block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName)))
                            .rotationY(yRot)
                            .build();
                }, BlockStateProperties.WATERLOGGED);

        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc(modelPath)));
    }

    private void genFullBlockIntegerMultiBlockSI(Block block, String textureSubFolder, int maxStates, IntegerProperty integerProperty) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();

        //make all models first
        for(int i=0; i<maxStates;i++) {
            String blockModelAndTextureName = stringName+"_"+i;
            build6FaceCubeModel(blockModelAndTextureName,
                    textureSubFolder,
                    blockModelAndTextureName,
                    blockModelAndTextureName,
                    blockModelAndTextureName,
                    blockModelAndTextureName,
                    blockModelAndTextureName,
                    blockModelAndTextureName);
        }

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction horizontalFacing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

                    int yRot = switch (horizontalFacing) {
                        case SOUTH -> 180;
                        case WEST  -> 270;
                        case EAST  -> 90;
                        default -> 0; //NORTH
                    };

                    int intBlockState = state.getValue(integerProperty);

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc("block/"+stringName + "_"+intBlockState)))
                            .rotationY(yRot)
                            .build();
                }, BlockStateProperties.WATERLOGGED);

        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc("block/"+stringName + "_"+0)));
    }

    private void genFullBlockIntegerPropFromVanillaAssetsSBI(Block block, String vanillaModelName, IntegerProperty integerProperty) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();

//        //make all models first
//        for(int i=0; i<maxStates;i++) {
//            models().cubeAll(
//                    stringName,
//                    modLoc( "block/" + vanillaModelName+"_"+i) //all sides, append integer value at end of file name automatically
//            )
//            .texture("particle", modLoc("block/" + vanillaModelName));
//        }

        getVariantBuilder(block)
                .forAllStates(state -> {
                    int intBlockState = state.getValue(integerProperty);

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(ResourceLocation.fromNamespaceAndPath("minecraft","block/" + vanillaModelName+"_"+intBlockState)))
                            .build();
                }
        );

        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(ResourceLocation.fromNamespaceAndPath("minecraft","block/" + vanillaModelName+"_"+0)));
    }

    private void genTrussSupportSBI(Block block, String textureSubFolder, EnumProperty<Direction.Axis> axisProperty, BooleanProperty booleanProperty) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();

        //make all models first
        buildSingleTextureKeyBlockModel(stringName+"_horizontal_0", "truss_beam_horizontal_0", "truss", textureSubFolder, stringName, "cutout");
        buildSingleTextureKeyBlockModel(stringName+"_horizontal_1", "truss_beam_horizontal_1", "truss", textureSubFolder, stringName, "cutout");
        buildSingleTextureKeyBlockModel(stringName+"_vertical_0", "truss_beam_vertical_0", "truss", textureSubFolder, stringName, "cutout");
        buildSingleTextureKeyBlockModel(stringName+"_vertical_1", "truss_beam_vertical_1", "truss", textureSubFolder, stringName, "cutout");

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction.Axis axis = state.getValue(axisProperty);
                    boolean altState = state.getValue(booleanProperty);

                    int yRot = switch (axis) {
                        case X -> 90;
                        case Y  -> 0;
                        case Z  -> 0;
                    };

                    String modelVariantName = switch (axis) {
                        case X, Z -> stringName+"_horizontal_" + (altState ? "1" : "0");
                        case Y  -> stringName+"_vertical_" + (altState ? "1" : "0");
                    };


                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc("block/"+modelVariantName)))
                            .rotationY(yRot)
                            .build();

                }, BlockStateProperties.WATERLOGGED);

        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc("block/"+stringName+"_vertical_0")));
    }

    private void genPeanutS(PeanutStatueBlock block, String modelSubFolder) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction horizontalFacing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                    boolean diagonal = state.getValue(PeanutStatueBlock.DIAGONAL);

                    String appendDiagonal = diagonal ? "_diagonal" : "";

                    int yRot = switch (horizontalFacing) {
                        case SOUTH -> 180;
                        case WEST  -> 270;
                        case EAST  -> 90;
                        default -> 0;
                    };

                    int intBlockState = state.getValue(Integer2Block.PART);

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc("block/"+ optionalFolder(modelSubFolder) +stringName  + "_"+intBlockState+appendDiagonal)))
                            .rotationY(yRot)
                            .build();
                }, BlockStateProperties.WATERLOGGED);
    }

    private void genMultiBlockHorizontalS(Block block, String modelSubFolder) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction horizontalFacing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

                    int yRot = switch (horizontalFacing) {
                        case SOUTH -> 180;
                        case WEST  -> 270;
                        case EAST  -> 90;
                        default -> 0; //NORTH
                    };

                    int intBlockState = state.getValue(Integer2Block.PART);

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc("block/"+ optionalFolder(modelSubFolder) +stringName  + "_"+intBlockState)))
                            .rotationY(yRot)
                            .build();
                }, BlockStateProperties.WATERLOGGED);
    }

    private void genBrushableBlockSI(Block block, String textureNameMC) {
        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    int textureInt = state.getValue(BlockStateProperties.DUSTED);

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(ResourceLocation.withDefaultNamespace("block/" +textureNameMC  + "_"+textureInt)))
                            .build();
                }, BlockStateProperties.WATERLOGGED);

        simpleBlockItem(block, models().getExistingFile(ResourceLocation.withDefaultNamespace("block/" +textureNameMC  + "_"+3)) );
    }

    private void genVariantMultiBlock(Block block, Block parentBlock, String originalModelSubFolder, String variantTextureSubFolder, String variantTextureName, String textureKey, Integer[] arrayOfStatesWithModelChange) {
        //Integer Object used for arrayOfStatesWithModelChange instead of Primitive
        //The .contains() comparison only works with Integer Objects

        String variantBlockName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String parentBlockName = BuiltInRegistries.BLOCK.getKey(parentBlock).getPath();
        String parentModelPath = "block/"+optionalFolder(originalModelSubFolder)+parentBlockName;
        String variantGeneratedModelPath = "block/"+variantBlockName; //generated/resources
        String variantTexturePath = "block/"+optionalFolder(variantTextureSubFolder)+variantTextureName;

        //On Model (generated from existing parent)
        for(int i=0; i<arrayOfStatesWithModelChange.length; i++) {
            int stateWithModelChange = arrayOfStatesWithModelChange[i];
            models()
                    .withExistingParent(variantBlockName+"_"+stateWithModelChange, modLoc(parentModelPath+"_"+stateWithModelChange))
                    .texture(textureKey, variantTexturePath);
        }

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction horizontalFacing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                    Integer intBlockState = state.getValue(Integer2Block.PART);
                    boolean partIntDesignatedForModelChange = (Arrays.asList(arrayOfStatesWithModelChange).contains(intBlockState) );

                    String modelToUse = partIntDesignatedForModelChange? variantGeneratedModelPath : parentModelPath;

                    int yRot = switch (horizontalFacing) {
                        case SOUTH -> 180;
                        case WEST  -> 270;
                        case EAST  -> 90;
                        default -> 0; //NORTH
                    };

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc(modelToUse+"_"+intBlockState)))
                            .rotationY(yRot)
                            .build();
                }, BlockStateProperties.WATERLOGGED);
    }

    private void genMultiBlockHorizontalTextureTogglingSI(Block block, String modelSubFolder, String litTextureSubFolder, String lightableTextureKey, String litTextureName, Integer[] arrayOfStatesWithModelChange) {
        //Integer Object used for arrayOfStatesWithModelChange instead of Primitive
        //The .contains() comparison only works with Integer Objects

        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String onModelStringName = stringName + "_on";
        String unpoweredParentModelPath = "block/"+optionalFolder(modelSubFolder)+stringName;
        String poweredModelPath = "block/"+onModelStringName; //generated/resources
        String poweredTexturePath = "block/"+litTextureSubFolder+(litTextureSubFolder.isEmpty() ? "":"/")+litTextureName;

        //On Model (generated from existing parent)
        for(int i=0; i<arrayOfStatesWithModelChange.length; i++) {
            int stateWithModelChange = arrayOfStatesWithModelChange[i];
            models()
                    .withExistingParent(onModelStringName+"_"+stateWithModelChange, modLoc(unpoweredParentModelPath+"_"+stateWithModelChange))
                    .texture(lightableTextureKey, poweredTexturePath);
        }

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction horizontalFacing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                    boolean lit = state.getValue(BlockStateProperties.LIT);
                    Integer intBlockState = state.getValue(Integer2Block.PART);
                    boolean partIntDesignatedForModelChange = (Arrays.asList(arrayOfStatesWithModelChange).contains(intBlockState) );
                    boolean modelNeedsChange = (lit && partIntDesignatedForModelChange);

                    String modelToUse = modelNeedsChange? poweredModelPath : unpoweredParentModelPath;

                    int yRot = switch (horizontalFacing) {
                        case SOUTH -> 180;
                        case WEST  -> 270;
                        case EAST  -> 90;
                        default -> 0; //NORTH
                    };

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc(modelToUse+"_"+intBlockState)))
                            .rotationY(yRot)
                            .build();
                }, BlockStateProperties.WATERLOGGED);
    }

    private void genHorizontalTextureTogglingSI(Block block, String modelSubFolder, String litUnlitTextureName) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String onModelStringName = stringName + "_on";
        String unpoweredModelPath = "block/"+optionalFolder(modelSubFolder)+stringName;
        String poweredModelPath = "block/"+stringName+"_on"; //generated/resources
        String poweredTexturePath = "block/lit_unlit_textures/"+litUnlitTextureName+"_on";

        //On Model
        ModelFile generatedOnModel = models()
                .withExistingParent(onModelStringName, modLoc(unpoweredModelPath))
                .texture("1", poweredTexturePath);

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction horizontalFacing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                    Boolean powered = state.getValue(BlockStateProperties.POWERED);
                    String modelToUse = powered? poweredModelPath : unpoweredModelPath;

                    int yRot = switch (horizontalFacing   ) {
                        case SOUTH -> 180;
                        case WEST  -> 270;
                        case EAST  -> 90;
                        default -> 0; //NORTH
                    };

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc(modelToUse)))
                            .rotationY(yRot)
                            .build();
                }, BlockStateProperties.WATERLOGGED);

        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc(unpoweredModelPath)));
    }

    private void genHorizontalModelTogglingSI(Block block, String modelSubFolder, String suffixForModelTogglePath, BooleanProperty toggleProperty, boolean useToggledTrueStateForItemModel) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String unpoweredModelPath = "block/"+optionalFolder(modelSubFolder)+stringName;
        String poweredModelPath = unpoweredModelPath+"_"+suffixForModelTogglePath;
        String itemModelPath = useToggledTrueStateForItemModel ? poweredModelPath : unpoweredModelPath;

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction horizontalFacing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                    Boolean powered = state.getValue(toggleProperty);
                    String modelToUse = powered? poweredModelPath : unpoweredModelPath;

                    int yRot = switch (horizontalFacing   ) {
                        case SOUTH -> 180;
                        case WEST  -> 270;
                        case EAST  -> 90;
                        default -> 0; //NORTH
                    };

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc(modelToUse)))
                            .rotationY(yRot)
                            .build();
                }, BlockStateProperties.WATERLOGGED);

        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc(itemModelPath)));
    }

    private void genFaceAttachedModelledSI(Block block, String modelSubFolder) {
        //For Blocks like Smoke Alarm (custom model)
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String existingModelPath = "block/"+optionalFolder(modelSubFolder)+stringName;

        getVariantBuilder(block).forAllStatesExcept(state -> {
            Direction facing = state.getValue(FaceAttachedHorizontalDirectionalBlock.FACING);
            AttachFace face = state.getValue(FaceAttachedHorizontalDirectionalBlock.FACE);
            simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));

            return ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc("block/"+optionalFolder(modelSubFolder)+stringName)))
                    .rotationX(face == AttachFace.FLOOR ? 0 : (face == AttachFace.WALL ? 90 : 180) )
                    //If the current blockstate is "FLOOR", don't rotate in X direction.
                    //If not,
                    //If the current blockstate is "WALL", rotate in the X direction 90 degrees so the model is on its side.
                    //If not,
                    //Then rotate in the X direction 180 degrees so model is upside down for the last block state, "ceiling"
                    .rotationY((int) (face == AttachFace.CEILING ? facing : facing.getOpposite()).toYRot())
                    .build();

        }, BlockStateProperties.WATERLOGGED);
    }

    private void genFaceAttachedSI(Block block, String textureSubFolder, String textureName, String frontTextureSuffix, String backTextureSuffix, String leftTextureSuffix, String rightTextureSuffix, String topTextureSuffix, String bottomTextureSuffix) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String generatedModelPath = "block/"+stringName;

        String texturePrecedingName = textureName+"_";
        String frontTexturePath = texturePrecedingName + frontTextureSuffix;
        String backTexturePath = texturePrecedingName + backTextureSuffix;
        String leftTexturePath = texturePrecedingName + leftTextureSuffix;
        String rightTexturePath = texturePrecedingName + rightTextureSuffix;
        String topTexturePath = texturePrecedingName + topTextureSuffix;
        String bottomTexturePath = texturePrecedingName + bottomTextureSuffix;

        //Generate Model
        ModelFile generatedModel = build6FaceCubeModel(
                stringName,
                textureSubFolder,
                frontTexturePath,
                backTexturePath,
                leftTexturePath,
                rightTexturePath,
                topTexturePath,
                bottomTexturePath);

        getVariantBuilder(block).forAllStatesExcept(state -> {
            Direction facing = state.getValue(FaceAttachedHorizontalDirectionalBlock.FACING);
            AttachFace face = state.getValue(FaceAttachedHorizontalDirectionalBlock.FACE);
            simpleBlockItem(block, models().getExistingFile(modLoc(generatedModelPath)));

            return ConfiguredModel.builder()
                    .modelFile(generatedModel)
                    .rotationX(face == AttachFace.FLOOR ? 0 : (face == AttachFace.WALL ? 90 : 180))
                    .rotationY((int) (face == AttachFace.CEILING ? facing : facing.getOpposite()).toYRot())
                    .build();

        }, BlockStateProperties.WATERLOGGED);
    }

    /*
        Generates Block States and Block Item Model for a face-attached block
        - Model changes variant depending on whether block is connected to wall, floor, or ceiling
        - TODO: Make floor variant of CCTV camera... Whatever that would look like
    */
    private void genFaceAttachedModelledFloorCeilingVariantSI(Block block, String modelSubFolder, String floorModelKey, String ceilingModelKey, String wallModelKey) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String blockName = "block/"+optionalFolder(modelSubFolder)+stringName;
        String ceilingModel = blockName+"_"+ceilingModelKey;
        String floorModel = blockName+"_"+floorModelKey;
        String wallModel = blockName+"_"+wallModelKey;

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction horizontalFacing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                    AttachFace surfaceAttached = state.getValue(FaceAttachedHorizontalDirectionalBlock.FACE);

                    String modelToUse = switch(surfaceAttached) {
                        case FLOOR -> floorModel;
                        case WALL -> wallModel;
                        case CEILING -> ceilingModel;
                    };

                    int yRot = switch (horizontalFacing) {
                        case SOUTH -> 180;
                        case WEST  -> 270;
                        case EAST  -> 90;
                        default -> 0; //Assume NORTH by default
                    };

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc(modelToUse)))
                            .rotationY(yRot)
                            .build();
                }, BlockStateProperties.WATERLOGGED);

        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc(wallModel)));
    }

    // Generates Panel Block Models, States, Item Models (multi-blocks that can be re-placed for all 6 sides)
    private void genMultiStatePanelSBI(PanelBlock block, String modelSubFolder, String parentModelName, String faceTextureSubFolder, String edgeTextureSubFolder, String faceTextureFileName, String edgeTextureFileName) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String blockFolder = "block/"; //generated/resources/assets/models/blocks but also used for mod's non-generated resource folder
        String generatedModelDestination = blockFolder+stringName;
        String existingParentModelLocation = blockFolder+optionalFolder(modelSubFolder)+parentModelName;
        String faceTexturePath = blockFolder+optionalFolder(faceTextureSubFolder)+faceTextureFileName;
        String edgeTexturePath = blockFolder+optionalFolder(edgeTextureSubFolder)+edgeTextureFileName;

        //ModelFile floorPanelModelFile = models().getExistingFile(modLoc(parentModelPath));
        ModelFile floorPanelModelFile = models().withExistingParent(generatedModelDestination, modLoc(existingParentModelLocation))
                .texture("0", faceTexturePath)
                .texture("1", edgeTexturePath);

        MultiPartBlockStateBuilder multipartBuilder = getMultipartBuilder(block);

        //preallocate for-loop variables;
        int xDegrees = 0;
        int yDegrees = 0;

        //Determine correct X and Y model rotations based on block state
        for(Direction dir : BlockStateProperties.FACING.getPossibleValues()) {
            switch(dir) {
                case NORTH -> {
                    xDegrees = 270; //right hand rule (negative)
                    yDegrees = 0;
                }
                case WEST -> {
                    xDegrees = 270; //orig 90
                    yDegrees = 270; //orig 90
                }
                case SOUTH -> {
                    xDegrees = 270;
                    yDegrees = 180;
                }
                case EAST -> {
                    xDegrees = 270; //orig 90
                    yDegrees = 90; //orig 270
                }

                case UP -> {
                    xDegrees = 180;
                    yDegrees = 0;
                }
                case DOWN -> {
                    xDegrees = 0;
                    yDegrees = 0;
                }
            }

            MultiPartBlockStateBuilder.PartBuilder panelModelBuilder = multipartBuilder.part().modelFile(floorPanelModelFile).rotationX(xDegrees).rotationY(yDegrees).addModel();
            panelModelBuilder.useOr()
                    .condition(switch(dir) {
                                case NORTH -> BlockStateProperties.NORTH;
                                case WEST -> BlockStateProperties.WEST;
                                case EAST -> BlockStateProperties.EAST;
                                case SOUTH -> BlockStateProperties.SOUTH;

                                case UP -> BlockStateProperties.UP;
                                case DOWN -> BlockStateProperties.DOWN;
                            }
                            , true);
            panelModelBuilder.end();
        }

        simpleBlockItem(block, models().getExistingFile(modLoc(generatedModelDestination)));
    }

    private void genFenceBlockSB(FenceBlock block, String modelSubFolder, String parentPostName, String parentFenceName, String textureSubFolder, String postTextureFileName, String fenceTextureFileName) {
        String blockFolder = "block/";
        String fenceModelName = BuiltInRegistries.BLOCK.getKey(block).getPath(); //Same as block id, but appended with "_post"
        String postModelName = fenceModelName+"_post";

        //Already existing (Parent) model files
        String parentPostModelLocation = blockFolder+optionalFolder(modelSubFolder)+parentPostName;
        String parentFenceModelLocation = blockFolder+optionalFolder(modelSubFolder)+parentFenceName;

        //Generated model files
        String generatedPostModelLocation = blockFolder+postModelName;
        String generatedFenceModelLocation = blockFolder+fenceModelName;

        //Texture path
        String postTexturePath = blockFolder+optionalFolder(textureSubFolder)+postTextureFileName;
        String fenceTexturePath = blockFolder+optionalFolder(textureSubFolder)+fenceTextureFileName;

        //Post Model
        ModelFile postModelFile = models().withExistingParent(generatedPostModelLocation, modLoc(parentPostModelLocation)).texture("0", postTexturePath);
        //Fence Model
        ModelFile fenceModelFile = models().withExistingParent(generatedFenceModelLocation, modLoc(parentFenceModelLocation)).texture("0", fenceTexturePath);

        //Main
        MultiPartBlockStateBuilder multipartBuilder = getMultipartBuilder(block);

        //preallocate for-loop variables;
        int yDegrees = 0;

        //Generate Fence model states
        for(Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            switch (dir) {
                case NORTH -> {
                    yDegrees = 0;
                }
                case WEST -> {
                    yDegrees = 270;
                }
                case SOUTH -> {
                    yDegrees = 180;
                }
                case EAST -> {
                    yDegrees = 90;
                }
            }

            MultiPartBlockStateBuilder.PartBuilder fenceModelBuilder = multipartBuilder.part().modelFile(fenceModelFile).rotationY(yDegrees).addModel();
            fenceModelBuilder.useOr()
                    .condition(switch(dir) {
                                case WEST -> BlockStateProperties.WEST;
                                case EAST -> BlockStateProperties.EAST;
                                case SOUTH -> BlockStateProperties.SOUTH;
                                default -> BlockStateProperties.NORTH;
                                }, true);
            fenceModelBuilder.end();
        }
        //----- Generate Fence Post model state -----
        multipartBuilder.part().modelFile(postModelFile).addModel()
                .conditions.put(BlockStateProperties.UP, true);
    }

    private void genOrthoRotFencePostS(FenceBlock block, String modelSubFolder, String postFileName, String fenceFileName) {
        String blockFolder = "block/";

        //Already existing model files
        String existingPostModelLocation = blockFolder+optionalFolder(modelSubFolder)+postFileName;
        String existingFenceModelLocation = blockFolder+optionalFolder(modelSubFolder)+fenceFileName;

        //Post Model
        ModelFile postModelFile = models().getExistingFile(modLoc(existingPostModelLocation));
        //Fence Model
        ModelFile fenceModelFile = models().getExistingFile(modLoc(existingFenceModelLocation));

        //Main
        MultiPartBlockStateBuilder multipartBuilder = getMultipartBuilder(block);

        //preallocate for-loop variables;
        int yDegrees = 0;

        //Generate Fence model states
        for(Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            switch (dir) {
                case NORTH -> {
                    yDegrees = 0;
                }
                case WEST -> {
                    yDegrees = 270;
                }
                case SOUTH -> {
                    yDegrees = 180;
                }
                case EAST -> {
                    yDegrees = 90;
                }
            }

            MultiPartBlockStateBuilder.PartBuilder fenceModelBuilder = multipartBuilder.part().modelFile(fenceModelFile).rotationY(yDegrees).addModel();
            fenceModelBuilder.useOr()
                    .condition(switch(dir) {
                        case WEST -> BlockStateProperties.WEST;
                        case EAST -> BlockStateProperties.EAST;
                        case SOUTH -> BlockStateProperties.SOUTH;
                        default -> BlockStateProperties.NORTH;
                    }, true);
            fenceModelBuilder.end();
        }
        //----- Generate Fence Post model state -----

        //Barbed Wire FenceTop    //Condition: UP AND (EAST OR WEST)
        MultiPartBlockStateBuilder.PartBuilder fencePostSuperGroup_UandEorW = multipartBuilder.part().modelFile(postModelFile).rotationY(90).addModel();
        fencePostSuperGroup_UandEorW
                .nestedGroup() //create a subgroup inside super-group
                .condition(BlockStateProperties.UP, true);
        //.endNestedGroup(); //appears to be optional here
        fencePostSuperGroup_UandEorW
                .nestedGroup() //create another second subgroup inside super-group
                .useOr() //conditions within this subgroup are OR
                .condition(BlockStateProperties.EAST, true)
                .condition(BlockStateProperties.WEST, true);
        //.endNestedGroup(); //appears to be optional here
        fencePostSuperGroup_UandEorW.end(); //closes the exterior group (super-group)

        //Barbed Wire FenceTop     //Condition: UP AND (NORTH OR SOUTH)
        MultiPartBlockStateBuilder.PartBuilder fencePostSuperGroupUandNorS = multipartBuilder.part().modelFile(postModelFile).rotationY(0).addModel();
        fencePostSuperGroupUandNorS
                .nestedGroup() //create a subgroup inside super-group
                .condition(BlockStateProperties.UP, true);
        //.endNestedGroup(); //appears to be optional here
        fencePostSuperGroupUandNorS
                .nestedGroup() //create another subgroup inside super-group
                .useOr() //conditions within this subgroup are OR
                .condition(BlockStateProperties.NORTH, true)
                .condition(BlockStateProperties.SOUTH, true);
        //.endNestedGroup(); //appears to be optional here
        fencePostSuperGroupUandNorS.end(); //closes the exterior group (super-group)

        //Barbed Wire FenceTop     //Condition: UP AND (NSEW all false)
        MultiPartBlockStateBuilder.PartBuilder fencePostSuperGroupSoloConnection = multipartBuilder.part().modelFile(postModelFile).rotationY(0).addModel();
        fencePostSuperGroupSoloConnection
                .nestedGroup()  //create a subgroup inside super-group
                .condition(BlockStateProperties.UP, true);
        //.endNestedGroup(); //appears to be optional here
        fencePostSuperGroupSoloConnection
                .nestedGroup()  //create another subgroup inside super-group
//                .useOr() //conditions within groups are AND by default
                .condition(BlockStateProperties.NORTH, false)
                .condition(BlockStateProperties.SOUTH, false)
                .condition(BlockStateProperties.EAST, false)
                .condition(BlockStateProperties.WEST, false);
        //.endNestedGroup(); //appears to be optional here
        fencePostSuperGroupSoloConnection.end(); //closes the exterior group (super-group)

        //Barbed Wire FenceTop     //Condition: UP AND (NSEW all false), identical to before, but adds a 90 degree rotated barbed post model
        MultiPartBlockStateBuilder.PartBuilder fencePostSuperGroupSoloConnection_yRot90 = multipartBuilder.part().modelFile(postModelFile).rotationY(90).addModel();
        fencePostSuperGroupSoloConnection_yRot90
                .nestedGroup() //create a subgroup inside super-group
                .condition(BlockStateProperties.UP, true);
        //.endNestedGroup(); //appears to be optional here
        fencePostSuperGroupSoloConnection_yRot90
                .nestedGroup()  //create another subgroup inside super-group
//                .useOr() //conditions within groups are AND by default
                .condition(BlockStateProperties.NORTH, false)
                .condition(BlockStateProperties.SOUTH, false)
                .condition(BlockStateProperties.EAST, false)
                .condition(BlockStateProperties.WEST, false);
        //.endNestedGroup(); //appears to be optional here
        fencePostSuperGroupSoloConnection_yRot90.end(); //closes the exterior group (super-group)
    }

    private void genRailingBlockSI(RailingBlock block, String modelSubFolder, String parentModelName, String textureSubFolder, String textureName) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String blockFolder = "block/"; //generated/resources/assets/models/blocks but also used for mod's non-generated resource folder
        String generatedModelDestination = blockFolder+stringName;
        String existingParentModelLocation = blockFolder+optionalFolder(modelSubFolder)+parentModelName;
        String faceTexturePath = blockFolder+optionalFolder(textureSubFolder)+textureName;

        //railing model
        ModelFile railingModelFile = models().withExistingParent(generatedModelDestination, modLoc(existingParentModelLocation))
                .texture("0", faceTexturePath)
                .texture("particle", faceTexturePath);

        MultiPartBlockStateBuilder multipartBuilder = getMultipartBuilder(block);

        //preallocate for-loop variables;
        int yDegrees = 0;

        //Generate Fence model states
        for(Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            switch (dir) {
                case NORTH -> {
                    yDegrees = 0;
                }
                case WEST -> {
                    yDegrees = 270;
                }
                case SOUTH -> {
                    yDegrees = 180;
                }
                case EAST -> {
                    yDegrees = 90;
                }
            }

            MultiPartBlockStateBuilder.PartBuilder railingModelBuilder = multipartBuilder.part().modelFile(railingModelFile).rotationY(yDegrees).addModel();
            railingModelBuilder.useOr()
                    .condition(switch(dir) {
                        case WEST -> BlockStateProperties.WEST;
                        case EAST -> BlockStateProperties.EAST;
                        case SOUTH -> BlockStateProperties.SOUTH;
                        default -> BlockStateProperties.NORTH;
                    }, true);
            railingModelBuilder.end();
        }

        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc(generatedModelDestination)));

    }

    private void genParapetBlockS(ParapetBlock block, String modelSubFolder, String parentModelName, String textureSubFolder, String textureName) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String blockFolder = "block/"; //generated/resources/assets/models/blocks but also used for mod's non-generated resource folder
        String texturePath = blockFolder+optionalFolder(textureSubFolder)+textureName;
        String innerCornerSuffix = "_ic";
        String outerCornerSuffix = "_oc";
        String mainWallSuffix = "_wall";

        String generatedModelDestination_IC = blockFolder+stringName+innerCornerSuffix; //block/gray_rockrete_parapet_ic
        String existingParentModelLocation_IC = blockFolder+optionalFolder(modelSubFolder)+parentModelName+innerCornerSuffix; //block/parapet/rockrete_parapet_ic

        String generatedModelDestination_OC = blockFolder+stringName+outerCornerSuffix;
        String existingParentModelLocation_OC = blockFolder+optionalFolder(modelSubFolder)+parentModelName+outerCornerSuffix;

        String generatedModelDestination_Wall = blockFolder+stringName+mainWallSuffix;
        String existingParentModelLocation_Wall = blockFolder+optionalFolder(modelSubFolder)+parentModelName+mainWallSuffix;

        //Inner Corner Model
        ModelFile parapetModelFile_IC = models().withExistingParent(generatedModelDestination_IC, modLoc(existingParentModelLocation_IC))
                .texture("0", texturePath)
                .texture("particle", texturePath);

        //Outer Corner Model
        ModelFile parapetModelFile_OC = models().withExistingParent(generatedModelDestination_OC, modLoc(existingParentModelLocation_OC))
                .texture("0", texturePath)
                .texture("particle", texturePath);

        //Main Wall Model
        ModelFile parapetModelFile_Wall = models().withExistingParent(generatedModelDestination_Wall, modLoc(existingParentModelLocation_Wall))
                .texture("0", texturePath)
                .texture("particle", texturePath);

        MultiPartBlockStateBuilder multipartBuilder = getMultipartBuilder(block);

        //preallocate for-loop variables;
        int yDegrees = 0;

        //Generate Fence model states
        for(Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            switch (dir) {
                case NORTH -> {
                    yDegrees = 0;
                }
                case WEST -> {
                    yDegrees = 270;
                }
                case SOUTH -> {
                    yDegrees = 180;
                }
                case EAST -> {
                    yDegrees = 90;
                }
            }

            //Inner Corner (IC NW model applies to North and West booleans
            MultiPartBlockStateBuilder.PartBuilder parapetBuilder_IC = multipartBuilder.part().modelFile(parapetModelFile_IC).rotationY(yDegrees).addModel();
            parapetBuilder_IC.useOr()
                    .condition(switch (dir) {
                        case WEST -> BlockStateProperties.WEST; //correct
                        case EAST -> BlockStateProperties.EAST; //correct
                        case SOUTH -> BlockStateProperties.SOUTH;
                        default -> BlockStateProperties.NORTH;
                    }, true)
                    .condition(switch (dir) {
                        case WEST -> BlockStateProperties.SOUTH; //correct //orig south
                        case EAST -> BlockStateProperties.NORTH; //correct //orig north
                        case SOUTH -> BlockStateProperties.EAST; //orig east
                        default -> BlockStateProperties.WEST; //orig west
                    }, true);
            parapetBuilder_IC.end();

            //Outer Corner
            MultiPartBlockStateBuilder.PartBuilder parapetBuilder_OC = multipartBuilder.part().modelFile(parapetModelFile_OC).rotationY(yDegrees).addModel();
            parapetBuilder_OC.useOr()
                    .condition(switch(dir) {
                        case WEST -> ParapetBlock.SOUTH_WEST_FENCE;
                        case EAST -> ParapetBlock.NORTH_EAST_FENCE;
                        case SOUTH -> ParapetBlock.SOUTH_EAST_FENCE;
                        default -> ParapetBlock.NORTH_WEST_FENCE;
                    }, true);
            parapetBuilder_OC.end();

            //Main Wall
            MultiPartBlockStateBuilder.PartBuilder parapetBuilder_Wall = multipartBuilder.part().modelFile(parapetModelFile_Wall).rotationY(yDegrees).addModel();
            parapetBuilder_Wall.useOr()
                    .condition(switch (dir) {
                        case WEST -> BlockStateProperties.WEST;
                        case EAST -> BlockStateProperties.EAST;
                        case SOUTH -> BlockStateProperties.SOUTH;
                        default -> BlockStateProperties.NORTH;
                    }, true);
            parapetBuilder_Wall.end();
        }
    }
}
