package mod.beethoven92.betterendforge.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.AuroraCrystalBlock;
import mod.beethoven92.betterendforge.common.util.ColorHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;

public class EternalCrystalRenderer 
{
	private static final ResourceLocation CRYSTAL_TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/eternal_crystal.png");
	private static final RenderType RENDER_LAYER;
	private static final ModelRenderer[] SHARDS;
	private static final ModelRenderer CORE;
	
	static 
	{
		RENDER_LAYER = RenderType.getBeaconBeam(CRYSTAL_TEXTURE, true);
		SHARDS = new ModelRenderer[4];
		SHARDS[0] = new ModelRenderer(16, 16, 2, 4).addBox(-5.0F, 1.0F, -3.0F, 2.0F, 8.0F, 2.0F);
		SHARDS[1] = new ModelRenderer(16, 16, 2, 4).addBox(3.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F);
		SHARDS[2] = new ModelRenderer(16, 16, 2, 4).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 4.0F, 2.0F);
		SHARDS[3] = new ModelRenderer(16, 16, 2, 4).addBox(0.0F, 3.0F, 4.0F, 2.0F, 6.0F, 2.0F);
		CORE = new ModelRenderer(16, 16, 0, 0);
		CORE.addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F);
	}
	
	public static void render(int age, float tickDelta, MatrixStack matrices, IRenderTypeBuffer bufferIn, int light) 
	{
		IVertexBuilder iVertexBuilder = bufferIn.getBuffer(RENDER_LAYER);
		float[] colors = colors(age);
		float rotation = (age + tickDelta) / 25.0F + 6.0F;
		matrices.push();
		matrices.scale(0.6F, 0.6F, 0.6F);
		matrices.rotate(Vector3f.YP.rotation(rotation));
		CORE.render(matrices, iVertexBuilder, light, OverlayTexture.NO_OVERLAY, colors[0], colors[1], colors[2], colors[3]);

		for (int i = 0; i < 4; i++) 
		{
			matrices.push();
			float offset = MathHelper.sin(rotation * 2 + i) * 0.15F;
			matrices.translate(0, offset, 0);
			SHARDS[i].render(matrices, iVertexBuilder, light, OverlayTexture.NO_OVERLAY, colors[0], colors[1], colors[2], colors[3]);
			matrices.pop();
		}
		
		matrices.pop();
	}
	
	public static float[] colors(int age) 
	{
		double delta = age * 0.01;
		int index = ModMathHelper.floor(delta);
		int index2 = (index + 1) & 3;
		delta -= index;
		index &= 3;
		
		Vector3i color1 = AuroraCrystalBlock.COLORS[index];
		Vector3i color2 = AuroraCrystalBlock.COLORS[index2];
		
		int r = ModMathHelper.floor(MathHelper.lerp(delta, color1.getX(), color2.getX()));
		int g = ModMathHelper.floor(MathHelper.lerp(delta, color1.getY(), color2.getY()));
		int b = ModMathHelper.floor(MathHelper.lerp(delta, color1.getZ(), color2.getZ()));
		
		return ColorHelper.toFloatArray(ModMathHelper.color(r, g, b));
	}
}
