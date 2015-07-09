package io.github.phantamanta44.war3.render.model.attach;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;

public interface IScopeAttachment extends IAttachmentRenderer {

	public void renderOrthoScope(Minecraft mc, Tessellator tess, int xBounds, int yBounds);
	public void onScopeEnd(Minecraft mc);
	
}
