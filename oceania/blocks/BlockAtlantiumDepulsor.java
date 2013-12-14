package oceania.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import oceania.Oceania;
import oceania.items.ItemBlockWithDescription;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockAtlantiumDepulsor extends Block
{
	
	public BlockAtlantiumDepulsor(int blockId)
	{
		super(blockId, Material.iron);
		setHardness(1.8f);
		setResistance(6.0f);
		setStepSound(soundMetalFootstep);
		setLightValue(0.2f); // TODO: Make this change depending on how much power it has
		setUnlocalizedName("blockDepulsor");
		setCreativeTab(Oceania.CREATIVE_TAB);
		ItemBlockWithDescription.registerDescription(this, "This block will create a\nbubble of air around itself\nwhen placed in water.");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry)
	{
		this.blockIcon = registry.registerIcon(Oceania.MOD_ID + ":depulsor");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		double bX = (double) x + 0.5;
		double bY = (double) y + 0.5;
		double bZ = (double) z + 0.5;
		
		int side = random.nextInt(6);
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		double pX = bX + ((double) dir.offsetX / 1.5);
		double pY = bY + ((double) dir.offsetY / 1.5);
		double pZ = bZ + ((double) dir.offsetZ / 1.5);
		
		for (int i = 0; i < 10; i++)
		{
			pX += (random.nextDouble() * 0.25) - 0.125f;
			pY += (random.nextDouble() * 0.25) - 0.125f;
			pZ += (random.nextDouble() * 0.25) - 0.125f;
			
			world.spawnParticle("portal", pX, pY, pZ, (double) dir.offsetX * 0.5, (double) dir.offsetY * 0.5, (double) dir.offsetZ * 0.5);
		}
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		// world.scheduleBlockUpdate(x, y, z, this.blockID, 1);
	}
	
	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int oldMeta)
	{
		int radius = 3;
		for (int xx = x - radius; xx <= x + radius; xx++)
		{
			for (int yy = y - radius; yy <= y + radius; yy++)
			{
				for (int zz = z - radius; zz <= z + radius; zz++)
				{
					int bID = world.getBlockId(xx, yy, zz);
					if (bID == Blocks.blockPlaceholder.blockID)
					{
						world.setBlockToAir(xx, yy, zz);
					}
				}
			}
		}
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{

	}
	
}
