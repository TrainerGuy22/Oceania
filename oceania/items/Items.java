package oceania.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraftforge.common.Configuration;
import oceania.Oceania;

public class Items
{
	public static final int DEFAULT_ID_RANGE = 3750;
	
	public static ItemDivingHelmet itemDivingHelmet;
	
	public static void initItems()
	{
		Configuration cfg = Oceania.CONFIG;
		
		/** Set up instances */
		itemDivingHelmet = new ItemDivingHelmet(cfg.getItem("oreNeptunite", DEFAULT_ID_RANGE).getInt(DEFAULT_ID_RANGE));
		
		/** Add to registry */
		GameRegistry.registerItem(itemDivingHelmet, "itemDivingHelmet");
		
		/** Add language */
		LanguageRegistry.addName(itemDivingHelmet, "Diving Helmet");
	}
}
