package mod.beethoven92.betterendforge.common.util.sdf.primitive;


import mod.beethoven92.betterendforge.common.util.ModMathHelper;

public class SDFTorus extends SDFPrimitive {
	private float radiusSmall;
	private float radiusBig;
	
	public SDFTorus setBigRadius(float radius) {
		this.radiusBig = radius;
		return this;
	}

	public SDFTorus setSmallRadius(float radius) {
		this.radiusSmall = radius;
		return this;
	}

	@Override
	public float getDistance(float x, float y, float z) {
		float nx = ModMathHelper.length(x, z) - radiusBig;
		return ModMathHelper.length(nx, y) - radiusSmall;
	}
}
