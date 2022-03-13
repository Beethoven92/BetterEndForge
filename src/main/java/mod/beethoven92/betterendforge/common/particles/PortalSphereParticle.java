package mod.beethoven92.betterendforge.common.particles;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
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
	
	protected PortalSphereParticle(ClientWorld world, double x, double y, double z, IAnimatedSprite spriteWithAge) 
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
		
		this.xd = MathHelper.lerp(delta, preVX, nextVX);
		this.yd = MathHelper.lerp(delta, preVY, nextVY);
		this.zd = MathHelper.lerp(delta, preVZ, nextVZ);
		
		super.tick();
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType> 
	{
		private final IAnimatedSprite sprites;

		public Factory(IAnimatedSprite sprites) 
		{
			this.sprites = sprites;
		}

		@Override
		public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z,
				double xSpeed, double ySpeed, double zSpeed) 
		{
			return new PortalSphereParticle(worldIn, x, y, z, this.sprites);
		}
	}
}
