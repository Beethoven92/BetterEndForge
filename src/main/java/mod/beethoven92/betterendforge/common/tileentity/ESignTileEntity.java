package mod.beethoven92.betterendforge.common.tileentity;

import java.util.function.Function;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ESignTileEntity extends TileEntity {
	private final ITextComponent[] text = new ITextComponent[] { StringTextComponent.EMPTY, StringTextComponent.EMPTY,
			StringTextComponent.EMPTY, StringTextComponent.EMPTY };
	private boolean editable = true;
	private PlayerEntity editor;
	private final IReorderingProcessor[] textBeingEdited = new IReorderingProcessor[4];
	private DyeColor textColor = DyeColor.BLACK;

	public ESignTileEntity() {
		super(ModTileEntityTypes.SIGN.get());
	}

	@Override
	public CompoundNBT save(CompoundNBT tag) {
		super.save(tag);

		for (int i = 0; i < 4; ++i) {
			String string = ITextComponent.Serializer.toJson(this.text[i]);
			tag.putString("Text" + (i + 1), string);
		}

		tag.putString("Color", this.textColor.getName());
		return tag;
	}

	@Override
	public void load(BlockState state, CompoundNBT tag) {
		this.editable = false;
		super.load(state, tag);
		this.textColor = DyeColor.byName(tag.getString("Color"), DyeColor.BLACK);

		for (int i = 0; i < 4; ++i) {
			String string = tag.getString("Text" + (i + 1));
			ITextComponent text = ITextComponent.Serializer.fromJson(string.isEmpty() ? "\"\"" : string);
			if (this.level instanceof ServerWorld) {
				try {
					this.text[i] = TextComponentUtils.updateForEntity(this.getCommandSource((ServerPlayerEntity) null),
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

	public void setTextOnRow(int row, ITextComponent text) {
		this.text[row] = text;
		this.textBeingEdited[row] = null;
	}

	@OnlyIn(Dist.CLIENT)
	public IReorderingProcessor getTextBeingEditedOnRow(int row, Function<ITextComponent, IReorderingProcessor> function) {
		if (this.textBeingEdited[row] == null && this.text[row] != null) {
			this.textBeingEdited[row] = (IReorderingProcessor) function.apply(this.text[row]);
		}

		return this.textBeingEdited[row];
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.worldPosition, 9, this.getUpdateTag());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.save(new CompoundNBT());
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

	public void setEditor(PlayerEntity player) {
		this.editor = player;
	}

	public PlayerEntity getEditor() {
		return this.editor;
	}

	public boolean onActivate(PlayerEntity player) {
		ITextComponent[] var2 = this.text;
		int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			ITextComponent text = var2[var4];
			Style style = text == null ? null : text.getStyle();
			if (style != null && style.getClickEvent() != null) {
				ClickEvent clickEvent = style.getClickEvent();
				if (clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
					player.getServer().getCommands()
							.performCommand(this.getCommandSource((ServerPlayerEntity) player), clickEvent.getValue());
				}
			}
		}

		return true;
	}

	public CommandSource getCommandSource(ServerPlayerEntity player) {
		String string = player == null ? "Sign" : player.getName().getString();
		ITextComponent text = player == null ? new StringTextComponent("Sign") : player.getDisplayName();
		return new CommandSource(ICommandSource.NULL, Vector3d.atCenterOf(this.worldPosition), Vector2f.ZERO,
				(ServerWorld) this.level, 2, string, (ITextComponent) text, this.level.getServer(), player);
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