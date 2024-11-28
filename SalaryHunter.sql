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
	position_name VARCHAR(64) PRIMARY KEY,
    description VARCHAR(500),
    year YEAR NOT NULL,
    salary_amount DECIMAL(10, 2) NOT NULL CHECK (salary_amount >= 0),
    company_id INT,
    FOREIGN KEY (company_id) REFERENCES Company_Branch(company_id)
		ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Benefit (
    benefit_name VARCHAR(64) PRIMARY KEY,
	benefit_type ENUM('Insurance', 'Holiday', 'Stock', 'Retirement', 'Family'),
    position_name VARCHAR(64),
    FOREIGN KEY (position_name) REFERENCES Job_Position(position_name)
		ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Background (
	background_id INT AUTO_INCREMENT PRIMARY KEY,
	degree_level ENUM('BS', 'MS', 'PhD'),
    university_name VARCHAR(64) NOT NULL,
    year_of_work VARCHAR(2) NOT NULL
);

CREATE TABLE IF NOT EXISTS SKill (
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
	FOREIGN KEY (skill_name) REFERENCES SKill(skill_name)
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
    position_name VARCHAR(64) NOT NULL,
    has_exprience BOOLEAN NOT NULL DEFAULT FALSE,
    got_position BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (username) REFERENCES Registered_User(username),
    FOREIGN KEY (interview_id) REFERENCES Interview(interview_id),
    FOREIGN KEY (position_name) REFERENCES Job_Position(position_name)
);

-- INSERT INTO Registered_User (username, password) VALUES('abc', '123456');

-- Insert Registered Users
INSERT INTO Registered_User (username, password) VALUES 
('user1', 'password1'),
('user2', 'password2'),
('user3', 'password3');

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


-- Insert Company Branches
INSERT INTO Company_Branch (company_name, state_abbr, industry_name) 
VALUES 
    ('Google', 'CA', 'Technology'),
    ('Amazon', 'WA', 'E-Commerce'),
    ('Tesla', 'NV', 'Automotive');

-- Insert Job Positions
INSERT INTO Job_Position (position_name, description, year, salary_amount, company_id) VALUES 
('Software Engineer 1', 'Develops software.', 2023, 120000.00, 1),
('Data Scientist', 'Analyzes data.', 2023, 130000.00, 2),
('Mechanical Engineer', 'Designs machinery.', 2023, 90000.00, 3);

-- Insert Benefits
INSERT INTO Benefit (benefit_name, benefit_type, position_name) VALUES 
('Health Insurance', 'Insurance', 'Software Engineer 1'),
('401k Plan', 'Retirement', 'Data Scientist'),
('Stock Options', 'Stock', 'Mechanical Engineer');

-- Insert Background Information
INSERT INTO Background (degree_level, university_name, year_of_work) VALUES 
('BS', 'Harvard University', '02'),
('MS', 'Stanford University', '05'),
('PhD', 'MIT', '08');

-- Insert Skills
INSERT INTO Skill (skill_name) VALUES 
('Java'),
('Python'),
('C++'),
('SQL'),
('Cloud Computing');

-- Insert User Backgrounds
INSERT INTO User_Background (username, background_id) VALUES 
('user1', 1),
('user2', 2),
('user3', 3);

-- Insert User Skills
INSERT INTO User_Skill (username, skill_name) VALUES 
('user1', 'Java'),
('user1', 'SQL'),
('user2', 'Python'),
('user2', 'Cloud Computing'),
('user3', 'C++'),
('user3', 'Python');

-- Insert Interviews
INSERT INTO Interview (interview_type, description) VALUES 
('Technical Interview', 'Coding questions and problem-solving'),
('Behavioral Interview', 'Questions about teamwork and leadership'),
('Online Assessment', 'Timed coding test');

-- Insert User Interview Positions
INSERT INTO User_Interview_Position (username, interview_id, position_name, has_exprience, got_position) VALUES 
('user1', 1, 'Software Engineer 1', TRUE, TRUE),
('user2', 2, 'Data Scientist', TRUE, FALSE),
('user3', 3, 'Mechanical Engineer', FALSE, FALSE);



-- DROP FUNCTION IF EXISTS check_user_exist;
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



