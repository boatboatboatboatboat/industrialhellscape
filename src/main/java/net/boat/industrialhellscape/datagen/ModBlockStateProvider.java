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
import net.minecraftforge.registries.RegistryObject;

//IF YOU GET AN ERROR REGARDING JAVA NOT LOOKING THROUGH A SUBFOLDER, THAT MAY BE AN ERROR CAUSED BY ModItemModelProvider, NOT FROM THIS DATAGEN CLASS

public class ModBlockStateProvider extends BlockStateProvider {
    public static final EnumProperty<TwoBlockMultiBlockState> HALF = EnumProperty.create("half", TwoBlockMultiBlockState.class);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, IndustrialHellscape.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        //GENERATED MODELS, GENERATED BLOCKSTATES

        //Iron-like Blocks
        folderedBlockWithItem(ModBlocks.VESSELPLATE.get(),"vesselplate");
        folderedBlockWithItem(ModBlocks.RIVETED_VESSELPLATE.get(),"vesselplate");

        blockWithItem(ModBlocks.GRIMY_RESTROOM_TILE);
        folderedBlockWithItem(ModBlocks.GRAY_VESSELPLATE.get(),"vesselplate");
        folderedBlockWithItem(ModBlocks.GRAY_RIVETED_VESSELPLATE.get(),"vesselplate");

        folderedBlockWithItem(ModBlocks.GRAY_HORIZONTAL_RIVETED_VESSELPLATE.get(),"vesselplate");
        folderedBlockWithItem(ModBlocks.GRAY_VERTICAL_RIVETED_VESSELPLATE.get(),"vesselplate");
        folderedBlockWithItem(ModBlocks.SMOOTH_GRAY_VESSELPLATE.get(),"vesselplate");
        folderedBlockWithItem(ModBlocks.SMOOTH_GRAY_VESSELPLATE_TILE.get(),"vesselplate");
        folderedBlockWithItem(ModBlocks.HORIZONTAL_RIVETED_VESSELPLATE.get(),"vesselplate");
        folderedBlockWithItem(ModBlocks.VERTICAL_RIVETED_VESSELPLATE.get(),"vesselplate");
        blockWithItem(ModBlocks.HORIZONTAL_ENCASED_CABLES);
        blockWithItem(ModBlocks.VERTICAL_ENCASED_CABLES);
        folderedBlockWithItem(ModBlocks.SMOOTH_VESSELPLATE.get(),"vesselplate");
        folderedBlockWithItem(ModBlocks.SMOOTH_VESSELPLATE_TILE.get(),"vesselplate");

        folderedBlockWithItem(ModBlocks.GRATE.get(),"grate");
        folderedBlockWithItem(ModBlocks.GRAY_GRATE.get(),"grate");
        folderedBlockWithItem(ModBlocks.RUSTY_GRATE.get(),"grate");

        //For blocks that have existing item models and block models due to special rendering needs (transparency)
        MakeBlockStateOnly(ModBlocks.REINFORCED_VESSELGLASS.get(),"vesselglass");
        MakeBlockStateOnly(ModBlocks.VESSELGLASS.get(),"vesselglass");
        MakeBlockStateOnly(ModBlocks.GRAY_REINFORCED_VESSELGLASS.get(),"vesselglass");
        MakeBlockStateOnly(ModBlocks.GRAY_VESSELGLASS.get(),"vesselglass");

        MakeBlockStateOnly(ModBlocks.SEETHROUGH_GRATE.get(),"");
        MakeBlockStateOnly(ModBlocks.GRAY_SEETHROUGH_GRATE.get(),"");
        //paneBlock((IronBarsBlock) ModBlocks.SEETHROUGH_GRATE_PANE.get(), modLoc("block/see-through_grate"),modLoc("block/see-through_grate_pane_top"));
        //paneBlock((IronBarsBlock) ModBlocks.GRAY_SEETHROUGH_GRATE_PANE.get(), modLoc("block/gray_see-through_grate"),modLoc("block/gray_see-through_grate_pane_top"));


        //Stone-like Blocks
        blockWithItem(ModBlocks.GRAY_ROCKRETE);
        blockWithItem(ModBlocks.GRAY_ROCKRETE_REBAR);
        stairsBlock(((StairBlock) ModBlocks.GRAY_ROCKRETE_STAIRS.get()), blockTexture(ModBlocks.GRAY_ROCKRETE.get()));
        slabBlock(((SlabBlock) ModBlocks.GRAY_ROCKRETE_SLAB.get()), blockTexture(ModBlocks.GRAY_ROCKRETE.get()), blockTexture(ModBlocks.GRAY_ROCKRETE.get()));

        blockWithItem(ModBlocks.GREEN_ROCKRETE);
        blockWithItem(ModBlocks.GREEN_ROCKRETE_REBAR);
        stairsBlock(((StairBlock) ModBlocks.GREEN_ROCKRETE_STAIRS.get()), blockTexture(ModBlocks.GREEN_ROCKRETE.get()));
        slabBlock(((SlabBlock) ModBlocks.GREEN_ROCKRETE_SLAB.get()), blockTexture(ModBlocks.GREEN_ROCKRETE.get()), blockTexture(ModBlocks.GREEN_ROCKRETE.get()));

        blockWithItem(ModBlocks.YELLOW_ROCKRETE);
        blockWithItem(ModBlocks.YELLOW_ROCKRETE_REBAR);
        stairsBlock(((StairBlock) ModBlocks.YELLOW_ROCKRETE_STAIRS.get()), blockTexture(ModBlocks.YELLOW_ROCKRETE.get()));
        slabBlock(((SlabBlock) ModBlocks.YELLOW_ROCKRETE_SLAB.get()), blockTexture(ModBlocks.YELLOW_ROCKRETE.get()), blockTexture(ModBlocks.YELLOW_ROCKRETE.get()));

