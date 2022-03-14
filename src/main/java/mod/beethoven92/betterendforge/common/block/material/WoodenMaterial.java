package mod.beethoven92.betterendforge.common.block.material;

import java.util.function.Supplier;

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
import net.minecraftforge.registries.RegistryObject;

// TO DO? Make all wooden blocks flammable so they can take and spread fire
public class WoodenMaterial 
{
	public final String name;

	public final RegistryObject<Block> log;
	public final RegistryObject<Block> bark;

	public final RegistryObject<Block> log_stripped;
	public final RegistryObject<Block> bark_stripped;

	public final RegistryObject<Block> planks;

	public final RegistryObject<Block> stairs;
	public final RegistryObject<Block> slab;
	public final RegistryObject<Block> fence;
	public final RegistryObject<Block> gate;
	public final RegistryObject<Block> button;
	public final RegistryObject<Block> pressurePlate;
	public final RegistryObject<Block> trapdoor;
	public final RegistryObject<Block> door;

	public final RegistryObject<Block> craftingTable;
	public final RegistryObject<Block> ladder;
	public final RegistryObject<Block> sign;

	public final RegistryObject<Block> chest;
	public final RegistryObject<Block> barrel;
	public final RegistryObject<Block> shelf;
	public final RegistryObject<Block> composter;

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
				() -> new PillarBlockTemplate(materialPlanks));
		bark_stripped = ModBlocks.registerBlockWithDefaultItem(name + "_stripped_bark", 
				() -> new BarkBlockTemplate(materialPlanks));
		
		log = ModBlocks.registerBlockWithDefaultItem(name + "_log", 
				() -> new StripableLogBlockTemplate(woodColor, log_stripped.get()));
		bark = ModBlocks.registerBlockWithDefaultItem(name + "_bark", 
				() -> new StripableBarkBlockTemplate(woodColor, bark_stripped.get()));
		
		planks = ModBlocks.registerBlockWithDefaultItem(name + "_planks", 
				() -> new Block(materialPlanks));
		stairs = ModBlocks.registerBlockWithDefaultItem(name + "_stairs", 
				() -> new StairBlock(() -> planks.get().defaultBlockState(), materialPlanks));
		slab = ModBlocks.registerBlockWithDefaultItem(name + "_slab", 
				() -> new SlabBlock(materialPlanks));
		fence = ModBlocks.registerBlockWithDefaultItem(name + "_fence", 
				() -> new FenceBlock(materialPlanks));
		gate = ModBlocks.registerBlockWithDefaultItem(name + "_gate", 
				() -> new FenceGateBlock(materialPlanks));
		button = ModBlocks.registerBlockWithDefaultItem(name + "_button", 
				() -> new WoodButtonBlock(materialPlanks));
		pressurePlate = ModBlocks.registerBlockWithDefaultItem(name + "_pressure_plate", 
				() -> new PressurePlateBlock(Sensitivity.EVERYTHING, materialPlanks));
		trapdoor = ModBlocks.registerBlockWithDefaultItem(name + "_trapdoor", 
				() -> new TrapDoorBlock(materialPlanksNotSolid));
		door = ModBlocks.registerBlockWithDefaultItem(name + "_door", 
				() -> new DoorBlock(materialPlanksNotSolid));
		
		composter = registerBlockWithBurnItem(name + "_composter", 
				() -> new ComposterBlock(materialPlanksNotSolid), 300);
		craftingTable = registerBlockWithBurnItem(name + "_crafting_table", 
				() -> new ModCraftingTableBlock(materialPlanks), 300);
		ladder = registerBlockWithBurnItem(name + "_ladder", 
				() -> new LadderBlock(materialPlanksNotSolid), 300);
		chest = ModBlocks.registerBlock(name + "_chest",
				() -> new ChestBlock(materialPlanksNotSolid, () -> ModTileEntityTypes.CHEST.get()) {
					public net.minecraft.world.level.block.entity.BlockEntity createTileEntity(BlockState state,
							net.minecraft.world.level.BlockGetter world) {
						return ModTileEntityTypes.CHEST.get().create();
					};
				});
		ModItems.ITEMS.register(name + "_chest", () -> new BlockItem(chest.get(), new Item.Properties()
				.tab(ModCreativeTabs.CREATIVE_TAB).setISTER(() -> ChestItemTileEntityRenderer::new)) {
			public int getBurnTime(net.minecraft.world.item.ItemStack itemStack) {
				return 300;
			};
		});

		sign = registerBlockWithBurnItem(name + "_sign", 
				() -> new EndSignBlock(BlockBehaviour.Properties.of(Material.WOOD, planksColor).
		                strength(2.0F, 3.0F).
		                sound(SoundType.WOOD).
		                noOcclusion().noCollission()), 200);
		barrel = registerBlockWithBurnItem(name + "_barrel",
				() -> new EndBarrelBlock(materialPlanksNotSolid), 300);
		shelf = registerBlockWithBurnItem(name + "_bookshelf",
				() -> new Block(materialPlanks) {
					@Override
					public float getEnchantPowerBonus(BlockState state, LevelReader world, BlockPos pos) 
					{
						return 1;
					};
				}, 300);
	}

	public boolean isTreeLog(Block block) {
		return block == log.get() || block == bark.get();
	}

	public boolean isTreeLog(BlockState state) {
		return isTreeLog(state.getBlock());
	}
	
	private static <T extends Block> RegistryObject<T> registerBlockWithBurnItem(String name,
			Supplier<? extends T> blockSupplier, int burnTime) {
		RegistryObject<T> block = ModBlocks.BLOCKS.register(name, blockSupplier);
		ModItems.ITEMS.register(name,
				() -> new BlockItem(block.get(), new Item.Properties().tab(ModCreativeTabs.CREATIVE_TAB)) {
					public int getBurnTime(net.minecraft.world.item.ItemStack itemStack) {
						return burnTime;
					};
				});
		return block;
	}
}
