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
		// TODO: Scale texture to actually fit.
		EntityOceaniaBoatWithChest boat = (EntityOceaniaBoatWithChest) entity;
		try 
		{
				System.out.println("textures/entity/" + BoatTypes.values()[boat.getDataWatcher().getWatchableObjectByte(EntityOceaniaBoat.BYTE_BOAT_TYPE)]._unloc + "Chest.png");
				return new ResourceLocation("oceania", "textures/entity/" + BoatTypes.values()[boat.getDataWatcher().getWatchableObjectByte(EntityOceaniaBoat.BYTE_BOAT_TYPE)]._unloc + "Chest.png");
		} catch(Exception e) 
		{
			e.printStackTrace();
		}
		return new ResourceLocation("oceania", "/textures/entity/ironBoatChest.png");
	}

}
