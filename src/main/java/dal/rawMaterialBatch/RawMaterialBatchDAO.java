package dal.rawMaterialBatch;

import dal.Columns;
import dal.ConnectionHelper;
import dal.DALException;
import dal.Tables;
import db.IConnPool;
import dto.rawMaterialBatch.IRawMaterialBatchDTO;
import dto.rawMaterialBatch.RawMaterialBatchDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class RawMaterialBatchDAO implements IRawMaterialBatchDAO {

    /*
    -------------------------- Fields --------------------------
     */
    private IConnPool iConnPool;
    private ConnectionHelper connectionHelper;
    
    /*
    ----------------------- Constructor -------------------------
     */

    public RawMaterialBatchDAO (IConnPool iConnPool) {
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
     * This method creates a new raw material batch in the DB from the information in the inputted object.
     *
     * @param rawMaterialBatchDTO contains the information for the raw material batch that is created.
     * @return "true" if the method goes through and "false" if something fails.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public boolean createRawMaterialBatch(IRawMaterialBatchDTO rawMaterialBatchDTO) throws DALException {

        int assignedRMB_ID = -1;

        Connection c = iConnPool.getConn();

        // Query for insertion of rawMaterialBatch information into rawMaterialBatch table.
        String insertQuery = String.format("INSERT INTO %s (%s, %s) VALUES (?,?)",
                Tables.rawMaterialBatch, Columns.rawMaterialBatch.isResidue, Columns.rawMaterialBatch.amount);

        // Query for insertion of rawMaterialBatch_id and rawMaterial_id in connection table.
        String connectionQuery = String.format("INSERT INTO %s (%s, %s) VALUES (?,?)",
                Tables.rawMaterialBatch_rawMaterial, Columns.rawMaterialBatch_rawMaterial.rawMaterialBatch_id,
                Columns.rawMaterialBatch_rawMaterial.rawMaterial_id);

        try {
            c.setAutoCommit(false);

            // region Creation of RawMaterialBatch
            PreparedStatement insertPS = c.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            insertPS.setBoolean(1, rawMaterialBatchDTO.isResidue());
            insertPS.setDouble(2,rawMaterialBatchDTO.getAmount());

            insertPS.executeUpdate();

            // endregion

            // region Getting created RawMaterialBatch_id
            ResultSet insertGeneratedKeys = insertPS.getGeneratedKeys();

            while (insertGeneratedKeys.next()) {
                assignedRMB_ID = insertGeneratedKeys.getInt(1);
            }
            // endregion

            // region Creation of connection between RawMaterialBatch and RawMaterial
            PreparedStatement connectionPS = c.prepareStatement(connectionQuery);
            connectionPS.setInt(1,assignedRMB_ID);
            connectionPS.setInt(2,rawMaterialBatchDTO.getRawMaterialID());

            connectionPS.executeUpdate();
            // endregion

            c.commit();

            return true;

        } catch (SQLException e) {
            connectionHelper.catchSQLExceptionAndDoRollback(c,e,"RawMaterialBatch.createRawMaterialBatch");
        } finally {
            connectionHelper.finallyActionsForConnection(c,"RawMaterialBatch.createRawMaterialBatch");
        }
        return false;
    }

    /**
     * This method gets IRawMaterialBatchDTO object from the inputted ID.
     *
     * @param rawMaterialBatchID is the ID for the raw material batch the method should return.
     * @return a IRawMaterialBatchDTO containing the information matching the inputted ID.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public IRawMaterialBatchDTO getRawMaterialBatch(int rawMaterialBatchID) throws DALException {

        IRawMaterialBatchDTO rmbToReturn = new RawMaterialBatchDTO();

        Connection c = iConnPool.getConn();

        String getRMBQuery = String.format("SELECT %s, %s, %s, %s FROM %s " +
                        "LEFT JOIN %s " +
                        "ON %s = %s " +
                        "WHERE %s = ?",
                Tables.rawMaterialBatch+"."+Columns.rawMaterialBatch.rawMaterialBatch_id,
                Tables.rawMaterialBatch_rawMaterial+"."+Columns.rawMaterialBatch_rawMaterial.rawMaterial_id,
                Tables.rawMaterialBatch+"."+Columns.rawMaterialBatch.amount,
                Tables.rawMaterialBatch+"."+Columns.rawMaterialBatch.isResidue,
                Tables.rawMaterialBatch, Tables.rawMaterialBatch_rawMaterial,
                Tables.rawMaterialBatch +"." +Columns.rawMaterialBatch.rawMaterialBatch_id,
                Tables.rawMaterialBatch_rawMaterial+"."+Columns.rawMaterialBatch_rawMaterial.rawMaterialBatch_id,
                Tables.rawMaterialBatch+"."+Columns.rawMaterialBatch.rawMaterialBatch_id);

        try {

            // region Getting rawMaterialBatch info from rawMaterialBatch and rawMaterialBatch_rawMaterial table.
            PreparedStatement getRMBPS = c.prepareStatement(getRMBQuery);
            getRMBPS.setInt(1, rawMaterialBatchID);

            ResultSet getRMBRS = getRMBPS.executeQuery();

            while(getRMBRS.next()){
                rmbToReturn.setRawMaterialBatchID(getRMBRS.getInt(Columns.rawMaterialBatch.rawMaterialBatch_id.toString()));
                rmbToReturn.setRawMaterialID(getRMBRS.getInt(Columns.rawMaterialBatch_rawMaterial.rawMaterial_id.toString()));
                rmbToReturn.setAmount(getRMBRS.getDouble(Columns.rawMaterialBatch.amount.toString()));
                rmbToReturn.setResidue(getRMBRS.getBoolean(Columns.rawMaterialBatch.isResidue.toString()));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }
        return rmbToReturn;
    }

    /**
     * This method returns a List object containing IRawMaterialBatchDTO objects of ALL raw material batches in the DB.
     *
     * @return a List<IRawMaterialDTO> of all raw material batches in the DB.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public List<IRawMaterialBatchDTO> getRawMaterialBatchList() throws DALException {

        List<IRawMaterialBatchDTO> RMBList = new ArrayList<>();

        Connection c = iConnPool.getConn();

        String getAllRMBIDs = String.format("SELECT %s FROM %s",
                Columns.rawMaterialBatch_rawMaterial.rawMaterialBatch_id, Tables.rawMaterialBatch_rawMaterial);

        try {
            Statement getRMBIDsPS = c.createStatement();
            ResultSet resultSet = getRMBIDsPS.executeQuery(getAllRMBIDs);

            // Finds all RawMaterialBatchIDs and gets a RawMaterialBatch object matching that ID and adds to list.
            while (resultSet.next()) {
                int rmbID = resultSet.getInt(Columns.rawMaterialBatch.rawMaterialBatch_id.toString());
                RMBList.add(getRawMaterialBatch(rmbID));
            }
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }
        return RMBList;
    }

    /**
     * This method updates an existing raw material batch with the information in the inputted Object.
     *
     * @param rawMaterialBatchDTO contains the information that the raw material batch is updated with.
     * @return a number corresponding to the number of rows that is changed as a result of the update.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public int updateRawMaterialBatch(IRawMaterialBatchDTO rawMaterialBatchDTO) throws DALException {

        int changesFromUpdate = 0;

        Connection c = iConnPool.getConn();

        String updateQuery = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?",
                Tables.rawMaterialBatch, Columns.rawMaterialBatch.isResidue, Columns.rawMaterialBatch.amount,
                Columns.rawMaterialBatch.rawMaterialBatch_id);

        try {
            c.setAutoCommit(false);

            PreparedStatement updatePS = c.prepareStatement(updateQuery);
            updatePS.setBoolean(1,rawMaterialBatchDTO.isResidue());
            updatePS.setDouble(2, rawMaterialBatchDTO.getAmount());
            updatePS.setInt(3, rawMaterialBatchDTO.getRawMaterialBatchID());

            changesFromUpdate=updatePS.executeUpdate();

            c.commit();

        } catch (SQLException e){
            connectionHelper.catchSQLExceptionAndDoRollback(c,e,"RawMaterialBatchDAO.updateRawMaterialBatchDAO");
        } finally {
            connectionHelper.finallyActionsForConnection(c,"RawMaterialBatchDAO.updateRawMaterialBatchDAO");
        }
        return changesFromUpdate;
    }

    /*
    ---------------------- Support Methods ----------------------
     */


}
