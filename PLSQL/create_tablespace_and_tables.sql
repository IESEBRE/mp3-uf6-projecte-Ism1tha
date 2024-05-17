-- Create tablespace for programs
CREATE TABLESPACE programs_tablespace
    DATAFILE 'programs_datafile.dbf'
    SIZE 100M
    AUTOEXTEND ON
    NEXT 10M
    MAXSIZE UNLIMITED;

-- Create user for programs
CREATE USER programs_user
    IDENTIFIED BY test1234
    DEFAULT TABLESPACE programs_tablespace
    TEMPORARY TABLESPACE temp;

-- Grant privileges to 'programs_user'
GRANT ALL PRIVILEGES TO programs_user;

-- Connect to the database with the user 'programs_user'

CONNECT programs_user/test1234@//localhost:1521/XEPDB1

-- Create sequence for programs table
CREATE SEQUENCE programs_seq
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE;

-- Create 'programs' table
CREATE TABLE programs (
    id INT DEFAULT programs_seq.NEXTVAL PRIMARY KEY,
    name VARCHAR2(255) NOT NULL,
    description CLOB,
    category VARCHAR2(50),
    language VARCHAR2(50),
    release_date DATE,
    password NUMBER(4,0),
    paid NUMBER(1,0) DEFAULT 0
);

-- Create sequence for versions table
CREATE SEQUENCE versions_seq
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE;

-- Create 'versions' table
CREATE TABLE versions (
    id INT DEFAULT versions_seq.NEXTVAL PRIMARY KEY,
    program_id INT,
    version VARCHAR2(50),
    release_date DATE,
    commits INT,
    FOREIGN KEY (program_id) REFERENCES programs(id)
);