package dto.recipe;

import dto.rawMaterial.RawMaterialDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class RecipeDTO implements IRecipeDTO{

    /*
    -------------------------- Fields --------------------------
     */

    private int recipe_ID;
    private String name;
    private List<RawMaterialDTO> ingredienses;
    private LocalDate startDate;
    private LocalDate storageDate;
    private LocalDate endDate;
    
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

    //Getter and SETTER

    public int getRecipe_ID() {
        return recipe_ID;
    }

    public void setRecipe_ID(int recipe_ID) {
        this.recipe_ID = recipe_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RawMaterialDTO> getIngredienses() {
        return ingredienses;
    }

    public void setIngredienses(List<RawMaterialDTO> ingredienses) {
        this.ingredienses = ingredienses;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getStorageDate() {
        return storageDate;
    }

    public void setStorageDate(LocalDate storageDate) {
        this.storageDate = storageDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }




}
