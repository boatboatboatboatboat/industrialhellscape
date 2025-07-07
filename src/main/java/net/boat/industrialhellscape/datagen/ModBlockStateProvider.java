package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
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
/*
        stairsWithCustomTextures(
                ModBlocks.CATWALK_STRUT_STAIRS.get(),
                modLoc("block/floorgrate_catwalk"), //bottom
                modLoc("block/floorgrate_catwalk"), //top
                modLoc("block/reinforced_strut") //side
        );
        slabWithCustomTextures(
                ModBlocks.CATWALK_STRUT_SLAB.get(),
                modLoc("block/strut"), //bottom
                modLoc("block/floorgrate_catwalk"), //top
                modLoc("block/reinforced_strut"), //side
                modLoc("block/catwalk_strut") //original full block for double-slab (Model)
        );
*/
        blockWithItem(ModBlocks.HORIZONTAL_RIVETED_VESSELPLATE);
        blockWithItem(ModBlocks.VERTICAL_RIVETED_VESSELPLATE);
        blockWithItem(ModBlocks.SMOOTH_VESSELPLATE);
        blockWithItem(ModBlocks.SMOOTH_VESSELPLATE_TILE);
        blockWithItem(ModBlocks.REINFORCED_VESSELGLASS);
        blockWithItem(ModBlocks.VESSELGLASS);

       // stairsBlock(((StairBlock) ModBlocks.STRUT_STAIRS.get()), blockTexture(ModBlocks.STRUT.get()));
      //  slabBlock(((SlabBlock) ModBlocks.STRUT_SLAB.get()), blockTexture(ModBlocks.STRUT.get()), blockTexture(ModBlocks.STRUT.get()));

        //*stairsBlock(((StairBlock) ModBlocks.CATWALK_STRUT_STAIRS.get()), blockTexture(ModBlocks.STRUT.get()));
        //slabBlock(((SlabBlock) ModBlocks.CATWALK_STRUT_SLAB.get()), blockTexture(ModBlocks.STRUT.get()), blockTexture(ModBlocks.STRUT.get()));

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
    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(IndustrialHellscape.MOD_ID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }
    private void stairsWithCustomTextures(Block block, ResourceLocation bottom, ResourceLocation top, ResourceLocation side) {
        ModelFile stairs = models().stairs(name(block), side, bottom, top);
        ModelFile stairsInner = models().stairsInner(name(block) + "_inner", side, bottom, top);
        ModelFile stairsOuter = models().stairsOuter(name(block) + "_outer", side, bottom, top);
        stairsBlock((StairBlock) block, stairs, stairsInner, stairsOuter);
    }

    private void slabWithCustomTextures(Block block, ResourceLocation bottom, ResourceLocation top, ResourceLocation side, ResourceLocation originalFullBLock) {
        ModelFile slab = models().slab(name(block), side, bottom, top);
        ModelFile slabTop = models().slabTop(name(block) + "_top", side, bottom, top);
        getVariantBuilder(block)
                .partialState().with(SlabBlock.TYPE, SlabType.BOTTOM).addModels(new ConfiguredModel(slab))
                .partialState().with(SlabBlock.TYPE, SlabType.TOP).addModels(new ConfiguredModel(slabTop))
                .partialState().with(SlabBlock.TYPE, SlabType.DOUBLE).addModels(new ConfiguredModel(models().getExistingFile(originalFullBLock)));
    }

    private String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }
}
