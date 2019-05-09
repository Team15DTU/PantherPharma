package dto.rawMaterial;

/**
 * @author Rasmus Sander Larsen
 */
public interface IRawMaterialDTO {

    /*
    ---------------------- Public Methods -----------------------
     */

    // region Getters and Setters

    int getRawMaterialDTO_ID();

    void setRawMaterialDTO_ID(int rawMaterialDTO_ID);

    String getName();

    void setName(String name);

    double getStdDeviation();

    void setStdDeviation(double stdDeviation);

    boolean isUsed();

    void setUsed(boolean used);

    int getRecipe_id();

    void setRecipe_id(int recipe_id);

    double getAmount();

    void setAmount(double amount);

    boolean isActive();

    void setActive(boolean active);


    // endregion
    

}
