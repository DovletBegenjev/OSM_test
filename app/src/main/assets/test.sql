BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS 'bus' (
	"busId"	INTEGER NOT NULL,
	"busNumber"	INTEGER NOT NULL,
	PRIMARY KEY("busId" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "busStop_bus" (
	"busStop_BusId"	INTEGER NOT NULL,
	"busId"	INTEGER NOT NULL,
	"busStopId"	INTEGER NOT NULL,
	PRIMARY KEY("busStop_BusId" AUTOINCREMENT),
	FOREIGN KEY("busId") REFERENCES "bus"("busId") ON DELETE CASCADE,
	FOREIGN KEY("busStopId") REFERENCES "bus_stop"("busStopId") ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS "bus_stop" (
	"busStopId"	INTEGER NOT NULL,
	"busStopName"	TEXT NOT NULL,
	"busStopLat"	REAL NOT NULL,
	"busStopLon"	REAL NOT NULL,
	PRIMARY KEY("busStopId" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "test" (
	"id"	INTEGER NOT NULL,
	"busId"	INTEGER NOT NULL,
	"busStopId"	INTEGER NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);
INSERT INTO "bus" ("busId","busNumber") VALUES (1,73),
 (2,16);
INSERT INTO "busStop_bus" ("busStop_BusId","busId","busStopId") VALUES (7,1,102),
 (8,1,100),
 (9,1,101),
 (10,1,99),
 (11,1,98),
 (12,1,104),
 (13,1,128),
 (14,1,132),
 (15,1,115),
 (16,1,129),
 (17,1,136),
 (18,1,131),
 (19,1,139),
 (20,1,145),
 (21,1,134),
 (22,1,141),
 (23,1,142),
 (24,1,140),
 (25,1,150),
 (26,1,106),
 (27,1,179),
 (28,1,90),
 (29,1,147),
 (30,1,92),
 (31,1,181),
 (32,1,79),
 (33,1,78),
 (34,1,74),
 (35,1,82),
 (36,1,203),
 (37,1,206),
 (38,1,213),
 (39,1,503),
 (40,1,208),
 (41,1,7),
 (42,1,37),
 (43,1,42),
 (44,1,219),
 (45,1,45),
 (46,1,35),
 (47,1,44),
 (48,1,221),
 (49,1,222),
 (50,1,286),
 (51,1,289),
 (52,1,288),
 (53,1,287),
 (54,1,293),
 (55,1,302),
 (56,1,296),
 (57,1,296),
 (58,1,294),
 (59,1,297),
 (60,1,504),
 (61,1,505),
 (62,1,326),
 (63,1,292),
 (64,1,295),
 (65,1,299),
 (66,1,310),
 (67,1,309),
 (68,1,322),
 (69,1,341),
 (70,1,319),
 (71,1,327),
 (72,1,335),
 (73,1,334),
 (74,1,342),
 (75,1,508),
 (76,1,507),
 (77,1,506),
 (78,2,366),
 (79,2,359),
 (80,2,358),
 (81,2,376),
 (82,2,371),
 (83,2,384),
 (84,2,375),
 (85,2,399),
 (86,2,416),
 (87,2,400),
 (88,2,202),
 (89,2,196),
 (90,2,200),
 (91,2,198),
 (92,2,80),
 (93,2,25),
 (94,2,27),
 (95,2,17),
 (96,2,68),
 (97,2,89),
 (98,2,72),
 (99,2,116),
 (100,2,126),
 (101,2,127),
 (102,2,512),
 (103,2,511),
 (104,2,86),
 (105,2,61),
 (106,2,54),
 (107,2,62),
 (108,2,29),
 (109,2,22),
 (110,2,57),
 (111,2,76),
 (112,2,510),
 (113,2,12),
 (114,2,204),
 (115,2,401),
 (116,2,417),
 (117,2,392),
 (118,2,377),
 (119,2,366),
 (120,2,514),
 (121,2,513);
INSERT INTO "bus_stop" ("busStopId","busStopName","busStopLat","busStopLon") VALUES (1,'Bus stop name',85.0,0.0),
 (2,'Bus stop name 2',0.0,85.0),
 (3,'Bus stop',58.0,35.0),
 (4,'Bus stop 2',35.0,58.0),
 (5,'Dal??yn',37.9316195,58.3686128),
 (6,'Senagat??ylar we teleke??iler birle??mesi',37.9312641,58.3679073),
 (7,'K??petdag etrap prokuraturasy',37.9147859,58.3639088),
 (8,'Altyn asyr se??ilg??hi',37.9255562,58.370559),
 (9,'Gara??syzlyk ??a??oly, 90',37.9229591,58.3757234),
 (10,'Oguzken myhmanhanasy',37.9291437,58.3734934),
 (11,'Mejlis',37.9301788,58.3783608),
 (12,'17-nji mekdep',37.9207555,58.3825205),
 (13,'Gara??syzlygy?? se??ilg??hi',37.9306216,58.3752038),
 (14,'Bitarap T??rkmenistan (Podwo??skowo), 221',37.9259234,58.3702452),
 (15,'Ertekiler d??n????si',37.9195255,58.3697635),
 (16,'37-nji mekdep',37.9201227,58.3676341),
 (17,'Olimpi??a ????her??esi',37.8980634,58.3789738),
 (18,'Gara??syzlyk ??a??oly, 21',37.8963274,58.358257),
 (19,'Gara??syzlyk ??a??oly, 54',37.9050477,58.3658912),
 (20,'????rite saz??ylyk mekdeb internaty',37.8984434,58.3640352),
 (21,'Gara??syzlyk ??a??oly, 21',37.8960938,58.3580765),
 (22,'Parahat 2/1, 10',37.9011992,58.3799548),
 (23,'Abadan??ylyk ??a??oly, 148',37.9006311,58.3471053),
 (24,'Gara??syzlyk ??a??oly, 51',37.905351,58.3651161),
 (25,'Olimpi??a ????her??esi',37.9048636,58.3798089),
 (26,'Ba??a ba?? toplumy',37.8995774,58.3717175),
 (27,'Olimpi??a ????her??esi',37.9012599,58.37933),
 (28,'T??rkmen d??wlet ykdysady??et we dolandyry?? instituty',37.899793,58.3708909),
 (29,'Parahat 2/2, 13',37.8971868,58.3794851),
 (30,'Gara??syzlyk ??a??oly, Olimp',37.9183769,58.3733888),
 (31,'Altyn asyr se??ilg??hi',37.9259352,58.3742532),
 (32,'Bitarap T??rkmenistan (Podwo??skowo), 159',37.9225074,58.3615217),
 (33,'Bitarap T??rkmenistan (Podwo??skowo), 148',37.9155472,58.3456621),
 (34,'Bitarap T??rkmenistan (Podwo??skowo), 131',37.91516,58.3440474),
 (35,'Polat gurlu??yk',37.913782,58.3521734),
 (36,'Gurbannazar Ezizow k????esi, 36',37.9166739,58.354973),
 (37,'Dowamat',37.91494,58.3607237),
 (38,'Bitarap T??rkmenistan (Podwo??skowo), 178',37.9192803,58.3543651),
 (39,'Bagty??ar nesiller ??agalar bagy',37.9202695,58.35522),
 (40,'Gurbannazar Ezizow k????esi, 19',37.9188855,58.3619479),
 (41,'74-nji mekdep',37.9206486,58.3516995),
 (42,'T??rkmenstandartlary',37.9138496,58.3577271),
 (43,'130-nji mekdep, 166-njy ??agalar bagy',37.9151127,58.3506822),
 (44,'T??rkmenhowa??ollary',37.9130132,58.349016),
 (45,'Owadan',37.9131712,58.3526571),
 (46,'Ruhy??et k????gi',37.9319341,58.3787725),
 (47,'Gara??syzlygy?? se??ilg??hi',37.9291089,58.3775866),
 (48,'Oguzkent myhmanhanasy',37.9282745,58.3698147),
 (49,'Bitarap T??rkmenistan (Podwo??skowo), 207',37.9243575,58.3664778),
 (50,'Bitarap T??rkmenistan (Podwo??skowo), 212',37.9239663,58.3667384),
 (51,'Parahat 2/3, 5',37.9004603,58.3881726),
 (52,'Gara??syzlyk se??ilg??hi',37.888221,58.38754),
 (53,'Mir bazary',37.9024661,58.3853599),
 (54,'Ruhnama',37.8909209,58.3787657),
 (55,'T??rkmenistan banky ',37.8957571,58.3943815),
 (56,'Nurmuhammet Andalyp k????esi, 41',37.8972976,58.3887514),
 (57,'Milli s??wda merkezi',37.9048015,58.3804244),
 (58,'Medeni??et instituty',37.895321,58.3680108),
 (59,'10 ??yl Abadan??ylyk ??a??oly, 102',37.8952768,58.3765388),
 (60,'Parahat 4',37.9018478,58.3911814),
 (61,'A??dym-saz merkezi',37.8833481,58.377767),
 (62,'Milli se??ilg??hi',37.893897,58.3791179),
 (63,'A??bagat Olimpi??a stadiony',37.9046095,58.3742814),
 (64,'Parahat 1',37.9059729,58.3886633),
 (65,'Parahat 3/1',37.9061899,58.3893946),
 (66,'T??rkmen d??wlet ykdysady??et we dolandyry?? instituty',37.8980433,58.3670876),
 (67,'Pa??tagt s??wda merkezi',37.8940343,58.3884632),
 (68,'Senagat bank',37.8938945,58.3786307),
 (69,'Sport myhmanhanasy',37.8966335,58.372097),
 (70,'10 ??yl Abadan??ylyk ??a??oly, 56',37.8952215,58.392514),
 (71,'Nurmuhammet Andalyp k????esi, 43/3',37.9118517,58.3898176),
 (72,'A??dym-saz merkezi',37.8833106,58.377389),
 (73,'83-nji mekdep',37.8833006,58.3936564),
 (74,'A??GB 171',37.9104416,58.3851479),
 (75,'Gara??syzlyk ??a??oly, 58',37.9078186,58.3657765),
 (76,'Parahat 1, 3',37.9093751,58.3810873),
 (77,'Pu??kin adyndaky mekdebi',37.9113961,58.3672581),
 (78,'??unus d??kany',37.9105868,58.3914583),
 (79,'??unus d??kany',37.9101512,58.3913946),
 (80,'Olimpi??a ????her??esi',37.9093149,58.3804089),
 (81,'??e??il atletika toplumy',37.9115986,58.3775692),
 (82,'A??GB 171',37.9112924,58.3839354),
 (83,'Parahat 4',37.8957362,58.3903494),
 (84,'Santa Barbara',37.8999366,58.3889354),
 (85,'135-nji mekdep',37.8955848,58.402273),
 (86,'Gara??syzlyk binasy',37.8812632,58.3776446),
 (87,'Ar??abil ??a??oly, 131',37.880598,58.3943667),
 (88,'Olimpi??a myhmanhanasy',37.8959816,58.3769974),
 (89,'Ruhnama',37.8909455,58.3782879),
 (90,'Gara??syzlygy?? 15 ??yllygy (Optowka) s??wda merkezi',37.9101207,58.4023154),
 (91,'Parahat 6, 29',37.9086954,58.3974779),
 (92,'Rysgal bank',37.9101054,58.3954964),
 (93,'Parahat 6, 84',37.908731,58.3896613),
 (94,'Parahat 5, 50',37.9078453,58.3980322),
 (95,'Parahat 3/2, 22',37.9036228,58.3975113),
 (96,'57-nji mekdep',37.9018551,58.3951139),
 (97,'Ankara k????esi, 53',37.8966098,58.3967308),
 (98,'Parahat 7/4, 41',37.8949912,58.4093493),
 (99,'Parahat 7/4, 25',37.8913648,58.4057198),
 (100,'Parahat 7/5, 30',37.8868546,58.4056479),
 (101,'Parahat 7/4, 21',37.8910914,58.4054754),
 (102,'Parahat 7/5, 26',37.8865496,58.4053451),
 (103,'Parahat 3/1',37.903795,58.3892791),
 (104,'Parahat 7/3, 7',37.8954668,58.4085203),
 (105,'T??rkmenba??y adyndaky dokma toplumy',37.9033546,58.4059312),
 (106,'T??rkmenistan kinoteatry',37.9099867,58.4080617),
 (107,'T??rkmenba??y adyndaky dokma toplumy',37.9005357,58.4081864),
 (108,'T??rkmenba??y adyndaky dokma toplumy',37.9004214,58.4086946),
 (109,'Nebitgaz uniwersiteti',37.8747769,58.3870959),
 (110,'Ar??abil ??a??oly, 109',37.8880776,58.3946994),
 (111,'Ar??abil ??a??oly, 131',37.8790509,58.3930211),
 (112,'Gara??syzlyk binasy',37.8796171,58.3804549),
 (113,'Nebit gaz uniwersiteti',37.8785108,58.3927078),
 (114,'Parahat 3/1',37.9021075,58.3911871),
 (115,'Parahat 7/2, 7',37.8953934,58.4174306),
 (116,'Be??ik Saparmyrat T??rkmenba??y ??a??oly',37.8764954,58.3767387),
 (117,'Berkarar s??wda we dyn?? aly?? merkezi',37.8949377,58.3687191),
 (118,'Ar??abil ??a??oly, 131',37.8798883,58.3939632),
 (119,'Ar??abil ??a??oly, 166',37.8797121,58.3755708),
 (120,'Olimpi??a sport k????gi',37.8788098,58.3748842),
 (121,'Ar??abil ??a??oly',37.8790848,58.3717569),
 (122,'??andybil ??a??oly',37.8735684,58.3691622),
 (123,'??andybil ??a??oly',37.8726616,58.3700682),
 (124,'Nurmuhammet Andalyp k????esi',37.869764,58.3867613),
 (125,'??andybil ??a??oly',37.8725669,58.3823965),
 (126,'Be??ik Saparmyrat T??rkmenba??y ??a??oly',37.8644184,58.3761835),
 (127,'Berze????i ??oly',37.8509024,58.3744131),
 (128,'Parahat 7/4, 37',37.8949468,58.4128228),
 (129,'170-nji ??agalar bagy',37.8948706,58.4182596),
 (130,'Ak bugda?? binasy',37.8783214,58.4205373),
 (131,'Parahat 7/2, 19',37.8983664,58.4234954),
 (132,'Parahat 7/3, 2',37.8954384,58.411968),
 (133,'Oguzhan k????esi, 1A',37.9097685,58.4266654),
 (134,'Ene we ??aga keselhanasy',37.9086027,58.4230607),
 (135,'Parahat 7/1, 9',37.900357,58.4155601),
 (136,'Parahat 7/2, 15',37.8972588,58.4230367),
 (137,'Parahat 7/2, 28',37.9002187,58.4210087),
 (138,'T??rkmenba??y adyndaky dokma toplumy',37.9025614,58.4056093),
 (139,'Parahat 7, 15',37.9029631,58.4231303),
 (140,'Energetik orta h??n??r mekdebi',37.9099608,58.412655),
 (141,'Ny??azow keselhanasy',37.9098753,58.4184984),
 (142,'Oguzhan k????esi, 48',37.9103014,58.4156759),
 (143,'Oguzhan k????esi, 30/1',37.9101502,58.4272452),
 (144,'Bereketli s??wda merkezi',37.9004718,58.4142513),
 (145,'Parahat 7, 15',37.9029613,58.4235854),
 (146,'100 fontan',37.912007,58.4057852),
 (147,'Gara??syzlygy?? 15 ??yllygy (Optowka) s??wda merkezi',37.9104565,58.402429),
 (148,'G??ndogar bazary',37.9096962,58.4343806),
 (149,'G??ndogar bazary',37.910401,58.4364484),
 (150,'Oguzhan k????esi, 56',37.910365,58.4110043),
 (151,'Azatlyk (Da??oguz) bazary',37.9138498,58.4231548),
 (152,'100 fontan',37.9147154,58.4060334),
 (153,'Azatlyk (Da??oguz) bazary',37.9148278,58.4237189),
 (154,'Atamyrat Ny??azow ??a??oly, 1A',37.9188362,58.4110771),
 (155,'Atamyrat Ny??azow ??a??oly, 72/1',37.9184477,58.4109694),
 (156,'A??GT',37.918892,58.4043801),
 (157,'L??lezar bazary',37.9202475,58.4134881),
 (158,'Atamyrat Ny??azow ??a??oly, Altyn a??ar',37.9182128,58.4262649),
 (159,'WDNH',37.9257228,58.4062939),
 (160,'Go??gar ata gonam??ylygy',37.9262892,58.4179695),
 (161,'95-nji ??agalar bagy',37.9248019,58.4095468),
 (162,'L??lezar bazary',37.9201554,58.4139365),
 (163,'Atamyrat Ny??azow ??a??oly, 63-67',37.9187524,58.4161884),
 (164,'Bank mekdebi',37.920605,58.4240351),
 (165,'Atamyrat Ny??azow ??a??oly, 92/2',37.9183338,58.419496),
 (166,'G??ndogar bazary',37.9100084,58.4327465),
 (167,'Alty Garly??ew (Surikow) k????esi, 60',37.9177478,58.4058719),
 (168,'57-nji ??agalar bagy',37.9228027,58.4136495),
 (169,'Gurbansoltan eje k????esi (Steklozawod)',37.9328871,58.4154232),
 (170,'L??le restorany',37.9302188,58.4106511),
 (171,'Gurbansoltan eje k????esi (Steklozawod)',37.9347934,58.4110619),
 (172,'A??gabat a??lawy (Ippodrom)',37.9320078,58.4315613),
 (173,'Binag??rlik gurlu??yk instituty',37.9254909,58.4333316),
 (174,'Binag??rlik gurlu??yk instituty',37.9251892,58.4327872),
 (175,'G??rogly k????esi, 63',37.9279965,58.4047359),
 (176,'Gurbansoltan eje k????esi (Steklozawod)',37.9353819,58.41073),
 (177,'Atamyrat Ny??azow (Gagarina) ??a??oly',37.9191946,58.4003333),
 (178,'Atamyrat Ny??azow (Gagarina) ??a??oly',37.9196452,58.4002512),
 (179,'T??rkmenistan kinoteatry',37.9104128,58.4071653),
 (180,'Alty Garly??ew (Surikow) k????esi, 3',37.9199505,58.4061857),
 (181,'Rysgal bank',37.9104797,58.3960647),
 (182,'WDNH',37.9263829,58.4064414),
 (183,'WDNH',37.9283033,58.4037408),
 (184,'Nurmuhammet Andalyp k????esi, 33/1',37.9267269,58.3914663),
 (185,'??er titreme mazarlygy ',37.9223485,58.3879027),
 (186,'Howdan W, 66',37.9125813,58.3975708),
 (187,'??er titreme mazarlygy ',37.9233569,58.3895593),
 (188,'Parahat 3/2, Awto ??oly',37.9041491,58.3979839),
 (189,'57-nji mekdep',37.9020978,58.3959671),
 (190,'Go??gar ata gonam??ylygy',37.9287116,58.4134319),
 (191,'Gaudan 5',37.9207887,58.3894692),
 (192,'Atamyrat Ny??azow ??a??oly, 64/1',37.9184657,58.407341),
 (193,'M??li??e instituty',37.920365,58.3957175),
 (194,'Halkbank',37.9241328,58.3800771),
 (195,'Nurmuhammet Andalyp k????esi, 64/1',37.920189,58.3885816),
 (196,'Harby instituty',37.9264365,58.3841871),
 (197,'Nurmuhammet Andalyp k????esi, 51/1',37.911857,58.389042),
 (198,'??impa?? s??wda merkezi',37.9155751,58.3814756),
 (199,'Halkbank',37.9244422,58.3800958),
 (200,'Ruhnama mekdebi',37.9219303,58.3825368),
 (201,'M??li??e instituty',37.9210316,58.3944836),
 (202,'Goranmak ministrligi',37.9293326,58.385287),
 (203,'Suw toplumy',37.915811,58.3748842),
 (204,'Harby instituty',37.9273094,58.3849563),
 (205,'??e??il atletika toplumy',37.9117717,58.3767127),
 (206,'Suw toplumy',37.9156095,58.3738643),
 (207,'??a??lyma beri?? merkezi',37.9093814,58.3758102),
 (208,'Gujurly in??ener',37.9155308,58.3649518),
 (209,'Gara??syzlyk ??a??oly',37.9135901,58.3701338),
 (210,'Bitarap T??rkmenistan (Podwo??skowo), 202',37.9222621,58.3626764),
 (211,'??er titreme mazarlygy ',37.9226061,58.3882193),
 (212,'Ertekiler d??n????si',37.9224755,58.3750504),
 (213,'Bagty??arlyk s??wda merkezi',37.9155089,58.3692576),
 (214,'Bitarap T??rkmenistan (Podwo??skowo)',37.918006,58.3488906),
 (215,'Bitarap T??rkmenistan (Podwo??skowo), 128',37.9100982,58.3401834),
 (216,'Bagt k????gi',37.9253388,58.3426658),
 (217,'Abadan??ylyk ??a??oly, 162',37.9030657,58.3400321),
 (218,'K??rizli s??wda d??kany',37.9023271,58.3359659),
 (219,'Nusa?? ??ollary',37.9144975,58.3577807),
 (220,'Bitarap T??rkmenistan (Podwo??skowo), 160',37.9174136,58.3492625),
 (221,'Golf klub',37.9138111,58.3336313),
 (222,'Golf klub',37.9141696,58.332829),
 (223,'10 ??yl Abadan??ylyk ??a??oly',37.9065505,58.3330211),
 (224,'10 ??yl Abadan??ylyk ??a??oly',37.9056828,58.3331579),
 (225,'Bugda??li ????rek',37.8994025,58.3357144),
 (226,'Bekrewe k????esi',37.9290947,58.3129414),
 (227,'Bagt k????gi',37.9276185,58.3402287),
 (228,'10 ??yl Abadan??ylyk ??a??oly',37.9073441,58.3208546),
 (229,'Bitarap T??rkmenistan (Podwo??skowo), 128',37.9105115,58.3395392),
 (230,'??andybil ??a??oly',37.8900286,58.3277901),
 (231,'Parahat dyn?? aly?? zolagy',37.8960247,58.3114721),
 (232,'Atat??rk k????esi, parkowka',37.8689684,58.346116),
 (233,'Nebitgaz toplumy',37.8908059,58.3447645),
 (234,'Bitaraplyk ??a??oly, 76',37.8936478,58.3347906),
 (235,'Abadan??ylyk ??a??oly, 137',37.8998867,58.3533392),
 (236,'Gara??syzlyk ??a??oly, 12',37.8924395,58.3556557),
 (237,'Gara??syzlyk ??a??oly, 21',37.896224,58.3581737),
 (238,'????rite saz??ylyk mekdeb internaty',37.8977978,58.3639011),
 (239,'Ar??abil ??a??oly, 30',37.8843395,58.3576339),
 (240,'Hirurgi??a we endokrinologi??a merkezi',37.8730071,58.349443),
 (241,'??andybil ??a??oly',37.8811351,58.3511215),
 (242,'Zehini ??a??lar mekdebi',37.8966171,58.3594384),
 (243,'Gara??syzlyk ??a??oly, ??',37.8929038,58.3551868),
 (244,'??edigen myhmanhanasy',37.8941829,58.3408361),
 (245,'ABM biznes sentry',37.8869742,58.3552137),
 (246,'Altyn suw myhmanhana',37.8893887,58.3520854),
 (247,'Da??an?? myhmanhanasy',37.885146,58.3588217),
 (248,'I?? keselleri merkezi',37.871235,58.356946),
 (249,'Atat??rk k????esi, 99',37.8875903,58.3624843),
 (250,'K??petdag myhmanhanasy',37.8858483,58.3574367),
 (251,'Be??ik Serdar Nesilleri mekdebi',37.8788836,58.3618854),
 (252,'Atat??rk k????esi, 95',37.8882342,58.3619842),
 (253,'Be??ik Serdar Nesilleri ??agalar bagy',37.8758409,58.3621435),
 (254,'Newrologi??a merkezi',37.8767584,58.3525309),
 (255,'Saglyk ministrligi',37.88038,58.365551),
 (256,'Hyta??y?? il??ihanasy',37.8889957,58.3526752),
 (257,'??andybil ??a??oly',37.8800429,58.3515781),
 (258,'T??rkmenistany?? milli muze??i',37.8872224,58.3519366),
 (259,'??andybil ??a??oly',37.8838251,58.3442307),
 (260,'Bitarap T??rkmenistan (Podwo??skowo) ??a??oly, 85',37.8987721,58.3347713),
 (261,'S??wda-Senagat edarasy',37.8898883,58.2955494),
 (262,'T??rkmenistany?? konstitusi??a binasy',37.8960665,58.3223443),
 (263,'??lem dyn?? aly?? merkezi',37.9007511,58.2993107),
 (264,'Senagat toplumy',37.8953327,58.3028072),
 (265,'??lem dyn?? aly?? merkezi',37.8960058,58.3019918),
 (266,'Bekrewe ??a??oly',37.9002944,58.2868048),
 (267,'Arkadag myhmanhanasy',37.8920636,58.2856835),
 (268,'Bekrewe ??a??oly',37.9011651,58.2877942),
 (269,'Arkadag myhmanhanasy',37.891894,58.2865895),
 (270,'Arkadag myhmanhanasy',37.8899502,58.2894529),
 (271,'??andybil ??a??oly',37.889319,58.2820494),
 (272,'Ar??abil ??a??oly',37.8955747,58.2756979),
 (273,'Bekrewe ??a??oly',37.9052571,58.2874831),
 (274,'??andybil ??a??oly',37.8894714,58.2627759),
 (275,'T??rkmendemir??ollary',37.8960947,58.2932586),
 (276,'Bekrewe ??a??oly',37.9115402,58.2882987),
 (277,'Bekrewe ??a??oly',37.9186999,58.2892695),
 (278,'10 ??yl Abadan??ylyk ??a??oly',37.9074732,58.3002177),
 (279,'Bekrewe ??a??oly',37.9105612,58.289064),
 (280,'Bekrewe ??a??oly',37.9221997,58.290621),
 (281,'21-nji ??agalar bagy',37.9305589,58.2954669),
 (282,'Bekrewe k????esi',37.9345471,58.289798),
 (283,'Bekrewe ??a??oly',37.9231961,58.2898595),
 (284,'Gadamly a??akgap ??n??mleri',37.9016185,58.3350847),
 (285,'??yldyz myhmanhanasy',37.9275781,58.3331103),
 (286,'Oguzhan (A??takowa) k????esi',37.9300603,58.320084),
 (287,'Se??ilg??hler toplumy',37.9352523,58.3207635),
 (288,'Se??ilg??hler toplumy',37.9328681,58.3197711),
 (289,'Oguzhan (A??takowa) k????esi',37.9309781,58.319726),
 (290,'10 ??yl Abadan??ylyk ??a??oly',37.9080256,58.3208492),
 (291,'Bekrewe k????esi',37.9299487,58.3065276),
 (292,'Aman Kekilow adyndaky mugallym??ylyk merkezi',37.9537136,58.331305),
 (293,'Serhet??iler hassahanasy',37.9443985,58.3301525),
 (294,'Tiz k??mek merkezi',37.9522212,58.3373195),
 (295,'Mahmyt I??an metjidi',37.956164,58.3264148),
 (296,'Tiz k??mek merkezi',37.951068,58.3386447),
 (297,'"A??gabady?? ??alkymy" s??wda merkezi',37.9524883,58.3366291),
 (298,'A??gabatenergo',37.9562867,58.3404833),
 (299,'Mahmyt I??an metjidi',37.95662,58.3258224),
 (300,'??yldyz myhmanhanasy',37.9308859,58.3351184),
 (301,'Esgerler hassahanasy',37.9572078,58.3394625),
 (302,'Serhet??iler hassahanasy',37.9444735,58.3305253),
 (303,'Alaba?? binasy, Bekrewe k????esi',37.9591226,58.3399805),
 (304,'Magtymguly ??a??oly',37.9619083,58.3341067),
 (305,'Magtymguly ??a??oly, 197',37.958004,58.3480591),
 (306,'Magtymguly ??a??oly, 172A',37.9582307,58.3475944),
 (307,'G??l zemin  s??wda merkezi',37.9631465,58.3320412),
 (308,'62-nji mekdep',37.9634449,58.3420506),
 (309,'Oguz han uniwersiteti',37.9629778,58.3276039),
 (310,'140-nji mekdep',37.9599327,58.3254019),
 (311,'G??rogly k????esi, 39G',37.9472246,58.3513362),
 (312,'Botanika bagy',37.9479538,58.3500301),
 (313,'Mollanepes k????esi, Baky dursun',37.9407304,58.3488503),
 (314,'Se??di k????esi, D??wleti d??wran',37.9416023,58.3489648),
 (315,'A??gabatenergo',37.9575358,58.3397826),
 (316,'G??l zemin  s??wda merkezi',37.9633977,58.3309071),
 (317,'Alaba?? binasy, Bekrewe k????esi',37.9593284,58.3395828),
 (318,'Hellew',37.9587311,58.3186975),
 (319,'116-njy mekdep',37.967143,58.3322563),
 (320,'62-nji mekdep',37.9633802,58.34134),
 (321,'Hellew',37.9596704,58.3177981),
 (322,'Oguz han uniwersiteti',37.9631043,58.3283075),
 (324,'Gurbansoltan eje k????esi',37.9807003,58.3377687),
 (325,'Gurbansoltan eje k????esi',37.980347,58.337451),
 (326,'Aman Kekilow adyndaky mugallym??ylyk merkezi',37.9535017,58.3322533),
 (327,'K????i, 46',37.9701654,58.3351544),
 (328,'Diller merkezi',37.952474,58.3552887),
 (329,'Pu??kin teatry',37.9568655,58.3599513),
 (330,'Magtymguly ??a??oly',37.9596933,58.3378196),
 (331,'45-nji ??agalar bagy',37.9577429,58.355175),
 (332,'??agalar ??eper??ilik mekdebi',37.9569182,58.358061),
 (333,'Watutin gonam??ylygy',37.9749507,58.3465576),
 (334,'K????i, 26',37.9731581,58.3379921),
 (335,'K????i, 35',37.972841,58.3370212),
 (336,'7-nji dermanhana',37.9580102,58.3548539),
 (337,'2-nji mekdep',37.9563982,58.3576735),
 (338,'Gurtly',37.9835982,58.3323229),
 (339,'Gurtly',37.9826354,58.3333019),
 (340,'Gurbansoltan eje k????esi',37.9902768,58.320028),
 (341,'61-nji mekdep',37.9673254,58.3317238),
 (342,'K????i, 12',37.9752689,58.3400313),
 (343,'T??rkmenba??y ruhy metjidi',38.0172612,58.2590648),
 (344,'Gurbansoltan eje k????esi',38.0140092,58.2671146),
 (345,'T??rkmenba??y ruhy metjidi',38.0240886,58.2443726),
 (346,'??ikesleri bejri?? halkara merkezi',37.9513185,58.3413351),
 (347,'Atamyrat Ny??azow ??a??oly, K??petdag stadion ugurdan',37.9461381,58.365877),
 (348,'Mollanepes k????esi, Bu??san??ly',37.9403124,58.353153),
 (349,'G??rogly k????esi, Berk pub',37.9450359,58.355965),
 (350,'D??wletm??mmet Azady (Engels) k????esi, 123',37.945535,58.3620713),
 (351,'Se??di k????esi, Melhem',37.9411737,58.3532716),
 (352,'Bekrewe k????esi',37.9657089,58.3457509),
 (353,'Watutin gonam??ylygy',37.9656932,58.358567),
 (354,'Gurbansoltan eje k????esi',37.9590321,58.3663695),
 (355,'Magtymguly ??a??oly',37.9584605,58.3415548),
 (356,'Kardiologi??a merkezi',37.9412839,58.3647807),
 (357,'K??petdag stadiony',37.9490064,58.3676025),
 (358,'65-nji mekdep',37.944384,58.3655334),
 (359,'D??wletm??mmet Azady (Engelsa) k????esi, 107',37.9446206,58.367873),
 (360,'Magtymguly ??a??oly, 124A',37.9523306,58.3690092),
 (361,'K??petdag stadiony',37.9497573,58.367572),
 (362,'Atamyrat Ny??azow ??a??oly',37.9383912,58.3642376),
 (363,'Diller merkezi',37.9521364,58.3554362),
 (364,'Itali??any?? il??ihanasy',37.9463588,58.3564661),
 (365,'A??gabat kinoteatry',37.9500008,58.3733717),
 (366,'Teke bazar',37.9465255,58.3711967),
 (367,'Atamyrat Ny??azow ??a??oly, 21',37.9383607,58.3649481),
 (368,'Altyn ??yldyz',37.9392784,58.3755633),
 (369,'Gurbansoltan eje k????esi, 53',37.9519077,58.3795524),
 (370,'??zbegistan il??ihanasy',37.9433782,58.3596732),
 (371,'Ylham se??ilg??hi',37.9419216,58.3764033),
 (372,'K??petdag stadiony',37.9510353,58.370971),
 (373,'Atamyrat Ny??azow ??a??oly, K??petdag stadion ugruna',37.9467448,58.3665488),
 (374,'19-njy mekdep',37.9419749,58.3694555),
 (375,'Akja',37.9462442,58.379959),
 (376,'8-nji h??n??rment mekdebi',37.9415074,58.3723431),
 (377,'D??wletm??mmet Azady (Engelsa) k????esi, 71',37.9428678,58.3791465),
 (378,'Atamyrat Ny??azow ??a??oly, 304',37.9597794,58.3780887),
 (379,'82-nji mekdep',37.9564617,58.3702959),
 (380,'Atamyrat Ny??azow ??a??oly',37.957413,58.3767486),
 (381,'I??erki aeroporty',37.9658316,58.3779244),
 (382,'Watan kinoteatry',37.9401591,58.379117),
 (383,'Janin salony',37.9460446,58.3808326),
 (384,'Ylham se??ilg??hi',37.9463205,58.3775856),
 (385,'Stamotologi??a merkezi',37.9410298,58.3650874),
 (386,'Se??di k????esi, parkowka',37.9407141,58.3579857),
 (387,'Energetika ministrligi',37.9358927,58.3760167),
 (388,'Magtymguly ??a??oly, 109',37.9485953,58.3755851),
 (392,'Mollanepes teatry',37.9417501,58.3890073),
 (393,'Gurbansoltan eje k????esi',37.9592704,58.3667596),
 (394,'82-nji mekdep',37.9571603,58.3699536),
 (395,'G??rogly k????esi, 17',37.9363048,58.3887785),
 (396,'Gurbansoltan eje k????esi, 8',37.9498742,58.3823891),
 (397,'Drama teatry',37.9387019,58.3828527),
 (398,'Gurbansoltan eje k????esi',37.948425,58.3851137),
 (399,'Ba?? drama teatry',37.9424938,58.3870857),
 (400,'T??rkmenbakale??a',37.9350573,58.3884632),
 (401,'T??rkmen d??wlet uniwersiteti',37.9333414,58.3877565),
 (402,'A??gabat wokzaly',37.943644,58.394777),
 (403,' Hristian gonam??ylygy',37.9523364,58.3913244),
 (404,'Nurmuhammet Andalyp k????esi, 35',37.9338783,58.3954909),
 (405,'??rtogrul Gazy metjidi',37.9314027,58.398041),
 (406,'Magtymguly ??a??oly, 104',37.9483541,58.3764923),
 (407,'WDNH',37.9309105,58.3935011),
 (408,'Nurmuhammet Andalyp k????esi, 6',37.9432113,58.4029578),
 (409,'Bagt ??oly restoran',37.9505867,58.3932814),
 (410,'3-nju se??ilg??hi',37.9479094,58.3976706),
 (411,'4-nji saglyk ????i',37.9348939,58.3916181),
 (412,'Russi??any?? il??ihanasy',37.9390282,58.393469),
 (413,'Magtymguly ??a??oly, 54',37.9363748,58.3990654),
 (414,'Watan kinoteatry',37.9400904,58.3799112),
 (415,'Bagt ??oly restoran',37.9523136,58.3913229),
 (416,'A??gabat se??ilg??hi (Perwy park)',37.9392554,58.3923104),
 (417,'Ulag we aragatna??yk instituty',37.9400856,58.3921682),
 (418,'Nurmuhammet Andalyp k????esi, 6',37.942756,58.4034101),
 (419,'Gurbansoltan eje k????esi',37.9376323,58.4064399),
 (420,'A??gabat wokzaly',37.9432324,58.3943027),
 (421,'TDU himi??a fakulteti',37.9341137,58.3935037),
 (422,'Ba?? market',37.9183971,58.4145894),
 (423,'Parahat 4',37.9004924,58.3972831),
 (424,'Ak ??ol bazary',37.9182206,58.429565),
 (425,'Taksomotor awtoulag k??rhanasy',37.9370178,58.4297155),
 (426,'Taksomotor awtoulag k??rhanasy',37.9367305,58.4307335),
 (427,'Hezreti Osman metjidi',37.9185529,58.4312862),
 (428,'Bank mekdebi',37.9214143,58.4235334),
 (429,'Gurbansoltan eje k????esi (Steklozawod)',37.932304,58.4157343),
 (430,'Taksomotor awtoulag k??rhanasy',37.9385537,58.4269368),
 (431,'Baba Annanow k????esi',37.9371781,58.4331205),
 (432,'Baba Annanow k????esi',37.9381329,58.4330242),
 (433,'G??ndogar k????esi, 22',37.9150635,58.4376617),
 (434,'Awtokombinat',37.9181784,58.4400305),
 (435,'Awtokombinat',37.9233411,58.4396502),
 (436,'Awtoulag k??rhanasy',37.9384197,58.4277333),
 (437,'86-njy mekdep',37.9181766,58.4330214),
 (438,'Awtokombinat',37.9220251,58.4423317),
 (439,'Hoja Ahmet ??asawy k????esi',37.9413002,58.4226796),
 (440,'Baba Annanow k????esi',37.9428233,58.4344501),
 (441,'Baba Annanow k????esi',37.9433761,58.435003),
 (442,'Hoja Ahmet ??asawy k????esi',37.9413012,58.4226918),
 (443,'Hoja Ahmet ??asawy k????esi',37.9506284,58.4057606),
 (444,'9-njy mekdep',37.9495402,58.4095611),
 (445,'Gurbansoltan eje k????esi',37.9377156,58.405423),
 (446,'Kuli??ew k????esi, Aja??yp ussat',37.9537113,58.4296954),
 (447,'K??rler mekdep internaty',37.947724,58.4103583),
 (448,'Nurmuhammet Andalyp k????esi, 10',37.9448478,58.4053007),
 (449,'Nurmuhammet Andalyp k????esi',37.9559943,58.4151302),
 (450,'Nurmuhammet Andalyp k????esi',37.9566699,58.4151616),
 (451,'Hoja Ahmet ??asawy k????esi',37.9505987,58.4056304),
 (452,'Magtymguly ??a??oly, 48 (Piw zawod)',37.9347597,58.4021334),
 (453,'Aga Berdi??ew k????esi, 35',37.9454791,58.4021125),
 (454,'B??ram han me??dany',37.9309688,58.408612),
 (455,'Azady metjidi',37.9477092,58.4079113),
 (456,'Taksomotor awtoulag k??rhanasy',37.9384377,58.4277463),
 (457,'Nurmuhammet Andalyp k????esi',37.9531249,58.4125359),
 (458,'Hoja Ahmet ??asawy k????esi',37.941478,58.4217765),
 (459,'9-njy mekdep',37.9500733,58.4090514),
 (460,'I??eri i??ler ministrligi',37.9678354,58.400358),
 (461,'Kuli??ew k????esi, Serpa??',37.9670786,58.4082181),
 (462,'Azady metjidi',37.9480444,58.4072248),
 (463,'Genji hazyna bazary',37.9442033,58.4174801),
 (464,'La??yn myhmanhanasy',37.963016,58.3995854),
 (465,'Gurbansoltan eje adyndaky mekdep',37.9558484,58.3936151),
 (466,'La??yn myhmanhanasy',37.9619986,58.3987498),
 (467,'I??eri i??ler ministrligi',37.9657608,58.4003853),
 (468,'Genji hazyna bazary',37.9449799,58.4154711),
 (469,'21-nji ??agalar bagy',37.9607934,58.3882992),
 (470,'Hoja Ahmet ??asawy k????esi, 235',37.9596408,58.3898334),
 (471,'A??gabat dokma toplumy',37.9701456,58.3971446),
 (472,'I??erki aeroporty ugrundan',37.9618206,58.3798996),
 (473,'Halkbank',37.9613378,58.3977267),
 (474,'I??erki aeroporty ugruna',37.9674075,58.3847151),
 (475,'Bitarap T??rkmenistan ??a??oly, Kamaz',37.9708955,58.3965754),
 (476,'Halkara aeroporty ugruna',37.9699919,58.3874506),
 (477,'Garagum myhmanhanasy',37.9667691,58.3847872),
 (478,'Hristian gonam??ylygy',37.9543836,58.3994898),
 (479,'Garagum myhmanhanasy',37.9629733,58.3829171),
 (480,'Gurbansoltan eje k????esi',37.947162,58.3883308),
 (481,'Kuli??ew k????esi, Rysgal',37.9700951,58.4039961),
 (482,'Atamyrat Ny??azow ??a??oly, 50',37.9609468,58.3797382),
 (483,'Halkara aeroporty',37.9818713,58.3753339),
 (484,'Halkara aeroporty',37.9814943,58.375076),
 (485,'Kuli??ew k????esi',37.9794192,58.3915955),
 (486,'K??rler mekdep internaty',37.9474846,58.4114231),
 (487,'Kuli??ew k????esi, A??ly di??ar',37.9725282,58.4006194),
 (488,'Bagt ??oly restoran',37.949997,58.3940368),
 (489,'Atamyrat Ny??azow (Gagarina) ??a??oly',37.991166,58.4095726),
 (490,'Atamyrat Ny??azow (Gagarina) ??a??oly',37.985487,58.4056724),
 (491,'Atamyrat Ny??azow (Gagarina) ??a??oly',37.9968026,58.4079584),
 (492,'G??ndogar (Tolku??ka) bazary',38.0386206,58.4059368),
 (493,'Atamyrat Ny??azow (Gagarina) ??a??oly',38.0228826,58.4054311),
 (494,'Atamyrat Ny??azow (Gagarina) ??a??oly',38.0237173,58.4064274),
 (495,'Altyn suw myhmanhanasy',37.8894584,58.3520394),
 (496,'Gara??syzlyk ??a??oly',37.9014078,58.3629634),
 (497,'Abadan??ylyk ??a??oly, 147',37.9012535,58.3474817),
 (498,'Medeni??et instituty',37.897402,58.3667845),
 (499,'Gyrgyzstany?? we Gazagystany?? il??ihanalary',37.8925711,58.3548601),
 (500,'Abadan??ylyk ??a??oly, 128',37.89871,58.3573485),
 (501,'Parahat 2/2, 18',37.8959119,58.3806874),
 (502,'Medeni??et merkezi',37.8787176,58.3822847),
 (503,'Bagty??arlyk s??wda merkezi',37.9159933,58.3681695),
 (504,'Oguzhan k????esi, 38/2',37.9101487,58.420159),
 (505,'Halkara ??ikes merkezi',37.9508461,58.3389172),
 (506,'K????i, 129',37.959683,58.324455),
 (507,'K????i, 53',37.9698406,58.33417),
 (508,'K????i, 9',37.9765651,58.3405889),
 (509,'65-nji mekdep',37.9443179,58.366006),
 (510,'??impa?? s??wda merkezi',37.9158652,58.3818075),
 (511,'Be??ik Saparmyrat T??rkmenba??y ??a??oly',37.876498,58.377032),
 (512,'Berze????i ??ypahanasy',37.8515395,58.3595342),
 (513,'Berze????i',37.8500074,58.3758312),
 (514,'Serhet??iler instituty',37.8369465,58.3763668);
INSERT INTO "test" ("id","busId","busStopId") VALUES (1,1,1),
 (2,1,2),
 (3,2,3),
 (4,2,4);
COMMIT;
