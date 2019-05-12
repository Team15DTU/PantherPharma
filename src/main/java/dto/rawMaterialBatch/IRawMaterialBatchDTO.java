package dto.rawMaterialBatch;

/**
 * @author Rasmus Sander Larsen
 */
public interface IRawMaterialBatchDTO {

    // region Getters and Setters

    int getRawMaterialBatchID();

    void setRawMaterialBatchID(int rawMaterialBatchID);

    int getRawMaterialID();

    void setRawMaterialID(int rawMaterialID);

    boolean isResidue();

    void setResidue(boolean residue);

    double getAmount() ;

    void setAmount(double amount);

    // endregion
    
    /*
    ---------------------- Public Methods -----------------------
     */

    String toString ();

}
