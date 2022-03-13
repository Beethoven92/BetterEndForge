package mod.beethoven92.betterendforge.common.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;

import mod.beethoven92.betterendforge.common.init.ModParticleTypes;
import mod.beethoven92.betterendforge.common.util.ColorHelper;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.command.arguments.ItemParser;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class InfusionParticleData implements IParticleData
{
	public static final IParticleData.IDeserializer<InfusionParticleData> DESERIALIZER = 
			new IParticleData.IDeserializer<InfusionParticleData>()
	{
		public InfusionParticleData fromCommand(ParticleType<InfusionParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException
		{
			reader.expect(' ');
		    ItemParser itemparser = (new ItemParser(reader, false)).parse();
		    ItemStack itemstack = (new ItemInput(itemparser.getItem(), itemparser.getNbt())).createItemStack(1, false);
		    return new InfusionParticleData(itemstack);
		}

		public InfusionParticleData fromNetwork(ParticleType<InfusionParticleData> particleTypeIn, PacketBuffer buffer) 
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
	public void writeToNetwork(PacketBuffer buffer) 
	{
		buffer.writeItem(itemStack);
	}

	@Override
	public String writeToString() 
	{
		return Registry.PARTICLE_TYPE.getKey(this.getType()).toString();
	}

}
