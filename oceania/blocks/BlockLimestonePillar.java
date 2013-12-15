package oceania.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import oceania.Oceania;
import oceania.blocks.BlockLimestone.LimestoneTypes;
import oceania.items.ItemBlockWithDescription;
import oceania.items.Items;
import oceania.util.IconRegistry;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockLimestonePillar extends BlockLog 
{

	protected BlockLimestonePillar(int id) 
	{
		super(id);
		setCreativeTab(Oceania.CREATIVE_TAB);
		this.setHardness(1.5F);
		this.setResistance(10.0F);
		this.setStepSound(soundStoneFootstep);
		this.setUnlocalizedName("limestonePillar");
		ItemBlockWithDescription.registerDescription(this, "A mineral deposit, good for\ndecoration.");
	}
	
	@Override
    public int idDropped(int meta, Random random, int fortune) 
	{
    	return this.blockID;
    }
	
	@Override
    public int damageDropped(int meta) 
	{
		return 0;
	}
	
	@Override
    public void breakBlock(World world, int par2, int par3, int par4, int par5, int par6)
    {}
	
    @SideOnly(Side.CLIENT)
    protected Icon getSideIcon(int meta)
    {
        return IconRegistry.getIcon("limestonePillar0");
    }

    @SideOnly(Side.CLIENT)
    protected Icon getEndIcon(int meta)
    {
        return IconRegistry.getIcon("limestonePillar1");
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry)
	{
		IconRegistry.setIcon("limestonePillar0", registry.registerIcon(Oceania.MOD_ID + ":limestonePillar0"));
		IconRegistry.setIcon("limestonePillar1", registry.registerIcon(Oceania.MOD_ID + ":limestonePillar1"));
	}
	
    @Override
    public boolean canSustainLeaves(World world, int x, int y, int z)
    {
        return false;
    }

    @Override
    public boolean isWood(World world, int x, int y, int z)
    {
        return false;
    }

}
