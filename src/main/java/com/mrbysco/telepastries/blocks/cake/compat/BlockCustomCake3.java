package com.mrbysco.telepastries.blocks.cake.compat;

import com.mrbysco.telepastries.blocks.cake.BlockCakeBase;
import com.mrbysco.telepastries.config.TeleConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class BlockCustomCake3 extends BlockCakeBase {
	public BlockCustomCake3(Properties properties) {
		super(properties);
	}

	@Override
	protected ItemInteractionResult useItemOn(
			ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result
	) {
		if (!TeleConfig.COMMON.customCake3Dimension.get().isEmpty()) {
			return super.useItemOn(stack, state, level, pos, player, hand, result);
		} else {
			if (player.getUsedItemHand() == hand && !level.isClientSide) {
				player.sendSystemMessage(Component.translatable("telepastries.pastry.custom.unbound").withStyle(ChatFormatting.RED));
			}
			return ItemInteractionResult.SUCCESS;
		}
	}

	@Override
	public MutableComponent getName() {
		return Component.translatable(this.getDescriptionId(), TeleConfig.COMMON.customCake3Name.get());
	}

	@Override
	public boolean isRefillItem(ItemStack stack) {
		List<? extends String> items = TeleConfig.COMMON.customCake3RefillItem.get();
		if (items.isEmpty()) return false;
		ResourceLocation registryLocation = BuiltInRegistries.ITEM.getKey(stack.getItem());
		return registryLocation != null && items.contains(registryLocation.toString());
	}

	@Override
	public ResourceKey<Level> getCakeWorld() {
		return ResourceKey.create(Registries.DIMENSION, new ResourceLocation(TeleConfig.COMMON.customCake3Dimension.get()));
	}

	@Override
	public boolean consumeCake() {
		return TeleConfig.COMMON.consumeCustomCake3.get();
	}
}
