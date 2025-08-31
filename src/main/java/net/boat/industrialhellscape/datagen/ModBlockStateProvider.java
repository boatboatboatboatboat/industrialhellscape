package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.special_blocks_properties.TwoBlockMultiBlockState;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

//IF YOU GET AN ERROR REGARDING JAVA NOT LOOKING THROUGH A SUBFOLDER, THAT MAY BE AN ERROR CAUSED BY ModItemModelProvider, NOT FROM THIS DATAGEN CLASS

public class ModBlockStateProvider extends BlockStateProvider {
    public static final EnumProperty<TwoBlockMultiBlockState> HALF = EnumProperty.create("half", TwoBlockMultiBlockState.class);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty ALT_TEXTURE = BooleanProperty.create("alt_texture");

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, IndustrialHellscape.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
         //Method naming convention ("SBI", "SI", etc):
        //"S" - Generates Block State
        //"B" - Generates Block Model (if no existing one is available). Simple full-blocks with no special rendering are being generated currently
        //"I" - Generates Item Model

        //---------- BLOCK ASSET GENERATION LIST ----------

        //Iron-like Blocks
        genFolderedSBI(ModBlocks.VESSELPLATE.get(),"vesselplate");
        genFolderedSBI(ModBlocks.RIVETED_VESSELPLATE.get(),"vesselplate");

        genFolderedToggleBlockSBI(ModBlocks.SMOOTH_VESSELPLATE_TILE.get(),"vesselplate", "", true, "_tile","");
        genFolderedToggleBlockSBI(ModBlocks.SMOOTH_GRAY_VESSELPLATE_TILE.get(),"vesselplate", "", true, "_tile","");

        genFolderedSBI(ModBlocks.HORIZONTAL_RIVETED_VESSELPLATE.get(),"vesselplate");
        genFolderedSBI(ModBlocks.VERTICAL_RIVETED_VESSELPLATE.get(),"vesselplate");

        genFolderedSBI(ModBlocks.GRAY_VESSELPLATE.get(),"vesselplate");
        genFolderedSBI(ModBlocks.GRAY_RIVETED_VESSELPLATE.get(),"vesselplate");
        genFolderedSBI(ModBlocks.GRAY_HORIZONTAL_RIVETED_VESSELPLATE.get(),"vesselplate");
        genFolderedSBI(ModBlocks.GRAY_VERTICAL_RIVETED_VESSELPLATE.get(),"vesselplate");

        genFolderedToggleBlockSBI(ModBlocks.ENCASED_CABLES.get(),"","",true, "encased","vertical_encased");

        genFolderedToggleBlockSBI(ModBlocks.GRATE.get(),"grate","",true,"grate","vertical_grate");
        genFolderedToggleBlockSBI(ModBlocks.GRAY_GRATE.get(),"grate","",true, "grate","vertical_grate");
        genFolderedToggleBlockSBI(ModBlocks.RUSTY_GRATE.get(),"grate","",true,"grate","vertical_grate");

        genFolderedSI(ModBlocks.SEETHROUGH_GRATE.get(), "grate");
        genFolderedSI(ModBlocks.GRAY_SEETHROUGH_GRATE.get(),"grate");
        //paneBlock((IronBarsBlock) ModBlocks.SEETHROUGH_GRATE_PANE.get(), modLoc("block/see-through_grate"),modLoc("block/see-through_grate_pane_top"));
        //paneBlock((IronBarsBlock) ModBlocks.GRAY_SEETHROUGH_GRATE_PANE.get(), modLoc("block/gray_see-through_grate"),modLoc("block/gray_see-through_grate_pane_top"));


        //Stone-like Blocks
        genFolderedSBI(ModBlocks.GRIMY_RESTROOM_TILE.get(),"");

