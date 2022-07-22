create table cars (
    manufacturer text,
    carmodel text,
    vin text,
    id int,
    addingdate date,
    productionyear int
);

create table checkups (
    carid int,
    checkupdatetime date,
    id int,
    worker text,
    price int
);