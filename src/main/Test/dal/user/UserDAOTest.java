package dal.user;

import dal.DALException;
import db.IConnPool;
import db.MySQL_DB;
import dto.user.IUserDTO;
import dto.user.UserDTO;
import dto.user.UserRoleEnum;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserDAOTest {


    IConnPool iConnPool = new MySQL_DB();
    UserDAO dao = new UserDAO(iConnPool);

    @Test
    public void createUser() throws DALException {
        
    }

    @Test
    public void getUser() throws DALException {
        assertEquals("Rasmus Larsen",dao.getUser(1).getName());
    }

    @Test
    public void getUserList() throws DALException {
        List<IUserDTO> testList = new ArrayList<>();
        testList = dao.getUserList();
        assertEquals("megakongen",testList.get(0).getUserName());
    }

    @Test
    public void getUserByRoleList() throws DALException {

        List<IUserDTO> TestList = new ArrayList<>();
        TestList = dao.getUserByRoleList(UserRoleEnum.laborant);
        assertEquals("Rasmus Larsen", TestList.get(0).getName());

    }

    @Test
    public void updateUser() throws DALException {

        UserDTO user = new UserDTO("Test", false,"megakongen","igætterdetaldrig");
        user.setUserID(1);
        dao.updateUser(user);
        assertEquals("Test",dao.getUser(1).getName());
        user.setName("Rasmus Larsen");
        dao.updateUser(user);
        assertEquals("Rasmus Larsen", dao.getUser(1).getName());
    }

    @Test
    public void deleteUser() throws DALException {
    }
}