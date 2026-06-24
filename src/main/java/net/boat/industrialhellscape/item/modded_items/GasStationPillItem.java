package net.boat.industrialhellscape.item.modded_items;


import net.boat.industrialhellscape.ModDamageTypes;
import net.boat.industrialhellscape.sound.ModSounds;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class GasStationPillItem extends Item {
    public GasStationPillItem(Properties pProperties) {
        super(pProperties);
    }

    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull LivingEntity target, @NotNull InteractionHand hand) {
        //Villager becomes horse when crouch-interacted with
        if (target instanceof Villager villager) {
            if (villager.isAlive() && !villager.hasCustomName() )  {
                int villagerInventorySlots = villager.getInventory().getContainerSize();

                if (!player.level().isClientSide) { //SERVER SIDE
                    villager.playSound(ModSounds.HORSEPILL.get());

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
            //https://github.com/Kapitencraft/Mysticcraft/blob/d70d4a84d9d5c51e6638b365700ba5f781be4c04/src/main/java/net/kapitencraft/mysticcraft/item/misc/ModExplosion.java#L241
            double d0 = entity.level().getRandom().nextGaussian()*0.2;
            double d1 = entity.level().getRandom().nextGaussian()*0.2;
            double d2 = entity.level().getRandom().nextGaussian()*0.2;
            entity.level().addParticle(particleType, entity.getRandomX((double)1.0F), entity.getRandomY() + (double)1.0F, entity.getRandomZ((double)1.0F), d0, d1, d2);
        }
    }

    //If player eats pill
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull LivingEntity entity) {
        if (entity instanceof Player) {
            if(!entity.level().isClientSide) {
                addParticlesAroundSelf(entity, ParticleTypes.HAPPY_VILLAGER);
            }
            entity.playSound(ModSounds.HORSEPILL.get());
            //entity.hurt(entity.level().damageSources().source(ModDamageTypes.HORSEPILL_DAMAGE), 512F);
            //MCreator
            entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("industrialhellscape:horsepill_damage")))), 512);

            if(entity.level() instanceof ServerLevel serverLevel && !entity.isAlive()) {
                Component name = entity.getName();
                EntityType.HORSE.spawn(serverLevel, entity.blockPosition(), MobSpawnType.MOB_SUMMONED).setCustomName(name);
            }
        }

        return super.finishUsingItem(itemstack, world, entity);
    }
}