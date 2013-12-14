package oceania.entity;

import oceania.util.BoatTypes;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityOceaniaBoat extends EntityBoat 
{

	public EntityOceaniaBoat(World world) 
	{
		super(world);
	}

	public EntityOceaniaBoat(World world, double x, double y, double z) 
	{
		super(world, x, y, z);
	}
	
	public EntityOceaniaBoat(World world, BoatTypes type, double x, double y, double z) 
	{
		super(world, x, y, z);
		this.setBoatType(type);
	}
	
	@Override
    protected void entityInit()
    {
		super.entityInit();
        this.getDataWatcher().addObjectByDataType(20, 4);
        this.getDataWatcher().addObjectByDataType(21, 4);
        this.getDataWatcher().addObjectByDataType(22, 2);
    }
	
	public void setBoatType(BoatTypes type) 
	{
		
		this.getDataWatcher().updateObject(20, type.worldTexture.getResourceDomain());
        this.getDataWatcher().setObjectWatched(20);
		this.getDataWatcher().updateObject(21, type.worldTexture.getResourcePath());
        this.getDataWatcher().setObjectWatched(21);
		this.getDataWatcher().updateObject(22, type.strength);
        this.getDataWatcher().setObjectWatched(22);
	}
	
	@Override
    public void writeEntityToNBT(NBTTagCompound tag)
    {
		tag.setString("namespace", this.getDataWatcher().getWatchableObjectString(20));
		tag.setString("path", this.getDataWatcher().getWatchableObjectString(21));
		tag.setInteger("strength", this.getDataWatcher().getWatchableObjectInt(22));
    }
    
	@Override
    public void readEntityFromNBT(NBTTagCompound tag)
    {
    	
    }

}
