package net.boat.industrialhellscape.block.block_entities;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.block_classes.MultiBlocks.BaseIntegerMultiBlock;
import net.boat.industrialhellscape.block.block_entities.StorageBE.StorageBE;
import net.boat.industrialhellscape.block.block_interfaces.MultiBlockPlacementInterface;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

/*
Source: https://docs.neoforged.net/docs/1.21.1/inventories/capabilities
Source: https://github.com/MarkusBordihn/BOs-Easy-Mob-Farm/commit/c15185ebe150e027c1fb872d2134b51515c0c59c

Code inside the lambda functions will tick every time an external block checks for the capability (vanilla hoppers = 8 ticks per item transfer)

Removal of this class is safe; Blocks and block entities will only have compatibility with vanilla item logistics blocks.
MultiBlocks will incur the same consequences, but blocks away from the origin block will not point to the block entity in the origin block, thus doing nothing.
 */

@EventBusSubscriber(modid = IndustrialHellscape.MOD_ID)
public class ModBlockCapabilities {
    @SubscribeEvent
    public static void registerBlockEntityCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.STORAGE_BE.get(),
                (be, side) -> {
                    //IndustrialHellscape.LOGGER.info("InHell: Tick. ItemHandler internal logic triggered");
                    return be.getItemCapability();
                }
        );

        //Item Capability for MultiBlocks
        /*
            - For MultiBlocks with block entities, only one exists.
            - The Block Entity exists at the "origin" block first placed down (block state part:0)
            - Any attempt to read the ItemCapability at a MultiBlock part not containing the BE must
                locate the BlockPos containing the BE before proper capability can be provided.
         */
        event.registerBlock(
                Capabilities.ItemHandler.BLOCK, // capability to register for
                (level, pos, state, be, side) -> {
                    //IndustrialHellscape.LOGGER.info("InHell: Tick. ItemHandler internal logic triggered");
                    if(state.getBlock() instanceof BaseIntegerMultiBlock multiBlock) {
                        //Only one BE exists per MultiBlock. Find it.
                        BlockEntity targetBlockEntity = level.getBlockEntity(MultiBlockPlacementInterface.vectorToOriginBlockPos(pos, multiBlock.multiBlockPlacementMatrix, state, multiBlock.partProperty));

                        if(targetBlockEntity instanceof StorageBE storageBE) {
                            return storageBE.getItemCapability();
                        }
                    }
                    return null;
                    //No worky: block entity not guaranteed to exist at pos where multiBlock is
//                    if(level.getBlockEntity(pos) instanceof StorageBlockInterface storageBE) {
//                        return storageBE.getItemCapability();
//                    }
//                    return null;
                },
                // blocks to register for (MultiBlocks ONLY)
                ModBlocks.DEBUG_BLOCK.get(),
                ModBlocks.LARGE_LOCKER.get()
        );
    }
}