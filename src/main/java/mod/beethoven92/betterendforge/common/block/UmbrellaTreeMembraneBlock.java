package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlimeBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;

public class UmbrellaTreeMembraneBlock extends SlimeBlock
{
	public static final IntegerProperty COLOR = IntegerProperty.create("color", 0, 7);
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(0);
	
	public UmbrellaTreeMembraneBlock(Properties properties)
	{
		super(properties);
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
	
	@Override
	public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) 
	{
		if (state.get(COLOR) > 0) 
		{
			return super.isSideInvisible(state, adjacentBlockState, side);
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public boolean isTransparent(BlockState state) 
	{
		return state.get(COLOR) > 0;
	}
}
