package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PedestalBlock;
import mod.beethoven92.betterendforge.common.rituals.InfusionRitual;
import mod.beethoven92.betterendforge.common.tileentity.InfusionPedestalTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class InfusionPedestal extends PedestalBlock
{
	private static final VoxelShape SHAPE_DEFAULT;
	private static final VoxelShape SHAPE_PEDESTAL_TOP;
	
	static {
		VoxelShape basinUp = Block.makeCuboidShape(2, 3, 2, 14, 4, 14);
		VoxelShape basinDown = Block.makeCuboidShape(0, 0, 0, 16, 3, 16);
		VoxelShape pedestalTop = Block.makeCuboidShape(1, 9, 1, 15, 11, 15);
		VoxelShape pedestalDefault = Block.makeCuboidShape(1, 13, 1, 15, 15, 15);
		VoxelShape pillar = Block.makeCuboidShape(3, 0, 3, 13, 9, 13);
		VoxelShape pillarDefault = Block.makeCuboidShape(3, 4, 3, 13, 13, 13);
		VoxelShape eyeDefault = Block.makeCuboidShape(4, 15, 4, 12, 16, 12);
		VoxelShape eyeTop = Block.makeCuboidShape(4, 11, 4, 12, 12, 12);
		VoxelShape basin = VoxelShapes.or(basinDown, basinUp);
		SHAPE_DEFAULT = VoxelShapes.or(basin, pillarDefault, pedestalDefault, eyeDefault);
		SHAPE_PEDESTAL_TOP = VoxelShapes.or(pillar, pedestalTop, eyeTop);
	}
	
	public InfusionPedestal(Properties properties) 
	{
		super(properties);
		this.height = 1.08F;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) 
	{
		if (state.isIn(this)) 
		{
			switch(state.get(STATE)) 
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
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) 
	{
		if (worldIn.isRemote || !state.isIn(this)) return ActionResultType.CONSUME;
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		InfusionPedestalTileEntity pedestal = null;
		if (tileEntity instanceof InfusionPedestalTileEntity) 
		{
			pedestal = (InfusionPedestalTileEntity) tileEntity;
			if (!pedestal.isEmpty() && pedestal.hasRitual()) 
			{
				if (pedestal.getRitual().hasRecipe()) 
				{
					pedestal.getRitual().stop();
					return ActionResultType.SUCCESS;
				} 
				else if (pedestal.getRitual().checkRecipe()) 
				{
					return ActionResultType.SUCCESS;
				}
			}
		}
		
		ActionResultType result = ActionResultType.FAIL;
		if (handIn != null) 
		{
			result = super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
		}
		
		if (result == ActionResultType.SUCCESS) 
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
	public TileEntity createTileEntity(BlockState state, IBlockReader world) 
	{
		return new InfusionPedestalTileEntity();
	}
}
