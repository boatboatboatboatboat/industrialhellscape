package net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks;

import net.boat.industrialhellscape.block.modded_logic_enums.MultiBlockPlacementDirection;
import net.boat.industrialhellscape.block.modded_block_entities.ModEntities;
import net.boat.industrialhellscape.block.modded_block_entities.SittableEntity.SittableEntity;
import net.boat.industrialhellscape.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class InteractableModelled2BMBlock extends Modelled2BMBlock {
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public InteractableModelled2BMBlock(Properties pProperties, MultiBlockPlacementDirection multiBlockPlacementDirection, VoxelShape hitboxPositiveShape, VoxelShape hitboxNegativeShape) {
        super(pProperties, multiBlockPlacementDirection, hitboxPositiveShape, hitboxNegativeShape);
    }

    public @Nonnull InteractionResult use(@Nonnull BlockState pState, @Nonnull Level pLevel, @Nonnull BlockPos pPos, Player pPlayer, @Nonnull InteractionHand pHand, @Nonnull BlockHitResult pHit) {
        boolean hasEmptyHands = pPlayer.getMainHandItem().isEmpty() && pPlayer.getOffhandItem().isEmpty();

        if(pPlayer.isSecondaryUseActive() && hasEmptyHands ) { //If player IS shift-interacting and has empty hands, change the block state
            pState = pState.cycle(OPEN);
            pLevel.setBlock(pPos, pState, 2);

            this.playSound(pPlayer, pLevel, pPos);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);

        } else if(!pPlayer.isSecondaryUseActive() && !pLevel.isClientSide() && hasEmptyHands ) { //For server-side logic only, if player is NOT shift-interacting, sit on block
            Entity entity;
            List<SittableEntity> entities = pLevel.getEntities(ModEntities.CHAIR.get(), new AABB(pPos), chair -> true);
            if(entities.isEmpty()) {
                entity = ModEntities.CHAIR.get().spawn((ServerLevel) pLevel, pPos, MobSpawnType.TRIGGERED); //Sittable Entity spawns in.
            } else {
                entity = entities.get(0); //Gets the first entity already present if one happens to be there for some reason instead of making a new one.
            }

            if(entity !=null) {
                pPlayer.startRiding(entity); //Player is now sitting.
            }
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return InteractionResult.FAIL; //If player has item in either hand, Interaction fails (blocks disappear if placed at the same time player is sitting. this is my solution to that.)
    }

    protected void playSound(@Nullable Player pPlayer, Level pLevel, BlockPos pPos) {
        pLevel.playSound(pPlayer, pPos, ModSounds.TOILET_FLUSH.get(), SoundSource.BLOCKS, 1.0F, pLevel.getRandom().nextFloat() * 0.1F + 0.9F);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, OPEN, HALF_PART, WATERLOGGED);
    }
}
