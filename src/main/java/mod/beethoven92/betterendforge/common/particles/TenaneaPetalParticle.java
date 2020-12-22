package mod.beethoven92.betterendforge.common.particles;

import mod.beethoven92.betterendforge.common.block.TenaneaFlowersBlock;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TenaneaPetalParticle extends SpriteTexturedParticle
{
	private double preVX;
	private double preVY;
	private double preVZ;
	private double nextVX;
	private double nextVY;
	private double nextVZ;

	protected TenaneaPetalParticle(ClientWorld world, double x, double y, double z, double r, 
			double g, double b, IAnimatedSprite spriteWithAge)
	{
		super(world, x, y, z, r, g, b);
		
		this.selectSpriteWithAge(spriteWithAge);
		
		int color = TenaneaFlowersBlock.getBlockColor(new BlockPos(x, y, z));
		
		this.particleRed = ((color >> 16) & 255) / 255F;
		this.particleGreen = ((color >> 8) & 255) / 255F;
		this.particleBlue = ((color) & 255) / 255F;
		
		this.maxAge = ModMathHelper.randRange(120, 200, rand);
		this.particleScale = ModMathHelper.randRange(0.05F, 0.15F, rand);
		this.particleAlpha = 0;
		
		preVX = 0;
		preVY = 0;
		preVZ = 0;
		
		nextVX = rand.nextGaussian() * 0.02;
		nextVY = -rand.nextDouble() * 0.02 - 0.02;
		nextVZ = rand.nextGaussian() * 0.02;
	}

	@Override
	public IParticleRenderType getRenderType() 
	{
		return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}
	
	@Override
	public void tick() 
	{
		int ticks = this.age & 63;
		if (ticks == 0) 
		{
			preVX = nextVX;
			preVY = nextVY;
			preVZ = nextVZ;
			nextVX = rand.nextGaussian() * 0.02;
			nextVY = -rand.nextDouble() * 0.02 - 0.02;
			nextVZ = rand.nextGaussian() * 0.02;
		}
		
		double delta = (double) ticks / 63.0;
		
		if (this.age <= 40) 
		{
			this.setAlphaF(this.age / 40F);
		}
		else if (this.age >= this.maxAge - 40) 
		{
			this.setAlphaF((this.maxAge - this.age) / 40F);
		}
		
		if (this.age >= this.maxAge) 
		{
			this.setExpired();
		}
		
		this.motionX = MathHelper.lerp(delta, preVX, nextVX);
		this.motionY = MathHelper.lerp(delta, preVY, nextVY);
		this.motionZ = MathHelper.lerp(delta, preVZ, nextVZ);
		
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
	    	return new TenaneaPetalParticle(worldIn, x, y, z, 1, 1, 1, sprite);
	    }
	}
}
