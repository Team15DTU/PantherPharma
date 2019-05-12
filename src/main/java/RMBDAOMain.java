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

        //rawMaterialBatchDAO.createRawMaterialBatch(bananBatch1);

        IRawMaterialBatchDTO rmb7 = rawMaterialBatchDAO.getRawMaterialBatch(7);
        rmb7.setResidue(false);
        rmb7.setAmount(400);
        System.out.println(rawMaterialBatchDAO.updateRawMaterialBatch(rmb7));
        //System.out.println(rawMaterialBatchDAO.getRawMaterialBatch(2));

        for (IRawMaterialBatchDTO rawMaterialBatchDTO : rawMaterialBatchDAO.getRawMaterialBatchList()) {
            System.out.println(rawMaterialBatchDTO);
        }


    }

}
