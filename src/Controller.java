import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {
    // Instance of the Model.
    Model model = new Model();

    /**
     * Logs into the database.
     * 
     * @param databaseUsername The username to log into the database.
     * @param databasePassword The password to log into the database.
     * 
     * @throws SQLException
     */
    public void dbLogin(String databaseUsername, String databasePassword) throws SQLException {
        model.databaseLogin(databaseUsername, databasePassword);
    }

    /**
     * Checks if a user exists in the database.
     * 
     * @param appUsername The username to check.
     * 
     * @return true if the user exists, false otherwise.
     * 
     * @throws SQLException
     */
    public boolean checkUserExist(String appUsername) throws SQLException {
        return model.existsUsername(appUsername);
    }

    /**
     * Verifies if the provided password matches the stored password for a given user.
     * 
     * @param appUsername The username whose password is being verified.
     * @param password The password to verify.
     * 
     * @return true if the password matches, false otherwise.
     * 
     * @throws SQLException
     */
    public boolean checkPassword(String appUsername, String password) throws SQLException {
        return model.passwordMatch(appUsername, password);
    }

    /**
     * Registers a new user in the database.
     * 
     * @param newUsername The username for the new user.
     * @param newPassword The password for the new user.
     * 
     * @return true if the user was successfully added, false otherwise.
     * 
     * @throws SQLException
     */
    public boolean addUserToDB(String newUsername, String newPassword) throws SQLException {
        return model.addUser(newUsername, newPassword);
    }

    /**
     * Displays records for current log in user.
     * 
     * @param newUsername The username whose records are being fetched.
     * 
     * @return A list of maps representing user records.
     * 
     * @throws SQLException
     */
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
    
    /**
     * Displays benefits for a given job ID.
     * 
     * @param jobID The job ID to fetch benefits for.
     * 
     * @return A list of maps representing the benefits.
     * 
     * @throws SQLException
     */
    public List<Map<String, String>> DisplayRecordBenefit(int jobID) throws SQLException {
        List<Map<String, String>> benefit = new ArrayList<>();
        ResultSet rsb = model.ShowUserRecordBenefit(jobID);
        while (rsb.next()) {
            Map<String, String> rowb = new HashMap<>();
            rowb.put("benefitID", rsb.getString("benefit_id"));
            rowb.put("benefitType", rsb.getString("benefit_type"));
            rowb.put("benefitName", rsb.getString("benefit_name"));
            benefit.add(rowb);
        }
        return benefit;
    }

    /**
     * Displays interviews for a given job ID.
     * 
     * @param jobID The job ID to fetch benefits for.
     * 
     * @return A list of maps representing the interviews.
     * 
     * @throws SQLException
     */
    public List<Map<String, String>> DisplayRecordInterview(int jobID) throws SQLException {
        List<Map<String, String>> interview = new ArrayList<>();
        ResultSet rsi = model.ShowUserRecordInterview(jobID);
        while (rsi.next()) {
            Map<String, String> rowi = new HashMap<>();
            rowi.put("interviewID", rsi.getString("interview_id"));
            rowi.put("interviewType", rsi.getString("interview_type"));
            rowi.put("interviewDescription", rsi.getString("description"));
            interview.add(rowi);
        }
        return interview;
    }

    /**
     * Displays skills for a given job ID.
     * 
     * @param jobID The job ID to fetch benefits for.
     * 
     * @return A list of maps representing the skills.
     * 
     * @throws SQLException
     */
    public List<Map<String, String>> DisplayRecordSkill(int jobID) throws SQLException {
        List<Map<String, String>> skill = new ArrayList<>();
        ResultSet rss = model.ShowUserRecordSkill(jobID);
        while (rss.next()) {
            Map<String, String> rows = new HashMap<>();
            rows.put("skillID", rss.getString("skill_id"));
            rows.put("skillName", rss.getString("skill_name"));
            skill.add(rows);
        }
        return skill;
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
    public void callUpdateRecord(int jobID, String stateAbb, String companyName, String industryName, String positionName, int year, BigDecimal salaryAmount, String description, String degree, int yearOfWork, String universityName) throws SQLException {
        model.updateRecord(jobID, stateAbb, companyName, industryName, positionName, year, salaryAmount, description, degree, yearOfWork, universityName);
    }

    /**
     * Deletes a job record from the database.
     * 
     * @param jobID The job ID to delete.
     * @throws SQLException
     */
    public void callDeleteRecord(int jobID) throws SQLException {
        model.deleteRecord(jobID);
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
    public void callUpdateBenefit(int jobID, int benefitID, String benefitType, String benefitName) throws SQLException {
        model.updateBenefit(jobID, benefitID, benefitType, benefitName);
    }

    /**
     * Deletes a benefit record from the database.
     * @param benefitID The ID of the benefit.
     * @param jobID The job ID to delete.
     * 
     * @throws SQLException
     */
    public void callDeleteBenefit(int jobID, int benefitID) throws SQLException {
        model.deleteBenefit(jobID, benefitID);
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
    public void callUpdateSkill(int jobID, int skillID, String skillName) throws SQLException {
        model.updateSkill(jobID, skillID, skillName);
    }

    /**
     * Deletes a skill record from the database.
     * 
     * @param jobID The job ID to delete.
     * @param skillID The ID of the skill.
     * 
     * @throws SQLException
     */
    public void callDeleteSkill(int jobID, int skillID) throws SQLException {
        model.deleteSkill(jobID, skillID);
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
    public void callUpdateInterview(int jobID, int interviewID, String interviewType, String description) throws SQLException {
        model.updateInterview(jobID, interviewID, interviewType, description);
    }

    /**
     * Deletes a interview record from the database.
     * 
     * @param jobID The job ID to delete.
     * @param interviewID The ID of the interview.
     * 
     * @throws SQLException
     */
    public void callDeleteInterview(int jobID, int interviewID) throws SQLException {
        model.deleteInterview(jobID, interviewID);
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
     * @return A list of maps representing search results.
     * 
     * @throws SQLException
     */
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

    /**
     * Retrieves a list of company names from the database.
     * 
     * @return A list of company names.
     * 
     * @throws SQLException
     */
    public List<String> searchCompanyList() throws SQLException {
        List<String> companyNames = new ArrayList<>();
        ResultSet rs = model.searchCompany();
        while (rs.next()) {
            companyNames.add(rs.getString("company_name"));
        }
        return companyNames;
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
    public boolean addRateToDB(String username, String companyBranch, int rate) throws SQLException {
        return model.addRate(username, companyBranch, rate);
    }

    /**
     * Displays the average rating for a given company branch.
     * 
     * @param CompanyBranch The company branch to get the rating for.
     * 
     * @return The average rating as a BigDecimal.
     * 
     * @throws SQLException
     */
    public BigDecimal showRate(String CompanyBranch) throws SQLException {
        return model.displayRate(CompanyBranch);
    }

    /**
     * Closes the database connection.
     * 
     * @throws SQLException
     */
    public void exit() throws SQLException {
        model.disconnect();
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
        return model.getOrCreateCompanyId(companyName, stateAbbr, industryName);
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
    public int insertJobPosition(String positionName, String description, int year, BigDecimal salary, int companyId, String username) throws SQLException {
        return model.addJobPosition(positionName, description, year, salary, companyId, username);
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
    public void insertBackground(int jobId, String degree, String universityName, int yearOfWork, String username) throws SQLException {
        model.addBackground(jobId, degree, universityName, yearOfWork, username);
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
    public void insertBenefit(int jobId, String benefitType, String benefitName) throws SQLException {
        model.addBenefit(jobId, benefitType, benefitName);
    }

    /**
     * Inserts a skill required for a given job position.
     * 
     * @param jobId The job ID to insert the skill for.
     * @param skillName The name of the skill.
     * 
     * @throws SQLException
     */
    public void insertSkill(int jobId, String skillName) throws SQLException {
        model.addSkill(jobId, skillName);
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
    public void insertInterview(int jobId, String interviewType, String description) throws SQLException {
        model.addInterview(jobId, interviewType, description);
    }
}
