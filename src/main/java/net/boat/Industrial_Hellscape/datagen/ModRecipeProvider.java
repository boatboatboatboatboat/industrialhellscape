package net.boat.Industrial_Hellscape.datagen;

import net.boat.Industrial_Hellscape.IndustrialHellscape;
import net.boat.Industrial_Hellscape.block.ModBlocks;
import net.boat.Industrial_Hellscape.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> VESSELPLATE_SMELTABLES = List.of(ModItems.FLOPPY_DISKETTE.get(), //Output of smelting
            ModBlocks.VESSELPLATE_GRATE_BLOCK.get());


    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {

        oreSmelting(pWriter, VESSELPLATE_SMELTABLES, RecipeCategory.MISC, Items.IRON_INGOT, 0.0f, 200, "Vesselplate Recycling");
        oreBlasting(pWriter, VESSELPLATE_SMELTABLES, RecipeCategory.MISC, Items.IRON_INGOT, 0.0f, 100, "Vesselplate Recycling");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SOUND_BLOCK.get()) //3x3 recipe for Sound Block from Floppy Diskettes
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                //Add more shaped recipes here
                .define('S', ModItems.FLOPPY_DISKETTE.get())
                .unlockedBy(getHasName(ModItems.FLOPPY_DISKETTE.get()), has(ModItems.FLOPPY_DISKETTE.get()))
                        .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.VESSELPLATE.get(), 9) //Vesselplate x9 base item recipe using iron block and non-consumeable multitool
                .requires(ModItems.MALEVOLENT_MULTITOOL.get())
                .requires(Items.IRON_BLOCK)
                .unlockedBy(getHasName(ModItems.MALEVOLENT_MULTITOOL.get()), has(ModItems.MALEVOLENT_MULTITOOL.get()))
                .save(pWriter);


    }


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
}
