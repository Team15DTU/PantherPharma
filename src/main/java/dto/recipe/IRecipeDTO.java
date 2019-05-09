package dto.recipe;

import dto.rawMaterial.IRawMaterialDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public interface IRecipeDTO {
    
    /*
    ---------------------- Public Methods -----------------------
     */

    // region Getters and Setters

    int getRecipe_ID();

    void setRecipe_ID(int recipe_ID);

    int getCreatedBy_ID();

    void setCreatedBy_ID(int createdBy_ID);

    int getOldRecipe_ID() ;

    void setOldRecipe_ID(int oldRecipe_ID) ;

    String getName();

    void setName(String name) ;

    List<IRawMaterialDTO> getIngredients();

    void setIngredients(List<IRawMaterialDTO> ingredients);

    LocalDate getStartDate();

    void setStartDate(LocalDate startDate);

    int getStorageTime();

    void setStorageTime(int storageTime);

    LocalDate getEndDate();

    void setEndDate(LocalDate endDate);

    // endregion

    void addRawMaterial (IRawMaterialDTO rawMaterialDTO);
    String toString ();

}
