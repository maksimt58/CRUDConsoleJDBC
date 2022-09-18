-- liquibase formatted sql
-- changeset liquibase:2

insert into writers (first_name, last_name) values ('Maks', 'Tata');
insert into writers (first_name, last_name) values ('Nata', 'Tata');
insert into writers (first_name, last_name) values ('John', 'Dou');
insert into writers (first_name, last_name) values ('Ivan', 'Bunin');
insert into writers (first_name, last_name) values ('Eugene', 'Proselyte');


insert into posts (content, writer_id, status) values ('Java in my life', 1, 'ACTIVE');
insert into posts (content, writer_id, status) values ('SQL in my life', 2, 'ACTIVE');
insert into posts (content, writer_id, status) values ('C# in my life', 3, 'DELETED');
insert into posts (content, writer_id, status) values ('CI/CD in my life', 4, 'UNDER_REVIEW');
insert into posts (content, writer_id, status) values ('DevOps in my life', 5, 'UNDER_REVIEW');
insert into posts (content, writer_id, status) values ('Support in my life', 1, 'ACTIVE');
insert into posts (content, writer_id, status) values ('Sport in my life', 1, 'ACTIVE');


insert into labels (name) values ('programming languages');
insert into labels (name) values ('Kafka');
insert into labels (name) values ('sport');
insert into labels (name) values ('lifehacks');
insert into labels (name) values ('life');
insert into labels (name) values ('hackers');
insert into labels (name) values ('travel');


insert into post_labels (post_id, label_id) values (1 , 1);
insert into post_labels (post_id, label_id) values (2 , 1);
insert into post_labels (post_id, label_id) values (3 , 1);
insert into post_labels (post_id, label_id) values (4 , 2);
insert into post_labels (post_id, label_id) values (4 , 4);
insert into post_labels (post_id, label_id) values (5 , 4);
insert into post_labels (post_id, label_id) values (6 , 5);
insert into post_labels (post_id, label_id) values (7 , 5);
insert into post_labels (post_id, label_id) values (7 , 3);
