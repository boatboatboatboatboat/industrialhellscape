package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    //COPY AND PASTE FROM ITEM TAG OF THE SAME NAME FOR REVERSIBLE STONECUTTING. REMOVE THE BASE BLOCKS TO REMOVE RECIPE REDUNDANCY

    private static final List<ItemLike> VESSELPLATE_STONECUT_OUTPUT = List.of(
            //ModBlocks.VESSELPLATE.get().asItem(), //base block commented out or else duplicate recipe created
            ModBlocks.VESSELPLATE_PILLAR.get().asItem(),
            ModBlocks.RIVETED_VESSELPLATE.get().asItem(),
            ModBlocks.VESSELPLATE_GRATE_BLOCK.get().asItem(),
            ModBlocks.VESSELPLATE_GRATE.get().asItem(),
            ModBlocks.SEETHROUGH_GRATE.get().asItem(),
            ModBlocks.HORIZONTAL_RIVETED_VESSELPLATE.get().asItem(),
            ModBlocks.VERTICAL_RIVETED_VESSELPLATE.get().asItem(),
            ModBlocks.SMOOTH_VESSELPLATE.get().asItem(),
            ModBlocks.SMOOTH_VESSELPLATE_TILE.get().asItem(),

            ModBlocks.GRAY_VESSELPLATE.get().asItem(),
            ModBlocks.GRAY_RIVETED_VESSELPLATE.get().asItem(),
            ModBlocks.GRAY_VESSELPLATE_GRATE.get().asItem(),
            ModBlocks.GRAY_SEETHROUGH_GRATE.get().asItem(),
            ModBlocks.GRAY_HORIZONTAL_RIVETED_VESSELPLATE.get().asItem(),
            ModBlocks.GRAY_VERTICAL_RIVETED_VESSELPLATE.get().asItem(),
            ModBlocks.SMOOTH_GRAY_VESSELPLATE.get().asItem(),
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

            ModBlocks.VERTICAL_ENCASED_CABLES.get().asItem(),
            ModBlocks.HORIZONTAL_ENCASED_CABLES.get().asItem(),
            ModBlocks.RUSTY_VESSELPLATE_GRATE.get().asItem()
    );
    private static final List<ItemLike> ROCKRETE_STONECUT_OUTPUT = List.of(
            
            //ModBlocks.GRAY_ROCKRETE.get().asItem(), //base block commented out or else duplicate recipe created
            ModBlocks.GRAY_ROCKRETE_SLAB.get().asItem(),
            ModBlocks.GRAY_ROCKRETE_STAIRS.get().asItem(),
            ModBlocks.GRAY_ROCKRETE_REBAR.get().asItem(),
            ModBlocks.GRAY_ROCKRETE_PILLAR.get().asItem(),

            ModBlocks.HAZARD_STRIPE_YELLOW.get().asItem(),
            ModBlocks.HAZARD_STRIPE_RED.get().asItem(),

            ModBlocks.GRIMY_RESTROOM_TILE.get().asItem()
    );
    private static final List<ItemLike> VESSELGLASS_STONECUT_OUTPUT = List.of(
            //ModBlocks.VESSELGLASS.get().asItem(),
            ModBlocks.REINFORCED_VESSELGLASS.get().asItem(),
            ModBlocks.GRAY_REINFORCED_VESSELGLASS.get().asItem(),
            ModBlocks.GRAY_VESSELGLASS.get().asItem()
    );

    private static final List<ItemLike> PIPEWORKS_STONECUT_OUTPUT = List.of(
            //ModBlocks.PIPEWORKS.get()
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
            ModBlocks.GRAY_BOLTED_BRACKET.get().asItem(),
            ModBlocks.BLACK_BOLTED_BRACKET.get().asItem(),
            ModBlocks.YELLOW_TRIPOD.get().asItem(),
            ModBlocks.WORK_LIGHT_MOUNT.get().asItem(),
            ModBlocks.FLOOR_WORK_LIGHT.get().asItem()
    );
    private static final List<ItemLike> TECHNOLOGY_FURNITURE = List.of(
            ModBlocks.RETRO_COMPUTER.get().asItem(),
            ModBlocks.CASSETTE_PLAYER.get().asItem()
    );
    private static final List<ItemLike> AMENITY_FURNITURE = List.of(
            ModBlocks.DESK.get().asItem(),
            ModBlocks.DESK_DRAWER.get().asItem(),
            ModBlocks.METAL_DESK.get().asItem(),
            ModBlocks.METAL_DESK_DRAWER.get().asItem(),
            ModBlocks.METAL_DESK_DRAWER_2.get().asItem()
    );

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {

        //CREATE BASE MODDED BLOCKS FROM VANILLA BLOCKS
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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.GRAY_ROCKRETE.get(), 1)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Ingredient.of(ModTags.Items.IH_RECIPE_STONELIKES))
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter);

        //Create Furniture Kit Base Block from 1 stonetype, one log, one iron ingot, and the HAVEN device
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.IHEA_FURNITURE_KIT.get(), 1)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Ingredient.of(ModTags.Items.IH_RECIPE_STONELIKES))
                .requires(Ingredient.of(ItemTags.LOGS))
                .requires(Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter, new ResourceLocation("industrialhellscape", "furniture_kit_recipe_1"));
        //Create Furniture Kit Base Block from 1 stonetype, four planks, one iron ingot, and the HAVEN device
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.IHEA_FURNITURE_KIT.get(), 1)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())
                .requires(Ingredient.of(ModTags.Items.IH_RECIPE_STONELIKES))
                .requires(Ingredient.of(ItemTags.PLANKS))
                .requires(Ingredient.of(ItemTags.PLANKS))
                .requires(Ingredient.of(ItemTags.PLANKS))
                .requires(Ingredient.of(ItemTags.PLANKS))
                .requires(Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter, new ResourceLocation("industrialhellscape", "furniture_kit_recipe_2"));
        //Create Pipeworks block, base block for Pipes
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.PIPEWORKS.get(), 27) //3 per copper ingot
                .requires(Items.COPPER_BLOCK)
                .requires(ModItems.INHELL_HAVEN_DEVICE.get())

                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter, new ResourceLocation("industrialhellscape", "pipeworks"));

        //----------

        //ALL SOLID VARIANT BLOCKS CAN BE STONECUT BACK INTO BASE BLOCK
        //The item tags in this section should NOT contain slabs
        
        //Stonecut Recycle from ALL Smeltable Block Vesselplate Variants into base block
        stonecutting(
                Ingredient.of(ModTags.Items.VESSELPLATE_SMELTABLE_ITEM),
                RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.VESSELPLATE.get(),1)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter, new ResourceLocation("industrialhellscape", "vesselplate_stonecut_recycle"));

        //Stonecut Recycle from ALL Smeltable Block Vesselglass Variants into base block
        stonecutting(
                Ingredient.of(ModTags.Items.VESSELGLASS_SMELTABLE_ITEM),
                RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.VESSELGLASS.get(),1)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter, new ResourceLocation("industrialhellscape", "vesselglass_stonecut_recycle"));

        //Stonecut Recycle from ALL Smeltable Block Rockrete Variants into base block
        stonecutting(
                Ingredient.of(ModTags.Items.ROCKRETE_SMELTABLE_ITEM),
                RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.GRAY_ROCKRETE.get(),1)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter, new ResourceLocation("industrialhellscape", "rockrete_stonecut_recycle"));

        //Stonecut Recycle ALL Furniture across ALL categories into base block
        stonecutting(
                Ingredient.of(ModTags.Items.ALL_FURNITURE_ITEMS),
                RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.IHEA_FURNITURE_KIT.get(),1)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter, new ResourceLocation("industrialhellscape", "all_furniture_stonecut_recycle"));
        //Stonecut Recycle ALL Pipeworks blocks into the base block
        stonecutting(
                Ingredient.of(ModTags.Items.PIPEWORKS_SMELTABLE_ITEM),
                RecipeCategory.BUILDING_BLOCKS,
                ModBlocks.PIPEWORKS.get(),1)
                .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                .save(pWriter, new ResourceLocation("industrialhellscape", "pipeworks_stonecut_recycle"));

        //----------

        //SPECIAL ITEM CRAFTING

        //Create InHell HAVEN Device
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.INHELL_HAVEN_DEVICE.get())
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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VAPORWAVE_CASSETTE.get())
                .pattern(" A ")
                .pattern("BCB")
                .pattern("DDD")

                .define('A', ModItems.INHELL_HAVEN_DEVICE.get())
                .define('B', Items.IRON_NUGGET)
                .define('C', Items.QUARTZ)
                .define('D', Items.GLASS_PANE)

                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);

        //----------

        //Input block tag's stonecutter recipes gets iteratively registered to every block within that tag.

        //Vesselplate
        for (int i = 0; i < VESSELPLATE_STONECUT_OUTPUT.size(); i++) {
            String itemName = VESSELPLATE_STONECUT_OUTPUT.get(i).toString().replaceAll("[^a-zA-Z]+","_");
            if (VESSELPLATE_STONECUT_OUTPUT.get(i).toString().contains("slab") ) {
                stonecutting(
                        //Identify slab blocks and specify recipe output of two slab blocks
                        Ingredient.of(ModTags.Items.VESSELPLATE_STONECUT_OUTPUTS), //1st Parameter "pIngredients"
                        RecipeCategory.BUILDING_BLOCKS, //2nd Parameter "pCategory"
                        VESSELPLATE_STONECUT_OUTPUT.get(i), //3rd Parameter "pResults"
                        2) //4rth Parameter "amount" crafting output
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(pWriter, new ResourceLocation("industrialhellscape", "vesselplate_stonecut_"+itemName+i));
            } else
                stonecutting(
                        Ingredient.of(ModTags.Items.VESSELPLATE_STONECUT_OUTPUTS),
                        RecipeCategory.BUILDING_BLOCKS,
                        VESSELPLATE_STONECUT_OUTPUT.get(i),
                        1)
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(pWriter, new ResourceLocation("industrialhellscape", "vesselplate_stonecut_"+itemName+i));
        }

        //Vesselglass
        for (int i = 0; i < VESSELGLASS_STONECUT_OUTPUT.size(); i++) {
            String itemName = VESSELGLASS_STONECUT_OUTPUT.get(i).toString().replaceAll("[^a-zA-Z]+","_");
            if (VESSELGLASS_STONECUT_OUTPUT.get(i).toString().contains("slab")  ) {
                //Identify slab blocks and specify recipe output of two slab blocks
                stonecutting(
                        Ingredient.of(ModTags.Items.VESSELGLASS_STONECUT_OUTPUTS), //1st Parameter "pIngredients"
                        RecipeCategory.BUILDING_BLOCKS, //2nd Parameter "pCategory"
                        VESSELGLASS_STONECUT_OUTPUT.get(i), //3rd Parameter "pResults"
                        2) //4rth Parameter "amount" crafting output
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(pWriter, new ResourceLocation("industrialhellscape", "vesselglass_stonecut_"+itemName+i));
            } else

                stonecutting(
                        Ingredient.of(ModTags.Items.VESSELGLASS_STONECUT_OUTPUTS),
                        RecipeCategory.BUILDING_BLOCKS,
                        VESSELGLASS_STONECUT_OUTPUT.get(i),1)
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(pWriter, new ResourceLocation("industrialhellscape", "vesselglass_stonecut_"+itemName+i));
        }

        //Rockrete
        for (int i = 0; i < ROCKRETE_STONECUT_OUTPUT.size(); i++) {
            String itemName = ROCKRETE_STONECUT_OUTPUT.get(i).toString().replaceAll("[^a-zA-Z]+","_");
            if (ROCKRETE_STONECUT_OUTPUT.get(i).toString().contains("slab")  ) {
                stonecutting(
                        Ingredient.of(ModTags.Items.ROCKRETE_STONECUT_OUTPUTS),
                        RecipeCategory.BUILDING_BLOCKS,
                        ROCKRETE_STONECUT_OUTPUT.get(i),2)
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(pWriter, new ResourceLocation("industrialhellscape", "rockrete_stonecut_"+itemName+i));
            } else

                stonecutting(
                        Ingredient.of(ModTags.Items.ROCKRETE_STONECUT_OUTPUTS),
                        RecipeCategory.BUILDING_BLOCKS,
                        ROCKRETE_STONECUT_OUTPUT.get(i),1)
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(pWriter, new ResourceLocation("industrialhellscape", "rockrete_stonecut_"+itemName+i));
        }

        //Pipeworks
        for (int i = 0; i < PIPEWORKS_STONECUT_OUTPUT.size(); i++) {
            String itemName = PIPEWORKS_STONECUT_OUTPUT.get(i).toString().replaceAll("[^a-zA-Z]+","_");
            stonecutting(
                    Ingredient.of(ModBlocks.PIPEWORKS.get().asItem()),
                    RecipeCategory.BUILDING_BLOCKS,
                    PIPEWORKS_STONECUT_OUTPUT.get(i),1)
                    .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                    .save(pWriter, new ResourceLocation("industrialhellscape", "pipeworks_stonecut_"+itemName+i));
        }

        //Furniture Sub-Categories
        for (int i = 0; i < FURNITURE_CATEGORIES.size(); i++) {
            String itemName = FURNITURE_CATEGORIES.get(i).toString().replaceAll("[^a-zA-Z]+","_");
                stonecutting(
                        Ingredient.of(ModBlocks.IHEA_FURNITURE_KIT.get().asItem()), //Recipe Input Item
                        RecipeCategory.BUILDING_BLOCKS, //Category
                        FURNITURE_CATEGORIES.get(i),1)  //Outputs into the category blocks (safety, hygiene, industrial)
                        .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                        .save(pWriter, new ResourceLocation("industrialhellscape", "furniture_kit_to_stonecut_"+itemName+i));
        }
        //Safety Furniture Blocks
        for (int i = 0; i < SAFETY_FURNITURE.size(); i++) {
            String itemName = SAFETY_FURNITURE.get(i).toString().replaceAll("[^a-zA-Z]+","_");
            stonecutting(
                    Ingredient.of(ModBlocks.SAFETY_FURNISHINGS.get().asItem()), //Recipe Input Item
                    RecipeCategory.BUILDING_BLOCKS, //Category
                    SAFETY_FURNITURE.get(i),1)  //Outputs into the category blocks (safety, hygiene, industrial)
                    .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                    .save(pWriter, new ResourceLocation("industrialhellscape", "safety_to_stonecut_"+itemName+i));
        }
        //Hygiene Furniture Blocks
        for (int i = 0; i < HYGIENE_FURNITURE.size(); i++) {
            String itemName = HYGIENE_FURNITURE.get(i).toString().replaceAll("[^a-zA-Z]+","_");
            stonecutting(
                    Ingredient.of(ModBlocks.HYGIENE_FURNISHINGS.get().asItem()), //Recipe Input Item
                    RecipeCategory.BUILDING_BLOCKS, //Category
                    HYGIENE_FURNITURE.get(i),1)  //Outputs into the category blocks (safety, hygiene, industrial)
                    .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                    .save(pWriter, new ResourceLocation("industrialhellscape", "hygiene_to_stonecut_"+itemName+i));
        }

        //Industrial Furniture Blocks
        for (int i = 0; i < INDUSTRIAL_FURNITURE.size(); i++) {
            String itemName = INDUSTRIAL_FURNITURE.get(i).toString().replaceAll("[^a-zA-Z]+","_");
            stonecutting(
                    Ingredient.of(ModBlocks.INDUSTRIAL_FURNISHINGS.get().asItem()), //Recipe Input Item
                    RecipeCategory.BUILDING_BLOCKS, //Category
                    INDUSTRIAL_FURNITURE.get(i),1)  //Outputs into the category blocks (safety, hygiene, industrial)
                    .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                    .save(pWriter, new ResourceLocation("industrialhellscape", "industrial_to_stonecut_"+itemName+i));
        }



        //Technology Furniture Blocks
        for (int i = 0; i < TECHNOLOGY_FURNITURE.size(); i++) {
            String itemName = TECHNOLOGY_FURNITURE.get(i).toString().replaceAll("[^a-zA-Z]+","_");
            stonecutting(
                    Ingredient.of(ModBlocks.TECHNOLOGY_FURNISHINGS.get().asItem()), //Recipe Input Item
                    RecipeCategory.BUILDING_BLOCKS, //Category
                    TECHNOLOGY_FURNITURE.get(i),1)  //Outputs into the category blocks (safety, hygiene, industrial)
                    .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                    .save(pWriter, new ResourceLocation("industrialhellscape", "technology_to_stonecut_"+itemName+i));
        }
        //Amenity Furniture Blocks
        for (int i = 0; i < AMENITY_FURNITURE.size(); i++) {
            String itemName = AMENITY_FURNITURE.get(i).toString().replaceAll("[^a-zA-Z]+","_");
            stonecutting(
                    Ingredient.of(ModBlocks.AMENITY_FURNISHINGS.get().asItem()), //Recipe Input Item
                    RecipeCategory.BUILDING_BLOCKS, //Category
                    AMENITY_FURNITURE.get(i),1)  //Outputs into the category blocks (safety, hygiene, industrial)
                    .unlockedBy(getHasName(ModItems.INHELL_HAVEN_DEVICE.get()), has(ModItems.INHELL_HAVEN_DEVICE.get()))
                    .save(pWriter, new ResourceLocation("industrialhellscape", "amenity_to_stonecut_"+itemName+i));
        }

    }

    protected static SingleItemRecipeBuilder stonecutting(Ingredient pIngredients, RecipeCategory pCategory, ItemLike pResult, Integer amount) {
        return new SingleItemRecipeBuilder(pCategory, RecipeSerializer.STONECUTTER, pIngredients, pResult, amount);
    }
}
