package dto.user;

/**
 * @author Rasmus Sander Larsen
 */
public class UserDTO implements IUserDTO {

    /*
    -------------------------- Fields --------------------------
     */

    private int userID;
    private String name;
    private UserRoleEnum userRole;
    private boolean isAdmin;
    private String userName;
    private String password;
    
    /*
    ----------------------- Constructor -------------------------
     */

    public UserDTO ( String name, boolean isAdmin, String userName, String password) {
        this.name = name;
        this.isAdmin = isAdmin;
        this.userName = userName;
        this.password = password;
    }
    
    
    /*
    ------------------------ Properties -------------------------
     */

    // region Properties

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRoleEnum getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoleEnum userRole) {
        this.userRole = userRole;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    // endregion
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
