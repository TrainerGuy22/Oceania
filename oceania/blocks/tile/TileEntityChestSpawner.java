package oceania.blocks.tile;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityChestSpawner extends TileEntityChest 
{
	
	private static int range = 7;
	private int cooldown = 0;
	
	public TileEntityChestSpawner()
	{
		super();
	}
	
	@Override
    public void updateEntity() 
    {
    	super.updateEntity();
    	cooldown--;
    	if(this.worldObj.rand.nextInt(6) == 3)
    	{
    		this.onRandomUpdate();
    	}
    	List<EntityVillager> villagers = this.worldObj.getEntitiesWithinAABB(EntityVillager.class, AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - 1 - range, zCoord - range, xCoord + 1 + range, yCoord + 1 + range, zCoord + range));
    	int villagerCount = 0;
    	for(EntityVillager villager : villagers)
    	{
    		villagerCount++;
    	}
    	if(villagerCount < 4 && cooldown == 0)
    	{
    		cooldown = 40;
    	}
    }
	
	private void onRandomUpdate()
	{
		Random random = this.worldObj.rand;
		double bX = (double) xCoord + 0.5;
		double bY = (double) yCoord + 0.5;
		double bZ = (double) zCoord + 0.5;
		
		int side = random.nextInt(6);
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		double pX = bX + ((double) dir.offsetX / 1.5);
		double pY = bY + ((double) dir.offsetY / 1.5);
		double pZ = bZ + ((double) dir.offsetZ / 1.5);
		
		for (int i = 0; i < 10; i++)
		{
			pX += (random.nextDouble() * 0.25) - 0.125f;
			pY += (random.nextDouble() * 0.25) - 0.125f;
			pZ += (random.nextDouble() * 0.25) - 0.125f;
			
			worldObj.spawnParticle("flame", pX, pY, pZ, (double) dir.offsetX * 0.5, (double) dir.offsetY * 0.5, (double) dir.offsetZ * 0.5);
		}
	}

}
