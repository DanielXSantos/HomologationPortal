CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  `users_user_id` int(11),
  PRIMARY KEY (`role_id`)
);
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `active` int(11) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `expiration_date` date NOT NULL,
  `fabricante_id` BIGINT,
  PRIMARY KEY (`user_id`),
  FOREIGN KEY (`fabricante_id`) REFERENCES `fabricante`(`id`)
);