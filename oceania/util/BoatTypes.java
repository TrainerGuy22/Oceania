package oceania.util;

import oceania.items.Items;
import oceania.items.ItemMulti.ItemMultiType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public enum BoatTypes 
{
	WOOD("minecraft", "boat", "Wooden Boat", new ResourceLocation("textures/entity/boat.png"), new ItemStack(Item.stick), 5),
	IRON("oceania", "ironBoat", "Iron Boat", new ResourceLocation("oceania", "textures/entity/ironBoat.png"), new ItemStack(Item.ingotIron), 25),
	ATLANTIUM("oceania", "atlantiumBoat", "Atlantium Boat", new ResourceLocation("oceania", "textures/entity/atlantiumBoat.png"), new ItemStack(Items.itemMulti, 1, ItemMultiType.ATLANTIUM.ordinal()), 150);
	
	public String namespace, _unloc, _loc;
	public int strength;
	public ResourceLocation worldTexture;
	public ItemStack resourceItem;
	private BoatTypes(String namespace, String unlocName, String locName, ResourceLocation worldTexture, ItemStack item, int strength)
	{
		this.namespace = namespace;
		this._unloc = unlocName;
		this._loc = locName;
		this.worldTexture = worldTexture;
		this.resourceItem = item;
		this.strength = strength;
	}
	
}
