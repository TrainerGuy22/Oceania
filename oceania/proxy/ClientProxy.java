package oceania.proxy;

import oceania.entity.EntityOceaniaBoat;
import oceania.entity.render.RenderOceaniaBoat;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init()
	{
		super.init();
		RenderingRegistry.registerEntityRenderingHandler(EntityOceaniaBoat.class, new RenderOceaniaBoat());
	}
}
