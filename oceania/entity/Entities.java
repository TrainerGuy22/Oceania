package oceania.entity;

import net.minecraft.util.ResourceLocation;
import oceania.Oceania;
import oceania.handler.VillageTradeHandler;
import oceania.util.OUtil;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;

public class Entities 
{
		
	public static int VILLAGER_OCEANIA_ID;
	
	public static void initEntities()
	{
		EntityRegistry.registerModEntity(EntityOceaniaBoatNormal.class, "OceaniaBoat", 0, Oceania.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntityOceaniaBoatWithChest.class, "OceaniaBoatWithChest", 1, Oceania.INSTANCE, 80, 3, true);
		EntityRegistry.registerModEntity(EntitySubmarine.class, "Submarine", 2, Oceania.INSTANCE, 80, 3, true);
	
		VILLAGER_OCEANIA_ID = VillagerRegistry.getRegisteredVillagers().size();
		VillagerRegistry.instance().registerVillagerId(VILLAGER_OCEANIA_ID);
		// TODO: Post ModJam - Have a custom skin for the villager.
		if (OUtil.getSide() == Side.CLIENT)
			VillagerRegistry.instance().registerVillagerSkin(VILLAGER_OCEANIA_ID, new ResourceLocation("textures/entity/villager/smith.png"));
		VillagerRegistry.instance().registerVillageTradeHandler(VILLAGER_OCEANIA_ID, new VillageTradeHandler());
	}
	
}
