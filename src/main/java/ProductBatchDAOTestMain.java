import dal.DALException;
import dal.multitool.MultiTool;
import dal.productBatch.IProductBatchDAO;
import dal.productBatch.ProductBatchDAO;
import db.IConnPool;
import db.MySQL_DB;
import dto.productBatch.IProductBatchDTO;
import dto.productBatch.ProductBatchDTO;

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



        // List of all productBatches in DB.
        System.out.println("ProductBatches from List :");
        for (IProductBatchDTO productBatchDTO : productBatchDAO.getProductBatchList()) {
            System.out.println(productBatchDTO);
        }
    }
}
