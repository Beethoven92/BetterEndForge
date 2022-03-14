package mod.beethoven92.betterendforge.common.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;

import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.util.ColorHelper;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.commands.arguments.item.ItemParser;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class InfusionParticleData implements ParticleOptions
{
	public static final ParticleOptions.Deserializer<InfusionParticleData> DESERIALIZER = 
			new ParticleOptions.Deserializer<InfusionParticleData>()
	{
		public InfusionParticleData fromCommand(ParticleType<InfusionParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
		{
			reader.expect(' ');
		    ItemParser itemparser = (new ItemParser(reader, false)).parse();
		    ItemStack itemstack = (new ItemInput(itemparser.getItem(), itemparser.getNbt())).createItemStack(1, false);
		    return new InfusionParticleData(itemstack);
		}

		public InfusionParticleData fromNetwork(ParticleType<InfusionParticleData> particleTypeIn, FriendlyByteBuf buffer) 
		{
			return new InfusionParticleData(buffer.readItem());
		}
	};

	public static final Codec<InfusionParticleData> CODEC = ItemStack.CODEC.xmap(itemStack -> {
		return new InfusionParticleData(itemStack);
	}, infusionParticleType -> {
		return infusionParticleType.itemStack;
	});
	
	private ParticleType<InfusionParticleData> type;
	private ItemStack itemStack;
	
	public InfusionParticleData(ItemStack stack) 
	{
		this.type = ModParticleTypes.INFUSION.get();
		this.itemStack = stack;
	}
	
	@OnlyIn(Dist.CLIENT)
	public float[] getPalette() 
	{
		int color = ColorHelper.extractColor(itemStack.getItem());
		return ColorHelper.toFloatArray(color);
	}

	@Override
	public ParticleType<?> getType() 
	{
		return this.type;
	}

	@Override
	public void writeToNetwork(FriendlyByteBuf buffer) 
	{
		buffer.writeItem(itemStack);
	}

	@Override
	public String writeToString() 
	{
		return Registry.PARTICLE_TYPE.getKey(this.getType()).toString();
	}

}
