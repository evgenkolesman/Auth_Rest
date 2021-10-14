create table if not exists employees (
    id BIGSERIAL PRIMARY KEY,
    surname varchar(100),
    name varchar(100),
    INN int,
    hired timestamp,
    person_id int references person(id)
);

insert into employees(surname, name, INN, person_id) VALUES ('Andersen', 'Milder', 134, 1)