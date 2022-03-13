package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3i;

import net.minecraft.block.AbstractBlock.Properties;

public class AuroraCrystalBlock extends AbstractGlassBlock
{	
	public static final Vector3i[] COLORS;
	
	static 
	{
		COLORS = new Vector3i[] 
				{
						new Vector3i(247,  77, 161),
						new Vector3i(120, 184, 255),
						new Vector3i(120, 255, 168),
						new Vector3i(243,  58, 255)
				};
	}
	
	public AuroraCrystalBlock(Properties properties) 
	{
		super(properties);
	}

	public static int getBlockColor(BlockPos pos) 
	{
		long i = (long) pos.getX() + (long) pos.getY() + (long) pos.getZ();
		double delta = i * 0.1;
		int index = MathHelper.floor(delta);
		int index2 = (index + 1) & 3;
		delta -= index;
		index &= 3;
			
		Vector3i color1 = COLORS[index];
		Vector3i color2 = COLORS[index2];
			
		int r = MathHelper.floor(MathHelper.lerp(delta, color1.getX(), color2.getX()));
		int g = MathHelper.floor(MathHelper.lerp(delta, color1.getY(), color2.getY()));
		int b = MathHelper.floor(MathHelper.lerp(delta, color1.getZ(), color2.getZ()));
			
		return ModMathHelper.color(r, g, b);
	}
	
	public static int getItemColor() 
	{
		return ModMathHelper.color(COLORS[3].getX(), COLORS[3].getY(), COLORS[3].getZ());
	}
}
