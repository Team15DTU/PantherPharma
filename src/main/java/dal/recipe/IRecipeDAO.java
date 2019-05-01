package dal.recipe;

import dal.DALException;
import dto.recipe.IRecipeDTO;

import java.time.LocalDate;
import java.util.List;


/**
 * @author Rasmus Sander Larsen
 */
public interface IRecipeDAO {

    /*
    ---------------------- Public Methods -----------------------
     */

    /**
     * This methods is used for the first time creation of a new recipe AND
     * the creation of a "new" recipe, which is a modification of a previously used recipe.
     * @param recipeDTO
     * @return
     * @throws DALException
     */
    boolean createRecipe (IRecipeDTO recipeDTO) throws DALException;

    List<IRecipeDTO> getRecipeList () throws DALException;

    IRecipeDTO getRecipe (int recipeID) throws DALException;

    IRecipeDTO getRecipe (String recipeName) throws DALException;

    IRecipeDTO getRecipe (String recipeName, LocalDate date) throws DALException;

    /**
     * This method is used for when a recipe is "deleted". Which means setting it to "inactive".
     * @param updatedRecipe
     * @return
     * @throws DALException This methods throws a DALException.
     */
    int updateRecipe (IRecipeDTO updatedRecipe) throws DALException;


    

}
