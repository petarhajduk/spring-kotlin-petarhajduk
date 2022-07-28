create table cars (
    manufacturer text,
    carmodel text,
    vin text,
    id uuid primary key,
    addingdate date,
    productionyear int
);

create table checkups (
    car uuid not null constraint car_fk references cars(id),
    checkupdate date,
    id uuid primary key,
    worker text,
    price int
);

Insert into cars (manufacturer, carmodel, vin, id, addingdate, productionyear)
values ('Fiat', 'Brava', 'U9U9BC9UCHTHEO4', gen_random_uuid(), '2020-01-01', 2002);

Insert into cars (manufacturer, carmodel, vin, id, addingdate, productionyear)
values ('Mazda', '6', 'N83B89G74BDJC9U', gen_random_uuid(), '2020-07-05', 2008);

insert into checkups (car, checkupdate, id, worker, price)
values ((select id from cars where vin = 'U9U9BC9UCHTHEO4'), '2021-04-21', gen_random_uuid(), 'Siniša', 300);
insert into checkups (car, checkupdate, id, worker, price)
values ((select id from cars where vin = 'U9U9BC9UCHTHEO4'), '2020-04-21', gen_random_uuid(), 'Siniša', 300);
insert into checkups (car, checkupdate, id, worker, price)
values ((select id from cars where vin = 'N83B89G74BDJC9U'), '2022-04-21', gen_random_uuid(), 'Siniša', 300);