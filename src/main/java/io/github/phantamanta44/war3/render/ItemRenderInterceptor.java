package io.github.phantamanta44.war3.render;

import io.github.phantamanta44.war3.config.Config;
import io.github.phantamanta44.war3.render.model.BoltActionItemRenderer;
import io.github.phantamanta44.war3.render.model.ClippedItemRenderer;
import io.github.phantamanta44.war3.render.model.L96ItemRenderer;
import io.github.phantamanta44.war3.render.model.ObjModelItemRenderer;
import io.github.phantamanta44.war3.render.model.P90ItemRenderer;
import io.github.phantamanta44.war3.render.model.PumpedItemRenderer;
import io.github.phantamanta44.war3.render.model.RaygunItemRenderer;
import io.github.phantamanta44.war3.render.model.RevolverItemRenderer;
import io.github.phantamanta44.war3.render.model.StraightPullBoltItemRenderer;
import io.github.phantamanta44.war3.render.model.attach.BallisticScope;
import io.github.phantamanta44.war3.render.model.attach.LaserSight;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.opengl.GL11;

public class ItemRenderInterceptor {

	private Minecraft mc;
	
	public ItemRenderInterceptor(Minecraft minecraft) {
		mc = minecraft;
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onHandRender(RenderHandEvent event) {
		if (!Config.useModels)
			return;
		if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && this.mc.thePlayer.getCurrentEquippedItem() != null && this.mc.thePlayer.getCurrentEquippedItem().getItem() != null) {
			IItemRenderer renderer = MinecraftForgeClient.getItemRenderer(this.mc.thePlayer.getCurrentEquippedItem(), ItemRenderType.EQUIPPED_FIRST_PERSON);
			if (renderer != null) {
				if (event.isCancelable())
					event.setCanceled(true);
				if (renderer instanceof ObjModelItemRenderer && mc.thePlayer.getActivePotionEffect(Potion.moveSlowdown) != null) {
					if (ObjModelItemRenderer.isFullyScopedIn() && ((ObjModelItemRenderer)renderer).isScoped())
						return;
				}
				double light = Math.max((double)mc.theWorld.getCombinedLight(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.eyeHeight, mc.thePlayer.posZ), 0) / 15728848, 0.1);
				GL11.glColor3d(light, light, light);
				renderer.renderItem(ItemRenderType.EQUIPPED_FIRST_PERSON, this.mc.thePlayer.getCurrentEquippedItem());
				GL11.glColor3d(1.0, 1.0, 1.0);
			}
		}
	}
	
	public static void registerRenderers() {
		MinecraftForgeClient.registerItemRenderer(Items.wooden_hoe, new ClippedItemRenderer(
				new ResourceLocation("war3", "model/obj/assault1.obj"),
				new ResourceLocation("war3", "textures/model/m16a4.png"), "Mag.012"));
		MinecraftForgeClient.registerItemRenderer(Items.stone_hoe, new ClippedItemRenderer(
				new ResourceLocation("war3", "model/obj/assault2.obj"),
				new ResourceLocation("war3", "textures/model/m4a1.png"), "Mag.013"));
		MinecraftForgeClient.registerItemRenderer(Items.iron_hoe, new ClippedItemRenderer(
				new ResourceLocation("war3", "model/obj/assault3.obj"),
				new ResourceLocation("war3", "textures/model/rpk.png"), "Mag.019"));
		MinecraftForgeClient.registerItemRenderer(Items.diamond_hoe, new ClippedItemRenderer(
				new ResourceLocation("war3", "model/obj/assault4.obj"),
				new ResourceLocation("war3", "textures/model/famas.png"), "Mag.016"));
		MinecraftForgeClient.registerItemRenderer(Items.iron_ingot, new ClippedItemRenderer(
				new ResourceLocation("war3", "model/obj/assault5.obj"),
				new ResourceLocation("war3", "textures/model/aek.png"), "Mag.017"));
		MinecraftForgeClient.registerItemRenderer(Items.gold_ingot, new ClippedItemRenderer(
				new ResourceLocation("war3", "model/obj/assault6.obj"),
				new ResourceLocation("war3", "textures/model/g36.png"), "Mag.018"));
		
		MinecraftForgeClient.registerItemRenderer(Items.wooden_axe, new BoltActionItemRenderer(
				new ResourceLocation("war3", "model/obj/sniper1.obj"),
				new ResourceLocation("war3", "textures/model/sv98.png"), "Mag.018", "Bolt.019", 3.05).addAttachment(new BallisticScope(0, 4.64, -7.53)));
		MinecraftForgeClient.registerItemRenderer(Items.stone_axe, new ClippedItemRenderer(
				new ResourceLocation("war3", "model/obj/sniper2.obj"),
				new ResourceLocation("war3", "textures/model/mk11.png"), "Mag.008"));
		MinecraftForgeClient.registerItemRenderer(Items.iron_axe, new BoltActionItemRenderer(
				new ResourceLocation("war3", "model/obj/sniper3.obj"),
				new ResourceLocation("war3", "textures/model/m98b.png"), "Mag.009", "Bolt.011", -0.5).addAttachment(new BallisticScope(0, 2.45, -10.8)));
		MinecraftForgeClient.registerItemRenderer(Items.diamond_axe, new StraightPullBoltItemRenderer(
				new ResourceLocation("war3", "model/obj/sniper4.obj"),
				new ResourceLocation("war3", "textures/model/m39.png"), "Mag.011", "Bolt.012"));
		MinecraftForgeClient.registerItemRenderer(Items.bowl, new L96ItemRenderer(
				new ResourceLocation("war3", "model/obj/sniper5.obj"),
				new ResourceLocation("war3", "textures/model/l96.png"), "Mag.019", "Bolt2.002", "Bolt.020", 2.48).addAttachment(new BallisticScope(0, 3.74, -8.67)));
		MinecraftForgeClient.registerItemRenderer(Items.paper, new StraightPullBoltItemRenderer(
				new ResourceLocation("war3", "model/obj/sniper6.obj"),
				new ResourceLocation("war3", "textures/model/sks.png"), "Mag.016", "Bolt.017"));
		
		MinecraftForgeClient.registerItemRenderer(Items.wooden_pickaxe, new ObjModelItemRenderer(
				new ResourceLocation("war3", "model/obj/shotgun1.obj"),
				new ResourceLocation("war3", "textures/model/m1014.png")));
		MinecraftForgeClient.registerItemRenderer(Items.stone_pickaxe, new PumpedItemRenderer(
				new ResourceLocation("war3", "model/obj/shotgun2.obj"),
				new ResourceLocation("war3", "textures/model/spas12.png"), "Pump.002"));
		MinecraftForgeClient.registerItemRenderer(Items.iron_pickaxe, new ClippedItemRenderer(
				new ResourceLocation("war3", "model/obj/shotgun3.obj"),
				new ResourceLocation("war3", "textures/model/usas.png"), "Mag.001"));
		MinecraftForgeClient.registerItemRenderer(Items.diamond_pickaxe, new PumpedItemRenderer(
				new ResourceLocation("war3", "model/obj/shotgun4.obj"),
				new ResourceLocation("war3", "textures/model/870.png"), "Pump.001"));
		MinecraftForgeClient.registerItemRenderer(Items.stick, new ClippedItemRenderer(
				new ResourceLocation("war3", "model/obj/shotgun5.obj"),
				new ResourceLocation("war3", "textures/model/saiga.png"), "default001.001"));
		MinecraftForgeClient.registerItemRenderer(Items.brick, new ObjModelItemRenderer(
				new ResourceLocation("war3", "model/obj/shotgun6.obj"),
				new ResourceLocation("war3", "textures/model/dao12.png")));
		
		MinecraftForgeClient.registerItemRenderer(Items.wooden_shovel, new ClippedItemRenderer(
				new ResourceLocation("war3", "model/obj/pistol1.obj"),
				new ResourceLocation("war3", "textures/model/mp443.png"), "Mag.020").addAttachment(new LaserSight(0, 1.5, -18.75)));
		MinecraftForgeClient.registerItemRenderer(Items.stone_shovel, new ClippedItemRenderer(
				new ResourceLocation("war3", "model/obj/pistol2.obj"),
				new ResourceLocation("war3", "textures/model/m9.png"), "Mag.021").addAttachment(new LaserSight(0, 1.39, -19.2)));
		MinecraftForgeClient.registerItemRenderer(Items.iron_shovel, new RevolverItemRenderer(
				new ResourceLocation("war3", "model/obj/pistol3.obj"),
				new ResourceLocation("war3", "textures/model/mp412.png"), "Chamber.001", 2.875));
		MinecraftForgeClient.registerItemRenderer(Items.diamond_shovel, new ClippedItemRenderer(
				new ResourceLocation("war3", "model/obj/pistol4.obj"),
				new ResourceLocation("war3", "textures/model/deagle.png"), "Mag.024").addAttachment(new LaserSight(0, 0.06, -20.9)));
		MinecraftForgeClient.registerItemRenderer(Items.leather, new ClippedItemRenderer(
				new ResourceLocation("war3", "model/obj/pistol5.obj"),
				new ResourceLocation("war3", "textures/model/m9.png"), "Mag.023"));
		MinecraftForgeClient.registerItemRenderer(Items.golden_shovel, new RevolverItemRenderer(
				new ResourceLocation("war3", "model/obj/pistol6.obj"),
				new ResourceLocation("war3", "textures/model/t44.png"), "Trigger001", 2.1725));
		
		MinecraftForgeClient.registerItemRenderer(Items.feather, new ClippedItemRenderer(
				new ResourceLocation("war3", "model/obj/smg1.obj"),
				new ResourceLocation("war3", "textures/model/pdw-r.png"), "Mag.006"));
		MinecraftForgeClient.registerItemRenderer(Items.blaze_powder, new ClippedItemRenderer(
				new ResourceLocation("war3", "model/obj/smg2.obj"),
				new ResourceLocation("war3", "textures/model/mp7.png"), "Mag.003"));
		MinecraftForgeClient.registerItemRenderer(Items.blaze_rod, new ClippedItemRenderer(
				new ResourceLocation("war3", "model/obj/smg3.obj"),
				new ResourceLocation("war3", "textures/model/ump45.png"), "Mag.004"));
		MinecraftForgeClient.registerItemRenderer(Items.gold_nugget, new P90ItemRenderer(
				new ResourceLocation("war3", "model/obj/smg4.obj"),
				new ResourceLocation("war3", "textures/model/p90.png"), "Mag.005"));
		MinecraftForgeClient.registerItemRenderer(Items.nether_wart, new ClippedItemRenderer(
				new ResourceLocation("war3", "model/obj/smg5.obj"),
				new ResourceLocation("war3", "textures/model/pp2k.png"), "default001.004"));
		
		/*MinecraftForgeClient.registerItemRenderer(Items.golden_pickaxe, new ObjModelItemRenderer(
				new ResourceLocation("war3", "model/obj/flamethrower.obj"),
				new ResourceLocation("war3", "textures/model/flamethrower.png")));*/
		MinecraftForgeClient.registerItemRenderer(Items.bow, new ObjModelItemRenderer(
				new ResourceLocation("war3", "model/obj/crossbow.obj"),
				new ResourceLocation("war3", "textures/model/m320.png")));
		MinecraftForgeClient.registerItemRenderer(Items.golden_hoe, new ClippedItemRenderer(
				new ResourceLocation("war3", "model/obj/law.obj"),
				new ResourceLocation("war3", "textures/model/smaw.png"), "Missile.001"));
		MinecraftForgeClient.registerItemRenderer(Items.diamond, new ClippedItemRenderer(
				new ResourceLocation("war3", "model/obj/minigun.obj"),
				new ResourceLocation("war3", "textures/model/m249.png"), "MagBox"));
		MinecraftForgeClient.registerItemRenderer(Items.bone, new RaygunItemRenderer(
				new ResourceLocation("war3", "model/obj/raygun.obj"),
				new ResourceLocation("war3", "textures/model/railgun-dual.png"), "solid_glass"));
	}
	
	public static void toolRenderFix() {
		Items.stick.setFull3D();
		Items.brick.setFull3D();
		Items.leather.setFull3D();
		Items.feather.setFull3D();
		Items.blaze_powder.setFull3D();
		Items.blaze_rod.setFull3D();
		Items.gold_nugget.setFull3D();
		Items.nether_wart.setFull3D();
		Items.iron_ingot.setFull3D();
		Items.gold_ingot.setFull3D();
		Items.bowl.setFull3D();
		Items.paper.setFull3D();
		Items.diamond.setFull3D();
		Items.bone.setFull3D();
		Items.wheat.setFull3D();
	}
	
}
