package oceania.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import oceania.OUtil;
import oceania.Oceania;
import oceania.util.BoatTypes;
import oceania.util.IconRegistry;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemSubmarine extends Item 
{
	
	public ItemSubmarine(int itemID) 
	{
		super(itemID);
		setCreativeTab(Oceania.CREATIVE_TAB);
		this.setUnlocalizedName("itemSubmarine");
	}

	// TODO: Figure out way to spawn these things that dosen't involve copypasta. (Submarine)
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
    	return stack;
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry)
	{
		this.itemIcon = registry.registerIcon(Oceania.MOD_ID + ":submarine");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List descriptionList, boolean noClueWhatThisEvenDoe)
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			descriptionList.add("A Submarine, the best");
			descriptionList.add("in ocean exploration!");
		}
		else
		{
			descriptionList.add(OUtil.colorString("Hold &&9SHIFT &&7for more information"));
		}
	}

}
