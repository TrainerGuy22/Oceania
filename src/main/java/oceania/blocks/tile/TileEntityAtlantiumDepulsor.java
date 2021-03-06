package oceania.blocks.tile;

import oceania.blocks.Blocks;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityAtlantiumDepulsor extends TileEntity 
{
	// Holds 32 Ender Pearls at a time, or 2 stacks.
	public int enderPearls = 0;
	
	// Each Ender Pearl lasts for half a Minecraft day.
	public int TIME_LEFT = 0;
	
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
				this.worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, worldObj.getBlockId(xCoord, yCoord, zCoord), 1);
			TIME_LEFT--;
		} else if(enderPearls != 0) 
		{
			enderPearls--;
			TIME_LEFT = 12000;
		} else
		{
			int radius = 3;
			for (int xx = xCoord - radius; xx <= xCoord + radius; xx++)
			{
				for (int yy = yCoord - radius; yy <= yCoord + radius; yy++)
				{
					for (int zz = zCoord - radius; zz <= zCoord + radius; zz++)
					{
						int bID = worldObj.getBlockId(xx, yy, zz);
						if (bID == Blocks.blockPlaceholder.blockID)
						{
							worldObj.setBlockToAir(xx, yy, zz);
						}
					}
				}
			}
		}
	}
	
    @Override
    public void readFromNBT(NBTTagCompound tag) 
    {
            super.readFromNBT(tag);
            enderPearls = tag.getInteger("ENDER_PEARLS");
            TIME_LEFT = tag.getInteger("TIME_LEFT");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tag) 
    {
            super.writeToNBT(tag);
            tag.setInteger("ENDER_PEARLS", enderPearls);
            tag.setInteger("TIME_LEFT", TIME_LEFT);
    }

}
