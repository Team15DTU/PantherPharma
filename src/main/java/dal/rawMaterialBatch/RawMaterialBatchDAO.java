package dal.rawMaterialBatch;

import dal.DALException;
import dto.rawMaterialBatch.IRawMaterialBatchDTO;

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
    
    private final String TABLE_NAME = "rawMaterialBatch";
    
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
     * @return a number corresponding to the number of columns that is changed as a result of the update.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public int updateRawMaterialBatch(IRawMaterialBatchDTO rawMaterialBatchDTO) throws DALException {
        return 0;
    }

    /*
    ---------------------- Support Methods ----------------------
     */


}
