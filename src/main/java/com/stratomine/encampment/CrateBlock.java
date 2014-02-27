package com.stratomine.encampment;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class CrateBlock extends BlockContainer {

	private static final CreativeTabs CREATIVE_TAB = CreativeTabs.tabDecorations;
	private static final float HARDNESS = 2.5F;
	private static final Material MATERIAL = Material.wood;
	private static final float RESISTANCE = 8F;
	private static final StepSound STEP_SOUND = Block.soundWoodFootstep;
	private static final String NAME = "com.stratomine.encampment.crate";

	private final Random random;

	protected CrateBlock(int id) {
		super(id, MATERIAL);

		random = new Random();

		setCreativeTab(CREATIVE_TAB);
		setHardness(HARDNESS);
		setResistance(RESISTANCE);
		setStepSound(STEP_SOUND);
		setTextureName(EncampmentMod.getTextureName("crate"));
		setUnlocalizedName(NAME);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		dropInventoryNear(world, x, y, z);
		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new CrateTileEntity();
	}

	private void dropInventoryNear(World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (!(tileEntity instanceof IInventory)) {
			return;
		}

		IInventory inventory = (IInventory)tileEntity;

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			dropItemStackNear(stack, world, x, y, z);
		}
	}

	private void dropItemStackNear(ItemStack stack, World world, int x, int y, int z) {
		if (stack == null || stack.stackSize == 0) {
			return;
		}

		float rx = random.nextFloat() * 0.8F + 0.1F;
		float ry = random.nextFloat() * 0.8F + 0.1F;
		float rz = random.nextFloat() * 0.8F + 0.1F;

		EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz,
				new ItemStack(stack.itemID, stack.stackSize, stack.getItemDamage()));

		if (stack.hasTagCompound()) {
			entityItem.getEntityItem().setTagCompound(stack.getTagCompound());
		}

		float factor = 0.5F;

		entityItem.motionX = random.nextGaussian() * factor;
		entityItem.motionY = random.nextGaussian() * factor + 0.2F;
		entityItem.motionZ = random.nextGaussian() * factor;

		world.spawnEntityInWorld(entityItem);

		stack.stackSize = 0;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float par7, float par8, float par9) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity == null || player.isSneaking()) {
			return false;
		}

		player.openGui(EncampmentMod.instance, 0, world, x, y, z);

		return true;
	}

	protected static void register(EncampmentMod mod, Block block) {
		GameRegistry.registerBlock(block, NAME);
		GameRegistry.registerTileEntity(CrateTileEntity.class, "com.stratomine.encampment.CrateTileEntity");
		LanguageRegistry.addName(block, "Crate");
		MinecraftForge.setBlockHarvestLevel(block, "axe", 0);
		NetworkRegistry.instance().registerGuiHandler(mod, new CrateGuiHandler());
	}

}
