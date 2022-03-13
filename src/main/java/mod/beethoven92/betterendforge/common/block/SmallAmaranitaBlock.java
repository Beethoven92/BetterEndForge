package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SmallAmaranitaBlock extends PlantBlock {

	public SmallAmaranitaBlock() {
		super(AbstractBlock.Properties.of(Material.PLANT).sound(SoundType.WET_GRASS).noCollission()
				.instabreak());
	}

	private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 10, 12);

	@Override
	protected boolean isTerrain(BlockState state) {
		return state.is(ModBlocks.SANGNUM.get()) || state.is(ModBlocks.MOSSY_OBSIDIAN.get()) || state.is(ModBlocks.MOSSY_DRAGON_BONE.get());
	}

	@Override
	public void performBonemeal(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		BlockPos bigPos = growBig(world, pos);
		if (bigPos != null) {
			if (ModFeatures.GIGANTIC_AMARANITA.place(world, null, random, bigPos, null)) {
				replaceMushroom(world, bigPos);
				replaceMushroom(world, bigPos.south());
				replaceMushroom(world, bigPos.east());
				replaceMushroom(world, bigPos.south().east());
			}
			return;
		}
		ModFeatures.LARGE_AMARANITA.place(world, null, random, pos, null);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Vector3d vec3d = state.getOffset(worldIn, pos);
		return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
	}

	private BlockPos growBig(ServerWorld world, BlockPos pos) {
		for (int x = -1; x < 2; x++) {
			for (int z = -1; z < 2; z++) {
				BlockPos p = pos.offset(x, 0, z);
				if (checkFrame(world, p)) {
					return p;
				}
			}
		}
		return null;
	}

	private boolean checkFrame(ServerWorld world, BlockPos pos) {
		return world.getBlockState(pos).is(this) && world.getBlockState(pos.south()).is(this)
				&& world.getBlockState(pos.east()).is(this) && world.getBlockState(pos.south().east()).is(this);
	}

	private void replaceMushroom(ServerWorld world, BlockPos pos) {
		if (world.getBlockState(pos).is(this)) {
			BlockHelper.setWithUpdate(world, pos, Blocks.AIR);
		}
	}

	@Override
	public boolean isBonemealSuccess(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return rand.nextInt(8) == 0;
	}
}
