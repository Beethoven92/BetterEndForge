package mod.beethoven92.betterendforge.common.world.feature;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.FeatureHelper;
import mod.beethoven92.betterendforge.common.util.NbtModIdReplacer;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;

public abstract class NBTFeature extends Feature<NoFeatureConfig>
{
	public NBTFeature() 
	{
		super(NoFeatureConfig.CODEC);
	}
	
	//protected static final DestructionStructureProcessor DESTRUCTION = new DestructionStructureProcessor();
	
	protected abstract Template getStructure(ISeedReader world, BlockPos pos, Random random);
	
	protected abstract boolean canSpawn(ISeedReader world, BlockPos pos, Random random);
	
	protected abstract Rotation getRotation(ISeedReader world, BlockPos pos, Random random);
	
	protected abstract Mirror getMirror(ISeedReader world, BlockPos pos, Random random);
	
	protected abstract int getYOffset(Template structure, ISeedReader world, BlockPos pos, Random random);
	
	protected abstract TerrainMerge getTerrainMerge(ISeedReader world, BlockPos pos, Random random);
	
	protected abstract void addStructureData(PlacementSettings data);
	
	protected BlockPos getGround(ISeedReader world, BlockPos center) 
	{
		Biome biome = world.getBiome(center);
		ResourceLocation id = ModBiomes.getBiomeID(biome);
		if (id.getNamespace().contains("moutain") || id.getNamespace().contains("lake")) 
		{
			int y = getAverageY(world, center);
			return new BlockPos(center.getX(), y, center.getZ());
		}
		else 
		{
			int y = getAverageYWG(world, center);
			return new BlockPos(center.getX(), y, center.getZ());
		}
	}
	
	protected int getAverageY(ISeedReader world, BlockPos center) {
		int y = FeatureHelper.getYOnSurface(world, center.getX(), center.getZ());
		y += FeatureHelper.getYOnSurface(world, center.getX() - 2, center.getZ() - 2);
		y += FeatureHelper.getYOnSurface(world, center.getX() + 2, center.getZ() - 2);
		y += FeatureHelper.getYOnSurface(world, center.getX() - 2, center.getZ() + 2);
		y += FeatureHelper.getYOnSurface(world, center.getX() + 2, center.getZ() + 2);
		return y / 5;
	}
	
	protected int getAverageYWG(ISeedReader world, BlockPos center) {
		int y = FeatureHelper.getYOnSurfaceWG(world, center.getX(), center.getZ());
		y += FeatureHelper.getYOnSurfaceWG(world, center.getX() - 2, center.getZ() - 2);
		y += FeatureHelper.getYOnSurfaceWG(world, center.getX() + 2, center.getZ() - 2);
		y += FeatureHelper.getYOnSurfaceWG(world, center.getX() - 2, center.getZ() + 2);
		y += FeatureHelper.getYOnSurfaceWG(world, center.getX() + 2, center.getZ() + 2);
		return y / 5;
	}
	
