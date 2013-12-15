package oceania.entity.render.model;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import oceania.entity.EntityOceaniaBoatWithChest;
import oceania.util.BoatTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.techne.TechneModel;

public class ModelOceaniaBoatWithChest extends ModelBase 
{
	public TechneModel model;
	
	public ModelOceaniaBoatWithChest()
	{
		model = ((TechneModel) AdvancedModelLoader.loadModel("/assets/oceania/textures/models/boatWithChest.tcn"));
	}
	
	@Override
    public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		EntityOceaniaBoatWithChest boat = (EntityOceaniaBoatWithChest) entity;
		// TODO: Scale texture to actually fit.
		try 
		{
			int strength = boat.getDataWatcher().getWatchableObjectInt(22);
			for(int index = 0; index < BoatTypes.values().length; index++) {
				if(((Integer) BoatTypes.values()[index].strength).equals(strength)) {
					System.out.println("textures/entity/" + BoatTypes.values()[index]._unloc + "Chest.png");
					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("oceania", "textures/entity/" + BoatTypes.values()[index]._unloc + "Chest.png"));
				}
			}
		} catch(Exception e) 
		{}
		GL11.glScalef(par7, par7, par7);
		model.renderAll();
		GL11.glPopMatrix();
	}

}
