CREATE OR REPLACE PROCEDURE create_tables AS
BEGIN
    -- Create sequence for programs table
EXECUTE IMMEDIATE 'CREATE SEQUENCE programs_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE';

-- Create 'programs' table
EXECUTE IMMEDIATE '
CREATE TABLE programs (
                          id INT DEFAULT programs_seq.NEXTVAL PRIMARY KEY,
                          name VARCHAR2(255) NOT NULL,
                          description CLOB,
                          category VARCHAR2(50),
                          language VARCHAR2(50),
                          release_date DATE,
                          password NUMBER(4,0),
                          paid NUMBER(1,0) DEFAULT 0
)';

-- Create sequence for versions table
EXECUTE IMMEDIATE 'CREATE SEQUENCE versions_seq START WITH 1 INCREMENT BY 1 NOMAXVALUE';

-- Create 'versions' table
EXECUTE IMMEDIATE '
CREATE TABLE versions (
                          id INT DEFAULT versions_seq.NEXTVAL PRIMARY KEY,
                          program_id INT,
                          version VARCHAR2(50),
                          release_date DATE,
                          commits INT,
                          FOREIGN KEY (program_id) REFERENCES programs(id)
)';

END;
