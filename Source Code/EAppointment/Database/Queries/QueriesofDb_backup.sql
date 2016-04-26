-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema EAppointmentdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema EAppointmentdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `EAppointmentdb` DEFAULT CHARACTER SET utf8 ;
USE `EAppointmentdb` ;

-- -----------------------------------------------------
-- Table `EAppointmentdb`.`user_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EAppointmentdb`.`user_details` (
  `email_id` NVARCHAR(320) NOT NULL,
  `firstname` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `mobile` VARCHAR(10) NULL,
  `password` CHAR(56) NOT NULL,
  `user_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`email_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `EAppointmentdb`.`prospect_students`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EAppointmentdb`.`prospect_students` (
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` NVARCHAR(320) NOT NULL,
  `mobile` VARCHAR(10) NULL,
  PRIMARY KEY (`email`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `EAppointmentdb`.`appointments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EAppointmentdb`.`appointments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `student_email` NVARCHAR(320) NULL,
  `staff_email` NVARCHAR(320) NOT NULL,
  `date` VARCHAR(10) NOT NULL,
  `time` VARCHAR(10) NOT NULL,
  `appointment_status` VARCHAR(15) NOT NULL,
  `appointment_notes` LONGTEXT NULL,
  `prospect_email` NVARCHAR(320) NULL,
  PRIMARY KEY (`id`),
  INDEX `student_email_idx` (`student_email` ASC),
  INDEX `staff_email_idx` (`staff_email` ASC),
  INDEX `fk_prospect_student_email_idx` (`prospect_email` ASC),
  CONSTRAINT `fk_student_email`
    FOREIGN KEY (`student_email`)
    REFERENCES `EAppointmentdb`.`user_details` (`email_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_staff_email`
    FOREIGN KEY (`staff_email`)
    REFERENCES `EAppointmentdb`.`user_details` (`email_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_prospect_student_email`
    FOREIGN KEY (`prospect_email`)
    REFERENCES `EAppointmentdb`.`prospect_students` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `EAppointmentdb`.`courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EAppointmentdb`.`courses` (
  `course_id` INT NOT NULL AUTO_INCREMENT,
  `course_name` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`course_id`),
  UNIQUE INDEX `course_name_UNIQUE` (`course_name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `EAppointmentdb`.`user_courses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EAppointmentdb`.`user_courses` (
  `user_email` NVARCHAR(320) NOT NULL,
  `course_id` INT NOT NULL,
  INDEX `course_id_idx` (`course_id` ASC),
  INDEX `fk_user_email_idx` (`user_email` ASC),
  CONSTRAINT `fk_course_id`
    FOREIGN KEY (`course_id`)
    REFERENCES `EAppointmentdb`.`courses` (`course_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_email`
    FOREIGN KEY (`user_email`)
    REFERENCES `EAppointmentdb`.`user_details` (`email_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `EAppointmentdb`.`setup_appointments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `EAppointmentdb`.`setup_appointments` (
  `email` NVARCHAR(320) NOT NULL,
  `day` VARCHAR(10) NOT NULL,
  `start_time` TIME NOT NULL,
  `end_time` TIME NOT NULL,
  `duration` INT NULL DEFAULT 15,
  INDEX `fk_staff_email_idx` (`email` ASC),
  CONSTRAINT `fk_staff_mail`
    FOREIGN KEY (`email`)
    REFERENCES `EAppointmentdb`.`user_details` (`email_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
