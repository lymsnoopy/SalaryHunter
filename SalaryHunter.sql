CREATE DATABASE IF NOT EXISTS SalaryHunter;
USE SalaryHunter;

-- DROP TABLE Registered_User;
CREATE TABLE IF NOT EXISTS Registered_User (
	username VARCHAR(64) PRIMARY KEY,
    password VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS State_Area (
    state_abbr CHAR(2) PRIMARY KEY,
    state_name VARCHAR(64) NOT NULL,
    in_area ENUM('northeast', 'midwest', 'south', 'west') NOT NULL
);

CREATE TABLE IF NOT EXISTS Company_Branch (
	company_id INT PRIMARY KEY,
    company_name VARCHAR(64) NOT NULL,
    state_abbr CHAR(2) NOT NULL,
    industry_name VARCHAR(64) NOT NULL,
    FOREIGN KEY (state_abbr) REFERENCES State_Area(state_abbr) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Job_Position (
	position_name VARCHAR(64) PRIMARY KEY,
    description VARCHAR(500),
    year YEAR NOT NULL,
    salary_amount DECIMAL(10, 2) NOT NULL CHECK (salary_amount >= 0)
);

CREATE TABLE IF NOT EXISTS Benifit (
	benefit_type ENUM('Insurance', 'Holiday', 'Stock', 'Retirement', 'Family'),
    benefit_name VARCHAR(64) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS Background (
	username VARCHAR(64) NOT NULL,
	degree_level ENUM('BS', 'MS', 'PhD'),
    university_name VARCHAR(64) NOT NULL,
    year_of_work VARCHAR(2) NOT NULL,
    FOREIGN KEY (username) REFERENCES Registered_User(username)
		ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS SKill (
	skill_name VARCHAR(64) PRIMARY KEY
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

INSERT INTO Registered_User (username, password) VALUES('romine24', 'Lym13416324524$');
-- SELECT * FROM Registered_User;

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