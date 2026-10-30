package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
/*
    Block Tags for main block types uses lists of stone-cutting recipe outputs found and used in ModRecipeProvider class

    - For "normal" blocks; update block tags through the appropriate recipe list in ModRecipeProvider class

    ModBlockTagProvider:
    - Blocks with connecting logic with block variants (Desks):
            update appropriate block-set family Block Tag HERE.
    - Blocks with Storage Block Entity & Item Storage Capability (Lockers):
            update STORAGE_INVENTORY_BLOCKS, and MOD_CREATE_SIMPLE_MOUNTED_STORAGE Block Tags HERE
    - Blocks with "cutout" appearance that read as "vents" for air pass-thru (Vents):
            update MOD_CREATE_FAN_TRANSPARENT Block Tag HERE
    - Multiple One-off Joke Blocks (Body-Pillows):
        update appropriate Block Tag HERE
    - Experimental Blocks:
            update mining tool Type, and mining tool Tier Block Tags here
*/

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, IndustrialHellscape.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        //----- MAIN BLOCKS -----

        //--- Main
        this.tag(ModTags.Blocks.VESSELPLATE_BLOCKS).add(ModRecipeProvider.VESSELPLATE_STONECUT_OUTPUT.toArray(new Block[0]));
        this.tag(ModTags.Blocks.ROCKRETE_BLOCKS).add(ModRecipeProvider.ROCKRETE_STONECUT_OUTPUT.toArray(new Block[0]));
        this.tag(ModTags.Blocks.VESSELGLASS_BLOCKS).add(ModRecipeProvider.VESSELGLASS_STONECUT_OUTPUT.toArray(new Block[0]));

        //--- Structural
        this.tag(ModTags.Blocks.HVAC_BLOCKS).add(ModRecipeProvider.HVAC_STONECUT_OUTPUT.toArray(new Block[0]));
        this.tag(ModTags.Blocks.TRUSS_BLOCKS).add(ModRecipeProvider.TRUSS_STONECUT_OUTPUT.toArray(new Block[0]));

        //--- Doors
        this.tag(ModTags.Blocks.DOOR_BLOCKS).add(ModRecipeProvider.DOORS_STONECUT_OUTPUT.toArray(new Block[0]));
        this.tag(ModTags.Blocks.TRAPDOOR_BLOCKS).add(ModRecipeProvider.TRUSS_STONECUT_OUTPUT.toArray(new Block[0]));

        //--- Works
        this.tag(ModTags.Blocks.METALWORKS_BLOCKS).add(ModRecipeProvider.METALWORKS_STONECUT_OUTPUT.toArray(new Block[0]));
        this.tag(ModTags.Blocks.PIPEWORKS_BLOCKS).add(ModRecipeProvider.PIPEWORKS_STONECUT_OUTPUT.toArray(new Block[0]));

        //----- END OF MAIN BLOCKS -----

        //----- FURNITURE BLOCKS -----
        this.tag(ModTags.Blocks.ALL_FURNITURE_BLOCKS)
                .addTags(
                        ModTags.Blocks.AMENITY_FURNITURE_CATEGORY,
                        ModTags.Blocks.HYGIENE_FURNITURE_CATEGORY,
                        ModTags.Blocks.INDUSTRIAL_FURNITURE_CATEGORY,
                        ModTags.Blocks.TECHNOLOGY_FURNITURE_CATEGORY,
                        ModTags.Blocks.SAFETY_FURNITURE_CATEGORY
                )
                .add(
                        ModBlocks.IHEA_FURNITURE_KIT.get()
                );
        //--- Furniture Categories; Block Contents for each
        this.tag(ModTags.Blocks.FURNITURE_CATEGORY_BLOCKS).add(ModRecipeProvider.FURNITURE_CATEGORY_STONECUT_OUTPUT.toArray(new Block[0]));
        this.tag(ModTags.Blocks.SAFETY_FURNITURE_CATEGORY).add(ModRecipeProvider.SAFETY_FURNITURE_STONECUT_OUTPUT.toArray(new Block[0]));
        this.tag(ModTags.Blocks.HYGIENE_FURNITURE_CATEGORY).add(ModRecipeProvider.HYGIENE_FURNITURE_STONECUT_OUTPUT.toArray(new Block[0]));
        this.tag(ModTags.Blocks.INDUSTRIAL_FURNITURE_CATEGORY).add(ModRecipeProvider.INDUSTRIAL_FURNITURE_STONECUT_OUTPUT.toArray(new Block[0]));
        this.tag(ModTags.Blocks.TECHNOLOGY_FURNITURE_CATEGORY).add(ModRecipeProvider.TECHNOLOGY_FURNITURE_STONECUT_OUTPUT.toArray(new Block[0]));
        this.tag(ModTags.Blocks.AMENITY_FURNITURE_CATEGORY).add(ModRecipeProvider.AMENITY_FURNITURE_STONECUT_OUTPUT.toArray(new Block[0]));
        //----- END OF FURNITURE BLOCKS -----

        //----- BODY PILLOWS -----
        this.tag(ModTags.Blocks.BODY_PILLOW_BLOCK)
                .add(
                        ModBlocks.BODY_PILLOW_OZY.get(),
                        ModBlocks.BODY_PILLOW_FANG.get(),
                        ModBlocks.BODY_PILLOW_PROV.get()
                );
        //----- END OF BODY PILLOWS -----

        //----- BLOCK SET FAMILIES -----
        this.tag(ModTags.Blocks.CLASSIC_DESK)
                .add(
                        ModBlocks.DESK.get(),
                        ModBlocks.DESK_DRAWER.get()
                );
        this.tag(ModTags.Blocks.METAL_DESK)
                .add(
                        ModBlocks.METAL_DESK.get(),
                        ModBlocks.METAL_DESK_DRAWER.get(),
                        ModBlocks.METAL_DESK_DRAWER_2.get()
                );
        this.tag(ModTags.Blocks.OFFICE_DESK)
                .add(
                        ModBlocks.OFFICE_DESK.get(),
                        ModBlocks.OFFICE_DESK_DRAWER.get()
                );
        this.tag(ModTags.Blocks.COPPER_PIPE_CONDUIT)
                .add(
                        ModBlocks.COPPER_PIPE_CONDUIT.get(),
                        ModBlocks.COPPER_PIPE_CONDUIT_PLANAR_CORNER.get(),
                        ModBlocks.COPPER_PIPE_CONDUIT_INNER_CORNER.get(),
                        ModBlocks.COPPER_PIPE_CONDUIT_OUTER_CORNER.get()
                );
        this.tag(ModTags.Blocks.BRASS_PIPE_CONDUIT)
                .add(
                        ModBlocks.BRASS_PIPE_CONDUIT.get(),
                        ModBlocks.BRASS_PIPE_CONDUIT_PLANAR_CORNER.get(),
                        ModBlocks.BRASS_PIPE_CONDUIT_INNER_CORNER.get(),
                        ModBlocks.BRASS_PIPE_CONDUIT_OUTER_CORNER.get()
                );
        this.tag(ModTags.Blocks.GRAY_PIPE_CONDUIT)
                .add(
                        ModBlocks.GRAY_PIPE_CONDUIT.get(),
                        ModBlocks.GRAY_PIPE_CONDUIT_PLANAR_CORNER.get(),
                        ModBlocks.GRAY_PIPE_CONDUIT_INNER_CORNER.get(),
                        ModBlocks.GRAY_PIPE_CONDUIT_OUTER_CORNER.get()
                );
        //----- END OF BLOCK SET FAMILIES -----

        //----- TRACKING BLOCKS WITH STORAGE BLOCK ENTITIES -----
        this.tag(ModTags.Blocks.STORAGE_INVENTORY_BLOCKS)
                .add(
                        ModBlocks.OFFICE_DESK_DRAWER.get(),
                        ModBlocks.DESK_DRAWER.get(),
                        ModBlocks.METAL_DESK_DRAWER.get(),
                        ModBlocks.METAL_DESK_DRAWER_2.get(),
                        ModBlocks.LOCKER_BOX.get(),
                        ModBlocks.LARGE_LOCKER.get(),
                        ModBlocks.FUEL_DRUM.get(),
                        ModBlocks.DEBUG_BLOCK.get()
                );
        //----- END OF BLOCKS WITH STORAGE BLOCK ENTITIES -----

        //----- VANILLA TAGS------
        this.tag(BlockTags.WALLS)
                .add(
                        /*
                        Wall and Fence-type blocks require this vanilla tag
                        in order to connect properly

                        Walls and Fences use DIFFERENT TAGS
                        */
                        ModBlocks.GRAY_ROCKRETE_WALL.get(),
                        ModBlocks.RED_ROCKRETE_WALL.get(),
                        ModBlocks.YELLOW_ROCKRETE_WALL.get(),
                        ModBlocks.BLUE_ROCKRETE_WALL.get(),
                        ModBlocks.GRAY_ROCKRETE_WALL.get(),
                        ModBlocks.GREEN_ROCKRETE_WALL.get()
                );

        this.tag(BlockTags.FENCES)
                .add(
                        /*
                        Wall and Fence-type blocks require this vanilla tag
                        in order to connect properly

                        Walls and Fences use DIFFERENT TAGS
                        */
                        ModBlocks.BARBED_WIRE_FENCE.get(),
                        ModBlocks.CHAIN_LINK_FENCE.get(),

                        ModBlocks.ANGLED_BARBED_WIRE_ARM.get(),
                        ModBlocks.VERTICAL_BARBED_WIRE_ARM.get(),
                        ModBlocks.COILED_BARBED_WIRE_ARM.get()
                );

        this.tag(BlockTags.NEEDS_STONE_TOOL) //When specific tool tiers are required
                .addTags(
                        ModTags.Blocks.VESSELPLATE_BLOCKS,
                        ModTags.Blocks.ROCKRETE_BLOCKS,

                        ModTags.Blocks.TRUSS_BLOCKS,
                        ModTags.Blocks.HVAC_BLOCKS,

                        ModTags.Blocks.PIPEWORKS_BLOCKS,
                        ModTags.Blocks.METALWORKS_BLOCKS,

                        ModTags.Blocks.DOOR_BLOCKS,
                        ModTags.Blocks.TRAPDOOR_BLOCKS,

                        ModTags.Blocks.ALL_FURNITURE_BLOCKS
                )
                .add(
                        ModBlocks.DEBUG_BLOCK.get()
                        //ModBlocks.LAND_MINE.get()
                );
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTags(
                        ModTags.Blocks.ROCKRETE_BLOCKS,
                        ModTags.Blocks.VESSELPLATE_BLOCKS,
                        ModTags.Blocks.VESSELGLASS_BLOCKS,

                        ModTags.Blocks.TRUSS_BLOCKS,
                        ModTags.Blocks.HVAC_BLOCKS,

                        ModTags.Blocks.PIPEWORKS_BLOCKS,
                        ModTags.Blocks.METALWORKS_BLOCKS,

                        ModTags.Blocks.DOOR_BLOCKS,
                        ModTags.Blocks.TRAPDOOR_BLOCKS,

                        ModTags.Blocks.ALL_FURNITURE_BLOCKS //All furniture can be mined with pickaxe.
                )
                .add(
                        ModBlocks.DEBUG_BLOCK.get(),
                        ModBlocks.PEANUT_STATUE.get()
                        //ModBlocks.LAND_MINE.get()
                );
        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .addTags(
                        ModTags.Blocks.ALL_FURNITURE_BLOCKS //All furniture can be mined with axe.
                );
        //----- END OF VANILLA TAGS -------

        //----- MOD COMPAT -----
        this.tag(ModTags.Blocks.MOD_CREATE_FAN_TRANSPARENT)
                .addTags(
                        ModTags.Blocks.TRUSS_BLOCKS
                )
                .add(
                        ModBlocks.HORIZONTAL_CUTOUT_VENT.get(),
                        ModBlocks.RUSTY_HORIZONTAL_CUTOUT_VENT.get(),
                        ModBlocks.VERTICAL_CUTOUT_VENT.get(),
                        ModBlocks.RUSTY_VERTICAL_CUTOUT_VENT.get()
                );

        this.tag(ModTags.Blocks.MOD_CREATE_SIMPLE_MOUNTED_STORAGE)
                .addTags(
                        ModTags.Blocks.STORAGE_INVENTORY_BLOCKS
                );

        this.tag(ModTags.Blocks.MOD_SABLE_MASS_SUPER_LIGHT)
                .addTags(
                  ModTags.Blocks.BODY_PILLOW_BLOCK
                );
        this.tag(ModTags.Blocks.MOD_SABLE_MASS_LIGHT)
                .addTags(
                        ModTags.Blocks.METALWORKS_BLOCKS,
                        ModTags.Blocks.PIPEWORKS_BLOCKS,

                        ModTags.Blocks.HVAC_BLOCKS,
                        ModTags.Blocks.TRUSS_BLOCKS,

                        ModTags.Blocks.DOOR_BLOCKS,
                        ModTags.Blocks.TRAPDOOR_BLOCKS,

                        ModTags.Blocks.ALL_FURNITURE_BLOCKS
                );
        this.tag(ModTags.Blocks.MOD_SABLE_MASS_NORMAL)
                .addTags(
                        ModTags.Blocks.VESSELPLATE_BLOCKS,
                        ModTags.Blocks.VESSELGLASS_BLOCKS
                );
        this.tag(ModTags.Blocks.MOD_SABLE_MASS_HEAVY)
                .addTags(
                        ModTags.Blocks.ROCKRETE_BLOCKS
                        );

        this.tag(ModTags.Blocks.MOD_SABLE_VOLUME_HALF)
                .addTags(
                        ModTags.Blocks.ALL_FURNITURE_BLOCKS
                );
        this.tag(ModTags.Blocks.MOD_SABLE_VOLUME_QUARTER)
                .addTags(
                        ModTags.Blocks.METALWORKS_BLOCKS,
                        ModTags.Blocks.PIPEWORKS_BLOCKS,
                        ModTags.Blocks.DOOR_BLOCKS,
                        ModTags.Blocks.TRAPDOOR_BLOCKS,
                        ModTags.Blocks.TRUSS_BLOCKS
                );
    }
    //----- END OF MOD COMPAT -----
}
