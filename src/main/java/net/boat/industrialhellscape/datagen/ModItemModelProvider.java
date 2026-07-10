package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Objects;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, IndustrialHellscape.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //Make item models from existing .png files
        //  Item
        basicItem(ModItems.JOB_APPLICATION.get());
        basicItem(ModItems.TERMINATION_LETTER.get());

        basicItem(ModItems.GAS_STATION_PILL.get());
        wallItem(ModBlocks.GRAY_ROCKRETE_WALL, ModBlocks.ROUGH_GRAY_ROCKRETE);
        wallItem(ModBlocks.RED_ROCKRETE_WALL, ModBlocks.ROUGH_RED_ROCKRETE);
        wallItem(ModBlocks.YELLOW_ROCKRETE_WALL, ModBlocks.ROUGH_YELLOW_ROCKRETE);
        wallItem(ModBlocks.BLUE_ROCKRETE_WALL, ModBlocks.ROUGH_BLUE_ROCKRETE);
        wallItem(ModBlocks.GREEN_ROCKRETE_WALL, ModBlocks.ROUGH_GREEN_ROCKRETE);

        //  Blocks
        basicFolderedItem(ModBlocks.ARMORED_DOOR.get().asItem(), "door");
        basicFolderedItem(ModBlocks.STAMPED_METAL_DOOR.get().asItem(), "door");
        basicFolderedItem(ModBlocks.BULKHEAD_DOOR.get().asItem(), "door");
        basicFolderedItem(ModItems.MARQUEE_DISC.get().asItem(), "disc");

    }
    public ItemModelBuilder basicFolderedItem(Item item, String subFolder) {
        return this.folderedItemFinder(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)), subFolder);
    }
    public ItemModelBuilder folderedItemFinder(ResourceLocation item, String subFolder) {
        return this.getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "item/" + subFolder + "/" + item.getPath()));
    }

    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID,
                        "block/" + baseBlock.getId().getPath()));
    }
}