	@Override
	public boolean place(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos center,
			NoFeatureConfig config) 
	{
		center = new BlockPos(((center.getX() >> 4) << 4) | 8, 128, ((center.getZ() >> 4) << 4) | 8);
		center = getGround(world, center);
		
		if (!canSpawn(world, center, rand)) 
		{
			return false;
		}
		
		int posY = center.getY() + 1;
		
		Template structure = getStructure(world, center, rand);
		Rotation rotation = getRotation(world, center, rand);
		Mirror mirror = getMirror(world, center, rand);
		BlockPos offset = Template.transform(structure.getSize(), mirror, rotation, BlockPos.ZERO);
		center = center.offset(0, getYOffset(structure, world, center, rand) + 0.5, 0);
		
		MutableBoundingBox bounds = makeBox(center);
		PlacementSettings placementData = new PlacementSettings().setRotation(rotation).setMirror(mirror).setBoundingBox(bounds);
		addStructureData(placementData);
		center = center.offset(-offset.getX() * 0.5, 0, -offset.getZ() * 0.5);
		structure.placeInWorld(world, center, placementData, rand);
		
		TerrainMerge merge = getTerrainMerge(world, center, rand);
		int x1 = center.getX();
		int z1 = center.getZ();
		int x2 = x1 + offset.getX();
		int z2 = z1 + offset.getZ();
		if (merge != TerrainMerge.NONE) 
		{
			Mutable mut = new Mutable();
			
			if (x2 < x1) 
			{
				int a = x1;
				x1 = x2;
				x2 = a;
			}
			
			if (z2 < z1) 
			{
				int a = z1;
				z1 = z2;
				z2 = a;
			}
			
			int surfMax = posY - 1;
			for (int x = x1; x <= x2; x++) 
			{
				mut.setX(x);
				for (int z = z1; z <= z2; z++) 
				{
					mut.setZ(z);
					mut.setY(surfMax);
					BlockState state = world.getBlockState(mut);
					if (!state.is(ModTags.GEN_TERRAIN) && state.isFaceSturdy(world, mut, Direction.DOWN)) 
					{
						for (int i = 0; i < 10; i++) 
						{
							mut.setY(mut.getY() - 1);
							BlockState stateSt = world.getBlockState(mut);
							if (!stateSt.is(ModTags.GEN_TERRAIN)) 
							{
								if (merge == TerrainMerge.SURFACE) 
								{
									ISurfaceBuilderConfig surfaceConfig = world.getBiome(mut).getGenerationSettings().getSurfaceBuilderConfig();
									boolean isTop = mut.getY() == surfMax && state.getMaterial().isSolidBlocking();
									BlockState top = isTop ? surfaceConfig.getTopMaterial() : surfaceConfig.getUnderMaterial();
									BlockHelper.setWithoutUpdate(world, mut, top);
								}
								else 
								{
									BlockHelper.setWithoutUpdate(world, mut, state);
								}
							}
							else 
							{
								if (stateSt.is(ModTags.END_GROUND) && state.getMaterial().isSolidBlocking()) 
								{
									if (merge == TerrainMerge.SURFACE)
									{
										ISurfaceBuilderConfig surfaceConfig = world.getBiome(mut).getGenerationSettings().getSurfaceBuilderConfig();
										BlockHelper.setWithoutUpdate(world, mut, surfaceConfig.getUnderMaterial());
									}
									else 
									{
										BlockHelper.setWithoutUpdate(world, mut, state);
									}
								}
								break;
							}
						}
					}
				}
			}
		}
		BlockHelper.fixBlocks(world, new BlockPos(x1, center.getY(), z1), new BlockPos(x2, center.getY() + offset.getY(), z2));
		
		return true;
	}

	protected MutableBoundingBox makeBox(BlockPos pos) 
	{
		int sx = ((pos.getX() >> 4) << 4) - 16;
		int sz = ((pos.getZ() >> 4) << 4) - 16;
		int ex = sx + 47;
		int ez = sz + 47;
		return MutableBoundingBox.createProper(sx, 0, sz, ex, 255, ez);
	}
	
	public static Template readStructure(String path, String replacePath) 
	{
		try 
		{
			InputStream inputstream = MinecraftServer.class.getResourceAsStream(path);
			return readStructureFromStream(inputstream, replacePath);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	protected static Template readStructure(ResourceLocation resource) 
	{
		String ns = resource.getNamespace();
		String nm = resource.getPath();

		try
		{
			InputStream inputstream = MinecraftServer.class.getResourceAsStream("/data/" + ns + "/structures/" + nm + ".nbt");
			return readStructureFromStream(inputstream, null);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		return null;
	}
	
	private static Template readStructureFromStream(InputStream stream, String replacePath) throws IOException 
	{
		CompoundNBT nbttagcompound = CompressedStreamTools.readCompressed(stream);
		
		NbtModIdReplacer.readAndReplace(nbttagcompound, replacePath);
		
		Template template = new Template();
		template.load(nbttagcompound);
		
		return template;
	}
	
	public static enum TerrainMerge 
	{
		NONE,
		SURFACE,
		OBJECT;
		
		public static TerrainMerge getFromString(String type) 
		{
			if (type.equals("surface")) 
			{
				return SURFACE;
			}
			else if (type.equals("object")) 
			{
				return OBJECT;
			}
			else 
			{
				return NONE;
			}
		}
	}
}
