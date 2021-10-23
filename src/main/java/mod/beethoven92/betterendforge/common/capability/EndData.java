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
import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
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

public class EndData implements INBTSerializable<CompoundNBT> {
	@CapabilityInject(EndData.class)
	public static final Capability<EndData> CAPABILITY = null;

	private Set<UUID> players;
	private BlockPos spawn;

	public EndData() {
		players = new HashSet<>();
	}

	private void login(ServerPlayerEntity player) {
		if (players.contains(player.getUniqueID()))
			return;
		players.add(player.getUniqueID());

		teleportToSpawn(player);
	}

	private void teleportToSpawn(ServerPlayerEntity player) {
		// If custom spawn point is set or config not set, get out of here
		if (player.func_241140_K_() != null || !GeneratorOptions.swapOverworldToEnd())
			return;

		ServerWorld world = player.getServerWorld();
		ServerWorld end = world.getServer().getWorld(World.THE_END);
		if (end == null)
			return;

		if (spawn == null)
			spawn = findSpawn(end, player);
		if (spawn == null)
			return;

		if (world == end) {
			player.setPosition(spawn.getX(), spawn.getY(), spawn.getZ());
		} else {
			player.changeDimension(end, new ITeleporter() {

				@Override
				public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw,
						Function<Boolean, Entity> repositionEntity) {
					return repositionEntity.apply(false);
				}

				@Override
				public PortalInfo getPortalInfo(Entity entity, ServerWorld destWorld,
						Function<ServerWorld, PortalInfo> defaultPortalInfo) {
					return new PortalInfo(Vector3d.copy(spawn), Vector3d.ZERO, entity.rotationYaw,
							entity.rotationPitch);
				}
			});
		}
	}

	private BlockPos findSpawn(ServerWorld end, PlayerEntity player) {
		ImmutableList<Biome> biomes = ImmutableList.of(ModBiomes.AMBER_LAND.getActualBiome(),
				ModBiomes.BLOSSOMING_SPIRES.getActualBiome(), ModBiomes.CHORUS_FOREST.getActualBiome(),
				ModBiomes.CRYSTAL_MOUNTAINS.getActualBiome(), ModBiomes.DRY_SHRUBLAND.getActualBiome(),
				ModBiomes.DUST_WASTELANDS.getActualBiome(), ModBiomes.FOGGY_MUSHROOMLAND.getActualBiome(),
				ModBiomes.GLOWING_GRASSLANDS.getActualBiome(), ModBiomes.LANTERN_WOODS.getActualBiome(),
				ModBiomes.MEGALAKE.getActualBiome(), ModBiomes.SULPHUR_SPRINGS.getActualBiome(),
				ModBiomes.UMBRELLA_JUNGLE.getActualBiome());
		for (Biome biome : biomes) {
			BlockPos pos = end.func_241116_a_(biome, BlockPos.ZERO.add(0, 40, 0), 6400, 8);
			if (pos == null)
				continue;

			for (int i = 0; i < 40; i++) {
				BlockPos p = pos.add(end.getRandom().nextInt(40) - 20, 0, end.getRandom().nextInt(40) - 20);
				for (int k = 0; k < 150; k++) {
					if (!end.isAirBlock(p.add(0, k, 0)) && end.isAirBlock(p.add(0, k + 1, 0))
							&& end.isAirBlock(p.add(0, k + 2, 0)))
						return p.add(0, k + 1, 0);
				}
			}
		}
		return null;
	}

	public static void playerLogin(ServerPlayerEntity player) {
		World end = player.getServer().getWorld(World.THE_END);
		if (end == null)
			return;
		end.getCapability(CAPABILITY).ifPresent(c -> c.login(player));
	}

	public static void playerRespawn(ServerPlayerEntity player) {
		World end = player.getServer().getWorld(World.THE_END);
		if (end == null)
			return;
		end.getCapability(CAPABILITY).ifPresent(c -> c.teleportToSpawn(player));
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		if (spawn != null)
			nbt.put("spawn", NBTUtil.writeBlockPos(spawn));
		ListNBT list = new ListNBT();
		for (UUID id : players)
			list.add(NBTUtil.func_240626_a_(id));
		nbt.put("players", list);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		if (nbt.contains("spawn"))
			spawn = NBTUtil.readBlockPos(nbt.getCompound("spawn"));

		ListNBT list = nbt.getList("players", Constants.NBT.TAG_INT_ARRAY);
		for (int i = 0; i < list.size(); i++)
			players.add(NBTUtil.readUniqueId(list.get(i)));
	}

	@EventBusSubscriber(modid = BetterEnd.MOD_ID, bus = EventBusSubscriber.Bus.FORGE)
	public static class Provider implements ICapabilitySerializable<INBT> {

		private LazyOptional<EndData> instance = LazyOptional.of(CAPABILITY::getDefaultInstance);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return CAPABILITY.orEmpty(cap, instance);
		}

		@Override
		public INBT serializeNBT() {
			return CAPABILITY.getStorage().writeNBT(CAPABILITY,
					instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
		}

		@Override
		public void deserializeNBT(INBT nbt) {
			CAPABILITY.getStorage().readNBT(CAPABILITY,
					instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null,
					nbt);
		}

		private static final ResourceLocation LOCATION = new ResourceLocation(BetterEnd.MOD_ID, "enddata");

		@SubscribeEvent
		public static void attachCapability(AttachCapabilitiesEvent<World> event) {
			if (event.getObject().getDimensionKey() == World.THE_END)
				event.addCapability(LOCATION, new Provider());
		}
	}

	public static class Storage implements IStorage<EndData> {

		@Override
		public INBT writeNBT(Capability<EndData> capability, EndData instance, Direction side) {
			return instance.serializeNBT();

		}

		@Override
		public void readNBT(Capability<EndData> capability, EndData instance, Direction side, INBT nbt) {
			instance.deserializeNBT((CompoundNBT) nbt);
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