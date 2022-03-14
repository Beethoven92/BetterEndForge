package mod.beethoven92.betterendforge.mixin;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.Connection;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

@Mixin(PlayerList.class)
public class PlayerListMixin 
{
	@Final
	@Shadow
	private static Logger LOGGER;
	
	@Final
	@Shadow
	private MinecraftServer server;
	
	@Final
	@Shadow
	private List<ServerPlayer> players;
	
	@Final
	@Shadow
	private Map<UUID, ServerPlayer> playersByUUID;
	
	@Final
	@Shadow
	private RegistryAccess.RegistryHolder registryHolder;

	@Shadow
	private int viewDistance;
	
	@Shadow
	public CompoundTag load(ServerPlayer player)
	{
		return null;
	}
	
	@Shadow
	public int getMaxPlayers() 
	{
		return 0;
	}
	
	@Shadow
	private void updatePlayerGameMode(ServerPlayer player, @Nullable ServerPlayer oldPlayer, ServerLevel world) {}
	
	@Shadow
	public MinecraftServer getServer() 
	{
		return null;
	}
	@Shadow
	public void sendPlayerPermissionLevel(ServerPlayer player) {}
	
	@Shadow
	protected void updateEntireScoreboard(ServerScoreboard scoreboardIn, ServerPlayer playerIn) {}
	
	@Shadow
	public void broadcastMessage(Component p_232641_1_, ChatType p_232641_2_, UUID p_232641_3_) {}
	
	@Shadow
	public void broadcastAll(Packet<?> packetIn) {}
	 
	@Shadow
	public void sendLevelInfo(ServerPlayer playerIn, ServerLevel worldIn) {}
	