        genFolderedSBI(ModBlocks.GRAY_ROCKRETE.get(),"");
        genFolderedSBI(ModBlocks.GRAY_ROCKRETE_REBAR.get(),"");
        stairsBlock(((StairBlock) ModBlocks.GRAY_ROCKRETE_STAIRS.get()), blockTexture(ModBlocks.GRAY_ROCKRETE.get()));
        slabBlock(((SlabBlock) ModBlocks.GRAY_ROCKRETE_SLAB.get()), blockTexture(ModBlocks.GRAY_ROCKRETE.get()), blockTexture(ModBlocks.GRAY_ROCKRETE.get()));

        genFolderedSBI(ModBlocks.GREEN_ROCKRETE.get(),"");
        genFolderedSBI(ModBlocks.GREEN_ROCKRETE_REBAR.get(),"");
        stairsBlock(((StairBlock) ModBlocks.GREEN_ROCKRETE_STAIRS.get()), blockTexture(ModBlocks.GREEN_ROCKRETE.get()));
        slabBlock(((SlabBlock) ModBlocks.GREEN_ROCKRETE_SLAB.get()), blockTexture(ModBlocks.GREEN_ROCKRETE.get()), blockTexture(ModBlocks.GREEN_ROCKRETE.get()));

        genFolderedSBI(ModBlocks.YELLOW_ROCKRETE.get(),"");
        genFolderedSBI(ModBlocks.YELLOW_ROCKRETE_REBAR.get(),"");
        stairsBlock(((StairBlock) ModBlocks.YELLOW_ROCKRETE_STAIRS.get()), blockTexture(ModBlocks.YELLOW_ROCKRETE.get()));
        slabBlock(((SlabBlock) ModBlocks.YELLOW_ROCKRETE_SLAB.get()), blockTexture(ModBlocks.YELLOW_ROCKRETE.get()), blockTexture(ModBlocks.YELLOW_ROCKRETE.get()));

        genFolderedSBI(ModBlocks.BLUE_ROCKRETE.get(),"");
        genFolderedSBI(ModBlocks.BLUE_ROCKRETE_REBAR.get(),"");
        stairsBlock(((StairBlock) ModBlocks.BLUE_ROCKRETE_STAIRS.get()), blockTexture(ModBlocks.BLUE_ROCKRETE.get()));
        slabBlock(((SlabBlock) ModBlocks.BLUE_ROCKRETE_SLAB.get()), blockTexture(ModBlocks.BLUE_ROCKRETE.get()), blockTexture(ModBlocks.BLUE_ROCKRETE.get()));

        genFolderedSBI(ModBlocks.HAZARD_STRIPE_YELLOW.get(),"");
        genFolderedSBI(ModBlocks.HAZARD_STRIPE_RED.get(),"");

        //Other Solid Opaque Full-Blocks with unique face textures
        horizontalBlock(ModBlocks.IHEA_FURNITURE_KIT.get(), build6FaceTexturesBlockModel("ihea_furniture_kit", "furniture_category_block", "ihea_furniture_kit_front","ihea_furniture_kit_back","ihea_furniture_kit_left","ihea_furniture_kit_right","ihea_furniture_kit_top","ihea_furniture_kit_bottom"));
        horizontalBlock(ModBlocks.PIPEWORKS.get(), build3FaceTexturesBlockModel("pipeworks", "pipeworks", "pipeworks_front", "pipeworks_sides", "pipeworks_top"));
        horizontalBlock(ModBlocks.SAFETY_FURNISHINGS.get(), build3FaceTexturesBlockModel("safety_furnishings", "furniture_category_block", "safety_furnishings_north", "safety_furnishings_west", "safety_furnishings_up"));
        horizontalBlock(ModBlocks.HYGIENE_FURNISHINGS.get(), build3FaceTexturesBlockModel("hygiene_furnishings","furniture_category_block", "hygiene_furnishings_north", "hygiene_furnishings_west", "hygiene_furnishings_up"));
        horizontalBlock(ModBlocks.INDUSTRIAL_FURNISHINGS.get(), build3FaceTexturesBlockModel("industrial_furnishings","furniture_category_block", "industrial_furnishings_north", "industrial_furnishings_west", "industrial_furnishings_up"));
        horizontalBlock(ModBlocks.TECHNOLOGY_FURNISHINGS.get(), build3FaceTexturesBlockModel("technology_furnishings","furniture_category_block", "technology_furnishings_north", "technology_furnishings_west", "technology_furnishings_up"));
        horizontalBlock(ModBlocks.AMENITY_FURNISHINGS.get(), build3FaceTexturesBlockModel("amenity_furnishings","furniture_category_block", "amenity_furnishings_north", "amenity_furnishings_west", "amenity_furnishings_up"));

