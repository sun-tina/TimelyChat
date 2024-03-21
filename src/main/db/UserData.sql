create database chat;

USE chat;

CREATE TABLE `USERS` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(64) NOT NULL,
  `email` VARCHAR(64) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY username_UNIQUE (email)
);