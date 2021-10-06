create table person (
                        id serial primary key not null,
                        login varchar(2000),
                        password varchar(2000)
);

insert into person (login, password) values ('ekoles', '123');
insert into person (login, password) values ('ban', '1');
insert into person (login, password) values ('ivan', '1');