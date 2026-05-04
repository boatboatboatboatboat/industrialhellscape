package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
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

    protected void buildRecipes(RecipeOutput recipeOutput) {

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

        //Create Job Application
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.JOB_APPLICATION.get(), 1)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Items.PAPER)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath("industrialhellscape", "job_application"));

    }

    //---------- RECIPE GENERATION METHODS ----------

    protected static void stonecutManyOutputs(List<ItemLike> stonecutOutputList, String inputTagName, Ingredient itemIngredient, RecipeOutput recipeOutput) {
        //For each entry in the ItemLike List,
        //Turn any non-alphanumeric character to underscore
        //Create stonecutting recipe from specified input ingredient to output at the list indice

        for (int i = 0; i < stonecutOutputList.size(); i++) {
            String itemName = stonecutOutputList.get(i).toString().replaceAll("[^a-zA-Z]+", "_");
            SingleItemRecipeBuilder.stonecutting(itemIngredient, RecipeCategory.MISC, stonecutOutputList.get(i), 1)
                    .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, inputTagName + "_stonecut_" + itemName+i));
        }
    }

    protected static void stonecutManyOutputsPlusSlabs(List<ItemLike> stonecutOutputList, String inputTagName, Ingredient itemIngredient, RecipeOutput recipeOutput) {
        for (int i = 0; i < stonecutOutputList.size(); i++) {
            String itemName = stonecutOutputList.get(i).toString().replaceAll("[^a-zA-Z]+","_");

            //Conditional version of oneIngredientStonecutsToMany()
            //If the ItemLike output is a slab,
            //Create stonecutter recipe producing twice the amount of output
            //Else, function normally

            if (itemName.contains("_slab")  ) {
                SingleItemRecipeBuilder.stonecutting(itemIngredient, RecipeCategory.MISC, stonecutOutputList.get(i), 2)
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, inputTagName + "_stonecut_" + itemName+i));
            } else {
                SingleItemRecipeBuilder.stonecutting(itemIngredient, RecipeCategory.MISC, stonecutOutputList.get(i))
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, inputTagName + "_stonecut_" + itemName+i));
            }
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

    public static SingleItemRecipeBuilder stonecutToAmount(Ingredient ingredient, RecipeCategory category, ItemLike result, int amount) {
        return new SingleItemRecipeBuilder(category, StonecutterRecipe::new, ingredient, result, amount);
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, IndustrialHellscape.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }

}

    //---------- END OF RECIPE GENERATION METHODS ----------

