package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.world.surfacebuilder.DoubleBlockSurfaceBuilder;
import mod.beethoven92.betterendforge.common.world.surfacebuilder.SulphuricSurfaceBuilder;
import mod.beethoven92.betterendforge.common.world.surfacebuilder.UmbraSurfaceBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.surfacebuilders.DefaultSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

// NEEDS RE-WRITE
public class ModSurfaceBuilders 
{
	public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = 
			DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, BetterEnd.MOD_ID);

	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderBaseConfiguration>> CRYSTAL_SURFACE = SURFACE_BUILDERS.register("crystal_surface",
			() -> new DefaultSurfaceBuilder(SurfaceBuilderBaseConfiguration.CODEC));
	
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderBaseConfiguration>> MEGALAKE_SURFACE = SURFACE_BUILDERS.register("megalake_surface",
			() -> new DoubleBlockSurfaceBuilder(ModBlocks.END_MOSS.get(), ModBlocks.ENDSTONE_DUST.get()));
	
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderBaseConfiguration>> MUSHROOMLAND_SURFACE = SURFACE_BUILDERS.register("mushroomland_surface",
			() -> new DoubleBlockSurfaceBuilder(ModBlocks.END_MOSS.get(), ModBlocks.END_MYCELIUM.get()));
	
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderBaseConfiguration>> NEON_OASIS_SURFACE = SURFACE_BUILDERS.register("neon_oasis_surface",
			() -> new DoubleBlockSurfaceBuilder(ModBlocks.ENDSTONE_DUST.get(), ModBlocks.END_MOSS.get()));

	
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderBaseConfiguration>> SULPHURIC_SURFACE = SURFACE_BUILDERS.register("sulphuric_surface",
			() -> new SulphuricSurfaceBuilder());

	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderBaseConfiguration>> UMBRA_SURFACE = SURFACE_BUILDERS.register("umbra_surface",
			() -> new UmbraSurfaceBuilder());


	// Built-in surface builder configurations
	public static class Configs
	{
		public static final SurfaceBuilderBaseConfiguration CRYSTAL_SURFACE = 
				makeTernaryConfig(ModBlocks.CRYSTAL_MOSS.get(), Blocks.END_STONE, Blocks.END_STONE);
		
		public static final SurfaceBuilderBaseConfiguration DUMMY = makeSimpleConfig(Blocks.END_STONE);
		
		public static final SurfaceBuilderBaseConfiguration DEFAULT_END_CONFIG = makeSimpleConfig(Blocks.END_STONE);
		public static final SurfaceBuilderBaseConfiguration FLAVOLITE_CONFIG = makeSimpleConfig(ModBlocks.FLAVOLITE.stone.get());
		public static final SurfaceBuilderBaseConfiguration BRIMSTONE_CONFIG = makeSimpleConfig(ModBlocks.BRIMSTONE.get());
		public static final SurfaceBuilderBaseConfiguration SULFURIC_ROCK_CONFIG = makeSimpleConfig(ModBlocks.SULPHURIC_ROCK.stone.get());
		public static final SurfaceBuilderBaseConfiguration UMBRA_SURFACE_CONFIG = makeSimpleConfig(ModBlocks.UMBRALITH.stone.get());

		public static final SurfaceBuilderBaseConfiguration PALLIDIUM_FULL_SURFACE_CONFIG = makeSurfaceConfig(ModBlocks.PALLIDIUM_FULL.get(), ModBlocks.UMBRALITH.stone.get());
		public static final SurfaceBuilderBaseConfiguration PALLIDIUM_HEAVY_SURFACE_CONFIG = makeSurfaceConfig(ModBlocks.PALLIDIUM_HEAVY.get(), ModBlocks.UMBRALITH.stone.get());
		public static final SurfaceBuilderBaseConfiguration PALLIDIUM_THIN_SURFACE_CONFIG = makeSurfaceConfig(ModBlocks.PALLIDIUM_THIN.get(), ModBlocks.UMBRALITH.stone.get());
		public static final SurfaceBuilderBaseConfiguration PALLIDIUM_TINY_SURFACE_CONFIG = makeSurfaceConfig(ModBlocks.PALLIDIUM_TINY.get(), ModBlocks.UMBRALITH.stone.get());

		private static SurfaceBuilderBaseConfiguration makeSimpleConfig(Block block) 
		{
			BlockState state = block.defaultBlockState();
			return new SurfaceBuilderBaseConfiguration(state, state, state);
		}
		
		private static SurfaceBuilderBaseConfiguration makeTernaryConfig(Block block1, Block block2, Block block3) 
		{
			BlockState state1 = block1.defaultBlockState();
			BlockState state2 = block2.defaultBlockState();
			BlockState state3 = block3.defaultBlockState();
			return new SurfaceBuilderBaseConfiguration(state1, state2, state3);
		}

		private static SurfaceBuilderBaseConfiguration makeSurfaceConfig(Block surface, Block under) {
			return new SurfaceBuilderBaseConfiguration(
					surface.defaultBlockState(),
					under.defaultBlockState(),
					under.defaultBlockState()
			);
		}
	}
}
