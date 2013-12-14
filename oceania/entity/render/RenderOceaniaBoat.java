package oceania.entity.render;

import oceania.entity.EntityOceaniaBoat;
import oceania.util.BoatTypes;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderOceaniaBoat extends RenderBoat
{
	
	@Override
	@SideOnly(Side.CLIENT)
    protected ResourceLocation getEntityTexture(Entity entity) 
	{
		EntityOceaniaBoat boat = (EntityOceaniaBoat) entity;
		try 
		{
			return new ResourceLocation(boat.getDataWatcher().getWatchableObjectString(20), boat.getDataWatcher().getWatchableObjectString(21));
		} catch(Exception e) 
		{}
		return BoatTypes.WOOD.worldTexture;
	}

}
