package mod.beethoven92.betterendforge.mixin;

import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
//import net.minecraft.world.level.levelgen.placement.ConfiguredDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Features.Placements.class)
public interface FeatureDecoratorsAccessor {
	@Accessor("HEIGHTMAP_SQUARE")
	ConfiguredPlacement<?> be_getHeightmapSquare();
}
