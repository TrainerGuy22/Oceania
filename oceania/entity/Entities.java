package oceania.entity;

import oceania.Oceania;
import cpw.mods.fml.common.registry.EntityRegistry;

public class Entities {
	
	public static void initEntities()
	{
		EntityRegistry.registerModEntity(EntityOceaniaBoat.class, "OceaniaBoat", 0, Oceania.INSTANCE, 32, 5, true);
	}
	
}
