package oceania.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import oceania.items.Items;
import oceania.net.NetworkHandler;
import oceania.util.BoatType;
import oceania.util.DataWatcherTypes;

public abstract class EntityOceaniaBoat extends EntityBoat
{
	private static final int	INDEX_BOAT_TYPE	= 20;
	private static final int	INDEX_HEALTH	= 21;
	private static final int	INDEX_OWNER		= 22;
	
	private static final double	TURN_SPEED		= 20.0;
	
	private int					ticksUntilHeal;
	private int					yawDelta;
	protected float				waterOffset;
	
	public EntityOceaniaBoat(World world)
	{
		super(world);
		this.waterOffset = -0.25f;
		this.motionX = 0.0f;
		this.motionY = 0.0f;
		this.motionZ = 0.0f;
		this.setPosition(0.0f, 0.0f, 0.0f);
		this.ignoreFrustumCheck = true;
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
		this.boundingBox.setBounds(x - hWidth, y + yOffset, z - hLength, x + hWidth, y + yOffset + getBoatHeight(), z + hLength);
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
		if (health <= 0)
		{
			dropItemsOnDeath();
			setDead();
		}
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
		super.entityInit();
		
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
		// super.onUpdate();
		
		if (this.ticksUntilHeal > 0)
		{
			if (--this.ticksUntilHeal == 0)
			{
				setBoatHealth(getMaxHealth());
			}
		}
		
		updateBoat();
		
		NetworkHandler.INSTANCE.sendBoatPosition(this.entityId, this.motionX, this.motionY, this.motionZ, this.posX, this.posY, this.posZ, this.rotationYaw);
	}
	
	public void updateBoat()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		int waterDelta = 5;
		double waterLevel = 0.0;
		
