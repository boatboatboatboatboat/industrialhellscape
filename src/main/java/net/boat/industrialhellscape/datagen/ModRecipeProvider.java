package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }
    //  Increasing from the default of 1 requires disabling Vesselplate, Rockrete, Pipeworks, and/or Vesselglass recycling
    private static final int vesselplatePerIronIngot = 1;
    private static final int strutPerIronIngot = 1;
    private static final int hvacPerIronIngot = 1;
    private static final int rockretePerStone = 1;
    private static final int vesselglassPerCraft = 4;

    private static final int pipeworksPerIngot = 3;
    private static final int metalworksPerIngot = 3;

    private static final int furnitureKitPerCraft = 8;

    private static final boolean doVesselplateCookRecycle = true;
    private static final boolean doRockreteCookRecycle = true;
    private static final boolean doStrutCookRecycle = true;
    private static final boolean doHvacCookRecycle = true;
    private static final boolean doVesselglassCookRecycle = false; //FALSE, craft x4

    private static final boolean doPipeworksCookRecycle = false; //FALSE, craft x3
    private static final boolean doMetalworksCookRecycle = false; //FALSE, craft x3

    private static final List<ItemLike> HVAC_STONECUT_OUTPUT = List.of(
            ModBlocks.DUCT.get().asItem(),
            ModBlocks.RUSTY_DUCT.get().asItem(),
//            ModBlocks.DUCT_VENT.get().asItem(),
//            ModBlocks.RUSTY_DUCT_VENT.get().asItem(),

            ModBlocks.HORIZONTAL_GRATE.get().asItem(),
            ModBlocks.HORIZONTAL_CUTOUT_GRATE.get().asItem(),

            ModBlocks.GRAY_HORIZONTAL_GRATE.get().asItem(),
            ModBlocks.GRAY_HORIZONTAL_CUTOUT_GRATE.get().asItem()
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
            ModBlocks.GRAY_CATWALK_TRUSS_STAIRS.get().asItem()
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

            ModBlocks.RIVETED_VESSELPLATE_STAIRS.get().asItem(),
            ModBlocks.RIVETED_VESSELPLATE_SLAB.get().asItem(),
            ModBlocks.GRAY_RIVETED_VESSELPLATE_STAIRS.get().asItem(),
            ModBlocks.GRAY_RIVETED_VESSELPLATE_SLAB.get().asItem()
    );
    private static final List<ItemLike> ROCKRETE_STONECUT_OUTPUT = List.of(

            ModBlocks.GRAY_ROCKRETE.get().asItem(),
            ModBlocks.ROUGH_GRAY_ROCKRETE.get().asItem(),
            ModBlocks.GRAY_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.GRAY_ROCKRETE_STAIRS.get().asItem(),
            ModBlocks.GRAY_ROCKRETE_PILLAR.get().asItem(),

            ModBlocks.GREEN_ROCKRETE.get().asItem(),
            ModBlocks.ROUGH_GREEN_ROCKRETE.get().asItem(),
            ModBlocks.GREEN_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.GREEN_ROCKRETE_STAIRS.get().asItem(),
            ModBlocks.GREEN_ROCKRETE_PILLAR.get().asItem(),

            ModBlocks.YELLOW_ROCKRETE.get().asItem(),
            ModBlocks.ROUGH_YELLOW_ROCKRETE.get().asItem(),
            ModBlocks.YELLOW_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.YELLOW_ROCKRETE_STAIRS.get().asItem(),
            ModBlocks.YELLOW_ROCKRETE_PILLAR.get().asItem(),

            ModBlocks.BLUE_ROCKRETE.get().asItem(),
            ModBlocks.ROUGH_BLUE_ROCKRETE.get().asItem(),
            ModBlocks.BLUE_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.BLUE_ROCKRETE_STAIRS.get().asItem(),
            ModBlocks.BLUE_ROCKRETE_PILLAR.get().asItem(),

            ModBlocks.RED_ROCKRETE.get().asItem(),
            ModBlocks.ROUGH_RED_ROCKRETE.get().asItem(),
            ModBlocks.RED_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.RED_ROCKRETE_STAIRS.get().asItem(),
            ModBlocks.RED_ROCKRETE_PILLAR.get().asItem(),
            ModBlocks.GRIMY_RESTROOM_TILE.get().asItem()
    );
    private static final List<ItemLike> VESSELGLASS_STONECUT_OUTPUT = List.of(
            ModBlocks.VESSELGLASS.get().asItem(),
            ModBlocks.REINFORCED_VESSELGLASS.get().asItem(),
            ModBlocks.GRAY_REINFORCED_VESSELGLASS.get().asItem(),
            ModBlocks.GRAY_VESSELGLASS.get().asItem()
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
            ModBlocks.VESSELPLATE_DOOR.get().asItem(),
            ModBlocks.GRAY_VESSELPLATE_DOOR.get().asItem()
    );
    private static final List<ItemLike> TRAPDOORS_STONECUT_OUTPUT = List.of(
            ModBlocks.VESSELPLATE_TRAPDOOR.get().asItem(),
            ModBlocks.GRAY_VESSELPLATE_TRAPDOOR.get().asItem()
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
            ModBlocks.FUEL_DRUM.get().asItem()
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


    protected void buildRecipes(RecipeOutput recipeOutput) {

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
                .save(recipeOutput);

        //Create 1x Vesselplate from 1x iron ingots
        HavenSingleIngredientRecipe(ModBlocks.RIVETED_VESSELPLATE.get(), vesselplatePerIronIngot, Ingredient.of(Items.IRON_INGOT), "vesselplate_from_iron_ingot", recipeOutput);
        //Create 9x Vesselplate from 1x iron block
        HavenSingleIngredientRecipe(ModBlocks.RIVETED_VESSELPLATE.get(), vesselplatePerIronIngot*9, Ingredient.of(Items.IRON_BLOCK), "vesselplate_from_iron_block", recipeOutput);
        //Create 1x Vesselglass Base Block from 1 iron ingot and 1 glass
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.VESSELGLASS.get(), vesselglassPerCraft)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Items.IRON_INGOT)
                .requires(Items.GLASS)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", "vesselglass_from_crafting"));
        
        //Create Rockrete Base Block from 1 stone
        HavenSingleIngredientRecipe(ModBlocks.GRAY_ROCKRETE.get(), rockretePerStone, Ingredient.of(ModTags.Items.IH_RECIPE_STONELIKES), "rockrete_from_stone", recipeOutput);

        //Create Metalworks recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.METALWORKS.get(),metalworksPerIngot*6)
                .pattern("A A")
                .pattern("ABA")
                .pattern("A A")

                .define('A', ModTags.Items.IH_RECIPE_INGOTS)
                .define('B', ModItems.INHELL_HAVEN_DEVICE.get())

                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput);
        //Create Pipeworks recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PIPEWORKS.get(),pipeworksPerIngot*6)
                .pattern("AAA")
                .pattern(" B ")
                .pattern("AAA")

                .define('A', ModTags.Items.IH_RECIPE_INGOTS)
                .define('B', ModItems.INHELL_HAVEN_DEVICE.get())

                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput);
        //Create Furniture Kit Base Block from 1 stonetype, one log (or 4 planks), one iron ingot, and the HAVEN device
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.IHEA_FURNITURE_KIT.get(), furnitureKitPerCraft)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Ingredient.of(ModTags.Items.IH_RECIPE_STONELIKES))
                .requires(Ingredient.of(ItemTags.LOGS))
                .requires(Ingredient.of(ModTags.Items.IH_RECIPE_INGOTS))
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", "furniture_kit_recipe_log"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.IHEA_FURNITURE_KIT.get(), furnitureKitPerCraft)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Ingredient.of(ModTags.Items.IH_RECIPE_STONELIKES))
                .requires(Ingredient.of(ItemTags.PLANKS))
                .requires(Ingredient.of(ItemTags.PLANKS))
                .requires(Ingredient.of(ItemTags.PLANKS))
                .requires(Ingredient.of(ItemTags.PLANKS))
                .requires(Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", "furniture_kit_recipe_planks"));
        
        //Create Door recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.VESSELPLATE_DOOR.get(),3)
                .pattern("AA ")
                .pattern("AA ")
                .pattern("AA ")

                .define('A', ModTags.Items.VESSELPLATE_SMELTABLE_ITEM)

                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput);
        //Create Trapdoor recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.VESSELPLATE_TRAPDOOR.get(),2)
                .pattern("   ")
                .pattern("AAA")
                .pattern("AAA")

                .define('A', ModTags.Items.VESSELPLATE_SMELTABLE_ITEM)

                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput);
        //---------- END OF RECIPES FOR BASE BLOCKS ----------
        
        //---------- REVERSIBLE BLOCK CRAFTING ----------
        //Create Horizontal Vesselplate
        reversibleBlockCrafting(ModBlocks.HORIZONTAL_VESSELPLATE.get(), ModBlocks.VERTICAL_VESSELPLATE.get(), recipeOutput);

        //Create Horizontal Reinforced Vesselplate
        reversibleBlockCrafting(ModBlocks.HORIZONTAL_REINFORCED_VESSELPLATE.get(), ModBlocks.VERTICAL_REINFORCED_VESSELPLATE.get(), recipeOutput);

        //Create Horizontal Grate
        reversibleBlockCrafting(ModBlocks.HORIZONTAL_GRATE.get(), ModBlocks.VERTICAL_GRATE.get(), recipeOutput);

        //Create Horizontal Cutout Grate
        reversibleBlockCrafting(ModBlocks.HORIZONTAL_CUTOUT_GRATE.get(), ModBlocks.VERTICAL_CUTOUT_GRATE.get(), recipeOutput);

        //Create GRAY Horizontal Riveted Vesselplate
        reversibleBlockCrafting(ModBlocks.GRAY_HORIZONTAL_REINFORCED_VESSELPLATE.get(), ModBlocks.GRAY_VERTICAL_REINFORCED_VESSELPLATE.get(), recipeOutput);

        //Create GRAY Horizontal Grate
        reversibleBlockCrafting(ModBlocks.GRAY_HORIZONTAL_GRATE.get(), ModBlocks.GRAY_VERTICAL_GRATE.get(), recipeOutput);

        //Create GRAY Horizontal Cutout Grate
        reversibleBlockCrafting(ModBlocks.GRAY_HORIZONTAL_CUTOUT_GRATE.get(), ModBlocks.GRAY_VERTICAL_CUTOUT_GRATE.get(), recipeOutput);

        //Create GRAY Horizontal Vesselplate
        reversibleBlockCrafting(ModBlocks.GRAY_HORIZONTAL_VESSELPLATE.get(), ModBlocks.GRAY_VERTICAL_VESSELPLATE.get(), recipeOutput);
        //---------- END OF REVERSIBLE BLOCK CRAFTING ----------
        
        //---------- RECIPES FOR JOKE CONTENT ----------
        //Create Job Application
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.JOB_APPLICATION.get(), 1)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Items.PAPER)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", "job_application"));
        //Create Body Pillow
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.BODY_PILLOW.get(), 1)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(ItemTags.WOOL)
                .requires(ItemTags.WOOL)
                .requires(ItemTags.WOOL)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", "body_pillow"));
        
        //---------- END OF RECIPES FOR JOKE CONTENT ----------
        
        //---------- RECYCLING RECIPES ----------
        stonecutToAmount(
                Ingredient.of(ModTags.Items.ALL_FURNITURE_ITEMS), //Recipe Input Item
                RecipeCategory.BUILDING_BLOCKS, //Category
                ModBlocks.IHEA_FURNITURE_KIT.get(),1)  //Outputs into the category blocks (safety, hygiene, industrial)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", "furniture_to_kit"));

        if(doVesselplateCookRecycle) {
            smeltAndBlast(recipeOutput, ModTags.Items.VESSELPLATE_SMELTABLE_ITEM, RecipeCategory.MISC, Items.IRON_INGOT, 200, 100, "vesselplate");
        }
        if(doStrutCookRecycle) {
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
        stonecutManyOutputsPlusSlabs(HVAC_STONECUT_OUTPUT, "hvac", ModTags.Items.HVAC_SMELTABLE_ITEM, recipeOutput);
        stonecutManyOutputsPlusSlabs(TRUSS_STONECUT_OUTPUT, "strut", ModTags.Items.TRUSS_SMELTABLE_ITEM, recipeOutput);
        stonecutManyOutputsPlusSlabs(VESSELPLATE_STONECUT_OUTPUT, "vesselplate", ModTags.Items.VESSELPLATE_SMELTABLE_ITEM, recipeOutput);
        stonecutManyOutputsPlusSlabs(VESSELGLASS_STONECUT_OUTPUT, "vesselglass", ModTags.Items.VESSELGLASS_SMELTABLE_ITEM, recipeOutput);
        stonecutManyOutputsPlusSlabs(ROCKRETE_STONECUT_OUTPUT, "rockrete", ModTags.Items.ROCKRETE_SMELTABLE_ITEM, recipeOutput);
        stonecutManyOutputsPlusSlabs(PIPEWORKS_STONECUT_OUTPUT, "pipeworks", ModTags.Items.PIPEWORKS_ITEMS, recipeOutput);
        stonecutManyOutputsPlusSlabs(METALWORKS_STONECUT_OUTPUT, "metalworks", ModTags.Items.METALWORKS_ITEMS, recipeOutput);
        stonecutManyOutputs(DOORS_STONECUT_OUTPUT, "doors", ModTags.Items.DOOR_ITEMS, recipeOutput);
        stonecutManyOutputs(TRAPDOORS_STONECUT_OUTPUT, "trapdoors", ModTags.Items.TRAPDOOR_ITEMS, recipeOutput);

        //Furniture - Parameters: (Tag of stonecut outputs, String for generated recipe name, Block as the single ingredient, pWriter)
        stonecutInputItemOutputList(FURNITURE_CATEGORIES, "furniture_categories", ModBlocks.IHEA_FURNITURE_KIT.get(), recipeOutput);
        stonecutInputItemOutputList(SAFETY_FURNITURE, "safety_furniture", ModBlocks.SAFETY_FURNISHINGS.get(), recipeOutput);
        stonecutInputItemOutputList(HYGIENE_FURNITURE, "hygiene_furniture", ModBlocks.HYGIENE_FURNISHINGS.get(), recipeOutput);
        stonecutInputItemOutputList(INDUSTRIAL_FURNITURE, "industrial_furniture", ModBlocks.INDUSTRIAL_FURNISHINGS.get(), recipeOutput);
        stonecutInputItemOutputList(TECHNOLOGY_FURNITURE, "technology_furniture", ModBlocks.TECHNOLOGY_FURNISHINGS.get(), recipeOutput);
        stonecutInputItemOutputList(AMENITY_FURNITURE, "amenity_furniture", ModBlocks.AMENITY_FURNISHINGS.get(), recipeOutput);
    }

    //---------- RECIPE GENERATION METHODS ----------
    public static SingleItemRecipeBuilder stonecutToAmount(Ingredient ingredient, RecipeCategory category, ItemLike result, int amount) {
        return new SingleItemRecipeBuilder(category, StonecutterRecipe::new, ingredient, result, amount);
    }

    protected static void stonecutInputItemOutputList(List<ItemLike> stonecutOutputList, String inputTagName, Block inputItem, RecipeOutput recipeOutput) {
        Ingredient ingredient = Ingredient.of(inputItem);

        for (int i = 0; i < stonecutOutputList.size(); i++) {
            Item itemName = stonecutOutputList.get(i).asItem();
            String id = BuiltInRegistries.ITEM.getKey(itemName).getPath();

            stonecutToAmount(ingredient, RecipeCategory.MISC, stonecutOutputList.get(i), 1)
                    .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, inputTagName + "_stonecut_to_" + id));
        }
    }

    protected static void stonecutManyOutputsPlusSlabs(List<ItemLike> stonecutOutputList, String inputTagName, TagKey<Item> inputTag, RecipeOutput recipeOutput) {
        Ingredient ingredient = Ingredient.of(inputTag);

        for (int i = 0; i < stonecutOutputList.size(); i++) {
            Item itemName = stonecutOutputList.get(i).asItem();
            String id = BuiltInRegistries.ITEM.getKey(itemName).getPath();

            //If the ItemLike output is a slab,
            //Create stonecutter recipe producing twice the amount of output
            //Else, function normally

            if (id.contains("_slab")  ) {
                stonecutToAmount(ingredient, RecipeCategory.MISC, stonecutOutputList.get(i), 2)
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, inputTagName + "_stonecut_to_" + id));
            } else {
                stonecutToAmount(ingredient, RecipeCategory.MISC, stonecutOutputList.get(i), 1)
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, inputTagName + "_stonecut_to_" + id));
            }
        }
    }
    protected static void stonecutManyOutputs(List<ItemLike> stonecutOutputList, String inputTagName, TagKey<Item> inputTag, RecipeOutput recipeOutput) {
        Ingredient ingredient = Ingredient.of(inputTag);

        for (int i = 0; i < stonecutOutputList.size(); i++) {
            Item itemName = stonecutOutputList.get(i).asItem();
            String id = BuiltInRegistries.ITEM.getKey(itemName).getPath();

            stonecutToAmount(ingredient, RecipeCategory.MISC, stonecutOutputList.get(i), 1)
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

    protected static void reversibleBlockCrafting(Block outputBlock, Block inputBlock, RecipeOutput recipeOutput) {
        //Shapeless recipe requiring one input Block that crafts into one output Block
        //An inverse recipe is also made vice-versa

        String inputBlockName = BuiltInRegistries.BLOCK.getKey(inputBlock).getPath();
        String outputBlockName = BuiltInRegistries.BLOCK.getKey(outputBlock).getPath();

        //Input to Output
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, outputBlock, 1)
                .requires(inputBlock)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", "reversible_" + outputBlockName));
        //Output to Input
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, inputBlock, 1)
                .requires(outputBlock)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", "reversible_" + inputBlockName));
    }

    protected static void smeltAndBlast(RecipeOutput recipeOutput, TagKey<Item> pIngredients, RecipeCategory pCategory, ItemLike pResult, int pCookingTIme, int pBlastingTime, String pGroup) {
        oreSmelting(recipeOutput, pIngredients, pCategory, pResult, 0, pCookingTIme, pGroup);
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
                pExperience, pCookingTime, pGroup, "_from_blasting"+pGroup);
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

