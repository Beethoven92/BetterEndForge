package mod.beethoven92.betterendforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;

@Mixin(BiomeColors.class)
public abstract class BiomeColorsMixin 
{
	private static final int POISON_COLOR = ModMathHelper.color(92, 160, 78);
	private static final int STREAM_COLOR = ModMathHelper.color(105, 213, 244);

	@Inject(method = "getWaterColor", at = @At("RETURN"), cancellable = true)
	private static void getWaterColor(IBlockDisplayReader world, BlockPos pos, CallbackInfoReturnable<Integer> info) 
	{
		int color = info.getReturnValue();
		
		boolean scanDeep = true;
		Mutable mut = new Mutable();
		for (Direction d: BlockHelper.HORIZONTAL_DIRECTIONS) 
		{
			mut.setPos(pos).move(d);
			if ((world.getBlockState(mut).isIn(ModBlocks.BRIMSTONE.get())))
			{
				color = POISON_COLOR;
				scanDeep = false;
				break;
			}
		}

		if (scanDeep) 
		{
			int x1 = pos.getX() - 2;
			int z1 = pos.getZ() - 2;

			int x2 = pos.getX() + 3;
			int z2 = pos.getZ() + 3;

			mut.setY(pos.getY());
			for (int x = x1; x < x2 && scanDeep; x++)
			{
				mut.setX(x);
				for (int z = z1; z < z2 && scanDeep; z++) 
				{
					mut.setZ(z);
					if (Math.abs(pos.getX() - x) != 2 || Math.abs(pos.getZ() - z) != 2) 
					{
						if ((world.getBlockState(mut).isIn(ModBlocks.BRIMSTONE.get()))) 
						{
							color = STREAM_COLOR;
							scanDeep = false;
						}
					}
				}
			}
		}
		
		info.setReturnValue(color);
	}
}
