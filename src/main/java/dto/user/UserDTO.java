package dto.user;

/**
 * @author Rasmus Sander Larsen
 */
public class UserDTO implements IUserDTO {

    /*
    -------------------------- Fields --------------------------
     */

    private int userID = 0;
    private String name;
    private UserRoleEnum userRole;
    private boolean isAdmin;
    private String userName;
    private String password;
    
    /*
    ----------------------- Constructor -------------------------
     */
    public UserDTO () {}

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
    
    public String toString () {
        StringBuilder toStringBuilder = new StringBuilder();
        if (userID != 0) {
            toStringBuilder.append("UserID: " + userID + ", ");
        }
        toStringBuilder.append("Name: " + name + ", ");
        toStringBuilder.append("UserName: " + userName + ", ");
        toStringBuilder.append("Password: " + password + ", ");
        toStringBuilder.append("Admin: " + isAdmin );
        if (userRole != null) {
            toStringBuilder.append(", UserRole: " + userRole);
        }

        return toStringBuilder.toString();
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
