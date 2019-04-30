package dal.rawMaterialBatch;

import dal.DALException;
import dto.rawMaterialBatch.IRawMaterialBatchDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public interface IRawMaterialBatchDAO {
    
    /*
    ---------------------- Public Methods -----------------------
     */

    boolean createRawMaterialBatch (IRawMaterialBatchDTO rawMaterialBatchDTO) throws DALException;

    IRawMaterialBatchDTO getRawMaterialBatch (int rawMaterialBatchID) throws DALException;

    List<IRawMaterialBatchDTO> getRawMaterialBatchList () throws DALException;

    int updateRawMaterialBatch (IRawMaterialBatchDTO rawMaterialBatchDTO) throws DALException;

}
