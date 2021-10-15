package mod.beethoven92.betterendforge.mixin;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.ModList;
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

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

@Mixin(BiomeColors.class)
public class BiomeColorsMixin {
	private static final int POISON_COLOR = ModMathHelper.color(92, 160, 78);
	private static final int STREAM_COLOR = ModMathHelper.color(105, 213, 244);
	private static final Point[] OFFSETS;
	private static final boolean HAS_MAGNESIUM;

	@Inject(method = "getWaterColor", at = @At("RETURN"), cancellable = true)
	private static void be_getWaterColor(IBlockDisplayReader world, BlockPos blockPos, CallbackInfoReturnable<Integer> info) {
		IBlockDisplayReader view = HAS_MAGNESIUM ? Minecraft.getInstance().world : world;
		Mutable mut = new Mutable();
		mut.setY(blockPos.getY());
		for (int i = 0; i < OFFSETS.length; i++) {
			mut.setX(blockPos.getX() + OFFSETS[i].x);
			mut.setZ(blockPos.getZ() + OFFSETS[i].y);
			if ((view.getBlockState(mut).getBlockState().isIn(ModBlocks.BRIMSTONE.get()))) {
				info.setReturnValue(i < 16 ? STREAM_COLOR : POISON_COLOR);
				info.cancel();
				return;
			}
		}

	}

	//Magnesium Compat
	static {
		HAS_MAGNESIUM = ModList.get().isLoaded("magnesium");

		OFFSETS = new Point[20];
		for (int i = 0; i < 3; i++) {
			int p = i - 1;
			OFFSETS[i] = new Point(p, -2);
			OFFSETS[i + 3] = new Point(p, 2);
			OFFSETS[i + 6] = new Point(-2, p);
			OFFSETS[i + 9] = new Point(2, p);
		}

		for (int i = 0; i < 4; i++) {
			int inner = i + 16;
			Direction dir = BlockHelper.HORIZONTAL_DIRECTIONS[i];
			OFFSETS[inner] = new Point(dir.getXOffset(), dir.getZOffset());
			dir = BlockHelper.HORIZONTAL_DIRECTIONS[(i + 1) & 3];
			OFFSETS[i + 12] = new Point(OFFSETS[inner].x + dir.getXOffset(), OFFSETS[inner].y + dir.getZOffset());
		}

	}

}