        blockWithItem(ModBlocks.BLUE_ROCKRETE);
        blockWithItem(ModBlocks.BLUE_ROCKRETE_REBAR);
        stairsBlock(((StairBlock) ModBlocks.BLUE_ROCKRETE_STAIRS.get()), blockTexture(ModBlocks.BLUE_ROCKRETE.get()));
        slabBlock(((SlabBlock) ModBlocks.BLUE_ROCKRETE_SLAB.get()), blockTexture(ModBlocks.BLUE_ROCKRETE.get()), blockTexture(ModBlocks.BLUE_ROCKRETE.get()));

        blockWithItem(ModBlocks.HAZARD_STRIPE_YELLOW);
        blockWithItem(ModBlocks.HAZARD_STRIPE_RED);

        //Furniture Block
        horizontalBlock(ModBlocks.IHEA_FURNITURE_KIT.get(), build6FaceTexturesBlock("ihea_furniture_kit", "furniture_category_block", "ihea_furniture_kit_front","ihea_furniture_kit_back","ihea_furniture_kit_left","ihea_furniture_kit_right","ihea_furniture_kit_top","ihea_furniture_kit_bottom"));
        horizontalBlock(ModBlocks.PIPEWORKS.get(), build3FaceTexturesBlock("pipeworks", "pipeworks", "pipeworks_front", "pipeworks_sides", "pipeworks_top"));
        horizontalBlock(ModBlocks.SAFETY_FURNISHINGS.get(), build3FaceTexturesBlock("safety_furnishings", "furniture_category_block", "safety_furnishings_north", "safety_furnishings_west", "safety_furnishings_up"));
        horizontalBlock(ModBlocks.HYGIENE_FURNISHINGS.get(), build3FaceTexturesBlock("hygiene_furnishings","furniture_category_block", "hygiene_furnishings_north", "hygiene_furnishings_west", "hygiene_furnishings_up"));
        horizontalBlock(ModBlocks.INDUSTRIAL_FURNISHINGS.get(), build3FaceTexturesBlock("industrial_furnishings","furniture_category_block", "industrial_furnishings_north", "industrial_furnishings_west", "industrial_furnishings_up"));
        horizontalBlock(ModBlocks.TECHNOLOGY_FURNISHINGS.get(), build3FaceTexturesBlock("technology_furnishings","furniture_category_block", "technology_furnishings_north", "technology_furnishings_west", "technology_furnishings_up"));
        horizontalBlock(ModBlocks.AMENITY_FURNISHINGS.get(), build3FaceTexturesBlock("amenity_furnishings","furniture_category_block", "amenity_furnishings_north", "amenity_furnishings_west", "amenity_furnishings_up"));

        //CUSTOM EXISTING MODELS, GENERATED BLOCKSTATES
        WaterloggableFacingBlock(ModBlocks.SINK.get(),"");
        WaterloggableFacingBlock(ModBlocks.WHITE_WALL_MEDKIT.get(),"medkit_containers");
        WaterloggableFacingBlock(ModBlocks.RED_WALL_MEDKIT.get(),"medkit_containers");
        WaterloggableBlock(ModBlocks.YELLOW_TRIPOD.get(),"");

        PowerableBlock(ModBlocks.WORK_LIGHT_MOUNT.get(), "work_light_mount");
        PowerableBlock(ModBlocks.FLOOR_WORK_LIGHT.get(), "work_light_mount");
        PowerableBlock(ModBlocks.RETRO_COMPUTER.get(), "retro_computer");
        PowerableBlock(ModBlocks.CASSETTE_PLAYER.get(), "cassette_player");
        TwoBlockMultiBlock(ModBlocks.LARGE_LOCKER.get(), "locker");
        WaterloggableFacingBlock(ModBlocks.LOCKER_BOX.get(),"locker");

    }

    private void MakeBlockStateOnly(Block block, String folderName) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath().toString();
        getVariantBuilder(block)
                .partialState()
                .modelForState()
                .modelFile(models().getExistingFile(modLoc("block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName)))
                .addModel();
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void folderedBlockWithItem(Block block, String subfolder) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath().toString();

        simpleBlock(block,
                models().withExistingParent(stringName, mcLoc("block/cube_all"))
                        .texture("all", modLoc("block/" + subfolder + "/" + stringName)));
    }

    private ModelFile build3FaceTexturesBlock(String blockName, String folderName, String frontAndBack, String leftAndRight, String topAndBottom) {
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

    private ModelFile build6FaceTexturesBlock(String blockName, String folderName, String front, String back, String left, String right, String top, String bottom) {
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

    private void WaterloggableFacingBlock(Block block, String folderName) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath().toString();
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
    }

    private void PowerableBlock(Block block, String folderName) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath().toString();
        String unpoweredModel = "block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName;
        String poweredModel = unpoweredModel+"_on";

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction facing = state.getValue(FACING);
                    Boolean powered = state.getValue(POWERED);
                    String modelToUse = powered? poweredModel : unpoweredModel;

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

    private void WaterloggableBlock(Block block, String folderName) {
        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath().toString();
        getVariantBuilder(block)
                .partialState()
                .modelForState()
                .modelFile(models().getExistingFile(modLoc("block/"+folderName+(folderName.isEmpty() ? "":"/")+stringName)))
                .addModel();
    }
}