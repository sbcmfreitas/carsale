MERGE INTO BRAND 
  KEY(ID) 
VALUES (1, 'Volkswagen'),
  (2, 'Ford'),
  (3, 'Opel');

MERGE INTO OPTION
  KEY(ID) 
VALUES (1, 'Back Sensor'),
  (2, 'Park Assist'),
  (3, 'Sky Window');  

MERGE INTO CLIENT
  KEY(ID) 
VALUES (1 ,'sbc.mfreitas@bol.com.br','Marcos Roberto','Freitas','910537364');

MERGE INTO CAR
  KEY(ID) 
VALUES (1 ,'52000', 1); 

MERGE INTO CAR_OPTION
  KEY(CAR_ID) 
VALUES (1 ,1); 

DELETE FROM CAR_ORDER WHERE ID = 1;
INSERT INTO CAR_ORDER VALUES(1, CURRENT_TIMESTAMP(), 'CANCELED', 520, 1, 1 );