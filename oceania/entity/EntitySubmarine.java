package oceania.entity;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import oceania.items.Items;
import oceania.util.DataWatcherTypes;
import oceania.util.OUtil;
import cpw.mods.fml.relauncher.Side;

public class EntitySubmarine extends EntityOceaniaBoat
{
	private static final int	INDEX_GAMMA			= 23;
	
	public static final float	ENT_WIDTH			= 3.0f;
	public static final float	ENT_LENGTH			= 4.0f;
	public static final float	ENT_HEIGHT			= 2.35f;
	private static final float	VERTICAL_VELOCITY	= 0.5f;
	
	private Entity				lastRidingEntity;
	
	public EntitySubmarine(World world)
	{
		super(world);
		this.boundingBox.maxX = this.boundingBox.minX + 3.0f;
		this.boundingBox.maxY = this.boundingBox.minY + 2.35f;
		this.boundingBox.maxZ = this.boundingBox.minZ + 4.0f;
		this.ignoreFrustumCheck = true;
		this.waterOffset = -2.0f;
		this.lastRidingEntity = null;
	}
	
	public EntitySubmarine(World world, double x, double y, double z)
	{
		this(world);
		this.setPosition(x, y + (double) yOffset, z);
		this.setPreviousPosition(x, y, z);
	}
	
	@Override
	public double getMountedYOffset()
	{
		return 1.5f;
	}
	
	@Override
	public boolean interactFirst(EntityPlayer player)
	{
		boolean superResult = super.interactFirst(player);
		if (OUtil.getSide() == Side.CLIENT)
		{
			GameSettings settings = Minecraft.getMinecraft().gameSettings;
			this.dataWatcher.updateObject(INDEX_GAMMA, settings.gammaSetting);
			settings.gammaSetting = 10.0f;
		}
		return superResult;
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
		
		this.dataWatcher.addObjectByDataType(INDEX_GAMMA, DataWatcherTypes.FLOAT.ordinal());
		this.dataWatcher.updateObject(INDEX_GAMMA, 1.0f);
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		if (this.riddenByEntity instanceof EntityPlayer && worldObj.isAABBInMaterial(boundingBox, Material.water))
		{
			EntityPlayer player = (EntityPlayer) this.riddenByEntity;
			float pPitch = player.rotationPitch;
			pPitch /= -90.0f; // -1 == down, 1 == up
			float vVel = pPitch * VERTICAL_VELOCITY;
			this.moveEntity(0.0, vVel, 0.0);
		}
		
		if (OUtil.getSide() == Side.CLIENT)
		{
			if (this.riddenByEntity instanceof EntityPlayer)
			{
				Minecraft.getMinecraft().gameSettings.gammaSetting = 10.0f;
				((EntityPlayer) this.riddenByEntity).setAir(300);
			}
			else if (this.riddenByEntity == null && this.lastRidingEntity != null)
			{
				if (this.lastRidingEntity instanceof EntityPlayer)
				{
					Minecraft.getMinecraft().gameSettings.gammaSetting = this.dataWatcher.getWatchableObjectFloat(INDEX_GAMMA);
				}
			}
			
			this.lastRidingEntity = this.riddenByEntity;
		}
	}
	
	@Override
	public void updateRiderPosition()
	{
		if (this.riddenByEntity != null)
		{
			double xOffset = 0.9 * Math.cos(((double) this.rotationYaw - 180.0) * Math.PI / 180.0D);
			double zOffset = 0.9 * Math.sin(((double) this.rotationYaw - 180.0) * Math.PI / 180.0D);
			this.riddenByEntity.setPosition(this.posX + xOffset, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + zOffset);
		}
	}
	
	@Override
	public float getBoatWidth()
	{
		return ENT_WIDTH;
	}
	
	@Override
	public float getBoatLength()
	{
		return ENT_LENGTH;
	}
	
	@Override
	public float getBoatHeight()
	{
		return ENT_HEIGHT;
	}
	
	@Override
	public float getMaxSpeed()
	{
		return 3.5f;
	}
	
	@Override
	public void dropItemsOnDeath()
	{
		this.dropItem(Items.itemSubmarine.itemID, 1);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);
		tag.setFloat("lastGamma", this.dataWatcher.getWatchableObjectFloat(INDEX_GAMMA));
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		super.readEntityFromNBT(tag);
		this.dataWatcher.updateObject(INDEX_GAMMA, (Float) tag.getFloat("lastGamma"));
	}
	
}
