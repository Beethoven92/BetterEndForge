package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import net.minecraft.block.AbstractBlock.Properties;

public class BrimstoneBlock extends Block
{
	public static final BooleanProperty ACTIVATED = BlockProperties.ACTIVATED;
	
	public BrimstoneBlock(Properties properties)
	{
		super(properties);
		this.registerDefaultState(defaultBlockState().setValue(ACTIVATED, false));
	}

	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) 
	{
		boolean deactivate = true;
		for (Direction dir: BlockHelper.DIRECTIONS) 
		{
			if (worldIn.getFluidState(pos.relative(dir)).getType().equals(Fluids.WATER)) 
			{
				deactivate = false;
				break;
			}
		}
		if (state.getValue(ACTIVATED)) 
		{
			if (deactivate) 
			{
				worldIn.setBlockAndUpdate(pos, defaultBlockState().setValue(ACTIVATED, false));
			}
			else if (state.getValue(ACTIVATED) && random.nextInt(16) == 0) 
			{
				Direction dir = BlockHelper.randomDirection(random);
				BlockPos side = pos.relative(dir);
				BlockState sideState = worldIn.getBlockState(side);
				if (sideState.getBlock() instanceof SulphurCrystalBlock)
				{
					if (sideState.getValue(SulphurCrystalBlock.AGE) < 2 && sideState.getValue(SulphurCrystalBlock.WATERLOGGED)) 
					{
						int age = sideState.getValue(SulphurCrystalBlock.AGE) + 1;
						worldIn.setBlockAndUpdate(side, sideState.setValue(SulphurCrystalBlock.AGE, age));
					}
				}
				else if (sideState.getFluidState().getType() == Fluids.WATER) 
				{
					BlockState crystal = ModBlocks.SULPHUR_CRYSTAL.get().defaultBlockState()
							.setValue(SulphurCrystalBlock.FACING, dir)
							.setValue(SulphurCrystalBlock.WATERLOGGED, true)
							.setValue(SulphurCrystalBlock.AGE, 0);
					worldIn.setBlockAndUpdate(side, crystal);
				}
			}
		}
		else if (!deactivate && !state.getValue(ACTIVATED)) 
		{
			worldIn.setBlockAndUpdate(pos, defaultBlockState().setValue(ACTIVATED, true));
		}
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(ACTIVATED);
	}
}
