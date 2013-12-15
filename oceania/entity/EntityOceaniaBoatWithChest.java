package oceania.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import oceania.items.Items;
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
		ItemStack returnStack;
		if(this.chestItems[slot] != null)
		{
			if(this.chestItems[slot].stackSize < amount)
			{
				returnStack = this.chestItems[slot];
				this.setInventorySlotContents(slot, null);
				return returnStack;
			} else
			{
				returnStack = this.chestItems[slot].splitStack(amount);
				
				if(this.chestItems[slot].stackSize == 0)
					this.setInventorySlotContents(slot, null);
				return returnStack;
			}
		}
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
		int strength = this.getDataWatcher().getWatchableObjectInt(22);
		for(int index = 0; index < BoatTypes.values().length; index++) {
			if(((Integer) BoatTypes.values()[index].strength).equals(strength)) {
				return BoatTypes.values()[index]._loc + " with Chest";
			}
		}
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
    public void writeEntityToNBT(NBTTagCompound tag)
    {
		super.writeEntityToNBT(tag);
		NBTTagCompound chestItemsTag = new NBTTagCompound();
		for(int count = 0; count < this.chestItems.length; count++)
		{
			if(this.chestItems[count] != null)
			{
				NBTTagCompound itemTag = new NBTTagCompound();
				this.chestItems[count].writeToNBT(itemTag);
				chestItemsTag.setCompoundTag("item" + String.valueOf(count), itemTag);
			}
		}
		tag.setCompoundTag("chestItems", chestItemsTag);
    }
    
	@Override
    public void readEntityFromNBT(NBTTagCompound tag)
    {
		super.readEntityFromNBT(tag);
		NBTTagCompound chestItemsTag = tag.getCompoundTag("chestItems");
		for(int count = 0; count < this.chestItems.length; count++)
		{
			if(chestItemsTag.hasKey("item" + String.valueOf(count)))
			{
				this.setInventorySlotContents(count, ItemStack.loadItemStackFromNBT(chestItemsTag.getCompoundTag("item" + String.valueOf(count))));
			}
		}
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
