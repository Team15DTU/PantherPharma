package dal.rawMaterial;

import dal.DALException;
import dto.rawMaterial.IRawMaterialDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class RawMaterialDAO implements IRawMaterialDAO {

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

    @Override
    public boolean createRawMaterial(IRawMaterialDTO rawMaterialDTO) throws DALException {
        return false;
    }

    @Override
    public IRawMaterialDTO getRawMaterial(int rawMaterialBatch) throws DALException {
        return null;
    }

    @Override
    public List<IRawMaterialDTO> getRawMaterialList() throws DALException {
        return null;
    }

    @Override
    public int updateRawMaterial(IRawMaterialDTO rawMaterialDTO) throws DALException {
        return 0;
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
