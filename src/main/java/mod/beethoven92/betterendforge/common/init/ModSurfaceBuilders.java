package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.world.surfacebuilder.DoubleBlockSurfaceBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSurfaceBuilders 
{
	public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = 
			DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, BetterEnd.MOD_ID);

	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> CRYSTAL_SURFACE = SURFACE_BUILDERS.register("crystal_surface",
			() -> new DefaultSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> MEGALAKE_SURFACE = SURFACE_BUILDERS.register("megalake_surface",
			() -> new DoubleBlockSurfaceBuilder(ModBlocks.END_MOSS.get(), ModBlocks.ENDSTONE_DUST.get()));
	
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> MUSHROOMLAND_SURFACE = SURFACE_BUILDERS.register("mushroomland_surface",
			() -> new DoubleBlockSurfaceBuilder(ModBlocks.END_MOSS.get(), ModBlocks.END_MYCELIUM.get()));
	
	// Built-in surface builder configurations
	public static class Configs
	{
		public static final SurfaceBuilderConfig CRYSTAL_SURFACE = 
				new SurfaceBuilderConfig(ModBlocks.CRYSTAL_MOSS.get().getDefaultState(), 
						Blocks.END_STONE.getDefaultState(), Blocks.END_STONE.getDefaultState());
		
		public static final SurfaceBuilderConfig DUMMY = 
				new SurfaceBuilderConfig(Blocks.END_STONE.getDefaultState(), 
						Blocks.END_STONE.getDefaultState(), Blocks.END_STONE.getDefaultState());
	}
}
