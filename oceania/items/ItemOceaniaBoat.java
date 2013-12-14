package oceania.items;

import java.util.List;

import oceania.OUtil;
import oceania.Oceania;
import oceania.entity.EntityOceaniaBoat;
import oceania.items.ItemMulti.ItemMultiType;
import oceania.util.BoatTypes;
import oceania.util.IconRegistry;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemOceaniaBoat extends ItemBoat 
{

	public ItemOceaniaBoat(int id) 
	{
		super(id);
		for(BoatTypes boat : BoatTypes.values()) 
		{
			LanguageRegistry.instance().addStringLocalization("item." + boat._unloc + ".name", boat._loc);
		}
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        float f = 1.0F;
        float f1 = par3EntityPlayer.prevRotationPitch + (par3EntityPlayer.rotationPitch - par3EntityPlayer.prevRotationPitch) * f;
        float f2 = par3EntityPlayer.prevRotationYaw + (par3EntityPlayer.rotationYaw - par3EntityPlayer.prevRotationYaw) * f;
        double d0 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * (double)f;
        double d1 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * (double)f + 1.62D - (double)par3EntityPlayer.yOffset;
        double d2 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * (double)f;
        Vec3 vec3 = par2World.getWorldVec3Pool().getVecFromPool(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
        MovingObjectPosition movingobjectposition = par2World.clip(vec3, vec31, true);

        if (movingobjectposition == null)
        {
            return par1ItemStack;
        }
        else
        {
            Vec3 vec32 = par3EntityPlayer.getLook(f);
            boolean flag = false;
            float f9 = 1.0F;
            List list = par2World.getEntitiesWithinAABBExcludingEntity(par3EntityPlayer, par3EntityPlayer.boundingBox.addCoord(vec32.xCoord * d3, vec32.yCoord * d3, vec32.zCoord * d3).expand((double)f9, (double)f9, (double)f9));
            int i;

            for (i = 0; i < list.size(); ++i)
            {
                Entity entity = (Entity)list.get(i);

                if (entity.canBeCollidedWith())
                {
                    float f10 = entity.getCollisionBorderSize();
                    AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)f10, (double)f10, (double)f10);

                    if (axisalignedbb.isVecInside(vec3))
                    {
                        flag = true;
                    }
                }
            }

            if (flag)
            {
                return par1ItemStack;
            }
            else
            {
                if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
                {
                    i = movingobjectposition.blockX;
                    int j = movingobjectposition.blockY;
                    int k = movingobjectposition.blockZ;

                    if (par2World.getBlockId(i, j, k) == Block.snow.blockID)
                    {
                        --j;
                    }

                    EntityOceaniaBoat entityboat = new EntityOceaniaBoat(par2World, BoatTypes.values()[par1ItemStack.getItemDamage()], (double)((float)i + 0.5F), (double)((float)j + 1.0F), (double)((float)k + 0.5F));
                    entityboat.rotationYaw = (float)(((MathHelper.floor_double((double)(par3EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) - 1) * 90);

                    if (!par2World.getCollidingBoundingBoxes(entityboat, entityboat.boundingBox.expand(-0.1D, -0.1D, -0.1D)).isEmpty())
                    {
                        return par1ItemStack;
                    }

                    if (!par2World.isRemote)
                    {
                        par2World.spawnEntityInWorld(entityboat);
                    }

                    if (!par3EntityPlayer.capabilities.isCreativeMode)
                    {
                        --par1ItemStack.stackSize;
                    }
                }

                return par1ItemStack;
            }
        }
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public String getUnlocalizedName(ItemStack stack) 
	{
		return "item." + BoatTypes.values()[stack.getItemDamage()]._unloc;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry)
	{
		for(int index = 0; index < BoatTypes.values().length; index++) 
		{
			IconRegistry.setIcon(BoatTypes.values()[index]._unloc, registry.registerIcon(BoatTypes.values()[index].namespace + ":" + BoatTypes.values()[index]._unloc));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int meta)
	{
		return IconRegistry.getIcon(BoatTypes.values()[meta]._unloc);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(int ID, CreativeTabs tabs, List list) 
	{
		for(int index = 0; index < BoatTypes.values().length; index++) 
		{
			list.add(new ItemStack(ID, 1, index));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List descriptionList, boolean noClueWhatThisEvenDoes)
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			switch(itemStack.getItemDamage())
			{
			case 0:
				descriptionList.add("A boat made of sturdy planks.");
				descriptionList.add("Doubt it will last long.");
				break;
			case 1:
				descriptionList.add("A boat made of Iron, should");
				descriptionList.add("last a while.");
				break;
			case 2: 
				descriptionList.add("Made of purely Atlantium,");
				descriptionList.add("almost indestructable.");
			}
		}
		else
		{
			descriptionList.add(OUtil.colorString("Hold &&9SHIFT &&7for more information"));
		}
	}

}
