package mod.beethoven92.betterendforge.common.rituals;

import java.awt.Point;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Sets;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.EndPortalBlock;
import mod.beethoven92.betterendforge.common.block.RunedFlavoliteBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.teleporter.EndPortals;
import mod.beethoven92.betterendforge.common.tileentity.EternalPedestalTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.data.worldgen.Features;
import net.minecraft.server.level.ServerLevel;

public class EternalRitual 
{
	private final static Set<Point> STRUCTURE_MAP = Sets.newHashSet(
			new Point(-4, -5), new Point(-4, 5), new Point(-6, 0),
			new Point(4, -5), new Point(4, 5), new Point(6, 0));
	private final static Set<Point> FRAME_MAP = Sets.newHashSet(
			new Point(0, 0), new Point(0, 6), new Point(1, 0),
			new Point(1, 6), new Point(2, 1), new Point(2, 5),
			new Point(3, 2), new Point(3, 3), new Point(3, 4));
	private final static Set<Point> PORTAL_MAP = Sets.newHashSet(
			new Point(0, 0), new Point(0, 1), new Point(0, 2),
			new Point(0, 3), new Point(0, 4), new Point(1, 0),
			new Point(1, 1), new Point(1, 2), new Point(1, 3),
			new Point(1, 4), new Point(2, 1), new Point(2, 2),
			new Point(2, 3));
	private final static Set<Point> BASE_MAP = Sets.newHashSet(
			new Point(3, 0), new Point(2, 0), new Point(2, 1), new Point(1, 1),
			new Point(1, 2), new Point(0, 1), new Point(0, 2));
	
	private final static Block BASE = ModBlocks.FLAVOLITE.tiles.get();
	private final static Block PEDESTAL = ModBlocks.ETERNAL_PEDESTAL.get();
	private final static Block FRAME = ModBlocks.FLAVOLITE_RUNED_ETERNAL.get();
	private final static Block PORTAL = ModBlocks.END_PORTAL_BLOCK.get();
	private final static BooleanProperty ACTIVE = BlockProperties.ACTIVATED;
	
	private Level world;
	private Direction.Axis axis;
	private BlockPos center;
	private BlockPos exit;
	private boolean active = false;
	
	public EternalRitual(Level world) 
	{
		this.world = world;
	}
	
	public EternalRitual(Level world, BlockPos initial) 
	{
		this(world);
		this.configure(initial);
	}
	
	public void setWorld(Level world) 
	{
		this.world = world;
	}
	
	private boolean isInvalid() 
	{
		return world == null || world.isClientSide() ||
				center == null || axis == null ||
				world.dimension() == Level.NETHER;
	}
	
	public void checkStructure() 
	{
		if (isInvalid()) return;
		Direction moveX, moveY;
		if (Direction.Axis.X == axis) 
		{
			moveX = Direction.EAST;
			moveY = Direction.NORTH;
		} 
		else 
		{
			moveX = Direction.SOUTH;
			moveY = Direction.EAST;
		}
		boolean valid = this.checkFrame();
		
		Item item = null;
		
		for (Point pos : STRUCTURE_MAP) 
		{
			BlockPos.MutableBlockPos checkPos = center.mutable();
			checkPos.move(moveX, pos.x).move(moveY, pos.y);
			valid &= this.isActive(checkPos);
			
			if (valid) 
			{
				EternalPedestalTileEntity pedestal = (EternalPedestalTileEntity) world.getBlockEntity(checkPos);
				Item pItem = pedestal.getStack().getItem();
				if (item == null)
				{
					item = pItem;
				}
				else if (!item.equals(pItem)) 
				{
					valid = false;
				}
			}
		}
		/*if (valid)
		{
			this.activatePortal();
		}*/
		if (valid && item != null) 
		{
			this.activatePortal(item);
		}
	}
	
	private boolean checkFrame() 
	{
		BlockPos framePos = center.below();
		Direction moveDir = Direction.Axis.X == axis ? Direction.NORTH: Direction.EAST;
		boolean valid = true;
		for (Point point : FRAME_MAP)
		{
			BlockPos pos = framePos.mutable().move(moveDir, point.x).move(Direction.UP, point.y);
			BlockState state = world.getBlockState(pos);
			valid &= state.getBlock() instanceof RunedFlavoliteBlock;
			pos = framePos.mutable().move(moveDir, -point.x).move(Direction.UP, point.y);
			state = world.getBlockState(pos);
			valid &= state.getBlock() instanceof RunedFlavoliteBlock;
		}
		return valid;
	}
	
