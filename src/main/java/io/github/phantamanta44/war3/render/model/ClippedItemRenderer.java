package io.github.phantamanta44.war3.render.model;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class ClippedItemRenderer extends ObjModelItemRenderer {

	protected static double clip = 0, clipTar = 0;
	protected String clipName;
	
	public ClippedItemRenderer(ResourceLocation path, ResourceLocation texMap, String clipObj) {
		super(path, texMap);
		clipName = clipObj;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		float scaleMultTar = 1;
		Minecraft mc = Minecraft.getMinecraft();
		
		clipTar = item.getDisplayName().toLowerCase().contains("reload") ? -32 : 0;
		
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
		GL11.glRotated(clip, 1.0, 0.0, -1.0);
		GL11.glScaled(0.04 * scaleMult, 0.04 * scaleMult, 0.04 * scaleMult);
		mc.renderEngine.bindTexture(texture);
		model.renderAllExcept(clipName);
		
		renderAttachments();
		
		GL11.glRotated(-clip, 1.0, 0.0, -1.0);
		GL11.glTranslated(0.0, clip, 0.0);
		if (clipTar == 0 || clip != clipTar)
			model.renderOnly(clipName);
		
		GL11.glPopMatrix();
	}

	protected static void inchStatics(double xOffsetTar, double zOffsetTar, double scaleMultTar) {
		if (xOffset != xOffsetTar)
			xOffset += (xOffsetTar - xOffset) / 2.71;
		
		if (zOffset != zOffsetTar)
			zOffset += (zOffsetTar - zOffset) / 2.71;
		
		if (scaleMult != scaleMultTar)
			scaleMult += (scaleMultTar - scaleMult) / 2.71;
		
		if (clip != clipTar)
			clip += (clipTar - clip) / 8.4;
	}
	
}