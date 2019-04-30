package dal.productBatch;

import dal.DALException;
import dto.productBatch.IProductBatchDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class ProductBatchDAO implements IProductBatchDAO {

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
    public boolean createProductBatch(IProductBatchDTO productBatchDTO) throws DALException {
        return false;
    }

    @Override
    public IProductBatchDTO getProductBatch(int productBatchID) throws DALException {
        return null;
    }

    @Override
    public List<IProductBatchDTO> getProductBatchList() throws DALException {
        return null;
    }

    @Override
    public int updateProductBatch(IProductBatchDTO productBatchDTO) throws DALException {
        return 0;
    }

    /*
    ---------------------- Support Methods ----------------------
     */


}
