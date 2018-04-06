INSERT INTO `fabricante` VALUES
(1,'Lucas','Lucasgomezsousa@gmail.com',992937515),
(2,'Daniel','emaildaniel@email.com',998617878),
(3,'Ronistone','ronipedra@gmail.com',993547060);

INSERT INTO `features` VALUES
(1,'ADSL','Acesso a Internet'),
(2,'Wifi','Acesso a Internet'),
(3,'Ronistone','manda pro github');

INSERT INTO `precificacao` VALUES
(1,'Cabo','123.456-789','Cabo de Energia'),
(2,'Cabo','987.654-321','Cabo de Energia'),
(3,'Teclado','1.0.0','Teclado ABNT2');

INSERT INTO `tipo` VALUES
(1,'Cabos','Cabos de Rede'),
(2,'Mouse','Mouse Wireless'),
(3,'Teclado','Teclado ABNT');

INSERT INTO `requisito` VALUES
(1,'wifi','wifi');

INSERT INTO `homologado` VALUES
(1,'Homologado','Concluido'),
(2,'Em Teste','Em Testes'),
(3,'Não-Homologado','Não foi homologado');


INSERT INTO `equipamento` VALUES	
(1,'Switch','EmTestes','2003-09-03','2012-11-17','B2B','Conexao a Internet',1,2,3),
(2,'Desktop','NaoHomologado','2019-03-18','2017-04-18','B2C','Uso CTT',2,3,3),
(3,'Fax','Homologado','2013-04-10','2017-03-18','B2B-B2C','Uso dos Associados',3,1,1);

INSERT INTO `equipamento_tipo` VALUE
(1,1),(1,2),(2,3),(2,1),(2,2),(3,3);


INSERT INTO `user` VALUE(1, 1, 'admin@admin.com','admin', 'admin','2019-01-01', NULL);
INSERT INTO `role` VALUE(DEFAULT, 'ADMIN', 1),(DEFAULT, 'USER', 1);