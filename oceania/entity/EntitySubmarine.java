package oceania.entity;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import oceania.items.Items;
import oceania.net.NetworkHandler;
import oceania.util.DataWatcherTypes;
import oceania.util.OUtil;

public class EntitySubmarine extends EntityOceaniaBoat
{
	public static final float	ENT_WIDTH	= 3.0f;
	public static final float	ENT_LENGTH	= 4.0f;
	public static final float	ENT_HEIGHT	= 2.35f;
	
	public EntitySubmarine(World world)
	{
		super(world);
		this.boundingBox.maxX = this.boundingBox.minX + 3.0f;
		this.boundingBox.maxY = this.boundingBox.minY + 2.35f;
		this.boundingBox.maxZ = this.boundingBox.minZ + 4.0f;
		this.ignoreFrustumCheck = true;
		this.waterOffset = -2.0f;
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
	public void onUpdate()
	{
		super.onUpdate();
		
		Minecraft.getMinecraft().gameSettings.gammaSetting = 10.0f;
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
	
}
