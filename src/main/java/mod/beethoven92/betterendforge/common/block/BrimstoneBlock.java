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

public class BrimstoneBlock extends Block
{
	public static final BooleanProperty ACTIVATED = BlockProperties.ACTIVATED;
	
	public BrimstoneBlock(Properties properties)
	{
		super(properties);
		this.setDefaultState(getDefaultState().with(ACTIVATED, false));
	}

	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) 
	{
		boolean deactivate = true;
		for (Direction dir: BlockHelper.DIRECTIONS) 
		{
			if (worldIn.getFluidState(pos.offset(dir)).getFluid().equals(Fluids.WATER)) 
			{
				deactivate = false;
				break;
			}
		}
		if (state.get(ACTIVATED)) 
		{
			if (deactivate) 
			{
				worldIn.setBlockState(pos, getDefaultState().with(ACTIVATED, false));
			}
			else if (state.get(ACTIVATED) && random.nextInt(16) == 0) 
			{
				Direction dir = BlockHelper.randomDirection(random);
				BlockPos side = pos.offset(dir);
				BlockState sideState = worldIn.getBlockState(side);
				if (sideState.getBlock() instanceof SulphurCrystalBlock)
				{
					if (sideState.get(SulphurCrystalBlock.AGE) < 2 && sideState.get(SulphurCrystalBlock.WATERLOGGED)) 
					{
						int age = sideState.get(SulphurCrystalBlock.AGE) + 1;
						worldIn.setBlockState(side, sideState.with(SulphurCrystalBlock.AGE, age));
					}
				}
				else if (sideState.getFluidState().getFluid() == Fluids.WATER) 
				{
					BlockState crystal = ModBlocks.SULPHUR_CRYSTAL.get().getDefaultState()
							.with(SulphurCrystalBlock.FACING, dir)
							.with(SulphurCrystalBlock.WATERLOGGED, true)
							.with(SulphurCrystalBlock.AGE, 0);
					worldIn.setBlockState(side, crystal);
				}
			}
		}
		else if (!deactivate && !state.get(ACTIVATED)) 
		{
			worldIn.setBlockState(pos, getDefaultState().with(ACTIVATED, true));
		}
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder)
	{
		builder.add(ACTIVATED);
	}
}