		for (int i = 0; i < waterDelta; i++)
		{
			double waterMin = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double) i / (double) waterDelta - waterOffset;
			double waterMax = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double) (i + 1) / (double) waterDelta - waterOffset;
			AxisAlignedBB aabb = AxisAlignedBB.getAABBPool().getAABB(this.boundingBox.minX, waterMin, this.boundingBox.minZ, this.boundingBox.maxX, waterMax, this.boundingBox.maxZ);
			
			if (this.worldObj.isAABBInMaterial(aabb, Material.water))
			{
				waterLevel += 1.0 / (double) waterDelta;
			}
		}
		
		double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		
		if (velocity > 0.2625)
		{
			double offsetX = Math.cos((double) this.rotationYaw * Math.PI / 180.0D);
			double offsetY = Math.sin((double) this.rotationYaw * Math.PI / 180.0D);
			
			for (int dist = 0; (double) dist < (velocity * 60.0) + 1; dist++)
			{
				double randX = (double) (this.rand.nextFloat() * 2.0F - 1.0F);
				double randZ = (double) (this.rand.nextInt(2) * 2 - 1) * 0.7D;
				double particleX;
				double particleZ;
				
				if (this.rand.nextBoolean())
				{
					particleX = this.posX - offsetX * randX * 0.8D + offsetY * randZ;
					particleZ = this.posZ - offsetY * randX * 0.8D - offsetX * randZ;
					this.worldObj.spawnParticle("splash", particleX, this.posY - 0.125D, particleZ, this.motionX, this.motionY, this.motionZ);
				}
				else
				{
					particleX = this.posX + offsetX + offsetY * randX * 0.7D;
					particleZ = this.posZ + offsetY - offsetX * randX * 0.7D;
					this.worldObj.spawnParticle("splash", particleX, this.posY - 0.125D, particleZ, this.motionX, this.motionY, this.motionZ);
				}
			}
		}
		
		double newPosX, newPosY, newPosZ;
		
		if (this.worldObj.isRemote)
		{
			newPosX = this.posX + this.motionX;
			newPosY = this.posY + this.motionY;
			newPosZ = this.posZ + this.motionZ;
			this.setPosition(newPosX, newPosY, newPosZ);
			
			if (this.onGround)
			{
				this.motionX *= 0.5D;
				this.motionY *= 0.5D;
				this.motionZ *= 0.5D;
			}
			
			this.motionX *= 0.99;
			this.motionY *= 0.95;
			this.motionZ *= 0.99;
		}
		else
		{
			if (waterLevel < 1.0)
			{
				double waterOffset = (waterLevel * 2.0) - 1.0;
				this.motionY += 0.04 * waterOffset;
			}
			else
			{
				if (this.motionY < 0.0)
				{
					this.motionY /= 2.0;
				}
				
				this.motionY += 0.007;
			}
			
			double fwdSpd = 0.0f;
			
			if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase)
			{
				double forward = (double) ((EntityLivingBase) this.riddenByEntity).moveForward;
				
				if (forward > 0.0)
				{
					fwdSpd = getMaxSpeed() / 10.0f;
				}
				else if (forward < -0.0) // lol, -0
				{
					fwdSpd = -getMaxSpeed() / 25.0f;
				}
				
				double xOffset = -Math.sin((double) (this.riddenByEntity.rotationYaw * (float) Math.PI / 180.0F));
				double zOffset = Math.cos((double) (this.riddenByEntity.rotationYaw * (float) Math.PI / 180.0F));
				this.motionX += xOffset * fwdSpd * 0.05;
				this.motionZ += zOffset * fwdSpd * 0.05;
			}
			
			double newVelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			
			if (newVelocity > getMaxSpeed())
			{
				double velRatio = getMaxSpeed() / newVelocity;
				this.motionX *= velRatio;
				this.motionZ *= velRatio;
				newVelocity = getMaxSpeed();
			}
			
			if (this.onGround)
			{
				this.motionX *= 0.5;
				this.motionY *= 0.5;
				this.motionZ *= 0.5;
			}
			
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			
			if (this.isCollidedHorizontally && velocity > 0.2)
			{
				if (!this.worldObj.isRemote && !this.isDead)
				{
					int damage = (int) ((velocity - 0.2) / 0.5);
					setBoatHealth(getBoatHealth() - damage);
					this.ticksUntilHeal = 5 * 20; // 5 seconds
				}
			}
			else
			{
				this.motionX *= 0.99;
				this.motionY *= 0.95;
				this.motionZ *= 0.99;
			}
			
			this.rotationPitch = 0.0f;
			double newYaw = (double) this.rotationYaw;
			double xDelta = this.prevPosX - this.posX;
			double zDelta = this.prevPosZ - this.posZ;
			double newVel2 = (xDelta * xDelta) + (zDelta * zDelta);
			double velDir = Math.atan2(zDelta, xDelta) * 180.0 / Math.PI;
			
			if (newVel2 > 0.001)
			{
				newYaw = velDir;
			}
			
			double yawDelta = MathHelper.wrapAngleTo180_double(newYaw - (double) this.rotationYaw);
			
			if (yawDelta > TURN_SPEED)
			{
				yawDelta = TURN_SPEED;
			}
			
			if (yawDelta < -TURN_SPEED)
			{
				yawDelta = -TURN_SPEED;
			}
			
			this.rotationYaw = (float) ((double) this.rotationYaw + yawDelta);
			this.setRotation(this.rotationYaw, this.rotationPitch);
			
			if (!this.worldObj.isRemote)
			{
				List<Entity> entities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.2, 0.0, 0.2));
				
				if (entities != null && !entities.isEmpty())
				{
					for (Entity e : entities)
					{
						if (e != this.riddenByEntity && e.canBePushed() && e instanceof EntityBoat)
						{
							e.applyEntityCollision(this);
						}
					}
				}
				
				for (int dir = 0; dir < 4; dir++)
				{
					int x = MathHelper.floor_double(this.posX + ((double) (dir % 2) - 0.5D) * 0.8D);
					int z = MathHelper.floor_double(this.posZ + ((double) (dir / 2) - 0.5D) * 0.8D);
					
					for (int yOff = 0; yOff < 2; yOff++)
					{
						int y = MathHelper.floor_double(this.posY) + yOff;
						int blockID = this.worldObj.getBlockId(x, y, z);
						
						if (blockID == Block.snow.blockID)
						{
							this.worldObj.setBlockToAir(x, y, z);
						}
						else if (blockID == Block.waterlily.blockID)
						{
							this.worldObj.destroyBlock(x, y, z, true);
						}
					}
				}
				
				if (this.riddenByEntity != null && this.riddenByEntity.isDead)
				{
					this.riddenByEntity = null;
				}
			}
		}
	}
	
	public BoatType getBoatType()
	{
		byte typeIndex = this.dataWatcher.getWatchableObjectByte(INDEX_BOAT_TYPE);
		if (typeIndex >= 0 && typeIndex < BoatType.values().length)
			return BoatType.values()[typeIndex];
		return null;
	}
	
	public void setBoatType(BoatType type)
	{
		for (int index = 0; index < BoatType.values().length; index++)
		{
			if (BoatType.values()[index].equals(type))
			{
				this.dataWatcher.updateObject(this.INDEX_BOAT_TYPE, (Byte) (byte) index);
			}
		}
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
