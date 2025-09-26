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

        //Make item models from Texture
        makeItemModel(ModItems.FLOPPY_DISK);
        makeItemModel(ModItems.FLOPPY_DISKETTE);
        makeItemModel(ModItems.VAPORWAVE_CASSETTE);
        makeItemModel(ModItems.CASSETTE_V2);
    }

    private ItemModelBuilder makeItemModel(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(IndustrialHellscape.MOD_ID,"item/" + item.getId().getPath()));
    }
    public void makeBlockitemModel(RegistryObject<Block> block) {
        this.withExistingParent(IndustrialHellscape.MOD_ID + ":item/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }
    public void makeFolderedBlockitemModel(RegistryObject<Block> block, String folderName) {
        this.withExistingParent(
                IndustrialHellscape.MOD_ID + ":item/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), //output path
                modLoc("block/" + folderName + "/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath())    //parent model path
        );
    }
}