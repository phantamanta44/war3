package io.github.phantamanta44.war3.gui;

import io.github.phantamanta44.war3.config.Config;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class GuiWarConfig extends GuiConfig {
	
	public GuiWarConfig(GuiScreen parent) {
		super(parent, getConfigElements(), "war3", false, false, "MC-War Client Configuration");
	}
  	
	private static List<IConfigElement> getConfigElements() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();
	  
		list.add(categoryElement(Config.CAT_HUD, "HUD", "war3.config.hud"));
		list.add(categoryElement(Config.CAT_VISUAL, "Visual", "war3.config.visual"));
		list.add(categoryElement(Config.CAT_GAME, "Gameplay", "war3.config.game"));
		list.add(categoryElement(Config.CAT_EXTRA, "Extra", "war3.config.extra"));
	  
		return list;
	}
  
	private static IConfigElement categoryElement(String category, String name, String tooltip_key) {
		return new DummyConfigElement.DummyCategoryElement(name, tooltip_key,
				new ConfigElement(Config.config.getCategory(category)).getChildElements());
	}

}
