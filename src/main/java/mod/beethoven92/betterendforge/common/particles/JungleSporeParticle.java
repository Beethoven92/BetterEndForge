package mod.beethoven92.betterendforge.common.particles;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class JungleSporeParticle extends SimpleAnimatedParticle
{
	protected JungleSporeParticle(ClientWorld world, double x, double y, double z, IAnimatedSprite spriteWithAge,
			double r, double g, double b) 
	{
		super(world, x, y, z, spriteWithAge, 0);
		
		setSprite(spriteWithAge.get(rand));
		this.maxAge = ModMathHelper.randRange(150, 300, rand);
		this.particleScale = ModMathHelper.randRange(0.05F, 0.15F, rand);
		this.setColorFade(15916745);
		this.selectSpriteWithAge(spriteWithAge);
		this.setAlphaF(0);
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		
		int ticks = this.age % 30;
		if (ticks == 0) 
		{
			this.motionX = rand.nextGaussian() * 0.02;
			this.motionY = rand.nextFloat() * 0.02 + 0.02;
			this.motionZ = rand.nextGaussian() * 0.02;
			ticks = 0;
		}
		
		if (this.age <= 30) 
		{
			float delta = ticks / 30F;
			this.setAlphaF(delta);
		}
		else if (this.age >= this.maxAge) 
		{
			this.setAlphaF(0);
		}
		else if (this.age >= this.maxAge - 30) 
		{
			this.setAlphaF((this.maxAge - this.age) / 30F);
		}
		else
		{
			this.setAlphaF(1);
		}
		
		this.motionY -= 0.001F;
		this.motionX *= 0.99F;
		this.motionZ *= 0.99F;
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
			return new JungleSporeParticle(worldIn, x, y, z, this.sprites, 1, 1, 1);
		}
	}
}
