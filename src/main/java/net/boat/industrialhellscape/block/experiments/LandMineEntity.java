package net.boat.industrialhellscape.block.experiments;

import net.boat.industrialhellscape.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LandMineEntity extends Entity implements TraceableEntity, ExplosiveInterface {
    private static final EntityDataAccessor<BlockState> DATA_BLOCK_STATE_ID = SynchedEntityData.defineId(PrimedTnt .class, EntityDataSerializers.BLOCK_STATE);;
    @javax.annotation.Nullable
    public LivingEntity owner;

    public LandMineEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public BlockState getBlockState() {
        return (BlockState)this.entityData.get(DATA_BLOCK_STATE_ID);
    }

    public void setBlockState(BlockState blockState) {
        this.entityData.set(DATA_BLOCK_STATE_ID, blockState);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        builder.define(DATA_BLOCK_STATE_ID, ModBlocks.METALWORKS.get().defaultBlockState());
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        if (compoundTag.contains("block_state", 10)) { //why is it 10
            this.setBlockState(NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), compoundTag.getCompound("block_state")));
        }
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        compoundTag.put("block_state", NbtUtils.writeBlockState(this.getBlockState()));
    }

    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public @Nullable Entity getOwner() {
        return this.owner;
    }
}
