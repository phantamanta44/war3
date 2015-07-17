package io.github.phantamanta44.war3.config;

import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigEventListener {
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onConfigChanged(OnConfigChangedEvent event) {
		if (event.modID.equals("war3"))
			Config.syncConfig();
	}
	
}
