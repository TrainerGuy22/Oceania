package oceania.entity;

import oceania.util.BoatTypes;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.world.World;

public class EntityOceaniaBoat extends EntityBoat 
{
	
	public BoatTypes boatType = null;

	public EntityOceaniaBoat(World world, BoatTypes type) 
	{
		super(world);
		this.boatType = type;
	}

}
