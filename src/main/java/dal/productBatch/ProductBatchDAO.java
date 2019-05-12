package dal.productBatch;

import dal.*;
import db.IConnPool;
import dto.productBatch.IProductBatchDTO;
import dto.productBatch.ProductBatchDTO;
import dto.productBatch.ProductBatchStatus_Enum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class ProductBatchDAO implements IProductBatchDAO {


    /*
    -------------------------- Fields --------------------------
     */
    private IConnPool iConnPool;
    private ConnectionHelper connectionHelper;
    
    /*
    ----------------------- Constructor -------------------------
     */

    public ProductBatchDAO(IConnPool iConnPool){
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
     * This method creates a productBatch in the DB from the information in the inputted Object.
     *
     * @param productBatchDTO contains the information for the productBatch that is created.
     * @return "true" if the method goes through and "false" if something fails.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public boolean createProductBatch(IProductBatchDTO productBatchDTO) throws DALException {

        int assigned_id = -1;

        Connection c = iConnPool.getConn();

        // Query of insertion of  information into productBatch
        String insertQuery = String.format("INSERT INTO %s (%s,%s) VALUES (?,?)",
                Tables.productBatch, Columns.productBatch.amount, Columns.productBatch.status_id);

        // Query for insertion of information into productBatch_recipe
        String connectionQuery = String.format("INSERT INTO %s (%s,%s) VALUES (?,?)",
                Tables.productBatch_recipe,Columns.productBatch_recipe.productBatch_id, Columns.productBatch_recipe.recipe_id);

        try {
            c.setAutoCommit(false);

            // region Creation of productBatch
            PreparedStatement insertPS = c.prepareStatement(insertQuery,Statement.RETURN_GENERATED_KEYS);
            // amount
            insertPS.setDouble(1,productBatchDTO.getAmount());
            // status_id
            insertPS.setInt(2,productBatchDTO.getStatus().getStatus_id());
            insertPS.executeUpdate();
            // endregion

            // region Get assigned ID

            ResultSet generatedKeys = insertPS.getGeneratedKeys();

            while (generatedKeys.next()) {
                assigned_id = generatedKeys.getInt(1);
            }

            // endregion

            // region Creation of Connection table between ProductBatch and Recipe.
            PreparedStatement connectionPS = c.prepareStatement(connectionQuery);
            // productBatch_id
            connectionPS.setInt(1,assigned_id);
            // recipe_id
            connectionPS.setInt(2,productBatchDTO.getRecipeID());
            connectionPS.executeUpdate();
            // endregion

            c.commit();
            return true;
        } catch (SQLException e) {
            connectionHelper.catchSQLExceptionAndDoRollback(c,e, "ProductBatchDAO.createProductBatch");
            return false;
        }finally {
            connectionHelper.finallyActionsForConnection(c,"ProductBatchDAO.createProductBatch");
        }
    }

    /**
     * This method gets a IProductBathDTO object containing the information matching the inputted productBatchID.
     *
     * @param productBatchID is the ID for the product batch the method should return.
     * @return a IProductBatchDTO containing information matching the inputted ID.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public IProductBatchDTO getProductBatch(int productBatchID) throws DALException {

        IProductBatchDTO productBatchDTO = new ProductBatchDTO();

        Connection c = iConnPool.getConn();

        String getQuery = String.format("SELECT %s AS %s,%s AS %s,%s AS %s,%s AS %s FROM %s" +
                        " LEFT JOIN %s" +
                        " ON %s = %s " +
                        " WHERE %s = ?",
                Tables.productBatch + "." + Columns.productBatch.productBatch_id,
                Columns.productBatch.productBatch_id,
                Tables.productBatch + "." + Columns.productBatch.amount,
                Columns.productBatch.amount,
                Tables.productBatch + "." + Columns.productBatch.status_id,
                Columns.productBatch.status_id,
                Tables.productBatch_recipe + "." + Columns.productBatch_recipe.recipe_id,
                Columns.productBatch_recipe.recipe_id,
                Tables.productBatch,Tables.productBatch_recipe,
                Tables.productBatch + "." + Columns.productBatch.productBatch_id,
                Tables.productBatch_recipe + "." + Columns.productBatch_recipe.productBatch_id,
                Tables.productBatch + "." +Columns.productBatch.productBatch_id);

        try {
            PreparedStatement preparedStatement = c.prepareStatement(getQuery);
            preparedStatement.setInt(1, productBatchID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                productBatchDTO.setProductBatchID(resultSet.getInt(Columns.productBatch.productBatch_id.toString()));
                productBatchDTO.setRecipeID(resultSet.getInt(Columns.productBatch_recipe.recipe_id.toString()));
                productBatchDTO.setAmount(resultSet.getInt(Columns.productBatch.amount.toString()));
                int status_id =resultSet.getInt(Columns.productBatch.status_id.toString());
                // turns status_id into ProductBatchStatusEnum.
                productBatchDTO.setStatus(getPBStatusEnumFromInt(status_id));
            }

        } catch (SQLException e){
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        return productBatchDTO;
    }

    /**
     * This method a List Object containing ALL productBatches in the DB.
     *
     * @return a List<IProductBatchDTO> containing ALL productBatches in DB.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public List<IProductBatchDTO> getProductBatchList() throws DALException {

        List<IProductBatchDTO> productBatchListToReturn = new ArrayList<>();

        Connection c = iConnPool.getConn();

        String getProductBatchIDsQuery = String.format("SELECT %s FROM %s ",
                Columns.productBatch.productBatch_id, Tables.productBatch);

        try {
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery(getProductBatchIDsQuery);

            while (resultSet.next()) {
                int productBatchID = resultSet.getInt(Columns.productBatch.productBatch_id.toString());
                productBatchListToReturn.add(getProductBatch(productBatchID));
            }

        } catch (SQLException e ) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }
        return productBatchListToReturn;
    }

    /**
     * This method updates an existing product batch with the information in the inputted Object.
     *
     * @param productBatchDTO contains the information that the product batch is updated with.
     * @return a number corresponding to the number of recipe that is changed as a result of the update.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public int updateProductBatch(IProductBatchDTO productBatchDTO) throws DALException {

        Connection c = iConnPool.getConn();

        String updateQuery = String.format("UPDATE %s SET %s = ? WHERE %s = ?",
                Tables.productBatch, Columns.productBatch.status_id, Columns.productBatch.productBatch_id);

        try {
            c.setAutoCommit(false);
            PreparedStatement updatePS = c.prepareStatement(updateQuery);
            updatePS.setInt(1,productBatchDTO.getStatus().getStatus_id());
            updatePS.setInt(2,productBatchDTO.getProductBatchID());

            updatePS.executeUpdate();

            c.commit();
        } catch (SQLException e ) {
            connectionHelper.catchSQLExceptionAndDoRollback(c,e,"ProductBatchDAO.updateProductBatch");
        } finally {
            connectionHelper.finallyActionsForConnection(c,"ProductBatchDAO.updateProductBatch");
        }
        return 0;
    }

    /*
    ---------------------- Support Methods ----------------------
     */

    private ProductBatchStatus_Enum getPBStatusEnumFromInt (int status_id) {
        ProductBatchStatus_Enum toReturn;
        switch (status_id) {
            case 1:
                toReturn = ProductBatchStatus_Enum.ordred;
                break;
            case 2:
                toReturn= ProductBatchStatus_Enum.underProduction;
                break;
            case 3:
                toReturn = ProductBatchStatus_Enum.done;
                break;
            default:
                System.out.println("ProductBatch Status from ID doesn't exist");
                toReturn=null;
                break;
        }
        return toReturn;
    }


}
