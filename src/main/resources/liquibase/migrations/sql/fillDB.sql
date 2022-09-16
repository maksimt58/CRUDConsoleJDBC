-- liquibase formatted sql
-- changeset liquibase:2

insert into writers (firstname, lastname) values ('Maks', 'Tata');
insert into writers (firstname, lastname) values ('Nata', 'Tata'); 
insert into writers (firstname, lastname) values ('John', 'Dou'); 
insert into writers (firstname, lastname) values ('Ivan', 'Bunin'); 
insert into writers (firstname, lastname) values ('Eugene', 'Proselyte');


insert into posts (content, WriterId, Status) values ('Java in my life', 1, 'ACTIVE');
insert into posts (content, WriterId, Status) values ('SQL in my life', 2, 'ACTIVE');
insert into posts (content, WriterId, Status) values ('C# in my life', 3, 'DELETED');
insert into posts (content, WriterId, Status) values ('CI/CD in my life', 4, 'UNDER_REVIEW');
insert into posts (content, WriterId, Status) values ('DevOps in my life', 5, 'UNDER_REVIEW');
insert into posts (content, WriterId, Status) values ('Support in my life', 1, 'ACTIVE');
insert into posts (content, WriterId, Status) values ('Sport in my life', 1, 'ACTIVE');


insert into labels (name) values ('programming languages');
insert into labels (name) values ('Kafka');
insert into labels (name) values ('sport');
insert into labels (name) values ('lifehacks');
insert into labels (name) values ('life');
insert into labels (name) values ('hackers');
insert into labels (name) values ('travel');


insert into postlabel (postid, labelid) values (1 , 1);
insert into postlabel (postid, labelid) values (2 , 1);
insert into postlabel (postid, labelid) values (3 , 1);
insert into postlabel (postid, labelid) values (4 , 2);
insert into postlabel (postid, labelid) values (4 , 4);
insert into postlabel (postid, labelid) values (5 , 4);
insert into postlabel (postid, labelid) values (6 , 5);
insert into postlabel (postid, labelid) values (7 , 5);
insert into postlabel (postid, labelid) values (7 , 3);
