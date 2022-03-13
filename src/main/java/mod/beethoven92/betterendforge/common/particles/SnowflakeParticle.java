package mod.beethoven92.betterendforge.common.particles;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SnowflakeParticle extends SpriteTexturedParticle
{
	private int ticks;
	private double preVX;
	private double preVY;
	private double preVZ;
	private double nextVX;
	private double nextVY;
	private double nextVZ;
	
	protected SnowflakeParticle(ClientWorld world, double x, double y, double z, double vx, 
			double vy, double vz, IAnimatedSprite spriteWithAge) 
	{
		super(world, x, y, z, vx, vy, vz);
		
		this.setSpriteFromAge(spriteWithAge);
		
		this.lifetime = ModMathHelper.randRange(150, 300, random);
		this.quadSize = ModMathHelper.randRange(0.05F, 0.2F, random);
		this.alpha = 0F;
		
		preVX = random.nextGaussian() * 0.015;
		preVY = random.nextGaussian() * 0.015;
		preVZ = random.nextGaussian() * 0.015;
		
		nextVX = random.nextGaussian() * 0.015;
		nextVY = random.nextGaussian() * 0.015;
		nextVZ = random.nextGaussian() * 0.015;
	}

	@Override
	public IParticleRenderType getRenderType() 
	{
		return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public void tick()
	{
		ticks ++;
		if (ticks > 200) 
		{
			preVX = nextVX;
			preVY = nextVY;
			preVZ = nextVZ;
			nextVX = random.nextGaussian() * 0.015;
			nextVY = random.nextGaussian() * 0.015;
			nextVZ = random.nextGaussian() * 0.015;
			if (random.nextInt(4) == 0) 
			{
				nextVY = Math.abs(nextVY);
			}
			ticks = 0;
		}
		double delta = (double) ticks / 200.0;
		
		if (this.age <= 40) {
			this.setAlpha(this.age / 40F);
		}
		else if (this.age >= this.lifetime - 40) {
			this.setAlpha((this.lifetime - this.age) / 40F);
		}
		
		if (this.age >= this.lifetime) {
			this.remove();
		}
		
		this.xd = MathHelper.lerp(delta, preVX, nextVX);
		this.yd = MathHelper.lerp(delta, preVY, nextVY);
		this.zd = MathHelper.lerp(delta, preVZ, nextVZ);
		
		super.tick();
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType> 
	{
		private final IAnimatedSprite sprite;

	    public Factory(IAnimatedSprite sprite) 
	    {
	         this.sprite = sprite;
	    }
	    
	    @Override
	    public Particle createParticle(BasicParticleType type, ClientWorld worldIn, double x, double y, double z,
	    		double xSpeed, double ySpeed, double zSpeed) 
	    {
	    	return new SnowflakeParticle(worldIn, x, y, z, 1, 1, 1, sprite);
	    }
	}
}
