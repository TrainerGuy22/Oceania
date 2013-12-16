package oceania.entity;

import oceania.items.Items;
import oceania.util.BoatTypes;
import oceania.util.DataWatcherTypes;
import net.minecraft.block.Block;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityOceaniaBoat extends EntityBoat
{
	
	public static final int	INDEX_BOAT_TYPE	= 20;
	public static final int	INDEX_DAMAGE	= 21;
	
	public EntityOceaniaBoat(World world)
	{
		super(world);
	}
	
	public EntityOceaniaBoat(World world, double x, double y, double z)
	{
		super(world, x, y, z);
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
		
		this.dataWatcher.addObjectByDataType(INDEX_BOAT_TYPE, DataWatcherTypes.BYTE.ordinal());
		this.dataWatcher.addObjectByDataType(INDEX_DAMAGE, DataWatcherTypes.INTEGER.ordinal());
		
		this.dataWatcher.updateObject(INDEX_BOAT_TYPE, 0);
		this.dataWatcher.updateObject(INDEX_DAMAGE, 0);
	}
	
	public void setBoatType(BoatTypes type)
	{
		for (int index = 0; index < BoatTypes.values().length; index++)
		{
			if (BoatTypes.values()[index].equals(type))
			{
				this.getDataWatcher().updateObject(this.INDEX_BOAT_TYPE, (byte) index);
			}
		}
		this.getDataWatcher().updateObject(this.INDEX_DAMAGE, type.strength);
	}
	
	/*
	 * @Override
	 * public void setDead()
	 * {
	 * if (this.getTimeSinceHit() >= 1)
	 * {
	 * this.isDead = true;
	 * }
	 * else
	 * {
	 * if (this.getDataWatcher().getWatchableObjectInt(this.INDEX_DAMAGE) == 1)
	 * {
	 * ItemStack stack = BoatTypes.values()[this.getDataWatcher().getWatchableObjectByte(INDEX_BOAT_TYPE)].resourceItem;
	 * if (this.worldObj.rand.nextBoolean())
	 * {
	 * this.entityDropItem(new ItemStack(stack.itemID, 1, stack.getItemDamage()), 0.0F);
	 * this.entityDropItem(new ItemStack(stack.itemID, 1, stack.getItemDamage()), 0.0F);
	 * }
	 * else
	 * this.entityDropItem(new ItemStack(stack.itemID, 1, stack.getItemDamage()), 0.0F);
	 * this.dropItemsOnDead();
	 * this.isDead = true;
	 * }
	 * else
	 * {
	 * this.getDataWatcher().updateObject(this.INDEX_DAMAGE, this.getDataWatcher().getWatchableObjectInt(22) - 1);
	 * }
	 * }
	 * }
	 */
	
	public void dropItemsOnDead()
	{
	}
	
	@Override
	public EntityItem dropItemWithOffset(int id, int count, float y)
	{
		if (id == Item.stick.itemID || id == Block.planks.blockID)
		{
			return null;
		}
		else if (id == Item.boat.itemID)
		{
			this.dropItemsOnDead();
			try
			{
				
				return this.entityDropItem(new ItemStack(Items.itemBoat, 1, this.dataWatcher.getWatchableObjectByte(this.INDEX_BOAT_TYPE)), y);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return super.dropItemWithOffset(id, count, y);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		tag.setByte("index", (byte) this.getDataWatcher().getWatchableObjectInt(this.INDEX_BOAT_TYPE));
		tag.setInteger("strength", this.getDataWatcher().getWatchableObjectInt(this.INDEX_DAMAGE));
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		this.getDataWatcher().updateObject(this.INDEX_BOAT_TYPE, tag.getByte("index"));
		this.getDataWatcher().setObjectWatched(this.INDEX_BOAT_TYPE);
		this.getDataWatcher().updateObject(this.INDEX_DAMAGE, tag.getInteger("strength"));
		this.getDataWatcher().setObjectWatched(this.INDEX_DAMAGE);
		
	}
	
}
