package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.modded_block_classes.RailingBlocks.ParapetBlock;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }
    //  Increasing from the default of 1 requires disabling Vesselplate, Rockrete, Pipeworks, and/or Vesselglass recycling
    private static final int vesselplatePerIronIngot = 1; //accepts only iron ingots
    private static final int rockretePerStone = 1; //stone 1:1, and stone is already plenty.

    private static final int trussPerIngot = 4;
    private static final int hvacPerIngot = 4;

    private static final int vesselglassPerCraft = 4;

    private static final int pipeworksPerCraft = 16;
    private static final int metalworksPerCraft = 16;

    private static final int furnitureKitPerCraft = 8;

    private static final boolean doVesselplateCookRecycle = (vesselplatePerIronIngot == 1);
    private static final boolean doRockreteCookRecycle = (rockretePerStone == 1);

    private static final boolean doTrussCookRecycle = false;
    private static final boolean doHvacCookRecycle = false;

    private static final boolean doVesselglassCookRecycle = false;

    private static final boolean doPipeworksCookRecycle = false;
    private static final boolean doMetalworksCookRecycle = false;

    private static final List<ItemLike> HVAC_STONECUT_OUTPUT = List.of(
            ModBlocks.DUCT.get().asItem(),
            ModBlocks.RUSTY_DUCT.get().asItem(),
            ModBlocks.HORIZONTAL_VENT.get().asItem(),
            ModBlocks.HORIZONTAL_CUTOUT_VENT.get().asItem(),
            ModBlocks.RUSTY_HORIZONTAL_VENT.get().asItem(),
            ModBlocks.RUSTY_HORIZONTAL_CUTOUT_VENT.get().asItem()
    );
    private static final List<ItemLike> TRUSS_STONECUT_OUTPUT = List.of(
            ModBlocks.TRUSS.get().asItem(),
            ModBlocks.TRUSS_STAIRS.get().asItem(),
            ModBlocks.TRUSS_SLAB.get().asItem(),

            ModBlocks.CATWALK_TRUSS.get().asItem(),
            ModBlocks.CATWALK_TRUSS_SLAB.get().asItem(),
            ModBlocks.CATWALK_TRUSS_STAIRS.get().asItem(),

            ModBlocks.GRAY_TRUSS.get().asItem(),
            ModBlocks.GRAY_TRUSS_STAIRS.get().asItem(),
            ModBlocks.GRAY_TRUSS_SLAB.get().asItem(),

            ModBlocks.GRAY_CATWALK_TRUSS.get().asItem(),
            ModBlocks.GRAY_CATWALK_TRUSS_SLAB.get().asItem(),
            ModBlocks.GRAY_CATWALK_TRUSS_STAIRS.get().asItem(),

            ModBlocks.RUSTY_TRUSS.get().asItem(),
            ModBlocks.RUSTY_TRUSS_STAIRS.get().asItem(),
            ModBlocks.RUSTY_TRUSS_SLAB.get().asItem(),

            ModBlocks.RUSTY_CATWALK_TRUSS.get().asItem(),
            ModBlocks.RUSTY_CATWALK_TRUSS_STAIRS.get().asItem(),
            ModBlocks.RUSTY_CATWALK_TRUSS_SLAB.get().asItem()
    );

    private static final List<ItemLike> VESSELPLATE_STONECUT_OUTPUT = List.of(
            ModBlocks.RIVETED_VESSELPLATE.get().asItem(),
            ModBlocks.HORIZONTAL_VESSELPLATE.get().asItem(),
            ModBlocks.VESSELPLATE_PILLAR.get().asItem(),
            ModBlocks.SMOOTH_VESSELPLATE.get().asItem(),
            ModBlocks.HORIZONTAL_REINFORCED_VESSELPLATE.get().asItem(),

            ModBlocks.GRAY_RIVETED_VESSELPLATE.get().asItem(),
            ModBlocks.SMOOTH_GRAY_VESSELPLATE.get().asItem(),
            ModBlocks.GRAY_VESSELPLATE_PILLAR.get().asItem(),
            ModBlocks.GRAY_HORIZONTAL_VESSELPLATE.get().asItem(),

            ModBlocks.SMOOTH_VESSELPLATE_SLAB.get().asItem(),
            ModBlocks.SMOOTH_VESSELPLATE_STAIRS.get().asItem(),
            ModBlocks.SMOOTH_GRAY_VESSELPLATE_SLAB.get().asItem(),
            ModBlocks.SMOOTH_GRAY_VESSELPLATE_STAIRS.get().asItem(),

            ModBlocks.RIVETED_VESSELPLATE_STAIRS.get().asItem(),
            ModBlocks.RIVETED_VESSELPLATE_SLAB.get().asItem(),
            ModBlocks.GRAY_RIVETED_VESSELPLATE_STAIRS.get().asItem(),
            ModBlocks.GRAY_RIVETED_VESSELPLATE_SLAB.get().asItem(),

            ModBlocks.RUSTY_HORIZONTAL_VESSELPLATE.get().asItem(),
            ModBlocks.RUSTY_VERTICAL_VESSELPLATE.get().asItem(),
            ModBlocks.RUSTY_HORIZONTAL_REINFORCED_VESSELPLATE.get().asItem(),
            ModBlocks.RUSTY_VERTICAL_REINFORCED_VESSELPLATE.get().asItem(),
            ModBlocks.RUSTY_VESSELPLATE_PILLAR.get().asItem(),

            ModBlocks.SMOOTH_RUSTY_VESSELPLATE.get().asItem(),
            ModBlocks.SMOOTH_RUSTY_VESSELPLATE_SLAB.get().asItem(),
            ModBlocks.SMOOTH_RUSTY_VESSELPLATE_STAIRS.get().asItem(),

            ModBlocks.RUSTY_RIVETED_VESSELPLATE.get().asItem(),
            ModBlocks.RUSTY_RIVETED_VESSELPLATE_SLAB.get().asItem()
    );
    private static final List<ItemLike> ROCKRETE_STONECUT_OUTPUT = List.of(

            ModBlocks.ROUGH_GRAY_ROCKRETE.get().asItem(),
            ModBlocks.ROUGH_GRAY_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.ROUGH_GRAY_ROCKRETE_STAIRS.get().asItem(),
            ModBlocks.ROUGH_GREEN_ROCKRETE.get().asItem(),
            ModBlocks.ROUGH_GREEN_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.ROUGH_GREEN_ROCKRETE_STAIRS.get().asItem(),
            ModBlocks.ROUGH_YELLOW_ROCKRETE.get().asItem(),
            ModBlocks.ROUGH_YELLOW_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.ROUGH_YELLOW_ROCKRETE_STAIRS.get().asItem(),
            ModBlocks.ROUGH_BLUE_ROCKRETE.get().asItem(),
            ModBlocks.ROUGH_BLUE_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.ROUGH_BLUE_ROCKRETE_STAIRS.get().asItem(),
            ModBlocks.ROUGH_RED_ROCKRETE.get().asItem(),
            ModBlocks.ROUGH_RED_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.ROUGH_RED_ROCKRETE_STAIRS.get().asItem(),

//            ModBlocks.WEATHERED_GRAY_ROCKRETE.get().asItem(),
//            ModBlocks.WEATHERED_GRAY_ROCKRETE_SLAB.get().asItem(),
//            ModBlocks.WEATHERED_GRAY_ROCKRETE_STAIRS.get().asItem(),
//            ModBlocks.WEATHERED_GREEN_ROCKRETE.get().asItem(),
//            ModBlocks.WEATHERED_GREEN_ROCKRETE_SLAB.get().asItem(),
//            ModBlocks.WEATHERED_GREEN_ROCKRETE_STAIRS.get().asItem(),
//            ModBlocks.WEATHERED_YELLOW_ROCKRETE.get().asItem(),
//            ModBlocks.WEATHERED_YELLOW_ROCKRETE_SLAB.get().asItem(),
//            ModBlocks.WEATHERED_YELLOW_ROCKRETE_STAIRS.get().asItem(),
//            ModBlocks.WEATHERED_BLUE_ROCKRETE.get().asItem(),
//            ModBlocks.WEATHERED_BLUE_ROCKRETE_SLAB.get().asItem(),
//            ModBlocks.WEATHERED_BLUE_ROCKRETE_STAIRS.get().asItem(),
//            ModBlocks.WEATHERED_RED_ROCKRETE.get().asItem(),
//            ModBlocks.WEATHERED_RED_ROCKRETE_SLAB.get().asItem(),
//            ModBlocks.WEATHERED_RED_ROCKRETE_STAIRS.get().asItem(),

            ModBlocks.GRAY_ROCKRETE.get().asItem(),
            ModBlocks.GRAY_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.GRAY_ROCKRETE_STAIRS.get().asItem(),
            ModBlocks.GRAY_ROCKRETE_PILLAR.get().asItem(),
            ModBlocks.GRAY_ROCKRETE_PARAPET.get().asItem(),

            ModBlocks.GREEN_ROCKRETE.get().asItem(),
            ModBlocks.GREEN_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.GREEN_ROCKRETE_STAIRS.get().asItem(),
            ModBlocks.GREEN_ROCKRETE_PILLAR.get().asItem(),
            ModBlocks.GREEN_ROCKRETE_PARAPET.get().asItem(),

            ModBlocks.YELLOW_ROCKRETE.get().asItem(),

            ModBlocks.YELLOW_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.YELLOW_ROCKRETE_STAIRS.get().asItem(),

            ModBlocks.YELLOW_ROCKRETE_PILLAR.get().asItem(),
            ModBlocks.YELLOW_ROCKRETE_PARAPET.get().asItem(),

            ModBlocks.BLUE_ROCKRETE.get().asItem(),

            ModBlocks.BLUE_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.BLUE_ROCKRETE_STAIRS.get().asItem(),
            ModBlocks.BLUE_ROCKRETE_PILLAR.get().asItem(),
            ModBlocks.BLUE_ROCKRETE_PARAPET.get().asItem(),

            ModBlocks.RED_ROCKRETE.get().asItem(),
            ModBlocks.RED_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.RED_ROCKRETE_STAIRS.get().asItem(),
            ModBlocks.RED_ROCKRETE_PILLAR.get().asItem(),
            ModBlocks.RED_ROCKRETE_PARAPET.get().asItem(),

            ModBlocks.GRAY_ROCKRETE_WALL.get().asItem(),
            ModBlocks.RED_ROCKRETE_WALL.get().asItem(),
            ModBlocks.YELLOW_ROCKRETE_WALL.get().asItem(),
            ModBlocks.BLUE_ROCKRETE_WALL.get().asItem(),
            ModBlocks.GREEN_ROCKRETE_WALL.get().asItem(),


            ModBlocks.GRIMY_RESTROOM_TILE.get().asItem()
    );
    private static final List<ItemLike> VESSELGLASS_STONECUT_OUTPUT = List.of(
            ModBlocks.VESSELGLASS.get().asItem(),
            ModBlocks.REINFORCED_VESSELGLASS.get().asItem(),
            ModBlocks.GRAY_REINFORCED_VESSELGLASS.get().asItem(),
            ModBlocks.GRAY_VESSELGLASS.get().asItem(),
            ModBlocks.RUSTY_REINFORCED_VESSELGLASS.get().asItem(),
            ModBlocks.RUSTY_VESSELGLASS.get().asItem()
    );

    private static final List<ItemLike> PIPEWORKS_STONECUT_OUTPUT = List.of(
            ModBlocks.PIPEWORKS.get().asItem(),
            ModBlocks.COPPER_PIPE_CONDUIT.get().asItem(),
            ModBlocks.COPPER_PIPE_CONDUIT_PLANAR_CORNER.get().asItem(),
            ModBlocks.COPPER_PIPE_CONDUIT_INNER_CORNER.get().asItem(),
            ModBlocks.COPPER_PIPE_CONDUIT_OUTER_CORNER.get().asItem(),
            ModBlocks.BRASS_PIPE_CONDUIT.get().asItem(),
            ModBlocks.BRASS_PIPE_CONDUIT_PLANAR_CORNER.get().asItem(),
            ModBlocks.BRASS_PIPE_CONDUIT_INNER_CORNER.get().asItem(),
            ModBlocks.BRASS_PIPE_CONDUIT_OUTER_CORNER.get().asItem(),
            ModBlocks.GRAY_PIPE_CONDUIT.get().asItem(),
            ModBlocks.GRAY_PIPE_CONDUIT_PLANAR_CORNER.get().asItem(),
            ModBlocks.GRAY_PIPE_CONDUIT_INNER_CORNER.get().asItem(),
            ModBlocks.GRAY_PIPE_CONDUIT_OUTER_CORNER.get().asItem()
    );

    private static final List<ItemLike> METALWORKS_STONECUT_OUTPUT = List.of(
            ModBlocks.METALWORKS.get().asItem(),
            ModBlocks.RUSTY_BOLTED_BRACKET.get().asItem(),
            ModBlocks.BLACK_BOLTED_BRACKET.get().asItem(),
            ModBlocks.GRAY_BOLTED_BRACKET.get().asItem(),
            ModBlocks.SMALL_GRAY_BOLTED_BRACKET.get().asItem(),
            ModBlocks.SMALL_BLACK_BOLTED_BRACKET.get().asItem(),
            ModBlocks.SMALL_RUSTY_BOLTED_BRACKET.get().asItem(),
            ModBlocks.YELLOW_RAILING.get().asItem(),
            ModBlocks.YELLOW_STAIR_RAILING.get().asItem(),
            ModBlocks.GRAY_RAILING.get().asItem(),
            ModBlocks.GRAY_STAIR_RAILING.get().asItem(),
            ModBlocks.BLACK_RAILING.get().asItem(),
            ModBlocks.BLACK_STAIR_RAILING.get().asItem(),
            ModBlocks.RUSTY_RAILING.get().asItem(),
            ModBlocks.RUSTY_STAIR_RAILING.get().asItem()
    );
    private static final List<ItemLike> DOORS_STONECUT_OUTPUT = List.of(
            ModBlocks.ARMORED_DOOR.get().asItem(),
            ModBlocks.STAMPED_METAL_DOOR.get().asItem(),
            ModBlocks.BULKHEAD_DOOR.get().asItem()
    );
    private static final List<ItemLike> TRAPDOORS_STONECUT_OUTPUT = List.of(
            //ModBlocks.VESSELPLATE_TRAPDOOR.get().asItem(),
            ModBlocks.VENT_TRAPDOOR.get().asItem(),
            ModBlocks.RUSTY_VENT_TRAPDOOR.get().asItem()
    );
    private static final List<ItemLike> FURNITURE_CATEGORIES = List.of(
            ModBlocks.SAFETY_FURNISHINGS.get().asItem(),
            ModBlocks.HYGIENE_FURNISHINGS.get().asItem(),
            ModBlocks.INDUSTRIAL_FURNISHINGS.get().asItem(),
            ModBlocks.TECHNOLOGY_FURNISHINGS.get().asItem(),
            ModBlocks.AMENITY_FURNISHINGS.get().asItem()
    );

    private static final List<ItemLike> SAFETY_FURNITURE = List.of(
            ModBlocks.RED_WALL_MEDKIT.get().asItem(),
            ModBlocks.WHITE_WALL_MEDKIT.get().asItem(),
            ModBlocks.FIRE_EXTINGUISHER.get().asItem(),
            ModBlocks.SMOKE_ALARM.get().asItem(),
            ModBlocks.OPERATING_TABLE.get().asItem(),
            ModBlocks.MEDICAL_BED.get().asItem(),
            ModBlocks.IV_DRIPSTAND.get().asItem(),
            ModBlocks.VITALS_MONITOR.get().asItem()
    );
    private static final List<ItemLike> HYGIENE_FURNITURE = List.of(
            ModBlocks.TOILET.get().asItem(),
            ModBlocks.SINK.get().asItem(),
            ModBlocks.URINAL.get().asItem()
    );
    private static final List<ItemLike> INDUSTRIAL_FURNITURE = List.of(
            ModBlocks.LOCKER_BOX.get().asItem(),
            ModBlocks.LARGE_LOCKER.get().asItem(),
            ModBlocks.WORK_LIGHT_STAND.get().asItem(),
            ModBlocks.FLOOR_WORK_LIGHT.get().asItem(),
            ModBlocks.FUEL_DRUM.get().asItem(),
            ModBlocks.INDUSTRIAL_LAMP.get(),
            ModBlocks.OBLONG_CAGE_LAMP.get()
    );
    private static final List<ItemLike> TECHNOLOGY_FURNITURE = List.of(
            ModBlocks.RETRO_COMPUTER.get().asItem(),
            ModBlocks.RETRO_COMPUTER_2.get().asItem(),
            ModBlocks.MONITOR_AND_KEYBOARD.get().asItem(),
            ModBlocks.DESKTOP_TOWER.get().asItem(),
            ModBlocks.CASSETTE_PLAYER.get().asItem(),
            ModBlocks.CCTV_CAMERA.get().asItem()
    );
    private static final List<ItemLike> AMENITY_FURNITURE = List.of(
            ModBlocks.DESK.get().asItem(),
            ModBlocks.DESK_DRAWER.get().asItem(),
            ModBlocks.METAL_DESK.get().asItem(),
            ModBlocks.METAL_DESK_DRAWER.get().asItem(),
            ModBlocks.METAL_DESK_DRAWER_2.get().asItem(),

            ModBlocks.OFFICE_DESK_DRAWER.get().asItem(),
            ModBlocks.OFFICE_DESK.get().asItem(),

            ModBlocks.OFFICE_CHAIR.get().asItem(),
            ModBlocks.BLACK_OFFICE_CHAIR.get().asItem(),
            ModBlocks.FOLDING_CHAIR.get().asItem()
    );


    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {

        //---------- RECIPES FOR BASE BLOCKS ----------
        //CREATE INHELL HAVEN DEVICE
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.INHELL_HAVEN_DEVICE.get())
                .pattern("ABC")
                .pattern("DDD")
                .pattern("ABC")

                .define('A', Items.IRON_INGOT)
                .define('B', Items.COPPER_INGOT)
                .define('C', Items.GOLD_INGOT)
                .define('D', Items.QUARTZ)

                .unlockedBy(getHasName(Items.QUARTZ), has(Items.QUARTZ))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", "haven_tool_from_crafting"));

        //Create 1x Vesselplate from 1x iron ingots
        HavenSingleIngredientRecipe(ModBlocks.HORIZONTAL_VESSELPLATE.get(), vesselplatePerIronIngot, Ingredient.of(Items.IRON_INGOT), "vesselplate_from_iron_ingot", recipeOutput);
        //Create 9x Vesselplate from 1x iron block
        HavenSingleIngredientRecipe(ModBlocks.HORIZONTAL_VESSELPLATE.get(), vesselplatePerIronIngot*9, Ingredient.of(Items.IRON_BLOCK), "vesselplate_from_iron_block", recipeOutput);
        //Create Rockrete Base Block from 1 stone
        HavenSingleIngredientRecipe(ModBlocks.ROUGH_GRAY_ROCKRETE.get(), rockretePerStone, Ingredient.of(ModTags.Items.IH_RECIPE_STONELIKES), "rockrete_from_stone", recipeOutput);

        //Create 1x Vesselglass Base Block from 1 iron ingot and 1 glass
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.VESSELGLASS.get(), vesselglassPerCraft)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Items.IRON_INGOT)
                .requires(Items.GLASS)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", "vesselglass_from_crafting"));

        //Create Grate recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DUCT.get(), hvacPerIngot *4)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")

                .define('A', Items.IRON_INGOT)
                .define('B', ModItems.INHELL_HAVEN_DEVICE.get())

                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", "grate_from_crafting"));

        //Create Truss recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TRUSS.get(), trussPerIngot *4)
                .pattern("A A")
                .pattern(" B ")
                .pattern("A A")

                .define('A', Items.IRON_INGOT)
                .define('B', ModItems.INHELL_HAVEN_DEVICE.get())

                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", "truss_from_crafting"));

        //Create Metalworks recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.METALWORKS.get(), metalworksPerCraft)
                .pattern("A A")
                .pattern("ABA")
                .pattern("A A")

                .define('A', Items.IRON_INGOT)
                .define('B', ModItems.INHELL_HAVEN_DEVICE.get())

                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", "metalworks_from_crafting"));
        //Create Pipeworks recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PIPEWORKS.get(), pipeworksPerCraft)
                .pattern("AAA")
                .pattern(" B ")
                .pattern("AAA")

                .define('A', Items.IRON_INGOT)
                .define('B', ModItems.INHELL_HAVEN_DEVICE.get())

                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", "pipeworks_from_crafting"));

        //Create Furniture Kit Base Block from 1 stonetype, one log (or 4 planks), one iron ingot, and the HAVEN device
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.IHEA_FURNITURE_KIT.get(), furnitureKitPerCraft)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Ingredient.of(ModTags.Items.IH_RECIPE_STONELIKES))
                .requires(Ingredient.of(ItemTags.LOGS))
                .requires(Ingredient.of(Items.IRON_INGOT))
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", "furniture_kit_from_crafting_log"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.IHEA_FURNITURE_KIT.get(), furnitureKitPerCraft)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Ingredient.of(ModTags.Items.IH_RECIPE_STONELIKES))
                .requires(Ingredient.of(ItemTags.PLANKS))
                .requires(Ingredient.of(ItemTags.PLANKS))
                .requires(Ingredient.of(ItemTags.PLANKS))
                .requires(Ingredient.of(ItemTags.PLANKS))
                .requires(Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", "furniture_kit_from_crafting_planks"));
        
        //Create Door recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ARMORED_DOOR.get(),3)
                .pattern("AA ")
                .pattern("AA ")
                .pattern("AA ")

                .define('A', ModTags.Items.VESSELPLATE_SMELTABLE_ITEM)

                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", "base_door_from_crafting"));
        //Create Trapdoor recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.VENT_TRAPDOOR.get(),2)
                .pattern("   ")
                .pattern("AAA")
                .pattern("AAA")

                .define('A', ModTags.Items.VESSELPLATE_SMELTABLE_ITEM)

                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", "base_trapdoor_from_crafting"));
        //---------- END OF RECIPES FOR BASE BLOCKS ----------
        
        //---------- REVERSIBLE BLOCK CRAFTING ----------
        //Create Horizontal Vesselplate
        reversibleBlockCrafting(ModBlocks.HORIZONTAL_VESSELPLATE.get(), ModBlocks.VERTICAL_VESSELPLATE.get(), recipeOutput);

        //Create Horizontal Reinforced Vesselplate
        reversibleBlockCrafting(ModBlocks.HORIZONTAL_REINFORCED_VESSELPLATE.get(), ModBlocks.VERTICAL_REINFORCED_VESSELPLATE.get(), recipeOutput);

        //Create Horizontal Vent
        reversibleBlockCrafting(ModBlocks.HORIZONTAL_VENT.get(), ModBlocks.VERTICAL_VENT.get(), recipeOutput);

        //Create Horizontal Cutout Vent
        reversibleBlockCrafting(ModBlocks.HORIZONTAL_CUTOUT_VENT.get(), ModBlocks.VERTICAL_CUTOUT_VENT.get(), recipeOutput);

        //Create Rusty Horizontal Vent
        reversibleBlockCrafting(ModBlocks.RUSTY_HORIZONTAL_VENT.get(), ModBlocks.RUSTY_VERTICAL_VENT.get(), recipeOutput);

        //Create Rusty Horizontal Cutout Vent
        reversibleBlockCrafting(ModBlocks.RUSTY_HORIZONTAL_CUTOUT_VENT.get(), ModBlocks.RUSTY_VERTICAL_CUTOUT_VENT.get(), recipeOutput);

        //Create GRAY Horizontal Riveted Vesselplate
        reversibleBlockCrafting(ModBlocks.GRAY_HORIZONTAL_REINFORCED_VESSELPLATE.get(), ModBlocks.GRAY_VERTICAL_REINFORCED_VESSELPLATE.get(), recipeOutput);

        //Create GRAY Horizontal Vesselplate
        reversibleBlockCrafting(ModBlocks.GRAY_HORIZONTAL_VESSELPLATE.get(), ModBlocks.GRAY_VERTICAL_VESSELPLATE.get(), recipeOutput);

        //Create Rusty Horizontal Vesselplate
        reversibleBlockCrafting(ModBlocks.RUSTY_HORIZONTAL_VESSELPLATE.get(), ModBlocks.RUSTY_VERTICAL_VESSELPLATE.get(), recipeOutput);

        //Create Rusty Horizontal Reinforced Vesselplate
        reversibleBlockCrafting(ModBlocks.RUSTY_HORIZONTAL_REINFORCED_VESSELPLATE.get(), ModBlocks.RUSTY_VERTICAL_REINFORCED_VESSELPLATE.get(), recipeOutput);
        //---------- END OF REVERSIBLE BLOCK CRAFTING ----------
        
        //---------- RECIPES FOR JOKE CONTENT ----------
        //Create Job Application and Termination Letter
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.JOB_APPLICATION.get(), 1)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Items.PAPER)
                .requires(Items.BLACK_DYE)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, "job_application"));

        reversibleBlockCrafting(ModItems.TERMINATION_LETTER.get(), ModItems.JOB_APPLICATION.get(), recipeOutput);

