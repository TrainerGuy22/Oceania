package oceania.entity;

import oceania.util.BoatTypes;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.item.EntityBoat;
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
	
	public void setBoatType(BoatTypes type) 
	{
		this.getDataWatcher().addObject(10, type.worldTexture.getResourceDomain());
        this.getDataWatcher().setObjectWatched(10);
		this.getDataWatcher().addObject(11, type.worldTexture.getResourcePath());
        this.getDataWatcher().setObjectWatched(11);
		this.getDataWatcher().addObject(12, type.strength);
        this.getDataWatcher().setObjectWatched(12);
	}

}
