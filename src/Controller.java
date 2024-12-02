import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    Model model = new Model();

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

    public List<Map<String, String>> DisplayRecord(String newUsername) throws SQLException {
        List<Map<String, String>> results = new ArrayList<>();
        ResultSet rs = model.ShowUserRecord(newUsername);
        while (rs.next()) {
            Map<String, String> row = new HashMap<>();
            row.put("state_abbr", rs.getString("state_abbr"));
            row.put("company_name", rs.getString("company_name"));
            row.put("industry_name", rs.getString("industry_name"));
            row.put("job_id", rs.getString("job_id"));
            row.put("position_name", rs.getString("position_name"));
            row.put("year", rs.getString("year"));
            row.put("salary_amount", rs.getString("salary_amount"));
            row.put("description", rs.getString("description"));
            row.put("degree_level", rs.getString("degree_level"));
            row.put("year_of_work", rs.getString("year_of_work"));
            row.put("university_name", rs.getString("university_name"));

            results.add(row);
        }
        return results;
    }
    
    public List<Map<String, String>> DisplayRecordBenefit(int jobID) throws SQLException {
        List<Map<String, String>> benefit = new ArrayList<>();
        ResultSet rsb = model.ShowUserRecordBenefit(jobID);
        while (rsb.next()) {
            Map<String, String> rowb = new HashMap<>();
            rowb.put("benefitType", rsb.getString("benefit_type"));
            rowb.put("benefitName", rsb.getString("benefit_name"));
            benefit.add(rowb);
        }
        return benefit;
    }

    public List<Map<String, String>> DisplayRecordInterview(int jobID) throws SQLException {
        List<Map<String, String>> interview = new ArrayList<>();
        ResultSet rsi = model.ShowUserRecordInterview(jobID);
        while (rsi.next()) {
            Map<String, String> rowi = new HashMap<>();
            rowi.put("interviewType", rsi.getString("interview_type"));
            rowi.put("interviewDescription", rsi.getString("description"));
            interview.add(rowi);
        }
        return interview;
    }

    public List<Map<String, String>> DisplayRecordSkill(int jobID) throws SQLException {
        List<Map<String, String>> skill = new ArrayList<>();
        ResultSet rss = model.ShowUserRecordSkill(jobID);
        while (rss.next()) {
            Map<String, String> rows = new HashMap<>();
            rows.put("skillName", rss.getString("skill_name"));
            skill.add(rows);
        }
        return skill;
    }

    public void callUpdateRecord(int jobID, String stateAbb, String companyName, String industryName, String positionName, int year, BigDecimal salaryAmount, String description, String degree, int yearOfWork, String universityName) throws SQLException {
        model.updateRecord(jobID, stateAbb, companyName, industryName, positionName, year, salaryAmount, description, degree, yearOfWork, universityName);
    }

    public void callDeleteRecord(int jobID) throws SQLException {
        model.deleteRecord(jobID);
    }

    public void callUpdateBenefit(int jobID, String benefitType, String benefitName) throws SQLException {
        model.updateBenefit(jobID, benefitType, benefitName);
    }

    public void callDeleteBenefit(int jobID) throws SQLException {
        model.deleteBenefit(jobID);
    }

    public void callUpdateSkill(int jobID, String skillName) throws SQLException {
        model.updateSkill(jobID, skillName);
    }

    public void callDeleteSkill(int jobID) throws SQLException {
        model.deleteSkill(jobID);
    }

    public void callUpdateInterview(int jobID, String interviewType, String description) throws SQLException {
        model.updateInterview(jobID, interviewType, description);
    }

    public void callDeleteInterview(int jobID) throws SQLException {
        model.deleteInterview(jobID);
    }

    public List<Map<String, String>> executeSearchFromDB(
        String area, String stateAbbr, String industryName, String companyBranch, String positionName, 
        Integer year, String degree, String universityName, Integer yearOfWork) throws SQLException {
        if (area == null) {
            area = "";
        }
        if (stateAbbr == null) {
            stateAbbr = "";
        }
        if (industryName == null) {
            industryName = "";
        }
        if (companyBranch.isEmpty()) {
            companyBranch = "";
        }
        if (positionName == null) {
            positionName = "";
        }
        if (degree == null) {
            degree = "";
        }
        if (universityName == null) {
            universityName = "";
        }
        
        ResultSet rs = model.executeSearch(area, stateAbbr, industryName, companyBranch, positionName, year, degree, universityName, yearOfWork);
        List<Map<String, String>> results = new ArrayList<>();
        while (rs.next()) {
            Map<String, String> row = new HashMap<>();
            row.put("area", rs.getString("in_area"));
            row.put("stateAbbr", rs.getString("state_abbr"));
            row.put("industryName", rs.getString("industry_name"));
            row.put("companyBranch", rs.getString("company_name"));
            row.put("jobID", rs.getString("job_id"));
            row.put("positionName", rs.getString("position_name"));
            row.put("positionDescription", rs.getString("description"));
            row.put("year", rs.getString("year"));
            row.put("salaryAmount", rs.getString("salary_amount"));
            row.put("degree", rs.getString("degree_level"));
            row.put("university", rs.getString("university_name"));
            row.put("yearOfWork", rs.getString("year_of_work"));
            results.add(row);
        }
        return results;
    }

    public List<String> searchCompanyList() throws SQLException {
        List<String> companyNames = new ArrayList<>();
        ResultSet rs = model.searchCompany();
        while (rs.next()) {
            companyNames.add(rs.getString("company_name"));
        }
        return companyNames;
    }

    public boolean addRateToDB(String username, String companyBranch, int rate) throws SQLException {
        return model.addRate(username, companyBranch, rate);
    }

    public BigDecimal showRate(String CompanyBranch) throws SQLException {
        return model.displayRate(CompanyBranch);
    }
    
}
