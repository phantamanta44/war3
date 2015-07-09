package io.github.phantamanta44.war3.handler;

import io.github.phantamanta44.war3.SoundType;
import io.github.phantamanta44.war3.War3;
import io.github.phantamanta44.war3.War3.Team;
import io.github.phantamanta44.war3.render.model.BoltActionItemRenderer;
import io.github.phantamanta44.war3.render.model.PumpedItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

public class SoundInterceptor {
	
	@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onSoundPlay(PlaySoundEvent event) {
		Minecraft mc = Minecraft.getMinecraft();
		final String path = event.sound.getSoundLocation().getResourcePath();
		if (path.equals("ambient.cave.cave")) {
			event.result = null;
			Team team = War3.cachedTeam;
			if (team == Team.RED)
				SoundType.playSound(mc, SoundType.RESPAWNRED);
			else if (team == Team.BLUE)
				SoundType.playSound(mc, SoundType.RESPAWNBLUE);
			else
				SoundType.playSound(mc, SoundType.RESPAWNRED);
		}
		if (event.sound.getPitch() == 2.0F) {
			if (isNearPlayer(event.sound, mc, 0.5F))
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
