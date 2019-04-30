package dal.rawMaterialBatch;

import dal.DALException;
import dto.rawMaterialBatch.IRawMaterialBatchDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class RawMaterialBatchDAO implements IRawMaterialBatchDAO {

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
    public boolean createRawMaterialBatch(IRawMaterialBatchDTO rawMaterialBatchDTO) throws DALException {
        return false;
    }

    @Override
    public IRawMaterialBatchDTO getRawMaterialBatch(int rawMaterialBatchID) throws DALException {
        return null;
    }

    @Override
    public List<IRawMaterialBatchDTO> getRawMaterialBatchList() throws DALException {
        return null;
    }

    @Override
    public int updateRawMaterialBatch(IRawMaterialBatchDTO rawMaterialBatchDTO) throws DALException {
        return 0;
    }

    /*
    ---------------------- Support Methods ----------------------
     */


}
