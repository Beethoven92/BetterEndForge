package mod.beethoven92.betterendforge.mixin;

import java.util.Random;

import mod.beethoven92.betterendforge.client.ClientOptions;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.BackgroundInfo;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin 
{
	private static final ResourceLocation NEBULA_1 = new ResourceLocation(BetterEnd.MOD_ID, "textures/sky/nebula_2.png");
	private static final ResourceLocation NEBULA_2 = new ResourceLocation(BetterEnd.MOD_ID, "textures/sky/nebula_3.png");
	private static final ResourceLocation HORIZON = new ResourceLocation(BetterEnd.MOD_ID, "textures/sky/nebula_1.png");
	private static final ResourceLocation FOG = new ResourceLocation(BetterEnd.MOD_ID, "textures/sky/fog.png");
	private static final ResourceLocation STARS = new ResourceLocation(BetterEnd.MOD_ID, "textures/sky/stars.png");
	
	private static VertexBuffer stars1;
	private static VertexBuffer stars2;
	private static VertexBuffer stars3;
	private static VertexBuffer stars4;
	private static VertexBuffer nebulas1;
	private static VertexBuffer nebulas2;
	private static VertexBuffer horizon;
	private static VertexBuffer fog;
	private static Vector3f axis1;
	private static Vector3f axis2;
	private static Vector3f axis3;
	private static Vector3f axis4;
	private static float time;
	private static float time2;
	private static float time3;
	private static float blind02;
	private static float blind06;
	private static boolean directOpenGL = false; // Unused
	
	@Shadow
	@Final
	private Minecraft mc;
	
	@Shadow
	@Final
	private TextureManager textureManager;
	
	@Shadow
	private ClientWorld world;
	
	@Shadow
	private int ticks;

	@Inject(method = "<init>*", at = @At("TAIL"))
	private void onInit(Minecraft client, RenderTypeBuffers rainTimeBuffersIn, CallbackInfo info) 
	{
		initStars();
		Random random = new Random(131);
		axis1 = new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
		axis2 = new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
		axis3 = new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
		axis4 = new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
		axis1.normalize();
		axis2.normalize();
		axis3.normalize();
		axis4.normalize();

		//directOpenGL = ModList.get().isLoaded("optifine") || ModList.get().isLoaded("immersive_portals");
	}
	
	@Inject(method = "renderSkyEnd", at = @At("HEAD"), cancellable = true)
	private void renderSkyEnd(MatrixStack matrices, CallbackInfo info) 
	{
		if (ClientOptions.isCustomSky())
		{
			time = (ticks % 360000) * 0.000017453292F;
			time2 = time * 2;
			time3 = time * 3;
			
			FogRenderer.resetFog();
			RenderSystem.enableTexture();
			
			//if (directOpenGL)
			{
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				GL11.glAlphaFunc(516, 0.0F);
				GL11.glEnable(GL11.GL_BLEND);
				RenderSystem.depthMask(false);
			}
			/*else 
			{
				RenderSystem.enableAlphaTest();
				RenderSystem.alphaFunc(516, 0.0F);
				RenderSystem.enableBlend();
			}*/
			
			float blindA = 1F - BackgroundInfo.blindness;
			blind02 = blindA * 0.2F;
			blind06 = blindA * 0.6F;
			
			if (blindA > 0) 
			{
				matrices.push();
				matrices.getLast().getMatrix().mul(new Quaternion(0, time, 0, false));
				textureManager.bindTexture(HORIZON);
				renderBuffer(matrices, horizon, DefaultVertexFormats.POSITION_TEX, 0.77F, 0.31F, 0.73F, 0.7F * blindA);
				matrices.pop();
				
				matrices.push();
				matrices.getLast().getMatrix().mul(new Quaternion(0, -time, 0, false));
				textureManager.bindTexture(NEBULA_1);
				renderBuffer(matrices, nebulas1, DefaultVertexFormats.POSITION_TEX, 0.77F, 0.31F, 0.73F, blind02);
				matrices.pop();
				
				matrices.push();
				matrices.getLast().getMatrix().mul(new Quaternion(0, time2, 0, false));
				textureManager.bindTexture(NEBULA_2);
				renderBuffer(matrices, nebulas2, DefaultVertexFormats.POSITION_TEX, 0.77F, 0.31F, 0.73F, blind02);
				matrices.pop();
				
				textureManager.bindTexture(STARS);

				matrices.push();
				matrices.getLast().getMatrix().mul(axis3.rotation(time));
				renderBuffer(matrices, stars3, DefaultVertexFormats.POSITION_TEX, 0.77F, 0.31F, 0.73F, blind06);
				matrices.pop();

				matrices.push();
				matrices.getLast().getMatrix().mul(axis4.rotation(time2));
				renderBuffer(matrices, stars4, DefaultVertexFormats.POSITION_TEX, 1F, 1F, 1F, blind06);
				matrices.pop();
			}
			
			float a = (BackgroundInfo.fog - 1F);
			if (a > 0)
			{
				if (a > 1) a = 1;
				textureManager.bindTexture(FOG);
				renderBuffer(matrices, fog, DefaultVertexFormats.POSITION_TEX, BackgroundInfo.red, BackgroundInfo.green, BackgroundInfo.blue, a);
			}

			RenderSystem.disableTexture();
			
			if (blindA > 0) 
			{
				matrices.push();
				matrices.getLast().getMatrix().mul(axis1.rotation(time3));
				renderBuffer(matrices, stars1, DefaultVertexFormats.POSITION, 1, 1, 1, blind06);
				matrices.pop();
				
				matrices.push();
				matrices.getLast().getMatrix().mul(axis2.rotation(time2));
				renderBuffer(matrices, stars2, DefaultVertexFormats.POSITION, 0.95F, 0.64F, 0.93F, blind06);
				matrices.pop();
			}
			
			RenderSystem.enableTexture();
			RenderSystem.depthMask(true);
		    
			info.cancel();
		}
	}
	
	private void renderBuffer(MatrixStack matrixStackIn, VertexBuffer buffer, VertexFormat format, float r, float g, float b, float a) 
	{
		RenderSystem.color4f(r, g, b, a);
		buffer.bindBuffer();;
		format.setupBufferState(0L);
        buffer.draw(matrixStackIn.getLast().getMatrix(), 7);
        VertexBuffer.unbindBuffer();
        format.clearBufferState();
	}
	
	private void initStars() 
	{
		BufferBuilder buffer = Tessellator.getInstance().getBuffer();
		stars1 = buildBufferStars(buffer, stars1, 0.1, 0.30, 3500, 41315);
		stars2 = buildBufferStars(buffer, stars2, 0.1, 0.35, 2000, 35151);
		stars3 = buildBufferUVStars(buffer, stars3, 0.4, 1.2, 1000, 61354);
		stars4 = buildBufferUVStars(buffer, stars4, 0.4, 1.2, 1000, 61355);
		nebulas1 = buildBufferFarFog(buffer, nebulas1, 40, 60, 30, 11515);
		nebulas2 = buildBufferFarFog(buffer, nebulas2, 40, 60, 10, 14151);
		horizon = buildBufferHorizon(buffer, horizon);
		fog = buildBufferFog(buffer, fog);
	}
	
	private VertexBuffer buildBufferStars(BufferBuilder bufferBuilder, VertexBuffer buffer, double minSize, double maxSize, int count, long seed)
	{
		if (buffer != null) 
		{
			buffer.close();
		}

		buffer = new VertexBuffer(DefaultVertexFormats.POSITION);
		makeStars(bufferBuilder, minSize, maxSize, count, seed);
		bufferBuilder.finishDrawing();
		buffer.upload(bufferBuilder);

		return buffer;
	}
	
	private VertexBuffer buildBufferUVStars(BufferBuilder bufferBuilder, VertexBuffer buffer, double minSize, double maxSize, int count, long seed) 
	{
		if (buffer != null) 
		{
			buffer.close();
		}

		buffer = new VertexBuffer(DefaultVertexFormats.POSITION_TEX);
		makeUVStars(bufferBuilder, minSize, maxSize, count, seed);
		bufferBuilder.finishDrawing();
		buffer.upload(bufferBuilder);

		return buffer;
	}
	
	private VertexBuffer buildBufferFarFog(BufferBuilder bufferBuilder, VertexBuffer buffer, double minSize, double maxSize, int count, long seed)
	{
		if (buffer != null) 
		{
			buffer.close();
		}

		buffer = new VertexBuffer(DefaultVertexFormats.POSITION_TEX);
		makeFarFog(bufferBuilder, minSize, maxSize, count, seed);
		bufferBuilder.finishDrawing();
		buffer.upload(bufferBuilder);

		return buffer;
	}
	
	private VertexBuffer buildBufferHorizon(BufferBuilder bufferBuilder, VertexBuffer buffer) 
	{
		if (buffer != null) 
		{
			buffer.close();
		}

		buffer = new VertexBuffer(DefaultVertexFormats.POSITION_TEX);
		makeCylinder(bufferBuilder, 16, 50, 100);
		bufferBuilder.finishDrawing();
		buffer.upload(bufferBuilder);

		return buffer;
	}
	
	private VertexBuffer buildBufferFog(BufferBuilder bufferBuilder, VertexBuffer buffer) 
	{
		if (buffer != null) {
			buffer.close();
		}

		buffer = new VertexBuffer(DefaultVertexFormats.POSITION_TEX);
		makeCylinder(bufferBuilder, 16, 50, 70);
		bufferBuilder.finishDrawing();
		buffer.upload(bufferBuilder);

		return buffer;
	}
	
	private void makeStars(BufferBuilder buffer, double minSize, double maxSize, int count, long seed) 
	{
		Random random = new Random(seed);
		buffer.begin(7, DefaultVertexFormats.POSITION);

		for (int i = 0; i < count; ++i) {
			double posX = random.nextDouble() * 2.0 - 1.0;
			double posY = random.nextDouble() * 2.0 - 1.0;
			double posZ = random.nextDouble() * 2.0 - 1.0;
			double size = MathHelper.nextDouble(random, minSize, maxSize);
			double length = posX * posX + posY * posY + posZ * posZ;
			if (length < 1.0 && length > 0.001) {
				length = 1.0 / Math.sqrt(length);
				posX *= length;
				posY *= length;
				posZ *= length;
				double j = posX * 100.0;
				double k = posY * 100.0;
				double l = posZ * 100.0;
				double m = Math.atan2(posX, posZ);
				double n = Math.sin(m);
				double o = Math.cos(m);
				double p = Math.atan2(Math.sqrt(posX * posX + posZ * posZ), posY);
				double q = Math.sin(p);
				double r = Math.cos(p);
				double s = random.nextDouble() * Math.PI * 2.0;
				double t = Math.sin(s);
				double u = Math.cos(s);

				for (int v = 0; v < 4; ++v) {
					double x = (double) ((v & 2) - 1) * size;
					double y = (double) ((v + 1 & 2) - 1) * size;
					double aa = x * u - y * t;
					double ab = y * u + x * t;
					double ad = aa * q + 0.0 * r;
					double ae = 0.0 * q - aa * r;
					double af = ae * n - ab * o;
					double ah = ab * n + ae * o;
					buffer.pos(j + af, k + ad, l + ah).endVertex();
				}
			}
		}
	}
	
	private void makeUVStars(BufferBuilder buffer, double minSize, double maxSize, int count, long seed) 
	{
		Random random = new Random(seed);
		buffer.begin(7, DefaultVertexFormats.POSITION_TEX);

		for (int i = 0; i < count; ++i) 
		{
			double posX = random.nextDouble() * 2.0 - 1.0;
			double posY = random.nextDouble() * 2.0 - 1.0;
			double posZ = random.nextDouble() * 2.0 - 1.0;
			double size = ModMathHelper.randRange(minSize, maxSize, random);
			double length = posX * posX + posY * posY + posZ * posZ;
			if (length < 1.0 && length > 0.001) 
			{
				length = 1.0 / Math.sqrt(length);
				posX *= length;
				posY *= length;
				posZ *= length;
				double j = posX * 100.0;
				double k = posY * 100.0;
				double l = posZ * 100.0;
				double m = Math.atan2(posX, posZ);
				double n = Math.sin(m);
				double o = Math.cos(m);
				double p = Math.atan2(Math.sqrt(posX * posX + posZ * posZ), posY);
				double q = Math.sin(p);
				double r = Math.cos(p);
				double s = random.nextDouble() * Math.PI * 2.0;
				double t = Math.sin(s);
				double u = Math.cos(s);

				int pos = 0;
				float minV = random.nextInt(4) / 4F;
				for (int v = 0; v < 4; ++v) 
				{
					double x = (double) ((v & 2) - 1) * size;
					double y = (double) ((v + 1 & 2) - 1) * size;
					double aa = x * u - y * t;
					double ab = y * u + x * t;
					double ad = aa * q + 0.0 * r;
					double ae = 0.0 * q - aa * r;
					double af = ae * n - ab * o;
					double ah = ab * n + ae * o;
					float texU = (pos >> 1) & 1;
					float texV = (((pos + 1) >> 1) & 1) / 4F + minV;
					pos ++;
					buffer.pos(j + af, k + ad, l + ah).tex(texU, texV).endVertex();
				}
			}
		}
	}
	
	private void makeFarFog(BufferBuilder buffer, double minSize, double maxSize, int count, long seed) 
	{
		Random random = new Random(seed);
		buffer.begin(7, DefaultVertexFormats.POSITION_TEX);

		for (int i = 0; i < count; ++i) {
			double posX = random.nextDouble() * 2.0 - 1.0;
			double posY = random.nextDouble() - 0.5;
			double posZ = random.nextDouble() * 2.0 - 1.0;
			double size = MathHelper.nextDouble(random, minSize, maxSize);
			double length = posX * posX + posY * posY + posZ * posZ;
			double distance = 2.0;
			double delta = 1.0 / count;
			if (length < 1.0 && length > 0.001) {
				length = distance / Math.sqrt(length);
				size *= distance;
				distance -= delta;
				posX *= length;
				posY *= length;
				posZ *= length;
				double j = posX * 100.0;
				double k = posY * 100.0;
				double l = posZ * 100.0;
				double m = Math.atan2(posX, posZ);
				double n = Math.sin(m);
				double o = Math.cos(m);
				double p = Math.atan2(Math.sqrt(posX * posX + posZ * posZ), posY);
				double q = Math.sin(p);
				double r = Math.cos(p);
				double s = random.nextDouble() * Math.PI * 2.0;
				double t = Math.sin(s);
				double u = Math.cos(s);

				int pos = 0;
				for (int v = 0; v < 4; ++v) {
					double x = (double) ((v & 2) - 1) * size;
					double y = (double) ((v + 1 & 2) - 1) * size;
					double aa = x * u - y * t;
					double ab = y * u + x * t;
					double ad = aa * q + 0.0 * r;
					double ae = 0.0 * q - aa * r;
					double af = ae * n - ab * o;
					double ah = ab * n + ae * o;
					float texU = (pos >> 1) & 1;
					float texV = ((pos + 1) >> 1) & 1;
					pos ++;
					buffer.pos(j + af, k + ad, l + ah).tex(texU, texV).endVertex();
				}
			}
		}
	}
	
	private void makeCylinder(BufferBuilder buffer, int segments, double height, double radius) 
	{
		buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		for (int i = 0; i < segments; i ++)
		{
			double a1 = (double) i * Math.PI * 2.0 / (double) segments;
			double a2 = (double) (i + 1) * Math.PI * 2.0 / (double) segments;
			double px1 = Math.sin(a1) * radius;
			double pz1 = Math.cos(a1) * radius;
			double px2 = Math.sin(a2) * radius;
			double pz2 = Math.cos(a2) * radius;
			
			float u0 = (float) i / (float) segments;
			float u1 = (float) (i + 1) / (float) segments;
			
			buffer.pos(px1, -height, pz1).tex(u0, 0).endVertex();
			buffer.pos(px1, height, pz1).tex(u0, 1).endVertex();
			buffer.pos(px2, height, pz2).tex(u1, 1).endVertex();
			buffer.pos(px2, -height, pz2).tex(u1, 0).endVertex();
		}
	}
}
