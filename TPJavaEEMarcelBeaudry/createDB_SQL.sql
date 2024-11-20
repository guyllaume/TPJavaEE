CREATE DATABASE IF NOT EXISTS  cegep_gg_bd_tp;

-- Sélectionner la base de données
USE cegep_gg_bd_tp;

-- Création de la table products et categories si elles n'existent pas
DROP TABLE IF EXISTS `products`;
DROP TABLE IF EXISTS `categories`;

CREATE TABLE `categories`(
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `description` TEXT
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `products` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `price` DECIMAL(10,2) NOT NULL,
    `image_url` VARCHAR(255),
    `categories_id` BIGINT,
    FOREIGN KEY (categories_id) REFERENCES categories(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Données de test
INSERT INTO `categories` (name, description) VALUES
('Pokemon', 'Cartes a jouer de Pokemon'),
('Yu-Gi-Oh', 'Cartes a jouer de Yu-Gi-Oh'),
('Magic: The Gathering', 'Cartes a jouer de Magic: The Gathering');

INSERT INTO `products` (name, price, image_url, categories_id) VALUES
('Carte Pokemon Dracaufeu', 299.99, 'dracaufeu.png', 1),
('Carte Yu-Gi-Oh Dragon Blanc', 199.99, 'dragon-blanc.jpg', 2),
('Carte Magic Black Lotus', 999.99, 'black-lotus.jfif', 3);

COMMIT;

-- Création de la table roles si elle n'existe pas
DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `courriel` varchar(100) NOT NULL,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`courriel`, `role`)
);

-- Insertion d'un administrateur
INSERT INTO roles (courriel, role) VALUES
    ('guyllaumebeaudry@gmail.com','ADMIN');

-- Création de la table user si elle n'existe pas
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `prenom` varchar(45) NOT NULL,
  `nom` varchar(45) NOT NULL,
  `date_de_naissance` date NOT NULL,
  `telephone` varchar(10) NOT NULL,
  `courriel` varchar(100) NOT NULL,
  `mot_de_passe` varchar(30) NOT NULL,
  `adresse` varchar(100) NOT NULL,
  `ville` varchar(100) NOT NULL,
  `province` varchar(100) NOT NULL,
  `pays` varchar(50) NOT NULL,
  `code_postal` varchar(6) NOT NULL,
  `adresse_livraison` varchar(100) NOT NULL,
  `ville_livraison` varchar(100) NOT NULL,
  `province_livraison` varchar(100) NOT NULL,
  `pays_livraison` varchar(50) NOT NULL,
  `code_postal_livraison` varchar(6) NOT NULL,
  PRIMARY KEY (`courriel`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Insertion d'un administrateur
INSERT INTO `user` VALUES ('admin','istrateur','1995-01-01','1','guyllaumebeaudry@gmail.com','securitaire123','a','a','a','a','a','a','a','a','a','a');

commit;

