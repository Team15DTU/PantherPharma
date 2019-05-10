package dto.recipe;

import dto.rawMaterial.RawMaterialDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public interface IRecipeDTO {
    
    /*
    ---------------------- Public Methods -----------------------
     */


    int getRecipe_ID();

    void setRecipe_ID(int recipe_ID);

    String getName();

    void setName(String name);

    List<RawMaterialDTO> getIngredienses();

    void setIngredienses(List<RawMaterialDTO> ingredienses);

    LocalDate getStartDate();

    void setStartDate(LocalDate startDate);

    LocalDate getStorageDate();

    void setStorageDate(LocalDate storageDate);

    LocalDate getEndDate();

    void setEndDate(LocalDate endDate);
    
    

}
