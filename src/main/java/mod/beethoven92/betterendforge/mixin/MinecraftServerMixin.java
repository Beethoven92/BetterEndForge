package mod.beethoven92.betterendforge.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.level.storage.ServerLevelData;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin 
{
	@Final
	@Shadow
	protected WorldData worldData;
	
	@Final
	@Shadow
	private Map<ResourceKey<Level>, ServerLevel> levels;
	
	@Shadow
	public PlayerList getPlayerList()
	{
		return null;
	}
	
	@Shadow
	private static void setInitialSpawn(ServerLevel p_240786_0_, ServerLevelData p_240786_1_,
			boolean hasBonusChest, boolean p_240786_3_, boolean p_240786_4_)
	{		
	}
	
	@Inject(method = "overworld", at = @At(value = "HEAD"), cancellable = true)
	private final void be_GetOverworld(CallbackInfoReturnable<ServerLevel> info) 
	{
//		if (CommonConfig.swapOverworldWithEnd()) 
//		{
//			ServerWorld world = worlds.get(World.THE_END);
//			if (world == null) 
//			{
//				world = worlds.get(World.OVERWORLD);
//			}
//			info.setReturnValue(world);
//			info.cancel();
//		}
	}
	
	@Inject(method = "createLevels", at = @At(value = "TAIL"))
	private final void be_CreateWorlds(ChunkProgressListener worldGenerationProgressListener, CallbackInfo info) 
	{
//		if (CommonConfig.swapOverworldWithEnd()) 
//		{
//			ServerWorld world = worlds.get(World.THE_END);
//			if (world == null) 
//			{
//				world = worlds.get(World.OVERWORLD);
//			}
//			this.getPlayerList().setLevel(world);
//		    IServerWorldInfo iServerWorldInfo = this.serverConfig.getServerWorldInfo();
//		    DimensionGeneratorSettings dimensionGeneratorSettings = this.serverConfig.getDimensionGeneratorSettings();
//			boolean bl = dimensionGeneratorSettings.isDebug();
//			setInitialSpawn(world, iServerWorldInfo, dimensionGeneratorSettings.hasBonusChest(), bl, true);
//		}
	}
	
	@Inject(method = "setInitialSpawn", at = @At(value = "HEAD"), cancellable = true)
	private static void be_SetupSpawn(ServerLevel world, ServerLevelData serverWorldProperties,
			boolean bonusChest, boolean debugWorld, boolean bl, CallbackInfo info) 
	{
//		if (CommonConfig.swapOverworldWithEnd() && world.getDimensionKey() == World.OVERWORLD) 
//		{
//			info.cancel();
//		}
	}
}
