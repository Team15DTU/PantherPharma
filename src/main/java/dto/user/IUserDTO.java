package dto.user;

/**
 * @author Rasmus Sander Larsen
 */
public interface IUserDTO {

    /*
    ---------------------- Public Methods -----------------------
     */

    // region Getters and Setters
    int getUserID();

    void setUserID(int userID);

    String getName();

    void setName(String name);

    UserRoleEnum getUserRole();

    void setUserRole(UserRoleEnum userRole);

    boolean isAdmin();

    void setAdmin(boolean admin);

    String getUserName();

    void setUserName(String userName);

    String getPassword();

    void setPassword(String password);

    // endregion

}
