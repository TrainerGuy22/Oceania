package oceania.common;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import oceania.CreativeTabOceania;
import oceania.Oceania;
import oceania.blocks.BlockLimestone;
import oceania.blocks.Blocks;
import oceania.blocks.tile.TileEntityAtlantiumDepulsor;
import oceania.blocks.tile.TileEntityChestSpawner;
import oceania.entity.Entities;
import oceania.gen.WorldGenAtlantite;
import oceania.gen.WorldGenLimestone;
import oceania.gen.WorldGenUnderwaterVillage;
import oceania.items.ItemMulti.ItemMultiType;
import oceania.items.Items;
import oceania.util.BoatType;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	public void init() {
		Oceania.TAB = new CreativeTabOceania();
		Entities.initEntities();
		initTileEntities();
		initRecipes();
		
		Oceania.TAB.setIconStack(new ItemStack(Items.itemMulti, 1, 0));
		
		GameRegistry.registerWorldGenerator(new WorldGenAtlantite(Oceania.CONFIG.get("oreConfig", "atlantiteVeinSize", 3).getInt(3), Oceania.CONFIG.get("oreConfig", "atlantiteVeinsPerChunk", 1).getInt(1)));
		GameRegistry.registerWorldGenerator(new WorldGenLimestone(Oceania.CONFIG.get("oreConfig", "limestoneVeinSize", 16).getInt(16), Oceania.CONFIG.get("oreConfig", "limestoneVeinsPerChunk", 2).getInt(2)));
		GameRegistry.registerWorldGenerator(new WorldGenUnderwaterVillage());
	}
	
	public void initTileEntities() {
		GameRegistry.registerTileEntity(TileEntityAtlantiumDepulsor.class, Oceania.MOD_ID + "AtlantiumDepulsor");
		GameRegistry.registerTileEntity(TileEntityChestSpawner.class, Oceania.MOD_ID + "ChestSpawner");
	}
	
	public void initRecipes()
	{
		/** Crafting Table recipes */
		GameRegistry.addRecipe(new ItemStack(Blocks.blockLimestone, 4, BlockLimestone.LimestoneTypes.LIMESTONE_BRICK.ordinal()), "XX", "XX", 'X', new ItemStack(Blocks.blockLimestone, 1, BlockLimestone.LimestoneTypes.LIMESTONE.ordinal()));
		GameRegistry.addRecipe(new ItemStack(Blocks.blockLimestonePillar, 6), "XX", "XX", "XX", 'X', new ItemStack(Blocks.blockLimestone, 1, BlockLimestone.LimestoneTypes.LIMESTONE.ordinal()));
		
		GameRegistry.addRecipe(new ItemStack(Items.itemMulti, 2, ItemMultiType.SCREW.ordinal()), "X", "X", 'X', Item.ingotIron);
		GameRegistry.addRecipe(new ItemStack(Items.itemDivingHelmet, 1), "AAA", "AGA", "SAS", 'S', new ItemStack(Items.itemMulti, 1, ItemMultiType.SCREW.ordinal()), 'A', new ItemStack(Items.itemMulti, 1, ItemMultiType.ATLANTIUM.ordinal()), 'G', Block.thinGlass);
		GameRegistry.addRecipe(new ItemStack(Items.itemAtlantiteTrident, 1), "I I", "IUI", " U ", 'I', new ItemStack(Items.itemMulti, 1, ItemMultiType.ATLANTITE.ordinal()), 'U', new ItemStack(Items.itemMulti, 1, ItemMultiType.ATLANTIUM.ordinal()));
		
		List recipies = CraftingManager.getInstance().getRecipeList();
		for(int count = 0; count < recipies.size(); count++) 
		{
			IRecipe recipe = (IRecipe) recipies.get(count);
			if(recipe.getRecipeOutput() != null && recipe.getRecipeOutput().itemID == Item.boat.itemID) {
				recipies.remove(count);
			}
		}
		
		for(int count = 0; count > BoatType.values().length; count++)
		{
			if(count == 0)
				GameRegistry.addRecipe(new ItemStack(Items.itemBoat, 1, count), "X X", "XXX", 'X', new ItemStack(BoatType.values()[count].resourceItem.getItem(), 1));
			else
				GameRegistry.addRecipe(new ItemStack(Items.itemBoat, 1, count), "X X", "XXX", 'X', new ItemStack(BoatType.values()[count].resourceItem.getItem(), 1, BoatType.values()[count].resourceItem.getItemDamage()));
		}
		
		/** Furnace recipes */
		FurnaceRecipes.smelting().addSmelting(Items.itemMulti.itemID, 0, new ItemStack(Items.itemMulti, 1, ItemMultiType.ATLANTIUM.ordinal()), 0.1F);
		GameRegistry.addSmelting(((Block) Blocks.blockAtlantite).blockID, new ItemStack(Items.itemMulti, 1, 0), 1.0F);
	}
}
