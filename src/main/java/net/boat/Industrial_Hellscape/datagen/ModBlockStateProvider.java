package net.boat.Industrial_Hellscape.datagen;

import net.boat.Industrial_Hellscape.IndustrialHellscape;
import net.boat.Industrial_Hellscape.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
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

        //STONE-LIKE BLOCKS
        blockWithItem(ModBlocks.GRAY_ROCKRETE);
        blockWithItem(ModBlocks.HAZARD_STRIPE_YELLOW);
        blockWithItem(ModBlocks.HAZARD_STRIPE_RED);

        //SPECIAL BLOCKS
        blockWithItem(ModBlocks.SOUND_BLOCK);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));

    }
}
