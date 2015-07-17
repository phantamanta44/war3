package io.github.phantamanta44.war3.render.model.attach;

import io.github.phantamanta44.war3.handler.WorldRenderHandler;
import io.github.phantamanta44.war3.model.AdvancedModelLoader;
import io.github.phantamanta44.war3.model.IModelCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class BallisticScope implements IScopeAttachment {

	private static final ResourceLocation scopeTex = new ResourceLocation("war3", "textures/model/attach/ballistic.png");
	private static final ResourceLocation orthoTex = new ResourceLocation("war3", "textures/gui/attach/ballistic.png");
	private static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("war3", "model/obj/attach/ballistic.obj"));
	private double x, y, z;
	private boolean sensitivityMod = false;
	
	public BallisticScope(double xOff, double yOff, double zOff) {
		x = xOff;
		y = yOff;
		z = zOff;
	}
	
	@Override
	public void render() {
		GL11.glPushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(scopeTex);
		GL11.glTranslated(-x, y, -z);
		model.renderAll();
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderOrthoScope(Minecraft mc, Tessellator tess, int xBounds, int yBounds) {
		if (!sensitivityMod) {
			mc.gameSettings.mouseSensitivity /= 2.5;
			sensitivityMod = true;
		}
		WorldRenderHandler.setFovMultiplier(0.08F);
		GL11.glDisable(GL11.GL_BLEND);
		mc.getTextureManager().bindTexture(orthoTex);
		WorldRenderer wr = tess.getWorldRenderer();
		wr.startDrawingQuads();
		wr.addVertexWithUV(0.0D, (double)yBounds, -90.0D, 0.0D, 1.0D);
		wr.addVertexWithUV((double)xBounds, (double)yBounds, -90.0D, 1.0D, 1.0D);
		wr.addVertexWithUV((double)xBounds, 0.0D, -90.0D, 1.0D, 0.0D);
		wr.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		tess.draw();
		GL11.glEnable(GL11.GL_BLEND);
	}
	
	@Override
	public void onScopeEnd(Minecraft mc) {
		mc.gameSettings.mouseSensitivity *= 2.5;
		sensitivityMod = false;
	}
	
	@Override
	public String getName() {
		return "Ballistic Scope";
	}
	
}
