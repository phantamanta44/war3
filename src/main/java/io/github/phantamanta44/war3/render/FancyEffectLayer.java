package io.github.phantamanta44.war3.render;

import io.github.phantamanta44.war3.War3;
import io.github.phantamanta44.war3.handler.VazkiiTickHandler;
import io.github.phantamanta44.war3.render.fx.FXSparkle;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

public class FancyEffectLayer implements LayerRenderer {
	
	private static final ResourceLocation horn = new ResourceLocation("war3", "textures/misc/horn.png");
	private final RenderPlayer rend;
	
	public FancyEffectLayer(RenderPlayer renderPlayer) {
		rend = renderPlayer;
	}

	public void doRenderLayer(EntityLivingBase ent, float f, float f1, float renderTick, float f2, float f3, float f4, float f5) {
		if (!(ent instanceof EntityPlayer))
			return;
		EntityPlayer player = (EntityPlayer)ent;
		if (player.getUniqueID().toString().equals("5b435dcb-1d26-4324-85a9-b7c8be22b6ea"))
			renderHorn(player, renderTick);
		if (War3.awesomePeople.contains(player.getUniqueID().toString())) {
			Vec3 pos = player.getPositionVector();
			Random rand = player.getRNG();
			int x = rand.nextInt(8);
			if (x == 0) {
				double a = (VazkiiTickHandler.ticksInGame % 180) * 2 * (Math.PI / 180);
				FXSparkle spark = new FXSparkle(player.worldObj, pos.xCoord + rand.nextFloat() - 0.5, pos.yCoord + rand.nextFloat() * 2, pos.zCoord + rand.nextFloat() - 0.5,
						rand.nextFloat() + 1, (float)Math.sin(a), (float)Math.sin(a + (0.6 * Math.PI)), (float)Math.sin(a + (1.2 * Math.PI)), 5);
				Minecraft.getMinecraft().effectRenderer.addEffect(spark);
			}
		}
	}

	@Override
	public boolean shouldCombineTextures() {
		return false;
	}
	
	public void renderHorn(EntityPlayer player, float tickPart) {
		float yaw = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * tickPart;
		float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * tickPart;
		float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * tickPart;
		GL11.glPushMatrix();
		GL11.glRotatef(yawOffset, 0, -1, 0);
		GL11.glRotatef(yaw - 270, 0, 1, 0);
		GL11.glRotatef(pitch, 0, 0, 1);
		GL11.glRotatef(90F, 0F, 1F, 0F);
		GL11.glRotatef(180F, 1F, 0F, 0F);
		GL11.glTranslatef(-0.4F, 0.1F, -0.25F);
		GL11.glScalef(0.75F, 0.75F, 0.75F);
		GL11.glRotatef(90F, 0F, 1F, 0F);
		GL11.glTranslatef(-0.3F, 0.1F, 0.55F);
		rend.bindTexture(horn);
		renderItemIn2D(Tessellator.getInstance(), 1, 0, 0, 1, 16, 16, 1F / 16F);
		GL11.glPopMatrix();
	}
	
