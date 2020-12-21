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
		super(AbstractBlock.Properties.from(striped));
		this.striped = striped;
		this.color = color;
	}

	@Override
	public MaterialColor getMaterialColor() 
	{
		return color;
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) 
	{
		if (player.getHeldItemMainhand().getItem() instanceof AxeItem) 
		{
			worldIn.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!worldIn.isRemote()) 
			{
				worldIn.setBlockState(pos, striped.getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS)), 11);
				if (player != null && !player.isCreative()) 
				{
					player.getHeldItemMainhand().attemptDamageItem(1, worldIn.rand, (ServerPlayerEntity) player);
				}
			}
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.FAIL;
	}
}
