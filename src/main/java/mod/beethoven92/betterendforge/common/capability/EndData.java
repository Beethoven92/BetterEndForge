package mod.beethoven92.betterendforge.common.capability;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import mod.beethoven92.betterendforge.config.CommonConfig;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class EndData implements INBTSerializable<CompoundTag> {
	@CapabilityInject(EndData.class)
	public static final Capability<EndData> CAPABILITY = null;

	private Set<UUID> players;
	private BlockPos spawn;

	public EndData() {
		players = new HashSet<>();
	}

	private void login(ServerPlayer player) {
		if (players.contains(player.getUUID()))
			return;
		players.add(player.getUUID());

		teleportToSpawn(player);
	}

	private void teleportToSpawn(ServerPlayer player) {
		// If custom spawn point is set or config not set, get out of here
		if (player.getRespawnPosition() != null || !GeneratorOptions.swapOverworldToEnd())
			return;

		ServerLevel world = player.getLevel();
		ServerLevel end = world.getServer().getLevel(Level.END);
		if (end == null)
			return;

		if (spawn == null)
			spawn = findSpawn(end, player);
		if (spawn == null)
			return;

		if (world == end) {
			player.setPos(spawn.getX(), spawn.getY(), spawn.getZ());
		} else {
			player.changeDimension(end, new ITeleporter() {

				@Override
				public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw,
						Function<Boolean, Entity> repositionEntity) {
					return repositionEntity.apply(false);
				}

				@Override
				public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld,
						Function<ServerLevel, PortalInfo> defaultPortalInfo) {
					return new PortalInfo(Vec3.atLowerCornerOf(spawn), Vec3.ZERO, entity.yRot,
							entity.xRot);
				}
			});
		}
	}

	private BlockPos findSpawn(ServerLevel end, Player player) {
		ImmutableList<Biome> biomes = ImmutableList.of(ModBiomes.AMBER_LAND.getActualBiome(),
				ModBiomes.BLOSSOMING_SPIRES.getActualBiome(), ModBiomes.CHORUS_FOREST.getActualBiome(),
				ModBiomes.CRYSTAL_MOUNTAINS.getActualBiome(), ModBiomes.DRY_SHRUBLAND.getActualBiome(),
				ModBiomes.DUST_WASTELANDS.getActualBiome(), ModBiomes.FOGGY_MUSHROOMLAND.getActualBiome(),
				ModBiomes.GLOWING_GRASSLANDS.getActualBiome(), ModBiomes.LANTERN_WOODS.getActualBiome(),
				ModBiomes.MEGALAKE.getActualBiome(), ModBiomes.SULPHUR_SPRINGS.getActualBiome(),
				ModBiomes.UMBRELLA_JUNGLE.getActualBiome());
		for (Biome biome : biomes) {
			BlockPos pos = end.findNearestBiome(biome, BlockPos.ZERO.offset(0, 40, 0), 6400, 8);
			if (pos == null)
				continue;

			for (int i = 0; i < 40; i++) {
				BlockPos p = pos.offset(end.getRandom().nextInt(40) - 20, 0, end.getRandom().nextInt(40) - 20);
				for (int k = 0; k < 150; k++) {
					if (!end.isEmptyBlock(p.offset(0, k, 0)) && end.isEmptyBlock(p.offset(0, k + 1, 0))
							&& end.isEmptyBlock(p.offset(0, k + 2, 0)))
						return p.offset(0, k + 1, 0);
				}
			}
		}
		return null;
	}

	public static void playerLogin(ServerPlayer player) {
		Level end = player.getServer().getLevel(Level.END);
		if (end == null)
			return;
		end.getCapability(CAPABILITY).ifPresent(c -> c.login(player));
	}

	public static void playerRespawn(ServerPlayer player) {
		Level end = player.getServer().getLevel(Level.END);
		if (end == null)
			return;
		end.getCapability(CAPABILITY).ifPresent(c -> c.teleportToSpawn(player));
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		if (spawn != null)
			nbt.put("spawn", NbtUtils.writeBlockPos(spawn));
		ListTag list = new ListTag();
		for (UUID id : players)
			list.add(NbtUtils.createUUID(id));
		nbt.put("players", list);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		if (nbt.contains("spawn"))
			spawn = NbtUtils.readBlockPos(nbt.getCompound("spawn"));

		ListTag list = nbt.getList("players", Constants.NBT.TAG_INT_ARRAY);
		for (int i = 0; i < list.size(); i++)
			players.add(NbtUtils.loadUUID(list.get(i)));
	}

	@EventBusSubscriber(modid = BetterEnd.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
	public static class Provider implements ICapabilitySerializable<Tag> {

		private LazyOptional<EndData> instance = LazyOptional.of(CAPABILITY::getDefaultInstance);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return CAPABILITY.orEmpty(cap, instance);
		}

		@Override
		public Tag serializeNBT() {
			return CAPABILITY.getStorage().writeNBT(CAPABILITY,
					instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
		}

		@Override
		public void deserializeNBT(Tag nbt) {
			CAPABILITY.getStorage().readNBT(CAPABILITY,
					instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null,
					nbt);
		}

		private static final ResourceLocation LOCATION = new ResourceLocation(BetterEnd.MOD_ID, "enddata");

		@SubscribeEvent
		public static void attachCapability(AttachCapabilitiesEvent<Level> event) {
			if (event.getObject().dimension() == Level.END)
				event.addCapability(LOCATION, new Provider());
		}
	}

	public static class Storage implements IStorage<EndData> {

		@Override
		public Tag writeNBT(Capability<EndData> capability, EndData instance, Direction side) {
			return instance.serializeNBT();

		}

		@Override
		public void readNBT(Capability<EndData> capability, EndData instance, Direction side, Tag nbt) {
			instance.deserializeNBT((CompoundTag) nbt);
		}
	}

	@EventBusSubscriber(modid = BetterEnd.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
	public static class Subscriber {
		@SubscribeEvent
		public static void setup(FMLCommonSetupEvent event) {
			CapabilityManager.INSTANCE.register(EndData.class, new EndData.Storage(), EndData::new);
		}
	}
}