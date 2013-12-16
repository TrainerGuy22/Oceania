package oceania.entity.render;

import java.util.Map;

import oceania.entity.EntitySubmarine;
import oceania.util.OUtil;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.techne.TechneModel;

@SideOnly(Side.CLIENT)
public class RenderSubmarine extends Render
{
	private TechneModel model;
	private ResourceLocation texLoc;
	
	public RenderSubmarine()
	{
		model = (TechneModel) AdvancedModelLoader.loadModel("/assets/oceania/models/submarine.tcn");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void doRender(Entity entity, double x, double y, double z, float scale, float partialTicks) 
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y + EntitySubmarine.ENT_HEIGHT, (float) z);
		GL11.glScalef(0.0625F, 0.0625F, 0.0625F);
		//System.out.printf("Some random parameter: %f\n", scale);
		if (this.texLoc == null)
			this.texLoc = this.getEntityTexture(entity);
		OUtil.bindTexture(this.texLoc);
		model.renderAll();
		GL11.glPopMatrix();
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected ResourceLocation getEntityTexture(Entity entity) 
	{
		return new ResourceLocation("oceania", "textures/models/submarine.png");
	}

}
