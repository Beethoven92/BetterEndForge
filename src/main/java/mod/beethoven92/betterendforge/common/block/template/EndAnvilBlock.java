package mod.beethoven92.betterendforge.common.block.template;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.fml.ModList;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class EndAnvilBlock extends AnvilBlock implements EntityBlock
{
	public static final IntegerProperty DESTRUCTION = BlockProperties.DESTRUCTION;
	protected final int level;
	
	public EndAnvilBlock(Properties properties, int level) 
	{
		super(properties);
		this.level = level;
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		// Need to check if the mod is loaded and also if their anvil module is enabled, otherwise this would crash!
		if (ModList.get().isLoaded("apotheosis") && Registry.BLOCK_ENTITY_TYPE.getOptional(new ResourceLocation("apotheosis", "anvil")).isPresent())
		{
			// Need to specify apotheosis anvil tile entity as the tile entity of this block if apotheosis is present.
			// This helps fixing an incompatibility with their anvil mechanics overhaul.
			return Registry.BLOCK_ENTITY_TYPE.getOptional(new ResourceLocation("apotheosis", "anvil")).get().create(pos, state);
		}
		return null;
	}
	
	public int getCraftingLevel() 
	{
		return level;
	}
	
	public IntegerProperty getDestructionProperty() 
	{
		return DESTRUCTION;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) 
	{
		super.createBlockStateDefinition(builder);
		builder.add(DESTRUCTION);
	}
}