	public boolean isActive() 
	{
		return this.active;
	}
	
	private void activatePortal(Item item) 
	{
		if (active) return;
		//this.activatePortal(world, center);
		int state = EndPortals.getPortalState(Registry.ITEM.getKey(item));
		this.activatePortal(world, center, state);
		
		this.doEffects((ServerLevel) world, center);
		if (exit == null) 
		{
			//this.exit = this.findPortalPos();
			this.exit = this.findPortalPos(state);
		} 
		else 
		{
			//World targetWorld = this.getTargetWorld();
			Level targetWorld = this.getTargetWorld(state);
			if (targetWorld.getBlockState(exit.above()).is(ModBlocks.END_PORTAL_BLOCK.get())) 
			{
				//this.exit = this.findPortalPos();
				this.exit = this.findPortalPos(state);
			}
			else 
			{
				//this.activatePortal(targetWorld, exit);
				this.activatePortal(targetWorld, exit, state);
			}
		}
		this.active = true;
	}

	private void doEffects(ServerLevel serverWorld, BlockPos center) 
	{
		Direction moveX, moveY;
		if (Direction.Axis.X == axis) 
		{
			moveX = Direction.EAST;
			moveY = Direction.NORTH;
		} 
		else 
		{
			moveX = Direction.SOUTH;
			moveY = Direction.EAST;
		}
		for (Point pos : STRUCTURE_MAP) 
		{
			BlockPos.MutableBlockPos p = center.mutable();
			p.move(moveX, pos.x).move(moveY, pos.y);
			serverWorld.sendParticles(ParticleTypes.PORTAL, p.getX() + 0.5, p.getY() + 1.5, p.getZ() + 0.5, 20, 0, 0, 0, 1);
			serverWorld.sendParticles(ParticleTypes.REVERSE_PORTAL, p.getX() + 0.5, p.getY() + 1.5, p.getZ() + 0.5, 20, 0, 0, 0, 0.3);
		}
		serverWorld.playSound(null, center, SoundEvents.END_PORTAL_SPAWN, SoundSource.NEUTRAL, 16, 1);
	}
	
	private void activatePortal(Level world, BlockPos center, int dim) 
	{
		BlockPos framePos = center.below();
		Direction moveDir = Direction.Axis.X == axis ? Direction.NORTH: Direction.EAST;
		BlockState frame = FRAME.defaultBlockState().setValue(ACTIVE, true);
		FRAME_MAP.forEach(point -> {
			BlockPos pos = framePos.mutable().move(moveDir, point.x).move(Direction.UP, point.y);
			BlockState state = world.getBlockState(pos);
			if (state.hasProperty(ACTIVE) && !state.getValue(ACTIVE)) 
			{
				world.setBlockAndUpdate(pos, frame);
			}
			pos = framePos.mutable().move(moveDir, -point.x).move(Direction.UP, point.y);
			state = world.getBlockState(pos);
			if (state.hasProperty(ACTIVE) && !state.getValue(ACTIVE)) {
				world.setBlockAndUpdate(pos, frame);
			}
		});
		Direction.Axis portalAxis = Direction.Axis.X == axis ? Direction.Axis.Z : Direction.Axis.X;
		
		//BlockState portal = PORTAL.getDefaultState().with(EndPortalBlock.AXIS, portalAxis);
		BlockState portal = PORTAL.defaultBlockState().setValue(EndPortalBlock.AXIS, portalAxis).setValue(EndPortalBlock.PORTAL, dim);
		
		ParticleOptions effect = new BlockParticleOption(ParticleTypes.BLOCK, portal);
		ServerLevel serverWorld = (ServerLevel) world;
		
		PORTAL_MAP.forEach(point -> {
			BlockPos pos = center.mutable().move(moveDir, point.x).move(Direction.UP, point.y);
			if (!world.getBlockState(pos).is(PORTAL)) {
				world.setBlockAndUpdate(pos, portal);
				serverWorld.sendParticles(effect, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0.1);
				serverWorld.sendParticles(ParticleTypes.REVERSE_PORTAL, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0.3);
			}
			pos = center.mutable().move(moveDir, -point.x).move(Direction.UP, point.y);
			if (!world.getBlockState(pos).is(PORTAL)) 
			{
				world.setBlockAndUpdate(pos, portal);
				serverWorld.sendParticles(effect, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0.1);
				serverWorld.sendParticles(ParticleTypes.REVERSE_PORTAL, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0.3);
			}
		});
	}
	
