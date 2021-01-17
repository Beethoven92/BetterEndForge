package mod.beethoven92.betterendforge.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class EndCrystalRenderer
{
	private static final ResourceLocation CRYSTAL_TEXTURE = new ResourceLocation("textures/entity/end_crystal/end_crystal.png");
	private static final ResourceLocation CRYSTAL_BEAM_TEXTURE = new ResourceLocation("textures/entity/end_crystal/end_crystal_beam.png");
	private static final RenderType CRYSTAL_BEAM_LAYER;
	private static final RenderType END_CRYSTAL;
	private static final ModelRenderer CORE;
	private static final ModelRenderer FRAME;
	private static final int AGE_CYCLE = 240;
	private static final float SINE_45_DEGREES;
	
	static 
	{
		END_CRYSTAL = RenderType.getEntityCutoutNoCull(CRYSTAL_TEXTURE);
		CRYSTAL_BEAM_LAYER = RenderType.getEntitySmoothCutout(CRYSTAL_BEAM_TEXTURE);
		SINE_45_DEGREES = (float) Math.sin(0.7853981633974483D);
		FRAME = new ModelRenderer(64, 32, 0, 0);
		FRAME.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
		CORE = new ModelRenderer(64, 32, 32, 0);
		CORE.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
	}
	
	public static void render(int age, int maxAge, float tickDelta, MatrixStack matrices, 
			IRenderTypeBuffer bufferIn, int light)
	{
		float k = (float) AGE_CYCLE / maxAge;
		float rotation = (age * k + tickDelta) * 3.0F;
		IVertexBuilder iVertexBuilder = bufferIn.getBuffer(END_CRYSTAL);
		matrices.push();
		matrices.scale(0.8F, 0.8F, 0.8F);
		matrices.translate(0.0D, -0.5D, 0.0D);
		matrices.rotate(Vector3f.YP.rotationDegrees(rotation));
		matrices.translate(0.0D, 0.8F, 0.0D);
		matrices.rotate(new Quaternion(new Vector3f(SINE_45_DEGREES, 0.0F, SINE_45_DEGREES), 60.0F, true));
		FRAME.render(matrices, iVertexBuilder, light, OverlayTexture.NO_OVERLAY);
		matrices.scale(0.875F, 0.875F, 0.875F);
		matrices.rotate(new Quaternion(new Vector3f(SINE_45_DEGREES, 0.0F, SINE_45_DEGREES), 60.0F, true));
		matrices.rotate(Vector3f.YP.rotationDegrees(rotation));
		FRAME.render(matrices, iVertexBuilder, light, OverlayTexture.NO_OVERLAY);
		matrices.scale(0.875F, 0.875F, 0.875F);
		matrices.rotate(new Quaternion(new Vector3f(SINE_45_DEGREES, 0.0F, SINE_45_DEGREES), 60.0F, true));
		matrices.rotate(Vector3f.YP.rotationDegrees(rotation));
		CORE.render(matrices, iVertexBuilder, light, OverlayTexture.NO_OVERLAY);
		matrices.pop();
	}
	
	public static void renderBeam(BlockPos start, BlockPos end, float tickDelta, int age, MatrixStack matrices, 
			IRenderTypeBuffer bufferIn, int light) 
	{
		float dx = start.getX() - end.getX() + 1.0F;
		float dy = start.getY() - end.getY() + 1.0F;
		float dz = start.getZ() - end.getZ() + 1.0F;
		float f = MathHelper.sqrt(dx * dx + dz * dz);
		float g = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
		matrices.push();
		matrices.translate(0.0D, 2.0D, 0.0D);
		matrices.rotate(Vector3f.YP.rotation((float)(-Math.atan2((double) dz, (double) dx)) - 1.5707964F));
		matrices.rotate(Vector3f.XP.rotation((float)(-Math.atan2((double) f, (double) dy)) - 1.5707964F));
		IVertexBuilder iVertexBuilder = bufferIn.getBuffer(CRYSTAL_BEAM_LAYER);
		float h = 0.0F - ((float) age + tickDelta) * 0.01F;
		float i = MathHelper.sqrt(dx * dx + dy * dy + dz * dz) / 32.0F - ((float) age + tickDelta) * 0.01F;
		float k = 0.0F;
		float l = 0.75F;
		float m = 0.0F;
		MatrixStack.Entry entry = matrices.getLast();
		Matrix4f matrix4f = entry.getMatrix();
		Matrix3f matrix3f = entry.getNormal();

		for(int n = 1; n <= 8; ++n) 
		{
		   float o = MathHelper.sin((float) n * 6.2831855F / 8.0F) * 0.75F;
		   float p = MathHelper.cos((float) n * 6.2831855F / 8.0F) * 0.75F;
		   float q = (float) n / 8.0F;
		   iVertexBuilder.pos(matrix4f, k * 0.2F, l * 0.2F, 0.0F).color(0, 0, 0, 255).tex(m, h).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
		   iVertexBuilder.pos(matrix4f, k, l, g).color(255, 255, 255, 255).tex(m, i).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
		   iVertexBuilder.pos(matrix4f, o, p, g).color(255, 255, 255, 255).tex(q, i).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
		   iVertexBuilder.pos(matrix4f, o * 0.2F, p * 0.2F, 0.0F).color(0, 0, 0, 255).tex(q, h).overlay(OverlayTexture.NO_OVERLAY).lightmap(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
		   k = o;
		   l = p;
		   m = q;
		}

		matrices.pop();
	}
}
