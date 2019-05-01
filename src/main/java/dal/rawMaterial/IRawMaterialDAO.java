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

    /**
     *
     * @param rawMaterialDTO
     * @return
     * @throws DALException This methods throws a DALException.
     */
    boolean createRawMaterial (IRawMaterialDTO rawMaterialDTO) throws DALException;

    IRawMaterialDTO getRawMaterial (int rawMaterialBatch) throws DALException;

    List<IRawMaterialDTO> getRawMaterialList () throws DALException;

    int updateRawMaterial (IRawMaterialDTO rawMaterialDTO) throws DALException;


}
