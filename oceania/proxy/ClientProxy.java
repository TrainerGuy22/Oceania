package oceania.proxy;

import oceania.entity.EntityOceaniaBoat;
import oceania.entity.EntityOceaniaBoatNormal;
import oceania.entity.EntityOceaniaBoatWithChest;
import oceania.entity.render.RenderOceaniaBoat;
import oceania.entity.render.RenderOceaniaBoatWithChest;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init()
	{
		super.init();
		// RenderingRegistry.registerEntityRenderingHandler(EntityOceaniaBoatNormal.class, new RenderOceaniaBoat());
		RenderingRegistry.registerEntityRenderingHandler(EntityOceaniaBoatWithChest.class, new RenderOceaniaBoatWithChest());
	}
}
