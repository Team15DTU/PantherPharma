import dal.DALException;
import dal.user.IUserDAO;
import dal.user.UserDAO;
import db.IConnPool;
import db.MySQL_DB;


/**
 * @author Rasmus Sander Larsen
 */
public class Main {

    public static void main(String[] args) throws DALException {

        IConnPool iConnPool = new MySQL_DB();

        IUserDAO userDAO = new UserDAO(iConnPool);


    }

}
