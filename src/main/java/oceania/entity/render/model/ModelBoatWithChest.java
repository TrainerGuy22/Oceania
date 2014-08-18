package oceania.entity.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBoatWithChest extends ModelBase
{
	// fields
	ModelRenderer	Bottom;
	ModelRenderer	LeftSide;
	ModelRenderer	Back;
	ModelRenderer	RightSide;
	ModelRenderer	Front;
	ModelRenderer	Chest;
	ModelRenderer	ChestButton;
	
	public ModelBoatWithChest()
	{
		textureWidth = 512;
		textureHeight = 256;
		
		Bottom = new ModelRenderer(this, 0, 0);
		Bottom.addBox(0F, 0F, 0F, 21, 2, 40);
		Bottom.setRotationPoint(-9F, 22F, -20F);
		Bottom.setTextureSize(512, 256);
		Bottom.mirror = true;
		setRotation(Bottom, 0F, 0F, 0F);
		LeftSide = new ModelRenderer(this, 172, 0);
		LeftSide.addBox(0F, 0F, 0F, 40, 6, 3);
		LeftSide.setRotationPoint(10F, 16F, 20F);
		LeftSide.setTextureSize(512, 256);
		LeftSide.mirror = true;
		setRotation(LeftSide, 0F, 1.579523F, 0F);
		Back = new ModelRenderer(this, 93, 14);
		Back.addBox(0F, 0F, 0F, 23, 6, 3);
		Back.setRotationPoint(-10F, 16F, 18F);
		Back.setTextureSize(512, 256);
		Back.mirror = true;
		setRotation(Back, 0F, 0F, 0F);
		RightSide = new ModelRenderer(this, 86, 0);
		RightSide.addBox(0F, 0F, 0F, 40, 6, 3);
		RightSide.setRotationPoint(-10F, 16F, 20F);
		RightSide.setTextureSize(512, 256);
		RightSide.mirror = true;
		setRotation(RightSide, 0F, 1.579523F, 0F);
		Front = new ModelRenderer(this, 93, 23);
		Front.addBox(0F, 0F, 0F, 23, 6, 3);
		Front.setRotationPoint(-10F, 16F, -21F);
		Front.setTextureSize(512, 256);
		Front.mirror = true;
		setRotation(Front, 0F, 0F, 0F);
		Chest = new ModelRenderer(this, 0, 213);
		Chest.addBox(0F, 0F, 0F, 14, 14, 14);
		Chest.setRotationPoint(-5.5F, 8F, 3F);
		Chest.setTextureSize(512, 256);
		Chest.mirror = true;
		setRotation(Chest, 0F, 0F, 0F);
		ChestButton = new ModelRenderer(this, 8, 222);
		ChestButton.addBox(0F, 0F, 0F, 2, 4, 1);
		ChestButton.setRotationPoint(0.5F, 11F, 2F);
		ChestButton.setTextureSize(512, 256);
		ChestButton.mirror = true;
		setRotation(ChestButton, 0F, 0F, 0F);
	}
	
	public void render(float scale)
	{
		Bottom.render(scale);
		LeftSide.render(scale);
		Back.render(scale);
		RightSide.render(scale);
		Front.render(scale);
		Chest.render(scale);
		ChestButton.render(scale);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
}
