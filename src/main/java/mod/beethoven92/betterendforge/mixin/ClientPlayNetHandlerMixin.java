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
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.server.SOpenSignMenuPacket;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

@Mixin(ClientPlayNetHandler.class)
public class ClientPlayNetHandlerMixin {
	@Shadow
	private Minecraft minecraft;

	@Shadow
	private ClientWorld level;

	@Inject(method = "handleOpenSignEditor", at = @At(value = "HEAD"), cancellable = true)
	public void be_openSignEditor(SOpenSignMenuPacket packet, CallbackInfo info) {
		PacketThreadUtil.ensureRunningOnSameThread(packet, (ClientPlayNetHandler) (Object) this, minecraft);
		TileEntity blockEntity = this.level.getBlockEntity(packet.getPos());
		if (blockEntity instanceof ESignTileEntity) {
			ESignTileEntity sign = (ESignTileEntity) blockEntity;
			minecraft.setScreen(new BlockSignEditScreen(sign));
			info.cancel();
		}
	}

	@Inject(method = "handleBlockEntityData", at = @At(value = "HEAD"), cancellable = true)
	public void be_onEntityUpdate(SUpdateTileEntityPacket packet, CallbackInfo info) {
		PacketThreadUtil.ensureRunningOnSameThread(packet, (ClientPlayNetHandler) (Object) this, minecraft);
		BlockPos blockPos = packet.getPos();
		TileEntity blockEntity = this.minecraft.level.getBlockEntity(blockPos);
		if (blockEntity instanceof ESignTileEntity || blockEntity instanceof PedestalTileEntity) {
			blockEntity.load(this.minecraft.level.getBlockState(blockPos), packet.getTag());
			info.cancel();
		}
	}
}