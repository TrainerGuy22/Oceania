package oceania.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import oceania.util.BoatTypes;

public class EntityOceaniaBoatWithChest extends EntityOceaniaBoat implements IInventory {
	
    private ItemStack[] chestItems = new ItemStack[36];
	
	public EntityOceaniaBoatWithChest(World world) 
	{
		super(world);
	}

	public EntityOceaniaBoatWithChest(World world, double x, double y, double z) 
	{
		super(world, x, y, z);
	}
	
	public EntityOceaniaBoatWithChest(World world, BoatTypes type, double x, double y, double z) 
	{
		super(world, x, y, z);
		this.setBoatType(type);
	}

	@Override
	public int getSizeInventory() 
	{
		return chestItems.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		return this.chestItems[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) 
	{
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) 
	{
		return this.getStackInSlot(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) 
	{
		this.chestItems[slot] = stack;
		
		if(stack != null && stack.stackSize > 64)
			stack.stackSize = 64;
	}

	@Override
	public String getInvName() 
	{
		return "";
	}

	@Override
	public boolean isInvNameLocalized() 
	{
		return true;
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return !this.isDead;
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) 
	{
		return true;
	}
	
	@Override
	public void onInventoryChanged() 
	{}

	@Override
	public void openChest() 
	{}

	@Override
	public void closeChest() 
	{}

}
