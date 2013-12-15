package oceania.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntitySubmarine extends Entity 
{

	public EntitySubmarine(World world) 
	{
		super(world);
	}
	
	public EntitySubmarine(World world, double x, double y, double z) 
	{
		super(world);
		this.setPosition(x, y + (double) yOffset, z);
		this.setPreviousPosition(x, y, z);
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
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
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) 
	{
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) 
	{
		
	}

}
