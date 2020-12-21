package mod.beethoven92.betterendforge.common.util.sdf.operator;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;

public class SDFSubtraction extends SDFBinary 
{
	@Override
	public float getDistance(float x, float y, float z) 
	{
		float a = this.sourceA.getDistance(x, y, z);
		float b = this.sourceB.getDistance(x, y, z);
		this.selectValue(a, b);
		return ModMathHelper.max(a, -b);
	}
}
