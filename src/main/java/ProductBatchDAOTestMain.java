import dal.DALException;
import dal.multitool.MultiTool;
import dal.productBatch.IProductBatchDAO;
import dal.productBatch.ProductBatchDAO;
import db.IConnPool;
import db.MySQL_DB;
import dto.productBatch.IProductBatchDTO;
import dto.productBatch.ProductBatchDTO;
import dto.productBatch.ProductBatchStatus_Enum;

/**
 * @author Rasmus Sander Larsen
 */
public class ProductBatchDAOTestMain {

    public static void main (String[] args) throws DALException {

        // MAIN FOR IProductBatchDAO.
        // HERE WE WILL SHOWCASE THE IMPLEMENTED FUNCTIONS!

        System.out.println("Main For IProductBatchDAO: \n");

        // region ConnPool and DAO.

        IConnPool iConnPool = new MySQL_DB();

        IProductBatchDAO productBatchDAO = new ProductBatchDAO(iConnPool);

        MultiTool multiTool = new MultiTool(iConnPool);
        int nextID = multiTool.getNewAutoIncreasedValueInTable("productBatch");
        // endregion

        // Creates a productBatchDTO with a recipe_id of a existing recipe and a amount on 1000.
        IProductBatchDTO banankageBatch = new ProductBatchDTO(23,1000);

        // Create productBatch in DB and prints result.
        System.out.println("Status of creation of productBatch: " +productBatchDAO.createProductBatch(banankageBatch));

        // Gets a ProductBatch from the DB
        IProductBatchDTO productBatchDTO_id4 = productBatchDAO.getProductBatch(4);
        System.out.println("\nGetted ProductBatchDTO with ID = 4: ");
        System.out.println(productBatchDTO_id4);

        // Variables a changed for productBatch_Id4
        productBatchDTO_id4.setStatus(ProductBatchStatus_Enum.underProduction);

        // Updates productBatch with id= 4 in the DB.
        productBatchDAO.updateProductBatch(productBatchDTO_id4);

        // Prints the updated ProductBatch.
        System.out.println("\nGetted ProductBatchDTO with = 4 AFTER UPDATE :");
        System.out.println(productBatchDAO.getProductBatch(4));

        // List of all productBatches in DB.
        System.out.println("\nProductBatches from List :");
        for (IProductBatchDTO productBatchDTO : productBatchDAO.getProductBatchList()) {
            System.out.println(productBatchDTO);
        }

        // region Set everything back to "normal"
        productBatchDTO_id4.setStatus(ProductBatchStatus_Enum.ordred);

        productBatchDAO.updateProductBatch(productBatchDTO_id4);
        // endregion
    }
}
