package oceania.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraftforge.common.Configuration;
import oceania.ItemBlockWithDescription;
import oceania.Oceania;

public class Blocks
{
	public static final int					DEFAULT_ID_RANGE	= 375;
	
	public static BlockNeptuniteOre			blockNeptunite;
	public static BlockNeptuniumDepulsor	blockDepulsor;
	public static BlockDepulsorPlaceholder	blockPlaceholder;
	
	public static void initBlocks()
	{
		Configuration cfg = Oceania.CONFIG;
		
		/** Set up instances */
		blockNeptunite = new BlockNeptuniteOre(cfg.getBlock("oreNeptunite", DEFAULT_ID_RANGE).getInt(DEFAULT_ID_RANGE));
		blockDepulsor = new BlockNeptuniumDepulsor(cfg.getBlock("machineDepulsor", DEFAULT_ID_RANGE).getInt(DEFAULT_ID_RANGE));
		blockPlaceholder = new BlockDepulsorPlaceholder(cfg.getBlock("blockDepulsorPlaceholder", DEFAULT_ID_RANGE, "The block that is used in the depulsor's \"air bubble\"").getInt());
		
		/** Add to registry */
		GameRegistry.registerBlock(blockNeptunite, "blockNeptunite");
		GameRegistry.registerBlock(blockDepulsor, ItemBlockWithDescription.class, "blockDepulsor");
		GameRegistry.registerBlock(blockPlaceholder, "blockDepulsorPlaceholder");
		
		/** Add language */
		LanguageRegistry.addName(blockNeptunite, "Neptunite Ore");
		LanguageRegistry.addName(blockDepulsor, "Neptunium Depulsor");
	}
}
