package oceania.items;

import java.util.HashMap;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import oceania.Oceania;
import oceania.util.IconRegistry;
import oceania.util.OUtil;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMulti extends Item 
{
	public enum ItemMultiType
	{
		ATLANTITE("rawAtlantium", "Raw Atlantite"),
		ATLANTIUM("atlantium", "Atlantium"),
		SCREW("screw", "Screw");
		
		private String _unloc, _loc;
		private ItemMultiType(String unlocName, String locName)
		{
			this._unloc = unlocName;
			this._loc = locName;
		}
		
		public String getUnlocalizedName()
		{
			return this._unloc;
		}
		
		public String getLocalizedName()
		{
			return this._loc;
		}
	}
	
	private static HashMap<Integer, String> descriptions = new HashMap<Integer, String>();
	
	static
	{
		descriptions.put(0, "Some sort of naturally occuring\nalloy...");
		descriptions.put(1, "Refined Atlantite. Much harder,\nbut much duller!");
		descriptions.put(2, "Holds things together. Hopefully.");
	}
	
	public ItemMulti(int par1) 
	{
		super(par1);
		setCreativeTab(Oceania.CREATIVE_TAB);
		this.hasSubtypes = true;
		for(ItemMultiType multiType : ItemMultiType.values()) 
		{
			LanguageRegistry.instance().addStringLocalization("item.sub." + multiType.getUnlocalizedName() + ".name", multiType.getLocalizedName());
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public String getUnlocalizedName(ItemStack stack) 
	{
		return "item.sub." + ItemMultiType.values()[stack.getItemDamage()].getUnlocalizedName();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry)
	{
		for(int index = 0; index < ItemMultiType.values().length; index++) 
		{
			IconRegistry.setIcon(ItemMultiType.values()[index].getUnlocalizedName(), registry.registerIcon(Oceania.MOD_ID + ":" + ItemMultiType.values()[index].getUnlocalizedName()));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int meta)
	{
		return IconRegistry.getIcon(ItemMultiType.values()[meta].getUnlocalizedName());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(int ID, CreativeTabs tabs, List list) 
	{
		for(int index = 0; index < ItemMultiType.values().length; index++) 
		{
			list.add(new ItemStack(ID, 1, index));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List descriptionList, boolean noClueWhatThisEvenDoes)
	{
		if (descriptions.containsKey((Integer) itemStack.getItemDamage()))
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
			{
				String[] lines = descriptions.get((Integer) itemStack.getItemDamage()).split("\n");
				for (String line : lines)
				{
					descriptionList.add(line);
				}
			}
			else
			{
				descriptionList.add(OUtil.colorString("Hold &&9SHIFT &&7for more information"));
			}
		}
	}


}
