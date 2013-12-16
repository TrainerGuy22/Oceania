package oceania.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import oceania.entity.EntityOceaniaBoat;
import oceania.entity.EntityOceaniaBoatNormal;
import oceania.entity.EntityOceaniaBoatWithChest;
import oceania.util.BoatType;
import oceania.util.IconRegistry;
import oceania.util.OUtil;

public class ItemOceaniaBoatWithChest extends ItemOceaniaBoat 
{

	public ItemOceaniaBoatWithChest(int id) 
	{
		super(id);
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        float pitch = player.rotationPitch;
        float yaw = player.rotationYaw;
        double pX = player.posX;
        double pY = player.posY;
        double pZ = player.posZ;
        Vec3 playerPos = world.getWorldVec3Pool().getVecFromPool(pX, pY, pZ);
        float yawOffZ = MathHelper.cos((-yaw * OUtil.PI_OVER_180) - (float) Math.PI);
        float yawOffX = MathHelper.sin((-yaw * OUtil.PI_OVER_180) - (float) Math.PI);
        float pitchOffZ = -MathHelper.cos((-pitch) * OUtil.PI_OVER_180);
        float pitchOffY = MathHelper.sin((-pitch) * OUtil.PI_OVER_180);
        float offsetX = yawOffX * pitchOffZ;
        float offsetZ = yawOffZ * pitchOffZ;
        double radius = 5.0;
        Vec3 offset = playerPos.addVector((double)offsetX * radius, (double)pitchOffY * radius, (double)offsetZ * radius);
        MovingObjectPosition hitPos = world.clip(playerPos, offset, true);

        if (hitPos == null)
        {
            return stack;
        }
        else if (hitPos.typeOfHit == EnumMovingObjectType.TILE)
        {
        	int bX = hitPos.blockX;
        	int bY = hitPos.blockY;
        	int bZ = hitPos.blockZ;
        	int bID = world.getBlockId(bX, bY, bZ);
        	
        	if (bID == Block.waterStill.blockID || bID == Block.waterMoving.blockID)
        	{
        		EntityOceaniaBoatWithChest boat = new EntityOceaniaBoatWithChest(world, bX + 0.5, bY + 1.0, bZ + 0.5);
                boat.rotationYaw = (float)(((MathHelper.floor_double((double)(player.rotationYaw * 4.0f / 360.0f) + 0.5) & 3) - 1) * 90);
                boat.setBoatType(BoatType.values()[stack.getItemDamage()]);
                //boat.setOwner(player.getEntityName());
                
        		if (!world.getCollidingBoundingBoxes(boat, boat.boundingBox.expand(-0.1D, -0.1D, -0.1D)).isEmpty())
                    return stack;
        		if (!world.isRemote)
        		{
        			world.spawnEntityInWorld(boat);
        		}
        		if (!player.capabilities.isCreativeMode)
        			stack.stackSize--;
        	}
        }
    	
    	return stack;
    }
	
	@Override
	public void initLangNames()
	{
		for(BoatType boat : BoatType.values()) 
		{
			LanguageRegistry.instance().addStringLocalization("item." + boat.unloc + ".chest.name", boat.loc + " with Chest");
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry)
	{
		for(int index = 0; index < BoatType.values().length; index++) 
		{
			IconRegistry.setIcon(BoatType.values()[index].unloc + "Chest", registry.registerIcon("oceania:" + BoatType.values()[index].unloc + "Chest"));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int meta)
	{
		return IconRegistry.getIcon(BoatType.values()[meta].unloc + "Chest");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public String getUnlocalizedName(ItemStack stack) 
	{
		return "item." + BoatType.values()[stack.getItemDamage()].unloc + ".chest";
	}
	
	protected static Entity createBoat(World world, BoatType boatType, double x, double y, double z)
	{
		return new EntityOceaniaBoatWithChest(world, boatType, x, y, z);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List descriptionList, boolean noClueWhatThisEvenDoes)
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			descriptionList.add(OUtil.colorString("&&dSteve not included.&&7"));
			switch(itemStack.getItemDamage())
			{
			case 0:
				descriptionList.add(OUtil.colorString("A boat made of &&esturdy planks&&7."));
				descriptionList.add("Doubt it will last long.");
				break;
			case 1:
				descriptionList.add(OUtil.colorString("A boat made of &&fIron&&7, should"));
				descriptionList.add("last a while.");
				break;
			case 2: 
				descriptionList.add(OUtil.colorString("Made of purely &&6Atlantium&&7,"));
				descriptionList.add("almost indestructable.");
			}
		}
		else
		{
			descriptionList.add(OUtil.colorString("Hold &&9SHIFT &&7for more information"));
		}
	}

}
