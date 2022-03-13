package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

public class UmbrellaTreeClusterBlock extends Block
{
	public static final BooleanProperty NATURAL = BlockProperties.NATURAL;
	
	public UmbrellaTreeClusterBlock(Properties properties) 
	{
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(NATURAL, false));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(NATURAL);
	}
	
	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) 
	{
		ItemStack stack = player.getMainHandItem();
		if (stack.getItem() == Items.GLASS_BOTTLE) 
		{
			if (!player.isCreative()) 
			{
				stack.shrink(1);
			}
			stack = new ItemStack(ModItems.UMBRELLA_CLUSTER_JUICE.get());
			player.addItem(stack);
			worldIn.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BOTTLE_FILL, SoundCategory.BLOCKS, 1, 1, false);
			BlockHelper.setWithUpdate(worldIn, pos, ModBlocks.UMBRELLA_TREE_CLUSTER_EMPTY.get().defaultBlockState().setValue(NATURAL, state.getValue(NATURAL)));
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.FAIL;
	}
}
