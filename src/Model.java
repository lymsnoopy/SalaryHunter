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
    /* Connection with the database. */
    private Connection connection = null;
    private final String dbName = "SalaryHunter";  // Database name.

     /**
     * Logs into the database.
     * 
     * @param username The username to log into the database.
     * @param password The password to log into the database.
     * 
     * @throws SQLException
     */
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

    /**
     * Checks if a user exists in the database.
     * 
     * @param username The username to check.
     * 
     * @return true if the user exists, false otherwise.
     * 
     * @throws SQLException
     */
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

     /**
     * Verifies if the provided password matches the stored password for a given user.
     * 
     * @param username The username whose password is being verified.
     * @param password The password to verify.
     * 
     * @return true if the password matches, false otherwise.
     * 
     * @throws SQLException
     */
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

    /**
     * Registers a new user in the database.
     * 
     * @param username The username for the new user.
     * @param password The password for the new user.
     * 
     * @return true if the user was successfully added, false otherwise.
     * 
     * @throws SQLException
     */
    public boolean addUser(String username, String password) throws SQLException {
        PreparedStatement insertUser = connection.prepareStatement(
            "{ CALL InsertUser(?, ?) }"
        );
        insertUser.setString(1, username);
        insertUser.setString(2, password);
        int update = insertUser.executeUpdate();
        return (update > 0);
    }

    /**
     * Retrieve records for current log in user.
     * 
     * @param username The username whose records are being fetched.
     * 
     * @return A result set of maps representing user records.
     * 
     * @throws SQLException
     */
    public ResultSet ShowUserRecord(String username) throws SQLException {
        PreparedStatement userRecord = connection.prepareStatement(
            "{ CALL user_record(?) }"
        );
        userRecord.setString(1, username); 
        return userRecord.executeQuery();
    }

    /**
     * Retrieve benefits for a given job ID.
     * 
     * @param jobID The job ID to fetch benefits for.
     * 
     * @return A result set of maps representing the benefits.
     * 
     * @throws SQLException
     */
    public ResultSet ShowUserRecordBenefit(int jobID) throws SQLException {
        PreparedStatement userRecordBenefit = connection.prepareStatement(
            "{ CALL record_benefit(?) }"
        );
        userRecordBenefit.setInt(1, jobID); 
        return userRecordBenefit.executeQuery();
    }

    /**
     * Retrieve interviews for a given job ID.
     * 
     * @param jobID The job ID to fetch benefits for.
     * 
     * @return A result set of maps representing the interviews.
     * 
     * @throws SQLException
     */
    public ResultSet ShowUserRecordInterview(int jobID) throws SQLException {
        PreparedStatement userRecordInterview = connection.prepareStatement(
            "{ CALL record_interview(?) }"
        );
        userRecordInterview.setInt(1, jobID); 
        return userRecordInterview.executeQuery();
    }
    
    /**
     * Retrieve skills for a given job ID.
     * 
     * @param jobID The job ID to fetch benefits for.
     * 
     * @return A result set of maps representing the skills.
     * 
     * @throws SQLException
     */
    public ResultSet ShowUserRecordSkill(int jobID) throws SQLException {
        PreparedStatement userRecordSkill = connection.prepareStatement(
            "{ CALL record_skill(?) }"
        );
        userRecordSkill.setInt(1, jobID); 
        return userRecordSkill.executeQuery();
    }

     /**
     * Updates a job record with new information.
     * 
     * @param jobID The ID of the job to update.
     * @param stateAbb The state abbreviation.
     * @param companyName The company name.
     * @param industryName The industry name.
     * @param positionName The position name.
     * @param year The year of the job.
     * @param salaryAmount The salary amount for the job.
     * @param description The description of the job.
     * @param degree The degree required for the job.
     * @param yearOfWork The number of years of work experience.
     * @param universityName The university name.
     * 
     * @throws SQLException
     */
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

    /**
     * Deletes a job record from the database.
     * 
     * @param jobID The job ID to delete.
     * @throws SQLException
     */
    public void deleteRecord(int jobID) throws SQLException {
        PreparedStatement recordDelete = connection.prepareStatement(
            "{ CALL record_delete(?) }"
        );
        recordDelete.setInt(1, jobID);
        recordDelete.executeQuery();
    }

    /**
     * Updates a benefit record with new information.
     * 
     * @param jobID The ID of the job to update.
     * @param benefitID The ID of the benefit.
     * @param benefitType The type of the benefit.
     * @param benefitName The name of the benefit.
     * 
     * @throws SQLException
     */
    public void updateBenefit(int jobID, int benefitID, String benefitType, String benefitName) throws SQLException {
        PreparedStatement benefitUpdate = connection.prepareStatement(
            "{ CALL benefit_update(?, ?, ?, ?) }"
        );
        benefitUpdate.setInt(1, jobID);
        benefitUpdate.setInt(2, benefitID);
        benefitUpdate.setString(3, benefitType);
        benefitUpdate.setString(4, benefitName);
        benefitUpdate.executeQuery();
    }

    /**
     * Deletes a benefit record from the database.
     * 
     * @param jobID The job ID to delete.
     * @param benefitID The ID of the benefit.
     * @throws SQLException
     */
    public void deleteBenefit(int jobID, int benefitID) throws SQLException {
        PreparedStatement benefitDelete = connection.prepareStatement(
            "{ CALL benefit_delete(?, ?) }"
        );
        benefitDelete.setInt(1, jobID);
        benefitDelete.setInt(2, benefitID);
        benefitDelete.executeQuery();
    }

    /**
     * Updates a skill record with new information.
     * 
     * @param jobID The ID of the job to update.
     * @param skillID The ID of the skill.
     * @param skillName The name of the skill.
     * 
     * @throws SQLException
     */
    public void updateSkill(int jobID, int skillID, String skillName) throws SQLException {
        PreparedStatement skillUpdate = connection.prepareStatement(
            "{ CALL skill_update(?, ?, ?) }"
        );
        skillUpdate.setInt(1, jobID);
        skillUpdate.setInt(2, skillID);
        skillUpdate.setString(3, skillName);
        skillUpdate.executeQuery();
    }

    /**
     * Deletes a skill record from the database.
     * 
     * @param jobID The job ID to delete.
     * @param skillID The ID of the skill.
     * 
     * @throws SQLException
     */
    public void deleteSkill(int jobID, int skillID) throws SQLException {
        PreparedStatement skillDelete = connection.prepareStatement(
            "{ CALL skill_delete(?, ?) }"
        );
        skillDelete.setInt(1, jobID);
        skillDelete.setInt(2, skillID);
        skillDelete.executeQuery();
    }

    /**
     * Updates a interview record with new information.
     * 
     * @param jobID The ID of the job to update.
     * @param interviewID The ID of the interview.
     * @param interviewType The type of the interview.
     * @param description The description of the interview.
     * 
     * @throws SQLException
     */
    public void updateInterview(int jobID, int interviewID, String interviewType, String description) throws SQLException {
        PreparedStatement interviewUpdate = connection.prepareStatement(
            "{ CALL interview_update(?, ?, ?, ?) }"
        );
        interviewUpdate.setInt(1, jobID);
        interviewUpdate.setInt(2, interviewID);
        interviewUpdate.setString(3, interviewType);
        interviewUpdate.setString(4, description);
        interviewUpdate.executeQuery();
    }

    /**
     * Deletes a interview record from the database.
     * 
     * @param jobID The job ID to delete.
     * @param interviewID The ID of the interview.
     * 
     * @throws SQLException
     */
    public void deleteInterview(int jobID, int interviewID) throws SQLException {
        PreparedStatement interviewDelete = connection.prepareStatement(
            "{ CALL interview_delete(?, ?) }"
        );
        interviewDelete.setInt(1, jobID);
        interviewDelete.setInt(2, interviewID);
        interviewDelete.executeQuery();
    }

    /**
     * Executes a search in the database based on various search criteria.
     * 
     * @param area The area of the job.
     * @param stateAbbr The state abbreviation.
     * @param industryName The name of the industry.
     * @param companyBranch The company branch.
     * @param positionName The job position.
     * @param year The year of the job.
     * @param degree The degree required for the job.
     * @param universityName The name of the university.
     * @param yearOfWork The number of years of work experience required.
     * 
     * @return A result set of maps representing search results.
     * 
     * @throws SQLException
     */
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

    /**
     * Retrieves a list of company names from the database.
     * 
     * @return A list of company names.
     * 
     * @throws SQLException
     */
    public ResultSet searchCompany() throws SQLException {
        PreparedStatement searchCompany = connection.prepareStatement(
            "{ CALL SearchCompany() }"
        );
        return searchCompany.executeQuery();
    }

     /**
     * Adds a rating for a given company.
     * @param username The username submitting the rating.
     * @param companyBranch The company being rated.
     * @param rate The rating value.
     * 
     * @return true if the rating was added successfully, false otherwise.
     * 
     * @throws SQLException
     */
    public boolean addRate(String username, String CompanyBranch, int rate) throws SQLException {
        PreparedStatement addRate = connection.prepareStatement(
            "{ CALL InsertRate(?, ?, ?) }"
        );
        addRate.setString(1, username);
        addRate.setString(2, CompanyBranch);
        addRate.setInt(3, rate);
        int update = addRate.executeUpdate();
        return (update > 0);
    }

    /**
     * Retrieves the average rating for a given company branch.
     * 
     * @param CompanyBranch The company branch to get the rating for.
     * 
     * @return The average rating as a BigDecimal.
     * 
     * @throws SQLException
     */
    public BigDecimal displayRate(String CompanyBranch) throws SQLException {
        CallableStatement rate = connection.prepareCall(
            "{ ? = call DisplayRate(?) }"
        );
        rate.registerOutParameter(1, java.sql.Types.DECIMAL);
        rate.setString(2, CompanyBranch); 
        rate.execute();
        BigDecimal averageRate = rate.getBigDecimal(1);
        return averageRate;
    }

    /**
     * Closes the database connection.
     * 
     * @throws SQLException
     */
    public void disconnect() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
            System.exit(0);
        }
    }

    /**
     * Retrieves or creates a company ID.
     * 
     * @param companyName The company name.
     * @param stateAbbr The state abbreviation.
     * @param industryName The industry name.
     * 
     * @return The company ID.
     * 
     * @throws SQLException
     */
    public int getOrCreateCompanyId(String companyName, String stateAbbr, String industryName) throws SQLException {
        CallableStatement getCompanyID = connection.prepareCall(
            "{ ? = call GetCompanyID(?, ?, ?) }"
        );
        getCompanyID.registerOutParameter(1, java.sql.Types.INTEGER);
        getCompanyID.setString(2, companyName);
        getCompanyID.setString(3, stateAbbr); 
        getCompanyID.setString(4, industryName); 
        getCompanyID.execute();
        int id = getCompanyID.getInt(1);
        return id;
    }

    /**
     * Inserts a new job position into the database and returns the generated job ID.
     * 
     * @param positionName The name of the job position.
     * @param description A description of the job position.
     * @param year The year the job position was created.
     * @param salary The salary for the job.
     * @param companyId The ID of the company offering the job.
     * @param username The username of the person adding the job position.
     * 
     * @return The generated job ID.
     * 
     * @throws SQLException
     */
    public int addJobPosition(String positionName, String description, int year, BigDecimal salary, int companyId, String username) throws SQLException {
        CallableStatement getJobID = connection.prepareCall(
            "{ ? = call GetJobID(?, ?, ?, ?, ?, ?) }"
        );
        getJobID.registerOutParameter(1, java.sql.Types.INTEGER);
        getJobID.setString(2, positionName);
        getJobID.setString(3, description); 
        getJobID.setInt(4, year); 
        getJobID.setBigDecimal(5, salary); 
        getJobID.setInt(6, companyId); 
        getJobID.setString(7, username);
        getJobID.execute();
        int id = getJobID.getInt(1);
        return id;
    }

    /**
     * Inserts background information for a given job position.
     * 
     * @param jobId The job ID to insert the background information for.
     * @param degree The degree required for the position.
     * @param universityName The university name.
     * @param yearOfWork The number of years of work experience required.
     * @param username The username of the person adding the background information.
     * 
     * @throws SQLException
     */
    public void addBackground(int jobId, String degree, String universityName, int yearOfWork, String username) throws SQLException {
        CallableStatement background = connection.prepareCall(
            "{ CALL insert_background(?, ?, ?, ?, ?) }"
        );
        background.setInt(1, jobId);
        background.setString(2, degree); 
        background.setString(3, universityName);
        background.setInt(4, yearOfWork);
        background.setString(5, username); 
        background.executeQuery();
    }

    /**
     * Inserts a benefit for a given job position.
     * 
     * @param jobId The job ID to insert the benefit for.
     * @param benefitType The type of benefit.
     * @param benefitName The name of the benefit.
     * 
     * @throws SQLException
     */
    public void addBenefit(int jobId, String benefitType, String benefitName) throws SQLException {
        CallableStatement benefit = connection.prepareCall(
            "{ CALL insert_benefit(?, ?, ?) }"
        );
        benefit.setInt(1, jobId);
        benefit.setString(2, benefitType); 
        benefit.setString(3, benefitName); 
        benefit.executeQuery();
    }

    /**
     * Inserts a skill required for a given job position.
     * 
     * @param jobId The job ID to insert the skill for.
     * @param skillName The name of the skill.
     * 
     * @throws SQLException
     */
    public void addSkill(int jobId, String skillName) throws SQLException {
        CallableStatement skill = connection.prepareCall(
            "{ CALL insert_skill(?, ?) }"
        );
        skill.setInt(1, jobId);
        skill.setString(2, skillName); 
        skill.executeQuery();
    }

    /**
     * Inserts an interview record for a given job position.
     * 
     * @param jobId The job ID to insert the interview for.
     * @param interviewType The type of interview.
     * @param description A description of the interview process.
     * 
     * @throws SQLException
     */
    public void addInterview(int jobId, String interviewType, String description) throws SQLException {
        CallableStatement interview = connection.prepareCall(
            "{ CALL insert_interview(?, ?, ?) }"
        );
        interview.setInt(1, jobId);
        interview.setString(2, interviewType); 
        interview.setString(3, description); 
        interview.executeQuery();
    }
}
