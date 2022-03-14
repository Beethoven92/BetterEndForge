package mod.beethoven92.betterendforge.mixin;

import net.minecraft.data.worldgen.Features;
import net.minecraft.world.level.levelgen.placement.ConfiguredDecorator;
//import net.minecraft.world.level.levelgen.placement.ConfiguredDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Features.Decorators.class)
public interface FeatureDecoratorsAccessor {
	@Accessor("HEIGHTMAP_SQUARE")
	ConfiguredDecorator<?> be_getHeightmapSquare();
}
