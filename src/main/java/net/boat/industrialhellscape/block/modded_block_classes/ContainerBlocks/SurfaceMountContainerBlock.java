package net.boat.industrialhellscape.block.modded_block_classes.ContainerBlocks;

import net.boat.industrialhellscape.block.modded_block_classes.SurfaceMountBlocks.SurfaceMountBlock;
import net.boat.industrialhellscape.block.modded_block_entities.GenericContainerBE;
import net.boat.industrialhellscape.block.modded_interfaces.ContainerBlockCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class SurfaceMountContainerBlock extends SurfaceMountBlock implements EntityBlock, SimpleWaterloggedBlock {
    public final int SLOTS; //Amount of inventory slots. Should be a multiple of 9.
    public final SoundEvent OPEN_SOUND;
    public final SoundEvent CLOSE_SOUND;

    public SurfaceMountContainerBlock(Properties pProperties, int SLOTS, SoundEvent OPEN_SOUND, SoundEvent CLOSE_SOUND) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(SURFACE_MOUNT, AttachFace.FLOOR)
        );
        this.SLOTS = SLOTS;
        this.OPEN_SOUND = OPEN_SOUND;
        this.CLOSE_SOUND = CLOSE_SOUND;
    }

    // PLACEMENT BEHAVIOR ALREADY HANDLED BY SUPERCLASS

    //---------- Block Entity Handling Methods below ----------

    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new GenericContainerBE(pos, state);
    }

    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        //Interaction for multiblocks will always occur at the NEGATIVE (bottom, right, or back) block's position


        if (state.is(this)) { //Since the defined block may be different, check if its still the same block
            //If so, open the Container inventory as usual
            return ContainerBlockCapability.OpenContainerInventory(level, pos, player);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
        //General method for safe removal of current (Container) and deprecated block entities.
        //Handles both the new container entities and previous non-container block entities
        ContainerBlockCapability.dropSavedContainerInventory(this, state, level, pos, newState);
        super.onRemove(state, level, pos, newState, isMoving);
    }

    //---------- End of Block Entity Handling Methods ----------
}