        GenFacingWaterloggableSI(ModBlocks.SINK.get(),"");
        GenFacingWaterloggableSI(ModBlocks.WHITE_WALL_MEDKIT.get(),"medkit_containers");
        GenFacingWaterloggableSI(ModBlocks.RED_WALL_MEDKIT.get(),"medkit_containers");
        genWaterloggableSI(ModBlocks.YELLOW_TRIPOD.get(),"");

        GenFacingPoweredSI(ModBlocks.WORK_LIGHT_MOUNT.get(), "work_light_mount");
        GenFacingPoweredSI(ModBlocks.FLOOR_WORK_LIGHT.get(), "work_light_mount");
        GenFacingPoweredSI(ModBlocks.RETRO_COMPUTER.get(), "retro_computer");
        GenFacingPoweredSI(ModBlocks.CASSETTE_PLAYER.get(), "cassette_player");

        TwoBlockMultiBlock(ModBlocks.LARGE_LOCKER.get(), "locker");
        GenFacingWaterloggableSI(ModBlocks.LOCKER_BOX.get(),"locker");

        genFolderedSI(ModBlocks.REINFORCED_VESSELGLASS.get(),"vesselglass");
        genFolderedSI(ModBlocks.VESSELGLASS.get(),"vesselglass");
        genFolderedSI(ModBlocks.GRAY_REINFORCED_VESSELGLASS.get(),"vesselglass");
        genFolderedSI(ModBlocks.GRAY_VESSELGLASS.get(),"vesselglass");

    }
    //---------- END OF BLOCK ASSET GENERATION LIST ----------

    //---------- CUSTOM BLOCK MODEL GENERATORS ----------

    private ModelFile build3FaceTexturesBlockModel(String blockName, String folderName, String frontAndBack, String leftAndRight, String topAndBottom) {
        //Builds a textured model that uses three texture .pngs for all 6 faces.
        ModelFile model = models().cube(
                blockName,
                modLoc( "block/" + folderName+ "/" + topAndBottom), //bottom
                modLoc("block/" + folderName+ "/" + topAndBottom), //top
                modLoc("block/" + folderName+ "/" + frontAndBack), //front
                modLoc("block/" + folderName+ "/" + frontAndBack), //back
                modLoc("block/"  + folderName+ "/" + leftAndRight), //left
                modLoc("block/"  + folderName+ "/" + leftAndRight) //right
                ).texture("particle", modLoc("block/" + folderName +"/" + topAndBottom));
        return model;
    }

    private ModelFile build6FaceTexturesBlockModel(String blockName, String folderName, String front, String back, String left, String right, String top, String bottom) {
        //Builds a textured model that uses three texture .pngs for all 6 faces.
        ModelFile model = models().cube(
                blockName,
                modLoc( "block/" + folderName+ "/" + bottom), //bottom
                modLoc("block/" + folderName+ "/" + top), //top
                modLoc("block/" + folderName+ "/" + front), //front
                modLoc("block/" + folderName+ "/" + back), //back
                modLoc("block/"  + folderName+ "/" + left), //left
                modLoc("block/"  + folderName+ "/" + right) //right
        ).texture("particle", modLoc("block/" + folderName +"/" + top));
        return model;
    }

    private void buildRotatedTextureBlockModel(Block block, String folderName) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath().toString();
        //Builds a textured model that uses one texture .pngs for all 6 faces. The model used here rotates the textures 90 degrees.
        ModelFile model = models().withExistingParent(stringName + "_rotated", modLoc("block/texture_horizontal_template"))
                .texture("all", modLoc("block/" + (folderName+(folderName.isEmpty() ? "":"/") + stringName)));
    }

    //---------- END OF CUSTOM BLOCK GENERATOR METHODS ----------

    //---------- SBI ASSET GENERATOR METHODS ----------

    private void genFolderedSI(Block block, String folderName) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath().toString();
        String existingModelPath = "block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName;
        getVariantBuilder(block)
                .partialState()
                .modelForState()
                .modelFile(models().getExistingFile(modLoc(existingModelPath)))
                .addModel();
        //Generate Item Model
        simpleBlockItem(block, models().getExistingFile(modLoc(existingModelPath)));
    }

    private void genFolderedSBI(Block block, String subfolder) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath().toString();

        simpleBlockWithItem(block,
                models().withExistingParent(stringName, mcLoc("block/cube_all"))
                        .texture("all", modLoc("block/" + subfolder+(subfolder.isEmpty() ? "":"/") + stringName)));
    }

    private void genFolderedToggleBlockSBI(Block block, String textureSubFolder, String existingBaseModelSubFolder, Boolean makeBaseModel, String nameStringToReplace, String nameStringReplacement) {

        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath().toString();
        String pathToName = "block/"+existingBaseModelSubFolder+(existingBaseModelSubFolder.isEmpty() ? "":"/");

        String baseModelPath = pathToName+stringName;
        String altModelPath = pathToName+stringName.replace(nameStringToReplace,nameStringReplacement);

        String pathToTexture = "block/" + (textureSubFolder+(textureSubFolder.isEmpty() ? "":"/"));
        String texturePath =  pathToTexture + stringName;
        String rotatedTexturePath = pathToTexture + stringName.replace(nameStringToReplace,nameStringReplacement);

        //GENERATE BASE MODEL (DEFAULT STATE), optional if not already present
        if(makeBaseModel) {
            models().withExistingParent(stringName, mcLoc("block/cube_all"))
                    .texture("all", modLoc(texturePath));
        }

        //GENERATE ROTATED MODEL (ALT TEXTURE STATE)
        models().withExistingParent(altModelPath, mcLoc("block/cube_all"))
                .texture("all", modLoc(rotatedTexturePath)); //Generate model of alt-texture block in generated models/block folder

        //GENERATE BLOCKSTATES
        getVariantBuilder(block)
                .forAllStates(state -> {
                    boolean altTexture = state.getValue(ALT_TEXTURE);
                    String modelToUse = (altTexture) ? altModelPath : baseModelPath;

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc(modelToUse)))
                            .build();
                });
        //GENERATE ITEM MODEL
        simpleBlockItem(block, models().getExistingFile(modLoc(baseModelPath)));
    }



    private void GenFacingWaterloggableSI(Block block, String folderName) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath().toString();
        String modelPath = "block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName;

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction facing = state.getValue(FACING);

                    int yRot = switch (facing) {
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
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath().toString();
        String unpoweredModelPath = "block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName;
        String poweredModelPath = unpoweredModelPath+"_on";

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction facing = state.getValue(FACING);
                    Boolean powered = state.getValue(POWERED);
                    String modelToUse = powered? poweredModelPath : unpoweredModelPath;

                    int yRot = switch (facing   ) {
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
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath().toString();
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
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath().toString();
        String negativeBlock = "block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName;
        String positiveBlock = negativeBlock+"_positive";

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction facing = state.getValue(FACING);
                    TwoBlockMultiBlockState half = state.getValue(HALF);
                    String modelToUse = (half == TwoBlockMultiBlockState.POSITIVE)? positiveBlock : negativeBlock;

                    int yRot = switch (facing   ) {
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

    //---------- END OF SBI ASSET GENERATOR METHODS ----------
}