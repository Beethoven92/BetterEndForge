package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public abstract class FullHeightScatterFeature extends Feature<NoFeatureConfig>
{
	private static final Mutable POS = new Mutable();
	private final int radius;
	
	public FullHeightScatterFeature(int radius) 
	{	
		super(NoFeatureConfig.field_236558_a_);
		this.radius = radius;
	}
	
	public abstract boolean canGenerate(ISeedReader world, Random random, BlockPos center, BlockPos blockPos, float radius);
	
	public abstract void generate(ISeedReader world, Random random, BlockPos blockPos);
	
	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random,
			BlockPos center, NoFeatureConfig config)
	{
		int maxY = world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, center.getX(), center.getZ());
		int minY = BlockHelper.upRay(world, new BlockPos(center.getX(), 0, center.getZ()), maxY);
		for (int y = maxY; y > minY; y--) 
		{
			POS.setPos(center.getX(), y, center.getZ());
			if (world.getBlockState(POS).isAir() && !world.getBlockState(POS.down()).isAir()) 
			{
				float r = ModMathHelper.randRange(radius * 0.5F, radius, random);
				int count = ModMathHelper.floor(r * r * ModMathHelper.randRange(1.5F, 3F, random));
				for (int i = 0; i < count; i++) 
				{
					float pr = r * (float) Math.sqrt(random.nextFloat());
					float theta = random.nextFloat() * ModMathHelper.PI2;
					float x = pr * (float) Math.cos(theta);
					float z = pr * (float) Math.sin(theta);
					
					POS.setPos(center.getX() + x, y + 5, center.getZ() + z);
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
