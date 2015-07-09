package io.github.phantamanta44.war3.render.model;

import io.github.phantamanta44.war3.model.AdvancedModelLoader;
import io.github.phantamanta44.war3.model.IModelCustom;
import io.github.phantamanta44.war3.render.model.attach.IAttachmentRenderer;
import io.github.phantamanta44.war3.render.model.attach.IScopeAttachment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class ObjModelItemRenderer implements IItemRenderer {

	protected static final double HALF_PI = Math.PI / 180;
	protected static final ResourceLocation glassTex = new ResourceLocation("war3", "textures/model/scope-dirt.png");
	protected static double xOffset, zOffset, scaleMult = 0;
	protected static boolean isSettled = true;
	protected IModelCustom model;
	protected Map<String, IAttachmentRenderer> attach = new HashMap<String, IAttachmentRenderer>();
	protected ResourceLocation texture;
	
	public ObjModelItemRenderer(ResourceLocation path, ResourceLocation texMap) {
		model = AdvancedModelLoader.loadModel(path);
		texture = texMap;
		model.renderAll();
	}
	
	public ObjModelItemRenderer addAttachment(IAttachmentRenderer renderer) {
		attach.put(renderer.getName(), renderer);
		return this;
	}
	
	public ObjModelItemRenderer removeAttachment(String name) {
		attach.remove(name);
		return this;
	}
	
	public Collection<IAttachmentRenderer> getAttachments() {
		return attach.values();
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch (type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		float scaleMultTar = 1;
		Minecraft mc = Minecraft.getMinecraft();
		
		double facing = (double)(mc.thePlayer.rotationYaw + 90) % 360;
		double pitch = (double)(mc.thePlayer.rotationPitch + 140) % 360 - 140;
		double fRad = -facing * HALF_PI;
		
		double xOffsetTar;
		double zOffsetTar;
		
		if (mc.thePlayer.getActivePotionEffect(Potion.moveSlowdown) != null) {
			xOffsetTar = 0;
			zOffsetTar = 0;
			scaleMultTar = 1.3F;
		}
		else {
			double theta = 90 - facing;
			xOffsetTar = 0.25 * Math.sin(-facing * HALF_PI);
			zOffsetTar = 0.25 * Math.sin(theta * HALF_PI);
		}
		
		inchStatics(xOffsetTar, zOffsetTar, scaleMultTar);
		
		double pMult = (pitch / 90) * 0.32;
		double xFix = pMult * Math.cos(fRad);
		double yFix = 1.346D;
		double zFix = pMult * Math.sin(fRad);
		
		if (mc.thePlayer.isSneaking())
			yFix -= 0.08D;
		
		GL11.glPushMatrix();
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glTranslated(-xFix, yFix, zFix);
		GL11.glTranslated(xOffset, 0.0, zOffset);
		GL11.glRotated(-facing + 90, 0.0, 1.0, 0.0);
		GL11.glRotated(pitch, 1.0, 0.0, 0.0);
		GL11.glScaled(0.04 * scaleMult, 0.04 * scaleMult, 0.04 * scaleMult);
		mc.renderEngine.bindTexture(texture);
		model.renderAll();
		
		renderAttachments();
		
		GL11.glPopMatrix();
	}

	protected static void inchStatics(double xOffsetTar, double zOffsetTar, double scaleMultTar) {
		boolean settled = true;
		
		if (xOffset != xOffsetTar) {
			xOffset += (xOffsetTar - xOffset) / 2.71;
			settled = false;
		}
		
		if (zOffset != zOffsetTar) {
			zOffset += (zOffsetTar - zOffset) / 2.71;
			settled = false;
		}
		
		if (scaleMult != scaleMultTar) {
			scaleMult += (scaleMultTar - scaleMult) / 2.71;
			settled = false;
		}
		
		isSettled = settled;
	}
	
	protected void renderAttachments() {
		for (IAttachmentRenderer renderer : attach.values())
			renderer.render();
	}
	
	public static boolean isSettled() {
		return isSettled;
	}
	
	public static boolean isFullyScopedIn() {
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("" + (Math.abs(scaleMult - 1.0)), 18, 48, 0xffffffff);
		return isSettled && (Math.abs(scaleMult - 1.3) < 0.1);
	}

	public boolean isScoped() {
		for (IAttachmentRenderer renderer : attach.values()) {
			if (renderer instanceof IScopeAttachment)
				return true;
		}
		return false;
	}
	
}