package mod.beethoven92.betterendforge.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ColorHelper 
{
	private static Map<ResourceLocation, Integer> colorPalette = Maps.newHashMap();
	private static float[] floatBuffer = new float[4];
	
	public static float[] toFloatArray(int color) 
	{
		floatBuffer[0] = ((color >> 16 & 255) / 255.0F);
		floatBuffer[1] = ((color >> 8 & 255) / 255.0F);
		floatBuffer[2] = ((color & 255) / 255.0F);
		floatBuffer[3] = ((color >> 24 & 255) / 255.0F);
		
		return floatBuffer;
	}
	
	public static int ABGRtoARGB(int color) 
	{
		int a = (color >> 24) & 255;
		int b = (color >> 16) & 255;
		int g = (color >> 8) & 255;
		int r = color & 255;
		return a << 24 | r << 16 | g << 8 | b;
	}
	public static int colorDistance(int color1, int color2) 
	{
		int r1 = (color1 >> 16) & 255;
		int g1 = (color1 >> 8) & 255;
		int b1 = color1 & 255;
		int r2 = (color2 >> 16) & 255;
		int g2 = (color2 >> 8) & 255;
		int b2 = color2 & 255;
		return ModMathHelper.pow2(r1 - r2) + ModMathHelper.pow2(g1 - g2) + ModMathHelper.pow2(b1 - b2);
	}
	
	public static int extractColor(Item item)
	{
		ResourceLocation id = Registry.ITEM.getKey(item);
		if (id.equals(Registry.ITEM.getDefaultKey())) return -1;
		if (colorPalette.containsKey(id)) 
		{
			return colorPalette.get(id);
		}
		ResourceLocation texture;
		if (item instanceof BlockItem) 
		{
			texture = new ResourceLocation(id.getNamespace(), "textures/block/" + id.getPath() + ".png");
		} 
		else 
		{
			texture = new ResourceLocation(id.getNamespace(), "textures/item/" + id.getPath() + ".png");
		}
		NativeImage image = loadImage(texture, 16, 16);
		List<Integer> colors = new ArrayList<>();
		for (int i = 0; i < image.getWidth(); i++) 
		{
			for (int j = 0; j < 16; j++) 
			{
				int col = image.getPixelRGBA(i, j);
				if (((col >> 24) & 255) > 0) 
				{
					colors.add(ABGRtoARGB(col));
				}
			}
		}
		image.close();
		
		if (colors.size() == 0) return -1;
		
		ColorExtractor extractor = new ColorExtractor(colors);
		int color = extractor.analize();
		colorPalette.put(id, color);
		
		return color;
	}
	
	public static NativeImage loadImage(ResourceLocation image, int w, int h) 
	{
		Minecraft minecraft = Minecraft.getInstance();
		IResourceManager resourceManager = minecraft.getResourceManager();
		if (resourceManager.hasResource(image)) 
		{
			try (IResource resource = resourceManager.getResource(image)) 
			{
				return NativeImage.read(resource.getInputStream());			
			} 
			catch (IOException e) 
			{
				BetterEnd.LOGGER.warn("Can't load texture image: {}. Will be created empty image.", image);
				BetterEnd.LOGGER.warn("Cause: {}.", e.getMessage());
			}
		}		
		return new NativeImage(w, h, false);
	}
}
