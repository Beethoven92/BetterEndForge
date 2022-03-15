package mod.beethoven92.betterendforge.common.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;

public class NbtModIdReplacer 
{
	// Helper method to replace mod ids of blocks inside nbt structures with my mod id
	// Saves the updated file in the specified location
	public static void readAndReplace(CompoundTag compound, String replacePath)
	{
		ListTag palette = compound.getList("palette", 10);

	    for(int i = 0; i < palette.size(); ++i) 
	    {
	    	if (palette.getCompound(i).contains("Name", 8)) 
	    	{
	    		ResourceLocation registryName = new ResourceLocation(palette.getCompound(i).getString("Name"));
	    		String namespace = registryName.getNamespace();
	    		String path = registryName.getPath();
	    		if (namespace.equals("betterend"))
	    		{
	    			ResourceLocation newRegistryName = new ResourceLocation("betterendforge", path);
	    			palette.getCompound(i).putString("Name", newRegistryName.toString());
	    		}
	    	}
	    }
	    compound.put("palette", palette);

	    // Insert your file path here
	    String filePath = "";
	    
        String fileToSave = filePath + replacePath + ".nbt";  
       
        try (OutputStream outputstream = new FileOutputStream(fileToSave)) 
        {
            NbtIo.writeCompressed(compound, outputstream);
            BetterEnd.LOGGER.debug("STRUCTURE: " + replacePath + " WROTE TO FILE");
        } 
        catch (IOException exception)
        {
        	exception.printStackTrace();
        }
	}
}
