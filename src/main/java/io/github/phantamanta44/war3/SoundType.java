package io.github.phantamanta44.war3;

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
	KILLNOTIFICATION("bf.hud.kill");
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
}
