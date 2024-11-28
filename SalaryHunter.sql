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
    year YEAR NOT NULL,
    salary_amount DECIMAL(10, 2) NOT NULL CHECK (salary_amount >= 0),
    company_id INT,
    FOREIGN KEY (company_id) REFERENCES Company_Branch(company_id)
		ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Benefit (
    benefit_name VARCHAR(64) PRIMARY KEY,
	benefit_type ENUM('Insurance', 'Holiday', 'Stock', 'Retirement', 'Family')
);

CREATE TABLE IF NOT EXISTS Job_Benefit (
	job_id INT,
    benefit_name VARCHAR(64),
    FOREIGN KEY (job_id) REFERENCES Job_Position(job_id)
		ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (benefit_name) REFERENCES Benifit(benefit_name)
		ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Background (
	background_id INT AUTO_INCREMENT PRIMARY KEY,
	degree_level ENUM('BS', 'MS', 'PhD'),
    university_name VARCHAR(64) NOT NULL,
    year_of_work INT NOT NULL
);

CREATE TABLE IF NOT EXISTS Skill (
	skill_name VARCHAR(64) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS User_Background (
	username VARCHAR(64) NOT NULL,
    background_id INT NOT NULL,
    FOREIGN KEY (username) REFERENCES Registered_User(username)
		ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (background_id) REFERENCES Background(background_id)
		ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS User_Skill (
	username VARCHAR(64) NOT NULL,
    skill_name VARCHAR(64) NOT NULL,
    FOREIGN KEY (username) REFERENCES Registered_User(username)
		ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (skill_name) REFERENCES Skill(skill_name)
		ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Interview (
	interview_id INT AUTO_INCREMENT PRIMARY KEY,
    interview_type ENUM('Online Assessment', 
						'Pre-Recorded Interview', 
						'Behavioral Interview', 
                        'Technical Interview', 
                        'Supervisor Interview'),
	description VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS User_Interview_Position (
	username VARCHAR(64) NOT NULL,
    interview_id INT NOT NULL,
    job_id INT NOT NULL,
    has_interview BOOLEAN NOT NULL DEFAULT FALSE,
    got_position BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (username) REFERENCES Registered_User(username),
    FOREIGN KEY (interview_id) REFERENCES Interview(interview_id),
    FOREIGN KEY (job_id) REFERENCES Job_Position(job_id)
);

INSERT INTO Registered_User (username, password)
VALUES 
    ('a', '123'),
    ('b', '456'),
    ('c', '789');

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
    ('Y', 'NY', 'AI'),
    ('X', 'CA', 'HealthCare');

INSERT INTO Job_Position (position_name, description, year, salary_amount, company_id)
VALUES 
    ('Software Engineer I', 'Develop applications', 2024, 120000.00, 1),
	('Software Engineer III', 'Design AI systems', 2024, 500000.00, 2),
	('Data Analyst', 'Analyze medical data', 2024, 120000.00, 3);
                 
INSERT INTO Benifit (benefit_name, benefit_type)
VALUES 
    ('401K', 'Retirement'),
	('Share Incentive Plan', 'Stock');

INSERT INTO Job_Benefit (job_id, benefit_name)
VALUES
    (1, '401K' ),
	(2, '401K'),
    (3, '401K'),
    (1, 'Share Incentive Plan');

INSERT INTO Background (degree_level, university_name, year_of_work)
VALUES 
    ('MS', 'Northeastern University', 1),
    ('PhD', 'Harvard University', 8),
    ('BS', 'Northeastern University', 0);

INSERT INTO User_Background (username, background_id) 
VALUES
    ('a', 3),
	('a', 1),
    ('b', 2),
    ('c', 3);

INSERT INTO Skill (skill_name)
VALUES 
    ('Python'),
    ('SQL'),
    ('R'),
    ('Machine Learning');

INSERT INTO User_Skill (username, skill_name)
VALUES 
    ('a', 'Python'),
    ('a', 'SQL'),
	('b', 'SQL'),
	('b', 'Python'),
    ('b', 'Machine Learning'),
	('c', 'R');

INSERT INTO Interview (interview_type, description)
VALUES 
    ('Technical Interview', 'Coding and algorithm problems'),
	('Technical Interview', 'Complex machine learning problems'),
	('Behavioral Interview', 'Assess behavioral skills and cultural fit');

INSERT INTO User_Interview_Position (username, interview_id, job_id, has_interview, got_position)
VALUES 
    ('a', 1, 1, TRUE, TRUE),
	('c', 3, 3, TRUE, TRUE),
    ('a', 3, 1, TRUE, FALSE),
	('b', 2, 2, TRUE, TRUE);

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
-- DELIMITER //
-- CREATE PROCEDURE user_record(
--     IN r_username VARCHAR(64)
-- )
-- BEGIN
--     SELECT sa.in_area, cb.industry_name, cb.company_name, jp.position_name, jp.description, 
--            jp.year, jp.salary_amount, be.benefit_type, be.benefit_name, ba.degree_level, 
--            ba.year_of_work, sk.skill_name, iv.type, iv.description FROM Registered_User
--         INNER JOIN User_Interview_Position USING username
--         INNER JOIN Interview AS iv USING interview_id
--         INNER JOIN Job_Position AS jp USING position_name
--         INNER JOIN Company_Branch AS cb USING company_id
--         INNER JOIN State_Area AS sa USING state_abbr
--         INNER JOIN Benefit AS be USING position_name
--         INNER JOIN User_Background USING username
--         INNER JOIN Background AS ba USING background_id
--         INNER JOIN User_Skill USING username
--         INNER JOIN Skill AS sk USING skill_name
--     WHERE username = r_username;
-- END //
-- DELIMITER ;
DELIMITER //

CREATE PROCEDURE GetFilteredRecords(
    IN p_positionName VARCHAR(64),
    IN p_area VARCHAR(16),
    IN p_stateAbbr CHAR(2),
    IN p_industryName VARCHAR(64),
    IN p_companyBranch VARCHAR(64)
)
BEGIN
    SELECT 
        uip.username,
        cb.company_name AS companyBranch,
        sa.state_abbr AS stateAbbr,
        sa.in_area AS area,
        jp.position_name AS positionName,
        jp.description AS positionDescription,
        jp.year,
        jp.salary_amount AS salaryAmount,
        i.interview_type AS interviewType,
        i.description AS interviewDescription,
        cb.industry_name AS industryName
    FROM 
        User_Interview_Position uip
    JOIN 
        Job_Position jp ON uip.position_name = jp.position_name
    JOIN 
        Company_Branch cb ON jp.company_id = cb.company_id
    JOIN 
        State_Area sa ON cb.state_abbr = sa.state_abbr
    JOIN 
        Interview i ON uip.interview_id = i.interview_id
    WHERE 
        (COALESCE(p_positionName, '') = '' OR jp.position_name LIKE CONCAT('%', p_positionName, '%'))
        AND (COALESCE(p_area, '') = '' OR sa.in_area = p_area)
        AND (COALESCE(p_stateAbbr, '') = '' OR sa.state_abbr = p_stateAbbr)
        AND (COALESCE(p_industryName, '') = '' OR cb.industry_name LIKE CONCAT('%', p_industryName, '%'))
        AND (COALESCE(p_companyBranch, '') = '' OR cb.company_name LIKE CONCAT('%', p_companyBranch, '%'));
END //

DELIMITER ;

DELIMITER //
CREATE PROCEDURE user_record(
    IN r_username VARCHAR(64)
)
BEGIN
    SELECT sa.in_area, cb.industry_name, cb.company_name, jp.position_name, jp.description, 
           jp.year, jp.salary_amount, be.benefit_type, be.benfit_name, ba.degree_level, 
           ba.year_of_work, sk.skill_name, iv.type, iv.description FROM Registered_User
        INNER JOIN User_Interview_Position USING username
        INNER JOIN Interview AS iv USING interview_id
        INNER JOIN Job_Position AS jp USING position_name
        INNER JOIN Company_Branch AS cb USING company_id
        INNER JOIN State_Area AS sa USING state_abbr
        INNER JOIN Benefit AS be USING position_name
        INNER JOIN User_Background USING username
        INNER JOIN Background AS ba USING background_id
        INNER JOIN User_Skill USING username
        INNER JOIN Skill AS sk USING skill_name
    WHERE username = r_username;
END //
DELIMITER ;



