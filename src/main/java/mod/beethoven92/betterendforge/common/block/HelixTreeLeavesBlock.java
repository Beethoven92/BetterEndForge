package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.MathHelper;

public class HelixTreeLeavesBlock extends Block {
	public static final IntegerProperty COLOR = IntegerProperty.create("color", 0, 7);
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(0);

	public HelixTreeLeavesBlock(AbstractBlock.Properties properties) {
		super(properties);
	}

	@Override
	   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(COLOR);
	}

	public static int getBlockColor(BlockState state) {
		return ModMathHelper.color(237, getGreen(state.get(COLOR)), 20);
	}

	public static int getItemColor() {
		return ModMathHelper.color(237, getGreen(4), 20);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext ctx) {
		double px = ctx.getPos().getX() * 0.1;
		double py = ctx.getPos().getY() * 0.1;
		double pz = ctx.getPos().getZ() * 0.1;
		return this.getDefaultState().with(COLOR, ModMathHelper.floor(NOISE.eval(px, py, pz) * 3.5 + 4));
	}

	private static int getGreen(int color) {
		float delta = color / 7F;
		return (int) MathHelper.lerp(delta, 80, 158);
	}
}
