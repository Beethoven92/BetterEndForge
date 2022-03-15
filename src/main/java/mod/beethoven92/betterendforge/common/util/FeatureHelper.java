package mod.beethoven92.betterendforge.common.util;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class FeatureHelper
{
	protected static final BlockState AIR = Blocks.AIR.defaultBlockState();
	protected static final BlockState WATER = Blocks.WATER.defaultBlockState();

	public static int getYOnSurface(WorldGenLevel world, int x, int z)
	{
		return world.getHeight(Types.WORLD_SURFACE, x, z);
	}
	
	public static int getYOnSurfaceWG(WorldGenLevel world, int x, int z)
	{
		return world.getHeight(Types.WORLD_SURFACE_WG, x, z);
	}
	
	public static BlockPos getPosOnSurface(WorldGenLevel world, BlockPos pos)
	{
		return world.getHeightmapPos(Types.WORLD_SURFACE, pos);
	}
	
	public static BlockPos getPosOnSurfaceWG(WorldGenLevel world, BlockPos pos)
	{
		return world.getHeightmapPos(Types.WORLD_SURFACE_WG, pos);
	}
	
	public static BlockPos getPosOnSurfaceRaycast(WorldGenLevel world, BlockPos pos)
	{
		return getPosOnSurfaceRaycast(world, pos, 256);
	}
	
	public static BlockPos getPosOnSurfaceRaycast(WorldGenLevel world, BlockPos pos, int dist)
	{
		int h = BlockHelper.downRay(world, pos, dist);
		return pos.below(h);
	}
}
