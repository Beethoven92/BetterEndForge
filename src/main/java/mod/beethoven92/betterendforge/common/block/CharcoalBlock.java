package mod.beethoven92.betterendforge.common.block;


import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.FuelRegistry;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModCreativeTabs;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.*;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;


public class CharcoalBlock extends Block{

    public CharcoalBlock(AbstractBlock.Properties properties)
    {
        super(properties);
    }
}
