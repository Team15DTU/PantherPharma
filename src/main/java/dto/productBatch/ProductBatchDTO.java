package dto.productBatch;

import dto.rawMaterialBatch.RawMaterialBatchDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class ProductBatchDTO {

    /*
    -------------------------- Fields --------------------------
     */
    
    private int productBatchID;
    private int recipeID;
    private int amount;
    private List<RawMaterialBatchDTO> usedRawMaterialBatches;
    private ProductBatchStatus_Enum status;
    
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
    
    
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
