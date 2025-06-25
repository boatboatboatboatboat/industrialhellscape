package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> INHELL_VESSELPLATE_RECYCLING = List.of(
            ModBlocks.VESSELPLATE.get(),
            ModBlocks.VESSELPLATE_GRATE_BLOCK.get()
    );
    private static final List<ItemLike> INHELL_ROCKRETE_RECYCLING = List.of(
            ModBlocks.GRAY_ROCKRETE.get(),
            ModBlocks.LIGHT_GRAY_ROCKRETE.get(),
            ModBlocks.WHITE_ROCKRETE.get(),
            ModBlocks.BLACK_ROCKRETE.get(),

            ModBlocks.HAZARD_STRIPE_YELLOW.get(),
            ModBlocks.HAZARD_STRIPE_RED.get()
    );


    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {


        //Melt Rockrete to base material
        //oreSmelting(pWriter, INHELL_ROCKRETE_RECYCLING, RecipeCategory.MISC, Items.STONE, 0.0f, 200, "Rockrete Recycling");
        //oreBlasting(pWriter, INHELL_ROCKRETE_RECYCLING, RecipeCategory.MISC, Items.STONE, 0.0f, 100, "Rockrete Recycling");

        //Stonecut Vesselplate into Vesselplate Variants

        /*stonecutting(Ingredient.of(ModBlocks.VESSELPLATE.get())
                ,RecipeCategory.BUILDING_BLOCKS, ModBlocks.VESSELPLATE.get())
                .unlockedBy(getHasName(ModBlocks.VESSELPLATE.get()), has(ModBlocks.VESSELPLATE.get()))
                .save(pWriter);
        */

        stonecutting(Ingredient.of(ModBlocks.VESSELPLATE.get())
                ,RecipeCategory.BUILDING_BLOCKS, ModBlocks.VESSELPLATE_GRATE_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.VESSELPLATE.get()), has(ModBlocks.VESSELPLATE.get()))
                .save(pWriter);

        //Create Sound Block
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOUND_BLOCK.get()) //Recipe for Sound Block using 9 Floppy Diskettes
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.FLOPPY_DISKETTE.get())
                .unlockedBy(getHasName(ModItems.FLOPPY_DISKETTE.get()), has(ModItems.FLOPPY_DISKETTE.get()))
                        .save(pWriter);

        //Create 9x Vesselplate Base Block from 9 iron ingots
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.VESSELPLATE.get(), 9)
                .requires(ModItems.MALEVOLENT_MULTITOOL.get())
                .requires(Items.IRON_BLOCK)
                .unlockedBy(getHasName(ModItems.MALEVOLENT_MULTITOOL.get()), has(ModItems.MALEVOLENT_MULTITOOL.get()))
                .save(pWriter);

        //Create Rockrete Base Block from 1 stone
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.LIGHT_GRAY_ROCKRETE.get(), 1)
                .requires(ModItems.MALEVOLENT_MULTITOOL.get())
                .requires(Items.STONE)
                .unlockedBy(getHasName(ModItems.MALEVOLENT_MULTITOOL.get()), has(ModItems.MALEVOLENT_MULTITOOL.get()))
                .save(pWriter);

    }   //End of recipe generation

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                    pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, IndustrialHellscape.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }

    protected static SingleItemRecipeBuilder stonecutting(Ingredient pIngredients, RecipeCategory pCategory, ItemLike pResult) {
        return new SingleItemRecipeBuilder(pCategory, RecipeSerializer.STONECUTTER, pIngredients, pResult, 1);
    }
}
