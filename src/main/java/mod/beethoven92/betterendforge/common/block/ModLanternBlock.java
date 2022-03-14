package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Lantern;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ModLanternBlock extends Lantern {
	private static final VoxelShape SHAPE_CEIL = Block.box(3, 1, 3, 13, 16, 13);
	private static final VoxelShape SHAPE_FLOOR = Block.box(3, 0, 3, 13, 15, 13);
	private static final Vec3i[] COLORS = AuroraCrystalBlock.COLORS;

	public ModLanternBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return state.getValue(Lantern.HANGING) ? SHAPE_CEIL : SHAPE_FLOOR;
	}

	public static int getBlockColor(BlockState state, BlockAndTintGetter world, BlockPos pos, int tintIndex) {
		long i = (long) pos.getX() + (long) pos.getY() + (long) pos.getZ();
		double delta = i * 0.1;
		int index = ModMathHelper.floor(delta);
		int index2 = (index + 1) & 3;
		delta -= index;
		index &= 3;

		Vec3i color1 = COLORS[index];
		Vec3i color2 = COLORS[index2];

		int r = ModMathHelper.floor(Mth.lerp(delta, color1.getX(), color2.getX()));
		int g = ModMathHelper.floor(Mth.lerp(delta, color1.getY(), color2.getY()));
		int b = ModMathHelper.floor(Mth.lerp(delta, color1.getZ(), color2.getZ()));

		return ModMathHelper.color(r, g, b);
	}

	public static int getItemColor(ItemStack stack, int tintIndex) {
		return ModMathHelper.color(COLORS[3].getX(), COLORS[3].getY(), COLORS[3].getZ());
	}

}
