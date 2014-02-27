package com.stratomine.encampment;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class CrateTileEntity extends TileEntity implements IInventory {

	private static final short INVENTORY_SIZE = 9;

	private final ItemStack[] inventory;

	public CrateTileEntity() {
		inventory = new ItemStack[INVENTORY_SIZE];
	}

	public void clearInventorySlotContents(int slot) {
		setInventorySlotContents(slot, null);
	}

	@Override
	public void closeChest() {
		// Do nothing
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack stack = getStackInSlot(slot);

		if (stack != null) {
			if (stack.stackSize <= amount) {
				clearInventorySlotContents(slot);
			} else {
				stack = stack.splitStack(amount);

				if (stack.stackSize == 0) {
					clearInventorySlotContents(slot);
				}
			}
		}

		return stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public String getInvName() {
		return "com.stratomine.encampment.Crate";
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);

		if (stack != null) {
			clearInventorySlotContents(slot);
		}

		return stack;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		boolean isValidTileEntity = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this;
		boolean playerIsCloseEnough = player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;

		return isValidTileEntity && playerIsCloseEnough;
	}

	@Override
	public void openChest() {
		// Do nothing
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		NBTTagList serializedInventory = tag.getTagList("Inventory");

		for (int i = 0; i < serializedInventory.tagCount(); i++) {
			NBTTagCompound serializedEntry = (NBTTagCompound)serializedInventory.tagAt(i);
			byte slot = serializedEntry.getByte("Slot");

			if (slot >= 0 && slot < getSizeInventory()) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(serializedEntry);
			}
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		NBTTagList serializedInventory = new NBTTagList();

		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack stack = inventory[i];

			if (stack != null) {
				NBTTagCompound serializedEntry = new NBTTagCompound();
				serializedEntry.setByte("Slot", (byte)i);
				stack.writeToNBT(serializedEntry);
				serializedInventory.appendTag(serializedEntry);
			}
		}

		tag.setTag("Inventory", serializedInventory);
	}

}
