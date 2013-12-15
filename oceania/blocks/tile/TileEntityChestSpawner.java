package oceania.blocks.tile;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityChestSpawner extends TileEntityChest 
{
	
	private static final int minRange = 3;
	private static final int maxRange = 7;
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
    	List<EntityVillager> villagers = this.worldObj.getEntitiesWithinAABB(EntityVillager.class, AxisAlignedBB.getBoundingBox(xCoord - maxRange, yCoord - 1 - maxRange, zCoord - maxRange, xCoord + 1 + maxRange, yCoord + 1 + maxRange, zCoord + maxRange));
    	int villagerCount = 0;
    	for(EntityVillager villager : villagers)
    	{
    		villagerCount++;
    	}
    	if(villagerCount < 4 && cooldown == 0)
    	{
    		boolean triggered = false;
    		for(int xCount = 3; xCount <= 7; xCount++)
    		{
        		for(int zCount = 3; zCount <= 7; zCount++)
        		{
        			if(this.worldObj.getBlockId(xCoord + 1 + xCount, yCoord - 3, zCoord + zCount) == 0 && this.worldObj.rand.nextInt(6) == 3 && !triggered)
        			{
        				triggered = true;
        	    		cooldown = 40;
        	    		EntityVillager villagerToSpawn = new EntityVillager(worldObj, 3);
        	    		villagerToSpawn.chunkCoordX = xCoord + 1 + xCount;
        	    		villagerToSpawn.chunkCoordY = yCoord - 3;
        	    		villagerToSpawn.chunkCoordZ = zCoord + zCount;
        	    		this.worldObj.spawnEntityInWorld(villagerToSpawn);
        			}
        			if(this.worldObj.getBlockId(xCoord - xCount, yCoord - 3, zCoord - zCount) == 0 && this.worldObj.rand.nextInt(6) == 3 && !triggered)
        			{
        				triggered = true;
        	    		cooldown = 40;
        	    		EntityVillager villagerToSpawn = new EntityVillager(worldObj, 3);
        	    		villagerToSpawn.chunkCoordX = xCoord - xCount;
        	    		villagerToSpawn.chunkCoordY = yCoord - 3;
        	    		villagerToSpawn.chunkCoordZ = zCoord - zCount;
        	    		this.worldObj.spawnEntityInWorld(villagerToSpawn);
        			}
        		}
    		}
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
