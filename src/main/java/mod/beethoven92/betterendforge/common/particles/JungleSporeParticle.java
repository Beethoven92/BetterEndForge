package mod.beethoven92.betterendforge.common.particles;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class JungleSporeParticle extends SimpleAnimatedParticle
{
	protected JungleSporeParticle(ClientLevel world, double x, double y, double z, SpriteSet spriteWithAge,
			double r, double g, double b) 
	{
		super(world, x, y, z, spriteWithAge, 0);
		
		setSprite(spriteWithAge.get(random));
		this.lifetime = ModMathHelper.randRange(150, 300, random);
		this.quadSize = ModMathHelper.randRange(0.05F, 0.15F, random);
		this.setFadeColor(15916745);
		this.setSpriteFromAge(spriteWithAge);
		this.setAlpha(0);
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		
		int ticks = this.age % 30;
		if (ticks == 0) 
		{
			this.xd = random.nextGaussian() * 0.02;
			this.yd = random.nextFloat() * 0.02 + 0.02;
			this.zd = random.nextGaussian() * 0.02;
			ticks = 0;
		}
		
		if (this.age <= 30) 
		{
			float delta = ticks / 30F;
			this.setAlpha(delta);
		}
		else if (this.age >= this.lifetime) 
		{
			this.setAlpha(0);
		}
		else if (this.age >= this.lifetime - 30) 
		{
			this.setAlpha((this.lifetime - this.age) / 30F);
		}
		else
		{
			this.setAlpha(1);
		}
		
		this.yd -= 0.001F;
		this.xd *= 0.99F;
		this.zd *= 0.99F;
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
			return new JungleSporeParticle(worldIn, x, y, z, this.sprites, 1, 1, 1);
		}
	}
}
