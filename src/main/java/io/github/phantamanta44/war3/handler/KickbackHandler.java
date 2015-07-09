package io.github.phantamanta44.war3.handler;

import io.github.phantamanta44.war3.War3;
import io.github.phantamanta44.war3.config.Config;
import io.github.phantamanta44.war3.gui.GuiWarOverlay;
import io.github.phantamanta44.war3.render.model.RevolverItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class KickbackHandler {

	private static double activeOffset = 0, passiveOffset = 0;
	private Minecraft mc;
	
	public KickbackHandler(Minecraft minecraft) {
		mc = minecraft;
	}
	
	@SubscribeEvent
	public void onGameTick(TickEvent.ServerTickEvent event) {
		if (activeOffset > 0) {
			mc.thePlayer.rotationPitch -= activeOffset / 2;
			passiveOffset += activeOffset / 2;
			activeOffset /= 2;
		}
		if (passiveOffset > 0) {
			mc.thePlayer.rotationPitch += passiveOffset * 0.04;
			passiveOffset *= 0.96;
		}
	}
	
	public static void kickBack(double amt) {
		GuiWarOverlay.shootTicks = Math.min(GuiWarOverlay.shootTicks + 5, 40);
		if (Config.doKickback)
			activeOffset = Math.min(activeOffset + passiveOffset + amt, 90 - (activeOffset + passiveOffset));
	}
	
	public static void tryKickback(String path, Minecraft mc) {
		Item i;
		try {
			i = mc.thePlayer.inventory.getCurrentItem().getItem();
		}
		catch (NullPointerException ex) {
			return;
		}
		if (mc.thePlayer.inventory.getCurrentItem().getDisplayName().toLowerCase().contains("reload")
				|| mc.thePlayer.inventory.getCurrentItem().getDisplayName().contains("0 | 0"))
			return;
		if (path.equals("mob.zombie.metal")) {
			if (i == Items.iron_shovel || i == Items.leather || i == Items.golden_shovel) {
				kickBack(18.5D);
				RevolverItemRenderer.revolve();
			}
			else if (i.getUnlocalizedName().contains("shovel"))
				kickBack(0.03D);
		}
		if (path.equals("mob.skeleton.hurt")) {
			if (i.getUnlocalizedName().contains("hoe") || i == Items.iron_ingot)
				kickBack(0.008D);
		}
		if (path.equals("note.snare")) {
			if (i.getUnlocalizedName().contains("hoe") || i == Items.gold_ingot)
				kickBack(1.2D);
		}
		if (path.equals("mob.ghast.fireball")) {
			if (i.getUnlocalizedName().contains("pickaxe") || i == Items.stick || i == Items.brick)
				kickBack(30.0D);
		}
		if ((path.equals("game.tnt.primed") || path.equals("creeper.primed"))) {
			if (i == Items.golden_hoe)
				kickBack(24.0D);
		}
		if (path.equals("mob.irongolem.hit")) {
			if (i.getUnlocalizedName().contains("hatchet") || i == Items.bowl || i == Items.paper)
				kickBack(24.9D);
			else if (i == Items.bone) {
				kickBack(14.0D);
				War3.killTheZombiesPls(mc);
			}
		}
	}
	
}
