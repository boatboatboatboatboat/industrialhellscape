package net.boat.industrialhellscape.block.block_experiments.LandMineBlocks;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.block_entities.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BuriedMineBE extends BrushableBlockEntity implements ExplosiveInterface {
    private ResourceKey<LootTable> customLootTable = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, "archaeology/very_suspicious_sand"));
    public UUID owner = null;
    public Item buriedItem = Items.AIR;

    //Flag for tick-scheduling sound-playing delay
    public boolean isArmed = false;

    public BuriedMineBE(BlockPos pos, BlockState blockState) {
        super(pos, blockState);

        this.setLootTable(
                customLootTable,
                0);
    }

    //----- Overrides of Vanilla BrushableBlockEntity Methods -----
    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.BRUSHABLE_BE.get();
    }

//    @Override
//    public ItemStack getItem() {
//        ItemStack item = ModItems.GAS_STATION_PILL.toStack();
//        return item;
//    }
    //----- End of Overrides of Vanilla BrushableBlockEntity Methods -----

    //---

    //----- Data Stuff -----

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("armed", this.isArmed);
        tag.putUUID("owner", this.owner);
        tag.putString("buried_item", this.buriedItem.toString());
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.isArmed = tag.getBoolean("armed");
        this.owner = tag.getUUID("owner");
        ResourceLocation itemPath = ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID,tag.getString("buried_item"));
        Item item = BuiltInRegistries.ITEM.get(itemPath); //attempt to get an item using resource location industrialhellscape:xyz
        this.buriedItem = item;
    }


    //----- End of Data Stuff -----

    //---

    //----- ExplosiveInterface method overrides -----

    /*
        Used by armable explosive block entities to
        communicate with respective blocks for appropriate
        tick scheduling of fuse arm/disarm countdown.
     */

    @Override
    public boolean getArmingStatusOfBE() {
        return this.isArmed;
    }

    @Override
    public @Nullable UUID getOwnerOfBE() {
        return owner;
    }

    @Override
    public boolean isArmed() {
        /*
            Allows block implementing ExplosiveInterface
            to read flag from block entity
        */
        return this.isArmed;
    }

    @Override
    public void setFlagArmed() {
        /*
            Allows block implementing ExplosiveInterface
            to change block entity's flag to true
        */
        isArmed = true;
    }

    @Override
    public void setFlagDisarmed() {
        /*
            Allows block implementing ExplosiveInterface
            to change block entity's flag to false
        */
        isArmed = false;
    }

    @Override
    public void assignExplosiveOwner(UUID explosivePlacer) {
        /*
            Allows block implementing ExplosiveInterface
            to set block entity's owner
        */
        this.owner = explosivePlacer;
    }
    // ----- End 0f ExplosiveInterface method overrides -----
}
