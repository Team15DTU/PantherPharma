package dal.multitool;

import dal.DALException;
import db.IConnPool;

import javax.swing.*;
import java.sql.*;

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
            ResultSet rs = statement.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int noOfColumns = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= noOfColumns; i++) {
                    if (i > 1) {
                        System.out.print(", ");
                    }

                    String columnValue = rs.getString(i);
                    System.out.print(rsmd.getColumnName(i) + ": " + columnValue );

                }
                System.out.println();
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
