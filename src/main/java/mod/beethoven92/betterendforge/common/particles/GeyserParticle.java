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
		
		this.setSpriteFromAge(spriteWithAge);
		
		this.lifetime = ModMathHelper.randRange(400, 800, random);
		this.quadSize = ModMathHelper.randRange(0.5F, 1.0F, random);
		
		this.xd = vx;
		this.zd = vz;
		this.yo = y - 0.125;
	}

	@Override
	public IParticleRenderType getRenderType() 
	{
		return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}
	
	@Override
	public void tick() 
	{
		if (this.yo == this.y || this.age > this.lifetime) 
		{
			this.remove();
		}
		else 
		{
			if (this.age >= this.lifetime - 200) 
			{
				this.setAlpha((this.lifetime - this.age) / 200F);
			}
			
			this.quadSize += 0.005F;
			this.yd = 0.125;
			
			if (changeDir) 
			{
				changeDir = false;
				check = false;
				this.xd += ModMathHelper.randRange(-0.2, 0.2, random);
				this.zd += ModMathHelper.randRange(-0.2, 0.2, random);
			}
			else if (check) 
			{
				changeDir = level.getBlockState(mut.set(x, y, z)).getFluidState().isEmpty();
				this.xd = 0;
				this.zd = 0;
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
	    public Particle createParticle(BasicParticleType type, ClientWorld worldIn, double x, double y, double z,
	    		double xSpeed, double ySpeed, double zSpeed) 
	    {
	    	return new GeyserParticle(worldIn, x, y, z, 0, 0.125, 0, sprite);
	    }
	}
}
