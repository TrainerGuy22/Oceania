package oceania.items;

import java.util.List;

import oceania.OUtil;
import oceania.Oceania;
import oceania.items.ItemMulti.ItemMultiType;
import oceania.util.BoatTypes;
import oceania.util.IconRegistry;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemOceaniaBoat extends ItemBoat 
{

	public ItemOceaniaBoat(int id) 
	{
		super(id);
		for(BoatTypes boat : BoatTypes.values()) 
		{
			LanguageRegistry.instance().addStringLocalization("item." + boat._unloc + ".name", boat._loc);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public String getUnlocalizedName(ItemStack stack) 
	{
		return "item." + BoatTypes.values()[stack.getItemDamage()]._unloc;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry)
	{
		for(int index = 0; index < BoatTypes.values().length; index++) 
		{
			IconRegistry.setIcon(BoatTypes.values()[index]._unloc, registry.registerIcon(BoatTypes.values()[index].namespace + ":" + BoatTypes.values()[index]._unloc));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int meta)
	{
		return IconRegistry.getIcon(BoatTypes.values()[meta]._unloc);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(int ID, CreativeTabs tabs, List list) 
	{
		for(int index = 0; index < BoatTypes.values().length; index++) 
		{
			list.add(new ItemStack(ID, 1, index));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List descriptionList, boolean noClueWhatThisEvenDoes)
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			switch(itemStack.getItemDamage())
			{
			case 0:
				descriptionList.add("A boat made of sturdy planks.");
				descriptionList.add("Doubt it will last long.");
				break;
			case 1:
				descriptionList.add("A boat made of Iron, should");
				descriptionList.add("last a while.");
				break;
			case 2: 
				descriptionList.add("Made of purely Atlantium,");
				descriptionList.add("almost indestructable.");
			}
		}
		else
		{
			descriptionList.add(OUtil.colorString("Hold &&9SHIFT &&7for more information"));
		}
	}

}