package rootenginear.carrycakeandpie.entity;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;

public class TileEntityPiePumpkin extends TileEntity {
	public boolean canBeCarried(World world, Entity potentialHolder) {
		return true;
	}
}
