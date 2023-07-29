-- Create the database 'assessment'
CREATE DATABASE IF NOT EXISTS assessment;

-- Create the 'leads' table
CREATE TABLE IF NOT EXISTS leads (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    father_name VARCHAR(255) NOT NULL,
    mother_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(30) NOT NULL,
    passport_number VARCHAR(30) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


#build cmd: mvn clean install -DskipTests

#run cmd :java -Xms2g -Xmx8g -jar assessment.jar
