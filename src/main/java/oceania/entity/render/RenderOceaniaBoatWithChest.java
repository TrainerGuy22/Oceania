package oceania.entity.render;

import static org.lwjgl.opengl.GL11.*;

import java.util.HashMap;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import oceania.entity.EntityOceaniaBoat;
import oceania.entity.EntityOceaniaBoatWithChest;
import oceania.entity.render.model.ModelBoatWithChest;
import oceania.util.BoatType;
import oceania.util.OUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderOceaniaBoatWithChest extends Render
{
	private ModelBoatWithChest					model;
	private HashMap<String, ResourceLocation>	texCache;
	
	public RenderOceaniaBoatWithChest()
	{
		// this.model = (TechneModel) AdvancedModelLoader.loadModel("/assets/oceania/models/boatWithChest.tcn");
		this.model = new ModelBoatWithChest();
		this.texCache = new HashMap<String, ResourceLocation>();
	}
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks)
	{
		glPushMatrix();
		glTranslated(x, y + entity.yOffset, z);
		// glScalef(1.0f / 16.0f, 1.0f / 16.0f, 1.0f / 16.0f);
		glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
		glRotatef(yaw + 90f, 0.0f, 1.0f, 0.0f);
		OUtil.bindTexture(this.getEntityTexture(entity));
		model.render(1.0f / 16.0f);
		glPopMatrix();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		EntityOceaniaBoatWithChest boat = (EntityOceaniaBoatWithChest) entity;
		try
		{
			// System.out.println("textures/entity/" + BoatTypes.values()[boat.getDataWatcher().getWatchableObjectByte(EntityOceaniaBoat.BYTE_BOAT_TYPE)]._unloc + "Chest.png");
			String boatName = boat.getBoatType().unloc;
			if (!this.texCache.containsKey(boatName))
				this.texCache.put(boatName, new ResourceLocation("oceania", "textures/models/" + boatName + "Chest.png"));
			return this.texCache.get(boatName);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return new ResourceLocation("oceania", "/textures/entity/ironBoatChest.png");
	}
	
}
