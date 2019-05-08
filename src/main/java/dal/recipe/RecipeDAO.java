package dal.recipe;

import dal.DALException;
import dto.recipe.IRecipeDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class RecipeDAO implements IRecipeDAO {

    public enum columns {
        recipe_id, name, storageTime, startDate
    }

    /*
    -------------------------- Fields --------------------------
     */

    private final String TABLE_NAME = "recipe";
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    /**
     * This methods is used for the first time creation of a new recipe AND
     * the creation of a "new" recipe, which is a modification of a previously used recipe.
     *
     * @param recipeDTO contains the information about the recipe to create.
     * @return "true" if the creation went through and "false" if something fail.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public boolean createRecipe(IRecipeDTO recipeDTO) throws DALException {
        return false;
    }

    /**
     * This method returns a List object containing RecipeDTO objects matching all the recipes in the DB.
     *
     * @return a List<IRecipeDTO> with all recipes.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public List<IRecipeDTO> getRecipeList() throws DALException {
        return null;
    }

    /**
     * This method gets a specific IRecipeDTO object containing the information
     * matching the recipe with the inputted recipeID.
     *
     * @param recipeID is the recipeID we are looking for.
     * @return a IRecipeDTO object with data matching the inputted recipeID.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public IRecipeDTO getRecipe(int recipeID) throws DALException {
        return null;
    }

    /**
     * This method gets a specific IRecipeDTO object containing the information
     * matching the recipe with the inputted recipeName.
     *
     * @param recipeName is the recipeName we are looking for.
     * @return a IRecipeDTO object with data matching the inputted recipeName.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public IRecipeDTO getRecipe(String recipeName) throws DALException {
        return null;
    }

    /**
     * This method gets a specific IRecipeDTO object containing the information
     * matching the recipe with the inputted recipeName AND that was in use at the inputted date.
     *
     * @param recipeName is the recipeName we are looking for.
     * @param date       should be between the start and end date for the recipe that is returned.
     * @return a IRecipeDTO object with the data matching the recipeName and the usageDate.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public IRecipeDTO getRecipe(String recipeName, LocalDate date) throws DALException {
        return null;
    }

    /**
     * This method is used for when a recipe is "deleted". Which means setting it to "inactive".
     *
     * @param updatedRecipe is the IRecipeDTO object that is moved to the "inactive" section.
     * @return a int corresponding with the number of rows changed as a result of the method.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public int updateRecipe(IRecipeDTO updatedRecipe) throws DALException {
        return 0;
    }

    /*
    ---------------------- Support Methods ----------------------
     */


}
