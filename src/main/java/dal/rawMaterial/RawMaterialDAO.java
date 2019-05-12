package dal.rawMaterial;

import dal.Columns;
import dal.ConnectionHelper;
import dal.DALException;
import dal.Tables;
import db.IConnPool;
import dto.rawMaterial.IRawMaterialDTO;
import dto.rawMaterial.RawMaterialDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class RawMaterialDAO implements IRawMaterialDAO {

    /*
    -------------------------- Fields --------------------------
     */

    private IConnPool iConnPool;
    private ConnectionHelper connectionHelper;
    private final String TABLE_NAME = "rawMaterial";
    
    /*
    ----------------------- Constructor -------------------------
     */

    public RawMaterialDAO (IConnPool iConnPool) {
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
     * This method creates a raw material in the DB with the information from the inputted IRawMaterialDTO object.
     *
     * @param rawMaterialDTO contains the information that is used to creating the raw material.
     * @return "true" if the creation went through and "false" if something fails.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public boolean createRawMaterial(IRawMaterialDTO rawMaterialDTO) throws DALException {

        boolean creationPassed = false;

        Connection c = iConnPool.getConn();

        // Query for Insertion of raw material information.
        String createQuery = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?,?,?)",
                Tables.rawMaterial, Columns.rawMaterial.rawMaterial_id, Columns.rawMaterial.name, Columns.rawMaterial.stdDeviation);

        try {
            c.setAutoCommit(false);

            PreparedStatement ps = c.prepareStatement(createQuery);
            ps.setInt(1,rawMaterialDTO.getRawMaterialDTO_ID());
            ps.setString(2, rawMaterialDTO.getName());
            ps.setDouble(3,rawMaterialDTO.getStdDeviation());

            ps.executeUpdate();

            c.commit();

            creationPassed = true;

        } catch (SQLException e) {

            // Prints exceptions messages and does rollback.
            connectionHelper.catchSQLExceptionAndDoRollback(c,e, "RawMaterialDAO.createRawMaterial");

        } finally {
            // Sets AutoCommit to true and releases connections
            connectionHelper.finallyActionsForConnection(c, "RawMaterialDAO.createRawMaterial");
        }

        return creationPassed;
    }

    /**
     * This method gets a IRawMaterial object that contains the information matching the inputted
     *
     * @param rawMaterial_ID is the ID on the raw material the method should return.
     * @return a IRawMaterial object containing the information matching the inputted ID.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public IRawMaterialDTO getRawMaterial(int rawMaterial_ID) throws DALException {

        IRawMaterialDTO rawMaterialDTOToReturn = new RawMaterialDTO();

        Connection c = iConnPool.getConn();

        // Get query
        String getQuery = String.format("SELECT * FROM %s WHERE %s = ?",
                Tables.rawMaterial, Columns.rawMaterial.rawMaterial_id);

        try {
            PreparedStatement pStatement = c.prepareStatement(getQuery);
            pStatement.setInt(1, rawMaterial_ID);

            ResultSet rs = pStatement.executeQuery();

            // region Sets User with information from DB.
            while (rs.next()) {
                rawMaterialDTOToReturn.setRawMaterialDTO_ID(rs.getInt(Columns.rawMaterial.rawMaterial_id.toString()));
                rawMaterialDTOToReturn.setName(rs.getString(Columns.rawMaterial.name.toString()));
                rawMaterialDTOToReturn.setStdDeviation(rs.getDouble(Columns.rawMaterial.stdDeviation.toString()));
                rawMaterialDTOToReturn.setUsed(false);
            }
            // endregion

        } catch (SQLException e) {
            System.out.println("SQLException in RawMaterialDAO.getRawMaterial");
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        return rawMaterialDTOToReturn;
    }

    /**
     * This method returns a List object containing ALL raw materials in the DB.
     *
     * @return a List<IRawMaterialDTO> of ALL raw material.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public List<IRawMaterialDTO> getRawMaterialList() throws DALException {

        // List of IRawMaterials to return.
        List<IRawMaterialDTO> rawMaterialDTOList = new ArrayList<>();

        Connection c = iConnPool.getConn();

        // Query getting ALL rawMaterial_id's
        String rawMaterialIDQuery = String.format("SELECT %s FROM %s",
                Columns.rawMaterial.rawMaterial_id, Tables.rawMaterial);

        try {
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery(rawMaterialIDQuery);

            // Gets id, creates a RawMaterial object and adds it to List.
            while (rs.next()) {
                int rawMaterial_ID = rs.getInt(Columns.rawMaterial.rawMaterial_id.toString());
                rawMaterialDTOList.add(getRawMaterial(rawMaterial_ID));
            }

        }catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }
        return rawMaterialDTOList;
    }

    /**
     * This method updates an existing raw material with the information in the inputted IRawMaterialDTO object.
     *
     * @param rawMaterialDTO contains the information that the raw materials should be updated with.
     * @return a number corresponding to the number of recipe that is changed as a result of the update.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public int updateRawMaterial(IRawMaterialDTO rawMaterialDTO) throws DALException {

        int variableChanges = 0;

        Connection c = iConnPool.getConn();

        IRawMaterialDTO rawMaterialDTOBeforeUpdate = getRawMaterial(rawMaterialDTO.getRawMaterialDTO_ID());

        // Query for updating of rawMaterial name
        String nameUpdateQuery = String.format("UPDATE %s SET %s = ? WHERE %s = %s",
                Tables.rawMaterial, Columns.rawMaterial.name, Columns.rawMaterial.rawMaterial_id, rawMaterialDTO.getRawMaterialDTO_ID());

        // Query for updating of RawMaterial stdDeviation.
        String stdDeviationUpdateQuery = String.format("UPDATE %s SET %s = ? WHERE %s = %s",
                Tables.rawMaterial, Columns.rawMaterial.stdDeviation, Columns.rawMaterial.rawMaterial_id, rawMaterialDTO.getRawMaterialDTO_ID());

        try {
            c.setAutoCommit(false);

            PreparedStatement namePS = c.prepareStatement(nameUpdateQuery);
            PreparedStatement stdDeviationPS = c.prepareStatement(stdDeviationUpdateQuery);

            // region Updates Raw Material Name.
            if (!rawMaterialDTOBeforeUpdate.getName().equals(rawMaterialDTO.getName())){
                namePS.setString(1, rawMaterialDTO.getName());

                namePS.executeUpdate();
                variableChanges++;
            }
            // endregion

            // region Updates Raw Material stdDeviation
            if (rawMaterialDTO.getStdDeviation() != rawMaterialDTOBeforeUpdate.getStdDeviation()) {
                stdDeviationPS.setDouble(1, rawMaterialDTO.getStdDeviation());

                stdDeviationPS.executeUpdate();
                variableChanges++;
            }
            // endregion

            c.commit();

        } catch (SQLException e) {
            connectionHelper.catchSQLExceptionAndDoRollback(c, e, "RawMaterialDAO.updateRawMaterial");
        } finally {
            connectionHelper.finallyActionsForConnection(c, "RawMaterialDAO.updateRawMaterial");
        }

        return variableChanges;
    }

    /**
     * This method gets a List Object containing all the Raw Materials that needs to be reordered.
     * A RawMaterial needs to be reordered if the total amount of rawMaterialBatches of a certain rawMaterial,
     * isn't enough to make 2 times the recipe using the most of that particular rawMaterial.
     * @return a List<IRawMaterialDTO> of rawMaterials to reorder.
     * @throws DALException This method throws a DALException.
     */
    @Override
    public List<IRawMaterialDTO> getRawMaterialsToReorderList () throws DALException {
        // List for Raw Materials to reorder.
        List<IRawMaterialDTO> rmToReOrderList = new ArrayList<>();

        Connection c = iConnPool.getConn();

        // Finds the maximum amount used in a recipe for each RawMaterial.
        String rmMaxUse = "SELECT rmUse.rmID AS rmID, rmUse.rmName as rmName, MAX(rmUse.amount) AS maxAmount FROM " +
                " (SELECT rawMaterial.rawMaterial_id AS rmID, rawMaterial.name AS rmName, rawMaterial_recipe.amount AS amount FROM rawMaterial" +
                " LEFT JOIN rawMaterial_recipe" +
                " ON rawMaterial.rawMaterial_id = rawMaterial_recipe.rawMaterial_id ) rmUse" +
                " GROUP BY rmID";

        // Joins rawMaterialBatch and rawMaterialBatch_rawMaterial, so we know that rawMaterial each RawMaterialBatch corresponds to.
        String rmb_rm = "SELECT rawMaterialBatch.rawMaterialBatch_id AS rmbID, rawMaterialBatch_rawMaterial.rawMaterial_id AS rmID, rawMaterial.name AS name, rawMaterialBatch.amount AS rmbAmount FROM rawMaterialBatch " +
                " LEFT JOIN rawMaterialBatch_rawMaterial" +
                " ON rawMaterialBatch.rawMaterialBatch_id = rawMaterialBatch_rawMaterial.rawMaterialBatch_id" +
                " LEFT JOIN rawMaterial" +
                " ON rawMaterialBatch_rawMaterial.rawMaterial_id = rawMaterial.rawMaterial_id";

        // Finds the RawMaterialBatches with an amount >= than the maximum usage in a recipe of that corresponding rawMaterial.
        String rmb_rmAboveMax = "SELECT rmb_rm.rmID AS rmID, rmb_rm.name AS rmName, rmb_rm.rmbAmount AS rmbAmountAboveMaxUse, rmMaxUse.maxAmount AS rmMaxAmount FROM ( "+ rmb_rm +")rmb_rm" +
                " LEFT JOIN ("+ rmMaxUse +") rmMaxUse " +
                " ON rmMaxUse.rmID = rmb_rm.rmID" +
                " WHERE rmb_rm.rmbAmount >= rmMaxUse.maxAmount ";

        // Finds the sum of amounts of rawMaterialBatches that is >= than the maximum usage in a recipe of the correspoding rawMaterial.
        String sumAboveMax = "SELECT rmb_rmAboveMax.rmName AS rmName, rmb_rmAboveMax.rmID AS rmID, SUM(rmb_rmAboveMax.rmbAmountAboveMaxUse) AS sumPrRm,rmb_rmAboveMax.rmMaxAmount as rmMaxAmount FROM ("+rmb_rmAboveMax +") rmb_rmAboveMax " +
                " WHERE rmb_rmAboveMax.rmbAmountAboveMaxUse >= rmb_rmAboveMax.rmMaxAmount" +
                " GROUP BY rmb_rmAboveMax.rmID";

        // Finds the rawMaterials where the sum of corresponding RawMaterialBatches is smaller than 2*maximum usage.
        String reOrderList = "SELECT sumAboveMax.rmName AS rmName, sumAboveMax.rmID AS rmID, sumAboveMax.sumPrRm as sumPrRm FROM (" + sumAboveMax + ") sumAboveMax" +
                " WHERE sumAboveMax.sumPrRm < 2*sumAboveMax.rmMaxAmount";

        try {
            PreparedStatement rawMaterialsToReOrderPS = c.prepareStatement(reOrderList);
            ResultSet rawMaterialsToReOrderRS = rawMaterialsToReOrderPS.executeQuery();

            while (rawMaterialsToReOrderRS.next()) {
                int rawMaterial_id = rawMaterialsToReOrderRS.getInt("rmID");
                rmToReOrderList.add(getRawMaterial(rawMaterial_id));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

        return rmToReOrderList;
    }

    /*
    ---------------------- Support Methods ----------------------
     */
}
