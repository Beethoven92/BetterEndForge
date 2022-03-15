package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public abstract class FullHeightScatterFeature extends Feature<NoneFeatureConfiguration>
{
	private static final MutableBlockPos POS = new MutableBlockPos();
	private final int radius;
	
	public FullHeightScatterFeature(int radius) 
	{	
		super(NoneFeatureConfiguration.CODEC);
		this.radius = radius;
	}
	
	public abstract boolean canGenerate(WorldGenLevel world, Random random, BlockPos center, BlockPos blockPos, float radius);
	
	public abstract void generate(WorldGenLevel world, Random random, BlockPos blockPos);
	
	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator, Random random,
			BlockPos center, NoneFeatureConfiguration config)
	{
		int maxY = world.getHeight(Heightmap.Types.WORLD_SURFACE_WG, center.getX(), center.getZ());
		int minY = BlockHelper.upRay(world, new BlockPos(center.getX(), 0, center.getZ()), maxY);
		for (int y = maxY; y > minY; y--) 
		{
			POS.set(center.getX(), y, center.getZ());
			if (world.getBlockState(POS).isAir() && !world.getBlockState(POS.below()).isAir()) 
			{
				float r = ModMathHelper.randRange(radius * 0.5F, radius, random);
				int count = ModMathHelper.floor(r * r * ModMathHelper.randRange(1.5F, 3F, random));
				for (int i = 0; i < count; i++) 
				{
					float pr = r * (float) Math.sqrt(random.nextFloat());
					float theta = random.nextFloat() * ModMathHelper.PI2;
					float x = pr * (float) Math.cos(theta);
					float z = pr * (float) Math.sin(theta);
					
					POS.set(center.getX() + x, y + 5, center.getZ() + z);
					int down = BlockHelper.downRay(world, POS, 16);
					if (down > 10) continue;
					POS.setY(POS.getY() - down);
					
					if (canGenerate(world, random, center, POS, r)) 
					{
						generate(world, random, POS);
					}
				}
			}
		}
		return true;
	}
}
