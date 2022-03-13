package mod.beethoven92.betterendforge.mixin;

import java.util.Random;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.StructureHelper;
import mod.beethoven92.betterendforge.common.util.WorldDataAPI;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraftforge.fml.loading.FMLLoader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.EndPodiumFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

@Mixin(EndPodiumFeature.class)
public abstract class EndPodiumFeatureMixin
{
	@Shadow @Final private boolean active;

	@Inject(method = "place", at = @At("HEAD"), cancellable = true)
	private void be_placePortal(ISeedReader level, ChunkGenerator chunkGenerator, Random random, BlockPos origin,
								  NoFeatureConfig config, CallbackInfoReturnable<Boolean> info)
	{
		if (!GeneratorOptions.hasPortal())
		{
			info.setReturnValue(false);
			info.cancel();
		}

		else if (GeneratorOptions.replacePortal() && FMLLoader.getLoadingModList().getModFileById("endergetic") == null) {
			ISeedReader world = level;
			BlockPos blockPos = be_updatePos(origin, world);
			Template structure = StructureHelper.readStructure(BetterEnd.makeID(active ? "portal/end_portal_active" : "portal/end_portal_inactive"));
			Vector3i size = structure.getSize();
			blockPos = blockPos.offset(-(size.getX() >> 1), -1, -(size.getZ() >> 1));
			structure.placeInWorld(world, blockPos, blockPos, new PlacementSettings(), random, 2);
			info.setReturnValue(true);
			info.cancel();
		}
	}


	private BlockPos be_updatePos(BlockPos blockPos, ISeedReader world) {
		if (GeneratorOptions.useNewGenerator()) {
			BlockPos pos = GeneratorOptions.getPortalPos();
			if (pos.equals(BlockPos.ZERO)) {
				int y = world.getChunk(0, 0, ChunkStatus.FULL).getHeight(Heightmap.Type.WORLD_SURFACE, blockPos.getX(), blockPos.getZ());
				if (y < 1) {
					y = 65;
				}
				pos = new BlockPos(pos.getX(), y, pos.getZ());
				GeneratorOptions.setPortalPos(pos);
				WorldDataAPI.getRootTag(BetterEnd.MOD_ID).put("portal", NBTUtil.writeBlockPos(pos));
			}
			return pos;
		}
		return blockPos;
	}
}