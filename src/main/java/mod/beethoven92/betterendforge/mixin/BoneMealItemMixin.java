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

	@Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
	private void be_useOn(ItemUseContext context, CallbackInfoReturnable<ActionResultType> info)
	{
		World world = context.getLevel();
		BlockPos blockPos = context.getClickedPos();
		
		// FIX underwater seeds not being able to grow when using bonemeal on them
		if (BoneMealItem.applyBonemeal(context.getItemInHand(), world, blockPos, context.getPlayer())) 
		{
	          if (!world.isClientSide) 
	          {
	             world.levelEvent(2005, blockPos, 0);
	          }

	          info.setReturnValue(ActionResultType.sidedSuccess(world.isClientSide));
	          info.cancel();
		}
		else if (!world.isClientSide()) 
		{
			BlockPos offseted = blockPos.relative(context.getClickedFace());
			boolean endBiome = world.getBiome(offseted).getBiomeCategory() == Category.THEEND;
			
			if (world.getBlockState(blockPos).is(ModTags.END_GROUND)) 
			{
				boolean consume = false;
				if (world.getBlockState(blockPos).is(Blocks.END_STONE)) 
				{
					BlockState nylium = be_getNylium(world, blockPos);
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
						if (world.getBlockState(offseted).is(Blocks.WATER)) consume = be_growWaterGrass(world, blockPos);
					}
					else if (world.getBlockState(offseted).isAir()) // FIX being able to use bone meal on a block where plants already grew
					{
						consume = be_growGrass(world, blockPos);
					}
				}
				if (consume) 
				{
					if (!context.getPlayer().isCreative()) context.getItemInHand().shrink(1);
					world.levelEvent(2005, blockPos, 0);
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
	
	private boolean be_growGrass(World world, BlockPos pos)
	{
		int y1 = pos.getY() + 3;
		int y2 = pos.getY() - 3;
		boolean result = false;
		for (int i = 0; i < 64; i++) 
		{
			int x = (int) (pos.getX() + world.random.nextGaussian() * 2);
			int z = (int) (pos.getZ() + world.random.nextGaussian() * 2);
			POS.setX(x);
			POS.setZ(z);
			for (int y = y1; y >= y2; y--) 
			{
				POS.setY(y);
				BlockPos down = POS.below();
				if (world.isEmptyBlock(POS) && !world.isEmptyBlock(down)) 
				{
					BlockState grass = be_getGrassState(world, down);
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
	
	private boolean be_growWaterGrass(World world, BlockPos pos)
	{
		int y1 = pos.getY() + 3;
		int y2 = pos.getY() - 3;
		boolean result = false;
		for (int i = 0; i < 64; i++) 
		{
			int x = (int) (pos.getX() + world.random.nextGaussian() * 2);
			int z = (int) (pos.getZ() + world.random.nextGaussian() * 2);
			POS.setX(x);
			POS.setZ(z);
			for (int y = y1; y >= y2; y--) 
			{
				POS.setY(y);
				BlockPos down = POS.below();
				if (world.getBlockState(POS).is(Blocks.WATER) && world.getBlockState(down).is(ModTags.END_GROUND)) 
				{
					BlockState grass = be_getWaterGrassState(world, down);
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
	
	private Block[] be_glowingGrasslandsGrass() {
		return new Block[] { ModBlocks.BLOOMING_COOKSONIA.get(), ModBlocks.VAIOLUSH_FERN.get(),
				ModBlocks.FRACTURN.get(), ModBlocks.SALTEAGO.get(), ModBlocks.CREEPING_MOSS.get(),
				ModBlocks.UMBRELLA_MOSS.get(), ModBlocks.TWISTED_UMBRELLA_MOSS.get() };
	}
	
	private BlockState be_getGrassState(World world, BlockPos pos)
	{
		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		if (block == ModBlocks.END_MOSS.get() || block == ModBlocks.END_MYCELIUM.get()) 
		{				
			if (world.getBiome(pos).getRegistryName().equals(ModBiomes.GLOWING_GRASSLANDS.getID())) {
				Block[] grasses = be_glowingGrasslandsGrass();
				return grasses[world.random.nextInt(grasses.length)].defaultBlockState();
			} else {
				return world.random.nextBoolean() ? ModBlocks.CREEPING_MOSS.get().defaultBlockState() : ModBlocks.UMBRELLA_MOSS.get().defaultBlockState();
			}
		}
		else if (block == ModBlocks.CAVE_MOSS.get()) 
		{
			return ModBlocks.CAVE_GRASS.get().defaultBlockState();
		}
		else if (block == ModBlocks.CHORUS_NYLIUM.get()) 
		{
			return ModBlocks.CHORUS_GRASS.get().defaultBlockState();
		}
		else if (block == ModBlocks.CRYSTAL_MOSS.get())
		{
			return ModBlocks.CRYSTAL_GRASS.get().defaultBlockState();
		}
		else if (block == ModBlocks.AMBER_MOSS.get())
		{
			return ModBlocks.AMBER_GRASS.get().defaultBlockState();
		}
		else if (block == ModBlocks.SHADOW_GRASS.get()) 
		{
			return ModBlocks.SHADOW_PLANT.get().defaultBlockState();
		}
		else if (block == ModBlocks.PINK_MOSS.get()) 
		{
			return ModBlocks.BUSHY_GRASS.get().defaultBlockState();
		}
		else if (block == ModBlocks.JUNGLE_MOSS.get())
		{
			return be_getRandomGrassState(world.random, ModBlocks.TWISTED_UMBRELLA_MOSS.get().defaultBlockState(),
					ModBlocks.SMALL_JELLYSHROOM.get().defaultBlockState(), ModBlocks.JUNGLE_GRASS.get().defaultBlockState());
		} else if (block == ModBlocks.SANGNUM.get() || block == ModBlocks.MOSSY_DRAGON_BONE.get() || block == ModBlocks.MOSSY_OBSIDIAN.get()) {
			return be_getRandomGrassState(world.random, ModBlocks.GLOBULAGUS.get().defaultBlockState(),
					ModBlocks.CLAWFERN.get().defaultBlockState(), ModBlocks.SMALL_AMARANITA_MUSHROOM.get().defaultBlockState());			
		} else if (block == ModBlocks.RUTISCUS.get()) {
			return be_getRandomGrassState(world.random, ModBlocks.AERIDIUM.get().defaultBlockState(),
					ModBlocks.LAMELLARIUM.get().defaultBlockState(), ModBlocks.BOLUX_MUSHROOM.get().defaultBlockState(), ModBlocks.ORANGO.get().defaultBlockState(), ModBlocks.LUTEBUS.get().defaultBlockState());			
		}
		return null;
	}
	
	private BlockState be_getWaterGrassState(World world, BlockPos pos)
	{
		BetterEndBiome biome = ModBiomes.getFromBiome(world.getBiome(pos));

		if (world.random.nextInt(16) == 0) 
		{
			return ModBlocks.CHARNIA_RED.get().defaultBlockState();
		}
		else if (biome == ModBiomes.FOGGY_MUSHROOMLAND || biome == ModBiomes.MEGALAKE || biome == ModBiomes.MEGALAKE_GROVE) 
		{
			return world.random.nextBoolean() ? ModBlocks.CHARNIA_CYAN.get().defaultBlockState() : ModBlocks.CHARNIA_LIGHT_BLUE.get().defaultBlockState();
		}
		else if (biome == ModBiomes.AMBER_LAND) 
		{
			return world.random.nextBoolean() ? ModBlocks.CHARNIA_ORANGE.get().defaultBlockState() : ModBlocks.CHARNIA_RED.get().defaultBlockState();
		}
		else if (biome == ModBiomes.CHORUS_FOREST || biome == ModBiomes.SHADOW_FOREST) 
		{
			return ModBlocks.CHARNIA_PURPLE.get().defaultBlockState();
		}
		else if (biome == ModBiomes.SULPHUR_SPRINGS)
		{
			return world.random.nextBoolean() ? ModBlocks.CHARNIA_ORANGE.get().defaultBlockState() : ModBlocks.CHARNIA_GREEN.get().defaultBlockState();
		}
		else if (biome == ModBiomes.UMBRELLA_JUNGLE)
		{
			return be_getRandomGrassState(world.random, ModBlocks.CHARNIA_CYAN.get().defaultBlockState(),
					ModBlocks.CHARNIA_GREEN.get().defaultBlockState(), ModBlocks.CHARNIA_LIGHT_BLUE.get().defaultBlockState());
		}
		else if (biome == ModBiomes.GLOWING_GRASSLANDS)
		{
			return be_getRandomGrassState(world.random, ModBlocks.CHARNIA_CYAN.get().defaultBlockState(),
					ModBlocks.CHARNIA_GREEN.get().defaultBlockState(), ModBlocks.CHARNIA_LIGHT_BLUE.get().defaultBlockState());
		}
		return ModBlocks.CHARNIA_RED.get().defaultBlockState();
		//return null;
	}

	private BlockState be_getRandomGrassState(Random rand, BlockState...states)
	{
		int index = rand.nextInt(states.length);
		return states[index];
	}
	
	private void be_shuffle(Random random)
	{
		for (int i = 0; i < 4; i++) {
			int j = random.nextInt(4);
			Direction d = DIR[i];
			DIR[i] = DIR[j];
			DIR[j] = d;
		}
	}

	private BlockState be_getNylium(World world, BlockPos pos)
	{
		be_shuffle(world.random);
		for (Direction dir : DIR)
		{
			BlockState state = world.getBlockState(pos.relative(dir));
			if (BlockHelper.isEndNylium(state))
				return state;
		}
		return null;
	}
}
