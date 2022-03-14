package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

public class SmallAmaranitaBlock extends PlantBlock {

	public SmallAmaranitaBlock() {
		super(BlockBehaviour.Properties.of(Material.PLANT).sound(SoundType.WET_GRASS).noCollission()
				.instabreak());
	}

	private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 10, 12);

	@Override
	protected boolean isTerrain(BlockState state) {
		return state.is(ModBlocks.SANGNUM.get()) || state.is(ModBlocks.MOSSY_OBSIDIAN.get()) || state.is(ModBlocks.MOSSY_DRAGON_BONE.get());
	}

	@Override
	public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
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
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		Vec3 vec3d = state.getOffset(worldIn, pos);
		return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
	}

	private BlockPos growBig(ServerLevel world, BlockPos pos) {
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

	private boolean checkFrame(ServerLevel world, BlockPos pos) {
		return world.getBlockState(pos).is(this) && world.getBlockState(pos.south()).is(this)
				&& world.getBlockState(pos.east()).is(this) && world.getBlockState(pos.south().east()).is(this);
	}

	private void replaceMushroom(ServerLevel world, BlockPos pos) {
		if (world.getBlockState(pos).is(this)) {
			BlockHelper.setWithUpdate(world, pos, Blocks.AIR);
		}
	}

	@Override
	public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) {
		return rand.nextInt(8) == 0;
	}
}
