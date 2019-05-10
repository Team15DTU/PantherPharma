package dal.rawMaterial;

import dal.DALException;
import db.IConnPool;
import db.MySQL_DB;
import dto.rawMaterial.IRawMaterialDTO;
import dto.rawMaterial.RawMaterialDTO;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RawMaterialDAOTest {

    IConnPool iConnPool = new MySQL_DB();
    RawMaterialDAO dao = new RawMaterialDAO(iConnPool);

    @Test
    public void createRawMaterial() throws DALException {
        RawMaterialDTO test = new RawMaterialDTO(6,"Citroner",5);
        dao.createRawMaterial(test);

        assertEquals("Citroner",dao.getRawMaterial(6).getName());
        try (Connection c = iConnPool.getConn()){
            String secureState2 = "DELETE FROM rawMaterial WHERE rawMaterial_id = 6";
            PreparedStatement state = c.prepareStatement(secureState2);
            state.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void getRawMaterial() throws DALException {
        RawMaterialDTO test = (RawMaterialDTO) dao.getRawMaterial(1);
        assertEquals("Æbler",test.getName());
    }


    @Test
    public void getRawMaterialList() throws DALException {

        List<IRawMaterialDTO> test = new ArrayList<>();
        test = dao.getRawMaterialList();

        assertEquals("Æbler",test.get(0).getName());
        assertEquals("Bananer", test.get(1).getName());
        assertEquals("Sukker", test.get(2).getName());
    }

    @Test
    public void updateRawMaterial() throws DALException {
        IRawMaterialDTO test = new RawMaterialDTO(3,"Citroner",5);
        dao.updateRawMaterial(test);

        assertEquals("Citroner",dao.getRawMaterial(3).getName());

        IRawMaterialDTO test2 = new RawMaterialDTO(3,"Sukker",5);
        dao.updateRawMaterial(test2);

        assertEquals("Sukker",dao.getRawMaterial(3).getName());
    }
}