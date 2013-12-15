package oceania.entity;

import oceania.util.DataWatcherTypes;
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
		/* 
		Init your entity and DataWatcher variables here. The index, first variable in 
		'this.getDataWatcher().addObjectByDataType()', starts at 17. All below are used by
		the Entity itself. For the second variable, use the DataWatcherTypes enum. 
		 */
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) 
	{
		/*
		You need to save all of your DataWatcher variables to NBT. 
		See EntityOceaniaBoat for an example.
		*/
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) 
	{
		/*
		You need to reconstruct all of your DataWatcher variables here from NBT. 
		See EntityOceaniaBoat for an example.
		*/
	}

}
