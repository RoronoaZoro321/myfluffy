-- Drop tables if they exist
-- DROP TABLE IF EXISTS users;
-- DROP TABLE IF EXISTS post;
-- DROP TABLE IF EXISTS pet;
-- DROP TABLE IF EXISTS user;

-- Create Users Table
CREATE TABLE IF NOT EXISTS `users` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `phone` VARCHAR(20),
    `location` VARCHAR(100)
);

-- Create Pets Table
CREATE TABLE IF NOT EXISTS `pets` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `age` FLOAT NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `status` VARCHAR(20) NOT NULL CHECK (status IN ('MISSING', 'FOUND')),
    `owner_id` INT,
    FOREIGN KEY (`owner_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
);

-- Create Posts Table
CREATE TABLE IF NOT EXISTS `posts` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(255) NOT NULL,
    `content` VARCHAR(255) NOT NULL,
    `pet_id` INT,
    `owner_id` INT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`pet_id`) REFERENCES `pets`(id) ON DELETE CASCADE,
    FOREIGN KEY (`owner_id`) REFERENCES `users`(id) ON DELETE CASCADE
);


-- -- Insert a user into the user table
-- INSERT INTO `user` (id, name, email, phone, location_id) VALUES (1, 'John Doe', 'john@example.com', '0123456789', 'bangkok');
INSERT INTO users (name, email, phone, location)
VALUES ('John Doe', 'john.doe@example.com', '555-1234', '101 Mall St. New York');

INSERT INTO pets (name, age, description, status, owner_id)
VALUES ('Fluffy', 2.5, 'A playful and fluffy dog', 'MISSING', 1);

INSERT INTO posts (title, content, pet_id, owner_id)
VALUES ('Lost Pet', 'Fluffy has been missing for 2 days. Please help!', 1, 1);
