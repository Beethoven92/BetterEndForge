package mod.beethoven92.betterendforge.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.Category;

// TO DO: consider replacing the mixin with events and block behaviours(see grass block grow method for example)
@Mixin(BoneMealItem.class)
public abstract class BoneMealItemMixin
{
	private static final Direction[] DIR = BlockHelper.makeHorizontal();
	private static final Mutable POS = new Mutable();

	@Inject(method = "onItemUse", at = @At("HEAD"), cancellable = true)
	private void onItemUse(ItemUseContext context, CallbackInfoReturnable<ActionResultType> info) 
	{
		World world = context.getWorld();
		BlockPos blockPos = context.getPos();
		
		// FIX underwater seeds not being able to grow when using bonemeal on them
		if (BoneMealItem.applyBonemeal(context.getItem(), world, blockPos, context.getPlayer())) 
		{
	          if (!world.isRemote) 
	          {
	             world.playEvent(2005, blockPos, 0);
	          }

	          info.setReturnValue(ActionResultType.func_233537_a_(world.isRemote));
	          info.cancel();
		}
		else if (!world.isRemote()) 
		{
			BlockPos offseted = blockPos.offset(context.getFace());
			boolean endBiome = world.getBiome(offseted).getCategory() == Category.THEEND;
			
			if (world.getBlockState(blockPos).isIn(ModTags.END_GROUND)) 
			{
				boolean consume = false;
				if (world.getBlockState(blockPos).isIn(Blocks.END_STONE)) 
				{
					BlockState nylium = getNylium(world, blockPos);
					if (nylium != null)
					{
						BlockHelper.setWithoutUpdate(world, blockPos, nylium);
						consume = true;
					}
					// Cannot grow underwater plants on end stone
					if (!world.getFluidState(offseted).isEmpty() && endBiome) 
					{
						info.setReturnValue(ActionResultType.FAIL);
						info.cancel();
					}
				}
				else 
				{
					if (!world.getFluidState(offseted).isEmpty() && endBiome) 
					{
						// FIX being able to use bone meal underwater on a block where plants already grew
						if (world.getBlockState(offseted).isIn(Blocks.WATER)) consume = growWaterGrass(world, blockPos);
					}
					else if (world.getBlockState(offseted).isAir()) // FIX being able to use bone meal on a block where plants already grew
					{
						consume = growGrass(world, blockPos);
					}
				}
				if (consume) 
				{
					if (!context.getPlayer().isCreative()) context.getItem().shrink(1);
					world.playEvent(2005, blockPos, 0);
					info.setReturnValue(ActionResultType.SUCCESS);
					info.cancel();
				}
			}
			// Prevents bonemeal generating sea grass underwater in end biomes
			else if (!world.getFluidState(offseted).isEmpty() && endBiome) 
			{
				info.setReturnValue(ActionResultType.FAIL);
				info.cancel();
			}
		}
	}
	
	private boolean growGrass(World world, BlockPos pos)
	{
		int y1 = pos.getY() + 3;
		int y2 = pos.getY() - 3;
		boolean result = false;
		for (int i = 0; i < 64; i++) 
		{
			int x = (int) (pos.getX() + world.rand.nextGaussian() * 2);
			int z = (int) (pos.getZ() + world.rand.nextGaussian() * 2);
			POS.setX(x);
			POS.setZ(z);
			for (int y = y1; y >= y2; y--) 
			{
				POS.setY(y);
				BlockPos down = POS.down();
				if (world.isAirBlock(POS) && !world.isAirBlock(down)) 
				{
					BlockState grass = getGrassState(world, down);
					if (grass != null) 
					{
						BlockHelper.setWithoutUpdate(world, POS, grass);
						result = true;
					}
					break;
				}
			}
		}
		return result;
	}
	
	private boolean growWaterGrass(World world, BlockPos pos) 
	{
		int y1 = pos.getY() + 3;
		int y2 = pos.getY() - 3;
		boolean result = false;
		for (int i = 0; i < 64; i++) 
		{
			int x = (int) (pos.getX() + world.rand.nextGaussian() * 2);
			int z = (int) (pos.getZ() + world.rand.nextGaussian() * 2);
			POS.setX(x);
			POS.setZ(z);
			for (int y = y1; y >= y2; y--) 
			{
				POS.setY(y);
				BlockPos down = POS.down();
				if (world.getBlockState(POS).isIn(Blocks.WATER) && world.getBlockState(down).isIn(ModTags.END_GROUND)) 
				{
					BlockState grass = getWaterGrassState(world, down);
					if (grass != null) 
					{
						BlockHelper.setWithoutUpdate(world, POS, grass);
						result = true;
					}
					break;
				}
			}
		}
		return result;
	}
	
	private Block[] glowingGrasslandsGrass() {
		return new Block[] { ModBlocks.BLOOMING_COOKSONIA.get(), ModBlocks.VAIOLUSH_FERN.get(),
				ModBlocks.FRACTURN.get(), ModBlocks.SALTEAGO.get(), ModBlocks.CREEPING_MOSS.get(),
				ModBlocks.UMBRELLA_MOSS.get(), ModBlocks.TWISTED_UMBRELLA_MOSS.get() };
	}
	
