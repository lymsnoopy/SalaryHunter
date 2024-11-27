import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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

    public void executeSearch(String query, List<String> parameters) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.size(); i++) {
                stmt.setString(i + 1, parameters.get(i));
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getString("company_name"));
                }
            }
        }
    }

}