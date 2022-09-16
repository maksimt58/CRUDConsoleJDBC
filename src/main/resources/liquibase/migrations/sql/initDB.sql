-- liquibase formatted sql
-- changeset liquibase:1

CREATE TABLE writers
(
id int NOT NULL auto_increment,
FirstName varchar(255) NOT NULL ,
LastName varchar(255) NOT NULL ,
PRIMARY KEY (id)
);

CREATE TABLE posts
(
    id int NOT NULL auto_increment,
    Content varchar(255) NOT NULL ,
    Status varchar(255) NOT NULL ,
    CreateDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UpdateDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    WriterId int NOT NULL ,
    foreign key (WriterId) references Writers (id) ON DELETE CASCADE on update cascade,
    PRIMARY KEY (id)
);

CREATE TABLE labels
(
    id int NOT NULL auto_increment,
    name varchar(255) NOT NULL unique,
    PRIMARY KEY (id)
);

CREATE TABLE postLabel
(
    PostId int NOT NULL,
    LabelId int NOT NULL,
    foreign key (PostId) references Posts (id) ON DELETE CASCADE on update cascade,
    foreign key (LabelId) references Labels (id) ON DELETE CASCADE on update cascade
);