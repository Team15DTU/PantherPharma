package dal;

/**
 * @author Rasmus Sander Larsen
 */
public class Columns {

    // Names on columns in the DB table: user
    public enum user {
        user_id, name, isAdmin, userName, password
    }

    // Names on columns in the DB table: oldRecipe
    public enum oldRecipe {
        recipe_id, oldRecipe_id, endDate
    }

    // Names on columns in the DB table: recipe
    public enum recipe {
        recipe_id, name, storageTime, startDate
    }

    // Names on columns in the DB table: rawMaterial_recipe
    public enum rm_recipe {
        rawMaterial_id, recipe_id, active, amount
    }

    // Names on columns in the DB table: rawMaterial
    public enum rawMaterial {
        rawMaterial_id, stdDeviation, name
    }

    // Names on columns in the DB table: recipe_farmaceut
    public enum recipe_farmaceut {
        recipe_id, farmaceut_id
    }

    // Names in columns in the DB table: farmaceut
    public enum farmaceut {
        farmaceut_id
    }

    // Names in columns in the DB table: laborant
    public enum laborant {
        laborant_id
    }

    // Names in columns in the DB table: produktionsleder
    public enum produktionsleder {
        produktionsleder_id
    }

    // Names in columns in the DB table: rawMaterialBatch
    public enum rawMaterialBatch {
        rawMaterialBatch_id, isResidue, amount
    }
    // Names in columns in the DB table: rawMaterialBatch_rawMaterial
    public enum rawMaterialBatch_rawMaterial {
        rawMaterialBatch_id, rawMaterial_id
    }


    /*
    -------------------------- Fields --------------------------
     */
    
    
    
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


}
