package mod.beethoven92.betterendforge.common.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Lantern;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.item.DyeColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.ToolType;

public class BulbVineLanternBlock extends Lantern implements IDyedBlock {
	
	private static final VoxelShape SHAPE_CEIL = Block.box(4, 4, 4, 12, 16, 12);
	private static final VoxelShape SHAPE_FLOOR = Block.box(4, 0, 4, 12, 12, 12);

	public BulbVineLanternBlock(BlockBehaviour.Properties properties) 
	{
		super(properties.sound(SoundType.LANTERN)
				.strength(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().lightLevel(s -> 15));
	}
	
	public BulbVineLanternBlock() {
		super(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_LIGHT_GRAY).sound(SoundType.LANTERN)
				.strength(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().lightLevel(s -> 15));
	}
	
	public BulbVineLanternBlock(DyeColor color) {
		super(BlockBehaviour.Properties.of(Material.METAL, color).sound(SoundType.LANTERN)
				.strength(1).harvestTool(ToolType.PICKAXE).requiresCorrectToolForDrops().lightLevel(s -> 15));
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return state.getValue(Lantern.HANGING) ? SHAPE_CEIL : SHAPE_FLOOR;
	}
	
	@Override
	public Block createFromColor(DyeColor color) 
	{
		return new BulbVineLanternBlock(color);
	}

}
