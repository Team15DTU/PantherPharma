import dal.DALException;
import dal.multitool.MultiTool;
import dal.user.IUserDAO;
import dal.user.UserDAO;
import db.IConnPool;
import db.MySQL_DB;
import dto.user.IUserDTO;
import dto.user.UserDTO;
import dto.user.UserRoleEnum;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class Main {

    public static void main(String[] args) throws DALException {

        // This is the MySQL DB that the program is running on.
        IConnPool iConnPool = new MySQL_DB();

        // UserDAO
        IUserDAO userDAO = new UserDAO(iConnPool);

        // User
        IUserDTO userDTO = new UserDTO("Rasmus Larsen", false, "megakongen", "igætterdetaldrig");
        IUserDTO userDTOUpdated = new UserDTO("Nicolaj Wassmann2", true, "megakongen2", "igætterdetaldrig");
        userDTO.setUserRole(UserRoleEnum.laborant);
        // Create user in DB
        //userDAO.createUser(userDTO);
        System.out.println("Getted User: ");
        System.out.println(userDAO.getUser(5));

        MultiTool multiTool = new MultiTool(iConnPool);
        //multiTool.printResultOfQuery("DESC farmaceut");
        //multiTool.printResultOfQuery("SHOW TABLES");
        multiTool.printResultOfQuery("SELECT * FROM user");

        List<IUserDTO> userList = userDAO.getUserList();
        System.out.println("Users in list");
        for (IUserDTO user : userList) {
            System.out.println(user);
        }

        userDTOUpdated.setUserID(15);
        userDTOUpdated.setUserRole(UserRoleEnum.laborant);
        System.out.println("before:");
        System.out.println(userDAO.getUser(15));

        int changes = userDAO.updateUser(userDTOUpdated);
        System.out.println("after:");
        System.out.println(userDAO.getUser(15));
        System.out.println(changes);

    }

}
