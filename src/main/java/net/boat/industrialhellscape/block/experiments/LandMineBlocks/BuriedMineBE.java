package net.boat.industrialhellscape.block.experiments.LandMineBlocks;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.block_entities.ModBlockEntities;
import net.boat.industrialhellscape.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BuriedMineBE extends BrushableBlockEntity implements ExplosiveInterface {
    private ResourceKey<LootTable> customLootTable = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, "archaeology/very_suspicious_sand"));
    public UUID owner = null;

    //Flag for tick-scheduling sound-playing delay
    public boolean hasScheduledArming = false;

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

    @Override //doesnt work for BER
    public ItemStack getItem() {
        ItemStack item = ModItems.GAS_STATION_PILL.toStack();
        return item;
    }
    //----- End of Overrides of Vanilla BrushableBlockEntity Methods -----

    //---

    //----- ExplosiveInterface method overrides -----

    /*
        Used by armable explosive block entities to
        communicate with respective blocks for appropriate
        tick scheduling of fuse arm/disarm countdown.
     */

    @Override
    public boolean getArmingStatusOfBE() {
        return this.hasScheduledArming;
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
        return this.hasScheduledArming;
    }

    @Override
    public void setFlagArmed() {
        /*
            Allows block implementing ExplosiveInterface
            to change block entity's flag to true
        */
        hasScheduledArming = true;
    }

    @Override
    public void setFlagDisarmed() {
        /*
            Allows block implementing ExplosiveInterface
            to change block entity's flag to false
        */
        hasScheduledArming = false;
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
