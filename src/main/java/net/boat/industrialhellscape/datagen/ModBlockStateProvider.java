package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.special_blocks_properties.InnerCornerConnectionState;
import net.boat.industrialhellscape.block.special_blocks_properties.PillarConnectionState;
import net.boat.industrialhellscape.block.special_blocks_properties.RelativePlanarDirectionState;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.naming.Context;

public class ModBlockStateProvider extends BlockStateProvider {

    public static final EnumProperty<PillarConnectionState> TYPE = EnumProperty.create("type", PillarConnectionState.class);
    public static final EnumProperty<InnerCornerConnectionState> TYPE_CORNER = EnumProperty.create("type", InnerCornerConnectionState.class); //"UP", "SIDE", or "DOWN" enum values
    public static final EnumProperty<Direction> SURFACE_DIRECTION = BlockStateProperties.FACING;
    public static final EnumProperty<Direction.Axis> PLANAR_AXIS = BlockStateProperties.AXIS;
    public static final EnumProperty<RelativePlanarDirectionState> PLANE_DIRECTION = EnumProperty.create("plane_direction", RelativePlanarDirectionState.class);

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, IndustrialHellscape.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        //IRON-LIKE BLOCKS
        blockWithItem(ModBlocks.VESSELPLATE);
        blockWithItem(ModBlocks.RIVETED_VESSELPLATE);
        blockWithItem(ModBlocks.VESSELPLATE_GRATE_BLOCK);
        blockWithItem(ModBlocks.VESSELPLATE_GRATE);
        blockWithItem(ModBlocks.RUSTY_VESSELPLATE_GRATE);
        blockWithItem(ModBlocks.GRIMY_RESTROOM_TILE);

        blockWithItem(ModBlocks.GRAY_VESSELPLATE);
        blockWithItem(ModBlocks.GRAY_RIVETED_VESSELPLATE);
        blockWithItem(ModBlocks.GRAY_VESSELPLATE_GRATE);
        blockWithItem(ModBlocks.GRAY_HORIZONTAL_RIVETED_VESSELPLATE);
        blockWithItem(ModBlocks.GRAY_VERTICAL_RIVETED_VESSELPLATE);
        blockWithItem(ModBlocks.SMOOTH_GRAY_VESSELPLATE);
        blockWithItem(ModBlocks.SMOOTH_GRAY_VESSELPLATE_TILE);

        blockWithItem(ModBlocks.HORIZONTAL_RIVETED_VESSELPLATE);
        blockWithItem(ModBlocks.VERTICAL_RIVETED_VESSELPLATE);
        blockWithItem(ModBlocks.HORIZONTAL_ENCASED_CABLES);
        blockWithItem(ModBlocks.VERTICAL_ENCASED_CABLES);
        blockWithItem(ModBlocks.SMOOTH_VESSELPLATE);
        blockWithItem(ModBlocks.SMOOTH_VESSELPLATE_TILE);
        blockWithItem(ModBlocks.REINFORCED_VESSELGLASS);
        blockWithItem(ModBlocks.VESSELGLASS);

        //-----

        //STONE-LIKE BLOCKS
        blockWithItem(ModBlocks.GRAY_ROCKRETE);
        blockWithItem(ModBlocks.GRAY_ROCKRETE_REBAR);
        stairsBlock(((StairBlock) ModBlocks.GRAY_ROCKRETE_STAIRS.get()), blockTexture(ModBlocks.GRAY_ROCKRETE.get()));
        slabBlock(((SlabBlock) ModBlocks.GRAY_ROCKRETE_SLAB.get()), blockTexture(ModBlocks.GRAY_ROCKRETE.get()), blockTexture(ModBlocks.GRAY_ROCKRETE.get()));

        blockWithItem(ModBlocks.HAZARD_STRIPE_YELLOW);
        blockWithItem(ModBlocks.HAZARD_STRIPE_RED);

        //Custom Models
        horizontalBlock(ModBlocks.PIPEWORKS.get(), build3FaceTexturesBlock("pipeworks", "pipeworks_front", "pipeworks_sides", "pipeworks_top"));
        WaterloggableFacingBlock(ModBlocks.SINK.get(), "sink");
        LightableBlock(ModBlocks.WORK_LIGHT_MOUNT.get());
        LightableBlock(ModBlocks.RETRO_COMPUTER.get());

        WaterloggableBlock(ModBlocks.YELLOW_TRIPOD.get(), "yellow_tripod");
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        //blockWithItem expects a ModBlock. Its second parameter, the "Model" is already defined here by blockRegistryObject
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private ModelFile build3FaceTexturesBlock(String blockName, String frontAndBack, String leftAndRight, String topAndBottom) {
        //Builds a textured model that uses three texture .pngs for all 6 faces.
        ModelFile model = models().cube(
                "pipeworks",
                modLoc( "block/" + blockName+ "/" + topAndBottom), //bottom
                modLoc("block/" + blockName+ "/" + topAndBottom), //top
                modLoc("block/" + blockName+ "/" + frontAndBack), //front
                modLoc("block/" + blockName+ "/" + frontAndBack), //back
                modLoc("block/"  + blockName+ "/" + leftAndRight), //left
                modLoc("block/"  + blockName+ "/" + leftAndRight) //right
                ).texture("particle", modLoc("block/" + blockName +"/" + topAndBottom));
        return model;
    }

    private ModelFile customModel(Block block) {
        String pathName = ForgeRegistries.BLOCKS.getKey(block).getPath();
        ModelFile model = models().getExistingFile(modLoc(pathName));
        return model;
    }

    private void WaterloggableFacingBlock(Block block, String modelName) {
        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

                    int yRot = switch (facing) {
                        case SOUTH -> 180;
                        case WEST  -> 270;
                        case EAST  -> 90;
                        default -> 0; //NORTH
                    };

                    return ConfiguredModel.builder()
                            .modelFile(models().getExistingFile(modLoc("block/" + modelName)))
                            .rotationY(yRot)
                            .build();
                }, BlockStateProperties.WATERLOGGED);
    }

    private void LightableBlock(Block block) {

        String stringName = BuiltInRegistries.BLOCK.getKey(block).getPath().toString();
        ModelFile unlitBlock = models().getExistingFile(modLoc("block/"+stringName)); //default
        ModelFile litBlock = models().getExistingFile(modLoc("block/"+stringName+"_on"));

        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction FACING = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
                    Boolean POWERED = state.getValue(BlockStateProperties.POWERED);

                    int yRotation = switch (FACING) {
                        case SOUTH -> 180;
                        case WEST  -> 270;
                        case EAST  -> 90;
                        default -> 0;
                    };

                    return
                            ConfiguredModel.builder()
                            .modelFile( POWERED ? litBlock : unlitBlock)
                            .rotationY(yRotation)
                            .build();
                }, BlockStateProperties.WATERLOGGED);



    }
    private void WaterloggableBlock(Block block, String modelName) {
        getVariantBuilder(block)
                .partialState()
                .modelForState()
                .modelFile(models().getExistingFile(modLoc("block/" + modelName)))
                .addModel();
    }
}


//ConfiguredModel.builder().modelFile(prov.models()
//          .withExistingParent(ctx.getName() + (state.getValue(BlockStateProperties.LIT) ? "" : "_off"), prov.modLoc("block/cage_lamp"))
//          .texture("cage", cage)
//          .texture("lamp", state.getValue(BlockStateProperties.LIT) ? lampOn : lampOff)
//          .texture("particle", cage)
//        )