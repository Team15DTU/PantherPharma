package dal.rawMaterial;

import dal.DALException;
import dto.rawMaterial.IRawMaterialDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public interface IRawMaterialDAO {
    
    /*
    ---------------------- Public Methods -----------------------
     */

    boolean createRawMaterial (IRawMaterialDTO rawMaterialDTO) throws DALException;

    IRawMaterialDTO getRawMaterial (int rawMaterialBatch) throws DALException;

    List<IRawMaterialDTO> getRawMaterialList () throws DALException;

    int updateRawMaterial (IRawMaterialDTO rawMaterialDTO) throws DALException;


}
