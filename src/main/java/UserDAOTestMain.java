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
        IUserDTO userDTO = new UserDTO("Rasmus Larsen", false, "user" + nextID, "password");
        userDTO.setUserRole(UserRoleEnum.farmaceut);
        System.out.println("Status for creation of UserDTO :" +userDAO.createUser(userDTO));

        // Gets a existing user from the DB.
        System.out.println("\nGetted UserDTO with ID = 24: ");
        IUserDTO user24 = userDAO.getUser(24);
        // User is printed.
        System.out.println(user24);

        // Variables of user19 is changed



        IUserDTO userDTOUpdated = new UserDTO("Nicolaj Wassmann3", true, "megakongen2", "igætterdetaldrig");


        // 29 Far
        //user19.setUserRole(UserRoleEnum.produktionsleder);
        //userDAO.updateUser(user19);


        // Create user in DB
        //userDAO.createUser(userDTO);



        System.out.println("Getted User: ");
        System.out.println(userDAO.getUser(5));

        List<IUserDTO> userList = userDAO.getUserList();
        System.out.println("Users in list");
        for (IUserDTO user : userList) {
            System.out.println(user);
        }
        userDAO.deleteUser(33);
        List<IUserDTO> userList2 = userDAO.getUserList();
        System.out.println("Users after list");
        for (IUserDTO user : userList2) {
            System.out.println(user);
        }

    }

}
