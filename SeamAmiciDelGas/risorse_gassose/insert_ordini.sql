INSERT INTO `articolo` VALUES
 (1,'Mela','quanto e'' buona questa mela', 0x00, 3.2, '1139780629'),
 (2,'Arancia','quanto e'' buona questa arancia', 0x00, 10.2, '1139780629'),
 (3,'Banana','quanto e'' buona questa banana', 0x00, 2.2, '1139780629'),
 (4,'Arancia','quanto e'' buona questa arancia', 0x00, 10.2, '1435650625'),
 (5,'Castagne','castagne di qualita''', 0x00, 2.2, '1139780629'),
 (6,'verdura','quanto e'' buona questa verdura', 0x00, 10.2, '1435650625');
COMMIT;



INSERT INTO `ordine` (`IDOrdine`,`Cancellato`,`DataRichiesta`,`Pendente`,`Quantita`,`QuantitaMinUtente`,`DataMassimaConsegna`,`DataCancellazioneAccettazione`,`Account_Username`,`Articolo_IDArticolo`) VALUES
 (1,0x00,'2009-02-17', 0x01, 4, 1, '2009-03-17', '2009-04-17', 'esteban', 1),
 (2,0x00,'2009-02-17', 0x01, 8, 5, '2009-03-17', '2009-04-17', 'esteban', 3),
 (3,0x00,'2009-02-17', 0x00, 4, 1, '2009-03-17', '2009-04-17', 'esteban', 2),
 (4,0x00,'2009-02-17', 0x00, 4, 1, '2009-03-17', '2009-04-17', 'esteban', 5),
 (5,0x00,'2009-02-17', 0x01, 4, 1, '2009-03-17', '2009-04-17', 'zlatan', 1),
 (6,0x00,'2009-02-20', 0x00, 4, 1, '2009-03-17', '2009-04-17', 'esteban', 2);
COMMIT;