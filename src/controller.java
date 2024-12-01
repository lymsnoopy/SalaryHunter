import java.math.BigDecimal;
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
    
    public ResultSet DisplayRecordBenefit(int jobID) throws SQLException {
        return model.ShowUserRecordBenefit(jobID);
    }

    public ResultSet DisplayRecordInterview(int jobID) throws SQLException {
        return model.ShowUserRecordInterview(jobID);
    }

    public ResultSet DisplayRecordSkill(int jobID) throws SQLException {
        return model.ShowUserRecordSkill(jobID);
    }

    public void callUpdateRecord(int jobID, String stateAbb, String companyName, String industryName, String positionName, int year, BigDecimal salaryAmount, String description, String degree, int yearOfWork, String universityName) throws SQLException {
        model.updateRecord(jobID, stateAbb, companyName, industryName, positionName, year, salaryAmount, description, degree, yearOfWork, universityName);
    }

    public void callDeleteRecord(int jobID) throws SQLException {
        model.deleteRecord(jobID);
    }

    public boolean recordSearchToDB(String companyName, String stateAbbr, String area, String industry, String position) throws SQLException {
        return model.recordSearch(companyName, stateAbbr, area, industry, position);
    }
    
    public List<Map<String, String>> executeSearchFromDB(String positionName, String area, String stateAbbr, String industryName, String companyBranch) throws SQLException {
        return model.executeSearch(positionName, area, stateAbbr, industryName, companyBranch);
    }
    
    public List<Map<String, String>> getInterviews(String username, String positionName) throws SQLException {
        return model.getInterviews(username, positionName);
    }
    
    public List<Map<String, String>> getBenefits(String username, String positionName) throws SQLException {
        return model.getBenefits(username, positionName);
    }
    
}
