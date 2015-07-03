package io.github.phantamanta44.war3.gui;

import io.github.phantamanta44.war3.War3;
import io.github.phantamanta44.war3.config.Config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
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
	
    private int sprintBarAnimStatus;
	
	public GuiWarOverlay(Minecraft minecraft) {
		mc = minecraft;
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void eventHandler(RenderGameOverlayEvent event) {
		if (!event.isCancelable())
			return;
		
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
			if (event.type == ElementType.CROSSHAIRS && ScopeHandler.isScopable(Item.getIdFromItem(mc.thePlayer.inventory.getCurrentItem().getItem()))) {
				if (this.mc.thePlayer.isPotionActive(Potion.moveSlowdown) && Config.useModels)
					event.setCanceled(true);
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
        
        String teamColor = War3.getTeam(mc);
        
        if (teamColor == "red") {
        	fr.drawStringWithShadow("RU", xBounds - fr.getStringWidth("RU") - 4, 2, 0xfff57e20);
        }
        else if (teamColor == "blue") {
        	fr.drawStringWithShadow("US", xBounds - fr.getStringWidth("US") - 4, 2, 0xff147c9b);
        }
        else {
        	fr.drawStringWithShadow("FFA", xBounds - fr.getStringWidth("FFA") - 4, 2, 0xffeeeeff);
        }
    }
	
}
