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
        IUserDTO testUser = new UserDTO();
        testUser.setUserID(1);
        testUser.setName("jens jensen");
        testUser.setAdmin(false);
        testUser.setUserName("GingerBob");
        testUser.setPassword("HelloWorld");
        testUser.setUserRole(UserRoleEnum.farmaceut);
        dao.createUser(testUser);

        assertEquals("jens jensen", dao.getUser(1).getName());

        dao.deleteUser(1);
    }

    @Test
    public void getUser() throws DALException {
        IUserDTO testUser = new UserDTO();
        testUser.setUserID(2);
        testUser.setName("jens jensen");
        testUser.setAdmin(false);
        testUser.setUserName("GingerBob2");
        testUser.setPassword("HelloWorld");
        testUser.setUserRole(UserRoleEnum.farmaceut);
        dao.createUser(testUser);

        assertEquals("jens jensen", dao.getUser(2).getName());

        dao.deleteUser(2);
    }

    @Test
    public void getUserList() throws DALException {
        List<IUserDTO> testList = new ArrayList<>();
        testList = dao.getUserList();
        assertEquals("GingerBob",testList.get(0).getUserName());
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
        user.setUserID(10);
        user.setUserRole(UserRoleEnum.laborant);
        dao.createUser(user);

        assertEquals("Test",dao.getUser(10).getName());

        user.setName("Peter");
        dao.updateUser(user);

        assertEquals("Peter", dao.getUser(10).getName());

        dao.deleteUser(10);
    }

    @Test
    public void deleteUser() throws DALException {
    }
}