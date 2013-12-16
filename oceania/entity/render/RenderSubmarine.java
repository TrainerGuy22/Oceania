package oceania.entity.render;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.techne.TechneModel;

@SideOnly(Side.CLIENT)
public class RenderSubmarine extends Render
{
	private IModelCustom model;
	
	public RenderSubmarine()
	{
		model = AdvancedModelLoader.loadModel("/assets/oceania/textures/models/submarine.tcn");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void doRender(Entity entity, double x, double y, double z, float f, float f1) 
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		
		
		GL11.glScalef(0.0625F, 0.0625F, 0.0625F);
		model.renderAll();
		GL11.glPopMatrix();
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ResourceLocation getEntityTexture(Entity entity) 
	{
		return new ResourceLocation("oceania", "/textures/entity/submarine.png");
	}

}
