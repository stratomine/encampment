package com.stratomine.encampment;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class CrateContainer extends Container {

	protected CrateTileEntity crateTileEntity;

	public CrateContainer(InventoryPlayer playerInventory, CrateTileEntity crateTileEntity) {
		this.crateTileEntity = crateTileEntity;

		buildContainerLayout(playerInventory);
	}

	private void buildContainerLayout(IInventory playerInventory) {
		buildCrateSlots();
		buildPlayerInventorySlots(playerInventory);
	}

	private void buildCrateSlots() {
		int x;
		int y = CrateGui.TOP_PADDING;
		Slot slot;

		for (int column = 0; column < crateTileEntity.getSizeInventory(); column++) {
			x = CrateGui.LEFT_PADDING + (CrateGui.SLOT_WIDTH * column);
			slot = new Slot(crateTileEntity, column, x, y);

			addSlotToContainer(slot);
		}
	}

	private void buildPlayerInventorySlots(IInventory playerInventory) {
		// Build inventory slots
		int index, x;
		int y = 50;
		Slot slot;

		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 9; column++) {
				x = CrateGui.LEFT_PADDING + (CrateGui.SLOT_WIDTH * column);
				index = column + row * 9 + 9;
				slot = new Slot(playerInventory, index, x, y);

				addSlotToContainer(slot);
			}

			y += CrateGui.SLOT_HEIGHT;
		}

		// Build hot bar slots
		y = 108;

		for (int i = 0; i < 9; i++) {
			x = CrateGui.LEFT_PADDING + (CrateGui.SLOT_WIDTH * i);
			slot = new Slot(playerInventory, i, x, y);

			addSlotToContainer(slot);
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return crateTileEntity.isUseableByPlayer(player);
	}

}
