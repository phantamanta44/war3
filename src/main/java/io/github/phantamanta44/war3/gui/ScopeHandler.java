package io.github.phantamanta44.war3.gui;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class ScopeHandler
{	
    
    public static boolean isScopable(int i) {
        for (ScopeHandler.Guns gun : ScopeHandler.Guns.values()) {
        	if (i == gun.getId()) {
        		return true;
        	}
        }
        return false;
    }
    
    public static void renderMuzzleFlash(int par1, int par2, Minecraft mc) {
    	Random rand = new Random();
    	GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableAlpha();
        
        mc.getTextureManager().bindTexture(new ResourceLocation("textures/war/muzzle/flash" + rand.nextInt(3) + ".png"));
        Tessellator tess = Tessellator.getInstance();
        WorldRenderer wr = tess.getWorldRenderer();
        wr.startDrawingQuads();
        wr.addVertexWithUV(par1 / 2, par2 / 2 + 64, -90.0D, 0.0D, 1.0D);
        wr.addVertexWithUV(par1 / 2 + 64, par2 / 2 + 64, -90.0D, 1.0D, 1.0D);
        wr.addVertexWithUV(par1 / 2 + 64, par2 / 2, -90.0D, 1.0D, 0.0D);
        wr.addVertexWithUV(par1 / 2, par2 / 2, -90.0D, 0.0D, 0.0D);
        tess.draw();
        
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
    
    public enum Guns {
    	USP4(269, "pistol1"),
    	M9(273, "pistol2"),
    	MAGN(256, "pistol3"),
    	DEAG(277, "pistol4"),
    	EXEC(334, "pistol5"),
    	PYTH(284, "pistol6"),
    	M101(270, "shotgun1"),
    	SPAS(274, "shotgun2"),
    	AA12(257, "shotgun3"),
    	MODE(278, "shotgun4"),
    	SPAZ(280, "shotgun5"),
    	TYPH(336, "shotgun6"),
    	L118(271, "sniper1"),
    	DRAG(275, "sniper2"),
    	BARR(258, "sniper3"),
    	MSR(279, "sniper4"),
    	ISOL(281, "sniper5"),
    	DISS(339, "sniper6"),
    	M16(290, "assault1"),
    	M4A1(291, "assault2"),
    	AK47(292, "assault3"),
    	FAMA(293, "assault4"),
    	LAME(265, "assault5"),
    	SKUL(266, "assault6"),
    	MAC1(288, "smg1"),
    	MP7(377, "smg2"),
    	UMP4(369, "smg3"),
    	P90(371, "smg4"),
    	PP90(372, "smg5"),
    	FLAM(285, "flamethrower"),
    	CBOW(261, "crossbow"),
    	LAW(294, "law"),
    	MINI(264, "minigun"),
    	RAYG(352, "raygun");
    	
    	private int id;
    	
    	private Guns(int arg1, String arg2)
    	{
    		id = arg1;
    	}
    	
    	public int getId()
    	{
    		return id;
    	}
    	
    	public static Guns fromItem(Item i) {
    		int id = Item.getIdFromItem(i);
    		for (Guns g : values()) {
    			if (id == g.getId())
    				return g;
    		}
    		return null;
    	}
    }
    
}