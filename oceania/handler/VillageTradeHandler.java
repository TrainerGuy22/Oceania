package oceania.handler;

import java.util.Random;

import oceania.blocks.Blocks;
import oceania.items.Items;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;

public class VillageTradeHandler implements IVillageTradeHandler
{

	@Override
	public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList list, Random random) 
	{
		for(int count = 0; count < 5; count++)
		{
			if(random.nextInt(7) == 2)
			{
				switch(count)
				{
				case 0:
					list.addToListWithCheck(new MerchantRecipe(new ItemStack(Item.emerald, 5), new ItemStack(Items.itemAtlantiteTrident, 1)));
					break;
				case 1:
					list.addToListWithCheck(new MerchantRecipe(new ItemStack(Item.emerald, 3), new ItemStack(Blocks.blockLimestone, 16, 0)));
					break;
				case 2:
					list.addToListWithCheck(new MerchantRecipe(new ItemStack(Item.emerald, 6), new ItemStack(Items.itemMulti, 5, 0)));
					break;
				case 3:
					list.addToListWithCheck(new MerchantRecipe(new ItemStack(Item.emerald, 2), new ItemStack(Items.itemMulti, 8, 2)));
					break;
				case 4:
					list.addToListWithCheck(new MerchantRecipe(new ItemStack(Item.emerald, 7), new ItemStack(Items.itemDivingHelmet, 1)));
					break;
				}
			}
		}
	}

}
