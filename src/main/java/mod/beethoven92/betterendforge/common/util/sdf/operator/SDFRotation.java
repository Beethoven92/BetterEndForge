package mod.beethoven92.betterendforge.common.util.sdf.operator;

import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class SDFRotation extends SDFUnary
{
	private static final Vector3f POS = new Vector3f();
	private Quaternion rotation;
	
	public SDFRotation setRotation(Vector3f axis, float rotationAngle) 
	{
		rotation = new Quaternion(axis, rotationAngle, false);
		return this;
	}
	
	@Override
	public float getDistance(float x, float y, float z) 
	{
		POS.set(x, y, z);
		POS.transform(rotation);
		return source.getDistance(POS.getX(), POS.getY(), POS.getZ());
	}
}
