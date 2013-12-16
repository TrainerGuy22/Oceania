package oceania.entity;

import oceania.util.DataWatcherTypes;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntitySubmarine extends Entity 
{
	private static final int INDEX_HEALTH = 17;

	public EntitySubmarine(World world) 
	{
		super(world);
		this.boundingBox.maxX = this.boundingBox.minX + 3.0f;
		this.boundingBox.maxY = this.boundingBox.minY + 2.35f;
		this.boundingBox.maxZ = this.boundingBox.minZ + 4.0f;
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
	
	public void setPreviousPosition(double x, double y, double z) 
	{
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}
	
	public int getHealth()
	{
		return this.dataWatcher.getWatchableObjectInt(INDEX_HEALTH);
	}
	
	public void setHealth(int health)
	{
		if (health < 0 || health > 20)
			return;
		this.dataWatcher.updateObject(INDEX_HEALTH, (Integer) health);
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
    public void onUpdate()
    {
    	super.onUpdate();
    	
    	if (this.getHealth() < 0)
    		this.setDead();
    	
    	if (this.getHealth() > 20)
    		this.setHealth(20);
    }

	@Override
	protected void entityInit() 
	{
		this.dataWatcher.addObjectByDataType(INDEX_HEALTH, DataWatcherTypes.INTEGER.ordinal());
		
		setHealth(20);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) 
	{
		setHealth(tag.getInteger("subHealth"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) 
	{
		tag.setInteger("subHealth", getHealth());
	}

}
