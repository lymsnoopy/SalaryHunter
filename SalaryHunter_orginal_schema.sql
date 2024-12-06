DROP DATABASE IF EXISTS SalaryHunter;
CREATE DATABASE IF NOT EXISTS SalaryHunter;
USE SalaryHunter;

CREATE TABLE IF NOT EXISTS Registered_User (
	username VARCHAR(64) PRIMARY KEY,
    password VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS State_Area (
    state_abbr CHAR(2) PRIMARY KEY,
    state_name VARCHAR(64) NOT NULL,
    in_area ENUM('Northeast', 'Midwest', 'South', 'West') NOT NULL
);

CREATE TABLE IF NOT EXISTS Company_Branch (
    company_id INT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(64) NOT NULL,
    state_abbr CHAR(2) NOT NULL,
    industry_name VARCHAR(64) NOT NULL,
    FOREIGN KEY (state_abbr) REFERENCES State_Area(state_abbr)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Job_Position (
    job_id INT AUTO_INCREMENT PRIMARY KEY,
	position_name VARCHAR(64) NOT NULL,
    description VARCHAR(500) NOT NULL,
    year INT NOT NULL ,
    salary_amount DECIMAL(10, 2) NOT NULL CHECK (salary_amount >= 0),
    company_id INT,
    FOREIGN KEY (company_id) REFERENCES Company_Branch(company_id)
		ON UPDATE CASCADE ON DELETE CASCADE,
	username VARCHAR(64),
     FOREIGN KEY (username) REFERENCES Registered_User(username)
		ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Benefit (
	benefit_id INT AUTO_INCREMENT PRIMARY KEY,
    benefit_name VARCHAR(64) NOT NULL,
	benefit_type ENUM('Insurance', 'Holiday', 'Stock', 'Retirement', 'Family'),
    job_id INT,
    FOREIGN KEY (job_id) REFERENCES Job_Position(job_id)
		ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Interview (
	interview_id INT AUTO_INCREMENT PRIMARY KEY,
    interview_type ENUM('Online Assessment', 
						'Pre-Recorded Interview', 
						'Behavioral Interview', 
                        'Technical Interview', 
                        'Supervisor Interview'),
	description VARCHAR(500) NOT NULL,
    job_id INT,
    FOREIGN KEY (job_id) REFERENCES Job_Position(job_id)
		ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Background (
	background_id INT AUTO_INCREMENT PRIMARY KEY,
	degree_level ENUM('BS', 'MS', 'PhD'),
    university_name VARCHAR(64) NOT NULL,
    year_of_work INT NOT NULL,
    username VARCHAR(64),
    job_id INT,
    FOREIGN KEY (username) REFERENCES Registered_User(username)
		ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (job_id) REFERENCES Job_Position(job_id)
		ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Skill (
	skill_id INT AUTO_INCREMENT PRIMARY KEY,
	skill_name VARCHAR(64) NOT NULL,
    job_id INT,
    FOREIGN KEY (job_id) REFERENCES Job_Position(job_id)
		ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS User_Rate_Company (
	username VARCHAR(64),
    company_id INT,
    rate INT CHECK (rate >= 0 AND rate <= 5),
    FOREIGN KEY (username) REFERENCES Registered_User(username)
		ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (company_id) REFERENCES Company_Branch(company_id)
		ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO Registered_User (username, password)
VALUES 
    ('a', '123'),
    ('b', '456');

-- Insert State Area Data
INSERT INTO State_Area (state_abbr, state_name, in_area) 
VALUES 
    ('AL', 'Alabama', 'South'),
    ('AK', 'Alaska', 'West'),
    ('AZ', 'Arizona', 'West'),
    ('AR', 'Arkansas', 'South'),
    ('CA', 'California', 'West'),
    ('CO', 'Colorado', 'West'),
    ('CT', 'Connecticut', 'Northeast'),
    ('DE', 'Delaware', 'South'),
    ('FL', 'Florida', 'South'),
    ('GA', 'Georgia', 'South'),
    ('HI', 'Hawaii', 'West'),
    ('ID', 'Idaho', 'West'),
    ('IL', 'Illinois', 'Midwest'),
    ('IN', 'Indiana', 'Midwest'),
    ('IA', 'Iowa', 'Midwest'),
    ('KS', 'Kansas', 'Midwest'),
    ('KY', 'Kentucky', 'South'),
    ('LA', 'Louisiana', 'South'),
    ('ME', 'Maine', 'Northeast'),
    ('MD', 'Maryland', 'South'),
    ('MA', 'Massachusetts', 'Northeast'),
    ('MI', 'Michigan', 'Midwest'),
    ('MN', 'Minnesota', 'Midwest'),
    ('MS', 'Mississippi', 'South'),
    ('MO', 'Missouri', 'Midwest'),
    ('MT', 'Montana', 'West'),
    ('NE', 'Nebraska', 'Midwest'),
    ('NV', 'Nevada', 'West'),
    ('NH', 'New Hampshire', 'Northeast'),
    ('NJ', 'New Jersey', 'Northeast'),
    ('NM', 'New Mexico', 'West'),
    ('NY', 'New York', 'Northeast'),
    ('NC', 'North Carolina', 'South'),
    ('ND', 'North Dakota', 'Midwest'),
    ('OH', 'Ohio', 'Midwest'),
    ('OK', 'Oklahoma', 'South'),
    ('OR', 'Oregon', 'West'),
    ('PA', 'Pennsylvania', 'Northeast'),
    ('RI', 'Rhode Island', 'Northeast'),
    ('SC', 'South Carolina', 'South'),
    ('SD', 'South Dakota', 'Midwest'),
    ('TN', 'Tennessee', 'South'),
    ('TX', 'Texas', 'South'),
    ('UT', 'Utah', 'West'),
    ('VT', 'Vermont', 'Northeast'),
    ('VA', 'Virginia', 'South'),
    ('WA', 'Washington', 'West'),
    ('WV', 'West Virginia', 'South'),
    ('WI', 'Wisconsin', 'Midwest'),
    ('WY', 'Wyoming', 'West');

INSERT INTO Company_Branch (company_name, state_abbr, industry_name)
VALUES
    ('Z', 'MA', 'Technology'),
    ('Y', 'NY', 'AI');

INSERT INTO Job_Position (position_name, description, year, salary_amount, company_id, username)
VALUES 
    ('Software Engineer I', 'Full stack developer', 2022, 120000.00, 1, 'a'),
	('Software Engineer I', 'Develop applications', 2024, 150000.00, 2, 'a'),
	('Software Engineer I', 'Full stack developer', 2022, 120000.00, 1, 'b');
                 
INSERT INTO Benefit (benefit_name, benefit_type, job_id)
VALUES 
    ('401K', 'Retirement', 1),
	('401K', 'Retirement', 2),
	('401K', 'Retirement', 3),
	('Share Incentive Plan', 'Stock', 2);

INSERT INTO Background (degree_level, university_name, year_of_work, username, job_id)
VALUES 
	('BS', 'Northeastern University', 0, 'a', 1),
    ('MS', 'Northeastern University', 2, 'a', 2),
    ('BS', 'Northeastern University', 0, 'b', 3);
    
INSERT INTO Interview (interview_type, description, job_id)
VALUES 
    ('Technical Interview', 'Coding and algorithm problems', 1),
	('Technical Interview', 'Complex machine learning problems', 2),
	('Behavioral Interview', 'Assess behavioral skills and cultural fit', 2),
    ('Technical Interview', 'Coding and algorithm problems', 3);

INSERT INTO Skill (skill_name, job_id)
VALUES 
    ('Python', 1),
    ('Python', 2),
    ('Machine Learning', 2),
    ('Python', 3);
    
INSERT INTO User_Rate_Company (username, company_id, rate)
VALUES 
    ('a', 1, 4),
    ('a', 2, 5),
    ('b', 1, 5),
    ('b', 2, 4);

-- check if username exists in the database
DELIMITER //
CREATE FUNCTION check_user_exist(
	user VARCHAR(64) 
)
RETURNS BOOLEAN
DETERMINISTIC READS SQL DATA
BEGIN
	DECLARE user_exists BOOLEAN;
	SELECT EXISTS(SELECT 1 FROM Registered_User WHERE username = user) INTO user_exists;
    RETURN user_exists;
END //
DELIMITER ;

-- check if password is matched
DELIMITER //
CREATE FUNCTION password_match(
    user VARCHAR(64) 
)
RETURNS VARCHAR(64) 
DETERMINISTIC READS SQL DATA
BEGIN
    DECLARE user_password VARCHAR(64);
    SELECT password INTO user_password FROM Registered_User WHERE username = user;
    RETURN user_password;
END //
DELIMITER ;

-- register new user
DELIMITER //
CREATE PROCEDURE InsertUser(
    IN p_username VARCHAR(64),
    IN p_password VARCHAR(64)
)
BEGIN
    INSERT INTO Registered_User (username, password)
    VALUES (p_username, p_password);
END //
DELIMITER ;

-- display user record
DELIMITER //
CREATE PROCEDURE user_record (
    IN r_username VARCHAR(64)
)
BEGIN
	SELECT cb.state_abbr, cb.company_name, cb.industry_name,  jp.job_id, jp.position_name,  jp.year, jp.salary_amount, 
                     jp.description, ba.degree_level, ba.year_of_work, ba.university_name  FROM Registered_User AS ru
			INNER JOIN Job_Position AS jp USING (username)
			INNER JOIN Company_Branch AS cb USING (company_id)
			INNER JOIN Background AS ba USING (job_id)
		WHERE ru.username = r_username;
END //
DELIMITER ;

-- display benefit record
DELIMITER //
CREATE PROCEDURE record_benefit (
	IN r_job_id INT 
)
BEGIN
	SELECT be.benefit_id, be.benefit_name, be.benefit_type FROM Benefit AS be
		WHERE job_id = r_job_id;
END //
DELIMITER ;

-- display interview record
DELIMITER //
CREATE PROCEDURE record_interview (
	IN r_job_id INT 
)
BEGIN
	SELECT iv.interview_id, iv.interview_type, iv.description FROM Interview AS iv
		WHERE job_id = r_job_id;
END //
DELIMITER ;

-- display skill record
DELIMITER //
CREATE PROCEDURE record_skill (
	IN r_job_id INT 
)
BEGIN
	SELECT sk.skill_id, sk.skill_name FROM Skill AS sk
		WHERE job_id = r_job_id;
END //
DELIMITER ;

-- record update
DELIMITER //
CREATE PROCEDURE record_update (
	IN r_job_id INT,
	IN r_state_abbr CHAR(2),
    IN r_company_name VARCHAR(64),
    IN r_industry_name VARCHAR(64),
    IN r_position_name VARCHAR(64),
    IN r_year INT,
    IN r_salary_amount DECIMAL(10, 2),
    IN r_description VARCHAR(500),
    IN r_degree_level ENUM('BS', 'MS', 'PhD'),
    IN r_year_of_work INT, 
    IN r_university_name VARCHAR(64)
)
BEGIN
	START TRANSACTION;
	UPDATE Company_Branch 
    SET state_abbr = r_state_abbr, company_name = r_company_name, industry_name = r_industry_name
    WHERE company_id = (SELECT company_id FROM Job_Position WHERE job_id = r_job_id);
    
    UPDATE Job_Position 
    SET position_name = r_position_name, year = r_year, salary_amount = r_salary_amount, description = r_description
    WHERE job_id = r_job_id;
    
    UPDATE Background 
    SET degree_level = r_degree_level, year_of_work = r_year_of_work, university_name = r_university_name
    WHERE  job_id = r_job_id;
    COMMIT;
END //
DELIMITER ;

-- record delete 
DELIMITER //
CREATE PROCEDURE record_delete (
	IN r_job_id INT
)
BEGIN
DELETE  FROM Job_Position WHERE job_id = r_job_id;
END //
DELIMITER ;

-- benefit update
DELIMITER //
CREATE PROCEDURE benefit_update (
	IN r_job_id INT,
    IN r_benefit_id INT,
    IN r_benefit_type ENUM('Insurance', 'Holiday', 'Stock', 'Retirement', 'Family'),
    IN r_benefit_name VARCHAR(64)
)
BEGIN
	IF EXISTS (SELECT 1 FROM Benefit WHERE job_id = r_job_id AND benefit_id = r_benefit_id) THEN
	UPDATE Benefit
    SET benefit_name = r_benefit_name, benefit_type = r_benefit_type
		WHERE job_id = r_job_id AND benefit_id = r_benefit_id;
	ELSE
    INSERT INTO Benefit (job_id, benefit_name, benefit_type)
        VALUES (r_job_id, r_benefit_name, r_benefit_type);
	END IF;
END //
DELIMITER ;

-- benefit delete 
DELIMITER //
CREATE PROCEDURE benefit_delete (
	IN r_job_id INT,
    IN r_benefit_id INT
)
BEGIN
DELETE  FROM Benefit WHERE job_id = r_job_id AND benefit_id = r_benefit_id;
END //
DELIMITER ;

-- skill update
DELIMITER //
CREATE PROCEDURE skill_update (
	IN r_job_id INT,
    IN r_skill_id INT,
    IN r_skill_name VARCHAR(64)
)
BEGIN
	IF EXISTS (SELECT 1 FROM Skill WHERE job_id = r_job_id AND skill_id =  r_skill_id ) THEN
	UPDATE Skill
    SET skill_name = r_skill_name
		WHERE job_id = r_job_id AND skill_id =  r_skill_id;
	ELSE
    INSERT INTO Skill (job_id, skill_name)
    VALUES (r_job_id, r_skill_name);
	END IF;
END //
DELIMITER ;

-- skill delete 
DELIMITER //
CREATE PROCEDURE skill_delete (
	IN r_job_id INT,
    IN r_skill_id INT
)
BEGIN
DELETE  FROM Skill WHERE job_id = r_job_id AND skill_id =  r_skill_id ;
END //
DELIMITER ;

-- interview update
DELIMITER //
CREATE PROCEDURE interview_update (
	IN r_job_id INT,
    IN r_interview_id INT,
    IN r_interview_type ENUM (
		'Online Assessment', 
        'Pre-Recorded Interview', 
        'Behavioral Interview', 
        'Technical Interview', 
		'Supervisor Interview'),
	IN r_description VARCHAR(500)
)
BEGIN
IF EXISTS (SELECT 1 FROM Interview WHERE job_id = r_job_id AND interview_id = r_interview_id) THEN
	UPDATE Interview
    SET interview_type = r_interview_type, description = r_description
    WHERE job_id = r_job_id AND interview_id = r_interview_id;
ELSE
    INSERT INTO Interview (job_id, interview_type, description)
    VALUES (r_job_id, r_interview_type, r_description);
	END IF;
END //
DELIMITER ;

-- interview delete 
DELIMITER //
CREATE PROCEDURE interview_delete (
	IN r_job_id INT,
     IN r_interview_id INT
)
BEGIN
DELETE  FROM Interview WHERE job_id = r_job_id AND interview_id = r_interview_id;
END //
DELIMITER ;

-- search records
DELIMITER //
CREATE PROCEDURE GetFilteredRecords(
	IN p_in_area VARCHAR(256),
    IN p_stateAbbr VARCHAR(256),
    IN p_industryName VARCHAR(64),
    IN p_companyName VARCHAR(64),
    IN p_positionName VARCHAR(64),
    IN p_year INT,
    IN p_degree_level VARCHAR(20),
	IN p_university_name VARCHAR(64),
    IN p_year_of_work INT
)
BEGIN
    SET @sql_query = 'SELECT sa.in_area, cb.state_abbr, cb.industry_name,  cb.company_name, 
									  jp.job_id, jp.position_name,  jp.year, jp.salary_amount, jp.description, ba.degree_level, 
                                      ba.year_of_work, ba.university_name FROM Job_Position AS jp
                                      INNER JOIN Company_Branch AS cb USING (company_id)
                                      INNER JOIN State_Area AS sa USING (state_abbr) 
                                      INNER JOIN Background AS ba USING (job_id)
                                      WHERE 1=1';
	IF p_in_area IS NOT NULL AND p_in_area != '' THEN
        SET @sql_query = CONCAT(@sql_query, ' AND (sa.in_area = "', REPLACE(p_in_area, ',', '" OR sa.in_area = "'), '" )');
	ELSE
		SET @sql_query = CONCAT(@sql_query, ' AND sa.in_area != ""');
    END IF;

    IF p_stateAbbr IS NOT NULL AND p_stateAbbr != '' THEN
        SET @sql_query = CONCAT(@sql_query, ' AND (cb.state_abbr = "', REPLACE(p_stateAbbr, ',', '" OR cb.state_abbr = "'), '" )');
    ELSE
		SET @sql_query = CONCAT(@sql_query, ' AND cb.state_abbr != ""');
    END IF;

    IF p_industryName IS NOT NULL AND p_industryName != '' THEN
        SET @sql_query = CONCAT(@sql_query, ' AND cb.industry_name = "',  p_industryName, '"');
    END IF;

    IF p_companyName IS NOT NULL AND p_companyName != '' THEN
        SET @sql_query = CONCAT(@sql_query, ' AND cb.company_name = "', p_companyName, '"');
    END IF;

    IF p_positionName IS NOT NULL AND p_positionName != '' THEN
        SET @sql_query = CONCAT(@sql_query, ' AND jp.position_name = "', p_positionName, '"');
    END IF;

    IF p_year IS NOT NULL THEN
        SET @sql_query = CONCAT(@sql_query, ' AND jp.year = ', p_year);
    END IF;

    IF p_degree_level IS NOT NULL AND p_degree_level != '' THEN
        SET @sql_query = CONCAT(@sql_query, ' AND (ba.degree_level = "', REPLACE(p_degree_level, ',', '" OR ba.degree_level = "'), '" )');
	ELSE
    SET @sql_query = CONCAT(@sql_query, ' AND ba.degree_level != ""');
    END IF;

    IF p_university_name IS NOT NULL AND p_university_name != '' THEN
        SET @sql_query = CONCAT(@sql_query, ' AND ba.university_name = "', p_university_name, '"');
    END IF;

    IF p_year_of_work IS NOT NULL THEN
        SET @sql_query = CONCAT(@sql_query, ' AND ba.year_of_work = ', p_year_of_work);
    END IF;
    
    PREPARE stmt FROM @sql_query;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END //
DELIMITER ;

-- select company name
DELIMITER //
CREATE PROCEDURE SearchCompany()
BEGIN
	SELECT company_name FROM Company_Branch;
END //
DELIMITER ;

 -- add new rate
DELIMITER //
CREATE PROCEDURE InsertRate(
	IN p_username VARCHAR(64),
    IN p_company VARCHAR(64),
    IN p_rate INT
)
BEGIN
	DECLARE v_company_id INT;
    SELECT company_id INTO v_company_id FROM Company_Branch WHERE company_name = p_company;
    INSERT INTO User_Rate_Company(username, company_id, rate)
    VALUES (p_username, v_company_id, p_rate);
END //
DELIMITER ;

-- display average rate
DELIMITER //
CREATE FUNCTION DisplayRate(
	 p_company VARCHAR(64)
)
RETURNS DECIMAL(10, 2)
DETERMINISTIC READS SQL DATA
BEGIN
	DECLARE v_company_id INT;
    DECLARE v_avg_rate DECIMAL(10, 2);
    SELECT company_id INTO v_company_id FROM Company_Branch WHERE company_name = p_company;
    SELECT AVG(rate) INTO v_avg_rate FROM User_Rate_Company WHERE company_id = v_company_id;
    RETURN v_avg_rate;
END //
DELIMITER ;

-- when add record, get or create company id
DELIMITER //
CREATE FUNCTION GetCompanyID(
	p_company_name VARCHAR(64),
    p_state_abbr CHAR(2),
    p_industry_name VARCHAR(64)
)
RETURNS INT 
DETERMINISTIC MODIFIES SQL DATA
BEGIN
	DECLARE v_company_id INT;
    SELECT company_id INTO v_company_id FROM Company_Branch 
		WHERE company_name = p_company_name AND state_abbr = p_state_abbr;
	IF v_company_id IS NOT NULL THEN
		RETURN v_company_id;
	ELSE 
		INSERT INTO Company_Branch (company_name, state_abbr, industry_name)
        VALUES (p_company_name, p_state_abbr, p_industry_name);
        RETURN LAST_INSERT_ID();
	END IF;
END //
DELIMITER ;

-- insert job position and get the job id
DELIMITER //
CREATE FUNCTION GetJobID(
	p_position_name VARCHAR(64),
	p_description VARCHAR(500),
    p_year INT,
    p_salary_amount DECIMAL(10, 2),
	p_company_id INT,
    p_username VARCHAR(64)
)
RETURNS INT 
DETERMINISTIC MODIFIES SQL DATA
BEGIN
	INSERT INTO Job_Position (position_name, description, year, salary_amount, company_id, username)
	VALUES (p_position_name, p_description, p_year, p_salary_amount, p_company_id, p_username);
    RETURN LAST_INSERT_ID();
END //
DELIMITER ;

-- insert background
DELIMITER //
CREATE PROCEDURE insert_background(
	IN p_job_id INT,
    IN p_degree_level ENUM('BS', 'MS', 'PhD'),
    IN p_university_name VARCHAR(64),
    IN p_year_of_work INT,
    IN p_username VARCHAR(64)
)
BEGIN
	INSERT INTO Background (degree_level, university_name, year_of_work, username, job_id)
    VALUES(p_degree_level, p_university_name, p_year_of_work, p_username, p_job_id);
END //
DELIMITER ;

-- insert benefit
DELIMITER //
CREATE PROCEDURE insert_benefit(
	IN p_job_id INT,
    IN p_benefit_type ENUM('Insurance', 'Holiday', 'Stock', 'Retirement', 'Family'),
    IN p_benefit_name VARCHAR(64)
)
BEGIN
	INSERT INTO Benefit (benefit_type, benefit_name, job_id)
    VALUES(p_benefit_type, p_benefit_name, p_job_id);
END //
DELIMITER ;

-- insert skill
DELIMITER //
CREATE PROCEDURE insert_skill(
	IN p_job_id INT,
    IN p_skill_name VARCHAR(64)
)
BEGIN
	INSERT INTO Skill (skill_name, job_id)
    VALUES(p_skill_name, p_job_id);
END //
DELIMITER ;

-- insert interview
DELIMITER //
CREATE PROCEDURE insert_interview(
	IN r_job_id INT,
    IN r_interview_type ENUM (
		'Online Assessment', 
        'Pre-Recorded Interview', 
        'Behavioral Interview', 
        'Technical Interview', 
		'Supervisor Interview'),
	IN r_description VARCHAR(500)
)
BEGIN
	INSERT INTO Interview (interview_type, description, job_id)
    VALUES(r_interview_type, r_description, r_job_id);
END //
DELIMITER ;