package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.modded_block_state_properties.TwoBlockMultiBlockState;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

//THIS JAVA CLASS HANDLES BLOCK STATE, BLOCK MODEL, AND ITEM MODEL DATA-GENERATION

public class ModBlockStateAndModelProvider extends BlockStateProvider {
    public static final EnumProperty<TwoBlockMultiBlockState> HALF = EnumProperty.create("half", TwoBlockMultiBlockState.class);
    public static final DirectionProperty FACING = BlockStateProperties.VERTICAL_DIRECTION;
    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty ALT_STATE = BooleanProperty.create("alt_state");
    public static final EnumProperty<AttachFace> SURFACE_MOUNT = BlockStateProperties.ATTACH_FACE;

    public ModBlockStateAndModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, IndustrialHellscape.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //Method naming convention ("SBI", "SI", etc.):
        //"S" - For State. Generates block State
        //"B" - For Block. Generates block Model (if no existing one is available).
        //"I" - For Item. Generates Item Model

        //---------- BLOCK ASSET GENERATION LIST ----------

        //Debug Blocks
        genHorizontalSBI(ModBlocks.PROTOTYPE_MACHINE.get(), build3FaceTexturesBlockModel("prototype_machine","experimental", "prototype_machine_front", "prototype_machine_side", "prototype_machine_top"));

        //Vesselplate Blocks
        genFolderedToggleBlockSBI(ModBlocks.RIVETED_VESSELPLATE.get(),"vesselplate","", true, true, "", "","solid");
        genFolderedToggleBlockSBI(ModBlocks.HORIZONTAL_VESSELPLATE.get(),"vesselplate","",true, true,"","","solid");
        genFolderedToggleBlockSBI(ModBlocks.VERTICAL_VESSELPLATE.get(),"vesselplate","",true, true,"","", "solid");

        genFolderedToggleBlockSBI(ModBlocks.SMOOTH_VESSELPLATE.get(),"vesselplate", "", true, true, "","","solid");
        genFolderedToggleBlockSBI(ModBlocks.HORIZONTAL_REINFORCED_VESSELPLATE.get(),"vesselplate","", true, true, "", "","solid");
        genFolderedToggleBlockSBI(ModBlocks.VERTICAL_REINFORCED_VESSELPLATE.get(),"vesselplate","", true, true, "", "","solid");

        genStairsWithRenderTypeSBI(ModBlocks.RIVETED_VESSELPLATE_STAIRS.get(),"vesselplate","riveted_vesselplate","riveted_vesselplate","riveted_vesselplate","solid");
        genStairsWithRenderTypeSBI(ModBlocks.SMOOTH_VESSELPLATE_STAIRS.get(),"vesselplate","smooth_vesselplate","smooth_vesselplate","smooth_vesselplate","solid");

        genSlabsWithCustomDoubleSBI(ModBlocks.RIVETED_VESSELPLATE_SLAB.get(), ModBlocks.RIVETED_VESSELPLATE.get(),"vesselplate","riveted_vesselplate_double_slab", "riveted_vesselplate","riveted_vesselplate","","");
        genSlabsWithCustomDoubleSBI(ModBlocks.SMOOTH_VESSELPLATE_SLAB.get(), ModBlocks.SMOOTH_VESSELPLATE.get(),"vesselplate","smooth_vesselplate_double_slab","smooth_vesselplate", "smooth_vesselplate","","");

        genFolderedToggleBlockSBI(ModBlocks.GRAY_RIVETED_VESSELPLATE.get(),"vesselplate","",true, true,"","","solid");
        genFolderedToggleBlockSBI(ModBlocks.GRAY_VERTICAL_REINFORCED_VESSELPLATE.get(),"vesselplate","",true, true,"","", "solid");
        genFolderedToggleBlockSBI(ModBlocks.GRAY_HORIZONTAL_REINFORCED_VESSELPLATE.get(),"vesselplate","",true, true,"","", "solid");
        genFolderedToggleBlockSBI(ModBlocks.SMOOTH_GRAY_VESSELPLATE.get(),"vesselplate", "", true, true, "","","solid");
        genFolderedToggleBlockSBI(ModBlocks.GRAY_HORIZONTAL_VESSELPLATE.get(),"vesselplate","", true, true, "", "","solid");
        genFolderedToggleBlockSBI(ModBlocks.GRAY_VERTICAL_VESSELPLATE.get(),"vesselplate","", true, true, "", "","solid");

        genStairsWithRenderTypeSBI(ModBlocks.GRAY_RIVETED_VESSELPLATE_STAIRS.get(),"vesselplate","gray_riveted_vesselplate","gray_riveted_vesselplate","gray_riveted_vesselplate","solid");
        //genStairsWithRenderTypeSBI(ModBlocks.GRAY_VESSELPLATE_PANEL_STAIRS.get(),"vesselplate","gray_vesselplate_panel","gray_vesselplate_panel","gray_vesselplate_panel","solid");
        genStairsWithRenderTypeSBI(ModBlocks.SMOOTH_GRAY_VESSELPLATE_STAIRS.get(),"vesselplate","smooth_gray_vesselplate","smooth_gray_vesselplate","smooth_gray_vesselplate","solid");

