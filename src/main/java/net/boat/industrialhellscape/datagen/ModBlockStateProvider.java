package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, IndustrialHellscape.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        //IRON-LIKE BLOCKS
        blockWithItem(ModBlocks.VESSELPLATE);
        blockWithItem(ModBlocks.VESSELPLATE_GRATE_BLOCK);
        blockWithItem(ModBlocks.VESSELPLATE_GRATE);
        blockWithItem(ModBlocks.RUSTY_VESSELPLATE_GRATE);

        //logBlock(((RotatedPillarBlock) ModBlocks.VERTICAL_RIVETED_VESSELPLATE.get()));
        //blockItem(ModBlocks.VERTICAL_RIVETED_VESSELPLATE);
        /*simpleBlock(ModBlocks.BUNKER_WALL.get(), models().cubeBottomTop(
                "vertical_riveted_vesselplate",
                modLoc("block/vertical_riveted_vesselplate"), //"side"
                modLoc("block/vertical_riveted_vesselplate_top"), //"end"
                modLoc("block/vertical_riveted_vesselplate_top") //"end"
        ));*/
        blockWithItem(ModBlocks.HORIZONTAL_RIVETED_VESSELPLATE);
        blockWithItem(ModBlocks.VERTICAL_RIVETED_VESSELPLATE);
        blockWithItem(ModBlocks.SMOOTH_VESSELPLATE);
        blockWithItem(ModBlocks.SMOOTH_VESSELPLATE_TILE);
        blockWithItem(ModBlocks.REINFORCED_VESSELGLASS);
        blockWithItem(ModBlocks.VESSELGLASS);

        //STONE-LIKE BLOCKS

        blockWithItem(ModBlocks.LIGHT_GRAY_ROCKRETE);
        blockWithItem(ModBlocks.LIGHT_GRAY_ROCKRETE_REBAR);
        stairsBlock(((StairBlock) ModBlocks.LIGHT_GRAY_ROCKRETE_STAIRS.get()), blockTexture(ModBlocks.LIGHT_GRAY_ROCKRETE.get()));
        slabBlock(((SlabBlock) ModBlocks.LIGHT_GRAY_ROCKRETE_SLAB.get()), blockTexture(ModBlocks.LIGHT_GRAY_ROCKRETE.get()), blockTexture(ModBlocks.LIGHT_GRAY_ROCKRETE.get()));

        blockWithItem(ModBlocks.GRAY_ROCKRETE);
        blockWithItem(ModBlocks.WHITE_ROCKRETE);
        blockWithItem(ModBlocks.BLACK_ROCKRETE);

        blockWithItem(ModBlocks.HAZARD_STRIPE_YELLOW);
        blockWithItem(ModBlocks.HAZARD_STRIPE_RED);

        //SPECIAL BLOCKS
        blockWithItem(ModBlocks.SOUND_BLOCK);


        simpleBlockWithItem(ModBlocks.DEBUG_DESK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/debug_desk")));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(IndustrialHellscape.MOD_ID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }
}
