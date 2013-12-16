package oceania.entity.render;

import java.util.HashMap;

import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.techne.TechneModel;
import oceania.entity.EntityOceaniaBoat;
import oceania.entity.EntityOceaniaBoatNormal;
import oceania.util.BoatTypes;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderOceaniaBoat extends RenderBoat
{
	private HashMap<String, ResourceLocation>	texCache;
	
	@Override
	public void renderBoat(EntityBoat boat, double x, double y, double z, float yaw, float partialTicks)
	{
		super.renderBoat(boat, x, y, z, yaw, partialTicks);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		EntityOceaniaBoatNormal boat = (EntityOceaniaBoatNormal) entity;
		try
		{
			//System.out.println("textures/entity/" + BoatTypes.values()[boat.getDataWatcher().getWatchableObjectByte(EntityOceaniaBoat.BYTE_BOAT_TYPE)]._unloc + "Chest.png");
			String boatName = BoatTypes.values()[boat.getDataWatcher().getWatchableObjectByte(EntityOceaniaBoat.INDEX_BOAT_TYPE)]._unloc;
			if (!this.texCache.containsKey(boatName))
				this.texCache.put(boatName, new ResourceLocation("oceania", "textures/models/" + boatName + ".png"));
			return this.texCache.get(boatName);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return new ResourceLocation("oceania", "textures/entity/ironBoat.png");
	}
	
}
