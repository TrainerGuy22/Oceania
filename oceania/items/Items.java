package oceania.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import oceania.Oceania;

public class Items
{
	public static final EnumToolMaterial TOOL_MATERIAL_ATLANTIUM = EnumHelper.addToolMaterial("atlantium", 2, 780, 9.0F, 4.0F, 10);
	
	public static final int DEFAULT_ID_RANGE = 3750;
	
	public static ItemDivingHelmet itemDivingHelmet;
	public static ItemMulti itemMulti;
	
	public static void initItems()
	{
		Configuration cfg = Oceania.CONFIG;
		
		/** Set up instances */
		itemDivingHelmet = new ItemDivingHelmet(cfg.getItem("itemDivingHelmet", DEFAULT_ID_RANGE).getInt(DEFAULT_ID_RANGE));
		itemMulti = new ItemMulti(cfg.getItem("itemMulti", DEFAULT_ID_RANGE + 1).getInt(DEFAULT_ID_RANGE + 1));
		
		/** Add to registry */
		GameRegistry.registerItem(itemDivingHelmet, Oceania.MOD_ID + "itemDivingHelmet");
		GameRegistry.registerItem(itemMulti, Oceania.MOD_ID + "itemMulti");
		
		TOOL_MATERIAL_ATLANTIUM.customCraftingMaterial = Item.ingotGold;
		
		/** Ore Dictionary support */
		OreDictionary.registerOre("oceaniaCraftingItems", itemMulti);
		
		/** Add language */
		LanguageRegistry.addName(itemDivingHelmet, "Atlantium Helm");
	}
}
