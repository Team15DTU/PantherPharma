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
public class UserDAOTestMain {

    public static void main(String[] args) throws DALException {

        // MAIN FOR IUserDAO.
        // HERE WE WILL SHOWCASE THE IMPLEMENTED FUNCTIONS!

        System.out.println("Main For IUserDAO: \n");

        // region ConnPoll and DAO
        // This is the MySQL DB that the program is running on.
        IConnPool iConnPool = new MySQL_DB();

        // UserDAO
        IUserDAO userDAO = new UserDAO(iConnPool);

        // MultiTool
        MultiTool multiTool = new MultiTool(iConnPool);
        int nextID = multiTool.getNewAutoIncreasedValueInTable("user");

        // endregion

        // Creates a IUserDTO object and with predefined values and creates it in the DB.
        IUserDTO userDTO = new UserDTO("New User FirstName LastName", false, "user" + nextID, "password");
        userDTO.setUserRole(UserRoleEnum.farmaceut);
        System.out.println("Status for creation of UserDTO: " + userDAO.createUser(userDTO));

        // Gets a existing user from the DB.
        System.out.println("\nGetted UserDTO with ID = 24: ");
        IUserDTO user24 = userDAO.getUser(24);
        // User is printed.
        System.out.println(user24);

        // Variables of user24 is changed
        user24.setName("FirstName LastName // CHANGED");
        user24.setPassword("Password // CHANGED");
        user24.setAdmin(true);
        user24.setUserName("User26 // CHANGED");

        // DB is updated with users new information
        System.out.println("Number of values changed as a result of the update: " + userDAO.updateUser(user24));

        // Printed the updated User
        System.out.println("\nGetted UserDTO with ID = 24 (AFTER UPDATE): ");
        System.out.println(userDAO.getUser(24));

        System.out.println("\nUsers in list");
        for (IUserDTO user : userDAO.getUserList()) {
            System.out.println(user);
        }

        System.out.println("\nUsers in list of role \"farmaceut\": ");
        for (IUserDTO user : userDAO.getUserByRoleList(UserRoleEnum.farmaceut)) {
            System.out.println(user);
        }

        // region Sets Values back to "normal"
        user24.setName("FirstName LastName");
        user24.setPassword("Password");
        user24.setAdmin(false);
        user24.setUserName("User24");
        userDAO.updateUser(user24);

        // Deletes newly created User.
        userDAO.deleteUser(nextID);

        // endregion
    }

}
