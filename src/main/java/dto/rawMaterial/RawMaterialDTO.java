package dto.rawMaterial;

import dal.rawMaterial.RawMaterialDAO;

/**
 * @author Rasmus Sander Larsen
 */
public class RawMaterialDTO implements IRawMaterialDTO {

    /*
    -------------------------- Fields --------------------------
     */

    private int rawMaterialDTO_ID = 0;
    private double stdDeviation;
    private String name;
    private boolean isUsed;
    private int recipe_id;
    private double amount;
    private boolean isActive;

    /*
    ----------------------- Constructor -------------------------
     */
    
    public RawMaterialDTO () {
    }

    public RawMaterialDTO (String name, double stdDeviation){
        this.name = name;
        this.stdDeviation = stdDeviation;
    }

    public RawMaterialDTO (int rawMaterialDTO_ID, String name, double stdDeviation){
        this.rawMaterialDTO_ID = rawMaterialDTO_ID;
        this.name = name;
        this.stdDeviation = stdDeviation;
    }

    public RawMaterialDTO (int rawMaterialDTO_ID, String name, double stdDeviation, double amount, boolean isActive) {
        this.rawMaterialDTO_ID = rawMaterialDTO_ID;
        this.name = name;
        this.stdDeviation = stdDeviation;
        this.amount = amount;
        this.isActive = isActive;
    }
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"

    @Override
    public int getRawMaterialDTO_ID() {
        return rawMaterialDTO_ID;
    }

    @Override
    public void setRawMaterialDTO_ID(int rawMaterialDTO_ID) {
        this.rawMaterialDTO_ID = rawMaterialDTO_ID;
    }

    @Override
    public double getStdDeviation() {
        return stdDeviation;
    }

    @Override
    public void setStdDeviation(double stdDeviation) {
        this.stdDeviation = stdDeviation;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isUsed() {
        return isUsed;
    }

    @Override
    public void setUsed(boolean used) {
        isUsed = used;
    }

    @Override
    public int getRecipe_id() {
        return recipe_id;
    }

    @Override
    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setActive(boolean active) {
        isActive = active;
    }


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    public String toString () {
        StringBuilder toStringBuilder = new StringBuilder();
        if (rawMaterialDTO_ID != 0){
            toStringBuilder.append("RawMaterial_ID: " + rawMaterialDTO_ID + ", ");
        }
        toStringBuilder.append("Name: " + name + ", ");
        toStringBuilder.append("StdDeviation: " + stdDeviation );
        if (isUsed) {
            toStringBuilder.append(", Recipe_id: " + recipe_id);
            toStringBuilder.append(", Amount: " + amount );
            toStringBuilder.append(", Active: " + isActive);
        }
        return toStringBuilder.toString();
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