        genSlabsWithCustomDoubleSBI(ModBlocks.GRAY_RIVETED_VESSELPLATE_SLAB.get(), ModBlocks.GRAY_RIVETED_VESSELPLATE.get(),"vesselplate","gray_riveted_vesselplate_double_slab","gray_riveted_vesselplate","gray_riveted_vesselplate","","");
        genSlabsWithCustomDoubleSBI(ModBlocks.SMOOTH_GRAY_VESSELPLATE_SLAB.get(), ModBlocks.SMOOTH_GRAY_VESSELPLATE.get(),"vesselplate","smooth_gray_vesselplate_double_slab","smooth_gray_vesselplate", "smooth_gray_vesselplate","","");
        //genSlabsWithCustomDoubleSBI(ModBlocks.GRAY_VESSELPLATE_PANEL_SLAB.get(), ModBlocks.GRAY_HORIZONTAL_VESSELPLATE.get(),"vesselplate","gray_vesselplate_panel_double_slab","gray_vesselplate_panel","gray_vesselplate_panel","","");

        genFolderedDirToggleBlockSBI(ModBlocks.RUSTY_DIRECTIONAL_RIVETED_VESSELPLATE.get(),"vesselplate","",true, true,"rusty_horizontal_riveted_vesselplate","rusty_vertical_riveted_vesselplate");

        genFolderedToggleBlockSBI(ModBlocks.RUSTY_RIVETED_VESSELPLATE_PANEL.get(),"vesselplate","", true, true, "_panel", "","solid");
        genStairsWithRenderTypeSBI(ModBlocks.RUSTY_RIVETED_VESSELPLATE_STAIRS.get(),"vesselplate","rusty_riveted_vesselplate_slab","rusty_riveted_vesselplate","rusty_riveted_vesselplate","solid");
        genSlabsWithCustomDoubleSBI(ModBlocks.RUSTY_RIVETED_VESSELPLATE_SLAB.get(), ModBlocks.RUSTY_RIVETED_VESSELPLATE_PANEL.get(),"vesselplate","rusty_riveted_vesselplate_double_slab","rusty_riveted_vesselplate_slab","rusty_riveted_vesselplate_panel","_panel","");
        genFolderedToggleBlockSBI(ModBlocks.RUSTY_VESSELPLATE_SHEETING.get(),"vesselplate","", true, true, "ing", "","solid");
        genStairsWithRenderTypeSBI(ModBlocks.RUSTY_VESSELPLATE_SHEETING_STAIRS.get(),"vesselplate","rusty_vesselplate_sheet","rusty_vesselplate_sheet","rusty_vesselplate_sheet","solid");
        genSlabsWithCustomDoubleSBI(ModBlocks.RUSTY_VESSELPLATE_SHEETING_SLAB.get(), ModBlocks.RUSTY_VESSELPLATE_SHEETING.get(),"vesselplate","rusty_vesselplate_sheeting_double_slab","rusty_vesselplate_sheeting_slab","rusty_vesselplate_sheeting","ing","");
        genFolderedToggleBlockSBI(ModBlocks.SMOOTH_RUSTY_VESSELPLATE_TILE.get(),"vesselplate", "", true, true, "_tile","","solid");
        genStairsWithRenderTypeSBI(ModBlocks.SMOOTH_RUSTY_VESSELPLATE_STAIRS.get(),"vesselplate","smooth_rusty_vesselplate","smooth_rusty_vesselplate","smooth_rusty_vesselplate","solid");
        genSlabsWithCustomDoubleSBI(ModBlocks.SMOOTH_RUSTY_VESSELPLATE_SLAB.get(), ModBlocks.SMOOTH_RUSTY_VESSELPLATE_TILE.get(),"vesselplate","smooth_rusty_vesselplate_double_slab","smooth_rusty_vesselplate_slab", "smooth_rusty_vesselplate_tile","_tile","");

        //Grate Blocks
        genFolderedToggleBlock2SBI(ModBlocks.HORIZONTAL_GRATE.get(),"grate","solid");
        genFolderedToggleBlock2SBI(ModBlocks.VERTICAL_GRATE.get(),"grate","solid");
        genFolderedToggleBlock2SBI(ModBlocks.HORIZONTAL_CUTOUT_GRATE.get(),"grate","cutout");
        genFolderedToggleBlock2SBI(ModBlocks.VERTICAL_CUTOUT_GRATE.get(),"grate","cutout");


