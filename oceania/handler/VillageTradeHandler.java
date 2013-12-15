package oceania.handler;

import java.util.Random;

import oceania.blocks.Blocks;
import oceania.items.Items;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.village.MerchantRecipeList;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;

public class VillageTradeHandler implements IVillageTradeHandler
{

	@Override
	public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList list, Random random) 
	{
		VillagerRegistry.instance().addEmeraldSellRecipe(villager, list, random, Items.itemMulti, 0.6f, 2, 4);
		VillagerRegistry.instance().addEmeraldBuyRecipe(villager, list, random, Items.itemMulti, 0.2f, 3, 4);
		VillagerRegistry.instance().addEmeraldSellRecipe(villager, list, random, Items.itemAtlantiteTrident, 0.2f, 1, 1);
	}

}
