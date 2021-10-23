package mod.beethoven92.betterendforge.mixin;

import net.minecraft.world.gen.placement.ConfiguredPlacement;
//import net.minecraft.world.level.levelgen.placement.ConfiguredDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.world.gen.feature.Features$Placements")
public interface FeatureDecoratorsAccessor {
	@Accessor("HEIGHTMAP_PLACEMENT")
	ConfiguredPlacement<?> be_getHeightmapSquare();
}