//        //Create Body Pillow from existing body pillow obtained from Wandering Trader
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.BODY_PILLOW.get(), 2)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(ItemTags.WOOL)
                .requires(ItemTags.WOOL)
                .requires(ItemTags.WOOL)
                .requires(ModBlocks.BODY_PILLOW.get().asItem())
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, "body_pillow"));
        //---------- END OF RECIPES FOR JOKE CONTENT ----------

        //---------- MUSIC DISC RECIPES ----------
        //Create Marquee Disc
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MARQUEE_DISC.get(),1)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")

                .define('A', Items.END_STONE)
                .define('B', ModItems.INHELL_HAVEN_DEVICE.get())

                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput);
        //---------- END OF MUSIC DISC RECIPES ----------
        
        //---------- RECYCLING RECIPES ----------
        stonecutToAmount(
                Ingredient.of(ModTags.Items.ALL_FURNITURE_ITEMS), //Recipe Input Item
                RecipeCategory.BUILDING_BLOCKS, //Category
                ModBlocks.IHEA_FURNITURE_KIT.get(), //Output block
                1)  //1 amount output
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, "furniture_to_kit"));

        if(doVesselplateCookRecycle) {
            smeltAndBlast(recipeOutput, ModTags.Items.VESSELPLATE_SMELTABLE_ITEM, RecipeCategory.MISC, Items.IRON_INGOT, 200, 100, "vesselplate");
        }
        if(doTrussCookRecycle) {
            smeltAndBlast(recipeOutput, ModTags.Items.TRUSS_SMELTABLE_ITEM, RecipeCategory.MISC, Items.IRON_INGOT, 200, 100, "strut");
        }
        if(doHvacCookRecycle) {
            smeltAndBlast(recipeOutput, ModTags.Items.HVAC_SMELTABLE_ITEM, RecipeCategory.MISC, Items.IRON_INGOT, 200, 100, "hvac");
        }
        if(doVesselglassCookRecycle) {
            smeltAndBlast(recipeOutput, ModTags.Items.VESSELGLASS_SMELTABLE_ITEM, RecipeCategory.MISC, Items.GLASS, 200, 100, "vesselglass");
        }
        if(doRockreteCookRecycle) {
            smeltAndBlast(recipeOutput, ModTags.Items.ROCKRETE_SMELTABLE_ITEM, RecipeCategory.MISC, Items.STONE, 200, 100, "rockrete");
        }
        if(doPipeworksCookRecycle) {
            smeltAndBlast(recipeOutput,ModTags.Items.PIPEWORKS_ITEMS, RecipeCategory.MISC, Items.COPPER_INGOT, 200, 100, "pipeworks");
        }
        if(doMetalworksCookRecycle) {
            smeltAndBlast(recipeOutput,ModTags.Items.METALWORKS_ITEMS, RecipeCategory.MISC, Items.COPPER_INGOT, 200, 100, "metalworks");
        }

        //---------- END OF RECYCLING TO VANILLA INGREDIENT RECIPES ----------

        //---------- BULK RECIPE GENERATION ----------
        //Building Blocks - Parameters: (List of possible output items, String for generated recipe name, Tag of possible input items, recipeOutput)
        stonecutTagToOutputListPlusSlabs(HVAC_STONECUT_OUTPUT, "hvac", ModTags.Items.HVAC_SMELTABLE_ITEM, recipeOutput);
        stonecutTagToOutputListPlusSlabs(TRUSS_STONECUT_OUTPUT, "truss", ModTags.Items.TRUSS_SMELTABLE_ITEM, recipeOutput);
        stonecutTagToOutputListPlusSlabs(VESSELPLATE_STONECUT_OUTPUT, "vesselplate", ModTags.Items.VESSELPLATE_SMELTABLE_ITEM, recipeOutput);
        stonecutTagToOutputListPlusSlabs(VESSELGLASS_STONECUT_OUTPUT, "vesselglass", ModTags.Items.VESSELGLASS_SMELTABLE_ITEM, recipeOutput);
        stonecutTagToOutputListPlusSlabs(ROCKRETE_STONECUT_OUTPUT, "rockrete", ModTags.Items.ROCKRETE_SMELTABLE_ITEM, recipeOutput); //SPECIAL METHOD FOR ROCKRETE ITEMS
        stonecutTagToOutputListPlusSlabs(PIPEWORKS_STONECUT_OUTPUT, "pipeworks", ModTags.Items.PIPEWORKS_ITEMS, recipeOutput);
        stonecutTagToOutputListPlusSlabs(METALWORKS_STONECUT_OUTPUT, "metalworks", ModTags.Items.METALWORKS_ITEMS, recipeOutput);
        stonecutInputTagToOutputList(DOORS_STONECUT_OUTPUT, "doors", ModTags.Items.DOOR_ITEMS, recipeOutput);
        stonecutInputTagToOutputList(TRAPDOORS_STONECUT_OUTPUT, "trapdoors", ModTags.Items.TRAPDOOR_ITEMS, recipeOutput);

        //Furniture - Parameters: (Tag of stonecut outputs, String for generated recipe name, Block as the single ingredient, pWriter)
        stonecutInputItemOutputList(FURNITURE_CATEGORIES, "furniture_categories", ModBlocks.IHEA_FURNITURE_KIT.get(), recipeOutput);
        stonecutInputItemOutputList(SAFETY_FURNITURE, "safety_furniture", ModBlocks.SAFETY_FURNISHINGS.get(), recipeOutput);
        stonecutInputItemOutputList(HYGIENE_FURNITURE, "hygiene_furniture", ModBlocks.HYGIENE_FURNISHINGS.get(), recipeOutput);
        stonecutInputItemOutputList(INDUSTRIAL_FURNITURE, "industrial_furniture", ModBlocks.INDUSTRIAL_FURNISHINGS.get(), recipeOutput);
        stonecutInputItemOutputList(TECHNOLOGY_FURNITURE, "technology_furniture", ModBlocks.TECHNOLOGY_FURNISHINGS.get(), recipeOutput);
        stonecutInputItemOutputList(AMENITY_FURNITURE, "amenity_furniture", ModBlocks.AMENITY_FURNISHINGS.get(), recipeOutput);
        //---------- END OF BULK RECIPE GENERATION ----------
    }

    //---------- RECIPE GENERATION METHODS ----------
    protected static void stonecutInputItemOutputList(List<ItemLike> stonecutOutputList, String inputTagName, Block inputItem, RecipeOutput recipeOutput) {
        Ingredient ingredient = Ingredient.of(inputItem);

        for (ItemLike itemLike : stonecutOutputList) {
            Item itemName = itemLike.asItem();
            String id = BuiltInRegistries.ITEM.getKey(itemName).getPath();

            stonecutToAmount(ingredient, RecipeCategory.MISC, itemLike, 1)
                    .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, inputTagName + "_stonecut_to_" + id));
        }
    }

    protected static void stonecutTagToOutputListPlusSlabs(List<ItemLike> stonecutOutputList, String inputTagName, TagKey<Item> inputTag, RecipeOutput recipeOutput) {
        Ingredient ingredient = Ingredient.of(inputTag);

        for (ItemLike itemInIndice : stonecutOutputList) {

            Item itemName = itemInIndice.asItem();
            String id = BuiltInRegistries.ITEM.getKey(itemName).getPath();

            //If the ItemLike output is a slab,
            //Create stonecutter recipe producing twice the amount of output
            //Else, function normally

            if ( (itemInIndice instanceof BlockItem blockItem) && (blockItem.getBlock() instanceof SlabBlock) ) {
                stonecutToAmount(ingredient, RecipeCategory.MISC, itemInIndice, 2) //Any block from the inputTag can produce two slabs from the stonecutOutputList
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, inputTagName + "_stonecut_to_" + id));

            } else if ( (itemInIndice instanceof BlockItem blockItem) && (blockItem.getBlock() instanceof ParapetBlock) ) {
                stonecutToAmount(ingredient, RecipeCategory.MISC, itemInIndice, 4) //Any block from the inputTag can produce four slabs parapets the stonecutOutputList
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, inputTagName + "_stonecut_to_" + id));
            } else {
                stonecutToAmount(ingredient, RecipeCategory.MISC, itemInIndice, 1)
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, inputTagName + "_stonecut_to_" + id));
            }
        }
    }

    protected static void stonecutInputTagToOutputList(List<ItemLike> stonecutOutputList, String inputTagName, TagKey<Item> inputTag, RecipeOutput recipeOutput) {
        Ingredient ingredient = Ingredient.of(inputTag);

        for (ItemLike itemLike : stonecutOutputList) {
            Item itemName = itemLike.asItem();
            String id = BuiltInRegistries.ITEM.getKey(itemName).getPath();

            stonecutToAmount(ingredient, RecipeCategory.MISC, itemLike, 1)
                    .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, inputTagName + "_stonecut_to_" + id));
        }
    }

    protected static void HavenSingleIngredientRecipe(Block outputBlock, int outputAmount, Ingredient inputIngredient, String recipeName, RecipeOutput recipeOutput) {
        //Simple shapeless recipe requiring the InHell Tool plus one defined input ingredient.

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, outputBlock, outputAmount)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(inputIngredient)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", recipeName));
    }

    protected static void reversibleBlockCrafting(ItemLike outputItemLike, ItemLike inputItemLike, RecipeOutput recipeOutput) {
        //Shapeless recipe requiring one input Block that crafts into one output Block
        //An inverse recipe is also made vice versa

        String inputBlockName = ItemLikeToBlockOrItemPath(inputItemLike);
        String outputBlockName = ItemLikeToBlockOrItemPath(outputItemLike);

        //Input to Output
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, outputItemLike, 1)
                .requires(inputItemLike)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", inputBlockName + "_to_" + outputBlockName));
        //Output to Input
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, inputItemLike, 1)
                .requires(outputItemLike)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", outputBlockName + "_to_" + inputBlockName));
    }

    protected static String ItemLikeToBlockOrItemPath(ItemLike itemLikeToReturnString) {
        //returns string of ItemLike ID. The procedure to do this is different if it's a Block or Item
        if(itemLikeToReturnString instanceof Block block) {
            return BuiltInRegistries.BLOCK.getKey(block).getPath();
        } else {
            return BuiltInRegistries.ITEM.getKey(itemLikeToReturnString.asItem()).getPath();
        }
    }

    protected static SingleItemRecipeBuilder stonecutToAmount(Ingredient ingredient, RecipeCategory category, ItemLike result, int amount) {
        return new SingleItemRecipeBuilder(category, StonecutterRecipe::new, ingredient, result, amount);
    }

    protected static void smeltAndBlast(RecipeOutput recipeOutput, TagKey<Item> pIngredients, RecipeCategory pCategory, ItemLike pResult, int pSmeltingTime, int pBlastingTime, String pGroup) {
        oreSmelting(recipeOutput, pIngredients, pCategory, pResult, 0, pSmeltingTime, pGroup);
        oreBlasting(recipeOutput, pIngredients, pCategory, pResult, 0, pBlastingTime, pGroup);
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, TagKey<Item> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting_"+pGroup);
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, TagKey<Item> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting_"+pGroup);
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       TagKey<Item> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(pIngredients), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                    .save(recipeOutput, IndustrialHellscape.MOD_ID + ":" + getItemName(pResult) + pRecipeName);
        }
    }

}

    //---------- END OF RECIPE GENERATION METHODS ----------

