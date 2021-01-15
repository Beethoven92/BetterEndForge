package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.EndVineBlock;
import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;

public class TenaneaFlowersBlock extends EndVineBlock
{
	public static final Vector3i[] COLORS;
	
	static 
	{
		COLORS = new Vector3i[] {
			new Vector3i(250, 111, 222),
			new Vector3i(167, 89, 255),
			new Vector3i(120, 207, 239),
			new Vector3i(255, 87, 182)
		};
	}
	
	public TenaneaFlowersBlock(Properties properties) 
	{
		super(properties);
	}

	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		super.animateTick(stateIn, worldIn, pos, rand);
		if (rand.nextInt(32) == 0) {
			double x = (double) pos.getX() + rand.nextGaussian() + 0.5;
			double z = (double) pos.getZ() + rand.nextGaussian() + 0.5;
			double y = (double) pos.getY() + rand.nextDouble();
			worldIn.addParticle(ModParticleTypes.TENANEA_PETAL.get(), x, y, z, 0, 0, 0);
		}
	}

	public static int getBlockColor(BlockPos pos) 
	{
		long i = (ModMathHelper.getRandom(pos.getX(), pos.getZ()) & 63) + pos.getY();
		
		double delta = i * 0.1;
		int index = ModMathHelper.floor(delta);
		int index2 = (index + 1) & 3;
		delta -= index;
		index &= 3;
			
		Vector3i color1 = COLORS[index];
		Vector3i color2 = COLORS[index2];
			
		int r = ModMathHelper.floor(MathHelper.lerp(delta, color1.getX(), color2.getX()));
		int g = ModMathHelper.floor(MathHelper.lerp(delta, color1.getY(), color2.getY()));
		int b = ModMathHelper.floor(MathHelper.lerp(delta, color1.getZ(), color2.getZ()));
		float[] hsb = ModMathHelper.fromRGBtoHSB(r, g, b);
			
		return ModMathHelper.fromHSBtoRGB(hsb[0], ModMathHelper.max(0.5F, hsb[1]), hsb[2]);
	}

	public static int getItemColor() 
	{
		return ModMathHelper.color(255, 255, 255);
	}
}
