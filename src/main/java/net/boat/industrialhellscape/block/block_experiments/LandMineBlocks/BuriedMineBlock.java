package net.boat.industrialhellscape.block.block_experiments.LandMineBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BuriedMineBlock extends BrushableBlock implements ExplosiveInterface {
    public static final BooleanProperty DISARMED = BlockStateProperties.DISARMED;

    public BuriedMineBlock(Block turnsInto, SoundEvent brushSound, SoundEvent brushCompletedSound, Properties properties) {
        super(turnsInto, brushSound, brushCompletedSound, properties);

        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(DISARMED, true)
                .setValue(BlockStateProperties.DUSTED, 0)
        );
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        BuriedMineBE be = new BuriedMineBE(pos, state);
        return be;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        /*
        Assigns LandMine BE owner to the LivingEntity that placed it down
         */
        if(level.getBlockEntity(pos) instanceof ExplosiveInterface be) {
            if(placer != null) {
                be.assignExplosiveOwner(placer.getUUID());
            }
        }
    }

    //TODO: test removal
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {

        /*
            If an armed landmine is placed; it will not have an owner (UUID) saved by default.
            Assuming a scenario where a player mines under a mine, and letting it fall by gravity,
            it will re-place itself upon landing, resetting any stored BE data.

            - Find the nearest player within 5 blocks who *may* have placed it.

            Solution:
            - If block state at position is a LandmineBE
            - And its state when placed is already armed
            - Find nearest player within 3 blocks including creative players
            - If found, assign "owner" UUID to block entity.
         */

        //If a landmine is placed already armed (Unnatural, unlikely event. Only triggered by armed mine above air block, falling and landing)
        if(!state.getValue(DISARMED)) {
            //If landMine has block entity inside, get nearest player within 5 blocks
            if(level.getBlockEntity(pos) instanceof ExplosiveInterface be) {
                Player nearestPlayer = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5, true);
                //if player is nearby, assign their UUID to owner.
                if(nearestPlayer != null) {
                    be.assignExplosiveOwner(nearestPlayer.getUUID());
                }
            }
        }

        super.onPlace(state, level, pos, oldState, isMoving);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(level.getBlockEntity(pos) instanceof BuriedMineBE be) {
            return ExplosiveInterface.interactWithLandmine(this, stack,state,level,pos,player,DISARMED, be.buriedItem);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected void attack(BlockState state, Level level, BlockPos pos, Player player) {
        if(level.getBlockEntity(pos) instanceof BuriedMineBE be) {
            ExplosiveInterface.landmineAttacked(state,level,pos,player,DISARMED, be.buriedItem);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        ExplosiveInterface.armUponScheduledTick(state,level, pos, DISARMED);

        //directly copied from super brushableBlock
        BlockEntity var6 = level.getBlockEntity(pos);
        if (var6 instanceof BrushableBlockEntity brushableblockentity) {
            brushableblockentity.checkReset();
        }

        //dont remove this or it wont fall
        if (FallingBlock.isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinBuildHeight()) {
            FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(level, pos, state);
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entityStepping) {
        if(level.getBlockEntity(pos) instanceof BuriedMineBE be) {
            ExplosiveInterface.steppedOnMine(level, pos, state, entityStepping, DISARMED, be.buriedItem);
        }
    }

    //If adding a new property to a subclass, add all prior properties from super-classes here
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(DISARMED, BlockStateProperties.DUSTED);
    }
}
