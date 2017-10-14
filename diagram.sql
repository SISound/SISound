-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema soundcloud
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema soundcloud
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `soundcloud` DEFAULT CHARACTER SET utf8 ;
USE `soundcloud` ;

-- -----------------------------------------------------
-- Table `soundcloud`.`countries`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundcloud`.`countries` (
  `country_id` INT(11) NOT NULL AUTO_INCREMENT,
  `country_name` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`country_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundcloud`.`cities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundcloud`.`cities` (
  `city_id` INT(11) NOT NULL AUTO_INCREMENT,
  `city_name` TEXT NOT NULL,
  `country_id` INT(11) NOT NULL,
  PRIMARY KEY (`city_id`),
  INDEX `city_country_country_id` (`country_id` ASC),
  CONSTRAINT `city_country_country_id`
    FOREIGN KEY (`country_id`)
    REFERENCES `soundcloud`.`countries` (`country_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundcloud`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundcloud`.`users` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_name` TEXT NOT NULL,
  `user_password` VARCHAR(60) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `city_id` INT(11) NOT NULL,
  `bio` TEXT NULL DEFAULT NULL,
  `profile_pic` TEXT NULL DEFAULT NULL,
  `first_name` TEXT NULL DEFAULT NULL,
  `last_name` TEXT NULL DEFAULT NULL,
  `cover_photo` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `users_cities_city_id` (`city_id` ASC),
  CONSTRAINT `users_cities_city_id`
    FOREIGN KEY (`city_id`)
    REFERENCES `soundcloud`.`cities` (`city_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundcloud`.`follows`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundcloud`.`follows` (
  `follower_id` INT(11) NOT NULL,
  `followed_id` INT(11) NOT NULL,
  PRIMARY KEY (`follower_id`, `followed_id`),
  INDEX `followed_id` (`followed_id` ASC),
  CONSTRAINT `follows_ibfk_1`
    FOREIGN KEY (`follower_id`)
    REFERENCES `soundcloud`.`users` (`user_id`),
  CONSTRAINT `follows_ibfk_2`
    FOREIGN KEY (`followed_id`)
    REFERENCES `soundcloud`.`users` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundcloud`.`music_genres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundcloud`.`music_genres` (
  `genre_id` INT(11) NOT NULL,
  `genre_title` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`genre_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundcloud`.`playlists`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundcloud`.`playlists` (
  `playlist_id` INT(11) NOT NULL AUTO_INCREMENT,
  `playlist_name` VARCHAR(40) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `upload_date` DATETIME NOT NULL,
  `isPrivate` TINYINT(1) NOT NULL,
  PRIMARY KEY (`playlist_id`),
  INDEX `user_id` (`user_id` ASC),
  CONSTRAINT `playlists_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `soundcloud`.`users` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundcloud`.`playlists_comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundcloud`.`playlists_comments` (
  `comment_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `comment_text` TEXT NOT NULL,
  `upload_date` DATETIME NOT NULL,
  `playlist_id` INT(11) NOT NULL,
  `parent_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  INDEX `songs_comments_users_user_id` (`user_id` ASC),
  INDEX `songs_comments_playlists_playlist_id` (`playlist_id` ASC),
  INDEX `songs_comments_playlists_playlist_id_idx` (`parent_id` ASC),
  CONSTRAINT `songs_comments_comment_id`
    FOREIGN KEY (`parent_id`)
    REFERENCES `soundcloud`.`playlists_comments` (`comment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `songs_comments_playlists_playlist_id`
    FOREIGN KEY (`playlist_id`)
    REFERENCES `soundcloud`.`playlists` (`playlist_id`),
  CONSTRAINT `songs_comments_users_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `soundcloud`.`users` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundcloud`.`playlist_comments_likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundcloud`.`playlist_comments_likes` (
  `user_id` INT(11) NOT NULL,
  `comment_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `comment_id`),
  INDEX `pc_likes_playlist_comm_comment_id` (`comment_id` ASC),
  CONSTRAINT `pc_likes_playlist_comm_comment_id`
    FOREIGN KEY (`comment_id`)
    REFERENCES `soundcloud`.`playlists_comments` (`comment_id`),
  CONSTRAINT `pc_likes_users_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `soundcloud`.`users` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundcloud`.`playlists_dislikes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundcloud`.`playlists_dislikes` (
  `playlist_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`playlist_id`, `user_id`),
  INDEX `user_id` (`user_id` ASC),
  CONSTRAINT `playlists_dislikes_ibfk_1`
    FOREIGN KEY (`playlist_id`)
    REFERENCES `soundcloud`.`playlists` (`playlist_id`),
  CONSTRAINT `playlists_dislikes_ibfk_2`
    FOREIGN KEY (`user_id`)
    REFERENCES `soundcloud`.`users` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundcloud`.`playlists_likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundcloud`.`playlists_likes` (
  `playlist_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`playlist_id`, `user_id`),
  INDEX `user_id` (`user_id` ASC),
  CONSTRAINT `playlists_likes_ibfk_1`
    FOREIGN KEY (`playlist_id`)
    REFERENCES `soundcloud`.`playlists` (`playlist_id`),
  CONSTRAINT `playlists_likes_ibfk_2`
    FOREIGN KEY (`user_id`)
    REFERENCES `soundcloud`.`users` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundcloud`.`playlists_shares`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundcloud`.`playlists_shares` (
  `playlist_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`playlist_id`, `user_id`),
  INDEX `user_id` (`user_id` ASC),
  CONSTRAINT `playlists_shares_ibfk_1`
    FOREIGN KEY (`playlist_id`)
    REFERENCES `soundcloud`.`playlists` (`playlist_id`),
  CONSTRAINT `playlists_shares_ibfk_2`
    FOREIGN KEY (`user_id`)
    REFERENCES `soundcloud`.`users` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundcloud`.`songs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundcloud`.`songs` (
  `song_id` INT(11) NOT NULL AUTO_INCREMENT,
  `song_name` TEXT NOT NULL,
  `upload_date` DATETIME NOT NULL,
  `listenings` INT(11) NULL DEFAULT NULL,
  `user_id` INT(11) NOT NULL,
  `genre_id` INT(11) NOT NULL,
  `song_url` TEXT NOT NULL,
  PRIMARY KEY (`song_id`),
  INDEX `user_id` (`user_id` ASC),
  INDEX `songs_genres_genre_id` (`genre_id` ASC),
  CONSTRAINT `songs_genres_genre_id`
    FOREIGN KEY (`genre_id`)
    REFERENCES `soundcloud`.`music_genres` (`genre_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `songs_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `soundcloud`.`users` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundcloud`.`playlists_songs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundcloud`.`playlists_songs` (
  `playlist_id` INT(11) NOT NULL,
  `song_id` INT(11) NOT NULL,
  `upload_date` DATETIME NOT NULL,
  PRIMARY KEY (`playlist_id`, `song_id`),
  INDEX `song_id` (`song_id` ASC),
  CONSTRAINT `playlists_songs_ibfk_1`
    FOREIGN KEY (`playlist_id`)
    REFERENCES `soundcloud`.`playlists` (`playlist_id`),
  CONSTRAINT `playlists_songs_ibfk_2`
    FOREIGN KEY (`song_id`)
    REFERENCES `soundcloud`.`songs` (`song_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundcloud`.`songs_comments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundcloud`.`songs_comments` (
  `comment_id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `comment_text` TEXT NOT NULL,
  `upload_date` DATETIME NOT NULL,
  `song_id` INT(11) NULL DEFAULT NULL,
  `parent_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  INDEX `user_id` (`user_id` ASC),
  INDEX `comments_songs_song_id` (`song_id` ASC),
  INDEX `songs_comments_parent_id` (`parent_id` ASC),
  CONSTRAINT `comments_songs_song_id`
    FOREIGN KEY (`song_id`)
    REFERENCES `soundcloud`.`songs` (`song_id`),
  CONSTRAINT `songs_comments_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `soundcloud`.`users` (`user_id`),
  CONSTRAINT `songs_comments_parent_id`
    FOREIGN KEY (`parent_id`)
    REFERENCES `soundcloud`.`songs_comments` (`comment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundcloud`.`songs_comments_likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundcloud`.`songs_comments_likes` (
  `user_id` INT(11) NOT NULL,
  `comment_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `comment_id`),
  INDEX `comments_comments_comment_id_idx` (`comment_id` ASC),
  CONSTRAINT `songs_comments_likes_comments_comment_id`
    FOREIGN KEY (`comment_id`)
    REFERENCES `soundcloud`.`songs_comments` (`comment_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `songs_comments_likes_users_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `soundcloud`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundcloud`.`songs_dislikes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundcloud`.`songs_dislikes` (
  `user_id` INT(11) NOT NULL,
  `song_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `song_id`),
  INDEX `songs_song_id` (`song_id` ASC),
  CONSTRAINT `songs_song_id`
    FOREIGN KEY (`song_id`)
    REFERENCES `soundcloud`.`songs` (`song_id`),
  CONSTRAINT `users_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `soundcloud`.`users` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundcloud`.`songs_likes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundcloud`.`songs_likes` (
  `user_id` INT(11) NOT NULL,
  `song_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `song_id`),
  INDEX `song_id` (`song_id` ASC),
  CONSTRAINT `song_id`
    FOREIGN KEY (`song_id`)
    REFERENCES `soundcloud`.`songs` (`song_id`),
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `soundcloud`.`users` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundcloud`.`songs_shares`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundcloud`.`songs_shares` (
  `user_id` INT(11) NOT NULL,
  `song_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `song_id`),
  INDEX `song_id` (`song_id` ASC),
  CONSTRAINT `songs_shares_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `soundcloud`.`users` (`user_id`),
  CONSTRAINT `songs_shares_ibfk_2`
    FOREIGN KEY (`song_id`)
    REFERENCES `soundcloud`.`songs` (`song_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
