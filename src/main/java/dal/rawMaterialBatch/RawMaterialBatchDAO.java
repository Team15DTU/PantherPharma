package dal.rawMaterialBatch;

import dal.Columns;
import dal.ConnectionHelper;
import dal.DALException;
import db.IConnPool;
import dto.rawMaterialBatch.IRawMaterialBatchDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class RawMaterialBatchDAO implements IRawMaterialBatchDAO {

    public enum columns {
        rawMaterialBatch_id, isResidue, amount
    }

    /*
    -------------------------- Fields --------------------------
     */
    private IConnPool iConnPool;
    private ConnectionHelper connectionHelper;
    private final String PBTABLE_NAME = "productBatch";
    private final String RTABLE_NAME = "recipe";
    private final String RRTABLE_NAME = "rawMaterial_recipe";
    private final String TABLE_NAME = "rawMaterialBatch";
    
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

    // <editor-folder desc="Properties"


    // </editor-folder>
    
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
        return null;
    }

    /**
     * This method returns a List object containing IRawMaterialBatchDTO objects of ALL raw material batches in the DB.
     *
     * @return a List<IRawMaterialDTO> of all raw material batches in the DB.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public List<IRawMaterialBatchDTO> getRawMaterialBatchList() throws DALException {
        return null;
    }

    /**
     * This method updates an existing raw material batch with the information in the inputted Object.
     *
     * @param rawMaterialBatchDTO contains the information that the raw material batch is updated with.
     * @return a number corresponding to the number of recipe that is changed as a result of the update.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public int updateRawMaterialBatch(IRawMaterialBatchDTO rawMaterialBatchDTO) throws DALException {
        return 0;
    }

    /**
     *
     * @throws DALException
     */

    public void orderRawMaterial() throws DALException {


        String query1 = "select rawMaterial_id from rawMaterial_recipe where (select MAX(amount) from rawMaterial_recipe) = amount";

        String query2 = "select rawMaterialBatch_id from rawMaterialBatch_rawMaterial " +
                "inner join rawMaterial on rawMaterial.rawMaterial_id = rawMaterialBatch_rawMaterial.rawMaterialBatch_id";

        String query3 = "select SUM(amount) from rawMaterialBatch where rawMaterialBatch_id in " +
                "(select rawMaterialBatch_id from rawMaterialBatch_rawMaterial" +
                " inner join rawMaterial on rawMaterial.rawMaterial_id = rawMaterialBatch_rawMaterial.rawMaterialBatch_id)";


        Connection c = iConnPool.getConn();

        double amountLeft = 0;
        double maxAmount = 0;
        int idForMax = 0;

        String getAmountQuery = String.format("SELECT %s FROM %s" , columns.amount, TABLE_NAME);
        String getRawMaterialAmount = String.format("SELECT * FROM %s ORDER BY %s", RRTABLE_NAME , Columns.rm_recipeColumns.amount);
        String getRawMaterialID = String.format("SELECT %s FROM %s ORDER BY %s", Columns.rm_recipeColumns.rawMaterial_id, RRTABLE_NAME, Columns.rm_recipeColumns.amount);

        try{
            PreparedStatement preparedStatementAmount = c.prepareStatement(getAmountQuery);
            ResultSet resultSetAmount = preparedStatementAmount.executeQuery();

            while (resultSetAmount.next()){
                amountLeft = resultSetAmount.getDouble(columns.amount.toString());
            }

            PreparedStatement preparedStatementrawMaterial = c.prepareStatement(getRawMaterialAmount);
            PreparedStatement preparedStatementID = c.prepareStatement(getRawMaterialID);
            ResultSet resultSetRaw = preparedStatementrawMaterial.executeQuery();
            ResultSet resultSetID = preparedStatementID.executeQuery();

            while (resultSetRaw.next()) {
                maxAmount = resultSetRaw.getDouble(Columns.rm_recipeColumns.amount.toString());
            }
            while (resultSetID.next()){
                idForMax = resultSetID.getInt(Columns.rm_recipeColumns.rawMaterial_id.toString());
            }





        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    /*
    ---------------------- Support Methods ----------------------
     */


}
