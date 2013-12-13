package oceania.proxy;

import net.minecraft.item.ItemStack;
import oceania.CreativeTabOceania;
import oceania.Oceania;
import oceania.blocks.Blocks;
import oceania.gen.WorldGenAtlantite;
import oceania.items.Items;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
	public void init()
	{
		Oceania.CREATIVE_TAB = new CreativeTabOceania();
		Blocks.initBlocks();
		Items.initItems();
		Oceania.CREATIVE_TAB.setIconStack(new ItemStack(Blocks.blockAtlantite));
		
		GameRegistry.registerWorldGenerator(new WorldGenAtlantite(Oceania.CONFIG.get("oreConfig", "atlantiteVeinSize", 6).getInt(), Oceania.CONFIG.get("oreConfig", "atlantiteVeinsPerChunk", 2).getInt()));  
	}
}
