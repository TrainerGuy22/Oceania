package oceania.entity.render;

import oceania.entity.EntityOceaniaBoat;
import oceania.entity.EntityOceaniaBoatWithChest;
import oceania.entity.render.model.ModelOceaniaBoatWithChest;
import oceania.util.BoatTypes;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderOceaniaBoatWithChest extends RenderBoat
{
	
	public RenderOceaniaBoatWithChest()
	{
		super();
		this.modelBoat = new ModelOceaniaBoatWithChest();
	}

	@Override
	@SideOnly(Side.CLIENT)
    protected ResourceLocation getEntityTexture(Entity entity) 
	{
		EntityOceaniaBoat boat = (EntityOceaniaBoatWithChest) entity;
		try 
		{
			String location = boat.getDataWatcher().getWatchableObjectString(21);
			return new ResourceLocation(boat.getDataWatcher().getWatchableObjectString(20), location.substring(0, (location.length() - 4)) + "Chest" + location.substring(location.length() - 4));
		} catch(Exception e) 
		{}
		return BoatTypes.WOOD.worldTexture;
	}

}
