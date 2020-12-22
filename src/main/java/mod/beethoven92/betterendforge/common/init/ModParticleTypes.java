package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.particles.InfusionParticleData;
import mod.beethoven92.betterendforge.common.particles.InfusionParticleType;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModParticleTypes 
{
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = 
			DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BetterEnd.MOD_ID);
	
	public static final RegistryObject<ParticleType<InfusionParticleData>> INFUSION = PARTICLE_TYPES.register("infusion",
			() -> new InfusionParticleType());
	
	public static final RegistryObject<BasicParticleType> PORTAL_SPHERE = PARTICLE_TYPES.register("portal_sphere",
			() -> new BasicParticleType(false));
	
	public static final RegistryObject<BasicParticleType> GLOWING_SPHERE = PARTICLE_TYPES.register("glowing_sphere",
			() -> new BasicParticleType(false));
	
	public static final RegistryObject<BasicParticleType> TENANEA_PETAL = PARTICLE_TYPES.register("tenanea_petal",
			() -> new BasicParticleType(false));
}
