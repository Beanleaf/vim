/** Makre sure there is an administrator */
DO
$$
    BEGIN
        IF NOT EXISTS(
                SELECT id
                FROM users
                WHERE user_role = 99
            )
        THEN
            INSERT INTO users(id, username, active, email_address, name, uuid, user_role,
                              language_tag)
            VALUES (nextval('users_id_seq'), 'admin', true, 'admin@vim.com', 'Administrator',
                    'admin', 99, 'en');
        END IF;
    END
$$;