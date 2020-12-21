package mod.beethoven92.betterendforge.common.block;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.IStringSerializable;

public class BlockProperties 
{
	public static final EnumProperty<TripleShape> TRIPLE_SHAPE = EnumProperty.create("shape", TripleShape.class);
	public static final EnumProperty<PedestalState> PEDESTAL_STATE = EnumProperty.create("state", PedestalState.class);
	public static final BooleanProperty HAS_ITEM = BooleanProperty.create("has_item");
	public static final BooleanProperty HAS_LIGHT = BooleanProperty.create("has_light");
	public static final BooleanProperty ACTIVATED = BooleanProperty.create("active");
	
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
}
