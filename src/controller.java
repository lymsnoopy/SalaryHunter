import java.sql.SQLException;

public class controller {

    model model = new model();

    public void dbLogin(String databaseUsername, String databasePassword) throws SQLException {
        model.databaseLogin(databaseUsername, databasePassword);
    }

    public boolean checkUserExist(String appUsername) throws SQLException {
        return model.existsUsername(appUsername);
    }

    public boolean checkPassword(String appUsername, String password) throws SQLException {
        return model.passwordMatch(appUsername, password);
    }

    public boolean addUserToDB(String newUsername, String newPassword) throws SQLException {
        return model.addUser(newUsername, newPassword);
    }
}
