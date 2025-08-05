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


        SimpleBlockItem(ModBlocks.GRAY_VESSELPLATE);
        SimpleBlockItem(ModBlocks.GRAY_VESSELPLATE_GRATE);
        SimpleBlockItem(ModBlocks.GRAY_RIVETED_VESSELPLATE);
        SimpleBlockItem(ModBlocks.GRAY_HORIZONTAL_RIVETED_VESSELPLATE);
        SimpleBlockItem(ModBlocks.GRAY_VERTICAL_RIVETED_VESSELPLATE);
        SimpleBlockItem(ModBlocks.SMOOTH_GRAY_VESSELPLATE_TILE);
        SimpleBlockItem(ModBlocks.SMOOTH_GRAY_VESSELPLATE);

        SimpleBlockItem(ModBlocks.RIVETED_VESSELPLATE);
        SimpleBlockItem(ModBlocks.GRAY_ROCKRETE_STAIRS);
        SimpleBlockItem(ModBlocks.GRAY_ROCKRETE_SLAB);
        SimpleBlockItem(ModBlocks.PIPEWORKS);

        SimpleBlockItem(ModBlocks.STRUT_STAIRS);
        SimpleBlockItem(ModBlocks.STRUT_SLAB);
        SimpleBlockItem(ModBlocks.CATWALK_STRUT_STAIRS);
        SimpleBlockItem(ModBlocks.CATWALK_STRUT_SLAB);

        SimpleBlockItem(ModBlocks.GRAY_STRUT_STAIRS);
        SimpleBlockItem(ModBlocks.GRAY_STRUT_SLAB);
        SimpleBlockItem(ModBlocks.GRAY_CATWALK_STRUT_STAIRS);
        SimpleBlockItem(ModBlocks.GRAY_CATWALK_STRUT_SLAB);

        SimpleBlockItem(ModBlocks.RETRO_COMPUTER);
        SimpleBlockItem(ModBlocks.CASSETTE_PLAYER);
        SimpleBlockItem(ModBlocks.SINK);
        SimpleBlockItem(ModBlocks.YELLOW_TRIPOD);
        SimpleBlockItem(ModBlocks.WORK_LIGHT_MOUNT);


    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(IndustrialHellscape.MOD_ID,"item/" + item.getId().getPath()));
    }
    public void SimpleBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(IndustrialHellscape.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }
}