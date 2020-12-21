package mod.beethoven92.betterendforge.common.util;

import java.util.Random;

import net.minecraft.util.math.MathHelper;

public class ModMathHelper 
{
	public static final float PI2 = (float) (Math.PI * 2);
	
	private static final int ALPHA = 255 << 24;

	public static int color(int r, int g, int b) 
	{
		return ALPHA | (r << 16) | (g << 8) | b;
	}
	
	public static int getColor(int r, int g, int b) 
	{
		r = MathHelper.clamp(r, 0, 255);
		g = MathHelper.clamp(g, 0, 255);
		b = MathHelper.clamp(b, 0, 255);
		return color(r, g, b);
	}
	
	public static int pow2(int i)
	{
		return i * i;
	}
	
	public static int randRange(int min, int max, Random random) 
	{
		return min + random.nextInt(max - min + 1);
	}
	
	public static double randRange(double min, double max, Random random) 
	{
		return min + random.nextDouble() * (max - min);
	}

	public static float randRange(float min, float max, Random random) 
	{
		return min + random.nextFloat() * (max - min);
	}
	
	public static float lengthSqr(float x, float y, float z) 
	{
		return x * x + y * y + z * z;
	}
		
	public static float length(float x, float y, float z) 
	{
		return (float) Math.sqrt(lengthSqr(x, y, z));
	}
	
	public static float lengthSqr(float x, float y) 
	{
		return x * x + y * y;
	}
	
	public static double lengthSqr(double x, double y) 
	{
		return x * x + y * y;
	}
	
	public static float length(float x, float y) 
	{
		return (float) Math.sqrt(lengthSqr(x, y));
	}
	
	public static double length(double x, double y) 
	{
		return Math.sqrt(lengthSqr(x, y));
	}
	
	public static float dot(float x1, float y1, float z1, float x2, float y2, float z2) 
	{
		return x1 * x2 + y1 * y2 + z1 * z2;
	}
	
	public static float dot(float x1, float y1, float x2, float y2) 
	{
		return x1 * x2 + y1 * y2;
	}
	
	public static int floor(double x) 
	{
		return x < 0 ? (int) (x - 1) : (int) x;
	}
	
	public static int min(int a, int b) 
	{
		return a < b ? a : b;
	}
	
	public static int max(int a, int b) 
	{
		return a > b ? a : b;
	}
	
	public static float min(float a, float b) 
	{
		return a < b ? a : b;
	}
	
	public static float max(float a, float b) 
	{
		return a > b ? a : b;
	}
	
	public static float max(float a, float b, float c) 
	{
		return max(a, max(b, c));
	}
}