	public void removePortal(int state) 
	{
		if (!active || isInvalid()) return;
		//World targetWorld = this.getTargetWorld();
		Level targetWorld = this.getTargetWorld(state);
		this.removePortal(world, center);
		this.removePortal(targetWorld, exit);
	}
	
	private void removePortal(Level world, BlockPos center) 
	{
		BlockPos framePos = center.below();
		Direction moveDir = Direction.Axis.X == axis ? Direction.NORTH: Direction.EAST;
		FRAME_MAP.forEach(point -> {
			BlockPos pos = framePos.mutable().move(moveDir, point.x).move(Direction.UP, point.y);
			BlockState state = world.getBlockState(pos);
			if (state.is(FRAME) && state.getValue(ACTIVE)) 
			{
				world.setBlockAndUpdate(pos, state.setValue(ACTIVE, false));
			}
			pos = framePos.mutable().move(moveDir, -point.x).move(Direction.UP, point.y);
			state = world.getBlockState(pos);
			if (state.is(FRAME) && state.getValue(ACTIVE)) 
			{
				world.setBlockAndUpdate(pos, state.setValue(ACTIVE, false));
			}
		});
		PORTAL_MAP.forEach(point -> {
			BlockPos pos = center.mutable().move(moveDir, point.x).move(Direction.UP, point.y);
			if (world.getBlockState(pos).is(PORTAL)) 
			{
				world.removeBlock(pos, false);
			}
			pos = center.mutable().move(moveDir, -point.x).move(Direction.UP, point.y);
			if (world.getBlockState(pos).is(PORTAL)) 
			{
				world.removeBlock(pos, false);
			}
		});
		this.active = false;
	}
	
	private BlockPos findPortalPos(int state) 
	{
		//ServerWorld targetWorld = (ServerWorld) this.getTargetWorld();
		ServerLevel targetWorld = (ServerLevel) this.getTargetWorld(state);
		
		DimensionType type = Objects.requireNonNull(targetWorld.dimensionType());
		double mult = type.coordinateScale();
		
		BlockPos.MutableBlockPos basePos = center.mutable().set(center.getX() / mult, center.getY(), center.getZ() / mult);
		Direction.Axis portalAxis = Direction.Axis.X == axis ? Direction.Axis.Z : Direction.Axis.X;
		if (checkIsAreaValid(targetWorld, basePos, portalAxis)) 
		{
			EternalRitual.generatePortal(targetWorld, basePos, portalAxis);
			return basePos.immutable();
		} 
		else 
		{
			Direction direction = Direction.EAST;
			BlockPos.MutableBlockPos checkPos = basePos.mutable();

			for (int step = 1; step < 128; step++) 
			{
				for (int i = 0; i < (step >> 1); i++)
				{
					ChunkAccess chunk = world.getChunk(checkPos);
					if (chunk != null)
					{
						int ceil = chunk.getHeight(Heightmap.Types.WORLD_SURFACE, checkPos.getX() & 15, checkPos.getZ() & 15) + 1;
						if (ceil < 2) continue;
						checkPos.setY(ceil);
						while (checkPos.getY() > 2) 
						{
							if(checkIsAreaValid(targetWorld, checkPos, portalAxis)) 
							{
								EternalRitual.generatePortal(targetWorld, checkPos, portalAxis);
								return checkPos.immutable();
							}
							checkPos.move(Direction.DOWN);
						}
					}
					checkPos.move(direction);
				}
				direction = direction.getClockWise();
			}
		}
		
		if (targetWorld.dimension() == Level.END) 
		{
			Features.END_ISLAND.place(targetWorld, targetWorld.getChunkSource().getGenerator(), new Random(basePos.asLong()), basePos.below());
		} 
		else 
		{
			basePos.setY(targetWorld.getChunk(basePos).getHeight(Heightmap.Types.WORLD_SURFACE, basePos.getX(), basePos.getZ()) + 1);
			ModConfiguredFeatures.OVERWORLD_ISLAND.place(targetWorld, targetWorld.getChunkSource().getGenerator(), new Random(basePos.asLong()), basePos.below());
		}
		
		EternalRitual.generatePortal(targetWorld, basePos, portalAxis);
		
		return basePos.immutable();
	}
	
