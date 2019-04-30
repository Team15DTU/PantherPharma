package dal.user;

import dal.DALException;
import dto.user.IUserDTO;
import dto.user.UserDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public interface IUserDAO {
    
    /*
    ---------------------- Public Methods -----------------------
     */

    boolean createUser (IUserDTO userDTO) throws DALException;

    UserDTO getUser (int userID) throws DALException;

    List<IUserDTO> getUserList () throws DALException;

    int updateUser (IUserDTO userDTO) throws DALException;

    boolean deleteUser (int userID) throws DALException;


}
