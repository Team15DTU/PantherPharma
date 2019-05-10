package dto.rawMaterialBatch;

/**
 * @author Rasmus Sander Larsen
 */
public interface IRawMaterialBatchDTO {

    
    /*
    ---------------------- Public Methods -----------------------
     */

    //Region GETTER and SETTER


    int getRawMaterialBatchID();

    void setRawMaterialBatchID(int rawMaterialBatchID);

    boolean isResidue();

    void setResidue(boolean residue);

    double getAmount();

    void setAmount(double amount);


}
