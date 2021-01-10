package mod.beethoven92.betterendforge.common.util.sdf.primitive;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.util.math.MathHelper;

public class SDFCappedCone extends SDFPrimitive 
{
	private float radius1;
	private float radius2;
	private float height;
	
	public SDFCappedCone setRadius1(float radius)
	{
		this.radius1 = radius;
		return this;
	}
	
	public SDFCappedCone setRadius2(float radius) 
	{
		this.radius2 = radius;
		return this;
	}
	
	public SDFCappedCone setHeight(float height) 
	{
		this.height = height;
		return this;
	}

	@Override
	public float getDistance(float x, float y, float z) 
	{
		float qx = ModMathHelper.length(x, z);
		float k2x = radius2 - radius1;
		float k2y = 2 * height;
		float cax = qx - ModMathHelper.min(qx, (y < 0F) ? radius1 : radius2);
		float cay = Math.abs(y) - height;
		float mlt = MathHelper.clamp(ModMathHelper.dot(radius2 - qx, height - y, k2x, k2y) / ModMathHelper.dot(k2x, k2y, k2x, k2y), 0F, 1F);
		float cbx = qx - radius2 + k2x * mlt;
		float cby = y - height + k2y * mlt;
		float s = (cbx < 0F && cay < 0F) ? -1F : 1F;
		return s * (float) Math.sqrt(ModMathHelper.min(ModMathHelper.dot(cax, cay, cax, cay), ModMathHelper.dot(cbx, cby, cbx, cby)));
	}

}
