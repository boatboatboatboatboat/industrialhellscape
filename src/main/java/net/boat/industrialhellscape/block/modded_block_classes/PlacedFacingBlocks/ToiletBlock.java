package net.boat.industrialhellscape.block.modded_block_classes.PlacedFacingBlocks;

import net.boat.industrialhellscape.entity.ModEntities;
import net.boat.industrialhellscape.entity.SittableEntity.SittableEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public class ToiletBlock extends InteractableModelledFacingBlock {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED; //For binary states (on, off, or lit, unlit)

    public final Supplier<SoundEvent> ON_SOUND;
    public final Supplier<SoundEvent> OFF_SOUND;

    public ToiletBlock(Properties pProperties, VoxelShape soloShape, Supplier<SoundEvent> onSound, Supplier<SoundEvent> offSound) {
        super(pProperties, soloShape, onSound, offSound);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false)
                .setValue(WATERLOGGED, false));
        this.ON_SOUND = onSound;
        this.OFF_SOUND = offSound;
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        boolean wasOn = state.getValue(POWERED);

        if(!level.isClientSide() && !player.isSecondaryUseActive()) {

            Entity entity;
            List<SittableEntity> entities = level.getEntities(ModEntities.CHAIR.get(), new AABB(pos), chair -> true);
            if(entities.isEmpty()) {
                entity = ModEntities.CHAIR.get().spawn((ServerLevel) level, pos, MobSpawnType.TRIGGERED);
            } else {
                entity = entities.get(0);
            }
            player.startRiding(entity);

        } else if (player.isSecondaryUseActive()){
            //Opens or closes lid, triggering sound
            state = state.cycle(POWERED);
            level.setBlock(pos, state, 2);
            SoundEvent onOffSound = wasOn ? OFF_SOUND.get() : ON_SOUND.get();
            level.playSound(player, pos, onOffSound, SoundSource.BLOCKS, 1f, 1f);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }
}
