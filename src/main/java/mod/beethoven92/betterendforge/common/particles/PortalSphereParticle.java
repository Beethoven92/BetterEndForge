package mod.beethoven92.betterendforge.common.particles;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PortalSphereParticle extends SimpleAnimatedParticle
{
	private int ticks;
	private double preVX;
	private double preVY;
	private double preVZ;
	private double nextVX;
	private double nextVY;
	private double nextVZ;
	
	protected PortalSphereParticle(ClientLevel world, double x, double y, double z, SpriteSet spriteWithAge) 
	{
		super(world, x, y, z, spriteWithAge, 0);
		setSprite(spriteWithAge.get(random));
		this.lifetime = ModMathHelper.randRange(20, 80, random);
		this.quadSize = ModMathHelper.randRange(0.05F, 0.15F, random);
		this.setColor(0xFEBBD5);
		this.setFadeColor(0xBBFEE4);
		this.setSpriteFromAge(spriteWithAge);
		
		preVX = random.nextGaussian() * 0.02;
		preVY = random.nextGaussian() * 0.02;
		preVZ = random.nextGaussian() * 0.02;
		
		nextVX = random.nextGaussian() * 0.02;
		nextVY = random.nextGaussian() * 0.02;
		nextVZ = random.nextGaussian() * 0.02;
	}

	@Override
	public void tick() 
	{
		ticks++;
		if (ticks > 30) 
		{
			preVX = nextVX;
			preVY = nextVY;
			preVZ = nextVZ;
			nextVX = random.nextGaussian() * 0.02;
			nextVY = random.nextGaussian() * 0.02;
			nextVZ = random.nextGaussian() * 0.02;
			ticks = 0;
		}
		double delta = (double) ticks / 30.0;
		
		this.xd = Mth.lerp(delta, preVX, nextVX);
		this.yd = Mth.lerp(delta, preVY, nextVY);
		this.zd = Mth.lerp(delta, preVZ, nextVZ);
		
		super.tick();
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Factory implements ParticleProvider<SimpleParticleType> 
	{
		private final SpriteSet sprites;

		public Factory(SpriteSet sprites) 
		{
			this.sprites = sprites;
		}

		@Override
		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z,
				double xSpeed, double ySpeed, double zSpeed) 
		{
			return new PortalSphereParticle(worldIn, x, y, z, this.sprites);
		}
	}
}
