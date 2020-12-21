package mod.beethoven92.betterendforge.server;

import mod.beethoven92.betterendforge.IPhysicalSide;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

public class PhysicalServerSide implements IPhysicalSide
{
	@Override
	public void setup(IEventBus modEventBus, IEventBus forgeEventBus) 
	{
		modEventBus.addListener(this::serverSetup);
	}

	private void serverSetup(FMLDedicatedServerSetupEvent event)
	{

	}
}
