package io.github.phantamanta44.war3.handler;

import io.github.phantamanta44.war3.SoundType;
import io.github.phantamanta44.war3.War3;
import io.github.phantamanta44.war3.War3.Team;
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
			if (msg.contains("BLUE"))
				processCTFMsg(CtfMsg.PICKUP, Team.BLUE, msg);
			else if (msg.contains("RED"))
				processCTFMsg(CtfMsg.PICKUP, Team.RED, msg);
		}
		else if (msg.contains("§6 dropped the")) {
			if (msg.contains("BLUE"))
				processCTFMsg(CtfMsg.DROP, Team.BLUE, msg);
			else if (msg.contains("RED"))
				processCTFMsg(CtfMsg.DROP, Team.RED, msg);
		}
		else if (msg.contains("§b captured the")) {
			if (msg.contains("BLUE"))
				processCTFMsg(CtfMsg.CAPTURE, Team.BLUE, msg);
			else if (msg.contains("RED"))
				processCTFMsg(CtfMsg.CAPTURE, Team.RED, msg);
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
	
	@SuppressWarnings("incomplete-switch")
	private static void processCTFMsg(CtfMsg status, Team team, String msg)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if (War3.getTeam(mc) == Team.RED) {
			if (status == CtfMsg.DROP)
				mc.theWorld.playSoundAtEntity(mc.thePlayer, SoundType.FLAGDROPRED.getLoc(), 1.0F, 1.0F);
			if (War3.getTeam(mc) == team) {
				switch (status) {
				case PICKUP:
					break;
				case CAPTURE:
					mc.theWorld.playSoundAtEntity(mc.thePlayer, SoundType.ENEMYFLAGCAPRED.getLoc(), 1.0F, 1.0F);
					break;
				}
			}
			else if (War3.getTeam(mc) != team) {
				switch (status) {
				case PICKUP:
					break;
				case CAPTURE:
					mc.theWorld.playSoundAtEntity(mc.thePlayer, SoundType.FLAGCAPRED.getLoc(), 1.0F, 1.0F);
					if (msg.contains(mc.getSession().getUsername())) {
						mc.theWorld.playSoundAtEntity(mc.thePlayer, SoundType.UINOTIFICATION.getLoc(), 1.0F, 1.0F);
						//mc.ingameGUI.kills.registerRaw("Flag Captured", 50);
					}
					break;
				}
			}
		}
			
		if (War3.getTeam(mc) == Team.BLUE) {
			if (status == CtfMsg.DROP) {
				mc.theWorld.playSoundAtEntity(mc.thePlayer, SoundType.FLAGDROPBLUE.getLoc(), 1.0F, 1.0F);
			}
			if (War3.getTeam(mc) == team) {
				switch (status) {
				case PICKUP:
					mc.theWorld.playSoundAtEntity(mc.thePlayer, SoundType.ENEMYFLAGCAPBLUE.getLoc(), 1.0F, 1.0F);
					break;
				case CAPTURE:
					break;
				}
			}
			else if (War3.getTeam(mc) != team) {
				switch (status) {
				case PICKUP:
					break;
				case CAPTURE:
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
	
	public static enum CtfMsg {
		
		PICKUP, DROP, CAPTURE;
		
	}
	
}