	@Inject(method = "placeNewPlayer", at = @At(value = "HEAD"), cancellable = true)
	public void be_placeNewPlayer(Connection netManager, ServerPlayer playerIn, CallbackInfo info)
	{
//		if (CommonConfig.swapOverworldWithEnd())
//		{
//			GameProfile gameprofile = playerIn.getGameProfile();
//		    PlayerProfileCache playerprofilecache = this.server.getPlayerProfileCache();
//		    GameProfile gameprofile1 = playerprofilecache.getProfileByUUID(gameprofile.getId());
//		    String s = gameprofile1 == null ? gameprofile.getName() : gameprofile1.getName();
//		    playerprofilecache.addEntry(gameprofile);
//		    CompoundNBT compoundnbt = this.readPlayerDataFromFile(playerIn);
//		    RegistryKey<World> registrykey = compoundnbt != null ? DimensionType.decodeWorldKey(new Dynamic<>(NBTDynamicOps.INSTANCE, compoundnbt.get("Dimension"))).resultOrPartial(LOGGER::error).orElse(World.THE_END) : World.THE_END;
//		    ServerWorld serverworld = this.server.getWorld(registrykey);
//		    ServerWorld serverworld1;
//		    if (serverworld == null) 
//		    {
//		    	LOGGER.warn("Unknown respawn dimension {}, defaulting to overworld", (Object)registrykey);
//		        serverworld1 = this.server.overworld();
//		    } 
//		    else 
//		    {
//		    	serverworld1 = serverworld;
//		    }
//		    
//		    playerIn.setWorld(serverworld1);
//		    playerIn.interactionManager.setWorld((ServerWorld)playerIn.world);
//		    String s1 = "local";
//		    if (netManager.getRemoteAddress() != null) 
//		    {
//		    	s1 = netManager.getRemoteAddress().toString();
//		    }
//		    
//		    LOGGER.info("{}[{}] logged in with entity id {} at ({}, {}, {})", playerIn.getName().getString(), s1, playerIn.getEntityId(), playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ());
//		    IWorldInfo iworldinfo = serverworld1.getWorldInfo();
//		    this.setPlayerGameTypeBasedOnOther(playerIn, (ServerPlayerEntity)null, serverworld1);
//		    ServerPlayNetHandler serverplaynethandler = new ServerPlayNetHandler(this.server, netManager, playerIn);
//		    net.minecraftforge.fml.network.NetworkHooks.sendMCRegistryPackets(netManager, "PLAY_TO_CLIENT");
//		    GameRules gamerules = serverworld1.getGameRules();
//		    boolean flag = gamerules.getBoolean(GameRules.DO_IMMEDIATE_RESPAWN);
//		    boolean flag1 = gamerules.getBoolean(GameRules.REDUCED_DEBUG_INFO);
//		    serverplaynethandler.sendPacket(new SJoinGamePacket(playerIn.getEntityId(), playerIn.interactionManager.getGameType(), playerIn.interactionManager.getPreviousGameModeForPlayer(), BiomeManager.getHashedSeed(serverworld1.getSeed()), iworldinfo.isHardcore(), this.server.levelKeys(), this.registryHolder, serverworld1.getDimensionType(), serverworld1.getDimensionKey(), this.getMaxPlayers(), this.viewDistance, flag1, !flag, serverworld1.isDebug(), serverworld1.isFlat()));
//		    serverplaynethandler.sendPacket(new SCustomPayloadPlayPacket(SCustomPayloadPlayPacket.BRAND, (new PacketBuffer(Unpooled.buffer())).writeString(this.getServer().getServerModName())));
//		    serverplaynethandler.sendPacket(new SServerDifficultyPacket(iworldinfo.getDifficulty(), iworldinfo.isDifficultyLocked()));
//		    serverplaynethandler.sendPacket(new SPlayerAbilitiesPacket(playerIn.abilities));
//		    serverplaynethandler.sendPacket(new SHeldItemChangePacket(playerIn.inventory.currentItem));
//		    serverplaynethandler.sendPacket(new SUpdateRecipesPacket(this.server.getRecipeManager().getRecipes()));
//		    serverplaynethandler.sendPacket(new STagsListPacket(this.server.getTags()));
//		    net.minecraftforge.fml.network.NetworkHooks.syncCustomTagTypes(playerIn, this.server.getTags());
//		    this.updatePermissionLevel(playerIn);
//		    playerIn.getStats().markAllDirty();
//		    playerIn.getRecipeBook().init(playerIn);
//		    this.sendScoreboard(serverworld1.getScoreboard(), playerIn);
//		    this.server.refreshStatusNextTick();
//		    IFormattableTextComponent iformattabletextcomponent;
//		    if (playerIn.getGameProfile().getName().equalsIgnoreCase(s)) 
//		    {
//		    	iformattabletextcomponent = new TranslationTextComponent("multiplayer.player.joined", playerIn.getDisplayName());
//		    }
//		    else 
//		    {
//		    	iformattabletextcomponent = new TranslationTextComponent("multiplayer.player.joined.renamed", playerIn.getDisplayName(), s);
//		    }
//		    
//		    this.broadcastMessage(iformattabletextcomponent.mergeStyle(TextFormatting.YELLOW), ChatType.SYSTEM, Util.DUMMY_UUID);
//		    serverplaynethandler.setPlayerLocation(playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), playerIn.rotationYaw, playerIn.rotationPitch);
//		    this.players.add(playerIn);
//		    this.uuidToPlayerMap.put(playerIn.getUniqueID(), playerIn);
//		    this.sendPacketToAllPlayers(new SPlayerListItemPacket(SPlayerListItemPacket.Action.ADD_PLAYER, playerIn));
//
//		    for(int i = 0; i < this.players.size(); ++i) 
//		    {
//		    	playerIn.connection.sendPacket(new SPlayerListItemPacket(SPlayerListItemPacket.Action.ADD_PLAYER, this.players.get(i)));
//		    }
//		    
//		    serverworld1.addNewPlayer(playerIn);
//		    this.server.getCustomBossEvents().onPlayerLogin(playerIn);
//		    this.sendWorldInfo(playerIn, serverworld1);
//		    if (!this.server.getResourcePackUrl().isEmpty()) 
//		    {
//		    	playerIn.loadResourcePack(this.server.getResourcePackUrl(), this.server.getResourcePackHash());
//		    }
//
//		    for(EffectInstance effectinstance : playerIn.getActivePotionEffects()) 
//		    {
//		    	serverplaynethandler.sendPacket(new SPlayEntityEffectPacket(playerIn.getEntityId(), effectinstance));
//		    }
//		    
//		    if (compoundnbt != null && compoundnbt.contains("RootVehicle", 10)) 
//		    {
//		    	CompoundNBT compoundnbt1 = compoundnbt.getCompound("RootVehicle");
//		        Entity entity1 = EntityType.loadEntityAndExecute(compoundnbt1.getCompound("Entity"), serverworld1, (p_217885_1_) -> {
//		        	return !serverworld1.summonEntity(p_217885_1_) ? null : p_217885_1_;
//		        });
//		        if (entity1 != null) 
//		        {
//		        	UUID uuid;
//		            if (compoundnbt1.hasUniqueId("Attach")) 
//		            {
//		            	uuid = compoundnbt1.getUniqueId("Attach");
//		            } 
//		            else 
//		            {
//		            	uuid = null;
//		            }
//
//		            if (entity1.getUniqueID().equals(uuid)) 
//		            {
//		            	playerIn.startRiding(entity1, true);
//		            } 
//		            else 
//		            {
//		            	for(Entity entity : entity1.getRecursivePassengers()) 
//		            	{
//		            		if (entity.getUniqueID().equals(uuid)) 
//		            		{
//		            			playerIn.startRiding(entity, true);
//		                        break;
//		                    }
//		                }
//		            }
//
//		            if (!playerIn.isPassenger()) 
//		            {
//		            	LOGGER.warn("Couldn't reattach entity to player");
//		                serverworld1.removeEntity(entity1);
//
//		                for(Entity entity2 : entity1.getRecursivePassengers()) 
//		                {
//		                   serverworld1.removeEntity(entity2);
//		                }
//		            }
//		        }
//		    }
//		    
//		    playerIn.addSelfToInternalCraftingInventory();
//		    net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerLoggedIn( playerIn );
//		    info.cancel();
//		}
	}
}
