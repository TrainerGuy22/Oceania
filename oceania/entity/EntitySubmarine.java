package oceania.entity;

import oceania.util.DataWatcherTypes;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntitySubmarine extends Entity 
{
	private static final int INDEX_HEALTH = 17;
	private static final int INDEX_TIME_SINCE_HIT = 18;

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
		this.boundingBox.maxX = this.boundingBox.minX + 3.0f;
		this.boundingBox.maxY = this.boundingBox.minY + 2.35f;
		this.boundingBox.maxZ = this.boundingBox.minZ + 4.0f;
	}
	
	public void setPreviousPosition(double x, double y, double z) 
	{
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}
	
	/**
     * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
     * pushable on contact, like boats or minecarts.
     */
    public AxisAlignedBB getCollisionBox(Entity other)
    {
        return other.boundingBox;
    }

    /**
     * returns the bounding box for this entity
     */
    public AxisAlignedBB getBoundingBox()
    {
        return this.boundingBox;
    }

	@Override
	protected void entityInit() 
	{
		
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
