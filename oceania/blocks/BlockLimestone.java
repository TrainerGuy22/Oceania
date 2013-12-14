package oceania.blocks;

import oceania.Oceania;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockLimestone extends Block {

	public BlockLimestone(int id) {
		super(id, Material.rock);
		setCreativeTab(Oceania.CREATIVE_TAB);
		this.setHardness(1.5F);
		this.setResistance(10.0F);
		this.setStepSound(soundStoneFootstep);
	}

}
