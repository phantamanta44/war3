package io.github.phantamanta44.war3.gui;

import io.github.phantamanta44.war3.config.Config;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class GuiWarConfig extends GuiConfig {
	
	public GuiWarConfig(GuiScreen parent) {
		super(parent, new ConfigElement(Config.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), "war3", false, false, "MC-War Client Configuration");
	}

}
