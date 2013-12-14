package oceania.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import oceania.Oceania;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemAtlantiumTrident extends ItemSword 
{

	public ItemAtlantiumTrident(int par1) 
	{
		super(par1, Items.TOOL_MATERIAL_ATLANTIUM);
		setCreativeTab(Oceania.CREATIVE_TAB);
		setUnlocalizedName("itemAtlantiumTrident");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List descriptionList, boolean noClueWhatThisEvenDoe)
	{
		descriptionList.add("Nothing can beat the");
		descriptionList.add("sharpness of raw Atlantite.");
	}

}
