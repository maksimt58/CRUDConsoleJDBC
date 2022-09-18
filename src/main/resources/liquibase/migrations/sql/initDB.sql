-- liquibase formatted sql
-- changeset liquibase:1

CREATE TABLE writers
(
id int NOT NULL auto_increment,
first_name varchar(255) NOT NULL ,
last_name varchar(255) NOT NULL ,
PRIMARY KEY (id)
);

CREATE TABLE posts
(
    id int NOT NULL auto_increment,
    content varchar(255) NOT NULL ,
    status varchar(255) NOT NULL ,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    writer_id int NOT NULL ,
    foreign key (writer_id) references writers (id) ON DELETE CASCADE on update cascade,
    PRIMARY KEY (id)
);

CREATE TABLE labels
(
    id int NOT NULL auto_increment,
    name varchar(255) NOT NULL unique,
    PRIMARY KEY (id)
);

CREATE TABLE post_labels
(
    post_id int NOT NULL,
    label_id int NOT NULL,
    foreign key (post_id) references posts (id) ON DELETE CASCADE on update cascade,
    foreign key (label_id) references labels (id) ON DELETE CASCADE on update cascade,
    UNIQUE (post_id,label_id)
);