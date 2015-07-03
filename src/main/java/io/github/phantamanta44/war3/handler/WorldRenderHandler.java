package io.github.phantamanta44.war3.handler;

import io.github.phantamanta44.war3.War3;
import io.github.phantamanta44.war3.render.ShaderHelper;
import io.github.phantamanta44.war3.render.fx.FXSparkle;
import io.github.phantamanta44.war3.render.fx.ParticleRenderDispatcher;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.opengl.GL11;

public class WorldRenderHandler {

	private Minecraft mc;
	
	public WorldRenderHandler(Minecraft minecraft) {
		mc = minecraft;
	}
	
	@SubscribeEvent
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		ParticleRenderDispatcher.dispatch();
	}
	
}
