CREATE DATABASE IF NOT EXISTS SalaryHunter;
USE SalaryHunter;
-- DROP TABLE Registered_User;
CREATE TABLE IF NOT EXISTS Registered_User (
	username VARCHAR(64) PRIMARY KEY,
    password VARCHAR(64) NOT NULL
);

INSERT INTO Registered_User (username, password) VALUES('romine24', 'Lym13416324524$');
-- SELECT * FROM Registered_User;

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