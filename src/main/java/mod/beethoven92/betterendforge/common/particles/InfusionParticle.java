package mod.beethoven92.betterendforge.common.particles;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class InfusionParticle extends SpriteTexturedParticle
{
	private final IAnimatedSprite spriteWithAge;

	public InfusionParticle(ClientWorld clientWorld, double x, double y, double z, double velocityX, 
			double velocityY, double velocityZ, float[] palette, IAnimatedSprite spriteWithAge)
	{
		super(clientWorld, x, y, z, 0.0, 0.0, 0.0);
		this.spriteWithAge = spriteWithAge;
		this.selectSpriteWithAge(spriteWithAge);
		this.setColor(palette[0], palette[1], palette[2]);
		this.particleAlpha = palette[3];
		this.motionX = velocityX * 0.1D;
		this.motionY = velocityY * 0.1D;
		this.motionZ = velocityZ * 0.1D;
		this.maxAge = (int) (3.0F / (this.rand.nextFloat() * 0.9F + 0.1F));
		this.particleScale *= 0.9F;
	}

	@Override
	public IParticleRenderType getRenderType() 
	{
		return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public void tick() 
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if (this.age++ >= this.maxAge) 
		{
			this.setExpired();
		}
		else 
		{
			this.selectSpriteWithAge(this.spriteWithAge);
			double velocityX = 2.0D * this.motionX * this.rand.nextDouble();
			double velocityY = 3.0D * this.motionY * this.rand.nextDouble();
			double velocityZ = 2.0D * this.motionZ * this.rand.nextDouble();
			this.move(velocityX, velocityY, velocityZ);
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<InfusionParticleData> 
	{
		private final IAnimatedSprite sprite;

	    public Factory(IAnimatedSprite sprite) 
	    {
	         this.sprite = sprite;
	    }
	    
	    @Override
	    public Particle makeParticle(InfusionParticleData data, ClientWorld worldIn, double x, double y, double z,
	    		double xSpeed, double ySpeed, double zSpeed) 
	    {
	    	return new InfusionParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, data.getPalette(), this.sprite);
	    }
	}
}
