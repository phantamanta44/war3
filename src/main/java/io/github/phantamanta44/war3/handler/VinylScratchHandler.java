package io.github.phantamanta44.war3.handler;

import io.github.phantamanta44.war3.config.Config;
import io.github.phantamanta44.war3.gui.ScopeHandler;
import io.github.phantamanta44.war3.render.fx.FXSparkle;
import io.github.phantamanta44.war3.render.fx.FXWisp;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class VinylScratchHandler {

	private Minecraft mc;
	
	public VinylScratchHandler(Minecraft minecraft) {
		mc = minecraft;
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (!Config.beamMode)
			return;
		if (mc.gameSettings.keyBindUseItem.isKeyDown() && mc.thePlayer.inventory.getCurrentItem() != null) {
			ItemStack is = mc.thePlayer.inventory.getCurrentItem();
			Item i = is.getItem();
			if (!ScopeHandler.isScopable(Item.getIdFromItem(i)))
				return;
			if (is.getDisplayName().toLowerCase().contains("reload") || is.getDisplayName().toLowerCase().contains("0 | 0"))
				return;
			renderBeam();
		}
	}
	
	private void renderBeam() {
		Vec3 v = mc.thePlayer.getLookVec();
		Vec3 pos = mc.thePlayer.getPositionVector().add(new Vec3(0, 1.6, 0)).add(v);
		for (int i = 0; i < 32 && mc.theWorld.isAirBlock(new BlockPos(pos)); i++) {
			FXWisp wisp = new FXWisp(mc.theWorld, pos.xCoord, pos.yCoord, pos.zCoord, Config.beamSize, 0.08F, 0.3F, 0.8F, true, false, 0.3F);
			mc.effectRenderer.addEffect(wisp);
			pos = pos.add(v);
		}
	}
	
}
