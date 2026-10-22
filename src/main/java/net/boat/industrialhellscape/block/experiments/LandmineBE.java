package net.boat.industrialhellscape.block.experiments;

import net.boat.industrialhellscape.block.block_entities.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class LandmineBE extends BlockEntity implements ExplosiveInterface {
    //Flag for tick-scheduling and explosion check
    public boolean isArmed = false;

    //Data for explosion killfeed recording (anti-griefing)
    public LivingEntity owner;

    public LandmineBE(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.LANDMINE_BE.get(), pos, blockState);
    }

    //----- Data Stuff -----

    // Read values from the passed CompoundTag here.
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        // Will default to 0 if absent. See the NBT article for more information.
        this.isArmed = tag.getBoolean("armed");
    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("armed", this.isArmed);
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
    public @Nullable LivingEntity getOwnerOfBE() {
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
    public void assignExplosiveOwner(LivingEntity explosivePlacer) {
        /*
            Allows block implementing ExplosiveInterface
            to set block entity's owner
        */
        this.owner = explosivePlacer;
    }
    // ----- End 0f ExplosiveInterface method overrides -----
}
