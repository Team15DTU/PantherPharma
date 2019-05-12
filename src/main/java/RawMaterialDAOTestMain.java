import dal.DALException;
import dal.multitool.MultiTool;
import dal.rawMaterial.IRawMaterialDAO;
import dal.rawMaterial.RawMaterialDAO;
import db.IConnPool;
import db.MySQL_DB;
import dto.rawMaterial.IRawMaterialDTO;
import dto.rawMaterial.RawMaterialDTO;

import java.sql.SQLOutput;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class RawMaterialDAOTestMain {

    public static void main (String[] args) throws DALException {


        // MAIN FOR IRawMaterialDAO.
        // HERE WE WILL SHOWCASE THE IMPLEMENTED FUNCTIONS!

        System.out.println("Main For IRawMaterialDAO: \n");

        // region ConnPoll and DAO
        IConnPool iConnPool = new MySQL_DB();

        IRawMaterialDAO rawMaterialDAO = new RawMaterialDAO(iConnPool);

        MultiTool multiTool = new MultiTool(iConnPool);
        int nextID = multiTool.getNewAutoIncreasedValueInTable("rawMaterial");
        // endregion

        // IRawMaterialDTO object is created and set with values
        IRawMaterialDTO banan = new RawMaterialDTO("Banan"+nextID, 10.0);
        // Object created in DB
        System.out.println("Status on creation of RawMaterial: " +rawMaterialDAO.createRawMaterial(banan));

        // Getting a RawMaterial object from a existing ID.
        IRawMaterialDTO gettedRawMaterial = rawMaterialDAO.getRawMaterial(4);
        System.out.println("\nGetted rawMaterial from ID = 4: ");
        System.out.println(gettedRawMaterial);

        // Changes values of rawMaterial
        gettedRawMaterial.setName("Pærer UPDATED");
        gettedRawMaterial.setStdDeviation(20.0);

        //Updates that RawMaterial with new information.
        System.out.println("\nRawMaterial with ID = 4 is update with: "
                +rawMaterialDAO.updateRawMaterial(gettedRawMaterial) +" new values");

        // Print updated RawMaterial
        System.out.println("\nGetted rawMaterial from ID = 4 (AFTER UPDATE)");
        System.out.println(rawMaterialDAO.getRawMaterial(4));

        // List of all RawMaterials in DB.
        System.out.println("\nRawMaterials in list: ");
        for (IRawMaterialDTO rm : rawMaterialDAO.getRawMaterialList()){
            System.out.println(rm);
        }

        // Gets a list of RawMaterials that needs to be reordered:
        System.out.println("\nList of RawMaterials that needs to be reordered:");
        for (IRawMaterialDTO rawMaterialDTO : rawMaterialDAO.getRawMaterialsToReorderList()) {
            System.out.println(rawMaterialDTO);
        }

        // region Set everything back to "normal"

        gettedRawMaterial.setName("Pærer");
        gettedRawMaterial.setStdDeviation(2);

        rawMaterialDAO.updateRawMaterial(gettedRawMaterial);

        // endregion
    }

}
