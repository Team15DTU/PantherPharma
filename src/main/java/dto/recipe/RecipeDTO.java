package dto.recipe;

import dto.rawMaterial.IRawMaterialDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class RecipeDTO implements IRecipeDTO {

    /*
    -------------------------- Fields --------------------------
     */

    private int recipe_ID = -1;
    private int createdBy_ID = -1;
    private int oldRecipe_ID = -1;
    private String name;
    private List<IRawMaterialDTO> ingredients;
    private LocalDate startDate;
    private int storageTime;
    private LocalDate endDate;
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public RecipeDTO (){
        ingredients = new ArrayList<>();
    }

    public RecipeDTO (String name, LocalDate startDate, int storageTime){
        this.name = name;
        this.startDate = startDate;
        this.storageTime = storageTime;
        ingredients = new ArrayList<>();
    }

    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"

    public int getRecipe_ID() {
        return recipe_ID;
    }

    public void setRecipe_ID(int recipe_ID) {
        this.recipe_ID = recipe_ID;
    }

    public int getCreatedBy_ID() {
        return createdBy_ID;
    }

    public void setCreatedBy_ID(int createdBy_ID) {
        this.createdBy_ID = createdBy_ID;
    }

    public int getOldRecipe_ID() {
        return oldRecipe_ID;
    }

    public void setOldRecipe_ID(int oldRecipe_ID) {
        this.oldRecipe_ID = oldRecipe_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IRawMaterialDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IRawMaterialDTO> ingredients) {
        this.ingredients = ingredients;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public int getStorageTime() {
        return storageTime;
    }

    @Override
    public void setStorageTime(int storageTime) {
        this.storageTime = storageTime;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    public void addRawMaterial (IRawMaterialDTO rawMaterialDTO) {
        rawMaterialDTO.setUsed(true);
        ingredients.add(rawMaterialDTO);
    }

    public String toString () {
        StringBuilder toStringBuilder = new StringBuilder();
        if (recipe_ID != -1) {
            toStringBuilder.append("RecipeID: " + recipe_ID + ", ");
        }
        if (createdBy_ID != -1){
            toStringBuilder.append("CreatedByID: " + createdBy_ID + ", ");
        }
        toStringBuilder.append("Name: " + name + ", ");
        toStringBuilder.append("StartDate: " + startDate + ", ");
        if (endDate != null) {
            toStringBuilder.append("EndDate: " + endDate + ", ");
        }
        toStringBuilder.append("StorageTime: " + storageTime + " days");
        if (oldRecipe_ID != -1) {
            toStringBuilder.append(", OldRecipeID: " + oldRecipe_ID);
        }
        if (ingredients.size() >= 1) {
            toStringBuilder.append(" // ALL RawMaterials Used In This Recipe: ");
            for (IRawMaterialDTO rawMaterialDTO : ingredients) {
                toStringBuilder.append("(RW_ID: " + rawMaterialDTO.getRawMaterialDTO_ID());
                toStringBuilder.append(", Name: "+ rawMaterialDTO.getName() ) ;
                toStringBuilder.append(", Amount: " + rawMaterialDTO.getAmount() + "), ");
            }

        }
        return toStringBuilder.toString();
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
