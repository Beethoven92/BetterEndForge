package mod.beethoven92.betterendforge.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import mod.beethoven92.betterendforge.common.block.EternalPedestal;
import mod.beethoven92.betterendforge.common.block.template.PedestalBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.tileentity.PedestalTileEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.util.Mth;
import com.mojang.math.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PedestalRenderer<T extends PedestalTileEntity> extends BlockEntityRenderer<T>
{
	public PedestalRenderer(BlockEntityRenderDispatcher rendererDispatcherIn) 
	{
		super(rendererDispatcherIn);
	}

	@Override
	public void render(T tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn,
			int combinedLightIn, int combinedOverlayIn) 
	{
		if (tileEntityIn.isEmpty())
		{
			return;
		}
		
		BlockState state = tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos());
		if (!(state.getBlock() instanceof PedestalBlock)) 
		{
			return;
		}

		ItemStack activeItem = tileEntityIn.getStack();
		matrixStackIn.pushPose();
		Minecraft minecraft = Minecraft.getInstance();
		BakedModel model = minecraft.getItemRenderer().getModel(activeItem, tileEntityIn.getLevel(), null);
		Vector3f translate = model.getTransforms().ground.translation;
		PedestalBlock pedestal = (PedestalBlock) state.getBlock();
		matrixStackIn.translate(translate.x(), translate.y(), translate.z());
		matrixStackIn.translate(0.5, pedestal.getHeight(state), 0.5);
		
		if (activeItem.getItem() instanceof BlockItem) 
		{
			matrixStackIn.scale(1.5F, 1.5F, 1.5F);
		} 
		else
		{
			matrixStackIn.scale(1.25F, 1.25F, 1.25F);
		}
		int age = tileEntityIn.getAge();
		if (state.is(ModBlocks.ETERNAL_PEDESTAL.get()) && state.getValue(EternalPedestal.ACTIVATED)) 
		{
			float[] colors = EternalCrystalRenderer.colors(age);
			int y = tileEntityIn.getBlockPos().getY();
			
			BeamRenderer.renderLightBeam(matrixStackIn, bufferIn, age, partialTicks, -y, 1024 - y, colors, 0.25F, 0.13F, 0.16F);
			float altitude = Mth.sin((tileEntityIn.getAge() + partialTicks) / 10.0F) * 0.1F + 0.1F;
			matrixStackIn.translate(0.0D, altitude, 0.0D);
		}
		if (activeItem.getItem() == Items.END_CRYSTAL) 
		{
			EndCrystalRenderer.render(age, tileEntityIn.getMaxAge(), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
		}
		else if (activeItem.getItem() == ModItems.ETERNAL_CRYSTAL.get()) 
		{
			EternalCrystalRenderer.render(age, partialTicks, matrixStackIn, bufferIn, combinedLightIn);
		} 
		else 
		{
			float rotation = (age + partialTicks) / 25.0F + 6.0F;
			matrixStackIn.mulPose(Vector3f.YP.rotation(rotation));
			minecraft.getItemRenderer().render(activeItem, TransformType.GROUND, false, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, model);
		}
		matrixStackIn.popPose();
	}
	
	@Override
	public boolean shouldRenderOffScreen(T te) 
	{
		return true;
	}
}
