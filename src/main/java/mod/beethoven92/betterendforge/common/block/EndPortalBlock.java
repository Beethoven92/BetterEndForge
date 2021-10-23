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
			TeleportingEntity teleEntity = TeleportingEntity.class.cast(entityIn);
			
			//if (teleEntity.hasCooldown()) return;
			// Checks if entity has nether portal cooldown
			if (entityIn.func_242280_ah()) return;

			
			boolean isOverworld = worldIn.getDimensionKey().equals(World.OVERWORLD);
			MinecraftServer server = ((ServerWorld) worldIn).getServer();
			ServerWorld destination = isOverworld ? server.getWorld(World.THE_END) : EndPortals.getWorld(server, state.get(PORTAL));
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
		        player.func_242279_ag();
			} 
			else 
			{
				teleEntity.beSetExitPos(exitPos);
				entityIn.changeDimension(destination);
				if (entityIn != null) 
				{
					// Resets nether portal cooldown
					entityIn.func_242279_ag();
				}
				//teleEntity.beSetCooldown(300);
			}
		}
	}
	
	private BlockPos findExitPos(ServerWorld world, BlockPos pos, Entity entity) 
	{
		DimensionType type = world.getDimensionType();

		double mult = type.getCoordinateScale();
		
		BlockPos.Mutable basePos;
		
		if (!world.getDimensionKey().equals(World.THE_END))
		//if (world.getDimensionKey().equals(World.OVERWORLD)) 
		{
			basePos = pos.toMutable().setPos(pos.getX() / mult, pos.getY(), pos.getZ() / mult);
		} 
		else 
		{
			basePos = pos.toMutable().setPos(pos.getX() * mult, pos.getY(), pos.getZ() * mult);
		}
		
		Direction direction = Direction.EAST;
		
		BlockPos.Mutable checkPos = basePos.toMutable();

		for (int step = 1; step < 128; step++)
		{
			for (int i = 0; i < (step >> 1); i++) 
			{
				IChunk chunk = world.getChunk(checkPos);
				
				if (chunk != null) 
				{
					int ceil = chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE, checkPos.getX() & 15, checkPos.getZ() & 15);
					if (ceil > 5) 
					{
						checkPos.setY(ceil);
						while (checkPos.getY() > 5) 
						{
							BlockState state = world.getBlockState(checkPos);
							if (state.isIn(this))
							{
								Axis axis = state.get(AXIS);
								checkPos = this.findCenter(world, checkPos, axis);

								Direction frontDir = Direction.getFacingFromAxisDirection(axis, AxisDirection.POSITIVE).rotateY();
								Direction entityDir = entity.getHorizontalFacing();
								if (entityDir.getAxis().isVertical()) 
								{				
									entityDir = frontDir;
								}

								if (frontDir == entityDir || frontDir.getOpposite() == entityDir) 
								{
									return checkPos.offset(entityDir);
								}
								else 
								{
									entity.getRotatedYaw(Rotation.CLOCKWISE_90);
									entityDir = entityDir.rotateY();
									return checkPos.offset(entityDir);
								}
							}
							checkPos.move(Direction.DOWN);
						}
					}
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
		if (step > 8) return pos;
		
		BlockState right, left;
		Direction rightDir, leftDir;
		
		rightDir = Direction.getFacingFromAxisDirection(axis, AxisDirection.POSITIVE);
		leftDir = rightDir.getOpposite();
		right = world.getBlockState(pos.offset(rightDir));
		left = world.getBlockState(pos.offset(leftDir));
		
		BlockState down = world.getBlockState(pos.down());
		
		if (down.isIn(this)) 
		{
			return findCenter(world, pos.move(Direction.DOWN), axis, step);
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
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		super.fillStateContainer(builder);
		builder.add(PORTAL);
	}
}
