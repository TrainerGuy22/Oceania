package oceania.blocks.tile;

import java.util.List;
import java.util.Random;

import oceania.blocks.Blocks;
import oceania.entity.Entities;
import oceania.items.Items;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityChestSpawner extends TileEntityChest 
{
	
	public enum Loot
	{
		ATLANTITE(Items.itemMulti.itemID, 0, 3, 5, 0.4f),
		ATLANTIUM(Items.itemMulti.itemID, 1, 2, 3, 0.2f),
		SCREW(Items.itemMulti.itemID, 2, 7, 8, 0.7f),
		TRIDENT(Items.itemAtlantiteTrident.itemID, 0, 1, 1, 0.2f);
		
		public int itemID, metadata, min, max;
		public float chance;
		private Loot(int itemID, int metadata, int min, int max, float chance)
		{
			this.itemID = itemID;
			this.metadata = metadata;
			this.min = min;
			this.max = max;
			this.chance = chance;
		}
		
	}
	
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
        					villagerToSpawn.setProfession(Entities.VILLAGER_OCEANIA_ID);
        					this.worldObj.spawnEntityInWorld(villagerToSpawn);
        				}
    		}
    	}
    	super.updateEntity();
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
