INSERT INTO `articolo` VALUES
 (1,'Mela','quanto e'' buona questa mela', false, 3.2, '1139780629'),
 (2,'Arancia','quanto e'' buona questa arancia', false, 10.2, '1139780629'),
 (3,'Banana','quanto e'' buona questa banana', false, 2.2, '1139780629'),
 (4,'Fragola','quanto e'' buona questa arancia', false, 10.2, '1435650625'),
 (5,'Castagne','castagne di qualita''', false, 2.2, '1139780629'),
 (6,'verdura','quanto e'' buona questa verdura', false, 10.2, '1435650625');
COMMIT;


INSERT INTO `ordine` VALUES
 (1, false, '2009-02-17', false, 4, 1, '2009-03-17', '2009-04-17', 'esteban', 1),
 (2, false, '2009-02-17', false, 0, 8, '2009-03-17', '2009-04-17', 'esteban', 3),
 (3, false, '2009-02-17', false, 4, 1, '2009-03-17', '2009-04-17', 'esteban', 2),
 (4, false, '2009-02-17', true, 4, 1, '2009-03-17', '2009-04-17', 'esteban', 5),
 (5, false, '2009-02-17', true, 4, 1, '2009-03-17', '2009-04-17', 'zlatan', 1),
 (6, false, '2009-02-20', true, 4, 1, '2009-03-17', '2009-04-17', 'esteban', 4),
 (7, false, '2009-02-20', true, 4, 1, '2009-03-17', '2009-04-17', 'zlatan', 2);
COMMIT;