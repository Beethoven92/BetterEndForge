package mod.beethoven92.betterendforge.client.renderer;

import java.util.HashMap;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.tileentity.EChestTileEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.Level;

public class EndChestTileEntityRenderer implements BlockEntityRenderer<EChestTileEntity> {
	private static final HashMap<Block, RenderType[]> LAYERS = Maps.newHashMap();
	private static RenderType[] defaultLayer;

	private static final int ID_NORMAL = 0;
	private static final int ID_LEFT = 1;
	private static final int ID_RIGHT = 2;

	public final ModelPart lid;
	public final ModelPart bottom;
	public final ModelPart lock;
	public final ModelPart doubleLeftLid;
	public final ModelPart doubleLeftBottom;
	public final ModelPart doubleLeftLock;
	public final ModelPart doubleRightLid;
	public final ModelPart doubleRightBottom;
	public final ModelPart doubleRightLock;

	public EndChestTileEntityRenderer(BlockEntityRendererProvider.Context ctx) {
		super();

		ModelPart modelpart = ctx.bakeLayer(ModelLayers.CHEST);
		this.bottom = modelpart.getChild("bottom");
		this.lid = modelpart.getChild("lid");
		this.lock = modelpart.getChild("lock");
		ModelPart modelpart1 = ctx.bakeLayer(ModelLayers.DOUBLE_CHEST_LEFT);
		this.doubleLeftBottom = modelpart1.getChild("bottom");
		this.doubleLeftLid = modelpart1.getChild("lid");
		this.doubleLeftLock = modelpart1.getChild("lock");
		ModelPart modelpart2 = ctx.bakeLayer(ModelLayers.DOUBLE_CHEST_RIGHT);
		this.doubleRightBottom = modelpart2.getChild("bottom");
		this.doubleRightLid = modelpart2.getChild("lid");
		this.doubleRightLock = modelpart2.getChild("lock");
	}

	public void render(EChestTileEntity chest, float tickDelta, PoseStack matrix, MultiBufferSource vertexConsumers, int light, int overlay) {
		Level world = chest.getLevel();
		boolean worldExists = world != null;
		BlockState blockState = worldExists ? chest.getBlockState() : Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
		ChestType chestType = blockState.hasProperty(ChestBlock.TYPE) ? blockState.getValue(ChestBlock.TYPE) : ChestType.SINGLE;
		Block block = blockState.getBlock();
		if (chest.hasChest())
			block = chest.getChest();
		if (block instanceof AbstractChestBlock<?> abstractChestBlock) {
			boolean isDouble = chestType != ChestType.SINGLE;

			float f = blockState.getValue(ChestBlock.FACING).toYRot();
			DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> propertySource;

			matrix.pushPose();
			matrix.translate(0.5D, 0.5D, 0.5D);
			matrix.mulPose(Vector3f.YP.rotationDegrees(-f));
			matrix.translate(-0.5D, -0.5D, -0.5D);

			if (worldExists) {
				propertySource = abstractChestBlock.combine(blockState, world, chest.getBlockPos(), true);
			} else {
				propertySource = DoubleBlockCombiner.Combiner::acceptNone;
			}

			float pitch = propertySource.apply(ChestBlock.opennessCombiner(chest)).get(tickDelta);
			pitch = 1.0F - pitch;
			pitch = 1.0F - pitch * pitch * pitch;
			@SuppressWarnings({ "unchecked", "rawtypes" })
			int blockLight = ((Int2IntFunction) propertySource.apply(new BrightnessCombiner())).applyAsInt(light);

			VertexConsumer vertexConsumer = getConsumer(vertexConsumers, block, chestType);

			if (isDouble) {
				if (chestType == ChestType.LEFT) {
					renderParts(matrix, vertexConsumer, this.doubleLeftLid, this.doubleLeftLock, this.doubleLeftBottom, pitch, blockLight, overlay);
				} else {
					renderParts(matrix, vertexConsumer, this.doubleRightLid, this.doubleRightLock, this.doubleRightBottom, pitch, blockLight, overlay);
				}
			} else {
				renderParts(matrix, vertexConsumer, this.lid, this.lock, this.bottom, pitch, blockLight, overlay);
			}

			matrix.popPose();
		}
	}

	private void renderParts(PoseStack matrices, VertexConsumer vertices, ModelPart modelPart, ModelPart modelPart2, ModelPart modelPart3, float pitch, int light, int overlay) {
		modelPart.xRot = -(pitch * 1.5707964F);
		modelPart2.xRot = modelPart.xRot;
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

	public static VertexConsumer getConsumer(MultiBufferSource provider, Block block, ChestType chestType) {
		RenderType[] layers = LAYERS.getOrDefault(block, defaultLayer);
		return provider.getBuffer(getChestTexture(chestType, layers));
	}

	static {
		defaultLayer = new RenderType[] {
				RenderType.entityCutout(new ResourceLocation("textures/entity/chest/normal.png")),
				RenderType.entityCutout(new ResourceLocation("textures/entity/chest/normal_left.png")),
				RenderType.entityCutout(new ResourceLocation("textures/entity/chest/normal_right.png"))
		};
		
		ModItems.ITEMS.getEntries().forEach((item) -> {
			if (item.get() instanceof BlockItem) {
				Block block = ((BlockItem) item.get()).getBlock();
				if (block instanceof ChestBlock) {
					String name = block.getRegistryName().getPath();
					LAYERS.put(block, new RenderType[] {
							RenderType.entityCutout(new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/chest/" + name + ".png")),
							RenderType.entityCutout(new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/chest/" + name + "_left.png")),
							RenderType.entityCutout(new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/chest/" + name + "_right.png"))
					});
				}
			}
		});
	}
}
