package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks.IntegerMultiBlock;
import net.boat.industrialhellscape.block.modded_block_classes.SurfaceMountBlocks.SurfaceMountBlock;
import net.boat.industrialhellscape.block.modded_block_classes.TextureToggleBlocks.SimpleTextureToggleBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Arrays;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, IndustrialHellscape.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //Debug Blocks
        //genSimpleSBI(ModBlocks.PROTOTYPE_MACHINE.get(), build3FaceTexturesBlockModel("prototype_machine","experimental", "prototype_machine_front", "prototype_machine_side", "prototype_machine_top"));
        genFullBlockIntegerMultiBlockSI(ModBlocks.DEBUG_BLOCK.get(), "debug_textures", 4);
        genModelledIntegerMultiBlockS(ModBlocks.BODY_PILLOW.get(), "body_pillow");

        //Base Building Blocks
        genSimpleSBI(ModBlocks.METALWORKS.get(), build6FaceTexturesBlockModel("metalworks","metalworks","metalworks_side","metalworks_side", "metalworks_side","metalworks_side","metalworks_top","metalworks_bottom"));
        genHorizontalSBI(ModBlocks.PIPEWORKS.get(), build3FaceTexturesBlockModel("pipeworks", "pipeworks", "pipeworks_front", "pipeworks_sides", "pipeworks_top"));
        genHorizontalSBI(ModBlocks.IHEA_FURNITURE_KIT.get(), build6FaceTexturesBlockModel("ihea_furniture_kit", "furniture_category_block", "ihea_furniture_kit_front", "ihea_furniture_kit_back", "ihea_furniture_kit_right", "ihea_furniture_kit_left", "ihea_furniture_kit_top", "ihea_furniture_kit_bottom"));

        //Furnishing Category Blocks
        genHorizontalSBI(ModBlocks.SAFETY_FURNISHINGS.get(), build3FaceTexturesBlockModel("safety_furnishings", "furniture_category_block", "safety_furnishings_north", "safety_furnishings_west", "safety_furnishings_up"));
        genHorizontalSBI(ModBlocks.HYGIENE_FURNISHINGS.get(), build3FaceTexturesBlockModel("hygiene_furnishings","furniture_category_block", "hygiene_furnishings_north", "hygiene_furnishings_west", "hygiene_furnishings_up"));
        genHorizontalSBI(ModBlocks.INDUSTRIAL_FURNISHINGS.get(), build3FaceTexturesBlockModel("industrial_furnishings","furniture_category_block", "industrial_furnishings_north", "industrial_furnishings_west", "industrial_furnishings_up"));
        genHorizontalSBI(ModBlocks.TECHNOLOGY_FURNISHINGS.get(), build3FaceTexturesBlockModel("technology_furnishings","furniture_category_block", "technology_furnishings_north", "technology_furnishings_west", "technology_furnishings_up"));
        genHorizontalSBI(ModBlocks.AMENITY_FURNISHINGS.get(), build3FaceTexturesBlockModel("amenity_furnishings","furniture_category_block", "amenity_furnishings_north", "amenity_furnishings_west", "amenity_furnishings_up"));

        //Duct Blocks
        genFolderedSBI(ModBlocks.DUCT.get(), "duct");
        genFolderedSBI(ModBlocks.RUSTY_DUCT.get(), "duct");

        //Railing Blocks
        genI(ModBlocks.YELLOW_RAILING.get(),"railing");
        genCustomI(ModBlocks.YELLOW_STAIR_RAILING.get(),"railing","yellow_stair_rail_left");
        genI(ModBlocks.GRAY_RAILING.get(),"railing");
        genCustomI(ModBlocks.GRAY_STAIR_RAILING.get(),"railing","gray_stair_rail_left");
        genI(ModBlocks.BLACK_RAILING.get(),"railing");
        genCustomI(ModBlocks.BLACK_STAIR_RAILING.get(),"railing","black_stair_rail_left");
        genI(ModBlocks.RUSTY_RAILING.get(),"railing");
        genCustomI(ModBlocks.RUSTY_STAIR_RAILING.get(),"railing","rusty_stair_rail_left");

        //vent Blocks
        genSimpleTextureToggleSBI(ModBlocks.HORIZONTAL_VENT.get(),"vent", "solid");
        genSimpleTextureToggleSBI(ModBlocks.VERTICAL_VENT.get(),"vent", "solid");
        genSimpleTextureToggleSBI(ModBlocks.HORIZONTAL_CUTOUT_VENT.get(),"vent","cutout");
        genSimpleTextureToggleSBI(ModBlocks.VERTICAL_CUTOUT_VENT.get(),"vent","cutout");

        genSimpleTextureToggleSBI(ModBlocks.RUSTY_HORIZONTAL_VENT.get(),"vent", "solid");
        genSimpleTextureToggleSBI(ModBlocks.RUSTY_VERTICAL_VENT.get(),"vent", "solid");
        genSimpleTextureToggleSBI(ModBlocks.RUSTY_HORIZONTAL_CUTOUT_VENT.get(),"vent","cutout");
        genSimpleTextureToggleSBI(ModBlocks.RUSTY_VERTICAL_CUTOUT_VENT.get(),"vent","cutout");

        //Vesselplate Blocks
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

        genSimpleTextureToggleSBI(ModBlocks.RUSTY_HORIZONTAL_VESSELPLATE.get(),"vesselplate", "solid");
        genSimpleTextureToggleSBI(ModBlocks.RUSTY_VERTICAL_VESSELPLATE.get(),"vesselplate",  "solid");
        genSimpleTextureToggleSBI(ModBlocks.RUSTY_HORIZONTAL_REINFORCED_VESSELPLATE.get(),"vesselplate", "solid");
        genSimpleTextureToggleSBI(ModBlocks.RUSTY_VERTICAL_REINFORCED_VESSELPLATE.get(),"vesselplate",  "solid");

        genSimpleTextureToggleSBI(ModBlocks.SMOOTH_RUSTY_VESSELPLATE.get(),"vesselplate","solid");
        genStairsWithRenderTypeSBI(ModBlocks.SMOOTH_RUSTY_VESSELPLATE_STAIRS.get(),"vesselplate","smooth_rusty_vesselplate","smooth_rusty_vesselplate","smooth_rusty_vesselplate","solid");
        genSlabsWithCustomDoubleSBI(ModBlocks.SMOOTH_RUSTY_VESSELPLATE_SLAB.get(), ModBlocks.SMOOTH_RUSTY_VESSELPLATE.get(),"vesselplate","smooth_rusty_vesselplate_double_slab","smooth_rusty_vesselplate", "smooth_rusty_vesselplate","","");

        genSimpleTextureToggleSBI(ModBlocks.RUSTY_RIVETED_VESSELPLATE.get(),"vesselplate","solid");
        genStairsWithRenderTypeSBI(ModBlocks.RUSTY_RIVETED_VESSELPLATE_STAIRS.get(),"vesselplate","rusty_riveted_vesselplate","rusty_riveted_vesselplate","rusty_riveted_vesselplate","solid");
        genSlabsWithCustomDoubleSBI(ModBlocks.RUSTY_RIVETED_VESSELPLATE_SLAB.get(), ModBlocks.RUSTY_RIVETED_VESSELPLATE.get(),"vesselplate","rusty_riveted_vesselplate_double_slab","rusty_riveted_vesselplate", "rusty_riveted_vesselplate","","");

        //Truss Blocks
        genTextureToggleAntiCullSBI(ModBlocks.TRUSS.get(),"truss", "truss","truss", "cutout");
        genStairsWithRenderTypeSBI(ModBlocks.TRUSS_STAIRS.get(),"truss","reinforced_truss","truss","truss","cutout");
        genAntiCullSlabBlockSBI(ModBlocks.TRUSS_SLAB.get(), "truss", "truss", "truss","truss","cutout");

        genTextureToggleAntiCullSBI(ModBlocks.CATWALK_TRUSS.get(),"truss", "truss","floorgrate_catwalk", "cutout");
        genStairsWithRenderTypeSBI(ModBlocks.CATWALK_TRUSS_STAIRS.get(),"truss","reinforced_truss","truss","floorgrate_catwalk","cutout");
        genAntiCullSlabBlockSBI(ModBlocks.CATWALK_TRUSS_SLAB.get(), "truss", "truss", "floorgrate_catwalk","truss","cutout");

        genTextureToggleAntiCullSBI(ModBlocks.GRAY_TRUSS.get(),"truss", "gray_truss","gray_truss", "cutout");
        genStairsWithRenderTypeSBI(ModBlocks.GRAY_TRUSS_STAIRS.get(),"truss","gray_reinforced_truss","gray_truss","gray_truss","cutout");
        genAntiCullSlabBlockSBI(ModBlocks.GRAY_TRUSS_SLAB.get(), "truss", "gray_truss", "gray_truss","gray_truss","cutout");

        genTextureToggleAntiCullSBI(ModBlocks.GRAY_CATWALK_TRUSS.get(),"truss", "gray_truss","gray_floorgrate_catwalk", "cutout");
        genStairsWithRenderTypeSBI(ModBlocks.GRAY_CATWALK_TRUSS_STAIRS.get(),"truss","gray_reinforced_truss","gray_truss","gray_floorgrate_catwalk","cutout");
        genAntiCullSlabBlockSBI(ModBlocks.GRAY_CATWALK_TRUSS_SLAB.get(), "truss", "gray_truss", "gray_floorgrate_catwalk","gray_truss", "cutout");

        genTextureToggleAntiCullSBI(ModBlocks.RUSTY_TRUSS.get(),"truss", "rusty_truss","rusty_truss", "cutout");
        genStairsWithRenderTypeSBI(ModBlocks.RUSTY_TRUSS_STAIRS.get(),"truss","rusty_reinforced_truss","rusty_truss","rusty_truss","cutout");
        genAntiCullSlabBlockSBI(ModBlocks.RUSTY_TRUSS_SLAB.get(), "truss", "rusty_truss", "rusty_truss","rusty_truss","cutout");

        genTextureToggleAntiCullSBI(ModBlocks.RUSTY_CATWALK_TRUSS.get(),"truss", "rusty_truss","rusty_floorgrate_catwalk", "cutout");
        genStairsWithRenderTypeSBI(ModBlocks.RUSTY_CATWALK_TRUSS_STAIRS.get(),"truss","rusty_reinforced_truss","rusty_truss","rusty_floorgrate_catwalk","cutout");
        genAntiCullSlabBlockSBI(ModBlocks.RUSTY_CATWALK_TRUSS_SLAB.get(), "truss", "rusty_truss", "rusty_floorgrate_catwalk","rusty_truss","cutout");

        //Vesselglass Blocks
        genSimpleTextureToggleSBI(ModBlocks.VESSELGLASS.get(),"vesselglass","translucent");
        genSimpleTextureToggleSBI(ModBlocks.REINFORCED_VESSELGLASS.get(),"vesselglass", "translucent");
        genSimpleTextureToggleSBI(ModBlocks.GRAY_VESSELGLASS.get(),"vesselglass",  "translucent");
        genSimpleTextureToggleSBI(ModBlocks.GRAY_REINFORCED_VESSELGLASS.get(),"vesselglass","translucent");
        genSimpleTextureToggleSBI(ModBlocks.RUSTY_VESSELGLASS.get(),"vesselglass",  "translucent");
        genSimpleTextureToggleSBI(ModBlocks.RUSTY_REINFORCED_VESSELGLASS.get(),"vesselglass","translucent");

        //Rockrete Blocks
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

        genFolderedSBI(ModBlocks.GRIMY_RESTROOM_TILE.get(), "");

        genSimpleWallsSB(ModBlocks.GRAY_ROCKRETE_WALL.get(), ModBlocks.ROUGH_GRAY_ROCKRETE.get(), "rough_rockrete");
        genSimpleWallsSB(ModBlocks.RED_ROCKRETE_WALL.get(), ModBlocks.ROUGH_RED_ROCKRETE.get(), "rough_rockrete");
        genSimpleWallsSB(ModBlocks.YELLOW_ROCKRETE_WALL.get(), ModBlocks.ROUGH_YELLOW_ROCKRETE.get(), "rough_rockrete");
        genSimpleWallsSB(ModBlocks.BLUE_ROCKRETE_WALL.get(), ModBlocks.ROUGH_BLUE_ROCKRETE.get(), "rough_rockrete");
        genSimpleWallsSB(ModBlocks.GREEN_ROCKRETE_WALL.get(), ModBlocks.ROUGH_GREEN_ROCKRETE.get(), "rough_rockrete");

        //BRACKETS
        genCornerShapedBlocksSI(ModBlocks.RUSTY_BOLTED_BRACKET.get(),"bolted_bracket");
        genCornerShapedBlocksSI(ModBlocks.BLACK_BOLTED_BRACKET.get(),"bolted_bracket");
        genCornerShapedBlocksSI(ModBlocks.GRAY_BOLTED_BRACKET.get(),"bolted_bracket");
        genCornerShapedBlocksSI(ModBlocks.SMALL_GRAY_BOLTED_BRACKET.get(),"bolted_bracket");
        genCornerShapedBlocksSI(ModBlocks.SMALL_BLACK_BOLTED_BRACKET.get(),"bolted_bracket");
        genCornerShapedBlocksSI(ModBlocks.SMALL_RUSTY_BOLTED_BRACKET.get(),"bolted_bracket");

        //Doors
        genDoorSBI(ModBlocks.ARMORED_DOOR.get() , "cutout");
        genDoorSBI(ModBlocks.STAMPED_METAL_DOOR.get() , "solid");
        genDoorSBI(ModBlocks.BULKHEAD_DOOR.get() , "cutout");

        //Trapdoors
        genTrapdoorSBI(ModBlocks.VENT_TRAPDOOR.get(),"solid");
        genTrapdoorSBI(ModBlocks.RUSTY_VENT_TRAPDOOR.get(),"solid");

        //FURNITURE BLOCKS WITH EXISTING BLOCK MODELS
        genFacingModelledSI(ModBlocks.SINK.get(),"");
        genFacingModelledSI(ModBlocks.OFFICE_CHAIR.get(),"office_chair");
        genFacingModelledSI(ModBlocks.BLACK_OFFICE_CHAIR.get(), "office_chair");
        genFacingModelledSI(ModBlocks.FOLDING_CHAIR.get(), "");
        genFacingModelledSI(ModBlocks.WHITE_WALL_MEDKIT.get(),"medkit_containers");
        genFacingModelledSI(ModBlocks.RED_WALL_MEDKIT.get(),"medkit_containers");
        genFacingModelledSI(ModBlocks.FIRE_EXTINGUISHER.get(), "");
        genFacingPoweredSI(ModBlocks.TOILET.get(), "toilet");
        genAttachedSI(ModBlocks.SMOKE_ALARM.get(), "");
        genAttachedSBI(ModBlocks.FUEL_DRUM.get(), "", build6FaceTexturesBlockModel("fuel_drum","fuel_drum", "red_labeled_fuel_drum_front", "red_labeled_fuel_drum_front", "red_labeled_fuel_drum_side", "red_labeled_fuel_drum_side", "red_fuel_drum_up","red_fuel_drum_down"));
        genWAllVsCeilingSI(ModBlocks.CCTV_CAMERA.get(),"cctv_camera","ceiling","wall");
        genFacingLightSI(ModBlocks.FLOOR_WORK_LIGHT.get(), "work_light", "work_light_lamp");
        genInteractableIntegerMultiBlock(ModBlocks.WORK_LIGHT_STAND.get(),"work_light", "lit_unlit_textures","1","work_light_lamp_on", new Integer[]{1});
        genInteractableIntegerMultiBlock(ModBlocks.VITALS_MONITOR.get(),"vitals_monitor", "lit_unlit_textures","1","vitals_monitor_screen_on", new Integer[]{1});
        genModelledIntegerMultiBlockS(ModBlocks.IV_DRIPSTAND.get(), "iv_dripstand");
        genModelledIntegerMultiBlockS(ModBlocks.OPERATING_TABLE.get(), "operating_table");
        genModelledIntegerMultiBlockS(ModBlocks.MEDICAL_BED.get(), "medical_bed");

        genFacingLightSI(ModBlocks.RETRO_COMPUTER.get(), "retro_computer", "retro_computer_screen");
        genFacingLightSI(ModBlocks.RETRO_COMPUTER_2.get(), "retro_computer", "retro_computer_2_screen");
        genFacingLightSI(ModBlocks.MONITOR_AND_KEYBOARD.get(), "modern_computer", "monitor_screen");
        genFacingModelledSI(ModBlocks.DESKTOP_TOWER.get(), "modern_computer");
        genFacingPoweredSI(ModBlocks.CASSETTE_PLAYER.get(), "cassette_player");
        genModelledIntegerMultiBlockS(ModBlocks.LARGE_LOCKER.get(), "locker");
        genHorizontalSBI(ModBlocks.LOCKER_BOX.get(), build6FaceTexturesBlockModel("locker_box","locker","locker_box_front","locker_box_side","locker_box_side","locker_box_side","locker_box_top","locker_box_bottom"));

        genFacingModelledSI(ModBlocks.URINAL.get(), "urinal");
        genSurfaceLightSI(ModBlocks.OBLONG_CAGE_LAMP.get(), "lighting");
