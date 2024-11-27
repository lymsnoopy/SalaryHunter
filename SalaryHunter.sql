DROP DATABASE IF EXISTS SalaryHunter;

CREATE DATABASE IF NOT EXISTS SalaryHunter;
USE SalaryHunter;

-- DROP TABLE IF EXISTS Registered_User ;
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

CREATE TABLE IF NOT EXISTS Benifit (
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

INSERT INTO Registered_User (username, password) VALUES('abc', '123456');

INSERT INTO State_Area (state_abbr, state_name, in_area) 
VALUES 
    ('AL', 'Alabama', 'south'),
    ('AK', 'Alaska', 'west'),
    ('AZ', 'Arizona', 'west'),
    ('AR', 'Arkansas', 'south'),
    ('CA', 'California', 'west'),
    ('CO', 'Colorado', 'west'),
    ('CT', 'Connecticut', 'northeast'),
    ('DE', 'Delaware', 'south'),
    ('FL', 'Florida', 'south'),
    ('GA', 'Georgia', 'south'),
    ('HI', 'Hawaii', 'west'),
    ('ID', 'Idaho', 'west'),
    ('IL', 'Illinois', 'midwest'),
    ('IN', 'Indiana', 'midwest'),
    ('IA', 'Iowa', 'midwest'),
    ('KS', 'Kansas', 'midwest'),
    ('KY', 'Kentucky', 'south'),
    ('LA', 'Louisiana', 'south'),
    ('ME', 'Maine', 'northeast'),
    ('MD', 'Maryland', 'south'),
    ('MA', 'Massachusetts', 'northeast'),
    ('MI', 'Michigan', 'midwest'),
    ('MN', 'Minnesota', 'midwest'),
    ('MS', 'Mississippi', 'south'),
    ('MO', 'Missouri', 'midwest'),
    ('MT', 'Montana', 'west'),
    ('NE', 'Nebraska', 'midwest'),
    ('NV', 'Nevada', 'west'),
    ('NH', 'New Hampshire', 'northeast'),
    ('NJ', 'New Jersey', 'northeast'),
    ('NM', 'New Mexico', 'west'),
    ('NY', 'New York', 'northeast'),
    ('NC', 'North Carolina', 'south'),
    ('ND', 'North Dakota', 'midwest'),
    ('OH', 'Ohio', 'midwest'),
    ('OK', 'Oklahoma', 'south'),
    ('OR', 'Oregon', 'west'),
    ('PA', 'Pennsylvania', 'northeast'),
    ('RI', 'Rhode Island', 'northeast'),
    ('SC', 'South Carolina', 'south'),
    ('SD', 'South Dakota', 'midwest'),
    ('TN', 'Tennessee', 'south'),
    ('TX', 'Texas', 'south'),
    ('UT', 'Utah', 'west'),
    ('VT', 'Vermont', 'northeast'),
    ('VA', 'Virginia', 'south'),
    ('WA', 'Washington', 'west'),
    ('WV', 'West Virginia', 'south'),
    ('WI', 'Wisconsin', 'midwest'),
    ('WY', 'Wyoming', 'west');

INSERT INTO Job_Position (position_name, description, year, salary_amount) 
	VALUES('SoftWare Engineer 1', 'software develop.', 2023, 100000.00);


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


