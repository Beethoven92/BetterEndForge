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

public class GlowingSphereParticle extends SimpleAnimatedParticle
{
	private int ticks;
	private double preVX;
	private double preVY;
	private double preVZ;
	private double nextVX;
	private double nextVY;
	private double nextVZ;
	
	protected GlowingSphereParticle(ClientWorld world, double x, double y, double z, IAnimatedSprite spriteWithAge, 
			double r, double g, double b) 
	{
		super(world, x, y, z, spriteWithAge, 0);
		setSprite(spriteWithAge.get(rand));
		this.maxAge = ModMathHelper.randRange(150, 300, rand);
		this.particleScale = ModMathHelper.randRange(0.05F, 0.15F, rand);
		this.setColorFade(15916745);
		this.selectSpriteWithAge(spriteWithAge);
		
		preVX = rand.nextGaussian() * 0.02;
		preVY = rand.nextGaussian() * 0.02;
		preVZ = rand.nextGaussian() * 0.02;
		
		nextVX = rand.nextGaussian() * 0.02;
		nextVY = rand.nextGaussian() * 0.02;
		nextVZ = rand.nextGaussian() * 0.02;
	}
	
	@Override
	public void tick() 
	{
		ticks ++;
		if (ticks > 30) {
			preVX = nextVX;
			preVY = nextVY;
			preVZ = nextVZ;
			nextVX = rand.nextGaussian() * 0.02;
			nextVY = rand.nextGaussian() * 0.02;
			nextVZ = rand.nextGaussian() * 0.02;
			ticks = 0;
		}
		double delta = (double) ticks / 30.0;
		
		this.motionX = MathHelper.lerp(delta, preVX, nextVX);
		this.motionY = MathHelper.lerp(delta, preVY, nextVY);
		this.motionZ = MathHelper.lerp(delta, preVZ, nextVZ);
		
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
		public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z,
				double xSpeed, double ySpeed, double zSpeed) 
		{
			return new GlowingSphereParticle(worldIn, x, y, z, this.sprites, 1, 1, 1);
		}
	}
}
