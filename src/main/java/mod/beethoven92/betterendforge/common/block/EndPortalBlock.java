package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.interfaces.TeleportingEntity;
import mod.beethoven92.betterendforge.common.teleporter.BetterEndTeleporter;
import mod.beethoven92.betterendforge.common.teleporter.EndPortals;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Direction.AxisDirection;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraft.block.AbstractBlock.Properties;

public class EndPortalBlock extends NetherPortalBlock
{
	public static final IntegerProperty PORTAL = BlockProperties.PORTAL;
	
	public EndPortalBlock(Properties properties) 
	{
		super(properties);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) 
	{
		if (rand.nextInt(100) == 0) 
		{
			worldIn.playLocalSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
		}

		double x = pos.getX() + rand.nextDouble();
		double y = pos.getY() + rand.nextDouble();
		double z = pos.getZ() + rand.nextDouble();
		int k = rand.nextInt(2) * 2 - 1;
		if (!worldIn.getBlockState(pos.west()).is(this) && !worldIn.getBlockState(pos.east()).is(this)) 
		{
			x = pos.getX() + 0.5D + 0.25D * k;
		} else {
			z = pos.getZ() + 0.5D + 0.25D * k;
		}

		worldIn.addParticle(ModParticleTypes.PORTAL_SPHERE.get(), x, y, z, 0, 0, 0);
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) 
	{
	}
	
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		return stateIn;
	}
	
	@Override
	public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) 
	{
		if (worldIn instanceof ServerWorld && !entityIn.isPassenger() && !entityIn.isVehicle() 
				&& entityIn.canChangeDimensions()) 
		{
			TeleportingEntity teleEntity = TeleportingEntity.class.cast(entityIn);
			
			//if (teleEntity.hasCooldown()) return;
			// Checks if entity has nether portal cooldown
			if (entityIn.isOnPortalCooldown()) return;

			
			boolean isOverworld = worldIn.dimension().equals(World.OVERWORLD);
			MinecraftServer server = ((ServerWorld) worldIn).getServer();
			ServerWorld destination = isOverworld ? server.getLevel(World.END) : EndPortals.getWorld(server, state.getValue(PORTAL));
			//ServerWorld destination = ((ServerWorld) worldIn).getServer().getWorld(isOverworld ? World.THE_END : World.OVERWORLD);
	        
			if (destination == null) 
	        {
	            return;
	        }
	        
			BlockPos exitPos = this.findExitPos(destination, pos, entityIn);
			
			if (exitPos == null) 
			{	
				return;
			}
			
			if (entityIn instanceof ServerPlayerEntity) 
			{
				ServerPlayerEntity player = (ServerPlayerEntity) entityIn;
				// FIX "player moved wrongly" errors
				player.changeDimension(destination, new BetterEndTeleporter(exitPos));
		        //teleEntity.beSetCooldown(player.isCreative() ? 50 : 300);
				// Resets nether portal cooldown
		        player.setPortalCooldown();
			} 
			else 
			{
				teleEntity.beSetExitPos(exitPos);
				entityIn.changeDimension(destination);
				if (entityIn != null) 
				{
					// Resets nether portal cooldown
					entityIn.setPortalCooldown();
				}
				//teleEntity.beSetCooldown(300);
			}
		}
	}
	
	private BlockPos findExitPos(ServerWorld world, BlockPos pos, Entity entity) 
	{
		DimensionType type = world.dimensionType();

		double mult = type.coordinateScale();
		
		BlockPos.Mutable basePos;
		
		if (!world.dimension().equals(World.END))
		//if (world.getDimensionKey().equals(World.OVERWORLD)) 
		{
			basePos = pos.mutable().set(pos.getX() / mult, pos.getY(), pos.getZ() / mult);
		} 
		else 
		{
			basePos = pos.mutable().set(pos.getX() * mult, pos.getY(), pos.getZ() * mult);
		}
		
		Direction direction = Direction.EAST;
		
		BlockPos.Mutable checkPos = basePos.mutable();

		for (int step = 1; step < 128; step++)
		{
			for (int i = 0; i < (step >> 1); i++) 
			{
				IChunk chunk = world.getChunk(checkPos);
				
				if (chunk != null) 
				{
					int ceil = chunk.getHeight(Heightmap.Type.WORLD_SURFACE, checkPos.getX() & 15, checkPos.getZ() & 15);
					if (ceil > 5) 
					{
						checkPos.setY(ceil);
						while (checkPos.getY() > 5) 
						{
							BlockState state = world.getBlockState(checkPos);
							if (state.is(this))
							{
								Axis axis = state.getValue(AXIS);
								checkPos = this.findCenter(world, checkPos, axis);

								Direction frontDir = Direction.fromAxisAndDirection(axis, AxisDirection.POSITIVE).getClockWise();
								Direction entityDir = entity.getDirection();
								if (entityDir.getAxis().isVertical()) 
								{				
									entityDir = frontDir;
								}

								if (frontDir == entityDir || frontDir.getOpposite() == entityDir) 
								{
									return checkPos.relative(entityDir);
								}
								else 
								{
									entity.rotate(Rotation.CLOCKWISE_90);
									entityDir = entityDir.getClockWise();
									return checkPos.relative(entityDir);
								}
							}
							checkPos.move(Direction.DOWN);
						}
					}
				}
				checkPos.move(direction);
			}
			direction = direction.getClockWise();
		}
		return null;
	}
	
	private BlockPos.Mutable findCenter(World world, BlockPos.Mutable pos, Direction.Axis axis) 
	{
		return this.findCenter(world, pos, axis, 1);
	}
	
	private BlockPos.Mutable findCenter(World world, BlockPos.Mutable pos, Direction.Axis axis, int step) 
	{
		if (step > 8) return pos;
		
		BlockState right, left;
		Direction rightDir, leftDir;
		
		rightDir = Direction.fromAxisAndDirection(axis, AxisDirection.POSITIVE);
		leftDir = rightDir.getOpposite();
		right = world.getBlockState(pos.relative(rightDir));
		left = world.getBlockState(pos.relative(leftDir));
		
		BlockState down = world.getBlockState(pos.below());
		
		if (down.is(this)) 
		{
			return findCenter(world, pos.move(Direction.DOWN), axis, step);
		} 
		else if (right.is(this) && left.is(this)) 
		{
			return pos;
		} 
		else if (right.is(this)) 
		{
			return findCenter(world, pos.move(rightDir), axis, ++step);
		} 
		else if (left.is(this)) 
		{
			return findCenter(world, pos.move(leftDir), axis, ++step);
		}
		return pos;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		super.createBlockStateDefinition(builder);
		builder.add(PORTAL);
	}
}
