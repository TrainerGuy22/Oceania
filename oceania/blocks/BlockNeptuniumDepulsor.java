package oceania.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import oceania.ItemBlockWithDescription;
import oceania.Oceania;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.World;

public class BlockNeptuniumDepulsor extends Block
{

	public BlockNeptuniumDepulsor(int blockId)
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
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		world.scheduleBlockUpdate(x, y, z, this.blockID, 1);
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
		int meta = world.getBlockMetadata(x, y, z);
		int radius = meta / 4;
		for (int xx = x - radius; xx <= x + radius; xx++)
		{
			for (int yy = y - radius; yy <= y + radius; yy++)
			{
				for (int zz = z - radius; zz <= z + radius; zz++)
				{
					int bID = world.getBlockId(xx, yy, zz);
					if (bID == Block.waterMoving.blockID || bID == Block.waterStill.blockID || bID == 0)
					{
						world.setBlock(xx, yy, zz, Blocks.blockPlaceholder.blockID);
					}
				}
			}
		}
		if (meta < 15)
			world.setBlockMetadataWithNotify(x, y, z, ++meta, 0);
		world.scheduleBlockUpdate(x, y, z, this.blockID, 1);
	}
	
}
