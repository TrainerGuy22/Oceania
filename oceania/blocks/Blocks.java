package oceania.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraftforge.common.Configuration;
import oceania.Oceania;
import oceania.items.ItemBlockLimestone;
import oceania.items.ItemBlockWithDescription;

public class Blocks
{
	public static final int					DEFAULT_ID_RANGE	= 375;
	
	public static BlockAtlantiteOre		blockAtlantite;
	public static BlockAtlantiumDepulsor	blockDepulsor;
	public static BlockDepulsorPlaceholder	blockPlaceholder;
	public static BlockLimestone			blockLimestone;
	public static BlockLimestonePillar		blockLimestonePillar;
	public static BlockChestSpawner		blockChestSpawner;
	
	public static void initBlocks()
	{
		Configuration cfg = Oceania.CONFIG;
		
		/** Set up instances */
		blockAtlantite = new BlockAtlantiteOre(cfg.getBlock("oreAtlantite", DEFAULT_ID_RANGE).getInt(DEFAULT_ID_RANGE));
		blockDepulsor = new BlockAtlantiumDepulsor(cfg.getBlock("machineDepulsor", DEFAULT_ID_RANGE).getInt(DEFAULT_ID_RANGE));
		blockPlaceholder = new BlockDepulsorPlaceholder(cfg.getBlock("blockDepulsorPlaceholder", DEFAULT_ID_RANGE, "The block that is used in the depulsor's \"air bubble\"").getInt());
		blockLimestone = new BlockLimestone(cfg.getBlock("blockLimestone", DEFAULT_ID_RANGE + 1).getInt(DEFAULT_ID_RANGE + 1));
		blockLimestonePillar = new BlockLimestonePillar(cfg.getBlock("blockLimestonePillar", DEFAULT_ID_RANGE + 2).getInt(DEFAULT_ID_RANGE + 2));
		blockChestSpawner = new BlockChestSpawner(cfg.getBlock("blockChestSpawner", DEFAULT_ID_RANGE + 3).getInt(DEFAULT_ID_RANGE + 3));
		
		/** Add to registry */
		GameRegistry.registerBlock(blockAtlantite, ItemBlockWithDescription.class, "blockAtlantite");
		GameRegistry.registerBlock(blockDepulsor, ItemBlockWithDescription.class, "blockDepulsor");
		GameRegistry.registerBlock(blockLimestone, ItemBlockLimestone.class, "blockLimestone");
		GameRegistry.registerBlock(blockLimestonePillar, ItemBlockWithDescription.class, "blockLimestonePillar");
		GameRegistry.registerBlock(blockPlaceholder, "blockDepulsorPlaceholder");
		GameRegistry.registerBlock(blockChestSpawner, ItemBlockWithDescription.class, "blockChestSpawner");
		
		/** Add language */
		LanguageRegistry.addName(blockAtlantite, "Atlantite Ore");
		LanguageRegistry.addName(blockDepulsor, "Atlantium Depulsor");
		LanguageRegistry.addName(blockLimestonePillar, "Limestone Pillar");
		LanguageRegistry.addName(blockChestSpawner, "Chest Spawner");
	}
}
