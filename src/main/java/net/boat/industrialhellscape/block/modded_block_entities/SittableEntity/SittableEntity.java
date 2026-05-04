package net.boat.industrialhellscape.block.modded_block_entities.SittableEntity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SittableEntity extends Entity {
    public SittableEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
    }

    @Override
    //When player stops sitting on the sitting entity, kill it to prevent ghost entities.
    protected void removePassenger(@NotNull Entity pPassenger) {
        super.removePassenger(pPassenger);
        this.kill();
    }
}