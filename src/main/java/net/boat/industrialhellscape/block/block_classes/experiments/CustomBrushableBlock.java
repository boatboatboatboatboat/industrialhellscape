package net.boat.industrialhellscape.block.block_classes.experiments;

import net.boat.industrialhellscape.block.block_entities.BrushableBE;
import net.boat.industrialhellscape.block.block_interfaces.ExplosiveInterface;
import net.boat.industrialhellscape.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
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
        boolean explosiveArmed = !state.getValue(DISARMED);
        boolean playerIsHoldingBrush = stack.is(Items.BRUSH);
        boolean playerIsHoldingDefuserItem = stack.is(Items.SHEARS) || stack.is(ModItems.INHELL_HAVEN_DEVICE.get());
        boolean hasSilkTouch = 0 == stack.getEnchantmentLevel(level.registryAccess().holderOrThrow(Enchantments.SILK_TOUCH));
        boolean playerIsHoldingSafeItem = (playerIsHoldingDefuserItem || hasSilkTouch);


        //if using safe items
        if(playerIsHoldingSafeItem) {
            //If explosive already armed
            if (explosiveArmed) {
                ExplosiveInterface.disarmExplosive(state, level, pos, player);

            //If explosive is disarmed
            } else {
                ExplosiveInterface.armExplosive(state,level,pos,player);
            }

            //successful interaction if holding safe item
            return ItemInteractionResult.SUCCESS;

        //Holding unsafe items (brush is notable exception)
        } else if(!playerIsHoldingBrush) {
            //if mine is already armed
            if(explosiveArmed) {
                //Explodes if mishandled by bare hands (or any item not brush, or does not have SilkTouch)
                ExplosiveInterface.explodeLandMine(level, pos, player);
            } else {
                ExplosiveInterface.armExplosive(state, level, pos, player);
            }

            //successful interaction if holding unsafe (non-brush) item
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION; //Holding a brush goes to here
    }

    @Override
    protected void attack(BlockState state, Level level, BlockPos pos, Player player) {
        if(!state.getValue(DISARMED)) {
            ExplosiveInterface.explodeLandMine(level,pos, player);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(level.getBlockEntity(pos) instanceof BrushableBE be) {
            if(be.initiatedArmingProcedure) {
                level.playSound(null, pos, SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1, 2);
                state = state.setValue(DISARMED, false);
                level.setBlock(pos, state, 3);
            }
        }

        super.tick(state, level, pos, random);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entityStepping) {
        boolean explosiveArmed = !state.getValue(DISARMED);

        if(explosiveArmed) {
            ExplosiveInterface.explodeLandMine(level, pos, entityStepping);
        }
        super.stepOn(level, pos, state, entityStepping);
    }

    //If adding a new property to a subclass, add all prior properties from super-classes here
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(DISARMED, BlockStateProperties.DUSTED);
    }
}
