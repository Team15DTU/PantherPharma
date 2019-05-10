package dto.productBatch;

import dto.rawMaterialBatch.RawMaterialBatchDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class ProductBatchDTO implements IProductBatchDTO {

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

    public int getProductBatchID() {
        return productBatchID;
    }

    public void setProductBatchID(int productBatchID) {
        this.productBatchID = productBatchID;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<RawMaterialBatchDTO> getUsedRawMaterialBatches() {
        return usedRawMaterialBatches;
    }

    public void setUsedRawMaterialBatches(List<RawMaterialBatchDTO> usedRawMaterialBatches) {
        this.usedRawMaterialBatches = usedRawMaterialBatches;
    }

    public ProductBatchStatus_Enum getStatus() {
        return status;
    }

    public void setStatus(ProductBatchStatus_Enum status) {
        this.status = status;
    }


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
