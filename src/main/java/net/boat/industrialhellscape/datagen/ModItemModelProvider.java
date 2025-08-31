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

        //Purely Items
        makeItemModel(ModItems.FLOPPY_DISK);
        makeItemModel(ModItems.FLOPPY_DISKETTE);
        makeItemModel(ModItems.VAPORWAVE_CASSETTE);

        //makeBlockitemModel(ModBlocks.MULTIBLOCK_DEBUG);
        makeFolderedBlockitemModel(ModBlocks.HANDRAIL,"obj_models");


        makeBlockitemModel(ModBlocks.PIPEWORKS);

        makeFolderedBlockitemModel(ModBlocks.STRUT,"strut");
        makeFolderedBlockitemModel(ModBlocks.STRUT_STAIRS,"strut");
        makeFolderedBlockitemModel(ModBlocks.STRUT_SLAB,"strut");
        makeFolderedBlockitemModel(ModBlocks.CATWALK_STRUT,"strut");
        makeFolderedBlockitemModel(ModBlocks.CATWALK_STRUT_STAIRS,"strut");
        makeFolderedBlockitemModel(ModBlocks.CATWALK_STRUT_SLAB,"strut");

        makeFolderedBlockitemModel(ModBlocks.GRAY_STRUT,"strut");
        makeFolderedBlockitemModel(ModBlocks.GRAY_STRUT_STAIRS,"strut");
        makeFolderedBlockitemModel(ModBlocks.GRAY_STRUT_SLAB,"strut");
        makeFolderedBlockitemModel(ModBlocks.GRAY_CATWALK_STRUT,"strut");
        makeFolderedBlockitemModel(ModBlocks.GRAY_CATWALK_STRUT_STAIRS,"strut");
        makeFolderedBlockitemModel(ModBlocks.GRAY_CATWALK_STRUT_SLAB,"strut");

        makeFolderedBlockitemModel(ModBlocks.VESSELGLASS, "vesselglass");
        makeFolderedBlockitemModel(ModBlocks.REINFORCED_VESSELGLASS, "vesselglass");
        makeFolderedBlockitemModel(ModBlocks.GRAY_VESSELGLASS, "vesselglass");
        makeFolderedBlockitemModel(ModBlocks.GRAY_REINFORCED_VESSELGLASS, "vesselglass");

        makeBlockitemModel(ModBlocks.IHEA_FURNITURE_KIT);
        makeBlockitemModel(ModBlocks.SAFETY_FURNISHINGS);
        makeBlockitemModel(ModBlocks.HYGIENE_FURNISHINGS);
        makeBlockitemModel(ModBlocks.INDUSTRIAL_FURNISHINGS);
        makeBlockitemModel(ModBlocks.TECHNOLOGY_FURNISHINGS);
        makeBlockitemModel(ModBlocks.AMENITY_FURNISHINGS);

        makeBlockitemModel(ModBlocks.BLUE_ROCKRETE_SLAB);
        makeBlockitemModel(ModBlocks.GRAY_ROCKRETE_SLAB);
        makeBlockitemModel(ModBlocks.GREEN_ROCKRETE_SLAB);
        makeBlockitemModel(ModBlocks.YELLOW_ROCKRETE_SLAB);
        makeBlockitemModel(ModBlocks.BLUE_ROCKRETE_STAIRS);
        makeBlockitemModel(ModBlocks.GRAY_ROCKRETE_STAIRS);
        makeBlockitemModel(ModBlocks.GREEN_ROCKRETE_STAIRS);
        makeBlockitemModel(ModBlocks.YELLOW_ROCKRETE_STAIRS);



        //Furniture Item Models
        makeFolderedBlockitemModel(ModBlocks.LOCKER_BOX, "locker");
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