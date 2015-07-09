package io.github.phantamanta44.war3.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {
	
	public static final String CAT_HUD = "hud";
	public static final String CAT_VISUAL = "visual";
	public static final String CAT_GAME = "gameplay";
	public static final String CAT_EXTRA = "extra";
    public static Configuration config;
    
    public static boolean showHotbar;
    public static boolean altReticle;
    public static boolean varReticle;
    public static String[] mentionRegex;
    public static boolean useModels;
    public static boolean doKickback;
    public static boolean beamMode;
    public static float beamSize;

    public static void init(File file) {
        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig() {
    	
        showHotbar = config.getBoolean("renderHotbar", CAT_HUD, false, "Show hotbar?");
        altReticle = config.getBoolean("altReticle", CAT_HUD, true, "Alternate reticles?");
        varReticle = config.getBoolean("varReticle", CAT_HUD, true, "Variable-size reticles?");
        mentionRegex = config.getStringList("mentionFilters", CAT_HUD, new String[] { "^(?!.*%NAME.*(»|killed)).*%NAME.*$" }, "Mention regex filters (case-insensitive)");
        useModels = config.getBoolean("renderModels", CAT_VISUAL, true, "Render models?");
        doKickback = config.getBoolean("doKickback", CAT_GAME, false, "Camera recoil?");
        beamMode = config.getBoolean("beamMode", CAT_EXTRA, false, "Bass cannon?");
        beamSize = config.getFloat("beamSize", CAT_EXTRA, 1.2F, 1.0F, 3.0F, "Bass cannon size");
        
        if (config.hasChanged())
            config.save();
        
    }
    
}
