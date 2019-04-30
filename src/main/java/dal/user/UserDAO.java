package dal.user;

import dal.DALException;
import dal.user.IUserDAO;
import dto.user.IUserDTO;
import dto.user.UserDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class UserDAO implements IUserDAO {

    /*
    -------------------------- Fields --------------------------
     */
    
    
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    public boolean createUser(IUserDTO userDTO) throws DALException {
        return false;
    }

    public UserDTO getUser(int userID) throws DALException {
        return null;
    }

    @Override
    public List<IUserDTO> getUserList() throws DALException {
        return null;
    }

    public int updateUser(IUserDTO userDTO) throws DALException {
        return 0;
    }

    public boolean deleteUser(int userID) throws DALException {
        return false;
    }

    /*
    ---------------------- Support Methods ----------------------
     */


}
