package mod.beethoven92.betterendforge.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import mod.beethoven92.betterendforge.common.interfaces.IBiomeArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeContainer;

@Mixin(BiomeContainer.class)
public class BiomeContainerMixin implements IBiomeArray
{
	@Final
	@Shadow
	private Biome[] biomes;

	@Final
	@Shadow
	private static int BIOMES_SIZE;

	@Final
	@Shadow
	public static int HORIZONTAL_MASK;

	@Final
	@Shadow
	public static int VERTICAL_MASK;

	@Override
	public void setBiome(Biome biome, BlockPos pos) 
	{
		int biomeX = pos.getX() >> 2;
		int biomeY = pos.getY() >> 2;
		int biomeZ = pos.getZ() >> 2;
		int index = be_getArrayIndex(biomeX, biomeY, biomeZ);
		biomes[index] = biome;
	}

	private int be_getArrayIndex(int biomeX, int biomeY, int biomeZ) 
	{
		int i = biomeX & HORIZONTAL_MASK;
		int j = MathHelper.clamp(biomeY, 0, VERTICAL_MASK);
		int k = biomeZ & HORIZONTAL_MASK;
		return j << BIOMES_SIZE + BIOMES_SIZE | k << BIOMES_SIZE | i;
	}
}
