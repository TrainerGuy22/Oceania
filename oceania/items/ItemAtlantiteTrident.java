package oceania.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import oceania.Oceania;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemAtlantiteTrident extends ItemSword 
{

	public ItemAtlantiteTrident(int par1) 
	{
		super(par1, Items.TOOL_MATERIAL_ATLANTIUM);
		setCreativeTab(Oceania.CREATIVE_TAB);
		setUnlocalizedName("itemAtlantiteTrident");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List descriptionList, boolean noClueWhatThisEvenDoe)
	{
		descriptionList.add("Nothing can beat the");
		descriptionList.add("sharpness of raw Atlantite.");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry)
	{
		this.itemIcon = registry.registerIcon(Oceania.MOD_ID + ":atlantiteTrident");
	}

}
