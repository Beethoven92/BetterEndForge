package mod.beethoven92.betterendforge.common.particles;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GeyserParticle extends SpriteTexturedParticle
{
	private Mutable mut = new Mutable();
	private boolean changeDir = false;
	private boolean check = true;

	protected GeyserParticle(ClientWorld world, double x, double y, double z, double vx, 
			double vy, double vz, IAnimatedSprite spriteWithAge)
	{
		super(world, x, y, z, vx, vy, vz);
		
		this.selectSpriteWithAge(spriteWithAge);
		
		this.maxAge = ModMathHelper.randRange(400, 800, rand);
		this.particleScale = ModMathHelper.randRange(0.5F, 1.0F, rand);
		
		this.motionX = vx;
		this.motionZ = vz;
		this.prevPosY = y - 0.125;
	}

	@Override
	public IParticleRenderType getRenderType() 
	{
		return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}
	
	@Override
	public void tick() 
	{
		if (this.prevPosY == this.posY || this.age > this.maxAge) 
		{
			this.setExpired();
		}
		else 
		{
			if (this.age >= this.maxAge - 200) 
			{
				this.setAlphaF((this.maxAge - this.age) / 200F);
			}
			
			this.particleScale += 0.005F;
			this.motionY = 0.125;
			
			if (changeDir) 
			{
				changeDir = false;
				check = false;
				this.motionX += ModMathHelper.randRange(-0.2, 0.2, rand);
				this.motionZ += ModMathHelper.randRange(-0.2, 0.2, rand);
			}
			else if (check) 
			{
				changeDir = world.getBlockState(mut.setPos(posX, posY, posZ)).getFluidState().isEmpty();
				this.motionX = 0;
				this.motionZ = 0;
			}
		}
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
	    public Particle makeParticle(BasicParticleType type, ClientWorld worldIn, double x, double y, double z,
	    		double xSpeed, double ySpeed, double zSpeed) 
	    {
	    	return new GeyserParticle(worldIn, x, y, z, 0, 0.125, 0, sprite);
	    }
	}
}
