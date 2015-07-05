package io.github.phantamanta44.war3;

import io.github.phantamanta44.war3.config.Config;
import io.github.phantamanta44.war3.config.ConfigEventListener;
import io.github.phantamanta44.war3.gui.GuiWarOverlay;
import io.github.phantamanta44.war3.handler.KickbackHandler;
import io.github.phantamanta44.war3.handler.SoundInterceptor;
import io.github.phantamanta44.war3.handler.VazkiiTickHandler;
import io.github.phantamanta44.war3.handler.VinylScratchHandler;
import io.github.phantamanta44.war3.handler.WarChatHandler;
import io.github.phantamanta44.war3.handler.WorldRenderHandler;
import io.github.phantamanta44.war3.render.FancyEffectLayer;
import io.github.phantamanta44.war3.render.ItemRenderInterceptor;
import io.github.phantamanta44.war3.render.fx.FXSparkle;
import io.github.phantamanta44.war3.render.fx.FXWisp;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.entity.RenderHackThing;
import net.minecraft.client.renderer.entity.RenderHackThing.DummyHackPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid="war3", name="MC-War Client", version="0.0.5", useMetadata=true, guiFactory="io.github.phantamanta44.war3.config.ConfigGuiFactory")
public class War3 {
	
		public static final String awesomePeopleUrl = "https://gist.githubusercontent.com/phantamanta44/048cb544cb2fe8c338e5/raw/awesomepeople.txt";
		public static List<String> awesomePeople;
		public static RenderPlayer defRP;
		public static RenderPlayer slimRP;
	
        @Instance("war3")
        public static War3 instance;
        
        @SidedProxy(clientSide="io.github.phantamanta44.war3.CommonProxy", serverSide="io.github.phantamanta44.war3.CommonProxy")
        public static CommonProxy proxy;
        
        public static String cachedTeam = "blue";
        
        @EventHandler
        public void preInit(FMLPreInitializationEvent event) {
        	Config.init(new File("./config/war3/config.cfg"));
        	FMLCommonHandler.instance().bus().register(new ConfigEventListener());
        }
        
        @EventHandler
        public void load(FMLInitializationEvent event) {
                proxy.registerRenderers();
        }
        
        @EventHandler
        public void postInit(FMLPostInitializationEvent event) {
        	Minecraft mc = Minecraft.getMinecraft();
        	getAwesomePeople();
        	registerEvents(mc);
        	registerRenderers(mc);
        }
        
        public static void getAwesomePeople() {
        	awesomePeople = new ArrayList<String>();
        	try {
        		BufferedReader readIn = new BufferedReader(new InputStreamReader(new URL(awesomePeopleUrl).openStream()));
        		String s;
        		while ((s = readIn.readLine()) != null)
        			awesomePeople.add(s);
        	}
        	catch (Exception ex) {
        		System.out.println("[War3] Error while getting awesome people list:");
        		ex.printStackTrace();
        	}
        }
        
        public static void registerRenderers(Minecraft mc) {
        	RenderManager man = mc.getRenderManager();
        	defRP = (RenderPlayer)man.getEntityRenderObject(new DummyHackPlayer("default"));
    		slimRP = (RenderPlayer)man.getEntityRenderObject(new DummyHackPlayer("slim"));
    		RenderHackThing.addLayer(defRP, new FancyEffectLayer(defRP));
    		RenderHackThing.addLayer(slimRP, new FancyEffectLayer(slimRP));
        }
        
        public static void registerEvents(Minecraft mc) {
        	MinecraftForge.EVENT_BUS.register(new GuiWarOverlay(mc));
        	MinecraftForge.EVENT_BUS.register(new WarChatHandler(mc));
        	MinecraftForge.EVENT_BUS.register(new SoundInterceptor());
        	MinecraftForge.EVENT_BUS.register(new ItemRenderInterceptor(mc));
        	MinecraftForge.EVENT_BUS.register(new WorldRenderHandler(mc));
        	FMLCommonHandler.instance().bus().register(new VazkiiTickHandler());
        	FMLCommonHandler.instance().bus().register(new VinylScratchHandler(mc));
        	FMLCommonHandler.instance().bus().register(new KickbackHandler(mc));
        	ItemRenderInterceptor.registerRenderers();
        	ItemRenderInterceptor.toolRenderFix();
        }
        
