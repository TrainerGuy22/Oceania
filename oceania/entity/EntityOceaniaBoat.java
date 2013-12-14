package oceania.entity;

import oceania.util.BoatTypes;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityOceaniaBoat extends EntityBoat 
{
	
	public ResourceLocation texture;
	public int strength = -1;

	public EntityOceaniaBoat(World world) 
	{
		super(world);
	}

	public EntityOceaniaBoat(World world, double x, double y, double z) 
	{
		super(world, x, y, z);
	}
	
	public void setBoatType(BoatTypes type) {
		// this.getDataWatcher();
	}

}