        genFolderedToggleBlock2SBI(ModBlocks.GRAY_HORIZONTAL_GRATE.get(),"grate","solid");
        genFolderedToggleBlock2SBI(ModBlocks.GRAY_VERTICAL_GRATE.get(),"grate","solid");
        genFolderedToggleBlock2SBI(ModBlocks.GRAY_HORIZONTAL_CUTOUT_GRATE.get(),"grate","cutout");
        genFolderedToggleBlock2SBI(ModBlocks.GRAY_VERTICAL_CUTOUT_GRATE.get(),"grate","cutout");

        genFolderedToggleBlockSBI(ModBlocks.RUSTY_GRATE.get(),"grate","",true, true,"grate","vertical_grate","solid");
        genFolderedToggleBlockSBI(ModBlocks.RUSTY_SEETHROUGH_GRATE.get(),"grate","grate",true,true, "see-through","vertical_see-through","cutout");

        //Duct Blocks
        genFolderedSBI(ModBlocks.DUCT.get(), "duct");
        genFolderedSBI(ModBlocks.RUSTY_DUCT.get(), "duct");
        genAttachedSI(ModBlocks.DUCT_VENT.get(), "duct_vent");
        genAttachedSI(ModBlocks.RUSTY_DUCT_VENT.get(), "duct_vent");

        //Glass Blocks
        genFolderedToggleBlockSBI(ModBlocks.VESSELGLASS.get(),"vesselglass","",true, true,"","","translucent");
        genFolderedToggleBlockSBI(ModBlocks.GRAY_VESSELGLASS.get(),"vesselglass","",true, true,"","","translucent");
        genFolderedToggleBlockSBI(ModBlocks.REINFORCED_VESSELGLASS.get(),"vesselglass","",true, true,"","","translucent");
        genFolderedToggleBlockSBI(ModBlocks.GRAY_REINFORCED_VESSELGLASS.get(),"vesselglass","",true, true,"","","translucent");

        //Misc. Stone Blocks
        genFolderedSBI(ModBlocks.GRIMY_RESTROOM_TILE.get(),"");
        genFolderedSBI(ModBlocks.HAZARD_STRIPE_YELLOW.get(),"");
        genFolderedSBI(ModBlocks.HAZARD_STRIPE_RED.get(),"");

        //Rockrete Blocks
        genFolderedToggleBlockSBI(ModBlocks.GRAY_ROCKRETE.get(),"","",true, true,"rockrete","rockrete_rebar","solid");
        genFolderedToggleBlockSBI(ModBlocks.ROUGH_GRAY_ROCKRETE.get(),"","",true, true,"","","solid");
        genStairsWithRenderTypeSBI(ModBlocks.GRAY_ROCKRETE_STAIRS.get(),"","gray_rockrete","gray_rockrete","gray_rockrete","solid");
        genSimpleSlabsSBI(ModBlocks.GRAY_ROCKRETE_SLAB.get(), ModBlocks.GRAY_ROCKRETE.get());

        genFolderedToggleBlockSBI(ModBlocks.GREEN_ROCKRETE.get(),"","",true, true,"rockrete","rockrete_rebar","solid");
        genFolderedToggleBlockSBI(ModBlocks.ROUGH_GREEN_ROCKRETE.get(),"","",true, true,"","","solid");
        genStairsWithRenderTypeSBI(ModBlocks.GREEN_ROCKRETE_STAIRS.get(),"","green_rockrete","green_rockrete","green_rockrete","solid");
        genSimpleSlabsSBI(ModBlocks.GREEN_ROCKRETE_SLAB.get(), ModBlocks.GREEN_ROCKRETE.get());

        genFolderedToggleBlockSBI(ModBlocks.YELLOW_ROCKRETE.get(),"","",true, true,"rockrete","rockrete_rebar","solid");
        genFolderedToggleBlockSBI(ModBlocks.ROUGH_YELLOW_ROCKRETE.get(),"","",true, true,"","","solid");
        genStairsWithRenderTypeSBI(ModBlocks.YELLOW_ROCKRETE_STAIRS.get(),"","yellow_rockrete","yellow_rockrete","yellow_rockrete","solid");
        genSimpleSlabsSBI(ModBlocks.YELLOW_ROCKRETE_SLAB.get(), ModBlocks.YELLOW_ROCKRETE.get());

        genFolderedToggleBlockSBI(ModBlocks.BLUE_ROCKRETE.get(),"","",true, true,"rockrete","rockrete_rebar","solid");
        genFolderedToggleBlockSBI(ModBlocks.ROUGH_BLUE_ROCKRETE.get(),"","",true, true,"","","solid");
        genStairsWithRenderTypeSBI(ModBlocks.BLUE_ROCKRETE_STAIRS.get(),"","blue_rockrete","blue_rockrete","blue_rockrete","solid");
        genSimpleSlabsSBI(ModBlocks.BLUE_ROCKRETE_SLAB.get(), ModBlocks.BLUE_ROCKRETE.get());

