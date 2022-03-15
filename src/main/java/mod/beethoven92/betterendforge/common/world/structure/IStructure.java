package mod.beethoven92.betterendforge.common.world.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;

import java.util.Random;

public interface IStructure {
	public void generate(ServerLevelAccessor world, BlockPos pos, Random random);
}