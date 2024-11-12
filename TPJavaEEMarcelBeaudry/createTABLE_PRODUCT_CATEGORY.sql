USE cegep_gg_bd_tp;

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
('Carte Pokemon Dracaufeu', 299.99, '/images/dracaufeu.jpg', 1),
('Carte Yu-Gi-Oh Dragon Blanc', 199.99, '/images/dragon-blanc.jpg', 2),
('Carte Magic Black Lotus', 999.99, '/images/black-lotus.jpg', 3);

COMMIT;