        genFolderedToggleBlockSBI(ModBlocks.RED_ROCKRETE.get(),"","",true, true,"rockrete","rockrete_rebar","solid");
        genFolderedToggleBlockSBI(ModBlocks.ROUGH_RED_ROCKRETE.get(),"","",true, true,"","","solid");
        genStairsWithRenderTypeSBI(ModBlocks.RED_ROCKRETE_STAIRS.get(),"","red_rockrete","red_rockrete","red_rockrete","solid");
        genSimpleSlabsSBI(ModBlocks.RED_ROCKRETE_SLAB.get(), ModBlocks.RED_ROCKRETE.get());

        //Other Solid Opaque Full-Blocks with unique face textures
        genHorizontalSBI(ModBlocks.IHEA_FURNITURE_KIT.get(), build6FaceTexturesBlockModel("ihea_furniture_kit", "furniture_category_block", "ihea_furniture_kit_front", "ihea_furniture_kit_back", "ihea_furniture_kit_right", "ihea_furniture_kit_left", "ihea_furniture_kit_top", "ihea_furniture_kit_bottom"));
        genHorizontalSBI(ModBlocks.SAFETY_FURNISHINGS.get(), build3FaceTexturesBlockModel("safety_furnishings", "furniture_category_block", "safety_furnishings_north", "safety_furnishings_west", "safety_furnishings_up"));
        genHorizontalSBI(ModBlocks.HYGIENE_FURNISHINGS.get(), build3FaceTexturesBlockModel("hygiene_furnishings","furniture_category_block", "hygiene_furnishings_north", "hygiene_furnishings_west", "hygiene_furnishings_up"));
        genHorizontalSBI(ModBlocks.INDUSTRIAL_FURNISHINGS.get(), build3FaceTexturesBlockModel("industrial_furnishings","furniture_category_block", "industrial_furnishings_north", "industrial_furnishings_west", "industrial_furnishings_up"));
        genHorizontalSBI(ModBlocks.TECHNOLOGY_FURNISHINGS.get(), build3FaceTexturesBlockModel("technology_furnishings","furniture_category_block", "technology_furnishings_north", "technology_furnishings_west", "technology_furnishings_up"));
        genHorizontalSBI(ModBlocks.AMENITY_FURNISHINGS.get(), build3FaceTexturesBlockModel("amenity_furnishings","furniture_category_block", "amenity_furnishings_north", "amenity_furnishings_west", "amenity_furnishings_up"));

        genHorizontalSBI(ModBlocks.PIPEWORKS.get(), build3FaceTexturesBlockModel("pipeworks", "pipeworks", "pipeworks_front", "pipeworks_sides", "pipeworks_top"));
        genSimpleSBI(ModBlocks.METALWORKS.get(), build6FaceTexturesBlockModel("metalworks","metalworks","metalworks_side","metalworks_side", "metalworks_side","metalworks_side","metalworks_top","metalworks_bottom"));

        genHorizontalSBI(ModBlocks.LOCKER_BOX.get(), build6FaceTexturesBlockModel("locker_box","locker","locker_box_front","locker_box_side","locker_box_side","locker_box_side","locker_box_top","locker_box_bottom"));

        //FURNITURE BLOCKS WITH EXISTING BLOCK MODELS
        GenFacingModelledSI(ModBlocks.SINK.get(),"");
        GenFacingModelledSI(ModBlocks.OFFICE_CHAIR.get(),"office_chair");
        GenFacingModelledSI(ModBlocks.BLACK_OFFICE_CHAIR.get(), "office_chair");
        GenFacingModelledSI(ModBlocks.FOLDING_CHAIR.get(), "");
        GenFacingModelledSI(ModBlocks.WHITE_WALL_MEDKIT.get(),"medkit_containers");
        GenFacingModelledSI(ModBlocks.RED_WALL_MEDKIT.get(),"medkit_containers");
        GenFacingModelledSI(ModBlocks.FIRE_EXTINGUISHER.get(), "");
        genAttachedSI(ModBlocks.SMOKE_ALARM.get(), "");
        genAttachedSI(ModBlocks.POSTER_1.get(), "decals");
        genAttachedSBI(ModBlocks.FUEL_DRUM.get(), "", build6FaceTexturesBlockModel("fuel_drum","fuel_drum", "red_labeled_fuel_drum_front", "red_labeled_fuel_drum_front", "red_labeled_fuel_drum_side", "red_labeled_fuel_drum_side", "red_fuel_drum_up","red_fuel_drum_down"));
        genWAllVsCeilingSI(ModBlocks.CCTV_CAMERA.get(),"cctv_camera","ceiling","wall");
        GenFacingModelledSI(ModBlocks.WALL_SPEAKER.get(), "");
        GenFacingPoweredSI(ModBlocks.FLOOR_WORK_LIGHT.get(), "work_light");
        GenFacingPoweredSI(ModBlocks.RETRO_COMPUTER.get(), "retro_computer");
        GenFacingPoweredSI(ModBlocks.RETRO_COMPUTER_2.get(), "retro_computer");
        GenFacingPoweredSI(ModBlocks.MONITOR_AND_KEYBOARD.get(), "modern_computer");
        GenFacingModelledSI(ModBlocks.DESKTOP_TOWER.get(), "modern_computer");
        GenFacingPoweredSI(ModBlocks.CASSETTE_PLAYER.get(), "cassette_player");
        TwoBlockMultiBlock(ModBlocks.LARGE_LOCKER.get(), "locker");
        TwoBlockMultiBlock(ModBlocks.URINAL.get(), "urinal");

