package net.boat.industrialhellscape.block.modded_block_entities;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks.BaseIntegerMultiBlock;
import net.boat.industrialhellscape.block.modded_block_entities.StorageBE.StorageBE;
import net.boat.industrialhellscape.block.modded_interfaces.MultiBlockPlacementInterface;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

/*
Source: https://docs.neoforged.net/docs/1.21.1/inventories/capabilities
Source: https://github.com/MarkusBordihn/BOs-Easy-Mob-Farm/commit/c15185ebe150e027c1fb872d2134b51515c0c59c

Code inside the lambda functions will tick every time an external block checks for the capability (vanilla hoppers)
 */

@EventBusSubscriber(modid = IndustrialHellscape.MOD_ID)
public class ModBlockCapabilities {
    @SubscribeEvent
    public static void registerBlockEntityCapabilities(RegisterCapabilitiesEvent event) {
        IndustrialHellscape.LOGGER.info("InHell: ItemHandler registration for StorageBE complete");
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.STORAGE_BE.get(),
                (be, side) -> {
                    //IndustrialHellscape.LOGGER.info("InHell: Tick. ItemHandler internal logic triggered");
                    return be.getItemCapability();
                }
        );
    }

    @SubscribeEvent
    public static void registerBlockCapabilities(RegisterCapabilitiesEvent event) {
        IndustrialHellscape.LOGGER.info("InHell: ItemHandler registration for BaseIntegerMultiBlock(s) complete");
        event.registerBlock(
                Capabilities.ItemHandler.BLOCK, // capability to register for
                (level, pos, state, be, side) -> {
                    //IndustrialHellscape.LOGGER.info("InHell: Tick. ItemHandler internal logic triggered");
                    if(state.getBlock() instanceof BaseIntegerMultiBlock multiBlock) {
                        BlockEntity targetBlockEntity = level.getBlockEntity(MultiBlockPlacementInterface.vectorToOriginBlockPos(pos, multiBlock.multiBlockPlacementMatrix, state, multiBlock.partProperty));

                        if(targetBlockEntity instanceof StorageBE storageBE) {
                            return storageBE.getItemCapability();
                        }
                    }
                    return null;

                    //Causation of stackoverflow error below?
                    //https://docs.neoforged.net/docs/1.20.6/datastorage/capabilities/#querying-capabilities

                    //If state provided is of a IntegerMultiBlock, use the level to point to
                    //The already existing capability provided by the block entity (registered above)
//                    if(state.getBlock() instanceof BaseIntegerMultiBlock multiBlock) {
//                        //IndustrialHellscape.LOGGER.info("InHell: Tick. ItemHandler internal logic triggered");
//                        return level.getCapability(
//                                Capabilities.ItemHandler.BLOCK,
//                                MultiBlockPlacementInterface.vectorToOriginBlockPos(pos, multiBlock.multiBlockPlacementMatrix, state, multiBlock.partProperty),
//                                side
//                        );
//                    }
//                    return null;
                },
                // blocks to register for
                ModBlocks.LARGE_LOCKER.get(),
                ModBlocks.DEBUG_BLOCK.get()
        );
    }
}