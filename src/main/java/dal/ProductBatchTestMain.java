package dal;

import dal.productBatch.IProductBatchDAO;
import dal.productBatch.ProductBatchDAO;
import db.IConnPool;
import db.MySQL_DB;
import dto.productBatch.IProductBatchDTO;
import dto.productBatch.ProductBatchDTO;
import dto.productBatch.ProductBatchStatus_Enum;

import java.util.zip.DataFormatException;

/**
 * @author Rasmus Sander Larsen
 */
public class ProductBatchTestMain {

    public static void main (String[] args) throws DALException {
        IConnPool iConnPool = new MySQL_DB();

        IProductBatchDAO productBatchDAO = new ProductBatchDAO(iConnPool);

        IProductBatchDTO banankage = new ProductBatchDTO(50,5000);

        // productBatchDAO.createProductBatch(banankage);


    }

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
    
    
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
