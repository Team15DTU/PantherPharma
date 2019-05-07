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
     * This method creates a productBatch in the DB from the information in the inputted Object.
     * @param productBatchDTO contains the information for the productBatch that is created.
     * @return "true" if the method goes through and "false" if something fails.
     * @throws DALException This methods throws a DALException.
     */
    boolean createProductBatch (IProductBatchDTO productBatchDTO) throws DALException;

    /**
     * This method gets a IProductBathDTO object containing the information matching the inputted productBatchID.
     * @param productBatchID is the ID for the product batch the method should return.
     * @return a IProductBatchDTO containing information matching the inputted ID.
     * @throws DALException This methods throws a DALException.
     */
    IProductBatchDTO getProductBatch (int productBatchID) throws DALException;

    /**
     * This method a List Object containing ALL productBatches in the DB.
     * @return a List<IProductBatchDTO> containing ALL productBatches in DB.
     * @throws DALException This methods throws a DALException.
     */
    List<IProductBatchDTO> getProductBatchList () throws DALException;

    /**
     * This method updates an existing product batch with the information in the inputted Object.
     * @param productBatchDTO contains the information that the product batch is updated with.
     * @return a number corresponding to the number of columns that is changed as a result of the update.
     * @throws DALException This methods throws a DALException.
     */
    int updateProductBatch (IProductBatchDTO productBatchDTO) throws DALException;

}
