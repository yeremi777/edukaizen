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

public class V3__CreateCoursesTable implements CustomTaskChange, CustomTaskRollback {
    @Override
    public void execute(Database database) throws CustomChangeException {
        try {
            JdbcConnection connection = (JdbcConnection) database.getConnection();

            try (Statement stmt = connection.createStatement()) {
                stmt.execute("""
                        CREATE TABLE courses (
                            id SERIAL PRIMARY KEY,
                            title VARCHAR(255) NOT NULL,
                            sub_title VARCHAR(255),
                            categories INT[],
                            sub_categories INT[],
                            tools INT[],
                            level VARCHAR(255),
                            price NUMERIC(10, 2) NOT NULL,
                            language_id INT,
                            subtitles INT[],
                            duration INT NOT NULL,
                            created_at TIMESTAMP DEFAULT NOW(),
                            updated_at TIMESTAMP DEFAULT NOW(),

                            FOREIGN KEY (language_id) REFERENCES master_languages(id)
                        );

                        CREATE TABLE course_details (
                            id SERIAL PRIMARY KEY,
                            course_id INT NOT NULL,
                            description text,
                            thumbnail VARCHAR(255),
                            trailer VARCHAR(255),
                            course_points JSON,
                            course_audience JSON,
                            course_requirements JSON,
                            welcome_message TEXT,
                            congratulation_message TEXT,
                            created_at TIMESTAMP DEFAULT NOW(),
                            updated_at TIMESTAMP DEFAULT NOW(),

                            FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
                        );

                        CREATE TABLE course_instructors (
                            course_id INT NOT NULL,
                            instructor_id INT NOT NULL,
                            is_creator BOOLEAN NOT NULL DEFAULT FALSE,

                            FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
                            FOREIGN KEY (instructor_id) REFERENCES users(id) ON DELETE CASCADE
                        );

                        CREATE TABLE course_wishlists (
                            course_id INT NOT NULL,
                            student_id INT NOT NULL,

                            FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
                            FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE
                        );
                        """);
            }
        } catch (Exception e) {
            throw new CustomChangeException("Failed to execute migration: " + e.getMessage(), e);
        }
    }

    @Override
    public void rollback(Database database) throws CustomChangeException, RollbackImpossibleException {
        try {
            String[] tables = { "course_wishlists", "course_instructors", "course_details", "courses" };

            JdbcConnection connection = (JdbcConnection) database.getConnection();

            try (Statement stmt = connection.createStatement()) {
                List<String> dropSql = Arrays.asList(tables).stream().map(table -> {
                    return String.format("""
                            DROP TABLE IF EXISTS %s;
                            """, table);
                }).collect(Collectors.toList());

                stmt.execute(String.join("", dropSql));
            }
        } catch (Exception e) {
            throw new RollbackImpossibleException("Failed to execute rollback: " + e.getMessage(), e);
        }
    }

    @Override
    public String getConfirmationMessage() {
        return "Courses table migration executed successfully.";
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
