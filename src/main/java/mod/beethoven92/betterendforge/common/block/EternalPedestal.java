package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PedestalBlock;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import mod.beethoven92.betterendforge.common.rituals.EternalRitual;
import mod.beethoven92.betterendforge.common.teleporter.EndPortals;
import mod.beethoven92.betterendforge.common.tileentity.EternalPedestalTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

public class EternalPedestal extends PedestalBlock
{
	public static final BooleanProperty ACTIVATED = BlockProperties.ACTIVATED;
	
	public EternalPedestal(Properties properties) 
	{
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(ACTIVATED, false));
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit)
	{
		ActionResultType result = super.use(state, worldIn, pos, player, handIn, hit);
		if (result.equals(ActionResultType.SUCCESS)) 
		{
			TileEntity blockEntity = worldIn.getBlockEntity(pos);
			if (blockEntity instanceof EternalPedestalTileEntity) 
			{
				EternalPedestalTileEntity pedestal = (EternalPedestalTileEntity) blockEntity;
				BlockState updatedState = worldIn.getBlockState(pos);
				if (pedestal.isEmpty() && updatedState.getValue(ACTIVATED)) 
				{
					if (pedestal.hasRitual()) 
					{
						EternalRitual ritual = pedestal.getRitual();
						/////
						Item item = pedestal.getStack().getItem();
						int dim = EndPortals.getPortalState(Registry.ITEM.getKey(item));
						/////
						ritual.removePortal(dim);
					}
					worldIn.setBlockAndUpdate(pos, updatedState.setValue(ACTIVATED, false));
				} 
				else 
				{
					ItemStack itemStack = pedestal.getStack();
					ResourceLocation id = Registry.ITEM.getKey(itemStack.getItem());
					
					//if (itemStack.getItem() == ModItems.ETERNAL_CRYSTAL.get()) 
					if (EndPortals.isAvailableItem(id))
					{
						worldIn.setBlockAndUpdate(pos, updatedState.setValue(ACTIVATED, true));
						if (pedestal.hasRitual()) 
						{
							pedestal.getRitual().checkStructure();
						} 
						else 
						{
							EternalRitual ritual = new EternalRitual(worldIn, pos);
							ritual.checkStructure();
						}
					}
				}
			}
		}
		return result;
	}
	
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		BlockState updated = super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		if (!updated.is(this)) return updated;
		if (!this.isPlaceable(updated)) 
		{
			return updated.setValue(ACTIVATED, false);
		}
		return updated;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		super.createBlockStateDefinition(builder);
		builder.add(ACTIVATED);
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) 
	{
		return ModTileEntityTypes.ETERNAL_PEDESTAL.get().create();
	}
}
