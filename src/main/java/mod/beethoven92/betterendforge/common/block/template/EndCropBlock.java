package mod.beethoven92.betterendforge.common.block.template;

import java.util.Random;

import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class EndCropBlock extends PlantBlock
{
	private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 14, 14);
	public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 3);
	
	private final Block[] terrain;
	
	public EndCropBlock(Properties properties, Block... terrain) 
	{
		super(properties);
		this.terrain = terrain;
		this.registerDefaultState(defaultBlockState().setValue(AGE, 0));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) 
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
			if (state.is(block)) 
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient)
	{
		return state.getValue(AGE) < 3;
	}
	
	@Override
	public boolean isBonemealSuccess(Level worldIn, Random rand, BlockPos pos, BlockState state) 
	{
		return state.getValue(AGE) < 3;
	}
	
	@Override
	public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState state)
	{
		if (rand.nextInt(8) == 0)
		{
			int age = state.getValue(AGE);
			if (age < 3) 
		    {
				BlockHelper.setWithUpdate(worldIn, pos, state.setValue(AGE, age + 1));
			}
		}
	}
	
	@Override
	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) 
	{
		//super.randomTick(state, worldIn, pos, random);
		performBonemeal(worldIn, random, pos, state);
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		builder.add(AGE);
	}
}
