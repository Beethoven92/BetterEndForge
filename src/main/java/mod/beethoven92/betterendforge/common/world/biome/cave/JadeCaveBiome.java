package mod.beethoven92.betterendforge.common.world.biome.cave;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndCaveBiome;
import mod.beethoven92.betterendforge.common.world.biome.BiomeTemplate;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.BlockState;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;


public class JadeCaveBiome extends BetterEndCaveBiome {
	private static final OpenSimplexNoise WALL_NOISE = new OpenSimplexNoise("jade_cave".hashCode());
	private static final OpenSimplexNoise DEPTH_NOISE = new OpenSimplexNoise("depth_noise".hashCode());
	private static final BlockState[] JADE = new BlockState[3];



	public JadeCaveBiome() {
		super(new BiomeTemplate("jade_cave")
				.setFogColor(118, 150, 112)
				.setFogDensity(2.0F)
				.setMusic(ModSoundEvents.MUSIC_CAVES.get())
				.setAmbientSound(ModSoundEvents.AMBIENT_CAVES.get())
				.setWaterFogColor(95, 223, 255));

		JADE[0] = ModBlocks.VIRID_JADESTONE.stone.get().getDefaultState();
		JADE[1] = ModBlocks.AZURE_JADESTONE.stone.get().getDefaultState();
		JADE[2] = ModBlocks.SANDY_JADESTONE.stone.get().getDefaultState();
	}
	
	@Override
	public BlockState getWall(BlockPos pos) {
		double depth = DEPTH_NOISE.eval(pos.getX() * 0.02, pos.getZ() * 0.02) * 0.2 + 0.5;
		int index = MathHelper.floor((pos.getY() + WALL_NOISE.eval(pos.getX() * 0.2, pos.getZ() * 0.2) * 1.5) * depth + 0.5);
		index = MathHelper.abs(index) % 3;
		return JADE[index];
	}
}
