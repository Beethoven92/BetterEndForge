package mod.beethoven92.betterendforge.common.tileentity;

import java.util.function.Function;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ESignTileEntity extends BlockEntity {
	private final Component[] text = new Component[] { TextComponent.EMPTY, TextComponent.EMPTY,
			TextComponent.EMPTY, TextComponent.EMPTY };
	private boolean editable = true;
	private Player editor;
	private final FormattedCharSequence[] textBeingEdited = new FormattedCharSequence[4];
	private DyeColor textColor = DyeColor.BLACK;

	public ESignTileEntity(BlockPos pos, BlockState state) {
		super(ModTileEntityTypes.SIGN.get(), pos, state);
	}

	@Override
	public void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);

		for (int i = 0; i < 4; ++i) {
			String string = Component.Serializer.toJson(this.text[i]);
			tag.putString("Text" + (i + 1), string);
		}

		tag.putString("Color", this.textColor.getName());
	}

	@Override
	public void load(CompoundTag tag) {
		this.editable = false;
		super.load(tag);
		this.textColor = DyeColor.byName(tag.getString("Color"), DyeColor.BLACK);

		for (int i = 0; i < 4; ++i) {
			String string = tag.getString("Text" + (i + 1));
			Component text = Component.Serializer.fromJson(string.isEmpty() ? "\"\"" : string);
			if (this.level instanceof ServerLevel) {
				try {
					this.text[i] = ComponentUtils.updateForEntity(this.getCommandSource((ServerPlayer) null),
							text, (Entity) null, 0);
				} catch (CommandSyntaxException var7) {
					this.text[i] = text;
				}
			} else {
				this.text[i] = text;
			}

			this.textBeingEdited[i] = null;
		}

	}

	public void setTextOnRow(int row, Component text) {
		this.text[row] = text;
		this.textBeingEdited[row] = null;
	}

	@OnlyIn(Dist.CLIENT)
	public FormattedCharSequence getTextBeingEditedOnRow(int row, Function<Component, FormattedCharSequence> function) {
		if (this.textBeingEdited[row] == null && this.text[row] != null) {
			this.textBeingEdited[row] = (FormattedCharSequence) function.apply(this.text[row]);
		}

		return this.textBeingEdited[row];
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = new CompoundTag();
		this.saveAdditional(tag);
		return tag;
	}

	@Override
	public boolean onlyOpCanSetNbt() {
		return true;
	}

	public boolean isEditable() {
		return this.editable;
	}

	@OnlyIn(Dist.CLIENT)
	public void setEditable(boolean bl) {
		this.editable = bl;
		if (!bl) {
			this.editor = null;
		}

	}

	public void setEditor(Player player) {
		this.editor = player;
	}

	public Player getEditor() {
		return this.editor;
	}

	public boolean onActivate(Player player) {
		Component[] var2 = this.text;
		int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			Component text = var2[var4];
			Style style = text == null ? null : text.getStyle();
			if (style != null && style.getClickEvent() != null) {
				ClickEvent clickEvent = style.getClickEvent();
				if (clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
					player.getServer().getCommands()
							.performCommand(this.getCommandSource((ServerPlayer) player), clickEvent.getValue());
				}
			}
		}

		return true;
	}

	public CommandSourceStack getCommandSource(ServerPlayer player) {
		String string = player == null ? "Sign" : player.getName().getString();
		Component text = player == null ? new TextComponent("Sign") : player.getDisplayName();
		return new CommandSourceStack(CommandSource.NULL, Vec3.atCenterOf(this.worldPosition), Vec2.ZERO,
				(ServerLevel) this.level, 2, string, (Component) text, this.level.getServer(), player);
	}

	public DyeColor getTextColor() {
		return this.textColor;
	}

	public boolean setTextColor(DyeColor value) {
		if (value != this.getTextColor()) {
			this.textColor = value;
			this.setChanged();
			this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
			return true;
		} else {
			return false;
		}
	}
}