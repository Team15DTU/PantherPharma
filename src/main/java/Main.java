import dal.DALException;
import dal.multitool.MultiTool;
import dal.user.IUserDAO;
import dal.user.UserDAO;
import db.IConnPool;
import db.MySQL_DB;
import dto.user.IUserDTO;
import dto.user.UserDTO;

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
        IUserDTO userDTO = new UserDTO("Rasmus Larsen", false, "jegerstadigkongen", "igætterdetaldrig");

        // Create user in DB
        //userDAO.createUser(userDTO);

        MultiTool multiTool = new MultiTool(iConnPool);
        multiTool.printResultOfQuery("DESC recipe");
    }

}
