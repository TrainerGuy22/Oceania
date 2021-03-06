package oceania.client;

import oceania.common.CommonProxy;
import oceania.entity.EntityOceaniaBoat;
import oceania.entity.EntityOceaniaBoatNormal;
import oceania.entity.EntityOceaniaBoatWithChest;
import oceania.entity.EntitySubmarine;
import oceania.entity.render.RenderOceaniaBoat;
import oceania.entity.render.RenderOceaniaBoatWithChest;
import oceania.entity.render.RenderSubmarine;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void init() {
		super.init();
		RenderingRegistry.registerEntityRenderingHandler(EntityOceaniaBoatNormal.class, new RenderOceaniaBoat());
		RenderingRegistry.registerEntityRenderingHandler(EntityOceaniaBoatWithChest.class, new RenderOceaniaBoatWithChest());
		RenderingRegistry.registerEntityRenderingHandler(EntitySubmarine.class, new RenderSubmarine());
	}
}
