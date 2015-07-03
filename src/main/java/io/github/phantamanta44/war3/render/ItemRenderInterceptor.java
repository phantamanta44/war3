package io.github.phantamanta44.war3.render;

import io.github.phantamanta44.war3.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
				renderer.renderItem(ItemRenderType.EQUIPPED_FIRST_PERSON, this.mc.thePlayer.getCurrentEquippedItem());
				if (event.isCancelable())
					event.setCanceled(true);
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
    	
    	MinecraftForgeClient.registerItemRenderer(Items.wooden_axe, new ClippedItemRenderer(
    			new ResourceLocation("war3", "model/obj/sniper1.obj"),
    			new ResourceLocation("war3", "textures/model/sv98.png"), "Mag.018"));
    	MinecraftForgeClient.registerItemRenderer(Items.stone_axe, new ClippedItemRenderer(
    			new ResourceLocation("war3", "model/obj/sniper2.obj"),
    			new ResourceLocation("war3", "textures/model/mk11.png"), "Mag.008"));
    	MinecraftForgeClient.registerItemRenderer(Items.iron_axe, new ClippedItemRenderer(
    			new ResourceLocation("war3", "model/obj/sniper3.obj"),
    			new ResourceLocation("war3", "textures/model/m98b.png"), "Mag.009"));
    	MinecraftForgeClient.registerItemRenderer(Items.diamond_axe, new ClippedItemRenderer(
    			new ResourceLocation("war3", "model/obj/sniper4.obj"),
    			new ResourceLocation("war3", "textures/model/m39.png"), "Mag.011"));
    	MinecraftForgeClient.registerItemRenderer(Items.bowl, new ClippedItemRenderer(
    			new ResourceLocation("war3", "model/obj/sniper5.obj"),
    			new ResourceLocation("war3", "textures/model/l96.png"), "Mag.019"));
    	MinecraftForgeClient.registerItemRenderer(Items.paper, new ClippedItemRenderer(
    			new ResourceLocation("war3", "model/obj/sniper6.obj"),
    			new ResourceLocation("war3", "textures/model/sks.png"), "Mag.016"));
    	
    	MinecraftForgeClient.registerItemRenderer(Items.wooden_pickaxe, new ObjModelItemRenderer(
    			new ResourceLocation("war3", "model/obj/shotgun1.obj"),
    			new ResourceLocation("war3", "textures/model/m1014.png")));
    	MinecraftForgeClient.registerItemRenderer(Items.stone_pickaxe, new PumpedItemRenderer(
    			new ResourceLocation("war3", "model/obj/shotgun2.obj"),
    			new ResourceLocation("war3", "textures/model/spas12.png"), "Pump.002"));
    	MinecraftForgeClient.registerItemRenderer(Items.iron_pickaxe, new ObjModelItemRenderer(
    			new ResourceLocation("war3", "model/obj/shotgun3.obj"),
    			new ResourceLocation("war3", "textures/model/usas.png")));
    	MinecraftForgeClient.registerItemRenderer(Items.diamond_pickaxe, new PumpedItemRenderer(
    			new ResourceLocation("war3", "model/obj/shotgun4.obj"),
    			new ResourceLocation("war3", "textures/model/870.png"), "Pump.001"));
    	MinecraftForgeClient.registerItemRenderer(Items.stick, new ObjModelItemRenderer(
    			new ResourceLocation("war3", "model/obj/shotgun5.obj"),
    			new ResourceLocation("war3", "textures/model/saiga.png")));
    	MinecraftForgeClient.registerItemRenderer(Items.brick, new ObjModelItemRenderer(
    			new ResourceLocation("war3", "model/obj/shotgun6.obj"),
    			new ResourceLocation("war3", "textures/model/dao12.png")));
    	
    	MinecraftForgeClient.registerItemRenderer(Items.wooden_shovel, new ClippedItemRenderer(
    			new ResourceLocation("war3", "model/obj/pistol1.obj"),
    			new ResourceLocation("war3", "textures/model/mp443.png"), "Mag.020"));
    	MinecraftForgeClient.registerItemRenderer(Items.stone_shovel, new ClippedItemRenderer(
    			new ResourceLocation("war3", "model/obj/pistol2.obj"),
    			new ResourceLocation("war3", "textures/model/m9.png"), "Mag.021"));
    	MinecraftForgeClient.registerItemRenderer(Items.iron_shovel, new ObjModelItemRenderer(
    			new ResourceLocation("war3", "model/obj/pistol3.obj"),
    			new ResourceLocation("war3", "textures/model/mp412.png")));
    	MinecraftForgeClient.registerItemRenderer(Items.diamond_shovel, new ClippedItemRenderer(
    			new ResourceLocation("war3", "model/obj/pistol4.obj"),
    			new ResourceLocation("war3", "textures/model/deagle.png"), "Mag.024"));
    	MinecraftForgeClient.registerItemRenderer(Items.leather, new ClippedItemRenderer(
    			new ResourceLocation("war3", "model/obj/pistol5.obj"),
    			new ResourceLocation("war3", "textures/model/m9.png"), "Mag.023"));
    	MinecraftForgeClient.registerItemRenderer(Items.golden_shovel, new ObjModelItemRenderer(
    			new ResourceLocation("war3", "model/obj/pistol6.obj"),
    			new ResourceLocation("war3", "textures/model/t44.png")));
    	
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
    	MinecraftForgeClient.registerItemRenderer(Items.nether_wart, new ObjModelItemRenderer(
    			new ResourceLocation("war3", "model/obj/smg5.obj"),
    			new ResourceLocation("war3", "textures/model/pp2k.png")));
    	
    	/*MinecraftForgeClient.registerItemRenderer(Items.golden_pickaxe, new ObjModelItemRenderer(
    			new ResourceLocation("war3", "model/obj/flamethrower.obj"),
    			new ResourceLocation("war3", "textures/model/flamethrower.png")));*/
    	MinecraftForgeClient.registerItemRenderer(Items.bow, new ObjModelItemRenderer(
    			new ResourceLocation("war3", "model/obj/crossbow.obj"),
    			new ResourceLocation("war3", "textures/model/m320.png")));
    	MinecraftForgeClient.registerItemRenderer(Items.golden_hoe, new ObjModelItemRenderer(
    			new ResourceLocation("war3", "model/obj/law.obj"),
    			new ResourceLocation("war3", "textures/model/smaw.png")));
    	MinecraftForgeClient.registerItemRenderer(Items.diamond, new ClippedItemRenderer(
    			new ResourceLocation("war3", "model/obj/minigun.obj"),
    			new ResourceLocation("war3", "textures/model/m249.png"), "MagBox"));
    	MinecraftForgeClient.registerItemRenderer(Items.bone, new ObjModelItemRenderer(
    			new ResourceLocation("war3", "model/obj/raygun.obj"),
    			new ResourceLocation("war3", "textures/model/railgun-dual.png")));
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
