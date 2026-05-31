package net.boat.industrialhellscape.item.modded_items;


import net.boat.industrialhellscape.ModDamageTypes;
import net.boat.industrialhellscape.sound.ModSounds;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HorsePillItem extends Item {
    public HorsePillItem(Properties pProperties) {
        super((new Item.Properties()).rarity(Rarity.UNCOMMON).food((new FoodProperties.Builder()).nutrition(0).saturationModifier(0.0F).alwaysEdible().build()));
    }

    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull LivingEntity target, @NotNull InteractionHand hand) {

        if(target instanceof Cow horse) {
            if (horse.isAlive() ) {
                if(!player.level().isClientSide) { //SERVER SIDE
                    horse.makeSound(SoundEvents.HORSE_EAT);
                    horse.level().gameEvent(target, GameEvent.ENTITY_INTERACT, target.position());
                    stack.shrink(1);
                } else { //CLIENT SIDE
                    //Does not work for horses for some reason
                    //addParticlesAroundSelf(horse, ParticleTypes.HAPPY_VILLAGER);
                }
                return InteractionResult.sidedSuccess(player.level().isClientSide);
            }
        }

        //Horse receives medicine.
        if(target instanceof Horse horse) {
            if (horse.isAlive() ) {
                if(!player.level().isClientSide) { //SERVER SIDE
                    horse.makeSound(SoundEvents.HORSE_EAT);
                    horse.heal(horse.getMaxHealth());
                    horse.addEffect(new MobEffectInstance(MobEffects.ABSORPTION,500,3));
                    horse.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,500,3));
                    horse.level().gameEvent(target, GameEvent.ENTITY_INTERACT, target.position());
                    stack.shrink(1);
                } else { //CLIENT SIDE
                    //addParticlesAroundSelf(horse, ParticleTypes.HAPPY_VILLAGER);
                }
                return InteractionResult.sidedSuccess(player.level().isClientSide);
            }
        }

        //Villager becomes horse when crouch-interacted with
        if (target instanceof Villager villager) {
            if (villager.isAlive() )  {
                int villagerInventorySlots = villager.getInventory().getContainerSize();

                if (!player.level().isClientSide) { //SERVER SIDE
                    villager.makeSound(ModSounds.HORSEPILL.get());

                    //Drop standard inventory
                    for(int i=1; i<villagerInventorySlots; i++) {
                        //Drop each stack that villager possesses to floor
                        villager.spawnAtLocation(villager.getInventory().getItem(i));
                        //Then erase that stack to avoid potential duping
                        villager.getInventory().setItem(i, ItemStack.EMPTY);
                    }
                    //Drop gear https://www.baeldung.com/java-enum-iteration
                    for(EquipmentSlot equipment: EquipmentSlot.values()) {
                        //Drop each stack that villager possesses to floor
                        villager.spawnAtLocation(villager.getItemBySlot(equipment));
                        //Then erase that stack to avoid potential duping
                        villager.setItemSlot(equipment, ItemStack.EMPTY);
                    }

                    //turn villager into horse
                    villager.convertTo(EntityType.HORSE, false);

                    //Game Event
                    villager.level().gameEvent(target, GameEvent.ENTITY_INTERACT, target.position());
                    //Subtract one Item from hand
                    stack.shrink(1);

                } else { //CLIENT SIDE
                    addParticlesAroundSelf(villager, ParticleTypes.HAPPY_VILLAGER);
                }
                //Return interaction result if target isAlive
                return InteractionResult.sidedSuccess(player.level().isClientSide);
            }

        }

        //If living entity neither a horse nor villager, or already dead
        return InteractionResult.PASS;
    }

    protected void addParticlesAroundSelf(Entity entity, ParticleOptions particleType) {
        for(int i = 0; i < 5; ++i) {
            double d0 = entity.getRandom().nextGaussian() * 0.02;
            double d1 = entity.getRandom().nextGaussian() * 0.02;
            double d2 = entity.getRandom().nextGaussian() * 0.02;
            entity.level().addParticle(particleType, entity.getRandomX((double)1.0F), entity.getRandomY() + (double)1.0F, entity.getRandomZ((double)1.0F), d0, d1, d2);
        }
    }

    //If player eats pill
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull LivingEntity entity) {
        if (entity instanceof Player) {
            if(!entity.level().isClientSide) {
                addParticlesAroundSelf(entity, ParticleTypes.HAPPY_VILLAGER);
            }
            entity.makeSound(ModSounds.HORSEPILL.get());
            entity.hurt(entity.level().damageSources().source(ModDamageTypes.HORSEPILL_DAMAGE), 512F);
        }

        if(entity.level() instanceof ServerLevel serverLevel && !entity.isAlive()) {
            EntityType.HORSE.spawn(serverLevel, entity.blockPosition(), MobSpawnType.MOB_SUMMONED).setCustomName(entity.getName());
        }

        return super.finishUsingItem(itemstack, world, entity);
    }

    //Item has tooltip text capability.
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.horsepill"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}