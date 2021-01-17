package mod.beethoven92.betterendforge.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;

import mod.beethoven92.betterendforge.common.block.EternalPedestal;
import mod.beethoven92.betterendforge.common.block.template.PedestalBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.tileentity.PedestalTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PedestalRenderer<T extends PedestalTileEntity> extends TileEntityRenderer<T>
{
	public PedestalRenderer(TileEntityRendererDispatcher rendererDispatcherIn) 
	{
		super(rendererDispatcherIn);
	}

	@Override
	public void render(T tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
			int combinedLightIn, int combinedOverlayIn) 
	{
		if (tileEntityIn.isEmpty())
		{
			return;
		}
		
		BlockState state = tileEntityIn.getWorld().getBlockState(tileEntityIn.getPos());
		if (!(state.getBlock() instanceof PedestalBlock)) 
		{
			return;
		}

		ItemStack activeItem = tileEntityIn.getStack();
		matrixStackIn.push();
		Minecraft minecraft = Minecraft.getInstance();
		IBakedModel model = minecraft.getItemRenderer().getItemModelWithOverrides(activeItem, tileEntityIn.getWorld(), null);
		Vector3f translate = model.getItemCameraTransforms().ground.translation;
		PedestalBlock pedestal = (PedestalBlock) state.getBlock();
		matrixStackIn.translate(translate.getX(), translate.getY(), translate.getZ());
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
		if (state.isIn(ModBlocks.ETERNAL_PEDESTAL.get()) && state.get(EternalPedestal.ACTIVATED)) 
		{
			float[] colors = EternalCrystalRenderer.colors(age);
			int y = tileEntityIn.getPos().getY();
			
			BeamRenderer.renderLightBeam(matrixStackIn, bufferIn, age, partialTicks, -y, 1024 - y, colors, 0.25F, 0.13F, 0.16F);
			float altitude = MathHelper.sin((tileEntityIn.getAge() + partialTicks) / 10.0F) * 0.1F + 0.1F;
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
			matrixStackIn.rotate(Vector3f.YP.rotation(rotation));
			minecraft.getItemRenderer().renderItem(activeItem, TransformType.GROUND, false, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, model);
		}
		matrixStackIn.pop();
	}
	
	@Override
	public boolean isGlobalRenderer(T te) 
	{
		return true;
	}
}
