package dto.productBatch;

import dto.rawMaterial.IRawMaterialDTO;
import dto.rawMaterialBatch.IRawMaterialBatchDTO;
import dto.rawMaterialBatch.RawMaterialBatchDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class ProductBatchDTO implements IProductBatchDTO {

    /*
    -------------------------- Fields --------------------------
     */
    
    private int productBatchID = -1;
    private int recipeID = -1;
    private int amount;
    private List<RawMaterialBatchDTO> usedRawMaterialBatches;
    private ProductBatchStatus_Enum status;
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public ProductBatchDTO(){

    }

    public ProductBatchDTO(int recipeID, int amount, ProductBatchStatus_Enum status){
        this.recipeID = recipeID;
        this.amount = amount;
        this.status = status;
    }

    public ProductBatchDTO(int productBatchID, int recipeID, int amount, ProductBatchStatus_Enum status){
        this.productBatchID = productBatchID;
        this.recipeID = recipeID;
        this.amount = amount;
        this.status = status;
    }


    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    // </editor-folder>

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


    
    /*
    ---------------------- Public Methods -----------------------
     */

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (productBatchID!=-1){
            stringBuilder.append("ProductBatchID: " + productBatchID);
        }
        if (recipeID!=-1){
            stringBuilder.append("RecipeID: " + recipeID);
        }
        stringBuilder.append("Amount: " + amount);
        if (usedRawMaterialBatches.size() >= 1) {
            stringBuilder.append(" // ALL RawMaterialsBatches Used In This Recipe: ");
            for (IRawMaterialBatchDTO rawMaterialBatchDTO : usedRawMaterialBatches) {
                stringBuilder.append("RWB_ID: " + rawMaterialBatchDTO.getRawMaterialBatchID());
            }
        }
        return stringBuilder.toString();
    }
    
    
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
