package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.core.Direction;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class UmbrellaTreeMembraneBlock extends SlimeBlock
{
	public static final IntegerProperty COLOR = IntegerProperty.create("color", 0, 7);
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(0);
	
	public UmbrellaTreeMembraneBlock(Properties properties)
	{
		super(properties);
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
	
	@Override
	public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) 
	{
		if (state.getValue(COLOR) > 0) 
		{
			return super.skipRendering(state, adjacentBlockState, side);
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public boolean useShapeForLightOcclusion(BlockState state) 
	{
		return state.getValue(COLOR) > 0;
	}
}
