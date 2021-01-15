package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlimeBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3i;

public class JellyshroomCapBlock extends SlimeBlock
{
	public static final IntegerProperty COLOR = IntegerProperty.create("color", 0, 7);
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(0);
	private final Vector3i colorStart;
	private final Vector3i colorEnd;
	private final int coloritem;
	
	public JellyshroomCapBlock(int r1, int g1, int b1, int r2, int g2, int b2) 
	{
		super(AbstractBlock.Properties.from(Blocks.SLIME_BLOCK));
		colorStart = new Vector3i(r1, g1, b1);
		colorEnd = new Vector3i(r2, g2, b2);
		coloritem = ModMathHelper.color((r1 + r2) >> 1, (g1 + g2) >> 1, (b1 + b2) >> 1);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		double px = context.getPos().getX() * 0.1;
		double py = context.getPos().getY() * 0.1;
		double pz = context.getPos().getZ() * 0.1;
		
		return this.getDefaultState().with(COLOR, ModMathHelper.floor(NOISE.eval(px, py, pz) * 3.5 + 4));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(COLOR);
	}

	public int getBlockColor(BlockState state) 
	{
		float delta = (float) state.get(COLOR) / 7F;
	    int r = MathHelper.floor(MathHelper.lerp(delta, colorStart.getX() / 255F, colorEnd.getX() / 255F) * 255F);
	    int g = MathHelper.floor(MathHelper.lerp(delta, colorStart.getY() / 255F, colorEnd.getY() / 255F) * 255F);
		int b = MathHelper.floor(MathHelper.lerp(delta, colorStart.getZ() / 255F, colorEnd.getZ() / 255F) * 255F);
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
