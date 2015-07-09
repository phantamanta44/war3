package io.github.phantamanta44.war3;

import net.minecraft.client.Minecraft;

public enum SoundType {
	DUMMY("bf.dummy"),
	ENEMYFLAGCAPRED("bf.ctfr.capenemy"),
	FLAGCAPRED("bf.ctfr.cap"),
	FLAGDROPRED("bf.ctfr.drop"),
	ENEMYFLAGCAPBLUE("bf.ctfu.capenemy"),
	FLAGCAPBLUE("bf.ctfu.cap"),
	FLAGDROPBLUE("bf.ctfu.drop"),
	RESPAWNRED("bf.resp.ru"),
	RESPAWNBLUE("bf.resp.us"),
	UINOTIFICATION("bf.hud.notify"),
	KILLNOTIFICATION("bf.hud.kill"),
	CHATTAG("bf.hud.tag");
	//sound enum
	
	private String resLoc;
	
	private SoundType(String loc)
	{
		resLoc = loc;
	}
	
	public String getLoc()
	{
		return "war3:" + resLoc;
	}
	
	public String toString() {
		return getLoc();
	}
	
	public static void playSound(Minecraft mc, SoundType sound) {
		mc.theWorld.playSound(mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ, sound.getLoc(), 1.0F, 1.0F, false);
	}
}
