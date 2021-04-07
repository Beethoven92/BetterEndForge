package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.UnderwaterPlantBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class PondAnemoneBlock extends UnderwaterPlantBlock {
	private static final VoxelShape SHAPE = Block.makeCuboidShape(2, 0, 2, 14, 14, 14);

	public PondAnemoneBlock() {
		super(AbstractBlock.Properties.create(Material.OCEAN_PLANT).doesNotBlockMovement().zeroHardnessAndResistance()
				.setLightLevel(s -> 13));
	}

	@Override
	public AbstractBlock.OffsetType getOffsetType() {
		return AbstractBlock.OffsetType.NONE;
	}

	@Override
	public void animateTick(BlockState stateIn, World world, BlockPos pos, Random random) {
		double x = pos.getX() + random.nextDouble();
		double y = pos.getY() + random.nextDouble() * 0.5F + 0.5F;
		double z = pos.getZ() + random.nextDouble();
		world.addParticle(ParticleTypes.BUBBLE, x, y, z, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
		return false;
	}
}
