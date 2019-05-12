import dal.DALException;
import dal.rawMaterialBatch.IRawMaterialBatchDAO;
import dal.rawMaterialBatch.RawMaterialBatchDAO;
import db.IConnPool;
import db.MySQL_DB;
import dto.rawMaterialBatch.IRawMaterialBatchDTO;
import dto.rawMaterialBatch.RawMaterialBatchDTO;

/**
 * @author Rasmus Sander Larsen
 */
public class RawMaterialBatchDAOTestMain {

    public static void main (String[] args) throws DALException {

        // MAIN FOR IRawMaterialBatchDAODAO.
        // HERE WE WILL SHOWCASE THE IMPLEMENTED FUNCTIONS!

        System.out.println("Main For IRawMaterialBatchDAO: \n");

        // region ConnPoll and DAO
        IConnPool iConnPool = new MySQL_DB();

        IRawMaterialBatchDAO rawMaterialBatchDAO = new RawMaterialBatchDAO(iConnPool);
        // endregion

        // A RawMaterialBatchDTO of RawMaterial = "Banan" (rawMaterial_id = 2 = "bananer")
        IRawMaterialBatchDTO bananBatch1 = new RawMaterialBatchDTO(2, 200);

        // RawMaterialBatch is added to the DB.
        System.out.println("Status for the creation of rawMaterialBatch: " + rawMaterialBatchDAO.createRawMaterialBatch(bananBatch1));

        // We get a IRawMaterialBatchDTO from a inputted id.
        IRawMaterialBatchDTO rawMaterialBatch_ID7 = rawMaterialBatchDAO.getRawMaterialBatch(7);
        System.out.println("\nGetted rawMaterialBatchDTO with id = 7 ");
        System.out.println(rawMaterialBatch_ID7);

        // The values of RawMaterialBatch_id7 is changed of the DB is updated with the new information.
        rawMaterialBatch_ID7.setResidue(true);
        rawMaterialBatch_ID7.setAmount(400);
        rawMaterialBatchDAO.updateRawMaterialBatch(rawMaterialBatch_ID7);
        System.out.println("\nNewly getted rawMateriaBatchDTO with id = 7 AFTER UPDATE : ");
        System.out.println(rawMaterialBatchDAO.getRawMaterialBatch(7));

        // Gets all RawMaterialBatches in the BD as a list.
        System.out.println("\nRawMaterialBatches in List: ");
        for (IRawMaterialBatchDTO rawMaterialBatchDTO : rawMaterialBatchDAO.getRawMaterialBatchList()) {
            System.out.println(rawMaterialBatchDTO);
        }

        // region Values updated back
        // Set values back "automatically"
        rawMaterialBatch_ID7.setResidue(false);
        rawMaterialBatch_ID7.setAmount(500);
        rawMaterialBatchDAO.updateRawMaterialBatch(rawMaterialBatch_ID7);
        // endregion

    }

}
