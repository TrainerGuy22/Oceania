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
		
		/* The first parameter is a index. It's no more of a 'magic number' than something
		 * in an array.
		 */
        this.getDataWatcher().addObjectByDataType(20, DataWatcherTypes.STRING.ordinal());
        this.getDataWatcher().addObjectByDataType(21, DataWatcherTypes.STRING.ordinal());
        this.getDataWatcher().addObjectByDataType(22, DataWatcherTypes.INTEGER.ordinal());
        this.getDataWatcher().addObjectByDataType(23, DataWatcherTypes.ITEMSTACK.ordinal());
    }
	
	public void setBoatType(BoatTypes type) 
	{
		this.getDataWatcher().updateObject(20, type.worldTexture.getResourceDomain());
        this.getDataWatcher().setObjectWatched(20);
		this.getDataWatcher().updateObject(21, type.worldTexture.getResourcePath());
        this.getDataWatcher().setObjectWatched(21);
		this.getDataWatcher().updateObject(22, type.strength);
        this.getDataWatcher().setObjectWatched(22);
		this.getDataWatcher().updateObject(23, type.resourceItem);
        this.getDataWatcher().setObjectWatched(23);
	}
	
	@Override
    public void setDead() {
		if(this.getTimeSinceHit() >= 1) 
		{
	        this.isDead = true;
		} else 
		{
			if(this.getDataWatcher().getWatchableObjectInt(22) == 1)
			{
				ItemStack stack = this.getDataWatcher().getWatchableObjectItemStack(23);
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
				this.getDataWatcher().updateObject(22, this.getDataWatcher().getWatchableObjectInt(22) - 1);
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
				int strength = this.getDataWatcher().getWatchableObjectInt(22);
				for(int index = 0; index < BoatTypes.values().length; index++) {
					if(((Integer) BoatTypes.values()[index].strength).equals(strength)) {
						return this.entityDropItem(new ItemStack(Items.itemBoat, 1, index), y);
					}
				}
			} catch(Exception e)
			{}
		}
		return super.dropItemWithOffset(id, count, y);
	}
	
	@Override
    public void writeEntityToNBT(NBTTagCompound tag)
    {
		tag.setString("namespace", this.getDataWatcher().getWatchableObjectString(20));
		tag.setString("path", this.getDataWatcher().getWatchableObjectString(21));
		tag.setInteger("strength", this.getDataWatcher().getWatchableObjectInt(22));
		NBTTagCompound item = new NBTTagCompound();
		this.getDataWatcher().getWatchableObjectItemStack(23).writeToNBT(item);
		tag.setCompoundTag("item", item);
    }
    
	@Override
    public void readEntityFromNBT(NBTTagCompound tag)
    {
		this.getDataWatcher().updateObject(20, tag.getString("namespace"));
        this.getDataWatcher().setObjectWatched(20);
		this.getDataWatcher().updateObject(21, tag.getString("path"));
        this.getDataWatcher().setObjectWatched(21);
		this.getDataWatcher().updateObject(22, tag.getInteger("strength"));
        this.getDataWatcher().setObjectWatched(22);
		this.getDataWatcher().updateObject(23, ItemStack.loadItemStackFromNBT(tag.getCompoundTag("item")));
        this.getDataWatcher().setObjectWatched(23);
    }

}
