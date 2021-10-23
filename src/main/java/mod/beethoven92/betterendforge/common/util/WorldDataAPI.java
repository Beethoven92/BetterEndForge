package mod.beethoven92.betterendforge.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WorldDataAPI {
    private static final Map<String, CompoundNBT> TAGS = Maps.newHashMap();
    private static final List<String> MODS = Lists.newArrayList();
    private static File dataDir;


    public static CompoundNBT getRootTag(String modID) {
        CompoundNBT root = TAGS.get(modID);
        if (root == null) {
            root = new CompoundNBT();
            TAGS.put(modID, root);
        }
        return root;
    }
    public static CompoundNBT getCompoundTag(String modID, String path) {
        String[] parts = path.split("\\.");
        CompoundNBT tag = getRootTag(modID);
        for (String part : parts) {
            if (tag.contains(part)) {
                tag = tag.getCompound(part);
            }
            else {
                CompoundNBT t = new CompoundNBT();
                tag.put(part, t);
                tag = t;
            }
        }
        return tag;
    }
}