        //Metalworks Blocks
        genI(ModBlocks.YELLOW_RAILING.get(),"railing");
        genCustomI(ModBlocks.YELLOW_STAIR_RAILING.get(),"railing","yellow_stair_rail_left");

        genI(ModBlocks.GRAY_RAILING.get(),"railing");
        genCustomI(ModBlocks.GRAY_STAIR_RAILING.get(),"railing","gray_stair_rail_left");

        genI(ModBlocks.BLACK_RAILING.get(),"railing");
        genCustomI(ModBlocks.BLACK_STAIR_RAILING.get(),"railing","black_stair_rail_left");

        genI(ModBlocks.RUSTY_RAILING.get(),"railing");
        genCustomI(ModBlocks.RUSTY_STAIR_RAILING.get(),"railing","black_stair_rail_left");

        genI(ModBlocks.RUSTY_BOLTED_BRACKET.get(),"bolted_bracket");
        genI(ModBlocks.BLACK_BOLTED_BRACKET.get(),"bolted_bracket");
        genI(ModBlocks.GRAY_BOLTED_BRACKET.get(),"bolted_bracket");
        genCornerShapedBlocksSI(ModBlocks.SMALL_GRAY_BOLTED_BRACKET.get(),"bolted_bracket");
        genCornerShapedBlocksSI(ModBlocks.SMALL_BLACK_BOLTED_BRACKET.get(),"bolted_bracket");
        genCornerShapedBlocksSI(ModBlocks.SMALL_RUSTY_BOLTED_BRACKET.get(),"bolted_bracket");
        
        //truss Blocks
        genAntiCullBLock(ModBlocks.TRUSS.get(),"truss", "truss","truss", "cutout");
        genStairsWithRenderTypeSBI(ModBlocks.TRUSS_STAIRS.get(),"truss","reinforced_truss","truss","truss","cutout");
        genSlabBlockSBI(ModBlocks.TRUSS_SLAB.get(), "truss", "truss", "truss","truss","cutout");

        genAntiCullBLock(ModBlocks.CATWALK_TRUSS.get(),"truss", "truss","floorgrate_catwalk", "cutout");
        genStairsWithRenderTypeSBI(ModBlocks.CATWALK_TRUSS_STAIRS.get(),"truss","reinforced_truss","truss","floorgrate_catwalk","cutout");
        genSlabBlockSBI(ModBlocks.CATWALK_TRUSS_SLAB.get(), "truss", "truss", "floorgrate_catwalk","truss","cutout");

        genAntiCullBLock(ModBlocks.GRAY_TRUSS.get(),"truss", "gray_truss","gray_truss", "cutout");
        genStairsWithRenderTypeSBI(ModBlocks.GRAY_TRUSS_STAIRS.get(),"truss","gray_reinforced_truss","gray_truss","gray_truss","cutout");
        genSlabBlockSBI(ModBlocks.GRAY_TRUSS_SLAB.get(), "truss", "gray_truss", "gray_truss","gray_truss","cutout");

        genAntiCullBLock(ModBlocks.GRAY_CATWALK_TRUSS.get(),"truss", "gray_truss","gray_floorgrate_catwalk", "cutout");
        genStairsWithRenderTypeSBI(ModBlocks.GRAY_CATWALK_TRUSS_STAIRS.get(),"truss","gray_reinforced_truss","gray_truss","gray_floorgrate_catwalk","cutout");
        genSlabBlockSBI(ModBlocks.GRAY_CATWALK_TRUSS_SLAB.get(), "truss", "gray_truss", "gray_floorgrate_catwalk","gray_truss", "cutout");

        genAntiCullBLock(ModBlocks.RUSTY_TRUSS.get(),"truss", "rusty_truss","rusty_truss", "cutout");
        genStairsWithRenderTypeSBI(ModBlocks.RUSTY_TRUSS_STAIRS.get(),"truss","rusty_reinforced_truss","rusty_truss","rusty_truss","cutout");
        genSlabBlockSBI(ModBlocks.RUSTY_TRUSS_SLAB.get(), "truss", "rusty_truss", "rusty_truss","rusty_truss","cutout");

