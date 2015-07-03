package io.github.phantamanta44.war3.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {
	
    public static Configuration config;

    public static boolean showHotbar;
    public static boolean useModels;
    public static boolean doKickback;
    public static boolean beamMode;
    public static float beamSize;

    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig() {
    	
        showHotbar = config.getBoolean("renderHotbar", Configuration.CATEGORY_GENERAL, false, "Show hotbar?");
        useModels = config.getBoolean("renderModels", Configuration.CATEGORY_GENERAL, true, "Render models?");
        doKickback = config.getBoolean("doKickback", Configuration.CATEGORY_GENERAL, false, "Camera recoil?");
        beamMode = config.getBoolean("beamMode", Configuration.CATEGORY_GENERAL, false, "Bass cannon?");
        beamSize = config.getFloat("beamSize", Configuration.CATEGORY_GENERAL, 1.2F, 0.4F, 3.0F, "Bass cannon size");
        
        if (config.hasChanged())
            config.save();
        
    }
    
}
