package io.github.phantamanta44.war3.handler;

import io.github.phantamanta44.war3.SoundType;
import io.github.phantamanta44.war3.War3;
import io.github.phantamanta44.war3.render.model.BoltActionItemRenderer;
import io.github.phantamanta44.war3.render.model.PumpedItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SoundInterceptor {
	
	@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onSoundPlay(PlaySoundEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		final String path = event.sound.getSoundLocation().getResourcePath();
		if (path.equals("ambient.cave.cave")) {
			String team = War3.cachedTeam;
			if (team.equals("red")) {
				mc.theWorld.playSoundAtEntity(mc.thePlayer, SoundType.RESPAWNRED.getLoc(), 1.0F, 1.0F);
			}
			else if (team.equals("blue")) {
				mc.theWorld.playSoundAtEntity(mc.thePlayer, SoundType.RESPAWNBLUE.getLoc(), 1.0F, 1.0F);
			}
			else
				mc.theWorld.playSoundAtEntity(mc.thePlayer, SoundType.RESPAWNRED.getLoc(), 1.0F, 1.0F);
		}
		if (event.sound.getPitch() == 2.0F) {
			if (isNearPlayer(event.sound, mc, 0.3F))
				KickbackHandler.tryKickback(path, mc);
			if (path.contains("tile.piston")) {
				if (path.equals("tile.piston.out"))
					PumpedItemRenderer.setPumping(true);
				else
					PumpedItemRenderer.setPumping(false);
			}
		}
		if (path.contains("random.door")) {
			if (path.equals("random.door_open"))
				BoltActionItemRenderer.setWorking(true);
			else
				BoltActionItemRenderer.setWorking(false);
		}
	}
	
	public static boolean isNearPlayer(ISound snd, Minecraft mc, float acc) {
		return Math.abs(snd.getXPosF() - mc.thePlayer.posX) <= acc
				&& Math.abs(snd.getYPosF() - mc.thePlayer.posY) <= acc
				&& Math.abs(snd.getZPosF() - mc.thePlayer.posZ) <= acc;
	}
	
}
