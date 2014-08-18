package oceania;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabOceania extends CreativeTabs {
	private ItemStack stack;

	public CreativeTabOceania() {
		super("tabOceania");
		this.stack = new ItemStack(Blocks.stone, 1);
	}
	
	public void setIconStack(ItemStack iconStack) {
		this.stack = iconStack;
	}
	
	@Override
	public ItemStack getIconItemStack() {
		return this.stack;
	}

	@Override
	public Item getTabIconItem() {
		return this.stack.getItem();
	}
	
}
