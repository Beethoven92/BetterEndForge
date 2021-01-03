package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.interfaces.ITeleportingEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EndPortalBlock extends NetherPortalBlock
{
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
			worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
		}

		double x = pos.getX() + rand.nextDouble();
		double y = pos.getY() + rand.nextDouble();
		double z = pos.getZ() + rand.nextDouble();
		int k = rand.nextInt(2) * 2 - 1;
		if (!worldIn.getBlockState(pos.west()).isIn(this) && !worldIn.getBlockState(pos.east()).isIn(this)) 
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
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) 
	{
		return stateIn;
	}
	
	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) 
	{
		if (worldIn instanceof ServerWorld && !entityIn.isPassenger() && !entityIn.isBeingRidden() 
				&& entityIn.isNonBoss()) 
		{
			ITeleportingEntity teleEntity = ITeleportingEntity.class.cast(entityIn);
			if (teleEntity.hasCooldown()) return;
			
			boolean isOverworld = worldIn.getDimensionKey().equals(World.OVERWORLD);
			ServerWorld destination = ((ServerWorld) worldIn).getServer().getWorld(isOverworld ? World.THE_END : World.OVERWORLD);
			
			//RegistryKey<World> dimension = worldIn.getDimensionKey() == World.THE_END ? World.OVERWORLD : World.THE_END;
			//boolean isOverworld = dimension.equals(World.OVERWORLD);			
			//ServerWorld destination = ((ServerWorld) worldIn).getServer().getWorld(dimension);
	        
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
				player.teleport(destination, exitPos.getX() + 0.5D, exitPos.getY(), exitPos.getZ() + 0.5D, entityIn.rotationYaw, entityIn.rotationPitch);
				teleEntity.beSetCooldown(player.isCreative() ? 50 : 300);
			} 
			else
			{
				teleEntity.beSetExitPos(exitPos);
				entityIn.changeDimension(destination);
				teleEntity.beSetCooldown(300);
			}
		}
	}
	
	private BlockPos findExitPos(ServerWorld world, BlockPos pos, Entity entity) 
	{
		//Registry<DimensionType> registry
		DimensionType type = world.getDimensionType();

		//double mult = registry.getOrDefault(DimensionType.THE_END_ID).getCoordinateScale();
		double mult = type.getCoordinateScale();
		BlockPos.Mutable basePos;
		if (world.getDimensionKey().equals(World.OVERWORLD)) 
		{
			basePos = pos.toMutable().setPos(pos.getX() / mult, pos.getY(), pos.getZ() / mult);
		} 
		else 
		{
			basePos = pos.toMutable().setPos(pos.getX() * mult, pos.getY(), pos.getZ() * mult);
		}
		Direction direction = Direction.EAST;
		BlockPos.Mutable checkPos = basePos.toMutable();
		for (int step = 1; step < 64; step++) 
		{
			for (int i = 0; i < step; i++) 
			{
				checkPos.setY(5);
				int ceil = world.getChunk(basePos).getTopBlockY(Heightmap.Type.WORLD_SURFACE, checkPos.getX(), checkPos.getZ()) + 1;
				if (ceil < 5) continue;
				
				while(checkPos.getY() < ceil) 
				{
					BlockState state = world.getBlockState(checkPos);
					if(state.isIn(this)) 
					{
						int offStep;
						checkPos = this.findCenter(world, checkPos, state.get(AXIS));
						if (state.get(AXIS).equals(Direction.Axis.X)) 
						{
							if (entity.getHorizontalFacing().getAxis() == Direction.Axis.X)
							{
								offStep = entity.getHorizontalFacing() == Direction.EAST ? 1 : -1;
								float rotation = entity.getRotatedYaw(Rotation.CLOCKWISE_90);
								entity.rotationYaw = rotation;
							} 
							else 
							{
								offStep = entity.getHorizontalFacing() == Direction.NORTH ? -1 : 1;
							}
							return checkPos.add(0, 0, offStep);
						} 
						else 
						{
							if (entity.getHorizontalFacing().getAxis() == Direction.Axis.Z)
							{
								offStep = entity.getHorizontalFacing() == Direction.SOUTH ? -1 : 1;
								float rotation = entity.getRotatedYaw(Rotation.CLOCKWISE_90);
								entity.rotationYaw = rotation;
							} 
							else 
							{
								offStep = entity.getHorizontalFacing() == Direction.EAST ? 1 : -1;
							}
							return checkPos.add(offStep, 0, 0);
						}
					}
					checkPos.move(Direction.UP);
				}
				checkPos.move(direction);
			}
			direction = direction.rotateY();
		}
		return null;
	}
	
	private BlockPos.Mutable findCenter(World world, BlockPos.Mutable pos, Direction.Axis axis) 
	{
		return this.findCenter(world, pos, axis, 1);
	}
	
	private BlockPos.Mutable findCenter(World world, BlockPos.Mutable pos, Direction.Axis axis, int step) 
	{
		if (step > 21) return pos;
		
		BlockState right, left;
		Direction rightDir, leftDir;
		if (axis == Direction.Axis.X) 
		{
			right = world.getBlockState(pos.east());
			left = world.getBlockState(pos.west());
			rightDir = Direction.EAST;
			leftDir = Direction.WEST;
		} 
		else
		{
			right = world.getBlockState(pos.south());
			left = world.getBlockState(pos.north());
			rightDir = Direction.SOUTH;
			leftDir = Direction.NORTH;
		}
		BlockState down = world.getBlockState(pos.down());
		if (down.isIn(this)) 
		{
			return findCenter(world, pos.move(Direction.DOWN), axis, ++step);
		} 
		else if (right.isIn(this) && left.isIn(this)) 
		{
			return pos;
		} 
		else if (right.isIn(this)) 
		{
			return findCenter(world, pos.move(rightDir), axis, ++step);
		} 
		else if (left.isIn(this)) 
		{
			return findCenter(world, pos.move(leftDir), axis, ++step);
		}
		return pos;
	}
}
