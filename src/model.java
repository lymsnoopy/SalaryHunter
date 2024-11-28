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

    public List<Map<String, String>> executeSearch(String position, String area, String state, String industry, String company) throws SQLException {
        List<Map<String, String>> results = new ArrayList<>();
        String sql = "{ CALL GetFilteredRecords(?, ?, ?, ?, ?) }";

        try (CallableStatement stmt = connection.prepareCall(sql)) {
            // Bind parameters to the stored procedure
            stmt.setString(1, position != null && !position.isEmpty() ? position : null);
            stmt.setString(2, area != null && !area.isEmpty() ? area : null);
            stmt.setString(3, state != null && !state.isEmpty() ? state : null);
            stmt.setString(4, industry != null && !industry.isEmpty() ? industry : null);
            stmt.setString(5, company != null && !company.isEmpty() ? company : null);

            // Execute the stored procedure
            try (ResultSet rs = stmt.executeQuery()) {
                // Iterate through the result set and map results
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    row.put("username", rs.getString("username"));
                    row.put("company_name", rs.getString("company_name"));
                    row.put("state", rs.getString("state"));
                    row.put("area", rs.getString("area"));
                    row.put("position", rs.getString("position"));
                    row.put("position_description", rs.getString("position_description"));
                    row.put("year", rs.getString("year"));
                    row.put("salary_amount", rs.getString("salary_amount"));
                    row.put("interview", rs.getString("interview"));
                    row.put("interview_description", rs.getString("interview_description"));
                    results.add(row);
                }
            }
        }
        return results;
    }
}