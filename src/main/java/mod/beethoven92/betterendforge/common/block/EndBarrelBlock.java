package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import javax.annotation.Nullable;

import mod.beethoven92.betterendforge.common.tileentity.EndBarrelTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EndBarrelBlock extends BarrelBlock {
	public EndBarrelBlock(AbstractBlock.Properties properties) {
		super(properties);
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader worldIn) {
		return new EndBarrelTileEntity();
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if (world.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			TileEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof EndBarrelTileEntity) {
				player.openMenu((EndBarrelTileEntity) blockEntity);
				player.awardStat(Stats.OPEN_BARREL);
				PiglinTasks.angerNearbyPiglins(player, true);
			}

			return ActionResultType.CONSUME;
		}
	}

	@Override
	public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
		TileEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof EndBarrelTileEntity) {
			((EndBarrelTileEntity) blockEntity).tick();
		}
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void setPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer,
			ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			TileEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof EndBarrelTileEntity) {
				((EndBarrelTileEntity) blockEntity).setCustomName(stack.getHoverName());
			}
		}
	}
}
