/** Create DML and DDL ROLES */
DO
$$
    BEGIN
        IF NOT EXISTS(
                SELECT *
                FROM pg_catalog.pg_group
                WHERE groname = 'vim_dml'
            )
        THEN
            EXECUTE 'CREATE ROLE vim_dml;';
        END IF;
    END
$$;

DO
$$
    BEGIN
        IF NOT EXISTS(
                SELECT *
                FROM pg_catalog.pg_group
                WHERE groname = 'vim_ddl'
            )
        THEN
            EXECUTE 'CREATE ROLE vim_ddl;';
        END IF;
    END
$$;

DO
$$
    BEGIN
        IF NOT EXISTS(
                SELECT *
                FROM pg_catalog.pg_group
                WHERE groname = 'vim_ro'
            )
        THEN
            EXECUTE 'CREATE ROLE vim_ro;';
        END IF;
    END
$$;

/** Create application user and flyway application user with default passwords */
DO
$$
    BEGIN
        IF NOT EXISTS(
                SELECT *
                FROM pg_catalog.pg_roles
                WHERE rolname = 'vim'
            )
        THEN
            EXECUTE 'CREATE ROLE vim LOGIN PASSWORD ''vim'' VALID UNTIL ''infinity'';';
        END IF;
    END
$$;

DO
$$
    BEGIN
        IF NOT EXISTS(
                SELECT *
                FROM pg_catalog.pg_roles
                WHERE rolname = 'vim_dba'
            )
        THEN
            EXECUTE 'CREATE ROLE vim_dba LOGIN PASSWORD ''vim_dba'' VALID UNTIL ''infinity'';';
        END IF;
    END
$$;

/* Setting privileges for dml and ddl, while revoking all from public. */
DO
$$
    BEGIN
        EXECUTE 'GRANT CONNECT ON DATABASE ' || current_database() ||
                ' TO vim_ddl, vim_dml, vim_ro;';
        EXECUTE 'GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public' ||
                ' TO vim_dml;';
        EXECUTE 'GRANT USAGE ON ALL SEQUENCES IN SCHEMA public TO vim_ddl;';
        EXECUTE 'GRANT ALL ON DATABASE ' || current_database() || ' TO vim_ddl;';
        EXECUTE 'REVOKE ALL ON DATABASE ' || current_database() || ' FROM public;';
    END
$$;

/* Grant our application user and flyway user the correct role and privileges to get started */
GRANT USAGE ON SCHEMA PUBLIC TO vim_dml;
GRANT USAGE ON SCHEMA PUBLIC TO vim_ro;
GRANT vim_dml TO vim;
GRANT vim_ddl TO vim_dba;
REVOKE ALL ON SCHEMA PUBLIC FROM PUBLIC;
GRANT ALL ON SCHEMA PUBLIC TO vim_ddl;