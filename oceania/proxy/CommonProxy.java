package oceania.proxy;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
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
		
		/** Crafting Table recipes. */
		GameRegistry.addRecipe(new ItemStack(Items.itemMulti, 2, 2), "X", "X", 'X', Item.ingotIron);
		
		/** Furnace recipes. */
		FurnaceRecipes.smelting().addSmelting(Items.itemMulti.itemID, 0, new ItemStack(Items.itemMulti, 1, 1), 0.1F);
		
		GameRegistry.registerWorldGenerator(new WorldGenAtlantite(Oceania.CONFIG.get("oreConfig", "atlantiteVeinSize", 6).getInt(), Oceania.CONFIG.get("oreConfig", "atlantiteVeinsPerChunk", 2).getInt()));  
	}
}
