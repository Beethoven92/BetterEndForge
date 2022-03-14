package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.util.Mth;

public class HelixTreeLeavesBlock extends Block {
	public static final IntegerProperty COLOR = IntegerProperty.create("color", 0, 7);
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(0);

	public HelixTreeLeavesBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(COLOR);
	}

	public static int getBlockColor(BlockState state) {
		return ModMathHelper.color(237, getGreen(state.getValue(COLOR)), 20);
	}

	public static int getItemColor() {
		return ModMathHelper.color(237, getGreen(4), 20);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		double px = ctx.getClickedPos().getX() * 0.1;
		double py = ctx.getClickedPos().getY() * 0.1;
		double pz = ctx.getClickedPos().getZ() * 0.1;
		return this.defaultBlockState().setValue(COLOR, ModMathHelper.floor(NOISE.eval(px, py, pz) * 3.5 + 4));
	}

	private static int getGreen(int color) {
		float delta = color / 7F;
		return (int) Mth.lerp(delta, 80, 158);
	}
}
