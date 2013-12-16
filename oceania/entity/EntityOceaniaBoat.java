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
	
	public static final int BYTE_BOAT_TYPE = 20;
	public static final int INT_DAMAGE = 21;

	public EntityOceaniaBoat(World world) 
	{
		super(world);
	}

	public EntityOceaniaBoat(World world, double x, double y, double z) 
	{
		super(world, x, y, z);
	}
	
	public EntityOceaniaBoat(World world, BoatTypes type, double x, double y, double z) 
	{
		super(world, x, y, z);
		this.setBoatType(type);
	}
	
	@Override
    protected void entityInit()
    {
		super.entityInit();
		
        /* This probably shouldn't be here
        this.getDataWatcher().addObjectByDataType(20, DataWatcherTypes.STRING.ordinal());
        this.getDataWatcher().addObjectByDataType(21, DataWatcherTypes.STRING.ordinal());
        this.getDataWatcher().addObjectByDataType(22, DataWatcherTypes.INTEGER.ordinal());
        this.getDataWatcher().addObjectByDataType(23, DataWatcherTypes.ITEMSTACK.ordinal());
		// The first parameter is a index. It's no more of a 'magic number' than something in an array.
		*/
        this.getDataWatcher().addObjectByDataType(this.BYTE_BOAT_TYPE, DataWatcherTypes.BYTE.ordinal());
        this.getDataWatcher().addObjectByDataType(this.INT_DAMAGE, DataWatcherTypes.INTEGER.ordinal());
    }
	
	public void setBoatType(BoatTypes type) 
	{
		for(int index = 0; index < BoatTypes.values().length; index++) {
			if(BoatTypes.values()[index].equals(type)) {
				this.getDataWatcher().updateObject(this.BYTE_BOAT_TYPE, (byte) index);
		        this.getDataWatcher().setObjectWatched(20);
		    }
		}
		this.getDataWatcher().updateObject(this.INT_DAMAGE, type.strength);
        this.getDataWatcher().setObjectWatched(21);
	}
	
	@Override
    public void setDead() {
		if(this.getTimeSinceHit() >= 1) 
		{
	        this.isDead = true;
		} else 
		{
			if(this.getDataWatcher().getWatchableObjectInt(this.INT_DAMAGE) == 1)
			{
				ItemStack stack = BoatTypes.values()[this.getDataWatcher().getWatchableObjectByte(BYTE_BOAT_TYPE)].resourceItem;
				if(this.worldObj.rand.nextBoolean())
				{
					this.entityDropItem(new ItemStack(stack.itemID, 1, stack.getItemDamage()), 0.0F);
					this.entityDropItem(new ItemStack(stack.itemID, 1, stack.getItemDamage()), 0.0F);
				} else 
					this.entityDropItem(new ItemStack(stack.itemID, 1, stack.getItemDamage()), 0.0F);
				this.dropItemsOnDead();
				this.isDead = true;
			} else
			{
				this.getDataWatcher().updateObject(this.INT_DAMAGE, this.getDataWatcher().getWatchableObjectInt(22) - 1);
			}
		}
	}
	
	public void dropItemsOnDead() 
	{}
	
	@Override
    public EntityItem dropItemWithOffset(int id, int count, float y)
	{
		if(id == Item.stick.itemID || id == Block.planks.blockID) 
		{
			return null;
		} else if(id == Item.boat.itemID) 
		{
			this.dropItemsOnDead();
			try {

					return this.entityDropItem(new ItemStack(Items.itemBoat, 1, this.dataWatcher.getWatchableObjectByte(this.BYTE_BOAT_TYPE)), y);
			} catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return super.dropItemWithOffset(id, count, y);
	}
	
	@Override
    public void writeEntityToNBT(NBTTagCompound tag)
    {
		tag.setByte("index", this.getDataWatcher().getWatchableObjectByte(this.BYTE_BOAT_TYPE));
		tag.setInteger("strength", this.getDataWatcher().getWatchableObjectInt(this.INT_DAMAGE));
    }
    
	@Override
    public void readEntityFromNBT(NBTTagCompound tag)
    {
		this.getDataWatcher().updateObject(this.BYTE_BOAT_TYPE, tag.getByte("index"));
        this.getDataWatcher().setObjectWatched(this.BYTE_BOAT_TYPE);
		this.getDataWatcher().updateObject(this.INT_DAMAGE, tag.getInteger("strength"));
        this.getDataWatcher().setObjectWatched(this.INT_DAMAGE);

    }

}
