package io.github.phantamanta44.war3.render.model.attach;

import io.github.phantamanta44.war3.config.Config;
import io.github.phantamanta44.war3.handler.VazkiiTickHandler;
import io.github.phantamanta44.war3.handler.WorldRenderHandler;
import io.github.phantamanta44.war3.model.AdvancedModelLoader;
import io.github.phantamanta44.war3.model.IModelCustom;
import io.github.phantamanta44.war3.render.fx.FXSparkle;
import io.github.phantamanta44.war3.render.fx.FXWisp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import org.lwjgl.opengl.GL11;

public class LaserSight implements IAttachmentRenderer {

	private static final ResourceLocation scopeTex = new ResourceLocation("war3", "textures/model/attach/flashlight.png");
	private static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("war3", "model/obj/attach/laser.obj"));
	private double x, y, z;
	
	public LaserSight(double xOff, double yOff, double zOff) {
		x = xOff;
		y = yOff;
		z = zOff;
	}
	
	@Override
	public void render() {
		Minecraft mc = Minecraft.getMinecraft();
		GL11.glPushMatrix();
		mc.renderEngine.bindTexture(scopeTex);
		GL11.glTranslated(-x, y, -z);
		model.renderAll();
		GL11.glPopMatrix();
		Vec3 v = mc.thePlayer.getLookVec();
		Vec3 pos = mc.thePlayer.getPositionVector().add(new Vec3(0, 1.6, 0)).add(v);
		for (int i = 0; i < 32 && mc.theWorld.isAirBlock(new BlockPos(pos)); i++)
			pos = pos.add(v);
		pos = pos.subtract(v);
		FXWisp wisp = new FXWisp(mc.theWorld, pos.xCoord, pos.yCoord, pos.zCoord, 0.08F, 0.6F, 0F, 0F, false, false, 0.08F);
		mc.effectRenderer.addEffect(wisp);
	}
	
	@Override
	public String getName() {
		return "Laser Sights";
	}
	
}
