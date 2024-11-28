import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

    public ResultSet DisplayRecord(String newUsername) throws SQLException {
        return model.ShowUserRecord(newUsername);
    }
    
    public boolean recordSearchToDB(String companyName, String stateAbbr, String area, String industry, String position) throws SQLException {
        return model.recordSearch(companyName, stateAbbr, area, industry, position);
    }
    
    public List<Map<String, String>> executeSearchFromDB(String position, String area, String state, String industry, String company) throws SQLException {
        return model.executeSearch(position, area, state, industry, company);
    }

}
