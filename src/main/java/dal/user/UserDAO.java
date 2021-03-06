package dal.user;

import dal.Columns;
import dal.ConnectionHelper;
import dal.DALException;
import dal.Tables;
import db.IConnPool;
import dto.user.IUserDTO;
import dto.user.UserDTO;
import dto.user.UserRoleEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class UserDAO implements IUserDAO {

    public enum columns {
        user_id, name, isAdmin, userName, password
    }

    /*
    -------------------------- Fields --------------------------
     */
    
    private IConnPool iConnPool;
    private ConnectionHelper connectionHelper;
    private final String TABLE_NAME = "user";

    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public UserDAO (IConnPool iConnPool) {

        this.iConnPool = iConnPool;
        connectionHelper = new ConnectionHelper(iConnPool);

    }
    
    /*
    ------------------------ Properties -------------------------
     */

    // region Properties

    // endregion
    
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

        int assignedUserID = -1;

        Connection c = iConnPool.getConn();

        String insertQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?,?,?,?,?)",
                Tables.user, Columns.user.user_id, Columns.user.name, Columns.user.isAdmin, Columns.user.userName, Columns.user.password);

        String roleQuery = String.format("INSERT INTO %s (%s) VALUES (?)",
                userDTO.getUserRole().toString(), userDTO.getUserRole().toString().concat("_id"));
        try {
            c.setAutoCommit(false);

            // region Creation of user
            PreparedStatement insertStatement = c.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement roleStatment = c.prepareStatement(roleQuery);

            insertStatement.setInt(1,userDTO.getUserID());
            insertStatement.setString(2, userDTO.getName());
            insertStatement.setBoolean(3, userDTO.isAdmin());
            insertStatement.setString(4, userDTO.getUserName());
            insertStatement.setString(5, userDTO.getPassword());

            insertStatement.executeUpdate();

            // endregion

            // region Get assigned userID

            ResultSet generatedKeysRS = insertStatement.getGeneratedKeys();

            if (generatedKeysRS.next()) {
                assignedUserID = generatedKeysRS.getInt(1);
            }

            // endregion

            // region Creation of role

            roleStatment.setInt(1, assignedUserID);

            roleStatment.executeUpdate();

            // endregion

            c.commit();
            return true;

        } catch (SQLException e) {
            connectionHelper.catchSQLExceptionAndDoRollback(c, e, "UserDAO.createUser");
            return false;
        } finally {
            connectionHelper.finallyActionsForConnection(c, "UserDAO.createUser");
        }
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

        // IUserDTO object to return.
        IUserDTO userDTOToReturn = new UserDTO();

        Connection c = iConnPool.getConn();

        // Get query
        String getQuery = String.format("SELECT * FROM %s WHERE %s = ?",
                Tables.user, Columns.user.user_id);

        String getLaborantId = String.format("SELECT %s FROM %s WHERE %s = ?",
                Columns.laborant.laborant_id, Tables.laborant, Columns.laborant.laborant_id);

        String getFarmaceutId = String.format("SELECT %s FROM %s WHERE %s = ?",
                Columns.farmaceut.farmaceut_id, Tables.farmaceut, Columns.farmaceut.farmaceut_id);

        String getproduktionslederId = String.format("SELECT %s FROM %s WHERE %s = ?",
                Columns.produktionsleder.produktionsleder_id, Tables.produktionsleder, Columns.produktionsleder.produktionsleder_id);

        try {

            // region Sets user with information from DB.
            PreparedStatement pStatement = c.prepareStatement(getQuery);
            pStatement.setInt(1, userID);

            ResultSet rs = pStatement.executeQuery();

            // Sets User with information from DB.
            while (rs.next()) {
                userDTOToReturn.setUserID(rs.getInt(Columns.user.user_id.toString()));
                userDTOToReturn.setName(rs.getString(Columns.user.name.toString()));
                userDTOToReturn.setUserName(rs.getString(Columns.user.userName.toString()));
                userDTOToReturn.setAdmin(rs.getBoolean(Columns.user.isAdmin.toString()));
                userDTOToReturn.setPassword(rs.getString(Columns.user.password.toString()));
            }
            // endregion

            // region Sets user with correct userRole

            PreparedStatement laborantPS = c.prepareStatement(getLaborantId);
            laborantPS.setInt(1,userID);

            ResultSet laborantRS = laborantPS.executeQuery();

            PreparedStatement farmaceutPS = c.prepareStatement(getFarmaceutId);
            farmaceutPS.setInt(1, userID);

            ResultSet farmaceutRS = farmaceutPS.executeQuery();

            PreparedStatement produktionslederPS = c.prepareStatement(getproduktionslederId);
            produktionslederPS.setInt(1, userID);

            ResultSet produktionslederRS = produktionslederPS.executeQuery();

            if(laborantRS.next()) {
                userDTOToReturn.setUserRole(UserRoleEnum.laborant);
            } else if (farmaceutRS.next()) {
                userDTOToReturn.setUserRole(UserRoleEnum.farmaceut);
            } else if (produktionslederRS.next()) {
                userDTOToReturn.setUserRole(UserRoleEnum.produktionsleder);
            } else{
                userDTOToReturn.setUserRole(null);
            }

            // endregion

        } catch (SQLException e ) {
            throw new DALException(e.getMessage());
        } finally {

            iConnPool.releaseConnection(c);
        }
        return userDTOToReturn;
    }

    /**
     * This method gets a List object containing IUserDTO objects matching all users in the DB.
     *
     * @return A list<IUserDTO> of all users in DB
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public List<IUserDTO> getUserList() throws DALException {

        // List to return. Containing all Users in DB.
        List<IUserDTO> userDTOListToReturn = new ArrayList<>();

        Connection c = iConnPool.getConn();

        String userIDQuery = String.format("SELECT %s FROM %s",
                Columns.user.user_id,Tables.user);

        try {
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery(userIDQuery);

            // Goes through all userIDs and gets a IUserDTO object which is added to the list.
            while (rs.next()) {

                int userID = rs.getInt(Columns.user.user_id.toString());
                // Gets and adds IUserDTO object.
                userDTOListToReturn.add(getUser(userID));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        return userDTOListToReturn;
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

        // List for all Users of the inputted role.
        List<IUserDTO> userDTOListToReturn = new ArrayList<>();

        Connection c = iConnPool.getConn();

        String userIDsInputtedRole = String.format("SELECT %s FROM %s",
                userRole+"_id", userRole);

        try {
            Statement statement = c.createStatement();
            ResultSet rs = statement.executeQuery(userIDsInputtedRole);

            // Gets userIDs for the users in the specified role and adds them to the list.
            while (rs.next()) {
                int userID = rs.getInt(userRole + "_id");

                // Get and adds user to list.
                userDTOListToReturn.add(getUser(userID));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }
        return userDTOListToReturn;
    }

    /**
     * This method updates an existing user in the DB.
     * The user is updated with the information from the inputted IUserDTO object.
     *
     * @param userDTO contains the information that the existing user is updated with.
     * @return an int matching the number of recipe that is changed as a result of this method.
     * @throws DALException This methods throws a DALException.
     */
    @Override
    public int updateUser(IUserDTO userDTO) throws DALException {

        int variablesChanged = 0;

        Connection c = iConnPool.getConn();

        IUserDTO userDTOBeforeUpdate = getUser(userDTO.getUserID());

        String updateNameQuery = String.format("UPDATE %s SET %s = ? WHERE %s = ?",
                Tables.user,Columns.user.name, Columns.user.user_id);

        String updateUserNameQuery = String.format("UPDATE %s SET %s = ? WHERE %s = ?",
                        Tables.user,Columns.user.userName, Columns.user.user_id);

        String updatePasswordQuery = String.format("UPDATE %s SET %s = ? WHERE %s = ?",
                Tables.user,Columns.user.password, Columns.user.user_id);

        String updateIsAdminQuery = String.format("UPDATE %s SET %s = ? WHERE %s = ?",
                Tables.user, Columns.user.isAdmin, Columns.user.user_id);

        String insertRoleQuery = String.format("INSERT INTO %s (%s) VALUES (?)",
                userDTO.getUserRole(),userDTO.getUserRole()+"_id");
        try {
            c.setAutoCommit(false);

            // region PreparedStatements

            PreparedStatement namePS = c.prepareStatement(updateNameQuery);
            PreparedStatement userNamePS = c.prepareStatement(updateUserNameQuery);
            PreparedStatement passwordPS = c.prepareStatement(updatePasswordQuery);
            PreparedStatement isAdminPS = c.prepareStatement(updateIsAdminQuery);
            PreparedStatement roleInsertPS = c.prepareStatement(insertRoleQuery);

            // endregion

            // region Checks is there is a change in users name and updates it if true
            if (!userDTO.getName().equals(userDTOBeforeUpdate.getName())) {
                setAndExecuteUpdatePreparedStatement(namePS, userDTO.getName(),userDTO.getUserID());
                variablesChanged++;
            }
            // endregion

            // region Checks is there is a change in users username and updates it if true
            if (!userDTO.getUserName().equals(userDTOBeforeUpdate.getUserName())) {
                setAndExecuteUpdatePreparedStatement(userNamePS, userDTO.getUserName(), userDTO.getUserID());
                variablesChanged++;
            }
            // endregion

            // region Checks is there is a change in users password and updates it if true
            if (!userDTO.getPassword().equals(userDTOBeforeUpdate.getPassword())) {
                setAndExecuteUpdatePreparedStatement(passwordPS, userDTO.getPassword(),userDTO.getUserID());
                variablesChanged++;
            }
            // endregion

            // region Checks is there is a change in users isAdmin and updates it if true
            if (userDTO.isAdmin() != userDTOBeforeUpdate.isAdmin()) {
                isAdminPS.setBoolean(1,userDTO.isAdmin());
                isAdminPS.setInt(2, userDTO.getUserID());

                isAdminPS.executeUpdate();

                variablesChanged++;
            }
            // endregion

            // region Checks is there is a change in users password and updates it if true
            if (!userDTO.getPassword().equals(userDTOBeforeUpdate.getPassword())) {
                setAndExecuteUpdatePreparedStatement(passwordPS, userDTO.getPassword(),userDTO.getUserID());
                variablesChanged++;
            }
            // endregion

            // region Check is a change in users role and updates it if true
            if (!userDTO.getUserRole().equals(userDTOBeforeUpdate.getUserRole())) {
                // INSERT INTO &s (%s) VALUES (?)
                roleInsertPS.setInt(1, userDTO.getUserID());

                roleInsertPS.executeUpdate();
                variablesChanged++;
            }
            // endregion

            c.commit();

        } catch (SQLException e) {
            connectionHelper.catchSQLExceptionAndDoRollback(c, e, "UserDAO.updateUser");
        } finally {
            connectionHelper.finallyActionsForConnection(c, "UserDAO.updateUser");
        }

        return variablesChanged;
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

        Connection c = iConnPool.getConn();

        String deleteUserQuery = String.format("DELETE FROM %s WHERE %s = ?",
                Tables.user, Columns.user.user_id);

        try {
            c.setAutoCommit(false);

            PreparedStatement deletePS = c.prepareStatement(deleteUserQuery);
            deletePS.setInt(1,userID);

            deletePS.executeUpdate();

            c.commit();
        } catch (SQLException e) {
                connectionHelper.catchSQLExceptionAndDoRollback(c,e, "UserDAO.deleteUser");
        } finally {
            connectionHelper.finallyActionsForConnection(c,"UserDAO.deleteUser");
        }

        return false;
    }

    /*
    ---------------------- Support Methods ----------------------
     */

    private void setAndExecuteUpdatePreparedStatement (PreparedStatement ps, String variable,  int userID) throws SQLException {
        ps.setString(1, variable);
        ps.setInt(2, userID);

        ps.executeUpdate();
    }
}
