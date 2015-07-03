package net.minecraft.client.renderer.entity;

import java.util.UUID;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.WorldInfo;

import com.mojang.authlib.GameProfile;

public class RenderHackThing {

	public static void addLayer(RendererLivingEntity ent, LayerRenderer layer) {
		ent.addLayer(layer);
	}
	
	public static String getLayers(RendererLivingEntity ent) {
		String ret = "";
		for (Object r : ent.layerRenderers)
			ret += r.getClass().getName() + " ";
		return ret;
	}
	
	public static class DummyHackPlayer extends AbstractClientPlayer {
		
		private final String skinType;
		
		public DummyHackPlayer(String skin) {
			super(new DummyHackWorld(), new GameProfile(UUID.randomUUID(), skin));
			skinType = skin;
		}
		
		@Override
		public String getSkinType() {
			return skinType;
		}
		
	}
	
	public static class DummyHackWorld extends World {

		protected DummyHackWorld() {
			super(null, new DummyHackWorldInfo(), new DummyHackProvider(), null, true);
			this.provider.registerWorld(this);
		}

		@Override
		protected IChunkProvider createChunkProvider() {
			return null;
		}

		@Override
		protected int getRenderDistanceChunks() {
			return 0;
		}
		
		public static class DummyHackWorldInfo extends WorldInfo {
			
			public DummyHackWorldInfo() {
				super();
			}
			
		}
		
		public static class DummyHackProvider extends WorldProvider {
			
			@Override
			public String getDimensionName() {
				return "DUMMY";
			}

			@Override
			public String getInternalNameSuffix() {
				return "DUMMY";
			}
			
		}
		
	}
	
}
