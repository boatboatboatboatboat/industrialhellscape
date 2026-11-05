# Minecraft Mod - Industrial Hellscape - Neoforge 1.21.1

Boat's Decor Mod
- As of 27/10/26: actually_main_1.21.1 is now up-to-date with other branches.

# Disclaimer
- This mod is in development as a hobby. Its scope may change, and that may come with updates to textures/palettes, id-remapping, or block state changes.
	- Textures may be improved or tweaked at any time whenever I feel like it because I am an artist.
	- Block/Item ID changes may occur to improve readability, make more sense in general, fit in with new content, and/or fit in with standard naming conventions if it did not adhere to one before. These changes will be properly remapped/aliased so that existing worlds updated will not experience any changes. I cannot guarantee that id-remapping will be harmless if you implement my mod's contents in complex manners beyond its role as a simple decor and building mod. Change-logs will inform of these events as best as I can should this occur.
	- Rarely will block-states or block placement logic change dramatically for similar reasons as above. It has happened before; blocks existing in a world will either be misaligned in placement direction or fallback to default block-states. I heard this can be resolved with Data Fixers, but NeoForge documentations claims it does not support Data Fixers.
- I make no promises to back-port or port to other versions. There is one coder and one tester, and both of those are me.

# CTM / Fusion Support (Built-In Resource Packs)

 - **Missing Textures will appear** in this mod by default unless you enable either the built-in Fusion or CTM resource pack.
 - **Only one mod (CTM or Fusion) is necessary** as an optional dependency. 
 - **Only one resource pack (CTM or Fusion)** is required to be enabled.

# Tags

## Block Tags
Most Block Tags are data-generated from hard-coded lists of Blocks used in stone-cutting (output) recipe data-generation in ModRecipeProvider.

## Item Tags

### Recipe Tags
- #ih_recipe_stones - For stone blocks that can be used to craft blocks in the crafting table:
	- Included by default: Stone, Granite, Andesite, Diorite, Deepslate, Blackstone, End Stone, and cobbled stones.
	
	- Rockrete is able to be smelted down into Stone regardless of the stone-like block used to craft it initially. This is considered acceptable, as it is either equivalent or a downgrade in material properties from the possible stone-like blocks used to craft it.
- #ih_recipe_ingots - For metal ingots that can be used to craft blocks in the crafting table:
	- Included by default: Iron Ingot.
	
	- Vesselplate is able to be smelted down into Iron Ingot regardless of the ingot used to craft it initially. If you add a metal to this tag, **consider removing Vesselplate smelting recipes** to avoid metal transmutation in a modpack. 

### General Item Tags
- Most Item Tags are copied from Block Tags. Afterwards, a stream filter is applied to remove and blacklist non-full blocks (Slabs, Stairs, Parapets, Barrier blocks) from the tag. Item tags are used in stone-cutting recipe data-generation as "input" ingredients. Filtering like this avoids stonecutting one slab (input) into two slabs (output), for example.

![Mod logo image.](/src/main/resources/logo_for_mods_toml_file.png "Mod logo image")
