package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.StructureHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class CrashedShipFeature extends NBTFeature
{
	private static final StructureProcessor REPLACER;
	private static final String STRUCTURE_PATH = "/data/minecraft/structures/end_city/ship.nbt";
	private StructureTemplate structure;
	
	static 
	{
		REPLACER = new StructureProcessor() 
		{
			@Override
			public StructureTemplate.StructureBlockInfo process(LevelReader worldView, BlockPos pos, BlockPos blockPos, 
					StructureTemplate.StructureBlockInfo structureBlockInfo, StructureTemplate.StructureBlockInfo structureBlockInfo2, 
					StructurePlaceSettings structurePlacementData, StructureTemplate template) 
			{
				BlockState state = structureBlockInfo2.state;
				if (state.is(Blocks.SPAWNER) || state.getMaterial().equals(Material.WOOL)) 
				{
					return new StructureTemplate.StructureBlockInfo(structureBlockInfo2.pos, Blocks.AIR.defaultBlockState(), null);
				}
				return structureBlockInfo2;
			}

			@Override
			protected StructureProcessorType<?> getType() 
			{
				return StructureProcessorType.NOP;
			}
		};
	}
	
	@Override
	protected StructureTemplate getStructure(WorldGenLevel world, BlockPos pos, Random random) 
	{
		if (structure == null) 
		{
			structure = world.getLevel().getStructureManager().get(new ResourceLocation("end_city/ship"));
			if (structure == null) 
			{
				structure = StructureHelper.readStructure(STRUCTURE_PATH);
			}
		}
		return structure;
	}

	@Override
	protected boolean canSpawn(WorldGenLevel world, BlockPos pos, Random random)
	{
		return pos.getY() > 58 && world.getBlockState(pos.below()).is(ModTags.GEN_TERRAIN);
	}

	@Override
	protected Rotation getRotation(WorldGenLevel world, BlockPos pos, Random random) 
	{
		return Rotation.getRandom(random);
	}

	@Override
	protected Mirror getMirror(WorldGenLevel world, BlockPos pos, Random random) 
	{
		return Mirror.values()[random.nextInt(3)];
	}

	@Override
	protected int getYOffset(StructureTemplate structure, WorldGenLevel world, BlockPos pos, Random random) 
	{
		int min = structure.getSize().getY() >> 3;
		int max = structure.getSize().getY() >> 2;
		return -ModMathHelper.randRange(min, max, random);
	}

	@Override
	protected TerrainMerge getTerrainMerge(WorldGenLevel world, BlockPos pos, Random random) 
	{
		return TerrainMerge.NONE;
	}

	@Override
	protected void addStructureData(StructurePlaceSettings data) 
	{
		data.addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR).addProcessor(REPLACER).setIgnoreEntities(true);
	}
	
	@Override
	public boolean place(WorldGenLevel world, ChunkGenerator generator, Random rand, BlockPos center,
			NoneFeatureConfiguration config) 
	{
		center = new BlockPos(((center.getX() >> 4) << 4) | 8, 128, ((center.getZ() >> 4) << 4) | 8);
		center = getGround(world, center);
		BoundingBox bounds = makeBox(center);
		
		if (!canSpawn(world, center, rand)) 
		{
			return false;
		}
		
		StructureTemplate structure = getStructure(world, center, rand);
		Rotation rotation = getRotation(world, center, rand);
		Mirror mirror = getMirror(world, center, rand);
		BlockPos offset = StructureTemplate.transform(structure.getSize(), mirror, rotation, BlockPos.ZERO);
		center = center.offset(0, getYOffset(structure, world, center, rand) + 0.5, 0);
		StructurePlaceSettings placementData = new StructurePlaceSettings().setRotation(rotation).setMirror(mirror);
		center = center.offset(-offset.getX() * 0.5, 0, -offset.getZ() * 0.5);
		
		BoundingBox structB = structure.getBoundingBox(placementData, center);
		bounds = StructureHelper.intersectBoxes(bounds, structB);
		
		addStructureData(placementData);
		structure.placeInWorld(world, center, placementData.setBoundingBox(bounds), rand);
		
		StructureHelper.erodeIntense(world, bounds, rand);
		BlockHelper.fixBlocks(world, new BlockPos(bounds.x0, bounds.y0, bounds.z0), new BlockPos(bounds.x1, bounds.y1, bounds.z1));
		
		return true;
	}
	
}
