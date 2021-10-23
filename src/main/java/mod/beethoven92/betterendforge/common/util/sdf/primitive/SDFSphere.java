package mod.beethoven92.betterendforge.common.util.sdf.primitive;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;

public class SDFSphere extends SDFPrimitive 
{
	private float radius;


	public SDFSphere setRadius(float radius) {
		this.radius = radius;
		return this;
	}
	
	@Override
	public float getDistance(float x, float y, float z) 
	{
		return ModMathHelper.length(x, y, z) - radius;
	}
}
