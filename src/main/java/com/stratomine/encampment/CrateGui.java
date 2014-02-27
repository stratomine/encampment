package com.stratomine.encampment;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class CrateGui extends GuiContainer {

	protected static final int LEFT_PADDING = 8;
	protected static final int SLOT_HEIGHT = 18;
	protected static final int SLOT_WIDTH = SLOT_HEIGHT;
	protected static final int TOP_PADDING = 18;

	private static final ResourceLocation GUI_TEXTURE =
			EncampmentMod.getResourceLocation("textures/gui/crate.png");

	public CrateGui(InventoryPlayer playerInventory, CrateTileEntity crateTileEntity) {
		super(new CrateContainer(playerInventory, crateTileEntity));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		mc.getTextureManager().bindTexture(GUI_TEXTURE);

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		fontRenderer.drawString("Crate", LEFT_PADDING, 6, 4210752);
	}

}
