package oceania.entity.render;

import oceania.entity.EntityOceaniaBoat;
import oceania.entity.EntityOceaniaBoatWithChest;
import oceania.entity.render.model.ModelOceaniaBoatWithChest;
import oceania.items.Items;
import oceania.util.BoatTypes;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderOceaniaBoatWithChest extends RenderBoat
{
	
	@Override
	@SideOnly(Side.CLIENT)
    protected ResourceLocation getEntityTexture(Entity entity) 
	{
		if(this.modelBoat instanceof ModelBoat)
			modelBoat = new ModelOceaniaBoatWithChest();
		EntityOceaniaBoatWithChest boat = (EntityOceaniaBoatWithChest) entity;
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
		return new ResourceLocation("oceania", "/textures/entity/ironBoatChest.png");
	}

}
