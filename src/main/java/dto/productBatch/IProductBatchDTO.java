package dto.productBatch;

import dto.rawMaterialBatch.RawMaterialBatchDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public interface IProductBatchDTO {

    // region Getters and Setter

    int getProductBatchID();

    void setProductBatchID(int productBatchID);

    int getRecipeID();

    void setRecipeID(int recipeID);

    int getAmount();

    void setAmount(int amount);

    List<RawMaterialBatchDTO> getUsedRawMaterialBatches();

    void setUsedRawMaterialBatches(List<RawMaterialBatchDTO> usedRawMaterialBatches);

    ProductBatchStatus_Enum getStatus();

    void setStatus(ProductBatchStatus_Enum status);

}
