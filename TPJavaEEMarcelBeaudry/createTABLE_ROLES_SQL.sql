USE cegep_gg_bd_tp;

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Insertion des roles
INSERT INTO roles (role) VALUES
    ('MEMBRE'),
    ('ADMIN');