	private Level getTargetWorld(int state) 
	{
		//RegistryKey<World> target = world.getDimensionKey() == World.THE_END ? World.OVERWORLD : World.THE_END;
		//return Objects.requireNonNull(world.getServer()).getWorld(target);
		if (world.dimension() == Level.END) 
		{
			return EndPortals.getWorld(world.getServer(), state);
		}
		return Objects.requireNonNull(world.getServer()).getLevel(Level.END);
	}
	
	private boolean checkIsAreaValid(Level world, BlockPos pos, Direction.Axis axis) 
	{
		if (!isBaseValid(world, pos, axis)) return false;
		return EternalRitual.checkArea(world, pos, axis);
	}
	
	private boolean isBaseValid(Level world, BlockPos pos, Direction.Axis axis) 
	{
		boolean solid = true;
		if (axis.equals(Direction.Axis.X)) 
		{
			pos = pos.below().offset(0, 0, -3);
			for (int i = 0; i < 7; i++) 
			{
				BlockPos checkPos = pos.offset(0, 0, i);
				BlockState state = world.getBlockState(checkPos);
				solid &= this.validBlock(world, checkPos, state);
			}
		} 
		else 
		{
			pos = pos.below().offset(-3, 0, 0);
			for (int i = 0; i < 7; i++) 
			{
				BlockPos checkPos = pos.offset(i, 0, 0);
				BlockState state = world.getBlockState(checkPos);
				solid &= this.validBlock(world, checkPos, state);
			}
		}
		return solid;
	}
	
	private boolean validBlock(Level world, BlockPos pos, BlockState state) 
	{
		return state.isRedstoneConductor(world, pos) && state.isSolidRender(world, pos);
	}
	
	public static void generatePortal(Level world, BlockPos center, Direction.Axis axis) 
	{
		BlockPos framePos = center.below();
		Direction moveDir = Direction.Axis.X == axis ? Direction.EAST: Direction.NORTH;
		BlockState frame = FRAME.defaultBlockState().setValue(ACTIVE, true);
		FRAME_MAP.forEach(point -> {
			BlockPos pos = framePos.mutable().move(moveDir, point.x).move(Direction.UP, point.y);
			world.setBlockAndUpdate(pos, frame);
			pos = framePos.mutable().move(moveDir, -point.x).move(Direction.UP, point.y);
			world.setBlockAndUpdate(pos, frame);
		});
		BlockState portal = PORTAL.defaultBlockState().setValue(EndPortalBlock.AXIS, axis);
		PORTAL_MAP.forEach(point -> {
			BlockPos pos = center.mutable().move(moveDir, point.x).move(Direction.UP, point.y);
			world.setBlockAndUpdate(pos, portal);
			pos = center.mutable().move(moveDir, -point.x).move(Direction.UP, point.y);
			world.setBlockAndUpdate(pos, portal);
		});
		generateBase(world, framePos, moveDir);
	}
	
	private static void generateBase(Level world, BlockPos center, Direction moveX) 
	{
		BlockState base = BASE.defaultBlockState();
		Direction moveY = moveX.getClockWise();
		BASE_MAP.forEach(point -> {
			BlockPos pos = center.mutable().move(moveX, point.x).move(moveY, point.y);
			world.setBlockAndUpdate(pos, base);
			pos = center.mutable().move(moveX, -point.x).move(moveY, point.y);
			world.setBlockAndUpdate(pos, base);
			pos = center.mutable().move(moveX, point.x).move(moveY, -point.y);
			world.setBlockAndUpdate(pos, base);
			pos = center.mutable().move(moveX, -point.x).move(moveY, -point.y);
			world.setBlockAndUpdate(pos, base);
		});
	}
	
