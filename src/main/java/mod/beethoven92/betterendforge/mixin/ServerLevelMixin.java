package mod.beethoven92.betterendforge.mixin;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.profiler.IProfiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.listener.IChunkStatusListener;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.ISpecialSpawner;
import net.minecraft.world.storage.IServerWorldInfo;
import net.minecraft.world.storage.ISpawnWorldInfo;
import net.minecraft.world.storage.SaveFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public abstract class ServerLevelMixin extends World {
	private static String be_lastWorld = null;
	
	protected ServerLevelMixin(ISpawnWorldInfo writableLevelData, RegistryKey<World> resourceKey, DimensionType dimensionType, Supplier<IProfiler> supplier, boolean bl, boolean bl2, long l) {
		super(writableLevelData, resourceKey, dimensionType, supplier, bl, bl2, l);
	}
	
	@Inject(method = "<init>*", at = @At("TAIL"))
	private void be_onServerWorldInit(MinecraftServer server, Executor workerExecutor, SaveFormat.LevelSave session, IServerWorldInfo properties, RegistryKey<World> registryKey, DimensionType dimensionType, IChunkStatusListener worldGenerationProgressListener, ChunkGenerator chunkGenerator, boolean debugWorld, long l, List<ISpecialSpawner> list, boolean bl, CallbackInfo info) {
		if (be_lastWorld != null && be_lastWorld.equals(session.getSaveName())) {
			return;
		}
		
		be_lastWorld = session.getSaveName();
		ServerWorld world = ServerWorld.class.cast(this);
		ModBiomes.onWorldLoad(world.getSeed(), world.func_241828_r().getRegistry(Registry.BIOME_KEY));
	}
	
	@Inject(method = "getSpawnPoint", at = @At("HEAD"), cancellable = true)
	private void be_getSharedSpawnPos(CallbackInfoReturnable<BlockPos> info) {
		if (GeneratorOptions.changeSpawn()) {
			if (ServerWorld.class.cast(this).getDimensionKey() == World.THE_END) {
				BlockPos pos = GeneratorOptions.getSpawn();
				info.setReturnValue(pos);
			}
		}
	}
	
	@Inject(method = "func_241121_a_", at = @At("HEAD"), cancellable = true)
	private static void be_createObsidianPlatform(ServerWorld serverLevel, CallbackInfo info) {
		if (!GeneratorOptions.generateObsidianPlatform()) {
			info.cancel();
		}
		else if (GeneratorOptions.changeSpawn()) {
			BlockPos blockPos = GeneratorOptions.getSpawn();
			int i = blockPos.getX();
			int j = blockPos.getY() - 2;
			int k = blockPos.getZ();
			BlockPos.getAllInBoxMutable(i - 2, j + 1, k - 2, i + 2, j + 3, k + 2).forEach((blockPosx) -> {
				serverLevel.setBlockState(blockPosx, Blocks.AIR.getDefaultState());
			});
			BlockPos.getAllInBoxMutable(i - 2, j, k - 2, i + 2, j, k + 2).forEach((blockPosx) -> {
				serverLevel.setBlockState(blockPosx, Blocks.OBSIDIAN.getDefaultState());
			});
			info.cancel();
		}
	}
	
	@ModifyArg(method = "tickEnvironment", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/server/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"))
	private BlockState be_modifyTickState(BlockPos pos, BlockState state) {
		if (state.isIn(Blocks.ICE)) {
			ResourceLocation biome = ModBiomes.getBiomeID(getBiome(pos));
			if (biome.getNamespace().equals(BetterEnd.MOD_ID)) {
				state = ModBlocks.EMERALD_ICE.get().getDefaultState();
			}
		}
		return state;
	}
}
