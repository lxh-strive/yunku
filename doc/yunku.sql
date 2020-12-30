
DROP TABLE IF EXISTS VideoSubType;
DROP TABLE IF EXISTS VideoParentType;
DROP TABLE IF EXISTS Video;
DROP TABLE IF EXISTS VideoComm;
DROP TABLE IF EXISTS CommReply;
DROP TABLE IF EXISTS UserSugges;
DROP TABLE IF EXISTS VideoColl;
DROP TABLE IF EXISTS UserInte;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS UserSign;
create table VideoSubType(
sub_id	int primary key AUTO_INCREMENT,
parent_id	int,
name	varchar(50),
logo	varchar(200)
);
create table VideoParentType(
parent_id	int primary key AUTO_INCREMENT,
name	varchar(50),
state	int,
logo	varchar(200)
);
create table Video(
video_id	int primary key AUTO_INCREMENT,
name	varchar(20),
src	varchar(50),
parent_id	int,
sub_id	int,
user_id	int,
pub_time	timestamp,
video_desc	text,
pic	varchar(100),
play_number	bigint,
down_number	bigint,
down_points	int,
mark	varchar(200),
size	bigint,
state	char(1)
);
create table VideoComm(
com_id	int primary key AUTO_INCREMENT,
video_id	int,
user_id	int,
comcon	text,
comtime	timestamp,
state	char(1)
);
create table CommReply(
rep_id	int,
com_id	int,
user_id	varchar(20),
repcon	text,
reptime	timestamp,
state	char(1)
);
create table UserSugges
(
sugges_id	int primary key AUTO_INCREMENT,
video_id	int,
com_id	int,
rep_id	int,
user_id	int,
type	char(1),
remark	varchar(2),
operTime	timestamp
);
create table VideoColl(
coll_id	int primary key AUTO_INCREMENT,
video_id	int,
user_id	int,
coll_time	timestamp
);

CREATE TABLE Userinte (
  inte_Id int(11) PRIMARY KEY AUTO_INCREMENT,
  user_id int(11) ,
  inte int(11) ,
  des  varchar(100) ,
  time timestamp ,
  bal  int(11) 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

create table User(
user_id	int  PRIMARY KEY AUTO_INCREMENT,
username	varchar(20),
password	char(32),
email	varchar(50),
pic	varchar(200),
mobile	char(11),
inte	int,
reg_time	timestamp,
up_time	timestamp,
login_state	char(1),
grade	int,
state	int
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
create table UserSign  (
sign_id	int,
user_id	int,
sign_date	date,
days int
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
