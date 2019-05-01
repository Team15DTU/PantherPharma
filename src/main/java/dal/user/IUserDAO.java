package dal.user;

import dal.DALException;
import dto.user.IUserDTO;
import dto.user.UserDTO;
import dto.user.UserRoleEnum;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public interface IUserDAO {
    
    /*
    ---------------------- Public Methods -----------------------
     */

    /**
     * This method creates a new User in the DB.
     * @param userDTO This is the UserObject containing all the data that is inputted into the DB.
     * @return "True" if the User successfully were added the the DB and "False" if somethings fails.
     * @throws DALException This methods throws a DALException.
     */
    boolean createUser (IUserDTO userDTO) throws DALException;

    /**
     * This method gets a IUserDTO object containing the information matching the inputted userID.
     * @param userID This is the userID for the UserDTO object that is returned.
     * @return an IUserDTO object.
     * @throws DALException This methods throws a DALException.
     */
    IUserDTO getUser (int userID) throws DALException;

    /**
     * This method gets a List object containing IUserDTO objects matching all users in the DB.
     * @return A list<IUserDTO> of all users in DB
     * @throws DALException This methods throws a DALException.
     */
    List<IUserDTO> getUserList () throws DALException;

    /**
     * This method gets a List object containing IUserDTO objects matching all user of a certain UserRole.
     * @param userRole This is the userRole that are looked for.
     * @return a List<IUserDTO> of all users with the inputted UserRole
     * @throws DALException This methods throws a DALException.
     */
    List<IUserDTO> getUserByRoleList (UserRoleEnum userRole) throws DALException;

    /**
     * This method updates an existing user in the DB.
     * The user is updated with the information from the inputted IUserDTO object.
     * @param userDTO contains the information that the existing user is updated with.
     * @return an int matching the number of columns that is changed as a result of this method.
     * @throws DALException This methods throws a DALException.
     */
    int updateUser (IUserDTO userDTO) throws DALException;

    /**
     * This method "deletes" the user that matches the inputted userID from the DB.
     * @param userID is the ID for the user that is "deleted".
     * @return "true" if the user is "deleted" and "false" if something fails.
     * @throws DALException This methods throws a DALException.
     */
    boolean deleteUser (int userID) throws DALException;

}
