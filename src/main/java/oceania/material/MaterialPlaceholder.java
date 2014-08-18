package oceania.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialPlaceholder extends Material
{

	public MaterialPlaceholder()
	{
		super(MapColor.airColor);
	}
	
	@Override
	public boolean blocksMovement()
	{
		return true;
	}
	
	@Override
	public boolean isReplaceable()
	{
		return true;
	}
	
	@Override
	public boolean isSolid()
	{
		return false;
	}
	
	@Override
	public boolean isOpaque()
	{
		return false;
	}
	
}
