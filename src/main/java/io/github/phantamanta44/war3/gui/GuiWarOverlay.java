package io.github.phantamanta44.war3.gui;

import io.github.phantamanta44.war3.War3;
import io.github.phantamanta44.war3.War3.Team;
import io.github.phantamanta44.war3.config.Config;
import io.github.phantamanta44.war3.gui.ScopeHandler.Guns;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.opengl.GL11;

public class GuiWarOverlay extends Gui {
	
	private Minecraft mc;
	private static final Set<ElementType> typesToCancel = new HashSet<ElementType>(Arrays.asList(new ElementType[] {
			ElementType.ARMOR, ElementType.FOOD, ElementType.HEALTH, ElementType.EXPERIENCE, ElementType.BOSSHEALTH}));
	private static final ResourceLocation bloodSplat = new ResourceLocation("war3", "textures/gui/bloodSplat.png");
	private static final ResourceLocation mainOverlay = new ResourceLocation("war3", "textures/gui/overlay.png");
	private static final ResourceLocation ret = new ResourceLocation("war3", "textures/gui/icons.png");
	public static int shootTicks = 0;
	
    private int sprintBarAnimStatus;
	
	public GuiWarOverlay(Minecraft minecraft) {
		mc = minecraft;
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void eventHandler(RenderGameOverlayEvent event) {
		if (!event.isCancelable())
			return;
		
		if (shootTicks > 0)
			shootTicks--;
		
		ScaledResolution sc = event.resolution;
		int xBounds = sc.getScaledWidth();
		int yBounds = sc.getScaledHeight();
		FontRenderer fr = this.mc.fontRendererObj;
		
		if (typesToCancel.contains(event.type)) {
			event.setCanceled(true);
			if (event.type == ElementType.BOSSHEALTH)
				GlStateManager.disableBlend();
			return;
		}
		
		if (mc.thePlayer.inventory.getCurrentItem() != null) {
			if (event.type == ElementType.CROSSHAIRS) {
				if (ScopeHandler.isScopable(mc.thePlayer.inventory.getCurrentItem().getItem())) {
					if (this.mc.thePlayer.isPotionActive(Potion.moveSlowdown) && Config.useModels)
						event.setCanceled(true);
				}
				if (!event.isCanceled()) {
					GL11.glPushMatrix();
					if (Config.varReticle) {
						float scalingFac = getScalingFac(mc);
						float varX = xBounds * scalingFac, varY = yBounds * scalingFac;
						float eX = (varX - xBounds) / 2, eY = (varY - yBounds) / 2;
						GL11.glTranslatef(-eX, -eY, 0F);
						GL11.glScalef(scalingFac, scalingFac, scalingFac);
					}
					if (Config.altReticle) {
						Reticle r;
						if ((r = getReticleCoords(mc.thePlayer.inventory.getCurrentItem().getItem())) != Reticle.VANILLA) {
							event.setCanceled(true);
							GL11.glEnable(GL11.GL_BLEND);
							OpenGlHelper.glBlendFunc(775, 769, 1, 0);
							mc.renderEngine.bindTexture(ret);
							drawTexturedModalRect(xBounds / 2 - 7, yBounds / 2 - 7, r.u, r.v, 16, 16);
							OpenGlHelper.glBlendFunc(770, 771, 1, 0);
						}
					}
					if (!event.isCanceled()) {
						event.setCanceled(true);
						GL11.glEnable(GL11.GL_BLEND);
						OpenGlHelper.glBlendFunc(775, 769, 1, 0);
						mc.renderEngine.bindTexture(GuiIngame.icons);
						drawTexturedModalRect(xBounds / 2 - 7, yBounds / 2 - 7, 0, 0, 16, 16);
						OpenGlHelper.glBlendFunc(770, 771, 1, 0);
					}
					GL11.glPopMatrix();
				}
			}
			
		}
		
		if (event.type == ElementType.HOTBAR) {
        
			if (!Config.showHotbar)
				event.setCanceled(true);
			
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			
			this.renderBloodOverlay(this.mc.thePlayer.getHealth() / this.mc.thePlayer.getMaxHealth(), xBounds, yBounds);
	        
	        mc.getTextureManager().bindTexture(mainOverlay);
	        Tessellator tess = Tessellator.getInstance();
	        WorldRenderer wr = tess.getWorldRenderer();
	        wr.startDrawingQuads();
	        wr.addVertexWithUV(0.0D, (double)yBounds, -90.0D, 0.0D, 1.0D);
	        wr.addVertexWithUV((double)xBounds, (double)yBounds, -90.0D, 1.0D, 1.0D);
	        wr.addVertexWithUV((double)xBounds, 0.0D, -90.0D, 1.0D, 0.0D);
	        wr.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
	        tess.draw();
	        
	        this.renderStatOverlay(xBounds, yBounds, fr);
	        
	        GL11.glDisable(GL11.GL_BLEND);
	        GL11.glPopAttrib();
	        GL11.glPopMatrix();
		}
		
	}
	
	private void renderBloodOverlay(float par1, int par2, int par3)
    {
    	par1 = 1.0F - par1;
    	
    	if (par1 < 0)
    		par1 = 0.0F;
    	
    	if (par1 > 1.0F)
    		par1 = 1.0F;
    		
        GL11.glColor4f(par1, par1, par1, par1);this.mc.getTextureManager().bindTexture(bloodSplat);
        Tessellator tess = Tessellator.getInstance();
        WorldRenderer wr = tess.getWorldRenderer();
        wr.startDrawingQuads();
        wr.addVertexWithUV(0.0D, (double)par3, -90.0D, 0.0D, 1.0D);
        wr.addVertexWithUV((double)par2, (double)par3, -90.0D, 1.0D, 1.0D);
        wr.addVertexWithUV((double)par2, 0.0D, -90.0D, 1.0D, 0.0D);
        wr.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        tess.draw();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
	
    private void renderStatOverlay(int xBounds, int yBounds, FontRenderer fr) {
    	// Health
        float varH = this.mc.thePlayer.getHealth();
        float varHM = this.mc.thePlayer.getMaxHealth();
        String healthPercent = War3.toIntString("H: " + Math.floor((varH / varHM) * 100));
        fr.drawStringWithShadow(healthPercent, xBounds - 4 - fr.getStringWidth(healthPercent), yBounds / 4 * 3, 0xffeeeeff);
        
        // Sprint
        FoodStats fs = this.mc.thePlayer.getFoodStats();
        String strToDraw = "S: [";
        int varS = fs.getFoodLevel() - 6;
        int varSM = 13;
        if (varS < varSM) {
        	if (sprintBarAnimStatus < varSM) {
        		sprintBarAnimStatus++;
        		for (int i = sprintBarAnimStatus; i >= 0; i--) {
        			strToDraw = strToDraw.concat("-");
        		}
        	}
        	else {
        		for (int i = varSM; i >= 0; i--) {
        			if (varS >= i) {
        				strToDraw = strToDraw.concat("=");
        			}
        			else {
        				strToDraw = strToDraw.concat("-");
        			}
        		}
        	}
        }
        else {
        	if (sprintBarAnimStatus > 0) {
        		sprintBarAnimStatus--;
        	}
        	for (int i = sprintBarAnimStatus; i >= 0; i--) {
    			strToDraw = strToDraw.concat("-");
    		}
        }
        strToDraw = strToDraw.concat("]");
        //strToDraw = strToDraw.concat(" s" + varS);
        fr.drawStringWithShadow(strToDraw, xBounds - 4 - fr.getStringWidth(strToDraw), yBounds / 4 * 3 + fr.FONT_HEIGHT + 4, 0xffeeeeff);
        
        // Experience
        float varE = this.mc.thePlayer.experience;
        String expPercent = War3.toIntString("E: " + Math.floor(varE * 100));
        fr.drawStringWithShadow(expPercent, 4, yBounds - 4 - fr.FONT_HEIGHT, 0xffeeeeff);
        this.drawGradientRect(0, yBounds - 1, (int)Math.floor(xBounds * varE), yBounds, 0xff00cc00, 0xff00bb00);
        
        // Blinded?
        if (this.mc.thePlayer.isPotionActive(Potion.blindness)) {
        	int dur = this.mc.thePlayer.getActivePotionEffect(Potion.blindness).getDuration();
        	fr.drawStringWithShadow("Blinded! [" + dur + "]", 4, yBounds - 6 - (fr.FONT_HEIGHT * 2), 0xffeeeeff);
        }
        
        // Ammo
        String ammoString = "";
        ItemStack stack = this.mc.thePlayer.inventory.getCurrentItem();
        if (stack != null) {
        	String name = stack.getDisplayName();
        	if (name.contains("§e")) {
        		if (name.contains("«")) {
        			ammoString = name.split("«")[1].replaceAll("»", "").replaceAll("\\|", "/").trim();
        		}
        		else if (name.contains("§c")) {
        			ammoString = "§cReloading...";
        		}
        		else {
        			ammoString = "" + stack.stackSize;
        		}
        	}
        	else {
        		ammoString = name.trim();
        	}
        }
        fr.drawStringWithShadow(ammoString, xBounds - fr.getStringWidth(ammoString) - 4, yBounds - 4 - fr.FONT_HEIGHT, 0xffeeeeff);
        
        // Time remaining
        String timeLeft = War3.secondsToTime(this.mc.thePlayer.experienceLevel, ":");
        fr.drawStringWithShadow(timeLeft, 4, yBounds / 2 - (fr.FONT_HEIGHT / 2), 0xffeeeeff);
        
        // Map and gamemode + team
        Team teamColor = War3.getTeam(mc);
        
        if (teamColor == Team.RED) {
        	fr.drawStringWithShadow("RU", xBounds - fr.getStringWidth("RU") - 4, 2, 0xfff57e20);
        }
        else if (teamColor == Team.BLUE) {
        	fr.drawStringWithShadow("US", xBounds - fr.getStringWidth("US") - 4, 2, 0xff147c9b);
        }
        else {
        	fr.drawStringWithShadow("FFA", xBounds - fr.getStringWidth("FFA") - 4, 2, 0xffeeeeff);
        }
    }
    
    private static Reticle getReticleCoords(Item i) {
    	Guns g;
    	if ((g = ScopeHandler.Guns.fromItem(i)) != null) {
	    	switch (g) {
	    	case L118:
	    	case DRAG:
	    	case BARR:
	    	case MSR:
	    	case ISOL:
	    	case DISS:
	    	case MAC1:
	    	case MP7:
	    	case UMP4:
	    	case P90:
	    	case PP90:
	    	case M16:
	    	case M4A1:
	    	case AK47:
	    	case FAMA:
	    	case LAME:
	    	case SKUL:
	    	case RAYG:
	    		return Reticle.VANILLA;
	    	case USP4:
	    	case M9:
	    	case MAGN:
	    	case DEAG:
	    	case PYTH:
	    		return Reticle.CROSS;
	    	case M101:
	    	case SPAS:
	    	case AA12:
	    	case MODE:
	    	case SPAZ:
	    	case TYPH:
	    	case EXEC:
	    		return Reticle.CIRCLE;
	    	case MINI:
	    		return Reticle.GATLING;
	    	case FLAM:
	    		return Reticle.FLAMETHROWER;
	    	case LAW:
	    	case CBOW:
	    		return Reticle.LAUNCHER;
	    	}
    	}
		if (i == Items.slime_ball || i == Items.glowstone_dust || i == Items.gunpowder || i == Items.ghast_tear)
			return Reticle.TACTICAL;
		if (i == Item.getItemFromBlock(Blocks.lever))
			return Reticle.C4;
    	return Reticle.VANILLA;
    }
    
    private static float getScalingFac(Minecraft mc) {
    	float scale = 1.0F;
    	if (mc.thePlayer.isSneaking())
    		scale *= 0.8F;
    	if (mc.thePlayer.isInWater() || mc.thePlayer.isInLava() || mc.thePlayer.isOnLadder())
    		scale *= 1.4F;
    	if (!mc.thePlayer.onGround)
    		scale *= 1.4F;
    	if (Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ) > 0.05)
    		scale *= 1.2F;
    	if (mc.thePlayer.isSprinting())
    		scale *= 1.2F;
    	scale *= 1 + (shootTicks / 4);
    	return Math.min(scale, 2.0F);
    }
    
    private static enum Reticle {
    	
    	VANILLA(-1, -1),
    	CIRCLE(0, 0),
    	CROSS(16, 0),
    	GATLING(32, 0),
    	LAUNCHER(48, 0),
    	FLAMETHROWER(64, 0),
    	TACTICAL(0, 16),
    	C4(16, 16);
    	
    	public final int u, v;
    	
    	private Reticle(int uCoord, int vCoord) {
    		u = uCoord;
    		v = vCoord;
    	}
    	
    }
	
}
