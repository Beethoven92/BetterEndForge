package mod.beethoven92.betterendforge.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.chunk.listener.IChunkStatusListener;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IServerConfiguration;
import net.minecraft.world.storage.IServerWorldInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin 
{
	@Final
	@Shadow
	protected IServerConfiguration serverConfig;
	
	@Final
	@Shadow
	private Map<RegistryKey<World>, ServerWorld> worlds;
	
	@Shadow
	public PlayerList getPlayerList()
	{
		return null;
	}
	
	@Shadow
	private static void func_240786_a_(ServerWorld p_240786_0_, IServerWorldInfo p_240786_1_,
			boolean hasBonusChest, boolean p_240786_3_, boolean p_240786_4_)
	{		
	}
	
	@Inject(method = "func_241755_D_", at = @At(value = "HEAD"), cancellable = true)
	private final void be_GetOverworld(CallbackInfoReturnable<ServerWorld> info) 
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
	
	@Inject(method = "func_240787_a_", at = @At(value = "TAIL"))
	private final void be_CreateWorlds(IChunkStatusListener worldGenerationProgressListener, CallbackInfo info) 
	{
//		if (CommonConfig.swapOverworldWithEnd()) 
//		{
//			ServerWorld world = worlds.get(World.THE_END);
//			if (world == null) 
//			{
//				world = worlds.get(World.OVERWORLD);
//			}
//			this.getPlayerList().func_212504_a(world);
//		    IServerWorldInfo iServerWorldInfo = this.serverConfig.getServerWorldInfo();
//		    DimensionGeneratorSettings dimensionGeneratorSettings = this.serverConfig.getDimensionGeneratorSettings();
//			boolean bl = dimensionGeneratorSettings.func_236227_h_();
//			func_240786_a_(world, iServerWorldInfo, dimensionGeneratorSettings.hasBonusChest(), bl, true);
//		}
	}
	
	@Inject(method = "func_240786_a_", at = @At(value = "HEAD"), cancellable = true)
	private static void be_SetupSpawn(ServerWorld world, IServerWorldInfo serverWorldProperties,
			boolean bonusChest, boolean debugWorld, boolean bl, CallbackInfo info) 
	{
//		if (CommonConfig.swapOverworldWithEnd() && world.getDimensionKey() == World.OVERWORLD) 
//		{
//			info.cancel();
//		}
	}
}
