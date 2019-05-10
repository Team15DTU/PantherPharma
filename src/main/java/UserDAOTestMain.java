import dal.DALException;
import dal.multitool.MultiTool;
import dal.rawMaterialBatch.IRawMaterialBatchDAO;
import dal.rawMaterialBatch.RawMaterialBatchDAO;
import dal.user.IUserDAO;
import dal.user.UserDAO;
import db.IConnPool;
import db.MySQL_DB;
import dto.rawMaterialBatch.IRawMaterialBatchDTO;
import dto.user.IUserDTO;
import dto.user.UserDTO;
import dto.user.UserRoleEnum;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class UserDAOTestMain {

    public static void main(String[] args) throws DALException {

        // This is the MySQL DB that the program is running on.
        IConnPool iConnPool = new MySQL_DB();

        // UserDAO
        //IUserDAO userDAO = new UserDAO(iConnPool);

        RawMaterialBatchDAO rawMaterialBatchDAO = new RawMaterialBatchDAO(iConnPool);

        rawMaterialBatchDAO.orderRawMaterial();

        /*
        // User
        IUserDTO userDTO = new UserDTO("Rasmus Larsen", false, "kongen7", "igætterdetaldrig");
        IUserDTO userDTOUpdated = new UserDTO("Nicolaj Wassmann3", true, "megakongen2", "igætterdetaldrig");
        userDTO.setUserRole(UserRoleEnum.farmaceut);

        //userDAO.createUser(userDTO);

        // 29 Far
        IUserDTO user19 = userDAO.getUser(26);
        System.out.println(user19);
        //user19.setUserRole(UserRoleEnum.produktionsleder);
        //userDAO.updateUser(user19);
        */

        // Create user in DB
        //userDAO.createUser(userDTO);


        /*
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

        IUserDTO user14 = userDAO.getUser(14);
        user14.setUserRole(UserRoleEnum.farmaceut);

        userDTOUpdated.setUserRole(UserRoleEnum.farmaceut);
        int changes = userDAO.updateUser(user14);
        System.out.println("after:");
        System.out.println(userDAO.getUser(15));
        System.out.println(changes);

        */

    }

}
