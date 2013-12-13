package oceania.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraftforge.common.Configuration;
import oceania.Oceania;
import oceania.items.ItemBlockWithDescription;

public class Blocks
{
	public static final int					DEFAULT_ID_RANGE	= 375;
	
	public static BlockAtlantiteOre			blockAtlantite;
	public static BlockAtlantiumDepulsor	blockDepulsor;
	public static BlockDepulsorPlaceholder	blockPlaceholder;
	
	public static void initBlocks()
	{
		Configuration cfg = Oceania.CONFIG;
		
		/** Set up instances */
		blockAtlantite = new BlockAtlantiteOre(cfg.getBlock("oreAtlantite", DEFAULT_ID_RANGE).getInt(DEFAULT_ID_RANGE));
		blockDepulsor = new BlockAtlantiumDepulsor(cfg.getBlock("machineDepulsor", DEFAULT_ID_RANGE).getInt(DEFAULT_ID_RANGE));
		blockPlaceholder = new BlockDepulsorPlaceholder(cfg.getBlock("blockDepulsorPlaceholder", DEFAULT_ID_RANGE, "The block that is used in the depulsor's \"air bubble\"").getInt());
		
		/** Add to registry */
		GameRegistry.registerBlock(blockAtlantite, "blockAtlantite");
		GameRegistry.registerBlock(blockDepulsor, ItemBlockWithDescription.class, "blockDepulsor");
		GameRegistry.registerBlock(blockPlaceholder, "blockDepulsorPlaceholder");
		
		/** Add language */
		LanguageRegistry.addName(blockAtlantite, "Atlantite Ore");
		LanguageRegistry.addName(blockDepulsor, "Atlantium Depulsor");
	}
}
