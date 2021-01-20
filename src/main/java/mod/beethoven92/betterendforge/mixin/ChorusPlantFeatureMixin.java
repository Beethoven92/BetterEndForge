package mod.beethoven92.betterendforge.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ChorusPlantFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

@Mixin(ChorusPlantFeature.class)
public abstract class ChorusPlantFeatureMixin 
{
	@Inject(method = "generate", at = @At("HEAD"), cancellable = true)
	private void be_generate(ISeedReader worldIn, ChunkGenerator chunkGenerator, Random random, 
			BlockPos blockPos, NoFeatureConfig config, CallbackInfoReturnable<Boolean> info) 
	{
		if (worldIn.isAirBlock(blockPos) && worldIn.getBlockState(blockPos.down()).isIn(ModBlocks.CHORUS_NYLIUM.get())) 
		{
			ChorusFlowerBlock.generatePlant(worldIn, blockPos, random, ModMathHelper.randRange(8, 16, random));
			BlockState bottom = worldIn.getBlockState(blockPos);
			if (bottom.isIn(Blocks.CHORUS_PLANT)) 
			{
				if ((CommonConfig.isCustomChorusPlantEnabled())) 
				{
					BlockHelper.setWithoutUpdate(worldIn, blockPos, bottom.with(BlockHelper.ROOTS, true).with(BlockStateProperties.DOWN, true));
				}
				else 
				{
					BlockHelper.setWithoutUpdate(worldIn, blockPos, bottom.with(BlockStateProperties.DOWN, true));
				}
			}
			info.setReturnValue(true);
		}
	}
}
