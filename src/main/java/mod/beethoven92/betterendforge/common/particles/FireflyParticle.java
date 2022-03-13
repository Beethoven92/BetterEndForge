package mod.beethoven92.betterendforge.common.particles;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;

public class FireflyParticle extends SimpleAnimatedParticle {
	private double preVX;
	private double preVY;
	private double preVZ;
	private double nextVX;
	private double nextVY;
	private double nextVZ;
	
	protected FireflyParticle(ClientWorld world, double x, double y, double z, IAnimatedSprite sprites, double r, double g, double b) {
		super(world, x, y, z, sprites, 0);
		setSprite(sprites.get(random));
		this.lifetime = ModMathHelper.randRange(150, 300, random);
		this.quadSize = ModMathHelper.randRange(0.05F, 0.15F, random);
		this.setFadeColor(15916745);
		this.setSpriteFromAge(sprites);
		this.setAlpha(0);
		
		preVX = random.nextGaussian() * 0.02;
		preVY = random.nextGaussian() * 0.02;
		preVZ = random.nextGaussian() * 0.02;
		
		nextVX = random.nextGaussian() * 0.02;
		nextVY = random.nextGaussian() * 0.02;
		nextVZ = random.nextGaussian() * 0.02;
	}
	
	@Override
	public void tick() {
		int ticks = this.age & 31;
		if (ticks == 0) {
			preVX = nextVX;
			preVY = nextVY;
			preVZ = nextVZ;
			nextVX = random.nextGaussian() * 0.02;
			nextVY = random.nextGaussian() * 0.02;
			nextVZ = random.nextGaussian() * 0.02;
		}
		double delta = (double) ticks / 31.0;
		
		this.xd = MathHelper.lerp(delta, preVX, nextVX);
		this.yd = MathHelper.lerp(delta, preVY, nextVY);
		this.zd = MathHelper.lerp(delta, preVZ, nextVZ);
		
		if (this.age <= 60) {
			this.setAlpha(this.age / 60F);
		}
		else if (this.age > lifetime - 60) {
			this.setAlpha((lifetime - this.age) / 60F);
		}
		
		super.tick();
	}

	public static class FireflyParticleFactory implements IParticleFactory<BasicParticleType>  {
		private final IAnimatedSprite sprites;

		public FireflyParticleFactory(IAnimatedSprite sprites) {
			this.sprites = sprites;
		}

		@Override
		public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z,
				double xSpeed, double ySpeed, double zSpeed) {
			return new FireflyParticle(worldIn, x, y, z, sprites, 1, 1, 1);
		}
	}
}