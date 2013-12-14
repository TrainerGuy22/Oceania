package oceania.blocks.tile;

import oceania.blocks.Blocks;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class TileEntityAtlantiumDepulsor extends TileEntity 
{
	public int ENDER_PEARLS = 0;
	public int TIME_LEFT = 0;
	private int TICK_COUNTER = 0;
	
	public TileEntityAtlantiumDepulsor()
	{}
	
	@Override
    public void updateEntity()
	{
		if(TIME_LEFT != 0) 
		{
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			int radius = meta / 4;
			for (int xx = xCoord - radius; xx <= xCoord + radius; xx++)
			{
				for (int yy = yCoord - radius; yy <= yCoord + radius; yy++)
				{
					for (int zz = zCoord - radius; zz <= zCoord + radius; zz++)
					{
						int bID = worldObj.getBlockId(xx, yy, zz);
						if (bID == Block.waterMoving.blockID || bID == Block.waterStill.blockID || bID == 0)
						{
							worldObj.setBlock(xx, yy, zz, Blocks.blockPlaceholder.blockID);
						}
					}
				}
			}
			if (meta < 15)
				this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, ++meta, 0);
			// this.worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, worldObj.getBlockId(xCoord, yCoord, zCoord), 1);
			TICK_COUNTER++;
			if(TICK_COUNTER == 20)
			{
				TICK_COUNTER = 0;
				TIME_LEFT--;
			}
		} else if(ENDER_PEARLS != 0) 
		{
			ENDER_PEARLS--;
			TIME_LEFT = 12000;
		}
	}

}
