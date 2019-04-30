package dal.recipe;

import dal.DALException;
import dto.recipe.IRecipeDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class RecipeDAO implements IRecipeDAO {

    /*
    -------------------------- Fields --------------------------
     */
    
    
    
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

    @Override
    public boolean createRecipe(IRecipeDTO recipeDTO) throws DALException {
        return false;
    }

    @Override
    public List<IRecipeDTO> getRecipeList() throws DALException {
        return null;
    }

    @Override
    public IRecipeDTO getRecipe(int recipeID) throws DALException {
        return null;
    }

    @Override
    public IRecipeDTO getRecipe(String recipeName) throws DALException {
        return null;
    }

    @Override
    public IRecipeDTO getRecipe(String recipeName, LocalDate date) throws DALException {
        return null;
    }

    @Override
    public int updateRecipe(IRecipeDTO updatedRecipe) throws DALException {
        return 0;
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
