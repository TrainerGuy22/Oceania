package oceania.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import oceania.blocks.BlockLimestone;
import oceania.items.ItemMulti.ItemMultiType;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockLimestone extends ItemBlockWithDescription 
{
	
	public ItemBlockLimestone(int itemID)
	{
		super(itemID);
		this.hasSubtypes = true;
		for(BlockLimestone.LimestoneTypes multiType : BlockLimestone.LimestoneTypes.values()) 
		{
			LanguageRegistry.instance().addStringLocalization("tile." + multiType.getUnlocalizedName() + ".name", multiType.getLocalizedName());
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public String getUnlocalizedName(ItemStack stack) 
	{
		return "tile." + BlockLimestone.LimestoneTypes.values()[stack.getItemDamage()].getUnlocalizedName();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(int ID, CreativeTabs tabs, List list) 
	{
		for(int index = 0; index < BlockLimestone.LimestoneTypes.values().length; index++) 
		{
			list.add(new ItemStack(ID, 1, index));
		}
	}

}
