package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PedestalBlock;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import mod.beethoven92.betterendforge.common.rituals.EternalRitual;
import mod.beethoven92.betterendforge.common.teleporter.EndPortals;
import mod.beethoven92.betterendforge.common.tileentity.EternalPedestalTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.Registry;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class EternalPedestal extends PedestalBlock
{
	public static final BooleanProperty ACTIVATED = BlockProperties.ACTIVATED;
	
	public EternalPedestal(Properties properties) 
	{
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(ACTIVATED, false));
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player,
			InteractionHand handIn, BlockHitResult hit)
	{
		InteractionResult result = super.use(state, worldIn, pos, player, handIn, hit);
		if (result.equals(InteractionResult.SUCCESS)) 
		{
			BlockEntity blockEntity = worldIn.getBlockEntity(pos);
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
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
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
	public BlockEntity createTileEntity(BlockState state, BlockGetter world) 
	{
		return ModTileEntityTypes.ETERNAL_PEDESTAL.get().create();
	}
}
