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
	public static final float	ENT_WIDTH	= 3.0f;
	public static final float	ENT_LENGTH	= 4.0f;
	public static final float	ENT_HEIGHT	= 2.35f;
	
	public float				velForward;
	public float				velTurning;
	
	public EntitySubmarine(World world)
	{
		super(world);
		this.boundingBox.maxX = this.boundingBox.minX + 3.0f;
		this.boundingBox.maxY = this.boundingBox.minY + 2.35f;
		this.boundingBox.maxZ = this.boundingBox.minZ + 4.0f;
		this.velForward = this.velTurning = 0.0f;
		this.ignoreFrustumCheck = true;
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
		return 5;
	}
	
	@Override
	public void dropItemsOnDeath()
	{
		this.dropItem(Items.itemSubmarine.itemID, 1);
	}
	
}
