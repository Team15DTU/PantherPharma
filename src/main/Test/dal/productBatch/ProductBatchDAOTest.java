package dal.productBatch;

import dal.DALException;
import db.IConnPool;
import db.MySQL_DB;
import dto.productBatch.IProductBatchDTO;
import dto.productBatch.ProductBatchDTO;
import dto.productBatch.ProductBatchStatus_Enum;
import dto.rawMaterial.RawMaterialDTO;
import dto.rawMaterialBatch.RawMaterialBatchDTO;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ProductBatchDAOTest {

    IConnPool iConnPool = new MySQL_DB();
    ProductBatchDAO dao = new ProductBatchDAO();

    @Test
    public void createProductBatch() throws DALException {
        ProductBatchDTO test = new ProductBatchDTO();
        test.setAmount(3);
        test.setProductBatchID(1);
        test.setRecipeID(2);
        test.setStatus(ProductBatchStatus_Enum.done);

        //laver listen til usedrawmaterial
        List<RawMaterialBatchDTO> testList = new ArrayList<>();
        RawMaterialBatchDTO nyTing1 = new RawMaterialBatchDTO();
        RawMaterialBatchDTO nyTing2 = new RawMaterialBatchDTO();
        RawMaterialBatchDTO nyTing3 = new RawMaterialBatchDTO();
        nyTing1.setAmount(1);
        nyTing2.setAmount(2);
        nyTing3.setAmount(3);
        nyTing1.setRawMaterialBatchID(1);
        nyTing2.setRawMaterialBatchID(2);
        nyTing3.setRawMaterialBatchID(3);
        nyTing1.setResidue(true);
        nyTing2.setResidue(true);
        nyTing3.setResidue(true);
        testList.add(nyTing1);
        testList.add(nyTing2);
        testList.add(nyTing3);

        //smider den ind i test og forsætter og creater den med DAO'en
        test.setUsedRawMaterialBatches(testList);
        dao.createProductBatch(test);

        assertEquals(2,dao.getProductBatch(1).getRecipeID());

        try (Connection c = iConnPool.getConn()){
            String secureState2 = "DELETE FROM productBatch WHERE productBatch_id = 1";
            PreparedStatement state = c.prepareStatement(secureState2);
            state.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getProductBatch() throws DALException {

        ProductBatchDTO test = new ProductBatchDTO();
        test.setAmount(3);
        test.setProductBatchID(1);
        test.setRecipeID(2);
        test.setStatus(ProductBatchStatus_Enum.done);

        //laver listen til usedrawmaterial
        List<RawMaterialBatchDTO> testList = new ArrayList<>();
        RawMaterialBatchDTO nyTing1 = new RawMaterialBatchDTO();
        RawMaterialBatchDTO nyTing2 = new RawMaterialBatchDTO();
        RawMaterialBatchDTO nyTing3 = new RawMaterialBatchDTO();
        nyTing1.setAmount(1);
        nyTing2.setAmount(2);
        nyTing3.setAmount(3);
        nyTing1.setRawMaterialBatchID(1);
        nyTing2.setRawMaterialBatchID(2);
        nyTing3.setRawMaterialBatchID(3);
        nyTing1.setResidue(true);
        nyTing2.setResidue(true);
        nyTing3.setResidue(true);
        testList.add(nyTing1);
        testList.add(nyTing2);
        testList.add(nyTing3);

        //smider den ind i test og forsætter og creater den med DAO'en
        test.setUsedRawMaterialBatches(testList);
        dao.createProductBatch(test);

        assertEquals(3,dao.getProductBatch(1).getAmount());

        try (Connection c = iConnPool.getConn()){
            String secureState2 = "DELETE FROM productBatch WHERE productBatch_id = 1";
            PreparedStatement state = c.prepareStatement(secureState2);
            state.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getProductBatchList() throws DALException {
        for(int i = 0; i<3 ; i++){
            ProductBatchDTO test = new ProductBatchDTO();
            test.setAmount(i+1);
            test.setProductBatchID(i+1);
            test.setRecipeID(i+1);
            test.setStatus(ProductBatchStatus_Enum.done);

            //laver listen til usedrawmaterial
            List<RawMaterialBatchDTO> testList = new ArrayList<>();
            RawMaterialBatchDTO nyTing1 = new RawMaterialBatchDTO();
            RawMaterialBatchDTO nyTing2 = new RawMaterialBatchDTO();
            RawMaterialBatchDTO nyTing3 = new RawMaterialBatchDTO();
            nyTing1.setAmount(1);
            nyTing2.setAmount(2);
            nyTing3.setAmount(3);
            nyTing1.setRawMaterialBatchID(1);
            nyTing2.setRawMaterialBatchID(2);
            nyTing3.setRawMaterialBatchID(3);
            nyTing1.setResidue(true);
            nyTing2.setResidue(true);
            nyTing3.setResidue(true);
            testList.add(nyTing1);
            testList.add(nyTing2);
            testList.add(nyTing3);

            //smider den ind i test og forsætter og creater den med DAO'en
            test.setUsedRawMaterialBatches(testList);
            dao.createProductBatch(test);
    }

        assertEquals(3,dao.getProductBatchList().size());

        for (int i = 1; i<4; i++) {
            try (Connection c = iConnPool.getConn()) {
                String secureState2 = "DELETE FROM productBatch WHERE productBatch_id = " + i;
                PreparedStatement state = c.prepareStatement(secureState2);
                state.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void updateProductBatch() throws DALException {
        ProductBatchDTO test = new ProductBatchDTO();
        test.setAmount(2);
        test.setProductBatchID(1);
        test.setRecipeID(3);
        test.setStatus(ProductBatchStatus_Enum.done);

        //laver listen til usedrawmaterial
        List<RawMaterialBatchDTO> testList = new ArrayList<>();
        RawMaterialBatchDTO nyTing1 = new RawMaterialBatchDTO();
        RawMaterialBatchDTO nyTing2 = new RawMaterialBatchDTO();
        RawMaterialBatchDTO nyTing3 = new RawMaterialBatchDTO();
        nyTing1.setAmount(1);
        nyTing2.setAmount(2);
        nyTing3.setAmount(3);
        nyTing1.setRawMaterialBatchID(1);
        nyTing2.setRawMaterialBatchID(2);
        nyTing3.setRawMaterialBatchID(3);
        nyTing1.setResidue(true);
        nyTing2.setResidue(true);
        nyTing3.setResidue(true);
        testList.add(nyTing1);
        testList.add(nyTing2);
        testList.add(nyTing3);

        //smider den ind i test og forsætter og creater den med DAO'en
        test.setUsedRawMaterialBatches(testList);
        dao.createProductBatch(test);

        assertEquals(3,dao.getProductBatch(1).getRecipeID());

        test.setRecipeID(5);
        dao.createProductBatch(test);

        assertEquals(5, dao.getProductBatch(1).getRecipeID());

    }
}