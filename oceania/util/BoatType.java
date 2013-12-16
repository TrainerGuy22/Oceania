package oceania.util;

import oceania.items.Items;
import oceania.items.ItemMulti.ItemMultiType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public enum BoatType 
{
	WOOD("minecraft", "boat", "Wooden Boat", new ResourceLocation("textures/entity/boat.png"), new ItemStack(Item.stick), 20),
	IRON("oceania", "ironBoat", "Iron Boat", new ResourceLocation("oceania", "textures/models/ironBoat.png"), new ItemStack(Item.ingotIron), 75),
	ATLANTIUM("oceania", "atlantiumBoat", "Atlantium Boat", new ResourceLocation("oceania", "textures/models/atlantiumBoat.png"), new ItemStack(Items.itemMulti, 1, ItemMultiType.ATLANTIUM.ordinal()), 450),
	SUBMARINE("oceania", "submarine", "Submarine", new ResourceLocation("oceania", "texture/models/submarine.png"), new ItemStack(Items.itemMulti, 1, ItemMultiType.ATLANTIUM.ordinal()), 950);
	
	public String namespace, unloc, loc;
	public int strength;
	public ResourceLocation worldTexture;
	public ItemStack resourceItem;
	private BoatType(String namespace, String unlocName, String locName, ResourceLocation worldTexture, ItemStack item, int strength)
	{
		this.namespace = namespace;
		this.unloc = unlocName;
		this.loc = locName;
		this.worldTexture = worldTexture;
		this.resourceItem = item;
		this.strength = strength;
	}
	
}
