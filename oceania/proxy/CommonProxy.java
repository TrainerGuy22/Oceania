package oceania.proxy;

import net.minecraft.item.ItemStack;
import oceania.CreativeTabOceania;
import oceania.Oceania;
import oceania.blocks.Blocks;
import oceania.gen.WorldGenNeptunite;
import oceania.items.Items;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
	public void init()
	{
		Oceania.CREATIVE_TAB = new CreativeTabOceania();
		Blocks.initBlocks();
		Items.initItems();
		Oceania.CREATIVE_TAB.setIconStack(new ItemStack(Blocks.blockNeptunite));
		
		GameRegistry.registerWorldGenerator(new WorldGenNeptunite(Oceania.CONFIG.get("oreConfig", "neptuniteVeinSize", 6).getInt(), Oceania.CONFIG.get("oreConfig", "neptuniteVeinsPerChunk", 2).getInt()));  
	}
}
