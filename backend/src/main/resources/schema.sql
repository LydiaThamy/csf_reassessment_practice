-- CREATE DATABASE second_hand;

USE second_hand;

CREATE TABLE postings(
	posting_id	char(8)	not null,
    posting_date	varchar(128) not null,
    name	varchar(128)	not null,
    email	varchar(128),
    phone	varchar(128) default '',
    title	varchar(256),
    description	mediumtext,
    image	varchar(256),
    constraint pk_posting_id primary key(posting_id) 
)