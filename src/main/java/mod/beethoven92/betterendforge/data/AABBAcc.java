package mod.beethoven92.betterendforge.data;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Inject;



public class AABBAcc {



    public static AABB ofSize(Vec3 vec3, double d, double e, double f) {
        return new AABB(vec3.x - d / 2.0D, vec3.y - e / 2.0D, vec3.z - f / 2.0D, vec3.x + d / 2.0D, vec3.y + e / 2.0D, vec3.z + f / 2.0D);
    }
}
