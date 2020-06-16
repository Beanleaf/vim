FROM postgres

COPY ./vim-persistence/src/main/resources/db/setup/0_database.sql /tmp/
COPY ./vim-persistence/src/main/resources/db/setup/1_roles_users.sql /tmp/
COPY ./.docker/database-setup.sh /docker-entrypoint-initdb.d/