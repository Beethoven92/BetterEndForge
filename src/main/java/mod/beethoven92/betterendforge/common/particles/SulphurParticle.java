package mod.beethoven92.betterendforge.common.particles;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SulphurParticle extends TextureSheetParticle
{
	private int ticks;
	private double preVX;
	private double preVY;
	private double preVZ;
	private double nextVX;
	private double nextVY;
	private double nextVZ;

	protected SulphurParticle(ClientLevel world, double x, double y, double z, double r, 
			double g, double b, SpriteSet spriteWithAge)
	{
		super(world, x, y, z, r, g, b);
		
		this.setSpriteFromAge(spriteWithAge);
		
		this.lifetime = ModMathHelper.randRange(150, 300, random);
		this.quadSize = ModMathHelper.randRange(0.05F, 0.15F, random);
		this.setColor(1, 1, 1);
		this.alpha = 0;
		
		preVX = random.nextGaussian() * 0.015;
		preVY = random.nextGaussian() * 0.015;
		preVZ = random.nextGaussian() * 0.015;
		
		nextVX = random.nextGaussian() * 0.015;
		nextVY = random.nextGaussian() * 0.015;
		nextVZ = random.nextGaussian() * 0.015;
	}

	@Override
	public ParticleRenderType getRenderType() 
	{
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
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
		
		if (this.age <= 40) 
		{
			this.setAlpha(this.age / 40F);
		}
		else if (this.age >= this.lifetime - 40) 
		{
			this.setAlpha((this.lifetime - this.age) / 40F);
		}
		
		if (this.age >= this.lifetime) 
		{
			this.remove();
		}
		
		this.xd = Mth.lerp(delta, preVX, nextVX);
		this.yd = Mth.lerp(delta, preVY, nextVY);
		this.zd = Mth.lerp(delta, preVZ, nextVZ);
		
		super.tick();
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Factory implements ParticleProvider<SimpleParticleType> 
	{
		private final SpriteSet sprite;

	    public Factory(SpriteSet sprite) 
	    {
	         this.sprite = sprite;
	    }
	    
	    @Override
	    public Particle createParticle(SimpleParticleType type, ClientLevel worldIn, double x, double y, double z,
	    		double xSpeed, double ySpeed, double zSpeed) 
	    {
	    	return new SulphurParticle(worldIn, x, y, z, 1, 1, 1, sprite);
	    }
	}

}
