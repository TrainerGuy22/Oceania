package oceania.blocks;

import java.util.Random;

import oceania.Oceania;
import oceania.items.Items;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class BlockAtlantiteOre extends Block
{

	public BlockAtlantiteOre(int blockID)
	{
		super(blockID, Material.sand);
		setHardness(2.8f);
		setResistance(8f);
		setStepSound(soundSandFootstep);
		setUnlocalizedName("blockAtlantite");
		setCreativeTab(Oceania.CREATIVE_TAB);
	}
	
	@Override
    public int idDropped(int meta, Random random, int fortune) 
	{
    	return Items.itemMulti.itemID;
    }
	
	@Override
    public int damageDropped(int meta) 
	{
		return 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry)
	{
		this.blockIcon = registry.registerIcon(Oceania.MOD_ID + ":oreAtlantite");
	}
	
}
