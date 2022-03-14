package mod.beethoven92.betterendforge.mixin;

import java.util.Random;

import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.world.level.block.PipeBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ChorusPlantFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

@Mixin(ChorusPlantFeature.class)
public abstract class ChorusPlantFeatureMixin 
{
	@Inject(method = "place", at = @At("HEAD"), cancellable = true)
	private void be_place(WorldGenLevel worldIn, ChunkGenerator chunkGenerator, Random random,
			BlockPos blockPos, NoneFeatureConfiguration config, CallbackInfoReturnable<Boolean> info) 
	{

		final WorldGenLevel structureWorldAccess = worldIn;
		if (structureWorldAccess.isEmptyBlock(blockPos) && structureWorldAccess.getBlockState(blockPos.below()).is(ModBlocks.CHORUS_NYLIUM.get())) {
			ChorusFlowerBlock.generatePlant(structureWorldAccess, blockPos, random, ModMathHelper.randRange(8, 16, random));
			BlockState bottom = structureWorldAccess.getBlockState(blockPos);
			if (bottom.is(Blocks.CHORUS_PLANT)) {
				BlockHelper.setWithoutUpdate(
						structureWorldAccess,
						blockPos,
						bottom.setValue(PipeBlock.DOWN, true)
				);
			}
			info.setReturnValue(true);
		}
	}
}
