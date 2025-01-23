create table movie(
		id serial primary key,
		"name" varchar(40),
		description varchar(200)
);

select *
from movie;


create table place(
		id serial primary key,
		number varchar(5) not null
);

select *
from place;


create table session(
		id serial primary key,
		movie_id int not null references movie(id),
		time timestamp not null,
		price numeric(10,2) not null
);

select *
from session;


create table ticket(
		id serial,
		place_id int not null references place(id),
		session_id int not null references session(id),
		is_bought boolean not null 
)

select *
from ticket;
































