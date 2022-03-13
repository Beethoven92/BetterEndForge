package mod.beethoven92.betterendforge.common.block.template;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class StripableLogBlockTemplate extends PillarBlockTemplate 
{
	private final Block striped;
	private final MaterialColor color;
	
	public StripableLogBlockTemplate(MaterialColor color, Block striped) 
	{
		super(AbstractBlock.Properties.copy(striped));
		this.striped = striped;
		this.color = color;
	}

	@Override
	public MaterialColor defaultMaterialColor() 
	{
		return color;
	}
	
	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) 
	{
		if (player.getMainHandItem().getItem() instanceof AxeItem) 
		{
			worldIn.playSound(player, pos, SoundEvents.AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!worldIn.isClientSide()) 
			{
				worldIn.setBlock(pos, striped.defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 11);
				if (player != null && !player.isCreative()) 
				{
					player.getMainHandItem().hurt(1, worldIn.random, (ServerPlayerEntity) player);
				}
			}
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.FAIL;
	}
}
