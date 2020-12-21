package mod.beethoven92.betterendforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusPlantBlock;
import net.minecraft.block.SixWayBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

@Mixin(value = ChorusPlantBlock.class, priority = 100)
public abstract class ChorusPlantBlockMixin extends SixWayBlock
{
	public ChorusPlantBlockMixin(float apothem, Properties properties)
	{
		super(apothem, properties);
	}
	
	@Inject(method = "<init>*", at = @At("TAIL"))
	private void init(AbstractBlock.Properties settings, CallbackInfo info) 
	{
		this.setDefaultState(this.getDefaultState().with(BlockHelper.ROOTS, false));
	}
	
	@Inject(method = "getStateForPlacement", at = @At("RETURN"), cancellable = true)
	private void getStateForPlacement(BlockItemUseContext context, CallbackInfoReturnable<BlockState> info) 
	{
		BlockPos pos = context.getPos();
		World world = context.getWorld();
		BlockState plant = info.getReturnValue();
		if (context.canPlace() && plant.isIn(Blocks.CHORUS_PLANT) && world.getBlockState(pos.down()).isIn(ModTags.END_GROUND))
		{
			info.setReturnValue(plant.with(BlockHelper.ROOTS, true).with(ChorusPlantBlock.DOWN, true));
			info.cancel();
		}
	}
	
	@Inject(method = "updatePostPlacement", at = @At("RETURN"), cancellable = true)
	private void updatePostPlacement(BlockState state, Direction direction, BlockState newState, IWorld world, 
			BlockPos pos, BlockPos posFrom, CallbackInfoReturnable<BlockState> info) 
	{
		BlockState plant = info.getReturnValue();
		if (plant.isIn(Blocks.CHORUS_PLANT)) 
		{
			if (world.getBlockState(pos.down()).isIn(ModTags.END_GROUND)) 
			{
				plant = plant.with(ChorusPlantBlock.DOWN, true).with(BlockHelper.ROOTS, true);
				info.cancel();
			}
			else 
			{
				plant = plant.with(BlockHelper.ROOTS, false);
				info.cancel();
			}
			info.setReturnValue(plant);
			info.cancel();
		}
	}
	
	@Inject(method = "makeConnections", at = @At("RETURN"), cancellable = true)
	private void beConnectionProperties(IBlockReader world, BlockPos pos, CallbackInfoReturnable<BlockState> info) 
	{
		BlockState plant = info.getReturnValue();
		if (plant.isIn(Blocks.CHORUS_PLANT)) 
		{
			if (world.getBlockState(pos.down()).isIn(ModTags.END_GROUND)) 
			{
				info.setReturnValue(plant.with(ChorusPlantBlock.DOWN, true).with(BlockHelper.ROOTS, true));
				info.cancel();
			}
			else 
			{
				info.setReturnValue(plant.with(BlockHelper.ROOTS, false));
				info.cancel();
			}
		}
	}
	
	@Inject(method = "isValidPosition", at = @At("HEAD"), cancellable = true)
	private void isValidPosition(BlockState state, IWorldReader world, BlockPos pos, CallbackInfoReturnable<Boolean> info) 
	{
		BlockState down = world.getBlockState(pos.down());
		if (down.isIn(ModBlocks.CHORUS_NYLIUM.get()) || down.isIn(Blocks.END_STONE)) 
		{
			info.setReturnValue(true);
			info.cancel();
		}
	}
	
	@Inject(method = "fillStateContainer", at = @At("TAIL"))
	private void fillStateContainer(StateContainer.Builder<Block, BlockState> builder, CallbackInfo info) 
	{
		builder.add(BlockHelper.ROOTS);
	}
	
}
