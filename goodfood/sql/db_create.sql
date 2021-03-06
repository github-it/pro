CREATE TABLE USER (
  ID        INT PRIMARY KEY AUTO_INCREMENT,
  LOGINEMAIL VARCHAR(255),
  PASSWORD  VARCHAR(255),
  BALANCE   INT,
  STATUS  VARCHAR(55)
);

CREATE TABLE ADDRESS (
  ID        INT PRIMARY KEY AUTO_INCREMENT,
  USER_ID   INT,
  STREET  VARCHAR(255),
  BUILDING  VARCHAR(255),
  APARTMENT  VARCHAR(255)
);

ALTER TABLE ADDRESS
ADD FOREIGN KEY (USER_ID) REFERENCES USER (ID);

CREATE TABLE FOOD (
  ID        INT PRIMARY KEY AUTO_INCREMENT,
  NAME  VARCHAR(255),
  DESCRIPTION  VARCHAR(255)
);

CREATE TABLE PROPERTY (
  ID        INT PRIMARY KEY AUTO_INCREMENT,
  NAME  VARCHAR(255)  
);

CREATE TABLE CONTENT (
  ID        INT PRIMARY KEY AUTO_INCREMENT,
  FOOD_ID   INT,
  PROPERTY_ID   INT,
  PRICE  INT,
  QUANTITY_ALL   INT
);

ALTER TABLE CONTENT
ADD FOREIGN KEY (FOOD_ID) REFERENCES FOOD (ID);

ALTER TABLE CONTENT
ADD FOREIGN KEY (PROPERTY_ID) REFERENCES PROPERTY (ID);

CREATE TABLE CLIENT_ORDER (
  ID        INT PRIMARY KEY AUTO_INCREMENT,
  USER_ID   INT,
  ADDRESS_ID   INT,
  FIRSTNAME  VARCHAR(255),
  PHONE_NUMBER  VARCHAR(255),
  TOTALSUM  INT,
  DATE DATE,
  STATUS  VARCHAR(55)
);

ALTER TABLE CLIENT_ORDER
ADD FOREIGN KEY (USER_ID) REFERENCES USER (ID);

ALTER TABLE CLIENT_ORDER
ADD FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESS (ID);

CREATE TABLE RESERVE (
  ID        INT PRIMARY KEY AUTO_INCREMENT,
  CLIENT_ORDER_ID   INT,
  CONTENT_ID   INT,
  QUANTITY_RESERVE  INT
);

ALTER TABLE RESERVE
ADD FOREIGN KEY (CLIENT_ORDER_ID) REFERENCES CLIENT_ORDER (ID);

ALTER TABLE RESERVE
ADD FOREIGN KEY (CONTENT_ID) REFERENCES CONTENT (ID);