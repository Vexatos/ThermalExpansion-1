package cofh.thermalexpansion.plugins.jei.dynamos;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import javax.annotation.Nonnull;

public class DynamoFuelHandler implements IRecipeHandler<DynamoFuelWrapper> {

	@Nonnull
	@Override
	public Class<DynamoFuelWrapper> getRecipeClass() {

		return DynamoFuelWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {

		return null;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull DynamoFuelWrapper recipe) {

		return recipe.getUid();
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull DynamoFuelWrapper recipe) {

		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull DynamoFuelWrapper recipe) {

		return true;
	}

}
