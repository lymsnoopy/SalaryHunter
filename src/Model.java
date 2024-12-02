import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Model {
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

    public void updateBenefit(int jobID, String benefitType, String benefitName) throws SQLException {
        PreparedStatement benefitUpdate = connection.prepareStatement(
            "{ CALL benefit_update(?, ?, ?) }"
        );
        benefitUpdate.setInt(1, jobID);
        benefitUpdate.setString(2, benefitType);
        benefitUpdate.setString(3, benefitName);
        benefitUpdate.executeQuery();
    }

    public void deleteBenefit(int jobID) throws SQLException {
        PreparedStatement benefitDelete = connection.prepareStatement(
            "{ CALL benefit_delete(?) }"
        );
        benefitDelete.setInt(1, jobID);
        benefitDelete.executeQuery();
    }

    public void updateSkill(int jobID, String skillName) throws SQLException {
        PreparedStatement skillUpdate = connection.prepareStatement(
            "{ CALL skill_update(?, ?, ?) }"
        );
        skillUpdate.setInt(1, jobID);
        skillUpdate.setString(2, skillName);
        skillUpdate.executeQuery();
    }

    public void deleteSkill(int jobID) throws SQLException {
        PreparedStatement skillDelete = connection.prepareStatement(
            "{ CALL skill_delete(?) }"
        );
        skillDelete.setInt(1, jobID);
        skillDelete.executeQuery();
    }

    public void updateInterview(int jobID, String interviewType, String description) throws SQLException {
        PreparedStatement interviewUpdate = connection.prepareStatement(
            "{ CALL interview_update(?, ?, ?) }"
        );
        interviewUpdate.setInt(1, jobID);
        interviewUpdate.setString(2, interviewType);
        interviewUpdate.setString(3, description);
        interviewUpdate.executeQuery();
    }

    public void deleteInterview(int jobID) throws SQLException {
        PreparedStatement interviewDelete = connection.prepareStatement(
            "{ CALL interview_delete(?) }"
        );
        interviewDelete.setInt(1, jobID);
        interviewDelete.executeQuery();
    }

    // public boolean recordSearch(String companyName, String stateAbbr, String area, String industry, String position) throws SQLException {
    //     try (PreparedStatement recordStmt = connection.prepareStatement(
    //         "{ CALL InsertSearchRecord(?, ?, ?, ?, ?) }")) {
    //         recordStmt.setString(1, companyName);
    //         recordStmt.setString(2, stateAbbr);
    //         recordStmt.setString(3, area);
    //         recordStmt.setString(4, industry);
    //         recordStmt.setString(5, position);
    //         int update = recordStmt.executeUpdate();
    //         return (update > 0);
    //     }   
    // }

    public ResultSet executeSearch(
        String area, String stateAbbr, String industryName, String companyBranch, String positionName, 
        Integer year, String degree, String universityName, Integer yearOfWork) throws SQLException {
        
        String sql = "{ CALL GetFilteredRecords(?, ?, ?, ?, ?, ?, ?, ?, ?) }";
    
        CallableStatement stmt = connection.prepareCall(sql); 
        stmt.setString(1, area);
        stmt.setString(2, stateAbbr);
        stmt.setString(3, industryName);
        stmt.setString(4, companyBranch);
        stmt.setString(5, positionName);
        if (year == null) {
            stmt.setNull(6, java.sql.Types.INTEGER);
        } else {
            stmt.setInt(6, year);
        }
        stmt.setString(7, degree);
        stmt.setString(8, universityName);
        if (yearOfWork == null) {
            stmt.setNull(9, java.sql.Types.INTEGER);
        } else {
            stmt.setInt(9, yearOfWork);
        }
        return stmt.executeQuery();
    }

}