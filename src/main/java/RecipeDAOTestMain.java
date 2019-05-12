import dal.DALException;
import dal.rawMaterial.IRawMaterialDAO;
import dal.rawMaterial.RawMaterialDAO;
import dal.recipe.IRecipeDAO;
import dal.recipe.RecipeDAO;
import db.IConnPool;
import db.MySQL_DB;
import dto.rawMaterial.IRawMaterialDTO;
import dto.rawMaterial.RawMaterialDTO;
import dto.recipe.IRecipeDTO;
import dto.recipe.RecipeDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class RecipeDAOTestMain  {

    public static void main (String[] args) throws DALException {

        // MAIN FOR IRecipeDAO.
        // HERE WE WILL SHOWCASE THE IMPLEMENTED FUNCTIONS!

        System.out.println("Main For IRecipeDAO: \n");

        // region ConnPoll and DAO
        IConnPool iConnPool = new MySQL_DB();

        IRawMaterialDAO rawMaterialDAO = new RawMaterialDAO(iConnPool);

        IRecipeDAO recipeDAO = new RecipeDAO(iConnPool);
        // endregion

        // Gets some rawMaterial object of rawMaterials that already exists in the BD

        IRawMaterialDTO bananer = rawMaterialDAO.getRawMaterial(2);
        IRawMaterialDTO sukker = rawMaterialDAO.getRawMaterial(3);
        IRawMaterialDTO mel = rawMaterialDAO.getRawMaterial(5);

        // The RawMaterials are assigned an amount of usage and active or inactive state
        bananer.setAmount(250.0);
        bananer.setActive(true);
        mel.setAmount(600.0);
        mel.setActive(true);
        sukker.setAmount(500.0);
        sukker.setUsed(true);

        // The RawMaterials a added the IRecipeObject
        IRecipeDTO banankage = new RecipeDTO("Banankage", LocalDate.parse("2000-02-02"), 365);
        banankage.addRawMaterial(mel);
        banankage.addRawMaterial(bananer);
        banankage.addRawMaterial(sukker);
        // CreatedBy_userID is set to an existing Laborant (CANT CREATE RECIPES)
        banankage.setCreatedBy_ID(26);

        System.out.println("Status of creation of recipe (as Laborant): " + recipeDAO.createRecipe(banankage));
        // Creation fails a the user who created the recipe wasn't a "Farmaceut".
        // Role is changed to an existing "FarmaCeut" and creation is retried.
        banankage.setCreatedBy_ID(29);
        System.out.println("\nStatus of creation of recipe as (\"Farmaceut\"): " + recipeDAO.createRecipe(banankage));

        // Next we get a recipe that is in the DB and prints it.

        System.out.println("\nGetted recipe with recipe_id = 23:");
        System.out.println(recipeDAO.getRecipe(23));

        // Next we get a List of all recipes in the DB and prints all recipes
        System.out.println("\nList of all recipes on the DB: ");
        for (IRecipeDTO recipeDTO : recipeDAO.getRecipeList()) {
            System.out.println(recipeDTO);
        }

        // Lastly we find the recipe that was active on a given date and print it.
        LocalDate searchDate =LocalDate.of(2018,5,11);
        String recipeNameWeAreLookingFor = "Banankage";
        System.out.println("\nRecipe that was used on a given date ("+ recipeNameWeAreLookingFor+" // "+searchDate+"):");
        System.out.println(recipeDAO.getRecipe(recipeNameWeAreLookingFor,searchDate));

    }
}
