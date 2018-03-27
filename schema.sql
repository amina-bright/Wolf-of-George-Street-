USE WolfOfGeorgeStreetDB;

CREATE TABLE User (
	username VARCHAR(25),
    pw VARCHAR(25),
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    email VARCHAR(50),
    emailUpdates BIT,
    creationTime DATETIME,
    PRIMARY KEY(username)
);


CREATE TABLE League (
	leagueID INT,
    leagueName VARCHAR(50),
    size INT,
    username VARCHAR(25),
    startDate DATETIME,
    endDate DATETIME,
    crypto BIT,
    startingPrinciple REAL,
    gameMode BIT,
    constraint foreign key(username) references User(username) ON Delete Cascade,
    PRIMARY KEY(leagueID)
);


CREATE TABLE Transaction (
	username VARCHAR(25),
    amount REAL,
    asset VARCHAR(20),
    transcationType BIT,
    transcationTime DATETIME,
    leagueID INT,
    foreign key(username) references User(username) On delete cascade,
    foreign key(leagueID) references League(leagueID) On delete cascade,
    primary key(username, transcationTime, leagueID)
);

CREATE TABLE Asset (
	username VARCHAR(25),
    amount real,
    asset VARCHAR(10),
    leagueID int,
    foreign key(username) references User(username) on delete CASCADE,
    foreign key(leagueID) REFERENCES League(leagueID) on delete Cascade,
    primary key(username,asset,leagueID)
);

CREATE TABLE LeagueUserList(
	username VARCHAR(25) references User,
    leagueID INT REFERENCES League,
    liquidMoney REAL,
    FOREIGN KEY(username) REFERENCES User(username) on delete Cascade,
    Foreign Key(leagueID) references League(leagueID) on delete CASCADE,
    primary key(username, leagueID)
);


SELECT * From User;