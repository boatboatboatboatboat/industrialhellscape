package net.boat.industrialhellscape.item.modded_items;


import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class JobPaperworkItem extends Item {

    //https://www.geeksforgeeks.org/java/java-functional-interfaces/
    //https://www.geeksforgeeks.org/java/java-8-predicate-with-examples/
    Predicate<Villager> hasThisJobUp;

    public JobPaperworkItem(Properties pProperties, Predicate<Villager> villagerJobToAffect) {
        super(pProperties);
        this.hasThisJobUp = villagerJobToAffect;
    }

    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (target instanceof Villager villager) {

            VillagerProfession jobToAcquire = VillagerProfession.NONE;

            if (villager.isAlive() && hasThisJobUp.test(villager) && !villager.hasCustomName())  {

                if (!villager.level().isClientSide)  { //IF ON SERVER

                    //The order of releasing POIs, setting villager profession, and refreshing their brain matters heavily

                    //Clear their memory of job blocks.
                    //Same behavior as releaseAllPois() when a villager dies.
                    villager.releasePoi(MemoryModuleType.HOME);
                    villager.releasePoi(MemoryModuleType.JOB_SITE);
                    villager.releasePoi(MemoryModuleType.POTENTIAL_JOB_SITE);
                    villager.releasePoi(MemoryModuleType.MEETING_POINT);

                    //Now that their memory is cleared and is a clean slate, replace their profession.
                    //Get current villager data and override the data's value for villager profession.
                    VillagerData villagerProfession = villager.getVillagerData().setProfession(jobToAcquire);
                    //Assign this overridden data to the villager.
                    villager.setVillagerData(villagerProfession);

                    //Refreshing brain still preserves player reputation
                    if(villager.level() instanceof ServerLevel serverLevel) {
                        villager.refreshBrain(serverLevel);
                    }

                    //Sound effects should be considered server side according to vanilla code
                    villager.makeSound(SoundEvents.VILLAGER_YES);

                    //Game Event
                    villager.level().gameEvent(target, GameEvent.ENTITY_INTERACT, target.position());

                    //Subtract one Job Application from hand
                    stack.shrink(1);

                } else { //IF ON CLIENT
                    addParticlesAroundSelf(villager);
                }

            } else if(!villager.level().isClientSide) {
                //Negative sound if villager does not have this job or has custom name
                villager.makeSound(SoundEvents.VILLAGER_NO);
            }

            return InteractionResult.sidedSuccess(player.level().isClientSide);
        }
        return InteractionResult.PASS;
    }

    protected void addParticlesAroundSelf(Entity entity) {
        if(entity.level().isClientSide) {
            for(int i = 0; i < 5; ++i) {
                double d0 = entity.getRandom().nextGaussian() * 0.02;
                double d1 = entity.getRandom().nextGaussian() * 0.02;
                double d2 = entity.getRandom().nextGaussian() * 0.02;
                entity.level().addParticle(ParticleTypes.HAPPY_VILLAGER, entity.getRandomX(1.0F), entity.getRandomY() + 1.0, entity.getRandomZ(1.0F), d0, d1, d2);
            }
        }
    }
}