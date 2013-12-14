package oceania.util;

import net.minecraft.util.ResourceLocation;

public enum BoatTypes 
{
	WOOD("minecraft", "boat", "Wooden Boat", new ResourceLocation("textures/entity/boat.png"), 5),
	IRON("oceania", "ironBoat", "Iron Boat", new ResourceLocation("oceania", "textures/entity/ironBoat.png"), 25),
	ATLANTIUM("oceania", "atlantiumBoat", "Atlantium Boat", new ResourceLocation("oceania", "textures/entity/atlantiumBoat.png"), 150);
	
	public String namespace, _unloc, _loc;
	public int strength;
	public ResourceLocation worldTexture;
	private BoatTypes(String namespace, String unlocName, String locName, ResourceLocation worldTexture, int strength)
	{
		this.namespace = namespace;
		this._unloc = unlocName;
		this._loc = locName;
		this.worldTexture = worldTexture;
		this.strength = strength;
	}
	
}
