package mod.beethoven92.betterendforge.mixin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.beethoven92.betterendforge.common.tileentity.ESignTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.network.play.client.CUpdateSignPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;

@Mixin(ServerPlayNetHandler.class)
public class ServerPlayNetHandlerMixin {
	@Shadow
	private static final Logger LOGGER = LogManager.getLogger();

	@Shadow
	public ServerPlayerEntity player;

	@Inject(method = "processUpdateSign", at = @At(value = "HEAD"), cancellable = true)
	private void be_signUpdate(CUpdateSignPacket packet, CallbackInfo info) {
		PacketThreadUtil.ensureRunningOnSameThread(packet, ServerPlayNetHandler.class.cast(this), this.player.getLevel());
		this.player.resetLastActionTime();
		ServerWorld serverWorld = this.player.getLevel();
		BlockPos blockPos = packet.getPos();
		if (serverWorld.hasChunkAt(blockPos)) {
			BlockState blockState = serverWorld.getBlockState(blockPos);
			TileEntity blockEntity = serverWorld.getBlockEntity(blockPos);
			if (blockEntity instanceof ESignTileEntity) {
				ESignTileEntity signBlockEntity = (ESignTileEntity) blockEntity;
				if (!signBlockEntity.isEditable() || signBlockEntity.getEditor() != this.player) {
					LOGGER.warn("Player {} just tried to change non-editable sign", this.player.getName().getString());
					return;
				}

				String[] strings = packet.getLines();

				for (int i = 0; i < strings.length; ++i) {
					signBlockEntity.setTextOnRow(i, new StringTextComponent(TextFormatting.stripFormatting(strings[i])));
				}

				signBlockEntity.setChanged();
				serverWorld.sendBlockUpdated(blockPos, blockState, blockState, 3);

				info.cancel();
			}
		}
	}
}
