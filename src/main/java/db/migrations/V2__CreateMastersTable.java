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

public class V2__CreateMastersTable implements CustomTaskChange, CustomTaskRollback {
    private final String[] tables = { "master_categories", "master_subcategories", "master_tools",
            "master_languages" };

    @Override
    public void execute(Database database) throws CustomChangeException {
        try {
            JdbcConnection connection = (JdbcConnection) database.getConnection();

            try (Statement stmt = connection.createStatement()) {
                List<String> createSql = Arrays.asList(this.tables).stream().map(table -> {
                    if (table == "master_subcategories") {
                        return String.format("""
                                CREATE TABLE %s (
                                    id SERIAL PRIMARY KEY,
                                    category_id INT NOT NULL,
                                    name VARCHAR(255) NOT NULL,
                                    created_at TIMESTAMP DEFAULT NOW(),
                                    updated_at TIMESTAMP DEFAULT NOW(),

                                    FOREIGN KEY (category_id) REFERENCES master_categories(id) ON DELETE CASCADE
                                ); \n
                                """, table);
                    }

                    return String.format("""
                            CREATE TABLE %s (
                                id SERIAL PRIMARY KEY,
                                name VARCHAR(255) NOT NULL,
                                created_at TIMESTAMP DEFAULT NOW(),
                                updated_at TIMESTAMP DEFAULT NOW()
                            ); \n
                            """, table);
                }).collect(Collectors.toList());

                stmt.execute(String.join("", createSql));
            }
        } catch (Exception e) {
            throw new CustomChangeException("Failed to execute migration: " + e.getMessage(), e);
        }
    }

    @Override
    public void rollback(Database database) throws CustomChangeException, RollbackImpossibleException {
        try {
            JdbcConnection connection = (JdbcConnection) database.getConnection();

            try (Statement stmt = connection.createStatement()) {
                List<String> dropSql = Arrays.asList(this.tables).stream().map(table -> {
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
        return "Masters table migration executed successfully.";
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
