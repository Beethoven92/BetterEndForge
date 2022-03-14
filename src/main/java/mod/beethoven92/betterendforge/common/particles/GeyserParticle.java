package mod.beethoven92.betterendforge.common.particles;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GeyserParticle extends TextureSheetParticle
{
	private MutableBlockPos mut = new MutableBlockPos();
	private boolean changeDir = false;
	private boolean check = true;

	protected GeyserParticle(ClientLevel world, double x, double y, double z, double vx, 
			double vy, double vz, SpriteSet spriteWithAge)
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
	public ParticleRenderType getRenderType() 
	{
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
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
	    	return new GeyserParticle(worldIn, x, y, z, 0, 0.125, 0, sprite);
	    }
	}
}