	private BlockState getGrassState(World world, BlockPos pos) 
	{
		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		if (block == ModBlocks.END_MOSS.get() || block == ModBlocks.END_MYCELIUM.get()) 
		{				
			if (world.getBiome(pos).getRegistryName().equals(ModBiomes.GLOWING_GRASSLANDS.getID())) {
				Block[] grasses = glowingGrasslandsGrass();
				return grasses[world.rand.nextInt(grasses.length)].getDefaultState();
			} else {
				return world.rand.nextBoolean() ? ModBlocks.CREEPING_MOSS.get().getDefaultState() : ModBlocks.UMBRELLA_MOSS.get().getDefaultState();
			}
		}
		else if (block == ModBlocks.CAVE_MOSS.get()) 
		{
			return ModBlocks.CAVE_GRASS.get().getDefaultState();
		}
		else if (block == ModBlocks.CHORUS_NYLIUM.get()) 
		{
			return ModBlocks.CHORUS_GRASS.get().getDefaultState();
		}
		else if (block == ModBlocks.CRYSTAL_MOSS.get())
		{
			return ModBlocks.CRYSTAL_GRASS.get().getDefaultState();
		}
		else if (block == ModBlocks.AMBER_MOSS.get())
		{
			return ModBlocks.AMBER_GRASS.get().getDefaultState();
		}
		else if (block == ModBlocks.SHADOW_GRASS.get()) 
		{
			return ModBlocks.SHADOW_PLANT.get().getDefaultState();
		}
		else if (block == ModBlocks.PINK_MOSS.get()) 
		{
			return ModBlocks.BUSHY_GRASS.get().getDefaultState();
		}
		else if (block == ModBlocks.JUNGLE_MOSS.get())
		{
			return getRandomGrassState(world.rand, ModBlocks.TWISTED_UMBRELLA_MOSS.get().getDefaultState(),
					ModBlocks.SMALL_JELLYSHROOM.get().getDefaultState(), ModBlocks.JUNGLE_GRASS.get().getDefaultState());
		} else if (block == ModBlocks.SANGNUM.get() || block == ModBlocks.MOSSY_DRAGON_BONE.get() || block == ModBlocks.MOSSY_OBSIDIAN.get()) {
			return getRandomGrassState(world.rand, ModBlocks.GLOBULAGUS.get().getDefaultState(),
					ModBlocks.CLAWFERN.get().getDefaultState(), ModBlocks.SMALL_AMARANITA_MUSHROOM.get().getDefaultState());			
		} else if (block == ModBlocks.RUTISCUS.get()) {
			return getRandomGrassState(world.rand, ModBlocks.AERIDIUM.get().getDefaultState(),
					ModBlocks.LAMELLARIUM.get().getDefaultState(), ModBlocks.BOLUX_MUSHROOM.get().getDefaultState(), ModBlocks.ORANGO.get().getDefaultState(), ModBlocks.LUTEBUS.get().getDefaultState());			
		}
		return null;
	}
	
	private BlockState getWaterGrassState(World world, BlockPos pos) 
	{
		BetterEndBiome biome = ModBiomes.getFromBiome(world.getBiome(pos));

		if (world.rand.nextInt(16) == 0) 
		{
			return ModBlocks.CHARNIA_RED.get().getDefaultState();
		}
		else if (biome == ModBiomes.FOGGY_MUSHROOMLAND || biome == ModBiomes.MEGALAKE || biome == ModBiomes.MEGALAKE_GROVE) 
		{
			return world.rand.nextBoolean() ? ModBlocks.CHARNIA_CYAN.get().getDefaultState() : ModBlocks.CHARNIA_LIGHT_BLUE.get().getDefaultState();
		}
		else if (biome == ModBiomes.AMBER_LAND) 
		{
			return world.rand.nextBoolean() ? ModBlocks.CHARNIA_ORANGE.get().getDefaultState() : ModBlocks.CHARNIA_RED.get().getDefaultState();
		}
		else if (biome == ModBiomes.CHORUS_FOREST || biome == ModBiomes.SHADOW_FOREST) 
		{
			return ModBlocks.CHARNIA_PURPLE.get().getDefaultState();
		}
		else if (biome == ModBiomes.SULPHUR_SPRINGS)
		{
			return world.rand.nextBoolean() ? ModBlocks.CHARNIA_ORANGE.get().getDefaultState() : ModBlocks.CHARNIA_GREEN.get().getDefaultState();
		}
		else if (biome == ModBiomes.UMBRELLA_JUNGLE)
		{
			return getRandomGrassState(world.rand, ModBlocks.CHARNIA_CYAN.get().getDefaultState(), 
					ModBlocks.CHARNIA_GREEN.get().getDefaultState(), ModBlocks.CHARNIA_LIGHT_BLUE.get().getDefaultState());
		}
		else if (biome == ModBiomes.GLOWING_GRASSLANDS)
		{
			return getRandomGrassState(world.rand, ModBlocks.CHARNIA_CYAN.get().getDefaultState(), 
					ModBlocks.CHARNIA_GREEN.get().getDefaultState(), ModBlocks.CHARNIA_LIGHT_BLUE.get().getDefaultState());
		}
		return ModBlocks.CHARNIA_RED.get().getDefaultState();
		//return null;
	}

	private BlockState getRandomGrassState(Random rand, BlockState...states)
	{
		int index = rand.nextInt(states.length);
		return states[index];
	}
	
	private void shuffle(Random random) 
	{
		for (int i = 0; i < 4; i++) {
			int j = random.nextInt(4);
			Direction d = DIR[i];
			DIR[i] = DIR[j];
			DIR[j] = d;
		}
	}

	private BlockState getNylium(World world, BlockPos pos) 
	{
		shuffle(world.rand);
		for (Direction dir : DIR)
		{
			BlockState state = world.getBlockState(pos.offset(dir));
			if (BlockHelper.isEndNylium(state))
				return state;
		}
		return null;
	}
}
