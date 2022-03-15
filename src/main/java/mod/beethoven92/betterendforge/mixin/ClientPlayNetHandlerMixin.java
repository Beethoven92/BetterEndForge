package mod.beethoven92.betterendforge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.beethoven92.betterendforge.client.gui.BlockSignEditScreen;
import mod.beethoven92.betterendforge.common.tileentity.ESignTileEntity;
import mod.beethoven92.betterendforge.common.tileentity.PedestalTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;

@Mixin(ClientPacketListener.class)
public class ClientPlayNetHandlerMixin {
	@Shadow
	private Minecraft minecraft;

	@Shadow
	private ClientLevel level;

	@Inject(method = "handleOpenSignEditor", at = @At(value = "HEAD"), cancellable = true)
	public void be_openSignEditor(ClientboundOpenSignEditorPacket packet, CallbackInfo info) {
		PacketUtils.ensureRunningOnSameThread(packet, (ClientPacketListener) (Object) this, minecraft);
		BlockEntity blockEntity = this.level.getBlockEntity(packet.getPos());
		if (blockEntity instanceof ESignTileEntity) {
			ESignTileEntity sign = (ESignTileEntity) blockEntity;
			minecraft.setScreen(new BlockSignEditScreen(sign));
			info.cancel();
		}
	}

	@Inject(method = "handleBlockEntityData", at = @At(value = "HEAD"), cancellable = true)
	public void be_onEntityUpdate(ClientboundBlockEntityDataPacket packet, CallbackInfo info) {
		PacketUtils.ensureRunningOnSameThread(packet, (ClientPacketListener) (Object) this, minecraft);
		BlockPos blockPos = packet.getPos();
		BlockEntity blockEntity = this.minecraft.level.getBlockEntity(blockPos);
		if (blockEntity instanceof ESignTileEntity || blockEntity instanceof PedestalTileEntity) {
			blockEntity.load(this.minecraft.level.getBlockState(blockPos), packet.getTag());
			info.cancel();
		}
	}
}