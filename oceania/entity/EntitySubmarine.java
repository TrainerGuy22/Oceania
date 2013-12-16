package oceania.entity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import oceania.items.Items;
import oceania.net.NetworkHandler;
import oceania.util.DataWatcherTypes;
import oceania.util.OUtil;

public class EntitySubmarine extends EntityOceaniaBoat
{
	public static final float	ENT_WIDTH		= 3.0f;
	public static final float	ENT_LENGTH		= 4.0f;
	public static final float	ENT_HEIGHT		= 2.35f;
	
	public float				velForward;
	public float				velTurning;
	
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
		
		setBoatHealth(MAX_HEALTH);
		setOwner("");
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		setBoatHealth(tag.getInteger("subHealth"));
		setOwner(tag.getString("subOwner"));
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		tag.setInteger("subHealth", getBoatHealth());
		tag.setString("subOwner", getOwner());
	}
	
}
