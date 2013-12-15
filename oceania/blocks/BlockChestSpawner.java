package oceania.blocks;

import java.util.Random;

import oceania.Oceania;
import oceania.blocks.tile.TileEntityChestSpawner;
import oceania.items.ItemBlockWithDescription;
import oceania.items.Items;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/*
 * Regulates villagers in underwater villages.
 */
public class BlockChestSpawner extends BlockChest 
{

	protected BlockChestSpawner(int id) 
	{
		super(id, 0);
        this.setCreativeTab(Oceania.CREATIVE_TAB);
        ItemBlockWithDescription.registerDescription(this, "You're not suppose to have\nthis, you cheater! :P");
	}
	
	@Override
    public int idDropped(int meta, Random random, int fortune) 
	{
    	return Block.chest.blockID;
    }
	
    @Override
    public int getExpDrop(World world, int data, int enchantmentLevel)
    {
        return 15 + world.rand.nextInt(15);
    }
	
	@Override
    public TileEntity createNewTileEntity(World par1World)
    {
		return new TileEntityChestSpawner();
    }

}
