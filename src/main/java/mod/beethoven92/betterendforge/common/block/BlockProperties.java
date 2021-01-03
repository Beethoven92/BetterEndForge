package mod.beethoven92.betterendforge.common.block;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.IStringSerializable;

public class BlockProperties 
{
	public static final EnumProperty<TripleShape> TRIPLE_SHAPE = EnumProperty.create("shape", TripleShape.class);
	public static final EnumProperty<PedestalState> PEDESTAL_STATE = EnumProperty.create("state", PedestalState.class);
	public static final EnumProperty<HydraluxShape> HYDRALUX_SHAPE = EnumProperty.create("shape", HydraluxShape.class);
	public static final BooleanProperty HAS_ITEM = BooleanProperty.create("has_item");
	public static final BooleanProperty HAS_LIGHT = BooleanProperty.create("has_light");
	public static final BooleanProperty ACTIVATED = BooleanProperty.create("active");
	public static final IntegerProperty ROTATION = IntegerProperty.create("rotation", 0, 3);
	public static final EnumProperty<PentaShape> PENTA_SHAPE = EnumProperty.create("shape", PentaShape.class);


	
	public static enum TripleShape implements IStringSerializable 
	{
		TOP("top"),
		MIDDLE("middle"),
		BOTTOM("bottom");
		
		private final String name;
		
		TripleShape(String name) 
		{
			this.name = name;
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
}
