package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.world.surfacebuilder.DoubleBlockSurfaceBuilder;
import mod.beethoven92.betterendforge.common.world.surfacebuilder.SulphuricSurfaceBuilder;
import mod.beethoven92.betterendforge.common.world.surfacebuilder.UmbraSurfaceBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

// NEEDS RE-WRITE
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
	
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> NEON_OASIS_SURFACE = SURFACE_BUILDERS.register("neon_oasis_surface",
			() -> new DoubleBlockSurfaceBuilder(ModBlocks.ENDSTONE_DUST.get(), ModBlocks.END_MOSS.get()));

	
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> SULPHURIC_SURFACE = SURFACE_BUILDERS.register("sulphuric_surface",
			() -> new SulphuricSurfaceBuilder());

	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> UMBRA_SURFACE = SURFACE_BUILDERS.register("umbra_surface",
			() -> new UmbraSurfaceBuilder());


	// Built-in surface builder configurations
	public static class Configs
	{
		public static final SurfaceBuilderConfig CRYSTAL_SURFACE = 
				makeTernaryConfig(ModBlocks.CRYSTAL_MOSS.get(), Blocks.END_STONE, Blocks.END_STONE);
		
		public static final SurfaceBuilderConfig DUMMY = makeSimpleConfig(Blocks.END_STONE);
		
		public static final SurfaceBuilderConfig DEFAULT_END_CONFIG = makeSimpleConfig(Blocks.END_STONE);
		public static final SurfaceBuilderConfig FLAVOLITE_CONFIG = makeSimpleConfig(ModBlocks.FLAVOLITE.stone.get());
		public static final SurfaceBuilderConfig BRIMSTONE_CONFIG = makeSimpleConfig(ModBlocks.BRIMSTONE.get());
		public static final SurfaceBuilderConfig SULFURIC_ROCK_CONFIG = makeSimpleConfig(ModBlocks.SULPHURIC_ROCK.stone.get());
		public static final SurfaceBuilderConfig UMBRA_SURFACE_CONFIG = makeSimpleConfig(ModBlocks.UMBRALITH.stone.get());

		public static final SurfaceBuilderConfig PALLIDIUM_FULL_SURFACE_CONFIG = makeSurfaceConfig(ModBlocks.PALLIDIUM_FULL.get(), ModBlocks.UMBRALITH.stone.get());
		public static final SurfaceBuilderConfig PALLIDIUM_HEAVY_SURFACE_CONFIG = makeSurfaceConfig(ModBlocks.PALLIDIUM_HEAVY.get(), ModBlocks.UMBRALITH.stone.get());
		public static final SurfaceBuilderConfig PALLIDIUM_THIN_SURFACE_CONFIG = makeSurfaceConfig(ModBlocks.PALLIDIUM_THIN.get(), ModBlocks.UMBRALITH.stone.get());
		public static final SurfaceBuilderConfig PALLIDIUM_TINY_SURFACE_CONFIG = makeSurfaceConfig(ModBlocks.PALLIDIUM_TINY.get(), ModBlocks.UMBRALITH.stone.get());

		private static SurfaceBuilderConfig makeSimpleConfig(Block block) 
		{
			BlockState state = block.getDefaultState();
			return new SurfaceBuilderConfig(state, state, state);
		}
		
		private static SurfaceBuilderConfig makeTernaryConfig(Block block1, Block block2, Block block3) 
		{
			BlockState state1 = block1.getDefaultState();
			BlockState state2 = block2.getDefaultState();
			BlockState state3 = block3.getDefaultState();
			return new SurfaceBuilderConfig(state1, state2, state3);
		}

		private static SurfaceBuilderConfig makeSurfaceConfig(Block surface, Block under) {
			return new SurfaceBuilderConfig(
					surface.getDefaultState(),
					under.getDefaultState(),
					under.getDefaultState()
			);
		}
	}
}
