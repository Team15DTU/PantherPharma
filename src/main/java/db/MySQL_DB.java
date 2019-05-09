package db;

import dal.DALException;

import java.sql.*;
import java.util.TimeZone;

/**
 * @author Rasmus Sander Larsen
 */
public class MySQL_DB implements IConnPool {

    /*
    -------------------------- Fields --------------------------
     */

    private final String url = "ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185082?";
    private final String user = "s185082";
    private final String password = "bUU1w4oFktMuJwrvRfSpB";

    /*
    ----------------------- Constructor -------------------------
     */

    public MySQL_DB () throws DALException {

        TimeZone.setDefault(TimeZone.getTimeZone(setTimeZoneFromSQLServer()));

    }

    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    public Connection getConn() throws DALException {
        try {
            return DriverManager.getConnection("jdbc:mysql://"+url,user, password);
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void releaseConnection(Connection connection) throws DALException {
        try {
            if (!connection.isClosed())
                connection.close();
        }
        catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    /*
    ---------------------- Support Methods ----------------------
     */

    private String setTimeZoneFromSQLServer ()  throws DALException{
        Connection c =getConn();
        try {
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT @@system_time_zone");
            resultSet.next();
            return resultSet.getString(1);

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            releaseConnection(c);
        }
    }

}
