package mod.beethoven92.betterendforge.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class BulbVineLanternBlock extends LanternBlock implements IDyedBlock {
	
	private static final VoxelShape SHAPE_CEIL = Block.box(4, 4, 4, 12, 16, 12);
	private static final VoxelShape SHAPE_FLOOR = Block.box(4, 0, 4, 12, 12, 12);

	public BulbVineLanternBlock(AbstractBlock.Properties properties) 
	{
		super(properties.sound(SoundType.LANTERN)
				.strength(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().lightLevel(s -> 15));
	}
	
	public BulbVineLanternBlock() {
		super(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_LIGHT_GRAY).sound(SoundType.LANTERN)
				.strength(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().lightLevel(s -> 15));
	}
	
	public BulbVineLanternBlock(DyeColor color) {
		super(AbstractBlock.Properties.of(Material.METAL, color).sound(SoundType.LANTERN)
				.strength(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().lightLevel(s -> 15));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return state.getValue(LanternBlock.HANGING) ? SHAPE_CEIL : SHAPE_FLOOR;
	}
	
	@Override
	public Block createFromColor(DyeColor color) 
	{
		return new BulbVineLanternBlock(color);
	}

}
