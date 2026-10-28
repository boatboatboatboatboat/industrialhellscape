package net.boat.industrialhellscape.block.block_experiments.LandMineBlocks;

import net.boat.industrialhellscape.block.block_entities.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class LandmineBE extends BlockEntity implements ExplosiveInterface {
    //Flag for tick-scheduling and explosion check
    public boolean isArmed = false;

    //Data for explosion killfeed recording (anti-griefing)
    //public LivingEntity owner;
    //https://www.baeldung.com/java-uuid | Default/fallback value is all zeroes instead of null
    public UUID owner = UUID.fromString("00000000-0000-0000-0000-000000000000"); //null makes crash when block falls and lands

    public LandmineBE(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.LANDMINE_BE.get(), pos, blockState);
    }

    //----- Data Stuff -----

    // Read values from the passed CompoundTag here.
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.isArmed = tag.getBoolean("armed");
        this.owner = tag.getUUID("owner");
    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("armed", this.isArmed);
        tag.putUUID("owner", this.owner);
    }


    //----- End of Data Stuff -----

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
    public CompoundTag tagBE(Level level) {
        return this.saveCustomOnly(level.registryAccess());
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
