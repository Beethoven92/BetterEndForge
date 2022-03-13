package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.StructureHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

public class CrashedShipFeature extends NBTFeature
{
	private static final StructureProcessor REPLACER;
	private static final String STRUCTURE_PATH = "/data/minecraft/structures/end_city/ship.nbt";
	private Template structure;
	
	static 
	{
		REPLACER = new StructureProcessor() 
		{
			@Override
			public Template.BlockInfo process(IWorldReader worldView, BlockPos pos, BlockPos blockPos, 
					Template.BlockInfo structureBlockInfo, Template.BlockInfo structureBlockInfo2, 
					PlacementSettings structurePlacementData, Template template) 
			{
				BlockState state = structureBlockInfo2.state;
				if (state.is(Blocks.SPAWNER) || state.getMaterial().equals(Material.WOOL)) 
				{
					return new Template.BlockInfo(structureBlockInfo2.pos, Blocks.AIR.defaultBlockState(), null);
				}
				return structureBlockInfo2;
			}

			@Override
			protected IStructureProcessorType<?> getType() 
			{
				return IStructureProcessorType.NOP;
			}
		};
	}
	
	@Override
	protected Template getStructure(ISeedReader world, BlockPos pos, Random random) 
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
	protected boolean canSpawn(ISeedReader world, BlockPos pos, Random random)
	{
		return pos.getY() > 58 && world.getBlockState(pos.below()).is(ModTags.GEN_TERRAIN);
	}

	@Override
	protected Rotation getRotation(ISeedReader world, BlockPos pos, Random random) 
	{
		return Rotation.getRandom(random);
	}

	@Override
	protected Mirror getMirror(ISeedReader world, BlockPos pos, Random random) 
	{
		return Mirror.values()[random.nextInt(3)];
	}

	@Override
	protected int getYOffset(Template structure, ISeedReader world, BlockPos pos, Random random) 
	{
		int min = structure.getSize().getY() >> 3;
		int max = structure.getSize().getY() >> 2;
		return -ModMathHelper.randRange(min, max, random);
	}

	@Override
	protected TerrainMerge getTerrainMerge(ISeedReader world, BlockPos pos, Random random) 
	{
		return TerrainMerge.NONE;
	}

	@Override
	protected void addStructureData(PlacementSettings data) 
	{
		data.addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_AND_AIR).addProcessor(REPLACER).setIgnoreEntities(true);
	}
	
	@Override
	public boolean place(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos center,
			NoFeatureConfig config) 
	{
		center = new BlockPos(((center.getX() >> 4) << 4) | 8, 128, ((center.getZ() >> 4) << 4) | 8);
		center = getGround(world, center);
		MutableBoundingBox bounds = makeBox(center);
		
		if (!canSpawn(world, center, rand)) 
		{
			return false;
		}
		
		Template structure = getStructure(world, center, rand);
		Rotation rotation = getRotation(world, center, rand);
		Mirror mirror = getMirror(world, center, rand);
		BlockPos offset = Template.transform(structure.getSize(), mirror, rotation, BlockPos.ZERO);
		center = center.offset(0, getYOffset(structure, world, center, rand) + 0.5, 0);
		PlacementSettings placementData = new PlacementSettings().setRotation(rotation).setMirror(mirror);
		center = center.offset(-offset.getX() * 0.5, 0, -offset.getZ() * 0.5);
		
		MutableBoundingBox structB = structure.getBoundingBox(placementData, center);
		bounds = StructureHelper.intersectBoxes(bounds, structB);
		
		addStructureData(placementData);
		structure.placeInWorld(world, center, placementData.setBoundingBox(bounds), rand);
		
		StructureHelper.erodeIntense(world, bounds, rand);
		BlockHelper.fixBlocks(world, new BlockPos(bounds.x0, bounds.y0, bounds.z0), new BlockPos(bounds.x1, bounds.y1, bounds.z1));
		
		return true;
	}
	
}
