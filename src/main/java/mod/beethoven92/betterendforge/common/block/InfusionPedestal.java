package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PedestalBlock;
import mod.beethoven92.betterendforge.common.rituals.InfusionRitual;
import mod.beethoven92.betterendforge.common.tileentity.InfusionPedestalTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class InfusionPedestal extends PedestalBlock
{
	private static final VoxelShape SHAPE_DEFAULT;
	private static final VoxelShape SHAPE_PEDESTAL_TOP;
	
	static {
		VoxelShape basinUp = Block.box(2, 3, 2, 14, 4, 14);
		VoxelShape basinDown = Block.box(0, 0, 0, 16, 3, 16);
		VoxelShape pedestalTop = Block.box(1, 9, 1, 15, 11, 15);
		VoxelShape pedestalDefault = Block.box(1, 13, 1, 15, 15, 15);
		VoxelShape pillar = Block.box(3, 0, 3, 13, 9, 13);
		VoxelShape pillarDefault = Block.box(3, 4, 3, 13, 13, 13);
		VoxelShape eyeDefault = Block.box(4, 15, 4, 12, 16, 12);
		VoxelShape eyeTop = Block.box(4, 11, 4, 12, 12, 12);
		VoxelShape basin = Shapes.or(basinDown, basinUp);
		SHAPE_DEFAULT = Shapes.or(basin, pillarDefault, pedestalDefault, eyeDefault);
		SHAPE_PEDESTAL_TOP = Shapes.or(pillar, pedestalTop, eyeTop);
	}
	
	public InfusionPedestal(Properties properties) 
	{
		super(properties);
		this.height = 1.08F;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) 
	{
		if (state.is(this)) 
		{
			switch(state.getValue(STATE)) 
			{
				case PEDESTAL_TOP: 
				{
					return SHAPE_PEDESTAL_TOP;
				}
				case DEFAULT:
				{
					return SHAPE_DEFAULT;
				}
				default: 
				{
					return super.getShape(state, worldIn, pos, context);
				}
			}
		}
		return super.getShape(state, worldIn, pos, context);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player,
			InteractionHand handIn, BlockHitResult hit) 
	{
		if (worldIn.isClientSide || !state.is(this)) return InteractionResult.CONSUME;
		BlockEntity tileEntity = worldIn.getBlockEntity(pos);
		InfusionPedestalTileEntity pedestal = null;
		if (tileEntity instanceof InfusionPedestalTileEntity) 
		{
			pedestal = (InfusionPedestalTileEntity) tileEntity;
			if (!pedestal.isEmpty() && pedestal.hasRitual()) 
			{
				if (pedestal.getRitual().hasRecipe()) 
				{
					pedestal.getRitual().stop();
					return InteractionResult.SUCCESS;
				} 
				else if (pedestal.getRitual().checkRecipe()) 
				{
					return InteractionResult.SUCCESS;
				}
			}
		}
		
		InteractionResult result = InteractionResult.FAIL;
		if (handIn != null) 
		{
			result = super.use(state, worldIn, pos, player, handIn, hit);
		}
		
		if (result == InteractionResult.SUCCESS) 
		{
			if (pedestal != null) 
			{
				if (pedestal.hasRitual()) 
				{
					pedestal.getRitual().checkRecipe();
				} 
				else 
				{
					InfusionRitual ritual = new InfusionRitual(worldIn, pos);
					pedestal.linkRitual(ritual);
					ritual.checkRecipe();
				}
			}
		}
		return result;
	}
	
	/*@Override
	public void checkRitual(World world, BlockPos pos) 
	{
		TileEntity blockEntity = world.getTileEntity(pos);
		if (blockEntity instanceof InfusionPedestalTileEntity) 
		{
			InfusionPedestalTileEntity pedestal = (InfusionPedestalTileEntity) blockEntity;
			if (pedestal.hasRitual()) 
			{
				pedestal.getRitual().checkRecipe();
			} 
			else 
			{
				InfusionRitual ritual = new InfusionRitual(world, pos);
				pedestal.linkRitual(ritual);
				ritual.checkRecipe();
			}
		}
	}*/
	
	@Override
	public BlockEntity createTileEntity(BlockState state, BlockGetter world) 
	{
		return new InfusionPedestalTileEntity();
	}
}
