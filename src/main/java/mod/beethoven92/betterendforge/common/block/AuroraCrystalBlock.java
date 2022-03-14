package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.core.Vec3i;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class AuroraCrystalBlock extends AbstractGlassBlock
{	
	public static final Vec3i[] COLORS;
	
	static 
	{
		COLORS = new Vec3i[] 
				{
						new Vec3i(247,  77, 161),
						new Vec3i(120, 184, 255),
						new Vec3i(120, 255, 168),
						new Vec3i(243,  58, 255)
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
		int index = Mth.floor(delta);
		int index2 = (index + 1) & 3;
		delta -= index;
		index &= 3;
			
		Vec3i color1 = COLORS[index];
		Vec3i color2 = COLORS[index2];
			
		int r = Mth.floor(Mth.lerp(delta, color1.getX(), color2.getX()));
		int g = Mth.floor(Mth.lerp(delta, color1.getY(), color2.getY()));
		int b = Mth.floor(Mth.lerp(delta, color1.getZ(), color2.getZ()));
			
		return ModMathHelper.color(r, g, b);
	}
	
	public static int getItemColor() 
	{
		return ModMathHelper.color(COLORS[3].getX(), COLORS[3].getY(), COLORS[3].getZ());
	}
}
