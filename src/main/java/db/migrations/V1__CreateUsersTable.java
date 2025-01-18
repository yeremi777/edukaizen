package db.migrations;

import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import liquibase.change.custom.CustomTaskChange;
import liquibase.change.custom.CustomTaskRollback;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.RollbackImpossibleException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;

public class V1__CreateUsersTable implements CustomTaskChange, CustomTaskRollback {
    @Override
    public void execute(Database database) throws CustomChangeException {
        try {
            JdbcConnection connection = (JdbcConnection) database.getConnection();

            try (Statement stmt = connection.createStatement()) {
                stmt.execute("""
                        CREATE TABLE roles (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            created_at TIMESTAMP DEFAULT NOW(),
                            updated_at TIMESTAMP DEFAULT NOW()
                        );

                        CREATE TABLE users (
                            id SERIAL PRIMARY KEY,
                            role_id INT NOT NULL,
                            first_name VARCHAR(255) NOT NULL,
                            last_name VARCHAR(255) NOT NULL,
                            username VARCHAR(255) NOT NULL UNIQUE,
                            email VARCHAR(255) NOT NULL UNIQUE,
                            password VARCHAR(255) NOT NULL,
                            phone_number VARCHAR(255),
                            profile_image VARCHAR(255),
                            title VARCHAR(255),
                            biography TEXT,
                            social_media JSON,
                            external_service VARCHAR(255),
                            external_service_user_id VARCHAR(255),
                            validated_as_instructor_at TIMESTAMP,
                            created_at TIMESTAMP DEFAULT NOW(),
                            updated_at TIMESTAMP DEFAULT NOW(),

                            FOREIGN KEY (role_id) REFERENCES roles(id)
                        );
                        """);

                String[] roles = { "Admin", "Instructor", "Student" };

                List<String> rolesSql = Arrays.asList(roles).stream().map(role -> {
                    return String.format("('%s')", role);
                }).collect(Collectors.toList());

                stmt.execute(String.format("INSERT INTO roles (name) VALUES %s", String.join(", ", rolesSql)));

                String createAdmin = """
                        INSERT INTO users (role_id, first_name, last_name, username, email, password) VALUES (1, 'Admin', 'Admin', 'admin', 'admin@admin.com', '$2a$10$9nEpr9V3tcqDxhC8s78Fc.s8zdgcEYhjPQ9uJcU1xWXkwfLNJIDAO');
                        """; // password = edukaizen123
                stmt.execute(createAdmin);
            }
        } catch (Exception e) {
            throw new CustomChangeException("Failed to execute migration: " + e.getMessage(), e);
        }
    }

    @Override
    public void rollback(Database database) throws CustomChangeException, RollbackImpossibleException {
        try {
            String[] tables = { "users", "roles" };

            JdbcConnection connection = (JdbcConnection) database.getConnection();

            List<String> dropSql = Arrays.asList(tables).stream().map(table -> {
                return String.format("DROP TABLE IF EXISTS %s; \n", table);
            }).collect(Collectors.toList());

            try (Statement stmt = connection.createStatement()) {
                stmt.execute(String.join("", dropSql));
            }
        } catch (Exception e) {
            throw new RollbackImpossibleException("Failed to execute rollback: " + e.getMessage(), e);
        }
    }

    @Override
    public String getConfirmationMessage() {
        return "Users table migration executed successfully.";
    }

    @Override
    public void setUp() throws SetupException {
    }

    @Override
    public void setFileOpener(ResourceAccessor resourceAccessor) {
    }

    @Override
    public ValidationErrors validate(Database database) {
        return new ValidationErrors();
    }
}
