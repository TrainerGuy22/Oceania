package oceania.items;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import oceania.entity.EntityOceaniaBoat;
import oceania.entity.EntityOceaniaBoatWithChest;
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
	@SideOnly(Side.CLIENT)
    public String getUnlocalizedName(ItemStack stack) 
	{
		return "item." + BoatTypes.values()[stack.getItemDamage()]._unloc + ".chest";
	}
	
	@Override
	public Entity createBoat(World world, BoatTypes boatType, double x, double y, double z)
	{
		return new EntityOceaniaBoatWithChest(world, boatType, x, y, z);
	}

}
