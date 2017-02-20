package cofh.thermalexpansion.item;

import cofh.api.core.IInitializer;
import cofh.core.item.ItemMulti;
import cofh.thermalexpansion.ThermalExpansion;
import cofh.thermalfoundation.block.BlockGlass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static cofh.lib.util.helpers.ItemHelper.ShapedRecipe;
import static cofh.lib.util.helpers.ItemHelper.addRecipe;

public class ItemFrame extends ItemMulti implements IInitializer {

	public ItemFrame() {

		super("thermalexpansion");

		setUnlocalizedName("frame");
		setCreativeTab(ThermalExpansion.tabItems);
	}


	@SideOnly (Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {

	}

	@Override
	public boolean isFull3D() {

		return true;
	}

	/* IInitializer */
	@Override
	public boolean preInit() {

		frameMachine = addItem(0, "frameMachine");
		frameAutomaton = addItem(32, "frameAutomaton");
		frameDevice = addItem(64, "frameDevice");
		frameCell = addItem(128, "frameCell");
		frameLight = addItem(160, "frameLight");

		return true;
	}

	@Override
	public boolean initialize() {

		return true;
	}

	@Override
	public boolean postInit() {

		addRecipe(ShapedRecipe(frameMachine,
				"IGI",
				"GCG",
				"IGI",
				'C', "gearTin",
				'G', "blockGlass",
				'I', "ingotIron"
		));
		addRecipe(ShapedRecipe(frameAutomaton,
				"IGI",
				"GCG",
				"IGI",
				'C', "gearBronze",
				'G', "blockGlass",
				'I', "ingotIron"
		));
		addRecipe(ShapedRecipe(frameDevice,
				"IGI",
				"GCG",
				"IGI",
				'C', "gearCopper",
				'G', "blockGlass",
				'I', "ingotIron"
		));
		addRecipe(ShapedRecipe(frameCell,
				"IGI",
				"GCG",
				"IGI",
				'C', "gearLead",
				'G', "blockGlass",
				'I', "ingotIron"
		));
		addRecipe(ShapedRecipe(frameLight,
				" Q ",
				"G G",
				" I ",
				'G', BlockGlass.glassLead,
				'I', "ingotSignalum",
				'Q', "gemQuartz"
		));

		return true;
	}

	/* REFERENCES */
	public static ItemStack frameMachine;
	public static ItemStack frameAutomaton;
	public static ItemStack frameDevice;
	public static ItemStack frameCell;
	public static ItemStack frameLight;

}