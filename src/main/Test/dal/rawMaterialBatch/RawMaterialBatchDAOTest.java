package dal.rawMaterialBatch;

import dal.DALException;
import db.IConnPool;
import db.MySQL_DB;
import dto.rawMaterial.IRawMaterialDTO;
import dto.rawMaterialBatch.IRawMaterialBatchDTO;
import dto.rawMaterialBatch.RawMaterialBatchDTO;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RawMaterialBatchDAOTest {

    RawMaterialBatchDAO dao = new RawMaterialBatchDAO();
    IConnPool iConnPool = new MySQL_DB();

    @Test
    public void createRawMaterialBatch() throws DALException {
        IRawMaterialBatchDTO test = new RawMaterialBatchDTO();
        test.setResidue(false);
        test.setRawMaterialBatchID(1);
        test.setAmount(3);

        dao.createRawMaterialBatch(test);

        assertEquals(false,dao.getRawMaterialBatch(1).isResidue());

        try (Connection c = iConnPool.getConn()){
            String secureState2 = "DELETE FROM rawMaterialBatch WHERE rawMaterialBatch_id = 1";
            PreparedStatement state = c.prepareStatement(secureState2);
            state.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRawMaterialBatch() throws DALException {

        IRawMaterialBatchDTO test = new RawMaterialBatchDTO();
        test.setResidue(false);
        test.setRawMaterialBatchID(1);
        test.setAmount(5);

        dao.createRawMaterialBatch(test);

        assertEquals(1,dao.getRawMaterialBatch(1).getRawMaterialBatchID());

        try (Connection c = iConnPool.getConn()){
            String secureState2 = "DELETE FROM rawMaterialBatch WHERE rawMaterialBatch_id = 1";
            PreparedStatement state = c.prepareStatement(secureState2);
            state.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getRawMaterialBatchList() throws DALException {

        List<IRawMaterialBatchDTO> testList = new ArrayList<>();

        for (int i = 1; i<4; i++) {
            IRawMaterialBatchDTO test = new RawMaterialBatchDTO();
            test.setResidue(false);
            test.setRawMaterialBatchID(i);
            test.setAmount(i);

            dao.createRawMaterialBatch(test);
        }

        testList = dao.getRawMaterialBatchList();

        assertEquals(3,testList.size());

        for (int i = 1; i<4; i++){
            try (Connection c = iConnPool.getConn()){
                String secureState2 = "DELETE FROM rawMaterialBatch WHERE rawMaterialBatch_id = " + i;
                PreparedStatement state = c.prepareStatement(secureState2);
                state.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void updateRawMaterialBatch() throws DALException {
        IRawMaterialBatchDTO test = new RawMaterialBatchDTO();
        test.setResidue(false);
        test.setRawMaterialBatchID(1);
        test.setAmount(5);
        dao.createRawMaterialBatch(test);

        assertEquals(false,dao.getRawMaterialBatch(1).isResidue());

        test.setResidue(true);

        assertEquals(true, dao.getRawMaterialBatch(1).isResidue());

    }
}