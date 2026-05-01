package net.boat.industrialhellscape.block.modded_block_classes.SurfaceMountBlocks;

import net.boat.industrialhellscape.block.modded_interfaces.RotationHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;

public class ModelledSurfaceMountBlock extends SurfaceMountBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<AttachFace> SURFACE_MOUNT = BlockStateProperties.ATTACH_FACE;

    public final VoxelShape SHAPE_FLOOR;
    public final VoxelShape SHAPE_CEILING;
    private final VoxelShape SHAPE_NORTH;
    private final VoxelShape SHAPE_SOUTH;
    private final VoxelShape SHAPE_EAST;
    private final VoxelShape SHAPE_WEST;
    private final boolean ceilingModelAlignedSameAsFloor;


    public ModelledSurfaceMountBlock(Properties pProperties, VoxelShape floorHitBox, boolean ceilingSurfaceMountOnly) {
        super(pProperties);

        this.SHAPE_FLOOR = floorHitBox; //default for blocks using this block class
        this.SHAPE_CEILING = RotationHelper.rotateVoxelXAxisIntTimes(2, floorHitBox);

        this.SHAPE_NORTH = RotationHelper.rotateVoxelXAxisIntTimes(1, floorHitBox); //Rotates to the north surface position
        this.SHAPE_SOUTH = RotationHelper.rotateVoxelCardinal(Direction.SOUTH, SHAPE_NORTH);
        this.SHAPE_EAST = RotationHelper.rotateVoxelCardinal(Direction.EAST, SHAPE_NORTH);
        this.SHAPE_WEST = RotationHelper.rotateVoxelCardinal(Direction.WEST, SHAPE_NORTH);

        this.ceilingModelAlignedSameAsFloor = ceilingSurfaceMountOnly;

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(SURFACE_MOUNT, AttachFace.WALL)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public @Nonnull VoxelShape getShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {

        //When placed, if the block's "TYPE" property is one of these cases, find its horizontal orientation and give it the correct hitbox
        //North is the default orientation assumed if no other cases met

        if(pState.getValue(SURFACE_MOUNT) == AttachFace.WALL) {
            return switch (pState.getValue(FACING)) {
                case SOUTH -> SHAPE_SOUTH;
                case EAST -> SHAPE_EAST;
                case WEST -> SHAPE_WEST;
                default -> SHAPE_NORTH;
            };
        } else if(pState.getValue(SURFACE_MOUNT) == AttachFace.FLOOR && ceilingModelAlignedSameAsFloor) {
            return SHAPE_CEILING;
        } else if(pState.getValue(SURFACE_MOUNT) == AttachFace.FLOOR) {
            return SHAPE_FLOOR;
        } else {
            return SHAPE_CEILING;
        }
    }
}