//        genSurfaceLightSI(ModBlocks.INDUSTRIAL_LAMP.get(), "lighting");
    }

    //String Helper Method for optional folders. Adds extra backslash if a folder is defined.
    private String optionalFolder(String optionalFolderName) {
        return (optionalFolderName+(optionalFolderName.isEmpty() ? "":"/"));
    }

    //---------- CUSTOM BLOCK MODEL GENERATORS ----------

    private ModelFile build3FaceTexturesBlockModel(String blockName, String folderName, String frontAndBack, String leftAndRight, String topAndBottom) {
        //Builds a textured model that uses three texture .pngs for all 6 faces.
        return build6FaceTexturesBlockModel(blockName, folderName, frontAndBack, frontAndBack, leftAndRight,leftAndRight,topAndBottom,topAndBottom);
    }

    private ModelFile build6FaceTexturesBlockModel(String blockName, String folderName, String front, String back, String left, String right, String top, String bottom) {
        //Builds a textured model that uses six texture .pngs for all 6 faces.
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

    private ModelFile buildCubeColumn(String modelName, String textureSubFolder, String sideTexture, String endTexture) {
        String basePath = "block/"; //generated/resources/assets/models/blocks

        return models()
                .withExistingParent(basePath + modelName, mcLoc("block/cube_column"))
                .texture("side", IndustrialHellscape.MOD_ID + ":" + basePath + optionalFolder(textureSubFolder) + sideTexture)
                .texture("end", IndustrialHellscape.MOD_ID + ":" + basePath + optionalFolder(textureSubFolder) + endTexture);
    }
    private ModelFile buildFullBlockIntegerMultiBlock(String blockName, String textureSubFolder, String allSideTexture, int blockState) {
        //Builds a textured model that uses three texture .pngs for all 6 faces.
        return build6FaceTexturesBlockModel(blockName, textureSubFolder, allSideTexture, allSideTexture, allSideTexture,allSideTexture,allSideTexture,allSideTexture);
    }

    //---------- END OF CUSTOM BLOCK MODEL GENERATOR METHODS ----------

    //---------- SBI METHODS (STATE, BLOCK MODEL, and/or ITEM MODEL) ----------
    private void genI(Block block, String modelFolderName) {
        //Only generate the item model for this block. Block states and block models are already written.
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String modelPath = "block/"+optionalFolder(modelFolderName)+stringName;

        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc(modelPath)));
    }

    private void genCustomI(Block block, String modelFolderName, String stringName) {
        //Only generate the item model for this block. Block states and block models are already written.
        String modelPath = "block/"+optionalFolder(modelFolderName)+stringName;

        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc(modelPath)));
    }

    private void genSimpleSBI(Block block, ModelFile model) {
        simpleBlock(block, model);

        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String existingModelPath = "block/"+stringName;
        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));
    }
    private void genHorizontalSBI(Block block, ModelFile model) {
        horizontalBlock(block, model);

        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String existingModelPath = "block/"+stringName;
        //expects an existing or generated model path with same name is block. If custom mod model (non generated) is used, error thrown
        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));
    }

    private void genFolderedSBI(Block block, String textureSubFolder) { //STATES, BLOCK MODEL, ITEM MODEL
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();

        simpleBlockWithItem(block,
                models().withExistingParent(stringName, mcLoc("block/cube_all"))
                        .texture("all", modLoc("block/" + optionalFolder(textureSubFolder) + stringName)));
    }

    private void genSimpleTextureToggleSBI(Block block, String textureSubFolder, String renderType) {

        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();

        String baseModelPath = "block/"+stringName;
        String pathToTexture = "block/" + optionalFolder(textureSubFolder);
        String texturePath =  pathToTexture + stringName;

       //GENERATE BASE MODEL
        models().withExistingParent(stringName, mcLoc("block/cube_all"))
                .texture("all", modLoc(texturePath)).renderType(renderType);

        getVariantBuilder(block)
                .forAllStates(state -> {
                    ResourceLocation model = modLoc(baseModelPath);
                    return ConfiguredModel.builder()
                            .modelFile(models().getBuilder(model.toString()))
                            .build();
                });

        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc(baseModelPath)));
    }

    private void genCustomTextureToggleSBI(Block block, String textureSubFolder, String nameStringToReplace, String nameStringReplacement, String renderType) {

        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();

        String baseModelPath = "block/"+stringName;
        String pathToTexture = "block/" + optionalFolder(textureSubFolder);
        String texturePath =  pathToTexture + stringName;


        String altStringName = stringName.replace(nameStringToReplace,nameStringReplacement);
        String altModelPath = "block/"+altStringName;
        String altTexturePath = pathToTexture + stringName.replace(nameStringToReplace,nameStringReplacement);

        //GENERATE BASE MODEL
        models().withExistingParent(stringName, mcLoc("block/cube_all"))
                .texture("all", modLoc(texturePath)).renderType(renderType);

        //GENERATE ALT MODEL (ALT TEXTURE STATE)
        models().withExistingParent(altStringName, mcLoc("block/cube_all"))
                .texture("all", modLoc(altTexturePath)).renderType(renderType); //Generate model of alt-texture block in generated models/block folder

        getVariantBuilder(block)
                .forAllStates(state -> {
                    boolean altTexture = state.getValue(SimpleTextureToggleBlock.ALT_STATE);

                    ResourceLocation model = modLoc(altTexture ? altModelPath : baseModelPath);

                    return ConfiguredModel.builder()
                            .modelFile(models().getBuilder(model.toString()))
                            .build();
                });

        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc(baseModelPath)));
    }

    private void genSimpleSlabsSBI(Block block, Block baseBlock, String textureSubFolder) {
        //For Rockrete slabs which have a homogenous texture and solid full-block model already data-generated
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String existingModelPath = "block/"+stringName;
        String baseStringName = BuiltInRegistries.BLOCK.getKey(baseBlock).getPath();
        String blockPath = "block/" + optionalFolder(textureSubFolder);
        ResourceLocation slabTexturePath = ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID,blockPath+baseStringName);
        ResourceLocation generatedBaseBlock = ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID,"block/" +baseStringName);

        slabBlock(((SlabBlock) block), generatedBaseBlock, slabTexturePath);

        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));

    }

    private void genSimpleWallsSB(Block block, Block baseBlock, String textureSubFolder) {
        //For Rockrete slabs which have a homogenous texture and solid model
        String baseStringName = BuiltInRegistries.BLOCK.getKey(baseBlock).getPath();
        String blockPath = "block/" + optionalFolder(textureSubFolder);
        ResourceLocation slabTexturePath = ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID,blockPath+baseStringName);

        wallBlock(((WallBlock) block), slabTexturePath);
    }

    //vesselplate slabs - rivets at edges halfway, get cut off. Fix someday by modifying this method
    private void genSlabsWithCustomDoubleSBI(Block block, Block parentBlock, String folderName, String doubleSlabName, String sideTexture, String endTexture, String nameStringToReplace, String nameStringReplacement ) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String parentStringName = BuiltInRegistries.BLOCK.getKey(parentBlock).getPath();
        String blockPath = "block/" + folderName+(folderName.isEmpty() ? "":"/");
        String generatedBlockPath = "block/";

        //build custom double-slab model
        buildCubeColumn(doubleSlabName, folderName, sideTexture, endTexture);

        ResourceLocation doubleSlabLoc = ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID,generatedBlockPath + doubleSlabName );
        ResourceLocation textureLoc = ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID,blockPath + parentStringName.replace(nameStringToReplace,nameStringReplacement) );

        slabBlock(((SlabBlock) block), doubleSlabLoc, textureLoc);

        String existingModelPath = "block/"+stringName;
        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));
    }
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

    private void genTextureToggleAntiCullSBI(Block block, String textureSubFolder, String side, String top, String renderType) {
        //For truss blocks. A side and top texture are required to accomodate Catwalk truss

        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String existingModelPath = "block/"+stringName;

        models()
                .withExistingParent(stringName, modLoc("block/anticull_template"))
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

    private void genAntiCullSlabBlockSBI(Block block, String textureSubFolder, String sideTextureName, String topTextureName, String bottomTextureName, String renderType) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();

        ResourceLocation side = modLoc("block/" + textureSubFolder + "/" + sideTextureName);
        ResourceLocation top = modLoc("block/" + textureSubFolder + "/" + topTextureName);
        ResourceLocation bottom =  modLoc("block/" + textureSubFolder + "/" + bottomTextureName);

        ModelFile slab = models()
                .withExistingParent(stringName, modLoc("block/anticull_slab"))
                .texture("side", side) //side
                .texture("top", top) //top
                .texture("bottom", bottom) //bottom
                .renderType(renderType);

        //models().slab(stringName, side, bottom, top).texture("particle", side).renderType(renderType);
        ModelFile slabTop = models()
                .withExistingParent(stringName+"_top", modLoc("block/anticull_slab_top"))
                .texture("side", side) //side
                .texture("top", top) //top
                .texture("bottom", bottom) //bottom
                .renderType(renderType);

        //models().slabTop(stringName + "_top", side, bottom, top).texture("particle", side).renderType(renderType);

        ModelFile slabDouble = models().getExistingFile(modLoc("block/"+stringName.replace("_slab","")));

        getVariantBuilder(block).forAllStates(state -> {
            SlabType type = state.getValue(SlabBlock.TYPE);
            return ConfiguredModel.builder()
                    .modelFile(
                            switch (type) {
                                case TOP -> slabTop;
                                case BOTTOM -> slab;
                                default -> slabDouble;
                            }
                    ).build();
        });

        itemModels().getBuilder(stringName)
                .parent(slab)
                .renderType(renderType);
    }
    private void genTrapdoorSBI(Block block, String renderType) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String existingModelPath = "block/"+stringName;
        trapdoorBlockWithRenderType(((TrapDoorBlock) block), modLoc("block/door/" + stringName), true, renderType);

        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath+"_bottom")));
    }

    private void genDoorSBI(Block block, String renderType) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        doorBlockWithRenderType(((DoorBlock) block), modLoc("block/door/"+stringName+"_bottom"), modLoc("block/door/"+stringName+"_top"), renderType);
    }

    private void genCornerShapedBlocksSI(Block block, String modelSubFolder) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String existingModelPath = "block/"+optionalFolder(modelSubFolder)+stringName;

        getVariantBuilder(block).forAllStatesExcept(state -> {
            Direction facing = state.getValue(FaceAttachedHorizontalDirectionalBlock.FACING);
            AttachFace face = state.getValue(FaceAttachedHorizontalDirectionalBlock.FACE);
            simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));

            int yRot = switch (facing) {
                case SOUTH -> 270;
                case WEST  -> 0;
                case EAST  -> 180;
                default -> 90; //NORTH
            };

            return ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc("block/"+optionalFolder(modelSubFolder)+stringName)))
                    .rotationX(face == AttachFace.FLOOR ? 0 : (face == AttachFace.WALL ? 90 : 180) )
                    //If the current blockstate is "FLOOR", don't rotate in X direction.
                    //If not,
                    //If the current blockstate is "WALL", rotate in the X direction 90 degrees so the model is on its side.
                    //If not,
                    //Then rotate in the X direction 180 degrees so model is upside down for the last block state, "ceiling"

                    .rotationY(yRot)
                    .build();

        }, BlockStateProperties.WATERLOGGED);
    }

    private void genSurfaceLightSI(Block block, String modelSubFolder) {
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

    private void genFacingModelledSI(Block block, String folderName) {
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

    private void genFullBlockIntegerMultiBlockSI(Block block, String textureSubFolder, int maxStates) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();

        //make all models first
        for(int i=0; i<maxStates;i++) {
            buildFullBlockIntegerMultiBlock(stringName+"_"+i,textureSubFolder, stringName+"_"+i, i);
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

                    int intBlockState = state.getValue(IntegerMultiBlock.PART);

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc("block/"+stringName + "_"+intBlockState)))
                            .rotationY(yRot)
                            .build();
                }, BlockStateProperties.WATERLOGGED);

        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc("block/"+stringName + "_"+0)));
    }

    private void genModelledIntegerMultiBlockS(Block block, String modelSubFolder) {
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

                    int intBlockState = state.getValue(IntegerMultiBlock.PART);

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc("block/"+ optionalFolder(modelSubFolder) +stringName  + "_"+intBlockState)))
                            .rotationY(yRot)
                            .build();
                }, BlockStateProperties.WATERLOGGED);
    }

    private void genInteractableIntegerMultiBlock(Block block, String modelSubFolder, String litTextureSubFolder, String lightableTextureKey, String litTextureName, Integer[] arrayOfStatesWithModelChange) {
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
                    Integer intBlockState = state.getValue(IntegerMultiBlock.PART);
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

    private void genFacingLightSI(Block block, String modelSubFolder, String litUnlitTextureName) {
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

    private void genFacingPoweredSI(Block block, String modelSubFolder) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String unpoweredModelPath = "block/"+optionalFolder(modelSubFolder)+stringName;
        String poweredModelPath = unpoweredModelPath+"_on";

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

    private void genAttachedSI(Block block, String modelSubFolder) {
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

    private void genAttachedSBI(Block block, String modelSubFolder, ModelFile model) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String existingModelPath = "block/"+optionalFolder(modelSubFolder)+stringName;

        getVariantBuilder(block).forAllStatesExcept(state -> {
            Direction facing = state.getValue(FaceAttachedHorizontalDirectionalBlock.FACING);
            AttachFace face = state.getValue(FaceAttachedHorizontalDirectionalBlock.FACE);
            simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationX(face == AttachFace.FLOOR ? 0 : (face == AttachFace.WALL ? 90 : 180))
                    .rotationY((int) (face == AttachFace.CEILING ? facing : facing.getOpposite()).toYRot())
                    .build();

        }, BlockStateProperties.WATERLOGGED);
    }

    private void genWAllVsCeilingSI(Block block, String modelSubFolder, String ceilingModelKey, String wallModelKey) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String blockName = "block/"+optionalFolder(modelSubFolder)+stringName;
        String ceilingOrFloorModel = blockName+"_"+ceilingModelKey;
        String wallModel = blockName+"_"+wallModelKey;

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction horizontalFacing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                    AttachFace surfaceAttached = state.getValue(SurfaceMountBlock.ATTACH_FACE);
                    String modelToUse = (surfaceAttached == AttachFace.CEILING || surfaceAttached == AttachFace.FLOOR)? ceilingOrFloorModel : wallModel;

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
        simpleBlockItem(block, models().getExistingFile(modLoc(wallModel)));

    }
}
