package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.teleporter.EndPortals;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.IStringSerializable;

public class BlockProperties 
{
	public static final EnumProperty<TripleShape> TRIPLE_SHAPE = EnumProperty.create("shape", TripleShape.class);
	public static final EnumProperty<PedestalState> PEDESTAL_STATE = EnumProperty.create("state", PedestalState.class);
	public static final EnumProperty<HydraluxShape> HYDRALUX_SHAPE = EnumProperty.create("shape", HydraluxShape.class);
	public static final EnumProperty<PentaShape> PENTA_SHAPE = EnumProperty.create("shape", PentaShape.class);
	public static final EnumProperty<CactusBottom> CACTUS_BOTTOM = EnumProperty.create("bottom", CactusBottom.class);

	
	public static final BooleanProperty HAS_ITEM = BooleanProperty.create("has_item");
	public static final BooleanProperty HAS_LIGHT = BooleanProperty.create("has_light");
	public static final BooleanProperty ACTIVATED = BooleanProperty.create("active");
	public static final BooleanProperty NATURAL = BooleanProperty.create("natural");
	public static final BooleanProperty IS_FLOOR = BooleanProperty.create("is_floor");
	
	public static final IntegerProperty ROTATION = IntegerProperty.create("rotation", 0, 3);
	public static final IntegerProperty FULLNESS = IntegerProperty.create("fullness", 0, 3);
	public static final IntegerProperty DESTRUCTION = IntegerProperty.create("destruction", 0, 2);
	public static final IntegerProperty DESTRUCTION_LONG = IntegerProperty.create("destruction", 0, 8);
	public static final IntegerProperty PORTAL = IntegerProperty.create("portal", 0, EndPortals.getCount());
	public static final IntegerProperty SIZE = IntegerProperty.create("size", 0, 7);
	
	public static enum TripleShape implements IStringSerializable 
	{
		TOP("top", 0),
		MIDDLE("middle", 1),
		BOTTOM("bottom", 2);
		
		private final String name;
		private final int index;
		
		TripleShape(String name, int index) 
		{
			this.name = name;
			this.index = index;
		}
		
		@Override
		public String getString() 
		{
			return name;
		}
		
		@Override
		public String toString() 
		{
			return name;
		}
		
		public int getIndex() {
			return index;
		}
		
		public static TripleShape fromIndex(int index) {
			return index > 1 ? BOTTOM : index == 1 ? MIDDLE : TOP;
		}
	}
	
	public static enum PedestalState implements IStringSerializable 
	{
		PEDESTAL_TOP("pedestal_top"),
		COLUMN_TOP("column_top"),
		BOTTOM("bottom"),
		PILLAR("pillar"),
		COLUMN("column"),
		DEFAULT("default");
		
		private final String name;
		
		PedestalState(String name) 
		{
			this.name = name;
		}

		@Override
		public String toString()
		{
			return this.name;
		}

		@Override
		public String getString() 
		{
			return name;
		}
	}
	
	public static enum HydraluxShape implements IStringSerializable
	{
		FLOWER_BIG_BOTTOM("flower_big_bottom", true),
		FLOWER_BIG_TOP("flower_big_top", true),
		FLOWER_SMALL_BOTTOM("flower_small_bottom", true),
		FLOWER_SMALL_TOP("flower_small_top", true),
		VINE("vine", false),
		ROOTS("roots", false);
		
		private final String name;
		private final boolean glow;
		
		HydraluxShape(String name, boolean glow) 
		{
			this.name = name;
			this.glow = glow;
		}

		@Override
		public String getString() 
		{
			return name;
		}
		
		@Override
		public String toString()
		{
			return name;
		}
		
		public boolean hasGlow() 
		{
			return glow;
		}
	}
	
	public static enum PentaShape implements IStringSerializable {
		BOTTOM("bottom"),
		PRE_BOTTOM("pre_bottom"),
		MIDDLE("middle"),
		PRE_TOP("pre_top"),
		TOP("top");
		
		private final String name;
		
		PentaShape(String name) {
			this.name = name;
		}

		@Override
		public String getString() {
			return name;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	public static enum LumecornShape implements IStringSerializable {
		LIGHT_TOP("light_top", 15),
		LIGHT_TOP_MIDDLE("light_top_middle", 15),
		LIGHT_MIDDLE("light_middle", 15),
		LIGHT_BOTTOM("light_bottom", 15),
		MIDDLE("middle", 0),
		BOTTOM_BIG("bottom_big", 0),
		BOTTOM_SMALL("bottom_small", 0);
		
		private final String name;
		private final int light;
		
		LumecornShape(String name, int light) {
			this.name = name;
			this.light = light;
		}

		@Override
		public String getString() {
			return name;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
		public int getLight() {
			return light;
		}
	}
	
	public static enum CactusBottom implements IStringSerializable {
		EMPTY("empty"),
		SAND("sand"),
		MOSS("moss");
		
		private final String name;
		
		CactusBottom(String name) {
			this.name = name;
		}

		@Override
		public String getString() {
			return name;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
}
