package com.stratomine.encampment;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class CrateBlock extends BlockContainer {

	private static final CreativeTabs CREATIVE_TAB = CreativeTabs.tabDecorations;
	private static final float HARDNESS = 2.5F;
	private static final Material MATERIAL = Material.wood;
	private static final StepSound STEP_SOUND = Block.soundWoodFootstep;
	private static final String NAME = "com.stratomine.encampment.crate";

	protected CrateBlock(int id) {
		super(id, MATERIAL);

		setCreativeTab(CREATIVE_TAB);
		setHardness(HARDNESS);
		setStepSound(STEP_SOUND);
		setTextureName(EncampmentMod.getTextureName("crate"));
		setUnlocalizedName(NAME);
	}

	protected static void register(Block block) {
		GameRegistry.registerBlock(block, NAME);
		LanguageRegistry.addName(block, "Crate");
		MinecraftForge.setBlockHarvestLevel(block, "axe", 0);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}

}
