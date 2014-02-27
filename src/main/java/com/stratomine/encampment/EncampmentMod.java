package com.stratomine.encampment;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "com.stratomine.encampment", name = "Encampment", version = "0.0.1")
public class EncampmentMod {

	@Instance(value = "com.stratomine.encampment")
	public static EncampmentMod instance;

	public static final Block crateBlock = new CrateBlock(500);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		registerBlocks();
	}

	private void registerBlocks() {
		CrateBlock.register(this, crateBlock);
	}

	protected static ResourceLocation getResourceLocation(String resourcePath) {
		return new ResourceLocation("com.stratomine.encampment", resourcePath);
	}

	protected static String getTextureName(String textureName) {
		return "com.stratomine.encampment:" + textureName;
	}

}
