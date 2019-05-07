package dal.multitool;

import dal.DALException;
import db.IConnPool;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Rasmus Sander Larsen
 */
public class MultiTool {

    /*
    -------------------------- Fields --------------------------
     */
    
    private IConnPool iConnPool;
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public MultiTool (IConnPool iConnPool) {
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
    
    public void printResultOfQuery (String query) throws DALException {
        Connection c = iConnPool.getConn();

        try {
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

    }
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
