package oceania.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import oceania.CreativeTabOceania;
import oceania.Oceania;
import oceania.blocks.Blocks;
import oceania.gen.WorldGenNeptunite;

public class CommonProxy
{
	public void init()
	{
		Oceania.CREATIVE_TAB = new CreativeTabOceania();
		Blocks.initBlocks();
		Oceania.CREATIVE_TAB.setIconStack(new ItemStack(Blocks.blockNeptunite));
		
		GameRegistry.registerWorldGenerator(new WorldGenNeptunite(Oceania.CONFIG.get("oreConfig", "neptuniteVeinSize", 6).getInt(), Oceania.CONFIG.get("oreConfig", "neptuniteVeinsPerChunk", 2).getInt()));  
	}
}
