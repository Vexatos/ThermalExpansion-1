package cofh.thermalexpansion.plugins.jei.transposer;

import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.StringHelper;
import cofh.thermalexpansion.block.machine.TileTransposer;
import cofh.thermalexpansion.plugins.jei.Drawables;
import cofh.thermalexpansion.plugins.jei.JEIPluginTE;
import cofh.thermalexpansion.plugins.jei.RecipeUidsTE;
import cofh.thermalexpansion.util.crafting.TransposerManager.ComparableItemStackTransposer;
import cofh.thermalexpansion.util.crafting.TransposerManager.RecipeTransposer;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransposerRecipeWrapper extends BlankRecipeWrapper {

	/* Recipe */
	final List<List<ItemStack>> inputs;
	final List<List<FluidStack>> inputFluids;
	final List<ItemStack> outputs;
	final List<FluidStack> outputFluids;

	final int energy;
	final String uId;

	/* Animation */
	final IDrawableStatic drop;
	final IDrawableAnimated fluid;
	final IDrawableAnimated progress;
	final IDrawableAnimated speed;
	final IDrawableAnimated energyMeter;

	public TransposerRecipeWrapper(IGuiHelper guiHelper, RecipeTransposer recipe, String uIdIn) {

		uId = uIdIn;

		List<ItemStack> recipeInputs = new ArrayList<>();

		if (ComparableItemStackTransposer.getOreID(recipe.getInput()) != -1) {
			for (ItemStack ore : OreDictionary.getOres(ItemHelper.getOreName(recipe.getInput()))) {
				recipeInputs.add(ItemHelper.cloneStack(ore, recipe.getInput().stackSize));
			}
		} else {
			recipeInputs.add(recipe.getInput());
		}
		List<ItemStack> recipeOutputs = new ArrayList<>();
		recipeOutputs.add(recipe.getOutput());

		List<FluidStack> recipeFluids = new ArrayList<>();
		recipeFluids.add(recipe.getFluid());

		inputs = Collections.singletonList(recipeInputs);
		outputs = recipeOutputs;

		if (uId.equals(RecipeUidsTE.TRANSPOSER_FILL)) {
			inputFluids = Collections.singletonList(recipeFluids);
			outputFluids = Collections.emptyList();
		} else {
			inputFluids = Collections.emptyList();
			outputFluids = recipeFluids;
		}
		energy = recipe.getEnergy();

		if (uId.equals(RecipeUidsTE.TRANSPOSER_FILL)) {
			IDrawableStatic fluidDrawable = Drawables.getDrawables(guiHelper).getProgressLeft(2);
			IDrawableStatic progressDrawable = Drawables.getDrawables(guiHelper).getProgressLeftFill(2);

			drop = Drawables.getDrawables(guiHelper).getProgressLeft(2);
			fluid = guiHelper.createAnimatedDrawable(fluidDrawable, energy / TileTransposer.basePower, StartDirection.RIGHT, true);
			progress = guiHelper.createAnimatedDrawable(progressDrawable, energy / TileTransposer.basePower, StartDirection.RIGHT, false);
		} else {
			IDrawableStatic fluidDrawable = Drawables.getDrawables(guiHelper).getProgress(2);
			IDrawableStatic progressDrawable = Drawables.getDrawables(guiHelper).getProgressFill(2);

			drop = Drawables.getDrawables(guiHelper).getProgress(2);
			fluid = guiHelper.createAnimatedDrawable(fluidDrawable, energy / TileTransposer.basePower, StartDirection.LEFT, true);
			progress = guiHelper.createAnimatedDrawable(progressDrawable, energy / TileTransposer.basePower, StartDirection.LEFT, false);
		}
		IDrawableStatic speedDrawable = Drawables.getDrawables(guiHelper).getSpeedFill(0);
		IDrawableStatic energyDrawable = Drawables.getDrawables(guiHelper).getEnergyFill();

		speed = guiHelper.createAnimatedDrawable(speedDrawable, 1000, StartDirection.TOP, true);
		energyMeter = guiHelper.createAnimatedDrawable(energyDrawable, 1000, StartDirection.TOP, true);
	}

	public String getUid() {

		return uId;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {

		ingredients.setInputLists(ItemStack.class, inputs);
		ingredients.setOutputs(ItemStack.class, outputs);

		if (uId.equals(RecipeUidsTE.TRANSPOSER_FILL)) {
			ingredients.setInputLists(FluidStack.class, inputFluids);
		} else {
			ingredients.setOutputs(FluidStack.class, outputFluids);
		}
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

		drop.draw(minecraft, 63, 11);

		if (uId.equals(RecipeUidsTE.TRANSPOSER_FILL)) {
			JEIPluginTE.drawFluid(63, 11, inputFluids.get(0).get(0), 24, 16);
		} else {
			JEIPluginTE.drawFluid(63, 11, outputFluids.get(0), 24, 16);
		}
		fluid.draw(minecraft, 63, 11);
		progress.draw(minecraft, 63, 11);
		speed.draw(minecraft, 68, 41);
		energyMeter.draw(minecraft, 2, 8);
	}

	@Nullable
	public List<String> getTooltipStrings(int mouseX, int mouseY) {

		List<String> tooltip = new ArrayList<>();

		if (mouseX > 2 && mouseX < 15 && mouseY > 8 && mouseY < 49) {
			tooltip.add(StringHelper.localize("info.cofh.energy") + ": " + energy + " RF");
		}
		return tooltip;
	}

}
