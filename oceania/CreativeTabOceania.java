package oceania;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabOceania extends CreativeTabs
{
	private ItemStack _iconStack;

	public CreativeTabOceania()
	{
		super("tabOceania");
		this._iconStack = new ItemStack(Block.stone, 1);
	}
	
	public void setIconStack(ItemStack iconStack)
	{
		this._iconStack = iconStack;
	}
	
	@Override
	public ItemStack getIconItemStack()
	{
		return this._iconStack;
	}
	
	@Override
	public String getTranslatedTabLabel()
	{
		return "Oceania";
	}
	
}
