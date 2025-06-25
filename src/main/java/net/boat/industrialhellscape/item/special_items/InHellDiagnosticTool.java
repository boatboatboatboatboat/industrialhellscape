package net.boat.industrialhellscape.item.special_items;


import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class InHellDiagnosticTool extends Item {
    public InHellDiagnosticTool(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) { //1st use for tool
        if(!pContext.getLevel().isClientSide() ) { //"If" statement now dependent on server side info

            BlockPos positionClicked = pContext.getClickedPos(); //Datatype BlockPos "positionClicked" stores block coordinates from the one clicked
            Player player = pContext.getPlayer();
            boolean foundBlock = false; //Default value
            for (int i=0; i <= positionClicked.getY() + 64; i++) {
                BlockState state = pContext.getLevel().getBlockState(positionClicked.below(i)); //i'th position below selected block

                if (isValuableBlock(state)) {
                    outputValuableCoordinates(positionClicked.below(i), player, state.getBlock());
                    foundBlock = true;
                    break;
                }
            }

            if(!foundBlock) { //If Block is NOT found (No valuables found to trigger foundBlock = true so it remains false
                Minecraft.getInstance().player.displayClientMessage(Component.literal("No Valuables Found"), true); //Text in lower center of client screen
            }


        }

        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));

        return InteractionResult.SUCCESS; //Gives right-click animation
    }

    private void outputValuableCoordinates(BlockPos blockPos, Player player, Block block) { //"blockPos" is a custom name
        player.sendSystemMessage(Component.literal(
                //Formatted Message Contents
                "You found " + I18n.get(block.getDescriptionId() + " at " + "(" +
                        blockPos.getX() + ", " +
                        blockPos.getY() + ", " +
                        blockPos.getZ() + ", " +
                        ")"

                ))
        );        //Second argument, a boolean, the message display method
    }

    private boolean isValuableBlock(BlockState state) { //Outputs true or false if Blockstate input is iron/diamond
        return state.is(Blocks.IRON_ORE) || state.is(Blocks.DIAMOND_ORE);
    }

}
