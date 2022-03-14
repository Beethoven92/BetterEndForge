package mod.beethoven92.betterendforge.common.block.material;

import java.util.function.Supplier;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.client.renderer.ChestItemTileEntityRenderer;
import mod.beethoven92.betterendforge.common.block.EndBarrelBlock;
import mod.beethoven92.betterendforge.common.block.EndSignBlock;
import mod.beethoven92.betterendforge.common.block.ModCraftingTableBlock;
import mod.beethoven92.betterendforge.common.block.template.BarkBlockTemplate;
import mod.beethoven92.betterendforge.common.block.template.PillarBlockTemplate;
import mod.beethoven92.betterendforge.common.block.template.StripableBarkBlockTemplate;
import mod.beethoven92.betterendforge.common.block.template.StripableLogBlockTemplate;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModCreativeTabs;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.PressurePlateBlock.Sensitivity;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.tags.Tag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.fml.RegistryObject;

// TO DO? Make all wooden blocks flammable so they can take and spread fire
public class WoodenMaterial 
{
	public final String name;

	public final Block log;
	public final Block bark;

	public final Block log_stripped;
	public final Block bark_stripped;

	public final Block planks;

	public final Block stairs;
	public final Block slab;
	public final Block fence;
	public final Block gate;
	public final Block button;
	public final Block pressurePlate;
	public final Block trapdoor;
	public final Block door;

	public final Block craftingTable;
	public final Block ladder;
	public final Block sign;

	public final Block chest;
	public final Block barrel;
	public final Block shelf;
	public final Block composter;

	public final Tag.Named<Block> logBlockTag;
	public final Tag.Named<Item> logItemTag;

	public WoodenMaterial(String name, MaterialColor woodColor, MaterialColor planksColor) 
	{
		this.name = name;
		
		logBlockTag = ModTags.makeModBlockTag(name + "_logs");
		logItemTag = ModTags.makeModItemTag(name + "_logs");

		BlockBehaviour.Properties materialPlanks = BlockBehaviour.Properties.of(Material.WOOD, planksColor).
				                                                           strength(2.0F, 3.0F).
				                                                           sound(SoundType.WOOD);
		BlockBehaviour.Properties materialPlanksNotSolid = BlockBehaviour.Properties.of(Material.WOOD, planksColor).
                strength(2.0F, 3.0F).
                sound(SoundType.WOOD).
                noOcclusion();
		
		log_stripped = ModBlocks.registerBlockWithDefaultItem(name + "_stripped_log", 
				new PillarBlockTemplate(materialPlanks));
		bark_stripped = ModBlocks.registerBlockWithDefaultItem(name + "_stripped_bark", 
				new BarkBlockTemplate(materialPlanks));
		
		log = ModBlocks.registerBlockWithDefaultItem(name + "_log", 
				new StripableLogBlockTemplate(woodColor, log_stripped));
		bark = ModBlocks.registerBlockWithDefaultItem(name + "_bark", 
				new StripableBarkBlockTemplate(woodColor, bark_stripped));
		
		planks = ModBlocks.registerBlockWithDefaultItem(name + "_planks", 
				new Block(materialPlanks));
		stairs = ModBlocks.registerBlockWithDefaultItem(name + "_stairs", 
				new StairBlock(planks.defaultBlockState(), materialPlanks));
		slab = ModBlocks.registerBlockWithDefaultItem(name + "_slab", 
				new SlabBlock(materialPlanks));
		fence = ModBlocks.registerBlockWithDefaultItem(name + "_fence", 
				new FenceBlock(materialPlanks));
		gate = ModBlocks.registerBlockWithDefaultItem(name + "_gate", 
				new FenceGateBlock(materialPlanks));
		button = ModBlocks.registerBlockWithDefaultItem(name + "_button", 
				new WoodButtonBlock(materialPlanks));
		pressurePlate = ModBlocks.registerBlockWithDefaultItem(name + "_pressure_plate", 
				new PressurePlateBlock(Sensitivity.EVERYTHING, materialPlanks));
		trapdoor = ModBlocks.registerBlockWithDefaultItem(name + "_trapdoor", 
				new TrapDoorBlock(materialPlanksNotSolid));
		door = ModBlocks.registerBlockWithDefaultItem(name + "_door", 
				new DoorBlock(materialPlanksNotSolid));
		
		composter = registerBlockWithBurnItem(name + "_composter", 
				new ComposterBlock(materialPlanksNotSolid), 300);
		craftingTable = registerBlockWithBurnItem(name + "_crafting_table", 
				new ModCraftingTableBlock(materialPlanks), 300);
		ladder = registerBlockWithBurnItem(name + "_ladder", 
				new LadderBlock(materialPlanksNotSolid), 300);
		chest = ModBlocks.registerBlock(name + "_chest",
				new ChestBlock(materialPlanksNotSolid, ModTileEntityTypes.CHEST) {
					public net.minecraft.world.level.block.entity.BlockEntity createTileEntity(BlockState state,
							net.minecraft.world.level.BlockGetter world) {
						return ModTileEntityTypes.CHEST.create();
					};
				});
		ModItems.ITEMS.register(name + "_chest", new BlockItem(chest, new Item.Properties()
				.tab(ModCreativeTabs.CREATIVE_TAB).setISTER(ChestItemTileEntityRenderer::new)) {
			public int getBurnTime(net.minecraft.world.item.ItemStack itemStack) {
				return 300;
			};
		});

		sign = registerBlockWithBurnItem(name + "_sign", 
				new EndSignBlock(BlockBehaviour.Properties.of(Material.WOOD, planksColor).
		                strength(2.0F, 3.0F).
		                sound(SoundType.WOOD).
		                noOcclusion().noCollission()), 200);
		barrel = registerBlockWithBurnItem(name + "_barrel",
				new EndBarrelBlock(materialPlanksNotSolid), 300);
		shelf = registerBlockWithBurnItem(name + "_bookshelf",
				new Block(materialPlanks) {
					@Override
					public float getEnchantPowerBonus(BlockState state, LevelReader world, BlockPos pos) 
					{
						return 1;
					};
				}, 300);
	}

	public boolean isTreeLog(Block block) {
		return block == log || block == bark;
	}

	public boolean isTreeLog(BlockState state) {
		return isTreeLog(state.getBlock());
	}
	
	private static <T extends Block> T registerBlockWithBurnItem(String name,
			T block, int burnTime) {
		ModBlocks.BLOCKS.put(new ResourceLocation(BetterEnd.MOD_ID, name), block);
		ModItems.ITEMS.register(name,
				new BlockItem(block, new Item.Properties().tab(ModCreativeTabs.CREATIVE_TAB)) {
					public int getBurnTime(net.minecraft.world.item.ItemStack itemStack) {
						return burnTime;
					};
				});
		return block;
	}
}
