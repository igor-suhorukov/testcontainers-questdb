package io.questdb.testcontainers;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.*;

public class QuestDbDriverTest {

    @Test
    void containerIsUpTestByJdbcInvocation() throws Exception {
        try (Connection connection = DriverManager.getConnection("jdbc:tc:questdb:///?user=admin&password=quest")){
            try (Statement statement = connection.createStatement()){
                try (ResultSet resultSet = statement.executeQuery("select 42 from long_sequence(1)")){
                    resultSet.next();
                    assertThat(resultSet.getInt(1)).isEqualTo(42);
                }
            }
        }
    }
}
