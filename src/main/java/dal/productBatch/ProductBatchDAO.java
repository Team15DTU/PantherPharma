package dal.productBatch;

import dal.ConnectionHelper;
import dal.DALException;
import db.IConnPool;
import dto.productBatch.IProductBatchDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class ProductBatchDAO implements IProductBatchDAO {

    public enum columns {
        productBatch_id, status_id, recipe_id, amount
    }
    /*
    -------------------------- Fields --------------------------
     */
    private IConnPool iConnPool;
    private ConnectionHelper connectionHelper;
    private final String TABLE_NAME = "productBatch";
    
    /*
    ----------------------- Constructor -------------------------
     */

    public ProductBatchDAO(IConnPool iConnPool){
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
     * This method creates a productBatch in the DB from the information in the inputted Object.
     *
     * @param productBatchDTO contains the information for the productBatch that is created.
     * @return "true" if the method goes through and "false" if something fails.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public boolean createProductBatch(IProductBatchDTO productBatchDTO) throws DALException {
        return false;
    }

    /**
     * This method gets a IProductBathDTO object containing the information matching the inputted productBatchID.
     *
     * @param productBatchID is the ID for the product batch the method should return.
     * @return a IProductBatchDTO containing information matching the inputted ID.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public IProductBatchDTO getProductBatch(int productBatchID) throws DALException {
        return null;
    }

    /**
     * This method a List Object containing ALL productBatches in the DB.
     *
     * @return a List<IProductBatchDTO> containing ALL productBatches in DB.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public List<IProductBatchDTO> getProductBatchList() throws DALException {
        return null;
    }

    /**
     * This method updates an existing product batch with the information in the inputted Object.
     *
     * @param productBatchDTO contains the information that the product batch is updated with.
     * @return a number corresponding to the number of recipe that is changed as a result of the update.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public int updateProductBatch(IProductBatchDTO productBatchDTO) throws DALException {
        return 0;
    }

    /*
    ---------------------- Support Methods ----------------------
     */


}
