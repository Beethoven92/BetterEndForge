package mod.beethoven92.betterendforge.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class BeamRenderer 
{
	private static final ResourceLocation BEAM_TEXTURE = new ResourceLocation("textures/entity/end_gateway_beam.png");
	
	public static void renderLightBeam(MatrixStack matrices, IRenderTypeBuffer bufferIn, int age, float tick, 
			int minY, int maxY, float[] colors, float alpha, float beamIn, float beamOut) 
	{
		float red = colors[0];
		float green = colors[1];
		float blue = colors[2];
		
		int maxBY = minY + maxY;
		float delta = maxY < 0 ? tick : -tick;
		float fractDelta = MathHelper.frac(delta * 0.2F - (float) MathHelper.floor(delta * 0.1F));		
		float xIn = -beamIn;
		float minV = MathHelper.clamp(fractDelta - 1.0F, 0.0F, 1.0F);
		float maxV = (float) maxY * (0.5F / beamIn) + minV;
		float rotation = (age + tick) / 25.0F + 6.0F;
		
		IVertexBuilder iVertexBuilder = bufferIn.getBuffer(RenderType.getBeaconBeam(BEAM_TEXTURE, true));
		
		matrices.push();
		matrices.rotate(Vector3f.YP.rotation(-rotation));
		renderBeam(matrices, iVertexBuilder, red, green, blue, alpha, minY, maxBY, beamIn, 0.0F, 0.0F, beamIn, 0.0F, xIn, xIn, 0.0F, 0.0F, 1.0F, minV, maxV);
		
		float xOut = -beamOut;
		maxV = (float) maxY + minV;
		renderBeam(matrices, iVertexBuilder, red, green, blue, alpha, minY, maxBY, xOut, xOut, beamOut, xOut, xOut, beamOut, beamOut, beamOut, 0.0F, 1.0F, minV, maxV);
		matrices.pop();
	}

	private static void renderBeam(MatrixStack matrices, IVertexBuilder vertexBuilder, float red, float green, float blue, float alpha, int minY, int maxY, float x1, float d1, float x2, float d2, float x3, float d3, float x4, float d4, float minU, float maxU, float minV, float maxV)
	{
		MatrixStack.Entry entry = matrices.getLast();
		Matrix4f matrix4f = entry.getMatrix();
		Matrix3f matrix3f = entry.getNormal();		
		renderBeam(matrix4f, matrix3f, vertexBuilder, red, green, blue, alpha, maxY, minY, x1, d1, x2, d2, minU, maxU, minV, maxV);
		renderBeam(matrix4f, matrix3f, vertexBuilder, red, green, blue, alpha, maxY, minY, x4, d4, x3, d3, minU, maxU, minV, maxV);
		renderBeam(matrix4f, matrix3f, vertexBuilder, red, green, blue, alpha, maxY, minY, x2, d2, x4, d4, minU, maxU, minV, maxV);
		renderBeam(matrix4f, matrix3f, vertexBuilder, red, green, blue, alpha, maxY, minY, x3, d3, x1, d1, minU, maxU, minV, maxV);
	}

	private static void renderBeam(Matrix4f matrix4f, Matrix3f matrix3f, IVertexBuilder vertexBuilder, float red, float green, float blue, float alpha, int minY, int maxY, float minX, float minD, float maxX, float maxD, float minU, float maxU, float minV, float maxV) 
	{
		addVertex(matrix4f, matrix3f, vertexBuilder, red, green, blue, alpha, maxX, minY, maxD, maxU, minV);
		addVertex(matrix4f, matrix3f, vertexBuilder, red, green, blue, alpha, maxX, maxY, maxD, maxU, maxV);
		addVertex(matrix4f, matrix3f, vertexBuilder, red, green, blue, alpha, minX, maxY, minD, minU, maxV);
		addVertex(matrix4f, matrix3f, vertexBuilder, red, green, blue, alpha, minX, minY, minD, minU, minV);
	}

	private static void addVertex(Matrix4f matrix4f, Matrix3f matrix3f, IVertexBuilder vertexBuilder, float red, float green, float blue, float alpha, float x, float y, float d, float u, float v) 
	{
		vertexBuilder.pos(matrix4f, x, y, d).color(red, green, blue, alpha).tex(u, v).overlay(OverlayTexture.NO_OVERLAY).lightmap(15728880).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
	}
}
