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
public class RMBDAOMain {

    public static void main (String[] args) throws DALException {
        IConnPool iConnPool = new MySQL_DB();

        IRawMaterialBatchDAO rawMaterialBatchDAO = new RawMaterialBatchDAO(iConnPool);

        IRawMaterialBatchDTO bananBatch1 = new RawMaterialBatchDTO(2, 200);

        rawMaterialBatchDAO.createRawMaterialBatch(bananBatch1);


    }

}
