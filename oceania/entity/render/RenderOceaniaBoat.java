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
		if(boat.texture != null)
			return boat.texture;
		return BoatTypes.WOOD.worldTexture;
	}

}
