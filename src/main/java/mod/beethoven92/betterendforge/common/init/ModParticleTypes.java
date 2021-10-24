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
	
	public static final RegistryObject<BasicParticleType> AMBER_SPHERE = PARTICLE_TYPES.register("amber_sphere",
			() -> new BasicParticleType(false));
	
	public static final RegistryObject<BasicParticleType> TENANEA_PETAL = PARTICLE_TYPES.register("tenanea_petal",
			() -> new BasicParticleType(false));
	
	public static final RegistryObject<BasicParticleType> GEYSER_PARTICLE = PARTICLE_TYPES.register("geyser_particle",
			() -> new BasicParticleType(true));
	
	public static final RegistryObject<BasicParticleType> SULPHUR_PARTICLE = PARTICLE_TYPES.register("sulphur_particle",
			() -> new BasicParticleType(false));
	
	public static final RegistryObject<BasicParticleType> SNOWFLAKE_PARTICLE = PARTICLE_TYPES.register("snowflake_particle",
			() -> new BasicParticleType(false));
	
	public static final RegistryObject<BasicParticleType> JUNGLE_SPORE = PARTICLE_TYPES.register("jungle_spore",
			() -> new BasicParticleType(false));
	
	public static final RegistryObject<BasicParticleType> FIREFLY = PARTICLE_TYPES.register("firefly",
			() -> new BasicParticleType(false));

	//public static final RegistryObject<BasicParticleType> SMARAGDANT = PARTICLE_TYPES.register("smaragdant_particle",
	//		() -> new BasicParticleType(false));

}
