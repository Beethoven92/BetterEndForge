package mod.beethoven92.betterendforge.mixin;

import java.util.Random;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.WorldDataAPI;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PaneBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.EndSpikeFeature;
import net.minecraft.world.gen.feature.EndSpikeFeatureConfig;

@Mixin(EndSpikeFeature.EndSpike.class)
public abstract class EndSpikeMixin
{
	@Final
@Shadow
private int height;

	@Inject(method = "getHeight", at = @At("HEAD"), cancellable = true)
	private void be_getSpikeHeight(CallbackInfoReturnable<Integer> info) {
		if (!GeneratorOptions.isDirectSpikeHeight()) {
			int x = getCenterX();
			int z = getCenterZ();
			String pillarID = String.format("%d_%d", x, z);
			CompoundNBT pillar = WorldDataAPI.getCompoundTag(BetterEnd.MOD_ID, "pillars");
			int minY = pillar.contains(pillarID) ? pillar.getInt(pillarID) : 65;
			int maxY = minY + height - 54;
			info.setReturnValue(maxY);
		}
	}

	@Shadow
	public int getCenterX() {
		return 0;
	}

	@Shadow
	public int getCenterZ() {
		return 0;
	}
}
