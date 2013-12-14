package oceania.items;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemMulti extends Item 
{

	String[] items = new String[]{"atlantium", "refinedAtlantium", "screw"};
	String[] langNames = new String[]{"Raw Atlantium", "Atlantium", "Screw"};
	
	public ItemMulti(int par1) 
	{
		super(par1);
		this.hasSubtypes = true;
		
		for(int index = 0; index < langNames.length; index++) 
		{
			LanguageRegistry.instance().addStringLocalization("item.sub." + items[index] + "name", langNames[index]);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry)
	{

}
