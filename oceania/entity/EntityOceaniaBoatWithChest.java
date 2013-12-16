package oceania.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import oceania.items.Items;
import oceania.util.BoatType;

public class EntityOceaniaBoatWithChest extends EntityOceaniaBoat implements IInventory
{
	
	private ItemStack[]	chestItems	= new ItemStack[36];
	
	public EntityOceaniaBoatWithChest(World world)
	{
		super(world);
	}
	
	public EntityOceaniaBoatWithChest(World world, double x, double y, double z)
	{
		super(world, x, y, z);
	}
	
	public EntityOceaniaBoatWithChest(World world, BoatType type, double x, double y, double z)
	{
		super(world, x, y, z);
		this.setBoatType(type);
	}

	@Override
    public void updateRiderPosition()
    {
        if (this.riddenByEntity != null)
        {
            double xOffset = Math.cos((double)this.rotationYaw * Math.PI / 180.0D) * -0.4D;
            double yOffset = Math.sin((double)this.rotationYaw * Math.PI / 180.0D) * -0.4D;
            this.riddenByEntity.setPosition(this.posX + xOffset, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + yOffset);
        }
    }
	
	@Override
	public boolean interactFirst(EntityPlayer player)
	{
		if (this.riddenByEntity == null && player.isSneaking())
		{
			if (!player.worldObj.isRemote)
			{
				player.displayGUIChest(this);
			}
		}
		else
		{
			return super.interactFirst(player);
		}
		return true;
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
		if (this.chestItems[slot] != null)
		{
			if (this.chestItems[slot].stackSize < amount)
			{
				returnStack = this.chestItems[slot];
				this.setInventorySlotContents(slot, null);
				return returnStack;
			}
			else
			{
				returnStack = this.chestItems[slot].splitStack(amount);
				
				if (this.chestItems[slot].stackSize == 0)
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
		
		if (stack != null && stack.stackSize > 64)
			stack.stackSize = 64;
	}
	
	@Override
	public String getInvName()
	{
		return BoatType.values()[this.getDataWatcher().getWatchableObjectByte(EntityOceaniaBoat.INDEX_BOAT_TYPE)].loc + " with Chest";
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
	public boolean isUseableByPlayer(EntityPlayer player)
	{
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
		for (int count = 0; count < this.chestItems.length; count++)
		{
			if (this.chestItems[count] != null)
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
		for (int count = 0; count < this.chestItems.length; count++)
		{
			if (chestItemsTag.hasKey("item" + String.valueOf(count)))
			{
				this.setInventorySlotContents(count, ItemStack.loadItemStackFromNBT(chestItemsTag.getCompoundTag("item" + String.valueOf(count))));
			}
		}
	}
	
	@Override
	public void onInventoryChanged()
	{
	}
	
	@Override
	public void openChest()
	{
	}
	
	@Override
	public void closeChest()
	{
	}
	
}
