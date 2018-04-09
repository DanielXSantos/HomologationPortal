CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
);
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `active` int(11) DEFAULT NULL,
  `email` varchar(255) UNIQUE NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `expiration_date` date,
  `fabricante_id` BIGINT,
  `is_deleted` int(11) DEFAULT FALSE ,
  PRIMARY KEY (`user_id`),
  FOREIGN KEY (`fabricante_id`) REFERENCES `fabricante`(`id`)
);

CREATE TABLE `user_roles`(
  `user_id` int(11),
  `role_id` int(11),
  PRIMARY  KEY (`user_id`, `role_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE CASCADE ,
  FOREIGN KEY (`role_id`) REFERENCES `role`(`role_id`) ON DELETE CASCADE
);