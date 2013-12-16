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
import oceania.util.DataWatcherTypes;
import oceania.util.OUtil;

public class EntitySubmarine extends Entity
{
	public static final float	ENT_WIDTH		= 3.0f;
	public static final float	ENT_LENGTH		= 4.0f;
	public static final float	ENT_HEIGHT		= 2.35f;
	
	private static final float	GRAVITY			= -0.04f;
	private static final float	ACCELERATION	= 0.0125f;
	private static final float	TURN_SPEED		= 0.5f;
	private static final int	INDEX_HEALTH	= 20;
	private static final int	INDEX_OWNER		= 21;
	private static final int	MAX_HEALTH		= 300;
	
	private int					ticksUntilHeal;
	private float				velForward;
	private float				velTurning;
	
	public EntitySubmarine(World world)
	{
		super(world);
		this.boundingBox.maxX = this.boundingBox.minX + 3.0f;
		this.boundingBox.maxY = this.boundingBox.minY + 2.35f;
		this.boundingBox.maxZ = this.boundingBox.minZ + 4.0f;
		this.ticksUntilHeal = 0;
		this.velForward = this.velTurning = 0.0f;
		this.ignoreFrustumCheck = true;
	}
	
	public EntitySubmarine(World world, double x, double y, double z)
	{
		this(world);
		this.setPosition(x, y + (double) yOffset, z);
		this.setPreviousPosition(x, y, z);
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
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
					if (!player.capabilities.isCreativeMode)
						this.dropItem(Items.itemSubmarine.itemID, 1);
					this.setDead();
				}
			}
		}
		
		return true;
	}
	
	public int getHealth()
	{
		return this.dataWatcher.getWatchableObjectInt(INDEX_HEALTH);
	}
	
	public void setHealth(int health)
	{
		if (health < 0 || health > MAX_HEALTH)
			return;
		this.dataWatcher.updateObject(INDEX_HEALTH, (Integer) health);
	}
	
	public String getOwner()
	{
		return this.dataWatcher.getWatchableObjectString(INDEX_OWNER);
	}
	
	public void setOwner(String owner)
	{
		this.dataWatcher.updateObject(INDEX_OWNER, (String) owner);
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
	public void onUpdate()
	{
		super.onUpdate();
		
		if (this.getHealth() < 0)
			this.setDead();
		
		if (this.getHealth() > MAX_HEALTH)
			this.setHealth(MAX_HEALTH);
		
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		if (worldObj.isAABBInMaterial(this.boundingBox, Material.water))
		{
			motionX *= 0.98;
			motionY *= 0.98;
			motionZ *= 0.98;
			
			if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer)
			{
				velForward = 0.0f;
				velTurning = 0.0f;
				EntityPlayer player = (EntityPlayer) this.riddenByEntity;
				if (player.moveForward < -0.0001f)
				{
					velForward += ACCELERATION;
				}
				else if (player.moveForward > 0.0001f)
				{
					velForward -= ACCELERATION;
				}
				if (player.moveStrafing < -0.0001f)
				{
					velTurning = TURN_SPEED;
				}
				else if (player.moveStrafing > 0.0001f)
				{
					velTurning = -TURN_SPEED;
				}
				else
				{
					velTurning = 0.0f;
				}
				
				this.rotationYaw += velTurning;
				
				float cos = (float) Math.cos(this.rotationYaw * OUtil.PI_OVER_180);
				float sin = (float) Math.sin(this.rotationYaw * OUtil.PI_OVER_180);
				
				this.motionX += cos * velForward;
				this.motionZ += sin * velForward;
			}
		}
		else
		{
			motionY += GRAVITY;
			motionX *= 0.5;
			motionY *= 0.5;
			motionZ *= 0.5;
		}
		
		this.setPosition(posX + motionX, posY + motionY, posZ + motionZ);
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
	public double getMountedYOffset()
	{
		return 1.5f;
	}
	
	@Override
	public void updateRiderPosition()
	{
		if (this.riddenByEntity != null)
		{
			double xOffset = Math.cos((double) this.rotationYaw * Math.PI / 180.0D) * -0.9;
			double zOffset = Math.sin((double) this.rotationYaw * Math.PI / 180.0D) * -0.9;
			this.riddenByEntity.setPosition(this.posX + xOffset, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + zOffset);
		}
	}
	
	@Override
	protected void entityInit()
	{
		this.dataWatcher.addObjectByDataType(INDEX_HEALTH, DataWatcherTypes.INTEGER.ordinal());
		this.dataWatcher.addObjectByDataType(INDEX_OWNER, DataWatcherTypes.STRING.ordinal());
		
		setHealth(MAX_HEALTH);
		setOwner("");
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound tag)
	{
		setHealth(tag.getInteger("subHealth"));
		setOwner(tag.getString("subOwner"));
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound tag)
	{
		tag.setInteger("subHealth", getHealth());
		tag.setString("subOwner", getOwner());
	}
	
}
