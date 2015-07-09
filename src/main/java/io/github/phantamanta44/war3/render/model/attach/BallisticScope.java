package io.github.phantamanta44.war3.render.model.attach;

import org.lwjgl.opengl.GL11;

import io.github.phantamanta44.war3.model.AdvancedModelLoader;
import io.github.phantamanta44.war3.model.IModelCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class BallisticScope implements IAttachmentRenderer {

	private static final ResourceLocation scopeTex = new ResourceLocation("war3", "textures/model/attach/ballistic.png");
	private static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("war3", "model/obj/attach/ballistic.obj"));
	private double x, y, z;
	
	public BallisticScope(double xOff, double yOff, double zOff) {
		x = xOff;
		y = yOff;
		z = zOff;
	}
	
	public void render() {
		GL11.glPushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(scopeTex);
		GL11.glTranslated(-x, y, -z);
		model.renderAll();
		GL11.glPopMatrix();
	}
	
	public String getName() {
		return "Ballistic Scope";
	}
	
}
