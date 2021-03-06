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
     * This method creates a raw material in the DB with the information from the inputted IRawMaterialDTO object.
     * @param rawMaterialDTO contains the information that is used to creating the raw material.
     * @return "true" if the creation went through and "false" if something fails.
     * @throws DALException This methods throws a DALException.
     */
    boolean createRawMaterial (IRawMaterialDTO rawMaterialDTO) throws DALException;

    /**
     * This method gets a IRawMaterial object that contains the information matching the inputted
     * @param rawMaterial_ID is the ID on the raw material the method should return.
     * @return a IRawMaterial object containing the information matching the inputted ID.
     * @throws DALException This methods throws a DALException.
     */
    IRawMaterialDTO getRawMaterial (int rawMaterial_ID) throws DALException;

    /**
     * This method returns a List object containing ALL raw materials in the DB.
     * @return a List<IRawMaterialDTO> of ALL raw material.
     * @throws DALException This methods throws a DALException.
     */
    List<IRawMaterialDTO> getRawMaterialList () throws DALException;

    /**
     * This method updates an existing raw material with the information in the inputted IRawMaterialDTO object.
     * @param rawMaterialDTO contains the information that the raw materials should be updated with.
     * @return a number corresponding to the number of recipe that is changed as a result of the update.
     * @throws DALException This methods throws a DALException.
     */
    int updateRawMaterial (IRawMaterialDTO rawMaterialDTO) throws DALException;

    /**
     * This method gets a List Object containing all the Raw Materials that needs to be reordered.
     * A RawMaterial needs to be reordered if the total amount of rawMaterialBatches of a certain rawMaterial,
     * isn't enough to make 2 times the recipe using the most of that particular rawMaterial.
     * @return a List<IRawMaterialDTO> of rawMaterials to reorder.
     * @throws DALException This method throws a DALException.
     */
    List<IRawMaterialDTO> getRawMaterialsToReorderList () throws DALException;
}
