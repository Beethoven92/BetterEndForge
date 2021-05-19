package mod.beethoven92.betterendforge.common.world.structure.piece;

import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModStructurePieces;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class LakePiece extends StructurePiece
{
	private static final BlockState ENDSTONE = Blocks.END_STONE.getDefaultState();
	private static final BlockState AIR = Blocks.AIR.getDefaultState();
	private static final BlockState WATER = Blocks.WATER.getDefaultState();
	
	private Map<Integer, Byte> heightmap = Maps.newHashMap();
	
	private OpenSimplexNoise noise;
	private BlockPos center;
	private float radius;
	private float aspect;
	private float depth;
	private int seed;
	
	private ResourceLocation biomeID;

	
	public LakePiece(BlockPos center, float radius, float depth, Random random, Biome biome) 
	{
		super(ModStructurePieces.LAKE_PIECE, random.nextInt());
		this.center = center;
		this.radius = radius;
		this.depth = depth;
		this.seed = random.nextInt();
		this.noise = new OpenSimplexNoise(this.seed);
		this.aspect = radius / depth;
		this.biomeID = ModBiomes.getBiomeID(biome);
		makeBoundingBox();
	}
	
	public LakePiece(TemplateManager p_i50677_1_, CompoundNBT nbt) 
	{
		super(ModStructurePieces.LAKE_PIECE, nbt);
        // READ STRUCTURE DATA
        this.center = new BlockPos(nbt.getInt("centerX"), nbt.getInt("centerY"), nbt.getInt("centerZ"));
        this.radius = nbt.getFloat("radius");
        this.depth = nbt.getFloat("depth");
		seed = nbt.getInt("seed");
		noise = new OpenSimplexNoise(seed);
		aspect = radius / depth;
        this.biomeID = new ResourceLocation(nbt.getString("biomeid"));
		makeBoundingBox();
	}

	@Override
	protected void readAdditional(CompoundNBT tagCompound) 
	{
		tagCompound.putInt("centerX", this.center.getX());
	    tagCompound.putInt("centerY", this.center.getY());
	    tagCompound.putInt("centerZ", this.center.getZ());
	    tagCompound.putFloat("radius", this.radius);
	    tagCompound.putFloat("depth", this.depth);
	    tagCompound.putInt("seed", seed);
		tagCompound.putString("biomeid", this.biomeID.toString());
	}
	
	private void makeBoundingBox() 
	{
		int minX = ModMathHelper.floor(center.getX() - radius - 8);
		int minY = ModMathHelper.floor(center.getY() - depth - 8);
		int minZ = ModMathHelper.floor(center.getZ() - radius - 8);
		int maxX = ModMathHelper.floor(center.getX() + radius + 8);
		int maxY = ModMathHelper.floor(center.getY() + depth);
		int maxZ = ModMathHelper.floor(center.getZ() + radius + 8);
		this.boundingBox = new MutableBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	@Override
	public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator chunkGenerator,
			Random random, MutableBoundingBox box, ChunkPos chunkPos, BlockPos blockPos) 
	{
		int minY = this.boundingBox.minY;
		int maxY = this.boundingBox.maxY;
		int sx = chunkPos.x << 4;
		int sz = chunkPos.z << 4;
		Mutable mut = new Mutable();
		IChunk chunk = world.getChunk(chunkPos.x, chunkPos.z);
		for (int x = 0; x < 16; x++) 
		{
			mut.setX(x);
			int wx = x | sx;
			double nx = wx * 0.1;
			int x2 = wx - center.getX();
			for (int z = 0; z < 16; z++) 
			{
				mut.setZ(z);
				int wz = z | sz;
				double nz = wz * 0.1;
				int z2 = wz - center.getZ();
				float clamp = getHeightClamp(world, 8, wx, wz);
				if (clamp < 0.01) continue;
				
				double n = noise.eval(nx, nz) * 1.5 + 1.5;
				double x3 = ModMathHelper.pow2(x2 + noise.eval(nx, nz, 100) * 10);
				double z3 = ModMathHelper.pow2(z2 + noise.eval(nx, nz, -100) * 10);
				
				for (int y = minY; y <= maxY; y++) 
				{
					mut.setY((int) (y + n));
					double y2 = ModMathHelper.pow2((y - center.getY()) * aspect);
					double r2 = radius * clamp;
					double r3 = r2 + 8;
					r2 *= r2;
					r3 = r3 * r3 + 100;
					double dist = x3 + y2 + z3;
					if (dist < r2) 
					{
						BlockState state = chunk.getBlockState(mut);
						if (canReplace(chunk, mut, state)) 
						{
							state = mut.getY() < center.getY() ? WATER : AIR;
							chunk.setBlockState(mut, state, false);
							removePlant(chunk, mut.toImmutable().up());
						}
					}
					else if (dist <= r3 && mut.getY() < center.getY()) 
					{
						BlockState state = chunk.getBlockState(mut);
						BlockPos worldPos = mut.add(sx, 0, sz);
						if (!state.isNormalCube(world, worldPos) && !state.isOpaqueCube(world, worldPos)) 
						{
							state = chunk.getBlockState(mut.up());
							if (state.isAir()) 
							{
								state = random.nextBoolean() ? ENDSTONE : world.getBiome(worldPos).getGenerationSettings().getSurfaceBuilderConfig().getTop();
							}
							else 
							{
								state = state.getFluidState().isEmpty() ? ENDSTONE : ModBlocks.ENDSTONE_DUST.get().getDefaultState();
							}
							chunk.setBlockState(mut, state, false);
						}
					}
				}
			}
		}
		
		fixWater(world, chunk, mut, random, sx, sz);
		return true;
	}
	
	private boolean canReplace(IChunk chunk, BlockPos pos, BlockState state) {
		if (state.isIn(ModTags.GEN_TERRAIN) || state.isAir())
			return true;
		Block b = state.getBlock();
		if (b instanceof PlantBlock && !(b instanceof IWaterLoggable)) {
			removePlant(chunk, pos);
			return true;
		}
		return false;
	}
	
	private void removePlant(IChunk chunk, BlockPos pos) {
		BlockPos p = new BlockPos(pos);
		while (chunk.getBlockState(p).getBlock() instanceof PlantBlock) {
			chunk.setBlockState(p, AIR, false);
			p = p.up();
		}
		p = new BlockPos(pos).down();
		while (chunk.getBlockState(p).getBlock() instanceof PlantBlock) {
			chunk.setBlockState(p, AIR, false);
			p = p.down();
		}
	}

	private void fixWater(ISeedReader world, IChunk chunk, Mutable mut, Random random, int sx, int sz) 
	{
		int minY = this.boundingBox.minY;
		int maxY = this.boundingBox.maxY;
		for (int x = 0; x < 16; x++) 
		{
			mut.setX(x);
			for (int z = 0; z < 16; z++) 
			{
				mut.setZ(z);
				for (int y = minY; y <= maxY; y++) 
				{
					mut.setY(y);
					FluidState state = chunk.getFluidState(mut);
					if (!state.isEmpty()) 
					{
						mut.setY(y - 1);
						if (chunk.getBlockState(mut).isAir()) 
						{
							mut.setY(y + 1);
							
							BlockState bState = chunk.getBlockState(mut);
							if (bState.isAir()) 
							{
								bState = random.nextBoolean() ? ENDSTONE : world.getBiome(mut.add(sx, 0, sz)).getGenerationSettings().getSurfaceBuilderConfig().getTop();
							}
							else 
							{
								bState = bState.getFluidState().isEmpty() ? ENDSTONE : ModBlocks.ENDSTONE_DUST.get().getDefaultState();
							}
							
							mut.setY(y);
							
							makeEndstonePillar(chunk, mut, bState);
						}
						else if (x > 1 && x < 15 && z > 1 && z < 15) 
						{
							mut.setY(y);
							for (Direction dir: BlockHelper.HORIZONTAL_DIRECTIONS) 
							{
								BlockPos wPos = mut.add(dir.getXOffset(), 0, dir.getZOffset());
								if (chunk.getBlockState(wPos).isAir()) 
								{
									mut.setY(y + 1);
									BlockState bState = chunk.getBlockState(mut);
									if (bState.isAir()) 
									{
										bState = random.nextBoolean() ? ENDSTONE : world.getBiome(mut.add(sx, 0, sz)).getGenerationSettings().getSurfaceBuilderConfig().getTop();
									}
									else {
										bState = bState.getFluidState().isEmpty() ? ENDSTONE : ModBlocks.ENDSTONE_DUST.get().getDefaultState();
									}
									mut.setY(y);
									makeEndstonePillar(chunk, mut, bState);
									break;
								}
							}
						}
						else if (chunk.getBlockState(mut.move(Direction.UP)).isAir()) 
						{
							chunk.getFluidsToBeTicked().scheduleTick(mut.move(Direction.DOWN), state.getFluid(), 0);
						}
					}
				}
			}
		}
	}
	
	private void makeEndstonePillar(IChunk chunk, Mutable mut, BlockState terrain) 
	{
		chunk.setBlockState(mut, terrain, false);
		mut.setY(mut.getY() - 1);
		while (!chunk.getFluidState(mut).isEmpty()) 
		{
			chunk.setBlockState(mut, ENDSTONE, false);
			mut.setY(mut.getY() - 1);
		}
	}
	
	private float getHeightClamp(ISeedReader world, int radius, int posX, int posZ) 
	{
		Mutable mut = new Mutable();
		int r2 = radius * radius;
		float height = 0;
		float max = 0;
		for (int x = -radius; x <= radius; x++) 
		{
			mut.setX(posX + x);
			int x2 = x * x;
			for (int z = -radius; z <= radius; z++)
			{
				mut.setZ(posZ + z);
				int z2 = z * z;
				if (x2 + z2 < r2) 
				{
					float mult = 1 - (float) Math.sqrt(x2 + z2) / radius;
					max += mult;
					height += getHeight(world, mut) * mult;
				}
			}
		}
		height /= max;
		return MathHelper.clamp(height, 0, 1);
	}
	
	private int getHeight(ISeedReader world, BlockPos pos) 
	{
		int p = ((pos.getX() & 2047) << 11) | (pos.getZ() & 2047);
		int h = heightmap.getOrDefault(p, Byte.MIN_VALUE);
		if (h > Byte.MIN_VALUE) 
		{
			return h;
		}
		
		if (!ModBiomes.getBiomeID(world.getBiome(pos)).equals(biomeID)) 
		{
			heightmap.put(p, (byte) 0);
			return 0;
		}
		
		h = world.getHeight(Type.WORLD_SURFACE_WG, pos.getX(), pos.getZ());
		h = MathHelper.abs(h - center.getY());
		h = h < 8 ? 1 : 0;
		
		heightmap.put(p, (byte) h);
		return h;
	}
}
