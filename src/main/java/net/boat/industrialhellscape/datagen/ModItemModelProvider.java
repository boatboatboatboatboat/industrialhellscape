package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, IndustrialHellscape.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //Make item models from existing .png files
        //--- Items ---
        basicItem(ModItems.JOB_APPLICATION.get());
        basicItem(ModItems.TERMINATION_LETTER.get());
        basicItem(ModItems.GAS_STATION_PILL.get());

        basicFolderedItem(ModItems.MARQUEE_DISC.asItem(), "disc");

        //  Wall Blocks (have a dedicated vanilla 3D item model)
//        wallItem(ModBlocks.GRAY_ROCKRETE_WALL, ModBlocks.ROUGH_GRAY_ROCKRETE.get(), "rough_rockrete");
//        wallItem(ModBlocks.RED_ROCKRETE_WALL, ModBlocks.ROUGH_RED_ROCKRETE.get(), "rough_rockrete");
//        wallItem(ModBlocks.YELLOW_ROCKRETE_WALL, ModBlocks.ROUGH_YELLOW_ROCKRETE.get(), "rough_rockrete");
//        wallItem(ModBlocks.BLUE_ROCKRETE_WALL, ModBlocks.ROUGH_BLUE_ROCKRETE.get(), "rough_rockrete");
//        wallItem(ModBlocks.GREEN_ROCKRETE_WALL, ModBlocks.ROUGH_GREEN_ROCKRETE.get(), "rough_rockrete");

        //--- Blocks with 2D Item Sprites ---

        // Misc/Easter Eggs

        // Doors
        basicFolderedItem(ModBlocks.ARMORED_DOOR.asItem(), "door");
        basicFolderedItem(ModBlocks.STAMPED_METAL_DOOR.asItem(), "door");
        basicFolderedItem(ModBlocks.BULKHEAD_DOOR.asItem(), "door");

        //Fences
        basicFolderedItem(ModBlocks.BARBED_WIRE_FENCE.asItem(), "fence");
        basicFolderedItem(ModBlocks.CHAIN_LINK_FENCE.asItem(), "fence");

        //Fence Tops
        basicFolderedItem(ModBlocks.ANGLED_BARBED_WIRE_ARM.asItem(), "fence");
        basicFolderedItem(ModBlocks.VERTICAL_BARBED_WIRE_ARM.asItem(), "fence");
        basicFolderedItem(ModBlocks.COILED_BARBED_WIRE_ARM.asItem(), "fence");

        /*
        - MultiBlocks (fully constructed blocks cannot be rendered in GUIs without a separate model
            fully assembling every part together
        - Use a separate 16x16 texture in the items/multiblock path to avoid redundancy and save RAM/storage
        */

        //Body Pillows
        basicFolderedItem(ModBlocks.BODY_PILLOW_OZY.get().asItem(), "multiblock");
        basicFolderedItem(ModBlocks.BODY_PILLOW_FANG.get().asItem(), "multiblock");
        basicFolderedItem(ModBlocks.BODY_PILLOW_PROV.get().asItem(), "multiblock");

        // Misc/Easter Eggs
        basicItem(ModBlocks.PEANUT_STATUE.asItem());
    }
    public ItemModelBuilder basicFolderedItem(Item item, String subFolder) {
        return this.folderedItemFinder(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)), subFolder);
    }
    public ItemModelBuilder folderedItemFinder(ResourceLocation item, String subFolder) {
        return this.getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "item/" + subFolder + "/" + item.getPath()));
    }
}