import java.sql.SQLException;

public class controller {

    model model = new model();

    public void dbLogin(String databaseUsername, String databasePassword) throws SQLException {
        model.databaseLogin(databaseUsername, databasePassword);
    }
}
