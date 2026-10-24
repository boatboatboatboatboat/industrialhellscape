package net.boat.industrialhellscape.block.experiments.LandMineBlocks;

import net.boat.industrialhellscape.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentUser;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.UUID;

public interface ExplosiveInterface {

    default boolean isArmed() {
        return false;
    }

    default void setFlagArmed() {
    }

    default void setFlagDisarmed() {
    }

    default void assignExplosiveOwner(UUID explosivePlacer) {
    }

    default boolean getArmingStatusOfBE() {
        return false;
    }

    default UUID getOwnerOfBE() {
        return null;
    }

    ExplosionDamageCalculator LANDMINE_DAMAGE_CALCULATOR = new ExplosionDamageCalculator() {

        //custom values
        boolean disableLandMineEntityDamage = false;
        boolean disableLandMineBlockDestruction = true;
        final int blockBlastResistanceBoost = 10; //Default behavior is 1; High values - weaker effect on block destruction
        final int entityLethalityCoefficient = 2; //TNT default is 2; High values - stronger damage over distance

        @Override
        public Optional<Float> getBlockExplosionResistance(Explosion explosion, BlockGetter reader, BlockPos pos, BlockState state, FluidState fluid) {
            if(disableLandMineBlockDestruction) {
                return Optional.of( (float) 3600000);
            } else {
                return state.is(Blocks.NETHER_PORTAL) ?
                        Optional.empty()
                        :
                        Optional.of(Math.max((float)
                                        state.getExplosionResistance(reader, pos, explosion) * blockBlastResistanceBoost,
                                        fluid.getExplosionResistance(reader, pos, explosion) * blockBlastResistanceBoost
                                )
                        );
            }
        }

        @Override
        public float getEntityDamageAmount(Explosion explosion, Entity entity) {
            float lethality = explosion.radius() * entityLethalityCoefficient;
            Vec3 vec3 = explosion.center();
            double d0 = Math.sqrt(entity.distanceToSqr(vec3)) / (double)lethality;
            double d1 = ((double)1.0F - d0) * (double)Explosion.getSeenPercent(vec3, entity);
            float calculatedEntityDamage = (float)((d1 * d1 + d1) / (double)2.0F * (double)7.0F * (double)lethality + (double)1.0F);
            return disableLandMineEntityDamage ? 0 : calculatedEntityDamage;
        }
    };



    static void armExplosiveBE(BlockState state, Level level, BlockPos pos, Player player, BooleanProperty disarmProperty) {
        if (level.getBlockEntity(pos) instanceof LandmineBE be) {
            //Play Sound
            if (!level.isClientSide) {
                level.playSound(null, pos, SoundEvents.TRIPWIRE_CLICK_ON, SoundSource.BLOCKS, 2, 1);

                if(!be.isArmed() && state.getValue(disarmProperty)) {
                    level.scheduleTick(pos, state.getBlock(), 60);
                    be.assignExplosiveOwner(player.getUUID());
                    level.setBlock(pos, state.setValue(BlockStateProperties.DISARMED, false), 3);
                    be.setChanged();
                }

            } else {
                player.displayClientMessage(Component.literal("Landmine is now armed"), true);
            }
        }
    }

    static void disarmExplosiveBE(BlockState state, Level level, BlockPos pos, Player player, BooleanProperty disarmProperty) {
        //Play Sound
        if (!level.isClientSide) {
            level.playSound(null, pos, SoundEvents.TRIPWIRE_CLICK_OFF, SoundSource.BLOCKS, 1, 1);

            //Disarm Explosive instantly
            if (level.getBlockEntity(pos) instanceof LandmineBE be) {
                if(be.isArmed()) {
                    be.setFlagDisarmed();
                    level.setBlock(pos, state.setValue(disarmProperty, true), 3);
                    be.setChanged();
                }
            }

        } else {
            player.displayClientMessage(Component.literal("Landmine is disarmed and SAFE"), true);
        }
    }

    static void steppedOnMine(Level level, BlockPos pos, BlockState state, Entity entityStepping, BooleanProperty disarmProperty) {
        if(entityStepping instanceof EquipmentUser bootWearer) {
            ItemStack bootItem = bootWearer.getItemBySlot(EquipmentSlot.FEET);
            boolean bootItemHasFeatherFalling = bootItem.getEnchantments().toString().contains(Enchantments.FEATHER_FALLING.toString());

            if(bootItemHasFeatherFalling) {
                //prematurely escape method, to prevent triggering explosion;
                return;
            }
        }

        //explodes by default unless entity has feather falling boots
        ExplosiveInterface.explodeLandMine(level, pos, state,  entityStepping, disarmProperty);
    }

