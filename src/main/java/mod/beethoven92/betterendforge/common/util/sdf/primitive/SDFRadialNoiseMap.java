package mod.beethoven92.betterendforge.common.util.sdf.primitive;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.util.math.MathHelper;

public class SDFRadialNoiseMap extends SDFDisplacement {
    private static final float SIN = MathHelper.sin(0.5F);
    private static final float COS = MathHelper.cos(0.5F);

    private OpenSimplexNoise noise;
    private float intensity = 1F;
    private float radius = 1F;
    private short offsetX;
    private short offsetZ;

    public SDFRadialNoiseMap() {
        setFunction((pos) -> {
            if (intensity == 0) {
                return 0F;
            }
            float px = pos.x() / radius;
            float pz = pos.z() / radius;
            float distance = ModMathHelper.lengthSqr(px, pz);
            if (distance > 1) {
                return 0F;
            }
            distance = 1 - MathHelper.sqrt(distance);
            float nx = px * COS - pz * SIN;
            float nz = pz * COS + px * SIN;
            distance *= getNoise(nx * 0.75 + offsetX, nz * 0.75 + offsetZ);
            return distance * intensity;
        });
    }

    private float getNoise(double x, double z) {
        return (float) noise.eval(x, z) + (float) noise.eval(
                x * 3 + 1000,
                z * 3
        ) * 0.5F + (float) noise.eval(x * 9 + 1000, z * 9) * 0.2F;
    }

    public SDFRadialNoiseMap setSeed(long seed) {
        noise = new OpenSimplexNoise(seed);
        return this;
    }

    public SDFRadialNoiseMap setIntensity(float intensity) {
        this.intensity = intensity;
        return this;
    }

    public SDFRadialNoiseMap setRadius(float radius) {
        this.radius = radius;
        return this;
    }

    public SDFRadialNoiseMap setOffset(int x, int z) {
        offsetX = (short) (x & 32767);
        offsetZ = (short) (z & 32767);
        return this;
    }
}
