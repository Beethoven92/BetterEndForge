package mod.beethoven92.betterendforge.mixin;

import java.util.Random;

import javax.annotation.Nullable;

import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.block.ChorusPlantBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

@Mixin(value = ChorusFlowerBlock.class, priority = 100)
public abstract class ChorusFlowerBlockMixin extends Block
{
	private static final VoxelShape SHAPE_FULL = Block.makeCuboidShape(0, 0, 0, 16, 16, 16);
	private static final VoxelShape SHAPE_HALF = Block.makeCuboidShape(0, 0, 0, 16, 4, 16);
	
	@Shadow
	@Final
	private ChorusPlantBlock plantBlock;
	
	public ChorusFlowerBlockMixin(Properties properties) 
	{
		super(properties);
	}

	@Inject(method = "isValidPosition", at = @At("HEAD"), cancellable = true)
	private void isValidPosition(BlockState state, IWorldReader world, BlockPos pos, CallbackInfoReturnable<Boolean> info) 
	{
		if (world.getBlockState(pos.down()).isIn(ModBlocks.CHORUS_NYLIUM.get())) 
		{
			info.setReturnValue(true);
			info.cancel();
		}
	}
	
	@Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
	private void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo info) 
	{
		if (world.getBlockState(pos.down()).isIn(ModTags.END_GROUND)) {
			BlockPos up = pos.up();
			if (world.isAirBlock(up) && up.getY() < 256) {
				int i = state.get(ChorusFlowerBlock.AGE);
				if (i < 5) {
					this.placeGrownFlower(world, up, i + 1);
					BlockHelper.setWithoutUpdate(world, pos, plantBlock.getDefaultState().with(ChorusPlantBlock.UP, true).with(ChorusPlantBlock.DOWN, true));
					info.cancel();
				}
			}
		}
	}

	@Shadow
	private void placeGrownFlower(World world, BlockPos pos, int age) {}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
	{
		if (GeneratorOptions.changeChorusPlant()) {
			return state.get(ChorusFlowerBlock.AGE) == 5 ? SHAPE_HALF : SHAPE_FULL;
		}
		else {
			return super.getShape(state, world, pos, context);
		}
	}

	@Inject(method = "placeDeadFlower", at = @At("HEAD"), cancellable = true)
	private void beOnDie(World world, BlockPos pos, CallbackInfo info) 
	{
		BlockState down = world.getBlockState(pos.down());
		if (down.isIn(Blocks.CHORUS_PLANT) || down.isIn(ModTags.GEN_TERRAIN)) 
		{
			world.setBlockState(pos, this.getDefaultState().with(ChorusFlowerBlock.AGE, Integer.valueOf(5)), 2);
			world.playEvent(1034, pos, 0);
		}
		info.cancel();
	}
}
