#  Industrial Hellscape - A Java Minecraft 1.20.1 mod

A Java Minecraft mod that adds gritty industrial-themed decorative blocks.


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
