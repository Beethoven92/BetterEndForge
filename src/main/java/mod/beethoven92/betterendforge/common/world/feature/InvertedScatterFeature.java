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

public abstract class InvertedScatterFeature extends Feature<NoFeatureConfig>
{
	private static final Mutable POS = new Mutable();
	private final int radius;
	
	public InvertedScatterFeature(int radius) 
	{
		super(NoFeatureConfig.field_236558_a_);
		this.radius = radius;
	}
	
	public abstract boolean canGenerate(ISeedReader world, Random random, BlockPos center, BlockPos blockPos, float radius);
	
	public abstract void generate(ISeedReader world, Random random, BlockPos blockPos);
	
	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random,
			BlockPos pos, NoFeatureConfig config) 
	{
		int maxY = world.getHeight(Heightmap.Type.WORLD_SURFACE, pos.getX(), pos.getZ());
		int minY = BlockHelper.upRay(world, new BlockPos(pos.getX(), 0, pos.getZ()), maxY);

		for (int y = maxY; y > minY; y--) 
		{
			POS.setPos(pos.getX(), y, pos.getZ());
			
			if (world.getBlockState(POS).isAir() && !world.getBlockState(POS.up()).isAir()) 
			{
				float r = ModMathHelper.randRange(radius * 0.5F, radius, random);
				int count = ModMathHelper.floor(r * r * ModMathHelper.randRange(0.5F, 1.5F, random));
				for (int i = 0; i < count; i++) 
				{
					float pr = r * (float) Math.sqrt(random.nextFloat());
					float theta = random.nextFloat() * ModMathHelper.PI2;
					float x = pr * (float) Math.cos(theta);
					float z = pr * (float) Math.sin(theta);

					/*POS.setPos(center.getX() + x, center.getY() - 7, center.getZ() + z);

					int up = BlockHelper.upRay(world, POS, 16);
					
					if (up > 14) continue;
					
					POS.setY(POS.getY() + up);*/
					
					POS.setPos(pos.getX() + x, POS.getY(), pos.getZ() + z);
					
					if (canGenerate(world, random, pos, POS, r)) 
					{
						generate(world, random, POS);
					}
				}
			}
		}
		return true;
	}
}
