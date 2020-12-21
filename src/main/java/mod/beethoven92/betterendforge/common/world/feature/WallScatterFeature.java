package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public abstract class WallScatterFeature extends Feature<NoFeatureConfig>
{
	private static final Direction[] DIR = BlockHelper.makeHorizontal();
	private final int radius;
	
	public WallScatterFeature(int radius) 
	{
		super(NoFeatureConfig.field_236558_a_);
		this.radius = radius;
	}
	
	public abstract boolean canGenerate(ISeedReader world, Random random, BlockPos pos, Direction dir);
	
	public abstract void generate(ISeedReader world, Random random, BlockPos pos, Direction dir);
	
	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random,
			BlockPos center, NoFeatureConfig config) 
	{
		int maxY = world.getHeight(Heightmap.Type.WORLD_SURFACE, center.getX(), center.getZ());
		int minY = BlockHelper.upRay(world, new BlockPos(center.getX(), 0, center.getZ()), maxY);
		if (maxY < 10 || maxY < minY) 
		{
			return false;
		}
		int py = ModMathHelper.randRange(minY, maxY, random);
		
		Mutable mut = new Mutable();
		for (int x = -radius; x <= radius; x++) 
		{
			mut.setX(center.getX() + x);
			for (int y = -radius; y <= radius; y++) 
			{
				mut.setY(py + y);
				for (int z = -radius; z <= radius; z++) 
				{
					mut.setZ(center.getZ() + z);
					if (random.nextInt(4) == 0 && world.isAirBlock(mut)) 
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
