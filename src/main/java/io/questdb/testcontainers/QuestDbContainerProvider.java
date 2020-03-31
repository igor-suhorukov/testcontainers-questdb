package io.questdb.testcontainers;

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.JdbcDatabaseContainerProvider;
import org.testcontainers.jdbc.ConnectionUrl;

public class QuestDbContainerProvider extends JdbcDatabaseContainerProvider {

    private static final String USER_PARAM = "user";
    private static final String PASSWORD_PARAM = "password";

    public QuestDbContainerProvider() {
    }

    public boolean supports(String databaseType) {
        return databaseType.equals("questdb");
    }

    public JdbcDatabaseContainer newInstance() {
        return this.newInstance("4.1.5");
    }

    public JdbcDatabaseContainer newInstance(String tag) {
        return new QuestDbContainer("questdb/questdb:" + tag);
    }

    public JdbcDatabaseContainer newInstance(ConnectionUrl connectionUrl) {
        return this.newInstanceFromConnectionUrl(connectionUrl, USER_PARAM, PASSWORD_PARAM);
    }
}
