package net.boat.industrialhellscape.block.block_classes.SurfaceMountBlocks;

import net.boat.industrialhellscape.block.block_interfaces.HitboxRotationInterface;
import net.boat.industrialhellscape.block.block_interfaces.ToolUseInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

// Code obtained from Create Deco mod (CC0 1.0 license)

public class ModelToggleLightBulbBlock extends LightBulbBlock implements ToolUseInterface {
    public static final BooleanProperty ALT_STATE = BooleanProperty.create("alt_state");

    private final VoxelShape ALT_SHAPE_NORTH;
    private final VoxelShape ALT_SHAPE_SOUTH;
    private final VoxelShape ALT_SHAPE_EAST;
    private final VoxelShape ALT_SHAPE_WEST;

    private final VoxelShape ALT_SHAPE_FLOOR_NORTH;
    private final VoxelShape ALT_SHAPE_FLOOR_SOUTH;
    private final VoxelShape ALT_SHAPE_FLOOR_EAST;
    private final VoxelShape ALT_SHAPE_FLOOR_WEST;

    private final VoxelShape ALT_SHAPE_CEILING_NORTH;
    private final VoxelShape ALT_SHAPE_CEILING_SOUTH;
    private final VoxelShape ALT_SHAPE_CEILING_EAST;
    private final VoxelShape ALT_SHAPE_CEILING_WEST;

    public ModelToggleLightBulbBlock(Properties pProperties, VoxelShape floorHitBox, SoundEvent onSound, SoundEvent offSound) {
        super(pProperties, floorHitBox, onSound, offSound);

        this.ALT_SHAPE_FLOOR_NORTH = HitboxRotationInterface.rotateVoxelYAxisIntTimes(1, floorHitBox);
        this.ALT_SHAPE_FLOOR_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, ALT_SHAPE_FLOOR_NORTH);
        this.ALT_SHAPE_FLOOR_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, ALT_SHAPE_FLOOR_NORTH);
        this.ALT_SHAPE_FLOOR_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, ALT_SHAPE_FLOOR_NORTH);

        this.ALT_SHAPE_NORTH = HitboxRotationInterface.rotateVoxelXAxisIntTimes(1, ALT_SHAPE_FLOOR_NORTH); //Rotates to the north surface position
        this.ALT_SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, ALT_SHAPE_NORTH);
        this.ALT_SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, ALT_SHAPE_NORTH);
        this.ALT_SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, ALT_SHAPE_NORTH);

        this.ALT_SHAPE_CEILING_NORTH = HitboxRotationInterface.rotateVoxelXAxisIntTimes(2, floorHitBox);
        this.ALT_SHAPE_CEILING_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, ALT_SHAPE_CEILING_NORTH);
        this.ALT_SHAPE_CEILING_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, ALT_SHAPE_CEILING_NORTH);
        this.ALT_SHAPE_CEILING_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, ALT_SHAPE_CEILING_NORTH);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BlockStateProperties.LIT, false)
                .setValue(BlockStateProperties.INVERTED, false)
                .setValue(ALT_STATE, false)
        );
    }

    @Override
    public @Nonnull VoxelShape getShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {

        //When placed, if the block's "TYPE" property is one of these cases, find its horizontal orientation and give it the correct hitbox
        //North is the default orientation assumed if no other cases met

        if(pState.getValue(ALT_STATE)) {
            if(pState.getValue(FACE) == AttachFace.WALL) {
                return switch (pState.getValue(FACING)) {
                    case SOUTH -> ALT_SHAPE_SOUTH;
                    case EAST -> ALT_SHAPE_EAST;
                    case WEST -> ALT_SHAPE_WEST;
                    default -> ALT_SHAPE_NORTH;
                };
            } else if(pState.getValue(FACE) == AttachFace.FLOOR) {
                return switch (pState.getValue(FACING)) {
                    case SOUTH -> ALT_SHAPE_FLOOR_SOUTH;
                    case EAST -> ALT_SHAPE_FLOOR_EAST;
                    case WEST -> ALT_SHAPE_FLOOR_WEST;
                    default -> ALT_SHAPE_FLOOR_NORTH;
                };
            } else {
                return switch (pState.getValue(FACING)) {
                    case SOUTH -> ALT_SHAPE_CEILING_SOUTH;
                    case EAST -> ALT_SHAPE_CEILING_EAST;
                    case WEST -> ALT_SHAPE_CEILING_WEST;
                    default -> ALT_SHAPE_CEILING_NORTH;
                };
            }
        }   else {
            if(pState.getValue(FACE) == AttachFace.WALL) {
                return switch (pState.getValue(FACING)) {
                    case SOUTH -> SHAPE_SOUTH;
                    case EAST -> SHAPE_EAST;
                    case WEST -> SHAPE_WEST;
                    default -> SHAPE_NORTH;
                };
            } else if(pState.getValue(FACE) == AttachFace.FLOOR) {
                return switch (pState.getValue(FACING)) {
                    case SOUTH -> SHAPE_FLOOR_SOUTH;
                    case EAST -> SHAPE_FLOOR_EAST;
                    case WEST -> SHAPE_FLOOR_WEST;
                    default -> SHAPE_FLOOR_NORTH;
                };
            } else {
                return switch (pState.getValue(FACING)) {
                    case SOUTH -> SHAPE_CEILING_SOUTH;
                    case EAST -> SHAPE_CEILING_EAST;
                    case WEST -> SHAPE_CEILING_WEST;
                    default -> SHAPE_CEILING_NORTH;
                };
            }
        }
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem (BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player entity, @NotNull BlockHitResult hitResult) {
        boolean wasOn = state.getValue(BlockStateProperties.LIT);
        SoundEvent onOffSound = wasOn ? OFF_SOUND : ON_SOUND;

        BlockState next = this.toggle(state.cycle(BlockStateProperties.INVERTED), level, pos);

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else if(entity.isCrouching()) {
            this.toggle(state.cycle(ALT_STATE), level, pos);
            return InteractionResult.CONSUME;
        } else {
            float pitch = next.getValue(BlockStateProperties.INVERTED) ? 0.6f : 0.5f;
            level.playSound(null, pos, onOffSound, SoundSource.BLOCKS, 0.3f, pitch);
            return InteractionResult.CONSUME;
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return ToolUseInterface.simpleToolUse(stack, state, level, pos, player, ALT_STATE, 2);
    }

    @Override
    protected void createBlockStateDefinition (StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.LIT, BlockStateProperties.INVERTED, FACING, WATERLOGGED, FACE, ALT_STATE);
    }

}


