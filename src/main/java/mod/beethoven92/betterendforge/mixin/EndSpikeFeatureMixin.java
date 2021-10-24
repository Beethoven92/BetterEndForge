package mod.beethoven92.betterendforge.mixin;

import java.util.Random;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.FeatureHelper;
import mod.beethoven92.betterendforge.common.util.StructureHelper;
import mod.beethoven92.betterendforge.common.util.WorldDataAPI;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PaneBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import org.spongepowered.asm.mixin.Mixin;
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

@Mixin(EndSpikeFeature.class)
public abstract class EndSpikeFeatureMixin 
{

	@Inject(method = "generate", at = @At("HEAD"), cancellable = true)
	private void beGenerateSpike(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos,
								 EndSpikeFeatureConfig endSpikeFeatureConfig, CallbackInfoReturnable<Boolean> info)
	{
		if (!GeneratorOptions.hasPillars())
		{
			info.setReturnValue(false);
		}
	}

	@Inject(method = "placeSpike", at = @At("HEAD"), cancellable = true)
	private void be_placeSpike(IServerWorld world, Random random, EndSpikeFeatureConfig config, EndSpikeFeature.EndSpike spike, CallbackInfo info) {
		int x = spike.getCenterX();
		int z = spike.getCenterZ();
		int radius = spike.getRadius();
		int minY = 0;

		long lx = (long) x;
		long lz = (long) z;
		if (lx * lx + lz * lz < 10000) {
			String pillarID = String.format("%d_%d", x, z);
			CompoundNBT pillar = WorldDataAPI.getCompoundTag(BetterEnd.MOD_ID, "pillars");
			boolean haveValue = pillar.contains(pillarID);
			minY = haveValue ? pillar.getInt(pillarID) : world.getChunk(x >> 4, z >> 4)
					.getTopBlockY(Heightmap.Type.WORLD_SURFACE, x & 15, z);
			if (!haveValue) {
				pillar.putInt(pillarID, minY);
			}
		}
		else {
			minY = world.getChunk(x >> 4, z >> 4).getTopBlockY(Type.WORLD_SURFACE, x & 15, z);
		}

		GeneratorOptions.setDirectSpikeHeight();
		int maxY = minY + spike.getHeight() - 64;

		if (GeneratorOptions.replacePillars() && be_radiusInRange(radius)) {
			radius--;
			Template base = StructureHelper.readStructure(BetterEnd.makeID("pillars/pillar_base_" + radius));
			Template top = StructureHelper.readStructure(BetterEnd.makeID("pillars/pillar_top_" + radius + (spike
					.isGuarded() ? "_cage" : "")));
			Vector3i side = base.getSize();
			BlockPos pos1 = new BlockPos(x - (side.getX() >> 1), minY - 3, z - (side.getZ() >> 1));
			minY = pos1.getY() + side.getY();
			side = top.getSize();
			BlockPos pos2 = new BlockPos(x - (side.getX() >> 1), maxY, z - (side.getZ() >> 1));
			maxY = pos2.getY();

			PlacementSettings data = new PlacementSettings();
			base.func_237146_a_(world, pos1, pos1, data, random, 2);
			top.func_237146_a_(world, pos2, pos2, data, random, 2);

			int r2 = radius * radius + 1;
			BlockPos.Mutable mut = new BlockPos.Mutable();
			for (int px = -radius; px <= radius; px++) {
				mut.setX(x + px);
				int x2 = px * px;
				for (int pz = -radius; pz <= radius; pz++) {
					mut.setZ(z + pz);
					int z2 = pz * pz;
					if (x2 + z2 <= r2) {
						for (int py = minY; py < maxY; py++) {
							mut.setY(py);
							if (world.getBlockState(mut).getMaterial().isReplaceable()) {
								if ((px == radius || px == -radius || pz == radius || pz == -radius) && random.nextInt(
										24) == 0) {
									BlockHelper.setWithoutUpdate(world, mut, Blocks.CRYING_OBSIDIAN);
								}
								else {
									BlockHelper.setWithoutUpdate(world, mut, Blocks.OBSIDIAN);
								}
							}
						}
					}
				}
			}
		}
		else {
			minY -= 15;
			int r2 = radius * radius + 1;
			BlockPos.Mutable mut = new BlockPos.Mutable();
			for (int px = -radius; px <= radius; px++) {
				mut.setX(x + px);
				int x2 = px * px;
				for (int pz = -radius; pz <= radius; pz++) {
					mut.setZ(z + pz);
					int z2 = pz * pz;
					if (x2 + z2 <= r2) {
						for (int py = minY; py < maxY; py++) {
							mut.setY(py);
							if (world.getBlockState(mut).getMaterial().isReplaceable()) {
								BlockHelper.setWithoutUpdate(world, mut, Blocks.OBSIDIAN);
							}
						}
					}
				}
			}

			mut.setX(x);
			mut.setZ(z);
			mut.setY(maxY);
			BlockHelper.setWithoutUpdate(world, mut, Blocks.BEDROCK);

			EnderCrystalEntity crystal = EntityType.END_CRYSTAL.create(world.getWorld());
			crystal.setBeamTarget(config.getCrystalBeamTarget());
			crystal.setInvulnerable(config.isCrystalInvulnerable());
			crystal.setLocationAndAngles(x + 0.5D, maxY + 1, z + 0.5D, random.nextFloat() * 360.0F, 0.0F);
			world.addEntity(crystal);

			if (spike.isGuarded()) {
				for (int px = -2; px <= 2; ++px) {
					boolean bl = MathHelper.abs(px) == 2;
					for (int pz = -2; pz <= 2; ++pz) {
						boolean bl2 = MathHelper.abs(pz) == 2;
						for (int py = 0; py <= 3; ++py) {
							boolean bl3 = py == 3;
							if (bl || bl2 || bl3) {
								boolean bl4 = px == -2 || px == 2 || bl3;
								boolean bl5 = pz == -2 || pz == 2 || bl3;
								BlockState blockState = (BlockState) ((BlockState) ((BlockState) ((BlockState) Blocks.IRON_BARS
										.getDefaultState()
										.with(PaneBlock.NORTH, bl4 && pz != -2)).with(
										PaneBlock.SOUTH,
										bl4 && pz != 2
								)).with(PaneBlock.WEST, bl5 && px != -2)).with(
										PaneBlock.EAST,
										bl5 && px != 2
								);
								BlockHelper.setWithoutUpdate(
										world,
										mut.setPos(spike.getCenterX() + px, maxY + py, spike.getCenterZ() + pz),
										blockState
								);
							}
						}
					}
				}
			}
		}
		info.cancel();
	}

	private boolean be_radiusInRange(int radius) {
		return radius > 1 && radius < 6;
	}
}
