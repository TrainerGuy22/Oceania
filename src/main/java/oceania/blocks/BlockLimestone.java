package oceania.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import oceania.Oceania;
import oceania.items.ItemBlockLimestone;
import oceania.items.Items;
import oceania.items.ItemMulti.ItemMultiType;
import oceania.util.IconRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockLimestone extends Block 
{
	
	public enum LimestoneTypes
	{
		LIMESTONE("limestone", "Limestone"),
		LIMESTONE_BRICK("limestoneBrick", "Limestone Brick");
		
		private String _unloc, _loc;
		private LimestoneTypes(String unlocName, String locName)
		{
			this._unloc = unlocName;
			this._loc = locName;
		}
		
		public String getUnlocalizedName()
		{
			return this._unloc;
		}
		
		public String getLocalizedName()
		{
			return this._loc;
		}
	}

	public BlockLimestone(int id) 
	{
		super(id, Material.rock);
		setCreativeTab(Oceania.CREATIVE_TAB);
		this.setHardness(1.5F);
		this.setResistance(10.0F);
		this.setStepSound(soundStoneFootstep);
		ItemBlockLimestone.registerDescription(this, "A mineral deposit, good for\ndecoration.");
	}
	
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) 
	{
		world.setBlockMetadataWithNotify(x, y, z, stack.getItemDamage(), 3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry)
	{
		for(int index = 0; index < LimestoneTypes.values().length; index++) 
		{
			IconRegistry.setIcon(LimestoneTypes.values()[index].getUnlocalizedName(), registry.registerIcon(Oceania.MOD_ID + ":" + LimestoneTypes.values()[index].getUnlocalizedName()));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
	{
		// Why do I have to put a modulus here? o_O
		return IconRegistry.getIcon(LimestoneTypes.values()[meta % LimestoneTypes.values().length].getUnlocalizedName());
	}
	
	@Override
    public int damageDropped(int meta) 
	{
		return meta;
	}

}
