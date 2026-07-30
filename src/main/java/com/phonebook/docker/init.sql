CREATE DATABASE IF NOT EXISTS phonebook;
USE phonebook;

CREATE TABLE IF NOT EXISTS contacts (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(100),
                                        lastName VARCHAR(100),
                                        email VARCHAR(100) UNIQUE NOT NULL,
                                        phone VARCHAR(50),
                                        address VARCHAR(255),
                                        description VARCHAR(255)
);