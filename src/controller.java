import java.sql.SQLException;
import java.util.List;

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

    public boolean recordSearchToDB(String companyName, String stateAbbr, String area, String industry, String position) throws SQLException {
        return model.recordSearch(companyName, stateAbbr, area, industry, position);
    }
    
    public void executeSearchFromDB(String query, List<String> parameters) throws SQLException {
        model.executeSearch(query, parameters);
    }
}
