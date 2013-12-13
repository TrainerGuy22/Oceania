package oceania.blocks;

import oceania.Oceania;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class BlockNeptuniteOre extends Block
{

	public BlockNeptuniteOre(int blockID)
	{
		super(blockID, Material.sand);
		setHardness(2.8f);
		setResistance(8f);
		setStepSound(soundSandFootstep);
		setUnlocalizedName("blockNeptunium");
		setCreativeTab(Oceania.CREATIVE_TAB);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry)
	{
		this.blockIcon = registry.registerIcon(Oceania.MOD_ID + ":oreNeptunite");
	}
	
}