    static void landmineAttacked(BlockState state, Level level, BlockPos pos, Player player, BooleanProperty disarmProperty) {
        boolean attackingToolIsSilkTouch = player.getMainHandItem().getEnchantments().toString().contains(Enchantments.SILK_TOUCH.toString());

        if(!attackingToolIsSilkTouch) {
            ExplosiveInterface.explodeLandMine(level, pos, state,  player, disarmProperty);
        }
    }

    static void armUponScheduledTick(BlockState state, ServerLevel level, BlockPos pos, BooleanProperty disarmProperty) {
        if(level.getBlockEntity(pos) instanceof ExplosiveInterface be) {
            boolean blockStateIsArmed = !state.getValue(disarmProperty);

            //Block entity arming flag resets to default value if block falls and lands due to gravity.
            //Fix is implemented in block class
            if(!be.isArmed() && blockStateIsArmed) {
                //declare successfully armed
                be.setFlagArmed();
                level.playSound(null, pos, SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1, 2);
            }
        }
    }

    static ItemInteractionResult interactWithLandmine(Block thisBlock, ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, BooleanProperty disarmProperty) {
        boolean explosiveDisArmed = state.getValue(disarmProperty);
        boolean explosiveArmed = !explosiveDisArmed;

        boolean playerIsHoldingBrush = stack.is(Items.BRUSH);
        boolean playerIsHoldingDefuserItem = stack.is(Items.SHEARS) || stack.is(ModItems.INHELL_HAVEN_DEVICE.get());
        //https://www.reddit.com/r/fabricmc/comments/of4bw3/check_if_itemstack_has_specific_enchantment/ ???

        //https://www.reddit.com/r/fabricmc/comments/1gjlr8x/enchantments_in_121/
        boolean containsSilkTouch = stack.getEnchantments().toString().contains(Enchantments.SILK_TOUCH.toString());

        //player.sendSystemMessage(Component.literal("You hold silk touch item: " + containsSilkTouch));

        //if player using a Defusal Item
        if(playerIsHoldingDefuserItem) {
            //if mine is already armed, disarm mine
            if (explosiveArmed) {
                ExplosiveInterface.disarmExplosiveBE(state, level, pos, player, disarmProperty);
            }
            //return interaction success regardless.
            //Holding a defuser item will never arm/detonate mine, only disarm it
            return ItemInteractionResult.SUCCESS;

            //If player holding Silk Touch item
        } else if(containsSilkTouch) {
            //if mine is already disarmed, arm it
            if(explosiveDisArmed) {
                ExplosiveInterface.armExplosiveBE(state, level, pos, player, disarmProperty);
            }

            //return interaction success regardless.
            //Holding a (non-defuser) silk-touch item will never disarm/detonate mine, only arm it
            return ItemInteractionResult.SUCCESS;

            //If block cannot be brushed and player has any other irrelevant item
        } else if(!(playerIsHoldingBrush && (thisBlock instanceof BrushableBlock) ) ) {

            //If mine is armed, detonate due to usage of unsafe tool
            if(explosiveArmed) {
                ExplosiveInterface.explodeLandMine(level,pos, state, player, disarmProperty);

                //If mine disarmed, arm it
            } else {
                ExplosiveInterface.armExplosiveBE(state,level,pos,player, disarmProperty);
            }
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION; //Holding a brush goes to here
    }

    static void explodeLandMine(Level level, BlockPos pos, BlockState state, Entity entityStepping, BooleanProperty disarmProperty) {
        if(level.getBlockEntity(pos) instanceof ExplosiveInterface be) {
            //If server-side
            if(level instanceof ServerLevel serverLevel) {
                //And if LandMine is internally and visually armed
                if(be.getArmingStatusOfBE() && !state.getValue(disarmProperty)) {
                    //get block entity owner
                    UUID owner = be.getOwnerOfBE();

                    //ensure no drops/surviving mine
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

                    Vec3 vec3 = pos.getCenter().add(0,1,0); //negligible damage if explosion raycasts are in center of block. need offset vertically

                    level.explode(null, Explosion.getDefaultDamageSource(entityStepping.level(), serverLevel.getEntity(owner)), LANDMINE_DAMAGE_CALCULATOR, vec3, 5.0F, false, Level.ExplosionInteraction.BLOCK);
                }
            }
        }

        //regardless if if-checks pass or fail, remove block entity at position.
        level.removeBlockEntity(pos);
    }
}
