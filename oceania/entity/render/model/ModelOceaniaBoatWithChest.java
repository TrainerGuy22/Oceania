package oceania.entity.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.techne.TechneModel;

public class ModelOceaniaBoatWithChest extends ModelBase 
{
	public TechneModel model;
	
	public ModelOceaniaBoatWithChest()
	{
		model = (TechneModel) AdvancedModelLoader.loadModel("/assets/oceania/textures/models/boatWithChest.tcn");
	}
	
	@Override
    public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		model.render(entity, par2, par3, par4, par5, par6, par7);
	}

}
