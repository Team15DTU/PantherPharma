package dal.productBatch;

import dal.DALException;
import dto.productBatch.IProductBatchDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public interface IProductBatchDAO {
    
    /*
    ---------------------- Public Methods -----------------------
     */

    /**
     *
     * @param productBatchDTO
     * @return
     * @throws DALException This methods throws a DALException.
     */
    boolean createProductBatch (IProductBatchDTO productBatchDTO) throws DALException;

    IProductBatchDTO getProductBatch (int productBatchID) throws DALException;

    List<IProductBatchDTO> getProductBatchList () throws DALException;

    int updateProductBatch (IProductBatchDTO productBatchDTO) throws DALException;

}
