drop database if exists concesionario;
create database concesionario;
use concesionario;

create table vendedor(
id int unsigned auto_increment primary key,
dni VARCHAR(9) unique not null,
nombre varchar(30) not null,
apellido1 varchar(60) not null,
apellido2 varchar(60)
);

create table modelo(
id int unsigned auto_increment primary key,
nombre_marca varchar(30) not null,
nombre_modelo varchar(100) not null 
);

create table coche(
id int unsigned auto_increment primary key,
matricula varchar(7) unique not null,
kilometraje int unsigned not null default 0,
potencia int unsigned not null, 
color varchar(20),
precio int unsigned not null,
estado enum('d','v') default 'd' not null,
id_vendedor int unsigned not null,
id_modelo int unsigned not null,
foreign key (id_vendedor) references vendedor(id),
foreign key (id_modelo) references modelo(id)
);

create table cliente(
id int unsigned auto_increment primary key,
dni VARCHAR(9) unique not null,
nombre varchar(30) not null,
apellido1 varchar(60) not null,
apellido2 varchar(60)
);

create table pedido (
id int unsigned auto_increment primary key,
numero_pedido int unique not null,
fecha_pedido date not null,
id_cliente int unsigned not null,
foreign key (id_cliente) references cliente(id)
);

create table linea_pedido(
id_pedido int unsigned not null,
id_coche int unsigned not null,
foreign key (id_pedido) references pedido(id),
foreign key (id_coche) references coche(id)
);

insert into modelo values(1,'a','a');
insert into vendedor values(1,'a','a','a','a');
insert into coche values(1, 'a',0,0,'a',0,'d',1,1);

insert into modelo values(2,'b','b');
insert into vendedor values(2,'b','b','b','b');
insert into coche values(2, 'b',1,1,'b',0,'d',2,2);

select * from coche;