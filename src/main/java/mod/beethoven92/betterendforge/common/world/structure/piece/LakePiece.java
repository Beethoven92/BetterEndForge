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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class LakePiece extends StructurePiece
{
	private static final BlockState ENDSTONE = Blocks.END_STONE.defaultBlockState();
	private static final BlockState AIR = Blocks.AIR.defaultBlockState();
	private static final BlockState WATER = Blocks.WATER.defaultBlockState();
	
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
	
	public LakePiece(StructureManager p_i50677_1_, CompoundTag nbt) 
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
	protected void addAdditionalSaveData(CompoundTag tagCompound) 
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
		this.boundingBox = new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	@Override
	public boolean postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator chunkGenerator,
			Random random, BoundingBox box, ChunkPos chunkPos, BlockPos blockPos) 
	{
		int minY = this.boundingBox.y0;
		int maxY = this.boundingBox.y1;
		int sx = chunkPos.x << 4;
		int sz = chunkPos.z << 4;
		MutableBlockPos mut = new MutableBlockPos();
		ChunkAccess chunk = world.getChunk(chunkPos.x, chunkPos.z);
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
							removePlant(chunk, mut.immutable().above());
						}
					}
					else if (dist <= r3 && mut.getY() < center.getY()) 
					{
						BlockState state = chunk.getBlockState(mut);
						BlockPos worldPos = mut.offset(sx, 0, sz);
						if (!state.isRedstoneConductor(world, worldPos) && !state.isSolidRender(world, worldPos)) 
						{
							state = chunk.getBlockState(mut.above());
							if (state.isAir()) 
							{
								state = random.nextBoolean() ? ENDSTONE : world.getBiome(worldPos).getGenerationSettings().getSurfaceBuilderConfig().getTopMaterial();
							}
							else 
							{
								state = state.getFluidState().isEmpty() ? ENDSTONE : ModBlocks.ENDSTONE_DUST.get().defaultBlockState();
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
	
	private boolean canReplace(ChunkAccess chunk, BlockPos pos, BlockState state) {
		if (state.is(ModTags.GEN_TERRAIN) || state.isAir())
			return true;
		Block b = state.getBlock();
		if (b instanceof PlantBlock && !(b instanceof SimpleWaterloggedBlock)) {
			removePlant(chunk, pos);
			return true;
		}
		return false;
	}
	
	private void removePlant(ChunkAccess chunk, BlockPos pos) {
		BlockPos p = new BlockPos(pos);
		while (chunk.getBlockState(p).getBlock() instanceof PlantBlock) {
			chunk.setBlockState(p, AIR, false);
			p = p.above();
		}
		p = new BlockPos(pos).below();
		while (chunk.getBlockState(p).getBlock() instanceof PlantBlock) {
			chunk.setBlockState(p, AIR, false);
			p = p.below();
		}
	}

	private void fixWater(WorldGenLevel world, ChunkAccess chunk, MutableBlockPos mut, Random random, int sx, int sz) 
	{
		int minY = this.boundingBox.y0;
		int maxY = this.boundingBox.y1;
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
								bState = random.nextBoolean() ? ENDSTONE : world.getBiome(mut.offset(sx, 0, sz)).getGenerationSettings().getSurfaceBuilderConfig().getTopMaterial();
							}
							else 
							{
								bState = bState.getFluidState().isEmpty() ? ENDSTONE : ModBlocks.ENDSTONE_DUST.get().defaultBlockState();
							}
							
							mut.setY(y);
							
							makeEndstonePillar(chunk, mut, bState);
						}
						else if (x > 1 && x < 15 && z > 1 && z < 15) 
						{
							mut.setY(y);
							for (Direction dir: BlockHelper.HORIZONTAL_DIRECTIONS) 
							{
								BlockPos wPos = mut.offset(dir.getStepX(), 0, dir.getStepZ());
								if (chunk.getBlockState(wPos).isAir()) 
								{
									mut.setY(y + 1);
									BlockState bState = chunk.getBlockState(mut);
									if (bState.isAir()) 
									{
										bState = random.nextBoolean() ? ENDSTONE : world.getBiome(mut.offset(sx, 0, sz)).getGenerationSettings().getSurfaceBuilderConfig().getTopMaterial();
									}
									else {
										bState = bState.getFluidState().isEmpty() ? ENDSTONE : ModBlocks.ENDSTONE_DUST.get().defaultBlockState();
									}
									mut.setY(y);
									makeEndstonePillar(chunk, mut, bState);
									break;
								}
							}
						}
						else if (chunk.getBlockState(mut.move(Direction.UP)).isAir()) 
						{
							chunk.getLiquidTicks().scheduleTick(mut.move(Direction.DOWN), state.getType(), 0);
						}
					}
				}
			}
		}
	}
	
	private void makeEndstonePillar(ChunkAccess chunk, MutableBlockPos mut, BlockState terrain) 
	{
		chunk.setBlockState(mut, terrain, false);
		mut.setY(mut.getY() - 1);
		while (!chunk.getFluidState(mut).isEmpty()) 
		{
			chunk.setBlockState(mut, ENDSTONE, false);
			mut.setY(mut.getY() - 1);
		}
	}
	
	private float getHeightClamp(WorldGenLevel world, int radius, int posX, int posZ) 
	{
		MutableBlockPos mut = new MutableBlockPos();
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
		return Mth.clamp(height, 0, 1);
	}
	
	private int getHeight(WorldGenLevel world, BlockPos pos) 
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
		
		h = world.getHeight(Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ());
		h = Mth.abs(h - center.getY());
		h = h < 8 ? 1 : 0;
		
		heightmap.put(p, (byte) h);
		return h;
	}
}
