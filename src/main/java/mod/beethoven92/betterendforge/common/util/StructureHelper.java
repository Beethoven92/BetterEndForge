package mod.beethoven92.betterendforge.common.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Random;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.google.common.collect.Sets;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;

public class StructureHelper 
{
	private static final Direction[] DIR = BlockHelper.makeHorizontal();
	
	public static Template readStructure(ResourceLocation resource) 
	{
		String ns = resource.getNamespace();
		String nm = resource.getPath();
		return readStructure("/data/" + ns + "/structures/" + nm + ".nbt");
	}
	
	public static Template readStructure(File datapack, String path) 
	{
		if (datapack.isDirectory()) 
		{
			return readStructure(datapack.toString() + "/" + path);
		}
		else if (datapack.isFile() && datapack.getName().endsWith(".zip")) 
		{
			try 
			{
				ZipFile zipFile = new ZipFile(datapack);
				Enumeration<? extends ZipEntry> entries = zipFile.entries();
				while (entries.hasMoreElements()) 
				{
					ZipEntry entry = entries.nextElement();
					String name = entry.getName();
					long compressedSize = entry.getCompressedSize();
					long normalSize = entry.getSize();
					String type = entry.isDirectory() ? "DIR" : "FILE";

					System.out.println(name);
					System.out.format("\t %s - %d - %d\n", type, compressedSize, normalSize);
				}
				zipFile.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static Template readStructure(String path) 
	{
		try 
		{
			InputStream inputstream = StructureHelper.class.getResourceAsStream(path);
			return readStructureFromStream(inputstream);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private static Template readStructureFromStream(InputStream stream) throws IOException 
	{
		CompoundNBT nbttagcompound = CompressedStreamTools.readCompressed(stream);

		Template template = new Template();
		template.read(nbttagcompound);
		
		return template;
	}
	
	public static BlockPos offsetPos(BlockPos pos, Template structure, Rotation rotation, Mirror mirror) 
	{
		BlockPos offset = Template.getTransformedPos(structure.getSize(), mirror, rotation, BlockPos.ZERO);
		return pos.add(-offset.getX() * 0.5, 0, -offset.getZ() * 0.5);
	}
	
	public static void placeCenteredBottom(ISeedReader world, BlockPos pos, Template structure, Rotation rotation, Mirror mirror, Random random) 
	{
		placeCenteredBottom(world, pos, structure, rotation, mirror, makeBox(pos), random);
	}
	
	public static void placeCenteredBottom(ISeedReader world, BlockPos pos, Template structure, Rotation rotation, Mirror mirror, MutableBoundingBox bounds, Random random)
	{
		BlockPos offset = offsetPos(pos, structure, rotation, mirror);
		PlacementSettings placementData = new PlacementSettings().setRotation(rotation).setMirror(mirror).setBoundingBox(bounds);
		structure.func_237144_a_(world, offset, placementData, random);
	}
	
	private static MutableBoundingBox makeBox(BlockPos pos) 
	{
		int sx = ((pos.getX() >> 4) << 4) - 16;
		int sz = ((pos.getZ() >> 4) << 4) - 16;
		int ex = sx + 47;
		int ez = sz + 47;
		return MutableBoundingBox.createProper(sx, 0, sz, ex, 255, ez);
	}
	
	public static MutableBoundingBox getStructureBounds(BlockPos pos, Template structure, Rotation rotation, Mirror mirror) 
	{
		BlockPos max = structure.getSize();
		BlockPos min = Template.getTransformedPos(structure.getSize(), mirror, rotation, BlockPos.ZERO);
		max = max.subtract(min);
		return new MutableBoundingBox(min.add(pos), max.add(pos));
	}
	
	public static MutableBoundingBox intersectBoxes(MutableBoundingBox box1, MutableBoundingBox box2) 
	{
		int x1 = ModMathHelper.max(box1.minX, box2.minX);
		int y1 = ModMathHelper.max(box1.minY, box2.minY);
		int z1 = ModMathHelper.max(box1.minZ, box2.minZ);
		
		int x2 = ModMathHelper.min(box1.maxX, box2.maxX);
		int y2 = ModMathHelper.min(box1.maxY, box2.maxY);
		int z2 = ModMathHelper.min(box1.maxZ, box2.maxZ);
		
		return MutableBoundingBox.createProper(x1, y1, z1, x2, y2, z2);
	}
	
	public static void erode(ISeedReader world, MutableBoundingBox bounds, int iterations, Random random) 
	{
		Mutable mut = new Mutable();
		boolean canDestruct = true;
		for (int i = 0; i < iterations; i++) {
			for (int x = bounds.minX; x <= bounds.maxX; x++) 
			{
				mut.setX(x);
				for (int z = bounds.minZ; z <= bounds.maxZ; z++)
				{
					mut.setZ(z);
					for (int y = bounds.maxY; y >= bounds.minY; y--) 
					{
						mut.setY(y);
						BlockState state = world.getBlockState(mut);
						if (canDestruct && state.isIn(ModBlocks.FLAVOLITE_RUNED_ETERNAL.get()) && random.nextInt(8) == 0 && world.isAirBlock(mut.down(2)))
{
							int r = ModMathHelper.randRange(1, 4, random);
							int cx = mut.getX();
							int cy = mut.getY();
							int cz = mut.getZ();
							int x1 = cx - r;
							int y1 = cy - r;
							int z1 = cz - r;
							int x2 = cx + r;
							int y2 = cy + r;
							int z2 = cz + r;
							for (int px = x1; px <= x2; px++) 
							{
								int dx = px - cx;
								dx *= dx;
								mut.setX(px);
								for (int py = y1; py <= y2; py++) 
								{
									int dy = py - cy;
									dy *= dy;
									mut.setY(py);
									for (int pz = z1; pz <= z2; pz++) 
									{
										int dz = pz - cz;
										dz *= dz;
										mut.setZ(pz);
										if (dx + dy + dz <= r && world.getBlockState(mut).isIn(ModBlocks.FLAVOLITE_RUNED_ETERNAL.get()))
										{
											BlockHelper.setWithoutUpdate(world, mut, Blocks.AIR);
										}
									}
								}
							}
							mut.setX(cx);
							mut.setY(cy);
							mut.setZ(cz);
							canDestruct = false;
							continue;
						}
						else if (ignore(state)) 
						{
							continue;
						}
						if (!state.isAir() && random.nextBoolean()) 
						{
							shuffle(random);
							for (Direction dir: DIR) 
							{
								if (world.isAirBlock(mut.offset(dir)) && world.isAirBlock(mut.down().offset(dir))) 
								{
									BlockHelper.setWithoutUpdate(world, mut, Blocks.AIR);
									mut.move(dir).move(Direction.DOWN);
									for (int py = mut.getY(); y >= bounds.minY - 10; y--) 
									{
										mut.setY(py - 1);
										if (!world.isAirBlock(mut)) {
											mut.setY(py);
											BlockHelper.setWithoutUpdate(world, mut, state);
											break;
										}
									}
								}
							}
							break;
						}
						else if (random.nextInt(8) == 0 && !world.getBlockState(mut.up()).isIn(ModBlocks.ETERNAL_PEDESTAL.get())) 
						{
							BlockHelper.setWithoutUpdate(world, mut, Blocks.AIR);
						}
					}
				}
			}
		}
		for (int x = bounds.minX; x <= bounds.maxX; x++) 
		{
			mut.setX(x);
			for (int z = bounds.minZ; z <= bounds.maxZ; z++) {
				
				mut.setZ(z);
				for (int y = bounds.maxY; y >= bounds.minY; y--) 
				{
					mut.setY(y);
					BlockState state = world.getBlockState(mut);
					if (!ignore(state) && world.isAirBlock(mut.down())) 
					{
						BlockHelper.setWithoutUpdate(world, mut, Blocks.AIR);
						for (int py = mut.getY(); py >= bounds.minY - 10; py--) 
						{
							mut.setY(py - 1);
							if (!world.isAirBlock(mut)) 
							{
								mut.setY(py);
								BlockHelper.setWithoutUpdate(world, mut, state);
								break;
							}
						}
					}
				}
			}
		}
	}

	public static void erodeIntense(ISeedReader world, MutableBoundingBox bounds, Random random) 
	{
		Mutable mut = new Mutable();
		Mutable mut2 = new Mutable();
		int minY = bounds.minY - 10;
		for (int x = bounds.minX; x <= bounds.maxX; x++) 
		{
			mut.setX(x);
			for (int z = bounds.minZ; z <= bounds.maxZ; z++) 
			{
				mut.setZ(z);
				for (int y = bounds.maxY; y >= bounds.minY; y--) 
				{
					mut.setY(y);
					BlockState state = world.getBlockState(mut);
					if (!ignore(state)) {
						if (random.nextInt(6) == 0) 
						{
							BlockHelper.setWithoutUpdate(world, mut, Blocks.AIR);
							if (random.nextBoolean()) 
							{
								int px = ModMathHelper.floor(random.nextGaussian() * 2 + x + 0.5);
								int pz = ModMathHelper.floor(random.nextGaussian() * 2 + z + 0.5);
								mut2.setPos(px, y, pz);
								while (world.getBlockState(mut2).getMaterial().isReplaceable() && mut2.getY() > minY) 
								{
									mut2.setY(mut2.getY() - 1);
								}
								if (!world.getBlockState(mut2).isAir() && state.isValidPosition(world, mut2))
								{
									mut2.setY(mut2.getY() + 1);
									BlockHelper.setWithoutUpdate(world, mut2, state);
								}
							}
						}
						else if (random.nextInt(8) == 0 && !world.getBlockState(mut.up()).isIn(ModBlocks.ETERNAL_PEDESTAL.get())) 
						{
							BlockHelper.setWithoutUpdate(world, mut, Blocks.AIR);
						}
					}
				}
			}
		}

		drop(world, bounds);
	}
	
	private static boolean isTerrainNear(ISeedReader world, BlockPos pos) 
	{
		for (Direction dir: BlockHelper.HORIZONTAL_DIRECTIONS) 
		{
			if (world.getBlockState(pos.offset(dir)).isIn(ModTags.GEN_TERRAIN)) 
			{
				return true;
			}
		}
		return false;
	}
	
	private static void drop(ISeedReader world, MutableBoundingBox bounds) 
	{
		Mutable mut = new Mutable();
		
		Set<BlockPos> blocks = Sets.newHashSet();
		Set<BlockPos> edge = Sets.newHashSet();
		Set<BlockPos> add = Sets.newHashSet();
		
		for (int x = bounds.minX; x <= bounds.maxX; x++) 
		{
			mut.setX(x);
			for (int z = bounds.minZ; z <= bounds.maxZ; z++) 
			{
				mut.setZ(z);
				for (int y = bounds.minY; y <= bounds.maxY; y++) 
				{
					mut.setY(y);
					BlockState state = world.getBlockState(mut);
					if (!ignore(state) && isTerrainNear(world, mut)) 
					{
						edge.add(mut.toImmutable());
					}
				}
			}
		}
		
		if (edge.isEmpty()) 
		{
			return;
		}
		
		while (!edge.isEmpty()) {
			for (BlockPos center: edge) 
			{
				for (Direction dir: BlockHelper.HORIZONTAL_DIRECTIONS) 
				{
					BlockState state = world.getBlockState(center);
					if (state.isNormalCube(world, center)) 
					{
						mut.setPos(center.getX(), center.getY(), center.getZ()).move(dir);
						if (bounds.isVecInside(mut.toImmutable())) 
						{
							state = world.getBlockState(mut);
							if (!ignore(state) && !blocks.contains(mut)) 
							{
								add.add(mut.toImmutable());
							}
						}
					}
				}
			}
			
			blocks.addAll(edge);
			edge.clear();
			edge.addAll(add);
			add.clear();
		}
		
		int minY = bounds.minY - 10;
		for (int x = bounds.minX; x <= bounds.maxX; x++) 
		{
			mut.setX(x);
			for (int z = bounds.minZ; z <= bounds.maxZ; z++)
			{
				mut.setZ(z);
				for (int y = bounds.minY; y <= bounds.maxY; y++) 
				{
					mut.setY(y);
					BlockState state = world.getBlockState(mut);
					if (!ignore(state) && !blocks.contains(mut)) 
					{
						BlockHelper.setWithoutUpdate(world, mut, Blocks.AIR);
						while (world.getBlockState(mut).getMaterial().isReplaceable() && mut.getY() > minY) 
						{
							mut.setY(mut.getY() - 1);
						}
						if (mut.getY() > minY) 
						{
							mut.setY(mut.getY() + 1);
							BlockHelper.setWithoutUpdate(world, mut, state);
						}
					}
				}
			}
		}
	}

	private static boolean ignore(BlockState state) 
	{
		return state.getMaterial().isReplaceable()
				|| !state.getFluidState().isEmpty()
				|| state.isIn(ModTags.END_GROUND)
				|| state.isIn(ModBlocks.ETERNAL_PEDESTAL.get())
				|| state.isIn(ModBlocks.FLAVOLITE_RUNED_ETERNAL.get())
				|| state.isIn(BlockTags.LOGS)
				|| state.isIn(BlockTags.LEAVES)
				|| state.getMaterial().equals(Material.PLANTS)
		        || state.getMaterial().equals(Material.LEAVES);
	}
	
	private static void shuffle(Random random) 
	{
		for (int i = 0; i < 4; i++) 
		{
			int j = random.nextInt(4);
			Direction d = DIR[i];
			DIR[i] = DIR[j];
			DIR[j] = d;
		}
	}
	
	public static void cover(ISeedReader world, MutableBoundingBox bounds, Random random) 
	{
		Mutable mut = new Mutable();
		for (int x = bounds.minX; x <= bounds.maxX; x++) 
		{
			mut.setX(x);
			for (int z = bounds.minZ; z <= bounds.maxZ; z++) 
			{
				mut.setZ(z);
				BlockState top = world.getBiome(mut).getGenerationSettings().getSurfaceBuilderConfig().getTop();
				for (int y = bounds.maxY; y >= bounds.minY; y--) 
				{
					mut.setY(y);
					BlockState state = world.getBlockState(mut);
					if (state.isIn(ModTags.END_GROUND) && !world.getBlockState(mut.up()).getMaterial().isOpaque()) 
					{
						BlockHelper.setWithoutUpdate(world, mut, top);
					}
				}
			}
		}
	}
}
