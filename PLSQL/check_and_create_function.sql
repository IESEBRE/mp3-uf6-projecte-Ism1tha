CREATE OR REPLACE FUNCTION check_and_create_tables RETURN VARCHAR2 IS
    v_table_count INT;
BEGIN
    -- Comprova si la taula 'programs' existeix
SELECT COUNT(*)
INTO v_table_count
FROM user_tables
WHERE table_name = 'PROGRAMS';

-- Si la taula 'programs' no existeix, crea totes les taules
IF v_table_count = 0 THEN
        create_tables; -- crida al procediment emmagatzemat per crear taules
RETURN 'NEW';
    ELSE
        RETURN 'ALREADY_EXISTS';
    END IF;
END;
/