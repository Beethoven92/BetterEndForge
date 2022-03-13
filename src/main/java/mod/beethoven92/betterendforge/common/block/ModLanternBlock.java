package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;

import net.minecraft.block.AbstractBlock.Properties;

public class ModLanternBlock extends LanternBlock {
	private static final VoxelShape SHAPE_CEIL = Block.box(3, 1, 3, 13, 16, 13);
	private static final VoxelShape SHAPE_FLOOR = Block.box(3, 0, 3, 13, 15, 13);
	private static final Vector3i[] COLORS = AuroraCrystalBlock.COLORS;

	public ModLanternBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return state.getValue(LanternBlock.HANGING) ? SHAPE_CEIL : SHAPE_FLOOR;
	}

	public static int getBlockColor(BlockState state, IBlockDisplayReader world, BlockPos pos, int tintIndex) {
		long i = (long) pos.getX() + (long) pos.getY() + (long) pos.getZ();
		double delta = i * 0.1;
		int index = ModMathHelper.floor(delta);
		int index2 = (index + 1) & 3;
		delta -= index;
		index &= 3;

		Vector3i color1 = COLORS[index];
		Vector3i color2 = COLORS[index2];

		int r = ModMathHelper.floor(MathHelper.lerp(delta, color1.getX(), color2.getX()));
		int g = ModMathHelper.floor(MathHelper.lerp(delta, color1.getY(), color2.getY()));
		int b = ModMathHelper.floor(MathHelper.lerp(delta, color1.getZ(), color2.getZ()));

		return ModMathHelper.color(r, g, b);
	}

	public static int getItemColor(ItemStack stack, int tintIndex) {
		return ModMathHelper.color(COLORS[3].getX(), COLORS[3].getY(), COLORS[3].getZ());
	}

}
