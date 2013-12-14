package oceania.items;

import java.util.List;

import oceania.Oceania;
import oceania.util.IconRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemMulti extends Item 
{

	String[] ITEMS = new String[]{"rawAtlantium", "atlantium", "screw"};
	String[] LANG_NAMES = new String[]{"Raw Atlantium", "Atlantium", "Screw"};
	
	public ItemMulti(int par1) 
	{
		super(par1);
		setCreativeTab(Oceania.CREATIVE_TAB);
		this.hasSubtypes = true;
		for(int index = 0; index < ITEMS.length; index++) 
		{
			LanguageRegistry.instance().addStringLocalization("item.sub." + ITEMS[index] + ".name", LANG_NAMES[index]);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public String getUnlocalizedName(ItemStack stack) 
	{
		return "item.sub." + ITEMS[stack.getItemDamage()];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry)
	{
		for(int index = 0; index < ITEMS.length; index++) 
		{
			IconRegistry.setIcon(ITEMS[index], registry.registerIcon(Oceania.MOD_ID + ":" + ITEMS[index]));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int meta)
	{
		return IconRegistry.getIcon(ITEMS[meta]);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(int ID, CreativeTabs tabs, List list) 
	{
		for(int index = 0; index < ITEMS.length; index++) 
		{
			list.add(new ItemStack(ID, 1, index));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List descriptionList, boolean noClueWhatThisEvenDoe)
	{
		if(itemStack.getItemDamage() <= 1) {
			descriptionList.add("Some kind of naturally");
			descriptionList.add("occuring alloy...");
		} else {
			descriptionList.add("Holds together machines.");
		}
	}


}
