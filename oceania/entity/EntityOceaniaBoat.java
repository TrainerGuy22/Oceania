package oceania.entity;

import cpw.mods.fml.relauncher.ReflectionHelper;
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
	public static final float	ENT_WIDTH		= 1.5f;
	public static final float	ENT_LENGTH		= 1.5f;
	public static final float	ENT_HEIGHT		= 0.2f;
	
	public static final int		INDEX_BOAT_TYPE	= 20;
	public static final int		INDEX_DAMAGE	= 21;
	
	public EntityOceaniaBoat(World world)
	{
		super(world);
		this.yOffset = 0.875f;
	}
	
	public EntityOceaniaBoat(World world, double x, double y, double z)
	{
		this(world);
		this.setPosition(x, y, z);
	}
	
	@Override
	public void setPosition(double x, double y, double z)
	{
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		float hWidth = ENT_WIDTH / 2.0f;
		float hLength = ENT_LENGTH / 2.0f;
		this.boundingBox.setBounds(x - hWidth, y - this.yOffset + ENT_HEIGHT, z - hLength, x + hWidth, y - this.yOffset + ENT_HEIGHT + this.height, z + hLength);
	}
	
	public void setPreviousPosition(double x, double y, double z)
	{
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
		
		this.dataWatcher.addObjectByDataType(INDEX_BOAT_TYPE, DataWatcherTypes.BYTE.ordinal());
		this.dataWatcher.addObjectByDataType(INDEX_DAMAGE, DataWatcherTypes.INTEGER.ordinal());
		
		this.dataWatcher.updateObject(INDEX_BOAT_TYPE, (Byte) (byte) 0);
		this.dataWatcher.updateObject(INDEX_DAMAGE, 0);
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		//this.setDead();
	}
	
	public void setBoatType(BoatTypes type)
	{
		for (int index = 0; index < BoatTypes.values().length; index++)
		{
			if (BoatTypes.values()[index].equals(type))
			{
				this.getDataWatcher().updateObject(this.INDEX_BOAT_TYPE, (Byte) (byte) index);
				ReflectionHelper.setPrivateValue(EntityBoat.class, this, 0.07 * (2.0 * (index + 1)), "speedMultiplier", "field_70276_b");
			}
		}
		this.getDataWatcher().updateObject(this.INDEX_DAMAGE, type.strength);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		tag.setByte("index", this.getDataWatcher().getWatchableObjectByte(this.INDEX_BOAT_TYPE));
		tag.setInteger("strength", this.getDataWatcher().getWatchableObjectInt(this.INDEX_DAMAGE));
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		this.getDataWatcher().updateObject(this.INDEX_BOAT_TYPE, tag.getByte("index"));
		this.getDataWatcher().updateObject(this.INDEX_DAMAGE, tag.getInteger("strength"));
		
	}
	
}
