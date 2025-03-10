
package net.mcreator.testmod.block;

import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;

import net.mcreator.testmod.init.TestModModItems;

import java.util.List;
import java.util.Collections;

public class CryptoWorldPortalFrameBlock extends Block {
	public CryptoWorldPortalFrameBlock() {
		super(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.NETHER_BRICKS).strength(1f, 10f));
		setRegistryName("crypto_world_portal_frame");
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 15;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
		List<ItemStack> dropsOriginal = super.getDrops(state, builder);
		if (!dropsOriginal.isEmpty())
			return dropsOriginal;
		return Collections.singletonList(new ItemStack(TestModModItems.BETTER_CHIP, (int) (4)));
	}
}
