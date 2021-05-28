-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema provider
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema provider
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `provider` DEFAULT CHARACTER SET utf8 ;
USE `provider` ;

-- -----------------------------------------------------
-- Table `provider`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `provider`.`user` ;

CREATE TABLE IF NOT EXISTS `provider`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(15) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `account` DECIMAL(10,2) UNSIGNED NOT NULL,
  `active` TINYINT(1) NOT NULL,
  `activation_code` VARCHAR(36) NULL,
  `email` VARCHAR(60) NOT NULL,
  `language` ENUM('EN', 'UK') NOT NULL,
  `is_admin` TINYINT(1) ZEROFILL UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `provider`.`service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `provider`.`service` ;

CREATE TABLE IF NOT EXISTS `provider`.`service` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `nameEn` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  UNIQUE INDEX `nameEn_UNIQUE` (`nameEn` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `provider`.`tariff`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `provider`.`tariff` ;

CREATE TABLE IF NOT EXISTS `provider`.`tariff` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` DECIMAL(7,2) UNSIGNED NOT NULL,
  `service_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tariff_service_idx` (`service_id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  CONSTRAINT `fk_tariff_service`
    FOREIGN KEY (`service_id`)
    REFERENCES `provider`.`service` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `provider`.`user_has_service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `provider`.`user_has_service` ;

CREATE TABLE IF NOT EXISTS `provider`.`user_has_service` (
  `user_id` INT NOT NULL,
  `service_id` INT NOT NULL,
  `tariff_id` INT NULL,
  PRIMARY KEY (`user_id`, `service_id`),
  INDEX `fk_user_has_service_service1_idx` (`service_id` ASC) VISIBLE,
  INDEX `fk_user_has_service_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_service_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `provider`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_service_service1`
    FOREIGN KEY (`service_id`)
    REFERENCES `provider`.`service` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `provider`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `provider`;
INSERT INTO `provider`.`user` (`id`, `login`, `name`, `password`, `account`, `active`, `activation_code`, `email`, `language`, `is_admin`) VALUES (1, 'admin', 'Administrator', '123', 0, 1, NULL, 'admin@admin.com', 'EN', 1);
INSERT INTO `provider`.`user` (`id`, `login`, `name`, `password`, `account`, `active`, `activation_code`, `email`, `language`, `is_admin`) VALUES (2, 'user', 'User', '123', 10, 1, NULL, 'user@user.com', 'UK', 0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `provider`.`service`
-- -----------------------------------------------------
START TRANSACTION;
USE `provider`;
INSERT INTO `provider`.`service` (`id`, `name`, `nameEn`) VALUES (1, 'Інтернет', 'Internet');
INSERT INTO `provider`.`service` (`id`, `name`, `nameEn`) VALUES (2, 'Телефон', 'Phone');
INSERT INTO `provider`.`service` (`id`, `name`, `nameEn`) VALUES (3, 'Кабельне ТБ', 'Cable TV');
INSERT INTO `provider`.`service` (`id`, `name`, `nameEn`) VALUES (4, 'IP-TV', 'IP-TV');

COMMIT;

