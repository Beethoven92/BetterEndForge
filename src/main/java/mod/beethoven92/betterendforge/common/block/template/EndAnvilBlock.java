package mod.beethoven92.betterendforge.common.block.template;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fml.ModList;

public class EndAnvilBlock extends AnvilBlock
{
	public static final IntegerProperty DESTRUCTION = BlockProperties.DESTRUCTION;
	protected final int level;
	
	public EndAnvilBlock(Properties properties, int level) 
	{
		super(properties);
		this.level = level;
	}

	@Override
	public boolean hasTileEntity(BlockState state) 
	{
		if (ModList.get().isLoaded("apotheosis") && Registry.BLOCK_ENTITY_TYPE.getOptional(new ResourceLocation("apotheosis", "anvil")).isPresent()) return true;
		return false;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) 
	{
		// Need to check if the mod is loaded and also if their anvil module is enabled, otherwise this would crash!
		if (ModList.get().isLoaded("apotheosis") && Registry.BLOCK_ENTITY_TYPE.getOptional(new ResourceLocation("apotheosis", "anvil")).isPresent())
		{
			// Need to specify apotheosis anvil tile entity as the tile entity of this block if apotheosis is present.
			// This helps fixing an incompatibility with their anvil mechanics overhaul.
			return Registry.BLOCK_ENTITY_TYPE.getOptional(new ResourceLocation("apotheosis", "anvil")).get().create();
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
	protected void fillStateContainer(Builder<Block, BlockState> builder) 
	{
		super.fillStateContainer(builder);
		builder.add(DESTRUCTION);
	}
}
