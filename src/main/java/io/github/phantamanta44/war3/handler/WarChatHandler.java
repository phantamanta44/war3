package io.github.phantamanta44.war3.handler;

import io.github.phantamanta44.war3.SoundType;
import io.github.phantamanta44.war3.War3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WarChatHandler {
	
	private Minecraft mc;
	
	public WarChatHandler(Minecraft minecraft) {
		mc = minecraft;
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void eventHandler(ClientChatReceivedEvent event) {
		String msg = event.message.getFormattedText();
		
		/* Experimental team color code
		for (Object obj : War3.getClientsidePlayerList()) {
			NetworkPlayerInfo playerInfo = (NetworkPlayerInfo)obj;
			if (msg.contains(playerInfo.getGameProfile().getName())) {
				event.message = new ChatComponentText(msg.replaceAll("§.\\s+" + playerInfo.getGameProfile().getName(), playerInfo.getPlayerTeam().getColorPrefix() + playerInfo.getGameProfile().getName() + EnumChatFormatting.RESET));
				break;
			}
		}
		*/
		
		// CTF Parsing
		if (msg.contains("§6 picked up the")) {
			if (msg.contains("BLUE")) {
				processCTFMsg(0, "blue", msg);
			}
			else if (msg.contains("RED")) {
				processCTFMsg(0, "red", msg);
			}
		}
		else if (msg.contains("§6 dropped the")) {
			if (msg.contains("BLUE")) {
				processCTFMsg(1, "blue", msg);
			}
			else if (msg.contains("RED")) {
				processCTFMsg(1, "red", msg);
			}
		}
		else if (msg.contains("§b captured the")) {
			if (msg.contains("BLUE")) {
				processCTFMsg(2, "blue", msg);
			}
			else if (msg.contains("RED")) {
				processCTFMsg(2, "red", msg);
			}
		}
		
		/*
		
		// Kill-get parsing
		if (msg.contains("§7 killed")) {
			mc.theWorld.playSoundAtEntity(mc.thePlayer, SoundType.KILLNOTIFICATION.getLoc(), 1.0F, 1.0F);
			mc.ingameGUI.registerOnKillfeed(msg.replaceAll("§.", ""), true);
		}
		
		// Death parsing
		if (msg.contains("§cKilled by")) {
			mc.ingameGUI.registerOnKillfeed(msg.replaceAll("§.", ""), false);
		}
		
		// Map parsing
		if (msg.contains("§4CURRENT MAP:"))
        {
            String cmap;
            String cgm;
            cmap = msg.replaceAll("CURRENT MAP:\\s", "").replaceAll("_.+", "").replaceAll("\\sGAMEMODE:\\s.+", "").trim();
            cgm = msg.replaceAll(".+GAMEMODE:\\s", "").trim();
            cmap = cmap.substring(0, 1).toUpperCase() + cmap.substring(1);
            cgm = cgm.substring(0, 1).toUpperCase() + cgm.substring(1);
            mc.ingameGUI.setCurrentMapAndGamemode(cmap, cgm);
        }
        if (msg.contains("§4NEXT MAP:"))
        {
            String cmap;
            String cgm;
            cmap = msg.replaceAll("NEXT MAP:\\s", "").replaceAll("_.+", "").replaceAll("\\sGAMEMODE:\\s.+", "").trim();
            cgm = msg.replaceAll(".+GAMEMODE:\\s", "").trim();
            cmap = cmap.substring(0, 1).toUpperCase() + cmap.substring(1);
            cgm = cgm.substring(0, 1).toUpperCase() + cgm.substring(1);
            mc.ingameGUI.setCurrentMapAndGamemode(cmap, cgm);
        }
		
		*/
		
	}
	
	private static void processCTFMsg(int status, String color, String msg)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if (War3.getTeam(mc) == "red") {
			if (status == 1) {
				mc.theWorld.playSoundAtEntity(mc.thePlayer, SoundType.FLAGDROPRED.getLoc(), 1.0F, 1.0F);
			}
			if (War3.getTeam(mc) == color) {
				switch (status) {
				case 0:
					break;
				case 2:
					mc.theWorld.playSoundAtEntity(mc.thePlayer, SoundType.ENEMYFLAGCAPRED.getLoc(), 1.0F, 1.0F);
					break;
				}
			}
			else if (War3.getTeam(mc) != color) {
				switch (status) {
				case 0:
					break;
				case 2:
					mc.theWorld.playSoundAtEntity(mc.thePlayer, SoundType.FLAGCAPRED.getLoc(), 1.0F, 1.0F);
					if (msg.contains(mc.getSession().getUsername())) {
						mc.theWorld.playSoundAtEntity(mc.thePlayer, SoundType.UINOTIFICATION.getLoc(), 1.0F, 1.0F);
						//mc.ingameGUI.kills.registerRaw("Flag Captured", 50);
					}
					break;
				}
			}
		}
			
		if (War3.getTeam(mc) == "blue") {
			if (status == 1) {
				mc.theWorld.playSoundAtEntity(mc.thePlayer, SoundType.FLAGDROPBLUE.getLoc(), 1.0F, 1.0F);
			}
			if (War3.getTeam(mc) == color) {
				switch (status) {
				case 0:
					mc.theWorld.playSoundAtEntity(mc.thePlayer, SoundType.ENEMYFLAGCAPBLUE.getLoc(), 1.0F, 1.0F);
					break;
				case 2:
					break;
				}
			}
			else if (War3.getTeam(mc) != color) {
				switch (status) {
				case 0:
					break;
				case 2:
					mc.theWorld.playSoundAtEntity(mc.thePlayer, SoundType.FLAGCAPBLUE.getLoc(), 1.0F, 1.0F);
					if (msg.contains(mc.getSession().getUsername())) {
						mc.theWorld.playSoundAtEntity(mc.thePlayer, SoundType.UINOTIFICATION.getLoc(), 1.0F, 1.0F);
						//mc.ingameGUI.kills.registerRaw("Flag Captured", 50);
					}
					break;
				}
			}
		}
	}
	
}
