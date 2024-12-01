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
	position_name VARCHAR(64),
    description VARCHAR(500),
    year INT NOT NULL,
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
    benefit_name VARCHAR(64),
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
	description VARCHAR(500),
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
	skill_name VARCHAR(64),
    job_id INT,
    FOREIGN KEY (job_id) REFERENCES Job_Position(job_id)
		ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS User_Rate_Company (
	username VARCHAR(64),
    job_id INT,
    rate INT CHECK (rate > 0 AND rate < 5),
    FOREIGN KEY (username) REFERENCES Registered_User(username)
		ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (job_id) REFERENCES Job_Position(job_id)
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
	SELECT be.benefit_name, be.benefit_type FROM Benefit AS be
		WHERE job_id = r_job_id;
END //
DELIMITER ;

-- display interview record
DELIMITER //
CREATE PROCEDURE record_interview (
	IN r_job_id INT 
)
BEGIN
	SELECT iv.interview_type, iv.description FROM Interview AS iv
		WHERE job_id = r_job_id;
END //
DELIMITER ;

-- display skill record
DELIMITER //
CREATE PROCEDURE record_skill (
	IN r_job_id INT 
)
BEGIN
	SELECT sk.skill_name FROM Skill AS sk
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
    
-- DELIMITER //
-- CREATE PROCEDURE GetFilteredRecords(
--     IN p_positionName VARCHAR(64),
--     IN p_area VARCHAR(16),
--     IN p_stateAbbr CHAR(2),
--     IN p_industryName VARCHAR(64),
--     IN p_companyBranch VARCHAR(64)
-- )
-- BEGIN
--     SELECT 
--         uip.username,
--         cb.company_name AS companyBranch,
--         sa.state_abbr AS stateAbbr,
--         sa.in_area AS area,
--         jp.position_name AS positionName,
--         jp.description AS positionDescription,
--         jp.year,
--         jp.salary_amount AS salaryAmount,
--         i.interview_type AS interviewType,
--         i.description AS interviewDescription,
--         cb.industry_name AS industryName
--     FROM 
--         User_Interview_Position uip
--     JOIN 
--         Job_Position jp ON uip.position_name = jp.position_name
--     JOIN 
--         Company_Branch cb ON jp.company_id = cb.company_id
--     JOIN 
--         State_Area sa ON cb.state_abbr = sa.state_abbr
--     JOIN 
--         Interview i ON uip.interview_id = i.interview_id
--     WHERE 
--         (COALESCE(p_positionName, '') = '' OR jp.position_name LIKE CONCAT('%', p_positionName, '%'))
--         AND (COALESCE(p_area, '') = '' OR sa.in_area = p_area)
--         AND (COALESCE(p_stateAbbr, '') = '' OR sa.state_abbr = p_stateAbbr)
--         AND (COALESCE(p_industryName, '') = '' OR cb.industry_name LIKE CONCAT('%', p_industryName, '%'))
--         AND (COALESCE(p_companyBranch, '') = '' OR cb.company_name LIKE CONCAT('%', p_companyBranch, '%'));
-- END //
-- DELIMITER ;