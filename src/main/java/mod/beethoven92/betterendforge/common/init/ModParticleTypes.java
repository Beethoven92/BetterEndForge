package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.particles.InfusionParticleData;
import mod.beethoven92.betterendforge.common.particles.InfusionParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModParticleTypes 
{
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = 
			DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BetterEnd.MOD_ID);
	
	public static final RegistryObject<ParticleType<InfusionParticleData>> INFUSION = PARTICLE_TYPES.register("infusion",
			() -> new InfusionParticleType());
	
	public static final RegistryObject<SimpleParticleType> PORTAL_SPHERE = PARTICLE_TYPES.register("portal_sphere",
			() -> new SimpleParticleType(false));
	
	public static final RegistryObject<SimpleParticleType> GLOWING_SPHERE = PARTICLE_TYPES.register("glowing_sphere",
			() -> new SimpleParticleType(false));
	
	public static final RegistryObject<SimpleParticleType> AMBER_SPHERE = PARTICLE_TYPES.register("amber_sphere",
			() -> new SimpleParticleType(false));
	
	public static final RegistryObject<SimpleParticleType> TENANEA_PETAL = PARTICLE_TYPES.register("tenanea_petal",
			() -> new SimpleParticleType(false));
	
	public static final RegistryObject<SimpleParticleType> GEYSER_PARTICLE = PARTICLE_TYPES.register("geyser_particle",
			() -> new SimpleParticleType(true));
	
	public static final RegistryObject<SimpleParticleType> SULPHUR_PARTICLE = PARTICLE_TYPES.register("sulphur_particle",
			() -> new SimpleParticleType(false));
	
	public static final RegistryObject<SimpleParticleType> SNOWFLAKE_PARTICLE = PARTICLE_TYPES.register("snowflake_particle",
			() -> new SimpleParticleType(false));
	
	public static final RegistryObject<SimpleParticleType> JUNGLE_SPORE = PARTICLE_TYPES.register("jungle_spore",
			() -> new SimpleParticleType(false));
	
	public static final RegistryObject<SimpleParticleType> FIREFLY = PARTICLE_TYPES.register("firefly",
			() -> new SimpleParticleType(false));

	//public static final RegistryObject<BasicParticleType> SMARAGDANT = PARTICLE_TYPES.register("smaragdant_particle",
	//		() -> new BasicParticleType(false));

}
