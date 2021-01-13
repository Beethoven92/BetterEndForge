package mod.beethoven92.betterendforge.client.renderer;

import java.util.HashMap;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.tileentity.EChestTileEntity;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class EndChestTileEntityRenderer extends TileEntityRenderer<EChestTileEntity> {
	private static final HashMap<Block, RenderType[]> LAYERS = Maps.newHashMap();
	private static RenderType[] defaultLayer;

	private static final int ID_NORMAL = 0;
	private static final int ID_LEFT = 1;
	private static final int ID_RIGHT = 2;

	private final ModelRenderer partA;
	private final ModelRenderer partC;
	private final ModelRenderer partB;
	private final ModelRenderer partRightA;
	private final ModelRenderer partRightC;
	private final ModelRenderer partRightB;
	private final ModelRenderer partLeftA;
	private final ModelRenderer partLeftC;
	private final ModelRenderer partLeftB;

	public EndChestTileEntityRenderer(TileEntityRendererDispatcher blockEntityRenderDispatcher) {
		super(blockEntityRenderDispatcher);

		this.partC = new ModelRenderer(64, 64, 0, 19);
		this.partC.addBox(1.0F, 0.0F, 1.0F, 14.0F, 9.0F, 14.0F, 0.0F);
		this.partA = new ModelRenderer(64, 64, 0, 0);
		this.partA.addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
		this.partA.rotationPointY = 9.0F;
		this.partA.rotationPointZ = 1.0F;
		this.partB = new ModelRenderer(64, 64, 0, 0);
		this.partB.addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
		this.partB.rotationPointY = 8.0F;
		this.partRightC = new ModelRenderer(64, 64, 0, 19);
		this.partRightC.addBox(1.0F, 0.0F, 1.0F, 15.0F, 9.0F, 14.0F, 0.0F);
		this.partRightA = new ModelRenderer(64, 64, 0, 0);
		this.partRightA.addBox(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
		this.partRightA.rotationPointY = 9.0F;
		this.partRightA.rotationPointZ = 1.0F;
		this.partRightB = new ModelRenderer(64, 64, 0, 0);
		this.partRightB.addBox(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
		this.partRightB.rotationPointY = 8.0F;
		this.partLeftC = new ModelRenderer(64, 64, 0, 19);
		this.partLeftC.addBox(0.0F, 0.0F, 1.0F, 15.0F, 9.0F, 14.0F, 0.0F);
		this.partLeftA = new ModelRenderer(64, 64, 0, 0);
		this.partLeftA.addBox(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
		this.partLeftA.rotationPointY = 9.0F;
		this.partLeftA.rotationPointZ = 1.0F;
		this.partLeftB = new ModelRenderer(64, 64, 0, 0);
		this.partLeftB.addBox(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
		this.partLeftB.rotationPointY = 8.0F;
	}

	public void render(EChestTileEntity entity, float tickDelta, MatrixStack matrices, IRenderTypeBuffer vertexConsumers, int light, int overlay) {
		World world = entity.getWorld();
		boolean worldExists = world != null;
		BlockState blockState = worldExists ? entity.getBlockState() : (BlockState) Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);
		ChestType chestType = blockState.hasProperty(ChestBlock.TYPE) ? (ChestType) blockState.get(ChestBlock.TYPE) : ChestType.SINGLE;
		Block block = blockState.getBlock();
		if (entity.hasChest())
			block = entity.getChest();
		if (block instanceof AbstractChestBlock) {
			AbstractChestBlock<?> abstractChestBlock = (AbstractChestBlock<?>) block;
			boolean isDouble = chestType != ChestType.SINGLE;
			float f = ((Direction) blockState.get(ChestBlock.FACING)).getHorizontalAngle();
			TileEntityMerger.ICallbackWrapper<? extends ChestTileEntity> propertySource;

			matrices.push();
			matrices.translate(0.5D, 0.5D, 0.5D);
			matrices.rotate(Vector3f.YP.rotationDegrees(-f));
			matrices.translate(-0.5D, -0.5D, -0.5D);

			if (worldExists) {
				propertySource = abstractChestBlock.combine(blockState, world, entity.getPos(), true);
			} else {
				propertySource = TileEntityMerger.ICallback::func_225537_b_;
			}

			float pitch = ((Float2FloatFunction) propertySource.apply(ChestBlock.getLidRotationCallback((IChestLid) entity))).get(tickDelta);
			pitch = 1.0F - pitch;
			pitch = 1.0F - pitch * pitch * pitch;
			@SuppressWarnings({ "unchecked", "rawtypes" })
			int blockLight = ((Int2IntFunction) propertySource.apply(new DualBrightnessCallback())).applyAsInt(light);

			IVertexBuilder vertexConsumer = getConsumer(vertexConsumers, block, chestType);

			if (isDouble) {
				if (chestType == ChestType.LEFT) {
					renderParts(matrices, vertexConsumer, this.partLeftA, this.partLeftB, this.partLeftC, pitch, blockLight, overlay);
				} else {
					renderParts(matrices, vertexConsumer, this.partRightA, this.partRightB, this.partRightC, pitch, blockLight, overlay);
				}
			} else {
				renderParts(matrices, vertexConsumer, this.partA, this.partB, this.partC, pitch, blockLight, overlay);
			}

			matrices.pop();
		}
	}

	private void renderParts(MatrixStack matrices, IVertexBuilder vertices, ModelRenderer modelPart, ModelRenderer modelPart2, ModelRenderer modelPart3, float pitch, int light, int overlay) {
		modelPart.rotateAngleX = -(pitch * 1.5707964F);
		modelPart2.rotateAngleX = modelPart.rotateAngleX;
		modelPart.render(matrices, vertices, light, overlay);
		modelPart2.render(matrices, vertices, light, overlay);
		modelPart3.render(matrices, vertices, light, overlay);
	}

	private static RenderType getChestTexture(ChestType type, RenderType[] layers) {
		switch (type) {
		case LEFT:
			return layers[ID_LEFT];
		case RIGHT:
			return layers[ID_RIGHT];
		case SINGLE:
		default:
			return layers[ID_NORMAL];
		}
	}

	public static IVertexBuilder getConsumer(IRenderTypeBuffer provider, Block block, ChestType chestType) {
		RenderType[] layers = LAYERS.getOrDefault(block, defaultLayer);
		return provider.getBuffer(getChestTexture(chestType, layers));
	}

	static {
		defaultLayer = new RenderType[] {
				RenderType.getEntityCutout(new ResourceLocation("textures/entity/chest/normal.png")),
				RenderType.getEntityCutout(new ResourceLocation("textures/entity/chest/normal_left.png")),
				RenderType.getEntityCutout(new ResourceLocation("textures/entity/chest/normal_right.png"))
		};
		
		ModItems.ITEMS.getEntries().forEach((item) -> {
			if (item.get() instanceof BlockItem) {
				Block block = ((BlockItem) item.get()).getBlock();
				if (block instanceof ChestBlock) {
					String name = block.getRegistryName().getPath();
					LAYERS.put(block, new RenderType[] {
							RenderType.getEntityCutout(new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/chest/" + name + ".png")),
							RenderType.getEntityCutout(new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/chest/" + name + "_left.png")),
							RenderType.getEntityCutout(new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/chest/" + name + "_right.png"))
					});
				}
			}
		});
	}
}
