package net.minecraft.src.buildcraft;

import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.World;

public class TileIronPipe extends TilePipe {

	boolean lastPower = false;
	
	public void checkPower () {
		World w = ModLoader.getMinecraftInstance().theWorld;
		boolean currentPower = w.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
		
		if (lastPower != currentPower) {
			moveOrientation();
		}
		
		lastPower = currentPower;
	}
	
	public void moveOrientation() {
		int metadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
		
		int nextMetadata = metadata;
		
		for (int l = 0; l < 6; ++l) {
			nextMetadata ++;
			
			if (nextMetadata > 5) {
				nextMetadata = 0;
			}
			
			Position pos = new Position(xCoord, yCoord, zCoord,
					Orientations.values()[nextMetadata]);
			pos.moveForwards(1.0);
			
			TileEntity tile = world.getBlockTileEntity((int) pos.i,
					(int) pos.j, (int) pos.k);
			
			if (tile instanceof IPipeEntry || tile instanceof TileEntityChest) {
				world.setBlockMetadata(xCoord, yCoord, zCoord, nextMetadata);
				return;
			}
		}
	}
	
	protected Orientations resolveDestination (EntityData data) {
		int metadata = world.getBlockMetadata(xCoord, yCoord, zCoord);
		
		if (metadata != -1) {
			Position pos = new Position(xCoord, yCoord, zCoord,
					Orientations.values()[metadata]);
			
			
			
			pos.moveForwards(1.0);
			
			TileEntity tile = world.getBlockTileEntity((int) pos.i,
					(int) pos.j, (int) pos.k);
			
			if (tile instanceof IPipeEntry || tile instanceof TileEntityChest) {
				return pos.orientation;
			}
		}
		
		return Orientations.Unknown;
	}
	
}
