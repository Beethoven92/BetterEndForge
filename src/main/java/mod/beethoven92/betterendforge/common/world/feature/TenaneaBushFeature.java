package mod.beethoven92.betterendforge.common.world.feature;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.Lists;
import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.FurBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale3D;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSubtraction;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFSphere;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class TenaneaBushFeature extends Feature<NoFeatureConfig>
{
	private static final Function<BlockState, Boolean> REPLACE;
	private static final Direction[] DIRECTIONS = Direction.values();
	
	static 
	{
		REPLACE = (state) -> {
			if (state.getMaterial().equals(Material.PLANT)) 
			{
				return true;
			}
			return state.getMaterial().isReplaceable();
		};
	}
	
	public TenaneaBushFeature() 
	{
		super(NoFeatureConfig.CODEC);
	}

	@Override
	public boolean place(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos,
			NoFeatureConfig config) 
	{
		if (!world.getBlockState(pos.below()).getBlock().is(ModTags.END_GROUND)) return false;
		
		float radius = ModMathHelper.randRange(1.8F, 3.5F, rand);
		OpenSimplexNoise noise = new OpenSimplexNoise(rand.nextInt());
		BlockState leaves = ModBlocks.TENANEA_LEAVES.get().defaultBlockState();
		SDF sphere = new SDFSphere().setRadius(radius).setBlock(leaves);
		sphere = new SDFScale3D().setScale(1, 0.75F, 1).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> { return (float) noise.eval(vec.x() * 0.2, vec.y() * 0.2, vec.z() * 0.2) * 3; }).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> { return ModMathHelper.randRange(-2F, 2F, rand); }).setSource(sphere);
		sphere = new SDFSubtraction().setSourceA(sphere).setSourceB(new SDFTranslate().setTranslate(0, -radius, 0).setSource(sphere));
		sphere.setReplaceFunction(REPLACE);
		List<BlockPos> support = Lists.newArrayList();
		sphere.addPostProcess((info) -> {
			if (info.getState().getBlock() instanceof LeavesBlock) 
			{
				int distance = info.getPos().distManhattan(pos);
				if (distance < 7) 
				{
					if (rand.nextInt(4) == 0 && info.getStateDown().isAir()) 
					{
						BlockPos d = info.getPos().below();
						support.add(d);
					}
					
					ModMathHelper.shuffle(DIRECTIONS, rand);
					for (Direction d: DIRECTIONS) 
					{
						if (info.getState(d).isAir()) 
						{
							info.setBlockPos(info.getPos().relative(d), ModBlocks.TENANEA_OUTER_LEAVES.get().defaultBlockState().setValue(FurBlock.FACING, d));
						}
					}
					
					return info.getState().setValue(LeavesBlock.DISTANCE, distance);
				}
				else 
				{
					return Blocks.AIR.defaultBlockState();
				}
			}
			return info.getState();
		});
		sphere.fillRecursive(world, pos);
		BlockState stem = ModBlocks.TENANEA.bark.get().defaultBlockState();
		BlockHelper.setWithoutUpdate(world, pos, stem);
		for (Direction d: Direction.values()) 
		{
			BlockPos p = pos.relative(d);
			if (world.isEmptyBlock(p)) 
			{
				BlockHelper.setWithoutUpdate(world, p, leaves.setValue(LeavesBlock.DISTANCE, 1));
			}
		}
		
		Mutable mut = new Mutable();
		BlockState top = ModBlocks.TENANEA_FLOWERS.get().defaultBlockState().setValue(BlockProperties.TRIPLE_SHAPE, TripleShape.TOP);
		BlockState middle = ModBlocks.TENANEA_FLOWERS.get().defaultBlockState().setValue(BlockProperties.TRIPLE_SHAPE, TripleShape.MIDDLE);
		BlockState bottom = ModBlocks.TENANEA_FLOWERS.get().defaultBlockState().setValue(BlockProperties.TRIPLE_SHAPE, TripleShape.BOTTOM);
		support.forEach((bpos) -> {
			BlockState state = world.getBlockState(bpos);
			if (state.isAir() || state.is(ModBlocks.TENANEA_OUTER_LEAVES.get())) 
			{
				int count = ModMathHelper.randRange(3, 8, rand);
				mut.set(bpos);
				if (world.getBlockState(mut.above()).is(ModBlocks.TENANEA_LEAVES.get())) 
				{
					BlockHelper.setWithoutUpdate(world, mut, top);
					for (int i = 1; i < count; i++) 
					{
						mut.setY(mut.getY() - 1);
						if (world.isEmptyBlock(mut.below())) 
						{
							BlockHelper.setWithoutUpdate(world, mut, middle);
						}
						else {
							break;
						}
					}
					BlockHelper.setWithoutUpdate(world, mut, bottom);
				}
			}
		});
		
		return true;
	}	
}
