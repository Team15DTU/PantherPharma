package dto.rawMaterialBatch;

/**
 * @author Rasmus Sander Larsen
 */
public class RawMaterialBatchDTO implements IRawMaterialBatchDTO{

    /*
    -------------------------- Fields --------------------------
     */
    
    private int rawMaterialBatchID;
    private boolean isResidue;
    private double amount;
    
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


    public int getRawMaterialBatchID() {
        return rawMaterialBatchID;
    }

    public void setRawMaterialBatchID(int rawMaterialBatchID) {
        this.rawMaterialBatchID = rawMaterialBatchID;
    }

    public boolean isResidue() {
        return isResidue;
    }

    public void setResidue(boolean residue) {
        isResidue = residue;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }



}
