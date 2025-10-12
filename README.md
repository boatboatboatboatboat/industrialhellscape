
#  Industrial Hellscape - A Java Minecraft 1.20.1 mod

A Java Minecraft mod that adds gritty industrial-themed decorative blocks.
I encourage feedback on how to make this documentation more clear for new Java mod-creators.


# Files

## Data-generating classes:
 - src/main/java/net/boat/industrialhellscape/**datagen** folder
	 - **ModBlockStateAndModelProvider** generates block-state, block model, and item model .json files when appropriate for the block.
		 - Some blocks have either of these manually written in cases where block-states are too complicated to generate or block models have special rendering conditions (translucency/cutout). Appropriate methods are used for such cases.
		 - Methods are notated with the letters "S", "B", and/or "I" corresponding to whether or not they generate block-state files, block models, or item models. respectively.
	 - **ModItemModelProvider** generates item models from textures (for purely items, not block items).
	 - **ModBlockTagGenerator** generates block tag files containing lists of blocks within (to then be tagged under vanilla block tags, such as tool-mining and bed tags).
	 - **ModItemTagGenerator** generates item tag files containing lists of both items and block items within (for recipes).
	 - **ModRecipeProvider** generates recipe .json files for all content in the mod. Most recipes use item tags as a recipe input to allow blocks to be interchangeably stonecut into each other.
	 - **ModLootTableProvider** generates loot tables for mined blocks. Most blocks simply drop themselves when mined with any correct tool, except for certain special behaviors with multi-part blocks (Railings, To-Be-Implemented).

## Modded Blocks
 - src/main/java/net/boat/industrialhellscape/**block** folder
	 - **ModBlocks** is used for registering modded blocks. 
	 - **modded_block_classes** is a list of block classes used in this mod, and subfolders organizing them.
	 - **modded_block_entities** contains Block Entity class(es), along with both the Block Entity and BE menu registration class. The menu class is currently unused. The entity class used for sitting on blocks is included here although it is strictly speaking not a block entity. Its seperate registration and rendering classes are present here.
	 - **modded_block_state_properties** contains custom enums. These enums are used for modded block state properties.
	 - **modded_interfaces** contains interface classes. 
		 - Some of these classes store commonly-used functions between certain modded blocks (suffixed with -Capabilities).
	- **modded_logic_enums** contains any other custom enums that are NOT used as block-state properties. 
	 - HitboxGeometryCollection stores VoxelShape hitboxes for blocks in one place.
	 - RotationHelper is used in many block classes to rotate the hitboxes along the cardinal directions to be placed conforming to the block model geometry.

## Modded Items
 - src/main/java/net/boat/industrialhellscape/**item** folder
	 - **modded_items** contains item classes
	 - ModCreativeModTabs - Creative mode tab registration for all modded content
	 - ModItems - Item registration

## Modded Sounds

 - src/main/java/net/boat/industrialhellscape/**sound** folder
	 - ModSounds
		 - Registers SoundEvent(s) (using custom sound files imported to mod assets)
		 - Registers ForgeSoundType(s), collections of SoundEvents used for in-world block interaction like placing or mining.

## Modded Tags

 - src/main/java/net/boat/industrialhellscape/util/**ModTags.java**
	 - Registers block and item tags.

## Mod Startup and Events

 - src/main/java/net/boat/industrialhellscape/**IndustrialHellscape.java**
	 - Aside from what is minimally necessary for the mod to function,
		 - Via MissingMappingsEvent(s), Removed content is handled here in order for updated Minecraft instances to safely function. Old content is replaced with existing content. Blocks in-world will be seamlessly replaced. Items and block items in inventories and chests will be replaced with the substitute.


# I Want to Use Your Code for My Own Mods

You can probably download this whole repo and delete the registered content in
- ModBlocks
- ModItems
- ModcreativeTab
- ModTags
- ModSounds

Along with all files inside of the assets folder. 
Change the mod namespace, mod name, and information in the mods.toml file to your mod.

The other way is to selectively download the java classes you want to incorporate into your mod:

## Importing the correct content:
### If you're here, you're probably focusing on the block classes since those are the bulk of InHell's content.

 - This is a bit of a complicated process. Copy over the folders into your mod environment:
	 - **modded_block_state_properties**
	 - **modded_interfaces**
	 - **modded_logic_enums**
 -  into your **block** folder is a good start.
 - You will need to rename any imported packages/folder paths to your name and your mod's name. Maybe there is a way to do it on-mass:
	 - Rename net.**boat.industrialhellscape**.block.modded_logic_enums; into
		 - net.**alex**.**alexsdecormod**.block.modded_logic_enums;
	 - Rename any mention of the main mod class: net.**boat.industrialhellscape.IndustrialHellscape**; into
		 - net.**johndoe.johnsamazingmod**.JohnsAmazingMod 
 - If you resolved all errors by renaming everything. You can do the same process with the **modded_block_entities** folder. 
	 - You will find errors. Some of them may be resolved by commenting out the any code blocks that I commented as deprecated. Others will be resolved by copying over the modded_block_classes/ContainerBlocks folder and renaming imports.

The manual renaming will be an inconvenience, but in the end, it should be a self-contained process so you can remove those folders if you don't like it.

 - Although not strictly necessary: Note that in your main mod class, you need to include modded entities in the ClientModEvents. There is only one applicable entity here if you're using my code. It is the chair, or the SittableEntity for player-seatable blocks, found in **modded_block_entities**. You may neglect registering the EntityRenderer for now until you actually implement a sittable block (if you do and don't include that code line, you will crash when right-clicking to sit on the block).


        @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)  
        public static class ClientModEvents {  
            @SubscribeEvent  
          public static void onClientSetup(FMLClientSetupEvent event) {  
                EntityRenderers.register(ModEntities.CHAIR.get(), SittableEntityRenderer::new);  
	    }  
       }
 - After this, you should be familiar with renaming imported packages. You can copy over any of the modded_block_classes files over to your mod and rename their imports to your mod's name. Or continue to use them as a reference and build your own block classes from the ground up.

As much as I would love custom block classes to be something that can be copy/pasted over once to a different mod and be done with it, it is almost never the case if you want a well-written mod, or have advanced functionality. In the mean time, devs can provide the best documentation and instruction they can. I hope that this README at least guides you towards understanding what is going on within my mod's code.

