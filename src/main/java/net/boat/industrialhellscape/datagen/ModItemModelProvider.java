package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, IndustrialHellscape.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.FLOPPY_DISK);
        simpleItem(ModItems.FLOPPY_DISKETTE);
        simpleItem(ModItems.MALEVOLENT_MULTITOOL);
        simpleItem(ModItems.VAPORWAVE_CASSETTE);

        evenSimplerBlockItem(ModBlocks.RIVETED_VESSELPLATE);
        evenSimplerBlockItem(ModBlocks.GRAY_ROCKRETE_STAIRS);
        evenSimplerBlockItem(ModBlocks.GRAY_ROCKRETE_SLAB);

        evenSimplerBlockItem(ModBlocks.STRUT_STAIRS);
        evenSimplerBlockItem(ModBlocks.STRUT_SLAB);
        evenSimplerBlockItem(ModBlocks.CATWALK_STRUT_STAIRS);
        evenSimplerBlockItem(ModBlocks.CATWALK_STRUT_SLAB);

        evenSimplerBlockItem(ModBlocks.GRAY_STRUT_STAIRS);
        evenSimplerBlockItem(ModBlocks.GRAY_STRUT_SLAB);
        evenSimplerBlockItem(ModBlocks.GRAY_CATWALK_STRUT_STAIRS);
        evenSimplerBlockItem(ModBlocks.GRAY_CATWALK_STRUT_SLAB);

    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(IndustrialHellscape.MOD_ID,"item/" + item.getId().getPath()));
    }
    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(IndustrialHellscape.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }
}