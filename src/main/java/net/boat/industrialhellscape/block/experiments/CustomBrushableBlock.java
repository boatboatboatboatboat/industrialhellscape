package net.boat.industrialhellscape.block.experiments;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
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

public class CustomBrushableBlock extends BrushableBlock implements ExplosiveInterface {
    public static final BooleanProperty DISARMED = BlockStateProperties.DISARMED;

    public CustomBrushableBlock(Block turnsInto, SoundEvent brushSound, SoundEvent brushCompletedSound, Properties properties) {
        super(turnsInto, brushSound, brushCompletedSound, properties);

        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(DISARMED, true)
                .setValue(BlockStateProperties.DUSTED, 0)
        );
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BrushableBE(pos, state);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return ExplosiveInterface.interactWithLandmine(this, stack,state,level,pos,player,DISARMED);
    }

    @Override
    protected void attack(BlockState state, Level level, BlockPos pos, Player player) {
        ExplosiveInterface.landmineAttacked(state,level,pos,player,DISARMED);
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
        ExplosiveInterface.steppedOnMine(level, pos, state, entityStepping, DISARMED);
    }

    //If adding a new property to a subclass, add all prior properties from super-classes here
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(DISARMED, BlockStateProperties.DUSTED);
    }
}
