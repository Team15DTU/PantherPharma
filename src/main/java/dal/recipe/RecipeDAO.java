package dal.recipe;

import dal.ConnectionHelper;
import dal.DALException;
import dal.Columns;
import dal.Tables;
import db.IConnPool;
import dto.rawMaterial.IRawMaterialDTO;
import dto.rawMaterial.RawMaterialDTO;
import dto.recipe.IRecipeDTO;
import dto.recipe.RecipeDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class RecipeDAO implements IRecipeDAO {

    /*
    -------------------------- Fields --------------------------
     */

    private IConnPool iConnPool;
    private ConnectionHelper connectionHelper;
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public RecipeDAO (IConnPool iConnPool) {
        this.iConnPool = iConnPool;
        connectionHelper = new ConnectionHelper(iConnPool);
    }
    
    /*
    ------------------------ Properties -------------------------
     */

    // region Properties


    // endregion
    
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

        int assignedRecipeID = -1 ;
        boolean isRecipeCreatedByValidUser = false;

        Connection c = iConnPool.getConn();

        // Query to see if CreatedByID is has a valid Role.
        String checkRoleQuery = String.format("SELECT * FROM %s WHERE %s = ?",
                Tables.farmaceut, Columns.farmaceut.farmaceut_id);

        // Query for insertion of userID and recipeID
        String createRecipeUserID = String.format("INSERT INTO %s (%s,%s) VALUES (?,?)",
                Tables.recipe_farmaceut, Columns.recipe_farmaceut.recipe_id, Columns.recipe_farmaceut.farmaceut_id);

        // Query for insertion of recipe information.
        String createRecipeQuery = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?,?,?)",
                Tables.recipe, Columns.recipe.name, Columns.recipe.storageTime, Columns.recipe.startDate);

        // Query for insertion of raw materials used in recipe.
        String createUsedRawMaterials = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?,?,?,?)",
                Tables.rawMaterial_recipe, Columns.rawMaterial_recipe.rawMaterial_id, Columns.rawMaterial_recipe.recipe_id, Columns.rawMaterial_recipe.active, Columns.rawMaterial_recipe.amount);

        try {
            c.setAutoCommit(false);

            // region Checks if recipe is created by a valid userRole
            PreparedStatement isValidPS = c.prepareStatement(checkRoleQuery);
            isValidPS.setInt(1,recipeDTO.getCreatedBy_ID());
            ResultSet isValidRS = isValidPS.executeQuery();

            while(isValidRS.next()){
                if (recipeDTO.getCreatedBy_ID() == isValidRS.getInt(Columns.farmaceut.farmaceut_id.toString())) {
                    isRecipeCreatedByValidUser = true;
                }
            }

            // endregion

            // region recipe IS valid
            if (isRecipeCreatedByValidUser) {

                // region Creation of recipe
                // name, storageTime, StartDate
                PreparedStatement createRecipePS = c.prepareStatement(createRecipeQuery, Statement.RETURN_GENERATED_KEYS);
                createRecipePS.setString(1, recipeDTO.getName());
                createRecipePS.setInt(2, recipeDTO.getStorageTime());
                createRecipePS.setDate(3, Date.valueOf(recipeDTO.getStartDate()));
                createRecipePS.executeUpdate();

                // endregion

                // region Gets the assigned recipe_id
                ResultSet generatedKeys = createRecipePS.getGeneratedKeys();

                if (generatedKeys.next()) {
                    assignedRecipeID = generatedKeys.getInt(1);
                }
                // endregion

                // region Creation of userID and recipeID connection.
                // "INSERT INTO &s (recipe_id, userID) VALUES (?,?)"
                PreparedStatement userAndRecipeIDPS = c.prepareStatement(createRecipeUserID);
                userAndRecipeIDPS.setInt(1, assignedRecipeID);
                userAndRecipeIDPS.setInt(2, recipeDTO.getCreatedBy_ID());

                userAndRecipeIDPS.executeUpdate();

                // endregion

                // region Creation of used RawMaterials
                // If the recipe is assigned a recipe_id then start creating the used raw materials.
                if (assignedRecipeID != -1) {
                    for (IRawMaterialDTO rawMaterialDTO : recipeDTO.getIngredients()) {

                        // rawMaterial_id, recipe_id, active, amount
                        PreparedStatement createUsedRMPS = c.prepareStatement(createUsedRawMaterials);
                        createUsedRMPS.setInt(1, rawMaterialDTO.getRawMaterialDTO_ID());
                        createUsedRMPS.setInt(2, assignedRecipeID);
                        createUsedRMPS.setBoolean(3, rawMaterialDTO.isActive());
                        createUsedRMPS.setDouble(4, rawMaterialDTO.getAmount());

                        createUsedRMPS.executeUpdate();
                    }
                }
                // endregion

            }
            // endregion

            // region recipe NOT valid
            else {
                System.out.println("Recipe is not created by valid user.");
            }
            // endregion

            c.commit();
            return true;
        } catch (SQLException e) {
            connectionHelper.catchSQLExceptionAndDoRollback(c, e, "RecipeDAO.createRecipe");
            return false;
        } finally {
            connectionHelper.finallyActionsForConnection(c, "RecipeDAO.createRecipe");
        }
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

        // IRecipe Object to return.
        IRecipeDTO recipeDTOToReturn = new RecipeDTO();

        Connection c = iConnPool.getConn();

        // Query to get recipe.
        String getRecipeQuery = String.format("SELECT %s, %s, %s, %s, %s, %s FROM recipe " +
                " LEFT JOIN %s ON %s = %s" +
                " WHERE recipe.recipe_id = ?",
                Tables.recipe+"."+Columns.recipe.recipe_id, Columns.recipe.name,Columns.recipe.storageTime,
                Columns.recipe.startDate, Tables.oldRecipe +"." + Columns.oldRecipe.oldRecipe_id,
                Tables.oldRecipe + "." + Columns.oldRecipe.endDate,
                Tables.oldRecipe, Tables.recipe + "." + Columns.recipe.recipe_id, Tables.oldRecipe +"."+Columns.oldRecipe.recipe_id,
                Tables.recipe + "." + Columns.recipe.recipe_id);

        // Query to get createdBy userID.
        String getCreatedByQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                Columns.recipe_farmaceut.farmaceut_id, Tables.recipe_farmaceut, Columns.recipe_farmaceut.recipe_id);

        String getRawMaterialsUsedQuery = String.format("SELECT * FROM %s" +
                " LEFT JOIN %s " +
                " ON %s = %s " +
                " WHERE %s = ? " +
                " UNION " +
                " SELECT * FROM %s " +
                " RIGHT JOIN %s " +
                " ON %s = %s " +
                " WHERE %s = ?",
                Tables.rawMaterial, Tables.rawMaterial_recipe,
                Tables.rawMaterial + "." + Columns.rawMaterial.rawMaterial_id,
                Tables.rawMaterial_recipe +"." +Columns.rawMaterial_recipe.rawMaterial_id,
                Tables.rawMaterial_recipe + "." +Columns.rawMaterial_recipe.recipe_id,
                Tables.rawMaterial, Tables.rawMaterial_recipe,
                Tables.rawMaterial + "." + Columns.rawMaterial.rawMaterial_id,
                Tables.rawMaterial_recipe + "." + Columns.rawMaterial_recipe.rawMaterial_id,
                Tables.rawMaterial_recipe + "." + Columns.rawMaterial_recipe.recipe_id);

        try {

            // region Creation of IRecipeDTO Object
            // Left join with recipe and oldRecipe with "WHERE recipe_id = ?"
            PreparedStatement getRecipePS = c.prepareStatement(getRecipeQuery);
            getRecipePS.setInt(1, recipeID);

            ResultSet recipeRS = getRecipePS.executeQuery();

            while (recipeRS.next()) {
                // Sets recipe_ID
                recipeDTOToReturn.setRecipe_ID(recipeRS.getInt(Columns.recipe.recipe_id.toString()));
                // Sets name
                recipeDTOToReturn.setName(recipeRS.getString(Columns.recipe.name.toString()));
                // Sets startDate
                recipeDTOToReturn.setStartDate(recipeRS.getDate(Columns.recipe.startDate.toString()).toLocalDate());
                // Sets StorageTime
                recipeDTOToReturn.setStorageTime(recipeRS.getInt(Columns.recipe.storageTime.toString()));
                // Sets EndDate
                try {
                    recipeDTOToReturn.setEndDate(recipeRS.getDate(Columns.oldRecipe.endDate.toString()).toLocalDate());
                } catch (NullPointerException e) {
                    // default for not initialized
                    recipeDTOToReturn.setEndDate(null);
                }
                // Sets OldRecipe_ID
                try{
                    recipeDTOToReturn.setOldRecipe_ID(recipeRS.getInt(Columns.oldRecipe.oldRecipe_id.toString()));
                } catch (NullPointerException e) {
                    // default for not initialized
                    recipeDTOToReturn.setOldRecipe_ID(-1);
                }
            }

            // region Getting and sets the CreatedByID

            // "SELECT %s FROM %s WHERE recipe_id = ?
            PreparedStatement createdByPS = c.prepareStatement(getCreatedByQuery);
            createdByPS.setInt(1,recipeID);

            ResultSet createdByRS = createdByPS.executeQuery();

            while (createdByRS.next()) {
                recipeDTOToReturn.setCreatedBy_ID(createdByRS.getInt(Columns.recipe_farmaceut.farmaceut_id.toString()));
            }
            // endregion

            // endregion

            // region Creation of IRawMaterials used in recipe and adding them to the IRecipeDTO object.
            // Union With Left and Right Join with 2 times "WHERE recipe_id = ?"
            PreparedStatement rawMaterialUsedPS = c.prepareStatement(getRawMaterialsUsedQuery);
            rawMaterialUsedPS.setInt(1,recipeID);
            rawMaterialUsedPS.setInt(2,recipeID);

            ResultSet rmRS = rawMaterialUsedPS.executeQuery();

            while (rmRS.next()) {
                IRawMaterialDTO rmUsed = new RawMaterialDTO();
                rmUsed.setRecipe_id(recipeID);
                rmUsed.setRawMaterialDTO_ID(rmRS.getInt(Columns.rawMaterial_recipe.rawMaterial_id.toString()));
                rmUsed.setName(rmRS.getString(Columns.rawMaterial.name.toString()));
                rmUsed.setStdDeviation(rmRS.getDouble(Columns.rawMaterial.stdDeviation.toString()));
                rmUsed.setUsed(true);
                rmUsed.setActive(rmRS.getBoolean(Columns.rawMaterial_recipe.active.toString()));
                rmUsed.setAmount(rmRS.getDouble(Columns.rawMaterial_recipe.amount.toString()));
                recipeDTOToReturn.addRawMaterial(rmUsed);
            }

            // endregion

        } catch (SQLException e) {
            System.out.println("Exception in RecipeDAO.getRecipe:");
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        return recipeDTOToReturn;
    }

    /**
     * This method returns a List object containing RecipeDTO objects matching all the recipes in the DB.
     *
     * @return a List<IRecipeDTO> with all recipes.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public List<IRecipeDTO> getRecipeList() throws DALException {

        // List for ALL recipes in DB.
        List<IRecipeDTO> recipeDTOList = new ArrayList<>();

        Connection c = iConnPool.getConn();

        // Query to get ALL recipe_ID's in
        String getAllRecipeIDQuery = String.format("SELECT %s FROM %s UNION SELECT %s FROM %s",
                Columns.recipe.recipe_id,Tables.recipe, Columns.recipe.recipe_id, Tables.oldRecipe );

        try {
            // Finds all recipeIDs, both active and old.
            Statement statement = c.createStatement();
            ResultSet allRecipeIDs = statement.executeQuery(getAllRecipeIDQuery);

            // Create recipe and adds them to the list
            while (allRecipeIDs.next()) {
                int recipeID = allRecipeIDs.getInt(Columns.recipe.recipe_id.toString());
                recipeDTOList.add(getRecipe(recipeID));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }
        return recipeDTOList;
    }

    /**
     * This method gets a List of IRecipeDTO object containing the information
     * matching the inputted recipeName.
     *
     * @param recipeName is the recipeName we are looking for.
     * @return a List<IRecipeDTO> object containing objects matching the inputted recipeName.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public List<IRecipeDTO> getRecipeList(String recipeName) throws DALException {

        // List to return.
        List<IRecipeDTO> recipeDTOList = new ArrayList<>();

        Connection c = iConnPool.getConn();

        String getRecipeIDFromRecipeNameQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                Columns.recipe.recipe_id, Tables.recipe, Columns.recipe.name);

        try {
            PreparedStatement recipeIdFromRecipeNamePS = c.prepareStatement(getRecipeIDFromRecipeNameQuery);
            recipeIdFromRecipeNamePS.setString(1, recipeName);

            ResultSet recipeIdFromRecipeNameRS = recipeIdFromRecipeNamePS.executeQuery();

            while (recipeIdFromRecipeNameRS.next()) {
                int recipeID = recipeIdFromRecipeNameRS.getInt(Columns.recipe.recipe_id.toString());
                recipeDTOList.add(getRecipe(recipeID));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        return recipeDTOList;
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

        IRecipeDTO recipeDTO = new RecipeDTO();

        Connection c = iConnPool.getConn();

        // Query for getting the recipe that was in use on a given date.
        String recipeOnDate = String.format("SELECT %s AS %s, %s AS %s, %s AS %s, %s AS %s FROM %s " +
                " LEFT JOIN %s" +
                " ON %s = %s" +
                " WHERE %s = ? AND %s <= ? AND %s >= ?",
                Tables.recipe + "." + Columns.recipe.recipe_id, "recipe_id",
                Tables.recipe + "." + Columns.recipe.name, "recipeName",
                Tables.recipe + "." + Columns.recipe.startDate , "startDate",
                Tables.oldRecipe + "." + Columns.oldRecipe.endDate, "endDate",
                Tables.recipe, Tables.oldRecipe,
                Tables.recipe + "." + Columns.recipe.recipe_id, Tables.oldRecipe + "." + Columns.oldRecipe.recipe_id,
                Tables.recipe + "." + Columns.recipe.name, Tables.recipe + "." + Columns.recipe.startDate,
                Tables.oldRecipe + "." + Columns.oldRecipe.endDate);


        try {
            PreparedStatement recipeOneDatePS = c.prepareStatement(recipeOnDate);
            recipeOneDatePS.setString(1, recipeName);
            recipeOneDatePS.setDate(2, Date.valueOf(date));
            recipeOneDatePS.setDate(3, Date.valueOf(date));

            // Gets recipe_id and gets it.
            ResultSet recipeIdOnDateRS = recipeOneDatePS.executeQuery();
            while (recipeIdOnDateRS.next()) {
                int recipe_id = recipeIdOnDateRS.getInt("recipe_id");
                recipeDTO = getRecipe(recipe_id);
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }
        return recipeDTO;
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

        Connection c = iConnPool.getConn();
        return 0;
    }

    /*
    ---------------------- Support Methods ----------------------
     */
}
