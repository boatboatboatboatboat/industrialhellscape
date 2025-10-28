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

//---------- INFO ----------
// Currently used for Fuel Drum block. Block when placed, aligns with surface placed on alongside cardinal direction. Also has an inventory

public class SurfaceMountContainerBlock extends SurfaceMountBlock implements EntityBlock, SimpleWaterloggedBlock {
    public final int SLOTS; //Amount of inventory slots. Should be a multiple of 9. Passed to Block Entity
    public final SoundEvent OPEN_SOUND;
    public final SoundEvent CLOSE_SOUND;

    public SurfaceMountContainerBlock(Properties pProperties, int SLOTS, SoundEvent OPEN_SOUND, SoundEvent CLOSE_SOUND) {
        // When registering this block, pass in
        // Properties
        // Integer amount of slots the block entity inventory will have (multiple of 9)
        // Sound to play when player opens block
        // Sound to play when player closes block
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
        //See modded interface ContainerBlockCapability for details
        return ContainerBlockCapability.OpenContainerInventory(level,pos,player);
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
        //See modded interface ContainerBlockCapability for details
        ContainerBlockCapability.dropSavedContainerInventory(this, state, level, pos, newState);
        super.onRemove(state, level, pos, newState, isMoving);
    }

    //---------- End of Block Entity Handling Methods ----------
}
