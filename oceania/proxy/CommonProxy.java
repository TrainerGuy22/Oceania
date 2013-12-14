package oceania.proxy;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import oceania.CreativeTabOceania;
import oceania.Oceania;
import oceania.blocks.Blocks;
import oceania.blocks.tile.TileEntityAtlantiumDepulsor;
import oceania.entity.Entities;
import oceania.gen.WorldGenAtlantite;
import oceania.items.ItemMulti.ItemMultiType;
import oceania.items.Items;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
	public void init()
	{
		Oceania.CREATIVE_TAB = new CreativeTabOceania();
		Blocks.initBlocks();
		Items.initItems();
		Entities.initEntities();
		initEntities();
		initRecipes();
		Oceania.CREATIVE_TAB.setIconStack(new ItemStack(Items.itemMulti, 1, 0));
		
		GameRegistry.registerWorldGenerator(new WorldGenAtlantite(Oceania.CONFIG.get("oreConfig", "atlantiteVeinSize", 6).getInt(), Oceania.CONFIG.get("oreConfig", "atlantiteVeinsPerChunk", 2).getInt()));
	}
	
	public void initEntities()
	{
		GameRegistry.registerTileEntity(TileEntityAtlantiumDepulsor.class, Oceania.MOD_ID + "AtlantiumDepulsor");
	}
	
	public void initRecipes()
	{
		/** Crafting Table recipes */
		GameRegistry.addRecipe(new ItemStack(Items.itemMulti, 2, ItemMultiType.SCREW.ordinal()), "X", "X", 'X', Item.ingotIron);
		GameRegistry.addRecipe(new ItemStack(Items.itemDivingHelmet, 1), "AAA", "AGA", "SAS", 'S', new ItemStack(Items.itemMulti, 1, ItemMultiType.SCREW.ordinal()), 'A', new ItemStack(Items.itemMulti, 1, ItemMultiType.ATLANTIUM.ordinal()), 'G', Block.thinGlass);
		GameRegistry.addRecipe(new ItemStack(Items.itemAtlantiteTrident, 1), "I I", "IUI", " U ", 'I', new ItemStack(Items.itemMulti, 1, ItemMultiType.ATLANTITE.ordinal()), 'U', new ItemStack(Items.itemMulti, 1, ItemMultiType.ATLANTIUM.ordinal()));
					
		List recipies = CraftingManager.getInstance().getRecipeList();
		for(int count = 0; count < recipies.size(); count++) {
			IRecipe recipe = (IRecipe) recipies.get(count);
			if(recipe.getRecipeOutput() != null && recipe.getRecipeOutput().itemID == Item.boat.itemID) {
				recipies.remove(count);
			}
		}
		
		/** Furnace recipes */
		FurnaceRecipes.smelting().addSmelting(Items.itemMulti.itemID, 0, new ItemStack(Items.itemMulti, 1, ItemMultiType.ATLANTIUM.ordinal()), 0.1F);
	}
}
