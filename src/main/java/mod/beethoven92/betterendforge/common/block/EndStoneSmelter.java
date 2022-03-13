package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import mod.beethoven92.betterendforge.common.tileentity.EndStoneSmelterTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import net.minecraft.block.AbstractBlock.Properties;

public class EndStoneSmelter extends Block
{
	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	
	public EndStoneSmelter(Properties properties) 
	{
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) 
	{
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) 
	{
		if (stateIn.getValue(LIT)) 
		{
			double x = pos.getX() + 0.5D;
			double y = pos.getY();
			double z = pos.getZ() + 0.5D;
			if (rand.nextDouble() < 0.1D) 
			{
			   worldIn.playLocalSound(x, y, z, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			}

			Direction direction = (Direction)stateIn.getValue(FACING);
			Direction.Axis axis = direction.getAxis();
			double defOffset = rand.nextDouble() * 0.6D - 0.3D;
			double offX = axis == Direction.Axis.X ? direction.getStepX() * 0.52D : defOffset;
			double offY = rand.nextDouble() * 9.0D / 16.0D;
			double offZ = axis == Direction.Axis.Z ? direction.getStepZ() * 0.52D : defOffset;
			worldIn.addParticle(ParticleTypes.SMOKE, x + offX, y + offY, z + offZ, 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit)
	{
		if (worldIn.isClientSide) 
		{
			return ActionResultType.SUCCESS;
		} 
		else
		{
			this.interactWith(worldIn, pos, player);
			return ActionResultType.CONSUME;
		}
	}
	
	protected void interactWith(World worldIn, BlockPos pos, PlayerEntity player) 
	{
		TileEntity tileEntity = worldIn.getBlockEntity(pos);
		if (tileEntity instanceof EndStoneSmelterTileEntity) 
		{
			NetworkHooks.openGui((ServerPlayerEntity)player, (EndStoneSmelterTileEntity)tileEntity);
		}
	}
	
	@Override
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) 
	{
		if (!state.is(newState.getBlock())) 
		{
			TileEntity tileentity = worldIn.getBlockEntity(pos);
	        if (tileentity instanceof EndStoneSmelterTileEntity) 
	        {
	        	InventoryHelper.dropContents(worldIn, pos, (EndStoneSmelterTileEntity)tileentity);
	             //((EndStoneSmelterTileEntity)tileentity).grantStoredRecipeExperience(worldIn, Vector3d.copyCentered(pos));
	             worldIn.updateNeighbourForOutputSignal(pos, this);
	          }

	          super.onRemove(state, worldIn, pos, newState, isMoving);
	       }
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) 
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) 
	{
		return ModTileEntityTypes.END_STONE_SMELTER.get().create();
	}
	
	@Override
	public boolean hasAnalogOutputSignal(BlockState state) 
	{
		return true;
	}
	
	@Override
	public int getAnalogOutputSignal(BlockState blockState, World worldIn, BlockPos pos) 
	{
		return Container.getRedstoneSignalFromBlockEntity(worldIn.getBlockEntity(pos));
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rot) 
	{
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) 
	{
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		builder.add(FACING, LIT);
	}
}
