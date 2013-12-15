package oceania.blocks.tile;

import java.util.List;
import java.util.Random;

import oceania.blocks.Blocks;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
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
    	if(cooldown != 0 && !worldObj.isRemote)
    		cooldown--;
    	if(this.worldObj.rand.nextInt(6) == 3 && worldObj.isRemote)
    		this.onRandomUpdate();
    	List<EntityVillager> villagers = this.worldObj.getEntitiesWithinAABB(EntityVillager.class, AxisAlignedBB.getBoundingBox(xCoord - maxRange, yCoord - 1 - maxRange, zCoord - maxRange, xCoord + 1 + maxRange, yCoord + 1 + maxRange, zCoord + maxRange));
    	int villagerCount = 0;
    	for(EntityVillager villager : villagers)
    	{
    		villagerCount++;
    	}
    	if(villagerCount < 4 && cooldown < 1 && !this.worldObj.isRemote)
    	{
    		boolean triggered = false;

        			int x = this.worldObj.rand.nextInt(maxRange * 2) - maxRange;
        			int z = this.worldObj.rand.nextInt(maxRange * 2) - maxRange;
        			while(x > (minRange * -1) - 1 && z < minRange + 1 && z < (minRange * -1) - 1 && z < minRange + 1)
        			{
            			x = this.worldObj.rand.nextInt(maxRange * 2) - maxRange;
            			z = this.worldObj.rand.nextInt(maxRange * 2) - maxRange;
        			}
        			if(this.worldObj.rand.nextInt(4) == 3 && !triggered)
        			{
        				boolean valid = false;
        				if(x <= 0)
        					valid =  this.worldObj.getBlockId(xCoord + 1 + x, yCoord - 2, zCoord + z) == 0 || this.worldObj.getBlockId(xCoord + 1 + x, yCoord - 2, zCoord + z) == Blocks.blockPlaceholder.blockID;
        				else 
        					valid =  this.worldObj.getBlockId(xCoord + x, yCoord - 2, zCoord + z) == 0 || this.worldObj.getBlockId(xCoord + x, yCoord - 2, zCoord + z)  == Blocks.blockPlaceholder.blockID;
        				if(valid)
        				{
        					System.out.println("Spawn.");
        					triggered = true;
        					cooldown = 40;
        					EntityVillager villagerToSpawn = new EntityVillager(worldObj);
            				if(x <= 0)
            					villagerToSpawn.setLocationAndAngles(xCoord + 1 + x, yCoord - 2, zCoord + z, 0.0F, 0.0F);
            				else 
            					villagerToSpawn.setLocationAndAngles(xCoord + x, yCoord - 2, zCoord + z, 0.0F, 0.0F);
            				((EntityLiving) villagerToSpawn).onSpawnWithEgg((EntityLivingData) null);
        					villagerToSpawn.setProfession(3);
        					this.worldObj.spawnEntityInWorld(villagerToSpawn);
        				}
    		}
    	}
    	super.updateEntity();
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
	
    @Override
    public void readFromNBT(NBTTagCompound tag) 
    {
            super.readFromNBT(tag);
            cooldown = tag.getInteger("cooldown");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tag) 
    {
            super.writeToNBT(tag);
            tag.setInteger("cooldown", cooldown);
    }

}
