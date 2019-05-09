package dal.rawMaterial;

import dal.ConnectionHelper;
import dal.DALException;
import dal.user.UserDAO;
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

    // Names on columns in the DB table: rawMaterial
    public enum columns {
        rawMaterial_id, stdDeviation, name
    }

    // Names on columns in the DB table: rawMaterial_recipe
    public enum rm_recipColumns {
        rawMaterial_id, recipe_id, active, amount

    }

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

    // <editor-folder desc="Properties"


    // </editor-folder>
    
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

        Connection c = iConnPool.getConn();

        String createQuery = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?,?,?)",
                TABLE_NAME, columns.rawMaterial_id, columns.name, columns.stdDeviation);

        try {
            c.setAutoCommit(false);

            PreparedStatement ps = c.prepareStatement(createQuery);
            ps.setInt(1,rawMaterialDTO.getRawMaterialDTO_ID());
            ps.setString(2, rawMaterialDTO.getName());
            ps.setDouble(3,rawMaterialDTO.getStdDeviation());

            ps.executeUpdate();

            c.commit();

        } catch (SQLException e) {
            // Prints exceptions messages and does rollback.
            connectionHelper.catchSQLExceptionAndDoRollback(c,e, "RawMaterialDAO.createRawMaterial");
        } finally {
            // Sets AutoCommit to true and releases connections
            connectionHelper.finallyActionsForConnection(c, "RawMaterialDAO.createRawMaterial");
        }

        return false;
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
                TABLE_NAME, columns.rawMaterial_id);

        try {
            PreparedStatement pStatement = c.prepareStatement(getQuery);
            pStatement.setInt(1, rawMaterial_ID);

            ResultSet rs = pStatement.executeQuery();

            // Sets User with information from DB.
            while (rs.next()) {
                rawMaterialDTOToReturn.setRawMaterialDTO_ID(rs.getInt(columns.rawMaterial_id.toString()));
                rawMaterialDTOToReturn.setName(rs.getString(columns.name.toString()));
                rawMaterialDTOToReturn.setStdDeviation(rs.getDouble(columns.stdDeviation.toString()));
            }

        } catch (SQLException e) {
            System.out.println("SQLException in RawMaterialDAO.getRawMaterial");
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        return rawMaterialDTOToReturn;
    }

    /**
     * This method gets a IRawMaterial object that contains the information matching the inputted
     *
     * @param rawMaterial_ID is the ID on the raw material the method should return.
     * @return a IRawMaterial object containing the information matching the inputted ID.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public IRawMaterialDTO getRawMaterialInRecipe(int rawMaterial_ID) throws DALException {

        IRawMaterialDTO rawMaterialDTOToReturn = new RawMaterialDTO();

        Connection c = iConnPool.getConn();

        String getRawMaterial_recipeQuery = String.format("SELECT * FROM %s WHERE %s = ?",
                TABLE_NAME+"_recipe",rm_recipColumns.rawMaterial_id);

        try {
            rawMaterialDTOToReturn = getRawMaterial(rawMaterial_ID);

            PreparedStatement rm_recipePS = c.prepareStatement(getRawMaterial_recipeQuery);
            rm_recipePS.setInt(1,rawMaterial_ID);

            ResultSet rs = rm_recipePS.executeQuery();

            while (rs.next()) {
                rawMaterialDTOToReturn.setUsed(true);
                rawMaterialDTOToReturn.setRecipe_id(rs.getInt(rm_recipColumns.recipe_id.toString()));
                rawMaterialDTOToReturn.setActive(rs.getBoolean(rm_recipColumns.active.toString()));
                rawMaterialDTOToReturn.setAmount(rs.getDouble(rm_recipColumns.amount.toString()));
            }

        } catch (SQLException e) {
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

        List<IRawMaterialDTO> rawMaterialDTOList = new ArrayList<>();

        Connection c = iConnPool.getConn();

        String rawMaterialIDQuery = String.format("SELECT %s FROM %s",
                columns.rawMaterial_id, TABLE_NAME);

        try {
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery(rawMaterialIDQuery);

            while (rs.next()) {
                int rawMaterial_ID = rs.getInt(columns.rawMaterial_id.toString());
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
     * @return a number corresponding to the number of columns that is changed as a result of the update.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public int updateRawMaterial(IRawMaterialDTO rawMaterialDTO) throws DALException {

        int variableChanges = 0;

        Connection c = iConnPool.getConn();

        IRawMaterialDTO rawMaterialDTOBeforeUpdate = getRawMaterial(rawMaterialDTO.getRawMaterialDTO_ID());

        String nameUpdateQuery = String.format("UPDATE %s SET %s = ? WHERE %s = %s",
                TABLE_NAME, columns.name, columns.rawMaterial_id, rawMaterialDTO.getRawMaterialDTO_ID());

        String stdDeviationUpdateQuery = String.format("UPDATE %s SET %s = ? WHERE %s = %s",
                TABLE_NAME, columns.stdDeviation, columns.rawMaterial_id, rawMaterialDTO.getRawMaterialDTO_ID());

        try {
            c.setAutoCommit(false);

            PreparedStatement namePS = c.prepareStatement(nameUpdateQuery);
            PreparedStatement stdDeviationPS = c.prepareStatement(stdDeviationUpdateQuery);

            if (!rawMaterialDTO.getName().equals(rawMaterialDTOBeforeUpdate.getName())){
                namePS.setString(1, rawMaterialDTO.getName());

                namePS.executeUpdate();
                variableChanges++;
            }

            if (rawMaterialDTO.getStdDeviation() != rawMaterialDTOBeforeUpdate.getStdDeviation()) {
                stdDeviationPS.setDouble(1, rawMaterialDTO.getStdDeviation());

                stdDeviationPS.executeUpdate();
                variableChanges++;
            }

            c.commit();

        } catch (SQLException e) {
            connectionHelper.catchSQLExceptionAndDoRollback(c, e, "RawMaterialDAO.updateRawMaterial");
        } finally {
            connectionHelper.finallyActionsForConnection(c, "RawMaterialDAO.updateRawMaterial");
        }

        return variableChanges;
    }

    /*
    ---------------------- Support Methods ----------------------
     */


}
