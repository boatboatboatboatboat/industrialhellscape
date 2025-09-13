package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    //---------- DATAGEN VARIABLES: OUTPUT AMOUNT PER INPUT INGREDIENT(S) ----------
        //  Increasing from the default of 1 requires disabling Vesselplate, Rockrete, Pipeworks, and/or Vesselglass recycling
    private static final int vesselplatePerIronIngot = 1;
    private static final int rockretePerStone = 1;
    private static final int pipeworksPerCopperIngot = 1;
    private static final int vesselglassPerCraft = 1;

    private static final int furnitureKitPerCraft = 1;

    private static final boolean doVesselplateCookRecycle = true;
    private static final boolean doRockreteCookRecycle = true;
    private static final boolean doPipeworksCookRecycle = true;
    private static final boolean doVesselglassCookRecycle = true;

    //---------- END OF DATAGEN VARIABLES ----------

    private static final List<ItemLike> VESSELPLATE_STONECUT_OUTPUT = List.of(
            ModBlocks.VESSELPLATE.get().asItem(),
            ModBlocks.VESSELPLATE_PILLAR.get().asItem(),
            ModBlocks.RIVETED_VESSELPLATE_PANEL.get().asItem(),
            ModBlocks.GRATE.get().asItem(),
            ModBlocks.SEETHROUGH_GRATE.get().asItem(),
            ModBlocks.HORIZONTAL_RIVETED_VESSELPLATE.get().asItem(),
            ModBlocks.VERTICAL_RIVETED_VESSELPLATE.get().asItem(),
            ModBlocks.SMOOTH_VESSELPLATE_TILE.get().asItem(),

            ModBlocks.GRAY_VESSELPLATE.get().asItem(),
            ModBlocks.GRAY_RIVETED_VESSELPLATE_PANEL.get().asItem(),
            ModBlocks.GRAY_GRATE.get().asItem(),
            ModBlocks.GRAY_SEETHROUGH_GRATE.get().asItem(),
            ModBlocks.GRAY_HORIZONTAL_RIVETED_VESSELPLATE.get().asItem(),
            ModBlocks.GRAY_VERTICAL_RIVETED_VESSELPLATE.get().asItem(),
            ModBlocks.SMOOTH_GRAY_VESSELPLATE_TILE.get().asItem(),
            ModBlocks.GRAY_VESSELPLATE_PILLAR.get().asItem(),

            ModBlocks.STRUT.get().asItem(),
            ModBlocks.STRUT_STAIRS.get().asItem(),
            ModBlocks.STRUT_SLAB.get().asItem(),

            ModBlocks.CATWALK_STRUT.get().asItem(),
            ModBlocks.CATWALK_STRUT_SLAB.get().asItem(),
            ModBlocks.CATWALK_STRUT_STAIRS.get().asItem(),

            ModBlocks.GRAY_STRUT.get().asItem(),
            ModBlocks.GRAY_STRUT_STAIRS.get().asItem(),
            ModBlocks.GRAY_STRUT_SLAB.get().asItem(),

            ModBlocks.GRAY_CATWALK_STRUT.get().asItem(),
            ModBlocks.GRAY_CATWALK_STRUT_SLAB.get().asItem(),
            ModBlocks.GRAY_CATWALK_STRUT_STAIRS.get().asItem(),

            ModBlocks.ENCASED_CABLES.get().asItem(),
            ModBlocks.RUSTY_GRATE.get().asItem()
    );
    private static final List<ItemLike> ROCKRETE_STONECUT_OUTPUT = List.of(
            
            ModBlocks.GRAY_ROCKRETE.get().asItem(),
            ModBlocks.GRAY_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.GRAY_ROCKRETE_STAIRS.get().asItem(),
            ModBlocks.GRAY_ROCKRETE_PILLAR.get().asItem(),

            ModBlocks.GREEN_ROCKRETE.get().asItem(),
            ModBlocks.GREEN_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.GREEN_ROCKRETE_STAIRS.get().asItem(),
            ModBlocks.GREEN_ROCKRETE_PILLAR.get().asItem(),

            ModBlocks.YELLOW_ROCKRETE.get().asItem(),
            ModBlocks.YELLOW_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.YELLOW_ROCKRETE_STAIRS.get().asItem(),
            ModBlocks.YELLOW_ROCKRETE_PILLAR.get().asItem(),

            ModBlocks.BLUE_ROCKRETE.get().asItem(),
            ModBlocks.BLUE_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.BLUE_ROCKRETE_STAIRS.get().asItem(),
            ModBlocks.BLUE_ROCKRETE_PILLAR.get().asItem(),

            ModBlocks.HAZARD_STRIPE_YELLOW.get().asItem(),
            ModBlocks.HAZARD_STRIPE_RED.get().asItem(),

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
    private static final List<ItemLike> FURNITURE_CATEGORIES = List.of(
            ModBlocks.SAFETY_FURNISHINGS.get().asItem(),
            ModBlocks.HYGIENE_FURNISHINGS.get().asItem(),
            ModBlocks.INDUSTRIAL_FURNISHINGS.get().asItem(),
            ModBlocks.TECHNOLOGY_FURNISHINGS.get().asItem(),
            ModBlocks.AMENITY_FURNISHINGS.get().asItem()
    );

    private static final List<ItemLike> SAFETY_FURNITURE = List.of(
            ModBlocks.RED_WALL_MEDKIT.get().asItem(),
            ModBlocks.WHITE_WALL_MEDKIT.get().asItem()
    );
    private static final List<ItemLike> HYGIENE_FURNITURE = List.of(
            ModBlocks.TOILET.get().asItem(),
            ModBlocks.SINK.get().asItem()
    );
    private static final List<ItemLike> INDUSTRIAL_FURNITURE = List.of(
            ModBlocks.LOCKER_BOX.get().asItem(),
            ModBlocks.LARGE_LOCKER.get().asItem(),
            ModBlocks.GRAY_BOLTED_BRACKET.get().asItem(),
            ModBlocks.BLACK_BOLTED_BRACKET.get().asItem(),
            ModBlocks.YELLOW_TRIPOD.get().asItem(),
            ModBlocks.WORK_LIGHT_MOUNT.get().asItem(),
            ModBlocks.FLOOR_WORK_LIGHT.get().asItem()
    );
    private static final List<ItemLike> TECHNOLOGY_FURNITURE = List.of(
            ModBlocks.RETRO_COMPUTER.get().asItem(),
            ModBlocks.RETRO_COMPUTER_2.get().asItem(),
            ModBlocks.CASSETTE_PLAYER.get().asItem()
    );
    private static final List<ItemLike> AMENITY_FURNITURE = List.of(
            ModBlocks.DESK.get().asItem(),
            ModBlocks.DESK_DRAWER.get().asItem(),
            ModBlocks.METAL_DESK.get().asItem(),
            ModBlocks.METAL_DESK_DRAWER.get().asItem(),
            ModBlocks.METAL_DESK_DRAWER_2.get().asItem(),
            ModBlocks.BODY_PILLOW.get().asItem()
    );

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {

        //---------- CREATE BASE MODDED BLOCKS FROM VANILLA BLOCKS ----------

        //Create 9x Vesselplate from 9x iron ingots OR 1x Vesselplate from 1x iron ingots
        OneIngredientShapelessRecipe(ModBlocks.VESSELPLATE.get(), vesselplatePerIronIngot, Ingredient.of(Items.IRON_INGOT), "vesselplate_from_iron_ingot", pWriter);
        OneIngredientShapelessRecipe(ModBlocks.VESSELPLATE.get(), vesselplatePerIronIngot*9, Ingredient.of(Items.IRON_BLOCK), "vesselplate_from_iron_block", pWriter);

        //Create Rockrete Base Block from 1 stone
        OneIngredientShapelessRecipe(ModBlocks.GRAY_ROCKRETE.get(), rockretePerStone, Ingredient.of(ModTags.Items.IH_RECIPE_STONELIKES), "rockrete_from_stone", pWriter);

        //Create Pipeworks block, base block for Pipes
        OneIngredientShapelessRecipe(ModBlocks.PIPEWORKS.get(), pipeworksPerCopperIngot, Ingredient.of(Items.COPPER_INGOT), "pipeworks_from_copper_ingot", pWriter);
        OneIngredientShapelessRecipe(ModBlocks.PIPEWORKS.get(), pipeworksPerCopperIngot*9, Ingredient.of(Items.COPPER_BLOCK), "pipeworks_from_copper_block", pWriter);

        //Create 1x Vesselglass Base Block from 1 iron ingot and 1 glass
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.VESSELGLASS.get(), vesselglassPerCraft)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Items.IRON_INGOT)
                .requires(Items.GLASS)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter, new ResourceLocation("industrialhellscape", "vesselglass_from_iron_ingot"));

        //Create Furniture Kit Base Block from 1 stonetype, one log (or 4 planks), one iron ingot, and the HAVEN device
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.IHEA_FURNITURE_KIT.get(), furnitureKitPerCraft)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Ingredient.of(ModTags.Items.IH_RECIPE_STONELIKES))
                .requires(Ingredient.of(ItemTags.LOGS))
                .requires(Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter, new ResourceLocation("industrialhellscape", "furniture_kit_recipe_log"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.IHEA_FURNITURE_KIT.get(), furnitureKitPerCraft)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Ingredient.of(ModTags.Items.IH_RECIPE_STONELIKES))
                .requires(Ingredient.of(ItemTags.PLANKS))
                .requires(Ingredient.of(ItemTags.PLANKS))
                .requires(Ingredient.of(ItemTags.PLANKS))
                .requires(Ingredient.of(ItemTags.PLANKS))
                .requires(Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter, new ResourceLocation("industrialhellscape", "furniture_kit_recipe_planks"));


        //---------- END OF BASE MODDED BLOCK CRAFTING ----------

        //---------- CONDITIONAL (IN DATAGEN ONLY) RECYCLE TO VANILLA INGREDIENT RECIPES ----------

        if(doVesselplateCookRecycle) {
            recipeSmeltAndBlast(pWriter, ModTags.Items.VESSELPLATE_SMELTABLE_ITEM, RecipeCategory.MISC, Items.IRON_INGOT, 200, 100, "Vesselplate");
        }
        if(doVesselglassCookRecycle) {
            recipeSmeltAndBlast(pWriter, ModTags.Items.VESSELGLASS_SMELTABLE_ITEM, RecipeCategory.MISC, Items.GLASS, 200, 100, "Vesselglass");
        }
        if(doRockreteCookRecycle) {
            recipeSmeltAndBlast(pWriter, ModTags.Items.ROCKRETE_SMELTABLE_ITEM, RecipeCategory.MISC, Items.STONE, 200, 100, "Rockrete");
        }
        if(doPipeworksCookRecycle) {
        recipeSmeltAndBlast(pWriter,ModTags.Items.PIPEWORKS_SMELTABLE_ITEM, RecipeCategory.MISC, Items.COPPER_INGOT, 200, 100, "Pipeworks");
        }

        //---------- END OF RECYCLING TO VANILLA INGREDIENT RECIPES ----------

        //---------- FURNITURE RECYCLE BACK INTO FURNITURE KIT ----------
        stonecutting(
                Ingredient.of(ModTags.Items.ALL_FURNITURE_ITEMS), //Recipe Input Item
                RecipeCategory.BUILDING_BLOCKS, //Category
                ModBlocks.IHEA_FURNITURE_KIT.get(),1)  //Outputs into the category blocks (safety, hygiene, industrial)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter, new ResourceLocation("industrialhellscape", "furniture_to_kit"));
        //---------- END OF FURNITURE RECYCLING ----------

        //----------SPECIAL ITEM CRAFTING ----------

        //Create InHell HAVEN Device
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.INHELL_HAVEN_DEVICE.get())
                .pattern("ABC")
                .pattern("DDD")
                .pattern("ABC")

                .define('A', Items.IRON_INGOT)
                .define('B', Items.COPPER_INGOT)
                .define('C', Items.GOLD_INGOT)
                .define('D', Items.QUARTZ)

                .unlockedBy(getHasName(Items.QUARTZ), has(Items.QUARTZ))
                        .save(pWriter);

        //Create Vaporwave Cassette
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VAPORWAVE_CASSETTE.get())
                .pattern(" A ")
                .pattern("BCB")
                .pattern("DDD")

                .define('A', ModItems.INHELL_HAVEN_DEVICE.get())
                .define('B', Items.IRON_NUGGET)
                .define('C', Items.QUARTZ)
                .define('D', Items.GLASS_PANE)

                .unlockedBy(getHasName(Items.QUARTZ), has(Items.QUARTZ))
                .save(pWriter);

        //Create Job Application
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.JOB_APPLICATION.get(), 1)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Items.PAPER)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter, new ResourceLocation("industrialhellscape", "job_application"));


        //---------- END OF SPECIAL ITEM CRAFTING ----------

        //---------- INTERCHANGEABLE STONECUTTER CRAFTING ----------

        //Input block tag's stonecutter recipes gets iteratively registered to every block within that tag.
        //If item in tag is a slab, produce two slabs as an output.
        //The ingredients are of the "Smeltable" item tag and the outputs are of the "Stonecut outputs" item list. This ensures that only smeltable blocks can be crafted into slabs.
        //And therefore slabs cannot be crafted into smeltable blocks. Slabs cannot be smelted. This prevents item duplication via smelting into base vanilla ingredient items.


        //Building Blocks - Parameters: (List of possible output items, String for generated recipe name, Tag of possible input items, pWriter)
        iterativeRecipes(VESSELPLATE_STONECUT_OUTPUT, "vesselplate", ModTags.Items.VESSELPLATE_SMELTABLE_ITEM, pWriter);
        iterativeRecipes(VESSELGLASS_STONECUT_OUTPUT, "vesselglass", ModTags.Items.VESSELGLASS_SMELTABLE_ITEM, pWriter);
        iterativeRecipes(ROCKRETE_STONECUT_OUTPUT, "rockrete", ModTags.Items.ROCKRETE_SMELTABLE_ITEM, pWriter);
        iterativeRecipes(PIPEWORKS_STONECUT_OUTPUT, "pipeworks", ModTags.Items.PIPEWORKS_SMELTABLE_ITEM, pWriter);

        //Furniture - Parameters: (Tag of stonecut outputs, String for generated recipe name, Block as the single ingredient, pWriter)
        oneIngredientStonecutsToMany(FURNITURE_CATEGORIES, "furniture_categories", ModBlocks.IHEA_FURNITURE_KIT.get().asItem(), pWriter);
        oneIngredientStonecutsToMany(SAFETY_FURNITURE, "safety_furniture", ModBlocks.SAFETY_FURNISHINGS.get().asItem(), pWriter);
        oneIngredientStonecutsToMany(HYGIENE_FURNITURE, "hygiene_furniture", ModBlocks.HYGIENE_FURNISHINGS.get().asItem(), pWriter);
        oneIngredientStonecutsToMany(INDUSTRIAL_FURNITURE, "industrial_furniture", ModBlocks.INDUSTRIAL_FURNISHINGS.get().asItem(), pWriter);
        oneIngredientStonecutsToMany(TECHNOLOGY_FURNITURE, "technology_furniture", ModBlocks.TECHNOLOGY_FURNISHINGS.get().asItem(), pWriter);
        oneIngredientStonecutsToMany(AMENITY_FURNITURE, "amenity_furniture", ModBlocks.AMENITY_FURNISHINGS.get().asItem(), pWriter);

        //---------- END OF INTERCHANGEABLE STONECUTTER CRAFTING ----------
    }

    //---------- RECIPE GENERATION METHODS ----------

    protected static void oneIngredientStonecutsToMany(List<ItemLike> stonecutOutputList, String materialType, Item itemIngredient, Consumer<FinishedRecipe> pWriter){
        for (int i = 0; i < stonecutOutputList.size(); i++) {
            String itemName = stonecutOutputList.get(i).toString().replaceAll("[^a-zA-Z]+","_");
            stonecutting(
                    Ingredient.of(itemIngredient),
                    RecipeCategory.BUILDING_BLOCKS,
                    stonecutOutputList.get(i),1)
                    .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                    .save(pWriter, new ResourceLocation("industrialhellscape", materialType + "_stonecut_" + itemName+i));
        }
    }

    protected static void iterativeRecipes(List<ItemLike> stonecutOutputList, String materialType, TagKey<Item> smeltableItemTag, Consumer<FinishedRecipe> pWriter) {
        for (int i = 0; i < stonecutOutputList.size(); i++) {
            String itemName = stonecutOutputList.get(i).toString().replaceAll("[^a-zA-Z]+","_");
            if (itemName.contains("_slab")  ) {
                stonecutting(
                        Ingredient.of(smeltableItemTag),
                        RecipeCategory.BUILDING_BLOCKS,
                        stonecutOutputList.get(i),2) //2x output for slab output
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(pWriter, new ResourceLocation("industrialhellscape", materialType + "_stonecut_" + itemName+i));
            } else

                stonecutting(
                        Ingredient.of(smeltableItemTag),
                        RecipeCategory.BUILDING_BLOCKS,
                        stonecutOutputList.get(i),1)
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(pWriter, new ResourceLocation("industrialhellscape", materialType + "_stonecut_" + itemName+i));
        }
    }

    protected static void OneIngredientShapelessRecipe(Block outputBlock, int outputAmount, Ingredient inputIngredient, String recipeName, Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, outputBlock, outputAmount)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(inputIngredient)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter, new ResourceLocation("industrialhellscape", recipeName));
    }

    protected static SingleItemRecipeBuilder stonecutting(Ingredient pIngredients, RecipeCategory pCategory, ItemLike pResult, Integer amount) {
        return new SingleItemRecipeBuilder(pCategory, RecipeSerializer.STONECUTTER, pIngredients, pResult, amount);
    }

    protected static void recipeSmeltAndBlast(Consumer<FinishedRecipe> pFinishedRecipeConsumer, TagKey<Item> tagOfInputIngredients, RecipeCategory pCategory, ItemLike outputIngredient, int smeltingTime, int blastingTime, String pGroup) {
        recipeSmelting(pFinishedRecipeConsumer, tagOfInputIngredients, pCategory, outputIngredient, smeltingTime, pGroup, "smelt_recycle");
        recipeBlasting(pFinishedRecipeConsumer, tagOfInputIngredients, pCategory, outputIngredient, blastingTime, pGroup, "blast_recycle");
    }

    protected static void recipeSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, TagKey<Item> tagOfInputIngredients, RecipeCategory pCategory, ItemLike outputIngredient, int pCookingTime, String pGroup, String pRecipeName) {
        cookingRecipe(pFinishedRecipeConsumer, tagOfInputIngredients, RecipeSerializer.SMELTING_RECIPE, pCategory, outputIngredient, pCookingTime, pGroup, pRecipeName);
    }

    protected static void recipeBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, TagKey<Item> tagOfInputIngredients, RecipeCategory pCategory, ItemLike outputIngredient, int pCookingTime, String pGroup, String pRecipeName) {
        cookingRecipe(pFinishedRecipeConsumer, tagOfInputIngredients, RecipeSerializer.SMELTING_RECIPE, pCategory, outputIngredient, pCookingTime, pGroup, pRecipeName);
    }
    protected static void cookingRecipe(Consumer<FinishedRecipe> pFinishedRecipeConsumer, TagKey<Item> tagOfIngredients, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, RecipeCategory pCategory, ItemLike pResult, int pCookingTime, String pGroup, String pRecipeName) {
        SimpleCookingRecipeBuilder.generic(Ingredient.of(tagOfIngredients), pCategory, pResult, 0, pCookingTime, pCookingSerializer)
                .group(pGroup).unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pFinishedRecipeConsumer, getItemName(pResult) + "_" + pRecipeName);
    }

    //---------- END OF RECIPE GENERATION METHODS ----------
}
