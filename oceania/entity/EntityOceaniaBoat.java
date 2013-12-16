package oceania.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import oceania.items.Items;
import oceania.util.BoatType;
import oceania.util.DataWatcherTypes;

public abstract class EntityOceaniaBoat extends Entity
{
	public static final int		INDEX_BOAT_TYPE	= 20;
	private static final int	INDEX_HEALTH	= 21;
	private static final int	INDEX_OWNER		= 22;
	
	private int					ticksUntilHeal;
	
	public EntityOceaniaBoat(World world)
	{
		super(world);
		this.yOffset = 0.875f;
		this.motionX = 0.0f;
		this.motionY = 0.0f;
		this.motionZ = 0.0f;
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
		float hWidth = getBoatWidth() / 2.0f;
		float hLength = getBoatLength() / 2.0f;
		this.boundingBox.setBounds(x - hWidth, y - this.yOffset + getBoatHeight(), z - hLength, x + hWidth, y - this.yOffset + getBoatHeight() + this.height, z + hLength);
	}
	
	public void setPreviousPosition(double x, double y, double z)
	{
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}
	
	public abstract float getBoatWidth();
	
	public abstract float getBoatLength();
	
	public abstract float getBoatHeight();
	
	public abstract float getMaxSpeed();
	
	public abstract void dropItemsOnDeath();
	
	public int getMaxHealth()
	{
		return getBoatType().strength;
	}
	
	public String getOwner()
	{
		return this.dataWatcher.getWatchableObjectString(INDEX_OWNER);
	}
	
	public void setOwner(String owner)
	{
		this.dataWatcher.updateObject(INDEX_OWNER, (String) owner);
	}
	
	public int getBoatHealth()
	{
		return this.dataWatcher.getWatchableObjectInt(INDEX_HEALTH);
	}
	
	public void setBoatHealth(int health)
	{
		this.dataWatcher.updateObject(INDEX_HEALTH, (Integer) health);
	}
	
	@Override
	public boolean interactFirst(EntityPlayer player)
	{
		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != player)
		{
			return true;
		}
		else
		{
			if (!this.worldObj.isRemote)
			{
				player.mountEntity(this);
			}
			
			return true;
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBox(Entity other)
	{
		return other.boundingBox;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox()
	{
		return this.boundingBox;
	}
	
	@Override
	public boolean canBePushed()
	{
		return true;
	}
	
	@Override
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}
	
	@Override
	protected void entityInit()
	{
		this.dataWatcher.addObjectByDataType(INDEX_BOAT_TYPE, DataWatcherTypes.BYTE.ordinal());
		this.dataWatcher.addObjectByDataType(INDEX_HEALTH, DataWatcherTypes.INTEGER.ordinal());
		this.dataWatcher.addObjectByDataType(INDEX_OWNER, DataWatcherTypes.STRING.ordinal());
		
		setBoatType(BoatType.WOOD);
		setBoatHealth(getMaxHealth());
		setOwner("");
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		this.ticksUntilHeal = 2 * 20; // 2 seconds
		
		if (source.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) source.getEntity();
			if (this.getOwner().isEmpty() || player.getEntityName().equalsIgnoreCase(this.getOwner()))
			{
				ItemStack hand = player.getCurrentEquippedItem();
				if (hand == null) // Empty hand
				{
					this.dropItemsOnDeath();
					this.setDead();
				}
			}
		}
		
		return true;
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		if (this.ticksUntilHeal > 0)
		{
			if (--this.ticksUntilHeal == 0)
			{
				setBoatHealth(getMaxHealth());
			}
		}
	}
	
	public BoatType getBoatType()
	{
		byte typeIndex = this.getDataWatcher().getWatchableObjectByte(INDEX_BOAT_TYPE);
		if (typeIndex > 0 && typeIndex < BoatType.values().length)
			return BoatType.values()[typeIndex];
		return null;
	}
	
	public void setBoatType(BoatType type)
	{
		for (int index = 0; index < BoatType.values().length; index++)
		{
			if (BoatType.values()[index].equals(type))
			{
				this.getDataWatcher().updateObject(this.INDEX_BOAT_TYPE, (Byte) (byte) index);
			}
		}
		setBoatHealth(type.strength);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		tag.setByte("oBoatType", (byte) getBoatType().ordinal());
		tag.setInteger("oHealth", getBoatHealth());
		tag.setString("oOwner", getOwner());
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		setBoatType(BoatType.values()[tag.getByte("oBoatType")]);
		setBoatHealth(tag.getInteger("oHealth"));
		setOwner(tag.getString("oOwner"));
	}
	
}
