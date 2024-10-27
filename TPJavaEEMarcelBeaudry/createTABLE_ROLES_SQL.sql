USE cegep_gg_bd_tp;

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `courriel` varchar(100) NOT NULL,
  `role` varchar(20) NOT NULL,
  PRIMARY KEY (`courriel`, `role`)
);

-- Insertion des roles
INSERT INTO roles (courriel, role) VALUES
    ('guyllaumebeaudry@gmail.com','ADMIN');