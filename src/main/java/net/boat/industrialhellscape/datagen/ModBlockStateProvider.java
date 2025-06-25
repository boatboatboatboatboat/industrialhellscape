package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
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
        blockWithItem(ModBlocks.VERTICAL_RIVETED_VESSELPLATE);

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
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));

    }
}
