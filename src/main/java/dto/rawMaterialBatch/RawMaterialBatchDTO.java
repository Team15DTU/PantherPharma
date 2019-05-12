package dto.rawMaterialBatch;

/**
 * @author Rasmus Sander Larsen
 */
public class RawMaterialBatchDTO implements IRawMaterialBatchDTO {

    /*
    -------------------------- Fields --------------------------
     */
    
    private int rawMaterialBatchID = -1;
    private int rawMaterialID ;
    private boolean isResidue = false;
    private double amount;
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public RawMaterialBatchDTO () {}

    public RawMaterialBatchDTO (int rawMaterialID, double amount) {
        this.rawMaterialID = rawMaterialID;
        this.amount = amount;
    }
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"

    public int getRawMaterialBatchID() {
        return rawMaterialBatchID;
    }

    public void setRawMaterialBatchID(int rawMaterialBatchID) {
        this.rawMaterialBatchID = rawMaterialBatchID;
    }

    public int getRawMaterialID() {
        return rawMaterialID;
    }

    public void setRawMaterialID(int rawMaterialID) {
        this.rawMaterialID = rawMaterialID;
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


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    public String toString () {
        StringBuilder toStringBuilder = new StringBuilder();
        if ( rawMaterialBatchID != -1) {
            toStringBuilder.append("RawMaterialBatchID: " + rawMaterialBatchID + ", ");
        }
        toStringBuilder.append("RawMaterialID: " + rawMaterialID + ", ");
        toStringBuilder.append("IsResidue: " + isResidue + ", ");
        toStringBuilder.append("Amount: " + amount);

        return toStringBuilder.toString();
    }
    
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
