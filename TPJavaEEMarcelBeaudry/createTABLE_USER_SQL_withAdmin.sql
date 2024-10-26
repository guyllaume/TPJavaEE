USE cegep_gg_bd_tp;

/* Étape 2 */
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NOT NULL,
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
  FOREIGN KEY (`role_id`) REFERENCES roles(id),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

INSERT INTO `user` VALUES (1,7,'admin','istrateur','1995-01-01','1','guyllaumebeaudry@gmail.com','securitaire123!','a','a','a','a','a','a','a','a','a','a');

commit;
