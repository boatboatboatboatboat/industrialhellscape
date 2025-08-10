package net.boat.industrialhellscape.block.special_blocks.StorageBlock;

import net.boat.industrialhellscape.block.special_blocks_properties.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class Test9SlotStorageBlock extends BarrelBlock {
    public Test9SlotStorageBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return ModBlockEntities.STORAGE_18_BLOCK_ENTITY.get().create(pPos, pState);
    }
}
