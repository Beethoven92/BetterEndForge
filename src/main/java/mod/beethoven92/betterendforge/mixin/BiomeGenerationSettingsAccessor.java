package mod.beethoven92.betterendforge.mixin;

import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.StructureFeature;
//import net.minecraft.world.level.biome.BiomeGenerationSettings;
//import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
//import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.function.Supplier;

@Mixin(BiomeGenerationSettings.class)
public interface BiomeGenerationSettingsAccessor {
	@Accessor("features")
	List<List<Supplier<ConfiguredFeature<?, ?>>>> be_getFeatures();
	
	@Accessor("features")
	void be_setFeatures(List<List<Supplier<ConfiguredFeature<?, ?>>>> features);
	
	@Accessor("structures")
	List<Supplier<StructureFeature<?, ?>>> be_getStructures();
	
	@Accessor("structures")
	void be_setStructures(List<Supplier<StructureFeature<?, ?>>> structures);
}
