package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.util.Mth;
import net.minecraft.core.Vec3i;

public class JellyshroomCapBlock extends SlimeBlock
{
	public static final IntegerProperty COLOR = IntegerProperty.create("color", 0, 7);
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(0);
	private final Vec3i colorStart;
	private final Vec3i colorEnd;
	private final int coloritem;
	
	public JellyshroomCapBlock(int r1, int g1, int b1, int r2, int g2, int b2) 
	{
		super(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK));
		colorStart = new Vec3i(r1, g1, b1);
		colorEnd = new Vec3i(r2, g2, b2);
		coloritem = ModMathHelper.color((r1 + r2) >> 1, (g1 + g2) >> 1, (b1 + b2) >> 1);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) 
	{
		double px = context.getClickedPos().getX() * 0.1;
		double py = context.getClickedPos().getY() * 0.1;
		double pz = context.getClickedPos().getZ() * 0.1;
		
		return this.defaultBlockState().setValue(COLOR, ModMathHelper.floor(NOISE.eval(px, py, pz) * 3.5 + 4));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		builder.add(COLOR);
	}

	public int getBlockColor(BlockState state) 
	{
		float delta = (float) state.getValue(COLOR) / 7F;
	    int r = Mth.floor(Mth.lerp(delta, colorStart.getX() / 255F, colorEnd.getX() / 255F) * 255F);
	    int g = Mth.floor(Mth.lerp(delta, colorStart.getY() / 255F, colorEnd.getY() / 255F) * 255F);
		int b = Mth.floor(Mth.lerp(delta, colorStart.getZ() / 255F, colorEnd.getZ() / 255F) * 255F);
		return ModMathHelper.color(r, g, b);
	}
	
	public static int getItemColor(ItemStack stack) 
	{
		if (stack.getItem() instanceof BlockItem)
		{
			BlockItem blockItem = (BlockItem)stack.getItem();
			if (blockItem.getBlock() instanceof JellyshroomCapBlock)
			{
				JellyshroomCapBlock block = (JellyshroomCapBlock)blockItem.getBlock();
				return block.coloritem;
			}
		}
		return 0;
	}
}