	public static boolean checkArea(Level world, BlockPos center, Direction.Axis axis) 
	{
		Direction moveDir = Direction.Axis.X == axis ? Direction.NORTH: Direction.EAST;
		for (BlockPos checkPos : BlockPos.betweenClosed(center.relative(moveDir.getClockWise()), center.relative(moveDir.getCounterClockWise()))) 
		{
			for (Point point : PORTAL_MAP) 
			{
				BlockPos pos = checkPos.mutable().move(moveDir, point.x).move(Direction.UP, point.y);
				BlockState state = world.getBlockState(pos);

				if (isStateInvalid(state)) return false;
				
				pos = checkPos.mutable().move(moveDir, -point.x).move(Direction.UP, point.y);
				state = world.getBlockState(pos);
				
				if (isStateInvalid(state)) return false;
			}
		}
		return true;
	}
	
	private static boolean isStateInvalid(BlockState state) 
	{
		if (!state.getFluidState().isEmpty()) return true;
		Material material = state.getMaterial();
		return !material.isReplaceable() && !material.equals(Material.PLANT);
	}
	
	public void configure(BlockPos initial) 
	{
		BlockPos checkPos = initial.east(12);
		if (this.hasPedestal(checkPos)) 
		{
			this.axis = Direction.Axis.X;
			this.center = initial.east(6);
			return;
		}
		checkPos = initial.west(12);
		if (this.hasPedestal(checkPos)) 
		{
			this.axis = Direction.Axis.X;
			this.center = initial.west(6);
			return;
		}
		checkPos = initial.south(12);
		if (this.hasPedestal(checkPos)) 
		{
			this.axis = Direction.Axis.Z;
			this.center = initial.south(6);
			return;
		}
		checkPos = initial.north(12);
		if (this.hasPedestal(checkPos)) 
		{
			this.axis = Direction.Axis.Z;
			this.center = initial.north(6);
			return;
		}
		checkPos = initial.north(10);
		if (this.hasPedestal(checkPos)) 
		{
			this.axis = Direction.Axis.X;
			checkPos = checkPos.east(8);
			if (this.hasPedestal(checkPos)) 
			{
				this.center = initial.north(5).east(4);
			} 
			else 
			{
				this.center = initial.north(5).west(4);
			}
			return;
		}
		checkPos = initial.south(10);
		if (this.hasPedestal(checkPos)) 
		{
			this.axis = Direction.Axis.X;
			checkPos = checkPos.east(8);
			if (this.hasPedestal(checkPos)) 
			{
				this.center = initial.south(5).east(4);
			} 
			else
			{
				this.center = initial.south(5).west(4);
			}
			return;
		}
		checkPos = initial.east(10);
		if (this.hasPedestal(checkPos)) 
		{
			this.axis = Direction.Axis.Z;
			checkPos = checkPos.south(8);
			if (this.hasPedestal(checkPos)) 
			{
				this.center = initial.east(5).south(4);
			} 
			else
			{
				this.center = initial.east(5).north(4);
			}
			return;
		}
		checkPos = initial.west(10);
		if (this.hasPedestal(checkPos)) 
		{
			this.axis = Direction.Axis.Z;
			checkPos = checkPos.south(8);
			if (this.hasPedestal(checkPos)) 
			{
				this.center = initial.west(5).south(4);
			} 
			else
			{
				this.center = initial.west(5).north(4);
			}
			return;
		}
	}
	
	private boolean hasPedestal(BlockPos pos) 
	{
		return world.getBlockState(pos).is(PEDESTAL);
	}
	
	private boolean isActive(BlockPos pos)
	{
		BlockState state = world.getBlockState(pos);
		if (state.is(PEDESTAL)) 
		{
			EternalPedestalTileEntity pedestal = (EternalPedestalTileEntity) world.getBlockEntity(pos);
			
			assert pedestal != null;
			
			if (!pedestal.hasRitual()) 
			{
				pedestal.linkRitual(this);
			}
			return state.getValue(ACTIVE);
		}
		return false;
	}
	
	public CompoundTag write(CompoundTag compound) 
	{
		compound.put("center", NbtUtils.writeBlockPos((center)));
		if (exit != null) {
			compound.put("exit", NbtUtils.writeBlockPos(exit));
		}
		compound.putString("axis", axis.getName());
		compound.putBoolean("active", active);
		return compound;
	}
	
	public void read(CompoundTag nbt) 
	{
		this.axis = Direction.Axis.byName(nbt.getString("axis"));
		this.center = NbtUtils.readBlockPos(nbt.getCompound("center"));
		this.active = nbt.getBoolean("active");
		if (nbt.contains("exit")) {
			this.exit = NbtUtils.readBlockPos(nbt.getCompound("exit"));
		}
	}
}
