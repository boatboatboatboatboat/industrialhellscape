package net.boat.industrialhellscape.item.modded_items;


import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JobApplicationItem extends Item {
    public JobApplicationItem(Properties pProperties) {
        super(pProperties);
    }

    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        //SaddleItem
        //AssignProfessionFromJobSite
        //p_258310_.setVillagerData(p_258310_.getVillagerData().setProfession(p_22464_));

        if (target instanceof Villager villager) {

            boolean hasThisJob = villager.getVillagerData().getProfession() == VillagerProfession.NITWIT;
            VillagerProfession jobToAcquire = VillagerProfession.NONE;

            if (villager.isAlive() && hasThisJob )  {
                if (!player.level().isClientSide) { //IF ON SERVER

                    //Effects
                    villager.makeSound(SoundEvents.VILLAGER_YES);
                    //get Nitwit data
                    VillagerData villagerProfession = villager.getVillagerData().setProfession(jobToAcquire);
                    //set villager with Nitwit data
                    villager.setVillagerData(villagerProfession);

                    //resets AI behavior. Nitwit post-transform will still refuse to find work unless brain is refreshed.
                    if(villager.level() instanceof ServerLevel targetServerLevel) {
                        villager.refreshBrain(targetServerLevel);
                    }

                    //Game Event
                    villager.level().gameEvent(target, GameEvent.ENTITY_INTERACT, target.position());
                    //Subtract one Job Application from hand
                    stack.shrink(1);

                } else { //IF ON CLIENT
                    addParticlesAroundSelf(villager);
                }
                //Return interaction result if target isAlive and hasThisJob
                return InteractionResult.sidedSuccess(player.level().isClientSide);

            } else if(!player.level().isClientSide) {
                //Negative sound if item was already given to them
                villager.makeSound(SoundEvents.VILLAGER_NO);
            }
        }
        return InteractionResult.PASS;
    }

    protected void addParticlesAroundSelf(Entity entity) {
        if(entity.level().isClientSide) {
            for(int i = 0; i < 5; ++i) {
                double d0 = entity.getRandom().nextGaussian() * 0.02;
                double d1 = entity.getRandom().nextGaussian() * 0.02;
                double d2 = entity.getRandom().nextGaussian() * 0.02;
                entity.level().addParticle(ParticleTypes.HAPPY_VILLAGER, entity.getRandomX((double)1.0F), entity.getRandomY() + (double)1.0F, entity.getRandomZ((double)1.0F), d0, d1, d2);
            }
        }
    }

    //Item has tooltip text capability.
    @Override
    public void appendHoverText(@NotNull ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.job_application"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    };
}