        genAntiCullBLock(ModBlocks.RUSTY_CATWALK_TRUSS.get(),"truss", "rusty_truss","rusty_floorgrate_catwalk", "cutout");
        genStairsWithRenderTypeSBI(ModBlocks.RUSTY_CATWALK_TRUSS_STAIRS.get(),"truss","rusty_reinforced_truss","rusty_truss","rusty_floorgrate_catwalk","cutout");
        genSlabBlockSBI(ModBlocks.RUSTY_CATWALK_TRUSS_SLAB.get(), "truss", "rusty_truss", "rusty_floorgrate_catwalk","rusty_truss", "cutout");

        //Doors and Trapdoors
        genDoorSBI(ModBlocks.VESSELPLATE_DOOR.get() , "cutout");
        genTrapdoorSBI(ModBlocks.VESSELPLATE_TRAPDOOR.get(),"solid");
        genDoorSBI(ModBlocks.GRAY_VESSELPLATE_DOOR.get() , "solid");
        genTrapdoorSBI(ModBlocks.GRAY_VESSELPLATE_TRAPDOOR.get(),"solid");

        //Uncategorized
        genFolderedToggleBlockSBI(ModBlocks.ENCASED_CABLES.get(),"","",true, true, "encased","vertical_encased", "solid");
    }
    //---------- END OF BLOCK ASSET GENERATION LIST ----------

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

    private ModelFile buildAntiCullBlock(String blockName, String folderName, String side, String top, String renderType) {
        return models()
                .withExistingParent(blockName, modLoc("block/anticull_template"))
                .texture("side", modLoc("block/" + folderName + "/" + side)) //side
                .texture("top", modLoc("block/" + folderName + "/" + top)) //top
                .texture("bottom", modLoc("block/" + folderName + "/" + side)) //bottom
                .renderType(renderType);
    }

    private ModelFile buildSimpleBlockWithRenderType(String blockName, String folderName, String renderType) {
        //Builds a textured model that uses six texture .pngs for all 6 faces.

        return models().cubeAll(
                blockName,
                modLoc( "block/" + folderName+ "/" + blockName)
        ).texture("particle", modLoc("block/" + folderName +"/" + blockName)).renderType(renderType);
    }

    private ModelFile buildCubeColumn(String modelName, String folderName, String sideTexture, String endTexture) {
        String basePath = "block/"; //generated/resources/assets/models/blocks

        return models()
                .withExistingParent(basePath + modelName, mcLoc("block/cube_column"))
                .texture("side", IndustrialHellscape.MOD_ID + ":" + basePath + (folderName+(folderName.isEmpty() ? "":"/")) + sideTexture)
                .texture("end", IndustrialHellscape.MOD_ID + ":" + basePath + (folderName+(folderName.isEmpty() ? "":"/")) + endTexture);
    }

    //---------- END OF CUSTOM BLOCK MODEL GENERATOR METHODS ----------

    //---------- SBI ASSET GENERATOR METHODS ----------
    // STATE, BLOCK MODEL, AND/OR ITEM MODEL GENERATION

    private void genSimpleBlockWithRenderTypeSBI(Block block, String folderName, String renderType) {
        //For blocks like Vesselglass
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        simpleBlock(block, buildSimpleBlockWithRenderType(stringName, folderName, renderType));

        String existingModelPath = "block/"+stringName;
        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));
    }

    private void genAntiCullBLock(Block block, String folderName, String side, String top, String renderType) {
        //For truss blocks. A side and top texture are required to accomodate Catwalk trusss
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        simpleBlock(block, buildAntiCullBlock(stringName, folderName, side, top, renderType));

        String existingModelPath = "block/"+stringName;
        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));
    }


    private void genStairsWithRenderTypeSBI(Block block, String folderName, String sideTexture, String bottomTexture, String topTexture, String renderType) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String blockPath = "block/" + folderName+(folderName.isEmpty() ? "":"/");


        ResourceLocation bottomTextureLoc = new ResourceLocation(IndustrialHellscape.MOD_ID,blockPath+bottomTexture );
        ResourceLocation sideTextureLoc = new ResourceLocation(IndustrialHellscape.MOD_ID,blockPath+sideTexture );
        ResourceLocation topTextureLoc = new ResourceLocation(IndustrialHellscape.MOD_ID,blockPath+topTexture );

        stairsBlockWithRenderType((StairBlock) block, sideTextureLoc,bottomTextureLoc,topTextureLoc,renderType);

        String existingModelPath = "block/"+stringName;
        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));
    }

    private void genSimpleSlabsSBI(Block block, Block parentBlock) {
        //For Rockrete slabs which have a homogenous texture and solid model
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();

        slabBlock(((SlabBlock) block), blockTexture(parentBlock), blockTexture(parentBlock));

        String existingModelPath = "block/"+stringName;
        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));
    }
    private void genSlabsWithCustomDoubleSBI(Block block, Block parentBlock, String folderName, String doubleSlabName, String sideTexture, String endTexture, String nameStringToReplace, String nameStringReplacement ) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String parentStringName = BuiltInRegistries.BLOCK.getKey(parentBlock).getPath();
        String blockPath = "block/" + folderName+(folderName.isEmpty() ? "":"/");
        String generatedBlockPath = "block/";

        //build custom double-slab model
        buildCubeColumn(doubleSlabName, folderName, sideTexture, endTexture);

        ResourceLocation doubleSlabLoc = new ResourceLocation(IndustrialHellscape.MOD_ID,generatedBlockPath + doubleSlabName );
        ResourceLocation textureLoc = new ResourceLocation(IndustrialHellscape.MOD_ID,blockPath + parentStringName.replace(nameStringToReplace,nameStringReplacement) );

        slabBlock(((SlabBlock) block), doubleSlabLoc, textureLoc);

        String existingModelPath = "block/"+stringName;
        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));
    }

    private void genSlabBlockSBI(Block block, String folderName, String sideTextureName, String topTextureName, String bottomTextureName, String renderType) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();

        ResourceLocation side = modLoc("block/" + folderName + "/" + sideTextureName);
        ResourceLocation top = modLoc("block/" + folderName + "/" + topTextureName);
        ResourceLocation bottom =  modLoc("block/" + folderName + "/" + bottomTextureName);

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

    private void genFolderedSI(Block block, String folderName) { //STATES AND ITEM MODEL ONLY
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String existingModelPath = "block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName;
        getVariantBuilder(block)
                .partialState()
                .modelForState()
                .modelFile(models().getExistingFile(modLoc(existingModelPath)))
                .addModel();
        //Generate Item Model
        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));
    }

    private void genFolderedSBI(Block block, String subfolder) { //STATES, BLOCK MODEL, ITEM MODEL
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();

        simpleBlockWithItem(block,
                models().withExistingParent(stringName, mcLoc("block/cube_all"))
                        .texture("all", modLoc("block/" + subfolder+(subfolder.isEmpty() ? "":"/") + stringName)));
    }

    private void genAttachedSI(Block block, String folderName) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String existingModelPath = "block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName;

        getVariantBuilder(block).forAllStatesExcept(state -> {
            Direction facing = state.getValue(FaceAttachedHorizontalDirectionalBlock.FACING);
            AttachFace face = state.getValue(FaceAttachedHorizontalDirectionalBlock.FACE);
            simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));

            return ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc("block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName)))
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

    private void genCornerShapedBlocksSI(Block block, String folderName) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String existingModelPath = "block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName;

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
                    .modelFile(models().getExistingFile(modLoc("block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName)))
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

    private void genAttachedSBI(Block block, String folderName, ModelFile model) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String existingModelPath = "block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName;

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

    private void genHorizontalSBI(Block block, ModelFile model) {
        horizontalBlock(block, model);

        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String existingModelPath = "block/"+stringName;
        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));
    }

    private void genTrapdoorSBI(Block block, String renderType) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String existingModelPath = "block/"+stringName;
        trapdoorBlockWithRenderType(((TrapDoorBlock) block), modLoc("block/doors/" + stringName), true, renderType);

        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath+"_bottom")));
    }

    private void genDoorSBI(Block block, String renderType) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String existingModelPath = "block/"+stringName;
        doorBlockWithRenderType(((DoorBlock) block), modLoc("block/doors/"+stringName+"_bottom"), modLoc("block/doors/"+stringName+"_top"), renderType);
    }

    private void genSimpleSBI(Block block, ModelFile model) {
        simpleBlock(block, model);

        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String existingModelPath = "block/"+stringName;
        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));
    }

    private void genFolderedToggleBlockSBI(Block block, String textureSubFolder, String existingBaseModelSubFolder, Boolean makeBaseModel, Boolean makeAltModel, String nameStringToReplace, String nameStringReplacement, String renderType) {

        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();

        String baseModelPath = "block/"+stringName;
        String altStringName = stringName.replace(nameStringToReplace,nameStringReplacement);
        String altModelPath = "block/"+altStringName;


        String pathToTexture = "block/" + (textureSubFolder+(textureSubFolder.isEmpty() ? "":"/"));
        String texturePath =  pathToTexture + stringName;
        String altTexturePath = pathToTexture + stringName.replace(nameStringToReplace,nameStringReplacement);

        //GENERATE BASE MODEL (DEFAULT STATE), optional if not already present
        if(makeBaseModel) {
            models().withExistingParent(stringName, mcLoc("block/cube_all"))
                    .texture("all", modLoc(texturePath)).renderType(renderType);
        }

        //GENERATE ROTATED MODEL (ALT TEXTURE STATE)
        if(makeAltModel) {
            models().withExistingParent(altStringName, mcLoc("block/cube_all"))
                    .texture("all", modLoc(altTexturePath)).renderType(renderType); //Generate model of alt-texture block in generated models/block folder
        }

        //GENERATE BLOCKSTATES
        getVariantBuilder(block)
                .forAllStates(state -> {
                    boolean altTexture = state.getValue(ALT_STATE);
                    String modelToUse = (altTexture) ? altModelPath : baseModelPath;

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc(modelToUse)))
                            .build();
                });
        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc(baseModelPath)));
    }

    private void genFolderedToggleBlock2SBI(Block block, String textureSubFolder, String renderType) {

        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();

        String baseModelPath = "block/"+stringName;


        String pathToTexture = "block/" + (textureSubFolder+(textureSubFolder.isEmpty() ? "":"/"));
        String texturePathWithFolder =  pathToTexture + stringName;

        //GENERATE BASE MODEL (DEFAULT STATE), optional if not already present
        models().withExistingParent(stringName, mcLoc("block/cube_all"))
                    .texture("all", modLoc(texturePathWithFolder)).renderType(renderType);

        //GENERATE BLOCKSTATES
        getVariantBuilder(block)
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(modLoc(baseModelPath)))
                        .build());

        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc(baseModelPath)));
    }

    private void genFolderedDirToggleBlockSBI(Block block, String textureSubFolder, String existingBaseModelSubFolder, Boolean makeBaseModel, Boolean makeAltModel, String textureName, String altTextureName) {

        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String pathToName = "block/"+existingBaseModelSubFolder+(existingBaseModelSubFolder.isEmpty() ? "":"/");

        String baseModelPath = pathToName+stringName;
        String altModelPath = pathToName+stringName+"_alt_texture";

        String pathToTexture = "block/" + (textureSubFolder+(textureSubFolder.isEmpty() ? "":"/"));
        String texturePath =  pathToTexture + textureName;
        String altTexturePath = pathToTexture + altTextureName;

        //GENERATE BASE MODEL (DEFAULT STATE), optional if not already present
        if(makeBaseModel) {
            models().withExistingParent(stringName, mcLoc("block/cube_all"))
                    .texture("all", modLoc(texturePath));
        }

        //GENERATE ROTATED MODEL (ALT TEXTURE STATE)
        if(makeAltModel) {
            models().withExistingParent(altModelPath, mcLoc("block/cube_all"))
                    .texture("all", modLoc(altTexturePath)); //Generate model of alt-texture block in generated models/block folder
        }

        //GENERATE BLOCKSTATES
        getVariantBuilder(block)
                .forAllStates(state -> {
                    boolean altTexture = state.getValue(ALT_STATE);
                    String modelToUse = (altTexture) ? altModelPath : baseModelPath;

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc(modelToUse)))
                            .build();
                });
        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc(baseModelPath)));
    }

    private void GenFacingModelledSI(Block block, String folderName) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String modelPath = "block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName;

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction horizontalFacing = state.getValue(HORIZONTAL_FACING);

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

    private void GenFacingPoweredSI(Block block, String folderName) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String unpoweredModelPath = "block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName;
        String poweredModelPath = unpoweredModelPath+"_on";

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction horizontalFacing = state.getValue(HORIZONTAL_FACING);
                    Boolean powered = state.getValue(POWERED);
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

    private void genWaterloggableSI(Block block, String folderName) {
        //No placement rotation necessary for these blocks
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String modelPath = "block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName;

        getVariantBuilder(block)
                .partialState()
                .modelForState()
                .modelFile(models().getExistingFile(modLoc(modelPath)))
                .addModel();

        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc(modelPath)));
    }

    private void genWAllVsCeilingSI(Block block, String folderName, String ceilingModelKey, String wallModelKey) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String blockName = "block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName;
        String ceilingOrFloorModel = blockName+"_"+ceilingModelKey;
        String wallModel = blockName+"_"+wallModelKey;

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction horizontalFacing = state.getValue(HORIZONTAL_FACING);
                    AttachFace surfaceAttached = state.getValue(SURFACE_MOUNT);
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


    private void TwoBlockMultiBlock(Block block, String folderName) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String blockName = "block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName;
        String positiveBlockName = blockName+"_positive";
        String negativeBlockName = blockName+"_negative";

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction horizontalFacing = state.getValue(HORIZONTAL_FACING);
                    TwoBlockMultiBlockState half = state.getValue(HALF);
                    String modelToUse = (half == TwoBlockMultiBlockState.POSITIVE)? positiveBlockName : negativeBlockName;

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

    }

    private void genI(Block block, String folderName) {
        //Only generate the item model for this block. Block states and block models are already written.
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String modelPath = "block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName;
        
        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc(modelPath)));
    }
    private void genCustomI(Block block, String folderName, String stringName) {
        //Only generate the item model for this block. Block states and block models are already written.
        String modelPath = "block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName;

        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc(modelPath)));
    }

    //---------- END OF SBI ASSET GENERATOR METHODS ----------
}