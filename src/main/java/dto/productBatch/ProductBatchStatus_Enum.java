package dto.productBatch;

/**
 * @author Rasmus Sander Larsen
 */
public enum ProductBatchStatus_Enum {

    ordred(1,"Ordered"), underProduction(2,"Under Production") , done (3,"Done");

    private String statusName;
    private int status_id;

    public String getStatusName() {
        return statusName;
    }
    public Integer getStatus_id () {
        return status_id;
    }
    ProductBatchStatus_Enum(int status_id, String statusName) {
        this.status_id = status_id;
        this.statusName = statusName;
    }

}
