package mod.beethoven92.betterendforge.mixin;

import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusPlantBlock;
import net.minecraft.block.SixWayBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

@Mixin(value = ChorusPlantBlock.class, priority = 100)
public abstract class ChorusPlantBlockMixin extends Block {
	public ChorusPlantBlockMixin(Properties settings) {
		super(settings);
	}

	@Inject(method = "getStateForPlacement", at = @At("RETURN"), cancellable = true)
	private void be_getStateForPlacement(BlockItemUseContext ctx, CallbackInfoReturnable<BlockState> info) {
		BlockPos pos = ctx.getPos();
		World world = ctx.getWorld();
		BlockState plant = info.getReturnValue();
		if (ctx.canPlace() && plant.isIn(Blocks.CHORUS_PLANT) && world.getBlockState(pos.down()).isIn(ModTags.END_GROUND)) {
			info.setReturnValue(plant.with(BlockStateProperties.DOWN, true));
		}
	}

	@Inject(method = "makeConnections", at = @At("RETURN"), cancellable = true)
	private void beConnectionProperties(IBlockReader blockGetter, BlockPos blockPos, CallbackInfoReturnable<BlockState> info)
	{
		BlockState plant = info.getReturnValue();
		if (plant.isIn(Blocks.CHORUS_PLANT) && blockGetter.getBlockState(blockPos.down()).isIn(ModTags.END_GROUND)) {
			info.setReturnValue(plant.with(BlockStateProperties.DOWN, true));
		}

	}

	@Inject(method = "isValidPosition", at = @At("HEAD"), cancellable = true)
	private void isValidPosition(BlockState state, IWorldReader world, BlockPos pos, CallbackInfoReturnable<Boolean> info)
	{
		BlockState down = world.getBlockState(pos.down());
		if (down.isIn(ModBlocks.CHORUS_NYLIUM.get()) || down.isIn(Blocks.END_STONE)) {
			info.setReturnValue(true);
		}
	}

	@Inject(method = "updatePostPlacement", at = @At("RETURN"), cancellable = true)
	private void updatePostPlacement(BlockState state, Direction direction, BlockState newState, IWorld world,
			BlockPos pos, BlockPos posFrom, CallbackInfoReturnable<BlockState> info)
	{
		BlockState plant = info.getReturnValue();
		if (plant.isIn(Blocks.CHORUS_PLANT) && world.getBlockState(pos.down()).isIn(ModTags.END_GROUND)) {
			plant = plant.with(BlockStateProperties.DOWN, true);
			info.setReturnValue(plant);

		}
	}

}