        public static Collection getClientsidePlayerList() {
        	NetHandlerPlayClient nethandlerplayclient = Minecraft.getMinecraft().thePlayer.sendQueue;
        	return nethandlerplayclient.func_175106_d();
        }
        
        public static String secondsToTime(int i, String seperatingChar) {
    		int seconds = i % 60;
    		int minutes = (i - seconds) / 60;
    		String secondsParsed = "" + seconds;
    		secondsParsed = (secondsParsed.length() == 1) ? (secondsParsed = "0" + secondsParsed) : secondsParsed;
    		return "" + minutes + seperatingChar + secondsParsed;
    	}
    	
    	public static String toIntString(String i) {
    		if (i.contains(".")) {
    			return i.split("\\.")[0] + "%";
    		}
    		return i + "%";
    	}
    	
    	public static String getTeam(Minecraft mc) {
    		if (mc.thePlayer.inventory.armorItemInSlot(3) != null) {
    			ItemStack helmItem = mc.thePlayer.inventory.armorItemInSlot(3);
    			if (Item.getIdFromItem(helmItem.getItem()) == 397) {
    				cachedTeam = "red";
    				return "red";
    			}
    			else if (Item.getIdFromItem(helmItem.getItem()) == 298) {
    				NBTTagCompound nbt = helmItem.getTagCompound();
    				int colorInt = nbt.getCompoundTag("display").getInteger("color");
    				if (colorInt == 16711680) {
    					cachedTeam = "red";
    					return "red";
    				}
    				else if (colorInt == 255) {
    					cachedTeam = "blue";
    					return "blue";
    				}
    				else {
    					return "ffa";
    				}
    			}
    		}
    		return "unknown";
    	}
    	
    	public static int getHelmetColor(Minecraft mc) {
    		if (mc.thePlayer.inventory.armorItemInSlot(3) != null) {
    			ItemStack helmItem = mc.thePlayer.inventory.armorItemInSlot(3);
    			if (Item.getIdFromItem(helmItem.getItem()) == 298) {
    				NBTTagCompound nbt = helmItem.getTagCompound();
    				int colorInt = nbt.getCompoundTag("display").getInteger("color");
    				return colorInt;
    			}
    		}
    		return 1337;
    	}
    	
    	public static void raygunParticle(Minecraft mc, Vec3 pos) {
    		Random rand = mc.theWorld.rand;
			FXWisp wisp = new FXWisp(mc.theWorld, pos.xCoord, pos.yCoord, pos.zCoord,
					1.0F, 0.02F, 0.4F, 0.05F, false, false, 0.5F);
			FXSparkle spark = new FXSparkle(mc.theWorld, pos.xCoord + rand.nextDouble() - 0.5, pos.yCoord + rand.nextDouble() - 0.5, pos.zCoord + rand.nextDouble() - 0.5,
					2.0F, 0.03F, 0.6F, 0.04F, 6);
			mc.effectRenderer.addEffect(wisp);
			mc.effectRenderer.addEffect(spark);
    	}
    	
    	public static void killTheZombiesPls(Minecraft mc) {
    		Vec3 v = mc.thePlayer.getLookVec();
    		Vec3 pos = mc.thePlayer.getPositionVector().add(new Vec3(0, 1.6, 0)).add(v);
    		for (int i = 0; i < 32 && mc.theWorld.isAirBlock(new BlockPos(pos)); i++) {
    			raygunParticle(mc, pos);
    			pos = pos.add(v);
    		}
    	}
    	
}
