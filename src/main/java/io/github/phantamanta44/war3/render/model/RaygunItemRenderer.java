package io.github.phantamanta44.war3.render.model;

import io.github.phantamanta44.war3.model.AdvancedModelLoader;
import io.github.phantamanta44.war3.model.IModelCustom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class RaygunItemRenderer extends ObjModelItemRenderer {

	protected String scope;
	
	public RaygunItemRenderer(ResourceLocation path, ResourceLocation texMap, String scopeName) {
		super (path, texMap);
		scope = scopeName;
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
		model.renderAllExcept(scope);
		
		mc.renderEngine.bindTexture(glassTex);
		model.renderOnly(scope);
		
		GL11.glPopMatrix();
	}
	
}