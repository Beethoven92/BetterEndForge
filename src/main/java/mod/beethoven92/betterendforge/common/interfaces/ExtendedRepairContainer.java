package mod.beethoven92.betterendforge.common.interfaces;

import java.util.List;

import mod.beethoven92.betterendforge.common.recipes.AnvilSmithingRecipe;

public interface ExtendedRepairContainer
{
	public void be_updateCurrentRecipe(AnvilSmithingRecipe recipe);
	public AnvilSmithingRecipe be_getCurrentRecipe();
	public List<AnvilSmithingRecipe> be_getRecipes();
	
	default void be_nextRecipe() 
	{
		List<AnvilSmithingRecipe> recipes = this.be_getRecipes();
		AnvilSmithingRecipe current = this.be_getCurrentRecipe();
		int i = recipes.indexOf(current) + 1;
		if (i >= recipes.size()) 
		{
			i = 0;
		}
		this.be_updateCurrentRecipe(recipes.get(i));
	}
	
	default void be_previousRecipe() 
	{
		List<AnvilSmithingRecipe> recipes = this.be_getRecipes();
		AnvilSmithingRecipe current = this.be_getCurrentRecipe();
		int i = recipes.indexOf(current) - 1;
		if (i <= 0) 
		{
			i = recipes.size() - 1;
		}
		this.be_updateCurrentRecipe(recipes.get(i));
	}
}
