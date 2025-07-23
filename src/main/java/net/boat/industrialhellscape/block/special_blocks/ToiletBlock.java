package net.boat.industrialhellscape.block.special_blocks;

import net.boat.industrialhellscape.block.special_blocks_properties.HitboxGeometryCollection;
import net.boat.industrialhellscape.block.special_blocks_properties.VoxelRotator;
import net.boat.industrialhellscape.entity.ModEntities;
import net.boat.industrialhellscape.entity.custom.SittableEntity;
import net.boat.industrialhellscape.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class ToiletBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, VoxelRotator, HitboxGeometryCollection {

    private static VoxelShape SHAPE_NORTH;
    private static VoxelShape SHAPE_SOUTH;
    private static VoxelShape SHAPE_EAST;
    private static VoxelShape SHAPE_WEST;


    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public ToiletBlock(Properties pProperties, VoxelShape pHitBox) {
        super(pProperties);
        SHAPE_NORTH = pHitBox;
        SHAPE_SOUTH = VoxelRotator.rotateToDirection(Direction.SOUTH, SHAPE_NORTH);
        SHAPE_EAST = VoxelRotator.rotateToDirection(Direction.EAST, SHAPE_NORTH);
        SHAPE_WEST = VoxelRotator.rotateToDirection(Direction.WEST, SHAPE_NORTH);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(OPEN, Boolean.valueOf(false))
                .setValue(POWERED, Boolean.valueOf(false))
                .setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch (pState.getValue(FACING)) {
            case EAST: return SHAPE_EAST;
            case SOUTH: return SHAPE_SOUTH;
            case WEST: return SHAPE_WEST;
            default: return SHAPE_NORTH;
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        switch (pType) {
            case LAND:
                return pState.getValue(OPEN);
            case WATER:
                return pState.getValue(WATERLOGGED);
            case AIR:
                return pState.getValue(OPEN);
            default:
                return false;
        }
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        boolean hasEmptyHands = pPlayer.getMainHandItem().isEmpty() && pPlayer.getOffhandItem().isEmpty();

        if(pPlayer.isSecondaryUseActive() && hasEmptyHands ) { //If player IS shift-interacting and has empty hands, change the block state
            pState = pState.cycle(OPEN);
            pLevel.setBlock(pPos, pState, 2);

            this.playSound(pPlayer, pLevel, pPos, pState.getValue(OPEN));
            return InteractionResult.sidedSuccess(pLevel.isClientSide);

        } else if(!pPlayer.isSecondaryUseActive() && !pLevel.isClientSide() && hasEmptyHands ) { //For server-side logic only, if player is NOT shift-interacting, sit on block
            Entity entity = null;
            List<SittableEntity> entities = pLevel.getEntities(ModEntities.CHAIR.get(), new AABB(pPos), chair -> true);
            if(entities.isEmpty()) {
                entity = ModEntities.CHAIR.get().spawn((ServerLevel) pLevel, pPos, MobSpawnType.TRIGGERED); //Sittable Entity spawns in.
            } else {
                entity = entities.get(0); //Gets the first entity already present if one happens to be there for some reason instead of making a new one.
            }

            pPlayer.startRiding(entity); //Player is now sitting.
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return InteractionResult.FAIL; //If player has item in either hand, Interaction fails (blocks disappear if placed at the same time player is sitting. this is my solution to that.)
    }

    protected void playSound(@Nullable Player pPlayer, Level pLevel, BlockPos pPos, boolean pIsOpened) {
        pLevel.playSound(pPlayer, pPos, pIsOpened ? SoundEvents.BAMBOO_WOOD_TRAPDOOR_OPEN : ModSounds.TOILET_FLUSH.get(), SoundSource.BLOCKS, 1.0F, pLevel.getRandom().nextFloat() * 0.1F + 0.9F);
        pLevel.gameEvent(pPlayer, pIsOpened ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pPos);
    }

    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
            boolean flag = pLevel.hasNeighborSignal(pPos);
            if (flag != pState.getValue(POWERED)) {
                if (pState.getValue(OPEN) != flag) {
                    pState = pState.setValue(OPEN, Boolean.valueOf(flag));
                    this.playSound((Player)null, pLevel, pPos, flag);
                }

                pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(flag)), 2);
                if (pState.getValue(WATERLOGGED)) {
                    pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
                }
            }
        }
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = this.defaultBlockState();
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        Direction direction = pContext.getHorizontalDirection();

        blockstate = blockstate.setValue(FACING, direction);


        if (pContext.getLevel().hasNeighborSignal(pContext.getClickedPos())) {
            blockstate = blockstate.setValue(OPEN, Boolean.valueOf(true)).setValue(POWERED, Boolean.valueOf(true));
        }

        return blockstate.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, OPEN, POWERED, WATERLOGGED);
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
}
