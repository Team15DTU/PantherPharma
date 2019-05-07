package db;

import dal.DALException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public MySQL_DB () {}

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


}
