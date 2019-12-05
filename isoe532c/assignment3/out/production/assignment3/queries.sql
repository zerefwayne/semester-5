create table events
(
	eventsID varchar(30) not null,
	name varchar(50) null,
	performanceType varchar(30) not null,
	numberOfParticipants varchar(40) not null,
	batch varchar(40) not null,
	judge1ID int not null,
	judge2ID int not null,
	judge3ID int not null,
);

create table jmarks
(
	marks int not null,
	judgeID int not null,
	performanceID int not null
);


create table judges
(
	judgeID int auto_increment
		primary key,
	username varchar(50) not null,
	email varchar(50) not null,
	password varchar(30) not null
);

