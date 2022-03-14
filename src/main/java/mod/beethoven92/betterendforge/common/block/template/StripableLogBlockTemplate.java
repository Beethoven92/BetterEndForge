package mod.beethoven92.betterendforge.common.block.template;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;

public class StripableLogBlockTemplate extends PillarBlockTemplate 
{
	private final Block striped;
	private final MaterialColor color;
	
	public StripableLogBlockTemplate(MaterialColor color, Block striped) 
	{
		super(BlockBehaviour.Properties.copy(striped));
		this.striped = striped;
		this.color = color;
	}

	@Override
	public MaterialColor defaultMaterialColor() 
	{
		return color;
	}
	
	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player,
			InteractionHand handIn, BlockHitResult hit) 
	{
		if (player.getMainHandItem().getItem() instanceof AxeItem) 
		{
			worldIn.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
			if (!worldIn.isClientSide()) 
			{
				worldIn.setBlock(pos, striped.defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 11);
				if (player != null && !player.isCreative()) 
				{
					player.getMainHandItem().hurt(1, worldIn.random, (ServerPlayer) player);
				}
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.FAIL;
	}
}
