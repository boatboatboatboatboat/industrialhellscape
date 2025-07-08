package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    //COPY AND PASTE FROM ITEM TAG OF THE SAME NAME FOR REVERSIBLE STONECUTTING. REMOVE THE BASE BLOCKS TO REMOVE RECIPE REDUNDANCY
    private static final List<ItemLike> VESSELPLATE_STONECUT_OUTPUT = List.of(
            //ModBlocks.VESSELPLATE.get().asItem(),
            ModBlocks.VESSELPLATE_PILLAR.get().asItem(),
            ModBlocks.VESSELPLATE_GRATE_BLOCK.get().asItem(),
            ModBlocks.VESSELPLATE_GRATE.get().asItem(),
            ModBlocks.RUSTY_VESSELPLATE_GRATE.get().asItem(),
            ModBlocks.HORIZONTAL_RIVETED_VESSELPLATE.get().asItem(),
            ModBlocks.VERTICAL_RIVETED_VESSELPLATE.get().asItem(),
            ModBlocks.SMOOTH_VESSELPLATE.get().asItem(),
            ModBlocks.SMOOTH_VESSELPLATE_TILE.get().asItem(),
            ModBlocks.STRUT.get().asItem(),
            ModBlocks.CATWALK_STRUT.get().asItem(),
            ModBlocks.CATWALK_STRUT_SLAB.get().asItem(),
            ModBlocks.CATWALK_STRUT_STAIRS.get().asItem(),
            ModBlocks.STRUT_STAIRS.get().asItem(),
            ModBlocks.STRUT_SLAB.get().asItem(),
            ModBlocks.RUSTY_VESSELPLATE_GRATE.get().asItem()
    );
    private static final List<ItemLike> ROCKRETE_STONECUT_OUTPUT = List.of(
            ModBlocks.BUNKER_WALL.get().asItem(),

            //ModBlocks.LIGHT_GRAY_ROCKRETE.get().asItem(),
            ModBlocks.LIGHT_GRAY_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.LIGHT_GRAY_ROCKRETE_STAIRS.get().asItem(),
            ModBlocks.LIGHT_GRAY_ROCKRETE_REBAR.get().asItem(),

            ModBlocks.HAZARD_STRIPE_YELLOW.get().asItem(),
            ModBlocks.HAZARD_STRIPE_RED.get().asItem()
    );
    private static final List<ItemLike> VESSELGLASS_STONECUT_OUTPUT = List.of(
            //ModBlocks.VESSELGLASS.get().asItem(),
            ModBlocks.REINFORCED_VESSELGLASS.get().asItem()
    );

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        //ALL SOLID VARIANT BLOCKS CAN BE STONECUT BACK INTO BASE BLOCK

        //Stonecut Recycle from ALL Smeltable Block Vesselplate Variants into default Vesselplate
        stonecutting(
                Ingredient.of(ModTags.Items.VESSELPLATE_SMELTABLE_ITEM),
                RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.VESSELPLATE.get(),1)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter, new ResourceLocation("industrialhellscape", "vesselplate_stonecut_recycle"));

        //Stonecut Recycle from ALL Smeltable Block Vesselglass Variants
        stonecutting(
                Ingredient.of(ModTags.Items.VESSELGLASS_SMELTABLE_ITEM),
                RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.VESSELGLASS.get(),1)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter, new ResourceLocation("industrialhellscape", "vesselglass_stonecut_recycle"));

        //Stonecut Recycle from ALL Smeltable Block Rockrete Variants
        stonecutting(
                Ingredient.of(ModTags.Items.ROCKRETE_SMELTABLE_ITEM),
                RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.LIGHT_GRAY_ROCKRETE.get(),1)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter, new ResourceLocation("industrialhellscape", "rockrete_stonecut_recycle"));

        //----------

        //Create InHell HAVEN Device
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.INHELL_HAVEN_DEVICE.get()) //Recipe for Sound Block using 9 Floppy Diskettes
                .pattern("ABC")
                .pattern("DDD")
                .pattern("ABC")

                .define('A', Items.IRON_INGOT)
                .define('B', Items.COPPER_INGOT)
                .define('C', Items.GOLD_INGOT)
                .define('D', Items.QUARTZ)

                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                        .save(pWriter);

        //Create Vaporwave Cassette
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VAPORWAVE_CASSETTE.get()) //Recipe for Sound Block using 9 Floppy Diskettes
                .pattern(" A ")
                .pattern("BCB")
                .pattern("DDD")

                .define('A', ModItems.INHELL_HAVEN_DEVICE.get())
                .define('B', Items.IRON_NUGGET)
                .define('C', Items.QUARTZ)
                .define('D', Items.GLASS_PANE)

                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);

        //Create Base Modded Blocks from Vanilla Blocks

        //Create 9x Vesselplate Base Block from 9 iron ingots
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.VESSELPLATE.get(), 9)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Items.IRON_BLOCK)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter);
        //Create 1x Vesselglass Base Block from 1 Vesselplate and 1 glass
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.VESSELGLASS.get(), 1)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(ModBlocks.VESSELPLATE.get())
                .requires(Items.GLASS)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter);
        //Create Rockrete Base Block from 1 stone
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.LIGHT_GRAY_ROCKRETE.get(), 1)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Items.STONE)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter);

        //----------


        //Input block tag's stonecutter recipes gets iteratively registered to every block within that tag.
        //Vesselplate

        //   protected static SingleItemRecipeBuilder stonecutting(Ingredient pIngredients, RecipeCategory pCategory, ItemLike pResult, Integer amount) {
        //        return new SingleItemRecipeBuilder(pCategory, RecipeSerializer.STONECUTTER, pIngredients, pResult, amount);
        //    }


        for (int i = 0; i < VESSELPLATE_STONECUT_OUTPUT.size(); i++) {
            if (VESSELPLATE_STONECUT_OUTPUT.get(i).toString().contains("slab") ) {
                stonecutting(
                        Ingredient.of(ModTags.Items.VESSELPLATE_STONECUT_OUTPUTS),
                        RecipeCategory.BUILDING_BLOCKS,
                        VESSELPLATE_STONECUT_OUTPUT.get(i),2)
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(pWriter, new ResourceLocation("industrialhellscape", "vesselplate_stonecut_"+i+"slab"));

            } else if(VESSELPLATE_STONECUT_OUTPUT.get(i).toString().contains("strut")) {
                stonecutting(
                        Ingredient.of(ModTags.Items.VESSELPLATE_STONECUT_OUTPUTS),
                        RecipeCategory.BUILDING_BLOCKS,
                        VESSELPLATE_STONECUT_OUTPUT.get(i),3)
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(pWriter, new ResourceLocation("industrialhellscape", "vesselplate_stonecut_"+i+"strut"));
            } else

                stonecutting(
                        Ingredient.of(ModTags.Items.VESSELPLATE_STONECUT_OUTPUTS),
                        RecipeCategory.BUILDING_BLOCKS,
                        VESSELPLATE_STONECUT_OUTPUT.get(i),1)
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(pWriter, new ResourceLocation("industrialhellscape", "vesselplate_stonecut_"+i));
        }

        //Vesselglass
        for (int i = 0; i < VESSELGLASS_STONECUT_OUTPUT.size(); i++) {
            if (VESSELGLASS_STONECUT_OUTPUT.get(i).toString().contains("slab")  ) {
                stonecutting(
                        Ingredient.of(ModTags.Items.VESSELGLASS_STONECUT_OUTPUTS),
                        RecipeCategory.BUILDING_BLOCKS,
                        VESSELGLASS_STONECUT_OUTPUT.get(i),2)
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(pWriter, new ResourceLocation("industrialhellscape", "vesselglass_stonecut_"+i+"slab"));
            } else

                stonecutting(
                        Ingredient.of(ModTags.Items.VESSELGLASS_STONECUT_OUTPUTS),
                        RecipeCategory.BUILDING_BLOCKS,
                        VESSELGLASS_STONECUT_OUTPUT.get(i),1)
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(pWriter, new ResourceLocation("industrialhellscape", "vesselglass_stonecut_"+i));
        }
        //Rockrete
        for (int i = 0; i < ROCKRETE_STONECUT_OUTPUT.size(); i++) {
            if (ROCKRETE_STONECUT_OUTPUT.get(i).toString().contains("slab")  ) {
                stonecutting(
                        Ingredient.of(ModTags.Items.ROCKRETE_STONECUT_OUTPUTS),
                        RecipeCategory.BUILDING_BLOCKS,
                        ROCKRETE_STONECUT_OUTPUT.get(i),2)
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(pWriter, new ResourceLocation("industrialhellscape", "rockrete_stonecut_"+i+"slab"));
            } else

                stonecutting(
                        Ingredient.of(ModTags.Items.ROCKRETE_STONECUT_OUTPUTS),
                        RecipeCategory.BUILDING_BLOCKS,
                        ROCKRETE_STONECUT_OUTPUT.get(i),1)
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(pWriter, new ResourceLocation("industrialhellscape", "rockrete_stonecut_"+i));
        }



        //Create Hazard Stripe Yellow (2x)
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.HAZARD_STRIPE_YELLOW.get(), 2)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Ingredient.of(ModTags.Items.VESSELPLATE_SMELTABLE_ITEM))
                .requires(Ingredient.of(ModTags.Items.VESSELPLATE_SMELTABLE_ITEM))
                .requires(Items.YELLOW_DYE)
                .requires(Items.BLACK_DYE)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter);
        //Create Hazard Stripe Red (2x)
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.HAZARD_STRIPE_RED.get(), 2)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Ingredient.of(ModTags.Items.VESSELPLATE_SMELTABLE_ITEM))
                .requires(Ingredient.of(ModTags.Items.VESSELPLATE_SMELTABLE_ITEM))
                .requires(Items.RED_DYE)
                .requires(Items.WHITE_DYE)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
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

    protected static SingleItemRecipeBuilder stonecutting(Ingredient pIngredients, RecipeCategory pCategory, ItemLike pResult, Integer amount) {
        return new SingleItemRecipeBuilder(pCategory, RecipeSerializer.STONECUTTER, pIngredients, pResult, amount);
    }

}
