package io.questdb.testcontainers;

import org.jetbrains.annotations.NotNull;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.wait.LogMessageWaitStrategy;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import static java.time.temporal.ChronoUnit.SECONDS;

public class QuestDbContainer <SELF extends QuestDbContainer<SELF>> extends JdbcDatabaseContainer<SELF> {

    private String username = "admin";
    private String password = "quest";

    private static final int POSTGRESQL_PORT = 8812;


    QuestDbContainer(String dockerImageName) {
        super(dockerImageName);
        this.waitStrategy = new LogMessageWaitStrategy()
                .withRegEx(".*server-main started.*")
                .withTimes(1)
                .withStartupTimeout(Duration.of(30, SECONDS));
    }

    @NotNull
    @Override
    protected Set<Integer> getLivenessCheckPorts() {
        return new HashSet<>(getMappedPort(POSTGRESQL_PORT));
    }

    @Override
    protected void configure() {
        addExposedPort(POSTGRESQL_PORT);
        addEnv("POSTGRES_USER", username);
        addEnv("POSTGRES_PASSWORD", password);
    }

    @Override
    public String getDriverClassName() {
        return "org.postgresql.Driver";
    }

    @Override
    public String getJdbcUrl() {
        // Disable Postgres driver use of java.util.logging to reduce noise at startup time
        return "jdbc:postgresql://"+getContainerIpAddress() + ":" + getMappedPort(POSTGRESQL_PORT) + "/";
    }

    @Override
    public SELF withDatabaseName(String dbName) {
        return self();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getTestQueryString() {
        return "select 1 from long_sequence(1)";
    }


    @Override
    public SELF withUsername(final String username) {
        this.username = username;
        return self();
    }

    @Override
    public SELF withPassword(final String password) {
        this.password = password;
        return self();
    }

    @Override
    protected void waitUntilContainerStarted() {
        getWaitStrategy().waitUntilReady(this);
    }
}
