USE cegep_gg_bd_tp;

DROP TABLE IF EXISTS `products`;

CREATE TABLE `products` (
    `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `description` TEXT,
    `price` DECIMAL(10,2) NOT NULL,
    `image_url` VARCHAR(255),
    `category` VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Données de test
INSERT INTO `products` (name, description, price, image_url, category) VALUES
('Carte Pokemon Dracaufeu', 'Carte rare Dracaufeu édition de base', 299.99, '/images/dracaufeu.jpg', 'Pokemon'),
('Carte Yu-Gi-Oh Dragon Blanc', 'Dragon Blanc aux Yeux Bleus', 199.99, '/images/dragon-blanc.jpg', 'Yu-Gi-Oh'),
('Carte Magic Black Lotus', 'Black Lotus Alpha Edition', 999.99, '/images/black-lotus.jpg', 'Magic');

COMMIT;