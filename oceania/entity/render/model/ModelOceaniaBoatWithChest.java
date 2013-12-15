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
		GL11.glScalef(par7, par7, par7);
		model.renderAll();
		GL11.glPopMatrix();
	}

}
