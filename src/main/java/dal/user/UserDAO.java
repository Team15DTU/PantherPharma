package dal.user;

import dal.DALException;
import db.IConnPool;
import dto.user.IUserDTO;
import dto.user.UserRoleEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class UserDAO implements IUserDAO {

    // Names on columns in the DB table: Jobs
    public enum columns {
        user_id, name, isAdmin, userName, password
    }

    /*
    -------------------------- Fields --------------------------
     */
    
    private IConnPool iConnPool;
    private final String TABLE_NAME = "user";

    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public UserDAO (IConnPool iConnPool) {

        this.iConnPool = iConnPool;

    }
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    /**
     * This method creates a new User in the DB.
     * @param userDTO This is the UserObject containing all the data that is inputted into the DB.
     * @return "True" if the User successfully were added the the DB and "False" if somethings fails.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public boolean createUser(IUserDTO userDTO) throws DALException {

        Connection c = iConnPool.getConn();

        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?,?,?,?,?)",
                TABLE_NAME, columns.user_id, columns.name, columns.isAdmin, columns.userName, columns.password);

        try {

            PreparedStatement pStatement = c.prepareStatement(query);
            pStatement.setInt(1,userDTO.getUserID());
            pStatement.setString(2, userDTO.getName());
            pStatement.setBoolean(3, userDTO.isAdmin());
            pStatement.setString(4, userDTO.getUserName());
            pStatement.setString(5, userDTO.getPassword());

            pStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }finally {
            iConnPool.releaseConnection(c);
        }
        return false;
    }

    /**
     * This method gets a IUserDTO object containing the information matching the inputted userID.
     *
     * @param userID This is the userID for the UserDTO object that is returned.
     * @return an IUserDTO object.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public IUserDTO getUser(int userID) throws DALException {
        return null;
    }

    /**
     * This method gets a List object containing IUserDTO objects matching all users in the DB.
     *
     * @return A list<IUserDTO> of all users in DB
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public List<IUserDTO> getUserList() throws DALException {
        return null;
    }

    /**
     * This method gets a List object containing IUserDTO objects matching all user of a certain UserRole.
     *
     * @param userRole This is the userRole that are looked for.
     * @return a List<IUserDTO> of all users with the inputted UserRole
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public List<IUserDTO> getUserByRoleList(UserRoleEnum userRole) throws DALException {
        return null;
    }

    /**
     * This method updates an existing user in the DB.
     * The user is updated with the information from the inputted IUserDTO object.
     *
     * @param userDTO contains the information that the existing user is updated with.
     * @return an int matching the number of columns that is changed as a result of this method.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public int updateUser(IUserDTO userDTO) throws DALException {
        return 0;
    }

    /**
     * This method "deletes" the user that matches the inputted userID from the DB.
     *
     * @param userID is the ID for the user that is "deleted".
     * @return "true" if the user is "deleted" and "false" if something fails.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public boolean deleteUser(int userID) throws DALException {
        return false;
    }

    /*
    ---------------------- Support Methods ----------------------
     */


}
