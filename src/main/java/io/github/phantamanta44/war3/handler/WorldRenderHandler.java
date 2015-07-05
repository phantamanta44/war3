package io.github.phantamanta44.war3.handler;

import io.github.phantamanta44.war3.render.FancyEffectLayer;
import io.github.phantamanta44.war3.render.fx.ParticleRenderDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderHackThing;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldRenderHandler {

	private Minecraft mc;
	
	public WorldRenderHandler(Minecraft minecraft) {
		mc = minecraft;
	}
	
	@SubscribeEvent
	public void onRenderEntityLiving(RenderLivingEvent.Pre event) {
		if (event.entity instanceof EntityPlayer && event.renderer instanceof RenderPlayer) {
			if (!RenderHackThing.getLayers(event.renderer).contains("io.github.phantamanta44.war3.render.FancyEffectLayer"))
				RenderHackThing.addLayer(event.renderer, new FancyEffectLayer((RenderPlayer)event.renderer));
		}
	}
	
	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		ParticleRenderDispatcher.dispatch();
	}
	
}
