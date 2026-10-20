package net.boat.industrialhellscape.block.block_entities;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class BrushableBE extends BrushableBlockEntity {
    private ResourceKey<LootTable> customLootTable = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, "archaeology/very_suspicious_sand"));
    public boolean initiatedArmingProcedure = false;
    public BrushableBE(BlockPos pos, BlockState blockState) {
        super(pos, blockState);

        this.setLootTable(
                customLootTable,
                0);
    }

    @Override
    public void unpackLootTable(Player player) {
        super.unpackLootTable(player);

        if (this.level != null && !this.level.isClientSide() && this.level.getServer() != null) {
            LootTable loottable = this.level.getServer().reloadableRegistries().getLootTable(customLootTable);

            LootParams lootparams = (new LootParams.Builder((ServerLevel)this.level)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.worldPosition)).withLuck(player.getLuck()).withParameter(LootContextParams.THIS_ENTITY, player).create(LootContextParamSets.CHEST);
            ObjectArrayList<ItemStack> objectarraylist = loottable.getRandomItems(lootparams, 0);
            ItemStack var10001;
            switch (objectarraylist.size()) {
                case 0:
                    var10001 = ItemStack.EMPTY;
                    break;
                case 1:
                    var10001 = (ItemStack)objectarraylist.get(0);
                    break;
                default:
                    var10001 = (ItemStack)objectarraylist.get(0);
            }

            player.sendSystemMessage(Component.literal("itemstack is:_"+var10001)); //returns the test item successfully, why is it not rendering
        } else {
            player.sendSystemMessage(Component.literal("level or server level is null"));
        }
    }

    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.BRUSHABLE_BE.get();
    }

    @Override //doesnt work for BER
    public ItemStack getItem() {
        ItemStack item = ModItems.GAS_STATION_PILL.toStack();
        return item;
    }
}
