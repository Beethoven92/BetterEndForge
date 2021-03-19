package mod.beethoven92.betterendforge.common.block.template;

import java.util.Random;

import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EndCropBlock extends PlantBlock
{
	private static final VoxelShape SHAPE = Block.makeCuboidShape(2, 0, 2, 14, 14, 14);
	public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);
	
	private final Block[] terrain;
	
	public EndCropBlock(Properties properties, Block... terrain) 
	{
		super(properties);
		this.terrain = terrain;
		this.setDefaultState(getDefaultState().with(AGE, 0));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		return SHAPE;
	}
	
	@Override
	public OffsetType getOffsetType()
	{
		return OffsetType.NONE;
	}
	
	@Override
	protected boolean isTerrain(BlockState state) 
	{
		for (Block block: terrain) 
		{
			if (state.isIn(block)) 
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient)
	{
		return state.get(AGE) < 3;
	}
	
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return state.get(AGE) < 3;
	}
	
	@Override
	public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state)
	{
		if (rand.nextInt(8) == 0)
		{
			int age = state.get(AGE);
			if (age < 3) 
		    {
				BlockHelper.setWithUpdate(worldIn, pos, state.with(AGE, age + 1));
			}
		}
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) 
	{
		//super.randomTick(state, worldIn, pos, random);
		grow(worldIn, random, pos, state);
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		builder.add(AGE);
	}
}
