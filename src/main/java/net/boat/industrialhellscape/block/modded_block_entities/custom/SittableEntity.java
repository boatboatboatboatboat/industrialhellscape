package net.boat.industrialhellscape.block.modded_block_entities.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class SittableEntity extends Entity {
    public SittableEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
    }

    @Override
    //When player stops sitting on the sitting entity, kill it to prevent ghost entities.
    protected void removePassenger(Entity pPassenger) {
        super.removePassenger(pPassenger);
        this.kill();
    }
}