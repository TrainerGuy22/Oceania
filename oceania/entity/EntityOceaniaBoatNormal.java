package oceania.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import oceania.items.ItemMulti.ItemMultiType;
import oceania.items.Items;

public class EntityOceaniaBoatNormal extends EntityOceaniaBoat
{
	
	public EntityOceaniaBoatNormal(World world)
	{
		super(world);
	}
	
	public EntityOceaniaBoatNormal(World world, double x, double y, double z)
	{
		super(world, x, y, z);
	}
	
	@Override
	public float getBoatWidth()
	{
		return 1.5f;
	}
	
	@Override
	public float getBoatLength()
	{
		return 1.5f;
	}
	
	@Override
	public float getBoatHeight()
	{
		return 0.6f;
	}
	
	@Override
	public float getMaxSpeed()
	{
		return 0.35f + ((float) getBoatType().ordinal() * 0.05f);
	}
	
	@Override
	public double getMountedYOffset()
	{
		return 1.0f;
	}
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public void dropItemsOnDeath()
	{
		switch (getBoatType())
		{
			case ATLANTIUM:
				EntityItem item = this.dropItem(Items.itemMulti.itemID, 5);
				item.getEntityItem().setItemDamage(ItemMultiType.ATLANTIUM.ordinal());
				break;
			case IRON:
				this.dropItem(Item.ingotIron.itemID, 5);
				break;
			case WOOD:
				this.dropItem(Item.stick.itemID, 3);
				this.dropItem(Block.planks.blockID, 2);
				break;
		
		}
	}
	
}
