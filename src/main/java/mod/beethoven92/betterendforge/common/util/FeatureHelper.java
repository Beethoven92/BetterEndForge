package mod.beethoven92.betterendforge.common.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class FeatureHelper
{
	protected static final BlockState AIR = Blocks.AIR.getDefaultState();
	protected static final BlockState WATER = Blocks.WATER.getDefaultState();

	public static int getYOnSurface(ISeedReader world, int x, int z)
	{
		return world.getHeight(Type.WORLD_SURFACE, x, z);
	}
	
	public static int getYOnSurfaceWG(ISeedReader world, int x, int z)
	{
		return world.getHeight(Type.WORLD_SURFACE_WG, x, z);
	}
	
	public static BlockPos getPosOnSurface(ISeedReader world, BlockPos pos)
	{
		return world.getHeight(Type.WORLD_SURFACE, pos);
	}
	
	public static BlockPos getPosOnSurfaceWG(ISeedReader world, BlockPos pos)
	{
		return world.getHeight(Type.WORLD_SURFACE_WG, pos);
	}
	
	public static BlockPos getPosOnSurfaceRaycast(ISeedReader world, BlockPos pos)
	{
		return getPosOnSurfaceRaycast(world, pos, 256);
	}
	
	public static BlockPos getPosOnSurfaceRaycast(ISeedReader world, BlockPos pos, int dist)
	{
		int h = BlockHelper.downRay(world, pos, dist);
		return pos.down(h);
	}
}
