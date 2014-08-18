package oceania.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import oceania.Oceania;
import oceania.entity.EntitySubmarine;
import oceania.util.OUtil;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSubmarine extends Item 
{	
	public ItemSubmarine(int itemID) 
	{
		super(itemID);
		setCreativeTab(Oceania.CREATIVE_TAB);
		this.setUnlocalizedName("itemSubmarine");
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
        		EntitySubmarine sub = new EntitySubmarine(world, bX + 0.5, bY + 1.0, bZ + 0.5);
                sub.rotationYaw = (float)(((MathHelper.floor_double((double)(player.rotationYaw * 4.0f / 360.0f) + 0.5) & 3) - 1) * 90);
                sub.setOwner(player.getEntityName());
                
        		if (!world.getCollidingBoundingBoxes(sub, sub.boundingBox.expand(-0.1D, -0.1D, -0.1D)).isEmpty())
                    return stack;
        		if (!world.isRemote)
        			world.spawnEntityInWorld(sub);
        		if (!player.capabilities.isCreativeMode)
        			stack.stackSize--;
        	}
        }
    	
    	return stack;
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry)
	{
		this.itemIcon = registry.registerIcon(Oceania.MOD_ID + ":submarine");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List descriptionList, boolean noClueWhatThisEvenDoe)
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			descriptionList.add("A Submarine, the best");
			descriptionList.add("in ocean exploration!");
		}
		else
		{
			descriptionList.add(OUtil.colorString("Hold &&9SHIFT &&7for more information"));
		}
	}

}
