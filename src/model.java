import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class model {
    /* Connection with the database */
    private Connection connection = null;
    private final String dbName = "SalaryHunter";

    public void databaseLogin(String username, String password) throws SQLException {
        String serverName = "localhost";
        int portNumber = 3306;
        Properties connectionProps = new Properties();
        connectionProps.put("user", username);
        connectionProps.put("password", password);
        connection = DriverManager.getConnection("jdbc:mysql://"
                    + serverName + ":" + portNumber
                    + "/" + dbName
                    + "?characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true", connectionProps);
    }

    public boolean existsUsername(String username) throws SQLException {
        // check if username exists
        CallableStatement checkUser = connection.prepareCall(
            "{ ? = call check_user_exist(?) }"
        );
        checkUser.registerOutParameter(1, JDBCType.BOOLEAN);
        checkUser.setString(2, username);
        checkUser.execute();
        return checkUser.getBoolean(1);
    }

    public boolean passwordMatch(String username, String password) throws SQLException {
        CallableStatement checkPassword = connection.prepareCall(
            "{ ? = call password_match(?) }"
        );
        checkPassword.registerOutParameter(1, JDBCType.VARCHAR);
        checkPassword.setString(2, username);
        checkPassword.execute();
        String passwordIndb = checkPassword.getString(1);
        return (password.equals(passwordIndb));
    }

    public boolean addUser(String username, String password) throws SQLException {
        PreparedStatement insertUser = connection.prepareStatement(
            "{ CALL InsertUser(?, ?) }"
        );
        insertUser.setString(1, username);
        insertUser.setString(2, password);
        int update = insertUser.executeUpdate();
        return (update > 0);
    }

    public ResultSet ShowUserRecord(String username) throws SQLException {
        PreparedStatement userRecord = connection.prepareStatement(
            "{ CALL user_record(?) }"
        );
        userRecord.setString(1, username); 
        return userRecord.executeQuery();
    }

    public ResultSet ShowUserRecordBenefit(int jobID) throws SQLException {
        PreparedStatement userRecordBenefit = connection.prepareStatement(
            "{ CALL record_benefit(?) }"
        );
        userRecordBenefit.setInt(1, jobID); 
        return userRecordBenefit.executeQuery();
    }

    public ResultSet ShowUserRecordInterview(int jobID) throws SQLException {
        PreparedStatement userRecordInterview = connection.prepareStatement(
            "{ CALL record_interview(?) }"
        );
        userRecordInterview.setInt(1, jobID); 
        return userRecordInterview.executeQuery();
    }
    
    public ResultSet ShowUserRecordSkill(int jobID) throws SQLException {
        PreparedStatement userRecordSkill = connection.prepareStatement(
            "{ CALL record_skill(?) }"
        );
        userRecordSkill.setInt(1, jobID); 
        return userRecordSkill.executeQuery();
    }

    public void updateRecord(int jobID, String stateAbb, String companyName, String industryName, String positionName, int year, BigDecimal salaryAmount, String description, String degree, int yearOfWork, String universityName) throws SQLException {
        PreparedStatement recordUpdate = connection.prepareStatement(
            "{ CALL record_update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }"
        );
        recordUpdate.setInt(1, jobID);
        recordUpdate.setString(2, stateAbb);
        recordUpdate.setString(3, companyName);
        recordUpdate.setString(4, industryName);
        recordUpdate.setString(5, positionName);
        recordUpdate.setInt(6, year);
        recordUpdate.setBigDecimal(7, salaryAmount);
        recordUpdate.setString(8, description);
        recordUpdate.setString(9, degree);
        recordUpdate.setInt(10, yearOfWork);
        recordUpdate.setString(11, universityName);
        recordUpdate.executeQuery();
    }

    public void deleteRecord(int jobID) throws SQLException {
        PreparedStatement recordDelete = connection.prepareStatement(
            "{ CALL record_delete(?) }"
        );
        recordDelete.setInt(1, jobID);
        recordDelete.executeQuery();
    }

    public boolean recordSearch(String companyName, String stateAbbr, String area, String industry, String position) throws SQLException {
        try (PreparedStatement recordStmt = connection.prepareStatement(
            "{ CALL InsertSearchRecord(?, ?, ?, ?, ?) }")) {
            recordStmt.setString(1, companyName);
            recordStmt.setString(2, stateAbbr);
            recordStmt.setString(3, area);
            recordStmt.setString(4, industry);
            recordStmt.setString(5, position);
            int update = recordStmt.executeUpdate();
            return (update > 0);
        }   
    }

    public List<Map<String, String>> executeSearch(String positionName, String area, String stateAbbr, String industryName, String companyBranch) throws SQLException {
        List<Map<String, String>> results = new ArrayList<>();
        String sql = "{ CALL GetFilteredRecords(?, ?, ?, ?, ?) }";
    
        try (CallableStatement stmt = connection.prepareCall(sql)) {
            stmt.setString(1, positionName != null && !positionName.isEmpty() ? positionName : null);
            stmt.setString(2, area != null && !area.isEmpty() ? area : null);
            stmt.setString(3, stateAbbr != null && !stateAbbr.isEmpty() ? stateAbbr : null);
            stmt.setString(4, industryName != null && !industryName.isEmpty() ? industryName : null);
            stmt.setString(5, companyBranch != null && !companyBranch.isEmpty() ? companyBranch : null);
    
            System.out.println("Executing stored procedure: " + sql);
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("Query executed successfully.");
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    row.put("username", rs.getString("username"));
                    row.put("companyBranch", rs.getString("companyBranch"));
                    row.put("stateAbbr", rs.getString("stateAbbr"));
                    row.put("area", rs.getString("area"));
                    row.put("positionName", rs.getString("positionName"));
                    row.put("positionDescription", rs.getString("positionDescription"));
                    row.put("year", rs.getString("year"));
                    row.put("salaryAmount", rs.getString("salaryAmount"));
                    row.put("interviewType", rs.getString("interviewType"));
                    row.put("interviewDescription", rs.getString("interviewDescription"));
                    row.put("industryName", rs.getString("industryName"));
                    results.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing search: " + e.getMessage());
            throw e;
        }
        return results;
    }
    
    public List<Map<String, String>> getInterviews(String username, String positionName) throws SQLException {
        List<Map<String, String>> results = new ArrayList<>();
        String sql = "SELECT interview_type AS interviewType, description AS interviewDescription " +
                     "FROM Interview i JOIN User_Interview_Position uip ON i.interview_id = uip.interview_id " +
                     "WHERE uip.username = ? AND uip.position_name = ?";
    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, positionName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    row.put("interviewType", rs.getString("interviewType"));
                    row.put("interviewDescription", rs.getString("interviewDescription"));
                    results.add(row);
                }
            }
        }
        return results;
    }
    
    public List<Map<String, String>> getBenefits(String username, String positionName) throws SQLException {
        List<Map<String, String>> results = new ArrayList<>();
        String sql = "SELECT benefit_type AS benefitType, benefit_name AS benefitName " +
                     "FROM Benefit b JOIN User_Interview_Position uip ON b.position_name = uip.position_name " +
                     "WHERE uip.username = ? AND b.position_name = ?";
    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, positionName);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    row.put("benefitType", rs.getString("benefitType"));
                    row.put("benefitName", rs.getString("benefitName"));
                    results.add(row);
                }
            }
        }
        return results;
    }
    

}