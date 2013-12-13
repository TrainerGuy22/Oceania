package oceania;

import java.util.HashMap;
import java.util.List;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockWithDescription extends ItemBlock
{
	private static HashMap<Integer, String> descriptions = new HashMap<Integer, String>();
	
	public static void registerDescription(Block block, String description)
	{
		descriptions.put(block.blockID, description);
	}

	public ItemBlockWithDescription(int itemID)
	{
		super(itemID);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List descriptionList, boolean noClueWhatThisEvenDoes)
	{
		if (descriptions.containsKey((Integer) itemStack.itemID))
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
			{
				String[] lines = descriptions.get((Integer) itemStack.itemID).split("\n");
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
