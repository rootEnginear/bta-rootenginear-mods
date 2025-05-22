package rootenginear.scrubbaddie.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSign;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.IPainted;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.block.entity.TileEntitySign;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ItemScrubBaddie extends Item {
	public ItemScrubBaddie(String translationKey, String namespaceId, int id) {
		super(translationKey, namespaceId, id);
		this.setMaxStackSize(1);
		this.setMaxDamage(2);
	}

	public boolean isDried(@NotNull ItemStack itemstack) {
		return itemstack.getMetadata() >= itemstack.getMaxDamage();
	}

	public void useSponge(@NotNull ItemStack itemstack, @Nullable Player player) {
		if (this.isDried(itemstack)) {
			itemstack.setMetadata(this.getMaxDamage());
		} else {
			itemstack.damageItem(1, player);
		}
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
		double reachDistance = entityplayer.getGamemode().getBlockReachDistance();
		HitResult hitResult = entityplayer.rayTrace(reachDistance, 1.0F, true, false);
		if (hitResult == null) return itemstack;
		if (hitResult.hitType == HitResult.HitType.TILE) {
			int i = hitResult.x;
			int j = hitResult.y;
			int k = hitResult.z;
			if (!world.canMineBlock(entityplayer, i, j, k)) return itemstack;

			if (world.getBlockMaterial(i, j, k) == Material.water) {
				entityplayer.swingItem();
				itemstack.setMetadata(0);
				return itemstack;
			}
		}

		return itemstack;
	}

	@Override
	public boolean onUseItemOnBlock(
		@NotNull ItemStack itemstack,
		@Nullable Player player,
		@NotNull World world,
		int blockX,
		int blockY,
		int blockZ,
		@NotNull Side side,
		double xPlaced,
		double yPlaced
	) {
		if (this.isDried(itemstack)) return false;

		int id = world.getBlockId(blockX, blockY, blockZ);
		Block<?> block = Blocks.getBlock(id);

		if (Block.hasLogicClass(block, BlockLogicSign.class) && player != null && player.isSneaking()) {
			TileEntitySign sign = (TileEntitySign) world.getTileEntity(blockX, blockY, blockZ);
			if (sign.getColor().id == 15) return false;
			sign.setColor(TextFormatting.get(15));
			useSponge(itemstack, player);
			return true;
		} else if (Block.hasLogicClass(block, IPainted.class)) {
			IPainted painted = (IPainted) block.getLogic();
//			TODO - if block is already washed, don't wash it again
			painted.removeDye(world, blockX, blockY, blockZ);
			useSponge(itemstack, player);
			return true;
		}

		return false;
	}

	@Override
	public void onUseByActivator(
		ItemStack itemstack,
		TileEntityActivator activatorBlock,
		World world,
		Random random,
		int blockX,
		int blockY,
		int blockZ,
		double offX,
		double offY,
		double offZ,
		Direction direction
	) {
		int x = blockX + direction.getOffsetX();
		int y = blockY + direction.getOffsetY();
		int z = blockZ + direction.getOffsetZ();
		if (isDried(itemstack) && world.getBlockMaterial(x, y, z) == Material.water) {
			itemstack.setMetadata(0);
		} else {
			this.onUseItemOnBlock(itemstack, null, world, x, y, z, direction.getSide(), 0.5, 0.5);
		}
	}
}