	private static void renderItemIn2D(Tessellator par0Tessellator, float par1, float par2, float par3, float par4, int par5, int par6, float par7) {
		par0Tessellator.getWorldRenderer().startDrawingQuads();
		par0Tessellator.getWorldRenderer().setNormal(0.0F, 0.0F, 1.0F);
		par0Tessellator.getWorldRenderer().addVertexWithUV(0.0D, 0.0D, 0.0D, (double)par1, (double)par4);
		par0Tessellator.getWorldRenderer().addVertexWithUV(1.0D, 0.0D, 0.0D, (double)par3, (double)par4);
		par0Tessellator.getWorldRenderer().addVertexWithUV(1.0D, 1.0D, 0.0D, (double)par3, (double)par2);
		par0Tessellator.getWorldRenderer().addVertexWithUV(0.0D, 1.0D, 0.0D, (double)par1, (double)par2);
		par0Tessellator.draw();
		par0Tessellator.getWorldRenderer().startDrawingQuads();
		par0Tessellator.getWorldRenderer().setNormal(0.0F, 0.0F, -1.0F);
		par0Tessellator.getWorldRenderer().addVertexWithUV(0.0D, 1.0D, (double)(0.0F - par7), (double)par1, (double)par2);
		par0Tessellator.getWorldRenderer().addVertexWithUV(1.0D, 1.0D, (double)(0.0F - par7), (double)par3, (double)par2);
		par0Tessellator.getWorldRenderer().addVertexWithUV(1.0D, 0.0D, (double)(0.0F - par7), (double)par3, (double)par4);
		par0Tessellator.getWorldRenderer().addVertexWithUV(0.0D, 0.0D, (double)(0.0F - par7), (double)par1, (double)par4);
		par0Tessellator.draw();
		float var8 = 0.5F * (par1 - par3) / (float)par5;
		float var9 = 0.5F * (par4 - par2) / (float)par6;
		par0Tessellator.getWorldRenderer().startDrawingQuads();
		par0Tessellator.getWorldRenderer().setNormal(-1.0F, 0.0F, 0.0F);
		int var10;
		float var11;
		float var12;

		for (var10 = 0; var10 < par5; ++var10)
		{
			var11 = (float)var10 / (float)par5;
			var12 = par1 + (par3 - par1) * var11 - var8;
			par0Tessellator.getWorldRenderer().addVertexWithUV((double)var11, 0.0D, (double)(0.0F - par7), (double)var12, (double)par4);
			par0Tessellator.getWorldRenderer().addVertexWithUV((double)var11, 0.0D, 0.0D, (double)var12, (double)par4);
			par0Tessellator.getWorldRenderer().addVertexWithUV((double)var11, 1.0D, 0.0D, (double)var12, (double)par2);
			par0Tessellator.getWorldRenderer().addVertexWithUV((double)var11, 1.0D, (double)(0.0F - par7), (double)var12, (double)par2);
		}

		par0Tessellator.draw();
		par0Tessellator.getWorldRenderer().startDrawingQuads();
		par0Tessellator.getWorldRenderer().setNormal(1.0F, 0.0F, 0.0F);
		float var13;

		for (var10 = 0; var10 < par5; ++var10)
		{
			var11 = (float)var10 / (float)par5;
			var12 = par1 + (par3 - par1) * var11 - var8;
			var13 = var11 + 1.0F / (float)par5;
			par0Tessellator.getWorldRenderer().addVertexWithUV((double)var13, 1.0D, (double)(0.0F - par7), (double)var12, (double)par2);
			par0Tessellator.getWorldRenderer().addVertexWithUV((double)var13, 1.0D, 0.0D, (double)var12, (double)par2);
			par0Tessellator.getWorldRenderer().addVertexWithUV((double)var13, 0.0D, 0.0D, (double)var12, (double)par4);
			par0Tessellator.getWorldRenderer().addVertexWithUV((double)var13, 0.0D, (double)(0.0F - par7), (double)var12, (double)par4);
		}

		par0Tessellator.draw();
		par0Tessellator.getWorldRenderer().startDrawingQuads();
		par0Tessellator.getWorldRenderer().setNormal(0.0F, 1.0F, 0.0F);

		for (var10 = 0; var10 < par6; ++var10)
		{
			var11 = (float)var10 / (float)par6;
			var12 = par4 + (par2 - par4) * var11 - var9;
			var13 = var11 + 1.0F / (float)par6;
			par0Tessellator.getWorldRenderer().addVertexWithUV(0.0D, (double)var13, 0.0D, (double)par1, (double)var12);
			par0Tessellator.getWorldRenderer().addVertexWithUV(1.0D, (double)var13, 0.0D, (double)par3, (double)var12);
			par0Tessellator.getWorldRenderer().addVertexWithUV(1.0D, (double)var13, (double)(0.0F - par7), (double)par3, (double)var12);
			par0Tessellator.getWorldRenderer().addVertexWithUV(0.0D, (double)var13, (double)(0.0F - par7), (double)par1, (double)var12);
		}

		par0Tessellator.draw();
		par0Tessellator.getWorldRenderer().startDrawingQuads();
		par0Tessellator.getWorldRenderer().setNormal(0.0F, -1.0F, 0.0F);

		for (var10 = 0; var10 < par6; ++var10)
		{
			var11 = (float)var10 / (float)par6;
			var12 = par4 + (par2 - par4) * var11 - var9;
			par0Tessellator.getWorldRenderer().addVertexWithUV(1.0D, (double)var11, 0.0D, (double)par3, (double)var12);
			par0Tessellator.getWorldRenderer().addVertexWithUV(0.0D, (double)var11, 0.0D, (double)par1, (double)var12);
			par0Tessellator.getWorldRenderer().addVertexWithUV(0.0D, (double)var11, (double)(0.0F - par7), (double)par1, (double)var12);
			par0Tessellator.getWorldRenderer().addVertexWithUV(1.0D, (double)var11, (double)(0.0F - par7), (double)par3, (double)var12);
		}

		par0Tessellator.draw();
	}
	
}
