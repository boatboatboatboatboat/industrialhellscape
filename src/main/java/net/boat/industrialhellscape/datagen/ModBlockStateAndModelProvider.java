package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.modded_block_state_properties.TwoBlockMultiBlockState;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
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

    public ModBlockStateAndModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, IndustrialHellscape.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //Method naming convention ("SBI", "SI", etc.):
        //"S" - For State. Generates block State
        //"B" - For Block. Generates block Model (if no existing one is available). Simple full-blocks with no special rendering are being generated currently
        //"I" - For Item. Generates Item Model

        //---------- BLOCK ASSET GENERATION LIST ----------

        //Debug Blocks
        genHorizontalSBI(ModBlocks.PROTOTYPE_MACHINE.get(), build3FaceTexturesBlockModel("prototype_machine","experimental", "prototype_machine_front", "prototype_machine_side", "prototype_machine_top"));

        //Iron-like Blocks
        genFolderedSBI(ModBlocks.VESSELPLATE.get(),"vesselplate");
        genFolderedToggleBlockSBI(ModBlocks.RIVETED_VESSELPLATE_PANEL.get(),"vesselplate","", true, true, "_panel", "");
        genFolderedSBI(ModBlocks.HORIZONTAL_RIVETED_VESSELPLATE.get(),"vesselplate");
        genFolderedSBI(ModBlocks.VERTICAL_RIVETED_VESSELPLATE.get(),"vesselplate");
        genFolderedToggleBlockSBI(ModBlocks.SMOOTH_VESSELPLATE_TILE.get(),"vesselplate", "", true, true, "_tile","");
        genFolderedToggleBlockSBI(ModBlocks.VESSELPLATE_SHEETING.get(),"vesselplate","", true, true, "ing", "");


        genFolderedSBI(ModBlocks.GRAY_VESSELPLATE.get(),"vesselplate");
        genFolderedToggleBlockSBI(ModBlocks.GRAY_RIVETED_VESSELPLATE_PANEL.get(),"vesselplate","", true, true, "_panel", "");
        genFolderedSBI(ModBlocks.GRAY_HORIZONTAL_RIVETED_VESSELPLATE.get(),"vesselplate");
        genFolderedSBI(ModBlocks.GRAY_VERTICAL_RIVETED_VESSELPLATE.get(),"vesselplate");
        genFolderedToggleBlockSBI(ModBlocks.SMOOTH_GRAY_VESSELPLATE_TILE.get(),"vesselplate", "", true, true, "_tile","");
        genFolderedToggleBlockSBI(ModBlocks.GRAY_VESSELPLATE_SHEETING.get(),"vesselplate","", true, true, "ing", "");


        genFolderedToggleBlockSBI(ModBlocks.ENCASED_CABLES.get(),"","",true, true, "encased","vertical_encased");

        genFolderedToggleBlockSBI(ModBlocks.GRATE.get(),"grate","",true, true,"grate","vertical_grate");
        genFolderedToggleBlockSBI(ModBlocks.GRAY_GRATE.get(),"grate","",true, true, "grate","vertical_grate");
        genFolderedToggleBlockSBI(ModBlocks.SEETHROUGH_GRATE.get(), "grate", "grate", false,false, "see-through", "vertical_see-through");
        genFolderedToggleBlockSBI(ModBlocks.GRAY_SEETHROUGH_GRATE.get(),"grate","grate",false,false, "see-through","vertical_see-through");

        genFolderedToggleBlockSBI(ModBlocks.RUSTY_GRATE.get(),"grate","",true, true,"grate","vertical_grate");

        //Glass-like Blocks (With existing Block models that specify rendering properties for glass-transparency)
        genFolderedSI(ModBlocks.REINFORCED_VESSELGLASS.get(),"vesselglass");
        genFolderedSI(ModBlocks.VESSELGLASS.get(),"vesselglass");
        genFolderedSI(ModBlocks.GRAY_REINFORCED_VESSELGLASS.get(),"vesselglass");
        genFolderedSI(ModBlocks.GRAY_VESSELGLASS.get(),"vesselglass");

        //Stone-like Blocks
        genFolderedSBI(ModBlocks.GRIMY_RESTROOM_TILE.get(),"");

        genFolderedToggleBlockSBI(ModBlocks.GRAY_ROCKRETE.get(),"","",true, true,"rockrete","rockrete_rebar");
        stairsBlock(((StairBlock) ModBlocks.GRAY_ROCKRETE_STAIRS.get()), blockTexture(ModBlocks.GRAY_ROCKRETE.get()));
        slabBlock(((SlabBlock) ModBlocks.GRAY_ROCKRETE_SLAB.get()), blockTexture(ModBlocks.GRAY_ROCKRETE.get()), blockTexture(ModBlocks.GRAY_ROCKRETE.get()));

        genFolderedToggleBlockSBI(ModBlocks.GREEN_ROCKRETE.get(),"","",true, true,"rockrete","rockrete_rebar");
        stairsBlock(((StairBlock) ModBlocks.GREEN_ROCKRETE_STAIRS.get()), blockTexture(ModBlocks.GREEN_ROCKRETE.get()));
        slabBlock(((SlabBlock) ModBlocks.GREEN_ROCKRETE_SLAB.get()), blockTexture(ModBlocks.GREEN_ROCKRETE.get()), blockTexture(ModBlocks.GREEN_ROCKRETE.get()));

        genFolderedToggleBlockSBI(ModBlocks.YELLOW_ROCKRETE.get(),"","",true, true,"rockrete","rockrete_rebar");
        stairsBlock(((StairBlock) ModBlocks.YELLOW_ROCKRETE_STAIRS.get()), blockTexture(ModBlocks.YELLOW_ROCKRETE.get()));
        slabBlock(((SlabBlock) ModBlocks.YELLOW_ROCKRETE_SLAB.get()), blockTexture(ModBlocks.YELLOW_ROCKRETE.get()), blockTexture(ModBlocks.YELLOW_ROCKRETE.get()));

        genFolderedToggleBlockSBI(ModBlocks.BLUE_ROCKRETE.get(),"","",true, true,"rockrete","rockrete_rebar");
        stairsBlock(((StairBlock) ModBlocks.BLUE_ROCKRETE_STAIRS.get()), blockTexture(ModBlocks.BLUE_ROCKRETE.get()));
        slabBlock(((SlabBlock) ModBlocks.BLUE_ROCKRETE_SLAB.get()), blockTexture(ModBlocks.BLUE_ROCKRETE.get()), blockTexture(ModBlocks.BLUE_ROCKRETE.get()));

        genFolderedToggleBlockSBI(ModBlocks.RED_ROCKRETE.get(),"","",true, true,"rockrete","rockrete_rebar");
        stairsBlock(((StairBlock) ModBlocks.RED_ROCKRETE_STAIRS.get()), blockTexture(ModBlocks.RED_ROCKRETE.get()));
        slabBlock(((SlabBlock) ModBlocks.RED_ROCKRETE_SLAB.get()), blockTexture(ModBlocks.RED_ROCKRETE.get()), blockTexture(ModBlocks.RED_ROCKRETE.get()));

        genFolderedSBI(ModBlocks.HAZARD_STRIPE_YELLOW.get(),"");
        genFolderedSBI(ModBlocks.HAZARD_STRIPE_RED.get(),"");

        //Other Solid Opaque Full-Blocks with unique face textures
        genHorizontalSBI(ModBlocks.IHEA_FURNITURE_KIT.get(), build6FaceTexturesBlockModel("ihea_furniture_kit", "furniture_category_block", "ihea_furniture_kit_front", "ihea_furniture_kit_back", "ihea_furniture_kit_right", "ihea_furniture_kit_left", "ihea_furniture_kit_top", "ihea_furniture_kit_bottom"));
        genHorizontalSBI(ModBlocks.PIPEWORKS.get(), build3FaceTexturesBlockModel("pipeworks", "pipeworks", "pipeworks_front", "pipeworks_sides", "pipeworks_top"));
        genHorizontalSBI(ModBlocks.SAFETY_FURNISHINGS.get(), build3FaceTexturesBlockModel("safety_furnishings", "furniture_category_block", "safety_furnishings_north", "safety_furnishings_west", "safety_furnishings_up"));
        genHorizontalSBI(ModBlocks.HYGIENE_FURNISHINGS.get(), build3FaceTexturesBlockModel("hygiene_furnishings","furniture_category_block", "hygiene_furnishings_north", "hygiene_furnishings_west", "hygiene_furnishings_up"));
        genHorizontalSBI(ModBlocks.INDUSTRIAL_FURNISHINGS.get(), build3FaceTexturesBlockModel("industrial_furnishings","furniture_category_block", "industrial_furnishings_north", "industrial_furnishings_west", "industrial_furnishings_up"));
        genHorizontalSBI(ModBlocks.TECHNOLOGY_FURNISHINGS.get(), build3FaceTexturesBlockModel("technology_furnishings","furniture_category_block", "technology_furnishings_north", "technology_furnishings_west", "technology_furnishings_up"));
        genHorizontalSBI(ModBlocks.AMENITY_FURNISHINGS.get(), build3FaceTexturesBlockModel("amenity_furnishings","furniture_category_block", "amenity_furnishings_north", "amenity_furnishings_west", "amenity_furnishings_up"));
        genHorizontalSBI(ModBlocks.LOCKER_BOX.get(), build6FaceTexturesBlockModel("locker_box","locker","locker_box_front","locker_box_side","locker_box_side","locker_box_side","locker_box_top","locker_box_bottom"));
        genSimpleSBI(ModBlocks.METALWORKS.get(), build6FaceTexturesBlockModel("metalworks","metalworks","metalworks_side","metalworks_side", "metalworks_side","metalworks_side","metalworks_top","metalworks_bottom"));

        //FURNITURE BLOCKS WITH EXISTING BLOCK MODELS
        GenFacingModelledSI(ModBlocks.SINK.get(),"");
        GenFacingModelledSI(ModBlocks.WHITE_WALL_MEDKIT.get(),"medkit_containers");
        GenFacingModelledSI(ModBlocks.RED_WALL_MEDKIT.get(),"medkit_containers");
        GenFacingModelledSI(ModBlocks.FIRE_EXTINGUISHER.get(), "");
        genAttachedSI(ModBlocks.SMOKE_ALARM.get(), "");
        genAttachedSI(ModBlocks.POSTER_1.get(), "decals");
        genAttachedSBI(ModBlocks.FUEL_DRUM.get(), "", build6FaceTexturesBlockModel("fuel_drum","fuel_drum", "red_labeled_fuel_drum_front", "red_labeled_fuel_drum_front", "red_labeled_fuel_drum_side", "red_labeled_fuel_drum_side", "red_fuel_drum_up","red_fuel_drum_down"));
        genWaterloggableSI(ModBlocks.YELLOW_TRIPOD.get(),"");

        GenFacingPoweredSI(ModBlocks.WORK_LIGHT_MOUNT.get(), "work_light_mount");
        GenFacingPoweredSI(ModBlocks.FLOOR_WORK_LIGHT.get(), "work_light_mount");
        GenFacingPoweredSI(ModBlocks.RETRO_COMPUTER.get(), "retro_computer");
        GenFacingPoweredSI(ModBlocks.RETRO_COMPUTER_2.get(), "retro_computer");
        GenFacingPoweredSI(ModBlocks.CASSETTE_PLAYER.get(), "cassette_player");

        TwoBlockMultiBlock(ModBlocks.LARGE_LOCKER.get(), "locker");
        TwoBlockMultiBlock(ModBlocks.URINAL.get(), "urinal");

        //Use ButtonBlock() For lever/button type redstone blocks.
        
        //Block Item Models only (For blocks with complex states and/or models
        genI(ModBlocks.STRUT.get(),"strut");
        genI(ModBlocks.STRUT_STAIRS.get(),"strut");
        genI(ModBlocks.STRUT_SLAB.get(),"strut");
        genI(ModBlocks.CATWALK_STRUT.get(),"strut");
        genI(ModBlocks.CATWALK_STRUT_STAIRS.get(),"strut");
        genI(ModBlocks.CATWALK_STRUT_SLAB.get(),"strut");
        genI(ModBlocks.GRAY_STRUT.get(),"strut");
        genI(ModBlocks.GRAY_STRUT_STAIRS.get(),"strut");
        genI(ModBlocks.GRAY_STRUT_SLAB.get(),"strut");
        genI(ModBlocks.GRAY_CATWALK_STRUT.get(),"strut");
        genI(ModBlocks.GRAY_CATWALK_STRUT_STAIRS.get(),"strut");
        genI(ModBlocks.GRAY_CATWALK_STRUT_SLAB.get(),"strut");
        genI(ModBlocks.YELLOW_RAILING.get(),"yellow_railing");
        genI(ModBlocks.YELLOW_STAIR_RAILING.get(),"yellow_railing");

        genI(ModBlocks.RIVETED_VESSELPLATE_SLAB.get(),"vesselplate");
        genI(ModBlocks.RIVETED_VESSELPLATE_STAIRS.get(),"vesselplate");
        genI(ModBlocks.GRAY_RIVETED_VESSELPLATE_SLAB.get(),"vesselplate");
        genI(ModBlocks.GRAY_RIVETED_VESSELPLATE_STAIRS.get(),"vesselplate");

        genI(ModBlocks.SMOOTH_VESSELPLATE_SLAB.get(),"vesselplate");
        genI(ModBlocks.SMOOTH_VESSELPLATE_STAIRS.get(),"vesselplate");
        genI(ModBlocks.SMOOTH_GRAY_VESSELPLATE_SLAB.get(),"vesselplate");
        genI(ModBlocks.SMOOTH_GRAY_VESSELPLATE_STAIRS.get(),"vesselplate");

        genI(ModBlocks.VESSELPLATE_SHEETING_SLAB.get(),"vesselplate");
        genI(ModBlocks.VESSELPLATE_SHEETING_STAIRS.get(),"vesselplate");
        genI(ModBlocks.GRAY_VESSELPLATE_SHEETING_SLAB.get(),"vesselplate");
        genI(ModBlocks.GRAY_VESSELPLATE_SHEETING_STAIRS.get(),"vesselplate");

        genI(ModBlocks.BODY_PILLOW.get(),"");
        genI(ModBlocks.BLUE_ROCKRETE_SLAB.get(),"");
        genI(ModBlocks.GRAY_ROCKRETE_SLAB.get(),"");
        genI(ModBlocks.GREEN_ROCKRETE_SLAB.get(),"");
        genI(ModBlocks.YELLOW_ROCKRETE_SLAB.get(),"");
        genI(ModBlocks.RED_ROCKRETE_SLAB.get(),"");

        genI(ModBlocks.BLUE_ROCKRETE_STAIRS.get(),"");
        genI(ModBlocks.GRAY_ROCKRETE_STAIRS.get(),"");
        genI(ModBlocks.GREEN_ROCKRETE_STAIRS.get(),"");
        genI(ModBlocks.YELLOW_ROCKRETE_STAIRS.get(),"");
        genI(ModBlocks.RED_ROCKRETE_STAIRS.get(),"");
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

    private ModelFile buildRotatedTextureBlockModel(Block block, String folderName) { //For blocks that DO NOT use CTM
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        //Builds a textured model that uses one texture .pngs for all 6 faces. The model used here rotates the textures 90 degrees.
        return models().withExistingParent(stringName + "_rotated", modLoc("block/texture_horizontal_template"))
                .texture("all", modLoc("block/" + (folderName+(folderName.isEmpty() ? "":"/") + stringName)));
    }

    //---------- END OF CUSTOM BLOCK MODEL GENERATOR METHODS ----------

    //---------- SBI ASSET GENERATOR METHODS ----------
    // STATE, BLOCK MODEL, AND/OR ITEM MODEL GENERATION

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
                    .rotationX(face == AttachFace.FLOOR ? 0 : (face == AttachFace.WALL ? 90 : 180))
                    .rotationY((int) (face == AttachFace.CEILING ? facing : facing.getOpposite()).toYRot())
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

    private void genSimpleSBI(Block block, ModelFile model) {
        simpleBlock(block, model);

        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String existingModelPath = "block/"+stringName;
        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));
    }

    private void genFolderedToggleBlockSBI(Block block, String textureSubFolder, String existingBaseModelSubFolder, Boolean makeBaseModel, Boolean makeAltModel, String nameStringToReplace, String nameStringReplacement) {

        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String pathToName = "block/"+existingBaseModelSubFolder+(existingBaseModelSubFolder.isEmpty() ? "":"/");

        String baseModelPath = pathToName+stringName;
        String altModelPath = pathToName+stringName.replace(nameStringToReplace,nameStringReplacement);

        String pathToTexture = "block/" + (textureSubFolder+(textureSubFolder.isEmpty() ? "":"/"));
        String texturePath =  pathToTexture + stringName;
        String altTexturePath = pathToTexture + stringName.replace(nameStringToReplace,nameStringReplacement);

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

    //---------- END OF SBI ASSET GENERATOR METHODS ----------
}