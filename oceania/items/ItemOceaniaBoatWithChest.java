package oceania.items;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import oceania.entity.EntityOceaniaBoat;
import oceania.util.BoatTypes;

public class ItemOceaniaBoatWithChest extends ItemOceaniaBoat 
{

	public ItemOceaniaBoatWithChest(int id) 
	{
		super(id);
	}
	
	@Override
	public void initLangNames()
	{
		for(BoatTypes boat : BoatTypes.values()) 
		{
			LanguageRegistry.instance().addStringLocalization("item." + boat._unloc + ".name", boat._loc + "with Chest");
		}
	}
	
	@Override
	public Entity createBoat(World world, BoatTypes boatType, double x, double y, double z)
	{
		return new EntityOceaniaBoat(world, boatType, x, y, z);
	}

}
