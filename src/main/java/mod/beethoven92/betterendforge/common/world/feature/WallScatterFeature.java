package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public abstract class WallScatterFeature extends Feature<NoneFeatureConfiguration>
{
	private static final Direction[] DIR = BlockHelper.makeHorizontal();
	private final int radius;
	
	public WallScatterFeature(int radius) 
	{
		super(NoneFeatureConfiguration.CODEC);
		this.radius = radius;
	}
	
	public abstract boolean canGenerate(WorldGenLevel world, Random random, BlockPos pos, Direction dir);
	
	public abstract void generate(WorldGenLevel world, Random random, BlockPos pos, Direction dir);
	
	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator, Random random,
			BlockPos center, NoneFeatureConfiguration config) 
	{
		int maxY = world.getHeight(Heightmap.Types.WORLD_SURFACE, center.getX(), center.getZ());
		int minY = BlockHelper.upRay(world, new BlockPos(center.getX(), 0, center.getZ()), maxY);
		if (maxY < 10 || maxY < minY) 
		{
			return false;
		}
		int py = ModMathHelper.randRange(minY, maxY, random);
		
		MutableBlockPos mut = new MutableBlockPos();
		for (int x = -radius; x <= radius; x++) 
		{
			mut.setX(center.getX() + x);
			for (int y = -radius; y <= radius; y++) 
			{
				mut.setY(py + y);
				for (int z = -radius; z <= radius; z++) 
				{
					mut.setZ(center.getZ() + z);
					if (random.nextInt(4) == 0 && world.isEmptyBlock(mut)) 
					{
						shuffle(random);
						for (Direction dir: DIR) 
						{
							if (canGenerate(world, random, mut, dir)) 
							{
								generate(world, random, mut, dir);
								break;
							}
						}
					}
				}
			}
		}
		
		return true;
	}
	
	private void shuffle(Random random) 
	{
		for (int i = 0; i < 4; i++) 
		{
			int j = random.nextInt(4);
			Direction d = DIR[i];
			DIR[i] = DIR[j];
			DIR[j] = d;
		}
	}
}
