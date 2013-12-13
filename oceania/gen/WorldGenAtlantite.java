package oceania.gen;

import java.util.Random;

import oceania.blocks.Blocks;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenAtlantite implements IWorldGenerator
{
	private int _veinSize;
	private int _veinCount;
	
	public WorldGenAtlantite(int veinSize, int veinsPerChunk)
	{
		this._veinSize = veinSize;
		this._veinCount = veinsPerChunk;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		if (world.provider.dimensionId == 0 /** Surface world */)
		{
			int chunkBlockX = chunkX * 16;
			int chunkBlockZ = chunkZ * 16;
			
			if (world.getBiomeGenForCoords(chunkBlockX, chunkBlockZ) == BiomeGenBase.ocean) // Generate in the ocean
			{
				generateOre(world, random, chunkBlockX, chunkBlockZ);
			}
		}
	}
	
	private void generateOre(World world, Random random, int chunkBlockX, int chunkBlockZ)
	{
		WorldGenMinable gen = new WorldGenMinable(Blocks.blockAtlantite.blockID, this._veinSize, Block.sand.blockID);
		
		for (int count = 0; count < _veinCount; count++)
		{
			int startX = chunkBlockX + random.nextInt(16);
			int startY = 64;
			int startZ = chunkBlockZ + random.nextInt(16);
			
			while ((world.getBlockId(startX, startY, startZ) == 0 || world.getBlockId(startX, startY, startZ) == Block.waterStill.blockID) && startY > 0)
				startY--;
			
			gen.generate(world, random, startX, startY, startZ);
		}
	}
	
}
