package net.boat.industrialhellscape.block.experiments;

import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.block_classes.PlacedFacingBlocks.FacingFallableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LandMineBlock extends FacingFallableBlock implements ExplosiveInterface, EntityBlock {

    public static final BooleanProperty DISARMED = BlockStateProperties.DISARMED;
    private final VoxelShape hitBox;

    public LandMineBlock(Properties pProperties, SoundEvent onDropSound, VoxelShape hitBox) {
        super(pProperties, onDropSound);
        this.hitBox = hitBox;
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(DISARMED, true)
        );
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction directionClicked = pContext.getHorizontalDirection().getOpposite(); //Gets the cardinal direction when player places new block
        BlockPos pos = pContext.getClickedPos();
        BlockState state = this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, directionClicked); //First, defines facing direction of the block
        FluidState fluid = pContext.getLevel().getFluidState(pos);
        state = state.setValue(BlockStateProperties.WATERLOGGED, fluid.getType() == Fluids.WATER);
        return state;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return hitBox;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        LandmineBE be = new LandmineBE(blockPos, blockState);

        /*
            If new block entity needs to spawn when Mine block already armed
            Preset its armed status to true;

            Fixes the stupid fucking bug where a falling armed mine plays the
            sound again when it lands.
         */

        if(!blockState.getValue(DISARMED)) {
            be.setFlagArmed();
        }
        return be;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        //bury mines
        if(level.getBlockState(pos.below()).getBlock() instanceof FallingBlock) {
            level.setBlock(pos.below(), ModBlocks.VERY_SUSPICIOUS_SAND.get().defaultBlockState(),3);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
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
        pBuilder.add(DISARMED, BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.WATERLOGGED);
    }
}

