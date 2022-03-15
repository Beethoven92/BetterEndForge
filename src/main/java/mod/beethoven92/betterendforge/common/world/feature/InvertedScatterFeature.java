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

public abstract class InvertedScatterFeature extends Feature<NoneFeatureConfiguration>
{
	private static final MutableBlockPos POS = new MutableBlockPos();
	private final int radius;
	
	public InvertedScatterFeature(int radius) 
	{
		super(NoneFeatureConfiguration.CODEC);
		this.radius = radius;
	}
	
	public abstract boolean canGenerate(WorldGenLevel world, Random random, BlockPos center, BlockPos blockPos, float radius);
	
	public abstract void generate(WorldGenLevel world, Random random, BlockPos blockPos);
	
	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator, Random random,
			BlockPos pos, NoneFeatureConfiguration config) 
	{
		int maxY = world.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ());
		int minY = BlockHelper.upRay(world, new BlockPos(pos.getX(), 0, pos.getZ()), maxY);

		for (int y = maxY; y > minY; y--) 
		{
			POS.set(pos.getX(), y, pos.getZ());
			
			if (world.getBlockState(POS).isAir() && !world.getBlockState(POS.above()).isAir()) 
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
					
					POS.set(pos.getX() + x, POS.getY(), pos.getZ() + z);
					
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
